/**
 * 
 */
package com.pay.poss.safemanager.dao;

import java.util.Date;
import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.safemanager.dto.OperatorCertDto;
import com.pay.poss.safemanager.dto.OperatorCertStatDto;
import com.pay.poss.safemanager.dto.OperatorCertUseDto;

/**
 * @Description 
 * @project 	ma-manager
 * @file 		CertManageDao.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright 2006-2010 woyo Corporation. All rights reserved.
 * Date					Author				Changes
 * 2011-11-22			DaiDeRong			Create
 */
public interface OperatorCertManageDao extends BaseDAO{
	/**
	 * 查询操作员安装的证书情况
	 * @param certDto
	 * @return List<OperatorCertDto>
	 */
	List<OperatorCertDto> queryOperatorCertInfo(OperatorCertDto certDto);
	
	/**
	 * 
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	List<OperatorCertUseDto> queryOperatorCertUseInfo(Long memberCode,Long operatorId);
	
	/**
	 * 更新某证书状态 
	 * @param id 用户 证书的关联id t_member_cert
	 * @return 更新个数
	 */
	int updateCertStatus(Long id,int status);
	
	/**
	 * 根据某证书id号更新所有对应使用地方的证书状态,一般是删除的时候用
	 * @param memberCertId t_member_cert id
	 * @param status t_cert_manage的状态 
	 * @return 更新个数
	 */
	int updateUseStatusByCertId(Long memberCertId,int status);
	
	/**
	 * 更新使用地方的证书状态 t_cert_manage
	 * @param id 证书管理的id号
	 * @param status
	 * @return 更新个数
	 */
	int updateUsePlaceStatus(Long id,int status);
	
	/**
	 * 根据时间 统计
	 * @param begin
	 * @param end
	 * @return OperatorCertStatDto
	 */
	public OperatorCertStatDto queryCertCountStat(Date begin,Date end);
}
