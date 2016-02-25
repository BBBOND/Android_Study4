package com.kim.push;

import android.app.Application;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * Created by 伟阳 on 2016/2/24.
 */
public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        JPushInterface.setAlias(this, "test", new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i(TAG, "set Alias result is " + i);
            }
        });
        Set<String> sets = new HashSet<>();
        sets.add("sports");
        sets.add("tech");
        JPushInterface.setTags(this, sets, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {
                Log.i(TAG, "set Tags result is " + i);
            }
        });
    }

}
