package com.pay.rm.blackwhitelist.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListApprovalDto;
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
public interface BlackWhiteListApprovalDAO extends BaseDAO<BlackWhiteListApprovalDto> {

	/**
	 * 创建黑白名单
	 * 
	 * @param BlackWhiteListDto
	 * @return Long
	 */
	public Long createBlackWhiteListApproval(BlackWhiteListApprovalDto model);

	public boolean createBlackWhiteListApproval(List<BlackWhiteListApprovalDto> models);

	/**
	 * 查询黑白名单列表
	 * 
	 * @param BlackWhiteListDto
	 * @return List<BlackWhiteListDto>
	 */
	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApproval(BlackWhiteListApprovalDto dto);

	/**
	 * 统计黑白名单列表
	 * 
	 * @param blackWhiteList
	 * @return Integer
	 */
	public Integer countBlackWhiteListApproval(BlackWhiteListApprovalDto dto);

	/**
	 * 黑白名单修改
	 * 
	 * @param blackWhiteListDto
	 * @return Integer
	 */
	public Integer updateBlackWhiteListApproval(BlackWhiteListApprovalDto dto);


	/**
	 * 删除黑白名单
	 * 
	 * @param BlackWhiteListDto
	 * @return Integer
	 */
	public Integer deleteBlackWhiteListApproval(BlackWhiteListApprovalDto dto);


	/**
	 * 由id查询BlackWhiteListDto
	 * 
	 * @param BlackWhiteListDto
	 * @return BlackWhiteListDto
	 */
	public BlackWhiteListApprovalDto queryBlackWhiteListApprovalById(BlackWhiteListApprovalDto dto);

	public BlackWhiteListApprovalDto queryBlackWhiteListApprovalByContent(String content);

	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApproval(Integer businessTypeId,
			Integer listType);

	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApprovalAll(BlackWhiteListApprovalDto dto);
	
	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApprovalCheck(BlackWhiteListApprovalDto dto);

	Integer checkInDatabase(BlackWhiteListApprovalDto dto);

}
