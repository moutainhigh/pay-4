package com.pay.txncore.crosspay.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dao.FrozenOrderDAO;
import com.pay.txncore.crosspay.model.FrozenOrder;
import com.pay.txncore.crosspay.model.FrozenOrderCriteria;
import com.pay.txncore.crosspay.service.FrozenOrderService;

public class FrozenOrderServiceImpl implements FrozenOrderService {

	private FrozenOrderDAO frozenOrderDao;

	public void setFrozenOrderDao(FrozenOrderDAO frozenOrderDao) {
		this.frozenOrderDao = frozenOrderDao;
	}

	@Override
	public FrozenOrder findById(Long id) {
		return frozenOrderDao.findById(id);
	}

	@Override
	public List<FrozenOrder> findByCriteria(FrozenOrderCriteria criteria) {
		return frozenOrderDao.findByCriteria(criteria);
	}

	@Override
	public long createFrozenOrder(FrozenOrder frozenOrder) {
		return (Long) frozenOrderDao.create(frozenOrder);
	}

	@Override
	public boolean updateFrozenOrder(FrozenOrder frozenOrder) {
		return frozenOrderDao.update(frozenOrder);
	}

	@Override
	public boolean deleteFrozenOrder(Long id) {
		return frozenOrderDao.delete(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<FrozenOrder> queryFrozenOrderForPage(
			Map<String, Object> frozenOrderCriteria, Page<FrozenOrder> page) {
		// 转换成查询page对象
		List<FrozenOrder> resultList = frozenOrderDao.findByCriteria(
				"selectTradeOrders", frozenOrderCriteria, page);
		// 转换成页面对象
		page.setResult(resultList);
		return page;
	}

	/**
	 * 冻结记录
	 * 
	 * @throws Exception
	 */
	@Override
	public void frozenOrder(Map<String, Object> conMap) throws Exception {
		// 锁定交易
		Object trade = null;
		try {
			trade = frozenOrderDao.findObjectByTemplate("lockTradeOrder",
					conMap);
		} catch (Exception ex) {
			throw new Exception("锁定交易失败!");
		}
		if (trade == null) {
			throw new Exception("锁定交易失败!");
		}
		// 更新交易状态
		boolean frozenFlag = frozenOrderDao.updateFrozenOrder("frozenTrade",
				conMap);
		if (!frozenFlag) {
			throw new Exception("冻结交易记录失败!");
		}
		// 插入冻结流水
		frozenOrderDao.createFrozenOrder("createFrozenOrder", conMap);
	}

	/**
	 * 冻结记录
	 * 
	 * @throws Exception
	 */
	@Override
	public void unFrozenOrder(Map<String, Object> conMap) throws Exception {
		// 锁定交易
		Object trade = null;
		try {
			trade = frozenOrderDao.findObjectByTemplate("lockTradeOrder",
					conMap);
		} catch (Exception ex) {
			throw new Exception("锁定交易失败!");
		}
		if (trade == null) {
			throw new Exception("锁定交易失败!");
		}
		// 更新交易状态
		boolean frozenFlag = frozenOrderDao.updateFrozenOrder("unFrozenTrade",
				conMap);
		if (!frozenFlag) {
			throw new Exception("解冻交易记录失败!");
		}
		// 插入冻结流水
		frozenOrderDao.createFrozenOrder("createUnFrozenOrder", conMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getTradeOrder(String tradeOrderNo) {
		Map<String, Object> conMap = new HashMap<String, Object>();
		return (Map<String, Object>) frozenOrderDao.findObjectByTemplate(
				"getTradeOrder", conMap);
	}
}