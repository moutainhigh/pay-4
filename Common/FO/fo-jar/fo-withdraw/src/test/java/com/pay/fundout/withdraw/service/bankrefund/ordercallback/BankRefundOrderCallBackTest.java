package com.pay.fundout.withdraw.service.bankrefund.ordercallback;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.dto.bankrefund.BankRefundOrderDTO;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/**/*.xml", "classpath*:context/spring/**/*.xml", "classpath*:context/*.xml" })
public class BankRefundOrderCallBackTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout_withdraw_BankRefundOrderCallBack")
	private OrderCallBackService bankCallBack;

	@Test
	public void testNotify() {
		bankCallBack.notify(buildParam());
	}

	private HandlerParam buildParam() {
		HandlerParam result = new HandlerParam();
		result.setWithdrawBusinessType(WithdrawBusinessType.BANKREFUND_WITHDRAW_ORDER_SUCC.getBusinessType());
		BankRefundOrderDTO dto = new BankRefundOrderDTO();
		dto.setSequenceId(1111L);
		dto.setTradeOrderId(2001012171514015401L);
		dto.setStatus(111);
		result.setBaseOrderDto(dto);
		return result;
	}

}
