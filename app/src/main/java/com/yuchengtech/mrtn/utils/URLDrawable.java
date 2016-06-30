package com.yuchengtech.mrtn.utils;

import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class URLDrawable extends BitmapDrawable {

    private Drawable drawable;

    public URLDrawable(Drawable defaultDraw) {
        setDrawable(defaultDraw);
    }

    public void setDrawable(Drawable nDrawable) {
        drawable = nDrawable;
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
        setBounds(0, 0, drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        drawable.draw(canvas);
    }

}
