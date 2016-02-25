package com.smapley.baibaohe.holder;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.Main2Head;
import com.smapley.baibaohe.mode.Main2Item;
import com.smapley.baibaohe.newAdapter.Main2NewAdapter;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.util.HashMap;
import java.util.List;

/**
 * Created by smapley on 2015/6/25.
 */
public class Main2HeadHolder extends RecyclerView.ViewHolder {

    private TextView item1;
    private TextView item2;
    private TextView item3;
    private TextView item4;
    private TextView item5;
    private TextView item6;
    private TextView item7;
    private TextView item8;
    private final int GETDATA = 1;
    private Main2NewAdapter adapter;
    private ProgressDialog progressDialog;

    public Main2HeadHolder(View view) {
        super(view);
        item1 = (TextView) view.findViewById(R.id.main2_item1);
        item2 = (TextView) view.findViewById(R.id.main2_item2);
        item3 = (TextView) view.findViewById(R.id.main2_item3);
        item4 = (TextView) view.findViewById(R.id.main2_item4);
        item5 = (TextView) view.findViewById(R.id.main2_item5);
        item6 = (TextView) view.findViewById(R.id.main2_item6);
        item7 = (TextView) view.findViewById(R.id.main2_item7);
        item8 = (TextView) view.findViewById(R.id.main2_item8);
    }

    public void setData(final Main2Head mainHead, final Context context, Main2NewAdapter adapter) {
        this.adapter = adapter;
        progressDialog = new ProgressDialog(context);
        item1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("0");
            }
        });
        item2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("1");
            }
        });
        item3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("2");
            }
        });
        item4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("3");
            }
        });
        item5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("4");
            }
        });
        item6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("5");
            }
        });
        item7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("6");
            }
        });
        item8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData("7");
            }
        });
    }

    private void getData(final String data) {
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("lx", data);
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETSJDP)).sendToTarget();

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
//                        if (list1 != null && !list1.isEmpty()) {
                            List list = list1;
                            list.add(0, new Main2Head());
                            adapter.listitem = list;
                            adapter.notifyDataSetChanged();
//                        }
                        break;

                }

            } catch (Exception e) {
            }
        }
    };
}

