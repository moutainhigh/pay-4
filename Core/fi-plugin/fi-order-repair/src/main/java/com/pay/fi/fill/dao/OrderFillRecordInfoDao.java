/**
 * 
 */
package com.pay.fi.fill.dao;

import java.util.List;
import java.util.Map;

import com.pay.fi.fill.model.FillRecordInfo;

/**
 * 补单记录明细DAO
 * @author PengJiangbo
 *
 */
public interface OrderFillRecordInfoDao {
	
	/**
	 * 明细记录保存
	 * @param list
	 */
	public void orderFillRecordSave(final List<FillRecordInfo> list) ;
	
	/**
	 * 根据补单请求批次号查询明细
	 * @param reqBatchNo
	 * @return
	 */
	public List<FillRecordInfo> findOrderFillRecordByReqBatchNo(Long reqBatchNo) ;
	
	/**
	 * 根据请求批次号更新批次记录结果状态
	 * @param hMap
	 * @return
	 */
	public int updateRecordStatusByReqBatchNo(Map<String, Object> hMap) ;
	
	/**
	 * 批量更新批次记录明细信息
	 * @param list
	 * @return
	 */
	boolean updateFillRecordBatch(final List<FillRecordInfo> list) ;
	
}
