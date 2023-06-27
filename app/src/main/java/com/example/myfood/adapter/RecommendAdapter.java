package com.example.myfood.adapter;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Food> foodArrayList;
    private OnClickListener onClickListener;


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public RecommendAdapter(Context context, List<Food> foodArrayList) {
        this.context = context;
        this.foodArrayList = foodArrayList;
    }

    @Override
    public int getItemCount() {
        return foodArrayList == null ? 0 : foodArrayList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recommend, null, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InnerHolder){
            Food restaurant = foodArrayList.get(position);
            if (restaurant != null){
                ((InnerHolder) holder).nameTv.setText(restaurant.getName());
                Glide.with(context)
                        .load(restaurant.getPic())
                        .transition(DrawableTransitionOptions.withCrossFade(600))
                        .into(((InnerHolder) holder).imageView);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickListener != null)
                        onClickListener.onMyClickListener(v,restaurant,position);
                }
            });

        }
    }


    public class InnerHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView nameTv;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.f1_item_image1);
            nameTv = itemView.findViewById(R.id.f1_item_text1);
        }
    }

    public interface OnClickListener{
        void onMyClickListener(View view,Food food,int position);

    }
}
