package com.pay.pe.accumulated.chargebackentry.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.service.account.AccountBalanceHandlerService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.dto.CalFeeDetailDto;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.dto.CalFeeRequestDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.pe.accumulated.chargebackentry.dao.ChargeBackEntryDao;
import com.pay.pe.accumulated.chargebackentry.dto.ChargeBackEntryDto;
import com.pay.pe.accumulated.chargebackentry.service.ChargeBackService;
import com.pay.pe.accumulated.deal.dao.PossDealDao;
import com.pay.pe.dto.DealDto;

public class ChargeBackServiceImpl implements ChargeBackService {

	private PossDealDao 		possDealDao;
	private ChargeBackEntryDao 	entryDao;	
	private AccountBalanceHandlerService 	accountUpdateService;
	private AccountQueryService 			accountQueryService;
	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public boolean doUpdateBalance(Long voucherCode,Integer dealType) {
		try {
			CalFeeReponseDto calFeeReponseDto=buildUpdateBalanceRequest(voucherCode);
			accountUpdateService.doUpdateAcctBalanceRntx(calFeeReponseDto, dealType);
		} catch (MaAcctBalanceException e) {
			if(e.getErrorEnum().getErrorCode()==ErrorExceptionEnum.BALANCE_DEAL_SUBMIT_AGIAN.getErrorCode()){
				entryDao.updateChargeBackLogStatus(voucherCode, 1);
				log.error("累计扣费失败,余额更新重复提交 : "+e);
			}else{
				log.error("累计扣费失败 : "+e);
			}
			return false;
		}catch (Exception e) {
			log.error("累计扣费失败 : "+e);
		}
		entryDao.updateChargeBackLogStatus(voucherCode, 1);
		log.error("累计扣费成功 : ");
		return true;
	}
	
	@Override
	public CalFeeReponseDto buildUpdateBalanceRequest(Long voucherCode) throws MaAccountQueryUntxException, NumberFormatException {
		
		DealDto dealDto=possDealDao.selectDealByVoucherCode(voucherCode);
		CalFeeReponseDto calFeeReponseDto =new CalFeeReponseDto();
		calFeeReponseDto.setPayeeFee(dealDto.getPayeeFee());
		calFeeReponseDto.setPayerFee(dealDto.getPayerFee());
		calFeeReponseDto.setPayeePriceStrategyCode(dealDto.getPayeePriceStrategy());
		calFeeReponseDto.setPayerPriceStrategyCode(dealDto.getPayerPriceStrategy());
		calFeeReponseDto.setHasCaculatedPrice(dealDto.getHasCaculatedPrice());
		calFeeReponseDto.setVoucherCode(dealDto.getVoucherCode());
		CalFeeRequestDto calFeeRequestDto=generateCalFeeRequestDto(dealDto);
		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);		
		List<ChargeBackEntryDto> entryList=entryDao.selectAccumulatedChargebackEntry(voucherCode);
		List<CalFeeDetailDto> calFeeDetailDtos = new ArrayList<CalFeeDetailDto>(entryList.size());
		if(null!=entryList && entryList.size()>0){
			for(ChargeBackEntryDto entryDto:entryList){
				calFeeDetailDtos.add(generateCalFeeDetailDto(entryDto));
			}
		}
		calFeeReponseDto.setCalFeeDetailDtos(calFeeDetailDtos);
	
		
		return calFeeReponseDto;
	}
	
	private String getAcctCode(Long memberCode,int acctType) throws MaAccountQueryUntxException{
		AcctAttribDto dto=accountQueryService.doQueryAcctAttribNsTx(memberCode,10);
		if(null!=dto){
			 return dto.getAcctCode();
		}
		return null;
	}
	
	public CalFeeRequestDto generateCalFeeRequestDto(DealDto dealDto) throws MaAccountQueryUntxException, NumberFormatException{
		CalFeeRequestDto dto=new CalFeeRequestDto();
		dto.setAmount(dealDto.getAmount());
		dto.setDealCode(dealDto.getDealCode());
		dto.setExchangeRate(dealDto.getExchangeRate());
		dto.setOrderAmount(dealDto.getOrderAmount());
		dto.setOrderCode(dealDto.getOrderCode());
		////dto.setOrderId(dealDto.getOrderId());
		dto.setPayee(dealDto.getPayee());
		dto.setPayeeAcctType(dealDto.getPayeeAcctType());
		dto.setPayeeCurrencyCode(dealDto.getPayeeCurrencyCode());
		String payerMemberAcctCode=dealDto.getPayerMemberAcctCode();
		if(null!=payerMemberAcctCode){
			dto.setPayerFullMemberAcctCode(getAcctCode(Long.parseLong(payerMemberAcctCode.substring(0,payerMemberAcctCode.length()-2)), 10));
		}
		dto.setPayeeMemberAcctCode(dealDto.getPayeeMemberAcctCode());		
		dto.setPayeeOrgCode(dealDto.getPayeeOrgCode());
		dto.setPayeeOrgType(dealDto.getPayeeOrgType());
		dto.setPayeeServiceLevel(dealDto.getPayeeServiceLevel());		
		dto.setPayer(dealDto.getPayer());
		dto.setPayerAcctType(dealDto.getPayerAcctType());
		dto.setPayerCurrencyCode(dealDto.getPayerCurrencyCode());
		
		dto.setPayeeCurrencyCode(dealDto.getPayeeCurrencyCode());
		String payeeMemberAcctCode=dealDto.getPayerMemberAcctCode();
		if(null!=payeeMemberAcctCode){
			dto.setPayeeFullMemberAcctCode(getAcctCode(Long.parseLong(payeeMemberAcctCode.substring(0,payeeMemberAcctCode.length()-2)), 10));
		}
		dto.setPayerMemberAcctCode(dealDto.getPayerMemberAcctCode());		
		dto.setPayerOrgCode(dealDto.getPayerOrgCode());
		dto.setPayerOrgType(dealDto.getPayerOrgType());
		dto.setPayerServiceLevel(dealDto.getPayerServiceLevel());		
		dto.setRequestDate(Calendar.getInstance().getTime());
		dto.setPayMethod(dealDto.getPayMethod());
		dto.setSubmitAcctCode(dealDto.getSubmitAcctCode());
		
		return dto;
	}
	
	private CalFeeDetailDto generateCalFeeDetailDto(ChargeBackEntryDto entryDto){
		CalFeeDetailDto calFeeDetailDto=new CalFeeDetailDto();
		calFeeDetailDto.setAcctcode(entryDto.getAcctCode());
		calFeeDetailDto.setCrdr(entryDto.getCrdr());
		calFeeDetailDto.setCreatedate(entryDto.getCreationDate());
		calFeeDetailDto.setCurrencyCode(entryDto.getCurrencyCode());
		calFeeDetailDto.setDealId(entryDto.getDealid());
		calFeeDetailDto.setDealType(entryDto.getDealType());
		calFeeDetailDto.setEntrycode(entryDto.getEntryCode());
		calFeeDetailDto.setExchangeRate(entryDto.getExchangeRate());
		calFeeDetailDto.setMaBlanceBy(entryDto.getMablanceBy());
		calFeeDetailDto.setPaymentServiceId(entryDto.getPaymentServiceCode());
		calFeeDetailDto.setStatus(entryDto.getStatus());
		calFeeDetailDto.setText(entryDto.getText());
		calFeeDetailDto.setTransactiondate(entryDto.getTransactionDate());
		calFeeDetailDto.setValue(entryDto.getValue());
		calFeeDetailDto.setVouchercode(entryDto.getVoucherCode());
		return calFeeDetailDto;
	}
	
	public void setPossDealDao(PossDealDao possDealDao) {
		this.possDealDao = possDealDao;
	}

	public void setEntryDao(ChargeBackEntryDao entryDao) {
		this.entryDao = entryDao;
	}

	
	public void setAccountUpdateService(
			AccountBalanceHandlerService accountUpdateService) {
		this.accountUpdateService = accountUpdateService;
	}
	
	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

}
