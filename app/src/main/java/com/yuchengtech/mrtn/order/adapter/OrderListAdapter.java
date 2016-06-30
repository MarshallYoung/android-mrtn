package com.yuchengtech.mrtn.order.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.base.ApprtnData;
import com.yuchengtech.mrtn.order.bean.OrderInfo;
import com.yuchengtech.mrtn.order.ui.OrderListActivity;
import com.yuchengtech.mrtn.predict.ui.QueryPredictActivity;

import java.util.List;

/**
 * 查询预计上门工单
 */
public class OrderListAdapter extends BaseAdapter {

    private Context context;
    private List<OrderInfo> list;

    public OrderListAdapter(OrderListActivity orderListActivity,
                            List<OrderInfo> orders) {
        this.context = orderListActivity;
        this.setList(orders);
    }

    public OrderListAdapter(QueryPredictActivity orderPredictListActivity,
                            List<OrderInfo> list2) {
        this.context = orderPredictListActivity;
        this.setList(list2);
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
        final OrderInfo indexData = getList().get(position);
        if (convertView == null) {
            holderView = new OrderListItemView();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.mrtn_order_list_item, null);
            holderView.txt_order_item_taskId = (TextView) convertView
                    .findViewById(R.id.txt_order_item_taskId);
            holderView.txt_order_item_taskType = (TextView) convertView
                    .findViewById(R.id.txt_order_item_taskType);
            holderView.txt_order_item_mcName = (TextView) convertView
                    .findViewById(R.id.txt_order_item_mcName);
            holderView.txt_order_item_disptTime = (TextView) convertView
                    .findViewById(R.id.txt_order_item_disptTime);
            holderView.txt_order_item_termaddr = (TextView) convertView
                    .findViewById(R.id.txt_order_item_termaddr);
            holderView.txt_order_item_termId = (TextView) convertView
                    .findViewById(R.id.txt_order_item_termId);
            holderView.tv_order_PredictTime = (TextView) convertView
                    .findViewById(R.id.tv_order_PredictTime);
            convertView.setTag(holderView);
        } else {
            holderView = (OrderListItemView) convertView.getTag();
        }
        holderView.txt_order_item_taskId.setText(indexData.getTaskId());
        holderView.txt_order_item_taskType.setText(ApprtnData.taskOrderTypeCode
                .get(indexData.getTaskType()));
        holderView.txt_order_item_mcName.setText(indexData.getMcName());

        holderView.txt_order_item_disptTime
                .setText(indexData.getDisptTime() == null ? "" : indexData
                        .getDisptTime().toString());
        holderView.txt_order_item_termaddr.setText(indexData.getTermTaddr());
        holderView.txt_order_item_termId.setText(indexData.getTermId());
        if (indexData.getTaskStatus() != null
                && indexData.getTaskStatus().equals("2")) {
            holderView.tv_order_PredictTime.setText("查看任务单");
        } else {
            if (indexData.getTaskType().equals("006")
                    || indexData.getTaskType().equals("007")
                    || indexData.getTaskType().equals("008")
                    || indexData.getTaskType().equals("009")) {
                if (indexData.getPredictTime() == null) {
                    holderView.tv_order_PredictTime.setText("设置预计上门时间");
                } else {
                    holderView.tv_order_PredictTime.setText("编辑任务单");
                }
            } else {
                holderView.tv_order_PredictTime.setText("编辑任务单");
            }
        }

        return convertView;
    }

    public List<OrderInfo> getList() {
        return list;
    }

    public void setList(List<OrderInfo> list) {
        this.list = list;
    }

    static class OrderListItemView {
        public TextView txt_order_item_taskId;
        public TextView txt_order_item_taskType;
        public TextView txt_order_item_mcName;
        public TextView txt_order_item_disptTime;
        public TextView txt_order_item_termaddr;
        public TextView txt_order_item_termId;
        public TextView tv_order_PredictTime;
    }
}
