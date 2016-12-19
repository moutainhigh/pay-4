/**
 *Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.acc.cert.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.cert.dao.CertManageDAO;
import com.pay.acc.cert.model.CertManage;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author fjl
 * @date 2011-11-15
 */
public class CertManageDAOImpl extends BaseDAOImpl<CertManage> implements
		CertManageDAO {

	@Override
	public int queryCount(Long memberCode, Long operatorId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberCode", memberCode);
		map.put("operatorId", operatorId);
		map.put("status", CertManage.StatusEnum.VALID.getValue());
		return countByCriteria(map);
	}

	@Override
	public List<CertManage> queryCertManage(Long memberCode, Long operatorId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberCode", memberCode);
		map.put("operatorId", operatorId);
		map.put("status", CertManage.StatusEnum.VALID.getValue());
		return findBySelective(map);
	}
	
	@Override
	public CertManage queryCertManage(Long memberCode, Long operatorId,
			String machineId) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberCode", memberCode);
		map.put("operatorId", operatorId);
		map.put("status", CertManage.StatusEnum.VALID.getValue());
		map.put("machineId", machineId);
		List<CertManage> list= findBySelective(map);
		if(list != null && list.size() > 0){
			 if(list.size() > 1){
				 logger.warn("一个会员同一台机器只能存在一个有效证书!");
			 }
			 return list.get(0);
		}
		return null;
	}

	@Override
	public CertManage queryTempCertManage(Long memberCode, Long operatorId,
			String machineId) {

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberCode", memberCode);
		map.put("operatorId", operatorId);
		map.put("status", CertManage.StatusEnum.TEMP.getValue());
		map.put("machineId", machineId);
		List<CertManage> list = findBySelective(map);
		if(list != null && list.size() > 0){
			 if(list.size() > 1){
				 logger.warn("一个会员同一台机器只能存在一个未安装完成的证书!");
			 }
			 return list.get(0);
		}
		return null;
	}

	@Override
	public boolean updateStatus(Long id, CertManage.StatusEnum status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", id);
		map.put("status", status.getValue());
		return getSqlMapClientTemplate().update(namespace.concat("updateStatus"),map) == 1;
	}

	@Override
	public void disableAll(Long memberCode, Long operatorId) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberCode", memberCode);
		map.put("operatorId", operatorId);
		map.put("status",CertManage.StatusEnum.DELETED.getValue());
		getSqlMapClientTemplate().update(namespace.concat("updateStatus"),map);
	}
	
	

}
