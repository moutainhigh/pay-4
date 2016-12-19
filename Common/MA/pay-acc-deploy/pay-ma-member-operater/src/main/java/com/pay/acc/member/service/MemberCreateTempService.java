package com.pay.acc.member.service;

import com.pay.acc.member.dto.ResultDto;
import com.pay.inf.exception.AppException;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-11-15 上午11:17:24
 * 创建临时会员服务
 */
public interface MemberCreateTempService {
	
	/**
	 * 创建临时会员
	 * @param loginName 登录名(手机号/邮箱地址)
	 * @return MemberCreateResult
	 * @throws AppException
	 */
	public ResultDto createTempMember(String loginName)throws AppException;
	
	
	/**
	 * 创建手机支付临时会员
	 * @param loginName 登录名(手机号)
	 * @return MemberCreateResult
	 * @throws AppException
	 */
	public ResultDto createMpMember(String loginName,String realName,String loginPwd,String payPwd) throws AppException;
	
	/**
	 * 验证登录名 
	 * @param loginName 登录名(手机号)
	 * @return MemberCreateResult
	 * @throws AppException
	 */
	public boolean checkMpMemberLoginName(String loginName) ;
	
}
