package com.pay.rm.result.service.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.rm.result.dao.CybsResultDAO;
import com.pay.rm.result.dto.CybsResult;
import com.pay.rm.result.service.CybsResultService;

public class CybsResultServiceImpl implements CybsResultService {
	private CybsResultDAO cybsResultDAO;
		

	public void setCybsResultDAO(CybsResultDAO cybsResultDAO) {
		this.cybsResultDAO = cybsResultDAO;
	}

	public List<CybsResult> findCybsResult(CybsResult cy) {
		 List<CybsResult> riskOrder = cybsResultDAO.findCybsResult(cy);
		 return  riskOrder;
	}
	public List<CybsResult> findCybsResult(CybsResult cy,Page page) {
		List<CybsResult> riskOrder = cybsResultDAO.findCybsResult(cy,page);
		return  riskOrder;
	}
}
