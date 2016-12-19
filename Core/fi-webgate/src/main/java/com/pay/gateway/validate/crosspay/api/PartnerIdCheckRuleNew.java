package com.pay.gateway.validate.crosspay.api;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.gateway.dto.CrosspayApiRequest;
import com.pay.gateway.dto.CrosspayApiResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Created by cuber on 2016/10/16.
 */
public class PartnerIdCheckRuleNew extends MessageRule {

    private MemberService memberService;

    /*
     * (non-Javadoc)
     *
     * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
     */
    @Override
    protected boolean makeDecision(Object validateBean) throws Exception {

        CrosspayApiRequest crosspayApiRequest = (CrosspayApiRequest) validateBean;
        CrosspayApiResponse crosspayApiResponse = crosspayApiRequest
                .getCrosspayApiResponse();

        String partnerId = crosspayApiRequest.getPartnerId();

        if (StringUtil.isEmpty(partnerId) || partnerId.trim().length() > 32) {
            crosspayApiResponse.setResultCode(getMessageId());
            crosspayApiResponse.setResultMsg(getMessage());
            return false;
        }

        if (!StringUtil.isEmpty(partnerId) && StringUtils.isNumeric(partnerId.trim())) {
            MemberDto memberDto = memberService.queryMemberByMemberCode(Long
                    .valueOf(partnerId));
            if (memberDto == null) {
                crosspayApiResponse.setResultCode(getMessageId());
                crosspayApiResponse.setResultMsg(getMessage());
                return false;
            }
            return true;
        } else {
            crosspayApiResponse.setResultCode(getMessageId());
            crosspayApiResponse.setResultMsg(getMessage());
            return false;
        }
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
}

