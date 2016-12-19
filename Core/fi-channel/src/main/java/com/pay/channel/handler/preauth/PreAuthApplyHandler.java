package com.pay.channel.handler.preauth;

import com.pay.channel.dto.ChannelResponseDto;
import com.pay.channel.dto.PaymentInfo;
import com.pay.channel.service.ChannelService;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuber on 2016/9/1.
 */
public class PreAuthApplyHandler implements EventHandler {

    public final Log logger = LogFactory.getLog(PreAuthApplyHandler.class);
    private ChannelService channelService;

    @Override
    public String handle(String dataMsg) throws HessianInvokeException {

        Map resultMap = new HashMap();


        Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
                new HashMap<String, String>().getClass());


        PaymentInfo channelRequest = MapUtil.map2Object(PaymentInfo.class,
                paraMap);

        try {
            ChannelResponseDto channelResponseDto = channelService
                    .preAuthApply(channelRequest);

            resultMap.putAll(MapUtil.bean2map(channelResponseDto));
        } catch (BusinessException e) {
            logger.error("ChannelPayHandler error:", e);
            ExceptionCodeEnum error = e.getCode();
            resultMap.put("responseCode", error.getCode());
            resultMap.put("responseDesc", error.getDescription());
        } catch (Exception e) {
            logger.error("ChannelPayHandler error:", e);
            resultMap.put("responseCode",
                    ResponseCodeEnum.UNDEFINED_ERROR.getCode());
            resultMap.put("responseDesc",
                    ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
            return JSonUtil.toJSonString(resultMap);
        }

        return JSonUtil.toJSonString(resultMap);
    }

    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }
}