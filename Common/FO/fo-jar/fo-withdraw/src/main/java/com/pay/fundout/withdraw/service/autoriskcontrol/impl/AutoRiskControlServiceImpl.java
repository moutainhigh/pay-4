package com.pay.fundout.withdraw.service.autoriskcontrol.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.service.MemberOperateService;
import com.pay.fundout.withdraw.dao.autoriskcontrol.AutoRiskControlDAO;
import com.pay.fundout.withdraw.service.autoriskcontrol.AutoRiskControlService;
import com.pay.poss.dto.fi.DepositQueryParamDTO;

public class AutoRiskControlServiceImpl implements AutoRiskControlService {
	
	private final static transient Log log = LogFactory.getLog(AutoRiskControlServiceImpl.class);
	
	private AutoRiskControlDAO autoRiskControlDAO;
	
	//private DepositOrderService depositOrderService;
	
	private MemberOperateService memberOperateService;
	
	private final static Properties properties  = new Properties() ;
	
	public void setAutoRiskControlDAO(AutoRiskControlDAO autoRiskControlDAO) {
		this.autoRiskControlDAO = autoRiskControlDAO;
	}
	
//	public void setDepositOrderService(DepositOrderService depositOrderService) {
//		this.depositOrderService = depositOrderService;
//	}

	public void setMemberOperateService(MemberOperateService memberOperateService) {
		this.memberOperateService = memberOperateService;
	}

	static{
		FileInputStream fileInputStream = null;
		try{
			StringBuilder stringBuilder = new StringBuilder();
			String separator = File.separator;
			stringBuilder.append(separator).append("opt").append(separator).append("pay").append(separator).append("config").append(separator)
			.append("fo").append(separator).append("autoriskcontrol.properties");
			fileInputStream = new FileInputStream(new File(stringBuilder.toString()));
			properties.load(fileInputStream);
		}catch (Exception e) {
			log.error("-----------------加载properties失败---------------"+e.getMessage());
		}finally{
			if(fileInputStream != null){
				try {
					fileInputStream.close();
				} catch (IOException e) {
					fileInputStream = null;
				}
			}
		}
	}

	@Override
	public String validateFundoutAmount(Long payerMemberCode) {
		String timeIntenal = properties.getProperty("fundout.aoturiskcontrol.timeinterval");
		String fundoutamount = properties.getProperty("fundout.aoturiskcontrol.fundoutamount");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("timeintenal", Integer.valueOf(timeIntenal)/24);
		params.put("fundoutamount", Integer.valueOf(fundoutamount));
		params.put("payermembercode", payerMemberCode);
		List list = this.autoRiskControlDAO.findFundoutAmounts(params);
		if(list != null && list.size() > 0){
			return "24小时内企业会员账户出款到银行或体现累计超过200万";
		}
		return null;
	}

	@Override
	public String validatePayeeBankAcc(Long payerMemberCode, String payeeBankAccCode) {
		String timeIntenal = properties.getProperty("fundout.aoturiskcontrol.timeinterval");
		String payeebankacc = properties.getProperty("fundout.aoturiskcontrol.payeebankacc");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("timeintenal", Integer.valueOf(timeIntenal)/24);
		params.put("payeebankacc", Integer.valueOf(payeebankacc));
		params.put("payermembercode", payerMemberCode);
		params.put("payeebankacccode", payeeBankAccCode);
		List list = this.autoRiskControlDAO.findPayeeBankAccs(params);
		if(list != null && list.size() > 0){
			return "企业24小时内向同一银行账户出款超过5笔";
		}
		return null;
	}

	@Override
	public String validateReceivedPersonAccTransferAmount(Long payerMemberCode) {
		String timeIntenal = properties.getProperty("fundout.aoturiskcontrol.timeinterval");
		String transfertimes = properties.getProperty("fundout.aoturiskcontrol.transfertimes");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("timeintenal", Integer.valueOf(timeIntenal)/24);
		params.put("transfertimes", Integer.valueOf(transfertimes));
		params.put("payermembercode", payerMemberCode);
		List list = this.autoRiskControlDAO.findReceivedPersonAccTransferAmounts(params);
		if(list != null && list.size() > 0){
			return "同一企业账户24小时内收到超过10次个人账户的转账";
		}
		return null;
	}

	@Override
	public String validateReceivedPersonAccTransferTimes(Long payerMemberCode) {
		String timeIntenal = properties.getProperty("fundout.aoturiskcontrol.timeinterval");
		String transferamount = properties.getProperty("fundout.aoturiskcontrol.transferamount");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("timeintenal", Integer.valueOf(timeIntenal)/24);
		params.put("transferamount", Long.valueOf(transferamount));
		params.put("payermembercode", payerMemberCode);
		List list = this.autoRiskControlDAO.findReceivedPersonAccTransferTimes(params);
		if(list != null && list.size() > 0){
			return "同一企业账户24小时内收到个人账户的转账超过20万";
		}
		return null;
	}
	
	@Override
	public String validateDepositNum(Long payerMemberCode) {
		String timeIntenal = properties.getProperty("fundout.aoturiskcontrol.timeinterval");
		String depositnum = properties.getProperty("fundout.aoturiskcontrol.depositnum");
		
		DepositQueryParamDTO dto = new DepositQueryParamDTO();
		Date date = new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date endDate = cal.getTime();
		cal.add(Calendar.DAY_OF_YEAR, -(Integer.parseInt(timeIntenal)/24));
		Date beginDate = cal.getTime();
		dto.setDepositProtocolStartDate(beginDate);
		dto.setDepositProtocolEndDate(endDate);
		//dto.setLimitNum(Integer.parseInt(depositnum));
		dto.setMemberCode(payerMemberCode);
//		if(depositOrderService.isDepositNumLimit(dto)){
//			return "系统交易类监控-充值类预警-24小时内企业会员账户充值次数超10次";
//		}
		return null;
	}
	
	@Override
	public String validateDepositAmount(Long payerMemberCode) {
		String timeIntenal = properties.getProperty("fundout.aoturiskcontrol.timeinterval");
		String depositamount = properties.getProperty("fundout.aoturiskcontrol.depositamount");
		
		DepositQueryParamDTO dto = new DepositQueryParamDTO();
		Date date = new Date();
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		Date endDate = cal.getTime();
		cal.add(Calendar.DAY_OF_YEAR, -(Integer.parseInt(timeIntenal)/24));
		Date beginDate = cal.getTime();
		dto.setDepositProtocolStartDate(beginDate);
		dto.setDepositProtocolEndDate(endDate);
//		dto.setLimitAmount(Long.valueOf(depositamount));
		dto.setMemberCode(payerMemberCode);
//		if(depositOrderService.isDepositAmountLimit(dto)){
//			return "系统交易类监控-充值类预警-24小时内会员账户单笔充值金额达50000元";
//		}
		return null;
	}
	
	@Override
	public String validateAccountSecurity(Long payerMemberCode) {
		if(memberOperateService.isErrLoginWarning(payerMemberCode)){
			return "账户安全类-同一账户出款前半小时累计登录错误三次";
		}
		return null;
	}
	
}
