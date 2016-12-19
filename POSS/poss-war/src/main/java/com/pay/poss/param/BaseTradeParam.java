package com.pay.poss.param;

import java.util.List;

/**
 * Created by songyilin on 2016/10/12 0012.
 */
public class BaseTradeParam {

    private String startDate;

    private String endDate;

    private Integer startIndex;

    private Integer endIndex;

    private Integer status;

    private List<String> orderByColList;

    private String orderType;

    private List<String> groupByCol;

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

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
    }

    public List<String> getOrderByColList() {
        return orderByColList;
    }

    public void setOrderByColList(List<String> orderByColList) {
        this.orderByColList = orderByColList;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public List<String> getGroupByCol() {
        return groupByCol;
    }

    public void setGroupByCol(List<String> groupByCol) {
        this.groupByCol = groupByCol;
    }
}
