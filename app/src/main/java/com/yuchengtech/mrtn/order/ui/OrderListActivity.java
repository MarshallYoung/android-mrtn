package com.yuchengtech.mrtn.order.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.base.ui.BaseActivity;
import com.yuchengtech.mrtn.base.util.LogUtil;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.daoimpl.order.GetViewOrders;
import com.yuchengtech.mrtn.http.daoimpl.order.PredictTaskOrder;
import com.yuchengtech.mrtn.order.adapter.OrderListAdapter;
import com.yuchengtech.mrtn.order.bean.OrderInfo;
import com.yuchengtech.mrtn.views.DialogDateAlert;
import com.yuchengtech.mrtn.views.DialogDateAlert.onDialogDateAlertData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 工单列表界面
 */
public class OrderListActivity extends BaseActivity implements
        OnItemClickListener, OnClickListener {

    interface Notes {
        String LOAD_DATA = "加载数据...";
        String UPLOAD_DATA = "上传数据...";
    }

    SweetAlertDialog pDialog;

    private Button btn_back;
    private TextView txt_title;
    private ListView lv_content;
    private OrderListAdapter adapter;
    private List<OrderInfo> list = new ArrayList<OrderInfo>();
    private OrderListHandler handler = new OrderListHandler();
    private String mcId;
    /**
     * 任务单状态
     */
    private String taskStatus;
    private String mcName;
    private OrderInfo current_order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mrtn_atv_order_list_search_data);
        mcId = (String) getIntent().getSerializableExtra("mcId");
        mcName = (String) getIntent().getSerializableExtra("mcName");
        taskStatus = (String) getIntent().getSerializableExtra("taskStatus");
        LogUtil.e("工单列表界面", "mcId=" + mcId + "   mcName=" + mcName
                + "   status=" + taskStatus);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initData(mcId, mcName, taskStatus);
    }

    private void initView() {
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        txt_title = (TextView) findViewById(R.id.txt_title);
        if (taskStatus.equals("1")) {
            txt_title.setText("待办任务单列表");
        } else {
            txt_title.setText("办结任务单列表");
        }
        adapter = new OrderListAdapter(this, list);
        lv_content = (ListView) findViewById(R.id.lv_list_content);
        lv_content.setAdapter(adapter);
        lv_content.setOnItemClickListener(this);
    }

    private void initData(String mcId, String mcName, String taskStatus) {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(Notes.LOAD_DATA);
        pDialog.setCancelable(false);
        pDialog.show();
        GetViewOrders viewOrders = new GetViewOrders(new IHttpURLs() {
            @Override
            public void despatch(Object o) {
            }

            @Override
            public void despatch(Object o, Object ob) {
                Message msg = new Message();
                Integer count = (Integer) ob;
                msg.what = 10;
                msg.getData().putSerializable("list", (Serializable) o);
                if (count == 0) {
                    msg.getData().putString("err", "没有最新的数据!");
                }
                handler.sendMessage(msg);
            }

            @Override
            public void handleErrorInfo(String err) {
                Message msg = new Message();
                msg.what = -1;
                msg.getData().putString("err", err);
                handler.sendMessage(msg);
            }

        }, this);
        viewOrders.request(mcId, taskStatus, mcName, "", "1");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        current_order = adapter.getList().get(position);
        if (current_order.getTaskType().equals("006")
                || current_order.getTaskType().equals("007")
                || current_order.getTaskType().equals("008")
                || current_order.getTaskType().equals("009")) {
            if (current_order.getPredictTime() == null) {
                // 进入预计上门对话框
                DialogDateAlert dialogDateAlert = new DialogDateAlert(this,
                        "设置预计上门时间", "工单号:" + current_order.getTaskId());
                dialogDateAlert.setOnDissDatas(new onDialogDateAlertData() {
                    @Override
                    public void setData(String date) {
                        PostPredictTimeData(current_order.getTaskId(), date);
                    }
                });
                dialogDateAlert.show();

            } else {
                // 进入工单明细界面
                Intent orderinfoIntent = new Intent(OrderListActivity.this,
                        OrderInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("taskId", current_order.getTaskId());
                bundle.putSerializable("taskStatus", taskStatus);
                bundle.putSerializable("taskType", current_order.getTaskType());
                orderinfoIntent.putExtras(bundle);
                startActivity(orderinfoIntent);
            }

        } else {
            Intent orderinfoIntent = new Intent(OrderListActivity.this,
                    OrderInfoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("taskId", current_order.getTaskId());
            bundle.putSerializable("taskStatus", taskStatus);
            bundle.putSerializable("taskType", current_order.getTaskType());
            orderinfoIntent.putExtras(bundle);
            startActivity(orderinfoIntent);
        }

    }

    protected void PostPredictTimeData(String taskId, String date) {
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(Notes.UPLOAD_DATA);
        pDialog.setCancelable(false);
        pDialog.show();
        PredictTaskOrder predictTaskOrder = new PredictTaskOrder(
                new IHttpURLs() {
                    @Override
                    public void handleErrorInfo(String err) {
                        Message msg = new Message();
                        msg.what = -1;
                        msg.getData().putString("err", err);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void despatch(Object o, Object ob) {
                    }

                    @Override
                    public void despatch(Object o) {
                        Message msg = new Message();
                        msg.what = 30;
                        msg.getData().putString("info", o.toString());
                        handler.sendMessage(msg);
                    }
                });
        predictTaskOrder.request(taskId, taskStatus, date);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;

        }

    }

    @SuppressLint("HandlerLeak")
    private class OrderListHandler extends Handler {
        @SuppressWarnings({"unchecked", "deprecation"})
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10: // 成功 - 获取配置信息
                    pDialog.cancel();
                    List<OrderInfo> list = (List<OrderInfo>) msg.getData()
                            .getSerializable("list");
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                    String msg1 = msg.getData().getString("err");
                    if (msg1 != null && !msg1.isEmpty()) {
                        Toast.makeText(OrderListActivity.this, msg1, Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 20:
                    showDialog(1);
                    break;
                case 30:
                    pDialog.cancel();
                    String info = msg.getData().getString("info");
                    Toast.makeText(OrderListActivity.this, info, Toast.LENGTH_SHORT).show();
                    initData(mcId, mcName, taskStatus);
                    break;
                case -1: // 失败 - 获取配置信息
                    pDialog.cancel();
                    String msgs = msg.getData().getString("err");
                    Toast.makeText(OrderListActivity.this, msgs, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}