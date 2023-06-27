package com.example.myfood.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.myfood.R;
import com.example.myfood.bean.Food;
import com.example.myfood.bean.Process;

import java.util.List;

public class DetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Process> processList;

    public DetailsAdapter(Context context, List<Process> processList) {
        this.context = context;
        this.processList = processList;
    }

    @Override
    public int getItemCount() {
        return processList == null ? 0 : processList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_details, null, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InnerHolder){
            Process step = processList.get(position);
            if (step != null){
                ((InnerHolder) holder).nameTv.setText((position)+" -> "+ Html.fromHtml(step.getPcontent()));
                Glide.with(context)
                        .load(step.getPic())
                        .transition(DrawableTransitionOptions.withCrossFade(600))
                        .into(((InnerHolder) holder).imageView);
            }

        }
    }


    public class InnerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTv;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.look_item_image1);
            nameTv = itemView.findViewById(R.id.look_item_text1);
        }
    }




}
