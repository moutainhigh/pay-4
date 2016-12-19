package com.pay.poss.specialmerchant.service.impl;

import java.util.List;

import com.pay.poss.specialmerchant.dao.SpCardInfoDao;
import com.pay.poss.specialmerchant.dto.SpCardInfoDto;
import com.pay.poss.specialmerchant.service.SpCardInfoService;

public class SpCardInfoServiceImpl implements SpCardInfoService{
	private SpCardInfoDao spCardInfoDao;
	@Override
	public Long insertSpCardInfo(SpCardInfoDto dto) {
		return this.spCardInfoDao.createSpCardInfo(dto);
	}

	@Override
	public boolean deleteSpCardInfo(SpCardInfoDto dto) {
		return this.spCardInfoDao.deleteById(dto.getSpMerchantCardId());
	}

	@Override
	public boolean updateSpCardInfo(SpCardInfoDto dto) {
		return this.spCardInfoDao.updateSpecialMerchant(dto);
	}

	@Override
	public List<SpCardInfoDto> queryMerchantCardInfo(SpCardInfoDto dto) {
		return this.spCardInfoDao.queryByMerchantId(dto);
	}
	@Override
	public SpCardInfoDto querySpCardInfoById(long spMerchantCardId) {
		return this.spCardInfoDao.queryByCardId(spMerchantCardId);
	}
	public SpCardInfoDao getSpCardInfoDao() {
		return spCardInfoDao;
	}

	public void setSpCardInfoDao(SpCardInfoDao spCardInfoDao) {
		this.spCardInfoDao = spCardInfoDao;
	}

	@Override
	public List<SpCardInfoDto> queryspcardbyselective(SpCardInfoDto cardDto) {
		return this.spCardInfoDao.queryBySelective(cardDto);
	}

	

}
