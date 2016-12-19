package com.pay.fundout.withdraw.service.paytoaccount.impl;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctService;

@ContextConfiguration(locations = { 
		"classpath*:config/env/test-dataAccessContext.xml", 
		"classpath*:config/spring/**/*.xml", 
		"classpath*:context/spring/**/*.xml", 
		"classpath*:context/*.xml" })
public class Pay2AcctServiceImplTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "fundout-withdraw-pay2AcctService")
	private Pay2AcctService pay2AcctService;

	// @Test
	public void testSaveOrder() {
		//Assert.assertEquals(true,  ((Pay2AcctServiceImpl)pay2AcctService).saveOrder(buildOrder(Pay2AcctService.REQUEST_CODE_FOR_APP), Pay2AcctService.REQUEST_CODE_FOR_APP));
	}

	// @Test
	public void testHandleAfterBatchFirst() {
		Pay2AcctOrder order = buildOrder(Pay2AcctService.REQUEST_CODE_FOR_BATCH_FIRST);
//		pay2AcctService.handleAfter(order, Pay2AcctService.REQUEST_CODE_FOR_BATCH_FIRST);
	}

	@Test
	public void testHandleAfterBatchSecond() {
		Pay2AcctOrder order = buildOrder(Pay2AcctService.REQUEST_CODE_FOR_BATCH_SECOND);
//		pay2AcctService.handleAfter(order, Pay2AcctService.REQUEST_CODE_FOR_BATCH_SECOND);
	}

//	 @Test
	public void testHandleAfterSimple() {
		Pay2AcctOrder order = buildOrder(Pay2AcctService.REQUEST_CODE_FOR_APP);
//		pay2AcctService.handleAfter(order, Pay2AcctService.REQUEST_CODE_FOR_APP);
	}

//	 @Test
	public void testPay2Acct() {
		Pay2AcctOrder order = buildOrder(Pay2AcctService.REQUEST_CODE_FOR_APP);
		pay2AcctService.pay2Acct(order);
	}

	private Pay2AcctOrder buildOrder(String request) {
		Pay2AcctOrder pay2AcctOrder = new Pay2AcctOrder();
		pay2AcctOrder.setSequenceId(System.currentTimeMillis());
		pay2AcctOrder.setAmount(1000L);
		pay2AcctOrder.setParentOrder(111L);
		pay2AcctOrder.setRemarks("代发gongzi");
		pay2AcctOrder.setUpdateDate(new Date());
		pay2AcctOrder.setRequestFrom(request);

		if (Pay2AcctService.REQUEST_CODE_FOR_APP.equals(request)) {
			pay2AcctOrder.setPayerMember(10000000114L);
			pay2AcctOrder.setPayerAcctCode("20010200100011000000011410");
			pay2AcctOrder.setPayerAcctType(10);
			pay2AcctOrder.setPayeeMember(10000000117L);
			pay2AcctOrder.setPayeeAcctCode("20010200100011000000011710");
			pay2AcctOrder.setPayeeAcctType(10);
		}

		if (Pay2AcctService.REQUEST_CODE_FOR_BATCH_FIRST.equals(request)) {
			pay2AcctOrder.setPayerMember(10000000114L);
			pay2AcctOrder.setPayerAcctCode("20010200100011000000011410");
			pay2AcctOrder.setPayerAcctType(10);

			pay2AcctOrder.setPayeeAcctCode("2181010010005");
		}

		if (Pay2AcctService.REQUEST_CODE_FOR_BATCH_SECOND.equals(request)) {
			pay2AcctOrder.setPayerMember(10000000114L);
			pay2AcctOrder.setPayerAcctCode("2181010010005");
			pay2AcctOrder.setPayerAcctType(10);
			pay2AcctOrder.setPayerCode("20010200100011000000011410");

			pay2AcctOrder.setPayeeMember(10000000117L);
			pay2AcctOrder.setPayeeAcctCode("20010200100011000000011710");
			pay2AcctOrder.setPayeeAcctType(10);
		}

		return pay2AcctOrder;
	}

	public static void main(String[] args) {
		Pattern innerPattern = Pattern.compile("\\{[^{]*\\}");
		String[] tips = { "A", "B" };
		String errorBefore = "当月次数超限,累计次数{0},每月限额{1}sdfasf";
		if (tips != null) {
			Matcher matcher = innerPattern.matcher(errorBefore);
			StringBuffer temp = new StringBuffer();
			while (matcher.find()) {
				String matchString = matcher.group();
				matcher.appendReplacement(temp, tips[Integer.parseInt(matchString.substring(1,matchString.length()-1))]);
			}
			matcher.appendTail(temp);

			System.out.println(temp.toString());
		}
	}
}
