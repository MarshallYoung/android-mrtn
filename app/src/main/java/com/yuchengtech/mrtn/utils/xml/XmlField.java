package com.yuchengtech.mrtn.utils.xml;


public class XmlField {
    private String name;
    private String label;
    private String end_label;
    private ControlEnum type;
    private boolean required;
    private String options;
    private String tags;
    private String defaultStr;
    private boolean readonly;
    private IXmlControl obj;
    private String field;
    private String value;

    // getters & setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public ControlEnum getType() {
        return type;
    }

    public void setType(ControlEnum type) {
        this.type = type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Field Name: " + this.name + "\n");
        sb.append("Field field: " + this.field + "\n");
        sb.append("Field Label: " + this.label + "\n");
        sb.append("Field Type: " + this.type + "\n");
        sb.append("Required? : " + this.required + "\n");
        sb.append("Options : " + this.options + "\n");
        sb.append("Value : " + (String) this.getData() + "\n");

        return sb.toString();
    }

    public String getFormattedResult() {
        return this.field + "= [" + (String) this.getData() + "]";

    }

    public String getData() {
        if (getObj() != null) {
            switch (type) {
                case text:
                case pwd:
                case numeric:
                case choice:
                case check:
                case date:
                case txt_html:
                case txt_img:
                    return getObj().getValue();
                case hidden:
                    return this.getValue();
                default:
                    break;
            }
        }

        return null;
    }

    public String getText() {
        if (getObj() != null) {
            switch (type) {
                case text:
                case pwd:
                case numeric:
                case choice:
                case check:
                case txt_img:
                    return getObj().getText();
                case hidden:
                    return this.getValue();
                default:
                    break;
            }
        }

        return null;
    }

    public IXmlControl getObj() {
        return obj;
    }

    public void setObj(IXmlControl obj) {
        this.obj = obj;
    }

    public String getDefaultStr() {
        return defaultStr;
    }

    public void setDefaultStr(String defaultStr) {
        this.defaultStr = defaultStr;
    }

    public boolean isReadonly() {
        return readonly;
    }

    public void setReadonly(boolean readonly) {
        this.readonly = readonly;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getEnd_label() {
        return end_label;
    }

    public void setEnd_label(String end_label) {
        this.end_label = end_label;
    }

}
