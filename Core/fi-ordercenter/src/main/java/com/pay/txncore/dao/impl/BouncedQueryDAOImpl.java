package com.pay.txncore.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.BouncedQueryDAO;

public class BouncedQueryDAOImpl<BouncedResultDTO> extends
		BaseDAOImpl implements BouncedQueryDAO<BouncedResultDTO> {


	@Override
	public List<BouncedResultDTO> bouncedQuery(String tradeOrderNo, String channelOrderNo,
			String cardNo,String cardNoLike, String authorisation, String tranDate,String orgCode) {
		
		Map<String,String> map = new HashMap<String,String>();
		if(tradeOrderNo!=null && !"null".equals(tradeOrderNo))
		{
			map.put("tradeOrderNo", tradeOrderNo);
		}
		if(channelOrderNo!=null && !"null".equals(channelOrderNo))
		{
			map.put("channelOrderNo", channelOrderNo);
		}
		if(cardNo!=null && !"null".equals(cardNo))
		{
			map.put("cardNo", cardNo);
		}
		if(cardNoLike!=null && !"null".equals(cardNoLike))
		{
			map.put("cardNoLike", cardNoLike);
		}
		if(authorisation!=null && !"null".equals(authorisation))
		{
			map.put("authorisation", authorisation);
		}
		if(tranDate!=null && !"null".equals(tranDate))
		{
			map.put("tranDate", tranDate);
		}
		if(orgCode!=null && !"null".equals(orgCode))
		{
			map.put("orgCode", orgCode);
		}
		return super.findByCriteria("bouncedQuery", map);
	}
	
	@Override
	public List<BouncedResultDTO> bouncedResultQuery(Map<String, Object> map) {
		return super.findByCriteria("bouncedResultQuery", map);
	}
	@Override
	public List<BouncedResultDTO> bouncedResultQuery(
			Map<String, Object> paraMap, Page page) {
		return super.findByCriteria("bouncedResultQuery", paraMap,page);
	}

	@Override
	public List<BouncedResultDTO> batchBouncedQuery(String batchNo) {
		
		Map<String,String> map = new HashMap<String,String>();
		if(batchNo!=null && !"null".equals(batchNo))
		{
			map.put("batchNo", batchNo);
		}
		return super.findByCriteria("batchBouncedQuery", map);
	}
	
}
