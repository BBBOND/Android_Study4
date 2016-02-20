package com.kim.androidannotation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

/**
 * Created by 伟阳 on 2016/2/19.
 */

@EActivity(R.layout.second_activity)
public class SecondActivity extends Activity {

    @Extra(MainActivity.NAME_KEY)
    public String name;
    @Extra(MainActivity.AGE_KEY)
    public String age;

    @ViewById
    TextView text1;
    @ViewById
    TextView text2;

    @AfterViews
    public void initView() {
        text1.setText(name);
        text2.setText(age);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
