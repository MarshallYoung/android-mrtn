package com.yuchengtech.mrtn.main.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.yuchengtech.mrtn.R;
import com.yuchengtech.mrtn.base.MrtnApplication;
import com.yuchengtech.mrtn.base.manager.NetworkManager;
import com.yuchengtech.mrtn.base.util.LogUtil;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.daoimpl.GetTaskConfigXml;
import com.yuchengtech.mrtn.main.adapter.FunctionAdapter;
import com.yuchengtech.mrtn.main.adapter.FunctionItem;
import com.yuchengtech.mrtn.merchant.ui.QueryMerchantActivity;
import com.yuchengtech.mrtn.order.ui.QueryOrdersActivity;
import com.yuchengtech.mrtn.predict.ui.QueryPredictActivity;
import com.yuchengtech.mrtn.scan.ui.ScanCodeActivity;
import com.yuchengtech.mrtn.utils.xml.FileService;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * 主页
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @date 2016年4月20日 下午5:56:46
 */
public class HomepageFragment extends Fragment {

    private static final String TAG = "HomepageFragment";
    private View fragmentView;

    @Bind(R.id.btn_back)
    Button btn_back;
    @Bind(R.id.gv_function)
    public GridView gv_function;
    private SweetAlertDialog dialog;

    int[] images = {R.drawable.icon_query_info, R.drawable.icon_query_info,
            R.drawable.icon_backlog, R.drawable.icon_done_order,
            R.drawable.icon_predict, R.drawable.empty};
    String[] texts = {"信息查询", "扫码", "待办列表", "办结列表", "预计上门时间", ""};
    private HomeIndexHandler handler = new HomeIndexHandler();
    private int count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_homepage, container, false);
            ButterKnife.bind(this, fragmentView);
            initView();
        }
        // 缓存Fragment,避免重新执行onCreateView
        ViewGroup parentView = (ViewGroup) fragmentView.getParent();
        if (parentView != null) {
            parentView.removeView(fragmentView);
        }
        return fragmentView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    private void downloadXml(String fileName, boolean isEnded) {
        final String fn = fileName;
        final boolean isEnd = isEnded;
        MrtnApplication.networkManager.downloadXml(fileName, new NetworkManager.NetworkListener() {
            @Override
            public void onSuccess(Object response) {
                LogUtil.e(TAG, fn + ":  " + response);
                FileService fService = new FileService(getActivity());
                byte[] buffer = ((String) response).getBytes();
                try {
                    fService.writeDateFile(fn, buffer);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "解析出错", Toast.LENGTH_SHORT).show();
                }
                if (isEnd) {
                    dismissDialog();
                }
            }

            @Override
            public void onFail(VolleyError error) {
                if (isEnd) {
                    dismissDialog();
                }
                Toast.makeText(getActivity(), "连接服务器出错", Toast.LENGTH_SHORT).show();
            }
        });
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

    private void initView() {
        btn_back.setVisibility(View.GONE);
        dialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setCancelable(false);
        dialog.setTitleText("初始化...").show();
        ArrayList itemList = new ArrayList();
        for (int i = 0; i < texts.length; i++) {
            FunctionItem item = new FunctionItem();
            item.image = images[i];
            item.text = texts[i];
            itemList.add(item);
        }
        FunctionAdapter adapter = new FunctionAdapter(getActivity(), itemList);
        gv_function.setAdapter(adapter);
        gv_function.setOnItemClickListener(itemClickListener);
        // 初始化xml
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
//        downloadXml("task_mcinfo.xml", false);
//        downloadXml("task_001.xml", false);
//        downloadXml("task_002.xml", false);
//        downloadXml("task_003.xml", false);
//        downloadXml("task_004.xml", false);
//        downloadXml("task_006.xml", false);
//        downloadXml("task_007.xml", false);
//        downloadXml("task_008.xml", false);
//        downloadXml("task_009.xml", false);
//        downloadXml("task_010.xml", true);
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
                        dismissDialog();
                    }
                    break;
                case -1: // 失败 - 获取配置信息
                    String msgs = msg.getData().getString("err");
                    Toast.makeText(getActivity(), msgs, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /**
     * 点击功能列表执行的方法
     */
    private AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            switch (position) {
                case 0:// 商户列表查询
                    Intent orderIntent = new Intent(getActivity(), QueryMerchantActivity.class);
                    startActivity(orderIntent);
                    break;
                case 1:// 扫码
                    Intent scanCodeIntent = new Intent(getActivity(), ScanCodeActivity.class);
                    startActivity(scanCodeIntent);
                    break;
                case 2:// 待办
                    Intent todoIntent = new Intent(getActivity(), QueryOrdersActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("taskStatus", "1");// 待办
                    todoIntent.putExtras(bundle);
                    todoIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(todoIntent);
                    break;
                case 3:// 办结
                    Intent completedIntent = new Intent(getActivity(), QueryOrdersActivity.class);
                    Bundle completedbundle = new Bundle();
                    completedbundle.putSerializable("taskStatus", "2");// 办结
                    completedIntent.putExtras(completedbundle);
                    completedIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(completedIntent);
                    break;
                case 4:// 预计
                    Intent order_predict = new Intent(getActivity(), QueryPredictActivity.class);
                    Bundle bundle_predict = new Bundle();
                    bundle_predict.putSerializable("type", "3");
                    order_predict.putExtras(bundle_predict);
                    startActivity(order_predict);
                    break;
            }
        }
    };

    private void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.cancel();
        }
    }
}