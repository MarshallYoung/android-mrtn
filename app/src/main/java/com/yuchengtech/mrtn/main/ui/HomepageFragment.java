package com.yuchengtech.mrtn.main.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.daoimpl.GetTaskConfigXml;
import com.yuchengtech.mrtn.main.adapter.FunctionsAdapter;
import com.yuchengtech.mrtn.main.bean.FunctionButtonInfo;
import com.yuchengtech.mrtn.merchant.ui.QueryMerchantActivity;
import com.yuchengtech.mrtn.order.ui.QueryOrdersActivity;
import com.yuchengtech.mrtn.predict.ui.QueryPredictActivity;
import com.yuchengtech.mrtn.scan.ui.ScanCodeActivity;
import com.yuchengtech.mrtn.utils.xml.FileService;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主页
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016年4月20日 下午5:56:46
 */
public class HomepageFragment extends Fragment implements OnItemClickListener {

    private View fragmentView;

    @Bind(R.id.btn_back)
    Button btn_back;
    @Bind(R.id.gv_function)
    public GridView gv_functions;

    String[] texts = {"信息查询", "扫码", "待办列表", "办结列表", "预计上门时间"};
    List<FunctionButtonInfo> functionList = new ArrayList<>();
    private FunctionsAdapter adapter;
    private HomeIndexHandler handler = new HomeIndexHandler();
    private int count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_homepage, container, false);
            ButterKnife.bind(this, fragmentView);
        }
        // 缓存Fragment,避免重新执行onCreateView
        ViewGroup parentView = (ViewGroup) fragmentView.getParent();
        if (parentView != null) {
            parentView.removeView(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initView();
        loadXmlConfig("task_mcinfo.xml");
        loadXmlConfig("task_001.xml");
        loadXmlConfig("task_002.xml");
        loadXmlConfig("task_003.xml");
        loadXmlConfig("task_004.xml");
        loadXmlConfig("task_006.xml");
        loadXmlConfig("task_007.xml");
        loadXmlConfig("task_008.xml");
        loadXmlConfig("task_009.xml");
        loadXmlConfig("task_010.xml");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void loadXmlConfig(String type) {
        GetTaskConfigXml getTaskConfigXml = new GetTaskConfigXml(
                new IHttpURLs() {
                    @Override
                    public void despatch(Object o) {

                    }

                    @Override
                    public void despatch(Object o, Object fileName) {
                        String fn = (String) fileName;
                        Message msg = new Message();
                        FileService fService = new FileService(getActivity());
                        byte[] buffer = o.toString().getBytes();
                        try {
                            fService.writeDateFile(fn, buffer);
                            msg.what = 10;
                            msg.getData().putSerializable("num", ++count);
                        } catch (Exception e) {
                            e.printStackTrace();
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

                }, getActivity(), type);
        getTaskConfigXml.request(type);
    }


    private void initData() {
        functionList.clear();
        FunctionButtonInfo mcinfoEntity = new FunctionButtonInfo();
        mcinfoEntity.id = FunctionButtonInfo.HOME_MCINFO;
        mcinfoEntity.imgIndex = R.drawable.icon_query_info;
        mcinfoEntity.name = "信息查询";
        functionList.add(mcinfoEntity);

        FunctionButtonInfo scanCodeEntity = new FunctionButtonInfo();
        scanCodeEntity.id = FunctionButtonInfo.HOME_SCAN_CODE;
        scanCodeEntity.imgIndex = R.drawable.icon_query_info;
        scanCodeEntity.name = "扫码";
        functionList.add(scanCodeEntity);

        FunctionButtonInfo todoEntity = new FunctionButtonInfo();
        todoEntity.id = FunctionButtonInfo.HOME_TODO_LIST;
        todoEntity.imgIndex = R.drawable.icon_backlog;
        todoEntity.name = "待办列表";
        functionList.add(todoEntity);

        FunctionButtonInfo completedEntity = new FunctionButtonInfo();
        completedEntity.id = FunctionButtonInfo.HOME_COMPLETED_LIST;
        completedEntity.imgIndex = R.drawable.icon_done_order;
        completedEntity.name = "办结列表";
        functionList.add(completedEntity);

        FunctionButtonInfo order_predict = new FunctionButtonInfo();
        order_predict.id = FunctionButtonInfo.HOME_PREDICT_TIME;
        order_predict.imgIndex = R.drawable.icon_predict;
        order_predict.name = "预计上门时间";
        functionList.add(order_predict);

        FunctionButtonInfo order_install = new FunctionButtonInfo();
        order_install.id = FunctionButtonInfo.HOME_ORDER_INSTALL;
        order_install.imgIndex = R.drawable.empty;
        order_install.name = "";
        functionList.add(order_install);

        for (int i = functionList.size(); i < 9; i++) {
            FunctionButtonInfo order_empty = new FunctionButtonInfo();
            order_empty.id = i;
            order_empty.imgIndex = 0;
            order_empty.name = "";
            functionList.add(order_empty);
        }
    }

    private void initView() {
        btn_back.setVisibility(View.GONE);
        adapter = new FunctionsAdapter(this, functionList);
        gv_functions.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        Jump2Type(arg2);
    }

    public void Jump2Type(int index) {
        switch (index) {
            case FunctionButtonInfo.HOME_MCINFO:// 商户列表查询
                Intent orderIntent = new Intent(getActivity(), QueryMerchantActivity.class);
                startActivity(orderIntent);
                break;
            case FunctionButtonInfo.HOME_SCAN_CODE:// 扫码
                Intent scanCodeIntent = new Intent(getActivity(), ScanCodeActivity.class);
                startActivity(scanCodeIntent);
                break;
            case FunctionButtonInfo.HOME_TODO_LIST:
                Intent todoIntent = new Intent(getActivity(), QueryOrdersActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("taskStatus", "1");// 待办
                todoIntent.putExtras(bundle);
                todoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(todoIntent);
                break;
            case FunctionButtonInfo.HOME_COMPLETED_LIST:
                Intent completedIntent = new Intent(getActivity(), QueryOrdersActivity.class);
                Bundle completedbundle = new Bundle();
                completedbundle.putSerializable("taskStatus", "2");// 办结
                completedIntent.putExtras(completedbundle);
                completedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(completedIntent);
                break;
            case FunctionButtonInfo.HOME_PREDICT_TIME: // 预计上门工单查询
                Intent order_predict = new Intent(getActivity(), QueryPredictActivity.class);
                Bundle bundle_predict = new Bundle();
                bundle_predict.putSerializable("type", "3");
                order_predict.putExtras(bundle_predict);
                startActivity(order_predict);
                break;
        }
    }

    @SuppressWarnings("unused")
    private class HomeIndexHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            switch (msg.what) {
                case 10: // 成功 - 获取配置信息
                    int num = msg.getData().getInt("num");
                    if (num == 8) {
                        Toast.makeText(getActivity(), "配置文件更新完成!", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case -1: // 失败 - 获取配置信息
                    String msgs = msg.getData().getString("err");
                    Toast.makeText(getActivity(), msgs, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}