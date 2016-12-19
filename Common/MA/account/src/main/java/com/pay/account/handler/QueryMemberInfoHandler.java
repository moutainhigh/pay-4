/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.account.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.member.dto.IdcVerifyDto;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.member.service.MemberIdentityService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.cidverify.IdCardVerifyService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 找回密码
 * 
 * @author chma
 */
public class QueryMemberInfoHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(QueryMemberInfoHandler.class);
	private MemberService memberService;
	private MemberQueryService memberQueryService;
	private IdCardVerifyService idCardVerifyService;
	private AccountQueryService accountQueryService;
	private MemberIdentityService memberIdentityService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setIdCardVerifyService(IdCardVerifyService idCardVerifyService) {
		this.idCardVerifyService = idCardVerifyService;
	}

	public void setMemberIdentityService(
			MemberIdentityService memberIdentityService) {
		this.memberIdentityService = memberIdentityService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();

		String loginName = paraMap.get("loginName");

		if (StringUtils.isEmpty(loginName)) {
			result.put("responseCode",
					ResponseCodeEnum.INVALID_PARAMETER.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.INVALID_PARAMETER.getDesc());
			return JSonUtil.toJSonString(result);
		}

		// 获取用户信息
		MemberDto member = memberService.queryMemberByLoginName(loginName);

		if (member == null) {
			return buildFailResponse(result,
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR);
		}

		// 临时会员提示用户不存在
		if (member.getStatus() == MemberStatusEnum.PROVISIONAL.getCode()) {
			// 用户不存在
			return buildFailResponse(result,
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR);
		}
		Long memberCode = member.getMemberCode();

		MemberInfoDto memberInfo = memberQueryService.doQueryMemberInfoNsTx(
				member.getLoginName(), memberCode, member.getType(), null);

		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(memberCode, AcctTypeEnum.BASIC_CNY.getCode());
		// 设置安全问题
		memberInfo.setSecurityQuestion(member.getSecurityQuestion());
		memberInfo.setSecurityAnswer(member.getSecurityAnswer());
		memberInfo.setLoginPwd(member.getLoginPwd());
		memberInfo.setPayPwd(acctAttribDto.getPayPwd());

		IdcVerifyDto idcVerifyDto = idCardVerifyService
				.queryVerifyInfo(memberCode);
		if (null != idcVerifyDto) {
			memberInfo.setIdNo(idcVerifyDto.getPaperNo());
		}

		if (!StringUtil.isEmpty(acctAttribDto.getPayPwd())) {
			memberInfo.setIsSetupPayPwd(1);
		}

		BalancesDto balancesDto = accountQueryService.doQueryBalancesNsTx(
				memberInfo.getMemberCode(), AcctTypeEnum.BASIC_CNY.getCode());
		memberInfo.setBalance(balancesDto.getBalance());

		result.put("memberInfo", memberInfo);
		result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(result);
	}

	private String buildFailResponse(Map<String, Object> result,
			ErrorExceptionEnum errorExceptionEnum) {

		result.put("responseCode", errorExceptionEnum.getErrorCode());
		result.put("responseDesc", errorExceptionEnum.getMessage());
		return JSonUtil.toJSonString(result);
	}

}
