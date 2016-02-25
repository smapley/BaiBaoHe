package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.Adapter.JiangQuanAdapter;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 2015/6/14.
 */
public class JiangQuan extends Activity {
    private ListView listView;
    private final int GETDATA = 1;
    private final int UPDATA = 2;
    private TextView shuliang;
    private TextView shengcheng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiangquan);
        initView();
        getData();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.jiangquan_list);
        shuliang = (TextView) findViewById(R.id.jiangquan_shuliang);
        shengcheng = (TextView) findViewById(R.id.jiangquan_okay);
        shengcheng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(JiangQuan.this);
                builder.setTitle(R.string.tip1);
                final EditText editText = new EditText(JiangQuan.this);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(editText);
                builder.setNegativeButton(R.string.cancel, null);
                builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!editText.getText().toString().equals("")) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HashMap map = new HashMap();
                                    map.put("sl", editText.getText().toString());
                                    map.put("user2", MyData.PHONE);
                                    mhandler.obtainMessage(UPDATA, HttpUtils.updata(map, MyData.URL_ADDSC)).sendToTarget();
                                }
                            }).start();
                        }
                    }
                });
                builder.create().show();
            }
        });


    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("user2", MyData.PHONE);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETSL)).sendToTarget();
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
                        final List<Map> list = JSON.parseObject(msg.obj.toString(), new TypeReference<List<Map>>() {
                        });
                        if (list != null && !list.isEmpty()) {
                            JiangQuanAdapter adapter = new JiangQuanAdapter(JiangQuan.this, list);
                            listView.setAdapter(adapter);
                            shuliang.setText(getString(R.string.jiangquan1) + list.size() + getString(R.string.jiangquan2));
                        }
                        break;
                    case UPDATA:
                        getData();
                        break;
                }
            } catch (Exception e) {
                Toast.makeText(JiangQuan.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }
        }
    };
}