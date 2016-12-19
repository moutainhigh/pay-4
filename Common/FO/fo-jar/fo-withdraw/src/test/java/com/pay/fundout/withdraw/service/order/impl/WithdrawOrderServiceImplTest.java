package com.pay.fundout.withdraw.service.order.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/**/*.xml", "classpath*:context/spring/**/*.xml", "classpath*:context/*.xml" })
public class WithdrawOrderServiceImplTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "withdrawOrderService")
	private WithdrawOrderService orderService;

	@Test
	public void testCreateWithdrawOrderRnTx() throws PossException {
		//orderService.createWithdrawOrderRnTx(buildWithdrawOrderAppDTO());
		throw new PossException("测试", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);

	}

	private WithdrawOrderAppDTO buildWithdrawOrderAppDTO() {
		WithdrawOrderAppDTO result = new WithdrawOrderAppDTO();

		result.setMemberCode(111L);
		result.setMemberType(1L);
		result.setMemberAcc("test");
		result.setMemberAccType(10L);
		result.setAmount(1000L);
		result.setAccountName("宋江");
		result.setBankAcct("123456789");
		result.setBusiType(0L);
		result.setBankKy("300");

		return result;
	}
}
