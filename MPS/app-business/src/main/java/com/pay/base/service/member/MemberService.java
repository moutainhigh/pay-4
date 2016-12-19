/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.base.service.member;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.base.common.enums.ServiceLevelEnum;
import com.pay.base.dto.MemberBalancesDto;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.MemberDto;
import com.pay.base.dto.MemberInfoDto;
import com.pay.base.dto.ResultDto;
import com.pay.inf.exception.AppException;

/**
 * 会员基本信息服务
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 上午11:57:36
 * 
 */
public interface MemberService {

	/**
	 * 根据会员号获取会员基本信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public MemberDto findByMemberCode(long memberCode);

	/**
	 * 根据会员号更新会员基本信息
	 * 
	 * @param memberDto
	 * @return
	 */
	public int updateMember(MemberDto memberDto);

	/**
	 * 根据会员号更新会员ssoUserid
	 * 
	 * @param memberDto
	 * @return
	 */
	public boolean updateMemberSsoUserid(Long memberCode, String ssoUserid);

	/**
	 * 激活企业用户并补全信息
	 *
	 * @param memberDto
	 * @param checkCode
	 * @param operatorIdentity
	 * @param oldIdentity
	 * @return
	 * @throws AppException
	 */
	public ResultDto activeEnterpriseRnTx(MemberDto memberDto,
			String checkCode, String operatorIdentity, String oldIdentity,
			String payPasswordint, int securityLvl, int scale)
			throws AppException;

	/**
	 * 根据登录名和会员类型获取会员信息
	 *
	 * @param loginName
	 * @param type
	 * @return
	 */
	public MemberDto findMemberByLogin(String loginName, int type);

	/**
	 * 查询用户
	 * 
	 * @param memberCode
	 *            帐号
	 * @return
	 */
	public MemberInfoDto queryMemberInfoByMemberCodeNsTx(Long memberCode);

	/**
	 * 校验登录名是否唯一
	 * 
	 * @param loginName
	 * @param loginType
	 * @return
	 */
	public boolean checkLoginNameByRegister(String loginName, int loginType);

	/**
	 * 校验登录名是否唯一
	 * 
	 * @param loginName
	 * @param loginType
	 * @return status
	 */
	public Integer checkLoginNameByRegister(String loginName);

	/**
	 * 个人会员注册
	 * 
	 * @param memberInfoDto
	 * @return
	 */
	public ResultDto doIndividualMemberRegisterRdTx(MemberInfoDto memberInfoDto)
			throws AppException;

	/* public ResultDto doIndividualMemberRegister(MemberInfoDto memberInfoDto); */

	/**
	 * 更新个人信息
	 * 
	 * @param memberInfoDto
	 * @return
	 */
	public ResultDto doUpdateMemberInfoRnTx(MemberInfoDto memberInfoDto);

	/**
	 * 会员设置安全问题
	 * 
	 * @param memberCode
	 *            会员号
	 * @param securQuestionId
	 *            安全问题ID
	 * @param answer
	 *            安全问题答案
	 * @return ResultDto:Object=MemberCode
	 */
	public ResultDto resetSecurityQuestionRnTx(Long memberCode,
			Integer securQuestionId, String answer);

	/**
	 * 个人会员设置登录密码
	 * 
	 * @param memberCode
	 *            会员号
	 * @param loginPwd
	 *            登录密码
	 * @param operatorId
	 *            操作员ID
	 * @return ResultDto
	 */
	public ResultDto resetLoginPwdRnTx(Long memberCode, String loginPwd,
			Long operatorId);

	/**
	 * 个人会员验证登录密码
	 * 
	 * @param memberCode
	 *            会员号
	 * @param loginPwd
	 *            登录密码
	 * @return ResultDto:Object=MemberCode
	 */
	public ResultDto verifyLoginPwdNsTx(Long memberCode, String loginPwd);

	/**
	 * 会员验证登录密码
	 * 
	 * @param memberCode
	 *            会员号
	 * @param loginPwd
	 *            登录密码
	 * @param operatorId
	 *            操作员号
	 * @return ResultDto:Object=MemberCode
	 */
	public ResultDto verifyLoginPwd(Long memberCode, String loginPwd,
			Long operatorId);

	/**
	 * 个人会员更新邮箱
	 * 
	 * @param memberCode
	 *            会员号
	 * @param email
	 *            邮箱
	 * @param email
	 *            原邮箱
	 * @return ResultDto:Object=MemberCode
	 */
	public ResultDto updateEmailRnTx(Long memberCode, String email,
			String oldEmail);

	/**
	 * 个人会员更新手机号码
	 * 
	 * @param memberCode
	 *            会员号
	 * @param mobile
	 *            手机号码
	 * @param mobile
	 *            原手机号码
	 * @return ResultDto:Object=MemberCode
	 */
	public ResultDto updateMobileRnTx(Long memberCode, String mobile,
			String oldMobile);

	/**
	 * 个人会员激活
	 * 
	 * @param memberCode
	 * @param activeType
	 * @param cdate
	 * @return
	 */
	public ResultDto doActiveMemberRnTx(Long memberCode,
			final String activeType, Date cdate);

	public MemberCriteria queryMemberCriteriaByMemberCodeNsTx(Long memberCode);

	/**
	 * 验证会员的安全问题
	 * 
	 * @param memberCode
	 *            会员号
	 * @param questionId
	 *            安全问题
	 * @param securInfo
	 *            安全信息
	 * @return
	 */
	public ResultDto validateSecurMemberQuestionWidthMemberInfo(
			Long memberCode, Integer questionId, String securInfo);

	/**
	 * 查询ssoUserid是否关联
	 * 
	 * @param ssoUserid
	 * @return
	 */
	public MemberDto queryMemberBySsoUserid(String ssoUserid);

	/**
	 * 查询ssoUserid是否关联
	 * 
	 * @param ssoUserid
	 * @return
	 */
	public MemberDto queryPersonMemberBySsoUserid(String ssoUserid);

	/**
	 * 根据会员登录名，查询会员信息
	 * 
	 * @param loginName
	 * @return
	 */
	public MemberDto findMemberByLoginName(String loginName);

	/**
	 * 根据会员登录名，查询会员号
	 * 
	 * @param loginName
	 * @param type
	 * @return
	 */
	public Long findMemberCodeByLoginName(String loginName);

	/**
	 * 更新企业会员信息
	 * 
	 * @param memberInfoDto
	 * @return
	 */
	public ResultDto doUpdateCorpMemberInfoRnTx(MemberInfoDto emberInfoDto);

	/**
	 * 根据会员号更新会员状态
	 *
	 * @param memberCode
	 * @param status
	 * @return
	 */
	public int updateStatusByMemberCode(long memberCode, int status);

	/**
	 * 更新会员服务级别
	 * 
	 * @param serviceLevel
	 * @param memberCode
	 * @return
	 */
	public int updateServiceLevelByMemCode(ServiceLevelEnum serviceLevel,
			long memberCode);

	/**
	 * 更新 用户为 个人卖家 身份
	 * 
	 * @author Sunny Ying
	 * @param map
	 * @throw
	 * @return int
	 */
	public int editMemberTypeByMemberCode(Map map);

	/**
	 * 更新临时用户用户信息
	 * 
	 * @param memberInfoDto
	 * @param memberCode
	 * @param payPassword
	 * @return
	 * @throws AppException
	 */
	public ResultDto doUpdateTempMemberInfoRnTx(MemberInfoDto memberInfoDto,
			Long memberCode, String payPassword) throws AppException;

	/**
	 * 根据父会员编号查询子会员信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public List<MemberBalancesDto> queryMemberByParent(Map<String, Object> param);

	/**
	 * 根据父会员编号查询子会员数量
	 * 
	 * @param param
	 * @return
	 */
	public int queryMemberCountByParent(Map<String, Object> param);

}
