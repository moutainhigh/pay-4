/**
 * 
 */
package com.pay.fo.bankcorp.service;

import com.pay.fo.bankcorp.dto.BankCorpQueryBalanceRespDTO;

/**
 * @author new
 *
 */
public interface BankAccBalanceNotifyService {
	
	/**
	 * 通知处理
	 * @param resp
	 */
	void process(BankCorpQueryBalanceRespDTO resp);

	

}
