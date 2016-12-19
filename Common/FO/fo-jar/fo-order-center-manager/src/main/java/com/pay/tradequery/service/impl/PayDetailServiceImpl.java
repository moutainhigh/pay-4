package com.pay.tradequery.service.impl;

import java.util.Map;

import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.inf.dao.Page;
import com.pay.tradequery.dao.PayDetailDao;
import com.pay.tradequery.service.PayDetailService;

/**
 * @author Sandy
 * @Date 2011-7-22
 * @Description
 * @Copyright Copyright (c) 2004-2013 pay.com. All rights reserved.
 */
public class PayDetailServiceImpl implements PayDetailService {

	private PayDetailDao payDetailDao;
	
	@Override
	public Page<Map<String, Object>> queryBatchPayDetailList(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		return payDetailDao.queryBatchPayDetailList(map, pageNo, pageSize);
	}

	@Override
	public Page<Map<String, Object>> queryPayDetailList(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		return payDetailDao.queryPayDetailList(map, pageNo, pageSize);
	}

	@Override
	public Map<String, Object> querySinglePayDetail(Map<String, Object> map) {
		Map<String,Object> resultMap = null;
		if("2".equals(map.get("channel"))){
			if (map.get("busiType") != null
					&& (Integer.valueOf((String) map.get("busiType")) == PayForEnum.MERCHANT_WITHDRAW_BACK
							.getCode())) {
				resultMap = payDetailDao.queryPay2BankRefundTicket(map);
			}else{
				resultMap = payDetailDao.querySinglePayDetailByBank(map);
			}
		}else if("3".equals(map.get("channel"))) {
			resultMap = payDetailDao.querySinglePayDetailByGateway(map);
		}else{
			resultMap = payDetailDao.querySinglePayDetailByAcc(map);
		}
		return resultMap;
	}
	
	
	
	@Override
	public Map<String, Object> querySinglePayBatchDetail(Map<String, Object> map) {
		return payDetailDao.querySinglePayBatchDetailByAcc(map);
	}

	@Override
	public Page<Map<String, Object>> queryReceiptRecordFormAccList(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		return payDetailDao.queryReceiptRecordFormAccList(map, pageNo, pageSize);
	}

	@Override
	public Page<Map<String, Object>> queryPersonTradeDetailList(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		return payDetailDao.queryPersonTradeDetailList(map, pageNo, pageSize);
	}
	
	@Override
	public Page<Map<String, Object>> queryPersonWithdrawDetailList(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		return payDetailDao.queryPersonWithdrawDetailList(map, pageNo, pageSize);
	}
	
	
	@Override
	public Map<String, Object> queryPersonSingleTradeDetail(
			Map<String, Object> map) {
		String busiType = map.get("busiType")==null?"1":map.get("busiType").toString();
		if ("0".equals(busiType)) {
			return payDetailDao.querySingleTradeForGateway(map);
		}else if("1".equals(busiType)){
			return payDetailDao.querySingleTradeForAcc(map);
		}else if("2".equals(busiType)){
			return payDetailDao.querySingleTradeForBank(map);
		}
		return null;
	}

	@Override
	public Page<Map<String, Object>> queryWithdrawSummaryFromBsp(
			Map<String, Object> map, Integer pageNo, Integer pageSize) {
		return payDetailDao.queryWithdrawSummaryFromBsp(map, pageNo, pageSize);
	}

	@Override
	public Map<String, Object> queryWithdrawDetailFromBsp(
			Map<String, Object> map) {
		return payDetailDao.queryWithdrawDetailFromBsp(map);
	}
	
	@Override
	public Map<String, Object> queryFundAllotDetail(Map<String, Object> map) {
		return payDetailDao.queryFundAllotDetail(map);
	}
	

	@Override
	public Page<Map<String, Object>> queryWithdrawList(Map<String, Object> map,
			Integer pageNo, Integer pageSize) {
		return payDetailDao.queryWithdrawList(map, pageNo, pageSize);
	}

	@Override
	public Map<String, Object> queryWithdrawDetail(Map<String, Object> map) {
		return payDetailDao.queryWithdrawDetail(map);
	}
	
	/** set **/
	public void setPayDetailDao(PayDetailDao payDetailDao) {
		this.payDetailDao = payDetailDao;
	}

	@Override
	public Map<String, Object> queryPayToBankCertificate(Map<String, Object> map) {
		return payDetailDao.queryPayToBankCertificate(map);
	}
}