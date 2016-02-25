package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smapley on 2015/7/10.
 */
public class TiXian extends Activity {

    private EditText item1;
    private EditText item2;
    private EditText item3;
    private EditText item4;

    private TextView item5;
    private ProgressDialog progressDialog;

    private final int UPDATA = 1;

    private final int GETDATA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tixian);
        progressDialog = new ProgressDialog(this);

        initView();
        getData();
    }

    private void initView() {
        item1 = (EditText) findViewById(R.id.tixian_item1);
        item2 = (EditText) findViewById(R.id.tixian_item2);
        item3 = (EditText) findViewById(R.id.tixian_item3);
        item4 = (EditText) findViewById(R.id.tixian_item4);
        item5 = (TextView) findViewById(R.id.tixian_item5);
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!item1.getText().toString().equals("") && !item2.getText().toString().equals("")
                        && !item3.getText().toString().equals("") && !item4.getText().toString().equals("")) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HashMap map = new HashMap();
                            map.put("phone", MyData.PHONE);
                            map.put("yinhang", item1.getText().toString());
                            map.put("ming", item2.getText().toString());
                            map.put("zhang", item3.getText().toString());
                            map.put("gold", item4.getText().toString());
                            mhandler.obtainMessage(UPDATA, HttpUtils.updata(map, MyData.URL_ADDTIXIAN)).sendToTarget();
                        }
                    }).start();
                } else {
                    Toast.makeText(TiXian.this, R.string.nonull, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("phone", MyData.PHONE);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETTIXIAN)).sendToTarget();
            }
        }).start();
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case GETDATA:
                        Map map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (map != null && !map.isEmpty()) {
                            if (map.get("yinhang") != null) {
                                item1.setText(map.get("yinhang").toString());
                            }
                            if (map.get("ming") != null) {
                                item2.setText(map.get("ming").toString());
                            }
                            if (map.get("zhang") != null) {
                                item3.setText(map.get("zhang").toString());
                            }
                        }
                        break;
                    case UPDATA:
                        progressDialog.dismiss();
                        Map map2 = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (map2 != null && !map2.isEmpty()) {
                            if (Integer.parseInt(map2.get("newid").toString()) > 0) {
                                Toast.makeText(TiXian.this, R.string.tixianed, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(TiXian.this, R.string.tixianfile, Toast.LENGTH_SHORT).show();
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                Toast.makeText(TiXian.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
