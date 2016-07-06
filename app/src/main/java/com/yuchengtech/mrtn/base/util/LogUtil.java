package com.yuchengtech.mrtn.base.util;

import android.util.Log;

/**
 * 日志工具类
 */
public class LogUtil {

    private static final Boolean DEBUG = true;// 日志开关

    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

    public static void d(String tag, String log) {
        if (DEBUG)
            Log.d(tag, log);
    }

    public static void i(String tag, String log) {
        if (DEBUG)
            Log.i(tag, log);
    }

    public static void v(String tag, String log) {
        if (DEBUG)
            Log.v(tag, log);
    }

    public static void w(String tag, String log) {
        if (DEBUG)
            Log.w(tag, log);
    }
}