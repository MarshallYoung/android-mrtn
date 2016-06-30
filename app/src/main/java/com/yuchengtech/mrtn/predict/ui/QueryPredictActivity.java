package com.yuchengtech.mrtn.predict.ui;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.base.ui.BaseActivity;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.daoimpl.order.GetViewOrders;
import com.yuchengtech.mrtn.http.daoimpl.order.PredictTaskOrder;
import com.yuchengtech.mrtn.order.bean.OrderInfo;
import com.yuchengtech.mrtn.predict.adapter.QueryPredictAdapter;
import com.yuchengtech.mrtn.views.DialogDateAlert;
import com.yuchengtech.mrtn.views.DialogDateAlert.onDialogDateAlertData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 预计上门工单查询界面 /上门装机或退件查询界面
 */
public class QueryPredictActivity extends BaseActivity implements
        OnItemClickListener {

    interface Notes {
        String LOAD_DATA = "加载数据...";
        String UPLOAD_DATA = "上传数据...";
    }

    SweetAlertDialog pDialog;

    protected int mYear;
    protected int mMonth;
    protected int mDay;
    @Bind(R.id.txt_title)
    TextView txt_title;// 导航栏标题
    @Bind(R.id.edt_predict_date)
    EditText edt_predictDate;// 派工日期
    @Bind(R.id.edt_merchant_name)
    EditText edt_merchantName;// 商户名称
    @Bind(R.id.lv_predict)
    ListView lv_content;// 结果列表
    private QueryPredictAdapter adapter;
    private List<OrderInfo> list = new ArrayList<OrderInfo>();
    private OrderListHandler handler = new OrderListHandler();
    private OrderInfo current_order;
    private String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_predict);
        ButterKnife.bind(this);
        type = (String) getIntent().getSerializableExtra("type");
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void initView() {
        if (type.equals("3")) {
            txt_title.setText("预计上门工单查询");
        } else {
            txt_title.setText("上门装机或退件查询");
        }
        adapter = new QueryPredictAdapter(this, list);
        lv_content.setAdapter(adapter);
        lv_content.setOnItemClickListener(this);
    }

    private void initData() {
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

        viewOrders.request("", "1", edt_merchantName.getText().toString(),
                edt_predictDate.getText().toString(), type);
    }

    // 提交数据
    private void PostData(String taskId, String date) {
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
        predictTaskOrder.request(taskId, type, date);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        current_order = adapter.getList().get(position);
        String title = "";
        if (type.equals("3")) {
            title = "设置预计上门时间";
        } else {
            title = "设置上门装机或退件时间";
        }
        DialogDateAlert dialogDateAlert = new DialogDateAlert(this, title,
                "工单号:" + current_order.getTaskId());
        dialogDateAlert.setOnDissDatas(new onDialogDateAlertData() {
            @Override
            public void setData(String date) {
                PostData(current_order.getTaskId(), date);
            }
        });

        dialogDateAlert.show();
    }

    public void onClick_btn_search_two(View view) {
        initData();
    }

    @OnClick(R.id.btn_check_date)
    void checkDate(View view) {
        DialogDateAlert dialogDateAlert = new DialogDateAlert(this, "设置日期", "");
        dialogDateAlert.setOnDissDatas(new onDialogDateAlertData() {
            @Override
            public void setData(String date) {
                edt_predictDate.setText(date);
            }
        });
        dialogDateAlert.show();
    }

    @OnClick(R.id.btn_back)
    void back(View view) {
        finish();
        overridePendingTransition(R.anim.slide_in_from_left,
                R.anim.slide_out_to_right);
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
                        Toast.makeText(QueryPredictActivity.this, msg1, Toast.LENGTH_SHORT).show();
                    }
                    break;

                case 30:
                    String info = msg.getData().getString("info");
                    Toast.makeText(QueryPredictActivity.this, info, Toast.LENGTH_SHORT).show();
                    initData();
                    break;
                case -1: // 失败 - 获取配置信息
                    pDialog.cancel();
                    String msgs = msg.getData().getString("err");
                    Toast.makeText(QueryPredictActivity.this, msgs, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}