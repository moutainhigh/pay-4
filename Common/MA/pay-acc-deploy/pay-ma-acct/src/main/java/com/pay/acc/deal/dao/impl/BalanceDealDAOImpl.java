/**
 * 
 */
package com.pay.acc.deal.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.acc.deal.dao.BalanceDealDAO;
import com.pay.acc.deal.dto.BalanceDealSimpleDto;
import com.pay.acc.deal.model.BalanceDeal;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author Administrator
 * 
 */
public class BalanceDealDAOImpl extends BaseDAOImpl<BalanceDeal> implements
		BalanceDealDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.acc.deal.dao.BalanceDealDAO#
	 * queryBalanceDealSimpleInfoWithSerialNo(java.lang.Long)
	 */
	@Override
	public BalanceDealSimpleDto queryBalanceDealSimpleInfoWithSerialNo(
			Long serialNo, Long amount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serialNo", String.valueOf(serialNo));
		map.put("amount", amount);
		return (BalanceDealSimpleDto) this
				.getSqlMapClientTemplate()
				.queryForObject(
						this.namespace
								.concat("queryBalanceDealSimpleInfoWithSerialNo"),
						map);
	}

	@Override
	public Integer queryDealInfoCounts(String serialNo, Integer dealCode,
			Long amount) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serialNo", serialNo);
		map.put("dealCode", dealCode);
		map.put("amount", amount);
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				this.namespace.concat("queryDealInfoCounts"), map);
	}

	@Override
	public Integer queryDealInfoCountsByVo(Integer dealType, Long voucherNo) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealType", dealType);
		map.put("voucherNo", voucherNo);
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				this.namespace.concat("queryDealInfoCountsByVno"), map);
	}

	@Override
	public Integer updateDealInfoChargeStatus(Long seqId, Integer charupStatus) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("seqId", seqId);
		map.put("charupStatus", charupStatus);
		return this.getSqlMapClientTemplate().update(
				this.namespace.concat("updateDealInfoChargeStatus"), map);

	}

	@Override
	public BalanceDealSimpleDto queryBalanceDealByOrderidAndDealCode(
			String orderId, Integer dealCode, Integer dealType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("dealCode", dealCode);
		map.put("dealType", dealType);
		return (BalanceDealSimpleDto) this.getSqlMapClientTemplate()
				.queryForObject(
						this.namespace.concat("queryDealByOrderidAndDealcode"),
						map);
	}

	@Override
	public BalanceDeal queryBalanceDealForFlushes(String orderId,
			Integer dealCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderId", orderId);
		map.put("dealCode", dealCode);
		return (BalanceDeal) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("queryBalanceDealForFlushes"), map);

	}

	@Override
	public Integer updateDealInfoChargeDealType(Long id, Integer dealType) {
		Map<String, Object> map = new HashMap<String, Object>(2);
		map.put("id", id);
		map.put("dealType", dealType);
		return this.getSqlMapClientTemplate().update(
				namespace.concat("updateDealInfoChargeDealType"), map);
	}

}
