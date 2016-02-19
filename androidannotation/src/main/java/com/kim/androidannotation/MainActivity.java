package com.kim.androidannotation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.kim.androidannotation.service.MyService_;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

//    @ViewById(R.id.toSecond)
//    Button toSecond;
//    @ViewById(R.id.startService)
//    Button startService;

    @Click(R.id.toSecond)
    public void toSecondActivity() {
        Intent intent = new Intent(MainActivity.this, SecondActivity_.class);
        startActivity(intent);
    }

    @Click(R.id.startService)
    public void startService() {
        Intent intent = new Intent(MainActivity.this, MyService_.class);
        startService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
    }
}
