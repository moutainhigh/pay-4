package com.pay.pe.accumulated.chargeback.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.accumulated.chargeback.service.ChargeBackService;
import com.pay.pe.accumulated.chargebacklog.dto.ChargeBackLogDto;
import com.pay.pe.accumulated.resources.dto.AccumulatedResourcesDto;
import com.pay.pe.helper.OrgType;
import com.pay.pe.helper.TakenOnEnum;
import com.pay.pe.service.PEService;
import com.pay.pe.service.PaymentReqDto;
import com.pay.pe.service.PaymentResponseDto;

public class PayerChargeBackServiceImpl extends AbstractChargeBackService
		implements ChargeBackService {

	private PEService peService;
	private final Log log = LogFactory.getLog(this.getClass());

	@Override
	public int getChargeBackmethod() {
		return TakenOnEnum.PAYER.getCode();
	}
	
	@Override
	public boolean doAccumulatedChargeBack(AccumulatedResourcesDto dto,
			String acctCode,String orderId) throws Exception {
		PaymentReqDto paymentReqDto=this.generatePaymentReqDto(dto);
		paymentReqDto.setPayeeFee(0L);
		paymentReqDto.setHasCaculatedPayeePrice(true);		
		paymentReqDto.setPayer(acctCode.substring(0, acctCode.length()-2));
		paymentReqDto.setPayerAcctType(Integer.valueOf(acctCode.substring(acctCode.length()-2,acctCode.length())));
		paymentReqDto.setPayerFullMemberAcctCode(getAcctCodeByMemberCode(Long.parseLong(acctCode.substring(0,acctCode.length() - 2))));
		paymentReqDto.setPayerOrgType(OrgType.MEMBER.getValue());
		paymentReqDto.setPayerMemberAcctCode(acctCode);		
		paymentReqDto.setOrderId(orderId);
		paymentReqDto.setPayeeOrgType(OrgType.INNER.getValue());		
		PaymentResponseDto responseDto=peService.processPayment(paymentReqDto);	
		if(log.isDebugEnabled()){
			log.debug(responseDto.toString());
		}
		ChargeBackLogDto logDto = generateChargeBackLogDto(responseDto);
		logDto.setFee(responseDto.getPayerFee());
		logDto.setMemberCode(Long.parseLong(acctCode.substring(0,acctCode.length() - 2)));
		logDto.setPriceStrategyCode(new Integer(responseDto.getPayerPriceStrategyCode().intValue()));
		logDto.setVoucherCode("" + responseDto.getVoucherCode());
		logDto.setDealType(dto.getDealType());
		return doUpdateBalance(paymentReqDto, responseDto, dto.getDealType(),logDto);

	}

	public void setPeService(PEService peService) {
		this.peService = peService;
	}

}
