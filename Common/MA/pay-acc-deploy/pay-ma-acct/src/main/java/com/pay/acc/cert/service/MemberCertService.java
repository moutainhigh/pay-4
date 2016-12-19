/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.acc.cert.service;

import com.pay.acc.cert.model.MemberCert;

/**
 * @author fjl
 * @date 2011-11-15
 */
public interface MemberCertService {
	
	/**
	 * @param modle
	 * @return
	 */
	public Long createMemberCert(MemberCert modle);
	
	/**
	 * @param modle
	 */
	public boolean modifyMemberCert(MemberCert modle);
	
	/**
	 * 注销证书
	 * @param dn
	 */
	public boolean disableMemberCertByDn(String dn);
	
	/**
	 * 根据dn 查询证书
	 * @param dn
	 * @return
	 */
	public MemberCert queryMemberCertByDn(String dn);
	
	/**
	 * 根据会员信息查询证书
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	public MemberCert queryMemberCert(Long memberCode, Long operatorId);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public MemberCert queryById(Long id);

}
