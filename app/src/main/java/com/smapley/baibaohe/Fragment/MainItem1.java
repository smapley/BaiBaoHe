package com.smapley.baibaohe.Fragment;

import android.content.Intent;
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
import com.example.listview.SlideInOutRightItemAnimator;
import com.smapley.baibaohe.Activity.SouSuo;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.mode.MainHead;
import com.smapley.baibaohe.mode.MainItem;
import com.smapley.baibaohe.newAdapter.MainNewAdapter;
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
public class MainItem1 extends Fragment {

    private SharedPreferences sharedPreferences;
    private View contentView;
    private final int GETDATA = 1;
    private final int GETHEAD = 2;
    private RecyclerView listView;
    private MainNewAdapter adapter;
    private EditText searchText;
    private TextView search;
    private GetBitmap getBitmap;
    private boolean haseHead = false;
    private String bianid;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_main_item1, container, false);
        getBitmap = new GetBitmap(getActivity());
        initView(contentView);
        getData();
        return contentView;
    }

    private void initView(View view) {
        searchText = (EditText) view.findViewById(R.id.main_search_text);
        searchText.clearFocus(); //Çå³ý½¹µã
        search = (TextView) view.findViewById(R.id.main_search);
        listView = (RecyclerView) view.findViewById(R.id.item1_list);
        listView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        adapter = new MainNewAdapter(getActivity(), new ArrayList(), getBitmap);
        listView.setAdapter(adapter);
        listView.setItemAnimator(new SlideInOutRightItemAnimator(listView));
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchText.getText().toString()!=null&&!searchText.getText().toString().isEmpty()) {
                    Intent intent = new Intent(getActivity(), SouSuo.class);
                    intent.putExtra("type", 1);
                    intent.putExtra("bian", searchText.getText().toString());
                    startActivity(intent);
                    searchText.setText("");
                }
            }
        });
    }


    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mhandler.obtainMessage(GETHEAD, HttpUtils.updata(null, MyData.URL_GETGFPIC)).sendToTarget();
                mhandler.obtainMessage(GETDATA, HttpUtils.updata(null, MyData.URL_GETSHOUYE)).sendToTarget();
                while (true) {
                    try {
                        Thread.sleep(10000);
                    } catch (Exception e) {

                    }
                    HashMap map = new HashMap();
                    map.put("bianid", bianid);
                    mhandler.obtainMessage(GETDATA, HttpUtils.updata(map, MyData.URL_GETSHOUYE1)).sendToTarget();

                }

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
                        List<MainItem> list1 = JSON.parseObject(msg.obj.toString(), new TypeReference<List<MainItem>>() {
                        });
                        if (list1 != null && !list1.isEmpty()) {
                            bianid = list1.get(0).bianid;
                            for (int i = list1.size(); i > 0; i--) {
                                if (haseHead) {
                                    adapter.add(list1.get(i - 1), 1);
                                } else {
                                    adapter.add(list1.get(i - 1), 0);
                                }
                            }

                        }
                        break;
                    case GETHEAD:
                        List<MainItem> list2 = JSON.parseObject(msg.obj.toString(), new TypeReference<List<MainItem>>() {
                        });
                        if (list2 != null && !list2.isEmpty()) {
                            MainHead mainHead = new MainHead();
                            mainHead.list = list2;
                            adapter.add(mainHead, 0);
                            haseHead = true;
                            //   initHeadView(list2);
                        }
                        break;
                }

            } catch (Exception e) {
            }
        }
    };
}

