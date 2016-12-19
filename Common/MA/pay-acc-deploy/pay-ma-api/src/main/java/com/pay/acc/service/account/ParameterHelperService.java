package com.pay.acc.service.account;

/**
 * @author Administrator
 * 
 */
public interface ParameterHelperService {

	/**
	 * 验证会员账号
	 * 
	 * @param acctCode
	 * @return 没有通过返回FALSE
	 */
	public boolean checkParameterAcctCodeOfMember(String acctCode);
}
