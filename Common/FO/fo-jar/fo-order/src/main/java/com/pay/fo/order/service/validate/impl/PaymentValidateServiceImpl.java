/**
 * 
 */
package com.pay.fo.order.service.validate.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.account.dto.VerifyResultDto;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.fundout.service.inf.CardBinFacadeService;
import com.pay.fundout.service.ma.AccountInfo;
import com.pay.fundout.service.ma.AccountQueryFacadeService;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.poss.service.ma.payment.PaymentPasswordFacacdeService;
import com.pay.util.IDContentUtil;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class PaymentValidateServiceImpl implements PaymentValidateService {

	/**
	 * 密码验证服务类
	 */
	private PaymentPasswordFacacdeService paymentPasswordFacacdeService;
	/**
	 * 会员查询服务类
	 */
	private MemberQueryFacadeService memberQueryFacadeService;
	/**
	 * 账户查询服务类
	 */
	private AccountQueryFacadeService accountQueryFacadeService;
	/**
	 * 卡Bin服务类
	 */
	private CardBinFacadeService cardBinFacadeService;
	/**
	 * 配置银行服务类
	 */
	private ConfigBankService configBankService;
	

	@Override
	public String validatePaymentPassword(long memberCode,
			int accountType, String pwd) {
		return validatePaymentPassword(memberCode, accountType, null, pwd);
	}



	@Override
	public String validatePaymentPassword(long memberCode, int accountType,
			Long op, String pwd) {
		
		MaResultDto maResult = null;
		if(StringUtil.isNull(op)){
			maResult = paymentPasswordFacacdeService.validatePaymentPwd(memberCode, accountType, pwd);
		}else{
			maResult = paymentPasswordFacacdeService.validatePaymentPwd(memberCode, accountType, pwd, op);
		}
		String errorMsg1 =  "支付密码不正确，您还有num次机会";
		String errorMsg2 =  "您的帐户已止出，请联系客服";
		String errorMsg3 =  "账户异常，请联系客服";
		String errorMsg8 = "支付密码不正确";
		if(null==maResult){
			return errorMsg1.replaceAll("num", "3");
		}
		int status = maResult.getResultStatus();
		VerifyResultDto verifyResult = (VerifyResultDto)maResult.getObject();
		if(status==1){
			return null;
		}else if(status==3){
			return errorMsg2.replaceAll("mm", String.valueOf(verifyResult.getLeavingMinute()));
		}else if(status==2){
			return errorMsg1.replaceAll("num", String.valueOf(verifyResult.getLeavingTime()));
		}else if(status==8){
			return errorMsg8;
		}else{
			return errorMsg3;
		}
	}
	
	
	@Override
	public String validatePayeeMemberInfo(MemberInfo payee,
			String payerMemberCode)  {
		return validatePayeeMemberInfo(payee,null, payerMemberCode);
	}






	@Override
	public String validatePayeeMemberInfo(String loginName,
			String payerMemberCode)  {
		return validatePayeeMemberInfo(loginName, null, payerMemberCode);
	}



	@Override
	public String validatePayeeAcctInfo(long memberCode, int acctType)
			 {
		String errorMsg = null;
		AccountInfo  payeeAcct = accountQueryFacadeService.getAccountInfo(memberCode,acctType);
		if(payeeAcct==null){
			errorMsg = "收款方账户不存在";
		}else if(payeeAcct.getFrozen()==0){
			errorMsg = "收款方账户已冻结";
		}else if(payeeAcct.getAllowIn()==0||payeeAcct.getAllowTransferIn()==0){
			errorMsg = "收款方账户止入";
		}
		return errorMsg;
	}



	@Override
	public String validatePayerMemberInfo(long memberCode)  {
		MemberInfo payer = memberQueryFacadeService.getMemberInfo(memberCode);
		return validatePayerMemberInfo(payer);
	}






	@Override
	public String validatePayerMemberInfo(MemberInfo payer)  {
		String errorMsg = "";
		int status = payer.getStatus();
		if(status!=MemberStatusEnum.NORMAL.getCode()){
			String msg = "";
			if(status ==MemberStatusEnum.DELETE.getCode()){
				msg = MemberStatusEnum.DELETE.getMessage();
			}else if(status==MemberStatusEnum.FROZEEN.getCode()){
				msg = MemberStatusEnum.FROZEEN.getMessage();
			}else if(status==MemberStatusEnum.NO_ACTIVE.getCode()){
				msg = MemberStatusEnum.NO_ACTIVE.getMessage();
			}
			if(!StringUtil.isNull(msg)){
				errorMsg ="您的会员账户处于"+msg+"状态,暂不能付款。如有疑问，请联系客服";
				
			}else{
				errorMsg="您的会员账户状态异常，暂不能付款。如有疑问，请联系客服";
			}
		}
		return errorMsg;
	}



	@Override
	public String validatePayerAcctInfo(long memberCode, int acctType) {
		String errorMsg = null;
		AccountInfo  payerAcct = accountQueryFacadeService.getAccountInfo(memberCode,acctType);
		if(payerAcct.getFrozen()==0){
			errorMsg = "您的账户已被冻结，暂不能付款。如有疑问，请联系客服";
		}else if(payerAcct.getAllowOut()==0||payerAcct.getAllowTransferOut()==0){
			errorMsg = "您的账户暂不能付款。如有疑问，请联系客服";
		}
		return errorMsg;
	}


	@Override
	public String validatePayeeBankAcctInfo(String bankName,String bankAcctCode, String bankCode,Integer busiType,Integer fundoutMode){
		String errorMsg = null;
		Map<String,Object> map = new HashMap<String,Object>();
		//目的银行编号
		map.put("targetBankId", bankCode);
		//出款方式
		map.put("foMode", Integer.valueOf(fundoutMode));
		//出款业务
		map.put("fobusiness", busiType);
		String fundoutBank = configBankService.queryFundOutBank2Withdraw(map);
		if(StringUtil.isNull(fundoutBank)){
			errorMsg="暂不支持"+bankName+"出款";
		}
		if(StringUtil.isNull(errorMsg)&&!cardBinFacadeService.validateBankAcctCode(bankAcctCode, bankCode)){
			errorMsg = "当前业务暂不支持信用卡账号";
		}
		return errorMsg;
	}

	
	public void setPaymentPasswordFacacdeService(
			PaymentPasswordFacacdeService paymentPasswordFacacdeService) {
		this.paymentPasswordFacacdeService = paymentPasswordFacacdeService;
	}



	/**
	 * @param memberQueryFacadeService the memberQueryFacadeService to set
	 */
	public void setMemberQueryFacadeService(
			MemberQueryFacadeService memberQueryFacadeService) {
		this.memberQueryFacadeService = memberQueryFacadeService;
	}



	/**
	 * @param accountQueryFacadeService the accountQueryFacadeService to set
	 */
	public void setAccountQueryFacadeService(
			AccountQueryFacadeService accountQueryFacadeService) {
		this.accountQueryFacadeService = accountQueryFacadeService;
	}



	/**
	 * @param cardBinFacadeService the cardBinFacadeService to set
	 */
	public void setCardBinFacadeService(CardBinFacadeService cardBinFacadeService) {
		this.cardBinFacadeService = cardBinFacadeService;
	}



	/**
	 * @param configBankService the configBankService to set
	 */
	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}



	@Override
	public String validatePayerBanlance(long paymentAmount, int isPayerPayFee, long fee,
			long memberCode, int acctType) {
		String errorMsg = null;
		long banlance = accountQueryFacadeService.getBalance(memberCode, acctType);
		long realpayAmount = paymentAmount;
		if(isPayerPayFee==1){
			realpayAmount += fee;
		}
		if(banlance-realpayAmount<0){
			errorMsg = "账户余额不足，请充值后再继续此操作";
		}
		return errorMsg;
	}



	@Override
	public boolean isAllowBankFundout(String bankCode, Integer busiType,
			Integer fundoutMode) {
		Map<String,Object> map = new HashMap<String,Object>();
		//目的银行编号
		map.put("targetBankId", bankCode);
		//出款方式
		map.put("foMode", Integer.valueOf(fundoutMode));
		//出款业务
		map.put("fobusiness", busiType);
		String fundoutBank = configBankService.queryFundOutBank2Withdraw(map);
		if(StringUtil.isNull(fundoutBank)){
			return false;
		}
		return true;
	}



	@Override
	public String validatePayeeAcctInfo(String loginName,int acctType) {
		MemberInfo payee = memberQueryFacadeService.getMemberInfo(loginName);
		if(payee==null){
			return "收款方用户名尚未注册";
		}
		return validatePayeeAcctInfo(payee.getMemberCode(), acctType);
	}



	@Override
	public String validatePayeeMemberInfo(String payeeLoginName,
			String payeeName, String payerMemberCode) {
		if (!IDContentUtil.validateEmail(payeeLoginName)
				&& !IDContentUtil.validateMobile(payeeLoginName)) {
			return "收款方用户名格式不正确";
		}
		
		MemberInfo payer = memberQueryFacadeService.getMemberInfo(payeeLoginName);
		return validatePayeeMemberInfo(payer,payeeName, payerMemberCode);
	}



	@Override
	public String validatePayeeMemberInfo(MemberInfo payee, String payeeName,
			String payerMemberCode) {
		if(payee==null){
			return "收款方用户名尚未注册";
		}
		//不能给自己付款
		if (payerMemberCode.equals(String.valueOf(payee
				.getMemberCode()))) {
			return "收款方用户和付款方用户名不能相同";
		}
		if(!StringUtil.isNull(payeeName) && !payee.getMemberName().equalsIgnoreCase(payeeName)){
			return "填写的收款方名称与系统中记录的收款方名称不匹配";
		}
		
		String errorMsg = "";
		if(payee.getStatus()!=MemberStatusEnum.NORMAL.getCode()){
			String msg = "";
			if(payee.getStatus()==MemberStatusEnum.DELETE.getCode()){
				msg = MemberStatusEnum.DELETE.getMessage();
			}else if(payee.getStatus()==MemberStatusEnum.FROZEEN.getCode()){
				msg = MemberStatusEnum.FROZEEN.getMessage();
			}else if(payee.getStatus()==MemberStatusEnum.NO_ACTIVE.getCode()){
				msg = MemberStatusEnum.NO_ACTIVE.getMessage();
			}
			if(!StringUtil.isNull(msg)){
				errorMsg ="收款方会员处于"+msg+"状态,不能收款";
			}else{
				errorMsg="收款方会员暂不能收款";
			}
		}
		
		return errorMsg;
	}
	
	

}
