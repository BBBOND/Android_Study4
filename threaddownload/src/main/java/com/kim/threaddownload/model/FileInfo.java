package com.kim.threaddownload.model;

import java.io.Serializable;

/**
 * Created by 伟阳 on 2016/2/17.
 */
public class FileInfo implements Serializable {
    private int id;
    private String url;
    private String name;
    private int length;
    private int finished;

    public FileInfo(int id, String url, String name, int length, int finished) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.length = length;
        this.finished = finished;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }
}
