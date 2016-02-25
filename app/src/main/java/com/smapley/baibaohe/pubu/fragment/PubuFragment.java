package com.smapley.baibaohe.pubu.fragment;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.Activity.Goods;
import com.smapley.baibaohe.Activity.TouchImageViewActivity;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.pubu.ResponseCallback;
import com.smapley.baibaohe.pubu.adapter.DataManager;
import com.smapley.baibaohe.pubu.extra.swiprefreshlayout.VerticalSwipeRefreshLayout;
import com.smapley.baibaohe.pubu.library.BaseRecyclerAdapter;
import com.smapley.baibaohe.pubu.library.DividerGridItemDecoration;
import com.smapley.baibaohe.pubu.library.ExRecyclerView;
import com.smapley.baibaohe.pubu.library.ExStaggeredGridLayoutManager;
import com.smapley.baibaohe.pubu.library.OnRecyclerViewScrollListener;
import com.smapley.baibaohe.pubu.mode.Photo;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by smapley on 2015/6/7.
 */
public class PubuFragment extends Fragment implements ResponseCallback {

    private final String TAG = getClass().getSimpleName();

    private Context mContext;
    private ExRecyclerView waterFallRcv;

    private BaseRecyclerAdapter waterFallAdapter;

    private Button footerBtn;

    private DataManager mDataManager;

    private VerticalSwipeRefreshLayout swipeRefreshLayout;
    private View contentView;
    private int SRC;
    private static PuBuCallBack mPuBuCallBack;
    private final int UPDATA = 1;


    public static PubuFragment newPubu(int src, PuBuCallBack puBuCallBack) {
        PubuFragment fragment = new PubuFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("src", src);
        fragment.setArguments(bundle);
        mPuBuCallBack = puBuCallBack;
        return fragment;
    }

    public static PubuFragment newPubu(int src) {
        PubuFragment fragment = new PubuFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("src", src);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.pubu_main_layout, container, false);
        SRC = getArguments().getInt("src");
        if (SRC != -1) {
            mDataManager = new DataManager(SRC);
        }
        mContext = getActivity();
        findViews(contentView);
        setViews();
        if (mDataManager != null) {
            mDataManager.loadNewData(this);
        }
        return contentView;
    }


    protected void findViews(View view) {
        swipeRefreshLayout = (VerticalSwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        waterFallRcv = (ExRecyclerView) view.findViewById(R.id.waterFall_recyclerView);
        footerBtn = new Button(mContext);
    }

    protected void setViews() {
        setWaterFallRcv();
        setFooterView();
        footerBtn.setVisibility(View.GONE);
        waterFallRcv.smoothScrollToPosition(0);
        swipeRefreshLayout.setProgressBackgroundColor(R.color.no);
        swipeRefreshLayout.setColorSchemeResources(R.color.no);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                mPuBuCallBack.onScrollDown();
            }
        });
    }


    private boolean isLoadingData = false;


    public void Refresh() {
        if (!isLoadingData) {
            Log.d(TAG, "load new data");
            mDataManager.loadNewData(PubuFragment.this);
            isLoadingData = true;
            footerBtn.setText(R.string.loding);
            footerBtn.setVisibility(View.GONE);
        }
    }

    /**
     * 设置瀑布流控件
     */
    private void setWaterFallRcv() {
        // 设置头部或底部的操作应该在setAdapter之前
        waterFallRcv.addFooterView(footerBtn);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new ExStaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        //GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getBaseContext(), LinearLayoutManager.VERTICAL, true);// 可替换
        waterFallRcv.setLayoutManager(staggeredGridLayoutManager);

        // 添加分割线
        waterFallRcv.addItemDecoration(new DividerGridItemDecoration(mContext));
        //waterFallRcv.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));//可替换


        waterFallRcv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                return false;
            }
        });


        List<Photo> list = new ArrayList<>();// 先放一个空的list
        waterFallAdapter = new BaseRecyclerAdapter(list);
        waterFallRcv.setAdapter(waterFallAdapter);

        waterFallAdapter.setClickListener(new BaseRecyclerAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, Photo data) {
                if (data.picid != null) {
                    Intent intent = new Intent(getActivity(), Goods.class);
                    intent.putExtra("picid", data.picid);
                    getActivity().startActivity(intent);
                } else {
                    Intent intent = new Intent(getActivity(), TouchImageViewActivity.class);
                    intent.putExtra("pic", data.pic);
                    intent.putExtra("height", data.height);
                    intent.putExtra("weight", data.weight);
                    getActivity().startActivity(intent);
                }
            }

            @Override
            public void onLongClick(View view, final Photo data) {
                if (MyData.CANDELECT) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(R.string.tips);
                    builder.setMessage(R.string.delect);
                    builder.setNegativeButton(R.string.cancel, null);
                    builder.setPositiveButton(R.string.okay, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    HashMap map = new HashMap();
                                    map.put("dpicid", data.dpicid);
                                    mhandler.obtainMessage(UPDATA, HttpUtils.updata(map, MyData.URL_DELDPIC)).sendToTarget();
                                }
                            }).start();
                        }
                    });
                    builder.create().show();

                }
            }
        });


        // 不显示滚动到顶部/底部的阴影（减少绘制）
        waterFallRcv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        //waterFallRcv.setClipToPadding(true);


        /**
         * 监听滚动事件
         */
        waterFallRcv.setOnScrollListener(new OnRecyclerViewScrollListener() {

            @Override
            public void onScrollUp() {
                Log.d(TAG, "onScrollUp");
                mPuBuCallBack.onScrollUp();
            }

            @Override
            public void onScrollDown() {
                Log.d(TAG, "onScrollDown");

            }

            @Override
            public void onBottom() {

            }

            @Override
            public void onMoved(int distanceX, int distanceY) {
                Log.d(TAG, "distance X = " + distanceX + "distance Y = " + distanceY);
                if (distanceY < 10) {

                }
            }
        });


    }

    /**
     * 设置底部的view
     */
    private void setFooterView() {
        footerBtn.setText(R.string.loding);
        footerBtn.setBackgroundColor(getResources().getColor(R.color.white));
        footerBtn.getBackground().setAlpha(80);

    }

    @Override
    public void onSuccess(List<Photo> list) {
        isLoadingData = false;
        footerBtn.setVisibility(View.GONE);
        if (list != null) {
            waterFallAdapter.updateData(list);
        }
        if (mPuBuCallBack != null) {
            mPuBuCallBack.onSuccess();
        }
    }

    @Override
    public void onError(int msg) {
        try {
            if (getString(msg).equals(getString(R.string.nomore))) {
                footerBtn.setText(msg);
            } else {
                Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
            }
            onSuccess(null);

        } catch (Exception e) {

        }
    }


    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                switch (msg.what) {
                    case UPDATA:
                        Map map = JSON.parseObject(msg.obj.toString(), new TypeReference<Map>() {
                        });
                        if (Integer.parseInt(map.get("count").toString()) > 0) {
                            Toast.makeText(getActivity(), R.string.delected, Toast.LENGTH_SHORT).show();
                            Refresh();
                        } else {
                            Toast.makeText(getActivity(), R.string.delectfaile, Toast.LENGTH_SHORT).show();

                        }

                        break;
                }

            } catch (Exception e) {
                Toast.makeText(getActivity(), R.string.changfile, Toast.LENGTH_SHORT).show();
            }
        }
    };

    public interface PuBuCallBack {
        void onSuccess();

        void onScrollUp();

        void onScrollDown();
    }

}

