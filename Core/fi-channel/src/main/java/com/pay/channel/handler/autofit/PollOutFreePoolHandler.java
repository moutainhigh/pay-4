package com.pay.channel.handler.autofit;

import com.pay.channel.redis.model.ChannelObj;
import com.pay.channel.service.AutoFitEngineService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eva on 2016/8/17.
 */
public class PollOutFreePoolHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(getClass());
    private AutoFitEngineService autoFitEngineService;

    public void setAutoFitEngineService(AutoFitEngineService autoFitEngineService) {
        this.autoFitEngineService = autoFitEngineService;
    }

    @Override
    public String handle(String dataMsg) throws HessianInvokeException {
        Map paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
        String id = (String) paraMap.get("id");
        Map resultMap = new HashMap();
        try{
            ChannelObj obj = new ChannelObj();
            obj.setDealType("3");
            obj.setSubDealType("2");
            obj.setChannelConfigId(new BigDecimal(id));
            autoFitEngineService.product2Redis(obj);
            resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
            resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
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
