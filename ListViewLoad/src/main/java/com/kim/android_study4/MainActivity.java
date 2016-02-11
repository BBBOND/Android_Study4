package com.kim.android_study4;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.kim.android_study4.view.LoadListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoadListView.LoadListener {

    private LoadListView listView;
    private ArrayAdapter<String> adapter = null;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (LoadListView) findViewById(R.id.listView);
        listView.setLoadListener(this);

        init();
    }

    private void init() {
        list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("item" + i);
        }
        showListView(list);
    }

    private void showListView(List<String> list) {
        if (adapter == null) {
            adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, list);
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoad() {
        //获取更多数据
        //更新listview显示
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    list.add("more item" + i);
                }
                showListView(list);
                listView.loadComplete();
            }
        }, 2000);

    }
}
