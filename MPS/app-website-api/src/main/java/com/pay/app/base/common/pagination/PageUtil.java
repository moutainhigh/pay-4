package com.pay.app.base.common.pagination;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Scope;



/**
 * Copyright 2009, Inc. All rights reserved. 
 * PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 * 
 * Projet Name: 
 * File Name: PageUtil.java
 * 
 * @Author: zengjin PageUtil Create Date: 2009-11-19
 * @Version: 1.0 Modification:<br>
 * <br>
 */

public class PageUtil {

    public final static int PAGESIZE = 20;

    private int pageSize = PAGESIZE;

    private int startIndex = 0;

    private int totalCount;
    
    private int curPageNo=1;

    private int[] indexes = new int[0];

    /**
     * part constractor
     * 
     * @param items
     * @param totalCount
     */
    public PageUtil(int curPageNo, int pageSize, int totalCount) {
        setPageSize(pageSize);
        setTotalCount(totalCount);
        setCurPageNo(curPageNo);
        setStartIndex(getIndexByPageNo(curPageNo));

    }

    public int getCurPageNo() {
        return curPageNo;
    }

    public void setCurPageNo(int curPageNo) {
        this.curPageNo = curPageNo;
    }
    /**
     * 
     * @return int
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 
     * @param totalCount
     */
    public void setTotalCount(int totalCount) {
        if (totalCount > 0) {
            this.totalCount = totalCount;
            int count = totalCount / pageSize;
            if (totalCount % pageSize > 0)
                count++;
            indexes = new int[count];
            for (int i = 0; i < count; i++) {
                indexes[i] = pageSize * i;
            }
        } else {
            this.totalCount = 0;
        }
    }

    /**
     * 当前索引
     * 
     * @return int
     */
    public int getIndexByPageNo(int curPageNo) {
        int index = 0;
        if (curPageNo > 0) {
            if (curPageNo > this.getmaxPageNo())
                return this.getEndIndex(this.getIndexes());
            index = (curPageNo - 1) * pageSize;
        }
        return index;
    }

    /**
     * 
     * @return int[]
     */
    public int[] getIndexes() {
        return indexes;
    }

    /**
     * 
     * @param indexes
     */
    public void setIndexes(int[] indexes) {
        this.indexes = indexes;
    }

    /**
     * 最大页
     * 
     * @return int
     */
    public int getmaxPageNo() {
        int recordCount = this.getTotalCount();
        int maxPageNo = (recordCount % pageSize == 0) ? (recordCount / pageSize) : (recordCount / pageSize + 1);
        return maxPageNo;
    }

    /**
     * 最后一页的索引
     * 
     * @param indexes
     * @return int
     */
    public int getEndIndex(int[] indexes) {
        int endIndex = 0;
        if (indexes.length > 0)
            return indexes[indexes.length - 1];
        else
            return endIndex;
    }
    
    
    /**
     * 获取下拉列表的所有页码信息
     * 
     * @param indexes
     * @return list
     */
    @SuppressWarnings("unchecked")
    public List getPageNumberList(int[] indexes) {
        List list = new ArrayList();
        Page page=null;
        for (int i = 0; i < indexes.length; i++) {
            page = new Page();
            page.setIndex(new Integer(i + 1));
            page.setValue(new Integer(indexes[i]));
            list.add(page);
        }
        return list;
    }

}
