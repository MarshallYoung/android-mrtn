package com.yuchengtech.mrtn.common;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

public class OnFingerTouchListener implements OnTouchListener {

    private OnScrollListener onScrollListener;

	/*
     * public OnFingerTouchListener(OnScrollListener onScrollListener) {
	 * this.onScrollListener = onScrollListener; }
	 */

    public OnFingerTouchListener(OnScrollListener onScrollListener) {
        this.onScrollListener = onScrollListener;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        final int action = event.getAction();
        boolean mFingerUp = action == MotionEvent.ACTION_UP
                || action == MotionEvent.ACTION_CANCEL;
        if (mFingerUp) {
            onScrollListener.onScrollStateChanged((AbsListView) v,
                    OnScrollListener.SCROLL_STATE_FLING);
            onScrollListener.onScrollStateChanged((AbsListView) v,
                    OnScrollListener.SCROLL_STATE_IDLE);
        }
        return false;
    }

}
