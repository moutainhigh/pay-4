package com.pay.channel.handler.preauth;

import com.pay.channel.dao.PaymentChannelItemDAO;
import com.pay.channel.dto.ChannelRequestDto;
import com.pay.channel.dto.ChannelResponseDto;
import com.pay.channel.model.PaymentChannelItem;
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
public class PreAuthProcessHandler implements EventHandler {

    public final Log logger = LogFactory.getLog(PreAuthProcessHandler.class);
    private ChannelService channelService;
    private PaymentChannelItemDAO paymentChannelItemDAO;

    public void setPaymentChannelItemDAO(PaymentChannelItemDAO paymentChannelItemDAO) {
        this.paymentChannelItemDAO = paymentChannelItemDAO;
    }

    @Override
    public String handle(String dataMsg) throws HessianInvokeException {

        Map resultMap = new HashMap();


        Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
                    new HashMap<String, String>().getClass());


        String orgiReturnNo = paraMap.get("origReturnNo");
        String orgiChannelOrderNo = paraMap.get("origChannelOrderNo");
        String modelType =  paraMap.get("modelType");
        String paymentChannelId = paraMap.get("paymentChannelId");
        ChannelRequestDto channelRequest = MapUtil.map2Object(ChannelRequestDto.class,
                paraMap);
        channelRequest.setMerchantNo(paraMap.get("orgMerchantCode"));
        try {
            PaymentChannelItem  paymentChannelItem = paymentChannelItemDAO.findById(Long.parseLong(paymentChannelId));
            ChannelResponseDto channelResponseDto = null;
            if("1".equals(modelType)){//capture
                channelResponseDto = channelService
                        .preAuthCapture(paymentChannelItem,  channelRequest,  orgiChannelOrderNo,  orgiReturnNo);
            }else{//viod
                channelResponseDto = channelService
                        .preAuthVoid(paymentChannelItem,  channelRequest,  orgiChannelOrderNo,  orgiReturnNo);
            }
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
