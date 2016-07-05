package com.yuchengtech.mrtn.base.util;

import android.util.Log;

/**
 * 日志工具类
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016年4月20日 下午2:54:04
 */
public class LogUtil {

    private static Boolean DEBUG = true;// 日志开关

    /**
     * 输出错误日志
     */
    public static void e(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }
}