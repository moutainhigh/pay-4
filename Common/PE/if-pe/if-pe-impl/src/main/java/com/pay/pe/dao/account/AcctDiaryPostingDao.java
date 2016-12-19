
package com.pay.pe.dao.account;



public interface AcctDiaryPostingDao {
	/**
	 * 执行存储过程生成日记账分录和更新日记账账户余额.
	 * @param procPara PostingProcParameter
	 * 	 
	 * */
	void callPostingProc(long voucherCode, Integer[] entries);
	
}