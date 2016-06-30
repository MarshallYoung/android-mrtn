package com.yuchengtech.mrtn.http.daoimpl;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yuchengtech.mrtn.base.YXAPI;
import com.yuchengtech.mrtn.bean.CfgMachine;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.request.HttpConnectPost;
import com.yuchengtech.mrtn.http.request.IHttpListener;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GetMachineBySerialNo implements YXAPI, IHttpListener {

    private String TAG = "machine/queryByHostSerialNo";
    private HttpConnectPost conn;
    private IHttpURLs listener;

    public GetMachineBySerialNo(IHttpURLs listener, Context context) {
        super();

        this.listener = listener;
        if (conn == null) {
            conn = new HttpConnectPost(URL + TAG, this);
        }
    }

    public void request(String serialNo) {
        List<NameValuePair> listPairs = new ArrayList<NameValuePair>();
        listPairs.add(new BasicNameValuePair("hostSerialNo", serialNo));

        conn.setParams(listPairs);
        conn.start();
    }

    public void handleError(String err) throws IOException {
        listener.handleErrorInfo(err);
    }

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
            boolean success = obj.getBoolean("success");
            if (success) {
                Gson gson = new Gson();
                CfgMachine machine = gson.fromJson(obj.getString("data"),
                        new TypeToken<CfgMachine>() {
                        }.getType());
                listener.despatch(machine);
            } else {
                String msg = obj.optString("msg");
                listener.handleErrorInfo(msg);
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