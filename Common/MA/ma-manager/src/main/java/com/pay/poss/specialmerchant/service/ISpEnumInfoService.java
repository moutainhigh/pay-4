package com.pay.poss.specialmerchant.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.poss.specialmerchant.dto.SpEnumInfoDto;

public interface ISpEnumInfoService {

	public Page<SpEnumInfoDto> search(Page<SpEnumInfoDto> paramPage,
			SpEnumInfoDto dto);

	public boolean addSpEnumInfoRnTx(String enumName, String enumCode,
			Long enumType, String value1, String value2);

	public boolean updateSpEnumInfoRnTx(Long id, String enumName,
			String value1, String value2);

	public SpEnumInfoDto getById(Long id);

	public boolean delSpEnumInfoRnTx(Long id);

	public List<SpEnumInfoDto> queryRangIdOrCardType(SpEnumInfoDto spEnumInfo);

	public int findMaxEnumCode(int enumType);
}
