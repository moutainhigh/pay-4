package com.pay.fundout.withdraw.service.paytobank.impl;

import com.pay.fundout.service.OrderStatus;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.requestproc.WithdrawRequestDTO;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.fundout.withdraw.service.paytobank.ProcessSaveWithDrawOrderService;

/**
 * 存储订单
 */
public class ProcessSaveWithDrawOrderServiceImpl implements ProcessSaveWithDrawOrderService {

	// 存储订单
	private WithdrawOrderService withdrawOrderService;

	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	// 存储订单
	@Override
	public WithdrawRequestDTO saveWithDrawOrder(WithdrawRequestDTO withdrawRequestDTO) throws Exception {

		WithdrawOrderAppDTO withdrawOrderAppDTO = new WithdrawOrderAppDTO();
		withdrawOrderAppDTO.setMemberCode(Long.valueOf(withdrawRequestDTO.getMemberCode()));
		withdrawOrderAppDTO.setMemberAcc(withdrawRequestDTO.getMemberAcc());
		withdrawOrderAppDTO.setMemberType(Long.valueOf(withdrawRequestDTO.getMemberType()));
		withdrawOrderAppDTO.setMemberAccType(withdrawRequestDTO.getMemberAccType());
		withdrawOrderAppDTO.setAmount(withdrawRequestDTO.getApplyWithdrawAmount());
		withdrawOrderAppDTO.setPrioritys(withdrawRequestDTO.getPrioritys());
		withdrawOrderAppDTO.setAccountName(withdrawRequestDTO.getAccountName());
		withdrawOrderAppDTO.setBankAcct(withdrawRequestDTO.getBankAcct());
		withdrawOrderAppDTO.setBankAcctType(withdrawRequestDTO.getBankAcctType());
		withdrawOrderAppDTO.setBankKy(withdrawRequestDTO.getBankKy());
		withdrawOrderAppDTO.setOrderRemarks(withdrawRequestDTO.getOrderRemarks());
		withdrawOrderAppDTO.setBankBranch(withdrawRequestDTO.getBankBranch());
		withdrawOrderAppDTO.setBankProvince(withdrawRequestDTO.getBankProvince());
		withdrawOrderAppDTO.setBankCity(withdrawRequestDTO.getBankCity());
		
		// 类型
		if(null != withdrawRequestDTO.getType()){
			withdrawOrderAppDTO.setType(withdrawRequestDTO.getType());// 提现类别
		}else{
			withdrawOrderAppDTO.setType(withdrawRequestDTO.isBusiness() ? 1L : 0L);// 提现类别
		}
		withdrawOrderAppDTO.setTradeType(withdrawRequestDTO.isBusiness() ? 1 : 0);// 交易类型
		withdrawOrderAppDTO.setBusiType(0L);// 0:个人提现
		// 订单状态
		withdrawOrderAppDTO.setStatus(Long.valueOf(OrderStatus.INIT.getValue()));
		// 手续费
		withdrawOrderAppDTO.setFee(withdrawRequestDTO.getFee());
		// 设置withdrawType,规则引擎返回1 (手工)
		withdrawOrderAppDTO.setWithdrawType((short) withdrawRequestDTO.getWithdrawType());

		// 设置出款银行
		withdrawOrderAppDTO.setWithdrawBankCode(withdrawRequestDTO.getWithdrawBankCode());
		withdrawOrderAppDTO.setOrderAmount(withdrawOrderAppDTO.getAmount());
		withdrawOrderAppDTO.setPayerAmount(withdrawOrderAppDTO.getAmount());
		Long seqId = withdrawOrderService.createWithdrawOrderRnTx(withdrawOrderAppDTO);

		if(seqId == null)
			return null;
		
		withdrawRequestDTO.setStatus(withdrawOrderAppDTO.getStatus());
		withdrawRequestDTO.setBusiType(withdrawOrderAppDTO.getBusiType());
		withdrawRequestDTO.setSeqId(seqId);

		return withdrawRequestDTO;
	}

}
