package com.example.myfood.mydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myfood.bean.CircleListBean;
import com.example.myfood.bean.Comment;

import java.util.ArrayList;

public class CommentDao {
    private final SqliteHelper helper;

    public CommentDao(Context context) {
        helper = new SqliteHelper(context);
    }



    public long add(Comment comment) {
        SQLiteDatabase database = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("pid", comment.getPid());
        values.put("commentContent", comment.getCommentContent());
        values.put("userNid", comment.getUserNid());
        values.put("time", comment.getTime());
        values.put("userName", comment.getUserName());
        values.put("replyName", comment.getReplyName());
        long res = database.insert(SqliteHelper.TAB_COMMENT, null, values);
        database.close();
        return res;
    }


    public int delelte(String id) {
        SQLiteDatabase database = helper.getReadableDatabase();
        int res = database.delete(SqliteHelper.TAB_COMMENT, "id=?", new String[]{id});
        database.close();
        return res;
    }



    public int update(Comment comment) {
        SQLiteDatabase database = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("pid", comment.getPid());
        values.put("commentContent", comment.getCommentContent());
        values.put("userNid", comment.getUserNid());
        values.put("time", comment.getTime());
        values.put("userName", comment.getUserName());
        values.put("replyName", comment.getReplyName());

        int res = database.update(SqliteHelper.TAB_COMMENT, values, "id=?", new String[]{comment.getId()+""});
        database.close();
        return res;
    }

    public ArrayList<Comment> select() {
        ArrayList<Comment> mArrayList = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(SqliteHelper.TAB_COMMENT, new String[]{"id",
                        "id", "pid", "commentContent","userNid","time","userName","replyName"},
                null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int idss = cursor.getInt(cursor.getColumnIndex("id"));
                int pid = cursor.getInt(cursor.getColumnIndex("pid"));

                String commentContent = cursor.getString(cursor.getColumnIndex("commentContent"));
                String userNid = cursor.getString(cursor.getColumnIndex("userNid"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String userName = cursor.getString(cursor.getColumnIndex("userName"));
                String replyName = cursor.getString(cursor.getColumnIndex("replyName"));

                Comment bean = new Comment(commentContent,idss,pid,userNid,time,userName,replyName);
                mArrayList.add(bean);
            }
        }
        cursor.close();
        database.close();
        return mArrayList;
    }


    public ArrayList<Comment> queryOfData(String account) {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(SqliteHelper.TAB_COMMENT, null,
                "pid=?", new String[]{"" + account}, null, null, null);

        ArrayList<Comment> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int idss = cursor.getInt(cursor.getColumnIndex("id"));
                int pid = cursor.getInt(cursor.getColumnIndex("pid"));

                String commentContent = cursor.getString(cursor.getColumnIndex("commentContent"));
                String userNid = cursor.getString(cursor.getColumnIndex("userNid"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                String userName = cursor.getString(cursor.getColumnIndex("userName"));
                String replyName = cursor.getString(cursor.getColumnIndex("replyName"));

                Comment bean = new Comment(commentContent,idss,pid,userNid,time,userName,replyName);
                list.add(bean);
            }
            cursor.close();
        }
        database.close();
        return list;
    }
}
