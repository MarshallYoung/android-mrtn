package com.yuchengtech.mrtn.bean;


public class TaskTrainDetail implements java.io.Serializable, Cloneable {

    private String taskId;
    private String itemCard;
    private String itemCardContent;
    private String itemMcAgreement;
    private String itemMcAgreementContent;
    private String itemPosFault;
    private String itemPosFaultContent;
    private String itemPostSave;
    private String itemPostSaveContent;
    private String itemUserinfo;
    private String itemUserinfoContent;
    private String itemVoucher;
    private String itemVoucherContent;
    private String trainDate;
    private int trainNum;
    private String trainRemark;
    private String trainStudent;
    private String trainTeacher;
    private String trainType;

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

    public String getItemCard() {
        return itemCard;
    }

    public void setItemCard(String itemCard) {
        this.itemCard = itemCard;
    }

    public String getItemCardContent() {
        return itemCardContent;
    }

    public void setItemCardContent(String itemCardContent) {
        this.itemCardContent = itemCardContent;
    }

    public String getItemMcAgreement() {
        return itemMcAgreement;
    }

    public void setItemMcAgreement(String itemMcAgreement) {
        this.itemMcAgreement = itemMcAgreement;
    }

    public String getItemMcAgreementContent() {
        return itemMcAgreementContent;
    }

    public void setItemMcAgreementContent(String itemMcAgreementContent) {
        this.itemMcAgreementContent = itemMcAgreementContent;
    }

    public String getItemPosFault() {
        return itemPosFault;
    }

    public void setItemPosFault(String itemPosFault) {
        this.itemPosFault = itemPosFault;
    }

    public String getItemPosFaultContent() {
        return itemPosFaultContent;
    }

    public void setItemPosFaultContent(String itemPosFaultContent) {
        this.itemPosFaultContent = itemPosFaultContent;
    }

    public String getItemPostSave() {
        return itemPostSave;
    }

    public void setItemPostSave(String itemPostSave) {
        this.itemPostSave = itemPostSave;
    }

    public String getItemPostSaveContent() {
        return itemPostSaveContent;
    }

    public void setItemPostSaveContent(String itemPostSaveContent) {
        this.itemPostSaveContent = itemPostSaveContent;
    }

    public String getItemUserinfo() {
        return itemUserinfo;
    }

    public void setItemUserinfo(String itemUserinfo) {
        this.itemUserinfo = itemUserinfo;
    }

    public String getItemUserinfoContent() {
        return itemUserinfoContent;
    }

    public void setItemUserinfoContent(String itemUserinfoContent) {
        this.itemUserinfoContent = itemUserinfoContent;
    }

    public String getItemVoucher() {
        return itemVoucher;
    }

    public void setItemVoucher(String itemVoucher) {
        this.itemVoucher = itemVoucher;
    }

    public String getItemVoucherContent() {
        return itemVoucherContent;
    }

    public void setItemVoucherContent(String itemVoucherContent) {
        this.itemVoucherContent = itemVoucherContent;
    }

    public String getTrainDate() {
        return trainDate;
    }

    public void setTrainDate(String trainDate) {
        this.trainDate = trainDate;
    }

    public int getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(int trainNum) {
        this.trainNum = trainNum;
    }

    public String getTrainRemark() {
        return trainRemark;
    }

    public void setTrainRemark(String trainRemark) {
        this.trainRemark = trainRemark;
    }

    public String getTrainStudent() {
        return trainStudent;
    }

    public void setTrainStudent(String trainStudent) {
        this.trainStudent = trainStudent;
    }

    public String getTrainTeacher() {
        return trainTeacher;
    }

    public void setTrainTeacher(String trainTeacher) {
        this.trainTeacher = trainTeacher;
    }

    public String getTrainType() {
        return trainType;
    }

    public void setTrainType(String trainType) {
        this.trainType = trainType;
    }

}
