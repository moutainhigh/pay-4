package com.pay.gateway.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public class ValidateResponse {
    private boolean pass = true;
    private String errCode;
    private String errMsgCn;
    private String errMsgEn;

    public boolean isPass() {
        return pass;
    }

    public void setPass(boolean pass) {
        this.pass = pass;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsgCn() {
        return errMsgCn;
    }

    public void setErrMsgCn(String errMsgCn) {
        this.errMsgCn = errMsgCn;
    }

    public String getErrMsgEn() {
        return errMsgEn;
    }

    public void setErrMsgEn(String errMsgEn) {
        this.errMsgEn = errMsgEn;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
