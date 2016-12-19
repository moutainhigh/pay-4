package com.pay.poss.form;

import java.io.Serializable;

/**
 * Created by songyilin on 2016/10/11 0011.
 */
public class DownloadDataForm implements Serializable{

    private String downloadType;

    private Integer dataType;

    private Integer status;

    private String startDate;

    private String endDate;

    private Integer pageNo;

    private Integer rows;

    private String orderByCol;

    private String orderType;

    public String getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(String downloadType) {
        this.downloadType = downloadType;
    }

    public Integer getDataType() {
        return dataType;
    }

    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getOrderByCol() {
        return orderByCol;
    }

    public void setOrderByCol(String orderByCol) {
        this.orderByCol = orderByCol;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
