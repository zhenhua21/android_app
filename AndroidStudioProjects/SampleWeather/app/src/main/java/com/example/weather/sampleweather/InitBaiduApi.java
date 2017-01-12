package com.example.weather.sampleweather;

import android.app.Application;

import com.baidu.apistore.sdk.ApiStoreSDK;

/**
 * Created by zhenhua on 17-1-11.
 */

public class InitBaiduApi extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ApiStoreSDK.init(this, "e6b642ccfe83be58357c450c5c5622a2"); //百度apikey
    }
}
