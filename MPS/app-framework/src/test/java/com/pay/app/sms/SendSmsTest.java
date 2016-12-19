package com.pay.app.sms;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;
import com.pay.jms.sender.JmsSender;
import com.pay.app.sms.SendSms;

@ContextConfiguration(locations = {"classpath*:context/*.xml" })
public class SendSmsTest extends AbstractTestNGSpringContextTests{

	@Resource
	private JmsSender jmsSender;
 // @Test
  public void sendSms() {
	 	SendSms sm = new SendSms();
	 	sm.setJmsSender(jmsSender);
		List<String> recAddress =new ArrayList<String>();
		recAddress.add("15921726702");
		sm.sendSms("007",recAddress,2,"123456");
  }
}
