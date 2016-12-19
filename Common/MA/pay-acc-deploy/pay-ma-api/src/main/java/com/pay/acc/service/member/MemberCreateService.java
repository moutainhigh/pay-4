package com.pay.acc.service.member;

import com.pay.acc.service.member.dto.MemberCreateResult;
import com.pay.inf.exception.AppException;

/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-11-15 上午11:17:24
 * 创建临时会员服务
 */
public interface MemberCreateService {
	
	/**
	 * 创建临时会员
	 * @param PhoneNo
	 * @return MemberCreateResult
	 * @throws AppException
	 */
	public MemberCreateResult doCreateTempMemberRdTx(String loginName)throws AppException;
}
