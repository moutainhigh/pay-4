/**
 *  File: ElecBillsPay2BankSingleHandler.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-6   Sany        Create
 *
 */
package com.pay.elecbills;

import groovy.lang.GroovyClassLoader;
import groovy.lang.GroovyObject;

import java.io.File;
import java.util.Map;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fo.elecbills.dto.ElecBillsDto;
import com.pay.fo.elecbills.impl.BaseElecBillsService;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.tradequery.service.PayDetailService;
import com.pay.util.StringUtil;

/**
 * 批量付款到银行
 */
public class Pay2BankBatchHandler extends BaseElecBillsService{
	
	private PayDetailService payDetailService;
	
	private MemberQueryFacadeService memberQueryFacadeService;
	
	@Override
	public ElecBillsDto getViewData(Map<String, Object> map){
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		ElecBillsDto elecBillsDto = new ElecBillsDto();
		map.put("memberCode", memberCode);
		map.put("sequenceId", map.get("serialNo"));
		MemberInfo memberInfo = memberQueryFacadeService.getMemberInfo(loginSession.getLoginName());
		Map<String, Object> resultMap = payDetailService.queryPayToBankCertificate(map);
		String fundoutBankcode = String.valueOf(resultMap.get("fundoutBankcode"));
		ClassLoader parent = getClass().getClassLoader();
		GroovyClassLoader loader = new GroovyClassLoader(parent);
		Class<?> gclass;
		GroovyObject groovyObject = null;
		try {
			gclass = loader.parseClass(new File("/opt/pay/config/poss/BankInfo.groovy"));
			groovyObject = (GroovyObject) gclass.newInstance();
		} catch (Exception e) {
			log.error("解析groovy出错",e);
		}
		String bankNo = (String) groovyObject.invokeMethod("getBankNo", fundoutBankcode);
		String bankAddress = (String) groovyObject.invokeMethod("getBankAddress", fundoutBankcode);
		
		elecBillsDto.setBatchNo(StringUtil.null2String(map.get("batchNo")));
		elecBillsDto.setTradeNo(StringUtil.null2String(resultMap.get("sequenceId")));
		elecBillsDto.setPayerName(memberInfo.getMemberName());
		elecBillsDto.setPayeeName(StringUtil.null2String(resultMap.get("payeeName")));
		elecBillsDto.setPayerLoginName(loginSession.getLoginName());
		elecBillsDto.setPayeeBankNo(StringUtil.null2String(resultMap.get("payeeLoginName")));//收款方银行账号命名payeeLoginName
		elecBillsDto.setAgreementPayName(getAgreementPayName());//协议代付方用户名
		elecBillsDto.setBankBranch(StringUtil.null2String(resultMap.get("bankBranch"))); //收款方开户行
		
		elecBillsDto.setAgreementPayBankName(bankAddress);//协议代付方开户行
		elecBillsDto.setAgreementPayBankNo(bankNo);//协议代付方账号
		elecBillsDto.setAmount(StringUtil.null2String(resultMap.get("amount")));
		elecBillsDto.setFee(StringUtil.null2String(resultMap.get("fee")));
		elecBillsDto.setRealAmount(amountAdd(resultMap.get("amount")+"", resultMap.get("fee")+""));
		elecBillsDto.setTradeType(getTradeType());
		elecBillsDto.setCreateTime(formatCreteTime(StringUtil.null2String(resultMap.get("createTime"))));
		elecBillsDto.setRemark(StringUtil.null2String(resultMap.get("remark")));
		return elecBillsDto;
	}

	/**
	 * @param payDetailService the payDetailService to set
	 */
	public void setPayDetailService(PayDetailService payDetailService) {
		this.payDetailService = payDetailService;
	}
	
	/**
	 * @param memberQueryFacadeService the memberQueryFacadeService to set
	 */
	public void setMemberQueryFacadeService(
			MemberQueryFacadeService memberQueryFacadeService) {
		this.memberQueryFacadeService = memberQueryFacadeService;
	}
	
}
