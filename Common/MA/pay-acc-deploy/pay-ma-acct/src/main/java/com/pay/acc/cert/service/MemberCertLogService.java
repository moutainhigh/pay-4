/**
*Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
*/
package com.pay.acc.cert.service;

import com.pay.acc.cert.model.MemberCertLog;

/**
 * @author fjl
 * @date 2011-11-15
 */
public interface MemberCertLogService {
	
	/**
	 * 创建一个证书操作日志
	 * @param model
	 * @return
	 */
	public Long createMemberCertLog(MemberCertLog model);
	
	/**
	 * 得到流水号
	 * @return
	 */
	public Long getSerialNo();

}
