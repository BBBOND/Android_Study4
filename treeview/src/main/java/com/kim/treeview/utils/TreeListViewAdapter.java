package com.kim.treeview.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.List;

/**
 * Created by 伟阳 on 2016/2/10.
 */
public abstract class TreeListViewAdapter<T> extends BaseAdapter {

    protected Context context;
    protected List<Node> allNodes;
    protected List<Node> visibleNodes;
    protected LayoutInflater inflater;

    protected ListView tree;

    public interface OnTreeNodeClickListener {
        void onClick(Node node, int position);
    }

    private OnTreeNodeClickListener listener;

    public void setOnTreeNodeClickListener(OnTreeNodeClickListener listener) {
        this.listener = listener;
    }

    public TreeListViewAdapter(ListView tree, Context context, List<T> datas, int defaultExpandLevel) throws IllegalAccessException {
        this.tree = tree;
        this.context = context;
        inflater = LayoutInflater.from(context);
        allNodes = TreeHelper.getSortedNodes(datas, defaultExpandLevel);
        visibleNodes = TreeHelper.filterVisibleNodes(allNodes);

        tree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                expandOrCollapse(position);
                if (listener != null) {
                    listener.onClick(visibleNodes.get(position), position);
                }
            }
        });
    }

    private void expandOrCollapse(int position) {
        Node node = visibleNodes.get(position);
        if (node != null) {
            if (node.isLeaf()) return;
            node.setExpand(!node.isExpand());
            visibleNodes = TreeHelper.filterVisibleNodes(allNodes);
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return visibleNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return visibleNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return visibleNodes.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node node = visibleNodes.get(position);
        convertView = getConvertView(node, position, convertView, parent);
        convertView.setPadding(node.getLevel() * 30, 3, 3, 3);
        return convertView;
    }

    public abstract View getConvertView(Node Node, int position, View convertView, ViewGroup parent);
}
