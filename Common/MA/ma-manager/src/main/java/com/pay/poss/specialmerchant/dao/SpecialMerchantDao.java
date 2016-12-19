package com.pay.poss.specialmerchant.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.specialmerchant.dto.SpecialMerchantDto;

public interface SpecialMerchantDao extends BaseDAO{
	/**
	 * 添加特约商户
	 * @param dto
	 * @return
	 */
	public Long createSpecialMerchant(SpecialMerchantDto dto);
	/**
	 * 查询特约商户
	 * @param dto
	 * @return
	 */
	public List<SpecialMerchantDto>  querySpecialMerchant(SpecialMerchantDto dto);

	/**
	 * 查询特约商户
	 * @param id
	 * @return
	 */
	public SpecialMerchantDto querySpecialMerchantById(Long id);
	/**
	 * @param dto
	 * @return
	 */
	public int updateSpecialMerchant(SpecialMerchantDto dto);
	
	/**
	 * @param param
	 * @return
	 */
	public List<SpecialMerchantDto> querySpecialMerchantByPage(
			Map<String,Object> param);
	
	/**
	 * @param param
	 * @return
	 */
	public int querySpecialMerchantByQuery(Map<String,Object> param);
	/**
	 * @param spMerchantId
	 * @return
	 */
	public boolean deleteById(long spMerchantId);
	/**
	 * @return
	 */
	public List<String> querySpeicialMerchantCity(); 
}
