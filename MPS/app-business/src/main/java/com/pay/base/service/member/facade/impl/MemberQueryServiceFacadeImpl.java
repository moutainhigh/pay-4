package com.pay.base.service.member.facade.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.MemberQueryService;
import com.pay.base.service.member.facade.MemberQueryServiceFacade;

public class MemberQueryServiceFacadeImpl implements MemberQueryServiceFacade{

	
	private MemberQueryService memberQueryService;
	
	private static final Log log = LogFactory.getLog(MemberQueryServiceFacadeImpl.class);
	
	@Override
	public String queryLastLoginTime(Long memberCode) {
		String time="";
		try {
			time = memberQueryService.queryLastLoginTime(memberCode);
		} catch (Exception e) {
			log.error("memberQueryService.queryLastLoginTime throws error",e);
		}
		return time;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

}
