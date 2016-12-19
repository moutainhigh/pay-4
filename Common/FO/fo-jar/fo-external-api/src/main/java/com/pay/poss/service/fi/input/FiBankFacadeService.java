/**
 *  File: FiBankFacadeService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-29      Sunsea.Li      Changes
 *  
 *
 */
package com.pay.poss.service.fi.input;

import java.util.List;
import java.util.Map;

/**<p>fi的银行信息服务</p>
 * @author Sunsea.Li
 *
 */
public interface FiBankFacadeService {
	/**获取银行列表
	 * @return
	 */
	public List<Map<String,String>> getFiBankList();
	
	/**根据银行编码获取银行名称
	 * @param bankCode
	 * @return
	 */
	public String getFiBankNameByCode(String bankCode);
}
