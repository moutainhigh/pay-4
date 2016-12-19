package com.pay.txncore.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
 /**
  * 单项登记查询
  *  File: BouncedQueryDAO.java
  *  Description:
  *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
  *  Date      Author      Changes
  *  2016年5月11日   mmzhang     Create
  *
  */
public interface BouncedQueryDAO<BouncedResultDTO> extends BaseDAO {

	/**
	 * 单项登记查询
	 * @param tradeOrderNo
	 * @param channelOrderNo
	 * @param cardNo
	 * @param authorisation
	 * @param tranDate
	 * @return
	 * 2016年5月11日   mmzhang     add
	 */
	public List<BouncedResultDTO> bouncedQuery(String tradeOrderNo,
			String channelOrderNo, String cardNo,String cardNoLike, String authorisation,String tranDate,String orgCode);
	
	/**
	 * 批量登记查询
	 * @param tradeOrderNo
	 * @param channelOrderNo
	 * @param cardNo
	 * @param authorisation
	 * @param tranDate
	 * @return
	 * 2016年5月11日   mmzhang     add
	 */
	public List<BouncedResultDTO> bouncedResultQuery(Map<String, Object> map);

	public List<BouncedResultDTO> bouncedResultQuery(
			Map<String, Object> paraMap, Page page);
	/**
	 * 
	 * @param tradeOrderNo
	 * @param channelOrderNo
	 * @param cardNo
	 * @param cardNoLike
	 * @param authorisation
	 * @param tranDate
	 * @param orgCode
	 * @return
	 * 2016年7月21日   mmzhang     add
	 */
	public List<BouncedResultDTO> batchBouncedQuery(String batchNo);
	
	
	
}