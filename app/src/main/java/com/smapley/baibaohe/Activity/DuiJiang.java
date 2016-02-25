package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.Adapter.DuiJiangAdapter;
import com.smapley.baibaohe.Adapter.ShouKuanAdapter;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by smapley on 2015/6/13.
 */
public class DuiJiang extends Activity {

    private ListView listView;
    private final int GETDATA1 = 1;
    private final int GETDATA2 = 2;
    private final int GETDATA3 = 3;
    private EditText searchText;
    private TextView search;
    private ImageView jiangquan;
    private ProgressDialog progressDialog;
    private TextView duijiang;
    private TextView shoukuan;
    private int TYPE = 1;
    private DuiJiangAdapter adapter1;
    private ShouKuanAdapter adapter2;
    private TextView tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.duijiang);
        progressDialog = new ProgressDialog(this);
        initView();
    }

    private void initView() {
        adapter1 = new DuiJiangAdapter(this, new ArrayList());
        adapter2 = new ShouKuanAdapter(this, new ArrayList());
        duijiang = (TextView) findViewById(R.id.duijiang_duijiang);
        shoukuan = (TextView) findViewById(R.id.duijiang_shoukuan);
        listView = (ListView) findViewById(R.id.duijiang_list);
        searchText = (EditText) findViewById(R.id.duijiang_search_text);
        search = (TextView) findViewById(R.id.duijiang_search);
        jiangquan = (ImageView) findViewById(R.id.duijiang_jiangquan);
        tips=(TextView)findViewById(R.id.duijiang_tip);
        if (MyData.UTYPE != 1) {
            jiangquan.setVisibility(View.GONE);
            getGongHao();
        }
        jiangquan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DuiJiang.this, Phone.class));
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchText.getText().equals("")) {
                    getData();
                }
            }
        });

        duijiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TYPE = 1;
                duijiang.setBackgroundResource(R.color.chengse);
                shoukuan.setBackgroundDrawable(null);
                duijiang.setTextColor(getResources().getColor(R.color.white2));
                shoukuan.setTextColor(getResources().getColor(R.color.chengse));
                searchText.setText("");
                searchText.setHint(R.string.search_text);
                if (adapter1 != null) {
                    listView.setAdapter(adapter1);
                }
            }
        });
        shoukuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TYPE = 2;
                duijiang.setBackgroundDrawable(null);
                shoukuan.setBackgroundResource(R.color.chengse);
                duijiang.setTextColor(getResources().getColor(R.color.chengse));
                shoukuan.setTextColor(getResources().getColor(R.color.white2));
                searchText.setText("");
                searchText.setHint(R.string.search_texts);
                if (adapter2 != null) {
                    listView.setAdapter(adapter2);
                }
            }
        });
    }

    public void getData() {
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("sou", searchText.getText().toString());
                map.put("phone", MyData.PHONE);
                if (TYPE == 1) {
                    mhandler.obtainMessage(GETDATA1, HttpUtils.updata(map, MyData.URL_GETSOUDJ)).sendToTarget();
                } else {
                    mhandler.obtainMessage(GETDATA2, HttpUtils.updata(map, MyData.URL_GETSOUSK)).sendToTarget();

                }
            }
        }).start();
    }


    private void getGongHao(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("phone",MyData.PHONE);
                mhandler.obtainMessage(GETDATA3,HttpUtils.updata(map,MyData.URL_GETDYLIST1)).sendToTarget();
            }
        }).start();
    }

    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case GETDATA1:
                        progressDialog.dismiss();
                        List list = JSON.parseObject(msg.obj.toString(), new TypeReference<List>() {
                        });
                        if (list != null && !list.isEmpty()) {
                            adapter1 = new DuiJiangAdapter(DuiJiang.this, list);
                            listView.setAdapter(adapter1);
                        } else {
                            Toast.makeText(DuiJiang.this, R.string.jiangquan_1, Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case GETDATA2:
                        progressDialog.dismiss();
                        List list2 = JSON.parseObject(msg.obj.toString(), new TypeReference<List>() {
                        });
                        if (list2 != null && !list2.isEmpty()) {
                            adapter2 = new ShouKuanAdapter(DuiJiang.this, list2);
                            listView.setAdapter(adapter2);
                        } else {
                            Toast.makeText(DuiJiang.this, R.string.jiangquan_2, Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case GETDATA3:
                        List<Map> list3 = JSON.parseObject(msg.obj.toString(), new TypeReference<List>() {
                        });
                        if(list3!=null&&!list3.isEmpty()){
                            tips.setText(getString(R.string.duijiang_2)+list3.get(0).get("dyid").toString());
                        }
                        break;
                }
            } catch (Exception e) {
                Toast.makeText(DuiJiang.this, R.string.connectfile, Toast.LENGTH_SHORT).show();
            }
        }
    };
}
