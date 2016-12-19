package com.pay.poss.refund.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.jms.sender.JmsSender;
import com.pay.util.JSonUtil;

@ContextConfiguration(locations = {
		"classpath*:mdpcontext/refundprocess/context-fundout-withdrawmdp-service.xml",
		"classpath*:context/notification-client-jms.xml" })
public class TestJmsSender extends AbstractTestNGSpringContextTests {

	@Autowired
	@Qualifier("jmsSender")
	private JmsSender jmsSender;

	@Test
	public void createWithdrawOrderTest() {
		String queueName = "fundout.refund.accounting.response";
		String jsonStr = JSonUtil.toJSonString("123456789");
		jmsSender.send(queueName, jsonStr);
		for (;;) {
			
		}
	}
	
	public static void main(){
		/*ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/config/applicationContext.xml");
		applicationContext.start();*/
		//ProcessEngine processEngine = (ProcessEngine) applicationContext.getBean("processEngine");
	}
}
