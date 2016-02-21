package com.kim.androidannotation;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 伟阳 on 2016/2/21.
 */
@EActivity(R.layout.third_activity)
public class ThirdActivity extends Activity {

    @ViewById(R.id.list)
    ListView listView;


    @AfterViews
    public void setList() {
        List<String> name = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            name.add("name " + i);
        }
        MyAdapter adapter = new MyAdapter(ThirdActivity.this, name);
        listView.setAdapter(adapter);
    }

    @ItemClick(R.id.list)
    public void listViewItemClick() {
        Toast.makeText(ThirdActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
