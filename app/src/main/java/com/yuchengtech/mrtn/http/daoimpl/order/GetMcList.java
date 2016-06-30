package com.yuchengtech.mrtn.http.daoimpl.order;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

public class GetMcList implements YXAPI, IHttpListener {
    private String TAG = "mcinfo/query";
    private HttpConnectPost conn;
    private IHttpURLs listener;

    public GetMcList(IHttpURLs listener, Context context) {
        super();
        this.listener = listener;
        if (conn == null) {
            conn = new HttpConnectPost(URL + TAG, this);
        }
    }

    public void request(String mcid, String mcname) {
        List<NameValuePair> listPairs = new ArrayList<NameValuePair>();
        listPairs.add(new BasicNameValuePair("currentPageNum", "1"));
        listPairs.add(new BasicNameValuePair("pageSize", "10"));
        if (mcid != null && !mcid.isEmpty()) {
            listPairs.add(new BasicNameValuePair("mcId", mcid));
        }
        if (mcname != null && !mcname.isEmpty()) {
            listPairs.add(new BasicNameValuePair("mcName", mcname));
        }
        /*
		 * Users user = UserManager.getInstance().getUserInfo(); if
		 * (user.getIsComp().equals("0")) { listPairs .add(new
		 * BasicNameValuePair("instids", user.getInstId())); } else {
		 * listPairs.add(new BasicNameValuePair("emp_no", user.getLoginName()));
		 * }
		 */
        conn.setParams(listPairs);
        conn.start();
    }

    @Override
    public void handleError(String err) throws IOException {
        listener.handleErrorInfo(err);
    }

    @Override
    public void decode(String in) throws IOException {
        Log.e("==MC LIST==", in);
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
            boolean success = obj.optBoolean("success");
            if (success) {
                String json_list = obj.getString("data");
                Gson gson = new Gson();
                List<MerchantInfo> list = gson.fromJson(json_list,
                        new TypeToken<List<MerchantInfo>>() {
                        }.getType());
                listener.despatch(list, list.size());
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
