package com.yuchengtech.mrtn.bean;


public class TaskRiskDetail implements java.io.Serializable, Cloneable {

    private String taskId;
    private String content;
    private String mcGuestmg;
    private String path;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMcGuestmg() {
        return mcGuestmg;
    }

    public void setMcGuestmg(String mcGuestmg) {
        this.mcGuestmg = mcGuestmg;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}

