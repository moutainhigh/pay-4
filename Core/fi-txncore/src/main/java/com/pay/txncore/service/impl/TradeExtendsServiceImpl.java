/**
 * 
 */
package com.pay.txncore.service.impl;

import com.pay.txncore.dao.TradeExtendsDAO;
import com.pay.txncore.dto.TradeExtendsDTO;
import com.pay.txncore.model.TradeExtends;
import com.pay.txncore.service.TradeExtendsService;
import com.pay.util.BeanConvertUtil;

/**
 * @author tianqing_wang
 *
 */
public class TradeExtendsServiceImpl implements TradeExtendsService {

	private TradeExtendsDAO tradeExtendsDAO;

	public void setTradeExtendsDAO(TradeExtendsDAO tradeExtendsDAO) {
		this.tradeExtendsDAO = tradeExtendsDAO;
	}

	public Long createTradeExtendsRnTx(TradeExtendsDTO tradeExtendsDTO) {

		TradeExtends tradeExtends = BeanConvertUtil.convert(TradeExtends.class,
				tradeExtendsDTO);
		return (Long) tradeExtendsDAO.create(tradeExtends);
	}

	@Override
	public TradeExtendsDTO findByTradeOrderNo(Long tradeOrderNo) {
		TradeExtends tradeExtends = tradeExtendsDAO
				.findByTradeOrderNo(tradeOrderNo);

		return BeanConvertUtil.convert(TradeExtendsDTO.class, tradeExtends);
	}

	@Override
	public boolean updateTradeExtends(TradeExtendsDTO tradeExtendsDTO){
		TradeExtends tradeExtends = BeanConvertUtil.convert(TradeExtends.class,
				tradeExtendsDTO);
		
		return tradeExtendsDAO.update(tradeExtends);
	}
	
}
