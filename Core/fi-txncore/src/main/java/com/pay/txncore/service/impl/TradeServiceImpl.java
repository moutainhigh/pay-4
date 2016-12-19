package com.pay.txncore.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.txncore.dao.TradeBaseDAO;
import com.pay.txncore.dao.TradeOrderDAO;
import com.pay.txncore.dto.TradeBaseDTO;
import com.pay.txncore.dto.TradeOrderDTO;
import com.pay.txncore.model.TradeBase;
import com.pay.txncore.model.TradeOrder;
import com.pay.txncore.service.TradeService;
import com.pay.util.BeanConvertUtil;

/**
 * @description 网关订单查询服务实现
 * @author Fred
 * @date 2011-4-12
 */
public class TradeServiceImpl implements TradeService {

	private TradeBaseDAO tradeBaseDAO;

	private TradeOrderDAO tradeOrderDAO;

	public List<TradeOrderDTO> queryTradeListByOrderNoStr(String tradeOrderNoStr)
			throws BusinessException, NumberFormatException {
		if (StringUtils.isEmpty(tradeOrderNoStr))
			return null;
		String[] orderNoArray = tradeOrderNoStr.split("|");
		List<TradeOrderDTO> listTradeOrderDTO = new ArrayList<TradeOrderDTO>(
				orderNoArray.length);
		TradeOrderDTO tradeOrderDTO;
		for (String orderNo : orderNoArray) {
			tradeOrderDTO = queryTradeListByOrderNo(Long.valueOf(orderNo));
			if (null == tradeOrderDTO)
				continue;
			listTradeOrderDTO.add(tradeOrderDTO);
		}
		return listTradeOrderDTO;
	}

	@Override
	public TradeOrderDTO queryTradeListByOrderNo(Long tradeOrderNo)
			throws BusinessException, NumberFormatException {

		TradeOrder tradeOrder = (TradeOrder) tradeOrderDAO.findById(Long.valueOf(tradeOrderNo));
		return BeanConvertUtil.convert(TradeOrderDTO.class, tradeOrder);
	}

	public TradeBaseDTO queryTradeByTradeOrderNo(Long tradeOrderNo)
			throws BusinessException {
		if (tradeOrderNo <= 0)
			throw new BusinessException(
					"FI-TradeServiceImpl-queryTradeByTradeOrderNo:参数错误",
					ExceptionCodeEnum.ILLEGAL_PARAMETER);
		TradeOrder tradeOrder = (TradeOrder) tradeOrderDAO.findById(tradeOrderNo);
		if (null == tradeOrder)
			throw new BusinessException(
					"FI-TradeServiceImpl-queryTradeByTradeOrderNo:查询结果异常",
					ExceptionCodeEnum.QUERY_COUNT_DB_ERROR);

		TradeBase tradeBase = tradeBaseDAO
				.findById(tradeOrder.getTradeBaseNo());
		if (null == tradeBase)
			throw new BusinessException(
					"FI-TradeServiceImpl-queryTradeByTradeOrderNo:查询结果异常",
					ExceptionCodeEnum.QUERY_COUNT_DB_ERROR);

		return this.constructOrderDTO(tradeOrder, tradeBase);
	}

	/**
	 * 将TradeOrder和TradeBase拼装成TradeBase
	 * 
	 * @return
	 */
	private TradeBaseDTO constructOrderDTO(TradeOrder tradeOrder,
			TradeBase tradeBase) {
		TradeBaseDTO tradeBaseDTO = new TradeBaseDTO();
		BeanUtils.copyProperties(tradeOrder, tradeBaseDTO);
		BeanUtils.copyProperties(tradeBase, tradeBaseDTO);
		return tradeBaseDTO;
	}

	public void setTradeBaseDAO(TradeBaseDAO tradeBaseDAO) {
		this.tradeBaseDAO = tradeBaseDAO;
	}

	public void setTradeOrderDAO(TradeOrderDAO tradeOrderDAO) {
		this.tradeOrderDAO = tradeOrderDAO;
	}
}
