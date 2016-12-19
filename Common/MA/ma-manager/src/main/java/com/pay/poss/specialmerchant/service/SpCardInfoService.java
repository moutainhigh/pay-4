package com.pay.poss.specialmerchant.service;

import java.util.List;

import com.pay.poss.specialmerchant.dto.SpCardInfoDto;


public interface SpCardInfoService {
	/**
	 * @param dto
	 * @return
	 */
	public Long insertSpCardInfo(SpCardInfoDto dto);
	/**
	 * @param dto
	 * @return
	 */
	public boolean deleteSpCardInfo(SpCardInfoDto dto);
	/**
	 * @param dto
	 * @return
	 */
	public boolean updateSpCardInfo(SpCardInfoDto dto);
	/**
	 * @param dto
	 * @return
	 */
	public List<SpCardInfoDto> queryMerchantCardInfo(SpCardInfoDto dto);
	/**
	 * @param spMerchantCardId
	 * @return
	 */
	public SpCardInfoDto querySpCardInfoById(long spMerchantCardId);
	/**
	 * @param cardDto
	 * @return
	 */
	public List<SpCardInfoDto> queryspcardbyselective(SpCardInfoDto cardDto);
}
