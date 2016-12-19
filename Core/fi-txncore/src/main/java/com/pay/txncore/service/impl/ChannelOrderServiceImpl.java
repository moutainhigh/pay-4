/**
 * 
 */
package com.pay.txncore.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.txncore.dao.ChannelOrderDAO;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.model.ChannelOrder;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.util.BeanConvertUtil;

/**
 * @author chaoyue
 *
 */
public class ChannelOrderServiceImpl implements ChannelOrderService {

	private final Log logger = LogFactory.getLog(ChannelOrderServiceImpl.class);
	private ChannelOrderDAO channelOrderDAO;

	public void setChannelOrderDAO(ChannelOrderDAO channelOrderDAO) {
		this.channelOrderDAO = channelOrderDAO;
	}

	@Override
	public Long saveChannelProtocolRnTx(ChannelOrderDTO channelOrderDTO) {
		return (Long) channelOrderDAO.create(BeanConvertUtil.convert(
				ChannelOrder.class, channelOrderDTO));
	}

	@Override
	public boolean updateChannelProtocolRnTx(ChannelOrderDTO channelOrderDTO) {
		return channelOrderDAO.update(BeanConvertUtil.convert(
				ChannelOrder.class, channelOrderDTO));
	}

	@Override
	public ChannelOrderDTO queryByTradeOrderNo(Long paymentOrderNo) {
		ChannelOrder channelOrder = channelOrderDAO
				.findByTradeOrderNo(paymentOrderNo);

		return BeanConvertUtil.convert(ChannelOrderDTO.class, channelOrder);
	}

	@Override
	public boolean updateChannelProtocolRnTx(ChannelOrderDTO channelOrderDTO,
			Integer oldStatus) {
		ChannelOrder channelOrder = BeanConvertUtil.convert(ChannelOrder.class,
				channelOrderDTO);
		channelOrder.setOldStatus(oldStatus);
		return channelOrderDAO.update(channelOrder);
	}

	public boolean updateChannelOrder(ChannelOrderDTO channelOrderDTO,
			Integer oldStatus) {
		ChannelOrder channelOrder = BeanConvertUtil.convert(ChannelOrder.class,
				channelOrderDTO);
		channelOrder.setOldStatus(oldStatus);
		return channelOrderDAO.update(channelOrder);
	}

	@Override
	public List<ChannelOrderDTO> queryChannelOrder(
			ChannelOrderDTO channelOrderDTO, Page page) {

		return (List<ChannelOrderDTO>) BeanConvertUtil.convert(
				ChannelOrderDTO.class, channelOrderDAO.findByCriteria(
						"findByCriteria", channelOrderDTO, page));
	}

	@Override
	public List<ChannelOrderDTO> queryChannelOrder(
			ChannelOrderDTO channelOrderDTO) {
		return (List<ChannelOrderDTO>) BeanConvertUtil.convert(
				ChannelOrderDTO.class, channelOrderDAO.findByCriteria(
						"findByCriteria", channelOrderDTO));
	}
	
	@Override
	public ChannelOrderDTO queryByChannelOrderNo(Long channelOrderNo) {
		ChannelOrder channelOrder = (ChannelOrder) channelOrderDAO
				.findById(channelOrderNo);
		return BeanConvertUtil.convert(ChannelOrderDTO.class, channelOrder);
	}

	@Override
	public long getMaxChannelSerialNo(String orgCode) {

		Map paraMap = new HashMap();
		paraMap.put("orgCode", orgCode);
		return (Long) channelOrderDAO.findObjectByCriteria(
				"getMaxChannelSerialNo", paraMap);
	}

	@Override
	public ChannelOrderDTO queryByOrgCodeAndSerialNo(String orgCode,
			String serialNo) {
		ChannelOrderDTO query = new ChannelOrderDTO();
		query.setOrgCode(orgCode);
		query.setSerialNo(serialNo);
		List<ChannelOrderDTO> list = (List<ChannelOrderDTO>) BeanConvertUtil
				.convert(ChannelOrderDTO.class,
						channelOrderDAO.findByCriteria("findByCriteria", query));
		if (null != list && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public ChannelOrderDTO queryByTradeDateAndAuthorisation(String orgCode,
			String tradeDate, String authorisation) {

		ChannelOrder channelOrder = channelOrderDAO
				.queryByTradeDateAndAuthorisation(orgCode, tradeDate,
						authorisation);
		return BeanConvertUtil.convert(ChannelOrderDTO.class, channelOrder);
	}

}
