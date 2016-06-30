package com.yuchengtech.mrtn.order.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.order.bean.OrderListInfo;
import com.yuchengtech.mrtn.order.ui.QueryOrdersActivity;

import java.util.List;

/**
 * 查询工单列表适配器
 */
public class QueryOrdersAdapter extends BaseAdapter {

    public List<OrderListInfo> list;
    private QueryOrdersActivity activity;

    public QueryOrdersAdapter(QueryOrdersActivity activity,
                              List<OrderListInfo> list) {
        this.activity = activity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
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
        final ViewHolder viewHolder;
        final OrderListInfo info = list.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.mrtn_mcinfo_list_item, null);
            viewHolder = new ViewHolder();
            viewHolder.txt_mc_id = (TextView) convertView
                    .findViewById(R.id.txt_mc_id);
            viewHolder.txt_mc_name = (TextView) convertView
                    .findViewById(R.id.txt_mc_name);
            viewHolder.txt_order_type_001 = (TextView) convertView
                    .findViewById(R.id.txt_order_type_001);
            viewHolder.txt_order_type_002 = (TextView) convertView
                    .findViewById(R.id.txt_order_type_002);
            viewHolder.txt_order_type_003 = (TextView) convertView
                    .findViewById(R.id.txt_order_type_003);
            viewHolder.txt_order_type_004 = (TextView) convertView
                    .findViewById(R.id.txt_order_type_004);
            viewHolder.txt_order_type_005 = (TextView) convertView
                    .findViewById(R.id.txt_order_type_005);
            viewHolder.txt_order_type_006 = (TextView) convertView
                    .findViewById(R.id.txt_order_type_006);
            viewHolder.txt_order_type_007 = (TextView) convertView
                    .findViewById(R.id.txt_order_type_007);
            viewHolder.txt_order_type_008 = (TextView) convertView
                    .findViewById(R.id.txt_order_type_008);
            viewHolder.txt_order_type_009 = (TextView) convertView
                    .findViewById(R.id.txt_order_type_009);
            viewHolder.txt_order_type_010 = (TextView) convertView
                    .findViewById(R.id.txt_order_type_010);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txt_mc_id.setText(info.getMcid());
        viewHolder.txt_mc_name.setText(info.getMcName());
        viewHolder.txt_order_type_001.setText(String.valueOf(info
                .getType001()));
        viewHolder.txt_order_type_002.setText(String.valueOf(info
                .getType002()));
        viewHolder.txt_order_type_003.setText(String.valueOf(info
                .getType003()));
        viewHolder.txt_order_type_004.setText(String.valueOf(info
                .getType004()));
        viewHolder.txt_order_type_005.setText(String.valueOf(info
                .getType005()));
        viewHolder.txt_order_type_006.setText(String.valueOf(info
                .getType006()));
        viewHolder.txt_order_type_007.setText(String.valueOf(info
                .getType007()));
        viewHolder.txt_order_type_008.setText(String.valueOf(info
                .getType008()));
        viewHolder.txt_order_type_009.setText(String.valueOf(info
                .getType009()));
        viewHolder.txt_order_type_010.setText(String.valueOf(info
                .getType010()));
        return convertView;
    }

    static class ViewHolder {
        public TextView txt_mc_id;
        public TextView txt_mc_name;
        public TextView txt_order_type_001;
        public TextView txt_order_type_002;
        public TextView txt_order_type_003;
        public TextView txt_order_type_004;
        public TextView txt_order_type_005;
        public TextView txt_order_type_006;
        public TextView txt_order_type_007;
        public TextView txt_order_type_008;
        public TextView txt_order_type_009;
        public TextView txt_order_type_010;
    }
}