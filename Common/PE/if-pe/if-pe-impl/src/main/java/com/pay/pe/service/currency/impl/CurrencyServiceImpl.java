package com.pay.pe.service.currency.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.inf.dao.Page;
import com.pay.pe.dao.currency.CurrencyDAO;
import com.pay.pe.dto.CurrencyDTO;
import com.pay.pe.model.Currency;
import com.pay.pe.service.currency.CurrencyService;
import com.pay.util.BeanConvertUtil;

public class CurrencyServiceImpl implements CurrencyService {
	
	private static Logger logger = LoggerFactory.getLogger(CurrencyServiceImpl.class);

	private CurrencyDAO currencyDAO;

	public void setCurrencyDAO(CurrencyDAO currencyDAO) {
		this.currencyDAO = currencyDAO;
	}

	public String findCurrencyNumByCode(String currencyCode) {
		
		return null;
		
		/*Currency currency = currencyDAO.getCurrencyByCode(currencyCode);
		if (currency == null) {
			return null;
		}
		return currency.getCurrencyNum();*/
	}

	public String getDefaultCurrencyCode() {
		return "CNY";
	}

	public CurrencyDTO getRMBCurrency() {
		Currency currency = currencyDAO.getCurrencyByCode("CNY");
		if (currency == null) {
			return null;
		}
		return BeanConvertUtil.convert(CurrencyDTO.class, currency);
	}

	@Override
	public List<CurrencyDTO> findByCriteria(CurrencyDTO currencyDTO, Page page) {
		return (List<CurrencyDTO>) BeanConvertUtil.convert(
				CurrencyDTO.class, currencyDAO.findByCriteria(
						"findByCriteria",currencyDTO, page));
	}

	@Override
	public List<CurrencyDTO> findByMapper(CurrencyDTO currencyDTO) {	
		return (List<CurrencyDTO>) BeanConvertUtil.convert(
				CurrencyDTO.class, currencyDAO.findByCriteria(
						"findByCriteria",currencyDTO));
	}

	@Override
	public Long create(CurrencyDTO currencyDTO) {
		
		logger.info("currencyDTO: "+currencyDTO);
		
		if(currencyDTO==null)
			return null;
		
		Currency tmp = currencyDAO.getCurrencyByCF(currencyDTO.getCurrencyCode()
				,currencyDTO.getFlag());
		
		if(tmp!=null)
			return null;
		Currency currency = BeanConvertUtil.convert(Currency.class, currencyDTO);
        return (Long) currencyDAO.create(currency);
	}

	@Override
	public void batchCreate(List<CurrencyDTO> currencyList) {
		if(currencyList!=null&&currencyList.size()>0){
		   List<Currency> list = new ArrayList<Currency>();
		   for(CurrencyDTO dto:currencyList){
			   Currency currency = BeanConvertUtil.convert(Currency.class, dto);
			   list.add(currency);
		   }
		   currencyDAO.batchCreate(list);
		}
	}

	@Override
	public boolean deleteByPrimaryKey(Long sequenceId) {
		return currencyDAO.deleteBySequenceId(sequenceId);
	}
}
