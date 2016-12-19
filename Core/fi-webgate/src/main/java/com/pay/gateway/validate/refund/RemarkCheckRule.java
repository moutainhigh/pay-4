package com.pay.gateway.validate.refund;

import com.pay.gateway.dto.refund.RefundApiRequest;
import com.pay.gateway.dto.refund.RefundApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * Created by cuber on 2016/10/16.
 */
public class RemarkCheckRule  extends MessageRule {

    /*
     * (non-Javadoc)
     *
     * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
     */
    @Override
    protected boolean makeDecision(Object validateBean) throws Exception {

        RefundApiRequest refundApiRequest = (RefundApiRequest) validateBean;
        RefundApiResponse refundApiResponse = refundApiRequest
                .getRefundApiResponse();

        String remark = refundApiRequest.getRemark();

        if (StringUtil.isEmpty(remark) || remark.length() <= 256) {
            return true;
        } else {
            refundApiResponse.setResultCode(getMessageId());
            refundApiResponse.setResultMsg(getMessage());
            return false;
        }

    }

}
