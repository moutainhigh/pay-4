package com.pay.base.service.member.facade;

import com.pay.acc.service.member.dto.MemberCreateResult;
import com.pay.inf.exception.AppException;

public interface MemberCreateServiceFacade {

	public abstract MemberCreateResult doCreateTempMemberRdTx(String PhoneNo)
			throws AppException;

}