/**
 * 
 */
package com.pay.txncore.service;

import com.pay.fi.exception.BusinessException;
import com.pay.txncore.model.PartnerConfig;

/**
 * @author chaoyue
 *
 */
public interface PartnerConfigService {

	void delete(Long id);

	/**
	 * 
	 * @param partnerID
	 * @param paramCode
	 * @return
	 */
	PartnerConfig findConfig(Long partnerID, String paramCode);

	/**
	 * 创建网关密钥
	 * 
	 * @param memberCode
	 * @param publicKeyValue
	 * @return
	 * @throws BusinessException
	 */
	public boolean createPartnerConfigRnTx(String memberCode,
			String publicKeyValue) throws BusinessException;
}
