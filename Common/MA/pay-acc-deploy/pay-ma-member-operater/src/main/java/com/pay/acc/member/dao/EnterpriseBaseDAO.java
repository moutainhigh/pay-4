/*
 * modify history 新增加获取退款配置的
 */
package com.pay.acc.member.dao;

import java.util.List;

import com.pay.acc.member.model.EnterpriseBase;
import com.pay.acc.member.model.WithdrawUnionBatchpayFee;
import com.pay.acc.member.model.RefundFeeConf;
import com.pay.inf.dao.BaseDAO;

public interface EnterpriseBaseDAO extends BaseDAO {

	/**
	 * 
	 * @param merchantTemp
	 * @return
	 */
	public List<EnterpriseBase> getCurrMaxMerchantCode(String merchantTemp);
	
	public List<EnterpriseBase> getByZhName(String zhName);
	
	/**
	 * 获取商户提现｜批量出款配置的手续费
	 * @param memberCode
	 * @return
	 */
	WithdrawUnionBatchpayFee queryWithdrawUnionBatchByCode(Long memberCode) ;
	
	/*
	 * 获取退款配置
	 */
	RefundFeeConf queryRefundFeeConf(Long memberCode);
}
