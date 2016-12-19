package com.pay.gateway.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by cuber.huang on 2016/7/20.
 */
public class ValidateObj {
    private String name;
    private String notnull;
    private String length;
    private String dateType;
    private List<Handler> handlers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotnull() {
        return notnull;
    }

    public void setNotnull(String notnull) {
        this.notnull = notnull;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public List<Handler> getHandlers() {
        return handlers;
    }

    public void setHandlers(List<Handler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
