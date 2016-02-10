package com.kim.treeview.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 伟阳 on 2016/2/10.
 */
public class Node {
    private int id = 0;
    private int pId = 0;
    private String name;
    private int level;
    private boolean isExpand = false;
    private int icon;

    private Node parent;
    private List<Node> children = new ArrayList<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    /**
     * 是否为根节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断当前父节点展开状态
     *
     * @return
     */
    public boolean isParentExpand() {
        return parent != null && parent.isExpand();
    }

    /**
     * 是否为叶子结点
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 计算当前结点的层级
     *
     * @return
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean expand) {
        isExpand = expand;
        if (!isExpand) {
            for (Node node : children) {
                node.setExpand(false);
            }
        }
    }
}
