package com.kim.treeview.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kim.treeview.R;
import com.kim.treeview.utils.Node;
import com.kim.treeview.utils.TreeListViewAdapter;

import java.util.List;

/**
 * Created by 伟阳 on 2016/2/10.
 */
public class SimpleTreeListViewAdapter<T> extends TreeListViewAdapter<T> {

    public SimpleTreeListViewAdapter(ListView tree, Context context, List<T> datas, int defaultExpandLevel) throws IllegalAccessException {
        super(tree, context, datas, defaultExpandLevel);
    }

    @Override
    public View getConvertView(Node Node, int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.icon = (ImageView) convertView.findViewById(R.id.item_icon);
            holder.text = (TextView) convertView.findViewById(R.id.item_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (Node.getIcon() == -1) {
            holder.icon.setVisibility(View.INVISIBLE);
        } else {
            holder.icon.setVisibility(View.VISIBLE);
            holder.icon.setImageResource(Node.getIcon());
        }

        holder.text.setText(Node.getName());
        return convertView;
    }

    private class ViewHolder {
        ImageView icon;
        TextView text;
    }
}
