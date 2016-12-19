/**
 *  <p>File: WorkorderOperationService.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.refund.service;

import java.util.List;

import com.pay.poss.refund.model.WordorderOperationLogDto;

public interface WorkorderOperationService {

	/**
	 * 
	 * @param stmtId
	 * @param pojo
	 * @return
	 */
	public long create(WordorderOperationLogDto pojo);

	/**
	 * 
	 * @param workorderKy
	 * @return
	 */
	public List<WordorderOperationLogDto> queryByWorkorderKy(String workorderKy);

	public void insertBatch(final List<WordorderOperationLogDto> paramList);
}
