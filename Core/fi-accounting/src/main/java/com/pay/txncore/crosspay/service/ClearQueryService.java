package com.pay.txncore.crosspay.service;

import java.util.Date;
import java.util.List;

import com.pay.fi.exception.BusinessException;
import com.pay.txncore.crosspay.model.ClearCollect;
import com.pay.txncore.crosspay.model.ClearLog;

public interface ClearQueryService {

	
	/**
	 * 查询结算汇总记录
	 * @param memberCode 商户号
	 * @return
	 * @throws BusinessException
	 */
	public List<ClearCollect> queryClearCollect(String memberCode)
			throws BusinessException;

	/**
	 * 根据id查询结算历史
	 * @param id
	 * @return
	 * @throws BusinessException
	 */
	public ClearLog queryClearLogById(String id)
			throws BusinessException;
	
	/**
	 * 分页查询结算历史记录
	 * @param memberCode
	 * @param beginTime
	 * @param endTime
	 * @param pageSize
	 * @param pageNo
	 * @return
	 * @throws BusinessException
	 */
	public List<ClearLog> queryClearLog(String memberCode,Date beginTime,Date endTime, int pageSize,
			int pageNo) throws BusinessException;
	
	
	/**
	 * 查询查询结算历史记录总数
	 * @param memberCode
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public Integer countQueryClearLog(String memberCode,Date beginTime,Date endTime)
			throws BusinessException;
}