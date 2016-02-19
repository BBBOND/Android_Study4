package com.kim.threaddownload;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kim.threaddownload.model.FileInfo;
import com.kim.threaddownload.service.DownloadService;

import java.util.List;

/**
 * Created by 伟阳 on 2016/2/17.
 */
public class FileListAdapter extends BaseAdapter {

    private Context context = null;
    private List<FileInfo> fileList = null;

    public FileListAdapter(Context context, List<FileInfo> fileList) {
        this.context = context;
        this.fileList = fileList;
    }

    @Override
    public int getCount() {
        return fileList.size();
    }

    @Override
    public Object getItem(int position) {
        return fileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final FileInfo fileInfo = fileList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.file_item, null);
            holder = new ViewHolder();
            holder.fileName = (TextView) convertView.findViewById(R.id.tv_file);
            holder.start = (Button) convertView.findViewById(R.id.btn_start);
            holder.stop = (Button) convertView.findViewById(R.id.btn_stop);
            holder.progressBar = (ProgressBar) convertView.findViewById(R.id.pg_file);
            convertView.setTag(holder);
            holder.fileName.setText(fileInfo.getName());
            holder.progressBar.setMax(100);
            holder.start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DownloadService.class);
                    intent.setAction(DownloadService.ACTION_START);
                    intent.putExtra("fileInfo", fileInfo);
                    context.startService(intent);
                }
            });
            holder.stop.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DownloadService.class);
                    intent.setAction(DownloadService.ACTION_STOP);
                    intent.putExtra("fileInfo", fileInfo);
                    context.startService(intent);
                }
            });
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.progressBar.setProgress(fileInfo.getFinished());

        return convertView;
    }

    /**
     * 更新进度条
     *
     * @param id
     * @param progress
     */
    public void updateProgress(int id, int progress) {
        FileInfo fileInfo = fileList.get(id);
        fileInfo.setFinished(progress);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        TextView fileName;
        Button start;
        Button stop;
        ProgressBar progressBar;
    }
}
