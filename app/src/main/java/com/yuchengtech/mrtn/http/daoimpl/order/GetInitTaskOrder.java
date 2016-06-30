package com.yuchengtech.mrtn.http.daoimpl.order;

import android.util.Log;

import com.google.gson.Gson;
import com.yuchengtech.mrtn.base.YXAPI;
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

public class GetInitTaskOrder implements YXAPI, IHttpListener {
    private String TAG = "taskOrder/InitTaskOrder";
    private HttpConnectPost conn;
    private IHttpURLs listener;

    public GetInitTaskOrder(IHttpURLs listener) {
        super();
        this.listener = listener;
        if (conn == null) {
            conn = new HttpConnectPost(URL + TAG, this);
        }
    }

    public void request(String mcId, String termId) {
        List<NameValuePair> listPairs = new ArrayList<NameValuePair>();
        if (mcId != null && !mcId.isEmpty()) {
            listPairs.add(new BasicNameValuePair("mcId", mcId));
        }
        if (termId != null && !termId.isEmpty()) {
            listPairs.add(new BasicNameValuePair("termId", termId));
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
        try {
            String temp = in;// Tools.seekSep(in);
            if (temp == null || temp.equals("")) {
                listener.handleErrorInfo("数据解析异常");
                return;
            }
            Log.d(TAG, temp);
            JSONObject obj = new JSONObject(temp);
            String success = obj.optString("success");
            if (success.equals(SUCCES_CODE)) {
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
