package com.yuchengtech.mrtn.base.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 应用基类
 */
@SuppressLint("Registered")
public class BaseActivity extends Activity {

    private long touchTime = 0; // 按下后退的时间点
    private long waitTime = 2000; // 在多少毫秒内第二次按下后退键就会退出应用
    private BroadcastReceiver mFinishReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("finish".equals(intent.getAction())) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter();
        filter.addAction("finish");
        registerReceiver(mFinishReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mFinishReceiver);
    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if ((touchTime + waitTime) < currentTime) {
            Toast.makeText(this, Notes.EXIT, Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        } else {
            Intent intent = new Intent("finish");
            sendBroadcast(intent);
        }
    }

    private interface Notes {
        String EXIT = "再次点击退出程序";
    }
}