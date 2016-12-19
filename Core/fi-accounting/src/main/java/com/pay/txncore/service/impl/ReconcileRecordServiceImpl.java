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

	/* (non-Javadoc)
	 * @see com.pay.txncore.service.ReconcileRecordService#queryReconcileDetailSingle(java.util.Map)
	 */
	@Override
	public List<ReconcileImportRecordDetailDto> queryReconcileDetailSingle(
			Map map) {
		return this.reconcileRecordDAO.queryReconcileDetailSingle(map) ;
	}

	
	//2016-08-12 更新详细信息的渠道订单号
	@Override 
	public void updateReconcileRecordDetailChannelOrderNo(
				ReconcileImportRecordDetailDto reconcileImportRecordDetailDto){
		this.reconcileRecordDAO.updateReconcileRecordDetailChannelOrderNo(reconcileImportRecordDetailDto);
	}
	
	//2016-08-12 更新批次信息，包括各种状态
	@Override 
	public void updateReconcileRecordBatchProcess(
			ReconcileImportRecordBatchDto importRecordBatch){
		this.reconcileRecordDAO.updateReconcileRecordBatchProcess(importRecordBatch);
	}
	
	//2016-08-12 根据文件名查找
	@Override
	public List<ReconcileImportRecordBatchDto> queryReconcileBatchByFileName(Map listMap){
		return this.reconcileRecordDAO.queryReconcileBatchByFileName(listMap);
	}
	
}
