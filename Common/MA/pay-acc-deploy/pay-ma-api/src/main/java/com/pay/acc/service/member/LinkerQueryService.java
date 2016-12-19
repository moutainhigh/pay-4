/**
 * 
 */
package com.pay.acc.service.member;

import com.pay.acc.exception.MaMemberQueryException;

/**
 * @author Administrator
 *
 */
public interface LinkerQueryService {
	
	/**
	 * 根据双方的会员号查询是否互为联系人
	 * @param orgMemberCode 原联系人会员号
	 * @param linkMemberCode 被联系人会员号
	 * @return true 互为联系人
	 */
	public boolean queryIsLinkerWithMemberCode(Long orgMemberCode,Long linkMemberCode) throws MaMemberQueryException;

}
