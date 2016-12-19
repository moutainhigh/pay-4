package com.pay.txncore.crosspay.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fi.exception.BusinessException;
import com.pay.txncore.crosspay.dao.ClearCollectDAO;
import com.pay.txncore.crosspay.dao.ClearLogDAO;
import com.pay.txncore.crosspay.model.ClearCollect;
import com.pay.txncore.crosspay.model.ClearCollectCriteria;
import com.pay.txncore.crosspay.model.ClearLog;
import com.pay.txncore.crosspay.service.ClearQueryService;

public class ClearQueryServiceImpl implements ClearQueryService {

	private ClearCollectDAO clearCollectDAO;

	private ClearLogDAO clearLogDAO;

	public ClearCollectDAO getClearCollectDAO() {
		return clearCollectDAO;
	}

	public void setClearCollectDAO(ClearCollectDAO clearCollectDAO) {
		this.clearCollectDAO = clearCollectDAO;
	}

	public ClearLogDAO getClearLogDAO() {
		return clearLogDAO;
	}

	public void setClearLogDAO(ClearLogDAO clearLogDAO) {
		this.clearLogDAO = clearLogDAO;
	}

	/**
	 * 得到可用的时间类型
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return String[0]开始时间，String[1]结束时间
	 * @throws Exception
	 */
	private String[] parseTime(Date startTime, Date endTime)
			throws BusinessException {
		String[] timeArr = new String[2];
		String sTime = null;
		String eTime = null;
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			if (null != startTime) {
				startTime = dateFormat.parse(dateFormat.format(startTime)
						.substring(0, 16) + ":00");
				sTime = dateFormat.format(startTime);
			}
			if (null != endTime) {
				endTime = dateFormat.parse(dateFormat.format(endTime)
						.substring(0, 16) + ":59");
				eTime = dateFormat.format(endTime);
			}
			timeArr[0] = sTime;
			timeArr[1] = eTime;
			return timeArr;
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("时间类型格式转换错误");
		}
	}

	@Override
	public List<ClearCollect> queryClearCollect(String memberCode)
			throws BusinessException {
		ClearCollectCriteria criteria = new ClearCollectCriteria();
		criteria.createCriteria().andPartnerIdEqualTo(memberCode);

		List<ClearCollect> clearCollectList = clearCollectDAO
				.findByCriteria(criteria);
		return clearCollectList;
	}

	@Override
	public List<ClearLog> queryClearLog(String memberCode, Date beginTime,
			Date endTime, int pageSize, int pageNo) throws BusinessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", memberCode);

		// 时间处理
		String[] time = parseTime(beginTime, endTime);
		paramMap.put("startTime", time[0]);
		paramMap.put("endTime", time[1]);

		List<ClearLog> listResult = null;
		try {
			listResult = clearLogDAO.queryClearLog(paramMap, pageSize, pageNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("查询清算历史记录出错");// TODO 此处需要添加错误枚举
		}

		return listResult;
	}

	@Override
	public Integer countQueryClearLog(String memberCode, Date beginTime,
			Date endTime) throws BusinessException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", memberCode);

		// 时间处理
		String[] time = parseTime(beginTime, endTime);
		paramMap.put("startTime", time[0]);
		paramMap.put("endTime", time[1]);

		Integer i = 0;
		try {
			i = clearLogDAO.countQueryClearLog(paramMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("查询清算历史记录总数出错");// TODO 此处需要添加错误枚举
		}

		return i;
	}

	@Override
	public ClearLog queryClearLogById(String id) throws BusinessException {
		return clearLogDAO.findById(Long.valueOf(id));
	}

}
