package com.yuchengtech.mrtn.bean;

public class TaskVisitDetail implements java.io.Serializable, Cloneable {

    private String taskId;
    private String externalChange;
    private String internalChange;
    private String managementChange;
    private String managementName;
    private String managementTel;
    private String otherRisk;
    private String personQualityChange;
    private String posIsAddr;
    private String posNewAddr;
    private String posPositionChange;
    private String result;
    private String statusChange;
    private String stockException;
    private String turnoverAvg;
    private String visitDate;
    private String visitLastDate;
    private String newAddr;
    private String newLat;
    private String newLong;

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getExternalChange() {
        return externalChange;
    }

    public void setExternalChange(String externalChange) {
        this.externalChange = externalChange;
    }

    public String getInternalChange() {
        return internalChange;
    }

    public void setInternalChange(String internalChange) {
        this.internalChange = internalChange;
    }

    public String getManagementChange() {
        return managementChange;
    }

    public void setManagementChange(String managementChange) {
        this.managementChange = managementChange;
    }

    public String getManagementName() {
        return managementName;
    }

    public void setManagementName(String managementName) {
        this.managementName = managementName;
    }

    public String getManagementTel() {
        return managementTel;
    }

    public void setManagementTel(String managementTel) {
        this.managementTel = managementTel;
    }

    public String getOtherRisk() {
        return otherRisk;
    }

    public void setOtherRisk(String otherRisk) {
        this.otherRisk = otherRisk;
    }

    public String getPersonQualityChange() {
        return personQualityChange;
    }

    public void setPersonQualityChange(String personQualityChange) {
        this.personQualityChange = personQualityChange;
    }

    public String getPosIsAddr() {
        return posIsAddr;
    }

    public void setPosIsAddr(String posIsAddr) {
        this.posIsAddr = posIsAddr;
    }

    public String getPosNewAddr() {
        return posNewAddr;
    }

    public void setPosNewAddr(String posNewAddr) {
        this.posNewAddr = posNewAddr;
    }

    public String getPosPositionChange() {
        return posPositionChange;
    }

    public void setPosPositionChange(String posPositionChange) {
        this.posPositionChange = posPositionChange;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatusChange() {
        return statusChange;
    }

    public void setStatusChange(String statusChange) {
        this.statusChange = statusChange;
    }

    public String getStockException() {
        return stockException;
    }

    public void setStockException(String stockException) {
        this.stockException = stockException;
    }

    public String getTurnoverAvg() {
        return turnoverAvg;
    }

    public void setTurnoverAvg(String turnoverAvg) {
        this.turnoverAvg = turnoverAvg;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitLastDate() {
        return visitLastDate;
    }

    public void setVisitLastDate(String visitLastDate) {
        this.visitLastDate = visitLastDate;
    }

    public String getNewAddr() {
        return newAddr;
    }

    public void setNewAddr(String newAddr) {
        this.newAddr = newAddr;
    }

    public String getNewLat() {
        return newLat;
    }

    public void setNewLat(String newLat) {
        this.newLat = newLat;
    }

    public String getNewLong() {
        return newLong;
    }

    public void setNewLong(String newLong) {
        this.newLong = newLong;
    }


}
