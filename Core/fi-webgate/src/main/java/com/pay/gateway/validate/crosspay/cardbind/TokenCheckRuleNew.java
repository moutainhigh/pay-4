package com.pay.gateway.validate.crosspay.cardbind;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.gateway.dto.CardBindRequest;
import com.pay.gateway.dto.CardBindResponse;
import com.pay.inf.rule.MessageRule;
import com.pay.util.StringUtil;

/**
 * Created by cuber on 2016/9/19.
 */
public class TokenCheckRuleNew extends MessageRule {

    /*
     * (non-Javadoc)
     *
     * @see com.pay.ruleengine.AbstractRule#makeDecision(java.lang.Object)
     */
    @Override
    protected boolean makeDecision(Object validateBean) throws Exception {
        CardBindRequest cardBindRequest = (CardBindRequest) validateBean;
        CardBindResponse cardBindResponse = cardBindRequest.getCardBindResponse();

        String tradeType = cardBindRequest.getTradeType();
        String token = cardBindRequest.getToken();

        if(TradeTypeEnum.CARD_UNBIND.getCode().equals(tradeType)){
            if(StringUtil.isEmpty(token) || token.trim().length() > 32){
                cardBindResponse.setResultCode(getMessageId());
                cardBindResponse.setResultMsg(getMessage());
                return false;
            }
        }

        if(!StringUtil.isEmpty(token) && token.trim().length() > 32){
            cardBindResponse.setResultCode(getMessageId());
            cardBindResponse.setResultMsg(getMessage());
            return false;
        }

        return true;
    }
}
