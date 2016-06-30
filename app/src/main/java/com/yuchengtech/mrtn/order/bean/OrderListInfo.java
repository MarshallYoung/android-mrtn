package com.yuchengtech.mrtn.order.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 商户工单列表概览信息
 */
public class OrderListInfo implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String mcid;
    private String mcName;
    private int type001;
    private int type002;
    private int type003;
    private int type004;
    private int type005;
    private int type006;
    private int type007;
    private int type008;
    private int type009;
    private int type010;
    private int count;

    public OrderListInfo() {
        // TODO Auto-generated constructor stub
    }

    public OrderListInfo(JSONObject jsonObject) {
        // TODO Auto-generated constructor stub
        mcid = jsonObject.optString("mcid");
        mcName = jsonObject.optString("mcName");
        type001 = jsonObject.optInt("type001");
        type002 = jsonObject.optInt("type002");
        type003 = jsonObject.optInt("type003");
        type004 = jsonObject.optInt("type004");
        type005 = jsonObject.optInt("type005");
        type006 = jsonObject.optInt("type006");
        type007 = jsonObject.optInt("type007");
        type008 = jsonObject.optInt("type008");
        type009 = jsonObject.optInt("type009");
        type010 = jsonObject.optInt("type010");
        count = jsonObject.optInt("count");
    }

    public static List<OrderListInfo> constructArrayList(JSONArray jsonArray) {
        // TODO Auto-generated method stub
        try {
            int size = jsonArray.length();
            List<OrderListInfo> typeList = new ArrayList<OrderListInfo>(size);
            for (int i = 0; i < size; i++) {
                typeList.add(new OrderListInfo(jsonArray.getJSONObject(i)));
            }
            return typeList;
        } catch (JSONException je) {
            je.printStackTrace();
        }
        return null;
    }

    public String getMcid() {
        return mcid;
    }

    public void setMcid(String mcid) {
        this.mcid = mcid;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }

    public int getType001() {
        return type001;
    }

    public void setType001(int type001) {
        this.type001 = type001;
    }

    public int getType002() {
        return type002;
    }

    public void setType002(int type002) {
        this.type002 = type002;
    }

    public int getType003() {
        return type003;
    }

    public void setType003(int type003) {
        this.type003 = type003;
    }

    public int getType004() {
        return type004;
    }

    public void setType004(int type004) {
        this.type004 = type004;
    }

    public int getType005() {
        return type005;
    }

    public void setType005(int type005) {
        this.type005 = type005;
    }

    public int getType006() {
        return type006;
    }

    public void setType006(int type006) {
        this.type006 = type006;
    }

    public int getType007() {
        return type007;
    }

    public void setType007(int type007) {
        this.type007 = type007;
    }

    public int getType009() {
        return type009;
    }

    public void setType009(int type009) {
        this.type009 = type009;
    }

    public int getType010() {
        return type010;
    }

    public void setType010(int type010) {
        this.type010 = type010;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getType008() {
        return type008;
    }

    public void setType008(int type008) {
        this.type008 = type008;
    }

}
