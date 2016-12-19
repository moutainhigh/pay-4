/**
 * 
 */
package com.pay.fundout.withdraw.service.paytoaccount;

import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.acc.service.member.dto.MemberInfoDto;


/**
 * @author NEW
 *
 */
public interface PayToAcctValidateService {
	/**
	 * 当月金额
	 * @return
	 */
	public Long getMonthTotalAmount(Long memberCode);
	
	/**
	 * 当日金额
	 * @return
	 */
	public Long getDayTotalAmount(Long memberCode);
	
	/**
	 * 当月笔数
	 * @return
	 */
	public Integer getMonthTotalCount(Long memberCode);
	
	/**
	 * 当日笔数
	 * @return
	 */
	public Integer getDayTotalCount(Long memberCode);
	
	/**
	 * 验证风控限额限次规则
	 * @param memberCode
	 * @param applyAmount
	 * @return
	 * @throws Exception 
	 */
	public String validateRCLimit(Long memberCode, Long applyAmount) throws Exception;
	
	/**
	 * 验证付款方信息
	 * @param memberCode
	 * @return
	 * @throws Exception 
	 */
	public String validatePayerInfo(Long memberCode) throws Exception;
	/**
	 * 验证付款方信息
	 * @param memberCode
	 * @param status
	 * @return
	 * @throws Exception
	 */
	public String validatePayerInfo(Long memberCode,Integer status) throws Exception;
	
	/**
	 * 验证收款方信息
	 * @param loginName
	 * @param payerMemberCode
	 * @return
	 * @throws Exception 
	 */
	public String validatePayeeInfo(String loginName,String payerMemberCode) throws Exception;
	
	/**
	 * 验证收款方信息
	 * @param payeeMemberInfo
	 * @param payerMemberCode
	 * @return
	 * @throws Exception
	 */
	public String validatePayeeInfo(MemberInfoDto payeeMemberInfo,String payerMemberCode) throws Exception;
	
	
}
