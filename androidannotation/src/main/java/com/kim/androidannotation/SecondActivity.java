package com.kim.androidannotation;

import android.app.Activity;
import android.os.Bundle;

import org.androidannotations.annotations.EActivity;

/**
 * Created by 伟阳 on 2016/2/19.
 */

@EActivity(R.layout.second_activity)
public class SecondActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
