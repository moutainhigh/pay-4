/**
 * 
 */
package com.pay.base.fi.service.impl;

import com.pay.base.fi.dao.PartnerSettlementOrderDAO;
import com.pay.base.fi.service.PartnerSettlementOrderService;

/**
 * @author PengJiangbo
 *
 */
public class PartnerSettlementOrderServiceImpl implements
		PartnerSettlementOrderService {

	private PartnerSettlementOrderDAO partnerSettlementOrderDAO ;
		
	/* (non-Javadoc)
	 * @see com.pay.base.fi.service.PartnerSettlementOrderService#queryAmountByMemberCodeAndCurrency(java.lang.Long, java.lang.String)
	 */
	@Override
	public Long queryAmountByMemberCodeAndCurrency(final Long memberCode,
			final String currency) {
		return this.partnerSettlementOrderDAO.queryAmountByMemberCodeAndCurrency(memberCode, currency) ;
	}

	public void setPartnerSettlementOrderDAO(
			final PartnerSettlementOrderDAO partnerSettlementOrderDAO) {
		this.partnerSettlementOrderDAO = partnerSettlementOrderDAO;
	}

}
