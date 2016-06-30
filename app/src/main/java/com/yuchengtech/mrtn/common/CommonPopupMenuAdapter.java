package com.yuchengtech.mrtn.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;

import java.util.List;

public class CommonPopupMenuAdapter extends BaseAdapter {

    private Context context;
    private List<CommonStrEntity> list;

    public CommonPopupMenuAdapter(Context context, List<CommonStrEntity> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ItemListView itemListView = null;
        if (convertView == null) {
            itemListView = new ItemListView();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.mrtn_atv_common_menu_list_item, null);
            itemListView.menu = (TextView) convertView.findViewById(R.id.menu);

            convertView.setTag(itemListView);
        }
        itemListView = (ItemListView) convertView.getTag();
        itemListView.menu.setText(list.get(position).name);
        return convertView;
    }

    static class ItemListView {
        private TextView menu;
    }

}
