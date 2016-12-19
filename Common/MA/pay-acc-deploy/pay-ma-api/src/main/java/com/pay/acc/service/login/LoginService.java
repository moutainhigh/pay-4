/**
 * 
 */
package com.pay.acc.service.login;

import com.pay.acc.service.member.dto.MemberInfoDto;

/**
 * @author chaoyue
 * 
 */
public interface LoginService {

	/**
	 * 个人登录
	 * 
	 * @param loginName
	 * @param loginPwd
	 * @return
	 */
	MemberInfoDto login(String loginName, String loginPwd) throws Exception;
	
	/**
	 * 个人登录
	 * 
	 * @param loginName
	 * @param loginPwd
	 * @return
	 */
	MemberInfoDto login(Long memberCode, String loginPwd) throws Exception;

	/**
	 * 企业登录
	 * 
	 * @param loginName
	 * @param identity
	 * @param loginPwd
	 * @return
	 */
	MemberInfoDto login(String loginName, String identity, String loginPwd)
			throws Exception;
}
