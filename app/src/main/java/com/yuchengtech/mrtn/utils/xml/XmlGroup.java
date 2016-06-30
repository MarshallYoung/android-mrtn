package com.yuchengtech.mrtn.utils.xml;

import java.util.List;
import java.util.Map;

public class XmlGroup {
    private String type;
    private String text;
    private List<XmlField> fields;
    private Map<String, Integer> indexs;
    public XmlGroup() {
        type = "";
    }

    public List<XmlField> getFields() {
        return fields;
    }

    public void setFields(List<XmlField> fields) {
        this.fields = fields;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Integer> getIndexs() {
        return indexs;
    }

    public void setIndexs(Map<String, Integer> indexs) {
        this.indexs = indexs;
    }
}
