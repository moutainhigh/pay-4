package com.pay.poss.excel;

/**
 * Created by songyilin on 2016/10/11.
 */
public class ExcelBean {

    private String headerName = "";//列头信息
    private String columnKey = "";//列表示
    private String styleNum = "";//列格式

    public ExcelBean() {
        super();
    }

    public ExcelBean(String headerName, String columnKey, String styleNum) {
        this.headerName = headerName;
        this.columnKey = columnKey;
        this.styleNum = styleNum;
    }

    public String getHeaderName() {
        return headerName;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getStyleNum() {
        return styleNum;
    }

    public void setStyleNum(String styleNum) {
        this.styleNum = styleNum;
    }

}
