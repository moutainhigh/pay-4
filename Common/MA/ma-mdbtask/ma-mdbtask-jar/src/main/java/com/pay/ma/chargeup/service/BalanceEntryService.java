/**
 * 
 */
package com.pay.ma.chargeup.service;

import java.util.List;
import java.util.Map;

import com.pay.ma.chargeup.dto.BalanceEntryDto;

/**
 * @author Administrator
 *
 */
public interface BalanceEntryService {
	
	/**
	 * 根据流水号查询简单分录信息
	 * 根据流水号和REQUEST_DATE 查询分录信息
	 * @param serialNo
	 * @return
	 */
	public List<BalanceEntryDto> queryBalanceEntryWithSerialNo(Map paramMap);

}
