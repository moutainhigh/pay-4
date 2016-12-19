/**
 * BankLogTest.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.banklog;

import java.util.Date;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hnapay.fi.order.repair.engine.loader.log.BankConnLog;
import com.hnapay.fi.order.repair.engine.loader.log.IBankConnLogPersist;

/**
 * latest modified time : 2011-9-8 上午10:23:07
 * @author bigknife
 */
public class BankLogTest {
	@Test
	public void testInsert(){
		BankConnLog log = new BankConnLog();
		log.setEndTime(new Date());
		log.setStartTime(new Date());
		log.setRequestBody("ddddddddddddddddddddd");
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < 1000; i ++){
			buf.append("sssssssssssssssssssssssssss");
		}
		log.setResponse(buf.toString());
		log.setUrl("http://wwwwwwwwwwwwwwwwwwwwwww");
		log.setStatus("200");
		
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[]{
				"classpath:/context/order/autorepair/autorepair-ds.xml",
				"classpath:/context/order/autorepair/autorepair-db-bean.xml"
		});
		IBankConnLogPersist persist = (IBankConnLogPersist) ac.getBean("aurorepair.banklog.persist");
		persist.insert(log);
	}
}
