package com.pay.gateway.model;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by cuber.huang on 2016/7/20.
 */
public class Handler {
    private String name;

    private String code;

    private String returnCn;

    private String returnEn;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getReturnEn() {
        return returnEn;
    }

    public void setReturnEn(String returnEn) {
        this.returnEn = returnEn;
    }

    public String getReturnCn() {
        return returnCn;
    }

    public void setReturnCn(String returnCn) {
        this.returnCn = returnCn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
