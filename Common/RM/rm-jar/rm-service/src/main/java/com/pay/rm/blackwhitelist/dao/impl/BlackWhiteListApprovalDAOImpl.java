package com.pay.rm.blackwhitelist.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.rm.blackwhitelist.dao.BlackWhiteListApprovalDAO;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListApprovalDto;

/**
 * @Description
 * @project ma-membermanager
 * @file BlackWhiteListDAO.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2011-1-18 tianqing_wang Create
 */
@SuppressWarnings({ "unchecked", "deprecation" })
public class BlackWhiteListApprovalDAOImpl extends BaseDAOImpl<BlackWhiteListApprovalDto> implements
		BlackWhiteListApprovalDAO {

	public Long createBlackWhiteListApproval(BlackWhiteListApprovalDto model) {
		return (Long) super.create(model);
	}
	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApproval(BlackWhiteListApprovalDto dto) {
		
		logger.info("dto: "+dto);
		return this.getSqlMapClientTemplate()
				.queryForList("ma-blackwhitelist-approval.queryBlackWhiteListApproval",dto);
	}

	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApprovalAll(BlackWhiteListApprovalDto dto) {
		return this.getSqlMapClientTemplate()
				.queryForList("ma-blackwhitelist-approval.queryBlackWhiteListApprovalByCriteria",dto);
	}

	public Integer countBlackWhiteListApproval(BlackWhiteListApprovalDto dto) {
		return (Integer) this.getSqlMapClientTemplate()
				.queryForObject("ma-blackwhitelist-approval.countBlackWhiteListApproval",dto);
	}


	public Integer updateBlackWhiteListApproval(BlackWhiteListApprovalDto dto) {
		Integer i = (Integer) this.getSqlMapClientTemplate()
				.update("ma-blackwhitelist-approval.updateBlackWhiteListApproval",dto);
		return i;
	}

	public Integer deleteBlackWhiteListApproval(BlackWhiteListApprovalDto dto) {
		return (Integer) this.getSqlMapClientTemplate()
				.delete("ma-blackwhitelist-approval.deleteBlackWhiteListApproval",dto);
	}
	public BlackWhiteListApprovalDto queryBlackWhiteListApprovalById(BlackWhiteListApprovalDto dto) {
		return (BlackWhiteListApprovalDto) this.getSqlMapClientTemplate()
				.queryForObject("ma-blackwhitelist.queryBlackWhiteListApprovalById",dto);
	}
	@Override
	public boolean createBlackWhiteListApproval(List<BlackWhiteListApprovalDto> models) {
		List<Object> ids = super.batchCreate("createBlackWhiteListApproval", models);
		return models.size() == ids.size();
	}
	@Override
	public BlackWhiteListApprovalDto queryBlackWhiteListApprovalByContent(String content) {
		BlackWhiteListApprovalDto blackWhiteList = new BlackWhiteListApprovalDto();
		blackWhiteList.setContent(content);
		BlackWhiteListApprovalDto blackWhiteListDto = (BlackWhiteListApprovalDto) super
				.findObjectByCriteria("queryBlackWhiteListApprovalByCriteria",blackWhiteList);
		return blackWhiteListDto;
	}
	@Override
	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApproval(Integer businessTypeId,
			Integer listType) {
		BlackWhiteListApprovalDto blackWhiteListDto = new BlackWhiteListApprovalDto();
		if(businessTypeId!=null){
			blackWhiteListDto.setBusinessTypeId(businessTypeId);
		}
		if(listType!=null){
			blackWhiteListDto.setListType(listType);
		}	
		List<BlackWhiteListApprovalDto> blackWhiteListDtos = super.findByCriteria(
				"queryBlackWhiteListApprovalByBuIDAndListType", blackWhiteListDto);
		return blackWhiteListDtos;
	}
	@Override
	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApprovalCheck(
			BlackWhiteListApprovalDto dto) {
		 return this.getSqlMapClientTemplate()
				.queryForList("ma-blackwhitelist-approval.queryBlackWhiteListApprovalCheck",dto);
	}

	@Override
	public Integer checkInDatabase(BlackWhiteListApprovalDto dto) {
		Integer i = (Integer) this.getSqlMapClientTemplate().queryForObject("ma-blackwhitelist-approval.checkInDatabase",dto);
		return i;
	}
}
