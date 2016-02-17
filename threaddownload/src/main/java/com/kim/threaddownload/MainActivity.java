package com.kim.threaddownload;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.Toast;

import com.kim.threaddownload.model.FileInfo;
import com.kim.threaddownload.service.DownloadService;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<FileInfo> fileInfoList;
    private FileListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        listView = (ListView) findViewById(R.id.lv_file);
    }

    private void initData() {
        fileInfoList = new ArrayList<>();
        FileInfo fileInfo = null;
        fileInfo = new FileInfo(0, "http://122.228.21.122/f1.market.xiaomi.com/download/AppStore/0599547a5818779df752b1614d93612cccf40d3c0/com.tencent.tmgp.sgame.apk",
                "com.tencent.tmgp.sgame.apk", 0, 0);
        fileInfoList.add(fileInfo);
        fileInfo = new FileInfo(1, "http://122.228.21.122/f1.market.xiaomi.com/download/AppStore/04d8f4cb7d555f6334b03cd0f87694d402b43dae7/com.hcg.cok.mi.apk",
                "com.hcg.cok.mi.apk", 0, 0);
        fileInfoList.add(fileInfo);
        fileInfo = new FileInfo(2, "http://122.228.21.122/f1.market.xiaomi.com/download/AppStore/0e3c553ec0afcbcfba754185322f3f6bd72400c7a/com.xm.ddz.xiaomi.mi.apk",
                "com.xm.ddz.xiaomi.mi.apk", 0, 0);
        fileInfoList.add(fileInfo);
        fileInfo = new FileInfo(3, "http://122.228.21.122/f1.market.xiaomi.com/download/AppStore/00cedd4c410d84f69311a8f4533886c1a9111332b/com.tencent.news.apk",
                "com.tencent.news.apk", 0, 0);
        fileInfoList.add(fileInfo);
        adapter = new FileListAdapter(MainActivity.this, fileInfoList);
        listView.setAdapter(adapter);

        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        filter.addAction(DownloadService.ACTION_FINISHED);
        registerReceiver(broadcastReceiver, filter);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (DownloadService.ACTION_UPDATE.equals(intent.getAction())) {
                int finished = intent.getIntExtra("finished", 0);
                int id = intent.getIntExtra("id", 0);
                adapter.updateProgress(id, finished);
            }
            if (DownloadService.ACTION_FINISHED.equals(intent.getAction())) {
                Bundle bundle = intent.getBundleExtra("fileInfo");
                FileInfo fileInfo = (FileInfo) bundle.getSerializable("fileInfo");
                adapter.updateProgress(fileInfo.getId(), 100);
                Toast.makeText(MainActivity.this, fileInfo.getName() + " download finished", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
