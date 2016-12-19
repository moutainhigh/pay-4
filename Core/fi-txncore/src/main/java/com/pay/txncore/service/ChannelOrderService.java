/**
 * 
 */
package com.pay.txncore.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.txncore.dto.ChannelOrderDTO;

/**
 * @author chaoyue
 *
 */
public interface ChannelOrderService {

	/**
	 * 保存渠道订单
	 * 
	 * @param channelOrderDTO
	 * @return
	 */
	Long saveChannelProtocolRnTx(ChannelOrderDTO channelOrderDTO);

	/**
	 * 更新渠道订单
	 * 
	 * @param channelOrderDTO
	 * @return
	 */
	boolean updateChannelProtocolRnTx(ChannelOrderDTO channelOrderDTO);

	/**
	 * 更新渠道订单
	 * 
	 * @param channelOrderDTO
	 * @return
	 */
	boolean updateChannelProtocolRnTx(ChannelOrderDTO channelOrderDTO,
			Integer oldStatus);

	/**
	 * 更新渠道订单
	 * 
	 * @param channelOrderDTO
	 * @param oldStatus
	 * @return
	 */
	boolean updateChannelOrder(ChannelOrderDTO channelOrderDTO,
			Integer oldStatus);

	/**
	 * 根据充值订单号查询
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	ChannelOrderDTO queryByTradeOrderNo(Long paymentOrderNo);

	/**
	 * 
	 * @param channelOrderNo
	 * @return
	 */
	ChannelOrderDTO queryByChannelOrderNo(Long channelOrderNo);

	/**
	 * 
	 * @param channelOrderNo
	 * @return
	 */
	ChannelOrderDTO queryByOrgCodeAndSerialNo(String orgCode, String serialNo);

	/**
	 * 根据充值订单号查询
	 * 
	 * @param tradeOrderNo
	 * @return
	 */
	List<ChannelOrderDTO> queryChannelOrder(ChannelOrderDTO channelOrderDTO,
			Page page);

	long getMaxChannelSerialNo(String orgCode);

	/**
	 * 查询渠道订单
	 * 
	 * @param tradeDate
	 * @param authorisation
	 * @return
	 */
	ChannelOrderDTO queryByTradeDateAndAuthorisation(String orgCode,
			String tradeDate, String authorisation);
	
	public List<ChannelOrderDTO> queryChannelOrder(
			ChannelOrderDTO channelOrderDTO);
}
