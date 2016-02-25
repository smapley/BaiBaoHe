package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 2015/7/30.
 */
public class FuKuanResult extends Activity {

    private TextView item1;
    private TextView item2;
    private TextView item3;
    private TextView item4;
    private final int GETDATA = 1;
    private String fuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fukuanresult);
        initData();
        initView();
        getData();
    }

    private void initData() {
        fuid = getIntent().getStringExtra("id");
    }

    private void initView() {
        item1 = (TextView) findViewById(R.id.fukuan_item1);
        item2 = (TextView) findViewById(R.id.fukuan_item2);
        item3 = (TextView) findViewById(R.id.fukuan_item3);
        item4 = (TextView) findViewById(R.id.fukuan_item4);
    }

    private void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("dingdan", fuid);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETFUJILU1)).sendToTarget();

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
                        List<Map> list = JSON.parseObject(msg.obj.toString(), new TypeReference<List<Map>>() {
                        });
                        if (list != null && !list.isEmpty()) {
                            item1.setText(list.get(0).get("gold").toString());
                            item2.setText(list.get(0).get("fumima").toString());
                            item3.setText(list.get(0).get("snm").toString());
                            item4.setText(list.get(0).get("tm").toString());
                        }

                        break;
                }
            } catch (Exception e) {
                Toast.makeText(FuKuanResult.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
