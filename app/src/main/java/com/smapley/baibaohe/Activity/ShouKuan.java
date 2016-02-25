package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.Adapter.ShouKuanJiLuAdapter;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by smapley on 2015/7/11.
 */
public class ShouKuan extends Activity {

    private ListView listView;
    private ProgressDialog progressDialog;
    private ShouKuanJiLuAdapter adapter;

    private final int GETDATA = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoukuan);
        progressDialog = new ProgressDialog(this);

        initView();
        getData();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.shoukuan_list);

    }

    private void getData() {
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("phone", MyData.PHONE);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETSHOUJILU)).sendToTarget();
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
                        progressDialog.dismiss();
                        List list = JSON.parseObject(msg.obj.toString(), new TypeReference<List>() {
                        });
                        if (list != null && !list.isEmpty()) {
                            adapter = new ShouKuanJiLuAdapter(ShouKuan.this, list);
                            listView.setAdapter(adapter);
                        }
                        break;

                }
            } catch (Exception e) {
                Toast.makeText(ShouKuan.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
