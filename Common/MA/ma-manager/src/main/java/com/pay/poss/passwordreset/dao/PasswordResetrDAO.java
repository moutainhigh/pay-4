package com.pay.poss.passwordreset.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.passwordreset.dto.PasswordResetDto;
import com.pay.poss.passwordreset.model.PasswordReset;
/**
 * @Description
 * @project ma-membermanager
 * @file PasswordResetrDAO.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *  Date Author Changes 
 *  2010-12-15 tianqing_wang Create
 */
public interface PasswordResetrDAO extends BaseDAO<PasswordReset>{
	
	/**
     * 记录企业用户秘密重置记录
     * @param PasswordResetDto
     * @return Long
     */
	
	public Long createEnterprisePasswordReset(PasswordResetDto dto);
	
	/**
     * 查询企业会员密码重置
     * @param PasswordResetDto
     * @return List<PasswordResetDto>
     */
	public List<PasswordResetDto> queryEnterprisePasswordReset(PasswordResetDto dto);
	
	/**
     * 查询企业会员密码重置count
     * @param PasswordResetDto
     * @return Integer
     */
	public Integer countEnterprisePasswordReset(PasswordResetDto dto);
	
	/**
     * 查询企业会员密码重置详情
     * @param PasswordResetDto(id)
     * @return PasswordResetDto
     */
	public PasswordResetDto queryEnterprisePasswordResetById(PasswordResetDto dto);
	
	/**
     * 企业会员密码重置修改
     * @param PasswordResetDto 
     * @return Integer
     */
	public Integer updateEnterprisePasswordReset(PasswordResetDto dto);
	
	/**
     * 记录checkCode
     * @param mailCheckCodeMap
     * @param messageCheckCodeMap
     * @return void
     */
	@SuppressWarnings("unchecked")
	public void createCheckCode(Map mailCheckCodeMap,Map messageCheckCodeMap);
	
	/**
     * 由loginName取得memberCode
     * @param loginName
     * @return PasswordResetDto
     */
	public PasswordResetDto queryMemberCodeByLoginName(String loginName);
}
