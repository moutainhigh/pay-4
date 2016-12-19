package com.pay.monitor.service.impl;


import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.monitor.dao.OrderMonitorDao;
import com.pay.monitor.dto.MonitorTradeData;
import com.pay.monitor.service.OrderMonitorService;
import com.pay.rm.blacklistcheck.dao.MonitorTradelistDAO;
import com.pay.rm.blacklistcheck.dto.MonitorTradeDTO;
import com.pay.util.JSonUtil;

public class OrderMonitorServiceImpl implements OrderMonitorService{
	
	private OrderMonitorDao orderMonitorDao;
	private MonitorTradelistDAO monitorTradelistDAO;
	
	
	

	


	public MonitorTradelistDAO getMonitorTradelistDAO() {
		return monitorTradelistDAO;
	}



	public void setMonitorTradelistDAO(MonitorTradelistDAO monitorTradelistDAO) {
		this.monitorTradelistDAO = monitorTradelistDAO;
	}



	public OrderMonitorDao getOrderMonitorDao() {
		return orderMonitorDao;
	}



	public void setOrderMonitorDao(OrderMonitorDao orderMonitorDao) {
		this.orderMonitorDao = orderMonitorDao;
	}



	@Override
	public void addMonitorOrder(String monitorOrder, long monitorType,Map<String, String> bnotifyMap) {
	
		
		List<MonitorTradeData> MonitorTradeDatas =orderMonitorDao.findMonitorOrder(monitorOrder);
		
		String temp =JSonUtil.bean2json(MonitorTradeDatas.get(0));
		MonitorTradeDTO data =(MonitorTradeDTO) JSonUtil.json2Object(temp, MonitorTradeDTO.class);
			data.setMonitorType(monitorType);
			data.setCardNo(bnotifyMap.get("cardHolderNumber"));
			data.setCardCountry(bnotifyMap.get("cardCountry"));
			String result =bnotifyMap.get("result");
			String responseDesc =bnotifyMap.get("responseDesc");
			data.setChannelRespCode(result);
			data.setChannelRespMsg(responseDesc);
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
			
			  Date strtodate=new Date();
			try {
				strtodate = formatter.parse(bnotifyMap.get("completeDate"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data.setCompleteDate(strtodate);
			monitorTradelistDAO.addMonitorTradelist(data,1);
		}



	@Override
	public void addMonitorOrder(String trademonitortemp2, long monitorType) {
		String[] monitorOrder=trademonitortemp2.split("_");

		List<MonitorTradeData> MonitorTradeDatas =orderMonitorDao.findMonitorOrder(monitorOrder[0]);
		
		String temp =JSonUtil.bean2json(MonitorTradeDatas.get(0));
		MonitorTradeDTO data =(MonitorTradeDTO) JSonUtil.json2Object(temp, MonitorTradeDTO.class);
			data.setMonitorType(monitorType);
			data.setCardNo(monitorOrder[2]);
			data.setCardCountry(monitorOrder[3]);
			String result =monitorOrder[4];
			String responseDesc =monitorOrder[5];
			data.setChannelRespCode(result);
			data.setChannelRespMsg(responseDesc);
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
			
			  Date strtodate=new Date();
			try {
				strtodate = formatter.parse(monitorOrder[1]);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			data.setCompleteDate(strtodate);
			monitorTradelistDAO.addMonitorTradelist(data,monitorType);
		
	}
		
	

}
