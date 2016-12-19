/**
 *  File: FoModeService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-28      Sunsea.Li      Changes
 *
 */
package com.pay.fundout.channel.service.mode;

import java.util.Map;

import com.pay.fundout.channel.dto.mode.FundoutModeDTO;
import com.pay.inf.dao.Page;

/**出款方式服务接口
 * @author Sunsea.Li
 *
 */
public interface FoModeService {
	/**创建出款方式
	 * @param fundoutMode
	 * @return
	 */
	public Map<String,Object> createFoMode(FundoutModeDTO fundoutModeDTO);
	
	/**查询出款方式列表
	 * @param resultPage
	 * @param fundoutMode
	 * @return
	 */
	public Map<String,Object> queryFoModeInfo(Page<FundoutModeDTO> resultPage,FundoutModeDTO fundoutModeDTO);
	
	/**
	 * 更新出款业务信息
	 * @param foBusiness
	 * @return
	 */
	public int updateFoModeInfo (FundoutModeDTO fundoutMode);
	/**
	 * 查询出款业务信息
	 * @param businessId
	 * @return
	 */
	public FundoutModeDTO queryFoModeInfoById(Long modeId) ;
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public FundoutModeDTO queryFoModeInfoByCode(String code) ;
	
}
