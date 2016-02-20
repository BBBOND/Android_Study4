package com.kim.androidannotation;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kim.androidannotation.service.MyService_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.LongClick;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;
import org.w3c.dom.ls.LSInput;

import java.util.List;

@EActivity(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {

    public static final String NAME_KEY = "name_key";
    public static final String AGE_KEY = "age_key";

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
        intent.putExtra(NAME_KEY, "kim");
        intent.putExtra(AGE_KEY, "23");
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

    @Click(R.id.text1)
    public void changeText() {
        list.get(0).setText("--" + list.get(0).getText().toString() + "--");
    }

    @LongClick(R.id.text2)
    public void changeTextLong() {
        list.get(1).setText("--" + list.get(1).getText().toString() + "--");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
    }
}
