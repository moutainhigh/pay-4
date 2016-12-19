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

	void updateReconcileRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch);

	void updateReconcileRecordDetail(
			ReconcileImportRecordDetailDto reconcileImportRecordDetailDto);

	List<ReconcileImportRecordBatchDto> queryReconcileBatch(Map listMap, Page page);

	List<ReconcileImportRecordDetailDto> queryReconcileDetailBatch(Map map);
}
