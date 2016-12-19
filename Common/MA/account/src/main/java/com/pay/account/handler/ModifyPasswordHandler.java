/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.account.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.common.MaConstant;
import com.pay.acc.exception.MaMemberException;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.AccountInfoService;
import com.pay.acc.service.account.AccountVerifyService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 
 * @author chma
 */
public class ModifyPasswordHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(ModifyPasswordHandler.class);
	private MemberService memberService;
	private MemberQueryService memberQueryService;
	private AccountVerifyService accountVerifyService;
	private AccountInfoService accountInfoService;

	public void setAccountVerifyService(
			AccountVerifyService accountVerifyService) {
		this.accountVerifyService = accountVerifyService;
	}

	public void setAccountInfoService(AccountInfoService accountInfoService) {
		this.accountInfoService = accountInfoService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();

		String loginName = paraMap.get("loginName");
		String type = paraMap.get("type");
		String oldPwd = paraMap.get("oldPwd");
		String password = paraMap.get("password");

		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);
		if (null == memberDto) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getErrorCode());
			result.put("responseDesc",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getMessage());
			return JSonUtil.toJSonString(result);
		}

		try {

			if ("1".equals(type)) {

				MaResultDto resultDto = memberQueryService
						.doVerifyLoginPassword(loginName, oldPwd);
				if (resultDto.getResultStatus() != MaConstant.SECCESS) {
					result.put("responseCode", resultDto.getErrorCode());
					result.put("responseDesc", resultDto.getErrorMsg());
					return JSonUtil.toJSonString(result);
				}
				memberService.updateLoginPwd(memberDto.getMemberCode(),
						password);

			} else if ("2".equals(type)) {

				// 验证原支付密码
				boolean verifyFlag = accountVerifyService
						.doVerifyAccountForPayPasswordNsTx(
								memberDto.getMemberCode(),
								AcctTypeEnum.BASIC_CNY.getCode(), oldPwd);
				if (!verifyFlag) {
					result.put("responseCode",
							ErrorExceptionEnum.ACCT_CHECK_PWD_ERROR
									.getErrorCode());
					result.put("responseDesc",
							ErrorExceptionEnum.ACCT_CHECK_PWD_ERROR
									.getMessage());
					return JSonUtil.toJSonString(result);
				}

				int resultFlag = accountInfoService.doResetPayPwdRnTx(
						memberDto.getMemberCode(), AcctTypeEnum.BASIC_CNY.getCode(),
						password);
				if (resultFlag != 1) {
					result.put("responseCode",
							ErrorExceptionEnum.MEMBER_UPDATE_PASSWORD
									.getErrorCode());
					result.put("responseDesc",
							ErrorExceptionEnum.MEMBER_UPDATE_PASSWORD
									.getMessage());
					return JSonUtil.toJSonString(result);
				}
			}

		} catch (MaMemberException e) {
			result.put("responseCode", e.getErrorEnum().getErrorCode());
			result.put("responseDesc", e.getErrorEnum().getMessage());
			return JSonUtil.toJSonString(result);
		}

		catch (Exception e) {
			logger.error("digest password error:", e);
			result.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
			return JSonUtil.toJSonString(result);
		}

		result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(result);
	}

}
