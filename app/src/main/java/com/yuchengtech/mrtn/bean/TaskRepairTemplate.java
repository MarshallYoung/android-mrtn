package com.yuchengtech.mrtn.bean;

/**
 * TaskRepairTemplate entity.
 *
 * @author MyEclipse Persistence Tools
 */

public class TaskRepairTemplate implements java.io.Serializable {

    // Fields

    private Long id;
    private String rtContent;

    // Constructors

    /**
     * default constructor
     */
    public TaskRepairTemplate() {
    }

    /**
     * minimal constructor
     */
    public TaskRepairTemplate(Long id) {
        this.id = id;
    }

    /**
     * full constructor
     */
    public TaskRepairTemplate(Long id, String rtContent) {
        this.id = id;
        this.rtContent = rtContent;
    }

    // Property accessors

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRtContent() {
        return this.rtContent;
    }

    public void setRtContent(String rtContent) {
        this.rtContent = rtContent;
    }

}