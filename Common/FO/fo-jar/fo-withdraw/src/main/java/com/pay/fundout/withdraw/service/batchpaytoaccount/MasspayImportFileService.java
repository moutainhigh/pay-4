/**
 *  File: MasspayImportFileService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-26     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.batchpaytoaccount;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.batchpaytoaccount.MasspayImportFileDTO;
import com.pay.fundout.withdraw.model.batchpaytoaccount.MasspayImportFile;
import com.pay.poss.base.exception.PossException;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public interface MasspayImportFileService {
	/**
	 * 插入导入文件信息表
	 */
	public long createMasspayImportFile(MasspayImportFile masspayImportFile);

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
	 * @return
	 */
	public Integer getMonthTotalCount(Long memberCode);
	
	/**
	 * 当日笔数
	 * @return
	 */
	public Integer getDayTotalCount(Long memberCode);

	/**
	 * 确认付款操作后的相关更新
	 * 
	 * @param sequenceId
	 */
	public void confirmPayRdTx(MasspayImportFile importFile)
			throws PossException;

	/**
	 * 上传后的信息创建
	 * 
	 * @param importFileDTO
	 * @param resolveList
	 */
	public void uploadAfterCreateRdTx(MasspayImportFile importFile, List resolveList) throws PossException;
	
	
	/**
	 * 更新上传基本信息
	 * @param masspayImportFile
	 * @return
	 */
	public boolean updateMasspayImportFileRdTx(MasspayImportFileDTO dto);


}
