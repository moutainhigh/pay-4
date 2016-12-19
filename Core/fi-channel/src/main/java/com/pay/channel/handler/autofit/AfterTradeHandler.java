package com.pay.channel.handler.autofit;

import com.pay.channel.redis.model.ChannelObj;
import com.pay.channel.service.AutoFitEngineService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by eva on 2016/8/18.
 */
public class AfterTradeHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(getClass());
    private AutoFitEngineService autoFitEngineService;

    public void setAutoFitEngineService(AutoFitEngineService autoFitEngineService) {
        this.autoFitEngineService = autoFitEngineService;
    }

    @Override
    public String handle(String dataMsg) throws HessianInvokeException {
        Map<String, Object> paraMap = null;
        Map resultMap = new HashMap();

        try {
            paraMap = JSonUtil.toObject(dataMsg,
                    new HashMap<String, String>().getClass());
            logger.info("get Data:" + paraMap);
            ChannelObj obj = new ChannelObj();
            String memberCurrentId = (String)paraMap.get("memberCurrentId");
            String paymentChannelItemId = (String)paraMap.get("paymentChannelItemId");
            String payAmount = (String)paraMap.get("payAmount");
            String success = (String)paraMap.get("success");
            String channelConfigId = (String)paraMap.get("channelConfigId");
            String partnerId = (String)paraMap.get("partnerId");
            String failure = (String)paraMap.get("failure");
            obj.setFailure(failure);
            if(!StringUtil.isEmpty(memberCurrentId) && StringUtil.isNumber(memberCurrentId)){
                obj.setId(new BigDecimal(memberCurrentId));
            }
            if(!StringUtil.isEmpty(paymentChannelItemId) && StringUtil.isNumber(paymentChannelItemId)){
                obj.setPaymentChannelItemId(new BigDecimal(paymentChannelItemId));
            }
            if(!StringUtil.isEmpty(channelConfigId) && StringUtil.isNumber(channelConfigId)){
                obj.setChannelConfigId(new BigDecimal(channelConfigId));
            }
            if(!StringUtil.isEmpty(partnerId) && StringUtil.isNumber(partnerId)){
                obj.setPartnerId(new BigDecimal(partnerId));
            }
            if(!StringUtil.isEmpty(payAmount) && StringUtil.isNumber(payAmount)){
                obj.setAmount(new BigDecimal(payAmount));
            }
            obj.setDealType("1");
            obj.setSubDealType("1".equals(success)?"1":"2");
            autoFitEngineService.product2Redis(obj);
        }catch (Exception e){
            logger.error("error:", e);
            resultMap.put("responseCode",
                    ResponseCodeEnum.UNDEFINED_ERROR.getCode());
            resultMap.put("responseDesc",
                    ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
        }
        return JSonUtil.toJSonString(resultMap);
    }
}
