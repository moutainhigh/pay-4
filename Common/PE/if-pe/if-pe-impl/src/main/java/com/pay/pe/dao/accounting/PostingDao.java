
package com.pay.pe.dao.accounting;

import com.pay.pe.dao.accounting.impl.PostingProcParameter;


public interface PostingDao {
	/**
	 * 执行存储过程记账和更新账户余额.
	 * @param procPara PostingProcParameter
	 * @return long
	 * 记账后产生的分录号.
	 */
	long callPostingProc(PostingProcParameter procPara);
	
	/**
	 * 将分录号为参数voucherCode的所有分录的状态改为参数status指定的状态.
	 * @param voucherCode String
	 * @param status int
	 */
	void changeEntriesStatus(String voucherCode, int status);
	
	/**
	 * 根据分录的voucherCode和entryCode确定到唯一一个entry，并更新其状态
	 * @param voucherCode
	 * @param entryCode
	 * @param status
	 */
	void changeEntryStatus(final String voucherCode, final Integer entryCode, final int status);
}
