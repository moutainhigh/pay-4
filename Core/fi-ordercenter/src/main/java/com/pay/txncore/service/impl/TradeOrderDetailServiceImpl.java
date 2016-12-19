package com.pay.txncore.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.dao.TradeOrderDAO;
import com.pay.txncore.dto.PayLinkDetailDTO;
import com.pay.txncore.dto.TradeOrderDetailDTO;
import com.pay.txncore.dto.TradeOrderDetailForNotifyDTO;
import com.pay.txncore.dto.TradeOrderDetailMpsDTO;
import com.pay.txncore.service.TradeOrderDetailService;

public class TradeOrderDetailServiceImpl implements TradeOrderDetailService{

	private TradeOrderDAO tradeOrderDAO;
	
	public void setTradeOrderDAO(TradeOrderDAO tradeOrderDAO) {
		this.tradeOrderDAO = tradeOrderDAO;
	}

	@Override
	public TradeOrderDetailDTO getTradeOrderDetail(Map<String, String> prameters) {
		List<TradeOrderDetailDTO> details = tradeOrderDAO.getTradeOrderDetail(prameters);
		if(details!=null && !details.isEmpty()){
			long refundedAmount=0;
			for(TradeOrderDetailDTO tod:details){
				if((RefundStatusEnum.SUCCESS.getCode()+"").equals(tod.getRefundStatus())){
					refundedAmount+=tod.getRefundAmount();
				}
			}
			TradeOrderDetailDTO tradeOrderDetailDTO = details.get(details.size()-1);
			tradeOrderDetailDTO.setRefundAmount(refundedAmount);
			return tradeOrderDetailDTO;
		}
		return null;
	}

	/***
	 * 通过商户会员号，查找指定订单号，以后交易记录信息，以邮件方式发送给商户 
	 * add by davis.guo at 2016-09-03
	 * @param prameters
	 * @return
	 */
	@Override
	public List<TradeOrderDetailForNotifyDTO> getTradeOrderDetailByPartner(Map<String, String> prameters) {
		List<TradeOrderDetailForNotifyDTO> details = tradeOrderDAO.findByCriteria("getTradeOrderDetailByPartner", prameters);
		if(details!=null && !details.isEmpty()){
			return details;
		}
		return null;
	}

	@Override
	public TradeOrderDetailMpsDTO getTradeOrderDetailMps(TradeOrderDetailMpsDTO detailMpsDTO) {
		return tradeOrderDAO.getTradeOrderDetailMps(detailMpsDTO);
	}
	
	@Override
	public PayLinkDetailDTO getPayLinkDetail(Map<String, String> prameters) {
		List<PayLinkDetailDTO> payLinkDetailList = tradeOrderDAO.getPayLinkDetail(prameters);
		return payLinkDetailList!=null && !payLinkDetailList.isEmpty()?payLinkDetailList.get(0):null;
	}


}
