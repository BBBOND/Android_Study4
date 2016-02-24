package com.kim.push;

import android.app.Application;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 伟阳 on 2016/2/24.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

}
