package com.pay.txncore.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dao.RemitFailTypingDao;
import com.pay.txncore.model.KfPayTradeDetail;
import com.pay.txncore.service.RemitFailTypingService;

public class RemitFailTypingServiceImpl implements RemitFailTypingService{

	private RemitFailTypingDao remitFailTypingDao;
	@Override
	public List<KfPayTradeDetail> findKfPayTradeDetail(
			KfPayTradeDetail kfPayTradeDetail, Page page) {
		return remitFailTypingDao.findKfPayTradeDetail(kfPayTradeDetail,page);
	}
	@Override
	public void batchReviewed(Map<String, Object> paraMap) {
	       String detailNos=String.valueOf(paraMap.get("detailNos"));
	       String[] detailNoArr = detailNos.split(",");
	       for (String detailNo : detailNoArr ) {
	    	paraMap.put("detailNo", detailNo);
	    	remitFailTypingDao.update(paraMap);
	      }
	}
	
	
	public void setRemitFailTypingDao(RemitFailTypingDao remitFailTypingDao) {
		this.remitFailTypingDao = remitFailTypingDao;
	}
	@Override
	public List<KfPayTradeDetail> findKfPayTradeDetail(
			KfPayTradeDetail kfPayTradeDetail) {
		return remitFailTypingDao.findKfPayTradeDetail(kfPayTradeDetail);
	}
	@Override
	public void batchUpdate(List<KfPayTradeDetail> list) {
		Map<String, Object> paraMap =new HashMap<String, Object>();
		for (KfPayTradeDetail kfPayTradeDetail : list) {
			paraMap.put("detailNo", kfPayTradeDetail.getDetailNo());
			paraMap.put("status", "0");
			remitFailTypingDao.update(paraMap);
		}
	}
}
