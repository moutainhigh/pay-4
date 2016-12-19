package com.pay.fundout.channel.dao.business.impl;

import com.pay.fundout.channel.dao.business.FundoutBusinessDAO;
import com.pay.fundout.channel.model.business.FundoutBusiness;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 
 * @author Herny_zeng
 * 
 */
@SuppressWarnings("unchecked")
public class FundoutBusinessDAOImpl extends BaseDAOImpl<FundoutBusiness>
		implements FundoutBusinessDAO {

	@Override
	public FundoutBusiness getFundoutBusinessById(Long businessId) {
		return (FundoutBusiness) getSqlMapClientTemplate().queryForObject(
				"findById", businessId);
	}

	@Override
	public long addFoBusinessInfo(FundoutBusiness pojo) {
		return (Long) super.create(pojo);
	}

	@Override
	public FundoutBusiness queryFoBusinessInfoById(String bankId) {
		return super.findById(bankId);
	}

	@Override
	public Page<FundoutBusiness> queryFoBusinessInfos(
			Page<FundoutBusiness> page, FundoutBusiness fundoutBusiness) {
		return super.findByQuery("queryBusinessList", page, fundoutBusiness);
	}

	@Override
	public int updateFoBusinessInfo(FundoutBusiness pojo) {
		return super.update(pojo) == true ? 1 : 0;
	}

	@Override
	public FundoutBusiness queryFoBusinessInfoByCode(String code) {
		return super.findObjectByCriteria("findByCode", code);
	}
}