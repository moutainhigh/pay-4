package com.pay.txncore.crosspay.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.dao.RefusePaymentOrderDAO;
import com.pay.txncore.crosspay.dto.acquire.RefusePaymentOrderDTO;
import com.pay.txncore.crosspay.model.RefusePaymentOrder;
import com.pay.txncore.crosspay.model.RefusePaymentOrderCriteria;
import com.pay.txncore.crosspay.service.RefusePaymentOrderService;

@SuppressWarnings("all")
public class RefusePaymentOrderServiceImpl implements RefusePaymentOrderService {

	private RefusePaymentOrderDAO refusePaymentOrderDao;

	public void setRefusePaymentOrderDao(
			RefusePaymentOrderDAO refusePaymentOrderDao) {
		this.refusePaymentOrderDao = refusePaymentOrderDao;
	}

	@Override
	public RefusePaymentOrder findById(Long id) {
		return refusePaymentOrderDao.findById(id);
	}

	@Override
	public List<RefusePaymentOrder> findByCriteria(
			RefusePaymentOrderCriteria criteria) {
		return refusePaymentOrderDao.findByCriteria(criteria);
	}

	@Override
	public long createRefusePaymentOrder(RefusePaymentOrder refusePaymentOrder) {
		return (Long) refusePaymentOrderDao.create(refusePaymentOrder);
	}

	@Override
	public boolean updateRefusePaymentOrder(
			RefusePaymentOrder refusePaymentOrder) {
		return refusePaymentOrderDao.update(refusePaymentOrder);
	}

	@Override
	public boolean deleteRefusePaymentOrder(Long id) {
		return refusePaymentOrderDao.delete(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<RefusePaymentOrder> queryRefusePaymentOrderForPage(
			Map<String, Object> refusePaymentOrderCriteria,
			Page<RefusePaymentOrder> origPage) {
		// 转换成查询page对象
		List<RefusePaymentOrder> resultList = refusePaymentOrderDao
				.findByCriteria("selectTradeOrders",
						refusePaymentOrderCriteria, origPage);
		// 转换成页面对象
		origPage.setResult(resultList);
		return origPage;
	}

	@Override
	public Page<RefusePaymentOrderDTO> selectRefusePamentOrderWebSite(
			RefusePaymentOrderCriteria refusePaymentOrderCriteria,
			Page<RefusePaymentOrderDTO> origPage) {
		// 转换成查询page对象
		List resultList = refusePaymentOrderDao.findByTemplate(
				"selectRefusePamentOrderWebSite", refusePaymentOrderCriteria);
		origPage.setResult(resultList);
		// 转换成页面对象
		return origPage;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<RefusePaymentOrder> queryRefusePaymentOrderForPage1(
			Map<String, Object> refusePaymentOrderCriteria,
			Page<RefusePaymentOrder> origPage) {
		Page page = new Page();
		// 转换成查询page对象
		List<RefusePaymentOrder> resultList = refusePaymentOrderDao
				.findByCriteria("selectRefuseOrders",
						refusePaymentOrderCriteria, origPage);
		// 转换成页面对象
		origPage.setResult(resultList);
		return origPage;
	}

	@Override
	public void refuseOrderApp(Map<String, Object> conMap) throws Exception {
		// 锁定交易
		Object trade = null;
		try {
			trade = refusePaymentOrderDao.findObjectByTemplate(
					"lockTradeOrder", conMap);
		} catch (Exception ex) {
			throw new Exception("锁定交易失败!");
		}
		if (trade == null) {
			throw new Exception("锁定交易失败!");
		}
		// 更新交易状态
		boolean frozenFlag = refusePaymentOrderDao.updateRefusePayment(
				"frozenTrade", conMap);
		if (!frozenFlag) {
			throw new Exception("冻结交易记录失败!");
		}
		// 插入冻结流水
		refusePaymentOrderDao.createRefusePayment("createRefuseOrder", conMap);
	}

	@Override
	public void updateRefuseOrderStatus(Map<String, Object> conMap)
			throws Exception {
		// 锁定交易
		Object trade = null;
		try {
			trade = refusePaymentOrderDao.findObjectByTemplate(
					"lockTradeOrder", conMap);
		} catch (Exception ex) {
			throw new Exception("锁定交易失败!");
		}
		if (trade == null) {
			throw new Exception("锁定交易失败!");
		}

		// 拒付状态
		String refuseStatus = (String) conMap.get("refuseStatus");
		// 拒付失败解冻交易订单
		if (StringUtils.equals("2", refuseStatus)) {
			// 更新交易状态
			boolean frozenFlag = refusePaymentOrderDao.updateRefusePayment(
					"unFrozenTrade", conMap);
			if (!frozenFlag) {
				throw new Exception("解冻交易记录失败!");
			}
		}

		// 更新拒付订单状态
		refusePaymentOrderDao.updateRefusePayment("updateRefuseOrderStatus",
				conMap);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getRefuseOrder(Map<String, Object> conMap) {
		return (Map<String, Object>) refusePaymentOrderDao
				.findObjectByTemplate("getRefuseOrder", conMap);
	}
}