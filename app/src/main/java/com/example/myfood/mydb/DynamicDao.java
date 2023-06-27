package com.example.myfood.mydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.myfood.bean.CircleListBean;
import com.example.myfood.bean.User;
import java.util.ArrayList;

public class DynamicDao {
    private final SqliteHelper helper;

    public DynamicDao(Context context) {
        helper = new SqliteHelper(context);
    }



    public long add(CircleListBean bean) {
        SQLiteDatabase database = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("content", bean.getContent());
        values.put("account", bean.getAccount());
        values.put("lat", bean.getLat());
        values.put("lon", bean.getLon());
        values.put("location", bean.getLocation());
        values.put("imgs", bean.getImgs());
        values.put("likes", bean.getLikes());
        values.put("time", bean.getTime());
        values.put("likeNames", bean.getLikeNames());
        long res = database.insert(SqliteHelper.TAB_DYNAMIC, null, values);
        database.close();
        return res;
    }


    public int delelte(String id) {
        SQLiteDatabase database = helper.getReadableDatabase();
        int res = database.delete(SqliteHelper.TAB_DYNAMIC, "id=?", new String[]{id});
        database.close();
        return res;
    }



    public int update(CircleListBean bean) {
        SQLiteDatabase database = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("content", bean.getContent());
        values.put("account", bean.getAccount());
        values.put("lat", bean.getLat());
        values.put("lon", bean.getLon());
        values.put("location", bean.getLocation());
        values.put("imgs", bean.getImgs());
        values.put("likes", bean.getLikes());
        values.put("likeNames", bean.getLikeNames());
        values.put("time", bean.getTime());

        int res = database.update(SqliteHelper.TAB_DYNAMIC, values, "id=?", new String[]{bean.getId()+""});
        database.close();
        return res;
    }

    public ArrayList<CircleListBean> select() {
        ArrayList<CircleListBean> mArrayList = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(SqliteHelper.TAB_DYNAMIC, new String[]{"id",
                        "content", "account", "lat","lon","location","imgs","likes","likeNames","time"},
                null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int idss = cursor.getInt(cursor.getColumnIndex("id"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String account = cursor.getString(cursor.getColumnIndex("account"));
                String lat = cursor.getString(cursor.getColumnIndex("lat"));
                String lon = cursor.getString(cursor.getColumnIndex("lon"));
                String location = cursor.getString(cursor.getColumnIndex("location"));
                String imgs = cursor.getString(cursor.getColumnIndex("imgs"));
                String likes = cursor.getString(cursor.getColumnIndex("likes"));
                String likeNames = cursor.getString(cursor.getColumnIndex("likeNames"));

                String time = cursor.getString(cursor.getColumnIndex("time"));

                CircleListBean bean = new CircleListBean(idss,content,account,lat,lon,location,imgs,likes,likeNames,time);
                mArrayList.add(bean);
            }
        }
        cursor.close();
        database.close();
        return mArrayList;
    }


    public ArrayList<CircleListBean> queryOfData(String account) {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(SqliteHelper.TAB_DYNAMIC, null,
                "account=?", new String[]{"" + account}, null, null, null);

        ArrayList<CircleListBean> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int idss = cursor.getInt(cursor.getColumnIndex("id"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String accounts = cursor.getString(cursor.getColumnIndex("account"));
                String lat = cursor.getString(cursor.getColumnIndex("lat"));
                String lon = cursor.getString(cursor.getColumnIndex("lon"));
                String location = cursor.getString(cursor.getColumnIndex("location"));
                String imgs = cursor.getString(cursor.getColumnIndex("imgs"));
                String likes = cursor.getString(cursor.getColumnIndex("likes"));
                String likeNames = cursor.getString(cursor.getColumnIndex("likeNames"));
                String time = cursor.getString(cursor.getColumnIndex("time"));
                CircleListBean bean = new CircleListBean(idss,content,accounts,lat,lon,location,imgs,likes,likeNames,time);
                list.add(bean);
            }
            cursor.close();
        }
        database.close();
        return list;
    }
}
