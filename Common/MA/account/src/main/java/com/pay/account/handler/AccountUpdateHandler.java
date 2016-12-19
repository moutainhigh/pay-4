package com.pay.account.handler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

/**
 * 更新余额
 * 
 * @author terry
 */
public class AccountUpdateHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(AccountUpdateHandler.class);
	private MemberService memberService;
	private AcctService acctService;
	private AcctAttribService acctAttribService;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();

		String loginName = paraMap.get("loginName");
		String type = paraMap.get("type");
		String amount = paraMap.get("amount");

		if (StringUtils.isEmpty(loginName)) {
			result.put("responseCode",
					ResponseCodeEnum.INVALID_PARAMETER.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.INVALID_PARAMETER.getDesc());
			return JSonUtil.toJSonString(result);
		}

		// 获取用户信息
		MemberDto member = memberService.queryMemberByLoginName(StringUtils
				.trim(StringUtils.lowerCase(loginName)));

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

		String memberAcctCode = memberCode + "" + AcctTypeEnum.BASIC_CNY.getCode();

		try {
			AcctAttribDto acctAttribDto = acctAttribService
					.queryAcctAttribWithMemberAcctCode(Long
							.valueOf(memberAcctCode));

			Long updateAmount = (new BigDecimal(amount)
					.multiply(new BigDecimal("10")).longValue());

			logger.info("updateAmount:" + updateAmount);
			
			//更新账户金额			
			if ("1".equals(type)) {
				acctService.updateAcctDebitBalanceWithAcctCode(updateAmount,
						updateAmount, acctAttribDto.getAcctCode(), true);
			} else {
				acctService.updateAcctCreditBalanceWithAcctCode(updateAmount,
						updateAmount, acctAttribDto.getAcctCode(), true);
			}
			result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("update balance error:", e);
			result.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		
		return JSonUtil.toJSonString(result);
	}

	private String buildFailResponse(Map<String, Object> result,
			ErrorExceptionEnum errorExceptionEnum) {

		result.put("responseCode", errorExceptionEnum.getErrorCode());
		result.put("responseDesc", errorExceptionEnum.getMessage());
		return JSonUtil.toJSonString(result);
	}

}
