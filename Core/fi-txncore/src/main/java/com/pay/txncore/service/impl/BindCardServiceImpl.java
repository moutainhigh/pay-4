/**
 * 
 */
package com.pay.txncore.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.trace.ThreadLocalLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.jms.notification.request.ChannelMidAmountNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.txncore.client.ChannelClientService;
import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.dao.PaymentOrderExpandDAO;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceParamDTO;
import com.pay.txncore.dto.refund.RefundTransactionServiceResultDTO;
import com.pay.txncore.model.CardBindOrder;
import com.pay.txncore.model.PaymentOrderExpand;
import com.pay.txncore.model.TokenPayInfo;
import com.pay.txncore.service.ApiPayService;
import com.pay.txncore.service.BindCardService;
import com.pay.txncore.service.CardBindOrderService;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.txncore.service.TokenPayInfoService;
import com.pay.txncore.service.preauth.PreAuth2Service;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.txncore.service.refund.RefundService;
import com.pay.txncore.utils.CancelPreAuthTask;
import com.pay.txncore.utils.TokenUtil;
import com.pay.util.DESUtil;
import com.pay.util.DateUtil;
import com.pay.util.MapUtil;
import com.pay.util.security.SHABaseAlgorithms;

/**
 * @author yanshichuan
 *
 */
public class BindCardServiceImpl implements BindCardService{
	private static Logger logger = LoggerFactory.getLogger(CardBindServiceImpl.class);
	private ChannelClientService channelClientService;
	private CardBindOrderService cardBindOrderService;
	private TokenPayInfoService tokenPayService;
	private  ApiPayService apiPayService;
	private RefundService refundService;	
	private RefundOrderService refundOrderService;
	private ChannelOrderService channelOrderService;
	private PaymentOrderExpandDAO paymentOrderExpandDAO;
	private PreAuth2Service preAuth2Service;
	private JmsSender jmsSender;
	private String partnerId;
	private String orderAmount;
	private String currencyCode;
	private String securityKey;
	private String siteId;
	/**
     * 撤销预授权失败重试次数
     */
	private int retryTimes;
	
	private static final String STATUS_ONGOING = "0";
	
	private static final String STATUS_SUCCESS = "1";
	
	private static final String STATUS_FAILURE = "2";
	
	private static final String TYPE_BIND = "0";
	
	private static final String TYPE_UNBIND = "1";

	@Override
	public PaymentResult bind(PaymentInfo paymentInfo) {
		logger.info("menthod: bind");
		PaymentResult paymentResult = new PaymentResult();
		String partnerId = paymentInfo.getPartnerId();
		String cardNo = paymentInfo.getCardHolderNumber();
		String registerUserId = paymentInfo.getRegisterUserId();
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("partnerId",partnerId);
		params.put("registerUserId",registerUserId);
		params.put("cardNo",DESUtil.encrypt(cardNo, securityKey));
		params.put("status", "1");
		List<TokenPayInfo> list = tokenPayService.getTokenPayInfos(params);
		logger.info("list size="+(list==null?0:list.size()));
		if(list!=null&&!list.isEmpty()){
			paymentInfo.setTradeOrderNo(null);
			addErrorCardBindOrder(paymentInfo, TYPE_BIND);
			paymentResult.setResponseCode("0450");
			paymentResult.setResponseDesc("Card is already bound:卡号已绑定");
			return paymentResult;
		}
		logger.info("preAuth Apply start...");
		   //这个地方加个支付退款的过程
	    this.buildPaymentInfo(paymentInfo);
	    paymentResult =preAuth2Service.preAuthApply(paymentInfo);
	    logger.info("preAuth Apply end:partnerId="+paymentInfo.getPartnerId()+",currencyCode="+paymentInfo.getCurrencyCode()+",orderAmount="+paymentInfo.getOrderAmount()); 
		
	    logger.info("respCode: "+paymentResult.getResponseCode()+",respMsg: "+paymentResult.getResponseDesc());
		String resultCode = paymentResult.getResponseCode();
		//设置错误信息
		paymentResult.setResponseDesc(paymentResult.getResponseDesc());
		paymentResult.setResponseCode(paymentResult.getResponseCode());
		if(ResponseCodeEnum.SUCCESS.getCode().equals(resultCode)){//先测试使用
			paymentInfo.setTradeOrderNo(paymentResult.getTradeOrderNo());
			TokenPayInfo payInfo = new TokenPayInfo();
			payInfo.setBillAddress(paymentInfo.getBillAddress());
			payInfo.setBillCity(paymentInfo.getBillCity());
			payInfo.setBillCountryCode(paymentInfo.getBillCountryCode());
			payInfo.setBillEmail(paymentInfo.getBillEmail());
			payInfo.setBillFirstName(paymentInfo.getBillFirstName());
			payInfo.setBillLastName(paymentInfo.getBillLastName());
			payInfo.setBillPhoneNumber(paymentInfo.getBillPhoneNumber());
			payInfo.setBillPostalCode(paymentInfo.getBillPostalCode());
			payInfo.setBillState(paymentInfo.getBillState());
			payInfo.setBillStreet(paymentInfo.getBillStreet());
			payInfo.setBorrowingMarked(paymentInfo.getBorrowingMarked());
			
			String expdYear = paymentInfo.getCardExpirationYear();
			String expdMonth = paymentInfo.getCardExpirationMonth();
			String cvv = paymentInfo.getSecurityCode();
			
			try {
				payInfo.setCardExpirationMonth(DESUtil.encrypt(expdMonth,securityKey));
				payInfo.setCardExpirationYear(DESUtil.encrypt(expdYear,securityKey));
				payInfo.setCardHolderNumber(DESUtil.encrypt(cardNo,securityKey));
				payInfo.setSecurityCode(DESUtil.encrypt(cvv,securityKey));
			} catch (Exception e) {
				e.printStackTrace();
			}
			payInfo.setCardHolderFirstName(paymentInfo.getCardHolderFirstName());
			payInfo.setCardHolderLastName(paymentInfo.getCardHolderLastName());
			payInfo.setCardHolderPhoneNumber(paymentInfo.getCardHolderPhoneNumber());
			payInfo.setCardHolderEmail(paymentInfo.getCardHolderEmail());
			payInfo.setRegisterUserId(registerUserId);
			payInfo.setPartnerId(Long.valueOf(partnerId));
			payInfo.setStatus(STATUS_SUCCESS);
			payInfo.setCreateDate(new Date());
			
//			String tokenSource = cardNo+expdYear+expdMonth+cvv+partnerId+registerUserId
//					+System.currentTimeMillis();
			String token =TokenUtil.genToken(String.valueOf(paymentInfo.getTradeOrderNo()), paymentInfo.getCardHolderNumber());//SHABaseAlgorithms.getSHA512Str(tokenSource);//TokenUtil.genToken();
			payInfo.setToken(token);
			
			boolean rst=tokenPayService.saveTokenPayInfo(payInfo);
			paymentInfo.setTradeOrderNo(null);
			paymentInfo.setPartnerId(partnerId);
			CardBindOrder cardBindOrder = addCardBindOrder(paymentInfo, TYPE_BIND, STATUS_ONGOING, payInfo.getId());
			if(rst){
				paymentInfo.setOrigOrderId(paymentInfo.getOrderId());
				//撤销预授权
				cancelPreAuthApply(paymentInfo);
	            updateCardBindOrder(cardBindOrder.getId(), STATUS_SUCCESS);
	            paymentResult.setTradeOrderNo(cardBindOrder.getId());
	            paymentResult.setToken(token);
	            paymentResult.setDealId(cardBindOrder.getId()+"");
	            paymentResult.setCardHolderNumber(cardNo);
	            paymentResult.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
	            paymentResult.setResponseDesc(ResponseCodeEnum.SUCCESS.getDesc());
			}else{
				updateCardBindOrder(cardBindOrder.getId(), STATUS_FAILURE);
	            paymentResult.setResponseCode(ResponseCodeEnum.FAIL.getCode());
	            paymentResult.setResponseDesc(ResponseCodeEnum.FAIL.getDesc());
			}
		}
		return paymentResult;
	}
	public PaymentResult addErrorCardBindOrder(PaymentInfo paymentInfo, String type) {
		PaymentResult paymentResult = new PaymentResult();
		paymentResult.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
        paymentResult.setResponseDesc(ResponseCodeEnum.SUCCESS.getDesc());
		addCardBindOrder(paymentInfo, type, "2", null);
		return paymentResult;
	}
	
	public PaymentResult updateErrorCardBindOrder(Long id) {
		updateCardBindOrder(id, "2");
		PaymentResult paymentResult = new PaymentResult();
		paymentResult.setResponseCode(ResponseCodeEnum.SUCCESS.getCode());
        paymentResult.setResponseDesc(ResponseCodeEnum.SUCCESS.getDesc());
		return paymentResult;
	}
	
	private CardBindOrder addCardBindOrder(PaymentInfo paymentInfo, String type, String status, Long tokenpayId) {
		CardBindOrder bindOrder = new CardBindOrder();
		bindOrder.setCreateDate(new Date());
		bindOrder.setOrderId(paymentInfo.getOrderId());
		bindOrder.setPartnerId(paymentInfo.getPartnerId());
		bindOrder.setRegisterUserId(paymentInfo.getRegisterUserId());
		bindOrder.setTokenPayId(tokenpayId);
		bindOrder.setOrderCommitTime(DateUtil.parse(DateUtil.PATTERN1, paymentInfo.getSubmitTime()));
		bindOrder.setCompletedTime(new Date());
        bindOrder.setType(type);
        bindOrder.setStatus(status);
        cardBindOrderService.saveCardBindOrder(bindOrder);
//        paymentInfo.setTradeOrderNo(bindOrder.getId());//TODO
        return bindOrder;
	}
	
	private void updateCardBindOrder(Long id, String status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id",id);
		List<CardBindOrder> cardBindOrders = cardBindOrderService.getCardBindOrders(params);
		if(cardBindOrders!=null && !cardBindOrders.isEmpty()){
			CardBindOrder cardBindOrder = cardBindOrders.get(0);
			cardBindOrder.setStatus(status);
			cardBindOrder.setUpdateDate(new Date());
			cardBindOrderService.update(cardBindOrder);
		}
	}

	@Override
	public PaymentResult unBind(PaymentInfo paymentInfo) {
		PaymentResult paymentResult = new PaymentResult();
		String partnerId = paymentInfo.getPartnerId();
		String registerUserId = paymentInfo.getRegisterUserId();
		String token = paymentInfo.getToken();
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("partnerId",partnerId);
		params.put("registerUserId",registerUserId);
		params.put("token",token);
		
		List<TokenPayInfo> list = tokenPayService.getTokenPayInfos(params);
		
		if(list==null||list.isEmpty()){
			paymentResult.setResponseCode("0452");
			paymentResult.setResponseDesc("Token无匹配记录");
			
			addErrorCardBindOrder(paymentInfo, TYPE_UNBIND);
			
			return paymentResult;
		}
		
		params.put("status","1");
		list = tokenPayService.getTokenPayInfos(params);
		
		if(list==null||list.isEmpty()){
			paymentResult.setResponseCode("0451");
			paymentResult.setResponseDesc("Token不存在或已解绑");
			
			addErrorCardBindOrder(paymentInfo, TYPE_UNBIND);
			return paymentResult;
		}
		
		TokenPayInfo payInfo = list.get(0);
		payInfo.setStatus("0");
		payInfo.setUpdateDate(new Date());
		boolean rst = tokenPayService.update(payInfo);
		
		String resultCode="";
		String resultMsg="";
		if(rst){
			CardBindOrder order = new CardBindOrder();
			order.setCreateDate(new Date());
			order.setOrderId(paymentInfo.getOrderId());
			order.setPartnerId(partnerId);
			order.setRegisterUserId(registerUserId);
			order.setOrderCommitTime(DateUtil.parse(DateUtil.PATTERN1,
					paymentInfo.getSubmitTime()));
			order.setStatus(STATUS_SUCCESS);
			order.setCompletedTime(new Date());
			order.setTokenPayId(payInfo.getId());
			order.setType(TYPE_UNBIND);
			boolean rst0=cardBindOrderService.saveCardBindOrder(order);
			logger.info("CardBindOrder create: "+rst0);
			resultCode = ResponseCodeEnum.SUCCESS.getCode();
			resultMsg = ResponseCodeEnum.SUCCESS.getDesc();
			paymentResult.setTradeOrderNo(order.getId());
			
			params = new HashMap<String, Object>();
			params.put("token",token);
			list = tokenPayService.getTokenPayInfos(params);
			if(list != null && !list.isEmpty()) {
				paymentResult.setCardHolderNumber(DESUtil.decrypt(list.get(0).getCardHolderNumber(), securityKey));
//				paymentResult.setCompleteTime(list.get(0).get);
			}
		}else{
			resultCode = ResponseCodeEnum.FAIL.getCode();
			resultMsg = ResponseCodeEnum.FAIL.getDesc();
		}
		
		paymentResult.setResponseCode(resultCode);
		paymentResult.setResponseDesc(resultMsg);
		return paymentResult;
	}
	
	public PaymentResult unBindByToken(PaymentInfo paymentInfo) {
		PaymentResult paymentResult = new PaymentResult();
		String token = paymentInfo.getToken();
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("token",token);
		
		List<TokenPayInfo> list = tokenPayService.getTokenPayInfos(params);
		
		if(list==null||list.isEmpty()){
			paymentResult.setResponseCode("054");
			paymentResult.setResponseDesc("Token不存在或已解绑");
			return paymentResult;
		}
		
		TokenPayInfo payInfo = list.get(0);
		payInfo.setStatus("0");
		payInfo.setUpdateDate(new Date());
		boolean rst = tokenPayService.update(payInfo);
		
		String resultCode="";
		String resultMsg="";
		if(rst){
			CardBindOrder order = new CardBindOrder();
			order.setCreateDate(new Date());
			order.setOrderId(paymentInfo.getOrderId());
			order.setPartnerId(payInfo.getPartnerId().toString());
			order.setRegisterUserId(payInfo.getRegisterUserId());
			order.setOrderCommitTime(DateUtil.parse(DateUtil.PATTERN1,
					paymentInfo.getSubmitTime()));
			order.setStatus("1");
			order.setCompletedTime(new Date());
			order.setTokenPayId(payInfo.getId());
			order.setType("1");
			boolean rst0=cardBindOrderService.saveCardBindOrder(order);
			logger.info("CardBindOrder create: "+rst0);
			resultCode = ResponseCodeEnum.SUCCESS.getCode();
			resultMsg = ResponseCodeEnum.SUCCESS.getDesc();
			paymentResult.setTradeOrderNo(order.getId());
		}else{
			resultCode = ResponseCodeEnum.FAIL.getCode();
			resultMsg = ResponseCodeEnum.FAIL.getDesc();
		}
		
		paymentResult.setResponseCode(resultCode);
		paymentResult.setResponseDesc(resultMsg);
		return paymentResult;
	}
	
	private void buildPaymentInfo(PaymentInfo paymentInfo){
		paymentInfo.setPartnerId(partnerId);
		paymentInfo.setCurrencyCode(currencyCode);
		paymentInfo.setOrderAmount(orderAmount);
		paymentInfo.setPayType("EDC");
		paymentInfo.setPrdtCode("EDC");
		paymentInfo.setOrderId(paymentInfo.getOrderId());
		paymentInfo.setGoodsDesc("CardBind");
		paymentInfo.setGoodsName("CardBind");
		paymentInfo.setBorrowingMarked("0");
		paymentInfo.setSiteId(siteId);
		logger.info("buildPaymentInfo: partnerId="+partnerId+",currencyCode="+currencyCode+",orderAmount="+orderAmount); 
	}
	
	/**
	 * 构建退款请求实体
	 * @param orderId
	 * @param tradeOrderNo
	 * @return
	 */
	private RefundTransactionServiceParamDTO buildRefundOrder(String orderId,String tradeOrderNo,String noticeUrl){
		String refundOrderId = "701" + System.currentTimeMillis();
		RefundTransactionServiceParamDTO paramDTO = new RefundTransactionServiceParamDTO();
		paramDTO.setOrderId(orderId);
		paramDTO.setPartnerId(partnerId);
		paramDTO.setRefundAmount(String.valueOf(orderAmount));
		paramDTO.setRefundTime(getDateStr(System.currentTimeMillis()));
		paramDTO.setRefundOrderId(refundOrderId);
		paramDTO.setDestType("1");
		paramDTO.setRefundType("1");
		paramDTO.setTradeOrderNo(tradeOrderNo);
		paramDTO.setNoticeUrl("mps.ipaylinks.com");
		return paramDTO;
	}
	
	private Long refundApply(RefundTransactionServiceParamDTO paramDTO) throws Exception{
		RefundTransactionServiceResultDTO resultDto = refundService
				.refundRdTx(paramDTO);
		RefundOrderDTO refundOrderDTO = new RefundOrderDTO();
		//add by mack 2016年6月1日- 退款订单不生成，不更新任何东西
		if (StringUtils.isNotEmpty(resultDto.getRefundOrderId())) 
		{
			refundOrderDTO.setRefundOrderNo(Long.valueOf(resultDto.getRefundOrderId()));
			refundOrderDTO.setRefundSource("CARD_BIND");
			refundOrderDTO.setUpdateDate(Calendar.getInstance().getTime());
		    refundOrderService.updateRefundOrderByPk(refundOrderDTO);
		}
		return refundOrderDTO.getRefundOrderNo();
	}
	
	private void cancelPreAuthApply(PaymentInfo paymentInfo){
		ChannelMidAmountNotifyRequest notifyRequest = new ChannelMidAmountNotifyRequest();
//		paymentInfo.setPartnerId(partnerId);
		Map<String, String> notifyMap=MapUtil.bean2map(paymentInfo);
		notifyMap.put("partnerId", partnerId);
//       notifyMap.put("memberCurrentId", memberCurrentId);
//       notifyMap.put("partnerId", partnerId);
//       notifyMap.put("paymentChannelItemId", paymentChannelId);
//       notifyMap.put("payAmount", rateO.toString());
//       notifyMap.put("success", success ? "1" : "0");
//       notifyMap.put("failure", failflag);
//       notifyMap.put("channelConfigId", channelConfigId);
       
//       preAuth2Service.preAuthVoid(paymentInfo);
		
//       PaymentResult result = null;
//		int times = 0;
//		while (times < retryTimes) {
//			logger.info("第" + (++times) + "次撤销预授权:orderId="+paymentInfo.getOrderId()+",memberCode="+paymentInfo.getPartnerId());
//			result = preAuth2Service.preAuthVoid(paymentInfo);
//			logger.info("RegisterUserId="+paymentInfo.getRegisterUserId()+",OrigOrderId="+paymentInfo.getOrigOrderId()+",PartnerId="+paymentInfo.getPartnerId()+",撤销预授权返回代码="+result.getResponseCode());
//			if (result!=null&&ExceptionCodeEnum.PRE_AUTH_VOID_SUCCESS.getCode().equals(result.getResponseCode())) {
//				logger.info("成功撤销预授权:orderId="+paymentInfo.getOrderId()+",memberCode="+paymentInfo.getPartnerId());
//				break;
//			}
//			logger.info("第" + (times) + "次撤销预授权失败:ResponseCode="+result.getResponseCode());
//		}
		
       notifyRequest.setData(notifyMap);
       notifyRequest.setMerchantCode(partnerId);
       jmsSender.send("notify.cancelPreAuthApply", notifyRequest);
//		if(retryTimes==0){
//			retryTimes=2;
//		}
//		new CancelPreAuthTask(preAuth2Service,paymentInfo,retryTimes,ThreadLocalLog.getTrace()).start();
	}
	private void orderRefund(PaymentInfo paymentInfo){
		RefundTransactionServiceParamDTO paramDTO = this.buildRefundOrder(paymentInfo.getOrderId()
				,String.valueOf(paymentInfo.getTradeOrderNo()),paymentInfo.getNoticeUrl());
		if(paramDTO!=null){
			Long refundOrderNo=0L;
			try {
				refundOrderNo = this.refundApply(paramDTO);
				
				RefundOrderDTO refundOrderDTO = refundOrderService.queryByPk(refundOrderNo);
				ChannelOrderDTO channelOrder = channelOrderService
						.queryByTradeOrderNo(refundOrderDTO.getPaymentOrderNo());
				this.buildChannelRefund(channelOrder, refundOrderDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	private void buildChannelRefund(ChannelOrderDTO channelOrder,RefundOrderDTO refundOrderDTO) 
			throws Exception{
		Map<String, String> refundPara = new HashMap<String, String>();
		refundPara.put("orgCode", channelOrder.getOrgCode());
		refundPara.put("partnerId", refundOrderDTO.getPartnerId());
		refundPara.put("merchantNo", channelOrder.getMerchantNo());
		refundPara.put("authorisation", channelOrder.getAuthorisation());
		refundPara.put("channelOrderNo", channelOrder.getChannelOrderNo().toString());
		refundPara.put("orderAmount", channelOrder.getPayAmount().toString());
		refundPara.put("currencyCode", channelOrder.getCurrencyCode());

		long serialNo = refundOrderService.getMaxChannelSerialNo();
		refundOrderDTO.setSerialNo(getSerial(serialNo));
		refundOrderDTO.setStatus(RefundStatusEnum.REFUNDING.getCode()+""); //2016-06-28 
		refundOrderService.updateRefundOrderByPkRnTx(refundOrderDTO);
		refundPara.put("serialNo", getSerial(serialNo));
		refundPara.put("dealSerialNo", channelOrder.getSerialNo());
		refundPara.put("returnNo", channelOrder.getReturnNo());
		refundPara.put("refundOrderNo", ""+refundOrderDTO.getRefundOrderNo());
		String refundAmount = refundOrderDTO.getRefundAmount().toString();
		
		this.doRefund(""+refundOrderDTO.getRefundOrderNo(), refundOrderDTO, 
				channelOrder, refundOrderDTO.getTradeOrderNo(), channelOrder.getTransferRate(),
				refundPara, refundAmount);
	}
	
	private void doRefund(String refundOrderNo,
			RefundOrderDTO refundOrderDTO, ChannelOrderDTO channelOrder,
			Long tradeOrderNo, String transferRate,
			Map<String, String> refundPara, String refundAmount)
			throws Exception {
		ChannelPaymentResult refundResult;
		String refundTransferAmount = refundOrderDTO.getTransferAmount() +"";
		refundPara.put("refundAmount", refundTransferAmount);
		refundPara.put(
				"tranDate",
				DateUtil.formatDateTime(DateUtil.PATTERN,
						channelOrder.getCompleteDate()));
		refundPara.put(
				"tranDatetime",
				DateUtil.formatDateTime(DateUtil.DEFAULT_DATE_FROMAT,
						channelOrder.getCompleteDate()));
		
		refundPara.put("currencyCode", channelOrder.getTransferCurrencyCode());

		PaymentOrderExpand paymentOrderExpand = paymentOrderExpandDAO
				.queryPayOrderExpandByPayNO(refundOrderDTO
						.getPaymentOrderNo());
		refundPara.put("customerIP", paymentOrderExpand.getIp());
		refundPara.put("cardHolderNumber",
				paymentOrderExpand.getCardNo());
		refundPara.put("accessCode",channelOrder.getAccessCode());
		refundPara.put("orgKey",channelOrder.getOrgKey());
		refundPara.put("merchantNo",channelOrder.getMerchantNo());
		
		refundResult = channelClientService.channelRefund(refundPara);
		if (ResponseCodeEnum.SUCCESS.getCode().equals(
				refundResult.getErrorCode())) {
			// 调用渠道退款
			refundService.refundHandle(refundOrderNo, refundResult);
		} else {
			// 调用渠道退款
			refundService.refundHandle(refundOrderNo, refundResult);
		}
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
	
	private  String getDateStr(final long millis) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(millis);
		Date date = cal.getTime();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		return sdf.format(date);
	}
	
	public void setCardBindOrderService(CardBindOrderService cardBindOrderService) {
		this.cardBindOrderService = cardBindOrderService;
	}
	public void setTokenPayService(TokenPayInfoService tokenPayService) {
		this.tokenPayService = tokenPayService;
	}
	public void setSecurityKey(String securityKey) {
		this.securityKey = securityKey;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
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
	public void setChannelClientService(ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}
	public void setPaymentOrderExpandDAO(PaymentOrderExpandDAO paymentOrderExpandDAO) {
		this.paymentOrderExpandDAO = paymentOrderExpandDAO;
	}

	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}

	public void setOrderAmount(String orderAmount) {
		this.orderAmount = orderAmount;
	}

	public void setApiPayService(ApiPayService apiPayService) {
		this.apiPayService = apiPayService;
	}
	public PreAuth2Service getPreAuth2Service() {
		return preAuth2Service;
	}
	public void setPreAuth2Service(PreAuth2Service preAuth2Service) {
		this.preAuth2Service = preAuth2Service;
	}
	public JmsSender getJmsSender() {
		return jmsSender;
	}
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
	public int getRetryTimes() {
		return retryTimes;
	}
	public void setRetryTimes(int retryTimes) {
		this.retryTimes = retryTimes;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	
}
