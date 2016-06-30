package com.yuchengtech.mrtn.merchant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.merchant.bean.MerchantInfo;
import com.yuchengtech.mrtn.merchant.ui.QueryMerchantActivity;

import java.util.List;

/**
 * 商户信息查询适配器,用于商户查询列表(QueryMerchantActivity)
 */
public class QueryMerchantAdapter extends BaseAdapter {

    private Context context;
    private List<MerchantInfo> list;

    public QueryMerchantAdapter(QueryMerchantActivity mcListActivity,
                                List<MerchantInfo> mcInfos) {
        this.context = mcListActivity;
        this.setList(mcInfos);
    }

    @Override
    public int getCount() {
        return getList().size();
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
        final OrderListItemView holderView;
        final MerchantInfo indexData = getList().get(position);
        if (convertView == null) {
            holderView = new OrderListItemView();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.mrtn_mc_list_item, null);
            holderView.txt_g_mcId = (TextView) convertView
                    .findViewById(R.id.txt_g_mcId);
            holderView.txt_g_mcName = (TextView) convertView
                    .findViewById(R.id.txt_g_mcName);
            holderView.txt_g_instName = (TextView) convertView
                    .findViewById(R.id.txt_g_instName);
            holderView.txt_g_addr = (TextView) convertView
                    .findViewById(R.id.txt_g_addr);
            convertView.setTag(holderView);
        } else {
            holderView = (OrderListItemView) convertView.getTag();
        }
        holderView.txt_g_mcId.setText(indexData.getMcId());
        holderView.txt_g_mcName.setText(indexData.getMcName());
        holderView.txt_g_instName.setText(indexData.getInstName());
        holderView.txt_g_addr.setText(indexData.getMcAddr());
        return convertView;
    }

    public List<MerchantInfo> getList() {
        return list;
    }

    public void setList(List<MerchantInfo> list) {
        this.list = list;
    }

    static class OrderListItemView {
        public TextView txt_g_mcId;
        public TextView txt_g_mcName;
        public TextView txt_g_instName;
        public TextView txt_g_addr;
    }
}