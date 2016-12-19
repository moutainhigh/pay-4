package com.pay.pe.manualbooking.dao;

import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchDataBuildException;
import com.pay.pe.manualbooking.exception.VouchDataInvalidException;


/**
 * 手工记账数据载入器
 */
public interface VouchDataLoader {
	/**
	 * @return
	 * @throws VouchDataInvalidException
	 * 载入手工记账数据
	 */
	VouchDataDto getData() throws VouchDataInvalidException, VouchDataBuildException;
}
