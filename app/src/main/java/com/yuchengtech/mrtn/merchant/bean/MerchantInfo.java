package com.yuchengtech.mrtn.merchant.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MerchantInfo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private long id;

    private List<TerminalInfo> terms = new ArrayList<TerminalInfo>();

    private String instName;

    private String instId;

    private String createTime;

    private String elongsTo;

    private String instanceid;

    private String isTempSave;

    private String locked;

    private String mcAddr;

    private String mcArea;

    private String mcCapital;

    private String mcHours;

    private String mcId;

    private String mcLand;

    private String mcLglidno;

    private String mcLglidtp;

    private String mcLglnam;

    private String mcLgltel;

    private String mcLicenseNo;

    private String mcLinknm;

    private String mcModel;

    private String mcMprofit;

    private String mcName;

    private String mcNature;

    private String mcOparea;

    private String mcOpnbnk;

    private String mcOrgcode;

    private String mcRegional;

    private String mcSection;

    private String mcSettleNature;

    private String mcSettleact;

    private String mcSettlenam;

    private String mcSetupDate;

    private String mcStatus;

    private String mcTaxNo;

    private String mcTel;

    private String nodeVersion;

    private String nodeid;

    private String track;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getElongsTo() {
        return elongsTo;
    }

    public void setElongsTo(String elongsTo) {
        this.elongsTo = elongsTo;
    }

    public String getInstanceid() {
        return instanceid;
    }

    public void setInstanceid(String instanceid) {
        this.instanceid = instanceid;
    }

    public String getIsTempSave() {
        return isTempSave;
    }

    public void setIsTempSave(String isTempSave) {
        this.isTempSave = isTempSave;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getMcAddr() {
        return mcAddr;
    }

    public void setMcAddr(String mcAddr) {
        this.mcAddr = mcAddr;
    }

    public String getMcArea() {
        return mcArea;
    }

    public void setMcArea(String mcArea) {
        this.mcArea = mcArea;
    }

    public String getMcCapital() {
        return mcCapital;
    }

    public void setMcCapital(String mcCapital) {
        this.mcCapital = mcCapital;
    }

    public String getMcHours() {
        return mcHours;
    }

    public void setMcHours(String mcHours) {
        this.mcHours = mcHours;
    }

    public String getMcId() {
        return mcId;
    }

    public void setMcId(String mcId) {
        this.mcId = mcId;
    }

    public String getMcLand() {
        return mcLand;
    }

    public void setMcLand(String mcLand) {
        this.mcLand = mcLand;
    }

    public String getMcLglidno() {
        return mcLglidno;
    }

    public void setMcLglidno(String mcLglidno) {
        this.mcLglidno = mcLglidno;
    }

    public String getMcLglidtp() {
        return mcLglidtp;
    }

    public void setMcLglidtp(String mcLglidtp) {
        this.mcLglidtp = mcLglidtp;
    }

    public String getMcLglnam() {
        return mcLglnam;
    }

    public void setMcLglnam(String mcLglnam) {
        this.mcLglnam = mcLglnam;
    }

    public String getMcLgltel() {
        return mcLgltel;
    }

    public void setMcLgltel(String mcLgltel) {
        this.mcLgltel = mcLgltel;
    }

    public String getMcLicenseNo() {
        return mcLicenseNo;
    }

    public void setMcLicenseNo(String mcLicenseNo) {
        this.mcLicenseNo = mcLicenseNo;
    }

    public String getMcLinknm() {
        return mcLinknm;
    }

    public void setMcLinknm(String mcLinknm) {
        this.mcLinknm = mcLinknm;
    }

    public String getMcModel() {
        return mcModel;
    }

    public void setMcModel(String mcModel) {
        this.mcModel = mcModel;
    }

    public String getMcMprofit() {
        return mcMprofit;
    }

    public void setMcMprofit(String mcMprofit) {
        this.mcMprofit = mcMprofit;
    }

    public String getMcName() {
        return mcName;
    }

    public void setMcName(String mcName) {
        this.mcName = mcName;
    }

    public String getMcNature() {
        return mcNature;
    }

    public void setMcNature(String mcNature) {
        this.mcNature = mcNature;
    }

    public String getMcOparea() {
        return mcOparea;
    }

    public void setMcOparea(String mcOparea) {
        this.mcOparea = mcOparea;
    }

    public String getMcOpnbnk() {
        return mcOpnbnk;
    }

    public void setMcOpnbnk(String mcOpnbnk) {
        this.mcOpnbnk = mcOpnbnk;
    }

    public String getMcOrgcode() {
        return mcOrgcode;
    }

    public void setMcOrgcode(String mcOrgcode) {
        this.mcOrgcode = mcOrgcode;
    }

    public String getMcRegional() {
        return mcRegional;
    }

    public void setMcRegional(String mcRegional) {
        this.mcRegional = mcRegional;
    }

    public String getMcSection() {
        return mcSection;
    }

    public void setMcSection(String mcSection) {
        this.mcSection = mcSection;
    }

    public String getMcSettleNature() {
        return mcSettleNature;
    }

    public void setMcSettleNature(String mcSettleNature) {
        this.mcSettleNature = mcSettleNature;
    }

    public String getMcSettleact() {
        return mcSettleact;
    }

    public void setMcSettleact(String mcSettleact) {
        this.mcSettleact = mcSettleact;
    }

    public String getMcSettlenam() {
        return mcSettlenam;
    }

    public void setMcSettlenam(String mcSettlenam) {
        this.mcSettlenam = mcSettlenam;
    }

    public String getMcSetupDate() {
        return mcSetupDate;
    }

    public void setMcSetupDate(String mcSetupDate) {
        this.mcSetupDate = mcSetupDate;
    }

    public String getMcStatus() {
        return mcStatus;
    }

    public void setMcStatus(String mcStatus) {
        this.mcStatus = mcStatus;
    }

    public String getMcTaxNo() {
        return mcTaxNo;
    }

    public void setMcTaxNo(String mcTaxNo) {
        this.mcTaxNo = mcTaxNo;
    }

    public String getMcTel() {
        return mcTel;
    }

    public void setMcTel(String mcTel) {
        this.mcTel = mcTel;
    }

    public String getNodeVersion() {
        return nodeVersion;
    }

    public void setNodeVersion(String nodeVersion) {
        this.nodeVersion = nodeVersion;
    }

    public String getNodeid() {
        return nodeid;
    }

    public void setNodeid(String nodeid) {
        this.nodeid = nodeid;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public List<TerminalInfo> getTerms() {
        return terms;
    }

    public void setTerms(List<TerminalInfo> terms) {
        this.terms = terms;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getInstId() {
        return instId;
    }

    public void setInstId(String instId) {
        this.instId = instId;
    }

}
