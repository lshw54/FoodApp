package com.example.myfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myfood.R;
import com.example.myfood.wight.RoundImageView;

import java.io.File;
import java.util.List;

public class GridViewAdapter extends android.widget.BaseAdapter {
    private List<String> datas;
    private Context context;
    private LayoutInflater inflater;
    private boolean isShowDetele = true;
    private int maxImages = 3;

    public GridViewAdapter(List<String> datas, Context context, boolean isShowDetele) {
        this.datas = datas;
        this.context = context;
        this.isShowDetele = isShowDetele;
        inflater = LayoutInflater.from(context);

    }

    public GridViewAdapter(Context context, List<String> datas) {
        this.datas = datas;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    public void setPathList(List<String> pathList) {
        this.datas = pathList;
    }

    public int getMaxImages() {
        return maxImages;
    }


    public void setMaxImages(int maxImages) {
        this.maxImages = maxImages;
    }


    @Override
    public int getCount() {
        if (isShowDetele){
            int count = datas == null ? 1 : datas.size() + 1;
            if (count > maxImages) {
                return datas.size();
            } else {
                return count;
            }
        }else {
            return datas.size();
        }

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void notifyDataSetChanged(List<String> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.delete_view, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (datas != null && position < datas.size()) {
            if (datas.get(position).contains("http")) {
                Glide.with(context)
                        .load(datas.get(position))
                        //.priority(Priority.HIGH)
                        .into(viewHolder.ivimage);
                if (isShowDetele)
                    viewHolder.btdel.setVisibility(View.VISIBLE);
                else viewHolder.btdel.setVisibility(View.GONE);
                viewHolder.btdel.setOnClickListener(v -> {
                    datas.remove(position);
                    notifyDataSetChanged();
                });
            } else {
                final File file = new File(datas.get(position));
                Glide.with(context)
                        .load(file)
                        //.priority(Priority.HIGH)
                        .into(viewHolder.ivimage);
                if (isShowDetele)
                    viewHolder.btdel.setVisibility(View.VISIBLE);
                else viewHolder.btdel.setVisibility(View.GONE);
                viewHolder.btdel.setOnClickListener(v -> {
                    if (file.exists()) {
                        file.delete();
                    }
                    datas.remove(position);
                    notifyDataSetChanged();
                });
            }

        } else {
            if (isShowDetele){
                Glide.with(context)
                        .load(R.drawable.ic_add_photo)
                        //.priority(Priority.HIGH)
                        //.centerCrop()
                        .into(viewHolder.ivimage);
                viewHolder.ivimage.setScaleType(ImageView.ScaleType.FIT_XY);
                viewHolder.btdel.setVisibility(View.GONE);
            }

        }

        return convertView;

    }

    public static class ViewHolder {
        public final RoundImageView ivimage;
        public final ImageView btdel;
        public final View root;

        public ViewHolder(View root) {
            ivimage = root.findViewById(R.id.iv_img);
            btdel = root.findViewById(R.id.iv_delete);
            this.root = root;
        }
    }

}

