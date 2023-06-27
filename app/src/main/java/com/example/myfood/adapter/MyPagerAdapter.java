package com.example.myfood.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.myfood.bean.Food;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class MyPagerAdapter  extends PagerAdapter {
    private List<Food> banners;
    private Context context;
    private OnBannerClickListener onBannerClickListener;
    private static final String TAG = "MyPagerAdapter";

    public MyPagerAdapter(Context context,List<Food> banners) {
        this.context = context;
        this.banners = banners;
    }

    @Override
    public int getCount() {
        //Returns the maximum value of int Can be slid all the time
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        //Log.e("TAG", "instantiateItem1: " + position);
        ImageView imageView = new ImageView(context);
        //Set the image scaling type
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //Set the current subscript in via the setTag method
        if (banners.size() > 0){
            position %= banners.size();
            imageView.setTag(position);
            Food food = banners.get(position);
            Glide.with(context).load(food.getPic()).into(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onBannerClickListener != null)
                        onBannerClickListener.onBannerClickListener(v,food);
                }
            });
        }


        container.addView(imageView);
        return imageView;
    }



    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }

    public interface OnBannerClickListener{

        void onBannerClickListener(View view,Food food);
    }

    public List<Food> getBanners() {
        return banners;
    }
}

