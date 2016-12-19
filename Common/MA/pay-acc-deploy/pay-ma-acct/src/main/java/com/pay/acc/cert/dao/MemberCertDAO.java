/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.acc.cert.dao;

import java.util.List;

import com.pay.acc.cert.model.MemberCert;
import com.pay.inf.dao.BaseDAO;

/**
* @author fjl
* @date 2011-11-15
*/
public interface MemberCertDAO extends BaseDAO<MemberCert>{
	
	
	/**
	 * @param dn
	 * @param status
	 * @return
	 */
	public boolean updateStatus(String dn ,MemberCert.StatusEnum status);
	
	/**
	 * 根据dn 查询证书
	 * @param dn
	 * @return
	 */
	public List<MemberCert>  queryMemberCertByDn(String dn,MemberCert.StatusEnum status);
	
	/**
	 * 根据会员信息查询证书
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	public List<MemberCert> queryMemberCert(Long memberCode, Long operatorId,MemberCert.StatusEnum status);
	
}
