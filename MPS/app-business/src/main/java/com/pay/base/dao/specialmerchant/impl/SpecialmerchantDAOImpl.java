package com.pay.base.dao.specialmerchant.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.base.dao.specialmerchant.SpecialmerchantDAO;
import com.pay.base.model.CardType;
import com.pay.base.model.SpEnumInfo;
import com.pay.base.model.SpecialMerchant;
import com.pay.base.model.SpecialMerchantCity;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class SpecialmerchantDAOImpl extends BaseDAOImpl implements
		SpecialmerchantDAO {

	public Class getModelClass() {
		return SpEnumInfo.class;
	}

	public List<SpEnumInfo> getSpEnumInfoList(int enumType) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enumType", enumType);
		return (List<SpEnumInfo>) this.getSqlMapClientTemplate().queryForList(
				getNamespace().concat("getSpEnumInfo"), map);

	}

	public List<SpecialMerchantCity> getSpecialMerchantCityCodeList() {
		Map<String, Object> map = new HashMap<String, Object>();
		return (List<SpecialMerchantCity>) this.getSqlMapClientTemplate()
				.queryForList(
						getNamespace().concat("getSpecialMerchantCityCode"),
						map);

	}

	public int querySpecialmerchantSum(SpecialMerchant specialMerchant) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("querySpecialmerchantSum"),
				specialMerchant);

	}

	public List<SpecialMerchant> querySpecialMerchantList(
			Map<String, Object> paramMap) {
		return getSqlMapClientTemplate().queryForList(
				getNamespace().concat("querySpecialMerchantList"), paramMap);
	}

	public List<CardType> queryCardTypeList(Map<String, Object> paramMap) {
		return getSqlMapClientTemplate().queryForList(
				getNamespace().concat("queryCardTypeList"), paramMap);
	}

	public SpecialMerchant getSpecialMerchantdetail(Map<String, Object> paramMap) {
		return (SpecialMerchant) getSqlMapClientTemplate().queryForObject(
				getNamespace().concat("getSpecialMerchantdetail"), paramMap);

	}
}
