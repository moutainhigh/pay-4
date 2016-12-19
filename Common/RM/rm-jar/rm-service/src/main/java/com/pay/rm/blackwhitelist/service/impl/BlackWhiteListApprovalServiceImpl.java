package com.pay.rm.blackwhitelist.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.exception.AppUnTxException;
import com.pay.rm.blackwhitelist.dao.BlackWhiteListApprovalDAO;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListApprovalDto;
import com.pay.rm.blackwhitelist.service.BlackWhiteListApprovalService;
import com.pay.util.BeanConvertUtil;

/**
 * @Description
 * @project ma-membermanager
 * @file BlackWhiteListServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2011-1-18 tianqing_wang Create
 */
public class BlackWhiteListApprovalServiceImpl implements BlackWhiteListApprovalService {

	private Log log = LogFactory.getLog(BlackWhiteListApprovalServiceImpl.class);
	private BlackWhiteListApprovalDAO blackWhiteListApprovalDAO;
	
	public void setBlackWhiteListApprovalDAO(
			BlackWhiteListApprovalDAO blackWhiteListApprovalDAO) {
		this.blackWhiteListApprovalDAO = blackWhiteListApprovalDAO;
	}

	public Long createBlackWhiteList(BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.createBlackWhiteListApproval(BeanConvertUtil.convert(
				BlackWhiteListApprovalDto.class, dto));
	}

	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApprovalPage(BlackWhiteListApprovalDto dto,
			Page<BlackWhiteListApprovalDto> page) {
		Integer totalCount = blackWhiteListApprovalDAO.countBlackWhiteListApproval(dto);
		BlackWhiteListApprovalDto paramDto = setPage(dto, page, totalCount);
		if (null == paramDto)
			return null;
		return blackWhiteListApprovalDAO.queryBlackWhiteListApproval(paramDto);
	}
	public List<BlackWhiteListApprovalDto> queryBlackWhiteList(BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.queryBlackWhiteListApprovalAll(dto);
	}

	public List<BlackWhiteListApprovalDto> queryMemberWhiteOrBlackList(Long memberCode,
			int businessId) throws AppUnTxException {
		List<BlackWhiteListApprovalDto> list = null;
		if (memberCode == null || businessId == 0) {
			log.error("#查询会员黑白名单详情时入参不正确#");
		} else {
			log.info("#准备查询会员黑白名单#");
			list = new ArrayList<BlackWhiteListApprovalDto>();

			List<BlackWhiteListApprovalDto> blackWhiteList = queryMemberWhiteOrBlackList(
					memberCode, businessId);
			for (int i = 0; i < blackWhiteList.size(); i++) {
				BlackWhiteListApprovalDto bwList = blackWhiteList.get(i);
				BlackWhiteListApprovalDto dto = new BlackWhiteListApprovalDto();
				dto.setId(bwList.getId());
				dto.setListType(bwList.getListType());
				dto.setMemberCode(bwList.getMemberCode());
				dto.setStatus(bwList.getStatus());
				dto.setBusinessTypeId(bwList.getBusinessTypeId());
				dto.setCreateDate(bwList.getCreateDate());
				list.add(dto);
			}

			log.info("#查询会员黑白名单结束#");
		}
		return list;
	}

	public BlackWhiteListApprovalDto queryBlackWhiteById(BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.queryBlackWhiteListApprovalById(dto);
	}

	public Integer countBlackWhiteList(BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.countBlackWhiteListApproval(dto);
	}

	public Integer updateBlackWhiteList(BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.updateBlackWhiteListApproval(dto);
	}

	public Integer deleteBlackWhiteList(BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.deleteBlackWhiteListApproval(dto);
	}

	private BlackWhiteListApprovalDto setPage(BlackWhiteListApprovalDto dto, 
			Page<BlackWhiteListApprovalDto> page,
			Integer totalCount) {
		Integer pageStartRow;// 起始行
		Integer pageEndRow;// 结束行
		if (null == page || totalCount == 0) {
			return null;
		}
		page.setTotalCount(totalCount);
		pageEndRow = page.getPageNo() * page.getPageSize();
		if ((page.getPageNo() - 1) == 0) {
			pageStartRow = 0;
		} else {
			pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
		}
		dto.setPageStartRow(pageStartRow);
		dto.setPageEndRow(pageEndRow);
		return dto;
	}

	@Override
	public boolean createBlackWhiteListApproval(List<BlackWhiteListApprovalDto> dtos) {

		for (BlackWhiteListApprovalDto dto : dtos) {
			BlackWhiteListApprovalDto blackWhiteList = new BlackWhiteListApprovalDto();
			blackWhiteList.setContent(dto.getContent());
			//changed by zhaoyang at 20161017,
			//because There is no statement named ma-blackwhitelist-approval.queryBlackWhiteListByCriteria in this SqlMap.
//			BlackWhiteListApprovalDto blackWhiteListDto = (BlackWhiteListApprovalDto) blackWhiteListApprovalDAO
//					.findObjectByCriteria("queryBlackWhiteListByCriteria",blackWhiteList);
			BlackWhiteListApprovalDto blackWhiteListDto = (BlackWhiteListApprovalDto) blackWhiteListApprovalDAO
					.findObjectByCriteria("queryBlackWhiteListApprovalByCriteria",blackWhiteList);
			if (null != blackWhiteListDto) {
				blackWhiteListDto.setBusinessTypeId(dto.getBusinessTypeId());
				blackWhiteListDto.setListType(dto.getListType());
				blackWhiteListDto.setPartType(dto.getPartType());
				blackWhiteListDto.setStatus(dto.getStatus());
				blackWhiteListApprovalDAO.updateBlackWhiteListApproval(blackWhiteListDto);
			} else {
				blackWhiteListApprovalDAO.createBlackWhiteListApproval(dto);
			}
		}

		return true;
	}

	@Override
	public BlackWhiteListApprovalDto queryBlackWhiteListApprovalByContent(String content) {
		BlackWhiteListApprovalDto blackWhiteListDto = blackWhiteListApprovalDAO
				.queryBlackWhiteListApprovalByContent(content);
		return blackWhiteListDto;
	}

	@Override
	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApproval(Integer businessTypeId,
			Integer listType) {
		List<BlackWhiteListApprovalDto> blackWhiteListDtos = blackWhiteListApprovalDAO
				.queryBlackWhiteListApproval(businessTypeId, listType);
		return blackWhiteListDtos;
	}
	
	@Override
	public List<BlackWhiteListApprovalDto> queryAllBlackWhiteListApproval() {
		List<BlackWhiteListApprovalDto> blackWhiteListDtos = blackWhiteListApprovalDAO
				.queryBlackWhiteListApproval(null,null);
		return blackWhiteListDtos;
	}

	@Override
	public Long createBlackWhiteListApproval(BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.createBlackWhiteListApproval(dto);
	}

	@Override
	public List<BlackWhiteListApprovalDto> queryMemberWhiteOrBlackListApproval(
			Long memberCode, int businessId) throws AppUnTxException {
		return null;
	}

	@Override
	public Integer countBlackWhiteListApproval(BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.countBlackWhiteListApproval(dto);
	}

	@Override
	public Integer updateBlackWhiteListApproval(BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.updateBlackWhiteListApproval(dto);
	}

	@Override
	public Integer deleteBlackWhiteListApproval(BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.deleteBlackWhiteListApproval(dto);
	}

	@Override
	public BlackWhiteListApprovalDto queryBlackWhiteListApprovalById(
			BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.queryBlackWhiteListApprovalById(dto);
	}

	@Override
	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApproval(
			BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.queryBlackWhiteListApproval(dto);
	}

	@Override
	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApprovalAll(
			BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.queryBlackWhiteListApprovalAll(dto);
	}

	@Override
	public List<BlackWhiteListApprovalDto> queryBlackWhiteListApprovalCheck(
			BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.queryBlackWhiteListApprovalCheck(dto);
	}

	@Override
	public int countAmount(BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.countBlackWhiteListApproval(dto);
	}

	@Override
	public Integer checkInDatabase(BlackWhiteListApprovalDto dto) {
		return blackWhiteListApprovalDAO.checkInDatabase(dto);
	}
}
