package com.pay.poss.service;

import java.util.List;

import com.pay.pe.dto.CurrencyDTO;
import com.pay.pe.service.currency.CurrencyService;
import com.pay.inf.dao.Page;

public class PossCurrencyService {
       
	private CurrencyService currencyService;

	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}
	
	public void createCurrency(CurrencyDTO currencyDTO){
		currencyService.create(currencyDTO);
	}
	
	public List<CurrencyDTO> getCurrencyDTOList(CurrencyDTO currencyDTO,Page page){
		return currencyService.findByCriteria(currencyDTO, page);
	}
	
	
}
