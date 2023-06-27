package com.example.myfood.mydb;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteHelper extends SQLiteOpenHelper {
    public static final String dbName = "Data.db";
    public static String TAB_USER = "user";
    public static String TAB_DYNAMIC = "dynamic_state";
    public static String TAB_COMMENT = "comment";

    private SQLiteDatabase db;


    public SqliteHelper(Context context) {
        super(context, dbName, null, 1);//Construct the database and pass in the database name and version number
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists " + TAB_USER +
                "(account text primary key," +
                "password text," +
                "avatar text," +
                "name text)");
        db.execSQL("create table if not exists " + TAB_DYNAMIC +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "content text," +
                "account text," +
                "lat text," +
                "lon text," +
                "location text," +
                "imgs text," +
                "time text," +
                "likeNames text," +
                "likes text)");
        db.execSQL("create table if not exists " + TAB_COMMENT +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "pid int," +
                "commentContent text," +
                "userNid text," +
                "time text," +
                "userName text," +
                "replyName text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
