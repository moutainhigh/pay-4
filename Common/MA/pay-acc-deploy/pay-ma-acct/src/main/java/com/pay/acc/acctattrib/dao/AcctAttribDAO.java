/**
 * 
 */
package com.pay.acc.acctattrib.dao;

import java.util.Map;

import com.pay.acc.acctattrib.model.AcctAttrib;
import com.pay.inf.dao.BaseDAO;

/**
 * @author Administrator
 * 
 */
public interface AcctAttribDAO extends BaseDAO<AcctAttrib> {

	boolean isAllowAcctrib(Map<String, Object> paramMap);

	/**
	 * 根据账户号查询账户属性
	 * 
	 * @param acctCode
	 * @return
	 */
	public AcctAttrib queryAcctAttribWithAcctCode(String acctCode);

	/**
	 * 账户支付密码重置 key:accountCode key:newPayPwd
	 * 
	 * @param map
	 * @return
	 */
	public int updateAcctAttribPwd(Map<String, Object> map);

	/**
	 * 根据会员账户号查询会员属性
	 * 
	 * @param memberAcctCode
	 * @return
	 */
	public AcctAttrib queryAcctAttribWithMemberAcctCode(Long memberAcctCode);

	/**
	 * 止入
	 * 
	 * @param acctCode
	 * @param status
	 * @return
	 */
	public boolean updateAcctAllowInStatus(String acctCode, Integer status);

	/**
	 * 止出
	 * 
	 * @param acctCode
	 * @param status
	 * @return
	 */
	public boolean updateAcctAllowOutStatus(String acctCode, Integer status);

	/**
	 * 冻结账户
	 * 
	 * @param acctCode
	 * @param status
	 * @return
	 */
	public boolean updateAcctFreeze(String acctCode, Integer status);

	/**
	 * 
	 * @param acctCode
	 * @return
	 */
	public int countAcctAttribFreeze(String acctCode);

	/**
	 * 查询是否有止出的操作记录
	 * 
	 * @param acctCode
	 * @return
	 */
	public int countAllowOutRecord(String acctCode);

	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	public int countPossAcctAttrib(Map<String, Object> paramMap);

	/**
	 * @Title: countIsAllowAcctribByMemberCode
	 * @Description:根据会员号查询账户属性
	 * @param @param paramMap
	 * @param @return 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean countIsAllowAcctribByMemberCode(Map<String, Object> paramMap);

	/**
	 * 
	 * @param memberCode
	 * @param status
	 * @return
	 */
	public boolean updateAcctAllowOutStatusWithMemberCode(String memberCode,
			Integer status);
}
