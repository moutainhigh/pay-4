package com.pay.poss.passwordreset.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.passwordreset.dao.PasswordResetrDAO;
import com.pay.poss.passwordreset.dto.PasswordResetDto;
import com.pay.poss.passwordreset.model.PasswordReset;
import com.pay.util.BeanConvertUtil;

/**
 * @Description
 * @project ma-membermanager
 * @file PasswordResetrDAOImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *  Date Author Changes 
 *  2010-12-15 tianqing_wang Create
 */

@SuppressWarnings("unchecked")
public class PasswordResetrDAOImpl extends BaseDAOImpl<PasswordReset> implements PasswordResetrDAO {
	
	public Long createEnterprisePasswordReset(PasswordResetDto dto){
		Long id = (Long)this.getSqlMapClientTemplate().insert("passwordreset.createEnterprise",BeanConvertUtil.convert(PasswordReset.class, dto));
		return id;
	}
	public List<PasswordResetDto> queryEnterprisePasswordReset(PasswordResetDto dto){
		return this.getSqlMapClientTemplate().queryForList("passwordreset.queryEnterprisePasswordReset",dto);
	}
	public Integer countEnterprisePasswordReset(PasswordResetDto dto){
		return (Integer)this.getSqlMapClientTemplate().queryForObject("passwordreset.countEnterprisePasswordReset",dto);
	}
	public PasswordResetDto queryEnterprisePasswordResetById(PasswordResetDto dto){
		return (PasswordResetDto)this.getSqlMapClientTemplate().queryForObject("passwordreset.queryEnterprisePasswordReset",dto);
	}
	public Integer updateEnterprisePasswordReset(PasswordResetDto dto){
		Integer i = (Integer)this.getSqlMapClientTemplate().update("passwordreset.updateEnterprisePasswordReset",dto);
		return i;
	}
	public void createCheckCode(Map checkCodeMap,Map messageCheckCodeMap){
		this.getSqlMapClientTemplate().insert("passwordreset.createCheckCode",checkCodeMap);
		//this.getSqlMapClientTemplate().insert("passwordreset.createCheckCode",messageCheckCodeMap);
	}
	public PasswordResetDto queryMemberCodeByLoginName(String loginName){
		return (PasswordResetDto)this.getSqlMapClientTemplate().queryForObject("passwordreset.queryMemberCodeByLoginName",loginName);
	} 
}
