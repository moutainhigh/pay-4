package com.pay.pe.manualbooking.dao.impl;

import com.pay.pe.manualbooking.dao.VouchDataLoader;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchDataBuildException;
import com.pay.pe.manualbooking.exception.VouchDataInvalidException;


/**
 * 抽象手工记账数据载入器
 */
public abstract class AbstractVouchDataLoader implements VouchDataLoader {
	
	/**
	 * @return
	 * @throws VouchDataBuildException
	 * 载入手工记账数据
	 */
	protected abstract VouchDataDto loadVouchData() throws VouchDataBuildException;
	
	/**
	 * @param vouchDataDto
	 * @throws VouchDataInvalidException
	 * 验证手工记账数据
	 */
	protected abstract void validateVouchData(VouchDataDto vouchDataDto) throws VouchDataInvalidException;
	
	/**
	 * @return
	 * 取得手工记账数据
	 */
	protected abstract VouchDataDto retriveVouchData();
	
	/**
	 * @return
	 * @throws VouchDataInvalidException
	 * 载入手工记账数据
	 */
	public VouchDataDto getData() throws VouchDataInvalidException, VouchDataBuildException {
		VouchDataDto vouchDataDto = loadVouchData();
		validateVouchData(vouchDataDto);
		return retriveVouchData();
	}

}
