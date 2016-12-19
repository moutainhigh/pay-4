package com.pay.fo.order.service.fundoutrefund.impl;

import com.pay.fo.order.dao.fundoutrefund.FundoutRefundOrderQueryDAO;
import com.pay.fo.order.model.bankrefund.BankRefundOrderQueryModel;
import com.pay.fo.order.service.fundoutrefund.FundoutRefundOrderQueryService;
import com.pay.inf.dao.Page;

/**
 * @author Sandy
 * @Date 2011-7-27
 * @Description
 * @Copyright Copyright (c) 2004-2013 pay.com. All rights reserved.
 */
public class FundoutRefundOrderQueryServiceImpl implements
		FundoutRefundOrderQueryService {
	
	private FundoutRefundOrderQueryDAO fundoutRefundOrderQueryDAO;

	public void setFundoutRefundOrderQueryDAO(
			FundoutRefundOrderQueryDAO fundoutRefundOrderQueryDAO) {
		this.fundoutRefundOrderQueryDAO = fundoutRefundOrderQueryDAO;
	}

	@Override
	public Page<BankRefundOrderQueryModel> queryAllowRefundList(
			Page<BankRefundOrderQueryModel> page, Object query) {
		return fundoutRefundOrderQueryDAO.queryAllowRefundList(page, query);
	}

	@Override
	public Page<BankRefundOrderQueryModel> queryHasRefundList(
			Page<BankRefundOrderQueryModel> page, Object query) {
		return fundoutRefundOrderQueryDAO.queryHasRefundList(page, query);
	}

	@Override
	public BankRefundOrderQueryModel queryHasRefundDetail(Object query) {
		return fundoutRefundOrderQueryDAO.queryHasRefundDetail(query);
	}

	@Override
	public BankRefundOrderQueryModel queryRefundDetail(Object query) {
		return fundoutRefundOrderQueryDAO.queryRefundDetail(query);
	}

}