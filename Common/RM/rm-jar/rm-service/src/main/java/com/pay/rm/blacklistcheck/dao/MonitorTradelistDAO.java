package com.pay.rm.blacklistcheck.dao;



import com.pay.rm.blacklistcheck.dto.MonitorTradeDTO;



public interface MonitorTradelistDAO {
	public Long addMonitorTradelist(MonitorTradeDTO dto, long monitorType);



}
