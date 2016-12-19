/**
 * 
 */
package com.pay.txncore.service;

import java.util.List;

import com.pay.fi.exception.BusinessException;
import com.pay.jms.sender.JmsSender;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ReconcileImportRecordBatchDto;
import com.pay.txncore.dto.ReconcileImportRecordDetailDto;
import com.pay.txncore.dto.ReconciliationDto;
import com.pay.txncore.model.RefundOrder;

/**
 * 渠道服务
 * 
 * @author chaoyue
 *
 */
public interface ChannelService {

	/**
	 * 渠道对账
	 * 
	 * @param reconciliationDtos
	 * @throws BusinessException
	 */
	ChannelOrderDTO reconciliationRnTx(String startDate, String endDate,
			ReconciliationDto reconciliationDto,String[] settlementCurrencyCodes) throws BusinessException;
	/**
	 * 退款对账
	 * 
	 * @param reconciliationDtos
	 * @throws BusinessException
	 */
	RefundOrder reconciliationRefundRnTx(String startDate, String endDate,
			ReconciliationDto reconciliationDto) throws BusinessException;
	
	CurrencyRateService getCurrencyRateService();
	
	public JmsSender getJmsSender();
	
		/***
	 * 生成对账批次表
	 * @param importRecordBatch
	 * @return
	 */
	String insertReconcileImportRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch);

	void insertReconcileImportRecordDetailDto(
			List<ReconcileImportRecordDetailDto> detailDtos);

	void updateReconcileRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch);

	void updateReconcileRecordDetail(
			List<ReconcileImportRecordDetailDto> detailDtos);

	/**
	 * 查询渠道订单。在对账时，进行预处理操作，根据账目时间获取指定时间段内的渠道订单。
	 * @author davis.guo at 2016-08-14
	 * 
	 * @param channelOrderDTO
	 * @return
	 */
	
	public List<ChannelOrderDTO> queryChannelOrder(ChannelOrderDTO channelOrderDTO);
}
