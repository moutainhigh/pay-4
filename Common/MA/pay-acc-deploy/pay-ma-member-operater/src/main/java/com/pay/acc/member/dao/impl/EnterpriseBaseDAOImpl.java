package com.pay.acc.member.dao.impl;

import java.util.List;

import com.pay.acc.member.dao.EnterpriseBaseDAO;
import com.pay.acc.member.model.EnterpriseBase;
import com.pay.acc.member.model.RefundFeeConf;
import com.pay.acc.member.model.WithdrawUnionBatchpayFee;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class EnterpriseBaseDAOImpl extends BaseDAOImpl implements
		EnterpriseBaseDAO {

	@Override
	public List<EnterpriseBase> getCurrMaxMerchantCode(final String merchantCodeTemp) {
		List<EnterpriseBase> enterpriseBaseList = (List<EnterpriseBase>) super
				.findObjectByCriteria("queryCurrMaxMerchantCode",
						merchantCodeTemp);
		return enterpriseBaseList;
	}
	
	@Override
	public List<EnterpriseBase> getByZhName(final String zhName) {
		List<EnterpriseBase> enterpriseBaseList = (List<EnterpriseBase>)super.findObjectByCriteria("queryByZhName", zhName);
		return enterpriseBaseList;
	}
	
	@Override
	public WithdrawUnionBatchpayFee queryWithdrawUnionBatchByCode(
			final Long memberCode) {
		return (WithdrawUnionBatchpayFee) this.getSqlMapClientTemplate().queryForObject("acc-enterpriseBase.queryWithdrawUnionBatchByCode", memberCode) ;
	}
	
	@Override
	public RefundFeeConf queryRefundFeeConf(final Long memberCode){
		return (RefundFeeConf) this.getSqlMapClientTemplate().queryForObject("acc-enterpriseBase.queryRefundFeeConfByCode", memberCode) ;
	}
}
