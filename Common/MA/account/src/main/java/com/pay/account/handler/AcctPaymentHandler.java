package com.pay.account.handler;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acct.dto.AcctDto;
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
import com.pay.inf.service.IMessageDigest;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.util.JSonUtil;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

/**
 * 账户支付接口
 * 
 * @author terry
 */
public class AcctPaymentHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(AcctPaymentHandler.class);
	private MemberService memberService;
	private AcctService acctService;
	private AcctAttribService acctAttribService;
	private IMessageDigest iMessageDigest;
	private AccountingService acctPaymentAccounting;

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setAcctPaymentAccounting(AccountingService acctPaymentAccounting) {
		this.acctPaymentAccounting = acctPaymentAccounting;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();

		String payer = paraMap.get("payer");
		String acctType = paraMap.get("acctType");
		String password = paraMap.get("password");
		String payee = paraMap.get("payee");
		String amount = paraMap.get("amount");
		String orderId = paraMap.get("orderId");
		
		if (StringUtil.isEmpty(orderId)) {
			result.put("responseCode", "A0001");
			result.put("responseDesc", "订单号不能为空！");
			return JSonUtil.toJSonString(result);
		}

		if (StringUtil.isEmpty(payer) || !NumberUtil.isNumber(payer)) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getErrorCode());
			result.put("responseDesc", "付款方会员号为空或不合法！");
			return JSonUtil.toJSonString(result);
		}

		// 获取用户信息
		MemberDto payerMember = memberService.queryMemberByMemberCode(Long
				.valueOf(payer));

		if (payerMember == null) {
			return buildFailResponse(result,
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR);
		}

		if (payerMember.getStatus() != MemberStatusEnum.NORMAL.getCode()) {
			// 用户不存在
			return buildFailResponse(result,
					ErrorExceptionEnum.MEMBER_INVALID);
		}

		if (StringUtil.isEmpty(acctType) || !NumberUtil.isNumber(acctType)
				|| Integer.valueOf(acctType) != AcctTypeEnum.BASIC_CNY.getCode()) {

			result.put("responseCode", ErrorExceptionEnum.ACCT_TYPE_ERROR.getErrorCode());
			result.put("responseDesc", ErrorExceptionEnum.ACCT_TYPE_ERROR.getMessage());
			return JSonUtil.toJSonString(result);
		}
		
		if(StringUtil.isEmpty(password)){
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_PASSWORD_ERROR.getErrorCode());
			result.put("responseDesc", "密码不能为空！");
			return JSonUtil.toJSonString(result);
		}
		
		String payerMemberAcctCode = payerMember.getMemberCode() + "" + AcctTypeEnum.BASIC_CNY.getCode();
		String payPassword = "";
		AcctAttribDto payerAcctAttribDto = null;
		try {
			payerAcctAttribDto = acctAttribService.queryAcctAttribWithMemberAcctCode(Long.valueOf(payerMemberAcctCode));
			payPassword = iMessageDigest.genMessageDigest(password.getBytes());
		} catch (Exception e) {
			logger.error("generator password error:",e);
		}
		if(payPassword.equalsIgnoreCase(payerAcctAttribDto.getPayPwd())){
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_PASSWORD_ERROR.getErrorCode());
			result.put("responseDesc", "密码不正确！");
			return JSonUtil.toJSonString(result);
		}
		
		//验证付款方是否允许支付
		if(payerAcctAttribDto.getAllowOut()==0){
			result.put("responseCode",
					ErrorExceptionEnum.ACCT_ALLOWIN_ERROR.getErrorCode());
			result.put("responseDesc", ErrorExceptionEnum.ACCT_ALLOWIN_ERROR.getMessage());
			return JSonUtil.toJSonString(result);
		}

		//验证收款方
		if (StringUtil.isEmpty(payee) || !NumberUtil.isNumber(payee)) {
			result.put("responseCode",
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR.getErrorCode());
			result.put("responseDesc", "收款方会员号为空或不合法！");
			return JSonUtil.toJSonString(result);
		}

		// 获取用户信息
		MemberDto payeeMember = memberService.queryMemberByMemberCode(Long
				.valueOf(payee));

		if (payeeMember == null) {
			return buildFailResponse(result,
					ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR);
		}

		if (payeeMember.getStatus() != MemberStatusEnum.NORMAL.getCode()) {
			// 用户不存在
			return buildFailResponse(result,
					ErrorExceptionEnum.MEMBER_INVALID);
		}
		String payeeMemberAcctCode = payeeMember.getMemberCode() + "" + AcctTypeEnum.BASIC_CNY.getCode();
		AcctAttribDto payeeAcctAttribDto = null;
		
		try{
			payeeAcctAttribDto = acctAttribService.queryAcctAttribWithMemberAcctCode(Long.valueOf(payeeMemberAcctCode));
		}catch(Exception e){
			logger.error("payeeAcctAttrib error:",e);
		}
		//验证付款方是否允许支付
		if(payeeAcctAttribDto.getAllowIn()==0){
			result.put("responseCode",
					ErrorExceptionEnum.ACCT_ALLOWIN_ERROR.getErrorCode());
			result.put("responseDesc", ErrorExceptionEnum.ACCT_ALLOWIN_ERROR.getMessage());
			return JSonUtil.toJSonString(result);
		}
		
		if (StringUtil.isEmpty(amount) || !NumberUtil.isNumber(amount)
				|| Long.valueOf(amount) <= 0) {
			result.put("responseCode", "A0002");
			result.put("responseDesc", "金额不合法！");
			return JSonUtil.toJSonString(result);
		}
		
		Long updateAmount = (new BigDecimal(amount).multiply(new BigDecimal(
				"10")).longValue());
		
		try {
			AcctDto acctDto = acctService.queryAcctByAcctCode(payerAcctAttribDto.getAcctCode());
			if(acctDto.getBalance()<updateAmount){
				result.put("responseCode", "A0003");
				result.put("responseDesc", "余额不足！");
				return JSonUtil.toJSonString(result);
			}

			AccountingDto accountingDto = new AccountingDto();
			accountingDto.setAmount(updateAmount);
			accountingDto.setOrderAmount(updateAmount);
			accountingDto.setOrderId(orderId);
			accountingDto.setPayee(Long.valueOf(payee));
			accountingDto.setPayer(Long.valueOf(payer));
			acctPaymentAccounting.doAccounting(accountingDto);
			
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
