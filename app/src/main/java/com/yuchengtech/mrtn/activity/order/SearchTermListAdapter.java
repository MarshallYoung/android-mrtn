package com.yuchengtech.mrtn.activity.order;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.merchant.bean.TerminalInfo;

import java.util.List;

public class SearchTermListAdapter extends BaseAdapter {

    private McTermListActivity activity;
    private List<TerminalInfo> list;

    public SearchTermListAdapter(McTermListActivity mcTermListActivity,
                                 List<TerminalInfo> mcTerms) {
        this.activity = mcTermListActivity;
        this.setList(mcTerms);
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
        final ViewHolder holderView;
        final TerminalInfo indexData = getList().get(position);
        if (convertView == null) {
            holderView = new ViewHolder();
            convertView = LayoutInflater.from(activity).inflate(
                    R.layout.mrtn_term_list_item, null);
            holderView.txt_item_mcId = (TextView) convertView
                    .findViewById(R.id.txt_item_mcId);
            holderView.txt_item_mcTaddr = (TextView) convertView
                    .findViewById(R.id.txt_item_mcTaddr);
            holderView.txt_item_mcTermId = (TextView) convertView
                    .findViewById(R.id.txt_item_mcTermId);

            convertView.setTag(holderView);
        } else {
            holderView = (ViewHolder) convertView.getTag();
        }
        holderView.txt_item_mcId.setText(indexData.getMcId());
        holderView.txt_item_mcTaddr.setText(indexData.getTermAddr());
        holderView.txt_item_mcTermId.setText(indexData.getTermId());

        return convertView;
    }

    public List<TerminalInfo> getList() {
        return list;
    }

    public void setList(List<TerminalInfo> list) {
        this.list = list;
    }

    class ViewHolder {
        public TextView txt_item_mcId;
        public TextView txt_item_mcTaddr;
        public TextView txt_item_mcTermId;
    }
}