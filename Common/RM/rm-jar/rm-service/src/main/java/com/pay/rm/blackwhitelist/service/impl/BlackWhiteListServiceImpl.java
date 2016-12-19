package com.pay.rm.blackwhitelist.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.exception.AppUnTxException;
import com.pay.rm.blackwhitelist.dao.BlackWhiteListDAO;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListApprovalDto;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListDto;
import com.pay.rm.blackwhitelist.dto.BusinessTypeDto;
import com.pay.rm.blackwhitelist.model.BlackWhiteList;
import com.pay.rm.blackwhitelist.service.BlackWhiteListApprovalService;
import com.pay.rm.blackwhitelist.service.BlackWhiteListService;
import com.pay.util.BeanConvertUtil;
import com.pay.util.StringUtil;

/**
 * @Description
 * @project ma-membermanager
 * @file BlackWhiteListServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2011-1-18 tianqing_wang Create
 */
public class BlackWhiteListServiceImpl implements BlackWhiteListService {

	private Log log = LogFactory.getLog(BlackWhiteListServiceImpl.class);
	private BlackWhiteListDAO blackWhiteListDAO;
	private BlackWhiteListApprovalService blackWhiteListApprovalService;

	public Long createBusinessType(BusinessTypeDto dto) {
		return blackWhiteListDAO.createBusinessType(dto);
	}

	public Long createBlackWhiteList(BlackWhiteListDto dto) {
		
		if(!StringUtil.isEmpty(dto.getValue1())){
			dto.setContent(dto.getValue1());
		}
		if(!StringUtil.isEmpty(dto.getValue2())){
			dto.setContent(dto.getValue1()+" 至  "+dto.getValue2());
		}
		
		if(dto.getListType().intValue()==2){
			dto.setStatus(0);
			BlackWhiteListApprovalDto approvalDto_ = new BlackWhiteListApprovalDto();			
			approvalDto_.setApprovalType(1);
			approvalDto_.setPartType(dto.getPartType());
			approvalDto_.setContent(dto.getContent());
			approvalDto_.setBusinessTypeId(dto.getBusinessTypeId());
			approvalDto_.setStatus(0);
			
			List<BlackWhiteListApprovalDto> list01 = blackWhiteListApprovalService
					.queryBlackWhiteListApprovalCheck(approvalDto_);
			approvalDto_.setStatus(1);
			List<BlackWhiteListApprovalDto> list02 = blackWhiteListApprovalService
					.queryBlackWhiteListApprovalCheck(approvalDto_);
			
			if(list01!=null&&!list01.isEmpty()){
				return -3L;
			}
			
			if(list01!=null&&!list02.isEmpty()){
				BlackWhiteListDto dto_=blackWhiteListDAO.queryBlackWhiteByContent(dto.getContent());
				if(dto_!=null){
					return -3L;
				}
			}
			
			BlackWhiteListApprovalDto approvalDto = new BlackWhiteListApprovalDto();
			approvalDto = BeanConvertUtil.convert(
					BlackWhiteListApprovalDto.class,dto);
			approvalDto.setApprovalType(1);
			approvalDto.setCreateDate(new Date());
			approvalDto.setStatus(0);
			return blackWhiteListApprovalService.createBlackWhiteListApproval(approvalDto);
		}else{
			   BlackWhiteList model = BeanConvertUtil.convert(
					   BlackWhiteList.class, dto);
			   model.setCreateDate(new Date());
			   model.setStatus(1);
			   BlackWhiteListDto dto_=blackWhiteListDAO.queryBlackWhiteByContent(dto.getContent());
			   if(dto_!=null){
				   return -2L;
			   }
			   return blackWhiteListDAO.createBlackWhiteList(model);
		}
	}

	public List<BusinessTypeDto> queryBusinessTypeList(BusinessTypeDto dto,
			Page page) {
		Integer totalCount = blackWhiteListDAO.countBusinessTypeList(dto);
		BusinessTypeDto paramDto = setPage(dto, page, totalCount);
		if (null == paramDto)
			return null;
		return blackWhiteListDAO.queryBusinessTypeList(paramDto);
	}

	public Integer countBusinessTypeList(BusinessTypeDto dto) {
		return blackWhiteListDAO.countBusinessTypeList(dto);
	}

	@SuppressWarnings("unchecked")
	public List<BlackWhiteListDto> queryBlackWhiteList(BlackWhiteListDto dto,
			Page page) {
		Integer totalCount = blackWhiteListDAO.countBlackWhiteList(dto);
		BlackWhiteListDto paramDto = setPage(dto, page, totalCount);
		if (null == paramDto)
			return null;
		return blackWhiteListDAO.queryBlackWhiteList(paramDto);
	}

	@SuppressWarnings("unchecked")
	public List<BlackWhiteListDto> queryBlackWhiteList(BlackWhiteListDto dto) {
		return blackWhiteListDAO.queryBlackWhiteListAll(dto);
	}

	public List<BlackWhiteListDto> queryMemberWhiteOrBlackList(Long memberCode,
			int businessId) throws AppUnTxException {
		List<BlackWhiteListDto> list = null;
		if (memberCode == null || businessId == 0) {
			log.error("#查询会员黑白名单详情时入参不正确#");
		} else {
			log.info("#准备查询会员黑白名单#");
			list = new ArrayList<BlackWhiteListDto>();

			List<BlackWhiteListDto> blackWhiteList = queryMemberWhiteOrBlackList(
					memberCode, businessId);
			for (int i = 0; i < blackWhiteList.size(); i++) {
				BlackWhiteListDto bwList = blackWhiteList.get(i);
				BlackWhiteListDto dto = new BlackWhiteListDto();
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

	public BlackWhiteListDto queryBlackWhiteById(BlackWhiteListDto dto) {
		return blackWhiteListDAO.queryBlackWhiteById(dto);
	}

	public BusinessTypeDto queryBusinessTypeById(BusinessTypeDto dto) {
		return blackWhiteListDAO.queryBusinessTypeById(dto);
	}

	public Integer countBlackWhiteList(BlackWhiteListDto dto) {
		return blackWhiteListDAO.countBlackWhiteList(dto);
	}

	public Integer updateBusinessTypeList(BusinessTypeDto dto) {
		return blackWhiteListDAO.updateBusinessTypeList(dto);
	}

	public Integer updateBlackWhiteList(BlackWhiteListDto dto) {
		Long id = dto.getId();
		if(id!=null){
			if(dto.getListType()==2){
				BlackWhiteListDto dtoTmp = new BlackWhiteListDto();
				dtoTmp.setId(id);
				BlackWhiteListDto dto_ = blackWhiteListDAO.queryBlackWhiteById(dtoTmp);
				if(dto_!=null){
					BlackWhiteListApprovalDto approvalDto = new BlackWhiteListApprovalDto();
					approvalDto.setBlackWhiteListId(dto_.getId());
					approvalDto.setCreateDate(new Date());
					approvalDto.setApprovalType(3);
					approvalDto.setStatus(0);
					approvalDto.setOperator(dto.getOperator());
					approvalDto.setBusinessTypeId(dto.getBusinessTypeId());
					approvalDto.setPartType(dto.getPartType());
					approvalDto.setContent(dto.getContent());
					approvalDto.setRemark(dto.getRemark());
					approvalDto.setId(null);
					Long idTmp = blackWhiteListApprovalService.createBlackWhiteListApproval(approvalDto);
					
					if(idTmp!=null&&idTmp>0){
						return 1;
					}
				}
			}else if(dto.getListType()==1){//白名单直接更新
				dto.setStatus(1);
				return blackWhiteListDAO.updateBlackWhiteList(dto);
			}
		}
		return 0;
	}

	public Integer deleteBusinessType(BusinessTypeDto dto) {
		return blackWhiteListDAO.deleteBusinessType(dto);
	}

	public Integer deleteBlackWhiteList(BlackWhiteListDto dto) {
		Long id = dto.getId();
		
		if(id!=null){
			BlackWhiteListDto dtoTmp = new BlackWhiteListDto();
			dtoTmp.setId(id);
			BlackWhiteListDto dto_ = blackWhiteListDAO.queryBlackWhiteById(dtoTmp);
			
			if(dto_!=null&&dto_.getListType()==1){//如果是白名单直接删除
				return blackWhiteListDAO.deleteBlackWhiteList(dto_);
			}
			
			if(dto_!=null){
				BlackWhiteListApprovalDto approvalDto = new BlackWhiteListApprovalDto();
				approvalDto = BeanConvertUtil.convert(
						BlackWhiteListApprovalDto.class,dto_);
				approvalDto.setBlackWhiteListId(dto_.getId());
				approvalDto.setCreateDate(new Date());
				approvalDto.setApprovalType(2);
				approvalDto.setStatus(0);
				approvalDto.setRemark(dto.getRemark());
				Long idTmp = blackWhiteListApprovalService.createBlackWhiteListApproval(approvalDto);
				
				if(idTmp!=null&&idTmp>0){
					return 1;
				}
			}
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	private BusinessTypeDto setPage(BusinessTypeDto dto, Page page,
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

	@SuppressWarnings("unchecked")
	private BlackWhiteListDto setPage(BlackWhiteListDto dto, Page page,
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

	public BlackWhiteListDAO getBlackWhiteListDAO() {
		return blackWhiteListDAO;
	}

	public void setBlackWhiteListDAO(BlackWhiteListDAO blackWhiteListDAO) {
		this.blackWhiteListDAO = blackWhiteListDAO;
	}

	@Override//批量加入黑名单
	public boolean createBlackWhiteList(List<BlackWhiteListDto> dtos) {
		if(dtos==null||dtos.isEmpty()){
			return false;
		}
		for (BlackWhiteListDto dto : dtos) {
			dto.setStatus(0);
			dto.setCreateDate(new Date());
			if(!StringUtil.isEmpty(dto.getValue1())){
				dto.setContent(dto.getValue1());
			}
			if(!StringUtil.isEmpty(dto.getValue2())){
				dto.setContent(dto.getValue1()+" 至  "+dto.getValue2());
			}
			
			BlackWhiteListApprovalDto approvalDto_ = new BlackWhiteListApprovalDto();
			approvalDto_.setApprovalType(1);
			approvalDto_.setPartType(dto.getPartType());
			approvalDto_.setContent(dto.getContent());
			approvalDto_.setBusinessTypeId(dto.getBusinessTypeId());
			
			List<BlackWhiteListApprovalDto> list = blackWhiteListApprovalService
					.queryBlackWhiteListApprovalCheck(approvalDto_);
			if(list!=null&&!list.isEmpty()){
				if(dtos.size()==1){
					return false;
				}
				continue;
			}
			BlackWhiteListApprovalDto approvalDto = new BlackWhiteListApprovalDto();
			approvalDto = BeanConvertUtil.convert(
					BlackWhiteListApprovalDto.class,dto);
			approvalDto.setApprovalType(1);
			approvalDto.setCreateDate(new Date());
			approvalDto.setStatus(0);
			blackWhiteListApprovalService.createBlackWhiteListApproval(approvalDto);
		}
		return true;
	}

	@Override
	public BlackWhiteListDto queryBlackWhiteByContent(String content) {

		BlackWhiteListDto blackWhiteListDto = blackWhiteListDAO
				.queryBlackWhiteByContent(content);
		return blackWhiteListDto;
	}

	@Override
	public List<BlackWhiteListDto> queryBlackWhiteList(Integer businessTypeId,
			Integer listType) {
		List<BlackWhiteListDto> blackWhiteListDtos = blackWhiteListDAO
				.queryBlackWhiteList(businessTypeId, listType);
		return blackWhiteListDtos;
	}
	
	@Override
	public List<BlackWhiteListDto> queryAllBlackWhiteList() {
		List<BlackWhiteListDto> blackWhiteListDtos = blackWhiteListDAO
				.queryBlackWhiteList(null,null);
		return blackWhiteListDtos;
	}

	public BlackWhiteListApprovalService getBlackWhiteListApprovalService() {
		return blackWhiteListApprovalService;
	}

	public void setBlackWhiteListApprovalService(
			BlackWhiteListApprovalService blackWhiteListApprovalService) {
		this.blackWhiteListApprovalService = blackWhiteListApprovalService;
	}

	@Override
	public int approvalBlackWhiteList(String ids, String remark,String approvalFlg,String approvalUser) {
		if(!StringUtil.isEmpty(ids)){
		    String [] idArray = ids.split(",");
		    if(idArray!=null){
		    	int i=0;
		       for(String idStr:idArray){
		    	   Long id = Long.valueOf(idStr);
		    	   BlackWhiteListApprovalDto tmp = new BlackWhiteListApprovalDto();
		    	   tmp.setId(id);
		    	   
		    	   List<BlackWhiteListApprovalDto> list = blackWhiteListApprovalService
	                         .queryBlackWhiteListApprovalAll(tmp);
		    	   BlackWhiteListApprovalDto tmp0 = null;
		    	   if(list!=null&&!list.isEmpty()){
		    		   tmp0 = list.get(0);
		    	   }
		    	   if(tmp0!=null){
		    		   if("1".equals(approvalFlg)){
		    			   if(tmp0.getApprovalType().intValue()==1){//新增审核
		    				   BlackWhiteList model = BeanConvertUtil.convert(
		    						   BlackWhiteList.class, tmp0);
		    				   model.setCreateDate(new Date());
		    				   model.setStatus(1);//
		    				   Integer checkNums = blackWhiteListApprovalService.checkInDatabase(tmp0);
							   if(!(checkNums % 10 >  0)){//黑名单存在
								   model.setApprovalId(tmp0.getId());
								   blackWhiteListDAO.createBlackWhiteList(model);
							   }
		    			   }else if(tmp0.getApprovalType().intValue()==2){//删除审核
		    				   BlackWhiteListDto dto = new BlackWhiteListDto();
		    				   dto.setId(tmp0.getBlackWhiteListId());
		    				   dto.setApprovalId(tmp0.getId());
		    				   blackWhiteListDAO.deleteBlackWhiteList(dto);
		    			   }else if(tmp0.getApprovalType().intValue()==3){//修改审核
		    				   BlackWhiteListDto dto = BeanConvertUtil.convert(
		    						   BlackWhiteListDto.class, tmp0);
		    				   dto.setId(tmp0.getBlackWhiteListId());
		    				   dto.setUpdateDate(new Date());
		    				   dto.setApprovalId(tmp0.getId());
		    				   dto.setListType(tmp0.getListType());
							   Integer checkNums = blackWhiteListApprovalService.checkInDatabase(tmp0);
							   if(!(checkNums % 10 >  0)){//黑名单存在
								   dto.setStatus(1);
								   blackWhiteListDAO.updateBlackWhiteList(dto);
							   }
		    			   }
		    			   tmp0.setStatus(1);//审核通过
		    		   } else if("0".equals(approvalFlg)){
		    			   tmp0.setStatus(-1);//审核拒绝
		    		   }
		    		   tmp0.setRemark(remark);
		    		   tmp0.setApprovalUser(approvalUser);
		    		   tmp0.setOperator(tmp0.getOperator()+","+approvalUser);
		    		   tmp0.setUpdateDate(new Date());
		    		   blackWhiteListApprovalService.updateBlackWhiteListApproval(tmp0);
		    		   i+=1;
		    	   }
		       }
		       return 1;
		    }
		}
		return 0;
	}

	@Override
	public int updateBlackWhiteListStatus(BlackWhiteListDto dto) {
		return blackWhiteListDAO.updateBlackWhiteList(dto);
	}

}
