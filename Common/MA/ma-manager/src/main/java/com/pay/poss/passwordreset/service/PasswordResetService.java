package com.pay.poss.passwordreset.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.passwordreset.dto.PasswordResetDto;

/**
 * @Description
 * @project ma-membermanager
 * @file PasswordResetServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *          Date Author Changes 2010-12-15 tianqing_wang Create
 */

public interface PasswordResetService {

	/**
	 * 记录企业用户秘密重置记录
	 * 
	 * @param PasswordResetDto
	 * @return Long
	 */
	public Long createEnterprisePasswordReset(PasswordResetDto dto);

	/**
	 * 查询企业会员密码重置list
	 * 
	 * @param PasswordResetDto
	 * @return List<PasswordResetDto>
	 */
	public List<PasswordResetDto> queryEnterprisePasswordReset(
			PasswordResetDto dto, Page page);

	/**
	 * 查询企业会员密码重置详情
	 * 
	 * @param PasswordResetDto
	 *            (id)
	 * @return PasswordResetDto
	 */
	public PasswordResetDto queryEnterprisePasswordResetById(
			PasswordResetDto dto);

	/**
	 * 企业会员密码重置修改
	 * 
	 * @param PasswordResetDto
	 * @return Integer
	 */
	public Integer updateEnterprisePasswordReset(PasswordResetDto dto);

	/**
	 * 企业会员密码重置发送信息
	 * 
	 * @param PasswordResetDto
	 * @return Boolean
	 */
	public Boolean passwordResetConfirmTrans(PasswordResetDto dto)
			throws PossException;

	/**
	 * 由loginName取得memberCode
	 * 
	 * @param dto
	 * @return PasswordResetDto
	 */
	public PasswordResetDto queryMemberCodeByLoginName(String loginName);

}
