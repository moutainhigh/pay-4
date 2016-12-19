package com.pay.poss.specialmerchant.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.pay.poss.specialmerchant.dto.SpecialMerchantDto;

public interface SpecialMerchantService {
	/**
	 * @param param
	 * @return
	 */
	public List<SpecialMerchantDto> querySpecialMerchantByPage(Map<String,Object> param);
	/**
	 * @param param
	 * @return
	 */
	public int querySpecialMerchantCount(Map<String,Object> param);
	
	/**
	 * @param merchantName
	 * @return
	 */
	public boolean isMerchantExist(String merchantName);
	
	/**
	 * @param dto
	 * @return
	 * @throws Exception 
	 */
	public Long insertSpecialMerchantRdTx(SpecialMerchantDto dto,Map<String,InputStream> map) throws Exception ; 
	
	/**
	 * @param sp_merchant_id
	 * @return
	 */
	public SpecialMerchantDto querySpecialMerchantById(Long sp_merchant_id);
	
	/**
	 * @param smDto
	 * @return
	 */
	public boolean deleteSpecialMerchant(SpecialMerchantDto smDto);
	/**
	 * @param dto
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Integer updateSpecialMerchantRdTx(SpecialMerchantDto dto,Map<String, InputStream> map) throws Exception;
	
	/**
	 * @return
	 */
	public List<String> querySpecialMerchantCity();
}
