package com.pay.fundout.securitycheck.tool;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.pay.fundout.securitycheck.exception.DeniedException;
import com.pay.inf.dto.PageMsgDto;

public class ErrorTipUtilTest {
	@BeforeTest
	public void init() {
		PageMsgDto e100404 = new PageMsgDto();
		e100404.setMsg("每日最多{0}{1}元，您今天最多还可以{0}{2}元");
		e100404.setMsgId("100404");
		e100404.setPageCode("securitycheck");
		Map tips = new HashMap<String, PageMsgDto>();
		tips.put("100404", e100404);

		ErrorTipUtil.addErrorTip(tips);
	}

	@Test
	public void testGetErrorTip() {
		DeniedException exception = new DeniedException("20", "100404", new String[] { "付款", "1000.00", "20.50" });
		System.out.println(ErrorTipUtil.getErrorTip(exception));
	}
}
