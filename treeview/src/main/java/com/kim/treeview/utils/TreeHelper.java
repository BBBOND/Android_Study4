package com.kim.treeview.utils;

import com.kim.treeview.R;
import com.kim.treeview.utils.annotation.TreeNodeId;
import com.kim.treeview.utils.annotation.TreeNodeLabel;
import com.kim.treeview.utils.annotation.TreeNodePId;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 伟阳 on 2016/2/10.
 */
public class TreeHelper {
    /**
     * 将数据转化为List<Node>
     *
     * @param datas
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    private static <T> List<Node> convertDatas2Nodes(List<T> datas) throws IllegalAccessException {
        List<Node> nodes = new ArrayList<>();
        Node node = null;
        for (T t : datas) {
            node = new Node();
            Class cla = t.getClass();
            Field[] declaredFields = cla.getDeclaredFields();
            for (Field field : declaredFields) {
                if (field.getAnnotation(TreeNodeId.class) != null) {
                    field.setAccessible(true);
                    node.setId(field.getInt(t));
                }
                if (field.getAnnotation(TreeNodePId.class) != null) {
                    field.setAccessible(true);
                    node.setpId(field.getInt(t));
                }
                if (field.getAnnotation(TreeNodeLabel.class) != null) {
                    field.setAccessible(true);
                    node.setName((String) field.get(t));
                }
            }
            nodes.add(node);
        }

        for (int i = 0; i < nodes.size(); i++) {
            Node n = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                Node m = nodes.get(j);
                if (n.getId() == m.getpId()) {
                    n.getChildren().add(m);
                    m.setParent(n);
                } else if (m.getId() == n.getpId()) {
                    m.getChildren().add(n);
                    n.setParent(m);
                }
            }
        }

        for (Node n : nodes) {
            setNodeIcon(n);
        }

        return nodes;
    }

    private static void setNodeIcon(Node n) {
        if (n.getChildren().size() > 0 && n.isExpand()) {
            n.setIcon(R.drawable.tree_ec);
        } else if (n.getChildren().size() > 0 && !n.isExpand()) {
            n.setIcon(R.drawable.tree_ex);
        } else {
            n.setIcon(-1);
        }
    }

    /**
     * 获取排序后的结点
     *
     * @param datas
     * @param defaultExpandLevel
     * @param <T>
     * @return
     * @throws IllegalAccessException
     */
    public static <T> List<Node> getSortedNodes(List<T> datas, int defaultExpandLevel) throws IllegalAccessException {
        List<Node> result = new ArrayList<>();
        List<Node> nodes = convertDatas2Nodes(datas);
        List<Node> rootNodes = getRootNodes(nodes);

        for (Node node : rootNodes) {
            addNode(result, node, defaultExpandLevel, 1);
        }
        return result;
    }

    public static List<Node> filterVisibleNodes(List<Node> nodes) {
        List<Node> result = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isRoot() || node.isParentExpand()) {
                setNodeIcon(node);
                result.add(node);
            }
        }
        return result;
    }

    /**
     * 把一个结点的所有孩子结点都放入result
     *
     * @param result
     * @param node
     * @param defaultExpandLevel
     * @param currentLevel
     */
    private static void addNode(List<Node> result, Node node, int defaultExpandLevel, int currentLevel) {
        result.add(node);
        if (defaultExpandLevel >= currentLevel) {
            node.setExpand(true);
        }
        if (node.isLeaf()) {
            return;
        }
        for (int i = 0; i < node.getChildren().size(); i++) {
            addNode(result, node.getChildren().get(i), defaultExpandLevel, currentLevel + 1);
        }
    }

    /**
     * 获取根节点
     *
     * @param nodes
     * @return
     */
    private static List<Node> getRootNodes(List<Node> nodes) {
        List<Node> root = new ArrayList<>();
        for (Node node : nodes) {
            if (node.isRoot()) {
                root.add(node);
            }
        }
        return root;
    }
}

