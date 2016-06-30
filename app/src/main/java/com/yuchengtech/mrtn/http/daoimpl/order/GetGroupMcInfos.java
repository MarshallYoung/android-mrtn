package com.yuchengtech.mrtn.http.daoimpl.order;

import android.content.Context;
import android.util.Log;

import com.yuchengtech.mrtn.base.YXAPI;
import com.yuchengtech.mrtn.base.manager.UserManager;
import com.yuchengtech.mrtn.base.util.LogUtil;
import com.yuchengtech.mrtn.http.IHttpURLs;
import com.yuchengtech.mrtn.http.request.HttpConnectPost;
import com.yuchengtech.mrtn.http.request.IHttpListener;
import com.yuchengtech.mrtn.login.bean.ShiroUser;
import com.yuchengtech.mrtn.order.bean.OrderListInfo;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GetGroupMcInfos implements YXAPI, IHttpListener {
    private String TAG = "taskOrderService/getGroupMcInfo";
    private HttpConnectPost conn;
    private IHttpURLs listener;

    public GetGroupMcInfos(IHttpURLs listener, Context context) {
        super();
        this.listener = listener;
        if (conn == null) {
            conn = new HttpConnectPost(URL + TAG, this);
        }
    }

    public void request(String mcid, String mcname, String disptTime,
                        String taskStatus) {
        List<NameValuePair> listPairs = new ArrayList<NameValuePair>();
        if (mcid != null && !mcid.isEmpty()) {
            listPairs.add(new BasicNameValuePair("mcId", mcid));
        }
        if (mcname != null && !mcname.isEmpty()) {
            listPairs.add(new BasicNameValuePair("mcName", mcname));
        }
        if (disptTime != null && !disptTime.isEmpty()) {
            listPairs.add(new BasicNameValuePair("disptTime", disptTime));
        }
        if (taskStatus != null && !taskStatus.isEmpty()) {
            listPairs.add(new BasicNameValuePair("taskStatus", taskStatus));
        }
        ShiroUser user = UserManager.getInstance().getUserInfo();
        if (user != null) {
            listPairs.add(new BasicNameValuePair("instId", user.instId));
            if (user.deptDutyman != null && !user.deptDutyman.equals("1")) {
                listPairs.add(new BasicNameValuePair("UserId", user.userId));
            }
        }
        LogUtil.e("==查询工单参数==", "listPairs是:  " + listPairs);
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
            boolean success = obj.optBoolean("success");
            if (success) {
                JSONArray jsonArray = obj.getJSONArray("data");
                List<OrderListInfo> list = OrderListInfo
                        .constructArrayList(jsonArray);
                listener.despatch(list, list.size());
            } else {
                listener.handleErrorInfo("获取信息失败");
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
