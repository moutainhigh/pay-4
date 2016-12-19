package com.pay.txncore.dao;


import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dto.ReconcileImportRecordBatchDto;
import com.pay.txncore.dto.ReconcileImportRecordDetailDto;

public interface ReconcileRecordDAO {
	
	String insertReconcileImportRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch);

	void insertReconcileImportRecordDetailDto(
			ReconcileImportRecordDetailDto reconcileImportRecordDetailDto);

	
	//更新批次信息   原来的代码，只是更新状态和成功条目数 
	void updateReconcileRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch);

	//2016-08-12 更新批次信息，包括各种状态
	void updateReconcileRecordBatchProcess(
			ReconcileImportRecordBatchDto importRecordBatch);
	
	
	void updateReconcileRecordDetail(
			ReconcileImportRecordDetailDto reconcileImportRecordDetailDto);

	//2016-08-12 更新详细信息的渠道订单号
	void updateReconcileRecordDetailChannelOrderNo(
			ReconcileImportRecordDetailDto reconcileImportRecordDetailDto);
	
	List<ReconcileImportRecordBatchDto> queryReconcileBatch(Map listMap, Page page);
	
	//2016-08-12 根据文件名查找
	List<ReconcileImportRecordBatchDto> queryReconcileBatchByFileName(Map listMap);

	
	List<ReconcileImportRecordDetailDto> queryReconcileDetailBatch(Map map);
	
	List<ReconcileImportRecordDetailDto> queryReconcileDetailSingle(Map map);
}
