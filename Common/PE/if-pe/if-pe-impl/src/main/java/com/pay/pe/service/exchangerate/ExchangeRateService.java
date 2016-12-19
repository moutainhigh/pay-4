package com.pay.pe.service.exchangerate;

import com.pay.pe.dto.ExchangeRateDTO;
import com.pay.util.MfDateTime;

public interface ExchangeRateService {
	/**
	 * 根据原币种，目标币种，开始日期，结束日期查询出汇率
	 * 
	 * @param cnyFrom
	 * @param cnyTo
	 * @param start
	 * @param end
	 * @return
	 */
	public ExchangeRateDTO findExchangeRate(String cnyFrom, String cnyTo,
			MfDateTime date);

}
