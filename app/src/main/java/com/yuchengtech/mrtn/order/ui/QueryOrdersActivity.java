package com.yuchengtech.mrtn.order.ui;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.base.ui.BaseActivity;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.daoimpl.order.GetGroupMcInfos;
import com.yuchengtech.mrtn.order.adapter.QueryOrdersAdapter;
import com.yuchengtech.mrtn.order.bean.OrderListInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 工单查询界面
 */
public class QueryOrdersActivity extends BaseActivity {

    interface Notes {
        String ERROR_ILLEGAL_ID = "商户编号不能大于15位";
        String LOADING_DATA = "加载数据...";
    }

    SweetAlertDialog pDialog;

    protected int mYear;
    protected int mMonth;
    protected int mDay;
    @Bind(R.id.txt_title)
    TextView txt_title;// 导航栏标题
    @Bind(R.id.edt_merchant_id)
    EditText edt_merchantId;// 商户编号
    @Bind(R.id.edt_merchant_name)
    EditText edt_merchantName;// 商户名称
    @Bind(R.id.edt_order_date)
    EditText edt_orderDate;// 订单日期
    private ListView lv_list_content;
    private List<OrderListInfo> mcInfos;
    private QueryOrdersAdapter adapter;
    private McinfoListHandler handler = new McinfoListHandler();
    private String taskStatus;
    OnItemClickListener itemclicklistener = new OnItemClickListener() {
        public void onItemClick(android.widget.AdapterView<?> parent,
                                View view, int position, long id) {
            OrderListInfo mcInfo = adapter.list.get(position);
            Intent orderlistIntent = new Intent(QueryOrdersActivity.this,
                    OrderListActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("mcId", mcInfo.getMcid());
            bundle.putSerializable("mcName", mcInfo.getMcName());
            bundle.putSerializable("taskStatus", taskStatus);
            orderlistIntent.putExtras(bundle);
            startActivity(orderlistIntent);
        }

        ;
    };
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            updateDisplay();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_orders);
        ButterKnife.bind(this);// 注解绑定控件框架
        mcInfos = new ArrayList<OrderListInfo>();
        taskStatus = (String) getIntent().getSerializableExtra("taskStatus");
        initView();
        setDateTime();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    /**
     * 查询订单列表
     */
    @OnClick(R.id.btn_query)
    void queryOrderList(View view) {
        String merchantId = edt_merchantId.getText().toString().trim();
        String merchantName = edt_merchantName.getText().toString().trim();
        String orderDate = edt_orderDate.getText().toString().trim();
        if (merchantId.length() > 15) {
            Toast.makeText(this, Notes.ERROR_ILLEGAL_ID, Toast.LENGTH_SHORT)
                    .show();
            return;
        }
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(Notes.LOADING_DATA);
        pDialog.setCancelable(false);
        pDialog.show();
        GetGroupMcInfos groupMcInfos = new GetGroupMcInfos(new IHttpURLs() {
            @Override
            public void despatch(Object o) {
            }

            @Override
            public void despatch(Object o, Object ob) {
                Message msg = new Message();
                msg.what = 10;
                msg.getData().putSerializable("list", (Serializable) o);
                handler.sendMessage(msg);
            }

            @Override
            public void handleErrorInfo(String err) {
                Toast.makeText(QueryOrdersActivity.this, err,
                        Toast.LENGTH_SHORT).show();
            }
        }, this);
        groupMcInfos.request(merchantId, merchantName, orderDate, taskStatus);
    }

    private void initView() {
        if (taskStatus.equals("1")) {
            txt_title.setText("待办列表");
        } else {
            txt_title.setText("办结列表");
        }
        lv_list_content = (ListView) findViewById(R.id.lv_list_content);
        adapter = new QueryOrdersAdapter(this, mcInfos);
        lv_list_content.setAdapter(adapter);
        lv_list_content.setOnItemClickListener(itemclicklistener);
    }

    private void setDateTime() {
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
    }

    // 更新日期文本控件
    protected void updateDisplay() {
        edt_orderDate.setText(new StringBuilder().append(mYear)
                .append((mMonth + 1) < 10 ? "0" + (mMonth + 1) : (mMonth + 1))
                .append((mDay < 10) ? "0" + mDay : mDay));
    }

    /**
     * 选择日期
     */
    @OnClick(R.id.btn_check_date)
    void checkDate(View view) {
        this.showDialog(1);
    }

    /*
     * 打开日期对话框(non-Javadoc)
     *
     * @see android.app.Activity#onCreateDialog(int)
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
    }

    public List<OrderListInfo> getMcInfos() {
        return mcInfos;
    }

    public void setMcInfos(List<OrderListInfo> mcInfos) {
        this.mcInfos = mcInfos;
    }

    /**
     * 后退
     */
    @OnClick(R.id.btn_back)
    void back(View view) {
        finish();
        overridePendingTransition(R.anim.slide_in_from_left,
                R.anim.slide_out_to_right);
    }

    @SuppressLint("HandlerLeak")
    private class McinfoListHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10: // 成功 - 获取配置信息
                    pDialog.cancel();
                    List<OrderListInfo> list = (List<OrderListInfo>) msg.getData().getSerializable("list");
                    int count = 0;
                    for (OrderListInfo info : list) {
                        count += info.getCount();
                    }
                    if (taskStatus.equals("1")) {
                        txt_title.setText("待办列表  数量:" + count);
                    } else {
                        txt_title.setText("办结列表  数量:" + count);
                    }
                    adapter.list = list;
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}