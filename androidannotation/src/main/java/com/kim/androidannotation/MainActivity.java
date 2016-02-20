package com.kim.androidannotation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.kim.androidannotation.service.MyService_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    @ViewById(R.id.toSecond)
    Button toSecond;
    @ViewById(R.id.startService)
    Button startService;
    //命名符合规格就可以不用加ID
    @ViewById
    TextView tvHello;
    @ViewsById({R.id.text1, R.id.text2, R.id.text3})
    List<TextView> list;

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

    @AfterViews
    public void showTextView() {
        tvHello.setText("Hello World!");
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setText("text " + i);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
    }
}
