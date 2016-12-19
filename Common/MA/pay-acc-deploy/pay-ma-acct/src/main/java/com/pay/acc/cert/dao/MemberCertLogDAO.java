/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.acc.cert.dao;

import com.pay.acc.cert.model.MemberCertLog;
import com.pay.inf.dao.BaseDAO;

/**
 * @author fjl
 * @date 2011-11-15
 */
public interface MemberCertLogDAO extends BaseDAO<MemberCertLog>{
	
	/**
	 * 得到流水号
	 * @return
	 */
	public Long getSerialNo();

}
