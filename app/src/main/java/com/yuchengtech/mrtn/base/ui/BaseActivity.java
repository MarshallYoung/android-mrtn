package com.yuchengtech.mrtn.base.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import butterknife.ButterKnife;

/**
 * 应用基类
 */
@SuppressLint("Registered")
public class BaseActivity extends Activity {

    private InputMethodManager manager;
    //    private long touchTime = 0; // 按下后退的时间点
    //    private long waitTime = 2000; // 在多少毫秒内第二次按下后退键就会退出应用
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
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        IntentFilter filter = new IntentFilter();
        filter.addAction("finish");
        registerReceiver(mFinishReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        hideKeyboard();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        unregisterReceiver(mFinishReceiver);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {// 点击空白区域隐藏输入法
            hideKeyboard();
        }
        return super.onTouchEvent(event);
    }

    /**
     * 隐藏软键盘
     */
    public void hideKeyboard() {
        if (manager.isActive()) {// 输入法打开
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

//    @Override
//    public void onBackPressed() {
//        long currentTime = System.currentTimeMillis();
//        if ((touchTime + waitTime) < currentTime) {
//            Toast.makeText(this, "再次点击退出程序", Toast.LENGTH_SHORT).show();
//            touchTime = currentTime;
//        } else {
//            Intent intent = new Intent("finish");
//            sendBroadcast(intent);
//        }
//    }
}