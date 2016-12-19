/**
 *Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.acc.cert.dao.impl;

import com.pay.acc.cert.dao.MemberCertLogDAO;
import com.pay.acc.cert.model.MemberCertLog;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author fjl
 * @date 2011-11-15
 */
public class MemberCertLogDAOImpl extends BaseDAOImpl<MemberCertLog> implements
		MemberCertLogDAO {

	@Override
	public Long getSerialNo(){
		
		return (Long) getSqlMapClientTemplate().queryForObject(namespace.concat("selectSerialNo"));
	}
}
