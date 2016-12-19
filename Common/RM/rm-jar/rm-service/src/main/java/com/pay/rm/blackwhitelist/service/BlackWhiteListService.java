package com.pay.rm.blackwhitelist.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.exception.AppUnTxException;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListDto;
import com.pay.rm.blackwhitelist.dto.BusinessTypeDto;

/**
 * @Description
 * @project ma-membermanager
 * @file BlackWhiteListService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2011-1-18 tianqing_wang Create
 */

public interface BlackWhiteListService {

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
	public Long createBlackWhiteList(BlackWhiteListDto dto);

	public boolean createBlackWhiteList(List<BlackWhiteListDto> dtos);

	/**
	 * 查询黑白名单类型
	 * 
	 * @param BusinessTypeDto
	 * @return List<BusinessTypeDto>
	 */
	public List<BusinessTypeDto> queryBusinessTypeList(BusinessTypeDto dto,
			Page page);

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
	public List<BlackWhiteListDto> queryBlackWhiteList(BlackWhiteListDto dto,
			Page page);
	
	/**
	 * 根据业务类型查询会员的黑白名单详情,查询不到或入参错误返回NULL
	 * 
	 * @param memberCode
	 *            会员号
	 * @param businessId
	 *            业务类型ID
	 * @return
	 */
	public List<BlackWhiteListDto> queryMemberWhiteOrBlackList(Long memberCode,
			int businessId) throws AppUnTxException;

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

	/**
	 * 模糊查询
	 * 
	 * @param content
	 * @return
	 */
	public List<BlackWhiteListDto> queryBlackWhiteList(Integer businessTypeId,
			Integer listType);

	public List<BlackWhiteListDto> queryBlackWhiteList(BlackWhiteListDto dto);
	
	List<BlackWhiteListDto> queryAllBlackWhiteList();
	
	BlackWhiteListApprovalService getBlackWhiteListApprovalService();
	
	int  approvalBlackWhiteList(String ids,String remark,String approvalFlg,String approvalUser);
	
	int updateBlackWhiteListStatus(BlackWhiteListDto dto);
}
