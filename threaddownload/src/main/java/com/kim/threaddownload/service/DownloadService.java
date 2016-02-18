package com.kim.threaddownload.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kim.threaddownload.MainActivity;
import com.kim.threaddownload.R;
import com.kim.threaddownload.model.FileInfo;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;

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
    private NotificationManager manager;
    private Map<Integer, Notification.Builder> builders;
    private Notification.Builder builder;
    private Intent intent;
    private PendingIntent contentIntent;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        FileInfo fileInfo;
        // 获得Activity传过来的参数
        if (ACTION_START.equals(intent.getAction())) {
            // 获取传过来的文件对象
            fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.i(TAG, "Start:" + fileInfo.toString());
            // 启动初始化线程
            DownloadTask.executorService.execute(new InitThread(fileInfo));
            // 开启通知
            if (builders.get(fileInfo.getId()) == null) {
                builder = new Notification.Builder(DownloadService.this);
                builder.setContentIntent(contentIntent);
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setContentTitle(fileInfo.getName());
                builder.setTicker(fileInfo.getName() + "开始下载");
                builder.setContentText("  已下载" + fileInfo.getFinished());
                builder.setOngoing(true);
                builders.put(fileInfo.getId(), builder);
            } else {
                builder = builders.get(fileInfo.getId());
                builder.setTicker(fileInfo.getName() + "开始下载");
                builder.setContentText("  已下载" + fileInfo.getFinished());
                builder.setOngoing(true);
                builders.put(fileInfo.getId(), builder);
            }
            manager.notify(fileInfo.getId(), builder.build());
        } else if (ACTION_STOP.equals(intent.getAction())) {
            // 获取传过来的文件对象
            fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            Log.i(TAG, "Stop:" + fileInfo.toString());

            // 从集合中取出下载任务
            DownloadTask task = tasks.get(fileInfo.getId());
            if (task != null) {
                task.isPause = true;
                if (builders.get(fileInfo.getId()) != null) {
                    builder = builders.get(fileInfo.getId());
                    builder.setTicker(fileInfo.getName() + "暂停下载");
                    builder.setContentText("下载已暂停");
                    builder.setOngoing(false);
                    manager.notify(fileInfo.getId(), builder.build());
                    builders.put(fileInfo.getId(), builder);
                }
            }
        } else if (ACTION_UPDATE.equals(intent.getAction())) {
            int finished = intent.getIntExtra("finished", 0);
            int id = intent.getIntExtra("id", -1);
            if (builders.get(id) != null) {
                builder = builders.get(id);
                builder.setTicker("开始下载");
                builder.setContentText("  已下载" + finished);
                builder.setOngoing(true);
                manager.notify(id, builder.build());
                builders.put(id, builder);
            }
        } else if (ACTION_FINISHED.equals(intent.getAction())) {
            fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
            if (builders.get(fileInfo.getId()) != null) {
                builder = builders.get(fileInfo.getId());
                builder.setTicker(fileInfo.getName() + "下载完成");
                builder.setContentText(fileInfo.getName() + "下载完成");
                builder.setOngoing(false);
                builders.remove(fileInfo.getId());
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
                Log.d(TAG, "run: " + file.getPath());
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


    @Override
    public void onCreate() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        builders = new HashMap<>();
        intent = new Intent(DownloadService.this, MainActivity.class);
        contentIntent = PendingIntent.getActivity(DownloadService.this, 0, intent, 0);

        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
