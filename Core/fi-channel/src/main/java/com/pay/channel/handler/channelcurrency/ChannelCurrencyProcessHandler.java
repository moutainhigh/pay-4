package com.pay.channel.handler.channelcurrency;

import com.pay.channel.service.ChannelCurrencyService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuber on 2016/9/24.
 */
public class ChannelCurrencyProcessHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(ChannelCurrencyProcessHandler.class);

    public void setChannelCurrencyService(ChannelCurrencyService channelCurrencyService) {
        this.channelCurrencyService = channelCurrencyService;
    }

    private ChannelCurrencyService channelCurrencyService;
    @Override
    public String handle(String dataMsg) throws HessianInvokeException {

        Map<String, Object> paraMap = null;
        Map resultMap = new HashMap();
        try {
            paraMap = JSonUtil.toObject(dataMsg,
                    new HashMap<String, String>().getClass());
            channelCurrencyService.processChannelCurrencyRev(paraMap);
            resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
            resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
        } catch (Exception e) {
            logger.error("error:", e);
            resultMap.put("responseCode",
                    ResponseCodeEnum.UNDEFINED_ERROR.getCode());
            resultMap.put("responseDesc", e.getMessage());
        }
        return JSonUtil.toJSonString(resultMap);
    }
}
