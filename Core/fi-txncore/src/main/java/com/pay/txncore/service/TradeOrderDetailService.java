package com.pay.txncore.service;

import java.util.Map;

import com.pay.txncore.dto.PayLinkDetailDTO;
import com.pay.txncore.dto.TradeOrderDetailDTO;
import com.pay.txncore.dto.TradeOrderDetailMpsDTO;

/**
 * 根据商户号，网关订单号，商户订单号
 * @author Bobby Guo
 * @date 2015年10月19日
 */
public interface TradeOrderDetailService {

	public TradeOrderDetailDTO getTradeOrderDetail(Map<String, String> prameters);

	public TradeOrderDetailMpsDTO getTradeOrderDetailMps(TradeOrderDetailMpsDTO detailMpsDTO);
	
	public PayLinkDetailDTO getPayLinkDetail(Map<String, String> prameters);
	
}
