package com.kim.threaddownload.dao;

import com.kim.threaddownload.model.ThreadInfo;

import java.util.List;


/**
 * 数据访问接口
 *
 * @date 2015-8-8 上午10:55:21
 */
public interface ThreadDAO {
    /**
     * 插入线程信息
     *
     * @param threadInfo
     * @return void
     * @date 2015-8-8 上午10:56:18
     */
    public void insertThread(ThreadInfo threadInfo);

    /**
     * 删除线程信息
     *
     * @param url
     * @return void
     * @date 2015-8-8 上午10:56:57
     */
    public void deleteThread(String url);

    /**
     * 更新线程下载进度
     *
     * @param url
     * @param thread_id
     * @return void
     * @date 2015-8-8 上午10:57:37
     */
    public void updateThread(String url, int thread_id, int finished);

    /**
     * 查询文件的线程信息
     *
     * @param url
     * @return List<ThreadInfo>
     * @date 2015-8-8 上午10:58:48
     */
    public List<ThreadInfo> getThreads(String url);

    /**
     * 线程信息是否存在
     *
     * @param url
     * @param thread_id
     * @return boolean
     * @date 2015-8-8 上午10:59:41
     */
    public boolean isExists(String url, int thread_id);
}
