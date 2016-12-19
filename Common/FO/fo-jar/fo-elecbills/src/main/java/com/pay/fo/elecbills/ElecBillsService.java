/**
 *  File: ElecBillsService.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-12-5   Sany        Create
 *
 */
package com.pay.fo.elecbills;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * 电子回单
 */
public interface ElecBillsService {
	
	/**
	 * 查看回单
	 * @param serialNo
	 * @return
	 */
	public Map<String,Object> layoutBills(Map<String,Object> serialNo);
	
	/**
	 * 生成电子回单
	 * @param serialNo
	 * @return
	 */
	public BufferedImage generateBills(Map<String,Object> serialNo) throws Exception;
	
	
}
