package com.pay.poss.specialmerchant.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.specialmerchant.dto.SpEnumInfoDto;

public interface ISpEnumInfoDao extends BaseDAO<SpEnumInfoDto> {

	public Page<SpEnumInfoDto> findPage(Page<SpEnumInfoDto> page,
			SpEnumInfoDto spEnumInfo);

	public int updateSpEnumInfo(Long id, String enumName, String value1,
			String value2);

	public List<SpEnumInfoDto> queryRangIdOrCardType(SpEnumInfoDto spEnumInfo);

	public int findMaxEnumCode(int enumType);
}
