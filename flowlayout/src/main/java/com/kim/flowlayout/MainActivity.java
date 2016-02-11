package com.kim.flowlayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.kim.flowlayout.view.FlowLayout;

public class MainActivity extends AppCompatActivity {

    private FlowLayout flowLayout;
    private String[] vals = new String[]{
            "I", "Hi", "WoW", "Welcome", "Android", "Button", "Test",
            "I", "Hi", "WoW", "Welcome", "Android", "Button", "Test",
            "I", "Hi", "WoW", "Welcome", "Android", "Button", "Test",
            "I", "Hi", "WoW", "Welcome", "Android", "Button", "Test"
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flowLayout = (FlowLayout) findViewById(R.id.flow_layout);

        initData();
    }

    public void initData() {
//        Button btn;
//        ViewGroup.MarginLayoutParams layoutParams;
//        for (int i = 0; i < vals.length; i++) {
//            btn = new Button(this);
//            layoutParams = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            btn.setText(vals[i]);
//            flowLayout.addView(btn, layoutParams);
//        }

        TextView tv;
        LayoutInflater inflater = LayoutInflater.from(this);
        for (int i = 0; i < vals.length; i++) {
            tv = (TextView) inflater.inflate(R.layout.tv, flowLayout, false);
            tv.setText(vals[i]);
            flowLayout.addView(tv);
        }
    }
}
