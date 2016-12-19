/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.crosspay.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.fi.commons.TradeTypeEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.service.CardBindService;
import com.pay.txncore.service.CashierPayService;
import com.pay.txncore.service.PaymentService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 跨境收银台支付
 * 
 * @author chma
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class CrosspayCashierPayHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private PaymentService paymentService;
	private CashierPayService cashierPayService;
	private CardBindService cardBindService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());
		Map resultMap = new HashMap();
		
		PaymentResult paymentResult = new PaymentResult();
		PaymentInfo paymentInfo = MapUtil
				.map2Object(PaymentInfo.class, paraMap);
		BeanUtils.copyProperties(paymentInfo, paymentResult, new String[] {
				"tradeOrderNo", "paymentOrderNo", "channelOrderNo" });
		try {
			//如果是TOKEN绑卡并支付时，调用
			if(TradeTypeEnum.TOKEN_CARD_BIND_CASH.getCode().equals(paymentInfo.getTradeType())){
				//请求绑卡支付
				paymentResult = cardBindService.cashierPayAndBind(paymentInfo);
			}else{
				paymentResult = cashierPayService.crossCashierPay(paymentInfo);
			}
			//将真实的渠道返回码返回, 供失败订单根据渠道返回码捕获异常卡使用 added by PengJiangbo
			resultMap.put("orgCode", paymentResult.getOrgCode()) ;
			resultMap.put("channelRespCode", paymentResult.getChannelRespCode()) ;
			resultMap.put("responseCode", paymentResult.getResponseCode());
			resultMap.put("responseDesc", paymentResult.getResponseDesc());
			resultMap.put("merchantBillName",paymentResult.getMerchantBillName());
		} catch (BusinessException e) {
			logger.error("CrosspayCashierPayHandler error:", e);
			ExceptionCodeEnum error = e.getCode();
			resultMap.put("responseCode", error.getCode());
			resultMap.put("responseDesc", error.getDescription());
		} catch (Exception e) {
			logger.error("CrosspayCashierPayHandler error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}

		TradeOrderDTO tradeOrderDTO = paymentService.getTradeOrderService()
				.queryTradeOrderById(Long.valueOf(paymentInfo.getTradeOrderNo()));
		TradeBaseDTO tradeBaseDTO = paymentService.getTradeOrderService()
				.queryTradeBaseById(tradeOrderDTO.getTradeBaseNo());

		resultMap.putAll(MapUtil.bean2map(tradeOrderDTO));
		resultMap.putAll(MapUtil.bean2map(tradeBaseDTO));

		Map paymentResultMap = MapUtil.bean2map(paymentResult);
		resultMap.putAll(paymentResultMap);
		resultMap.put("returnUrl", tradeBaseDTO.getReturnUrl());
		resultMap.put("noticeUrl", tradeBaseDTO.getNotifyUrl());

		return JSonUtil.toJSonString(resultMap);
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public void setCashierPayService(CashierPayService cashierPayService) {
		this.cashierPayService = cashierPayService;
	}

	public void setCardBindService(CardBindService cardBindService) {
		this.cardBindService = cardBindService;
	}
	
}
