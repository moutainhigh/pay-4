package com.pay.txncore.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dto.ReconcileImportRecordBatchDto;
import com.pay.txncore.dto.ReconcileImportRecordDetailDto;


public interface ReconcileRecordService {

	String insertReconcileImportRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch);

	void insertReconcileImportRecordDetailDto(
			List<ReconcileImportRecordDetailDto> detailDtos);

	void updateReconcileRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch);

	void updateReconcileRecordDetail(
			List<ReconcileImportRecordDetailDto> detailDtos);

	List<ReconcileImportRecordBatchDto> queryReconcileBatch(Map listMap, Page page);

	List<ReconcileImportRecordDetailDto> queryReconcileDetailBatch(Map map);
	
	List<ReconcileImportRecordDetailDto> queryReconcileDetailSingle(Map map);

	
	//2016-08-12 更新详细信息的渠道订单号
	void updateReconcileRecordDetailChannelOrderNo(
				ReconcileImportRecordDetailDto reconcileImportRecordDetailDto);
	
	//2016-08-12 更新批次信息，包括各种状态
	void updateReconcileRecordBatchProcess(
			ReconcileImportRecordBatchDto importRecordBatch);
	
	//2016-08-12 根据文件名查找
	List<ReconcileImportRecordBatchDto> queryReconcileBatchByFileName(Map listMap);
}
