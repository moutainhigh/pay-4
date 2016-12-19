package com.pay.poss.specialmerchant.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.specialmerchant.dto.SpCardInfoDto;

public interface SpCardInfoDao extends BaseDAO {
	/**
	 * create
	 * @param dto
	 * @return
	 */
	public Long createSpCardInfo(SpCardInfoDto dto);
	/**
	 * query all card discount info of special merchant
	 * @param dto
	 * @return
	 */
	public List<SpCardInfoDto>  queryByMerchantId(SpCardInfoDto dto);
	/**
	 * @param spMerchantCardId
	 * @return
	 */
	public SpCardInfoDto  queryByCardId(long spMerchantCardId);
	/**
	 * @param spMerchantCardId
	 * @return
	 */
	public boolean deleteById(long spMerchantCardId);
	/**
	 * @param dto
	 * @return
	 */
	public boolean updateSpecialMerchant(SpCardInfoDto dto);
	/**
	 * @param cardDto
	 * @return
	 */
	public List<SpCardInfoDto> queryBySelective(SpCardInfoDto cardDto);
	/**
	 * @param spMerchantId
	 * @return
	 */
	public Integer deleteByMerchantId(long spMerchantId);
}
