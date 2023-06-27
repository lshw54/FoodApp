package com.example.myfood.adapter;

import android.content.Context;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfood.R;
import com.example.myfood.bean.Comment;
import com.example.myfood.wight.RoundImageView;

import java.util.List;


public class CommentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private onMyChildrenClickListenr onMyChildrenClickListenr;
    private Context context;
    private List<Comment> commentList;

    public CommentAdapter(Context context, List<Comment> commentList) {
        this.context = context;
        this.commentList = commentList;
    }

    public void setOnMyChildrenClickListenr(onMyChildrenClickListenr onMyChildrenClickListenr) {
        this.onMyChildrenClickListenr = onMyChildrenClickListenr;
    }

    @Override
    public int getItemCount() {
        return commentList == null ? 0 : commentList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment_layout, parent, false);
        return new InnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof InnerHolder){
            Comment comment = commentList.get(position);
            if (comment != null){
                String str = "";
                if (!TextUtils.isEmpty(comment.getReplyName())){
                    str = "<font color='#4A649D'>"+comment.getUserName()+"</font>"+"回复"+
                            "<font color='#4A649D'>"+comment.getReplyName()+": </font>"+comment.getCommentContent();
                }else {
                    str = "<font color='#4A649D'>"+comment.getUserName()+": </font>"+comment.getCommentContent();
                }
                ((InnerHolder) holder).textView.setText(Html.fromHtml(str));
            }
            ((InnerHolder) holder).textView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (event.getAction() == MotionEvent.ACTION_DOWN){
                        ((InnerHolder) holder).textView.setBackgroundColor(context.getResources().getColor(R.color.DCDCDC));

                    }
                    if (event.getAction() == MotionEvent.ACTION_UP){
                        ((InnerHolder) holder).textView.setBackgroundColor(context.getResources().getColor(R.color.F7F7F7));
                    }
                    return false;
                }
            });

            ((InnerHolder) holder).textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMyChildrenClickListenr != null)
                        onMyChildrenClickListenr.onMyClickListener(comment,position, v);
                }
            });
        }

    }


    public interface onMyChildrenClickListenr{
        void onMyClickListener(Comment comment, int postion, View view);
    }

    public class InnerHolder extends RecyclerView.ViewHolder {
        TextView textView;


        public InnerHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.content_tv);


        }
    }

}
