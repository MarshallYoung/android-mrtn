package com.yuchengtech.mrtn.merchant.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.activity.order.McTermListActivity;
import com.yuchengtech.mrtn.base.ui.BaseActivity;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.daoimpl.order.GetMcInfo;
import com.yuchengtech.mrtn.http.daoimpl.order.GetMcList;
import com.yuchengtech.mrtn.merchant.adapter.QueryMerchantAdapter;
import com.yuchengtech.mrtn.merchant.bean.MerchantInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/*
 * 商户查询列表
 */
public class QueryMerchantActivity extends BaseActivity {

    interface Notes {
        String LOAD_DATA = "加载数据...";
        String ERROR_ILLEGAL_ID = "商户编号不能大于15位";
        String NONE_TERMIANL_DATA = "无终端数据";
    }

    SweetAlertDialog pDialog;

    @Bind(R.id.btn_back)
    Button btn_back;// 后退
    @Bind(R.id.txt_title)
    TextView txt_title;// 标题
    @Bind(R.id.txt_area_mc_id)
    TextView txt_area_mc_id;
    @Bind(R.id.txt_area_mc_name)
    TextView txt_area_mc_name;
    @Bind(R.id.lv_list_content)
    ListView lv_list_content;
    private List<MerchantInfo> mcInfos;
    private QueryMerchantAdapter adapter;
    private McinfoListHandler handler = new McinfoListHandler();
    private String taskType = "";
    OnItemClickListener itemclicklistener = new OnItemClickListener() {
        public void onItemClick(android.widget.AdapterView<?> parent,
                                View view, int position, long id) {
            MerchantInfo mcInfo = adapter.getList().get(position);
            if (taskType != null && !taskType.isEmpty()) {
                getMcInfoData(mcInfo.getId());
            } else {
                Intent mcinfoIntent = new Intent(QueryMerchantActivity.this,
                        MerchantInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("id", mcInfo.getId());
                mcinfoIntent.putExtras(bundle);
                startActivity(mcinfoIntent);
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_merchant);
        ButterKnife.bind(this);
        taskType = (String) getIntent().getSerializableExtra("taskType");
        mcInfos = new ArrayList<MerchantInfo>();
        initView();
    }

    private void initView() {
        btn_back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_from_left,
                        R.anim.slide_out_to_right);
            }
        });
        txt_title.setText("商户查询列表");
        adapter = new QueryMerchantAdapter(this, mcInfos);
        lv_list_content.setAdapter(adapter);
        lv_list_content.setOnItemClickListener(itemclicklistener);
    }

    private void initData() {
        String mc_id = txt_area_mc_id.getText().toString();
        if (mc_id.length() > 15) {
            Toast.makeText(QueryMerchantActivity.this, Notes.ERROR_ILLEGAL_ID, Toast.LENGTH_SHORT).show();
            return;
        }
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText(Notes.LOAD_DATA);
        pDialog.setCancelable(false);
        pDialog.show();
        GetMcList mclist = new GetMcList(new IHttpURLs() {
            @Override
            public void despatch(Object o) {
            }

            @Override
            public void despatch(Object o, Object ob) {
                Message msg = new Message();
                Integer count = (Integer) ob;
                if (count > 0) {
                    msg.what = 10;
                    msg.getData().putSerializable("list", (Serializable) o);
                    handler.sendMessage(msg);
                } else {
                    msg.what = -1;
                    msg.getData().putString("err", "没有最新的数据!");
                    handler.sendMessage(msg);
                }

            }

            @Override
            public void handleErrorInfo(String err) {
                // TODO Auto-generated method stub
                Message msg = new Message();
                msg.what = -1;
                msg.getData().putString("err", err);
                handler.sendMessage(msg);
            }
        }, this);
        mclist.request(txt_area_mc_id.getText().toString(), txt_area_mc_name
                .getText().toString());
    }

    public void onClick_btn_search_two(View view) {
        initData();
    }

    private void getMcInfoData(Long id) {
        GetMcInfo mcinfo = new GetMcInfo(new IHttpURLs() {
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
                msg.getData().putSerializable("mcinfo", (Serializable) o);
                handler.sendMessage(msg);
            }
        });
        mcinfo.request(id);
    }

    public List<MerchantInfo> getMcInfos() {
        return mcInfos;
    }

    public void setMcInfos(List<MerchantInfo> mcInfos) {
        this.mcInfos = mcInfos;
    }

    @SuppressLint("HandlerLeak")
    private class McinfoListHandler extends Handler {
        @SuppressWarnings({"unchecked", "deprecation"})
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10: // 成功 - 获取配置信息
                    pDialog.cancel();
                    List<MerchantInfo> list = (List<MerchantInfo>) msg.getData()
                            .getSerializable("list");
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                    break;
                case 20:
                    showDialog(1);
                    break;
                case 30: // 成功 - 获取单个商户相关信息
                    MerchantInfo mcinfo = (MerchantInfo) msg.getData()
                            .getSerializable("mcinfo");
                    if (mcinfo.getTerms() == null || mcinfo.getTerms().size() == 0) {
                        Toast.makeText(QueryMerchantActivity.this, Notes.NONE_TERMIANL_DATA, Toast.LENGTH_SHORT).show();
                    } else {
                        Intent terms = new Intent(QueryMerchantActivity.this,
                                McTermListActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("Terms",
                                (Serializable) mcinfo.getTerms());
                        bundle.putSerializable("taskType", taskType);
                        bundle.putSerializable("taskStatus", "0");
                        terms.putExtras(bundle);
                        startActivity(terms);
                    }
                    break;
                case -1: // 失败 - 获取配置信息
                    pDialog.cancel();
                    String msgs = msg.getData().getString("err");
                    Toast.makeText(QueryMerchantActivity.this, msgs, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}