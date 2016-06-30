package com.yuchengtech.mrtn.bean;

import org.json.JSONObject;

/**
 * UsrInst entity. @author MyEclipse Persistence Tools
 */

public class Inst implements java.io.Serializable {

    // Fields

    private String instId;
    private String instName;
    private String instAddr;
    private String instPostcode;
    private String instDutyman;
    private String instTel;
    private String instParentId;
    private String instLevel;
    private String instStatus;
    private String instDesc;
    private String cidLevel;
    // ----
    private String instParentName;

    // Constructors

    /**
     * default constructor
     */
    public Inst() {
    }

    /**
     * minimal constructor
     */
    public Inst(String instId) {
        this.instId = instId;
    }

    /**
     * minimal constructor
     */
    public Inst(String instId, String instName, String instLevel) {
        this.instId = instId;
        this.instName = instName;
        this.instLevel = instLevel;
    }

    /**
     * full constructor
     */
    public Inst(String instId, String instName, String instAddr,
                String instPostcode, String instDutyman, String instTel,
                String instParentId, String instLevel, String instStatus,
                String instDesc) {
        this.instId = instId;
        this.instName = instName;
        this.instAddr = instAddr;
        this.instPostcode = instPostcode;
        this.instDutyman = instDutyman;
        this.instTel = instTel;
        this.instParentId = instParentId;
        this.instLevel = instLevel;
        this.instStatus = instStatus;
        this.instDesc = instDesc;
    }

    // Property accessors

    public Inst(JSONObject jsonObject) {
        this.instId = jsonObject.optString("instId");
        this.instName = jsonObject.optString("instName");
        this.instAddr = jsonObject.optString("instAddr");
        this.instPostcode = jsonObject.optString("instPostcode");
        this.instDutyman = jsonObject.optString("instDutyman");
        this.instTel = jsonObject.optString("instTel");
        this.instParentId = jsonObject.optString("instParentId");
        this.instLevel = jsonObject.optString("instLevel");
        this.instStatus = jsonObject.optString("instStatus");
        this.instDesc = jsonObject.optString("instDesc");
    }

    public static Inst construct(JSONObject jsonObject) {
        // TODO Auto-generated method stub
        try {
            return new Inst(jsonObject);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    public String getInstId() {
        return this.instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getInstName() {
        return this.instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getInstAddr() {
        return this.instAddr;
    }

    public void setInstAddr(String instAddr) {
        this.instAddr = instAddr;
    }

    public String getInstPostcode() {
        return this.instPostcode;
    }

    public void setInstPostcode(String instPostcode) {
        this.instPostcode = instPostcode;
    }

    public String getInstDutyman() {
        return this.instDutyman;
    }

    public void setInstDutyman(String instDutyman) {
        this.instDutyman = instDutyman;
    }

    public String getInstTel() {
        return this.instTel;
    }

    public void setInstTel(String instTel) {
        this.instTel = instTel;
    }

    public String getInstParentId() {
        return this.instParentId;
    }

    public void setInstParentId(String instParentId) {
        this.instParentId = instParentId;
    }

    public String getInstLevel() {
        return this.instLevel;
    }

    public void setInstLevel(String instLevel) {
        this.instLevel = instLevel;
    }

    public String getInstStatus() {
        return this.instStatus;
    }

    public void setInstStatus(String instStatus) {
        this.instStatus = instStatus;
    }

    public String getInstDesc() {
        return this.instDesc;
    }

    public void setInstDesc(String instDesc) {
        this.instDesc = instDesc;
    }

    public String getInstParentName() {
        return instParentName;
    }

    public void setInstParentName(String instParentName) {
        this.instParentName = instParentName;
    }

    public String getCidLevel() {
        return cidLevel;
    }

    public void setCidLevel(String cidLevel) {
        this.cidLevel = cidLevel;
    }
}