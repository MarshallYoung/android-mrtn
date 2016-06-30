package com.yuchengtech.mrtn.base;

import android.app.Application;

import com.yuchengtech.mrtn.base.manager.UserManager;

/**
 * 应用主体
 */
public class MrtnApplication extends Application {

    public static String sid;// sessionId
    private static MrtnApplication instance;

    public static MrtnApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UserManager.getInstance();
        instance = this;
        sid = "";
    }
}