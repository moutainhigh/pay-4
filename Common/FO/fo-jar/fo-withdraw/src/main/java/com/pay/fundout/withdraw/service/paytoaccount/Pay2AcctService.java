package com.pay.fundout.withdraw.service.paytoaccount;

import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;
import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctResponse;
import com.pay.poss.common.accounting.WithdrawBusinessType;

public interface Pay2AcctService {
	public static final String REQUEST_CODE_FOR_APP = WithdrawBusinessType.PAYTOACCT_ORDER_PERSON_O.getBusinessType();
	public static final String REQUEST_CODE_FOR_FO = WithdrawBusinessType.PAYTOACCT_ORDER_PERSON_I.getBusinessType();
	public static final String REQUEST_CODE_FOR_BATCH_FIRST = WithdrawBusinessType.PAYTOACCT_BATCHORDER_REQ_PERSON.getBusinessType();
	
	public static final String FUNDADJUSTMENT_ORDER_REQ = WithdrawBusinessType.FUNDADJUSTMENT_ORDER_REQ.getBusinessType();
	public static final String FUNDADJUSTMENT_ORDER_REJECT = WithdrawBusinessType.FUNDADJUSTMENT_ORDER_FAIL.getBusinessType();
	public static final String FUNDADJUSTMENT_ORDER_PASS = WithdrawBusinessType.FUNDADJUSTMENT_ORDER_SUCC.getBusinessType();
	
	
	// 也可以取WithdrawBusinessType.PAYTOACCT_BATCHORDER_FAIL_PERSON.getBusinessType()
	public static final String REQUEST_CODE_FOR_BATCH_SECOND = WithdrawBusinessType.PAYTOACCT_BATCHORDER_SUCC_PERSON.getBusinessType();

	public Pay2AcctResponse pay2Acct(Pay2AcctOrder order);
	public Pay2AcctOrder findById(Long sequenceId);

}
