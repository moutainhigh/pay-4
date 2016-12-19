package com.pay.poss.specialmerchant.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.specialmerchant.dao.ISpEnumInfoDao;
import com.pay.poss.specialmerchant.dto.SpEnumInfoDto;

public class SpEnumInfoDaoImpl extends BaseDAOImpl<SpEnumInfoDto> implements
		ISpEnumInfoDao {

	private BaseDAO<SpEnumInfoDto> pageDaoCommon;

	public void setPageDaoCommon(BaseDAO<SpEnumInfoDto> pageDaoCommon) {
		this.pageDaoCommon = pageDaoCommon;
	}

	public Page<SpEnumInfoDto> findPage(Page<SpEnumInfoDto> page,
			SpEnumInfoDto spEnumInfo) {

		return pageDaoCommon.findByQuery(namespace.concat("search"), page,
				spEnumInfo);
	}

	public int updateSpEnumInfo(Long id, String enumName, String value1,
			String value2) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enumId", id);
		map.put("enumName", enumName);
		map.put("value1", value1);
		map.put("value2", value2);
		return getSqlMapClientTemplate().update(
				namespace.concat("updateSpEnumInfo"), map);
	}

	public List<SpEnumInfoDto> queryRangIdOrCardType(SpEnumInfoDto spEnumInfo) {
		List<SpEnumInfoDto> enumList = this.getSqlMapClientTemplate()
				.queryForList(namespace.concat("search"), spEnumInfo);
		return enumList;
	}

	public int findMaxEnumCode(int enumType) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("enumType", enumType);
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("findMaxEnumCode"), map);
	}
}
