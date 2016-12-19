package com.pay.gateway.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by cuber.huang on 2016/7/20.
 */
public class GeneralData {
    private List<Column> columns;

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
