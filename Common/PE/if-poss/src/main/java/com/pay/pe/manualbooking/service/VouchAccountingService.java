package com.pay.pe.manualbooking.service;

import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchDataPostingException;

/**
 * 
 * 手工记帐服务
 */
public interface VouchAccountingService {
	/**
	 * @param vouchDataDto
	 * @return
	 * @throws VouchDataPostingException
	 * 记帐，生成分录，更新余额。生成日记帐分录。
	 */
	String posting(VouchDataDto vouchDataDto) throws VouchDataPostingException;
}
