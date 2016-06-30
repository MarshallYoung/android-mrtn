package com.yuchengtech.mrtn.utils.xml;

import java.util.Vector;

public class Grid extends XmlGroup {

    private String field;
    private Vector<GridColumn> columns;

    public Grid() {
        setType("grid");
    }

    public Vector<GridColumn> getColumns() {
        return columns;
    }

    public void setColumns(Vector<GridColumn> columns) {
        this.columns = columns;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

}
