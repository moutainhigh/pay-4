package com.pay.txncore.newpaymentprocess.processvo;

import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/24.
 */
public class BackfromChannelVo {
    private  String  channelReturnId;//渠道返回的ID
    private String showProcessOrSucess;//显示订单成功还是订单在交易中
    private String returnCode;//返回code, 0000.或者交易中，    gateWay根据这个返回页面
    private String  returnDesc;//返回的描述
    private String continueUrl;//继续提交的地址，
    private Map<String, String> submitData;//继续提交的数据

    public Map<String, String> getSubmitData() {
        return submitData;
    }

    public void setSubmitData(Map<String, String> submitData) {
        this.submitData = submitData;
    }

    public String getChannelReturnId() {
        return channelReturnId;
    }

    public void setChannelReturnId(String channelReturnId) {
        this.channelReturnId = channelReturnId;
    }

    public String getShowProcessOrSucess() {
        return showProcessOrSucess;
    }

    public void setShowProcessOrSucess(String showProcessOrSucess) {
        this.showProcessOrSucess = showProcessOrSucess;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnDesc() {
        return returnDesc;
    }

    public void setReturnDesc(String returnDesc) {
        this.returnDesc = returnDesc;
    }

    public String getContinueUrl() {
        return continueUrl;
    }

    public void setContinueUrl(String continueUrl) {
        this.continueUrl = continueUrl;
    }
}
