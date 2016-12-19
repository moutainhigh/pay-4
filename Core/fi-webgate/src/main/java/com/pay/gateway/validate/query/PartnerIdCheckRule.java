package com.pay.gateway.validate.query;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.gateway.dto.CrosspayRequest;
import com.pay.gateway.dto.CrosspayResponse;
import com.pay.gateway.dto.OrderApiQueryRequest;
import com.pay.gateway.dto.OrderApiQueryResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Created by cuber on 2016/10/16.
 */
public class PartnerIdCheckRule extends MessageRule {

    private MemberService memberService;
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

        String partnerId = orderApiQueryRequest.getPartnerId();

        if(StringUtil.isEmpty(partnerId) || partnerId.trim().length() > 32) {
            orderApiQueryResponse.setResultCode(getMessageId());
            orderApiQueryResponse.setResultMsg(getMessage());
            return false;
        }

        if (!StringUtil.isEmpty(partnerId) && StringUtils.isNumeric(partnerId.trim()) ) {
            try{
                MemberDto memberDto = memberService.queryMemberByMemberCode(Long
                        .valueOf(partnerId));//
                if(memberDto==null){
                    orderApiQueryResponse.setResultCode(getMessageId());
                    orderApiQueryResponse.setResultMsg(getMessage());
                    return false;
                }
                return true;
            }catch (Exception e){
                orderApiQueryResponse.setResultCode(getMessageId());
                orderApiQueryResponse.setResultMsg(getMessage());
                return false;
            }
        } else {
            orderApiQueryResponse.setResultCode(getMessageId());
            orderApiQueryResponse.setResultMsg(getMessage());
            return false;
        }
    }
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    public static void main(String [] args) {
        System.out.println(Long.valueOf("10000003671"));
    }
}
