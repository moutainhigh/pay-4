package com.pay.txncore.crosspay.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.service.TransRateMarkupService;
import com.pay.txncore.dao.TransRateMarkupDAO;
import com.pay.txncore.model.TransRateMarkup;

public class TransRateMarkupServiceImpl implements TransRateMarkupService {
	
	private TransRateMarkupDAO transRateMarkupDAO;

	@Override
	public boolean updateTransRateMarkup(TransRateMarkup markup) {
		return transRateMarkupDAO.update(markup);
	}

	@Override
	public int updateTransRateMarkupStatus(List<TransRateMarkup> markupList) {	
		return 0;
	}

	@Override
	public int updateTransRateMarkup(List<TransRateMarkup> markupList) {
		return 0;
	}

	@Override
	public void batchCreate(List<TransRateMarkup> markups) {
           transRateMarkupDAO.batchCreate(markups);
	}

	@Override
	public List<TransRateMarkup> findByCriteria(TransRateMarkup rate, Page page) {
		return transRateMarkupDAO.findByCriteria("findByCriteria",rate,page);
	}

	@Override
	public List<TransRateMarkup> findByCriteria(TransRateMarkup rate) {
		return transRateMarkupDAO.findByCriteria("findByCriteria",rate);
	}

	@Override
	public TransRateMarkup findTransRateMarkup(String sourceCurrency,
			String targetCurrency, String status, String memberCode,
			Date currentDate) {
		return null;
	}

	@Override
	public TransRateMarkup findTransRateMarkup(Map<String, Object> paramMap) {
		List<TransRateMarkup> list = transRateMarkupDAO.findByCriteria("findTransRateMarkup",paramMap);	
		if(list!=null&&!list.isEmpty())
			return list.get(0);
		return null;
	}

	@Override
	public TransRateMarkup getTransRateMarkup(TransRateMarkup markup) {
		return transRateMarkupDAO.findObjectByCriteria("findExistMarkup",markup);
	}

	public void setTransRateMarkupDAO(TransRateMarkupDAO transRateMarkupDAO) {
		this.transRateMarkupDAO = transRateMarkupDAO;
	}

	@Override
	public TransRateMarkup getTransRateMarkup(Map<String, Object> paramMap) {
		TransRateMarkup markup = this.findTransRateMarkup(paramMap);
		if(markup==null){
			paramMap.put("memberCode","0");
			markup = this.findTransRateMarkup(paramMap);
		}
		return markup;
	}
}
