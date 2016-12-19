package com.pay.gateway.validate.rule.newrule;

import com.pay.fi.commons.CardOrgEnum;
import com.pay.gateway.dto.ValidateResponse;
import com.pay.gateway.model.Key;
import com.pay.gateway.model.ValidateObj;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public class CardRule implements InterfaceRule {
    @Override
    public ValidateResponse validate(Object bean, ValidateObj validateObj) {
        ValidateResponse response = new ValidateResponse();
        BeanWrapper wapper = new BeanWrapperImpl(bean);
        Object validValue = wapper.getPropertyValue(validateObj.getName());
        Map<String,Object> map =  null;
        if(validateObj instanceof Key){
            Key key = (Key) validateObj;
            map = (Map<String,Object>)wapper.getPropertyValue(key.getMapProperties());
            validValue = map.get(key.getName());
        }
        if(validValue != null){
            String cardNumber = validValue.toString();
            boolean pass = StringUtils.isNumeric(cardNumber);
            if(pass){
                String cardLimit = (String) wapper.getPropertyValue("cardLimit");
                if(null == cardLimit)
                    cardLimit = (String)map.get("cardLimit");
                if(null != cardLimit){
                    if(CardOrgEnum.VISA.getCode().equals(cardLimit)){
                        pass = cardNumber.startsWith("4");
                    }else if(CardOrgEnum.MASTERCARD.getCode().equals(cardLimit)){
                        pass = cardNumber.startsWith("5");
                    }
                }
            }
            response.setPass(pass);
        }
        if(!response.isPass()){
            response.setErrCode("0007");
            response.setErrMsgEn(validateObj.getName() + ": Please enter the correct number.");
            response.setErrMsgEn(validateObj.getName() + ": 请输入正确的银行卡号");
        }
        return response;
    }
}
