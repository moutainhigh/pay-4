package com.pay.fundout.withdraw.service.bankrefund.impl;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.dto.bankrefund.BankRefundOrderDTO;
import com.pay.fundout.withdraw.model.bankrefund.BackfundOrder;
import com.pay.fundout.withdraw.service.bankrefund.BankRefundOrderService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/**/*.xml", "classpath*:context/spring/**/*.xml", "classpath*:context/*.xml" })
public class BankRefundOrderServiceImplTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout_bankrefund_BankRefundOrderService")
	private BankRefundOrderService orderService;

//	@Test
	public void testCreateOrderRnTx() throws PossException {
		orderService.createOrderRnTx(buildBankRefundOrder());
		throw new PossException("测试", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
	}

	@Test
	public void testQueryBackfundOrder() {
		BackfundOrder order = orderService.queryBackfundOrderByInnerOrderId("121212", "nnn");
		Assert.assertNull(order.getBackfundOrderId());
		order = orderService.queryBackfundOrderByInnerOrderId("2001012221839023717", "nnn");
		Assert.assertNotNull(order.getBackfundOrderId());
	}

	private BankRefundOrderDTO buildBankRefundOrder() {
		BankRefundOrderDTO result = new BankRefundOrderDTO();
		result.setAmount(100L);
		result.setPayeeMemberCode(121L);
		result.setPayeeAcctCode("1111");
		result.setPayeeAcctType(10);
		result.setStatus(101);
		result.setPayeeName("宋江");
		result.setUniqueKey("ddddd");
		result.setTradeOrderId(111L);
		result.setFee(1L);
		result.setBusiType(0);
		result.setRealPayAmount(1L);
		return result;
	}

}
