package com.pay.pe.accumulated.chargeback.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.service.account.AccountBalanceHandlerService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.dto.CalFeeDetailDto;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.dto.CalFeeRequestDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.pe.accumulated.chargebacklog.dto.ChargeBackLogDto;
import com.pay.pe.accumulated.chargebacklog.service.ChargeBackLogService;
import com.pay.pe.accumulated.resources.dto.AccumulatedResourcesDto;
import com.pay.pe.service.PaymentDetailDto;
import com.pay.pe.service.PaymentReqDto;
import com.pay.pe.service.PaymentResponseDto;
import com.pay.util.BeanConvertUtil;

/**
 * 
 * @ClassName: AbstractChargeBackService
 * @Description: 生成pe，ma需要的参数
 * @author cf
 * @date Mar 8, 2012 9:35:23 AM
 * 
 */
public abstract class AbstractChargeBackService {

	private AccountBalanceHandlerService accountUpdateService;
	private ChargeBackLogService chargeBackLogService;
	private AccountQueryService accountQueryService;

	public String getAcctCodeByMemberCode(Long memberCode)
			throws MaAccountQueryUntxException {
		AcctAttribDto dto = accountQueryService.doQueryAcctAttribNsTx(
				memberCode, 10);
		if (null != dto)
			return dto.getAcctCode();
		return null;
	}

	public boolean doUpdateBalance(PaymentReqDto calFeeRequest,
			PaymentResponseDto calFeeRespone, Integer dealType,
			ChargeBackLogDto logDto) {
		try {
			accountUpdateService.doUpdateAcctBalanceRntx(
					generateCalFeeReponseDto(calFeeRequest, calFeeRespone),
					dealType);
			logDto.setStatus(1);
		} catch (MaAcctBalanceException e) {
			logDto.setStatus(2);
			logDto.setErrorMsg(e.getErrorEnum().getMessage());
			e.printStackTrace();
		}
		saveChargeBackLog(logDto, calFeeRespone);
		return true;
	}

	public ChargeBackLogDto generateChargeBackLogDto(
			PaymentResponseDto calFeeRespone) {
		ChargeBackLogDto logDto = new ChargeBackLogDto();
		logDto.setVoucherCode("" + calFeeRespone.getVoucherCode());
		return logDto;
	}

	public boolean saveChargeBackLog(ChargeBackLogDto chargeBackLogDto,
			PaymentResponseDto calFeeRespone) {
		return chargeBackLogService.saveChargeBackRnTx(chargeBackLogDto,
				calFeeRespone);
	}

	/**
	 * @Title: generatePaymentReqDto
	 * @Description: 生成pe需要的参数
	 * @param @return 设定文件
	 * @return PaymentReqDto 返回类型
	 * @throws
	 */
	public PaymentReqDto generatePaymentReqDto(AccumulatedResourcesDto dto) {
		PaymentReqDto reqDto = new PaymentReqDto();
		reqDto.setAmount(0L);
		reqDto.setDealCode(dto.getDealCode());
		reqDto.setOrderAmount(0L);
		reqDto.setOrderCode(dto.getOrderType());
		reqDto.setPayMethod(1);
		reqDto.setRequestDate(Calendar.getInstance().getTime());
		return reqDto;
	}

	/**
	 * @Title: generateCalFeeReponseDto
	 * @Description: 生成ma需要的参数
	 * @param @return 设定文件
	 * @return CalFeeReponseDto 返回类型
	 * @throws
	 */
	CalFeeReponseDto generateCalFeeReponseDto(PaymentReqDto calFeeRequest,
			PaymentResponseDto calFeeRespone) {
		return this.buildUpdateBalanceRequest(calFeeRequest, calFeeRespone);
	}

	private CalFeeReponseDto buildUpdateBalanceRequest(
			PaymentReqDto calFeeRequest, PaymentResponseDto calFeeRespone) {
		CalFeeReponseDto calFeeReponseDto = BeanConvertUtil.convert(
				CalFeeReponseDto.class, calFeeRespone);
		CalFeeRequestDto calFeeRequestDto = BeanConvertUtil.convert(
				CalFeeRequestDto.class, calFeeRequest);
		calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);

		List<CalFeeDetailDto> calFeeDetailDtos = new ArrayList<CalFeeDetailDto>();
		List<PaymentDetailDto> calFeeDetails = calFeeRespone
				.getPaymentDetails();
		if (null != calFeeDetails && !calFeeDetails.isEmpty()) {
			for (PaymentDetailDto calFeeDetail : calFeeDetails) {
				CalFeeDetailDto calFeeDetailDto = BeanConvertUtil.convert(
						CalFeeDetailDto.class, calFeeDetail);
				calFeeDetailDtos.add(calFeeDetailDto);
			}
		}
		calFeeReponseDto.setCalFeeDetailDtos(calFeeDetailDtos);
		return calFeeReponseDto;
	}

	public void setAccountUpdateService(
			AccountBalanceHandlerService accountUpdateService) {
		this.accountUpdateService = accountUpdateService;
	}

	public void setChargeBackLogService(
			ChargeBackLogService chargeBackLogService) {
		this.chargeBackLogService = chargeBackLogService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}
}
