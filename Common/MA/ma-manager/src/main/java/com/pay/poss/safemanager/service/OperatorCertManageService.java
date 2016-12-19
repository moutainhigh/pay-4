package com.pay.poss.safemanager.service;

import java.util.Date;
import java.util.List;

import com.pay.poss.safemanager.dto.OperatorCertDto;
import com.pay.poss.safemanager.dto.OperatorCertStatDto;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		OperatorCertManageService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2011 pay Corporation. All rights reserved.
 * Date				Author				Changes
 * 2011-12-1			DaiDeRong			Create
 */
public interface OperatorCertManageService {

	
	/**
	 * 查询用户安装证书信息
	 * @param paramDto
	 * @return List<OperatorCertDto>
	 */
	public List<OperatorCertDto> queryOperatorCertInfo(OperatorCertDto paramDto);
	
	/**
	 * 查询 用户 证书使用情况
	 * @param memberCode
	 * @param operatorId
	 * @return OperatorCertDto
	 */
	public OperatorCertDto queryOperatorCertUseInfo(Long memberCode,Long operatorId);
	
	/**
	 *	删除某用户 证书
	 * @param certManageId
	 * @return true/false
	 */
	public boolean removeCert(Long certMemberId);
	
	
	/**
	 * 删除某使用地点证书
	 * @param certManageId
	 * @return true/false
	 */
	public boolean removeUesPalceCert(Long certManageId);
	
	/**
	 * 统计查询 证书使用情况
	 * @param begin
	 * @param end
	 * @return OperatorCertStatDto
	 */
	public OperatorCertStatDto queryCertStat(Date begin,Date end);
	
}
