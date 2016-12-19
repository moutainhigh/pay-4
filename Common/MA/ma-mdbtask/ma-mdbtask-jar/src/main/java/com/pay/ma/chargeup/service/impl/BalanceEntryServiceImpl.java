package com.pay.ma.chargeup.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pay.ma.chargeup.dao.BalanceEntryDAO;
import com.pay.ma.chargeup.dto.BalanceEntryDto;
import com.pay.ma.chargeup.model.BalanceEntry;
import com.pay.ma.chargeup.service.BalanceEntryService;
import com.pay.util.BeanConvertUtil;

public class BalanceEntryServiceImpl implements BalanceEntryService {
	
	private BalanceEntryDAO balanceEntryDAO;

	/* (non-Javadoc)
	 * @see com.pay.ma.chargeup.service.BalanceEntryService#queryBalanceEntryWithSerialNo(java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<BalanceEntryDto> queryBalanceEntryWithSerialNo(Map paramMap) {
		
		List<BalanceEntry> balanceEntries= this.balanceEntryDAO.queryBalanceEntryInfoWithSerialNo(paramMap);
		List<BalanceEntryDto> balanceEntryDtos= new ArrayList<BalanceEntryDto>();
		
		for (BalanceEntry balanceEntry : balanceEntries) {
			balanceEntryDtos.add(BeanConvertUtil.convert(BalanceEntryDto.class, balanceEntry));
		}
		return balanceEntryDtos;
	}

	/**
	 * @param balanceEntryDAO the balanceEntryDAO to set
	 */
	public void setBalanceEntryDAO(BalanceEntryDAO balanceEntryDAO) {
		this.balanceEntryDAO = balanceEntryDAO;
	}
	
	

}
