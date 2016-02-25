package com.smapley.baibaohe.Fragment;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.Main2Head;
import com.smapley.baibaohe.mode.Main2Item;
import com.smapley.baibaohe.newAdapter.Main2NewAdapter;
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
public class MainItem2 extends Fragment {

    private SharedPreferences sharedPreferences;
    private View contentView;
    private final int GETDATA = 1;
    private final int GETSEARCH = 2;
    private RecyclerView listView;
    private EditText searchText;
    private TextView search;
    private ProgressDialog progressDialog;
    private Main2NewAdapter adapter;
    private GetBitmap getBitmap;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_main_item2, container, false);
        getBitmap = new GetBitmap(getActivity());
        initView(contentView);
        getData();
        return contentView;
    }

    private void initView(View view) {
        progressDialog = new ProgressDialog(getActivity());
        searchText = (EditText) view.findViewById(R.id.main2_search_text);
        searchText.clearFocus(); //Çå³ý½¹µã
        search = (TextView) view.findViewById(R.id.main2_search);
        listView = (RecyclerView) view.findViewById(R.id.item2_list);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        adapter = new Main2NewAdapter(getActivity(), new ArrayList(), getBitmap);
        listView.setAdapter(adapter);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchText.getText().equals("")) {
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HashMap map = new HashMap();
                            map.put("sou", searchText.getText().toString());
                            mhandler.obtainMessage(GETSEARCH, HttpUtils.updata(map, MyData.URL_GETMOHUDP)).sendToTarget();

                        }
                    }).start();
                    searchText.setText("");
                }
            }
        });
    }


    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap map = new HashMap();
                map.put("lx", "0");
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
                        List<Main2Item> list1 = JSON.parseObject(msg.obj.toString(), new TypeReference<List<Main2Item>>() {
                        });
                        if (list1 != null && !list1.isEmpty()) {
                            List list = list1;
                            list.add(0, new Main2Head());
                            adapter.listitem = list;
                            adapter.notifyDataSetChanged();
                        }
                        break;
                    case GETSEARCH:
                        progressDialog.dismiss();
                        List<Main2Item> list2 = JSON.parseObject(msg.obj.toString(), new TypeReference<List<Main2Item>>() {
                        });
                        if (list2 != null && !list2.isEmpty()) {
                            List list = list2;
                            list.add(0, new Main2Head());
                            adapter.listitem = list;
                            adapter.notifyDataSetChanged();
                        }
                        break;

                }

            } catch (Exception e) {
            }
        }
    };

}

