/**
 *  File: MassPayToBankService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-28     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.orderconsistency;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportBaseDTO;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportDetailDTO;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankOrderDTO;
import com.pay.poss.base.exception.PossException;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public interface OrderMassPayToBankService {

	public List getMasspayToBankOrder(Map params);

	public List getDetail(Map params);

	@Deprecated
	public void createSingleOrderRnTx(MassPaytobankImportDetailDTO detail,
			MassPaytobankImportBaseDTO base, MassPaytobankOrderDTO order,
			String operator) throws PossException;

	/**
	 * 
	 * @param batchpaymentOrderId
	 * @param operator
	 * @throws PossException
	 */
	public void createSingleOrderRnTx(String uploadSeq,
			String batchPaymentOrderId, String operator) throws PossException;
}
