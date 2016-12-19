package com.pay.txncore.model;

import java.util.Date;

/**
 * Created by cuber on 2016/9/1.
 */
public class AuthChangeOrder {
    private long id;
    private long preControllerId;
    private String channelResponseNo;
    private String channelResponseCode;
    private String channelResponseDesc;
    private String requestId;
    private Date createDate;
    private String channgeType;
    private String status;
    private String bizCode;
    private String bizMsg;
    private Date completeDate;


    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPreControllerId() {
        return preControllerId;
    }

    public void setPreControllerId(long preControllerId) {
        this.preControllerId = preControllerId;
    }

    public String getChannelResponseNo() {
        return channelResponseNo;
    }

    public void setChannelResponseNo(String channelResponseNo) {
        this.channelResponseNo = channelResponseNo;
    }

    public String getChannelResponseCode() {
        return channelResponseCode;
    }

    public void setChannelResponseCode(String channelResponseCode) {
        this.channelResponseCode = channelResponseCode;
    }

    public String getChannelResponseDesc() {
        return channelResponseDesc;
    }

    public void setChannelResponseDesc(String channelResponseDesc) {
        this.channelResponseDesc = channelResponseDesc;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getChanngeType() {
        return channgeType;
    }

    public void setChanngeType(String channgeType) {
        this.channgeType = channgeType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public String getBizMsg() {
        return bizMsg;
    }

    public void setBizMsg(String bizMsg) {
        this.bizMsg = bizMsg;
    }

    public Date getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Date completeDate) {
        this.completeDate = completeDate;
    }
}
