package com.pay.pe.manualbooking.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.manualbooking.dto.VouchDataDetailDto;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchDataPostingException;
import com.pay.pe.manualbooking.service.VouchAccountingService;

/**
 * 
 * 手工记账申请记帐服务实现
 */
public class VouchAccountingServiceImpl implements VouchAccountingService {
	
	private static final Log LOG = LogFactory.getLog(VouchAccountingServiceImpl.class);
	/**
	 * 明细比较器，按账号。
	 */
	protected static Comparator<VouchDataDetailDto> c = new Comparator<VouchDataDetailDto>() {

		public int compare(VouchDataDetailDto o1, VouchDataDetailDto o2) {
			BigDecimal b1 = new BigDecimal(o1.getAccountCode());
			BigDecimal b2 = new BigDecimal(o2.getAccountCode());
			
			return b1.compareTo(b2);
		}
		
	};
	
//	
	public String posting(VouchDataDto vouchDataDto) throws VouchDataPostingException {
		return "" ;
//		LOG.info("Start");
//		List<VouchDataDetailDto> oldDetailDtos = vouchDataDto.getVouchDataDetails();
//		
//		List<VouchDataDetailDto> detailDtos = new ArrayList<VouchDataDetailDto>();
//		detailDtos.addAll(oldDetailDtos);
//		
//		Collections.sort(detailDtos, c);
//		
//		List<AccountEntryDTO> accountEntryDTOs = convertDataForPosting(detailDtos);
//		
//		long voucherCode = 0;
//		try {
//			voucherCode = this.postingService.postingByProc(accountEntryDTOs);
//			LOG.debug("Vouch code : " + voucherCode);
//			
//			this.accountDairyService.posting(voucherCode);
//		} catch (AccountNotFoundException e) {
//			LOG.debug("Posting fails, because account not found!");
//			throw new VouchDataPostingException("posting fails, because account not found", e);
//		} catch (Exception e) {
//			LOG.debug("Posting fails, because of other reason!");
//			throw new VouchDataPostingException("posting fails, because of other reason", e); 
//		}
//		
//		if (voucherCode == 0) {
//			LOG.debug("Dr/Cr not balance!");
//			throw new VouchDataPostingException("Dr/Cr not balance");
//		}
//		
//		LOG.info("End");
//		return voucherCode + "";
//		
	}
	
//	protected List<AccountEntryDTO> convertDataForPosting(List<VouchDataDetailDto> detailDtos) {
//		LOG.info("Start");
//		
//		List<AccountEntryDTO> accountEntryDTOs = new ArrayList<AccountEntryDTO>();
//		Date date = new Date();
//		int entryCode = 1;
//		for (VouchDataDetailDto detailDto : detailDtos) {
//			AccountEntryDTO accountEntryDTO = new AccountEntryDTO();
//			
//			accountEntryDTO.setAcctcode(detailDto.getAccountNo());
//			accountEntryDTO.setCrdr(detailDto.getCrdr().longValue());
//			accountEntryDTO.setCreatedate(null);
//			accountEntryDTO.setCurrencyCode(null);
//			accountEntryDTO.setDealId("");
//			accountEntryDTO.setEntrycode(entryCode);
//			accountEntryDTO.setExchangeRate(null);
//			//accountEntryDTO.setPaymentServiceId(FRCTRXPAYMENTSERVICECODE.PAYMENTSERVICECODE);
//			accountEntryDTO.setPaymentServiceId(0);
//			accountEntryDTO.setStatus(1);
//			accountEntryDTO.setText(detailDto.getRemark());
//			
//			BigDecimal value = new BigDecimal(detailDto.getAmount());
//			value = value.multiply(new BigDecimal(1000));
//			accountEntryDTO.setValue(value.longValue());
//			
//			accountEntryDTO.setVouchercode(null);
//			
//			accountEntryDTO.setTransactiondate(date);
//			
//			accountEntryDTOs.add(accountEntryDTO);
//			entryCode++;
//		}
//		
//		LOG.info("End");
//		return accountEntryDTOs;
//	}

}
