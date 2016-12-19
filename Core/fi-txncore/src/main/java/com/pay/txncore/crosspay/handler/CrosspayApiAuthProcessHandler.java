package com.pay.txncore.crosspay.handler;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.service.preauth.PreAuth2Service;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuber on 2016/9/1.
 */
public class CrosspayApiAuthProcessHandler implements EventHandler {

    public final Log logger = LogFactory.getLog(getClass());
    private PreAuth2Service preAuth2Service;

    public void setPreAuth2Service(PreAuth2Service preAuth2Service) {
        this.preAuth2Service = preAuth2Service;
    }

    @Override
    public String handle(String dataMsg) throws HessianInvokeException {
        Map<String, String> paraMap = null;
        Map resultMap = new HashMap();
        try {
            paraMap = JSonUtil.toObject(dataMsg,
                    new HashMap<String, String>().getClass());
            logger.info("txncore preAuthProcessLog:" + dataMsg);
            PaymentInfo paymentInfo = MapUtil.map2Object(PaymentInfo.class,
                    paraMap);
            String processType = paraMap.get("processType");
            PaymentResult paymentResult =  null;
            if("1".equals(processType)){
                paymentInfo.setTradeType("2100");
                paymentResult = preAuth2Service.preAuthCapture(paymentInfo);
            }else{
                String requestId = paraMap.get("requestId");
                if(!StringUtil.isEmpty(requestId)){
                    paymentInfo.setOrderId(requestId);
                }
                paymentResult = preAuth2Service.preAuthVoid(paymentInfo);
            }

            resultMap.putAll(MapUtil.bean2map(paymentResult));
            //将真实的渠道返回码返回, 供失败订单根据渠道返回码捕获异常卡使用
            resultMap.put("orgCode", paymentResult.getOrgCode()) ;
            resultMap.put("channelRespCode", paymentResult.getChannelRespCode()) ;
            resultMap.put("responseCode", paymentResult.getResponseCode());
            resultMap.put("responseDesc", paymentResult.getResponseDesc());
        } catch (BusinessException e) {
            logger.error("CrosspayApiPreAuthHandler error:", e);
            ExceptionCodeEnum error = e.getCode();
            resultMap.put("responseCode", error.getCode());
            resultMap.put("responseDesc", error.getDescription());
        } catch (Exception e) {
            logger.error("api author error:", e);
            resultMap.put("responseCode",
                    ResponseCodeEnum.UNDEFINED_ERROR.getCode());
            resultMap.put("responseDesc",
                    ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
        }
        return JSonUtil.toJSonString(resultMap);
    }
}
