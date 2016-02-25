package com.smapley.baibaohe.pubu.adapter;

import android.os.Handler;
import android.os.Message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.smapley.baibaohe.R;
import com.smapley.baibaohe.pubu.ResponseCallback;
import com.smapley.baibaohe.pubu.mode.Photo;
import com.smapley.baibaohe.utls.MyData;
import com.smapley.baibaohe.utls.http.HttpUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Jack Tony
 * @date 2015/5/17
 */
public class DataManager {

    private String TAG = getClass().getSimpleName();

    /**
     * 加载最新数据的起点
     */
    public final int LATEST_INDEX = 0;

    private List mData;

    /**
     * 下一页的起始数
     */
    private int mNextStart;

    /**
     * 数据的list
     */
    private List<Photo> mDataList = new ArrayList<Photo>();
    private ResponseCallback mcallback;
    private boolean nomore = false;
    private int msrc;
    private int INDEX = 0;

    public DataManager(int src) {
        msrc = src;
    }

    public void loadNewData(final ResponseCallback callback) {
        loadData(0, callback);
        mcallback = callback;
    }


    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            try {
                List<Photo> list = null;
                switch (msg.what) {
                    case 0:
                    case 1:
                        list = JSON.parseObject(msg.obj.toString(), new TypeReference<List<Photo>>() {
                        });
                        break;

                }
                if (mDataList == null || INDEX == LATEST_INDEX) {
                    mDataList = list;
                } else {
                    mDataList.addAll(list);
                }
                mcallback.onSuccess(mDataList);

            } catch (Exception e) {
                mcallback.onError(R.string.connectfile);
            }

        }
    };

    public void loadData(final int index, final ResponseCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (msrc) {
                    case 1:
                        HashMap map = new HashMap();
                        map.put("phone", MyData.PHONE);
                        mhandler.obtainMessage(msrc, HttpUtils.updata(map, MyData.URL_GETALLPIC)).sendToTarget();
                        break;
                    case 0:
                        HashMap map2 = new HashMap();
                        map2.put("phone", MyData.USER2);
                        mhandler.obtainMessage(msrc, HttpUtils.updata(map2, MyData.URL_GETALLPIC)).sendToTarget();
                        break;

                }


            }
        }).start();
    }

    public List<Photo> getData() {
        return mDataList;
    }

    public void loadOldData(final ResponseCallback callback) {
        if (!nomore) {
            mcallback = callback;
            loadData(mNextStart, callback);
        } else {
            mcallback.onError(R.string.nomore);
        }
    }

}
