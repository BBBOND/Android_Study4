package com.kim.threaddownload.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.kim.threaddownload.dao.ThreadDAO;
import com.kim.threaddownload.dao.ThreadDaoImpl;
import com.kim.threaddownload.model.FileInfo;
import com.kim.threaddownload.model.ThreadInfo;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by 伟阳 on 2016/2/17.
 */
public class DownloadTask {

    private Context context = null;
    private FileInfo fileInfo = null;
    private ThreadDAO threadDAO = null;
    private int finished = 0;
    public boolean isPause = false;
    private int threadCount = 1;  // 线程数量
    private List<DownloadThread> downloadThreadList = null; // 线程集合
    // 带缓存的线程池（和系统支持的线程数量有关）
    public static ExecutorService executorService = Executors.newCachedThreadPool();
    // 固定线程数的线程池 Executors.newFixedThreadPool(int);
    // 可以周期性地执行线程池中的线程 Executors.newScheduledThreadPool();
    // 只有单个线程可以工作 Executors.newSingleThreadExecutor();


    /**
     * @param context
     * @param fileInfo
     * @param threadCount
     */
    public DownloadTask(Context context, FileInfo fileInfo, int threadCount) {
        this.context = context;
        this.fileInfo = fileInfo;
        this.threadCount = threadCount;
        threadDAO = new ThreadDaoImpl(context);
    }

    public void downLoad() {
        // 读取数据库的线程信息
        List<ThreadInfo> threads = threadDAO.getThreads(fileInfo.getUrl());
        ThreadInfo threadInfo = null;

        if (0 == threads.size()) {
            // 计算每个线程下载长度
            int len = fileInfo.getLength() / threadCount;
            for (int i = 0; i < threadCount; i++) {
                // 初始化线程信息对象
                threadInfo = new ThreadInfo(i, fileInfo.getUrl(), len * i, (i + 1) * len - 1, 0);

                // 处理最后一个线程下载长度不能整除的问题
                if (threadCount - 1 == i) {
                    threadInfo.setEnd(fileInfo.getLength());
                }
                // 添加到线程集合中
                threads.add(threadInfo);
                threadDAO.insertThread(threadInfo);
            }
        }

        downloadThreadList = new ArrayList<DownloadThread>();
        // 启动多个线程进行下载
        for (ThreadInfo info : threads) {
            DownloadThread thread = new DownloadThread(info);
//            thread.start();
            DownloadTask.executorService.execute(thread);
            // 添加到线程集合中
            downloadThreadList.add(thread);
        }
    }

    /**
     * 下载线程
     */
    private class DownloadThread extends Thread {
        private ThreadInfo threadInfo = null;
        public boolean isFinished = false;  // 线程是否执行完毕

        /**
         * @param mInfo
         */
        public DownloadThread(ThreadInfo mInfo) {
            this.threadInfo = mInfo;
        }

        @Override
        public void run() {
            HttpURLConnection connection = null;
            RandomAccessFile raf = null;
            InputStream inputStream = null;

            try {
                URL url = new URL(threadInfo.getUrl());
                connection = (HttpURLConnection) url.openConnection();
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                // 设置下载位置
                int start = threadInfo.getStart() + threadInfo.getFinished();
                connection.setRequestProperty("Range", "bytes=" + start + "-" + threadInfo.getEnd());
                // 设置文件写入位置
                File file = new File(DownloadService.DOWNLOAD_PATH, fileInfo.getName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);
                Intent intent = new Intent();
                intent.setAction(DownloadService.ACTION_UPDATE);
                finished += threadInfo.getFinished();
                Log.i("finished", threadInfo.getId() + "finished = " + threadInfo.getFinished());
                // 开始下载
                if (connection.getResponseCode() == 206) {
                    // 读取数据
                    inputStream = connection.getInputStream();
                    byte buf[] = new byte[1024 << 2];
                    int len = -1;
                    long time = System.currentTimeMillis();
                    while ((len = inputStream.read(buf)) != -1) {
                        // 写入文件
                        raf.write(buf, 0, len);
                        // 累加整个文件完成进度
                        finished += len;
                        // 累加每个线程完成的进度
                        threadInfo.setFinished(threadInfo.getFinished() + len);
                        if (System.currentTimeMillis() - time > 1000) {
                            time = System.currentTimeMillis();
                            int f = finished * 100 / fileInfo.getLength();
                            if (f > fileInfo.getFinished()) {
                                intent.putExtra("finished", f);
                                intent.putExtra("id", fileInfo.getId());
                                context.startService(intent);
                                context.sendBroadcast(intent);
                            }
                        }

                        // 在下载暂停时，保存下载进度
                        if (isPause) {
                            threadDAO.updateThread(threadInfo.getUrl(), threadInfo.getId(), threadInfo.getFinished());
                            Log.i("threadInfo", threadInfo.getId() + "finished = " + threadInfo.getFinished());
                            return;
                        }
                    }

                    // 标识线程执行完毕
                    isFinished = true;
                    // 检查下载任务是否结束
                    checkAllThreadFinished();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (connection != null) {
                        connection.disconnect();
                    }
                    if (raf != null) {
                        raf.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断所有的线程是否执行完毕
     */
    private synchronized void checkAllThreadFinished() {
        boolean allFinished = true;

        // 遍历线程集合，判断线程是否都执行完毕
        for (DownloadThread thread : downloadThreadList) {
            if (!thread.isFinished) {
                allFinished = false;
                break;
            }
        }

        if (allFinished) {
            // 删除下载记录
            threadDAO.deleteThread(fileInfo.getUrl());
            // 发送广播知道UI下载任务结束
            Intent intent = new Intent(DownloadService.ACTION_FINISHED);
            intent.putExtra("fileInfo", fileInfo);
            context.startService(intent);
            context.sendBroadcast(intent);
        }
    }
}
