package com.pay.rm.blackwhitelist.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.inf.exception.AppUnTxException;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListApprovalDto;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListDto;

/**
 * @Description
 * @project ma-membermanager
 * @file BlackWhiteListService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2011-1-18 tianqing_wang Create
 */

public interface BlackWhiteListApprovalService {


	/**
	 * 创建黑白名单审核
	 * 
	 * @param BlackWhiteListDto
	 * @return Long
	 */
	public Long createBlackWhiteListApproval(BlackWhiteListApprovalDto dto);

	public boolean createBlackWhiteListApproval(List<BlackWhiteListApprovalDto> dtos);

	/**
	 * 查询黑白名单审核列表
	 * 
	 * @param BlackWhiteListDto
	 * @return List<BlackWhiteListDto>
	 */
	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApprovalPage(BlackWhiteListApprovalDto dto,
			Page<BlackWhiteListApprovalDto> page);
	
	/**
	 * 根据业务类型查询会员的黑白名单详情,查询不到或入参错误返回NULL
	 * 
	 * @param memberCode
	 *            会员号
	 * @param businessId
	 *            业务类型ID
	 * @return
	 */
	public List<BlackWhiteListApprovalDto> queryMemberWhiteOrBlackListApproval(Long memberCode,
			int businessId) throws AppUnTxException;

	/**
	 * 统计黑白名单列表
	 * 
	 * @param blackWhiteList
	 * @return Integer
	 */
	public Integer countBlackWhiteListApproval(BlackWhiteListApprovalDto dto);

	/**
	 * 黑白名单审核修改
	 * 
	 * @param blackWhiteListDto
	 * @return Integer
	 */
	public Integer updateBlackWhiteListApproval(BlackWhiteListApprovalDto dto);

	/**
	 * 删除黑白名单审核
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

	/**
	 * 模糊查询
	 * 
	 * @param content
	 * @return
	 */
	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApproval(Integer businessTypeId,
			Integer listType);

	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApproval(BlackWhiteListApprovalDto dto);
	
	List<BlackWhiteListApprovalDto> queryAllBlackWhiteListApproval();
	
	List<BlackWhiteListApprovalDto> queryBlackWhiteListApprovalAll(BlackWhiteListApprovalDto dto);
	List<BlackWhiteListApprovalDto> queryBlackWhiteListApprovalCheck(BlackWhiteListApprovalDto dto);
	
	int countAmount(BlackWhiteListApprovalDto dto);

	Integer checkInDatabase(BlackWhiteListApprovalDto dto);
}
