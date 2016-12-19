/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.acc.cert.service.impl;

import com.pay.acc.cert.dao.MemberCertLogDAO;
import com.pay.acc.cert.model.MemberCertLog;
import com.pay.acc.cert.service.MemberCertLogService;

/**
 * @author fjl
 * @date 2011-11-15
 */
public class MemberCertLogServiceImpl implements MemberCertLogService {

	private MemberCertLogDAO memberCertLogDAO;
	
	@Override
	public Long createMemberCertLog(MemberCertLog model) {
		
		return (Long) memberCertLogDAO.create(model);
	}
	
	@Override
	public Long getSerialNo(){
		return memberCertLogDAO.getSerialNo();
	}

	/**
	 * @param memberCertLogDAO
	 */
	public void setMemberCertLogDAO(MemberCertLogDAO memberCertLogDAO) {
		this.memberCertLogDAO = memberCertLogDAO;
	}

}
