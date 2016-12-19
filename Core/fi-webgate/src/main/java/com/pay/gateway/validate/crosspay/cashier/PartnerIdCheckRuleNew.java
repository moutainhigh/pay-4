package com.pay.gateway.validate.crosspay.cashier;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.gateway.dto.CrosspayApiRequest;
import com.pay.gateway.dto.CrosspayApiResponse;
import com.pay.gateway.dto.CrosspayGatewayRequest;
import com.pay.gateway.dto.CrosspayGatewayResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Created by cuber on 2016/10/16.
 */
public class PartnerIdCheckRuleNew extends MessageRule {

    private MemberService memberService;
    private String messageEn;

    public String getMessageEn() {
        return messageEn;
    }

    public void setMessageEn(String messageEn) {
        this.messageEn = messageEn;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
     */
    @Override
    protected boolean makeDecision(Object validateBean) throws Exception {

        CrosspayGatewayRequest onlineRequestDTO = (CrosspayGatewayRequest) validateBean;
        CrosspayGatewayResponse onlineResponseDTO = onlineRequestDTO
                .getGatewayResponseDTO();

        String partnerId = onlineRequestDTO.getPartnerId();
        String language = onlineRequestDTO.getLanguage();

        if (StringUtil.isEmpty(partnerId) || partnerId.trim().length() > 32) {
            if ("cn".equals(language))
                onlineResponseDTO.setResultMsg(getMessage());
            else
                onlineResponseDTO.setResultMsg(getMessageEn());
            onlineResponseDTO.setResultCode(getMessageId());
            return false;
        }

        if (!StringUtil.isEmpty(partnerId) && StringUtils.isNumeric(partnerId.trim())) {
            MemberDto memberDto = memberService.queryMemberByMemberCode(Long
                    .valueOf(partnerId));
            if (memberDto == null) {
                if ("cn".equals(language))
                    onlineResponseDTO.setResultMsg(getMessage());
                else
                    onlineResponseDTO.setResultMsg(getMessageEn());
                onlineResponseDTO.setResultCode(getMessageId());
                return false;
            }
            return true;
        } else {
            if ("cn".equals(language))
                onlineResponseDTO.setResultMsg(getMessage());
            else
                onlineResponseDTO.setResultMsg(getMessageEn());
            onlineResponseDTO.setResultCode(getMessageId());
            return false;
        }
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
}