/**
 * 
 */
package com.pay.fo.bankcorp.service;

import com.pay.fo.bankcorp.dto.BankCorpPaymentReqDTO;
import com.pay.fo.bankcorp.dto.BankCorpPaymentRespDTO;
import com.pay.fo.bankcorp.dto.BankCorpQueryBalanceReqDTO;
import com.pay.fo.bankcorp.dto.BankCorpQueryBalanceRespDTO;

/**
 * @author new
 *
 */
public interface BankCorporateProcessService {
	/**
	 * 付款
	 * @param req
	 * @return
	 */
	BankCorpPaymentRespDTO doPayment(BankCorpPaymentReqDTO req);
	
	/**
	 * 交易状态查询
	 * @param req
	 * @return
	 */
	BankCorpPaymentRespDTO doQuery(BankCorpPaymentReqDTO req);
	
	/**
	 * 余额查询
	 * @param req
	 * @return
	 */
	BankCorpQueryBalanceRespDTO doQueryBalance(BankCorpQueryBalanceReqDTO req);
	
	

}
