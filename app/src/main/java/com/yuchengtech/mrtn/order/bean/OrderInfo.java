package com.yuchengtech.mrtn.order.bean;

import com.yuchengtech.mrtn.base.TermStatus;
import com.yuchengtech.mrtn.bean.TaskApplyDetail;
import com.yuchengtech.mrtn.bean.TaskBankDetail;
import com.yuchengtech.mrtn.bean.TaskDistributionDetail;
import com.yuchengtech.mrtn.bean.TaskInstallDetail;
import com.yuchengtech.mrtn.bean.TaskRepairDetail;
import com.yuchengtech.mrtn.bean.TaskRiskDetail;
import com.yuchengtech.mrtn.bean.TaskSurveyDetail;
import com.yuchengtech.mrtn.bean.TaskTrainDetail;
import com.yuchengtech.mrtn.bean.TaskVisitDetail;

import java.io.Serializable;

/**
 * 任务单概览信息
 */
public class OrderInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private TaskSurveyDetail taskSurveyDetail;

    private TaskVisitDetail taskVisitDetail;

    private TaskTrainDetail taskTrainDetail;

    private TaskRiskDetail taskRiskDetail;

    private TaskRepairDetail taskRepairDetail;

    private TaskDistributionDetail taskDistributionDetail;

    private TaskBankDetail taskBankDetail;

    private TaskInstallDetail taskInstallDetail;

    private TaskApplyDetail taskApplyDetail;

    private transient String createTime_begin;
    private transient String createTime_end;

    private String instName;

    private String servicesManName;

    private String operatorName;

    private String partnerName;

    private String mcTypeIdName;

    private String taskId;

    private String attachment;

    private String completionTime;

    private String createTime;

    private String disptTime;

    private String handleTime;

    private String instId;

    private String mcAddr;

    private String mcId;

    private String mcName;

    private String mcSalesMan;

    private String mcServicesMan;

    private String mcTypeId;

    private String mhType;
    private transient String mhTypeName;

    private String operator;

    private String operatorType;

    private String partnerId;

    private String predictTime;

    private String sign;

    private String taskName;

    private String taskStatus;
    private String taskType;

    private String termId;

    private String termStatus;

    private transient String termStatusName;

    private String termTaddr;

    private String termTlinknm;

    private String termTlinktel;

    private String yxMcId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDisptTime() {
        return disptTime;
    }

    public void setDisptTime(String disptTime) {
        this.disptTime = disptTime;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

    public String getMcAddr() {
        return mcAddr;
    }

    public void setMcAddr(String mcAddr) {
        this.mcAddr = mcAddr;
    }

    public String getMcId() {
        return mcId;
    }

    public void setMcId(String mcId) {
        this.mcId = mcId;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }

    public String getMcSalesMan() {
        return mcSalesMan;
    }

    public void setMcSalesMan(String mcSalesMan) {
        this.mcSalesMan = mcSalesMan;
    }

    public String getMcServicesMan() {
        return mcServicesMan;
    }

    public void setMcServicesMan(String mcServicesMan) {
        this.mcServicesMan = mcServicesMan;
    }

    public String getMcTypeId() {
        return mcTypeId;
    }

    public void setMcTypeId(String mcTypeId) {
        this.mcTypeId = mcTypeId;
    }

    public String getMhType() {
        return mhType;
    }

    public void setMhType(String mhType) {
        this.mhType = mhType;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getPredictTime() {
        return predictTime;
    }

    public void setPredictTime(String predictTime) {
        this.predictTime = predictTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTermId() {
        return termId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public String getTermStatus() {
        return termStatus;
    }

    public void setTermStatus(String termStatus) {
        this.termStatus = termStatus;
    }

    public String getTermTaddr() {
        return termTaddr;
    }

    public void setTermTaddr(String termTaddr) {
        this.termTaddr = termTaddr;
    }

    public String getTermTlinknm() {
        return termTlinknm;
    }

    public void setTermTlinknm(String termTlinknm) {
        this.termTlinknm = termTlinknm;
    }

    public String getTermTlinktel() {
        return termTlinktel;
    }

    public void setTermTlinktel(String termTlinktel) {
        this.termTlinktel = termTlinktel;
    }

    public String getYxMcId() {
        return yxMcId;
    }

    public void setYxMcId(String yxMcId) {
        this.yxMcId = yxMcId;
    }

    public TaskSurveyDetail getTaskSurveyDetail() {
        return taskSurveyDetail;
    }

    public void setTaskSurveyDetail(TaskSurveyDetail taskSurveyDetail) {
        this.taskSurveyDetail = taskSurveyDetail;
    }

    public TaskVisitDetail getTaskVisitDetail() {
        return taskVisitDetail;
    }

    public void setTaskVisitDetail(TaskVisitDetail taskVisitDetail) {
        this.taskVisitDetail = taskVisitDetail;
    }

    public TaskTrainDetail getTaskTrainDetail() {
        return taskTrainDetail;
    }

    public void setTaskTrainDetail(TaskTrainDetail taskTrainDetail) {
        this.taskTrainDetail = taskTrainDetail;
    }

    public TaskRiskDetail getTaskRiskDetail() {
        return taskRiskDetail;
    }

    public void setTaskRiskDetail(TaskRiskDetail taskRiskDetail) {
        this.taskRiskDetail = taskRiskDetail;
    }

    public TaskRepairDetail getTaskRepairDetail() {
        return taskRepairDetail;
    }

    public void setTaskRepairDetail(TaskRepairDetail taskRepairDetail) {
        this.taskRepairDetail = taskRepairDetail;
    }

    public TaskDistributionDetail getTaskDistributionDetail() {
        return taskDistributionDetail;
    }

    public void setTaskDistributionDetail(
            TaskDistributionDetail taskDistributionDetail) {
        this.taskDistributionDetail = taskDistributionDetail;
    }

    public TaskBankDetail getTaskBankDetail() {
        return taskBankDetail;
    }

    public void setTaskBankDetail(TaskBankDetail taskBankDetail) {
        this.taskBankDetail = taskBankDetail;
    }

    public TaskInstallDetail getTaskInstallDetail() {
        return taskInstallDetail;
    }

    public void setTaskInstallDetail(TaskInstallDetail taskInstallDetail) {
        this.taskInstallDetail = taskInstallDetail;
    }

    public TaskApplyDetail getTaskApplyDetail() {
        return taskApplyDetail;
    }

    public void setTaskApplyDetail(TaskApplyDetail taskApplyDetail) {
        this.taskApplyDetail = taskApplyDetail;
    }

    public String getCreateTime_begin() {
        return createTime_begin;
    }

    public void setCreateTime_begin(String createTime_begin) {
        this.createTime_begin = createTime_begin;
    }

    public String getCreateTime_end() {
        return createTime_end;
    }

    public void setCreateTime_end(String createTime_end) {
        this.createTime_end = createTime_end;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getServicesManName() {
        return servicesManName;
    }

    public void setServicesManName(String servicesManName) {
        this.servicesManName = servicesManName;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getMcTypeIdName() {
        return mcTypeIdName;
    }

    public void setMcTypeIdName(String mcTypeIdName) {
        this.mcTypeIdName = mcTypeIdName;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getMhTypeName() {
        if (mhType != null) {
            return com.yuchengtech.mrtn.base.MachineType.getName(mhType);
        }
        return mhTypeName;
    }

    public void setMhTypeName(String mhTypeName) {
        this.mhTypeName = mhTypeName;
    }

    public String getTermStatusName() {
        if (termStatus != null) {
            return TermStatus.ConvertStatus(termStatus);
        }
        return termStatusName;
    }

    public void setTermStatusName(String termStatusName) {
        this.termStatusName = termStatusName;
    }
}