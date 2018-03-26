package com.xiaomi.animation;

import android.app.Application;
import android.content.Context;

/**
 * Created by admin on 2018/3/22.
 */

public class LaunchApplication extends Application{
    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this.getBaseContext();
    }

    public static Context getContext() {
        return sContext;
    }
}
