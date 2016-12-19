package com.pay.txncore.service;

import java.util.List;

import com.pay.fi.exception.BusinessException;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeOrderDTO;

/**
 * @description 网关订单查询服务接口
 * @author Fred
 * @date 2011-4-12
 */
public interface TradeService {

	/**
	 * 根据网关订单流水查询出当前订单及网关基本信息
	 * 
	 * @return
	 */
	public TradeBaseDTO queryTradeByTradeOrderNo(Long tradeOrderNo)
			throws BusinessException;

	/**
	 * 根据一组网关订单流水号查出List<TradeBaseDTO>
	 * 
	 * @param tradeOrderNo
	 *            例如："1021104141122010057,1021104141122010058"
	 * @return List<TradeBaseDTO>
	 * @throws NumberFormatException
	 * @throws BusinessException
	 */
	public List<TradeOrderDTO> queryTradeListByOrderNoStr(String tradeOrderNoStr)
			throws BusinessException, NumberFormatException;

	/**
	 * 根据一个网关订单流水号查出TradeOrderDTO
	 * 
	 * @param tradeOrderNo
	 * @return
	 * @throws BusinessException
	 * @throws NumberFormatException
	 */
	public TradeOrderDTO queryTradeListByOrderNo(Long tradeOrderNo)
			throws BusinessException, NumberFormatException;

}
