/**
 *  File: BankRefundProcessServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-1      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.bankrefund.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.bankrefund.BankRefundOrderDTO;
import com.pay.fundout.withdraw.service.bankrefund.BankRefundOrderService;
import com.pay.fundout.withdraw.service.bankrefund.BankRefundProcessService;
import com.pay.fundout.withdraw.service.order.WithdrawOrderBusiType;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.innerback.BackFundingOrderDaoService;
import com.pay.poss.external.service.innerback.impl.DefaultBackFundingInnerServiceImpl;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;
import com.pay.poss.service.withdraw.orderafterproc.OrderAfterProcService;

/**
 * @author bill_peng
 *
 */
public class BankRefundProcessServiceImpl implements BankRefundProcessService {
	
	protected transient Log log = LogFactory.getLog(getClass());
	private BankRefundOrderService bankRefundOrderService;
	private OrderAfterProcService orderAfterProcService;
	//订单回调后处理服务 
	private OrderCallBackService orderCallBackService;
	//订单后处理算费和更新余额服务
	private Map<Integer,AccountingService> bankRefundReqAccountingServices;
	private Map<Integer,AccountingService> bankRefundSuccAccountingServices;
	private Map<Integer,AccountingService> bankRefundFailAccountingServices;
	
	// 提现订单服务
	private WithdrawOrderService withdrawOrderService;
	private BackFundingOrderDaoService orderDaoService;

	@Override
	public void startRdTx(BankRefundOrderDTO order) throws PossException {
		try{
			//boolean result = false;
			//Long ordrerId = 0L;
			bankRefundOrderService.createOrderRnTx(order);
			//BankRefundOrderDTO dto = bankRefundOrderService.getOrder(ordrerId);
			//HandlerParam param = new HandlerParam();
			//param.setBaseOrderDto(dto);
			//param.setOrderStatus(new Integer(String.valueOf(dto.getStatus())));
			//param.setWithdrawBusinessType(WithdrawBusinessType.BANKREFUND_ORDER_REQ.getBusinessType());
			//订单状态初始为100
			//result = orderAfterProcService.process(param,orderCallBackService,null);
			//if(!result){
			//	log.error("orderAfterProcService.process failed!");
			//}
			
			//if(!result){
			//	throw new RuntimeException("更新订单状态失败!");
			//}
		}catch(Exception e){
			log.error("创建出款退款订单失败!", e);
			throw new PossException("创建出款退款订单失败!", ExceptionCodeEnum.UN_DEFINE_EXCEPTIONCODE);
		}
		
		
	}

	@Override
	public boolean auditRdTx(BankRefundWFParameter param) throws PossException {
		
		//TODO 增加工单操作日志
		try{
			BankRefundOrderDTO dto  =  bankRefundOrderService.getOrder(new Long(param.getOrderId()));
			//BackFundmentOrder backFundOrder = buildBackOrder(dto);
			boolean  status = true;
			if(status){
				
				dto.setUpdateDate(new Date());
				//dto.setRefundPerson(param.getRemark());
				HandlerParam handerParam = new HandlerParam();
				
				handerParam.setOrderStatus(new Integer(String.valueOf(dto.getStatus())));
				
				if(BankRefundWFHelper.AUDIT_SUCCESS.equals(param.getOption())){
					dto.setStatus(111);
					if(WithdrawOrderBusiType.WITHDRAW.getCode()==dto.getBusiType().intValue()){
						handerParam.setWithdrawBusinessType(WithdrawBusinessType.BANKREFUND_WITHDRAW_ORDER_SUCC.getBusinessType());
					}else if(WithdrawOrderBusiType.PAY2BANK.getCode()==dto.getBusiType().intValue()){
						handerParam.setWithdrawBusinessType(WithdrawBusinessType.BANKREFUND_PAY2BANK_ORDER_SUCC.getBusinessType());
					}else{
						handerParam.setWithdrawBusinessType(WithdrawBusinessType.BANKREFUND_ORDER_SUCC.getBusinessType());
					}
				}else{
					//backFundOrder.setUniqueKy(String.valueOf(backFundOrder.getSequenceSrc()));
					//boolean result = orderDaoService.saveBackFundingOrderRnTx(backFundOrder);
					//if(!result) {
					//	throw new PossException("原订单号已存在,请不要重复提交!", ExceptionCodeEnum.ILLEGAL_PARAMETER);
					//}
					dto.setStatus(112);
					//dto.setBackAccSeq(backFundOrder.getSequenceId());
					handerParam.setWithdrawBusinessType(WithdrawBusinessType.BANKREFUND_ORDER_FAIL.getBusinessType());
				}
				handerParam.setBaseOrderDto(dto);
				
				//update by terry_ma
				AccountingDto accountingDto = new AccountingDto();
				accountingDto.setAmount(dto.getAmount());
				accountingDto.setHasCaculatedPrice(true);
				accountingDto.setPayerFee(Math.abs(dto.getFee()));
				accountingDto.setOrderAmount(dto.getOrderAmount());
				accountingDto.setOrderId(dto.getSequenceId());
				accountingDto.setPayee(dto.getPayeeMemberCode());
				accountingDto.setBankCode(dto.getWithdrawBankCode());
				handerParam.setAccountingDto(accountingDto);
				
				if(BankRefundWFHelper.AUDIT_SUCCESS.equals(param.getOption())){
					status = orderAfterProcService.process(handerParam,orderCallBackService,bankRefundSuccAccountingServices.get(dto.getBusiType()));
				}else{
					//backFundOrder.setStatus(Constants.ORDER_STATUS_SUCC);
					//orderDaoService.updateBackFundingOrderRnTx(backFundOrder);
					//TODO 退款只有一步，审核失败记账修改  
					status = orderAfterProcService.process(handerParam,orderCallBackService,null);	
				}
				if(!status){
					LogUtil.info(DefaultBackFundingInnerServiceImpl.class, "退款订单余额更新失败", OPSTATUS.START, "auditRdTx", 
							"退款订单号:"+dto.getSequenceId()+"");
					//backFundOrder.setUniqueKy(backFundOrder.getSequenceId()+""+backFundOrder.getSequenceSrc());
					//backFundOrder.setStatus(Constants.ORDER_STATUS_INIT);
					//orderDaoService.updateBackFundingOrderRnTx(backFundOrder);
					log.error("orderAfterProcService.process failed!");
					throw new RuntimeException("orderAfterProcService.process failed!");
				}
				if(BankRefundWFHelper.AUDIT_SUCCESS.equals(param.getOption()) && status){
					WithdrawOrderAppDTO order = new WithdrawOrderAppDTO();
					order.setSequenceId(dto.getTradeOrderId());
					order.setStatus(113L);
					order.setUpdateTime(new Date());
					// 更新订单状态
					try {
						withdrawOrderService.updateWithdrawOrder(order);
					} catch (Exception e) {
						log.error("更新订单状态失败,原订单信息 [" + order + "]", e);
					}
				}
			}
			return status;
		}catch(Exception e){
			log.error("审核失败!", e);
			throw new PossException("审核失败!", ExceptionCodeEnum.SEND_WORKFLOW_AUDIT_EXCEPTION);
		}
	}

	private BackFundmentOrder buildBackOrder(BankRefundOrderDTO order) {
		BackFundmentOrder backOrder = new BackFundmentOrder();
		backOrder.setSequenceSrc(order.getSequenceId());
		backOrder.setTimeSrc(order.getUpdateDate());
		backOrder.setAmountSrc(new BigDecimal(order.getAmount()));
		backOrder.setFeeSrc(new BigDecimal(order.getFee()));
		backOrder.setFromCode("bankrefund");
		backOrder.setPayerMember(order.getPayeeMemberCode());
		backOrder.setPayerAcctType(order.getPayeeAcctType());
		backOrder.setPayerAcctCode(order.getPayeeAcctCode());
		backOrder.setPayeeAcctCode(order.getPayerAcctCode());
		backOrder.setAppAmount(new BigDecimal(order.getAmount() * (-1)));
		backOrder.setAppType("bankerfund");
		backOrder.setAppFee(new BigDecimal(0));
		return backOrder;
	}
	
	// set注入
	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	public void setOrderAfterProcService(OrderAfterProcService orderAfterProcService) {
		this.orderAfterProcService = orderAfterProcService;
	}

	public void setOrderCallBackService(OrderCallBackService orderCallBackService) {
		this.orderCallBackService = orderCallBackService;
	}

	public void setBankRefundReqAccountingServices(
			Map<Integer, AccountingService> bankRefundReqAccountingServices) {
		this.bankRefundReqAccountingServices = bankRefundReqAccountingServices;
	}
	public void setOrderDaoService(BackFundingOrderDaoService orderDaoService) {
		this.orderDaoService = orderDaoService;
	}
	public void setBankRefundSuccAccountingServices(
			Map<Integer, AccountingService> bankRefundSuccAccountingServices) {
		this.bankRefundSuccAccountingServices = bankRefundSuccAccountingServices;
	}

	public void setBankRefundFailAccountingServices(
			Map<Integer, AccountingService> bankRefundFailAccountingServices) {
		this.bankRefundFailAccountingServices = bankRefundFailAccountingServices;
	}

	public void setBankRefundOrderService(
			BankRefundOrderService bankRefundOrderService) {
		this.bankRefundOrderService = bankRefundOrderService;
	}
}
