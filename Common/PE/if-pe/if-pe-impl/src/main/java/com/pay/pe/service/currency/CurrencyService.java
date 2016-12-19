package com.pay.pe.service.currency;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.pe.dto.CurrencyDTO;

/**
 * 币种服务
 *
 *
 */
public interface CurrencyService {
	/**
	 * 获得系统默认的币种
	 * @return
	 */
	public String getDefaultCurrencyCode();
	
	public CurrencyDTO getRMBCurrency();
	
	/**
	 * 根据币种缩写获得币种号
	 * @param currencyCode
	 * @return
	 */
	public String findCurrencyNumByCode(String currencyCode);
	
	/**
	 * 
	 * @param rate
	 * @return
	 */
	List<CurrencyDTO> findByCriteria(CurrencyDTO currencyDTO,Page page);
	
	List<CurrencyDTO> findByMapper(CurrencyDTO currencyDTO);
	
	Long  create(CurrencyDTO currencyDTO);
	void  batchCreate(List<CurrencyDTO> currencyList);
	
	boolean deleteByPrimaryKey(Long sequenceId);
}
