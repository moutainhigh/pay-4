/**
 *  File: SaveWithDrawOrderAction.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-12      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.validate.action;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.inf.rule.AbstractAction;
import com.pay.poss.base.common.order.WithdrawOrderStatus;

/**
 * 保存提现订单处理
 * @author zliner
 */
public class SaveWithDrawOrderAction extends AbstractAction {
	
	private WithdrawOrderService withdrawOrderService; 
	/**
	 * 规则验证中规则执行保存提现订单处理逻辑
	 * @param validateBean        待验证规则对象 
	 * @throws Exception          验证中抛出运行时异常
	 */
	protected void doExecute(Object validateBean) throws Exception {
		WithdrawRequestDTO withdrawRequest = (WithdrawRequestDTO)validateBean;
		
		WithdrawOrderAppDTO withdrawOrderAppDTO = new WithdrawOrderAppDTO();
		
		withdrawOrderAppDTO.setMemberCode(Long.valueOf(withdrawRequest.getMemberCode()));
		withdrawOrderAppDTO.setMemberAcc(withdrawRequest.getMemberAcc());
		withdrawOrderAppDTO.setMemberType(Long.valueOf(withdrawRequest.getMemberType()));
		withdrawOrderAppDTO.setMemberAccType(withdrawRequest.getMemberAccType());
		withdrawOrderAppDTO.setAmount(withdrawRequest.getApplyWithdrawAmount());
		withdrawOrderAppDTO.setPrioritys(withdrawRequest.getPrioritys());
		withdrawOrderAppDTO.setAccountName(withdrawRequest.getAccountName());
		withdrawOrderAppDTO.setBankAcct(withdrawRequest.getBankAcct());
		withdrawOrderAppDTO.setBankAcctType(withdrawRequest.getBankAcctType());
		withdrawOrderAppDTO.setBankKy(withdrawRequest.getBankKy());
		withdrawOrderAppDTO.setOrderRemarks(withdrawRequest.getOrderRemarks());
		withdrawOrderAppDTO.setBankBranch(withdrawRequest.getBankBranch());
		withdrawOrderAppDTO.setBankProvince(withdrawRequest.getBankProvince());
		withdrawOrderAppDTO.setBankCity(withdrawRequest.getBankCity());
		//类型
		withdrawOrderAppDTO.setType(withdrawRequest.isBusiness()?1L:0L);//提现类别
		withdrawOrderAppDTO.setTradeType(withdrawRequest.isBusiness()?1:0);//交易类型
		withdrawOrderAppDTO.setBusiType(0L);//0:个人提现
		//订单状态
		withdrawOrderAppDTO.setStatus(Long.valueOf(WithdrawOrderStatus.INIT.getValue()));
		//手续费
		withdrawOrderAppDTO.setFee(withdrawRequest.getFee());
		//设置withdrawType,规则引擎返回1 (手工)
		withdrawOrderAppDTO.setWithdrawType((short)withdrawRequest.getWithdrawType());
		
		//设置出款银行
		withdrawOrderAppDTO.setWithdrawBankCode(withdrawRequest.getWithdrawBankCode());
		withdrawOrderAppDTO.setOrderAmount(withdrawOrderAppDTO.getAmount());
		withdrawOrderAppDTO.setPayerAmount(withdrawOrderAppDTO.getAmount());
		Long seqId = withdrawOrderService.createWithdrawOrderRnTx(withdrawOrderAppDTO);
		
		withdrawRequest.setStatus(withdrawOrderAppDTO.getStatus());
		withdrawRequest.setBusiType(withdrawOrderAppDTO.getBusiType());
		withdrawRequest.setSeqId(seqId);
	}

	public void setWithdrawOrderService(
			WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}
}
