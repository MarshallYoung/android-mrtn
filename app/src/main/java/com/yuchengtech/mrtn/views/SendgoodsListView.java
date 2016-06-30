package com.yuchengtech.mrtn.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class SendgoodsListView extends ListView {

    public SendgoodsListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SendgoodsListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SendgoodsListView(Context context) {
        super(context);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
