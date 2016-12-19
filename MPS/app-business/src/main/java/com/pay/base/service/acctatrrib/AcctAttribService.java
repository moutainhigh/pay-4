package com.pay.base.service.acctatrrib;

import java.util.List;
import java.util.Map;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.base.model.AcctAttrib;
import com.pay.base.model.PseudoAcct;


public interface AcctAttribService {
	
	/**
	 * 查询账户属性
	 * @param acctCode
	 * @return
	 */
	public AcctAttribDto queryAcctAttribByAcctCode(String acctCode);
	
	
	/**
	 * 充值支付密码
	 * @param acctCode
	 * @param newPayPwd
	 * @return
	 */
	public boolean resetAcctAttribPwd(String acctCode, String newPayPwd);
	
	boolean resetAcctAttribPwd(String acctCode,String memberCode,String newPayPwd);
	
	
	/**
	 * 添加 一条 acctAttrib
	 * @author Sunny Ying
	 * @param acctAttrib
	 * @throw null
	 * @return Long
	 */
	public abstract Long createAcctAttrib(AcctAttrib acctAttrib);

	/**
	 * 验证 用户下载密钥输入的支付密码是否正确
	 * @param map
	 * @return
	 */
	public AcctAttrib checkPaymentPwd(Map<String, String> map);
	
		/**
	 * 通过会员号查询账户的货币类型
	 * @param currency
	 * @return
	 */
	List<PseudoAcct> queryAcctCurrencyByMemberCode(Long memberCode) ;
}