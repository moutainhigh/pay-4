package com.pay.acc.member.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.member.dao.EnterpriseBaseDAO;
import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.dto.EnterpriseContactDto;
import com.pay.acc.member.dto.EnterpriseContractDto;
import com.pay.acc.member.dto.OperatorDto;
import com.pay.acc.member.model.EnterpriseBase;
import com.pay.acc.member.model.EnterpriseContact;
import com.pay.acc.member.model.EnterpriseContract;
import com.pay.acc.member.model.Operator;
import com.pay.acc.member.model.WithdrawUnionBatchpayFee;
import com.pay.acc.member.model.RefundFeeConf;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.util.BeanConvertUtil;

public class EnterpriseBaseServiceImpl implements EnterpriseBaseService {

	private EnterpriseBaseDAO enterpriseBaseDAO;

	public void setEnterpriseBaseDAO(final EnterpriseBaseDAO enterpriseBaseDAO) {
		this.enterpriseBaseDAO = enterpriseBaseDAO;
	}

	@Override
	public void insertEnterpriseBase(final EnterpriseBaseDto enterpriseBase) {
		// insertEnterpriseBase
		enterpriseBaseDAO.create("insertEnterpriseBase",
				BeanConvertUtil.convert(EnterpriseBase.class, enterpriseBase));
	}

	@Override
	public void insertEnterpriseContact(final EnterpriseContactDto enterpriseContact) {
		enterpriseBaseDAO.create("insertContact", BeanConvertUtil.convert(
				EnterpriseContact.class, enterpriseContact));
	}

	@Override
	public void insertEnterpriseContract(
			final EnterpriseContractDto enterpriseContract) {
		enterpriseBaseDAO.create("insertContract", BeanConvertUtil.convert(
				EnterpriseContract.class, enterpriseContract));
	}

	@Override
	public void insertOperator(final OperatorDto operator) {

		enterpriseBaseDAO.create("insertOperator",
				BeanConvertUtil.convert(Operator.class, operator));
	}

	@Override
	public EnterpriseBaseDto queryEnterpriseBaseByMemberCode(final Long memberCode) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("memberCode", memberCode);
		EnterpriseBase enterpriseBase = null;
		enterpriseBase = (EnterpriseBase) enterpriseBaseDAO
				.findObjectByTemplate("queryEnterpriseBaseByMemberCode",
						paraMap);

		return BeanConvertUtil.convert(EnterpriseBaseDto.class, enterpriseBase);
	}

	@Override
	public Long queryEnterpriseRiskLeveCode(final Long memberCode) {
		Object obj = this.enterpriseBaseDAO.findObjectByCriteria(
				"queryEnterpriseRiskLeveCodeByMemberCode", memberCode);
		Long riskLevelCode = null;
		if (obj != null) {
			riskLevelCode = Long.valueOf(obj.toString());
		}
		return riskLevelCode;
	}
	
	@Override
	public Long queryEnterpriseMccCode(final Long memberCode) {
		Object obj = this.enterpriseBaseDAO.findObjectByCriteria(
				"queryEnterpriseMccCodeByMemberCode", memberCode);
		Long riskLevelCode = null;
		if (obj != null) {
			riskLevelCode = Long.valueOf(obj.toString());
		}
		return riskLevelCode;
	}

	@Override
	public List<EnterpriseBaseDto> getCurrMaxMerchantCode(
			final String merchantCodeTemp) {
		List<EnterpriseBase> enterpriseBaseList = enterpriseBaseDAO
				.getCurrMaxMerchantCode(merchantCodeTemp);
		return (List<EnterpriseBaseDto>) BeanConvertUtil.convert(
				EnterpriseBaseDto.class, enterpriseBaseList);
	}

	@Override
	public List<EnterpriseBase> getByZhName(final String zhName) {
		return enterpriseBaseDAO.getByZhName(zhName);
	}
	@Override
	public WithdrawUnionBatchpayFee queryWithdrawUnionBatchByCode(
			final Long memberCode) {
		return this.enterpriseBaseDAO.queryWithdrawUnionBatchByCode(memberCode) ;
	}
	
	@Override 
	public RefundFeeConf queryRefundFeeConfByCode(final Long memberCode){
		return this.enterpriseBaseDAO.queryRefundFeeConf(memberCode);
	}
}
