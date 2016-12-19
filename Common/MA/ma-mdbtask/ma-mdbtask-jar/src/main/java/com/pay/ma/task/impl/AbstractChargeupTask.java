/**
 * modifyHistory:
 *  2016-08-09 nico.shao queryBalanceEntryInfo(.....,voucherCode) 增加了参数voucherCode
 */
package com.pay.ma.task.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.ma.chargeup.dto.BalanceDealDto;
import com.pay.ma.chargeup.dto.BalanceEntryDto;
import com.pay.ma.chargeup.service.BalanceDealService;
import com.pay.ma.chargeup.service.BalanceEntryService;
import com.pay.ma.commmon.ChargeUpStatusEnum;
import com.pay.pe.service.PaymentDetailDto;
import com.pay.pe.service.PaymentReqDto;
import com.pay.pe.service.PaymentResponseDto;

/**
 * 记账通用类
 * 
 * @author Administrator
 * 
 */
public abstract class AbstractChargeupTask extends AbstractTask {

	private BalanceDealService balanceDealService;

	private BalanceEntryService balanceEntryService;

	/**
	 * 根据状态查询deal信息
	 * 
	 * @param chargeUpStatusEnum
	 * @return
	 */
	protected List<BalanceDealDto> queryBalanceChargeupWithEnum(ChargeUpStatusEnum chargeUpStatusEnum) {
		List<BalanceDealDto> balanceDealDtos = this.balanceDealService.callQueryBalanceChargeup(chargeUpStatusEnum.getCode());
		if (balanceDealDtos == null) {
			return new ArrayList<BalanceDealDto>(0);
		}
		return balanceDealDtos;

	}

	protected boolean handlerUpdateDealChargeupStatus(String serialNo, Integer dealCode, ChargeUpStatusEnum chargeUpStatusEnum) {
		boolean result = this.balanceDealService.callUpdateBalanceDealStatus(serialNo, dealCode, chargeUpStatusEnum.getCode());
		return result;
	}

	/**
	 * 查询分录的信息
	 * 
	 * @param serialNo
	 * @param dealCode
	 * @return
	 */
	
	protected List<BalanceEntryDto> queryBalanceEntryInfo(String serialNo, Integer dealCode,Long voucherCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("serialNo", serialNo);
		map.put("dealCode", dealCode);
		//add by nico.shao 2016-08-09
		if((voucherCode!=null) &&(voucherCode >0)){
			map.put("voucherCode", voucherCode);
		}
		//end 2016-08-09
		
		List<BalanceEntryDto> balanceEntryDtos = this.balanceEntryService.queryBalanceEntryWithSerialNo(map);
		if (balanceEntryDtos == null) {
			return new ArrayList<BalanceEntryDto>(0);
		}
		return balanceEntryDtos;

	}

	protected PaymentResponseDto makeUpCalFeeReponse(BalanceDealDto balanceDealDto, List<BalanceEntryDto> balanceEntryDtos) {
		PaymentResponseDto calFeeReponse = new PaymentResponseDto();
		// fee
		calFeeReponse.setPayeeFee(balanceDealDto.getPayeeFee());
		calFeeReponse.setPayerFee(balanceDealDto.getPayerFee());
		//calFeeReponse.setHasCaculatedPrice(balanceDealDto.getHasCaculatedPrice() == 1 ? true : false);
		// calFeeRequest
		PaymentReqDto calFeeRequest = new PaymentReqDto();
		// base
		calFeeRequest.setAmount(balanceDealDto.getAmount());
		calFeeRequest.setDealCode(balanceDealDto.getDealCode());
		calFeeRequest.setOrderAmount(balanceDealDto.getOrderAmount());
		calFeeRequest.setOrderId(balanceDealDto.getOrderId());
		calFeeRequest.setExchangeRate(balanceDealDto.getExchangeRate());
		calFeeRequest.setRequestDate(balanceDealDto.getRequestDate());
		calFeeRequest.setTerminalType(balanceDealDto.getTerminalType());
		calFeeRequest.setSubmitAcctCode(balanceDealDto.getSubmitAcctCode());
		calFeeRequest.setPayMethod(balanceDealDto.getPayMethod());
		// payer
		calFeeRequest.setPayer(balanceDealDto.getPayer());
		calFeeRequest.setPayerAcctType(balanceDealDto.getPayerAcctType());
		calFeeRequest.setPayerCurrencyCode(balanceDealDto.getPayerCurrencyCode());
		calFeeRequest.setPayerFullMemberAcctCode(balanceDealDto.getPayerFullMemberAcctCode());
		calFeeRequest.setPayerMemberAcctCode(balanceDealDto.getPayerMemberAcctCode());
		calFeeRequest.setPayerOrgCode(balanceDealDto.getPayerOrgCode());
		calFeeRequest.setPayerOrgType(balanceDealDto.getPayerOrgType());
		//calFeeRequest.setPayerServiceLevel(balanceDealDto.getPayerServiceLevel());

		// payee
		calFeeRequest.setPayee(balanceDealDto.getPayee());
		calFeeRequest.setPayeeAcctType(balanceDealDto.getPayeeAcctType());
		calFeeRequest.setPayeeCurrencyCode(balanceDealDto.getPayeeCurrencyCode());
		calFeeRequest.setPayeeFullMemberAcctCode(balanceDealDto.getPayeeFullMemberAcctCode());
		calFeeRequest.setPayeeMemberAcctCode(balanceDealDto.getPayeeMemberAcctCode());
		calFeeRequest.setPayeeOrgCode(balanceDealDto.getPayeeOrgCode());
		calFeeRequest.setPayeeOrgType(balanceDealDto.getPayeeOrgType());
		calFeeRequest.setPayeeServiceLevel(balanceDealDto.getPayeeServiceLevel());

		calFeeReponse.setPaymentReq(calFeeRequest);
		List<PaymentDetailDto> calFeeDetails = new ArrayList<PaymentDetailDto>(balanceEntryDtos.size());
		// calfeedetail
		PaymentDetailDto calFeeDetail = null;
		for (BalanceEntryDto balanceEntryDto : balanceEntryDtos) {
			calFeeDetail = new PaymentDetailDto();
			calFeeDetail.setAcctcode(balanceEntryDto.getAcctcode());
			calFeeDetail.setCrdr(balanceEntryDto.getCrdr());
			calFeeDetail.setCreatedate(balanceEntryDto.getCreateDate());
			calFeeDetail.setCurrencyCode(balanceEntryDto.getCurrencyCode());
			calFeeDetail.setDealId(balanceEntryDto.getDealId());
			calFeeDetail.setEntrycode(balanceEntryDto.getEntrycode());
			calFeeDetail.setExchangeRate(balanceEntryDto.getExchangeRate());
			calFeeDetail.setMaBlanceBy(balanceEntryDto.getMaBlanceBy());
			calFeeDetail.setPaymentServiceId(balanceEntryDto.getPaymentServiceId());
			calFeeDetail.setStatus(balanceEntryDto.getStatus());
			calFeeDetail.setText(balanceEntryDto.getText());
			calFeeDetail.setTransactiondate(balanceEntryDto.getTransactiondate());
			calFeeDetail.setValue(balanceEntryDto.getValue());
			calFeeDetail.setVouchercode(balanceDealDto.getVoucherCode());
			calFeeDetails.add(calFeeDetail);

		}
		calFeeReponse.setPaymentDetails(calFeeDetails);
		calFeeReponse.setVoucherCode(balanceDealDto.getVoucherCode());
		return calFeeReponse;
	}

	/**
	 * @param balanceDealService
	 *            the balanceDealService to set
	 */
	public void setBalanceDealService(BalanceDealService balanceDealService) {
		this.balanceDealService = balanceDealService;
	}

	/**
	 * @param balanceEntryService
	 *            the balanceEntryService to set
	 */
	public void setBalanceEntryService(BalanceEntryService balanceEntryService) {
		this.balanceEntryService = balanceEntryService;
	}

}
