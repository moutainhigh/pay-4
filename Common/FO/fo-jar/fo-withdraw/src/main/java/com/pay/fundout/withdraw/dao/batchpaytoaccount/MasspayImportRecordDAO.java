/**
 *  File: MasspayImportRecordDAO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.batchpaytoaccount;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportRecordDTO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportRecord;
import com.pay.inf.dao.BaseDAO;

/**
 * @author darv
 * 
 */
public interface MasspayImportRecordDAO extends BaseDAO<MasspayImportRecord> {
	/**
	 * 插入文件导入记录
	 * 
	 * @param masspayImportRecord
	 * @return
	 */
	public long createMasspayImportRecord(
			MasspayImportRecord masspayImportRecord);

	/**
	 * 批量插入导入记录
	 * 
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	public void batchCreateImporRecord(List list);

	/**
	 * 得到导入文件记录
	 * 
	 * @param params
	 * @return
	 */
	// 所有
	public List getImportRecordListByFileKyAll(Map params);

	// 分页
	public List getImportRecordListByFileKyPage(Map params);

	// 记录数
	public Integer getImportRecordListByFileKyCount(Map params);

	/**
	 * 得到错误的金额总数
	 * 
	 * @param params
	 * @return
	 */
	public Long getErrorAmountByFileKy(Map params);

	/**
	 * 更新是否生成订单的标记
	 * 
	 * @param importRecord
	 * @return
	 */
	public int updateStatus(MasspayImportRecordDTO importRecord);
}
