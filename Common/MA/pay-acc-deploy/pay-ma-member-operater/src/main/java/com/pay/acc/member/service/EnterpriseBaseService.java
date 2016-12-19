package com.pay.acc.member.service;

import java.util.List;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.dto.EnterpriseContactDto;
import com.pay.acc.member.dto.EnterpriseContractDto;
import com.pay.acc.member.dto.OperatorDto;
import com.pay.acc.member.model.EnterpriseBase;
import com.pay.acc.member.model.WithdrawUnionBatchpayFee;
import com.pay.acc.member.model.RefundFeeConf;

public interface EnterpriseBaseService {

	/**
	 * 
	 * @param enterpriseBase
	 */
	public void insertEnterpriseBase(EnterpriseBaseDto enterpriseBase);

	public void insertEnterpriseContact(EnterpriseContactDto enterpriseContact);

	public void insertEnterpriseContract(
			EnterpriseContractDto enterpriseContract);

	public void insertOperator(OperatorDto operator);

	/**
	 * 
	 * @param memberCode
	 * @return
	 */
	public EnterpriseBaseDto queryEnterpriseBaseByMemberCode(Long memberCode);

	/**
	 * 
	 * @param memberCode
	 * @return
	 */
	public Long queryEnterpriseRiskLeveCode(Long memberCode);

	/**
	 * 获取风控MCC
	 * 
	 * @param memberCode
	 * @return
	 */
	public Long queryEnterpriseMccCode(Long memberCode);

	/**
	 * 
	 * @param merchantTemp
	 * @return
	 */
	public List<EnterpriseBaseDto> getCurrMaxMerchantCode(String merchantTemp);
	
	public List<EnterpriseBase> getByZhName(String zhName);
	
	/**
	 * 获取商户提现｜批量出款配置的手续费
	 * @param memberCode
	 * @return
	 */
	WithdrawUnionBatchpayFee queryWithdrawUnionBatchByCode(Long memberCode) ;
	
	/*
	 * 2016-05-07
	 */
	public RefundFeeConf queryRefundFeeConfByCode(Long memberCode);
}
