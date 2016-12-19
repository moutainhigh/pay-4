package com.pay.txncore.dto;

import com.googlecode.aviator.AviatorEvaluator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Created by cuber.huang on 2016/7/27.
 */
public class Condition {
    private String expression;
    private String returnCode;
    private String returnMsg;
    private String returnMsgCn;
    private String selfMsg;

    public String getSelfMsg() {
        return selfMsg;
    }

    public void setSelfMsg(String selfMsg) {
        this.selfMsg = selfMsg;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        expression = StringUtils.replace(expression,"&amp;","&");
        expression = StringUtils.replace(expression,"&lt;","<");
        expression = StringUtils.replace(expression,"&gt;;",">");
        expression = StringUtils.replace(expression,"&quot;","\"");
        this.expression = expression;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getReturnMsgCn() {
        return returnMsgCn;
    }

    public void setReturnMsgCn(String returnMsgCn) {
        this.returnMsgCn = returnMsgCn;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public static void main(String[] args){
        System.out.print(AviatorEvaluator.exec("string.length(Code)==8 &&'0000'==string.substring(Code,4)","000"));
    }
}
