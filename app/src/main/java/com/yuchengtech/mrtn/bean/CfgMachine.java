package com.yuchengtech.mrtn.bean;

import java.io.Serializable;

public class CfgMachine implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private long id;

    private String elongsTo;

    private String hostSerialNo;

    private String inoutType;

    private String instId;

    private String locked;

    private String mhId;

    private String mhModel;

    private String mhStatus;

    private String mhType;

    private String opName;

    private String partnerId;
    private String mhFactory;

    private String mhLat;

    private String mhLong;

    private String mhAddr;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getElongsTo() {
        return elongsTo;
    }

    public void setElongsTo(String elongsTo) {
        this.elongsTo = elongsTo;
    }

    public String getHostSerialNo() {
        return hostSerialNo;
    }

    public void setHostSerialNo(String hostSerialNo) {
        this.hostSerialNo = hostSerialNo;
    }

    public String getInoutType() {
        return inoutType;
    }

    public void setInoutType(String inoutType) {
        this.inoutType = inoutType;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getMhId() {
        return mhId;
    }

    public void setMhId(String mhId) {
        this.mhId = mhId;
    }

    public String getMhModel() {
        return mhModel;
    }

    public void setMhModel(String mhModel) {
        this.mhModel = mhModel;
    }

    public String getMhStatus() {
        return mhStatus;
    }

    public void setMhStatus(String mhStatus) {
        this.mhStatus = mhStatus;
    }

    public String getMhType() {
        return mhType;
    }

    public void setMhType(String mhType) {
        this.mhType = mhType;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getMhFactory() {
        return mhFactory;
    }

    public void setMhFactory(String mhFactory) {
        this.mhFactory = mhFactory;
    }

    public String getMhLat() {
        return mhLat;
    }

    public void setMhLat(String mhLat) {
        this.mhLat = mhLat;
    }

    public String getMhLong() {
        return mhLong;
    }

    public void setMhLong(String mhLong) {
        this.mhLong = mhLong;
    }

    public String getMhAddr() {
        return mhAddr;
    }

    public void setMhAddr(String mhAddr) {
        this.mhAddr = mhAddr;
    }

}