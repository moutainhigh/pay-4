package com.pay.tradequery.dao.impl;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.tradequery.dao.PayDetailDao;

/**
 * @author Sandy
 * @Date 2011-7-22
 * @Description
 * @Copyright Copyright (c) 2004-2013 pay.com. All rights reserved.
 */
@SuppressWarnings("unchecked")
public class PayDetailDaoImpl extends BaseDAOImpl implements PayDetailDao {

	@Override
	public Page<Map<String, Object>> queryBatchPayDetailList(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageSize);
		page.setPageNo(pageNo);
		return super.findByQuery("queryPayBatchDetailList",
				page, map);
	}

	@Override
	public Page<Map<String, Object>> queryPayDetailList(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageSize);
		page.setPageNo(pageNo);
		return super.findByQuery("queryPayDetailList", page,
				map);
	}

	@Override
	public Map<String, Object> querySinglePayDetailByAcc(Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"querySinglePayDeailbyPay2Acc", map);
	}

	@Override
	public Map<String, Object> querySinglePayDetailByBank(
			Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"querySinglePayDeailbyPay2Bank", map);
	}

	@Override
	public Map<String, Object> querySinglePayBatchDetailByAcc(
			Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"querySingleBatchPayDeailbyAcc", map);
	}

	@Override
	public Map<String, Object> querySinglePayBatchDetailByBank(
			Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"querySinglePayBatchDeailbyPay2Bank", map);
	}

	@Override
	public Page<Map<String, Object>> queryReceiptRecordFormAccList(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageSize);
		page.setPageNo(pageNo);
		return super.findByQuery("queryReceiptRecordFormAcc",
				page, map);
	}

	@Override
	public Page<Map<String, Object>> queryPersonTradeDetailList(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageSize);
		page.setPageNo(pageNo);
		return super.findByQuery("queryPersonTradeDetailList", page, map);
	}

	@Override
	public Page<Map<String, Object>> queryPersonWithdrawDetailList(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		return null;// 调用了以前withdrawOrder的service
	}

	@Override
	public Map<String, Object> querySingleTradeForAcc(Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"tradeQueryForAcc", map);
	}

	@Override
	public Map<String, Object> querySingleTradeForBank(Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"tradeQueryForBank", map);
	}

	@Override
	public Map<String, Object> querySingleTradeForGateway(
			Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"tradeQueryForGateway", map);
	}

	@Override
	public Map<String, Object> queryPay2BankRefundTicket(Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"queryPay2BankRefundTicket", map);
	}

	@Override
	public Map<String, Object> querySinglePayDetailByGateway(
			Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"querySinglePayDetailByGateway", map);
	}

	@Override
	public Page<Map<String, Object>> queryWithdrawSummaryFromBsp(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageSize);
		page.setPageNo(pageNo);
		return super.findByQuery("queryWithdrawSummaryFromBsp", page, map);
	}

	@Override
	public Map<String, Object> queryWithdrawDetailFromBsp(
			Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"queryWithdrawDetailFromBsp", map);
	}

	@Override
	public Map<String, Object> queryFundAllotDetail(Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"queryFundAllotDetail", map);
	}

	@Override
	public Page<Map<String, Object>> queryWithdrawList(Map<String, Object> map,
			Integer pageNo, Integer pageSize) {
		Page<Map<String, Object>> page = new Page<Map<String, Object>>(pageSize);
		page.setPageNo(pageNo);
		return super.findByQuery("queryWithdrawList", page,
				map);
	}

	@Override
	public Map<String, Object> queryWithdrawDetail(Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"queryWithdrawDetail", map);
	}

	@Override
	public Map<String, Object> queryPayToBankCertificate(Map<String, Object> map) {
		return (Map<String, Object>) super.findObjectByCriteria(
				"queryPayToBankCertificate", map);
	}

}
