package com.pay.gateway.validate.rule.newrule;

import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.ValidateResponse;
import com.pay.gateway.model.Key;
import com.pay.gateway.model.ValidateObj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public class TradeOrdeRule implements InterfaceRule {

    private static Logger logger = LoggerFactory.getLogger(TradeOrdeRule.class);
    private TxncoreClientService txncoreClientService;

    public void setTxncoreClientService(
            TxncoreClientService txncoreClientService) {
        this.txncoreClientService = txncoreClientService;
    }
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
        boolean pass = false;
        if(null != validValue){
            try{
                String tradeOrderId = (String)validValue;
                Map<String,String> params = new HashMap<String, String>();
                params.put("orderId",(String)wapper.getPropertyValue("orderId"));
                params.put("partnerId",(String)wapper.getPropertyValue("orderId"));
                params.put("status","0");
                params.put("tradeOrderNo",tradeOrderId);

                Map map = txncoreClientService.completedOrderQuery(params);

                String hasTradeOrder = (String) map.get("hasTradeOrder");
                if("0".equals(hasTradeOrder)){
                    response.setPass(false);
                    response.setErrMsgCn("不存在该订单");
                }else{
                    Map<String,String> mapInfo = (Map<String, String>) map.get("mapInfo");

                    String orderAmount_ = (String) mapInfo.get("orderAmount");
                    String currencyCode_ = (String) mapInfo.get("currencyCode");
                    String status = (String) mapInfo.get("status");
                    String orderAmount = (String)wapper.getPropertyValue("orderAmount");
                    String currencyCode = (String)wapper.getPropertyValue("currencyCode");

                    BigDecimal oldAmount = new BigDecimal(orderAmount_);
                    BigDecimal newAmount = new BigDecimal(orderAmount)
                            .multiply(new BigDecimal("10"));
                    //满足了这些条件才可以提交成功
                    if(oldAmount.compareTo(newAmount)==0&&currencyCode_.equals(currencyCode)
                            &&"0".equals(status)){
                        response.setPass(true);
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
                response.setPass(false);
            }
        }
        if(!response.isPass()){
            response.setErrMsgCn("订单校验失败");
            response.setErrMsgEn("the order verification failed");
            response.setErrCode("0054");
        }
        return response;
    }
}
