/**
 * 
 */
package com.pay.txncore.service;

import com.pay.txncore.dto.TradeExtendsDTO;

/**
 *
 */
public interface TradeExtendsService {

	/**
	 * 插入附属表TradeExtends
	 */
	Long createTradeExtendsRnTx(TradeExtendsDTO tradeExtendsDTO);

	/**
	 * 查询附属表TradeExtends
	 */
	TradeExtendsDTO findByTradeOrderNo(Long tradeOrderNo);
	
	/**
	 * 更新TradeExtendS
	 */
	boolean updateTradeExtends(TradeExtendsDTO tradeExtendsDTO);
}
