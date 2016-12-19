/**
 *  File: WithDrawOrderFacadeServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-19      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.app.output.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.fundout.withdraw.model.bankrefund.BackfundOrder;
import com.pay.fundout.withdraw.service.app.output.WithDrawOrderFacadeService;
import com.pay.fundout.withdraw.service.app.output.WithDrawOrderQueryParam;
import com.pay.fundout.withdraw.service.app.output.WithDrawOrderQueryResult;
import com.pay.fundout.withdraw.service.app.output.WithdrawOrderParam;
import com.pay.fundout.withdraw.service.bankrefund.BankRefundOrderService;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.common.order.WithdrawOrderStatus;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterProcService;
import com.pay.poss.service.withdraw.orderafterproc.impl.OrderAfterFailProcAlertHandler;
import com.pay.util.JSonUtil;

/**
 * 出款订单门面服务实现类
 * 
 * @author zliner
 * 
 */
public class WithDrawOrderFacadeServiceImpl implements WithDrawOrderFacadeService {
	// 出款服务
	private WithdrawOrderService withdrawOrderService;
	private OrderAfterProcService orderAfterProcService;
	private OrderCallBackService orderCallBackService;
	private AccountingService accountingService;
	private NotifyFacadeService notifyFacadeService;
	private BankRefundOrderService backfundOrderService;

	//更新余额失败记录报警日志
	private OrderAfterFailProcAlertHandler orderAfterFailProcAlertHandler;
	
	public void setOrderAfterFailProcAlertHandler(
			OrderAfterFailProcAlertHandler orderAfterFailProcAlertHandler) {
		this.orderAfterFailProcAlertHandler = orderAfterFailProcAlertHandler;
	}
	
	// set注入
	public void setNotifyFacadeService(final NotifyFacadeService param) {
		this.notifyFacadeService = param;
	}

	/** JMS消息名 **/
	private String queueName;

	private ConfigBankService configBankService;

	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}

	// set注入
	public void setAccountingService(final AccountingService param) {
		this.accountingService = param;
	}

	// set注入
	public void setOrderAfterProcService(final OrderAfterProcService param) {
		this.orderAfterProcService = param;
	}

	// set注入
	public void setOrderCallBackService(final OrderCallBackService param) {
		this.orderCallBackService = param;
	}

	// set注入
	public void setQueueName(final String param) {
		this.queueName = param;
	}

	// set注入
	public void setWithdrawOrderService(final WithdrawOrderService param) {
		withdrawOrderService = param;
	}

	public void setBackfundOrderService(BankRefundOrderService backfundOrderService) {
		this.backfundOrderService = backfundOrderService;
	}

	/**
	 * 根据外系统提供的查询参数返回相对应的查询结果
	 * 
	 * @param queryParam
	 *            出款订单查询参数
	 * @return withdrawOrderQueryResult 出款订单查询结果参数
	 */
	public WithDrawOrderQueryResult queryWithDrawOrderResult(WithDrawOrderQueryParam queryParam) {
		return withdrawOrderService.queryWithDrawOrderResult(queryParam);
	}

	/**
	 * 保存外系统订单号
	 * 
	 * @param param
	 *            出款订单参数
	 * @return withdrawOrderAPPDTO 保存出款订单相关参数
	 */
	public WithdrawOrderParam saveWithdrawOrder(WithdrawOrderParam param) {
		WithdrawOrderAppDTO appDto = new WithdrawOrderAppDTO();
		// 判断出款渠道是否开启
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("targetBankId", param.getWithdrawBankCode());// 目的银行编号
		map.put("foMode", "1");// 出款方式:暂时为1手工,以后直连接入在进行修改
		map.put("fobusiness", String.valueOf(param.getBusiType()));// //出款业务
		String outBankCode = configBankService.queryFundOutBank2Withdraw(map);
		if (StringUtils.isEmpty(outBankCode)) {// 如果出款渠道关闭,对APP返回失败,错误信息:出款渠道关闭
			param.setStatus(112L);// 状态失败
			param.setErrorMessage(WithdrawBusinessType.WITHDRAWCHANNEL_CLOSED.getBusinessType());// ErrorCode
			return param;
		}

		BeanUtils.copyProperties(param, appDto);
		appDto.setPayerAmount(param.getAmount());
		appDto.setOrderAmount(param.getAmount());
		appDto.setCreateTime(new Date());
		Long sequenceId = withdrawOrderService.createWithdrawOrderRnTx(appDto);
		appDto.setSequenceId(sequenceId);
		param.setSequenceId(sequenceId);
		//update by terry_ma
		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setAmount(appDto.getAmount());
		accountingDto.setOrderAmount(appDto.getOrderAmount());
		accountingDto.setHasCaculatedPrice(true);
		accountingDto.setOrderId(sequenceId);
		accountingDto.setPayer(appDto.getMemberCode());
		accountingDto.setPayerFee(Math.abs(appDto.getFee()));
		HandlerParam handlerParam = buildHanlderParam(appDto);
		handlerParam.setAccountingDto(accountingDto);
		boolean flage = orderAfterProcService.process(handlerParam, orderCallBackService, accountingService);
		if(flage){
			notifyFacadeService.sendRequest(buildNotify2QueueRequest(JSonUtil.toJSonString(sequenceId)));
		}else{
			OrderFailProcAlertModel result = new OrderFailProcAlertModel();
			result.setOrderSeq(sequenceId);
			result.setOrderStatus(101);
			result.setFailReason("更新余额失败");
			result.setAppFrom("API付款");
			result.setUpdateTime(new Date());
			orderAfterFailProcAlertHandler.execute(result);
		}
		return param;
	}

	// 构建对象产生
	private Notify2QueueRequest buildNotify2QueueRequest(String jsonStr) {
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(jsonStr);
		return request;
	}

	// 构建更新余额对象
	private HandlerParam buildHanlderParam(WithdrawOrderAppDTO appDto) {
		WithdrawRequestDTO requestdto = new WithdrawRequestDTO();
		requestdto.setAccountName(appDto.getAccountName());
		requestdto.setAcctType(appDto.getMemberAccType().intValue());
		requestdto.setApplyWithdrawAmount(appDto.getAmount());
		requestdto.setBankAcct(appDto.getBankAcct());
		requestdto.setBankAcctType(appDto.getBankAcctType());
		requestdto.setBankBranch(appDto.getBankBranch());
		requestdto.setBankCity(appDto.getBankCity());
		requestdto.setBankKy(appDto.getBankKy());
		requestdto.setBankProvince(appDto.getBankProvince());
		requestdto.setBusiType(appDto.getBusiType());
		requestdto.setFundorigin(appDto.getFundorigin());
		requestdto.setMemberAcc(appDto.getMemberAcc());
		requestdto.setMemberAccType(appDto.getMemberAccType());
		requestdto.setMemberCode(appDto.getMemberCode().toString());
		requestdto.setMemberType(appDto.getMemberType().intValue());
		requestdto.setMoneyType(appDto.getMoneyType());
		requestdto.setOrderRemarks(appDto.getOrderRemarks());
		requestdto.setPrioritys(appDto.getPrioritys());
		requestdto.setSeqId(appDto.getSequenceId());
		requestdto.setStatus(appDto.getStatus());
		requestdto.setType(appDto.getType());
		requestdto.setWithdrawBankCode(appDto.getWithdrawBankCode());
		requestdto.setWithdrawType(appDto.getWithdrawType());
		HandlerParam handlerParam = new HandlerParam();
		handlerParam.setWithdrawBusinessType(WithdrawBusinessType.WITHDRAW_REQ_PERSON.getBusinessType());
		handlerParam.setOrderStatus(WithdrawOrderStatus.INIT.getValue());
		handlerParam.setBaseOrderDto(requestdto);
		return handlerParam;
	}

	/**
	 * 根据外系统订单号
	 * 
	 * @param orderId
	 *            外系统订单号
	 * @param orderStatus
	 *            出款订单状态
	 */
	public void handlerOrder(String orderId, Integer orderStatus) {

	}

	@Override
	public BackfundOrder queryBackfundOrder(String innerOrderId, String outerOrderId) {
		return backfundOrderService.queryBackfundOrderByInnerOrderId(innerOrderId, outerOrderId);
	}

}
