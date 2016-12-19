package com.pay.rm.blackwhitelist.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListDto;
import com.pay.rm.blackwhitelist.dto.BusinessTypeDto;
import com.pay.rm.blackwhitelist.model.BlackWhiteList;

/**
 * @Description
 * @project ma-membermanager
 * @file BlackWhiteListDAO.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2011-1-18 tianqing_wang Create
 */
public interface BlackWhiteListDAO extends BaseDAO {

	/**
	 * 创建黑白名单类型
	 * 
	 * @param BusinessTypeDto
	 * @return Long
	 */
	public Long createBusinessType(BusinessTypeDto dto);

	/**
	 * 创建黑白名单
	 * 
	 * @param BlackWhiteListDto
	 * @return Long
	 */
	public Long createBlackWhiteList(BlackWhiteList model);

	public boolean createBlackWhiteList(List<BlackWhiteList> models);

	/**
	 * 查询黑白名单类型
	 * 
	 * @param BusinessTypeDto
	 * @return List<BusinessTypeDto>
	 */
	public List<BusinessTypeDto> queryBusinessTypeList(BusinessTypeDto dto);

	/**
	 * 统计黑白名单类型
	 * 
	 * @param businessType
	 * @return Integer
	 */
	public Integer countBusinessTypeList(BusinessTypeDto dto);

	/**
	 * 查询黑白名单列表
	 * 
	 * @param BlackWhiteListDto
	 * @return List<BlackWhiteListDto>
	 */
	public List<BlackWhiteListDto> queryBlackWhiteList(BlackWhiteListDto dto);

	/**
	 * 统计黑白名单列表
	 * 
	 * @param blackWhiteList
	 * @return Integer
	 */
	public Integer countBlackWhiteList(BlackWhiteListDto dto);

	/**
	 * 黑白名单类型修改
	 * 
	 * @param businessTypeDto
	 * @return Integer
	 */
	public Integer updateBusinessTypeList(BusinessTypeDto dto);

	/**
	 * 黑白名单修改
	 * 
	 * @param blackWhiteListDto
	 * @return Integer
	 */
	public Integer updateBlackWhiteList(BlackWhiteListDto dto);

	/**
	 * 删除黑白名单类型
	 * 
	 * @param BusinessTypeDto
	 * @return Integer
	 */
	public Integer deleteBusinessType(BusinessTypeDto dto);

	/**
	 * 删除黑白名单
	 * 
	 * @param BlackWhiteListDto
	 * @return Integer
	 */
	public Integer deleteBlackWhiteList(BlackWhiteListDto dto);

	/**
	 * 由id查询BusinessTypeDto
	 * 
	 * @param BlackWhiteListDto
	 * @return BusinessTypeDto
	 */
	public BusinessTypeDto queryBusinessTypeById(BusinessTypeDto dto);

	/**
	 * 由id查询BlackWhiteListDto
	 * 
	 * @param BlackWhiteListDto
	 * @return BlackWhiteListDto
	 */
	public BlackWhiteListDto queryBlackWhiteById(BlackWhiteListDto dto);

	public BlackWhiteListDto queryBlackWhiteByContent(String content);

	public List<BlackWhiteListDto> queryBlackWhiteList(Integer businessTypeId,
			Integer listType);

	public List<BlackWhiteListDto> queryBlackWhiteListAll(BlackWhiteListDto dto);
	
	public List<BlackWhiteListDto> queryBlackWhiteList_(Map<String,Object> param);
	public List<BlackWhiteListDto> queryBlackWhiteList_(Map<String,Object> param,Page<BlackWhiteListDto> page);

}
