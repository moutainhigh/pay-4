package com.pay.base.service.usercheck;

import com.pay.base.dto.ResultDto;




public interface UserCheckService {

	/**
	 * 根据sso_userId查询用户是否关联
	 */
	public boolean queryUserRelation(String userId);
	/**
	 * 验证账户是否关联
	 * 
	 * @param memberCode
	 * @return
	 */
	public boolean validateMemberRelation(Long memberCode);
	/**
	 * 社区用户与pay用户关联
	 */
	public Integer updateUserRelation(Long memberCode, String userId);

	/**
	 * 社区用户与pay用户关联
	 * @param memberCode
	 * @param userId
	 * @return
	 */
	public ResultDto updateUserRelationNew(Long memberCode, String userId);
	/**
	 * 根据loginName,loginPwd验证用户，并返回用户membercode
	 */
	public Long checkUser(String loginName, String loginPwd);
}
