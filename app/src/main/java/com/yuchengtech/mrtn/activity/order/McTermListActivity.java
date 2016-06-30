package com.yuchengtech.mrtn.activity.order;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.base.ui.BaseActivity;
import com.yuchengtech.mrtn.merchant.bean.TerminalInfo;
import com.yuchengtech.mrtn.order.ui.OrderInfoActivity;

import java.util.ArrayList;
import java.util.List;

public class McTermListActivity extends BaseActivity implements
        OnItemClickListener, OnClickListener {
    private Button btn_back;
    private TextView txt_title;
    private ListView lv_content;
    private SearchTermListAdapter adapter;
    private List<TerminalInfo> list = new ArrayList<TerminalInfo>();
    private String taskStatus;
    private String taskType;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mrtn_atv_order_list_search_data);
        taskStatus = (String) getIntent().getSerializableExtra("taskStatus");
        list = (List<TerminalInfo>) getIntent().getSerializableExtra("Terms");
        taskType = (String) getIntent().getSerializableExtra("taskType");
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initView() {
        btn_back = (Button) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);
        txt_title = (TextView) findViewById(R.id.txt_title);
        txt_title.setText("终端列表");
        adapter = new SearchTermListAdapter(this, list);
        lv_content = (ListView) findViewById(R.id.lv_list_content);
        lv_content.setAdapter(adapter);
        lv_content.setOnItemClickListener(this);
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        TerminalInfo term = adapter.getList().get(position);
        Intent termIntent = new Intent(McTermListActivity.this,
                OrderInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("mcId", term.getMcId());
        bundle.putSerializable("mcTermId", term.getTermId());
        bundle.putSerializable("taskType", taskType);
        bundle.putSerializable("taskStatus", taskStatus);
        termIntent.putExtras(bundle);
        startActivity(termIntent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
        }

    }

}
