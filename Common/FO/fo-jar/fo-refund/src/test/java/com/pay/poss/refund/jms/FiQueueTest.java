package com.pay.poss.refund.jms;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.jms.sender.JmsSender;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;

/**		
 *  @author lIWEI
 *  @Date 2011-8-1
 *  @Description
 *  @Copyright Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
@Test
@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml",
"classpath*:context/**/*.xml"})
public class FiQueueTest extends AbstractTestNGSpringContextTests {
	@Autowired
	@Qualifier("jmsSender")
	private JmsSender jmsSender;
	
	@Test
	public void testFiToFo() {
		/*String queueName = "fo.customRefund.fiTofo.req";
		Map<String,Object> requestMap = new HashMap<String,Object>();
		requestMap.put("memberCode", "20000000061");
		requestMap.put("depositOrderNo", "1071108091045010634");
		requestMap.put("bankSerialNo", "517551");
		requestMap.put("depositAmount", "20000000061");
		requestMap.put("depositDate", "20110809104536");
		requestMap.put("bankChannel", "100000");
		requestMap.put("applyAmount", "100");
		requestMap.put("applyReason", "aaa");
		requestMap.put("applyMax", "99300");
		requestMap.put("bankOrderId", "517551");
		requestMap.put("depositBackNo", "1081108091124010048");
		requestMap.put("memberType", "1");
		String jsonStr = JSonUtil.toJSonString(requestMap);
		jmsSender.send(queueName, jsonStr);*/
		
		Map<String,String> toFi = new HashMap<String,String>();
		toFi.put("depositOrderNo", "1071106091230010377");
		toFi.put("depositBackNo", "");
		toFi.put("amount", "100");
		toFi.put("dateTime", "20110809104536");
		toFi.put("type", "2");
		toFi.put("result", "0");
		toFi.put("errorCode", "1011");
		String jsonStr = JSonUtil.toJSonString(toFi);
		jmsSender.send("fi.depositback.notice", jsonStr);
	}
}
