package com.example.myfood.mydb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.myfood.bean.User;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    private final SqliteHelper helper;

    public UserDao(Context context) {
        helper = new SqliteHelper(context);
    }



    public long add(User user) {
        SQLiteDatabase database = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("account", user.getAccount());
        values.put("password", user.getPassword());
        values.put("avatar", user.getAvatar());
        values.put("name", user.getName());
        long res = database.insert(SqliteHelper.TAB_USER, null, values);
        database.close();
        return res;
    }


    public int delelte(String account) {
        SQLiteDatabase database = helper.getReadableDatabase();
        int res = database.delete(SqliteHelper.TAB_USER, "account=?", new String[]{account});
        database.close();
        return res;
    }



    public int update(User user) {
        SQLiteDatabase database = helper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());
        values.put("avatar", user.getAvatar());
        values.put("name", user.getName());
        int res = database.update(SqliteHelper.TAB_USER, values, "account=?", new String[]{user.getAccount()});
        database.close();
        return res;
    }

    public ArrayList<User> select() {
        ArrayList<User> mArrayList = new ArrayList<>();
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(SqliteHelper.TAB_USER, new String[]{"account", "password", "avatar", "name"},
                null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String account = cursor.getString(cursor.getColumnIndex("account"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                User user = new User(account, password, avatar, name);
                mArrayList.add(user);
            }
        }
        cursor.close();
        database.close();
        return mArrayList;
    }


    public ArrayList<User> queryOfData(String account) {
        SQLiteDatabase database = helper.getReadableDatabase();
        Cursor cursor = database.query(SqliteHelper.TAB_USER, null,
                "account=?", new String[]{"" + account}, null, null, null);

        ArrayList<User> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String accounts = cursor.getString(cursor.getColumnIndex("account"));
                String password = cursor.getString(cursor.getColumnIndex("password"));
                String avatar = cursor.getString(cursor.getColumnIndex("avatar"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                User user = new User(accounts, password, avatar, name);
                list.add(user);
            }
            cursor.close();
        }
        database.close();
        return list;
    }
}
