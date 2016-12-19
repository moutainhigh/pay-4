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

}
