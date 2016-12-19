package com.pay.risk.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.risk.model.ExpressTracking;
import com.pay.risk.model.ExpressTrackingCriteria;

public interface ExpressTrackingService {

	ExpressTracking findById(Long id);

	long createExpressTracking(ExpressTracking expressTracking);

	int updateExpressTracking(List<ExpressTracking> expressTrackings);

	boolean updateExpressTracking(ExpressTracking expressTracking);

	boolean deleteExpressTracking(Long id);

	List<ExpressTracking> findByCriteria(ExpressTracking criteria);

	List<ExpressTracking> findByCriteria(ExpressTracking criteria, Page page);

	List<ExpressTracking> findByCriteria(ExpressTrackingCriteria criteria);

	public Page<ExpressTracking> queryExpressTrackingForPage(
			ExpressTrackingCriteria expressTrackingCriteria,
			Page<ExpressTracking> origPage);

	public ExpressTracking findByTradeOrderNo(String tradeOrderNo);

	/**
	 * 统计商户未上传运单号金额
	 * 
	 * @param partnerId
	 * @return
	 */
	Long sumUnUploadTrackingNoAmount(Long partnerId);

	public List<ExpressTracking> queryTrackingDetailList(Map queryDetailPara,
			Integer pageNo, Integer pageSize);

	public Map<String, Object> queryTrackingDetailListCount(Map queryDetailPara);

	public ExpressTracking queryTrackingDetailList(Map queryDetailPara);

	public List<ExpressTracking> queryTrackingDetailLists(Map queryDetailPara);

	Page<ExpressTracking> queryTrackingForPage(
			Map<String, Object> orderCriteria, Page<ExpressTracking> origPage);

	public boolean updateTrackingInfoByTradeOrderNo(
			ExpressTracking expressTracking);

}