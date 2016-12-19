package com.pay.gateway.validate.rule.newrule;

import com.pay.gateway.dto.ValidateResponse;
import com.pay.gateway.model.Key;
import com.pay.gateway.model.ValidateObj;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public class NotNullRule implements InterfaceRule {
    @Override
    public ValidateResponse validate(Object bean, ValidateObj validateObj) {
        ValidateResponse response = new ValidateResponse();
        BeanWrapper wapper = new BeanWrapperImpl(bean);
        if(validateObj instanceof Key){
            Key key = (Key) validateObj;
            Map<String,Object> map = (Map<String,Object>)wapper.getPropertyValue(key.getMapProperties());
            response.setPass(null != map.get(key.getName()));
        }else{
            response.setPass(null != wapper.getPropertyValue(validateObj.getName()));
        }
        if(!response.isPass()){
            response.setErrCode("0009");
            response.setErrMsgCn(validateObj.getName() + ":不能为空");
            response.setErrMsgEn(validateObj.getName() + ": can't be null");
        }
        return response;
    }
}
