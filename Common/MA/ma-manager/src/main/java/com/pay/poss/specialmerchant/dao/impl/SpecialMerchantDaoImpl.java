package com.pay.poss.specialmerchant.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.specialmerchant.dao.SpecialMerchantDao;
import com.pay.poss.specialmerchant.dto.SpecialMerchantDto;

public class SpecialMerchantDaoImpl extends BaseDAOImpl implements SpecialMerchantDao {

	@Override
	public Long createSpecialMerchant(SpecialMerchantDto dto) {
		Long id = (Long)this.getSqlMapClientTemplate().insert(namespace.concat("createspecialmerchant"),dto);
		return id;
	}

	@Override
	public List<SpecialMerchantDto> querySpecialMerchant(SpecialMerchantDto dto) {
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("queryspecialmerchant"), dto);
	}

	@Override
	public SpecialMerchantDto querySpecialMerchantById(Long id) {
		return (SpecialMerchantDto)this.getSqlMapClientTemplate().queryForObject(namespace.concat("querySpecialMerchantById"), id);
	}

	@Override
	public int updateSpecialMerchant(SpecialMerchantDto dto) {
		return getSqlMapClientTemplate().update(namespace.concat("updateSpecialMerchant"), dto);
	}

	@Override
	public List<SpecialMerchantDto> querySpecialMerchantByPage(
			Map<String, Object> param) {
		return (List<SpecialMerchantDto>)getSqlMapClientTemplate().queryForList(namespace.concat("querySpecialMerchantByPageQuery"),param);
		
	}
	
	@Override
	public int querySpecialMerchantByQuery(Map<String, Object> param) {
		return(Integer) this.getSqlMapClientTemplate().queryForObject(namespace.concat("querySpecialMerchantCountByPageQuery"), param);
	}

	@Override
	public boolean deleteById(long spMerchantId) {
		return this.getSqlMapClientTemplate().delete(namespace.concat("deleteBySpMerchantId"), spMerchantId) == 1;
	}

	@Override
	public List<String> querySpeicialMerchantCity() {
		List<String> cityCodeList = this.getSqlMapClientTemplate().queryForList(namespace.concat("querySpecialMerchantCity"));
		return cityCodeList;
	}
	

}
