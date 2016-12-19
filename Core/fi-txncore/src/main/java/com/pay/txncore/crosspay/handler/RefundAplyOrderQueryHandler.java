/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.crosspay.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.service.refund.RefundService;
import com.pay.util.JSonUtil;

/**
 * 
 * 
 * @author chma
 */
public class RefundAplyOrderQueryHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private RefundService refundService;
	public void setRefundService(RefundService refundService) {
		this.refundService = refundService;
	}



	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();
		
		logger.info("dataMsg: "+dataMsg);

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, String>().getClass());

			String refundOrderId = paraMap.get("refundOrderId");
			String partnerId = paraMap.get("partnerId");
			
			
			
			Map<String,String> params = new HashMap<String, String>();
			params.put("partnerRefundOrderId",refundOrderId);
            params.put("partnerId",partnerId);
           
            RefundOrderDTO orderDTO = refundService.queryRefundOrderDTO(partnerId, refundOrderId);
            
            if(orderDTO==null){
            	resultMap.put("hasRefundOrder","0");
            }else{
            	resultMap.put("status",orderDTO.getStatus());
            	resultMap.put("refundAmount",orderDTO.getRefundAmount());
            	resultMap.put("refundTime",orderDTO.getPartnerRefundTime());
            	resultMap.put("tradeOrderNo",orderDTO.getTradeOrderNo());
            	resultMap.put("refundOrderNo",orderDTO.getRefundOrderNo());
            	resultMap.put("hasRefundOrder","1");
            }

			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("api pay error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
		}

		return JSonUtil.toJSonString(resultMap);
	}
}
