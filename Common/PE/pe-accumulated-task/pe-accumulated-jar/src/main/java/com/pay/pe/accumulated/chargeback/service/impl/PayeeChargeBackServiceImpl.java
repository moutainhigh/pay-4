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

public class PayeeChargeBackServiceImpl extends AbstractChargeBackService
		implements ChargeBackService {

	private PEService peService;
	private Log log = LogFactory.getLog(this.getClass());

	@Override
	public int getChargeBackmethod() {
		return TakenOnEnum.PAYEE.getCode();
	}

	@Override
	public boolean doAccumulatedChargeBack(AccumulatedResourcesDto dto,
			String acctCode, String orderId) throws Exception {
		PaymentReqDto paymentReqDto = this.generatePaymentReqDto(dto);
		paymentReqDto.setPayerFee(0L);
		paymentReqDto.setHasCaculatedPayerPrice(true);
		paymentReqDto.setPayee(acctCode.substring(0, acctCode.length() - 2));
		paymentReqDto.setPayeeAcctType(Integer.valueOf(acctCode.substring(
				acctCode.length() - 2, acctCode.length())));
		paymentReqDto.setPayeeFullMemberAcctCode(getAcctCodeByMemberCode(Long.parseLong(acctCode.substring(0,acctCode.length() - 2))));
		paymentReqDto.setPayeeMemberAcctCode(acctCode);
		paymentReqDto.setPayeeOrgType(OrgType.MEMBER.getValue());
		paymentReqDto.setOrderId(orderId);
		paymentReqDto.setPayerOrgType(OrgType.INNER.getValue());
		PaymentResponseDto responseDto = peService.processPayment(paymentReqDto);
		if (log.isDebugEnabled()) {
			log.debug(responseDto.toString());
		}
		ChargeBackLogDto logDto = generateChargeBackLogDto(responseDto);
		logDto.setFee(responseDto.getPayeeFee());
		logDto.setMemberCode(Long.parseLong(acctCode.substring(0,acctCode.length() - 2)));
		logDto.setPriceStrategyCode(new Integer(responseDto.getPayeePriceStrategyCode().intValue()));
		logDto.setVoucherCode("" + responseDto.getVoucherCode());
		logDto.setDealType(dto.getDealType());
		return doUpdateBalance(paymentReqDto, responseDto, dto.getDealType(),logDto);
	}

	public void setPeService(PEService peService) {
		this.peService = peService;
	}

}
