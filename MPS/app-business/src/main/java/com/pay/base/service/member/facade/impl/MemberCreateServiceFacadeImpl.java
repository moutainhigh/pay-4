package com.pay.base.service.member.facade.impl;

import com.pay.acc.service.member.MemberCreateService;
import com.pay.acc.service.member.dto.MemberCreateResult;
import com.pay.base.service.member.facade.MemberCreateServiceFacade;
import com.pay.inf.exception.AppException;

public class MemberCreateServiceFacadeImpl implements MemberCreateServiceFacade {

	private MemberCreateService memberCreateService;

	
	/* (non-Javadoc)
	 * @see com.pay.base.service.member.facade.impl.MemberCreateServiceFacade#doCreateTempMemberRdTx(java.lang.String)
	 */
	public MemberCreateResult doCreateTempMemberRdTx(String PhoneNo) throws AppException{
		return memberCreateService.doCreateTempMemberRdTx(PhoneNo);
	}
	public MemberCreateService getMemberCreateService() {
		return memberCreateService;
	}

	public void setMemberCreateService(MemberCreateService memberCreateService) {
		this.memberCreateService = memberCreateService;
	}
}
