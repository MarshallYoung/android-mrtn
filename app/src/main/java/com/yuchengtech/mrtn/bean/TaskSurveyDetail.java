package com.yuchengtech.mrtn.bean;


public class TaskSurveyDetail implements java.io.Serializable, Cloneable {

    private String taskId;
    private String bankOpinion;
    private String feedback;
    private String measures;
    private String riskQuestion;
    private String surveyRequire;
    private String surveyUser;

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

    public String getBankOpinion() {
        return bankOpinion;
    }

    public void setBankOpinion(String bankOpinion) {
        this.bankOpinion = bankOpinion;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getMeasures() {
        return measures;
    }

    public void setMeasures(String measures) {
        this.measures = measures;
    }

    public String getRiskQuestion() {
        return riskQuestion;
    }

    public void setRiskQuestion(String riskQuestion) {
        this.riskQuestion = riskQuestion;
    }

    public String getSurveyRequire() {
        return surveyRequire;
    }

    public void setSurveyRequire(String surveyRequire) {
        this.surveyRequire = surveyRequire;
    }

    public String getSurveyUser() {
        return surveyUser;
    }

    public void setSurveyUser(String surveyUser) {
        this.surveyUser = surveyUser;
    }

}
