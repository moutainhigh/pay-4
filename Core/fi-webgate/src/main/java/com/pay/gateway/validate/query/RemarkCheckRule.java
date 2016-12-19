package com.pay.gateway.validate.query;

import com.pay.gateway.dto.OrderApiQueryRequest;
import com.pay.gateway.dto.OrderApiQueryResponse;
import com.pay.gateway.dto.refund.RefundApiRequest;
import com.pay.gateway.dto.refund.RefundApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * Created by cuber on 2016/10/16.
 */
public class RemarkCheckRule extends MessageRule {

    /*
     * (non-Javadoc)
     *
     * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
     */
    @Override
    protected boolean makeDecision(Object validateBean) throws Exception {

        OrderApiQueryRequest orderApiQueryRequest = (OrderApiQueryRequest) validateBean;
        OrderApiQueryResponse orderApiQueryResponse = orderApiQueryRequest
                .getOrderApiQueryResponse();


        String remark = orderApiQueryRequest.getRemark();

        if (StringUtil.isEmpty(remark) || remark.length() <= 256) {
            return true;
        } else {
            orderApiQueryResponse.setResultCode(getMessageId());
            orderApiQueryResponse.setResultMsg(getMessage());
            return false;
        }

    }

}
