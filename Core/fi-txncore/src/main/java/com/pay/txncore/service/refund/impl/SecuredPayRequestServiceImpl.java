/**
 * 
 */
package com.pay.txncore.service.refund.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.txncore.dto.PaymentNoticeDTO;
import com.pay.txncore.dto.ResponseNotify;
import com.pay.txncore.dto.refund.RefundResultNoticeDTO;
import com.pay.txncore.service.refund.SecuredPayRequestService;
import com.pay.util.MapUtil;

/**
 * 交易请求处理实现类
 * @author min.lin
 * 
 */
public class SecuredPayRequestServiceImpl implements SecuredPayRequestService {

	private Log log = LogFactory.getLog(SecuredPayRequestServiceImpl.class);
	private JmsSender jmsSender;//消息服务(异步通知类)

	@Override
	public void paymentResultNotify(PaymentNoticeDTO noticeDto) {
		HttpNotifyRequest request = new HttpNotifyRequest();
		if(noticeDto.getServiceVersion().startsWith("bsp")
			|| noticeDto.getServiceVersion().startsWith("mrp")) {
			request.setTemplateId(1002);
		} else {
			request.setTemplateId(1001);
		}
		Map<String, String> data = MapUtil.string2map(noticeDto.getNoticeContent());
		log.info(data.toString());
//		data.put("content", noticeDto.getNoticeContent());
		log.info("orderId = " + noticeDto.getOrderNo() + "@FI-支付结果通知数据："+data);
		request.setData(data);
		request.setUrl(noticeDto.getNoticeUrl());
		log.info("orderId = " + noticeDto.getOrderNo() + "@FI-支付结果通知地址："+request.getUrl());
		jmsSender.send(request);
		log.info("orderId = " + noticeDto.getOrderNo() + "@FI-支付结果通知已经受理");
	}
	
	@Override
	public void sharingNotice(String content, String url) {
		log.info("通知到q服务器开始");
		HttpNotifyRequest request = new HttpNotifyRequest();
		request.setTemplateId(1004);
		Map<String, String> data = MapUtil.string2map(content);
		log.info("通知内容 " + data);
		request.setData(data);
		log.info("通知地址 " + url);
		request.setUrl(url);
		jmsSender.send(request);
		log.info("发送完毕");
	}
	
	/**
	 * 退款发送通知模板
	 * crate by fred at  20110430
	 */
	@Override
	public boolean notify(ResponseNotify response) {
		log.info("@FI-发送通知 RESPONSE_NO:"+response.getResponseNo());
		
		HttpNotifyRequest request = new HttpNotifyRequest();
		request.setTemplateId(1103);
		Map<String, String> data = MapUtil.string2map(response.getNoticeContent());
		request.setData(data);
		request.setUrl(response.getNoticeUrl());
		log.info("@FI-发送通知 BG_URL:"+response.getNoticeUrl());
		try{
			jmsSender.send(request);
			log.info("@FI-发送通知成功, RESPONSE_NO:"+response.getResponseNo());
			return true;
		}catch(Exception e){
			log.error("@FI-发送通知异常, RESPONSE_NO:"+response.getResponseNo(),e);
			return false;
		}
	}

	@Override
	public boolean noiftyRefundResult(RefundResultNoticeDTO resultDTO) {
		String pattern = "refundOrderID={0}&resultCode={1}&stateCode={2}&orderID={3}&refundAmount={4}&refundTime={5}&completeTime={6}&refundNo={7}&partnerID={8}&remark={9}&charset={10}&signType={11}&signMsg={12}";
		HttpNotifyRequest request = new HttpNotifyRequest();
		System.out.println("request.getNotifyId() = " + request.getNotifyId());
		// 模板ID,需要配置查询.暂时写在程序中.
		request.setTemplateId(3);
		Map<String, String> data = new HashMap<String, String>();
		data.put("refundOrderID", resultDTO.getRefundOrderId());
		data.put("resultCode", resultDTO.getResultCode());
		//data.put("stateCode", resultDTO.getStateCode());
		data.put("orderID", resultDTO.getOrderId());
		data.put("refundAmount", resultDTO.getRefundAmount());
		data.put("refundTime", resultDTO.getRefundAmount());
		data.put("completeTime", resultDTO.getCompleteTime());
		data.put("refundNo", resultDTO.getDealId());
		data.put("partnerID", resultDTO.getPartnerId());
		data.put("remark", resultDTO.getRemark());
		data.put("charset", resultDTO.getCharset());
		data.put("signType", resultDTO.getSignType());
		data.put("charset", resultDTO.getCharset());
		log.info("@FI-SecuredPayRequestServiceImpl-paymentResultNotify-退款结果通知数据："+ data);
		// TODO
		// data.put("signMsg", signature(pattern,
		// buildRefundParamsObject(resultDTO)));
		request.setData(data);
		// 退款回调地址可能需要配置的配置文件或者数据库中
		//request.setUrl(resultDTO.getNoticeUrl());

		log.info("********************* 退款通知 refund result URL is : "+ request.getUrl());
		log.info("********************* 退款通知 refund result Data is : "+ request.getData());
	
		try{
			jmsSender.send(request);
		}catch(Exception e){
			log.info("@SecuredPayRequestServiceImpl-noiftyRefundResult发送通知出错");
			return false;
		}
		return true;
	}
		

	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

//	@Override
//	public void activeSeller(ActiveSellerDTO activeSellerDTO) {
//
//	}
//
//	/**
//	 * 订单修改处理接口
//	 * processChangeActionService --根据操作类型，交易变更处理接口
//	 */
//	@Override
//	public ChangeDTO change(ChangeDTO changeDTO) throws Exception {
//		String changeAction = changeDTO.getAction();
//		//TODO 按操作类型action ,得到不同的操作服务
//		ProcessChangeActionService processChangeActionService = changeServiceMap.get(changeDTO.getAction());
//		
//		logger.info("订单变更服务方法Map类：changeServiceMap:" + changeServiceMap);
//		logger.info("订单变更服务方法changeAction类：changeAction:" + changeAction);
//		
//		//TODO 按操作类型,订单修改处理
//		if (processChangeActionService != null) {
//			logger.info("订单变更服务处理类：processChangeActionService:" + processChangeActionService.getClass());
//			try{
//				
//				logger.info("开始订单变更服务处理：changeDTO:" + changeDTO);
//				changeDTO = processChangeActionService.process(changeDTO);
//				logger.info("订单变更服务处理结果：changeDTO:" + changeDTO);
//				
//			}catch(Exception e){
//				logger.error("订单修改错误："+e.getMessage());
//				throw new Exception("订单修改错误："+TransReturnCode.CLOSE_TRADE_NO_IS_NOT_EXIST.getValue());
//			}
//		}
//		return changeDTO;
//	}
//	
//
//	@Override
//	public void confirmReceived(ConfirmPaymentDTO confirmRecievedDTO)
//			throws Exception {
//		String pattern = "serialNo={0}&tradeNo={1}&result={2}&partner={3}&remark={4}&charset={5}";
//		HttpNotifyRequest request = new HttpNotifyRequest();
//		// ("request.getNotifyId() = " + request.getNotifyId());
//		// 模板ID,需要配置查System.out.println询.暂时写在程序中.
//		request.setTemplateId(6);
//		Map<String, String> data = new HashMap<String, String>();
//		data.put("serialNo", confirmRecievedDTO.getSerialNo());
//		data.put("tradeNo", confirmRecievedDTO.getTradeNo());
//		data.put("result", confirmRecievedDTO.getResult());
//		data.put("partner", confirmRecievedDTO.getPartner());
//		data.put("remark", confirmRecievedDTO.getRemark());
//		data.put("charset", String.valueOf(confirmRecievedDTO.getCharset()));
//		data.put("signMsg", signature(pattern,
//				new Object[] { confirmRecievedDTO.getSerialNo(),
//						confirmRecievedDTO.getTradeNo(),
//						confirmRecievedDTO.getResult(),
//						confirmRecievedDTO.getPartner(),
//						confirmRecievedDTO.getRemark(),
//						confirmRecievedDTO.getCharset() }));
//		
//		logger.info("******************** 确认付款通知数据："+data);
//		
//		request.setData(data);
//		// 退款回调地址可能需要配置的配置文件或者数据库中
//		String url=memberInfoServiceFacade
//		.queryConfirmPayNotifyURL(confirmRecievedDTO.getPartner());
//		logger.info("******************** 确认付款通知URL："+url);
//		request.setUrl(url);
//		jmsSender.send(request);
//	}
//
//	@Override
//	public void sentToBankNotifty(SentToBankDTO sentToBankDTO) {
//		String orderIds = "";
//		TradeBaseInfo transBaseInfo = tradeBaseInfoDAO
//				.findBySerialNoAndPartner(sentToBankDTO.getSerialNo(),
//						sentToBankDTO.getPartner());
//		if (transBaseInfo != null) {
//			List<TradeOrderInfo> orderList = tradeOrderInfoDAO
//					.findListByTbId(transBaseInfo.getTradeBaseInfoId());
//			if (orderList != null && !orderList.isEmpty()) {
//				for (TradeOrderInfo tradeOrderInfo : orderList) {
//					orderIds += "|" + tradeOrderInfo.getOrderId();
//				}
//				orderIds = orderIds.substring(1);
//			}
//			try {
//				noifySendToBank(sentToBankDTO, orderIds);
//			} catch (Exception e) {
//				logger
//						.error(
//								"notify merchant send to bank has happen some error, the error is : ",
//								e);
//			}
//		}
//
//	}
//
//	@Override
//	public void payResultNotify(SentToBankDTO sentToBankDTO) {
//		String orderResultList = "";
//		TradeBaseInfo transBaseInfo =
//			tradeBaseInfoDAO.findBySerialNoAndPartner
//			(sentToBankDTO.getSerialNo(), sentToBankDTO.getPartner());
//		if(transBaseInfo != null) {
//			Long payOnlineId = sentToBankDTO.getPayOnlineId();
//			List<PayOnlineDetail> payOnlineDetailList =
//				payOnlineDetailDAO.findByTemplate("findByPayOnlineId", payOnlineId);
//			
//			List<TradeOrderInfo> orderList = new ArrayList<TradeOrderInfo>();
//			if(payOnlineDetailList != null && !payOnlineDetailList.isEmpty()) {
//				for(PayOnlineDetail detail : payOnlineDetailList) {
//					TradeOrderInfo orderInfo = tradeOrderInfoDAO.findById(detail.getTradeOrderInfoId());
//					orderList.add(orderInfo);
//				}
//			}
//			
////			List<TradeOrderInfo> orderList =
////				tradeOrderInfoDAO.findListByTbId(transBaseInfo.getTradeBaseInfoId());
//			if(orderList != null && !orderList.isEmpty()) {
//				for(TradeOrderInfo transOrderInfo : orderList) {
//					BigDecimal amount =  new BigDecimal(transOrderInfo.getOrderAmount()).divide(new BigDecimal(10)).setScale(0, BigDecimal.ROUND_HALF_UP);
//					orderResultList += "|"
//						+ payResultMap.get(transOrderInfo.getStatus()) 
//						+ "," + transOrderInfo.getOrderId()
//						+ "," + amount.longValue()
//						+ "," + transOrderInfo.getTradeOrderInfoId();
//				}
//				orderResultList = orderResultList.substring(1);
//			}
//			try {
//				noifyPayResult(sentToBankDTO, orderResultList);
//			} catch (Exception e) {
//				logger.error("notify merchant pay result has happen some error, the error is : ", e);
//			}
//		}
//
//	}
//
//	@Override
//	public StatusQueryDTO query(StatusQueryDTO statusQueryDTO) {
//		TradeOrderInfo tradeOrderInfo = tradeOrderInfoDAO
//				.findByOrderIdAndPartner(statusQueryDTO.getOrderId(),
//						statusQueryDTO.getPartner());
//		if (tradeOrderInfo != null) {
//			statusQueryDTO.setResult(TransReturnCode.QUERY_SUCCESS.getValue());
//			statusQueryDTO.setTradeNo(tradeOrderInfo.getTradeOrderInfoId());
//			// 如果返回0(未付款)或者1(交易己关闭),则置为交易未付款.否则则置为己付款.
//			if (tradeOrderInfo.getStatus().intValue() == DepositStatusEnum.CREATE
//					.getCode()
//					|| tradeOrderInfo.getStatus().intValue() == DepositStatusEnum.PROCESS
//							.getCode()) {
//				statusQueryDTO.setStatus(String
//						.valueOf(DepositStatusEnum.CREATE.getCode()));
//			} else {
//				statusQueryDTO.setStatus(String
//						.valueOf(DepositStatusEnum.PROCESS.getCode()));
//			}
//		} else {
//			statusQueryDTO.setResult(TransReturnCode.QUERY_ORDER_IS_NOT_EXIST
//					.getValue());
//		}
//		return statusQueryDTO;
//	}
//
//	
//	/**
//	 * 退款
//	 */
//	@Override
//	public RefundTransactionServiceResultDTO refund(RefundDTO refundDTO) {
//		// 2010-10-10 sean.chen update
//		
//		// 退款交易结果DTO
//		RefundTransactionServiceResultDTO resultDTO = new RefundTransactionServiceResultDTO();
//		try {
//			
//			// 数据build
//			RefundTransactionServiceParamDTO paramDTO = new RefundTransactionServiceParamDTO();
//			paramDTO.setTradeNo(refundDTO.getTradeNo()+"");
//			paramDTO.setSerialNo(refundDTO.getSerialNo());
//			
//			DateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss");
//			paramDTO.setRefundTime(fmt.format(refundDTO.getRefundTime()));
//			
//			paramDTO.setRefundAmount(refundDTO.getRefundAmount()+"");
//			paramDTO.setPartner(refundDTO.getPartner());
//			paramDTO.setOrderId(refundDTO.getOrderId());
//			
//			
//			logger.info("开始执行退款:refundTransactionService.processRefund:paramDTO:" +paramDTO );
//			resultDTO = refundTransactionService.refundRnTx(paramDTO);
//			logger.info("结束执行退款:refundTransactionService.processRefund:resultDTO:" +resultDTO );
//			
//		
//		} catch (RefundException e) {
//			logger.error("*****************Refund exception is : ", e);
//			// 处理退款异常
//			resultDTO.setErrorCode(TransReturnCode.REFUND_ORIGINAL_TRADE_NO_NOT_EXIST.getValue());
//			resultDTO.setRefundStatus(RefundStatusEnum.FAILED.getCode());
//			
//		}
//		
//		// 通知商户处理结果
//		try {
//			logger.info("开始退款确认通知：resultDTO:" +  resultDTO);
//			
//			// 消息数据
//			Map<String, String> msgData = this.noiftyRefundResult(resultDTO);
//			// 注入消息数据
//			resultDTO.setResultData(msgData);
//			
//			logger.debug("结束退款确认通知");
//		} catch (Exception e) {
//			logger.error("notify merchant reufnd result has happen some error, the error is : ",e);
//		}
//		
//		
//		return resultDTO;
//		
//	}
//
//	@Override
//	public Map<String, String> noiftyRefundResult(RefundTransactionServiceResultDTO resultDTO) throws Exception {
//		String pattern = "serialNo={0}&orderId={1}&refundNo={2}&result={3}&partner={4}&remark={5}&charset={6}";
//		HttpNotifyRequest request = new HttpNotifyRequest();
//		// System.out.println("request.getNotifyId() = " +
//		// request.getNotifyId());
//		// 模板ID,需要配置查询.暂时写在程序中.
//		request.setTemplateId(3);
//		Map<String, String> data = new HashMap<String, String>();
//		data.put("serialNo", resultDTO.getSerialNo());
//		data.put("orderId", resultDTO.getOrderId());
//		data.put("refundNo", String.valueOf(resultDTO.getRefundOrderInfoId()));
//		data.put("result", resultDTO.getErrorCode());
//		data.put("partner", resultDTO.getPartner());
//		data.put("remark", resultDTO.getRemark()+"");
//		data.put("charset", String.valueOf(resultDTO.getCharset()));
//		data.put("signMsg", signature(pattern,
//				buildRefundParamsObject(resultDTO)));
//		request.setData(data);
//		// 退款回调地址可能需要配置的配置文件或者数据库中
//		request.setUrl(memberInfoServiceFacade.queryRefundNotifyURL(resultDTO
//				.getPartner()));
//		
//		
//		logger.info("********************* 退款通知 refund result URL is : " + request.getUrl());
//		logger.info("********************* 退款通知 refund result Data is : " + request.getData());
//		jmsSender.send(request);
//		return data;
//	}
//
//	private Object[] buildRefundParamsObject(RefundTransactionServiceResultDTO resultDTO) {
//		Object[] params = new Object[] { 
//				resultDTO.getSerialNo(),
//				resultDTO.getOrderId(),
//				String.valueOf(resultDTO.getRefundOrderInfoId()),
//				resultDTO.getErrorCode(), 
//				resultDTO.getPartner(),
//				resultDTO.getRemark(), 
//				resultDTO.getCharset()};
//		return params;
//	}
//
//	private String signature(String pattern, Object[] params) {
//		String sign = "";
//		try {
//			sign = signatureService
//					.generateSecuredGatewaySignature(MessageFormat.format(
//							pattern, params));
//			if (logger.isInfoEnabled()) {
//				logger.info("the signature template is : " + pattern);
//				logger.info("the need sign str is : "
//						+ MessageFormat.format(pattern, params));
//				logger.info("the signature is : " + sign);
//			}
//		} catch (Exception e) {
//			logger.error("signature error, the error is : ", e);
//		}
//		return sign;
//	}
//	
//	/**
//	 * 返回退款错误结果串
//	 */
//	public String buildFailRefundResultStr(Map<String,String> dataMap){
//		String pattern = "serialNo={0}&orderId={1}&refundNo={2}&result={3}&partner={4}&remark={5}&charset={6}";
//		
//		String failRefundResultStr = "";
//		
//		Map<String, String> data = new HashMap<String, String>();
//		data.put("serialNo", dataMap.get("serialNo")+"");
//		data.put("orderId", dataMap.get("orderId")+"");
//		data.put("refundNo",dataMap.get("refundNo")+"");
//		data.put("result", dataMap.get("result")+"");
//		data.put("partner", dataMap.get("partner")+"");
//		data.put("remark", dataMap.get("remark")+"");
//		data.put("charset", dataMap.get("charset")+"");
//		data.put("signMsg", signature(pattern,
//				buildRefundParamsObject(data)));
//		
//		return failRefundResultStr;
//		
//	}
//	
//	private Object[] buildRefundParamsObject(Map<String,String> dataMap) {
//		Object[] params = new Object[] { 
//				dataMap.get("serialNo")+"",
//				dataMap.get("orderId")+"",
//				dataMap.get("refundNo")+"",
//				dataMap.get("result")+"", 
//				dataMap.get("partner")+"",
//				dataMap.get("remark")+"", 
//				dataMap.get("charset")+""};
//		return params;
//	}
//	

//	private void noifySendToBank(SentToBankDTO sentToBankDTO, String orderIds)
//			throws Exception {
//		String pattern = "serialNo={0}&orderIds={1}&partner={2}&remark={3}&charset={4}";
//		HttpNotifyRequest request = new HttpNotifyRequest();
//		// 模板ID,需要配置查询.暂时写在程序中.
//		request.setTemplateId(5);
//		String remark = sentToBankDTO.getRemark() == null ? "" : sentToBankDTO.getRemark();
//		Map<String, String> data = new HashMap<String, String>();
//		data.put("serialNo", sentToBankDTO.getSerialNo());
//		data.put("orderIds", orderIds);
//		data.put("partner", sentToBankDTO.getPartner());
//		data.put("remark", remark);
//		data.put("charset", CharsetTypeEnum.UTF8.getCode()+"");
//		data.put("signMsg", signature(pattern, new Object[] {
//				sentToBankDTO.getSerialNo(), orderIds,
//				sentToBankDTO.getPartner(), remark,
//				String.valueOf(sentToBankDTO.getCharset()) }));
//		request.setData(data);
//		// 退款回调地址可能需要配置的配置文件或者数据库中
//		request.setUrl(memberInfoServiceFacade
//				.querySentToBankNotifyURL(sentToBankDTO.getPartner()));
//		logger.info("********商家通知数据："+data);
//		logger.info("********商家通知地址："+request.getUrl());
//		jmsSender.send(request);
//		if(!data.isEmpty()) {
//			saveNotifyLog(request.getUrl(), data);
//		}
//	}
//
//	private void noifyPayResult(SentToBankDTO sentToBankDTO,
//			String orderResultList) throws Exception {
//		String pattern = "serialNo={0}&orderResultList={1}&partner={2}&remark={3}&charset={4}";
//		HttpNotifyRequest request = new HttpNotifyRequest();
//		// 模板ID,需要配置查询.暂时写在程序中.
//		request.setTemplateId(8);
//		String remark = sentToBankDTO.getRemark() == null ? "" : sentToBankDTO.getRemark();
//		Map<String, String> data = new HashMap<String, String>();
//		data.put("serialNo", sentToBankDTO.getSerialNo());
//		data.put("orderResultList", orderResultList);
//		data.put("partner", sentToBankDTO.getPartner());
//		data.put("remark", remark);
//		data.put("charset", String.valueOf(sentToBankDTO.getCharset()));
//		data.put("signMsg", signature(pattern, new Object[] {
//			sentToBankDTO.getSerialNo(), orderResultList,
//			sentToBankDTO.getPartner(), remark,
//			String.valueOf(sentToBankDTO.getCharset()) }));
//		request.setData(data);
//		// 退款回调地址可能需要配置的配置文件或者数据库中
//		request.setUrl(memberInfoServiceFacade
//			.queryPayResultNotifyURL(sentToBankDTO.getPartner()));
//		logger.info("支付结果通知数据：" + request.getData());
//		logger.info("支付结果通知商家地址：" + request.getUrl());
//		jmsSender.send(request);
//		if(!data.isEmpty()) {
//			saveNotifyLog(request.getUrl(), data);
//		}
//	}
//	
//	@Override
//	public void payResultNotify(List<TradeOrderInfo> orderInfoList) {
//		if(orderInfoList != null && !orderInfoList.isEmpty()) {
//			for(TradeOrderInfo order : orderInfoList){
//				SentToBankDTO sentToBankDTO = new SentToBankDTO();
//				TradeBaseInfo tbi = tradeBaseInfoDAO.findById(order.getTradeBaseInfoId());
//				sentToBankDTO.setSerialNo(tbi.getSerialNo());
//				sentToBankDTO.setCharset(tbi.getCharset());
//				sentToBankDTO.setPartner(tbi.getPartner());
//				sentToBankDTO.setRemark(tbi.getRemark());
//				sentToBankDTO.setTradeOrderInfoId(order.getTradeOrderInfoId());
//				
//				//网关内部是厘单位，外部还是要分为单位
//				BigDecimal orderAmount = new BigDecimal(order.getOrderAmount())
//					.divide(new BigDecimal(10)).setScale(0, BigDecimal.ROUND_HALF_UP);
//				
//				String orderResultList =  payResultMap.get(order.getStatus())
//					+ "," + order.getOrderId() + ","
//					+ orderAmount.longValue() + ","
//					+ order.getTradeOrderInfoId();
//				
//				try {
//					noifyPayResult(sentToBankDTO, orderResultList);
//				} catch (Exception e) {
//					logger.error(
//						"notify merchant pay result has happen some error, the error is : ",
//							e);
//				}
//			}
//		}
//	}
//	
//	@Override
//	public void bankPayResultNotify(List<TradeOrderInfo> orderInfoList) {
//		if(orderInfoList != null && !orderInfoList.isEmpty()) {
//			for(TradeOrderInfo order : orderInfoList){
//				SentToBankDTO sentToBankDTO = new SentToBankDTO();
//				TradeBaseInfo tbi = tradeBaseInfoDAO.findById(order.getTradeBaseInfoId());
//				sentToBankDTO.setSerialNo(tbi.getSerialNo());
//				sentToBankDTO.setCharset(tbi.getCharset());
//				sentToBankDTO.setPartner(tbi.getPartner());
//				sentToBankDTO.setRemark(tbi.getRemark());
//				sentToBankDTO.setTradeOrderInfoId(order.getTradeOrderInfoId());
//				
//				String orders =  order.getOrderId();
//				try {
//					noifySendToBank(sentToBankDTO, orders);
//				} catch (Exception e) {
//					logger.error(
//						"notify merchant pay result has happen some error, the error is : ",
//							e);
//				}
//			}
//		}
//	}
//	
//	@Override
//	public Map generateNotifyParam(TradeBaseInfo baseInfo, List<TradeOrderInfo> orderList) {
//		logger.info("generateNotifyParam生成前台通知参数的input:" + baseInfo + ", orderList: " + orderList);
//		Map<String, String> notifyParamMap = new HashMap<String, String>();
//		String pattern = "serialNo={0}&orderResultList={1}&partner={2}&remark={3}&charset={4}";
//		if(null != orderList && !orderList.isEmpty()) {
//			StringBuffer sbResultList = new StringBuffer();
//			for(TradeOrderInfo orderInfo : orderList) {
//				BigDecimal orderAmount = new BigDecimal(orderInfo.getOrderAmount())
//					.divide(new BigDecimal(10)).setScale(0, BigDecimal.ROUND_HALF_UP);
//				sbResultList.append("|")
//					.append(payResultMap.get(orderInfo.getStatus()))
//					.append(",")
//					.append(orderInfo.getOrderId())
//					.append(",")
//					.append(orderAmount.longValue())
//					.append(",")
//					.append(orderInfo.getTradeOrderInfoId());
//			}
//			String remark = baseInfo.getRemark() == null ? "" : baseInfo.getRemark();
//			notifyParamMap.put("serialNo", baseInfo.getSerialNo());
//			notifyParamMap.put("partner", baseInfo.getPartner());
//			notifyParamMap.put("remark", remark);
//			notifyParamMap.put("charset", String.valueOf(baseInfo.getCharset()));
//			notifyParamMap.put("orderResultList", sbResultList.substring(1));
//			notifyParamMap.put("signMsg", signature(pattern, new Object[] {
//				baseInfo.getSerialNo(), sbResultList.substring(1),
//				baseInfo.getPartner(), remark,
//				String.valueOf(baseInfo.getCharset()) }));
//		}
//		if(!notifyParamMap.isEmpty()) {
//			saveNotifyLog(baseInfo.getReturnUrl(), notifyParamMap);
//		}
//		return notifyParamMap;
//	}
//	
//	@Override
//	public Map createPageNotify(Long tradeBaseNo) {
//		logger.info("createPageNotify前台页面通知依据：tradeBaseId=" + tradeBaseNo);
//		TradeBaseInfo baseInfo = tradeBaseInfoDAO.findById(tradeBaseNo);
//		List<TradeOrderInfo> orderList = tradeOrderInfoDAO.findListByTbId(tradeBaseNo);
//		if(null != baseInfo && null != orderList && !orderList.isEmpty()) {
//			return generateNotifyParam(baseInfo, orderList);
//		} else {
//			return null;
//		}
//	}
//	
//	public void saveNotifyLog(String url, Map notifyData) {
//		TransLogDTO transDto = new TransLogDTO();
//		transDto.setAction(TransLogType.PAYED.getCode());
//		transDto.setIsReceived(false);
//		notifyData.put("url", url);
//		transDto.setLogContent(notifyData.toString());
//		transDto.setPartner((String)notifyData.get("partner"));
//		transDto.setSerialNo((String)notifyData.get("serialNo"));
//		transDto.setCreateTime(new Date());
//		try{
//			logService.doTransLogRnTx(transDto);
//		} catch (Exception e) {
//			logger.error("发送商城通知记录日志表出错：");
//		}
//	}
//	
//	public void setJmsSender(JmsSender jmsSender) {
//		this.jmsSender = jmsSender;
//	}
//
//	public void setSignatureService(SignatureService signatureService) {
//		this.signatureService = signatureService;
//	}
//
//	
//
//	public void setTradeOrderInfoDAO(TradeOrderInfoDAO tradeOrderInfoDAO) {
//		this.tradeOrderInfoDAO = tradeOrderInfoDAO;
//	}
//
//	public void setTradeBaseInfoDAO(TradeBaseInfoDAO tradeBaseInfoDAO) {
//		this.tradeBaseInfoDAO = tradeBaseInfoDAO;
//	}
//
////	public void setChangeServiceMap(
////			Map<String, ProcessChangeActionService> changeServiceMap) {
////		this.changeServiceMap = changeServiceMap;
////	}
//
//	public void setMemberInfoServiceFacade(
//			MemberInfoServiceFacade memberInfoServiceFacade) {
//		this.memberInfoServiceFacade = memberInfoServiceFacade;
//	}
//
//	public void setPayResultMap(Map<Integer, String> payResultMap) {
//		this.payResultMap = payResultMap;
//	}
//
////	public void setRefundTransactionService(
////			RefundTransactionService refundTransactionService) {
////		this.refundTransactionService = refundTransactionService;
////	}
//
//	public void setPayOnlineDetailDAO(PayOnlineDetailDAO payOnlineDetailDAO) {
//		this.payOnlineDetailDAO = payOnlineDetailDAO;
//	}
//
//	public void setLogService(LogService logService) {
//		this.logService = logService;
//	}
//	
	
}
