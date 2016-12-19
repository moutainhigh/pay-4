package com.pay.recurring.dao.impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.recurring.dao.TaskRecurringDAO;
import com.pay.recurring.model.RecurringQue;

public class TaskRecurringDAOImpl  extends BaseDAOImpl implements TaskRecurringDAO{

	@Override
	public void crateRecurringQue() {
      	try {
      
      			this.getSqlMapClient().insert("taskRecurring.create");
      			this.getSqlMapClient().update("taskRecurring.update");
      	} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<RecurringQue> findRecurringQue() {
		return this.findAll("findRecurringQue");
	}

	@Override
	public void updateRecurring(RecurringQue resultMap) {
		this.update("updateRecurring",resultMap);
	}

	@Override
	public void delRecurringQue(RecurringQue resultMap) {
		this.delete("delRecurringQue",resultMap);
	}

	@Override
	public void updateRecurringQue(RecurringQue resultMap) {
		this.update("updateRecurringQue",resultMap);
	}

	@Override
	public Integer findMaxFailedTimes(RecurringQue resultMap) {
		try {
			return (Integer)this.getSqlMapClient().queryForObject("taskRecurring.findMaxFailedTimes",resultMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Integer findFailedTimes(RecurringQue resultMap) {
		try {
			return (Integer)this.getSqlMapClient().queryForObject("taskRecurring.findFailedTimes",resultMap);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateFailedRecurring(RecurringQue resultMap) {
		this.update("updateFailedRecurring",resultMap);
	}

	@Override
	public void updateSingleFailedRecurringQue(RecurringQue resultMap) {
		this.update("updateSingleFailedRecurringQue",resultMap);
	}
}
