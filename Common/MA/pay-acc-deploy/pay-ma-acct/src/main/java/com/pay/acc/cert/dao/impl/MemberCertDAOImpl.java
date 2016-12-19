/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.acc.cert.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.cert.dao.MemberCertDAO;
import com.pay.acc.cert.model.MemberCert;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author fjl
 * @date 2011-11-15
 */
public class MemberCertDAOImpl extends BaseDAOImpl<MemberCert> implements
		MemberCertDAO {

	@Override
	public boolean updateStatus(String dn, MemberCert.StatusEnum status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userDn", dn);
		map.put("status", status.getValue());
		return getSqlMapClientTemplate().update(namespace.concat("updateStatus"),map) > 0 ;
	}

	@Override
	public List<MemberCert> queryMemberCertByDn(String dn,MemberCert.StatusEnum status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("userDn", dn);
		if(status != null){
			map.put("status", status.getValue());
		}
		return  findBySelective(map);
	}

	@Override
	public List<MemberCert> queryMemberCert(Long memberCode, Long operatorId, MemberCert.StatusEnum status) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("memberCode", memberCode);
		map.put("operatorId", operatorId);
		if(status != null){
			map.put("status", status.getValue());
		}
		return findBySelective(map);
	}
	
}
