package com.pay.acct.buySettleParities.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pay.acct.buySettleParities.dao.AccApplyCheckDao;
import com.pay.acct.buySettleParities.dict.ApplyCheckDict;
import com.pay.acct.buySettleParities.model.AccApplyCheck;
import com.pay.acct.buySettleParities.service.AccApplyCheckService;
import com.pay.inf.dao.Page;

@Service(value="accApplyCheckServiceImpl")
public class AccApplyCheckServiceImpl implements AccApplyCheckService{

	@Autowired
	private AccApplyCheckDao accApplyCheckDaoImpl;
	
	@Override
	public List<AccApplyCheck> queryConditions(AccApplyCheck accApplyCheck) {
		return accApplyCheckDaoImpl.queryConditions(accApplyCheck);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false,noRollbackFor={RuntimeException.class,Exception.class})
	@Override
	public long createAccApplyCheck(AccApplyCheck accApplyCheck) {
		accApplyCheck.setCreateDate(new Date());
		accApplyCheck.setStatus(ApplyCheckDict.WAIT_CHECK.getStatus());
		return (Long) accApplyCheckDaoImpl.create(accApplyCheck);
	}

	@Override
	public List<AccApplyCheck> queryByCriteria(Map<String, Object> params) {
		return accApplyCheckDaoImpl.queryByCriteria(params);
	}

	@Override
	public List<AccApplyCheck> queryByCriteria(Map<String, Object> params,
			Page<Object> page) {
		return accApplyCheckDaoImpl.queryByCriteria(params,page);
	}

	@Override
	public void check(List<AccApplyCheck> accApplyChecks) {
		accApplyCheckDaoImpl.updateBatch("check", accApplyChecks);
	}

	@Override
	public Integer queryByCriteriaCount(Map<String, Object> params) {
		return accApplyCheckDaoImpl.queryByCriteriaCount(params);
	}
}
