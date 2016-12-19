package com.pay.gateway.validate.rule.newrule;

import com.pay.gateway.dto.ValidateResponse;
import com.pay.gateway.model.Key;
import com.pay.gateway.model.ValidateObj;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public class DateRule implements InterfaceRule {

    @Override
    public ValidateResponse validate(Object bean, ValidateObj validateObj) {
        ValidateResponse response = new ValidateResponse();
        BeanWrapper wapper = new BeanWrapperImpl(bean);
        Object validValue = wapper.getPropertyValue(validateObj.getName());
        if(validateObj instanceof Key){
            Key key = (Key) validateObj;
            Map<String,Object> map = (Map<String,Object>)wapper.getPropertyValue(key.getMapProperties());
            validValue = map.get(key.getName());
        }
        if("MM".equals(validateObj.getDateType())){
            if(validValue != null){
                boolean pass = validValue.toString().length() == 2 && StringUtils.isNumeric(validValue.toString());
                pass = pass && Integer.parseInt(validValue.toString()) > 0 && Integer.parseInt(validValue.toString()) >=12;
                response.setPass(pass);
            }
        }else{
            String validStr = (String)validValue;
            try{
                SimpleDateFormat sdf = new SimpleDateFormat(validateObj.getDateType());
                boolean pass = validStr.length() == validateObj.getDateType().length();
                if(pass){
                    sdf.parse(validStr);
                }
                response.setPass(pass);
            }catch (Exception e){
                response.setPass(false);
            }
        }
        if(!response.isPass()){
            response.setErrCode("0008");
            response.setErrMsgCn(validateObj.getName() + ": 格式不对");
            response.setErrMsgEn(validateObj.getName() + ": data type does not match");
        }
        return response;
    }
}
