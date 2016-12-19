/**
 *  File: GetSelectListInfo.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-10-2   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.reconcile.service.common;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Sandy_Yang
 */
public interface GetSelectListInfoService {
	
	public List<Map<String, String>> getBankOrgCodeList();
	//出款业务
	public List<Map<String, String>> getWithdrawBusiType();
	//对账状态
	public List<Map<String, String>> getBusiFlag();
	//调账状态
	public List<Map<String, String>> getReviseStatus();
	
	public List<Map<String,String>> getUploadStatus();

}
