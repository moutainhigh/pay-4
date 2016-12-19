package com.pay.acc.service.account;

import java.util.List;

import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.dto.FreezeBalanceDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;

public interface AccountSplitHandlerService {

	/**
	 * 批量更新余额接口
	 * @param updateBalanceRequestList
	 * @param freezeList（有值便根据里面的值针对账户做冻结）
	 * @param payType
	 * @return
	 * @throws MaAcctBalanceException
	 */
	public abstract boolean batchUpdateAcctBalanceRntx(
			List<CalFeeReponseDto> updateBalanceRequestList,
			List<FreezeBalanceDto> freezeList, Integer payType)
			throws MaAcctBalanceException;

	/**
	 * 分账接口
	 * @param updateBalanceRequestList
	 * @param unfreezeDto（需要解冻的单个账户）
	 * @param freezeList（需要冻结的多个分账账户）
	 * @param payType
	 * @return
	 */
	public abstract boolean batchSplitAcctBalanceRntx(
			List<CalFeeReponseDto> updateBalanceRequestList,
			FreezeBalanceDto unfreezeDto, List<FreezeBalanceDto> freezeList,
			Integer payType) throws MaAcctBalanceException;

	/**
	 * 批量解冻接口
	 * @param unfreezeList（需要解冻的多个账户）
	 * @return
	 */
	public abstract boolean batchUnFreezeBalanceRntx(
			List<FreezeBalanceDto> unfreezeList) throws MaAcctBalanceException;
	
	
	/**
	 * 批量分账退款接口
	 * @param unfreezeList（需要解冻的多个账户）
	 * @param updateBalanceRequestList
	 * @param payType
	 * @return
	 */
	public boolean batchRefundBalanceRntx(
			List<FreezeBalanceDto> unfreezeList,List<CalFeeReponseDto> updateBalanceRequestList,Integer payType) throws MaAcctBalanceException;

}