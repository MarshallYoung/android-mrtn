package com.yuchengtech.mrtn.http.daoimpl;

import android.util.Log;

import com.yuchengtech.mrtn.base.YXAPI;
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
import java.util.Map;

public class PostMachine implements YXAPI, IHttpListener {
    private String TAG = "machine/scanCodeUpdate";
    private HttpConnectPost conn;
    private IHttpURLs listener;

    public PostMachine(IHttpURLs listener) {
        super();
        this.listener = listener;
        if (conn == null) {
            conn = new HttpConnectPost(URL + TAG, this);
        }
    }

    public void request(Map<String, String> fields) {
        List<NameValuePair> listPairs = new ArrayList<NameValuePair>();
        for (String key : fields.keySet()) {
            listPairs.add(new BasicNameValuePair(key, fields.get(key)));
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
            boolean success = obj.getBoolean("success");
            if (success) {
                listener.despatch("关联成功!");
            } else {
                String msg = obj.getString("msg");
                listener.handleErrorInfo(msg == null || msg.isEmpty() ? "关联失败"
                        : msg);
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
