/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.crosspay.handler;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceResultDTO;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.txncore.service.refund.RefundService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 跨境退款接口
 * 
 * @author chma
 */
public class CrosspayRefundHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(getClass());
	private RefundService refundService;
	
	private RefundOrderService refundOrderService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg,
					new HashMap<String, Object>().getClass());

			RefundTransactionServiceParamDTO refundParam = MapUtil.map2Object(
					RefundTransactionServiceParamDTO.class, paraMap);

			RefundTransactionServiceResultDTO resultDto = refundService
					.refundRdTx(refundParam);
			resultMap.put("refundAmount",resultDto.getRefundAmount());
            resultMap.put("completeTime",resultDto.getCompleteTime());
            resultMap.put("refundOrderId",resultDto.getRefundOrderId());
			resultMap.put("responseCode", resultDto.getResponseCode());
			resultMap.put("responseDesc", resultDto.getResponseDesc());
			RefundOrderDTO refundOrderDTO = new RefundOrderDTO();
			if(resultDto!=null && resultDto.getRefundOrderId()!=null &&  !"".equals(resultDto.getRefundOrderId()))
			{
				refundOrderDTO.setRefundOrderNo(Long.valueOf(resultDto.getRefundOrderId()));
			}
			refundOrderDTO.setRefundSource(paraMap.get("refundSource")+"");
			refundOrderDTO.setUpdateDate(Calendar.getInstance().getTime());
			refundOrderService.updateRefundOrderByPk(refundOrderDTO);
			
		}catch (MaAcctBalanceException e) {
			logger.error("CrosspayRefundHandler.handle error:refundOrderId="+resultMap.get("refundOrderId"), e);
			resultMap.put("responseCode",
					e.getErrorEnum().getErrorCode());
			resultMap.put("responseDesc",
					e.getMessage());
			
		}catch (NumberFormatException e) {
			logger.error("CrosspayRefundHandler.handle error:refundOrderId="+resultMap.get("refundOrderId"), e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					"退款订单数据转换错误!");
		} catch (Exception e) {
			logger.error("api refund error:", e);
			if("".equals(resultMap.get("responseDesc")) || null==resultMap.get("responseDesc"))
			{
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					e.getCause().getMessage());
			}
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}

	public void setRefundService(RefundService refundService) {
		this.refundService = refundService;
	}

}
