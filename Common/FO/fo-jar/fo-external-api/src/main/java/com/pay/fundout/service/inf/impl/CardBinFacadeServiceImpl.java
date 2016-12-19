/**
 * 
 */
package com.pay.fundout.service.inf.impl;

import com.pay.fundout.service.inf.CardBinFacadeService;
import com.pay.poss.service.adapter.AbstractExternalAdapter;

/**
 * @author NEW
 *
 */
public class CardBinFacadeServiceImpl extends AbstractExternalAdapter implements CardBinFacadeService {

	@Override
	public boolean validateBankAcctCode(final String bankAcctCode, final String bankCode) {
		//return !cardBinService.isCreditCard(bankCode, bankAcctCode);
		
		return true;
	}
}
