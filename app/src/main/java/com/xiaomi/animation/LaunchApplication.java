package com.xiaomi.animation;

import android.app.Application;

/**
 * Created by admin on 2018/3/22.
 */

public class LaunchApplication extends Application{
    private static LaunchApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static LaunchApplication getAppContext() {
        return instance;
    }
}
