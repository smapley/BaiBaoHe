package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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
 * Created by smapley on 2015/8/4.
 */
public class ZheKou extends Activity {

    private String user2;
    private final int GETDATA = 1;
    private TextView item1;
    private TextView item2;
    private TextView item3;
    private TextView item4;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhekou);
        initData();
        initView();
        getData();

    }

    private void initData() {
        user2 = getIntent().getStringExtra("user2");
    }

    private void initView() {
        item1 = (TextView) findViewById(R.id.set_min);
        item2 = (TextView) findViewById(R.id.set_max);
        item3 = (TextView) findViewById(R.id.set_min2);
        item4 = (TextView) findViewById(R.id.set_max2);
        listView = (ListView) findViewById(R.id.zhekou_list);
    }

    private void getData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("user2", user2);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETJILUZK)).sendToTarget();
            }
        }).start();
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                List<Map<String, Object>> list = JSON.parseObject(msg.obj.toString(), new TypeReference<List<Map<String, Object>>>() {
                });
                if (list != null && !list.isEmpty()) {
                    item1.setText(list.get(0).get("zmin").toString());
                    item3.setText(list.get(0).get("zmin").toString());
                    item2.setText(list.get(0).get("zmax").toString());
                    item4.setText(list.get(0).get("zmax2").toString());
                    list.remove(0);
                    SimpleAdapter adapter = new SimpleAdapter(ZheKou.this, list, R.layout.layout_zhekou_item,
                            new String[]{"buyer", "zk", "tm"},
                            new int[]{R.id.zhekou_item_1, R.id.zhekou_item_2, R.id.zhekou_item_3});

                    listView.setAdapter(adapter);

                }

            } catch (Exception e) {
                Toast.makeText(ZheKou.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
