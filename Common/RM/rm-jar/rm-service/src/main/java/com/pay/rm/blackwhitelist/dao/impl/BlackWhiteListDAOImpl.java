package com.pay.rm.blackwhitelist.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.rm.blackwhitelist.dao.BlackWhiteListDAO;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListDto;
import com.pay.rm.blackwhitelist.dto.BusinessTypeDto;
import com.pay.rm.blackwhitelist.model.BlackWhiteList;
import com.pay.rm.blackwhitelist.model.BusinessType;
import com.pay.util.BeanConvertUtil;

/**
 * @Description
 * @project ma-membermanager
 * @file BlackWhiteListDAO.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2011-1-18 tianqing_wang Create
 */

@SuppressWarnings("unchecked")
public class BlackWhiteListDAOImpl extends BaseDAOImpl implements
		BlackWhiteListDAO {

	public Long createBusinessType(BusinessTypeDto dto) {
		Long id = (Long) this.getSqlMapClientTemplate().insert(
				"ma-blackwhitelist.createBusinessType",
				BeanConvertUtil.convert(BusinessType.class, dto));
		return id;
	}

	public Long createBlackWhiteList(BlackWhiteList model) {
		return (Long) super.create(model);
	}

	public List<BusinessTypeDto> queryBusinessTypeList(BusinessTypeDto dto) {
		return this.getSqlMapClientTemplate().queryForList(
				"ma-blackwhitelist.queryBusinessTypeList",
				BeanConvertUtil.convert(BusinessType.class, dto));
	}

	public Integer countBusinessTypeList(BusinessTypeDto dto) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"ma-blackwhitelist.countBusinessTypeList",
				BeanConvertUtil.convert(BusinessType.class, dto));
	}

	public List<BlackWhiteListDto> queryBlackWhiteList(BlackWhiteListDto dto) {
		return this.getSqlMapClientTemplate().queryForList(
				"ma-blackwhitelist.queryBlackWhiteList",
				BeanConvertUtil.convert(BlackWhiteList.class, dto));
	}

	public List<BlackWhiteListDto> queryBlackWhiteListAll(BlackWhiteListDto dto) {
		return this.getSqlMapClientTemplate().queryForList(
				"ma-blackwhitelist.queryBlackWhiteListByCriteria",
				BeanConvertUtil.convert(BlackWhiteList.class, dto));
	}

	public Integer countBlackWhiteList(BlackWhiteListDto dto) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"ma-blackwhitelist.countBlackWhiteList",
				BeanConvertUtil.convert(BlackWhiteList.class, dto));
	}

	public Integer updateBusinessTypeList(BusinessTypeDto dto) {
		Integer i = (Integer) this.getSqlMapClientTemplate().update(
				"ma-blackwhitelist.updateBusinessTypeList",
				BeanConvertUtil.convert(BusinessType.class, dto));
		return i;
	}

	public Integer updateBlackWhiteList(BlackWhiteListDto dto) {
		Integer i = (Integer) this.getSqlMapClientTemplate().update(
				"ma-blackwhitelist.updateBlackWhiteList",
				BeanConvertUtil.convert(BlackWhiteList.class, dto));
		return i;
	}

	public Integer deleteBusinessType(BusinessTypeDto dto) {
		return (Integer) this.getSqlMapClientTemplate().delete(
				"ma-blackwhitelist.deleteBusinessType",
				BeanConvertUtil.convert(BusinessType.class, dto));
	}

	public Integer deleteBlackWhiteList(BlackWhiteListDto dto) {
		return (Integer) this.getSqlMapClientTemplate().delete(
				"ma-blackwhitelist.deleteBlackWhiteList",
				BeanConvertUtil.convert(BlackWhiteList.class, dto));
	}

	public BusinessTypeDto queryBusinessTypeById(BusinessTypeDto dto) {
		return (BusinessTypeDto) this.getSqlMapClientTemplate().queryForObject(
				"ma-blackwhitelist.queryBusinessTypeById",
				BeanConvertUtil.convert(BusinessType.class, dto));
	}

	public BlackWhiteListDto queryBlackWhiteById(BlackWhiteListDto dto) {
		return (BlackWhiteListDto) this.getSqlMapClientTemplate()
				.queryForObject("ma-blackwhitelist.queryBlackWhiteById",
						BeanConvertUtil.convert(BlackWhiteList.class, dto));
	}

	@Override
	public boolean createBlackWhiteList(List<BlackWhiteList> models) {

		List<Object> ids = super.batchCreate("createBlackWhiteList", models);
		return models.size() == ids.size();
	}

	@Override
	public BlackWhiteListDto queryBlackWhiteByContent(String content) {
		BlackWhiteList blackWhiteList = new BlackWhiteList();
		blackWhiteList.setContent(content);
		BlackWhiteListDto blackWhiteListDto = (BlackWhiteListDto) super
				.findObjectByCriteria("queryBlackWhiteListByCriteria",
						blackWhiteList);
		return blackWhiteListDto;
	}

	@Override
	public List<BlackWhiteListDto> queryBlackWhiteList(Integer businessTypeId,
			Integer listType) {

		BlackWhiteListDto blackWhiteListDto = new BlackWhiteListDto();
		
		if(businessTypeId!=null){
			blackWhiteListDto.setBusinessTypeId(businessTypeId);
		}
		
		if(listType!=null){
			blackWhiteListDto.setListType(listType);
		}
		
		List<BlackWhiteListDto> blackWhiteListDtos = super.findByCriteria(
				"queryBlackWhiteListByBuIDAndListType", blackWhiteListDto);
		return blackWhiteListDtos;
	}

	@Override 
	public List<BlackWhiteListDto> queryBlackWhiteList_(
			Map<String, Object> param) {
		return super.findByCriteria("",param);
	}

	@Override
	public List<BlackWhiteListDto> queryBlackWhiteList_(
			Map<String, Object> param, Page<BlackWhiteListDto> page) {
		return super.findByCriteria("",param, page);
	}


}
