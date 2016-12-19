/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.acc.service.cert.impl;

import javax.naming.directory.SearchResult;

import com.pay.acc.cert.model.CertManage;
import com.pay.acc.cert.model.MemberCert;
import com.pay.acc.cert.service.CertManageService;
import com.pay.acc.cert.service.MemberCertService;
import com.pay.acc.service.cert.CertQueryService;
import com.pay.acc.service.cert.dto.CertManageDto;
import com.pay.pe.dto.BeanUtils;

/**
 * @author fjl
 * @date 2011-11-16
 */
public class CertQueryServiceImpl implements CertQueryService {

	private MemberCertService memberCertService;
	
	//private CFCACertService cfcaCertService;
	
	private CertManageService certManageService;
	

	@Override
	public boolean isCertUser(Long memberCode,Long operatorId){
		
		try {
			//查询本地库中是否存在用户证书关联,并且状态为有效
			MemberCert cert =  memberCertService.queryMemberCert(memberCode, operatorId);
			if(cert != null && MemberCert.StatusEnum.VALID.getValue() == cert.getStatus().intValue()){
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	

//	@Override
//	public CerStatusEnum queryUserCertStatus(Long memberCode,Long operatorId){
//		//查询本地获取证书
//		MemberCert cert =  memberCertService.queryMemberCert(memberCode, operatorId);
//		if(cert != null){
//		//查询CFCA服务器
//			SearchResult result = cfcaCertService.searchCerByRefNo(cert.getRefNo());
//			if(result != null){
//				return CerStatusEnum.getByCode(result.getCertStatusCode());
//			}
//		}
//		return null;
//	}
	
	
	@Override
	public Integer queryUserCertLocalStatus(Long memberCode, Long operatorId) {
		//查询本地获取证书
		MemberCert cert =  memberCertService.queryMemberCert(memberCode, operatorId);
		if(cert != null){
			return cert.getStatus().intValue();
		}
		return null;
	}


	@Override
	public boolean isValidCertUser(Long memberCode,Long operatorId){
		
		Integer status = queryUserCertLocalStatus(memberCode, operatorId);
		if(status != null){
			//检查本地
			if(MemberCert.StatusEnum.VALID.getValue() == status.intValue()){
				//检查服务器
				//CerStatusEnum certStatus = queryUserCertStatus(memberCode, operatorId);
//				if(certStatus != null && certStatus.getCode() == CerStatusEnum.ACTIVATION.getCode()){
//					return true;
//				}
			}
		}
		
		return false;
	}


	@Override
	public CertManageDto queryUsePalce(Long memberCode, Long operatorId,
			String machineId) {
		
		CertManage cm = certManageService.queryCertManage(memberCode, operatorId, machineId);
		
		if(cm != null){
			CertManageDto dto = new CertManageDto();
			BeanUtils.copyProperties(cm, dto);
			return dto;
		}
		return null;
	}

	@Override
	public CertManageDto queryTempUsePalce(Long memberCode, Long operatorId,
			String machineId) {
		CertManage cm = certManageService.queryTempCertManage(memberCode, operatorId, machineId);
		
		if(cm != null){
			CertManageDto dto = new CertManageDto();
			BeanUtils.copyProperties(cm, dto);
			return dto;
		}
		return null;
	}


	public void setMemberCertService(MemberCertService memberCertService) {
		this.memberCertService = memberCertService;
	}


//	public void setCfcaCertService(CFCACertService cfcaCertService) {
//		this.cfcaCertService = cfcaCertService;
//	}


	public void setCertManageService(CertManageService certManageService) {
		this.certManageService = certManageService;
	}
}
