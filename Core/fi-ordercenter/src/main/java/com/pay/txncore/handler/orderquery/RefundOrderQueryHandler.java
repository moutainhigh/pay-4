/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

/**
 * 退款查询
 * 
 * @author chma
 */
public class RefundOrderQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(RefundOrderQueryHandler.class);
	private RefundOrderService refundOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			ChannelOrderDTO channelOrderDTO = MapUtil.map2Object(
					ChannelOrderDTO.class, paraMap);
			Map pageMap = (Map) paraMap.get("page");
			
			

			String partnerId = (String) paraMap.get("partnerId");
			String paymentOrderNo = (String) paraMap.get("paymentOrderNo");
			String tradeOrderNo = (String) paraMap.get("tradeOrderNo");
			String refundOrderNo = (String) paraMap.get("refundOrderNo");
			String partnerRefundOrderId = (String) paraMap
					.get("partnerRefundOrderId");
			String status = (String) paraMap.get("status");
			String beginTime = (String) paraMap.get("beginTime");
			String endTime = (String) paraMap.get("endTime");
			String orderId = (String) paraMap.get("orderId");
			String orgCode = (String) paraMap.get("orgCode");
			
			String compStartTime = (String) paraMap.get("compStartTime");
			String compEndTime = (String) paraMap.get("compEndTime");
			String reconciliationFlg = (String)paraMap.get("reconciliationFlg");
			String bankCode= (String)paraMap.get("bankCode");
			String ctvReturnNo= (String)paraMap.get("ctvReturnNo");
			
			String channelOrderNo = null == paraMap.get("channelOrderNo")?"":paraMap.get("channelOrderNo").toString() ;
			
			Page page = null;
			
			if(pageMap!=null){
				page = MapUtil.map2Object(Page.class, pageMap);
			}

			RefundOrderDTO refundOrderDTO = new RefundOrderDTO();

			refundOrderDTO.setPartnerId(partnerId);
			
			
			
			if (!StringUtil.isEmpty(paymentOrderNo)
					&& NumberUtil.isNumber(paymentOrderNo)) {
				refundOrderDTO.setPaymentOrderNo(Long.valueOf(paymentOrderNo));
			}
			if (!StringUtil.isEmpty(tradeOrderNo)
					&& NumberUtil.isNumber(tradeOrderNo)) {
				refundOrderDTO.setTradeOrderNo(Long.valueOf(tradeOrderNo));
			}
			if (!StringUtil.isEmpty(refundOrderNo)
					&& NumberUtil.isNumber(refundOrderNo)) {
				refundOrderDTO.setRefundOrderNo(Long.valueOf(refundOrderNo));
			}
			
			refundOrderDTO.setPartnerRefundOrderId(partnerRefundOrderId);
			
			if (!StringUtil.isEmpty(status)) {
				refundOrderDTO.setStatus(status);
			}
			
			if (!StringUtil.isEmpty(orgCode)) {
				refundOrderDTO.setOrgCode(orgCode);
			}
			
			if (!StringUtil.isEmpty(beginTime)) {
				refundOrderDTO.setBeginTime(beginTime);
			}
			
			if (!StringUtil.isEmpty(endTime)) {
				refundOrderDTO.setEndTime(endTime);
			}
			
			if(!StringUtil.isEmpty(orderId)){
				refundOrderDTO.setOrderId(orderId);
			}
			if (!StringUtil.isEmpty(compStartTime)) {//完成时间
				refundOrderDTO.setCompStartTime(compStartTime);
			}
			if (!StringUtil.isEmpty(compEndTime)) {
				refundOrderDTO.setCompEndTime(compEndTime);
			}
			if(!StringUtil.isEmpty(channelOrderNo)){
				refundOrderDTO.setChannelOrderNo(Long.valueOf(channelOrderNo));
			}
			
			if(!StringUtil.isEmpty(reconciliationFlg)){
				refundOrderDTO.setReconciliationFlg(Integer.valueOf(reconciliationFlg));
				//logger.info("reconciliationFlag is not null" + refundOrderDTO.getReconciliationFlg());
			}
			
			if(!StringUtil.isEmpty(bankCode)){
				refundOrderDTO.setRefundOrgCode(bankCode);
			}
			
			if(!StringUtil.isEmpty(ctvReturnNo)){
				refundOrderDTO.setChannelReturnNo(ctvReturnNo);
			}
			
			List<RefundOrderDTO> refundOrders = null;
					
			if(page==null){
				
				refundOrders = refundOrderService
						.findRefundOrder(refundOrderDTO);
			}else{
				refundOrders = refundOrderService
						.findRefundOrder(refundOrderDTO, page);
			}
			
			//logger.info("refundOrders" + refundOrders);
			
			resultMap.put("result", refundOrders);
			
			logger.info("page: "+page);
			resultMap.put("page", page);
		} catch (Exception e) {
			logger.error("query error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}

}
