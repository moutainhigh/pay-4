package com.pay.pe.service.payment.posting;

import java.util.ArrayList;
import java.util.List;

import com.pay.pe.dao.accounting.impl.PostingProcAcctParameter;
import com.pay.pe.dao.accounting.impl.PostingProcParameter;
import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.exception.PEBisnessRuntimeException;

/**
 * @Company:  过账
 */
public interface PostingService {
    
    /**
	 * 过账：扣钱加钱
	 * 缺省为同步更新payee/payer的余额.
	 * @param accountEntryDTOs
	 * @return long 凭证号.
	 */
	long posting(List <AccountEntryDTO> accountEntryDTOs) throws PEBisnessRuntimeException;
    
//	long postingByProc(final List<AccountEntryDTO> accountEntryDTOs, List<NotifyTask> tasks)throws PEBisnessRuntimeException;
    /**
     * @param accountEntryDTOs
     * @param updatePayerSync
     * 是否可以异步更新收款方余额.
     * @param updatePayeeSync
     * 是否可以异步更新付款方余额.
     * @return long 凭证号.
     */
	long postingByProc(final List<AccountEntryDTO> accountEntryDTOs) throws PEBisnessRuntimeException;

	/**
	 * 将分录号为参数voucherCode的所有分录的状态改为参数status指定的状态.
	 * @param voucherCode String
	 * @param status int
	 */
	public void changeEntriesStatus(final String voucherCode, final int status);
	
	/**
	 * 执行存储过程记账和更新账户余额.
	 * @param procPara PostingProcParameter
	 * @return long
	 * 记账后产生的分录号.
	 */
	long callPostingProc(PostingProcParameter procPara)throws PEBisnessRuntimeException;
	
	/**
	 * 根据分录的voucherCode和entryCode确定到唯一一个entry，并更新其状态
	 * @param voucherCode
	 * @param entryCode
	 * @param status
	 */
	void changeEntryStatus(final String voucherCode, final Integer entryCode, final int status);
	
	/**
	 * 执行异步更新余额操作, 这里不做错误处理
	 * @param acctParameters
	 * @param voucherCode
	 */
	void asyncPosting(final List<PostingProcAcctParameter> acctParameters, final String voucherCode);
	
	/**
	 * 发送异步更新余额的消息
	 * @param voucherCode
	 * @param acctParameters
	 */
	void notifyAsyncChangeBalance(final long voucherCode,
			final ArrayList<PostingProcAcctParameter> acctParameters);
}
