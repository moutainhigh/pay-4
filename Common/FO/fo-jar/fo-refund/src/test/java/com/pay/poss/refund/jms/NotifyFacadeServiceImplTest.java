/**
 *  File: NotifyFacadeServiceImplTest.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-10-22      sunsea_li      Changes
 *  
 *
 */
package com.pay.poss.refund.jms;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.jms.notification.request.RequestType;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.refund.common.util.NumberUtil;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;

/**
 * 通知封装服务test
 * @author sunsea_li
 *
 */
@ContextConfiguration(locations={
		"classpath*:config/env/test-dataAccessContext.xml", 
		
		"classpath*:config/spring/**/*.xml",
		"classpath*:context/*.xml",
		"classpath*:context/spring/**/*.xml",
		"classpath*:context/servlet/*.xml",
		"classpath*:mdpcontext/**/*.xml",
		
		"classpath*:context/servlet/**/*.xml",
		"classpath*:config/action/**/*.xml",
		
		"classpath*:/context/spring/context-fundout-fee-cal-service.xml"})
		
public class NotifyFacadeServiceImplTest extends
		AbstractTestNGSpringContextTests {
	
	@Resource(name="fundout-withdraw-notifyFacadeService")
	private NotifyFacadeService notifyFacadeService;
	
	//jms消息发送，与监听接收功能测试
	//@Test
	public void testSendRequest() {
		String[] orderIdStr = new String[]{"2001010132023001358"};
		for(int i = 0 ;i < orderIdStr.length;i++) {
			Notify2QueueRequest request = new Notify2QueueRequest();
			request.setQueueName("fundout.refund.accounting.response");
			request.setTargetObject(JSonUtil.toJSonString(orderIdStr[i]));
			//notifyFacadeService.sendRequest(request);
		}
	}
	
	//邮件，手机发送短信功能测试
	@Test
	public void testNotifyRequest() {
		NotifyTargetRequest request = new NotifyTargetRequest();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("payerName", "李威");
		map.put("appDate", DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss",new Date()));
		map.put("amount", NumberUtil.formatDoubleToString(new BigDecimal(1000).divide(new BigDecimal(1000)).doubleValue(), "##0.00"));
		request.setData(map);//请求的数据
		request.setMerchantCode(Long.valueOf("123456"));//商户号
		request.setRequestTime(new Date());//请求创建时间
		request.setRetryTimes(0);//重试次数
		
		//手机测试
		request.setNotifyType(RequestType.SMS.value());
		request.setTemplateId(37);//模板id,在inf.notify_template表中配置
		List<String> mobileList = new ArrayList<String>();
		mobileList.add("13761164419");
		//mobileList.add("13671600869");//测试 黄
		//mobileList.add("15221337785");//测试 庄
		//mobileList.add("15021340035");//产品 欧阳
		request.setMobiles(mobileList);//*/
		
		//邮件测试
		/*request.setNotifyType(RequestType.EMAIL.value());//发送类型
		request.setTemplateId(36);//模板id,在inf.notify_template表中配置
		List<String> recAddress = new ArrayList<String>();
		recAddress.add("sunsea_li@staff.hnacom");
		request.setRecAddress(recAddress);//收件人列表
		List<String> ccAddress = new ArrayList<String>();
		ccAddress.add("helen_huang@staff.hnacom");//测试 黄
		ccAddress.add("arzous_zhuang@staff.hnacom");//测试 庄
		ccAddress.add("elx_ouyang@staff.hnacom");//产品 欧阳
		ccAddress.add("zliner_zhong@staff.hnacom");//组长 钟理
		//request.setCcAddress(ccAddress);//抄送人列表
		//request.setSubject("您已经申请充退，等待支付向银行账户打款！");//充退申请邮件标题
		//request.setSubject("您的申请充退已经处理完毕！");//充退处理成功邮件标题
		request.setSubject("您的申请充退处理失败！");//充退处理失败邮件标题
		request.setFromAddress("10000@qq.com");//发件人*/
		
		notifyFacadeService.notifyRequest(request);
	}
}
