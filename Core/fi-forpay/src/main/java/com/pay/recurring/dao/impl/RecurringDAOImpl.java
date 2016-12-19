package com.pay.recurring.dao.impl;

import java.sql.SQLException;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.recurring.dao.RecurringDAO;

public class RecurringDAOImpl extends BaseDAOImpl implements RecurringDAO{

	@Override
	public void createRecurring(Map<String, String> data) {
		this.create(data);
	}

	@Override
	public void cancelRecurring(Map<String, String> data) {
		this.update("cancelRecurring",data); // 取消 recurring 表
		this.update("cancelRecurringQue",data); // 取消 recurringQue 表
	}

	@Override
	public void createRecurringCancel(Map<String, String> data) {
		this.create("createRecurringCancel",data);
	}

	@Override
	public void cancelPostponeRecurring(Map<String, String> data) {
	 	try {
	 		this.getSqlMapClient().insert("recurring.cancelPostponeRecurring",data);//延期取消
	 		this.update("updatePostponeRecurring",data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
