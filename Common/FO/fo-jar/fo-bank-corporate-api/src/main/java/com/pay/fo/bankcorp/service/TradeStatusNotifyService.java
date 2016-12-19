/**
 * 
 */
package com.pay.fo.bankcorp.service;

import com.pay.fo.bankcorp.dto.BankCorpPaymentRespDTO;

/**
 * @author new
 *
 */
public interface TradeStatusNotifyService {
	/**
	 * 通知处理
	 * @param resp
	 */
	void process(BankCorpPaymentRespDTO resp); 
}
