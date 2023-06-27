package com.example.myfood.wight;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {

    private static final String FILLNAME = "share_data";
    private static SharedPreferences mSharedPreferences = null;

    /**
     * Singleton mode
     */
    private static synchronized SharedPreferences getInstance(Context context) {
        if (mSharedPreferences == null) {
            mSharedPreferences = context.getApplicationContext().getSharedPreferences(FILLNAME, Context.MODE_PRIVATE);
        }
        return mSharedPreferences;
    }

    public static void putString(Context context, String key, String value) {
        getInstance(context).edit().putString(key, value).apply();
    }

    public static String getString(Context context, String key, String defValue) {
        return getInstance(context).getString(key, defValue);
    }

    public static void putBoolean(Context context, String key, Boolean value) {
        getInstance(context).edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(Context context, String key, Boolean defValue) {
        return getInstance(context).getBoolean(key, defValue);
    }

    public static boolean contains(Context context, String key) {
        return getInstance(context).contains(key);
    }
}
