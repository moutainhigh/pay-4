package com.pay.account.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;


/**
 * @author xiaodai.Rainy
 * @time 2015-11-25
 * @param acctCode,
 * @param status
 * 账户冻结或者解冻
 */

public class AccountStatusUpdateHandler implements EventHandler {

	private AcctService acctService;
	private MemberService memberService;
	
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	public void setAcctAttribService(AcctAttribService acctAttribService) {
	}
	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public final Log logger = LogFactory.getLog(AccountQueryHandler.class);

	public String handle(String dataMsg) throws HessianInvokeException {

		@SuppressWarnings("unchecked")
		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();
		//获取参数校验
		String memberCode = paraMap.get("memberCode");
		String acctCode = paraMap.get("acctCode");
		String status = paraMap.get("status");
		
		if (StringUtils.isEmpty(acctCode)) {
			result.put("responseCode",
					ResponseCodeEnum.INVALID_PARAMETER.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.INVALID_PARAMETER.getDesc());
			return JSonUtil.toJSonString(result);
		}
		if (StringUtils.isEmpty(status)) {
			result.put("responseCode",
					ResponseCodeEnum.INVALID_PARAMETER.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.INVALID_PARAMETER.getDesc());

		}
		// 根据会员号查询会员信息

		MemberDto acctInfo=memberService.queryMemberByMemberCode(Long.valueOf(memberCode));
		//System.out.println(acctInfo+"============================");
				
		try {
			// 根据账户号更新账户信息
			boolean isSuccess = acctService.updateAcctFreezeStatusWithAcctCode(
					acctCode, Integer.valueOf(status));
			
			String sign = "";
			if (isSuccess) {
				sign = "账户状态更新成功!";
			} else {
				sign = "账户状态更新失败成功,请与管理员联系...";

			}
			result.put("sign", sign);
//			result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
//			result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			return JSonUtil.toJSonString(result);
			
		} catch (Exception e) {
			logger.error("update  AcctStatus error:", e);
			result.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(result);

	}

}
