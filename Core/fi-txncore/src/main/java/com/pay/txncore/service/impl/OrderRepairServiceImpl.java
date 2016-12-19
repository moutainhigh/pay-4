package com.pay.txncore.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fi.fill.model.FillRecordInfo;
import com.pay.fi.fill.service.OrderFillRecordInfoService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.txncore.client.ChannelClientService;
import com.pay.txncore.commons.ChannelOrderStatusEnum;
import com.pay.txncore.commons.PaymentOrderStatusEnum;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.dao.PaymentOrderExpandDAO;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.dto.RepairOrderDTO;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.model.PaymentOrderExpand;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.txncore.service.OrderRepairService;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.txncore.service.PaymentOrderService;
import com.pay.txncore.service.PaymentService;
import com.pay.txncore.service.TradeOrderService;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;


/**
 * 系统自动补单服务
 * @author peiyu.yang
 * @since  2015年11月7日16:37:26
 */
public class OrderRepairServiceImpl implements OrderRepairService {
    private static Logger logger = LoggerFactory.getLogger(OrderRepairServiceImpl.class);
	private OrderFillRecordInfoService orderFillRecordInfoService;
	private ChannelClientService channelClientService;
	private ChannelOrderService channelOrderService;
	private TradeOrderService tradeOrderService;
	private PaymentOrderExpandDAO paymentOrderExpandDAO;
	private PaymentOrderService paymentOrderService;
	private PartnerSettlementOrderService partnerSettlementOrderService;
	private PaymentService paymentService;
	


	@Override
	public boolean repairOrder(RepairOrderDTO orderDTO) {
		
		ChannelOrderDTO channelOrderDTO = channelOrderService
		           .queryByChannelOrderNo(Long.valueOf(orderDTO.getChannelOrderNo()));
		
		if(channelOrderDTO==null){
			logger.info("channelOrderDTO: "+orderDTO.getChannelOrderNo()+", 渠道订单不存在");
			return false;
		}
		
		if(channelOrderDTO.getStatus().intValue()!=
				ChannelOrderStatusEnum.SUCCESS.getCode()){
			channelOrderDTO.setErrorCode(ResponseCodeEnum.FAIL.getCode());
			channelOrderDTO.setErrorMsg("掉单已退款");
			channelOrderDTO.setUpdateDate(new Date());
			channelOrderDTO.setCompleteDate(new Date());
			channelOrderDTO.setAuthorisation(orderDTO.getAuthCode());
			channelOrderDTO.setReturnNo(orderDTO.getRefNo());
			
			Map<String,String> resultMap = new HashMap<String, String>();
			
			String refundAmount = String.valueOf(channelOrderDTO.getPayAmount());
			
			ChannelPaymentResult refundResult= this.channelRefund(channelOrderDTO,resultMap,refundAmount);
			
			if (ResponseCodeEnum.SUCCESS.getCode().equals(
					refundResult.getErrorCode())) {
				return channelOrderService.updateChannelOrder(channelOrderDTO, channelOrderDTO.getStatus());
			}
			
			return false;
		}
		
		//----
		PaymentOrderDTO paymentOrderDTO = new PaymentOrderDTO();
		paymentOrderDTO.setPaymentOrderNo(channelOrderDTO.getPaymentOrderNo());
		paymentOrderDTO.setStatus(PaymentOrderStatusEnum.SUCCESS.getCode());
		paymentOrderDTO.setCompleteDate(new Date());
		paymentOrderDTO.setOrgCode(channelOrderDTO.getOrgCode());
		
		boolean updateFlag = paymentOrderService.updatePaymentOrderRnTx(
				paymentOrderDTO, PaymentOrderStatusEnum.INIT.getCode());
		if (!updateFlag) {
			logger.error("do not update paymentOrder," + channelOrderDTO.getPaymentOrderNo());
		}

		TradeOrderDTO tradeOrderDTO = tradeOrderService
				                       .queryTradeOrderById(Long.valueOf(orderDTO.getTradeOrderNo()));
		
		tradeOrderDTO.setCompleteDate(new Date());
		tradeOrderDTO.setStatus(TradeOrderStatusEnum.SUCCESS.getCode());
		tradeOrderDTO.setRefundAmount(tradeOrderDTO.getOrderAmount());

		boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(
				tradeOrderDTO, TradeOrderStatusEnum.WAIT_PAY.getCode());
		if (!tradeUpFlg) {
			logger.error("do not update trade order," + tradeOrderDTO.getTradeOrderNo());
		}
		
		PaymentOrderExpand orderExpand = paymentOrderExpandDAO
				                                .queryPayOrderExpandByPayNO(Long.valueOf(orderDTO.getPaymentOrderNo()));
		
		String cardOrg = this.getCardType(orderExpand.getCardNo());
		
		TradeBaseDTO tradeBaseDTO = tradeOrderService.queryTradeBaseById(tradeOrderDTO.getTradeBaseNo());
		
		// 保存清算订单
		/*List<PartnerSettlementOrder> settlementOrders = paymentService.buildSettlementOrder(
				channelOrderDTO.getPaymentOrderNo(), tradeBaseDTO, tradeOrderDTO,
				tradeBaseDTO.getSettlementCurrencyCode(),cardOrg);
		partnerSettlementOrderService
				.createPartnerSettlementOrder(settlementOrders);*/
		
		orderDTO.setCreateDate(new Date());
		orderDTO.setStatus("1");

		return false;
	}
	
	@Override
	public boolean repairOrder(FillRecordInfo fillRecordInfo) {
		Long channelOrderNo = fillRecordInfo.getChannelOrderNo();
		
		ChannelOrderDTO channelOrderDTO = channelOrderService
				           .queryByChannelOrderNo(channelOrderNo);
		
		if(channelOrderDTO==null){
			logger.info("channelOrderDTO: "+channelOrderNo+", 渠道订单不存在");
			this.updateRecords(fillRecordInfo.getReqRecordNo(),"渠道订单不存在",2);
			return false;
		}
		
		String returnNo = fillRecordInfo.getReturnNo();
		String authCode = fillRecordInfo.getAuthorization();
		Long amount = fillRecordInfo.getAmount();
		String payCurrencyCode = fillRecordInfo.getCurrencyCode();
        
		if(!StringUtil.isEmpty(returnNo)&&returnNo.equals(channelOrderDTO.getReturnNo())
				&&!StringUtil.isEmpty(authCode)&&authCode.equals(channelOrderDTO.getAuthorisation())
				&&!StringUtil.isEmpty(payCurrencyCode)&&payCurrencyCode
				.equals(channelOrderDTO.getTransferCurrencyCode())&&
				amount!=null&&amount.longValue()==channelOrderDTO.getPayAmount().longValue()
				&&channelOrderDTO.getStatus().intValue()!=1){
			
			TradeOrderDTO tradeOrder = tradeOrderService.queryTradeOrderById(channelOrderDTO.getTradeOrderNo());
			
			if(tradeOrder==null){
				this.updateRecords(fillRecordInfo.getReqRecordNo(),"补单信息与渠道订单不匹配,或者渠道订单已是成功状态",2);
				return false;
			}

			if(tradeOrder.getStatus().intValue()==
					          TradeOrderStatusEnum.SUCCESS.getCode()){
				ChannelOrderDTO dto = new ChannelOrderDTO();
				dto.setChannelOrderNo(channelOrderNo);
				dto.setStatus(ChannelOrderStatusEnum.SUCCESS.getCode());
				channelOrderService.updateChannelOrder(dto,channelOrderDTO.getStatus());
                channelOrderRefund(tradeOrder);
			}else{
				
				
				
			}
			
		}

		this.updateRecords(fillRecordInfo.getReqRecordNo(),"补单信息与渠道订单不匹配,或者渠道订单已是成功状态",2);
		return false;
	}
	
	
	private void updateRecords(Long reqRecodeNo,String reason,int status){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("recordStatus",status);
		map.put("failReason",reason);
		map.put("reqRecodeNo",reqRecodeNo);
		orderFillRecordInfoService.updateRecordStatusByReqBatchNo(map);
	}
	
	
	/**
	 * 渠道退款
	 */
	private void channelOrderRefund(TradeOrderDTO tradeOrder){
		
		RefundTransactionServiceParamDTO refundParam = new RefundTransactionServiceParamDTO();
		refundParam.setTradeOrderNo(""+tradeOrder.getTradeOrderNo());
		refundParam.setPartnerId(""+tradeOrder.getPartnerId());
		refundParam.setOrderId(tradeOrder.getOrderId());
		refundParam.setRefundAmount(""+tradeOrder.getRefundAmount());
		refundParam.setRefundType("1");
		refundParam.setDestType("1");
		
		String refundOrderId = "301" + System.currentTimeMillis();
		refundParam.setRefundOrderId(refundOrderId);
		
		try {
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private String getCardType(String cardNo){
		int cardLen = cardNo.length();
		
		if(cardLen == 16){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=400000 && subCard <=499999){
				return "VISA";
			}
			//Mack comment below line and add nee line
			//if(subCard>=510000 && subCard <=559999){
		   if((subCard>=510000 && subCard <=559999)||(subCard>=222100 && subCard <=272099)){
				return "MASTER";
			}
			if(subCard>=352800 && subCard <=358999){
				return "JCB";
			}
		}
		if(cardLen == 14){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=300000 && subCard <=305999){
				return "DC";
			}
			if(subCard>=309500 && subCard <=309599){
				return "DC";
			}
			if(subCard>=360000 && subCard <=369999){
				return "DC";
			}
			if(subCard>=380000 && subCard <=399999){
				return "DC";
			}
		}
		if(cardLen == 15){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=340000 && subCard <=349999){
				return "AE";
			}
			if(subCard>=370000 && subCard <=379999){
				return "AE";
			}
		}
		return null;
	}
	
	private ChannelPaymentResult channelRefund(ChannelOrderDTO channelOrder,
			Map<String, String> refundPara,String refundAmount){
		
		ChannelPaymentResult refundResult;
		
		refundPara.put("orgCode", channelOrder.getOrgCode());
		refundPara.put("partnerId", ""+channelOrder.getPartnerId());
		refundPara.put("merchantNo", channelOrder.getMerchantNo());
		refundPara.put("authorisation", channelOrder.getAuthorisation());
		refundPara.put("channelOrderNo", channelOrder.getChannelOrderNo().toString());
		refundPara.put("orderAmount", channelOrder.getPayAmount().toString());
		refundPara.put("currencyCode", channelOrder.getCurrencyCode());
		long serialNo = channelOrderService.getMaxChannelSerialNo(channelOrder.getOrgCode());
		
		refundPara.put("serialNo", getSerial(serialNo));
		refundPara.put("dealSerialNo", channelOrder.getSerialNo());
		refundPara.put("returnNo", channelOrder.getReturnNo());
		refundPara.put("refundOrderNo", "106"+System.currentTimeMillis());

		refundPara.put("refundAmount", refundAmount);
		refundPara.put("tranDate",DateUtil.formatDateTime(DateUtil.PATTERN,channelOrder.getCompleteDate()));
		refundPara.put("tranDatetime",
				DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT,channelOrder.getCompleteDate()));		
		refundPara.put("currencyCode", channelOrder.getTransferCurrencyCode());
		PaymentOrderExpand paymentOrderExpand = paymentOrderExpandDAO
				.queryPayOrderExpandByPayNO(channelOrder.getPaymentOrderNo());
		refundPara.put("customerIP", paymentOrderExpand.getIp());
		refundPara.put("cardHolderNumber",paymentOrderExpand.getCardNo());
		refundResult = channelClientService.channelRefund(refundPara);
		
		return refundResult;
	}
	
	private String getSerial(long serialNo) {
		String str = String.valueOf(serialNo);
		int l = str.length();
		if (str.length() < 6) {
			for (int i = 0; i < 6 - l; i++) {
				str = "0" + str;
			}
		}
		return str;
	}
	
	public OrderFillRecordInfoService getOrderFillRecordInfoService() {
		return orderFillRecordInfoService;
	}
	public void setOrderFillRecordInfoService(
			OrderFillRecordInfoService orderFillRecordInfoService) {
		this.orderFillRecordInfoService = orderFillRecordInfoService;
	}

	public void setChannelClientService(ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}
	public void setChannelOrderService(ChannelOrderService channelOrderService) {
		this.channelOrderService = channelOrderService;
	}
	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
		this.paymentOrderService = paymentOrderService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}

	public void setPaymentOrderExpandDAO(PaymentOrderExpandDAO paymentOrderExpandDAO) {
		this.paymentOrderExpandDAO = paymentOrderExpandDAO;
	}
	
	
	
	
}
