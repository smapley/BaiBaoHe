package com.smapley.baibaohe.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.Main2Item;
import com.smapley.baibaohe.mode.SouSuoHead;
import com.smapley.baibaohe.newAdapter.SouSuoAdapter;
import com.smapley.baibaohe.pubu.library.DividerItemDecoration;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;
import com.smapley.baibaohe.utls.http.bitmap.GetBitmap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by smapley on 2015/5/11.
 */
public class SouSuo extends Activity {

    private SharedPreferences sharedPreferences;
    private View contentView;
    private final int GETDATA = 1;
    private final int GETSEARCH = 2;
    private RecyclerView listView;
    private ProgressDialog progressDialog;
    private SouSuoAdapter adapter;
    private GetBitmap getBitmap;
    private String number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sousuo);
        getBitmap = new GetBitmap(SouSuo.this);
        progressDialog = new ProgressDialog(SouSuo.this);
        number = getIntent().getStringExtra("bian");
        MyData.BIAN = number;
        initView();
        getData();
    }

    private void initView() {
        listView = (RecyclerView) findViewById(R.id.item2_list);
        listView.setLayoutManager(new LinearLayoutManager(SouSuo.this));
        listView.addItemDecoration(new DividerItemDecoration(SouSuo.this, LinearLayoutManager.VERTICAL));
        adapter = new SouSuoAdapter(SouSuo.this, new ArrayList(), getBitmap);
        listView.setAdapter(adapter);
    }


    private void getData() {
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("bian", number);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETLIAN)).sendToTarget();

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
                        List<Main2Item> list1 = JSON.parseObject(msg.obj.toString(), new TypeReference<List<Main2Item>>() {
                        });
                        if (list1 != null && !list1.isEmpty()) {
                            List list = list1;
                            list.add(0, new SouSuoHead(getString(R.string.sousuo_1)));
                            list.add(2, new SouSuoHead(getString(R.string.sousuo_2)));
                            adapter.listitem = list;
                            adapter.notifyDataSetChanged();
                        } else {


                        }
                        break;

                }

            } catch (Exception e) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SouSuo.this);
                builder.setTitle(R.string.tips);
                builder.setMessage(R.string.jiangquan_bucunzais);
                builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.create().show();
            }
        }
    };
}

