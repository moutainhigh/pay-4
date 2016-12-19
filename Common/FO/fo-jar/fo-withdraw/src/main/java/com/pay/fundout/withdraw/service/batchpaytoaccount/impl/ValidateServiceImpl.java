/**
 *  File: ValidateAccountServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-3     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.batchpaytoaccount.impl;

import java.text.DecimalFormat;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportFileDTO;
import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportRecordDTO;
import com.pay.fundout.withdraw.service.batchpaytoaccount.MasspayBatchOrderService;
import com.pay.fundout.withdraw.service.batchpaytoaccount.ValidateService;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.service.ma.batchpaytoaccount.MassPayService;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;
import com.pay.util.StringUtil;

/**
 * @author darv
 * 
 */
public class ValidateServiceImpl implements ValidateService {
	private FoRcLimitFacade foRcLimitFacade;

	/**
	 * @param foRcLimitFacade the foRcLimitFacade to set
	 */
	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}

	private MassPayService massPayService;


	private MasspayBatchOrderService masspayBatchOrderService;


	private DecimalFormat format = new DecimalFormat("0.00");

	public void setMasspayBatchOrderService(MasspayBatchOrderService masspayBatchOrderService) {
		this.masspayBatchOrderService = masspayBatchOrderService;
	}


	public void setMassPayService(MassPayService massPayService) {
		this.massPayService = massPayService;
	}

	@Override
	public String validatePayerAccount(Long memberCode, Integer accountType, Long applyMoney, MasspayImportFileDTO importFile,
			boolean validateRMRule) {
		String errorInfo = "";
		try {
			MemberInfoDto memberInfo = this.massPayService.getMemberInfo(null, memberCode, null, null);
			Integer status = memberInfo.getStatus();
			String errorMsg = "";
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
			if(!StringUtil.isNull(errorMsg)){
				return errorMsg;
			}
			
			AcctDto acctDto = this.massPayService.getAcctInfo(memberCode, accountType);
			if (!Integer.valueOf(1).equals(acctDto.getStatus())) {// 您的账户无效或已冻结
				return "accountIsInvalid";
			}
			AcctAttribDto acctAttribDto = this.massPayService.getAcctAttrInfo(memberCode, accountType);
			if (!Integer.valueOf(2).equals(acctAttribDto.getAcctType())) {// 您的账户不是企业会员类型
				return "notFirmType";
			}
			BalancesDto balancesDto = this.massPayService.getBalancesInfo(memberCode, accountType);
			if (Integer.valueOf(0).equals(acctAttribDto.getFrozen())) {// 您的账户已冻结
				errorInfo = "payerIsFrozen";
			} else if (Integer.valueOf(0).equals(acctAttribDto.getAllowOut())) {// 您的账户止出
				errorInfo = "payerNotAllowOut";
			} else if (Integer.valueOf(0).equals(acctAttribDto.getPayAble())) {// 您的账户不可进行付款
				errorInfo = "payerNotPay";
			} else if (Integer.valueOf(0).equals(acctAttribDto.getAllowTransferOut())) {// 您的账户不可以转出
				errorInfo = "payerNotTranOut";
			} else if (balancesDto.getBalance().longValue() < applyMoney.longValue()) {// 您的账户余额不足
				errorInfo = "moneyNotEnough";
			} else {
				if(validateRMRule){
					errorInfo = this.riskControlInfo(memberCode, applyMoney, importFile);
				}
			}
			if (errorInfo.equals("")) {
				importFile.setPayerAcctCode(acctAttribDto.getAcctCode());
			}
		} catch (Exception e) {
			LogUtil.error(getClass(), e.getMessage(), OPSTATUS.EXCEPTION, memberCode.toString(), "", e.toString(), "", "");
			errorInfo = "payerAccountInvalid"; // 您的账户状态有误
		}
		return errorInfo;
	}

	private String riskControlInfo(Long memberCode, Long applyMoney, MasspayImportFileDTO importFile)
			throws Exception {
		String info = "";
		RCLimitResultDTO rcLimitResultDTO  =  getRCLimitResult(RCLIMITCODE.FO_PAY_ENTERPRISE_ACC2P.getKey(), memberCode);
		if(rcLimitResultDTO == null) throw new Exception("未配置风控规则");

		// 复核时,从订单表中统计已付总金额
		Long monthTotalAmount = masspayBatchOrderService.getMonthTotalAmount(memberCode);
		Integer monthTotalCount = masspayBatchOrderService.getMonthTotalCount(memberCode);
		Long dayTotalAmount = masspayBatchOrderService.getDayTotalAmount(memberCode);
		Integer dayTotalCount = masspayBatchOrderService.getDayTotalCount(memberCode);
		int allowTimes = 0;
		long allowAmount = 0L;
		
		int monthLimitTimes = rcLimitResultDTO.getMonthTimes() - monthTotalCount.intValue();
		int dayLimitTimes = rcLimitResultDTO.getDayTimes() - dayTotalCount.intValue();
		
		if(monthLimitTimes < dayLimitTimes){
			if (rcLimitResultDTO.getMonthTimes() < monthTotalCount + importFile.getPayTotalCount()) {
				allowTimes = monthLimitTimes<0?0:monthLimitTimes;
				info = "payerMonthCount-" +rcLimitResultDTO.getMonthTimes() + "," + (allowTimes);
			}
		}else{
			if (rcLimitResultDTO.getDayTimes() < dayTotalCount + importFile.getPayTotalCount()) {
				allowTimes = dayLimitTimes<0?0:dayLimitTimes;
				info = "payerDayCount-" + rcLimitResultDTO.getDayTimes() + "," + allowTimes;
			}
		}
		
		if(StringUtil.isNull(info)){
			long monthLimitAmount = rcLimitResultDTO.getMonthLimit()-monthTotalAmount.longValue();
			long dayLimitAmount = rcLimitResultDTO.getDayLimit()-dayTotalAmount.longValue();
			
			if(monthLimitAmount < dayLimitAmount){
				
				if (rcLimitResultDTO.getMonthLimit() < monthTotalAmount + importFile.getPayTotalAmount()) {// 支付金额超出了本月金额限制
					allowAmount=monthLimitAmount<0?0:monthLimitAmount;
					info = "payerMonthAmount-" + format.format(rcLimitResultDTO.getMonthLimit() / 1000.0) + "," + format.format(allowAmount / 1000.0);
				}
			}else{
				
				 if (rcLimitResultDTO.getDayLimit() < dayTotalAmount + importFile.getPayTotalAmount()) {// 支付金额超出了今天金额限制
					 allowAmount=dayLimitAmount<0?0:dayLimitAmount;
					 info = "payerDayAmount-" + format.format(rcLimitResultDTO.getDayLimit() / 1000.0) + "," + format.format(allowAmount / 1000.0);
				 }
			}
		}

		return info;
	}

	@Override
	public String validatePayeeAccount(MasspayImportRecordDTO importRecord,long singleLimit) {
		String errorInfo = "N";
		try {
			MemberInfoDto memberInfo = this.massPayService.getMemberInfo(importRecord.getPayeeCode(), null, null, null);
			
			if(memberInfo==null){
				return "收款方用户名尚未注册";
			}
			//不能给自己付款
			if (memberInfo.equals(String.valueOf(memberInfo
					.getMemberCode()))) {
				return "收款方用户和付款方用户名不能相同";
			}
			String errorMsg = "";
			if(memberInfo.getStatus()!=MemberStatusEnum.NORMAL.getCode()){
				String msg = "";
				if(memberInfo.getStatus()==MemberStatusEnum.DELETE.getCode()){
					msg = MemberStatusEnum.DELETE.getMessage();
				}else if(memberInfo.getStatus()==MemberStatusEnum.FROZEEN.getCode()){
					msg = MemberStatusEnum.FROZEEN.getMessage();
				}else if(memberInfo.getStatus()==MemberStatusEnum.NO_ACTIVE.getCode()){
					msg = MemberStatusEnum.NO_ACTIVE.getMessage();
				}
				if(!StringUtil.isNull(msg)){
					errorMsg ="收款方会员处于"+msg+"状态,不能收款";
				}else{
					errorMsg="收款方会员暂不能收款";
				}
			}
			if(!StringUtil.isNull(errorMsg)){
				return errorMsg;
			}
			
			//importRecord.setPayeeAcctType(Integer.valueOf(memberInfo.getAcctType()));
			importRecord.setPayeeMember(memberInfo.getMemberCode());
			AcctAttribDto accAttrInfo = this.massPayService.getAcctAttrInfo(memberInfo.getMemberCode(), null);
			importRecord.setPayeeAcctCode(accAttrInfo.getAcctCode());

			if (Integer.valueOf(0).equals(accAttrInfo.getFrozen())) {// 收款账户已冻结
				errorInfo = "payeeIsFrozen";
			} else if (Integer.valueOf(0).equals(accAttrInfo.getAllowIn())) {// 收款账户为止入
				errorInfo = "payeeNotAllowIn";
			} else if (Integer.valueOf(0).equals(accAttrInfo.getAllowTransferIn())) {// 收款账户不允许转入
				errorInfo = "payeeNotTranIn";
			} else {
				if (singleLimit < importRecord.getAmount()) {// 超出了单笔支付限额
					errorInfo = "payerOneAmount-" + format.format(singleLimit / 1000.0);
				}
			}
		} catch (Exception e) {
			errorInfo = "payeeAccountInvalid"; // 收款账户状态有误
			LogUtil.error(getClass(), e.getMessage(), OPSTATUS.EXCEPTION, importRecord.getPayeeCode(), "", e.toString(), "", "");
		}
		return errorInfo;
	}


	@Override
	public RCLimitResultDTO getRCLimitResult(int busiType, Long memberCode) {
		return foRcLimitFacade.getBusinessRcLimit(busiType,null, memberCode);
	}
}
