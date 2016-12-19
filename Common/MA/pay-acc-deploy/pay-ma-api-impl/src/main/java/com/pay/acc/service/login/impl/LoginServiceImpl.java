/**
 * 
 */
package com.pay.acc.service.login.impl;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.common.MaConstant;
import com.pay.acc.exception.MaMemberException;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.dto.MemberOperateDto;
import com.pay.acc.member.memberenum.MemberOperateTypeEnum;
import com.pay.acc.member.service.MemberOperateService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.login.LoginService;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.util.StringUtil;

/**
 * @author chaoyue
 * 
 */
public class LoginServiceImpl implements LoginService {

	private final Log logger = LogFactory.getLog(getClass());
	private MemberService memberService;
	private MemberQueryService memberQueryService;
	private AccountQueryService accountQueryService;
	private MemberOperateService memberOperateService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setMemberOperateService(
			MemberOperateService memberOperateService) {
		this.memberOperateService = memberOperateService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.service.login.LoginService#individualMemberLogin(java.lang
	 * .String, java.lang.String)
	 */
	@Override
	public MemberInfoDto login(String loginName, String loginPwd)
			throws Exception {

		MemberInfoDto memberInfoDto = new MemberInfoDto();
		if (StringUtil.isEmpty(loginName)) {
			throw new MaMemberException(
					ErrorExceptionEnum.MEMBER_LOGINNAME_ERROR, "登录名为空！");
		}
		if (StringUtil.isEmpty(loginPwd)) {
			throw new MaMemberException(
					ErrorExceptionEnum.MEMBER_LOGINNAME_ERROR, "输入密码为空！");
		}

		// 转为小写
		loginName = StringUtils.lowerCase(loginName);
		// 检查是否为个人会员登录
		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);
		if (memberDto == null) {
			throw new MaMemberException(
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR, "会员不存在！");
		}
		// 校验个人会员登录
		MaResultDto maResultDto = memberQueryService.doLogin(loginName,
				loginPwd);
		if (MaConstant.SECCESS != maResultDto.getResultStatus()) {
			logger.error("errorCode:" + maResultDto.getErrorCode());
			logger.error("errorMsg:" + maResultDto.getErrorMsg());

			if (maResultDto.getResultStatus() == MaConstant.ERROR_NOT_LOCK
					|| maResultDto.getResultStatus() == MaConstant.ERROR_AND_LOCK) {
				throw new MaMemberException(
						ErrorExceptionEnum.ACCT_CHECK_PWD_ERROR, "登录异常！");
			}
			throw new MaMemberException(ErrorExceptionEnum.UNKNOW_ERROR,
					"登录异常！");
		}

		memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(loginName,
				memberDto.getMemberCode(), memberDto.getType(), null);

		BalancesDto balancesDto = accountQueryService.doQueryBalancesNsTx(
				memberDto.getMemberCode(), AcctTypeEnum.BASIC_CNY.getCode());
		memberInfoDto.setBalance(balancesDto.getBalance());

		MemberOperateDto mo = memberOperateService.queryMemberOperate(
				memberDto.getMemberCode(),
				MemberOperateTypeEnum.LOGIN_PWD.getCode());
		if (null != mo && null != mo.getLastLoginTime()) {
			memberInfoDto.setLastLoginTime(new SimpleDateFormat(
					"yyyy-MM-dd HH:MM:ss").format(mo.getLastLoginTime()));
		}

		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(memberDto.getMemberCode(),
						AcctTypeEnum.BASIC_CNY.getCode());
		if (!StringUtil.isEmpty(acctAttribDto.getPayPwd())) {
			memberInfoDto.setIsSetupPayPwd(1);
		}
		return memberInfoDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.acc.service.login.LoginService#enterpriceMemberLogin(java.lang
	 * .String, java.lang.String, java.lang.String)
	 */
	@Override
	public MemberInfoDto login(String loginName, String identity,
			String loginPwd) throws Exception {

		MemberInfoDto memberInfoDto = new MemberInfoDto();

		return memberInfoDto;

	}

	@Override
	public MemberInfoDto login(Long memberCode, String loginPwd)
			throws Exception {
		MemberInfoDto memberInfoDto = new MemberInfoDto();

		if (StringUtil.isEmpty(loginPwd)) {
			throw new MaMemberException(
					ErrorExceptionEnum.MEMBER_LOGINNAME_ERROR, "输入密码为空！");
		}

		// 检查是否为个人会员登录
		MemberDto memberDto = memberService.queryMemberByMemberCode(memberCode);
		if (memberDto == null) {
			throw new MaMemberException(
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR, "会员不存在！");
		}
		// 校验个人会员登录
		String loginName = memberDto.getLoginName();
		MaResultDto maResultDto = memberQueryService.doLogin(loginName,
				loginPwd);
		if (MaConstant.SECCESS != maResultDto.getResultStatus()) {
			logger.error("errorCode:" + maResultDto.getErrorCode());
			logger.error("errorMsg:" + maResultDto.getErrorMsg());

			if (maResultDto.getResultStatus() == MaConstant.ERROR_NOT_LOCK
					|| maResultDto.getResultStatus() == MaConstant.ERROR_AND_LOCK) {
				throw new MaMemberException(
						ErrorExceptionEnum.ACCT_CHECK_PWD_ERROR, "登录异常！");
			}
			throw new MaMemberException(ErrorExceptionEnum.UNKNOW_ERROR,
					"登录异常！");
		}

		memberInfoDto = memberQueryService.doQueryMemberInfoNsTx(loginName,
				memberDto.getMemberCode(), memberDto.getType(), null);

		BalancesDto balancesDto = accountQueryService.doQueryBalancesNsTx(
				memberDto.getMemberCode(), AcctTypeEnum.BASIC_CNY.getCode());
		memberInfoDto.setBalance(balancesDto.getBalance());

		MemberOperateDto mo = memberOperateService.queryMemberOperate(
				memberDto.getMemberCode(),
				MemberOperateTypeEnum.LOGIN_PWD.getCode());
		if (null != mo && null != mo.getLastLoginTime()) {
			memberInfoDto.setLastLoginTime(new SimpleDateFormat(
					"yyyy-MM-dd HH:MM:ss").format(mo.getLastLoginTime()));
		}

		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(memberDto.getMemberCode(),
						AcctTypeEnum.BASIC_CNY.getCode());
		if (!StringUtil.isEmpty(acctAttribDto.getPayPwd())) {
			memberInfoDto.setIsSetupPayPwd(1);
		}
		return memberInfoDto;
	}

}
