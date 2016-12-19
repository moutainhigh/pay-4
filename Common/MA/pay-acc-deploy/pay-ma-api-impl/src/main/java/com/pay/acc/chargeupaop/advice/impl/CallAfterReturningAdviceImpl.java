/**
 * 
 */
package com.pay.acc.chargeupaop.advice.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.pay.acc.chargeupaop.advice.CallAfterReturningAdvice;
import com.pay.acc.chargeupaop.jmsmessage.JmsMessageService;
import com.pay.acc.service.account.dto.CalFeeDetailDto;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.pe.service.CalFeeDetail;
import com.pay.pe.service.CalFeeReponse;
import com.pay.pe.service.CalFeeRequest;
import com.pay.util.BeanConvertUtil;

/**
 * @author Administrator
 * 
 */
public class CallAfterReturningAdviceImpl implements CallAfterReturningAdvice {

	private JmsMessageService jmsMessageService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang
	 * .Object, java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	@Override
	public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {

		if (returnValue == null) {
			return;
		}
		if (returnValue instanceof Integer) {
			Integer result = (Integer) returnValue;
			// 1表示接口的返回成功值，根据现行业务没有做修改，直接有1表示
			if (result.intValue() == 1) {
				for (Object object : args) {
					if (object instanceof CalFeeReponseDto) {
						CalFeeReponseDto calFeeReponseDto = (CalFeeReponseDto) object;
						this.jmsMessageService.sendChargeUpMessage(this.handlerMakeUpCalFeeReponse(calFeeReponseDto));
					}
				}
			}
		}

	}

	/**
	 * @param jmsMessageService
	 *            the jmsMessageService to set
	 */
	public void setJmsMessageService(JmsMessageService jmsMessageService) {
		this.jmsMessageService = jmsMessageService;
	}

	/**
	 * 组装记账对象
	 * 
	 * @param updateBalanceRequestDto
	 * @return
	 */
	private CalFeeReponse handlerMakeUpCalFeeReponse(CalFeeReponseDto updateBalanceRequestDto) {
		CalFeeReponse calFeeReponse = BeanConvertUtil.convert(CalFeeReponse.class, updateBalanceRequestDto);
		CalFeeRequest calFeeRequest = BeanConvertUtil.convert(CalFeeRequest.class, updateBalanceRequestDto.getCalFeeRequestDto());
		calFeeReponse.setCalFeeRequest(calFeeRequest);
		List<CalFeeDetail> calFeeDetails = new ArrayList<CalFeeDetail>();
		List<CalFeeDetailDto> calFeeDetailDtos = updateBalanceRequestDto.getCalFeeDetailDtos();
		for (CalFeeDetailDto calFeeDetailDto : calFeeDetailDtos) {
			calFeeDetails.add(BeanConvertUtil.convert(CalFeeDetail.class, calFeeDetailDto));
		}
		calFeeReponse.setCalFeeDetails(calFeeDetails);
		return calFeeReponse;
	}

}
