/**
 * 
 */
package com.pay.acc.rate.service;

import java.util.List;

import com.pay.acc.rate.dto.MerchantRateDto;

/**
 * @author chaoyue
 *
 */
public interface MerchantRateService {

	/**
	 * 
	 * @param merchantRate
	 * @return
	 */
	public Long addMerchantRate(MerchantRateDto merchantRate);

	/**
	 * 
	 * @param id
	 * @return
	 */
	public boolean delMerchantRate(Long id);

	/**
	 * 
	 * @param merchantRate
	 * @return
	 */
	public List<MerchantRateDto> queryMerchantRate(MerchantRateDto merchantRate);

	/**
	 * 
	 * @param merchantRate
	 * @return
	 */
	public boolean updateMerchantRate(MerchantRateDto merchantRate);

	/**
	 * 
	 * @param merchantCode
	 * @param dealCode
	 * @param merchantRate
	 * @return
	 */
	public MerchantRateDto queryMerchantRate(Long merchantCode,
			Integer dealCode, MerchantRateDto merchantRate);
	
	public boolean updateLevelCurrencyCode(MerchantRateDto merchantRate);
}
