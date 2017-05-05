package com.cipher.seed.domain;

/**
 * Created by cipher on 2017/5/5.
 */
public class TableInfo {

    private String colName;
    private String colComment;
    private String colType;

    public String getColName() {
        return colName;
    }

    public String getColComment() {
        return colComment;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public void setColComment(String colComment) {
        this.colComment = colComment;
    }

    public String getColType() {
        return colType;
    }

    public void setColType(String colType) {
        this.colType = colType;
    }

}
