package com.pay.txncore.crosspay.handler;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.newpaymentprocess.processvo.BackfromChannelVo;
import com.pay.txncore.service.AllChannelPayService;
import com.pay.txncore.service.CashierPayService;
import com.pay.txncore.service.PaymentService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/24.
 */
public class CashierAllPayHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(getClass());
    private PaymentService paymentService;
    private AllChannelPayService allChannelPayService;
    @Override
    public String handle(String dataMsg) throws HessianInvokeException {
        Map<String, Object> paraMap = JSonUtil.toObject(dataMsg,
                new HashMap<String, Object>().getClass());
        Map resultMap = new HashMap();

        PaymentResult paymentResult = new PaymentResult();
        PaymentInfo paymentInfo = MapUtil
                .map2Object(PaymentInfo.class, paraMap);
        BeanUtils.copyProperties(paymentInfo, paymentResult, new String[] {
                "tradeOrderNo", "paymentOrderNo", "channelOrderNo" });
        try {
            paymentResult = allChannelPayService.dispatchPay(paymentInfo);
            //将真实的渠道返回码返回, 供失败订单根据渠道返回码捕获异常卡使用 added by PengJiangbo
            resultMap.put("orgCode", paymentResult.getOrgCode()) ;
            resultMap.put("channelRespCode", paymentResult.getChannelRespCode()) ;
            resultMap.put("responseCode", paymentResult.getResponseCode());
            resultMap.put("responseDesc", paymentResult.getResponseDesc());
            resultMap.put("merchantBillName",paymentResult.getMerchantBillName());
            TradeOrderDTO tradeOrderDTO = paymentService.getTradeOrderService()
                    .queryTradeOrderById(Long.valueOf(paymentInfo.getTradeOrderNo()));
            TradeBaseDTO tradeBaseDTO = paymentService.getTradeOrderService()
                    .queryTradeBaseById(tradeOrderDTO.getTradeBaseNo());
            resultMap.putAll(MapUtil.bean2map(tradeOrderDTO));
            resultMap.putAll(MapUtil.bean2map(tradeBaseDTO));
            Map paymentResultMap = MapUtil.bean2map(paymentResult);
            resultMap.putAll(paymentResultMap);
            if(null != paymentResult && null!=paymentResult.getReturnfromFront()){
                BackfromChannelVo  vo = paymentResult.getReturnfromFront();
                resultMap.put("responseCode", vo.getReturnCode());
                resultMap.put("responseDesc", vo.getReturnDesc());
                resultMap.put("backfromFront",MapUtil.bean2map(vo));
            }
            resultMap.put("returnUrl", tradeBaseDTO.getReturnUrl());
            resultMap.put("noticeUrl", tradeBaseDTO.getNotifyUrl());
        } catch (BusinessException e) {
            logger.error("CrosspayCashierPayHandler error:", e);
            ExceptionCodeEnum error = e.getCode();
            resultMap.put("responseCode", error.getCode());
            resultMap.put("responseDesc", error.getDescription());
        } catch (Exception e) {
            logger.error("CrosspayCashierPayHandler error:", e);
            resultMap.put("responseCode",
                    ResponseCodeEnum.UNDEFINED_ERROR.getCode());
            resultMap.put("responseDesc", "Other Error:其他异常");
        }



        return JSonUtil.toJSonString(resultMap);
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void setAllChannelPayService(AllChannelPayService allChannelPayService) {
        this.allChannelPayService = allChannelPayService;
    }
}
