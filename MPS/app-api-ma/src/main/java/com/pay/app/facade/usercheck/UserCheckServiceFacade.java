package com.pay.app.facade.usercheck;

import com.pay.app.facade.dto.ResultDto;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-8-17 下午02:24:23 账户关联服务
 */
public interface UserCheckServiceFacade {

	/**
	 * 根据sso_userId查询用户是否关联
	 */
	public boolean queryUserRelation(String userId);
	/**
	 * 社区用户与pay用户关联
	 */
	public int updateUserRelation(Long memberCode, String userId);
	/**
	 * 社区用户注册成为pay用户
	 */
	public void createUserRelation();
	/**
	 * 用户激活并关联
	 */
	public void userQualification(Long memberCode, String userId);
	/**
	 * 根据loginName,loginPwd验证用户，并返回用户membercode
	 */
	public Long checkUser(String loginName, String loginPwd);
	/**
	 * 社区用户忽略关联pay账户
	 */
	public void ignoreRelation();

	/**
	 * 验证账户是否关联
	 * 
	 * @param memberCode
	 * @return
	 */
	public boolean ValidateMemberRelation(Long memberCode);

	/**
	 * 根据memberCode查询关联ssoid
	 * 
	 * @param memberCode
	 * @return
	 */
	public String QueryMemberRelation(Long memberCode);
	
	
	/**
	 * 社区用户与pay用户关联
	 * @param memberCode
	 * @param userId
	 * @return
	 */
	ResultDto updateUserRelationNew(Long memberCode, String userId);
}
