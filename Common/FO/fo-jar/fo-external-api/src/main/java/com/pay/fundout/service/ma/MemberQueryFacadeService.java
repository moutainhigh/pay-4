/**
 * 
 */
package com.pay.fundout.service.ma;

/**
 * @author NEW
 *
 */
public interface MemberQueryFacadeService {
	/**
	 * 根据会员号获取会员信息
	 * @param memberCode
	 * @return
	 */
	MemberInfo  getMemberInfo(Long memberCode);
	
	/**
	 * 根据登录名称获取会员信息
	 * @param loginName
	 * @return
	 */
	MemberInfo  getMemberInfo(String loginName);
	
	/**
	 * 验证是subMemberCode是否是memberCode的成员
	 * @param memberCode
	 * @param subMemberCode
	 * @return
	 */
	boolean isBePartOfTheBourse(Long memberCode,Long subMemberCode);
}
