/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.dao.enterprise.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.base.dao.enterprise.LiquidateInfoDAO;
import com.pay.base.model.LiquidateInfo;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * 企业会员结算信息
 * 
 * @author zhi.wang
 * @version $Id: LiquidateInfoDAOImpl.java, v 0.1 2010-10-12 上午10:39:03 zhi.wang
 *          Exp $
 */
public class LiquidateInfoDAOImpl extends BaseDAOImpl implements
		LiquidateInfoDAO {

	/**
	 * @param memberCode
	 * @return
	 * @see com.pay.base.dao.enterprise.LiquidateInfoDAO#getByMemberCode(long)
	 */
	@Override
	public List<LiquidateInfo> getByMemberCode(final long memberCode) {
		return (List<LiquidateInfo>) this.getSqlMapClientTemplate()
				.queryForList(
						getNamespace().concat("getLiquidateInfoByMemberCode"),
						memberCode);
	}

	@Override
	public Long createLiquidateInfo(LiquidateInfo li) {
		return (Long) super.create(li);
	}
	//查看商户一共有几张银行卡
	@Override
	public int getCountByMemberCode(Long memberCode) {
		Integer count = (Integer) getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getCountByMemberCode"), memberCode);
		return count;
	}

	@Override
	public int removeByMemberCodeAndId(String memberCode, Long id) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("liquidateId", id);
		map.put("memberCode", new Long(memberCode));
		return getSqlMapClientTemplate().delete(namespace.concat("removeById"),
				map);
	}

	public LiquidateInfo getById(Long id) {
		return (LiquidateInfo) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getById"), id);
	}

	@Override
	public int updateLiquidateInfo(LiquidateInfo li) {

		return getSqlMapClientTemplate().update(namespace.concat("update"), li);
	}

	@Override
	public int updateStatus(String memberCode, Long liquidateId, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (liquidateId != null && liquidateId > 0) {
			map.put("liquidateId", liquidateId);
		}
		map.put("memberCode", new Long(memberCode));
		map.put("status", status);
		return getSqlMapClientTemplate().update(
				namespace.concat("updateStatus"), map);
	}

	@Override
	public int updateStatus(String memberCode, Integer status) {
		return updateStatus(memberCode, null, status);
	}

	@Override
	public Integer getAccountMode(Long memberCode) {
		return (Integer) getSqlMapClientTemplate().queryForObject(
				namespace.concat("getAccountMode"), memberCode);
	}

}
