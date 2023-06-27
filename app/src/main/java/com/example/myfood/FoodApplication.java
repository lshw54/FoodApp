package com.example.myfood;

import android.app.Activity;
import android.app.Application;

import com.example.myfood.bean.User;

import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;
import com.amap.api.maps.MapsInitializer;

public class FoodApplication extends Application {
    private static FoodApplication instance;
    public static synchronized FoodApplication getInstance() {
        return instance;
    }
    private Set<Activity> allActivities;
    private User user;




    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        MapsInitializer.updatePrivacyShow(this,true,true);
        MapsInitializer.updatePrivacyAgree(this,true);
    }


    public void addActivity(Activity activity) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(activity);
    }

    public void removeAllActivity() {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        for (Activity activity : allActivities) {
            activity.finish();
        }
        System.exit(0);
    }

    public void removeActivity(Activity activity) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.remove(activity);
    }

    public User getUser() {
        return user;
    }

    public void setLoginUser(User user) {
        this.user = user;
    }
}
