/**
 * 
 */
package com.pay.acc.member.service;

import com.pay.acc.member.dto.MemberInfoDto;

/**
 * @author chaoyue
 *
 */
public interface RegisterService {

	/**
	 * 会员注册
	 * 
	 * @param memberInfoDto
	 * @return
	 */
	public Long registerRdTx(MemberInfoDto memberInfoDto) throws Exception;
}
