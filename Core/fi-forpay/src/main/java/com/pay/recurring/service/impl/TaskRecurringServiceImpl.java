package com.pay.recurring.service.impl;

import java.util.List;

import com.pay.recurring.dao.TaskRecurringDAO;
import com.pay.recurring.model.RecurringQue;
import com.pay.recurring.service.TaskRecurringService;

public class TaskRecurringServiceImpl implements TaskRecurringService{

	private TaskRecurringDAO taskRecurringDao;

	public void settaskRecurringDao(
			TaskRecurringDAO taskRecurringDao) {
		this.taskRecurringDao = taskRecurringDao;
	}

	@Override
	public void crateRecurringQue() {
		this.taskRecurringDao.crateRecurringQue();
	}

	@Override
	public List<RecurringQue> findRecurringQue() {
		return this.taskRecurringDao.findRecurringQue();
	}

	@Override
	public void updateRecurring(RecurringQue resultMap) {
		this.taskRecurringDao.updateRecurring(resultMap);
	}	

	@Override
	public void delRecurringQue(RecurringQue resultMap) {
		this.taskRecurringDao.delRecurringQue(resultMap);
	}

	@Override
	public void updateRecurringQue(RecurringQue resultMap) {
		this.taskRecurringDao.updateRecurringQue(resultMap);
	}

	@Override
	public Boolean compareFailedTimes(RecurringQue resultMap ) {
		Integer maxFailedTimes=this.taskRecurringDao.findMaxFailedTimes(resultMap);
		Integer failedTimes=this.taskRecurringDao.findFailedTimes(resultMap);
		return failedTimes<maxFailedTimes-1?true:false;
	}

	@Override
	public void updateFailedRecurring(RecurringQue resultMap) {
		this.taskRecurringDao.updateFailedRecurring(resultMap);
	}

	@Override
	public void updateSingleFailedRecurringQue(RecurringQue resultMap) {
		this.taskRecurringDao.updateSingleFailedRecurringQue(resultMap);
	}
	
}
