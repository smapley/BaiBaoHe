package com.smapley.baibaohe.pubu.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;

import cn.smssdk.SMSSDK;

/**
 * @author Jack Tony
 * @brief
 * @date 2015/4/5
 */
public class WaterFallApplication extends Application {

    // 请求队列
    public static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        SMSSDK.initSDK(this, "81b198a575f1", "bbcff405ad84909cb467fa5ecd52a175");
        // 不必为每一次HTTP请求都创建一个RequestQueue对象，推荐在application中初始化
        requestQueue = Volley.newRequestQueue(this);
        // 初始化fresco库
        Fresco.initialize(this);
    }
}
