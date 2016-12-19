package com.pay.poss.external.service.innerback.impl;

import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.poss.base.exception.PossException;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.external.service.innerback.BackFundingInnerService;

@ContextConfiguration(locations = { 
		"classpath*:context/spring/**/*.xml",
		"classpath*:context/*.xml" })
public class DefaultBackFundingInnerServiceImplTest extends AbstractTestNGSpringContextTests{
	@Resource(name = "fundout-withdraw-backFundingInnerService")
	private BackFundingInnerService backService;

	@Test
	public void testBackPay() {
		try {
			backService.processCancelOrderRnTx(buildBackOrder());
		} catch (PossException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private BackFundmentOrder buildBackOrder() {
		BackFundmentOrder backOrder = new BackFundmentOrder();

		backOrder.setSequenceSrc(System.currentTimeMillis());
		backOrder.setTimeSrc(new Date());
		backOrder.setAmountSrc(new BigDecimal(1000L));
		backOrder.setFeeSrc(new BigDecimal(1));
		backOrder.setFromCode(WithdrawBusinessType.ACCTCHARGE_REFUND_ORDER_FAIL_PERSON.getBusinessType());

		backOrder.setPayerMember(10000000114L);
		backOrder.setPayerAcctCode("2181010010005");
		backOrder.setPayerCode("20010200100011000000011410");
		backOrder.setPayerAcctType(10);

		backOrder.setPayeeMember(10000000117L);
		backOrder.setPayeeAcctCode("20010200100011000000011710");
		backOrder.setPayeeAcctType(10);

		backOrder.setAppAmount(new BigDecimal(1000L));
		backOrder.setAppType(WithdrawBusinessType.ACCTCHARGE_REFUND_ORDER_FAIL_PERSON.getBusinessType());

		return backOrder;
	}
}
