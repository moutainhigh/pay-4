package com.pay.txncore.handler.refund;

//import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.txncore.service.refund.RefundService;
import com.pay.util.JSonUtil;

/**
 * 根据业务需要设置为成功处理 
 * @author Bobby Guo
 * @date 2015年11月12日
 * sch   2016-04-08 增加了状态判断,代码稍微重构了一下  
 * sch   2016-04-28 去掉了修改清算时间 
 */

public class SetRefundSuccessHandler implements EventHandler{
	
	public final Log logger = LogFactory.getLog(SetRefundSuccessHandler.class);
	
	private RefundService refundService;
	
	private RefundOrderService refundOrderService;
	
	private ChannelOrderService channelOrderService;

	private PartnerSettlementOrderService partnerSettlementOrderService;
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			logger.info("SetRefundSuccessHandler paraMap : "+paraMap);
			String refundOrderNo = String.valueOf(paraMap.get("refundOrderNo"));
			String refundChannelReturnNo = String.valueOf(paraMap.get("refundChannelReturnNo"));
			logger.info("refundChannelReturnNo : "+refundChannelReturnNo);
			ChannelPaymentResult refundResult = new ChannelPaymentResult();
			refundResult.setErrorCode(ResponseCodeEnum.SUCCESS.getCode());
			refundResult.setErrorMsg(ResponseCodeEnum.SUCCESS.getDesc());
			
			//add by sch 2016-04-08
			RefundOrderDTO refundOrder = refundOrderService.queryByPk(Long.valueOf(refundOrderNo));			
			if((refundOrder ==null) || (refundOrder.getStatus() == null)){
				resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
				resultMap.put("responseDesc",ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
				return JSonUtil.toJSonString(resultMap);
			}
			
			//订单中的状态
			if( RefundStatusEnum.MANUAL.getCode() != Integer.valueOf(refundOrder.getStatus())){	
				resultMap.put("responseCode",ResponseCodeEnum.UNDEFINED_ERROR.getCode());
				resultMap.put("responseDesc",ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
				return JSonUtil.toJSonString(resultMap);
			}
			//end 2016-04-08
			
			refundService.refundHandle(refundOrderNo, refundResult);

			/* delete by sch 2016-04-28
			//note by sch 2016-04-08 都已经调用  refundHandle 了，这几句还有还有必要么？ 
			RefundOrderDTO refundOrderDTO = new RefundOrderDTO();		
			refundOrderDTO.setRefundOrderNo(Long.valueOf(refundOrderNo));	
			refundOrderDTO.setStatus(RefundStatusEnum.SUCCESS.getCode()+"");		
			refundOrderDTO.setUpdateDate(Calendar.getInstance().getTime());
			String failReason = String.valueOf(paraMap.get("failReason"));
			refundOrderService.updateRefundOrderByPk(refundOrderDTO);
			
			//这个查询其实也是没有必要，前面已经查出来了  sch 2016-04-08
			//RefundOrderDTO refundOrder = refundOrderService.queryByPk(Long.valueOf(refundOrderNo));
			//ChannelOrderDTO channelOrderDTO = channelOrderService.queryByTradeOrderNo(refundOrder.getPaymentOrderNo());
			ChannelOrderDTO channelOrderDTO = channelOrderService.queryByTradeOrderNo(refundOrder.getPaymentOrderNo());
			
			String merchantOrderId= channelOrderDTO.getOrderId();
			
			//再记渠道成功的账
			refundService.doAccounting_500_508(channelOrderDTO.getOrgCode(), refundOrder.getOrderId(), 
					refundOrder.getRefundAmount(),channelOrderDTO.getCurrencyCode(),channelOrderDTO.getTransferRate(),merchantOrderId);
			refundService.doAccounting_500_509(channelOrderDTO.getOrgCode(), refundOrder.getOrderId(), 
					refundOrder.getRefundAmount(), channelOrderDTO.getCurrencyCode(),merchantOrderId);
			refundService.doAccounting_500_510(channelOrderDTO.getOrgCode(), refundOrder.getOrderId(), 
					refundOrder.getRefundAmount(),channelOrderDTO.getTransferCurrencyCode(),channelOrderDTO.getTransferRate(),merchantOrderId);
			*/
			//delete end 2016-04-28 

			//为什么要设置 清算订单的清算时间 ？ 2016-04-09   说是为了商户在对账单中能马上看到. 这些代码都放在
			/*
			PartnerSettlementOrder partnerSettlementOrder = partnerSettlementOrderService.querySettlementOrderByTradeOrderNo(Long.valueOf(refundOrder.getPartnerId()), refundOrder.getTradeOrderNo());
			if(partnerSettlementOrder != null )
			{
				java.util.Date settleDate = partnerSettlementOrder.getSettlementDate();
				if(settleDate.before(Calendar.getInstance().getTime()))    //如果还没到清算时间，则不管它
				{
					partnerSettlementOrder.setSettlementDate(Calendar.getInstance().getTime());
					partnerSettlementOrderService.updatePartnerSettlementOrder(partnerSettlementOrder);
				}
			}
			*/

			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());	
			
		} catch (Exception e) {
			resultMap.put("responseCode", ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			logger.error("将退款订单置为成功", e);
			return JSonUtil.toJSonString(resultMap);
		}
		return JSonUtil.toJSonString(resultMap);
	}

	public void setRefundService(RefundService refundService) {
		this.refundService = refundService;
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}

	public void setChannelOrderService(ChannelOrderService channelOrderService) {
		this.channelOrderService = channelOrderService;
	}

	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}

	
}
