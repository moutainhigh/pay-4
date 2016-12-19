/**
 * 
 */
package com.pay.fundout.withdraw.service.bankcorp;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fo.bankcorp.dto.BankCorpPaymentRespDTO;
import com.pay.fo.bankcorp.service.TradeStatusNotifyService;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawService;
import com.pay.poss.base.exception.PossException;

/**
 * @author new
 *
 */
public class TradeStatusNotifyServiceImpl implements TradeStatusNotifyService {
	final Logger log = LoggerFactory.getLogger(getClass());
	
	private WithdrawService withdrawService;

	/* (non-Javadoc)
	 * @see com.pay.fo.bankcorp.service.TradeStatusNotifyService#process(com.pay.fo.bankcorp.dto.BankCorpPaymentRespDTO)
	 */
	@Override
	public void process(BankCorpPaymentRespDTO resp) {
		Map<String,String> param = new HashMap<String, String>();
		param.put("orderId", resp.getTradeOrderId().toString());
		String isSuccess = "0";
		if(resp.getOrderStatus()==OrderStatus.PROCESSED_SUCCESS.getValue()){
			isSuccess="1";
		}else if(resp.getOrderStatus()==OrderStatus.PROCESSED_FAILURE.getValue()){
			isSuccess="2";
			param.put("failReason", resp.getFailedReason());
		}else{
			log.error("无效的请求参数:"+resp);
			return;
		}
		param.put("isSuccess", isSuccess);
		try {
			withdrawService.bankBackRdTx(param);
		} catch (PossException e) {
			log.error("银企直连处理发生异常",e);
		}

	}

	/**
	 * @param withdrawService the withdrawService to set
	 */
	public void setWithdrawService(WithdrawService withdrawService) {
		this.withdrawService = withdrawService;
	}
	
	

}
