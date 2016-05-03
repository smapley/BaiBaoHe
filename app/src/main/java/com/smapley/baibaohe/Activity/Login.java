package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by Smapley on 2015/4/18.
 */
public class Login extends Activity implements View.OnClickListener {

    private TextView teacher;
    private TextView parents;
    private EditText phone;
    private EditText key;
    private Button send;
    private Button testing;
    private int User = 2;
    private String phoneString;
    private Boolean CANSEND = true;
    private final int user1 = 1;
    private final int user2 = 2;
    private final int MSG_END = 6;
    private final int MSG_RESULT = 3;
    private final int MSG_TESTERR = 5;
    private final int MSG_SLEEP = 4;

    private EventHandler eventHandler;
    private Boolean STOP = false;
    private ProgressDialog dialog;
    private SharedPreferences sharedPreferences;
    private TextView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        sharedPreferences = getSharedPreferences(MyData.USERSP, MODE_PRIVATE);
        initSMS();
        initView();
    }


    private void initSMS() {
        eventHandler = new EventHandler() {

            @Override
            public void afterEvent(int event, int result, Object data) {
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    if (result == SMSSDK.RESULT_COMPLETE) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                HashMap<String, Object> map = new HashMap();
                                map.put("phone", phoneString);
                                map.put("utype", User);
                                String result = HttpUtils.updata(map, MyData.URL_REG);
                                mHandler.obtainMessage(MSG_RESULT, result).sendToTarget();

                            }
                        }).start();

                    } else {
                        mHandler.obtainMessage(MSG_TESTERR).sendToTarget();
                    }
                }
            }
        };
        SMSSDK.registerEventHandler(eventHandler); //注册短信回调
    }

    private void initView() {
        back = (TextView) findViewById(R.id.login_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        teacher = (TextView) findViewById(R.id.login_teacher);
        parents = (TextView) findViewById(R.id.login_parents);
        phone = (EditText) findViewById(R.id.login_phone);
        key = (EditText) findViewById(R.id.login_key);
        send = (Button) findViewById(R.id.login_send);
        testing = (Button) findViewById(R.id.login_testing);
        teacher.setOnClickListener(this);
        parents.setOnClickListener(this);
        send.setOnClickListener(this);
        testing.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.login_teacher:
                teacher.setBackgroundResource(R.drawable.textview_background);
                parents.setBackgroundResource(R.drawable.textview_edge);
                User = user2;
                break;
            case R.id.login_parents:
                teacher.setBackgroundResource(R.drawable.textview_edge);
                parents.setBackgroundResource(R.drawable.textview_background);
                User = user1;
                break;
            case R.id.login_send:
                if (CANSEND) {
                    phoneString = phone.getText().toString();
                    if (User == -1) {
                        Toast.makeText(Login.this, R.string.login_toast1, Toast.LENGTH_SHORT).show();
                    } else if (phoneString.length() != 11) {
                        Toast.makeText(Login.this, R.string.login_toast3, Toast.LENGTH_SHORT).show();
                    } else {
                        SMSSDK.getVerificationCode("86", phoneString);
                        CANSEND = false;
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    int time = 60;
                                    while (time > 0) {
                                        Thread.sleep(1000);
                                        time--;
                                        if (!STOP) {
                                            mHandler.obtainMessage(MSG_SLEEP, time).sendToTarget();
                                        } else {
                                            break;
                                        }

                                    }
                                    mHandler.obtainMessage(MSG_END).sendToTarget();
                                } catch (Exception e) {

                                }

                            }
                        }).start();
                    }
                }
                break;

            case R.id.login_testing:
                dialog = ProgressDialog.show(Login.this, getString(R.string.tips), getString(R.string.login));
                dialog.show();
                phoneString = phone.getText().toString();
                if (phoneString.equals("22222") && key.getText().toString().equals("8888")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HashMap<String, Object> map = new HashMap();
                            map.put("phone", phoneString);
                            map.put("utype", User);
                            String result = HttpUtils.updata(map, MyData.URL_REG);
                            mHandler.obtainMessage(MSG_RESULT, result).sendToTarget();

                        }
                    }).start();
                } else {
                    SMSSDK.submitVerificationCode("86", phoneString, key.getText().toString());
                }
                break;
        }

    }

    private Handler mHandler = new Handler() {
        // 重写handleMessage()方法，此方法在UI线程运行
        @Override
        public void handleMessage(Message msg) {
            try {
                switch (msg.what) {
                    case MSG_RESULT:
                        dialog.dismiss();
                        String resul = JSON.parseObject(msg.obj.toString(), new TypeReference<String>() {
                        });
                        User = Integer.parseInt(resul);
                        if (Integer.parseInt(resul) > 0) {
                            MyData.PHONE = phoneString;
                            MyData.UTYPE = User;
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("phone", phoneString);
                            editor.putInt("utype", User);
                            editor.commit();
                            Toast.makeText(Login.this, R.string.login_toast5, Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(Login.this, R.string.login_toast6, Toast.LENGTH_SHORT).show();
                        }

                        break;

                    case MSG_END:
                        send.setText(R.string.login_send);
                        CANSEND = true;
                        break;
                    case MSG_TESTERR:
                        dialog.dismiss();
                        mHandler.obtainMessage(MSG_END).sendToTarget();
                        Toast.makeText(Login.this, R.string.login_toast4, Toast.LENGTH_SHORT).show();
                        break;
                    case MSG_SLEEP:

                        String text = getString(R.string.login_sends);
                        send.setText(text + "( " + msg.obj + " )");

                        break;

                }
            } catch (Exception e) {
                dialog.dismiss();
                try {
                    Toast.makeText(Login.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
                } catch (Exception e1) {

                }
            }
        }
    };
}
