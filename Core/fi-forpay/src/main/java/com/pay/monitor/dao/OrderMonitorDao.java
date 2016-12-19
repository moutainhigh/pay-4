package com.pay.monitor.dao;

import java.util.List;
import java.util.Map;

import com.pay.monitor.dto.MonitorTradeData;

public interface OrderMonitorDao {

	List<MonitorTradeData> findMonitorOrder(String  tradeOderNo);

}
