package com.pay.pe.manualbooking.dao;

import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchDataBuildException;




/**
 * 手工记帐申请构建器
 */
public interface VouchBuilder {
	
	/**
	 * @return
	 * @throws VouchDataBuildException
	 * 构建手工记账申请数据
	 */
	VouchDataDto buildVouch() throws VouchDataBuildException;
	
}
