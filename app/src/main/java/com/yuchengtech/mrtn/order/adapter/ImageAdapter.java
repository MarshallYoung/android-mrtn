package com.yuchengtech.mrtn.order.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.bitmap.PauseOnScrollListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageAdapter extends BaseAdapter implements OnScrollListener {
    /**
     * 上下文对象的引用
     */
    private Context context;
    private BitmapUtils bitmapUtils;
    /**
     * Image Url的数组
     */
    private List<String> imageThumbUrls;

    /**
     * GridView对象的应用
     */
    private GridView mGridView;

    /**
     * 一屏中第一个item的位置
     */
    private int mFirstVisibleItem;

    /**
     * 一屏中所有item的个数
     */
    private int mVisibleItemCount;

    private String lastItem;

    public ImageAdapter(Context context, GridView mGridView,
                        String[] imageThumbUrls) {
        this.context = context;
        bitmapUtils = new BitmapUtils(context);
        this.mGridView = mGridView;
        mGridView.setOnScrollListener(new PauseOnScrollListener(bitmapUtils,
                false, true));
        if (imageThumbUrls != null) {
            this.imageThumbUrls = Arrays.asList(imageThumbUrls);
        } else {
            this.imageThumbUrls = new ArrayList<String>();
        }
    }

    /**
     * GridView滚动的时候调用的方法，刚开始显示GridView也会调用此方法
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;

    }

    @Override
    public int getCount() {
        return imageThumbUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return imageThumbUrls.get(position);
    }

    public void setItem(String item) {
        imageThumbUrls.add(item);
        lastItem = item;
    }

    // 回退 删除最近添加的一张图片
    public void backSpace() {
        if (lastItem != null && !lastItem.isEmpty()) {
            imageThumbUrls.remove(imageThumbUrls.lastIndexOf(lastItem));
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView mImageView;
        final String mImageUrl = imageThumbUrls.get(position);
        if (convertView == null) {
            mImageView = new ImageView(context);
        } else {
            mImageView = (ImageView) convertView;
        }

        mImageView.setLayoutParams(new GridView.LayoutParams(120, 120));
        mImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        // 动态高度
        double row = (getCount() / 4 > 0 ? (getCount() / 4.0) : 1.0);
        if (row > 1) {
            LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) mGridView
                    .getLayoutParams();
            linearParams.height = 120 * (int) Math.ceil(row);
            mGridView.setLayoutParams(linearParams);
        }
        // 给ImageView设置Tag
        mImageView.setTag(mImageUrl);
        bitmapUtils.display(mImageView, mImageUrl);
        return mImageView;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // TODO Auto-generated method stub

    }

}
