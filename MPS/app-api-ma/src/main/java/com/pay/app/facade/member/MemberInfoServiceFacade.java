package com.pay.app.facade.member;

import java.util.List;

import com.pay.app.facade.dto.MemberCriteria;
import com.pay.app.facade.dto.MemberRegisterBO;
import com.pay.app.facade.dto.PowersModel;
import com.pay.app.facade.dto.ResultDto;

/**
 * File: MemberInfoServiceFacade.java Description: Copyright 2006-2010 pay
 * Corporation. All rights reserved. Date 2010-8-13 Author zengjin Changes
 * Comment
 */
public interface MemberInfoServiceFacade {

	/**
	 * 获取会员基本信息
	 * 
	 * @param userId
	 * @return
	 */
	MemberCriteria queryMemberInfoByUserId(final String userId);

	/**
	 * 获取权限菜单列表
	 * 
	 * @param memberCode
	 * @return
	 */
	List<PowersModel> getFeatureMenu(String memberCode);

	/**
	 * 会员激活
	 * 
	 * @param memerCode
	 * @param activeType
	 * @param cdate
	 *            发送激活验证信息时间
	 * @return
	 */
	//ResultDto doActiveMemberRnTx(long memerCode, final String activeType,
	//		Date cdate);

	/**
	 * 支付网站会员登录
	 * 
	 * @param userName
	 * @param passWord
	 * @return 会员信息
	 */
	ResultDto memberLogin(String userName, String passWord);

	/**
	 * 支付注册
	 * 
	 * @param memberRegisterBO
	 * @return
	 */
	ResultDto doMemberRegister(MemberRegisterBO memberRegisterBO);

	/**
	 * 校验重复的登录名
	 * 
	 * @param regType
	 * @param loginName
	 * @return
	 */
	boolean checkLoginName(String regType, String loginName);

	MemberCriteria queryMemberInfoByMemberCode(final Long memberCode);

	MemberCriteria queryMallMemberInfoByMemberCode(final Long memberCode);

	/**
	 * 根据用户名查询memberCode
	 * 
	 * @param username
	 * @return
	 */
	Long queryMemberCodeByUserName(String username);

}