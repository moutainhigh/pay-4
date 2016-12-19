package com.pay.poss.service.gateway;

import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.dto.fi.IncomeDetailParaDTO;

/**
 * @author Sandy
 * @Date 2011-4-11
 * @Description 收款明细Service接口
 * @Copyright Copyright (c) 2004-2013 pay.com. All rights reserved.
 */
public interface IncomeDetailServiceFacade {

	/**
	 * 根据入参查询收款明细
	 * @param tradeSummaryDto
	 * @return List
	 */
	public Page<Map<String,Object>> queryIncomeDetailList(
			IncomeDetailParaDTO tradeSummaryDto, Integer pageNo,
			Integer pageSize);

	/**
	 * 查询单笔收款明细
	 * @param tradeSummaryDto
	 * @return List
	 */
	public Map<String, Object> querySingleIncomeDetail(Map<String, Object> map);

}
