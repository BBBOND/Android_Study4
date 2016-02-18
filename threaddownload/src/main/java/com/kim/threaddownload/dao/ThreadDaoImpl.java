package com.kim.threaddownload.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kim.threaddownload.model.ThreadInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 伟阳 on 2016/2/17.
 */
public class ThreadDaoImpl implements ThreadDAO {

    private static String DB_NAME = "thread_info";
    private DBHelper dbHelper;

    public ThreadDaoImpl(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    @Override
    public synchronized void insertThread(ThreadInfo threadInfo) {
        SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("thread_id", threadInfo.getId());
        cv.put("url", threadInfo.getUrl());
        cv.put("start", threadInfo.getStart());
        cv.put("end", threadInfo.getEnd());
        cv.put("finished", threadInfo.getFinished());
        dbWriter.insert(DB_NAME, null, cv);
//        dbWriter.execSQL("insert into thread_info(thread_id,url,start,end,finished) values(?,?,?,?,?)",
//                new Object[]{threadInfo.getId(), threadInfo.getUrl(), threadInfo.getStart(), threadInfo.getEnd(), threadInfo.getFinished()});
        dbWriter.close();
    }

    @Override
    public synchronized void deleteThread(String url) {
        SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
//        dbWriter.execSQL("delete from thread_info where url = ?", new Object[]{url});
        dbWriter.delete(DB_NAME, "url=?", new String[]{url});
        dbWriter.close();
    }

    @Override
    public synchronized void updateThread(String url, int thread_id, int finished) {
        SQLiteDatabase dbWriter = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("finished", finished);
//        dbWriter.execSQL("update thread_info set finished = ? where url = ? and thread_id = ?", new Object[]{finished, url, thread_id});
        dbWriter.update(DB_NAME, cv, "url = ? and thread_id = ?", new String[]{url, thread_id + ""});
        dbWriter.close();
    }

    @Override
    public List<ThreadInfo> getThreads(String url) {
        List<ThreadInfo> list = new ArrayList<ThreadInfo>();
        SQLiteDatabase dbReader = dbHelper.getReadableDatabase();
//        Cursor cursor = dbReader.rawQuery("select * from thread_info where url = ?", new String[]{url});
        Cursor cursor = dbReader.query(DB_NAME, null, "url=?", new String[]{url}, null, null, null);
        while (cursor.moveToNext()) {
            ThreadInfo threadInfo = new ThreadInfo();
            threadInfo.setId(cursor.getInt(cursor.getColumnIndex("thread_id")));
            threadInfo.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            threadInfo.setStart(cursor.getInt(cursor.getColumnIndex("start")));
            threadInfo.setEnd(cursor.getInt(cursor.getColumnIndex("end")));
            threadInfo.setFinished(cursor.getInt(cursor.getColumnIndex("finished")));
            list.add(threadInfo);
        }
        cursor.close();
        dbReader.close();
        return list;
    }

    @Override
    public boolean isExists(String url, int thread_id) {
        SQLiteDatabase dbReader = dbHelper.getReadableDatabase();
//        Cursor cursor = dbReader.rawQuery("select * from thread_info where url = ? and thread_id = ?", new String[]{url, thread_id+""});
        Cursor cursor = dbReader.query(DB_NAME, null, "url = ? and thread_id = ?", new String[]{url, thread_id + ""}, null, null, null);
        boolean exists = cursor.moveToNext();
        cursor.close();
        dbReader.close();
        return exists;
    }
}
