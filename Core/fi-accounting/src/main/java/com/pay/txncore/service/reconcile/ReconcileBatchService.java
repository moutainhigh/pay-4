package com.pay.txncore.service.reconcile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ReconcileImportRecordBatchDto;
import com.pay.txncore.dto.ReconcileImportRecordDetailDto;
import com.pay.txncore.dto.ReconciliationDto;
import com.pay.txncore.model.RefundOrder;

/*
 * 批次信息的处理，   已经一些对账逻辑的实现 
 * 把　ReconciliationHandle(老的处理流程) 以及 ReconcilationTask 中需要调用的函数，封装在这个Service 中去了  
 */
public interface ReconcileBatchService {
	
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
	
	void updateReconcileRecordDetailChannelOrderNo(
				ReconcileImportRecordDetailDto reconcileImportRecordDetailDto);
	
	/*
	 * 插入预处理的批次
	 */
	String insertReconcileImportRecordBatch_Pre(List<ReconciliationDto> reconciliationDtos,Map map,Date date) throws ParseException;
	
	/*
	 * 插入批次
	 */
	String insertReconcileImportRecordBatch2(List<ReconciliationDto> reconciliationDtos,Map map,Date date) throws ParseException;
	
	//更新状态： 和处理相关
	void updateBatchStatus(String batchNo,int status);
	
	//更新状态，和预处理相关的状态
	void updateBatchPreStatus(String batchNo,int status);
	
	//2016-08-12 根据文件名查找
	List<ReconcileImportRecordBatchDto> queryReconcileBatchByFileName(String fileName);
	
	//2016-08-12 处理批次的各种信息，最后完成的时候调用 
	void updateReconcileRecordBatchProcess(
			ReconcileImportRecordBatchDto importRecordBatch);
	
	void test();
	
	
	void addResultList_ChannelOrderDTO(ReconcileImportRecordBatchDto importRecordBatch, 
			List<ReconcileImportRecordDetailDto> detailDtos,
			final ChannelOrderDTO channelOrder,
			final String batchNo,
			final String listIndex);
	
	
	void addResultList_RefundOrderDTO(ReconcileImportRecordBatchDto importRecordBatch, 
			List<ReconcileImportRecordDetailDto> detailDtos,
			final RefundOrder refundOrder,
			final String batchNo,
			final String listIndex);
	
	/*
	 * 检查是否需要发送到交易日报表服务
	 * 检查是哪个渠道的对账单数据不在这边处理，交给异步监听服务fopay处理,这边只关心是否对账成功，
	 * 对账成功就加进交易日报表数据更新集合
	 */
	boolean checkNeedAddtoReportService(
			ReconciliationDto reconciliationDto,
			ChannelOrderDTO   channelOrderDTO);
	
	/*
	 * 正规化数据，==>ReportSerivce
	 * 这里可能有问题，所以这个代码需要放在 对账之后，不影响原有的对账流程
	 */
	void normalize2ReportService(ReconciliationDto reconciliationDto);	
}
