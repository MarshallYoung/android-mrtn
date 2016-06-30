package com.yuchengtech.mrtn.bean;


public class TaskApplyDetail implements java.io.Serializable, Cloneable {
    private String taskId;
    private String intromcid;
    private String mcApproveDate;
    private String mcIsApprove;
    private String mcbusilicense;
    private String mcoparea;

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

    public String getIntromcid() {
        return intromcid;
    }

    public void setIntromcid(String intromcid) {
        this.intromcid = intromcid;
    }

    public String getMcApproveDate() {
        return mcApproveDate;
    }

    public void setMcApproveDate(String mcApproveDate) {
        this.mcApproveDate = mcApproveDate;
    }

    public String getMcIsApprove() {
        return mcIsApprove;
    }

    public void setMcIsApprove(String mcIsApprove) {
        this.mcIsApprove = mcIsApprove;
    }

    public String getMcbusilicense() {
        return mcbusilicense;
    }

    public void setMcbusilicense(String mcbusilicense) {
        this.mcbusilicense = mcbusilicense;
    }

    public String getMcoparea() {
        return mcoparea;
    }

    public void setMcoparea(String mcoparea) {
        this.mcoparea = mcoparea;
    }

}
