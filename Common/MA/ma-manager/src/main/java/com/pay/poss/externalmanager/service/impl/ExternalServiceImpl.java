package com.pay.poss.externalmanager.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.externalmanager.common.Constants;
import com.pay.poss.externalmanager.dao.IExternalDao;
import com.pay.poss.externalmanager.dto.IsMemberLegal;
import com.pay.poss.externalmanager.service.IExternalService;
/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		ExternalServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-11-12		gungun_zhang			Create
 */

public class ExternalServiceImpl implements IExternalService{
	private Log log = LogFactory.getLog(ExternalServiceImpl.class);
	private IExternalDao externalDao;
	
	@Override
	public IsMemberLegal isMemberLegal(String memberName, String loginName) {
		IsMemberLegal isMemberLegal = new IsMemberLegal();
		Long memberCode = null;
		Boolean isMemberAccountActive = false;
		try{
			memberCode = this.isMemberActive(memberName, loginName);
			if(memberCode!=null){
				isMemberAccountActive = this.isMemberAccountActive(memberCode,Constants.ACCOUNT_RMB);
				if(isMemberAccountActive){
					isMemberLegal.setReturnBool(true);
					isMemberLegal.setMsg(Constants.ISLEGAL);
				}else{
					isMemberLegal.setReturnBool(false);
					isMemberLegal.setMsg(Constants.ERROR_ACCOUNT);
				}
			}else{
				isMemberLegal.setReturnBool(false);
				isMemberLegal.setMsg(Constants.ERROR_MEMBER);
			}
		}catch(Exception e){
			log.error("ExternalServiceImpl.isMemberLegal is error...");
			e.printStackTrace();
			isMemberLegal.setReturnBool(false);
			isMemberLegal.setMsg(Constants.ERROR_SYSTEM);
		}
		return isMemberLegal;
	}
	@Override
	public IsMemberLegal isMemberAndAccountActiv(String email,String orgCode) {
		IsMemberLegal isMemberLegal = new IsMemberLegal();		
		Boolean isMemberAccountActive = false;
		try{
			Long memberCode  = this.queryMemberByEmailAndOrgCode(email, orgCode);
			if(memberCode!=null){
				isMemberAccountActive = this.isMemberAccountActivation(memberCode);
				if(isMemberAccountActive){
					isMemberLegal.setReturnBool(true);
					isMemberLegal.setMsg(memberCode.toString());
				}else{
					isMemberLegal.setReturnBool(true);
					isMemberLegal.setMsg(memberCode.toString());
				}
			}else{
				isMemberLegal.setReturnBool(false);
				isMemberLegal.setMsg(Constants.ERROR_MEMBER);
			}
		}catch(Exception e){
			log.error("ExternalServiceImpl.isMemberAndAccountActiv is error...");
			e.printStackTrace();
			isMemberLegal.setReturnBool(false);
			isMemberLegal.setMsg(Constants.ERROR_SYSTEM);
		}
		return isMemberLegal;
	}
	
	
	
	private Long  queryMemberByEmailAndOrgCode(String email,String orgCode){
		return this.externalDao.queryMemberByEmailAndOrgCode(email, orgCode);
	}
	
	private Boolean isMemberAccountActivation(Long memberCode){
		Integer accActivCount = this.externalDao.queryAccountByMemberCode(memberCode);
		if(accActivCount>0){
			return true;
		}else{
			return false;
		}
		
	}
	


	
	private Long  isMemberActive(String memberName,String loginName){
		return this.externalDao.isMemberActive( memberName,loginName);
	}
	private Boolean  isMemberAccountActive(Long memberCode,Integer accountType){		
		return this.externalDao.isMemberAccountActive(memberCode, accountType);
	}
	public void setExternalDao(IExternalDao externalDao) {
		this.externalDao = externalDao;
	}
	
}
