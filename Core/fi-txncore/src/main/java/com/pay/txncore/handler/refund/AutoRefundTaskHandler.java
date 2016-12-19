/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.refund;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
import com.pay.util.StringUtil;

/**
 * 自动调用退款任务接口
 * 
 * @author libo
 */
public class AutoRefundTaskHandler implements EventHandler {

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
					.refundTaskRdTx(refundParam);
			
			RefundOrderDTO refundOrderDTO = new RefundOrderDTO();
			//add by mack 2016年6月1日- 退款订单不生成，不更新任何东西
			if (StringUtils.isNotEmpty(resultDto.getRefundOrderId())) 
			{
			refundOrderDTO.setRefundOrderNo(Long.valueOf(resultDto.getRefundOrderId()));
			refundOrderDTO.setRefundSource(paraMap.get("refundSource")+"");
			refundOrderDTO.setUpdateDate(Calendar.getInstance().getTime());
			refundOrderService.updateRefundOrderByPk(refundOrderDTO);
			}
			
			resultMap.put("refundAmount",resultDto.getRefundAmount());
            resultMap.put("completeTime",resultDto.getCompleteTime());
            resultMap.put("refundOrderId",resultDto.getRefundOrderId());
			resultMap.put("responseCode", resultDto.getResponseCode());
			resultMap.put("responseDesc", resultDto.getResponseDesc());
		} catch (Exception e) {
			logger.error("api refund error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
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
