package com.kim.treeview;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kim.treeview.adapter.SimpleTreeListViewAdapter;
import com.kim.treeview.model.File;
import com.kim.treeview.utils.Node;
import com.kim.treeview.utils.TreeListViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView tree;
    private SimpleTreeListViewAdapter<File> adapter;

    private List<File> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tree = (ListView) findViewById(R.id.list);

        initDatas();
        try {
            adapter = new SimpleTreeListViewAdapter<>(tree, this, datas, 1);
            tree.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        adapter.setOnTreeNodeClickListener(new TreeListViewAdapter.OnTreeNodeClickListener() {
            @Override
            public void onClick(Node node, int position) {
                if (node.isLeaf()) {
                    Toast.makeText(MainActivity.this, "Click:" + node.getName(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        tree.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //官方推荐DialogFragment
                final EditText editText = new EditText(MainActivity.this);
                new AlertDialog.Builder(MainActivity.this).setTitle("Add Node").setView(editText).setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        adapter.addExtraNode(position,editText.getText().toString().trim());
                    }
                }).setNegativeButton("Cancel", null).show();
                return false;
            }
        });
    }

    private void initDatas() {
        datas = new ArrayList<>();
        File file = new File(1, 0, "根目录1");
        datas.add(file);
        file = new File(2, 0, "根目录2");
        datas.add(file);
        file = new File(3, 0, "根目录3");
        datas.add(file);
        file = new File(4, 1, "目录1-1");
        datas.add(file);
        file = new File(5, 1, "目录1-2");
        datas.add(file);
        file = new File(6, 1, "目录1-3");
        datas.add(file);
        file = new File(7, 2, "目录2-1");
        datas.add(file);
        file = new File(8, 2, "目录2-2");
        datas.add(file);
        file = new File(9, 2, "目录2-3");
        datas.add(file);
        file = new File(10, 3, "目录3-1");
        datas.add(file);
        file = new File(11, 4, "目录1-1-1");
        datas.add(file);
        file = new File(12, 11, "目录1-1-1-1");
        datas.add(file);
    }
}
