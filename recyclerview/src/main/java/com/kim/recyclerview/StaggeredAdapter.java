package com.kim.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StaggeredAdapter extends MyAdapter {

    private List<Integer> heights;

    public StaggeredAdapter(Context context, List<String> datas) {
        super(context, datas);
        heights = new ArrayList<>();
        for (int i = 0; i < datas.size(); i++) {
            heights.add((int) (100 + Math.random() * 300));
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = heights.get(position);
        holder.itemView.setLayoutParams(layoutParams);
        holder.item.setText(datas.get(position));
        setClickMethod(holder);
    }
}

