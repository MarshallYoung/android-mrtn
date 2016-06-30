package com.yuchengtech.mrtn.activity.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.merchant.bean.TerminalInfo;
import com.yuchengtech.mrtn.merchant.ui.MerchantInfoActivity;

import java.util.List;

public class TermsAdapter extends BaseAdapter {

    private MerchantInfoActivity activity;
    private List<TerminalInfo> list;

    public TermsAdapter(MerchantInfoActivity activity, List<TerminalInfo> list) {
        this.activity = activity;
        this.setList(list);
    }

    @Override
    public int getCount() {
        return getList().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holderView;
        final TerminalInfo indexData = getList().get(position);
        if (convertView == null) {
            holderView = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.mrtn_mcinfo__term_list_item, null);
            holderView.tv_column_termId = (TextView) convertView
                    .findViewById(R.id.tv_column_termId);
            holderView.tv_column_termAddr = (TextView) convertView
                    .findViewById(R.id.tv_column_termAddr);
            holderView.tv_column_servicesManName = (TextView) convertView
                    .findViewById(R.id.tv_column_servicesManName);
            holderView.tv_column_salesManName = (TextView) convertView
                    .findViewById(R.id.tv_column_salesManName);
            holderView.tv_column_hostSerialNo = (TextView) convertView
                    .findViewById(R.id.tv_column_hostSerialNo);
            holderView.tv_column_termLinknm = (TextView) convertView
                    .findViewById(R.id.tv_column_termLinknm);
            holderView.tv_column_termLinktel = (TextView) convertView
                    .findViewById(R.id.tv_column_termLinktel);
            convertView.setTag(holderView);
        } else {
            holderView = (ViewHolder) convertView.getTag();
        }
        holderView.tv_column_termId.setText(indexData.getTermId());
        holderView.tv_column_termAddr.setText(indexData.getTermAddr());
        holderView.tv_column_servicesManName.setText(indexData
                .getServicesManName());
        holderView.tv_column_salesManName.setText(indexData.getSalesManName());
        holderView.tv_column_hostSerialNo.setText(indexData.getHostSerialNo());
        holderView.tv_column_termLinknm.setText(indexData.getTermLinknm());
        holderView.tv_column_termLinktel.setText(indexData.getTermLinktel());

        return convertView;
    }

    public List<TerminalInfo> getList() {
        return list;
    }

    public void setList(List<TerminalInfo> list) {
        this.list = list;
    }

    static class ViewHolder {
        TextView tv_column_termId;
        TextView tv_column_termAddr;
        TextView tv_column_servicesManName;
        TextView tv_column_salesManName;
        TextView tv_column_hostSerialNo;
        TextView tv_column_termLinknm;
        TextView tv_column_termLinktel;
    }
}