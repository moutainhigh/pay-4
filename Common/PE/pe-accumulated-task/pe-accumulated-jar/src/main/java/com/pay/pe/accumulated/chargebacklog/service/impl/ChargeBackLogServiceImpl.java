package com.pay.pe.accumulated.chargebacklog.service.impl;

import java.util.List;

import com.pay.pe.accumulated.chargebackentry.dao.ChargeBackEntryDao;
import com.pay.pe.accumulated.chargebackentry.dto.ChargeBackEntryDto;
import com.pay.pe.accumulated.chargebacklog.dao.ChargeBackLogDao;
import com.pay.pe.accumulated.chargebacklog.dto.ChargeBackLogDto;
import com.pay.pe.accumulated.chargebacklog.service.ChargeBackLogService;
import com.pay.pe.service.PaymentDetailDto;
import com.pay.pe.service.PaymentResponseDto;

public class ChargeBackLogServiceImpl implements ChargeBackLogService {

	private ChargeBackLogDao 	chargeBackLogDao;
	private ChargeBackEntryDao 	chargeBackEntryDao;


	@Override
	public boolean saveChargeBackRnTx(ChargeBackLogDto chargeBackLogDto,PaymentResponseDto calFeeRespone) {
		chargeBackLogDao.create(chargeBackLogDto);
		List<PaymentDetailDto>  detailList=calFeeRespone.getPaymentDetails();
		for(PaymentDetailDto dto:detailList){
			chargeBackEntryDao.create(generateChargeBackEntry(dto));
		}
		return true;
	}
	
	private ChargeBackEntryDto generateChargeBackEntry(PaymentDetailDto detailDto) {
		ChargeBackEntryDto dto=new ChargeBackEntryDto();		
		dto.setAcctCode(detailDto.getAcctcode());
		dto.setCrdr(detailDto.getCrdr());
		dto.setCreationDate(detailDto.getCreatedate());
		dto.setCurrencyCode(detailDto.getCurrencyCode());
		dto.setDealid(detailDto.getDealId());
		dto.setEntryCode(detailDto.getEntrycode());
		dto.setDealType(detailDto.getDealType());
		dto.setExchangeRate(detailDto.getExchangeRate());
		dto.setMablanceBy(detailDto.getMaBlanceBy());
		dto.setPaymentServiceCode(detailDto.getPaymentServiceId());
		dto.setStatus(detailDto.getStatus());
		dto.setText(detailDto.getText());
		dto.setTransactionDate(detailDto.getTransactiondate());
		dto.setValue(detailDto.getValue());
		dto.setVoucherCode(detailDto.getVouchercode());
		return dto;		
		
	}
	public void setChargeBackLogDao(ChargeBackLogDao chargeBackLogDao) {
		this.chargeBackLogDao = chargeBackLogDao;
	}

	public void setChargeBackEntryDao(ChargeBackEntryDao chargeBackEntryDao) {
		this.chargeBackEntryDao = chargeBackEntryDao;
	}

}
