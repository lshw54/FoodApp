package com.example.myfood.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class AssertUtils {
    public static String getContent(Context context, String fileName) {
        try {
            InputStreamReader inputReader = new
                    InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            StringBuilder Result = new StringBuilder();
            while ((line = bufReader.readLine()) != null)
                Result.append(line);

            return Result.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
