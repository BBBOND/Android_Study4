package com.kim.threaddownload.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.kim.threaddownload.MainActivity;
import com.kim.threaddownload.model.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 伟阳 on 2016/2/17.
 */
public class DownloadService extends Service {

    public static final String DOWNLOAD_PATH = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
    public static final String ACTION_START = "ACTION_START";
    public static final String ACTION_STOP = "ACTION_STOP";
    public static final String ACTION_UPDATE = "ACTION_UPDATE";
    public static final String ACTION_FINISHED = "ACTION_FINISHED";
    public static final int MSG_INIT = 0;
    private String TAG = "DownloadService";
    private Map<Integer, DownloadTask> tasks = new LinkedHashMap<Integer, DownloadTask>();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // 获取传过来的文件对象
        Bundle bundle = intent.getBundleExtra("fileInfo");
        FileInfo fileInfo = (FileInfo) bundle.getSerializable("fileInfo");
        // 获得Activity传过来的参数
        if (ACTION_START.equals(intent.getAction())) {
            Log.i(TAG, "Start:" + fileInfo.toString());
            // 启动初始化线程
            new InitThread(fileInfo).start();
        } else if (ACTION_STOP.equals(intent.getAction())) {
            Log.i(TAG, "Stop:" + fileInfo.toString());

            // 从集合中取出下载任务
            DownloadTask task = tasks.get(fileInfo.getId());
            if (task != null) {
                task.isPause = true;
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case MSG_INIT:
                    FileInfo fileInfo = (FileInfo) msg.obj;
                    Log.i(TAG, "Init:" + fileInfo);
                    // 启动下载任务
                    DownloadTask task = new DownloadTask(DownloadService.this, fileInfo, 3);
                    task.downLoad();
                    // 把下载任务添加到集合中
                    tasks.put(fileInfo.getId(), task);
                    break;
                default:
                    break;
            }
        }
    };

    private class InitThread extends Thread {
        private FileInfo fileInfo = null;

        public InitThread(FileInfo mFileInfo) {
            this.fileInfo = mFileInfo;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;
            try {
                // 连接网络文件
                URL url = new URL(fileInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                int length = -1;
                if (connection.getResponseCode() == 200) {
                    // 获得文件的长度
                    length = connection.getContentLength();
                }
                if (length <= 0) {
                    return;
                }
                File dir = new File(DOWNLOAD_PATH);
                if (!dir.exists()) {
                    dir.mkdir();
                }

                // 在本地创建文件
                File file = new File(dir, fileInfo.getName());
                raf = new RandomAccessFile(file, "rwd");
                // 设置文件长度
                raf.setLength(length);
                fileInfo.setLength(length);
                handler.obtainMessage(MSG_INIT, fileInfo).sendToTarget();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (raf != null) {
                    try {
                        raf.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
