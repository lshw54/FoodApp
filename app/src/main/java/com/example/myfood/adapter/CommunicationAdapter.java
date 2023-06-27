package com.example.myfood.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.example.myfood.FoodApplication;
import com.example.myfood.R;
import com.example.myfood.activity.LoginActivity;
import com.example.myfood.bean.CircleListBean;
import com.example.myfood.bean.Comment;
import com.example.myfood.bean.Process;
import com.example.myfood.bean.User;
import com.example.myfood.mydb.CommentDao;
import com.example.myfood.mydb.DynamicDao;
import com.example.myfood.wight.MyGridView;
import com.example.myfood.wight.RoundImageView;

import java.util.Arrays;
import java.util.List;

public class CommunicationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<CircleListBean> processList;
    private User loginUser;
    private DynamicDao dynamicDao;
    private OnMyClickListener onMyClickListener;
    private CommentDao commentDao;
    private int type;

    public void setOnMyClickListener(OnMyClickListener onMyClickListener) {
        this.onMyClickListener = onMyClickListener;
    }

    public CommunicationAdapter(Context context, List<CircleListBean> processList, int type) {
        this.context = context;
        this.processList = processList;
        this.type = type;
        loginUser = FoodApplication.getInstance().getUser();
        dynamicDao = new DynamicDao(context);
        commentDao = new CommentDao(context);
    }


    public CommunicationAdapter(Context context, List<CircleListBean> processList) {
        this.context = context;
        this.processList = processList;
        loginUser = FoodApplication.getInstance().getUser();
        dynamicDao = new DynamicDao(context);
        commentDao = new CommentDao(context);
    }

    @Override
    public int getItemCount() {
        return processList == null ? 0 : processList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_communication, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InnerHolder) {


            CircleListBean bean = processList.get(position);
            String likeStr = bean.getLikes();
            if (!TextUtils.isEmpty(likeStr)) {
                String[] listArray = likeStr.split(",");
                ((InnerHolder) holder).like_user_tv.setVisibility(View.VISIBLE);
                ((InnerHolder) holder).like_user_tv.setText(likeStr);
                ((InnerHolder) holder).like_num_tv.setText(listArray.length + "");
            } else {
                ((InnerHolder) holder).like_user_tv.setVisibility(View.GONE);
                ((InnerHolder) holder).like_num_tv.setText("0");

            }

            if (bean != null) {
                if (TextUtils.isEmpty(bean.getLocation())) {
                    ((InnerHolder) holder).location_tv.setVisibility(View.GONE);
                } else {
                    ((InnerHolder) holder).location_tv.setVisibility(View.VISIBLE);
                    ((InnerHolder) holder).location_tv.setText(bean.getLocation());
                }
                if (bean.getUser() != null) {
                    Log.e("TAG", "onBindViewHolder: " + bean.getUser());
                    if (!TextUtils.isEmpty(bean.getUser().getAvatar())) {
                        Glide.with(context).load(bean.getUser().getAvatar()).into(((InnerHolder) holder).imageView);
                    }
                    ((InnerHolder) holder).nameTv.setText(bean.getUser().getName());

                }
                ((InnerHolder) holder).contentTv.setText(bean.getContent());
                ((InnerHolder) holder).timeTv.setText(bean.getTime());

                if (!TextUtils.isEmpty(bean.getImgs())) {
                    ((InnerHolder) holder).imgRecyclerview.setVisibility(View.VISIBLE);
                    String[] imgArray = bean.getImgs().split(",");
                    List<String> imgList = Arrays.asList(imgArray);
                    GridViewAdapter gridViewAdapter = new GridViewAdapter(imgList, context, false);
                    ((InnerHolder) holder).imgRecyclerview.setAdapter(gridViewAdapter);
                } else {
                    ((InnerHolder) holder).imgRecyclerview.setVisibility(View.GONE);
                }
            }

            if (type == 1) {

                ((InnerHolder) holder).delete_tv.setVisibility(View.VISIBLE);

            } else {
                ((InnerHolder) holder).delete_tv.setVisibility(View.GONE);

            }
            ((InnerHolder) holder).delete_tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
                            .setTitle("Tip")
                            .setMessage("Are you sure you want to delete it")
                            .setPositiveButton("sure", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (dynamicDao.delelte(bean.getId()+"") > 0){
                                        processList.remove(bean);
                                        notifyDataSetChanged();
                                    }
                                }
                            })
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();

                }
            });

            List<Comment> commentList = commentDao.queryOfData(bean.getId() + "");
            bean.setCommentList(commentList);
            ((InnerHolder) holder).comment_recycler.setLayoutManager(new LinearLayoutManager(context));
            CommentAdapter commentAdapter = new CommentAdapter(context, bean.getCommentList());
            ((InnerHolder) holder).comment_recycler.setAdapter(commentAdapter);
            ((InnerHolder) holder).comment_num_tv.setText(bean.getCommentList().size() + "");
            commentAdapter.setOnMyChildrenClickListenr(new CommentAdapter.onMyChildrenClickListenr() {
                @Override
                public void onMyClickListener(Comment comment, int p, View view) {
                    if (onMyClickListener != null)
                        onMyClickListener.onClickListener(view, bean, comment, position, p);
                }
            });


            ((InnerHolder) holder).like_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (FoodApplication.getInstance().getUser() == null) {
                        Toast.makeText(context, "Please login", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, LoginActivity.class));
                        return;
                    }
                    int a = Integer.parseInt(((InnerHolder) holder).like_num_tv.getText().toString());
                    String likeStr = bean.getLikes();
                    if (!TextUtils.isEmpty(likeStr)) {
                        StringBuilder stringBuilder = new StringBuilder();
                        String[] listArray = likeStr.split(",");
                        for (String s : listArray) {
                            if (s.equals(loginUser.getName())) {

                            } else {
                                if (stringBuilder.length() == 0) {
                                    stringBuilder.append(s);
                                } else {
                                    stringBuilder.append(",").append(s);
                                }
                            }
                        }
                        likeStr = stringBuilder.toString();
                        if (a > 0)
                            a--;
                    } else {
                        likeStr = loginUser.getName();
                    }
                    bean.setLikes(likeStr);
                    dynamicDao.update(bean);
                    notifyDataSetChanged();
                }
            });

            ((InnerHolder) holder).comment_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMyClickListener != null)
                        onMyClickListener.onClickListener(v, bean, position);
                }
            });
        }
    }


    public class InnerHolder extends RecyclerView.ViewHolder {
        RoundImageView imageView;
        TextView nameTv;
        TextView contentTv;
        TextView timeTv;
        TextView like_user_tv;
        TextView like_num_tv;
        TextView comment_num_tv;
        TextView location_tv;
        TextView delete_tv;
        MyGridView imgRecyclerview;
        RecyclerView comment_recycler;

        LinearLayout like_layout;
        LinearLayout comment_layout;

        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            nameTv = itemView.findViewById(R.id.name_tv);
            contentTv = itemView.findViewById(R.id.content_tv);
            timeTv = itemView.findViewById(R.id.time_tv);
            like_user_tv = itemView.findViewById(R.id.like_user_tv);
            like_num_tv = itemView.findViewById(R.id.like_num_tv);
            comment_num_tv = itemView.findViewById(R.id.comment_num_tv);
            location_tv = itemView.findViewById(R.id.location_tv);
            delete_tv = itemView.findViewById(R.id.delete_tv);

            like_layout = itemView.findViewById(R.id.like_layout);
            comment_layout = itemView.findViewById(R.id.comment_layout);

            imgRecyclerview = itemView.findViewById(R.id.recycler_view);
            comment_recycler = itemView.findViewById(R.id.comment_recycler);

        }
    }

    public interface OnMyClickListener {
        void onClickListener(View view, CircleListBean bean, int position);

        void onClickListener(View view, CircleListBean bean, Comment comment, int position, int childerPosti);

    }


}
