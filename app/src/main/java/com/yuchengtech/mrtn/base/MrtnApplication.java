package com.yuchengtech.mrtn.base;

import android.app.Application;

import com.yuchengtech.mrtn.base.manager.NetworkManager;
import com.yuchengtech.mrtn.base.manager.UserManager;

/**
 * 应用主体
 */
public class MrtnApplication extends Application {

    private static MrtnApplication instance;
    public static String sid;// sessionId
    public static NetworkManager networkManager;

    public static MrtnApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UserManager.getInstance();
        instance = this;
        sid = "";
        networkManager = new NetworkManager(getApplicationContext());
    }
}