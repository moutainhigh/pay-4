/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.acc.cert.service.impl;

import java.util.List;

import com.pay.acc.cert.dao.CertManageDAO;
import com.pay.acc.cert.model.CertManage;
import com.pay.acc.cert.service.CertManageService;

/**
 * @author fjl
 * @date 2011-11-15
 */
public class CertManageServiceImpl implements CertManageService {

	private CertManageDAO certManageDAO;

	@Override
	public long createCerManage(CertManage model) {
		
		return (Long) certManageDAO.create(model);
	}


	@Override
	public boolean modifyCerManage(CertManage model) {
		
		return certManageDAO.update(model);
	}


	@Override
	public int queryCount(Long memberCode, Long operatorId) {
		
		return certManageDAO.queryCount(memberCode, operatorId);
	}


	@Override
	public List<CertManage> queryCertManage(Long memberCode, Long operatorId) {
		
		return certManageDAO.queryCertManage(memberCode, operatorId);
	}


	@Override
	public boolean logicDeleteById(Long id) {
		return certManageDAO.updateStatus(id, CertManage.StatusEnum.DELETED);
	}


	/**
	 * @param certManageDAO the certManageDAO to set
	 */
	public void setCertManageDAO(CertManageDAO certManageDAO) {
		this.certManageDAO = certManageDAO;
	}


	@Override
	public CertManage queryCertManage(Long memberCode, Long operatorId,
			String machineId) {
		return certManageDAO.queryCertManage(memberCode, operatorId, machineId);
	}


	@Override
	public CertManage queryTempCertManage(Long memberCode, Long operatorId,
			String machineId) {
		
		return certManageDAO.queryTempCertManage(memberCode, operatorId, machineId);
	}


	@Override
	public void logicDeleteAll(Long memberCode, Long operatorId) {
		certManageDAO.disableAll(memberCode, operatorId);
	}

}
