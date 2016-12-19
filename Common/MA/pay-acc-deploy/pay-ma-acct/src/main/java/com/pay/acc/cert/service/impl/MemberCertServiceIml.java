/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.acc.cert.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.cert.dao.MemberCertDAO;
import com.pay.acc.cert.model.MemberCert;
import com.pay.acc.cert.service.MemberCertService;

/**
 * @author fjl
 * @date 2011-11-15
 */
public class MemberCertServiceIml implements MemberCertService {
	
	private static final Log logger = LogFactory.getLog(MemberCertServiceIml.class);
	
	private MemberCertDAO memberCertDAO;

	@Override
	public Long createMemberCert(MemberCert modle) {
		
		return (Long) memberCertDAO.create(modle);
	}


	@Override
	public boolean modifyMemberCert(MemberCert modle) {
		
		return memberCertDAO.update(modle);
	}


	@Override
	public boolean disableMemberCertByDn(String dn) {
		
		return memberCertDAO.updateStatus(dn, MemberCert.StatusEnum.DISABLE);
	}


	@Override
	public MemberCert queryMemberCertByDn(String dn) {
	
		 List<MemberCert> list = memberCertDAO.queryMemberCertByDn(dn,null);
		 if(list != null && list.size() > 0){
			 if(list.size() > 1){
				 logger.warn("一个DN 只能对应一条证书!");
			 }
			 return list.get(0);
		 }
		 return null;
	}


	@Override
	public MemberCert queryMemberCert(Long memberCode, Long operatorId) {
		
		 List<MemberCert> list = memberCertDAO.queryMemberCert(memberCode, operatorId,null);
		 if(list != null && list.size() > 0){
			 if(list.size() > 1){
				 logger.warn("一个会员用户只能对应一条证书!");
			 }
			 return list.get(0);
		 }
		 return null;
	}
	
	
	@Override
	public MemberCert queryById(Long id) {
		
		return memberCertDAO.findById(id);
	}


	public void setMemberCertDAO(MemberCertDAO memberCertDAO) {
		this.memberCertDAO = memberCertDAO;
	}
	

}
