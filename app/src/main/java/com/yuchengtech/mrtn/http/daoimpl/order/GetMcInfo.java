package com.yuchengtech.mrtn.http.daoimpl.order;

import android.util.Log;

import com.google.gson.Gson;
import com.yuchengtech.mrtn.base.YXAPI;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.request.HttpConnectPost;
import com.yuchengtech.mrtn.http.request.IHttpListener;
import com.yuchengtech.mrtn.merchant.bean.MerchantInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GetMcInfo implements YXAPI, IHttpListener {
    private String TAG = "mcinfo/queryOne";
    private HttpConnectPost conn;
    private IHttpURLs listener;

    public GetMcInfo(IHttpURLs listener) {
        // TODO Auto-generated constructor stub
        super();
        this.listener = listener;
        if (conn == null) {
            conn = new HttpConnectPost(URL + TAG, this);
        }
    }

    public void request(Long id) {
        // TODO Auto-generated method stub
        List<NameValuePair> listPairs = new ArrayList<NameValuePair>();
        if (id != null && id > 0) {
            listPairs.add(new BasicNameValuePair("id", id.toString()));
        }
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
                String jsonStr = obj.getString("data");
                Gson gson = new Gson();
                MerchantInfo mcInfo = gson
                        .fromJson(jsonStr, MerchantInfo.class);
                listener.despatch(mcInfo);
            } else {
                listener.handleErrorInfo("获取商户信息失败");
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
