/**
 *  File: NotifyFacadeServiceImplTest.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-10      zliner      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.inf;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.JSonUtil;

/**
 * 通知封装服务test
 * @author zliner
 *
 */
@ContextConfiguration(locations={"classpath*:config/env/test-dataAccessContext.xml", "classpath*:config/spring/platform/*.xml","classpath:/context/*.xml"
		,"classpath:/context/spring/inf/*.xml","classpath*:/context/*.xml","classpath*:/mdpcontext/**/*.xml","classpath*:/context/**/*.xml",
		"classpath*:config/**/*.xml",
		"classpath*:/context/spring/context-fundout-fee-cal-service.xml"})
public class NotifyFacadeServiceImplTest extends
		AbstractTestNGSpringContextTests {
	@Resource(name="fundout-withdraw-notifyFacadeService")
	private NotifyFacadeService notifyFacadeService;
	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}
	@Test
	public void testSendRequest() {
		String[] orderIdStr = new String[]{"2001010132023001358"};
		for(int i = 0 ;i < orderIdStr.length;i++) {
			Notify2QueueRequest request = new Notify2QueueRequest();
			request.setQueueName("foundout.withdraw.order.requestqueue1013");
			request.setTargetObject(JSonUtil.toJSonString(orderIdStr[i]));
			notifyFacadeService.sendRequest(request);
		}
	}
	@Test
	public void testNotifyRequest() {
//		NotifyTargetRequest request = new NotifyTargetRequest();
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("name", "jack");
//		map.put("date", "2010-10-10");
//		map.put("amount", "1000");
//		request.setData(map);
//		request.setMerchantCode(Long.valueOf("123456"));
//		request.setRequestTime(new Date());
//		request.setRetryTimes(3);
//		request.setTemplateId(18);
		//手机测试
//		List<String> mobileList = new ArrayList<String>();
//		mobileList.add("13482716756");
//		mobileList.add("13501972866");
//		mobileList.add("15921441288");
//		request.setNotifyType(RequestType.SMS.value());
//		request.setMobiles(mobileList);
		//邮件测试
//		List<String> recAddress = new ArrayList<String>();
//		recAddress.add("zliner_zhong@staff.hnacom");
//		recAddress.add("bill_peng@staff.hnacom");
//		request.setRecAddress(recAddress);
//		request.setSubject("测试邮件");
//		request.setNotifyType(RequestType.EMAIL.value());
//		request.setFromAddress("zhongli165@sohu.com");
//		notifyFacadeService.notifyRequest(request);
	}
}
