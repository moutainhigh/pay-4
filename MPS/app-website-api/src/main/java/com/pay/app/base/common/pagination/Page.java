package com.pay.app.base.common.pagination;

import java.io.Serializable;

public class Page implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -9211627513349305542L;

    private Integer index;

    private Integer value;

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
