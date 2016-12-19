/**
 * 
 */
package com.pay.poss.yk.service.impl;

import org.springframework.beans.BeanUtils;

import com.pay.inf.dao.Page;
import com.pay.poss.yk.dao.IExternalLogDao;
import com.pay.poss.yk.dto.ExternalLogDto;
import com.pay.poss.yk.dto.ExternalLogSearchDto;
import com.pay.poss.yk.service.IYkCompensateService;

/**
 * @Description
 * @project ma-manager
 * @file YkCompensateServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 woyo Corporation. All rights reserved. Date
 *          Author Changes 2011-6-2 DDR Create
 */
public class YkCompensateServiceImpl implements IYkCompensateService {

	private IExternalLogDao externalLogDao;

	//private ExternalLogService externalLogService;

	public void setExternalLogDao(IExternalLogDao externalLogDao) {
		this.externalLogDao = externalLogDao;
	}

//	public void setExternalLogService(ExternalLogService externalLogService) {
//		this.externalLogService = externalLogService;
//	}

	@Override
	public Page<ExternalLogDto> search(Page<ExternalLogDto> paramPage,
			ExternalLogSearchDto dto) {
		return externalLogDao.findPage(paramPage, dto);
	}

	@Override
	public void aplayRefund(String orderNo) {
		//externalLogService.ykRechargeRefundImmediate(orderNo);
	}

	@Override
	public ExternalLogDto findExternalLogByOrderNo(String orderNo) {
//		ExternalLog model = externalLogService.findByOrderNo(orderNo);
		ExternalLogDto dto = new ExternalLogDto();
		//BeanUtils.copyProperties(model, dto);
		return dto;
	}

}
