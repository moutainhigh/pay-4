package com.pay.acc.service.member.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.dto.ResultDto;
import com.pay.acc.member.service.MemberCreateTempService;
import com.pay.acc.service.member.MemberCreateService;
import com.pay.acc.service.member.dto.MemberCreateResult;
import com.pay.inf.exception.AppException;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-11-15 上午11:50:49
 */
public class MemberCreateServiceImpl implements MemberCreateService {

	private MemberCreateTempService memberCreateTempService;
	private Log log = LogFactory.getLog(MemberCreateServiceImpl.class);
	private final String NAME ="匿名";
	@Override
	public MemberCreateResult doCreateTempMemberRdTx(String loginName) throws AppException {
		MemberCreateResult memberCreateResult = new MemberCreateResult();
		log.info("#########create temp member start.###########");
		ResultDto resultDto = memberCreateTempService.createTempMember(loginName);
		memberCreateResult.setMemberCode(resultDto.getMemberCode());
		memberCreateResult.setAcctCode(resultDto.getAcctCode());
		memberCreateResult.setTempAutoPwd(resultDto.getTempAutoPwd());
		memberCreateResult.setLoginName(resultDto.getLoginName());
		memberCreateResult.setName(NAME);
		log.info("#########create temp member done.###########");
		log.info("#########[membercode:" + resultDto.getMemberCode()
				+ "|acctcode:" + resultDto.getAcctCode() + "|autoPwd:"
				+ resultDto.getTempAutoPwd() + "|loginName:"
				+ resultDto.getLoginName() + "]###########");
		return memberCreateResult;
	}

	public void setMemberCreateTempService(
			MemberCreateTempService memberCreateTempService) {
		this.memberCreateTempService = memberCreateTempService;
	}

}
