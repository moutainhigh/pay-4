package com.pay.acc.service.cidverify;

import com.pay.acc.member.dto.IdcVerifyGovDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-10-2 下午06:33:37
 */
public interface IdcVerifyGovService {

	/**
	 * 保存请求记录
	 * 
	 * @param idcVerifyGov
	 * @return
	 */
	IdcVerifyGovDto saveGov(IdcVerifyGovDto idcVerifyGov);

	/**
	 * 新增
	 * 
	 * @param idcVerifyGov
	 */
	public long insertIdcVerifyGov(IdcVerifyGovDto idcVerifyGov)
			throws MemberException, MemberUnknowException;

	/**
	 * 更新
	 * 
	 * @param idcVerifyGov
	 */
	public boolean updateIdcVerifyGov(IdcVerifyGovDto idcVerifyGov)
			throws MemberException, MemberUnknowException;
}
