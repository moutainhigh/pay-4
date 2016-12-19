/**
 * 
 */
package com.pay.acc.service.account;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acctattrib.dto.AcctAttribDto;

/**
 * 对更新余额相关入参，账户，账户属性，
 * 
 * @author Administrator
 * 
 */
public interface AccountHelperService {

	/**
	 * 根据会员号查询账户
	 * 
	 * @param acctCode
	 * @param isCheckAcctStatus 验证账户是否冻结
	 * @return 如果账户被冻结返回NULL
	 */
	public AcctDto queryAcct(String acctCode, boolean isCheckAcctStatus);
	
	/**
	 * 根据会员号查询账户
	 * @param acctCode
	 * @return 如果账户不存在返回NULL
	 */
	public AcctDto queryAcct(String acctCode);
	
	/**
	 * 根据账户号查询账户属性
	 * @param acctCode
	 * @return 如果不存在返回NULL
	 */
	public AcctAttribDto queryAcctAttrib(String acctCode);
	
	
	/**
	 * 更新贷方余额
	 * @param amount
	 * @param debitAmount
	 * @param acctCode
	 * @return
	 */
	public boolean executeUpdateDebitBalance(Long amount, Long debitAmount, String acctCode);
	
	
	/**
	 * 更新借方余额
	 * @param amount
	 * @param crebitAmount
	 * @param acctCode
	 * @return
	 */
	public boolean executeUpdateCrebitBalance(Long amount, Long crebitAmount,String acctCode);
	
	

}
