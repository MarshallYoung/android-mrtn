package com.yuchengtech.mrtn.http.daoimpl.order;

import com.google.gson.Gson;
import com.yuchengtech.mrtn.base.YXAPI;
import com.yuchengtech.mrtn.base.util.LogUtil;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.request.HttpConnectPost;
import com.yuchengtech.mrtn.http.request.IHttpListener;
import com.yuchengtech.mrtn.order.bean.OrderInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GetTaskOrder implements YXAPI, IHttpListener {
    private String TAG = "taskOrder/queryOne";
    private HttpConnectPost conn;
    private IHttpURLs listener;

    public GetTaskOrder(IHttpURLs listener) {
        super();
        this.listener = listener;
        if (conn == null) {
            conn = new HttpConnectPost(URL + TAG, this);
        }
    }

    public void request(String taskId) {
        List<NameValuePair> listPairs = new ArrayList<NameValuePair>();
        if (taskId != null && !taskId.isEmpty()) {
            listPairs.add(new BasicNameValuePair("id", taskId));
            LogUtil.e("查询工单详情", "id=" + taskId);
        }
        conn.setParams(listPairs);
        conn.start();
    }

    @Override
    public void handleError(String err) throws IOException {
        listener.handleErrorInfo(err);
    }

    @Override
    public void decode(String in) throws IOException {
        unmashal(in);
    }

    private void unmashal(String in) {
        // LogUtil.e("查询工单详情", "服务器返回的数据是= " + in);
        try {
            String temp = in;// Tools.seekSep(in);
            if (temp == null || temp.equals("")) {
                listener.handleErrorInfo("数据解析异常");
                return;
            }
            LogUtil.e("查询工单详情", "数据解析正常");
            JSONObject obj = new JSONObject(temp);
            boolean success = obj.optBoolean("success");
            if (success) {
                Gson gson = new Gson();
                OrderInfo order = gson.fromJson(obj.getString("data"),
                        OrderInfo.class);
                listener.despatch(order);
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
        // TODO Auto-generated method stub

    }

}
