package com.yuchengtech.mrtn.utils.xml;

import com.yuchengtech.mrtn.views.XmlDateBox;
import com.yuchengtech.mrtn.views.XmlImgBox;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Vector;

public class XmlForm {

    private String formName;
    private String submitTo;
    private String imageList = "";

    private Vector<XmlGroup> groups;

    public XmlForm() {
        this.setGroups(new Vector<XmlGroup>());
        formName = "";
        submitTo = "loopback"; // do nothing but display the results
    }

    public String getFormName() {
        return formName;
    }

    public void setFormName(String formName) {
        this.formName = formName;
    }

    public String getSubmitTo() {
        return submitTo;
    }

    public void setSubmitTo(String submitTo) {
        this.submitTo = submitTo;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("XmlGuiForm:\n");

        sb.append("Form Name: " + this.formName + "\n");
        sb.append("Submit To: " + this.submitTo + "\n");
        if (this.getGroups() == null)
            return sb.toString();
        ListIterator<XmlGroup> li = this.getGroups().listIterator();
        while (li.hasNext()) {
            sb.append(li.next().toString());
        }

        return sb.toString();
    }

    public String getFormattedResults() {
        StringBuilder sb = new StringBuilder();
        sb.append("Results:\n");
        if (this.getGroups() == null)
            return sb.toString();
        ListIterator<XmlGroup> li = this.getGroups().listIterator();
        while (li.hasNext()) {
            XmlGroup group = li.next();
            if (!group.getType().equals("grid")) {
                Iterator<XmlField> lf = group.getFields().iterator();
                while (lf.hasNext()) {
                    sb.append(lf.next().getFormattedResult() + "\n");
                }
            }
        }

        return sb.toString();
    }

    public Map<String, String> FormValid() {
        Map<String, String> map = new HashMap<String, String>();
        XmlField item = null;
        try {
            if (this.getGroups() == null)
                return null;
            ListIterator<XmlGroup> li = this.getGroups().listIterator();
            while (li.hasNext()) {
                XmlGroup group = li.next();
                if (!group.getType().equals("grid")) {
                    if (group.getIndexs().containsKey("txt_g_taskType")) {
                        String task_type = group.getFields()
                                .get(group.getIndexs().get("txt_g_taskType"))
                                .getData();
                        if (task_type != null) {
                            if (task_type.equals("002")) {
                                map.putAll(task002Valid(group.getFields(),
                                        group.getIndexs()));
                            } else if (task_type.equals("006")) {
                                map.putAll(task006Valid(group.getFields(),
                                        group.getIndexs()));
                            } else if (task_type.equals("007")) {
                                map.putAll(task007Valid(group.getFields(),
                                        group.getIndexs()));
                            }
                        }
                    }
                    Iterator<XmlField> lf = group.getFields().iterator();
                    while (lf.hasNext()) {
                        item = lf.next();
                        if (item.getType() != ControlEnum.hidden) {
                            if (item.getType() != ControlEnum.txt_img) {
                                if (item.getName().equals("date_g_handleTime")) {
                                    XmlDateBox datebox = (XmlDateBox) item
                                            .getObj();
                                    if (!datebox.isDisplay()
                                            && item.isRequired()
                                            && (item.getData() == null || item
                                            .getData().isEmpty())) {
                                        map.put(item.getLabel(), "不能为空 ");

                                    }
                                } else {
                                    if (item.isRequired()
                                            && (item.getData() == null || item
                                            .getData().isEmpty())) {
                                        map.put(item.getLabel(), "不能为空 ");

                                    }
                                }
                            } else {
                                XmlImgBox imgbox = (XmlImgBox) item.getObj();
                                if (item.isRequired()
                                        && imgbox.isDisplay()
                                        && (item.getData() == null || item
                                        .getData().isEmpty())) {
                                    map.put(item.getLabel(), "不能为空 ");

                                }
                            }

                        }

                    }
                }
            }

            return map;
        } catch (Exception e) {
            return null;
        }
    }

    private Map<String, String> task002Valid(List<XmlField> fields,
                                             Map<String, Integer> indexs) {
        Map<String, String> map = new HashMap<String, String>();
        XmlField item = null;
        Iterator<XmlField> lf = fields.iterator();
        while (lf.hasNext()) {
            item = lf.next();
            if (item.getType() == ControlEnum.choice) {
                if (item.isRequired()
                        && (item.getText() != item.getDefaultStr())) {
                    if (fields.get(indexs.get("txt_g_result")).getValue() == null
                            && fields.get(indexs.get("txt_g_result"))
                            .getValue().isEmpty()) {
                        if (!map.containsKey("txt_g_result"))
                            map.put(fields.get(indexs.get("txt_g_result"))
                                    .getLabel(), "不能为空 ");
                    }
                }
                if (item.getName().equals("choice_g_posIsAddr")
                        && !item.getText().equals(item.getDefaultStr())) {
                    if (fields.get(indexs.get("txt_g_posNewAddr")).getData() == null
                            || fields.get(indexs.get("txt_g_posNewAddr"))
                            .getData().isEmpty()) {
                        map.put(fields.get(indexs.get("txt_g_posNewAddr"))
                                .getLabel(), "不能为空 ");
                    }
                }
                if (item.getName().equals("choice_g_managementChange")
                        && !item.getText().equals(item.getDefaultStr())) {
                    if (fields.get(indexs.get("txt_g_managementName"))
                            .getData() == null
                            || fields.get(indexs.get("txt_g_managementName"))
                            .getData().isEmpty()) {
                        map.put(fields.get(indexs.get("txt_g_managementName"))
                                .getLabel(), "不能为空 ");
                    }
                    if (fields.get(indexs.get("txt_g_managementTel")).getData() == null
                            || fields.get(indexs.get("txt_g_managementTel"))
                            .getData().isEmpty()) {
                        map.put(fields.get(indexs.get("txt_g_managementTel"))
                                .getLabel(), "不能为空 ");
                    }
                }
            }
        }
        return map;
    }

    private Map<String, String> task006Valid(List<XmlField> fields,
                                             Map<String, Integer> indexs) {
        Map<String, String> map = new HashMap<String, String>();
        if (fields.get(indexs.get("numeric_g_mtalNum1")).getData() == null
                || fields.get(indexs.get("numeric_g_mtalNum1")).getData()
                .isEmpty()) {
            if (fields.get(indexs.get("numeric_g_mtalNum2")).getData() == null
                    || fields.get(indexs.get("numeric_g_mtalNum2")).getData()
                    .isEmpty()) {
                if (fields.get(indexs.get("numeric_g_mtalNum3")).getData() == null
                        || fields.get(indexs.get("numeric_g_mtalNum3"))
                        .getData().isEmpty()) {
                    map.put("热敏纸大卷，热敏纸小卷，针打纸", "必须选填一项");
                }

            }
        }

        return map;
    }

    private Map<String, String> task007Valid(List<XmlField> fields,
                                             Map<String, Integer> indexs) {
        Map<String, String> map = new HashMap<String, String>();
        if (fields.get(indexs.get("choice_g_rtContent")).getData() != null
                && fields.get(indexs.get("choice_g_rtContent")).getData()
                .equals("其他")) {
            if (fields.get(indexs.get("txt_g_other_context")).getData() == null
                    || fields.get(indexs.get("txt_g_other_context")).getData()
                    .isEmpty()) {
                map.put(fields.get(indexs.get("txt_g_other_context"))
                        .getLabel(), "不能为空 ");
            }

        }
        return map;
    }

    public Map<String, String> getFormMapData() {
        Map<String, String> map = new HashMap<String, String>();
        XmlField item = null;
        try {
            if (this.getGroups() == null)
                return null;
            ListIterator<XmlGroup> li = this.getGroups().listIterator();
            while (li.hasNext()) {
                XmlGroup group = li.next();
                if (!group.getType().equals("grid")) {
                    Iterator<XmlField> lf = group.getFields().iterator();
                    while (lf.hasNext()) {
                        item = lf.next();
                        if (item.getType() != ControlEnum.hidden) {
                            map.put(item.getField(), item.getData());
                        } else {
                            map.put(item.getField(), item.getValue());
                        }

                    }
                }
            }

            return map;
        } catch (Exception e) {
            return null;
        }
    }

    public Vector<XmlGroup> getGroups() {
        return groups;
    }

    public void setGroups(Vector<XmlGroup> groups) {
        this.groups = groups;
    }

    public String getImageList() {
        return imageList;
    }

    public void setImageList(String imageList) {
        this.imageList = imageList;
    }
}