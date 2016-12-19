/**
 * 
 */
package com.pay.acc.acctattrib.service;

import java.util.List;
import java.util.Map;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.exception.AcctAttribException;
import com.pay.acc.acctattrib.exception.AcctAttribUnknowException;
import com.pay.acc.acctattrib.model.AcctAttrib;

/**
 * @author Administrator
 * 
 */
public interface AcctAttribService {

	/**
	 * 
	 * @param memberCode
	 * @param acctType
	 * @return
	 */
	public AcctAttribDto queryAcctAttribByMemberCodeAndAcctType(
			Long memberCode, Integer acctType);

	/**
	 * 查询默认账户
	 * 
	 * @param memberCode
	 * @return
	 */
	AcctAttribDto doQueryDefaultAcctAttribNsTx(Long memberCode);

	/**
	 * 根据memberCode查询全部账户
	 * 
	 * @param memberCode
	 * @return
	 */
	List<AcctAttribDto> QueryAcctAttribNsTx(Long memberCode);
	
	/**
	 * 
	 * @param memberCode
	 * @param currencyCode
	 * @return
	 */
	List<AcctAttribDto> doQueryAcctAttribByMemberCodeAndCurrencyCodeNsTx(
			Long memberCode, String currencyCode);

	/**
	 * @Title: countIsAllowAcctribByMemberCode
	 * @Description: 根据会员号查询账户属性
	 * @param @param paramMap
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean countIsAllowAcctribByMemberCode(Map<String, Object> paramMap);

	/**
	 * 根据会员账户号查询会员属性信息
	 * 
	 * @param memberAcctCode
	 * @return
	 * @throws AcctAttribException
	 * @throws AcctAttribUnknowException
	 */
	public AcctAttribDto queryAcctAttribWithMemberAcctCode(Long memberAcctCode)
			throws AcctAttribException, AcctAttribUnknowException;

	/**
	 * 根据账户号查询账户属性
	 * 
	 * @param acctCode
	 * @return
	 * @throws AcctAttribException
	 * @throws AcctAttribUnknowException
	 */
	public AcctAttribDto queryAcctAttribWithAcctCode(String acctCode)
			throws AcctAttribException, AcctAttribUnknowException;

	/**
	 * 根据会员的账户属性判断 是否请允许操作
	 * 
	 * @param paramMap
	 * @return
	 */
	public boolean isAllowAcctrib(Map<String, Object> paramMap);

	/**
	 * 重置支付密码
	 * 
	 * @param accoutCode
	 * @param newPayPwd
	 * @return
	 */
	public boolean resetAcctAttribPwd(String acctCode, String newPayPwd)
			throws AcctAttribException, AcctAttribUnknowException;

	/**
	 * 冻结或者解冻账户止入状态
	 * 
	 * @param acctCode
	 * @param status
	 * @return
	 * @throws AcctAttribException
	 * @throws AcctAttribUnknowException
	 */
	public boolean updateAcctFreezeAllowInStatusWithAcctCode(String acctCode,
			Integer status) throws AcctAttribException,
			AcctAttribUnknowException;

	/**
	 * 冻结或者解冻账户止出状态
	 * 
	 * @param acctCode
	 * @param status
	 * @return
	 * @throws AcctAttribException
	 * @throws AcctAttribUnknowException
	 */
	public boolean updateAcctFreezeAllowOutStatusWithAcctCode(String acctCode,
			Integer status) throws AcctAttribException,
			AcctAttribUnknowException;

	/**
	 * 冻结账户
	 * 
	 * @param acctCode
	 * @param status
	 * @return
	 * @throws AcctAttribException
	 * @throws AcctAttribUnknowException
	 */
	public boolean updateAcctFreezeWithAcctCode(String acctCode, Integer status)
			throws AcctAttribException, AcctAttribUnknowException;

	/**
	 * 判断不是后台止入的
	 * 
	 * @param acctCode
	 * @return
	 */
	public int countAcctAttribFreeze(String acctCode);

	/**
	 * 判断是否调用接口API止出
	 * 
	 * @param acctCode
	 * @return
	 */
	public int countAllowOutRecord(String acctCode);

	/**
	 * 
	 * @param acctCode
	 * @param status
	 * @param type
	 * @return
	 */
	public int countPossAcctAttrib(String acctCode, int status, int type);

	/**
	 * 根据会员号止出账户
	 * 
	 * @param memberCode
	 * @param status
	 * @return
	 * @throws AcctAttribException
	 * @throws AcctAttribUnknowException
	 */
	boolean updateAcctFreezeAllowOutStatusWithMemberCode(String memberCode,
			Integer status) throws AcctAttribException,
			AcctAttribUnknowException;

	/**
	 * 查询账户属性
	 * 
	 * @param acctCode
	 * @return
	 */
	public AcctAttribDto queryAcctAttribByAcctCode(String acctCode);

	/**
	 * 添加 一条 acctAttrib
	 * 
	 * @author Sunny Ying
	 * @param acctAttrib
	 * @throw null
	 * @return Long
	 */
	public Long createAcctAttrib(AcctAttribDto acctAttrib);

	/**
	 * 更新账户密码
	 * 
	 * @param acctAttrib
	 * @return
	 */
	public boolean updatePayPwd(String acctCode, String payPwd);


	List<AcctAttribDto> QueryByMemberCodeAndAcctTypeNsTx(Long memberCode);

}
