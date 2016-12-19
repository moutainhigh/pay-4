package com.pay.monitor.service;

import java.util.Map;

public interface OrderMonitorService {

	void addMonitorOrder(String monitorOrder, long monitorType, Map<String, String> bnotifyMap);

	void addMonitorOrder(String trademonitortemp2, long monitorType);

}
