/**
 *  File: FoBusinessService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-28      Sunsea.Li      Changes
 *
 */
package com.pay.fundout.channel.service.business;

import java.util.Map;

import com.pay.fundout.channel.dto.business.FundoutBusinessDTO;
import com.pay.inf.dao.Page;

/**
 * 出款业务服务接口
 * 
 * @author Sunsea.Li
 * 
 */
public interface FoBusinessService {

	/**
	 * 创建出款业务
	 * 
	 */
	public Map<String,Object> createFoBusiness(FundoutBusinessDTO foBusiness);
	

	/**
	 * 查询出款业务列表
	 * @param businessId
	 * @return
	 */
	public Map<String,Object> queryFoBusinessInfo(Page<FundoutBusinessDTO> resultPage,FundoutBusinessDTO foBusiness);
	
	/**
	 * 更新出款业务信息
	 * @param foBusiness
	 * @return
	 */
	public int updateFoBusinessInfo (FundoutBusinessDTO foBusiness);
	/**
	 * 查询出款业务信息
	 * @param businessId
	 * @return
	 */
	public FundoutBusinessDTO queryFoBusinessInfoById(Long businessId) ;
	
	/**
	 * 
	 * @param businessCode
	 * @return
	 */
	public FundoutBusinessDTO queryFoBusinessInfoByCode(String businessCode);
	
}
