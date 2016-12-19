package com.pay.txncore.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.dao.ReconcileRecordDAO;
import com.pay.txncore.dto.ReconcileImportRecordBatchDto;
import com.pay.txncore.dto.ReconcileImportRecordDetailDto;
import com.pay.txncore.service.ReconcileRecordService;

public class ReconcileRecordServiceImpl implements ReconcileRecordService{

	private ReconcileRecordDAO reconcileRecordDAO;

	public void setReconcileRecordDAO(ReconcileRecordDAO reconcileRecordDAO) {
		this.reconcileRecordDAO = reconcileRecordDAO;
	}

	public String insertReconcileImportRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch) {
		return reconcileRecordDAO.insertReconcileImportRecordBatch(importRecordBatch);
	}

	public void insertReconcileImportRecordDetailDto(
			List<ReconcileImportRecordDetailDto> detailDtos) {
		for (ReconcileImportRecordDetailDto reconcileImportRecordDetailDto : detailDtos) {
				reconcileRecordDAO.insertReconcileImportRecordDetailDto(reconcileImportRecordDetailDto);
			}
	}

	@Override
	public void updateReconcileRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch) {
		reconcileRecordDAO.updateReconcileRecordBatch(importRecordBatch);
	}

	@Override
	public void updateReconcileRecordDetail(
			List<ReconcileImportRecordDetailDto> detailDtos) {
		for (ReconcileImportRecordDetailDto reconcileImportRecordDetailDto : detailDtos) {
			reconcileRecordDAO.updateReconcileRecordDetail(reconcileImportRecordDetailDto);
		}
	}

	@Override
	public List<ReconcileImportRecordBatchDto> queryReconcileBatch(Map listMap,Page page) {
		return 	reconcileRecordDAO.queryReconcileBatch(listMap,page);
	}

	@Override
	public List<ReconcileImportRecordDetailDto> queryReconcileDetailBatch(
			Map map) {
		return reconcileRecordDAO.queryReconcileDetailBatch(map);
	}

	
}
