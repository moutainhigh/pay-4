/**
 * 
 */
package com.pay.acc.pedealinfo.service;

import com.pay.acc.pedealinfo.dto.PeDealInfoDto;
import com.pay.acc.pedealinfo.exception.PeDealInfoException;

/**
 * @author Administrator
 *
 */
public interface PeDealInfoService {
	/**
	 * 保存记账信息
	 * @param peDealInfoDto
	 * @return
	 * @throws PeDealInfoException 
	 */
	public Long createPeDealInfo(PeDealInfoDto peDealInfoDto) throws PeDealInfoException;

}
