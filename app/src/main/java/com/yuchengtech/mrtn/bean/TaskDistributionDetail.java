package com.yuchengtech.mrtn.bean;


public class TaskDistributionDetail implements java.io.Serializable, Cloneable {

    private String taskId;
    private int mtalNum1;
    private int mtalNum2;
    private int mtalNum3;
    private String remarks;

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

    public int getMtalNum1() {
        return mtalNum1;
    }

    public void setMtalNum1(int mtalNum1) {
        this.mtalNum1 = mtalNum1;
    }

    public int getMtalNum2() {
        return mtalNum2;
    }

    public void setMtalNum2(int mtalNum2) {
        this.mtalNum2 = mtalNum2;
    }

    public int getMtalNum3() {
        return mtalNum3;
    }

    public void setMtalNum3(int mtalNum3) {
        this.mtalNum3 = mtalNum3;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
