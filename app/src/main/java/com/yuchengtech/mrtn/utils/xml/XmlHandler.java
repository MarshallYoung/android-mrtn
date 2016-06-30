package com.yuchengtech.mrtn.utils.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class XmlHandler extends DefaultHandler {

    private XmlForm form;
    private XmlField field;
    private XmlGroup group;
    private StringBuilder builder;
    private Grid grid;
    private GridColumn column;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();

        builder = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName,
                             Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if (localName.equals("form")) {
            setForm(new XmlForm());
            if (attributes.getLength() > 0) {
                getForm().setFormName(attributes.getValue("name"));
            }
        } else if (localName.equals("group")) {
            group = new XmlGroup();
            group.setText(attributes.getValue("text"));

        } else if (localName.equals("grid")) {
            grid = new Grid();
            grid.setText(attributes.getValue("text"));
            grid.setField(attributes.getValue("field"));

        } else if (localName.equals("field")) {
            field = new XmlField();
            field.setType(ControlEnum.valueOf(ControlEnum.class,
                    attributes.getValue("type")));
            field.setName(attributes.getValue("name"));
            field.setField(attributes.getValue("field"));
            field.setLabel(attributes.getValue("label"));
            field.setOptions(attributes.getValue("options"));
            if (attributes.getIndex("tag") > 0)
                field.setTags(attributes.getValue("tag"));
            if (attributes.getIndex("end_label") > 0)
                field.setEnd_label(attributes.getValue("end_label"));
            if (attributes.getIndex("required") > 0)
                field.setRequired(attributes.getValue("required").equals("Y") ? true
                        : false);
            if (attributes.getIndex("readonly") > 0)
                field.setReadonly(attributes.getValue("readonly").equals("Y") ? true
                        : false);
            if (attributes.getIndex("default") > 0)
                field.setDefaultStr(attributes.getValue("default"));
        } else if (localName.equals("column")) {
            column = new GridColumn();
            column.setName(attributes.getValue("name"));
            column.setLabel(attributes.getValue("label"));
            column.setDefaultStr(attributes.getValue("default"));
        }

        builder.setLength(0);

    }

    @Override
    public void characters(char[] ch, int start, int length)
            throws SAXException {
        super.characters(ch, start, length);
        builder.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName)
            throws SAXException {
        super.endElement(uri, localName, qName);
        if (localName.equals("field")) {
            if (group.getFields() == null) {
                group.setFields(new ArrayList<XmlField>());
                group.setIndexs(new HashMap<String, Integer>());
            }
            group.getFields().add(field);
            group.getIndexs().put(field.getName(), group.getFields().indexOf(field));
        } else if (localName.equals("group")) {
            getForm().getGroups().add(group);
        } else if (localName.equals("grid")) {
            getForm().getGroups().add(grid);
        } else if (localName.equals("column")) {
            if (grid.getColumns() == null) {
                grid.setColumns(new Vector<GridColumn>());
            }
            grid.getColumns().add(column);
        }
    }

    public XmlForm getForm() {
        return form;
    }

    public void setForm(XmlForm form) {
        this.form = form;
    }

}
