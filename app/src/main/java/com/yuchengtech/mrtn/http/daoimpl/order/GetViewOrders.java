package com.yuchengtech.mrtn.http.daoimpl.order;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuchengtech.mrtn.base.YXAPI;
import com.yuchengtech.mrtn.base.util.LogUtil;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.request.HttpConnectPost;
import com.yuchengtech.mrtn.http.request.IHttpListener;
import com.yuchengtech.mrtn.order.bean.OrderInfo;
import com.yuchengtech.mrtn.order.ui.OrderListActivity;
import com.yuchengtech.mrtn.predict.ui.QueryPredictActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GetViewOrders implements YXAPI, IHttpListener {
    private String TAG = "taskOrderService/getOrderList";
    private HttpConnectPost conn;
    private IHttpURLs listener;

    public GetViewOrders(IHttpURLs listener, OrderListActivity orderListActivity) {
        super();
        this.listener = listener;
        if (conn == null) {
            conn = new HttpConnectPost(URL + TAG, this);
        }
    }

    public GetViewOrders(IHttpURLs listener,
                         QueryPredictActivity orderPredictListActivity) {
        super();
        this.listener = listener;
        if (conn == null) {
            conn = new HttpConnectPost(URL + TAG, this);
        }
    }

    public void request(String mcId, String taskStatus, String mcName,
                        String disptTime, String type) {
        // TODO type 1 待办任务单列表 2 办结任务单列表
        List<NameValuePair> listPairs = new ArrayList<NameValuePair>();
        if (mcId != null && !mcId.isEmpty()) {
            listPairs.add(new BasicNameValuePair("mcId", mcId));
        }
        if (taskStatus != null && !taskStatus.isEmpty()) {
            listPairs.add(new BasicNameValuePair("taskStatus", taskStatus));
        }
        if (mcName != null && !mcName.isEmpty()) {
            listPairs.add(new BasicNameValuePair("mcName", mcName));
        }
        // if (type != null && !type.isEmpty()) {
        // listPairs.add(new BasicNameValuePair("type", type));
        // }
        if (disptTime != null && !disptTime.isEmpty()) {
            listPairs.add(new BasicNameValuePair("disptTime", disptTime));
        }
        // ShiroUser user = UserManager.getInstance().getUserInfo();
        // if (user != null) {
        // listPairs.add(new BasicNameValuePair("instId", user.instId));
        // if (user.deptDutyman != null && !user.deptDutyman.equals("1")) {
        // listPairs.add(new BasicNameValuePair("UserId", user.userId));
        // }
        // }
        conn.setParams(listPairs);
        conn.start();
    }

    @Override
    public void handleError(String err) throws IOException {
        // TODO Auto-generated method stub
        listener.handleErrorInfo(err);
    }

    @Override
    public void decode(String in) throws IOException {
        // TODO Auto-generated method stub
        unmashal(in);
    }

    private void unmashal(String in) {
        // TODO Auto-generated method stub
        try {
            String temp = in;// Tools.seekSep(in);
            if (temp == null || temp.equals("")) {
                listener.handleErrorInfo("数据解析异常");
                return;
            }
            Log.d(TAG, temp);
            JSONObject obj = new JSONObject(temp);
            boolean success = obj.optBoolean("success");
            if (success) {
                String json_list = obj.getString("data");
                LogUtil.e("查询工单,服务器返回", json_list);
                Gson gson = new Gson();
                List<OrderInfo> list = gson.fromJson(json_list,
                        new TypeToken<List<OrderInfo>>() {
                        }.getType());
                listener.despatch(list, list.size());
            } else {
                listener.handleErrorInfo("获取任务单信息失败");
            }
        } catch (Exception e) {
            listener.handleErrorInfo(DATA_MODO_ERR);
            e.printStackTrace();
        }
    }

    @Override
    public void decode(InputStream in) throws IOException {

    }

}
