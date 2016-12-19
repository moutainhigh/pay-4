package com.pay.fo.order.dao.fundoutrefund.impl;

import com.pay.fo.order.dao.fundoutrefund.FundoutRefundOrderQueryDAO;
import com.pay.fo.order.model.bankrefund.BankRefundOrderQueryModel;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 退票查询
 * 
 * @author Sandy
 * @Date 2011-7-27
 * @Description
 * @Copyright Copyright (c) 2004-2013 pay.com. All rights reserved.
 */
@SuppressWarnings("unchecked")
public class FundoutRefundOrderQueryDAOImpl extends
		BaseDAOImpl<BankRefundOrderQueryModel> implements
		FundoutRefundOrderQueryDAO {

	@Override
	public Page<BankRefundOrderQueryModel> queryAllowRefundList(
			Page<BankRefundOrderQueryModel> page, Object query) {
		return super.findByQuery("FO_FUNDOUT_REFUNDORDER.queryAllowRefundList",
				page, query);
	}

	@Override
	public Page<BankRefundOrderQueryModel> queryHasRefundList(
			Page<BankRefundOrderQueryModel> page, Object query) {
		return super.findByQuery("FO_FUNDOUT_REFUNDORDER.queryHasRefundList",
				page, query);
	}

	@Override
	public BankRefundOrderQueryModel queryHasRefundDetail(Object query) {
		return super.findObjectByCriteria(
				"FO_FUNDOUT_REFUNDORDER.queryHasRefundDetail", query);
	}

	@Override
	public BankRefundOrderQueryModel queryRefundDetail(Object query) {
		return super.findObjectByCriteria(
				"FO_FUNDOUT_REFUNDORDER.queryRefundDetail", query);
	}

}
