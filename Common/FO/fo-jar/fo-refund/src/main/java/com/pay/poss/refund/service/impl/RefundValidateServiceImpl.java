/**
 *  <p>File: RefundValidateServiceImpl.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.refund.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.inf.service.BankService;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.refund.common.constant.RefundConstants;
import com.pay.poss.refund.model.MemberInfoDTO;
import com.pay.poss.refund.model.RechargeRecordDto;
import com.pay.poss.refund.model.RefundBanlanceCheck;
import com.pay.poss.refund.model.RefundOrderD;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.model.WebQueryRefundDTO;
import com.pay.poss.refund.service.RefundValidateService;
import com.pay.poss.service.ma.RD4MaServiceApi;

public class RefundValidateServiceImpl implements RefundValidateService {

	private Log logger = LogFactory.getLog(RefundValidateServiceImpl.class);
	private transient RD4MaServiceApi foRD4MaServiceApi;
	private transient BaseDAO baseDao; // 处理数据库操作的基础DAO
	private BankService bankService;

	public void setFoRD4MaServiceApi(RD4MaServiceApi foRD4MaServiceApi) {
		this.foRD4MaServiceApi = foRD4MaServiceApi;
	}

	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.poss.refund.service.RefundValidateService#validateRefundData
	 * (com.pay.poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public String validateRefundData(WebQueryRefundDTO dto) {

		if (null != dto.getAccount()
				&& !StringUtils.isNumeric(dto.getAccount().trim())) {
			return "会员号必须为数字";
		}
		if (null != dto.getRechargeSeq()
				&& !StringUtils.isNumeric(dto.getRechargeSeq().trim())) {
			return "充值流水号必须为数字";
		}
		if (null != dto.getBankSeq()
				&& !StringUtils.isNumeric(dto.getBankSeq().trim())) {
			return "银行流水号必须为数字";
		}

		String loginName = StringUtils.isEmpty(dto.getUserId()) ? null : dto
				.getUserId();
		Long memberCode = StringUtils.isEmpty(dto.getAccount()) ? null
				: StringUtils.isEmpty(dto.getAccount().trim()) ? null
						: new Long(dto.getAccount().trim());

		if (null == memberCode && null == loginName) {
			return "登录名和会员号必须输入1个";
		}
		return null;
	}

	@Override
	public String validateMember(WebQueryRefundDTO dto,
			Page<RechargeRecordDto> page, Map<String, Object> model) {

		String loginName = StringUtils.isEmpty(dto.getUserId()) ? null : dto
				.getUserId();
		Long memberCode = StringUtils.isEmpty(dto.getAccount()) ? null
				: StringUtils.isEmpty(dto.getAccount().trim()) ? null
						: new Long(dto.getAccount().trim());
		Integer acctType = StringUtils.isEmpty(dto.getAccountType()) ? null
				: new Integer(dto.getAccountType());

		// 2.调用ma接口拿app会员信息
		MemberInfoDto maResultDto = new MemberInfoDto();
		try {
			maResultDto = foRD4MaServiceApi.doQueryMemberInfoNsTx(loginName,
					memberCode, null, acctType);
		} catch (PossUntxException e) {
			return "调用查询会员信息接口异常:" + e.getMessage();
		}

		MemberInfoDTO memberInfo = new MemberInfoDTO();
		if (null != maResultDto) {
			memberInfo.setUserId(maResultDto.getLoginName());
			memberInfo.setMemberNo(maResultDto.getMemberCode().toString());
			memberInfo.setUserName(maResultDto.getMemberName());
			memberInfo.setMemberType(maResultDto.getMemberType().toString());
			memberInfo.setAccountType(String.valueOf(acctType));
			memberInfo.setLevelCode(maResultDto.getLevelCode());
		} else {
			return "没有找到相关会员";
		}
		model.put("memberInfo", memberInfo);// 会员信息

		// 3.1调用ma接口拿账户余额
		Long balance = 0L;
		try {
			BalancesDto balancesDto = foRD4MaServiceApi.doQueryBalancesNsTx(
					maResultDto.getMemberCode(), 10);// 账户类型暂定10:人名币账户
			balance = balancesDto.getBalance();
		} catch (PossUntxException e) {
			logger.error("调用查询余额接口异常:" ,e);
			return "调用查询余额接口异常:" + e.getMessage();
		}
		model.put("balance", balance);// 余额

		// 3.2调用ma接口拿账户号
		String acctCode = "";
		try {
			AcctAttribDto acctAttribDto = foRD4MaServiceApi
					.doQueryAcctAttribNsTx(maResultDto.getMemberCode(), acctType);// 账户类型暂定10:人名币账户
			acctCode = acctAttribDto.getAcctCode();
		} catch (PossUntxException e) {
			e.printStackTrace();
			return "调用查询余额接口异常:" + e.getMessage();
		}
		model.put("acctCode", acctCode);// 余额

		return null;
	}

	@Override
	public String validateBankCode(RefundOrderM mDto) {
		String code = "";
		for (RefundOrderD dDto : mDto.getListDetails()) {
			code = "'R_" + dDto.getRechargeBank() + "'";
			
			String chargeBank = dDto.getRechargeBank();
			logger.error("不支持到机构" + chargeBank + "的充退");
			logger.error("fo.code_mapping中没有" + code + "的配置");
			
			List<String> parsers = baseDao.findByQuery(
					"bfrefund.queryGeneratorsByCode", code);
			if (null == parsers || parsers.isEmpty() || parsers.size() == 0) {
				return "暂不支持机构【" + chargeBank + "】的充退";
			}
		}
		return null;
	}

	@Override
	public String setMemberInfo(RefundOrderM mDto) {
		Long memberCode = StringUtils.isEmpty(mDto.getMemberCode()) ? null
				: new Long(mDto.getMemberCode().trim());
		Integer acctType = StringUtils.isEmpty(String.valueOf(mDto
				.getMemberAccType())) ? null : mDto.getMemberAccType();

		// 2.调用ma接口拿app会员信息
		MemberInfoDto maResultDto = new MemberInfoDto();
		try {
			maResultDto = foRD4MaServiceApi.doQueryMemberInfoNsTx(null,
					memberCode, null, acctType);
		} catch (PossUntxException e) {
			return "调用查询会员信息接口异常:" + e.getMessage();
		}

		if (null != maResultDto) {
			mDto.setMemberCode(String.valueOf(maResultDto.getMemberCode()));
			mDto.setMemberName(maResultDto.getMemberName());//
			mDto.setMemberType(maResultDto.getMemberType().toString());//
			mDto.setMemberAccType(acctType);
			mDto.setMemberLevel(maResultDto.getLevelCode());//
		} else {
			return "没有找到相关会员";
		}

		// 3.2调用ma接口拿账户号
		String acctCode = "";
		try {
			AcctAttribDto acctAttribDto = foRD4MaServiceApi
					.doQueryAcctAttribNsTx(maResultDto.getMemberCode(), mDto.getMemberAccType());// 账户类型暂定10:人名币账户
			acctCode = acctAttribDto.getAcctCode();
		} catch (PossUntxException e) {
			return "调用查询余额接口异常:" + e.getMessage();
		}
		mDto.setMemberAcc(acctCode);

		return null;
	}

	@Override
	public boolean validateAmount(RefundOrderM mDto) {
		Long balance = 0L;
		try {
			BalancesDto balancesDto = foRD4MaServiceApi.doQueryBalancesNsTx(
					Long.valueOf(mDto.getMemberCode()), 10);// 账户类型暂定10:人名币账户
			balance = balancesDto.getBalance();
			Long amount = mDto.getApplyAmount().longValue();
			if (amount <= balance) {
				return true;
			}
		} catch (PossUntxException e) {
			logger.error("调用查询余额接口异常,余额校验失败:" + e.getMessage());
			return false;
		}
		return false;
	}

	@Override
	public boolean validateApplyMax(RefundOrderM mDto, RefundOrderD dDto) {
		Long balance = 0L;
		try {
			BalancesDto balancesDto = foRD4MaServiceApi.doQueryBalancesNsTx(
					Long.valueOf(mDto.getMemberCode()), 10);// 账户类型暂定10:人名币账户
			balance = balancesDto.getBalance();
			Long rechargeAmount = dDto.getRechargeAmount().longValue();
			Long allowAmount = 0L;
			// 根据充值流水号查refund_banlance_check表，查已充退金额
			RefundBanlanceCheck queryDto = new RefundBanlanceCheck();
			queryDto.setRechargeSeq(dDto.getRechargeOrderSeq());
			RefundBanlanceCheck balanceDto = (RefundBanlanceCheck) baseDao
					.findObjectByCriteria(
							RefundConstants.QUERY_REFUNDBANLANCECHECK, queryDto);
			if (null != balanceDto && null != balanceDto.getRefundAmount()) {
				allowAmount = (rechargeAmount.longValue()
						- balanceDto.getRefundAmount().longValue() < balance
						.longValue()) ? Long.valueOf(rechargeAmount.longValue()
						- balanceDto.getRefundAmount().longValue()) : balance;
			} else {
				allowAmount = (rechargeAmount.longValue() < balance.longValue()) ? rechargeAmount
						: balance;
			}
			if (dDto.getApplyAmount().longValue() <= allowAmount.longValue()) {
				return true;
			}
		} catch (PossUntxException e) {
			logger.error("可充退金额校验出现异常:" + dDto.getDetailKy() + ":"
					+ e.getMessage());
			return false;
		}
		return false;
	}

}
