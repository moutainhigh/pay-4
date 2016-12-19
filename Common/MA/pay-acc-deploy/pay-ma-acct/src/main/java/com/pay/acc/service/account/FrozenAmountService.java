package com.pay.acc.service.account;

import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;

/**
 * 冻结余额接口
 * 
 * @author jim_chen
 * @version 2011-2-25
 */
public interface FrozenAmountService {

	/**
	 * 冻结金额，先调更新余额接口，然后将冻结的金额记录到t_acct中的FROZEN_AMOUNT
	 * 产生一个新事务，更新余额的方法重构一下，封装一下，提供一个没有事务的方法
	 * 
	 * @param updateBalanceRequestDto
	 * @param payType
	 * @return
	 */
	public boolean doFrozenAmountRnTx(CalFeeReponseDto updateBalanceRequestDto,
			Integer payType) throws MaAcctBalanceException;

	/**
	 * 解冻金额
	 * 
	 * 调冲正交易接口，冲正接口重构一下
	 * 
	 * @param flushesOrderId
	 *            原冻结订单
	 * @param orderId
	 *            解冻订单
	 * @param dealCode
	 *            记账code
	 * @param amount
	 *            解冻金额
	 * @param dealType
	 *            交易类型
	 * @return
	 * @throws MaAcctBalanceException
	 */
	public boolean doUnFrozenAmountRnTx(CalFeeReponseDto calRequestDto,
			String flushesOrderId, String orderId, Integer dealCode,
			Long amount, Integer dealType) throws MaAcctBalanceException;

	/**
	 * 解冻金额 （相当于对原来的交易做退款操作）
	 * 
	 * @param updateBalanceRequestDto
	 * @param payType
	 * @return
	 */
	public boolean doUnFrozenAmountRnTx(
			CalFeeReponseDto updateBalanceRequestDto, Integer payType)
			throws MaAcctBalanceException;

}
