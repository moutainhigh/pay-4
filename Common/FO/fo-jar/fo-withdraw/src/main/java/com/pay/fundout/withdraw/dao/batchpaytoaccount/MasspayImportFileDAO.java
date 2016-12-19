/**
 *  File: MasspayImportFileDAO.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.dao.batchpaytoaccount;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportFile;
import com.pay.inf.dao.BaseDAO;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public interface MasspayImportFileDAO extends BaseDAO<MasspayImportFile> {
	/**
	 * 插入导入文件信息表
	 * 
	 * @param masspayImportFile
	 * @return
	 */
	public long createMasspayImportFile(MasspayImportFile masspayImportFile);

	/**
	 * 更新上传基本信息
	 * 
	 * @param masspayImportFile
	 * @return
	 */
	public boolean updateMasspayImportFile(MasspayImportFile masspayImportFile);

	/**
	 * 得到流水
	 * 
	 * @return
	 */
	public Long getSeqId();

	/**
	 * 判断批次号是否存在
	 * 
	 * @param batchNum
	 * @return
	 */
	public Integer isExistForBatchNum(Map params);

	/**
	 * 查询某个操作员的上传文件列表
	 * 
	 * @param page
	 * @param params
	 * @return
	 */
	// 分页
	public List getImportFileByOperatorsPage(Map params);

	// 所有
	public List getImportFileByOperatorsAll(Map params);

	// 记录数
	public Integer getImportFileByOperatorsCount(Map params);

	/**
	 * 当月金额
	 * 
	 * @return
	 */
	public Long getMonthTotalAmount(Long memberCode);

	/**
	 * 当日金额
	 * 
	 * @return
	 */
	public Long getDayTotalAmount(Long memberCode);

	/**
	 * 当月笔数
	 * 
	 * @return
	 */
	public Integer getMonthTotalCount(Long memberCode);

	/**
	 * 当日笔数
	 * 
	 * @return
	 */
	public Integer getDayTotalCount(Long memberCode);

	/**
	 * 更新记录可用状态
	 * 
	 * @param sequenceId
	 */
	public void updateBatchNum(Map params);

	/**
	 * 根据批次号和会员号获取指定的批量上传信息
	 * 
	 * @param params
	 * @return
	 */
	public MasspayImportFile getImportFileByBatchNumAndPayer(Map params);
}
