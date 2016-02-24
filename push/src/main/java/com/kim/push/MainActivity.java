package com.kim.push;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import cn.jpush.android.api.JPushInterface;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(getApplication());
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(getApplication());
    }
}
