package com.pay.poss.specialmerchant.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.specialmerchant.dao.SpCardInfoDao;
import com.pay.poss.specialmerchant.dto.SpCardInfoDto;

public class SpCardInfoDaoImpl extends BaseDAOImpl implements SpCardInfoDao {

	@Override
	public Long createSpCardInfo(SpCardInfoDto dto) {
		Long id = (Long)this.getSqlMapClientTemplate().insert(namespace.concat("create"),dto);
		return id;
	}

	@Override
	public List<SpCardInfoDto> queryByMerchantId(SpCardInfoDto dto) {
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("queryspcardinfo"), dto);
	}

	@Override
	public SpCardInfoDto queryByCardId(long spMerchantCardId) {
		return (SpCardInfoDto)this.getSqlMapClientTemplate().queryForObject(namespace.concat("queryspcardinfobyid"), spMerchantCardId);
	}

	@Override
	public boolean deleteById(long spMerchantCardId) {
		return this.getSqlMapClientTemplate().delete(namespace.concat("delete"), spMerchantCardId) == 1;
	}
	@Override
	public boolean updateSpecialMerchant(SpCardInfoDto dto) {
		return getSqlMapClientTemplate().update(namespace.concat("update"), dto) == 1;
	}

	@Override
	public List<SpCardInfoDto> queryBySelective(SpCardInfoDto cardDto) {
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("queryspcardbyselective"), cardDto);
	}

	@Override
	public Integer deleteByMerchantId(long spMerchantId) {
		return this.getSqlMapClientTemplate().delete(namespace.concat("deletebyselective"),spMerchantId);
	}
	

}
