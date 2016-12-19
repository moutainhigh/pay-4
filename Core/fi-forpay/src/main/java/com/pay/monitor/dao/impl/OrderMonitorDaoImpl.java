package com.pay.monitor.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.monitor.dao.OrderMonitorDao;
import com.pay.monitor.dto.MonitorTradeData;

public class OrderMonitorDaoImpl  extends BaseDAOImpl implements OrderMonitorDao{

	@Override
	public List<MonitorTradeData> findMonitorOrder(String  tradeOderNo) {
	
		try {
			return this.getSqlMapClient().queryForList("orderMonitor.findTradeOderData",tradeOderNo);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	

}
