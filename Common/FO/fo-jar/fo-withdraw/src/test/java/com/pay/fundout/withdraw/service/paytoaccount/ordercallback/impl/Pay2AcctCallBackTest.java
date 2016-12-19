package com.pay.fundout.withdraw.service.paytoaccount.ordercallback.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctService;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.OrderCallBackService;

@ContextConfiguration(locations = { "classpath*:context/spring/base/*.xml", "classpath*:context/spring/withdraw/context-fundout-withdraw-pefacade-service.xml",
		"classpath*:context/spring/withdraw/context-fundout-withdraw-accounting-service.xml", "classpath*:context/spring/ordercallback/context-fundout-orderhandler-service.xml",
		"classpath*:context/spring/pay2acct/context-fundout-pay2acct-service.xml", "classpath*:config/env/test-dataAccessContext.xml", "classpath*:context/spring/**/*.xml",
		"classpath*:config/spring/**/*.xml", "classpath*:context/*.xml" })
public class Pay2AcctCallBackTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-withdraw-pay2AcctCallBack")
	private OrderCallBackService pay2AcctCallBack;
	@Resource(name = "fundout-withdraw-pay2AcctMS")
	private AccountingService accountingService;

	@Test
	public void testProcessOrder() {
		try {
			pay2AcctCallBack.processOrderRdTx(buildParam(), accountingService);
		} catch (PossException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// @Test
	public void testChangeOrderInfo() {
		((Pay2AcctCallBack) pay2AcctCallBack).changeOrderStatus(buildParam());
	}

	// @Test
	public void testNotify() {
		Pay2AcctOrder order = buildOrder();
		HandlerParam param = buildParam();
		((Pay2AcctCallBack) pay2AcctCallBack).notify(param);
	}

	private HandlerParam buildParam() {
		HandlerParam result = new HandlerParam();
		result.setBaseOrderDto(buildOrder());
		result.setOrderStatus(111);
		result.setWithdrawBusinessType(WithdrawBusinessType.PAYTOACCT_BATCHORDER_SUCC_PERSON.getBusinessType());
		return result;
	}

	private Pay2AcctOrder buildOrder() {
		Pay2AcctOrder pay2AcctOrder = new Pay2AcctOrder();
//		pay2AcctOrder.setSequenceId(System.currentTimeMillis());
		pay2AcctOrder.setSequenceId(2001011041240002832L);
		pay2AcctOrder.setAmount(1000L);
		// pay2AcctOrder.setParentOrder(111L);
		pay2AcctOrder.setRemarks("代发gongzi");
		pay2AcctOrder.setUpdateDate(new Date());

		pay2AcctOrder.setPayerMember(10000000114L);
		pay2AcctOrder.setPayerAcctCode("2181010010005");
		pay2AcctOrder.setPayerCode("20010200100011000000011410");
		pay2AcctOrder.setPayerAcctType(10);
		pay2AcctOrder.setPayerName("宋江");
		pay2AcctOrder.setPayerPhone("13564990597");

		pay2AcctOrder.setPayeeMember(10000000117L);
		pay2AcctOrder.setPayeeAcctCode("20010200100011000000011710");
		pay2AcctOrder.setPayeeAcctType(10);
		pay2AcctOrder.setPayeeName("卢俊义");
		pay2AcctOrder.setPayeePhone("13564990597");
		pay2AcctOrder.setPayeeMail("rick_lv@staff.hnacom");

		pay2AcctOrder.setStatus(101);
		pay2AcctOrder.setErrorTips("单元测试");
		pay2AcctOrder.setRequestFrom(Pay2AcctService.REQUEST_CODE_FOR_BATCH_SECOND);

		return pay2AcctOrder;
	}

}
