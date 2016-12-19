package com.pay.pe.service.payment.verification.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.dto.DealDto;
import com.pay.pe.exception.ErrorCodeType;
import com.pay.pe.helper.COMMONORDERSTATUS;
import com.pay.pe.helper.CRDRType;
import com.pay.pe.helper.DealStatus;
import com.pay.pe.service.accounting.AccountEntryService;
import com.pay.pe.service.payment.common.LogUtil;
import com.pay.pe.service.payment.verification.PaymentVerificationService;


public class PaymentVerificationServiceImpl implements
		PaymentVerificationService {
	
	private Log logger = LogFactory.getLog(getClass());

	private AccountEntryService accountEntryService;
	

//	private MemberAcctSpecService memberAcctSpecService;
	
	public void setAccountEntryService(AccountEntryService accountEntryService) {
		this.accountEntryService = accountEntryService;
	}
//	public void setMemberAcctSpecService(MemberAcctSpecService memberAcctSpecService) {
//		this.memberAcctSpecService = memberAcctSpecService;
//	}

	/**
	 * 
	 */
	public boolean doVerifyBeforeNotifyMerchant(DealDto deal) {
		/*boolean b = validatePaymentPostedSuccess(deal);
		if (!b) {
			logger.info("!verify before notify merchant failed: orderSeqId = " + deal.getOrder().getSequenceId() + ".");
			logger.info("!verify before notify merchant failed: dealstatus = " + deal.getDealStatus() + ", orderstatus = " + deal.getOrder().getOrderStatus() + "..");
		}
		return b;*/
		return true;
	}

	/**
	 * 
	 */
	public boolean validatePaymentPostedSuccess(DealDto deal) {
		Map<String,String> mainIden = new HashMap<String,String>();
		mainIden.put("OrderCode", (deal.getOrder().getOrderType()==null?null:deal.getOrder().getOrderType().toString()));
		mainIden.put("orderNo", deal.getOrder().getOrderId());
		mainIden.put("DealCode", (deal.getDealCode()==null?"":deal.getDealCode().toString()));
		mainIden.put("MerchantId", "(payeracctcode="+deal.getPayerAcctCode()+";payeeacctcode="+deal.getPayeeAcctCode()+")");
		logger.info(LogUtil.wrapLog("validatePaymentPostedSuccess",
				"Start",
				deal.getDealId(),
				mainIden,
				null,
				null,null,null));
		if (deal.getDealStatus() == DealStatus.posted.getValue() 
				&& deal.getOrder().getOrderStatus() == COMMONORDERSTATUS.DealSuccess.getValue()) {
			List<AccountEntryDTO> entries = 
				accountEntryService.getAccountEntryByDealId(deal.getDealId());
			if (entries != null 
					&& entries.size() > 0 
					&& validateCrDrBalance(entries)) {
				logger.info(LogUtil.wrapLog("validatePaymentPostedSuccess",
						"Success",
						deal.getDealId(),
						mainIden,
						null,
						null,null,null));
				mainIden = null;
				return true;
			}
		}
		logger.info(LogUtil.wrapLog("validatePaymentPostedSuccess",
				"Fail",
				deal.getDealId(),
				mainIden,
				null,
				null,null,null));
		mainIden = null;
		return false;
	}
	
	private boolean validateCrDrBalance(List<AccountEntryDTO> entries) {
		StringBuffer entries_str = new StringBuffer();
		for(AccountEntryDTO accountEntryDTO:entries){
			entries_str.append("[")
					.append(accountEntryDTO.toString())
					.append("]");
		}
		Map<String,String> addition = new HashMap<String,String>();
		addition.put("entries", entries_str.toString());
		logger.info(LogUtil.wrapLog("validateCrDrBalance",
				"Start",
				null,
				null,
				addition,
				null,null,null));
		long crAmount = 0L;
		long drAmount = 0L;
		AccountEntryDTO entry;
		for(int i = 0; i < entries.size(); i++) {
			entry = entries.get(i);
			if (entry.getCrdr() == CRDRType.DEBIT.getValue()) {
				drAmount = drAmount + entry.getValue().longValue();
			} else {
				crAmount = crAmount + entry.getValue().longValue();
			}
		}
		if (Math.abs(drAmount) == Math.abs(crAmount)) {
			logger.info(LogUtil.wrapLog("validateCrDrBalance",
					"Success",
					null,
					null,
					addition,
					null,null,null));
			addition = null;
			return true;
		}
		logger.info(LogUtil.wrapLog("validateCrDrBalance",
				"Fail",
				null,
				null,
				addition,
				null,null,null));
		addition = null;
		return false;
	}

	public boolean validateDealBeforeProcessOrder(DealDto deal) {
		Map<String,String> mainIden = new HashMap<String,String>();
		mainIden.put("OrderCode", (deal.getOrder().getOrderType()==null?null:deal.getOrder().getOrderType().toString()));
		mainIden.put("orderNo", deal.getOrder().getOrderId());
		mainIden.put("DealCode", (deal.getDealCode()==null?"":deal.getDealCode().toString()));
		mainIden.put("MerchantId", "(payeracctcode="+deal.getPayerAcctCode()+";payeeacctcode="+deal.getPayeeAcctCode()+")");
		logger.info(LogUtil.wrapLog("validateDealBeforeProcessOrder",
				"Start",
				deal.getDealId(),
				mainIden,
				null,
				null,null,null));
		boolean payeefrozen = false;
		boolean payerfrozen = false;
		
//		if(deal.getPayeeAcctCode()!=null&&!deal.getPayeeAcctCode().equals("")){
//			payeefrozen = memberAcctSpecService.isFrozen(deal.getPayeeAcctCode());
//		}
//		if(deal.getPayerAcctCode()!=null&&!deal.getPayerAcctCode().equals("")){
//			payerfrozen = memberAcctSpecService.isFrozen(deal.getPayerAcctCode());
//		}
		
		if(!payeefrozen&&!payerfrozen){
			logger.info(LogUtil.wrapLog("validateDealBeforeProcessOrder",
					"Success",
					deal.getDealId(),
					mainIden,
					null,
					null,null,null));
			mainIden = null;
			return true;
		}else{
			logger.info(LogUtil.wrapLog("validateDealBeforeProcessOrder",
					"Exception",
					deal.getDealId(),
					mainIden,
					null,
					null,null,ErrorCodeType.ACCT_FROZENED.getDesc()+
					 " PayeeAcctCode="+deal.getPayeeAcctCode()+" PayerAcctCode="+deal.getPayerAcctCode()));
			mainIden = null;
			return false;
		}
	}

}
