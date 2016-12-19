package com.pay.acc.member.service;

import java.util.Map;

import com.pay.acc.member.dto.IdcVerifyDto;
import com.pay.acc.member.dto.MemberVerifyResultDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;

public interface IdcVerifyBaseService {


	
	/**
	 * 查询该会员是否验证成功
	 * 
	 * @param map
	 * @return true 成功记录 成功
	 */
	public boolean queryVerifySuccess(Map<String,Object> map) throws MemberException, MemberUnknowException;
	
	/**
	 * 查询该会员成功验证成功信息
	 * 
	 * @param map
	 * @return IdcVerifyDto  成功记录
	 */
	public IdcVerifyDto queryVerifyInfo(Map<String,Object> map) throws MemberException, MemberUnknowException;
	
	/**
	 * 根据memberCode查询实名认证信息
	 * @param memberCode
	 * @return MemberVerifyDto
	 * @exception Exception
	 * @author lei.jiangl
	 */
	public MemberVerifyResultDto QueryMemberVerifyByMemberCode(Long memberCode)  throws Exception;
}
