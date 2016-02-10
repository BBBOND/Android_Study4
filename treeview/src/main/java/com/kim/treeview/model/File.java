package com.kim.treeview.model;

import com.kim.treeview.utils.annotation.TreeNodeId;
import com.kim.treeview.utils.annotation.TreeNodeLabel;
import com.kim.treeview.utils.annotation.TreeNodePId;

/**
 * Created by 伟阳 on 2016/2/10.
 */
public class File {
    @TreeNodeId
    private int id;
    @TreeNodePId
    private int pId;
    @TreeNodeLabel
    private String label;
    private String desc;


    //...


    public File() {
    }

    public File(int id, int pId, String label) {
        this.id = id;
        this.pId = pId;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
