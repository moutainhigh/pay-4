/**
 * 
 */
package com.pay.channel.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.channel.client.HandlerClientService;
import com.pay.channel.dto.ChannelPreathResponseDto;
import com.pay.channel.dto.ChannelRequestDto;
import com.pay.channel.dto.ChannelResponseDto;
import com.pay.channel.dto.PaymentInfo;
import com.pay.channel.dto.PreauthInfo;
import com.pay.channel.model.ChannelMidCount;
import com.pay.channel.model.PaymentChannelItem;
import com.pay.channel.service.ChannelMidCountService;
import com.pay.channel.service.ChannelService;
import com.pay.channel.service.PaymentChannelService;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class ChannelServiceImpl implements ChannelService {

	private final Log logger = LogFactory.getLog(getClass());
	private PaymentChannelService paymentChannelService;
	private HandlerClientService handlerClientService;
	//comment by Delin for 按量负载均衡
	/*private ChannelMidCountService channelMidCountService;
	
	public void setChannelMidCountService(
			ChannelMidCountService channelMidCountService) {
		this.channelMidCountService = channelMidCountService;
	}
*/
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.ChannelService#channelPay(com.pay.channel.dto
	 * .ChannelRequest)
	 */
	@Override
	public ChannelResponseDto channelPay(PaymentInfo channelRequest) {

		ChannelResponseDto channelPaymentResult = new ChannelResponseDto();
		Map<String, String> resultMap = handlerClientService
				.prePay(channelRequest);
		String responseCode = resultMap.get("responseCode");
		String responseDesc = resultMap.get("responseDesc");
		String payAmount = String.valueOf(resultMap.get("payAmount"));
		String channelReturnNo = resultMap.get("channelReturnNo");
		String authorisation = resultMap.get("Auth_Code");
		String referenceNo = resultMap.get("reference_no");
		channelPaymentResult.setResponseCode(responseCode);
		channelPaymentResult.setResponseDesc(responseDesc);
		channelPaymentResult.setAuthorisation(authorisation);
		logger.info("获得ChannelServiceImpl.channelPay()的参考号:reference_no"+referenceNo);
		channelPaymentResult.setReferenceNo(referenceNo);
		if (!StringUtil.isEmpty(payAmount)) {
			BigDecimal payA = new BigDecimal(payAmount)
			                          .multiply(new BigDecimal("10"));
			Long amount = payA.longValue();
			channelPaymentResult.setPayAmount(amount);
		}
		if (!StringUtil.isEmpty(channelReturnNo)) {
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
		}
		//更新二级商户号统计
			//comment by Delin for 按量负载均衡
	/*	ChannelMidCount channelMidCount = new ChannelMidCount();
		channelMidCount.setMemberCode(StringUtil.isEmpty(channelRequest.getPartnerId())?null:Long.valueOf(channelRequest.getPartnerId()));
		channelMidCount.setChannelCode(channelRequest.getOrgCode());
		channelMidCount.setMid(channelRequest.getOrgMerchantCode());
		channelMidCount.setUpdateDate(Calendar.getInstance().getTime());
		channelMidCountService.updateChannelMidCount(channelMidCount);*/
		return channelPaymentResult;
	}
	
	/*
	 * (non-Javadoc)
	 * 系统联通行检测
	 * @see
	 * com.pay.channel.service.ChannelService#channelPay(com.pay.channel.dto
	 * .ChannelRequest)
	 */
	@Override
	public ChannelResponseDto systemDetected(PaymentInfo channelRequest) {

		ChannelResponseDto channelPaymentResult = new ChannelResponseDto();
		Map<String, String> resultMap = handlerClientService
				.systemDetected(channelRequest);

		String responseCode = resultMap.get("responseCode");
		String responseDesc = resultMap.get("responseDesc");

		channelPaymentResult.setResponseCode(responseCode);
		channelPaymentResult.setResponseDesc(responseDesc);
		
		return channelPaymentResult;
	}

	@Override
	public ChannelResponseDto channelCallBack(ChannelRequestDto channelRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.ChannelService#channelQuery(com.pay.channel.dto
	 * .ChannelRequest)
	 */
	@Override
	public ChannelResponseDto channelQuery(ChannelRequestDto channelRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.ChannelService#channelRefund(com.pay.channel.
	 * dto.ChannelRequest)
	 */
	@Override
	public ChannelResponseDto channelRefund(ChannelRequestDto channelRequest) {
		ChannelResponseDto channelResponseDto = new ChannelResponseDto();

		String orgCode = channelRequest.getOrgCode();
		PaymentChannelItem channelItem = paymentChannelService
				.queryPaymentChannelItemByOrgCode(orgCode);

		if (null == channelItem) {
			channelResponseDto.setResponseCode(ExceptionCodeEnum.ORGCODE_ERROR
					.getCode());
			channelResponseDto.setResponseDesc(ExceptionCodeEnum.ORGCODE_ERROR
					.getDescription());
			logger.error("can not find channel ....");
			return channelResponseDto;
		}

		// 调用前置完成支付
		if (logger.isInfoEnabled()) {
			logger.info("channel item url:" + channelItem.getPreServerUrl());
		}
		channelResponseDto = handlerClientService.preRefund(channelItem,
				channelRequest);
		logger.info("channel refund completeStatus :" + channelResponseDto.getCompleteStatus());
		return channelResponseDto;
	}
	
	@Override
	public ChannelResponseDto channelRefundStatusQuery(ChannelRequestDto channelRequest) {
		ChannelResponseDto channelResponseDto = new ChannelResponseDto();

		String orgCode = channelRequest.getOrgCode();
		PaymentChannelItem channelItem = paymentChannelService
				.queryPaymentChannelItemByOrgCode(orgCode);

		if (null == channelItem) {
			channelResponseDto.setResponseCode(ExceptionCodeEnum.ORGCODE_ERROR
					.getCode());
			channelResponseDto.setResponseDesc(ExceptionCodeEnum.ORGCODE_ERROR
					.getDescription());
			logger.error("can not find channel ....");
			return channelResponseDto;
		}

		// 调用前置完成支付
		if (logger.isInfoEnabled()) {
			logger.info("channel item url:" + channelItem.getPreServerUrl());
		}
		channelResponseDto = handlerClientService.qryRefundSts(channelItem,
				channelRequest);
		logger.info("channel refund status query completeStatus :" + channelResponseDto.getCompleteStatus());
		return channelResponseDto;
	}

	public void setHandlerClientService(
			HandlerClientService handlerClientService) {
		this.handlerClientService = handlerClientService;
	}

	public void setPaymentChannelService(
			PaymentChannelService paymentChannelService) {
		this.paymentChannelService = paymentChannelService;
	}

	@Override
	public ChannelPreathResponseDto channelPreauth(PreauthInfo channelRequest) {

		ChannelPreathResponseDto channelResponseDto = new ChannelPreathResponseDto();

		String orgCode = channelRequest.getOrgCode();
		PaymentChannelItem channelItem = paymentChannelService
				.queryPaymentChannelItemByOrgCode(orgCode);

		if (null == channelItem) {
			channelResponseDto.setResponseCode(ExceptionCodeEnum.ORGCODE_ERROR
					.getCode());
			channelResponseDto.setResponseDesc(ExceptionCodeEnum.ORGCODE_ERROR
					.getDescription());
			logger.error("can not find channel ....");
			return channelResponseDto;
		}

		// 调用前置完成支付
		if (logger.isInfoEnabled()) {
			logger.info("channel item url:" + channelItem.getPreServerUrl());
		}
		channelResponseDto = handlerClientService.preauth(channelItem,
				channelRequest);
		return channelResponseDto;
	}

	@Override
	public ChannelResponseDto channelPreauthCompeleted(
			PreauthInfo channelRequest) {

		ChannelResponseDto channelResponseDto = new ChannelResponseDto();

		// 查找可用渠道
		String orgCode = channelRequest.getOrgCode();
		PaymentChannelItem channelItem = paymentChannelService
				.queryPaymentChannelItemByOrgCode(orgCode);

		if (null == channelItem) {
			channelResponseDto.setResponseCode(ExceptionCodeEnum.ORGCODE_ERROR
					.getCode());
			channelResponseDto.setResponseDesc(ExceptionCodeEnum.ORGCODE_ERROR
					.getDescription());
			logger.error("can not find channel ....");
			return channelResponseDto;
		}

		// 调用前置完成预授权完成
		if (logger.isInfoEnabled()) {
			logger.info("channel item url:" + channelItem.getPreServerUrl());
		}
		channelResponseDto = handlerClientService.preauthCompeleted(
				channelItem, channelRequest);
		return channelResponseDto;
	}

	@Override
	public ChannelResponseDto channelPreauthRevoke(PreauthInfo channelRequest) {

		ChannelResponseDto channelResponseDto = new ChannelResponseDto();

		String orgCode = channelRequest.getOrgCode();
		PaymentChannelItem channelItem = paymentChannelService
				.queryPaymentChannelItemByOrgCode(orgCode);

		if (null == channelItem) {
			channelResponseDto.setResponseCode(ExceptionCodeEnum.ORGCODE_ERROR
					.getCode());
			channelResponseDto.setResponseDesc(ExceptionCodeEnum.ORGCODE_ERROR
					.getDescription());
			logger.error("can not find channel ....");
			return channelResponseDto;
		}

		// 调用前置完成支付
		if (logger.isInfoEnabled()) {
			logger.info("channel item url:" + channelItem.getPreServerUrl());
		}
		channelResponseDto = handlerClientService.preauthRevoke(channelItem,
				channelRequest);
		return channelResponseDto;
	}


	@Override
	public ChannelResponseDto preAuthApply(PaymentInfo channelRequest) {
		ChannelResponseDto channelPaymentResult = new ChannelResponseDto();
		Map<String, String> resultMap = handlerClientService
				.preAuthApply(channelRequest);
		String responseCode = resultMap.get("responseCode");
		String responseDesc = resultMap.get("responseDesc");
		String payAmount = String.valueOf(resultMap.get("payAmount"));
		String channelReturnNo = resultMap.get("channelReturnNo");
		String authorisation = resultMap.get("Auth_Code");
		String referenceNo = resultMap.get("reference_no");
		channelPaymentResult.setResponseCode(responseCode);
		channelPaymentResult.setResponseDesc(responseDesc);
		channelPaymentResult.setAuthorisation(authorisation);
		logger.info("获得ChannelServiceImpl.preAuthApply()的参考号:reference_no"+referenceNo);
		channelPaymentResult.setReferenceNo(referenceNo);
		if (!StringUtil.isEmpty(payAmount)) {
			BigDecimal payA = new BigDecimal(payAmount)
					.multiply(new BigDecimal("10"));
			Long amount = payA.longValue();
			channelPaymentResult.setPayAmount(amount);
		}
		if (!StringUtil.isEmpty(channelReturnNo)) {
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
		}
		return channelPaymentResult;
	}

	@Override
	public ChannelResponseDto preAuthVoid(PaymentChannelItem channelItem, ChannelRequestDto channelRequestDto, String orgiChannelOrderNo, String orgiReturnNo) {
		ChannelResponseDto channelPaymentResult = new ChannelResponseDto();
		Map<String, String> resultMap = handlerClientService
				.preAuthVoid( channelItem,  channelRequestDto,  orgiChannelOrderNo,  orgiReturnNo);
		String responseCode = resultMap.get("responseCode");
		String responseDesc = resultMap.get("responseDesc");
		String payAmount = String.valueOf(resultMap.get("payAmount"));
		String channelReturnNo = resultMap.get("channelReturnNo");
		String authorisation = resultMap.get("Auth_Code");
		String referenceNo = resultMap.get("reference_no");
		channelPaymentResult.setResponseCode(responseCode);
		channelPaymentResult.setResponseDesc(responseDesc);
		channelPaymentResult.setAuthorisation(authorisation);
		logger.info("获得ChannelServiceImpl.preAuthApply()的参考号:reference_no"+referenceNo);
		channelPaymentResult.setReferenceNo(referenceNo);
		if (!StringUtil.isEmpty(payAmount)) {
			BigDecimal payA = new BigDecimal(payAmount)
					.multiply(new BigDecimal("10"));
			Long amount = payA.longValue();
			channelPaymentResult.setPayAmount(amount);
		}
		if (!StringUtil.isEmpty(channelReturnNo)) {
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
		}
		return channelPaymentResult;
	}

	@Override
	public ChannelResponseDto preAuthCapture(PaymentChannelItem channelItem, ChannelRequestDto channelRequestDto, String orgiChannelOrderNo, String orgiReturnNo) {
		ChannelResponseDto channelPaymentResult = new ChannelResponseDto();
		Map<String, String> resultMap = handlerClientService
				.preAuthCapture( channelItem,  channelRequestDto,  orgiChannelOrderNo,  orgiReturnNo);
		String responseCode = resultMap.get("responseCode");
		String responseDesc = resultMap.get("responseDesc");
		String payAmount = String.valueOf(resultMap.get("payAmount"));
		String channelReturnNo = resultMap.get("channelReturnNo");
		String authorisation = resultMap.get("Auth_Code");
		String referenceNo = resultMap.get("reference_no");
		channelPaymentResult.setResponseCode(responseCode);
		channelPaymentResult.setResponseDesc(responseDesc);
		channelPaymentResult.setAuthorisation(authorisation);
		logger.info("获得ChannelServiceImpl.preAuthApply()的参考号:reference_no"+referenceNo);
		channelPaymentResult.setReferenceNo(referenceNo);
		if (!StringUtil.isEmpty(payAmount)) {
			BigDecimal payA = new BigDecimal(payAmount)
					.multiply(new BigDecimal("10"));
			Long amount = payA.longValue();
			channelPaymentResult.setPayAmount(amount);
		}
		if (!StringUtil.isEmpty(channelReturnNo)) {
			channelPaymentResult.setChannelReturnNo(channelReturnNo);
		}
		return channelPaymentResult;
	}
}
