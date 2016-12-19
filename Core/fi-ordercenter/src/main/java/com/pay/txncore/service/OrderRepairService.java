package com.pay.txncore.service;


import com.pay.fi.fill.model.FillRecordInfo;
import com.pay.fi.fill.service.OrderFillRecordInfoService;
import com.pay.txncore.dto.RepairOrderDTO;

public interface OrderRepairService {
	boolean repairOrder(FillRecordInfo fillRecordInfo);
	
	public OrderFillRecordInfoService getOrderFillRecordInfoService();
	
	boolean repairOrder(RepairOrderDTO orderDTO);
}
