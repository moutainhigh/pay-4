/**
 * 
 */
package com.pay.fundout.service.inf;

/**
 * @author NEW
 *
 */
public interface CardBinFacadeService {

	/**
	 * 验证卡号是否是有效的借记卡卡号
	 * @param bankAcctCode
	 * @param bankCode
	 * @return
	 */
	boolean validateBankAcctCode(String bankAcctCode,String bankCode);
}
