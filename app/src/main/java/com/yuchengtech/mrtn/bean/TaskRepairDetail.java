package com.yuchengtech.mrtn.bean;

public class TaskRepairDetail implements java.io.Serializable, Cloneable {

    private String taskId;
    private String rtContent;
    private String remark;
    private String otherContext;

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

    public String getRtContent() {
        return rtContent;
    }

    public void setRtContent(String rtContent) {
        this.rtContent = rtContent;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOtherContext() {
        return otherContext;
    }

    public void setOtherContext(String otherContext) {
        this.otherContext = otherContext;
    }

}
