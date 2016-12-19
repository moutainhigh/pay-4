package com.pay.txncore.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.txncore.dao.ReconcileRecordDAO;
import com.pay.txncore.dto.ReconcileImportRecordBatchDto;
import com.pay.txncore.dto.ReconcileImportRecordDetailDto;

public class ReconcileRecordDAOImpl extends BaseDAOImpl implements ReconcileRecordDAO{

	@Override
	public String insertReconcileImportRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch) {
		String bacthNo=(String)super.create("insertReconcileImportRecordBatch", importRecordBatch);
		return bacthNo;
	}
	
	@Override
	public void insertReconcileImportRecordDetailDto(
			ReconcileImportRecordDetailDto reconcileImportRecordDetailDto) {
		super.create("insertReconcileRecordDetail", reconcileImportRecordDetailDto);
	}

	@Override
	public void updateReconcileRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch) {
		super.update("updateReconcileRecordBatch", importRecordBatch);
	}

	//2016-08-12 更新各种状态
	@Override
	public void updateReconcileRecordBatchProcess(
				ReconcileImportRecordBatchDto importRecordBatch){
		super.update("updateReconcileRecordBatchProcess", importRecordBatch);
	}
		
	@Override
	public void updateReconcileRecordDetail(
			ReconcileImportRecordDetailDto reconcileImportRecordDetailDto) {
		super.update("updateReconcileRecordDetail", reconcileImportRecordDetailDto);
	}

	//2016-08-12 更新详细信息的渠道订单号
	@Override 
	public void updateReconcileRecordDetailChannelOrderNo(
			ReconcileImportRecordDetailDto reconcileImportRecordDetailDto){
		super.update("updateReconcileRecordChannelOrderNo",reconcileImportRecordDetailDto);
	}
	
	@Override  
	public List<ReconcileImportRecordBatchDto> queryReconcileBatch(Map listMap,Page page) {
		return 	super.findByCriteria(listMap, page);
	}
	
	@Override  
	public List<ReconcileImportRecordBatchDto> queryReconcileBatchByFileName(Map listMap) {
		return 	super.findByCriteria("findByFileName",listMap);
	}
	

	@Override
	public List<ReconcileImportRecordDetailDto> queryReconcileDetailBatch(
			Map map) {
		return super.findByCriteria("queryReconcileDetailBatch", map);
	}

	/* (non-Javadoc)
	 * @see com.pay.txncore.dao.ReconcileRecordDAO#queryReconcileDetailSingle(java.util.Map)
	 */
	@Override
	public List<ReconcileImportRecordDetailDto> queryReconcileDetailSingle(
			Map map) {
		return super.findByCriteria("queryReconcileDetailSingle", map) ;
	}
	
}
