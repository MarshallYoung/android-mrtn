package com.yuchengtech.mrtn.main.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.main.bean.FunctionButtonInfo;
import com.yuchengtech.mrtn.main.ui.HomepageFragment;

import java.util.List;

/**
 * 功能按钮的适配器,用于主页(HomepageFragment)
 */
public class FunctionsAdapter extends BaseAdapter {

    public HomepageFragment fragment;
    public List<FunctionButtonInfo> functionButtonList;

    public FunctionsAdapter(HomepageFragment context,
                            List<FunctionButtonInfo> list) {
        this.fragment = context;
        this.functionButtonList = list;
    }

    @Override
    public int getCount() {
        return functionButtonList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return arg0;
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup arg2) {
        final HolderView holderView;
        final FunctionButtonInfo indexData = getIndexEntities().get(position);
        if (convertView == null) {
            holderView = new HolderView();
            convertView = LayoutInflater.from(fragment.getActivity()).inflate(
                    R.layout.item_function_button, null);
            holderView.btn_icon = (ImageButton) convertView
                    .findViewById(R.id.btn_icon);
            holderView.txt_name = (TextView) convertView
                    .findViewById(R.id.txt_name);
            convertView.setTag(holderView);
        } else {
            holderView = (HolderView) convertView.getTag();
        }
        if (indexData.imgIndex != 0) {
            holderView.btn_icon.setBackgroundResource(indexData.imgIndex);
            holderView.btn_icon.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((HomepageFragment) fragment).Jump2Type(position);
                }
            });
        }
        // holderView.btn_icon.setImageResource(indexData.getImgIndex());
        holderView.txt_name.setText(indexData.name);
        return convertView;
    }

    public List<FunctionButtonInfo> getIndexEntities() {
        return functionButtonList;
    }

    public void setIndexEntities(List<FunctionButtonInfo> indexEntities) {
        this.functionButtonList = indexEntities;
    }

    class HolderView {
        public ImageButton btn_icon;
        public TextView txt_name;
    }
}