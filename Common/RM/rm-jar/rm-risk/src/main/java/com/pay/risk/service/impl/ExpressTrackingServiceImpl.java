package com.pay.risk.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.risk.dao.ExpressTrackingDAO;
import com.pay.risk.model.ExpressTracking;
import com.pay.risk.model.ExpressTrackingCriteria;
import com.pay.risk.service.ExpressTrackingService;

public class ExpressTrackingServiceImpl implements ExpressTrackingService {

	private ExpressTrackingDAO expressTrackingDao;

	public void setExpressTrackingDao(ExpressTrackingDAO expressTrackingDao) {
		this.expressTrackingDao = expressTrackingDao;
	}

	@Override
	public ExpressTracking findById(Long id) {
		return (ExpressTracking) expressTrackingDao.findById(id);
	}

	@Override
	public ExpressTracking findByTradeOrderNo(String tradeOrderNo) {
		return (ExpressTracking) expressTrackingDao.findObjectByTemplate(
				"findByTradeOrderNo", tradeOrderNo);
	}

	@Override
	public List<ExpressTracking> findByCriteria(ExpressTrackingCriteria criteria) {
		return expressTrackingDao.findByCriteria(criteria);
	}

	@Override
	public long createExpressTracking(ExpressTracking expressTracking) {
		return (Long) expressTrackingDao.create(expressTracking);
	}

	@Override
	public boolean updateExpressTracking(ExpressTracking expressTracking) {
		return expressTrackingDao.update(expressTracking);
	}

	@Override
	public boolean deleteExpressTracking(Long id) {
		return expressTrackingDao.delete(id);
	}

	@Override
	public Page<ExpressTracking> queryExpressTrackingForPage(
			ExpressTrackingCriteria expressTrackingCriteria,
			Page<ExpressTracking> page) {
		// 转换成查询page对象
		List<ExpressTracking> resultList = expressTrackingDao.findByCriteria(
				expressTrackingCriteria, page);
		page.setResult(resultList);
		return page;
	}

	@Override
	public Long sumUnUploadTrackingNoAmount(Long partnerId) {

		Long amount = (Long) expressTrackingDao.findObjectByTemplate(
				"sumUnUploadTrackingNoAmount", partnerId);
		return amount;
	}

	@Override
	public List<ExpressTracking> queryTrackingDetailList(Map queryDetailPara,
			Integer pageNo, Integer pageSize) {

		List<ExpressTracking> resultList = expressTrackingDao
				.queryTrackingDetailList(queryDetailPara, pageNo, pageSize);

		return resultList;
	}

	@Override
	public Map<String, Object> queryTrackingDetailListCount(Map queryDetailPara) {
		return expressTrackingDao.countTrackingDetailList(queryDetailPara);
	}

	@Override
	public ExpressTracking queryTrackingDetailList(Map queryDetailPara) {

		List<ExpressTracking> resultList = expressTrackingDao
				.queryTrackingDetailList(queryDetailPara);

		return resultList.get(0);
	}

	@Override
	public List<ExpressTracking> queryTrackingDetailLists(Map queryDetailPara) {
		List<ExpressTracking> resultList = expressTrackingDao
				.queryTrackingDetailList(queryDetailPara);
		return resultList;
	}

	@Override
	public Page<ExpressTracking> queryTrackingForPage(
			Map<String, Object> trackingCriteria, Page<ExpressTracking> page) {

		List<ExpressTracking> resultList = expressTrackingDao.findByCriteria(
				"selectTrackings", trackingCriteria, page);
		page.setResult(resultList);
		return page;
	}

	@Override
	public boolean updateTrackingInfoByTradeOrderNo(
			ExpressTracking expressTracking) {
		// EXPRESS_TRACKING.updateTrackingInfo

		if (expressTrackingDao.countByCriteria("findByTrackingNo",
				expressTracking) > 0) {
			return false;
		}

		return expressTrackingDao.updateTrackingInfo("updateTrackingInfo",
				expressTracking);
	}

	@Override
	public List<ExpressTracking> findByCriteria(ExpressTracking criteria) {
		return expressTrackingDao.findByCriteria(criteria);
	}

	@Override
	public List<ExpressTracking> findByCriteria(ExpressTracking criteria,
			Page page) {
		return expressTrackingDao.findByCriteria("findByCriteria",criteria, page);
	}

	@Override
	public int updateExpressTracking(List<ExpressTracking> expressTrackings) {
		return expressTrackingDao.updateBatch("updateTrackingInfo", expressTrackings);
	}
}