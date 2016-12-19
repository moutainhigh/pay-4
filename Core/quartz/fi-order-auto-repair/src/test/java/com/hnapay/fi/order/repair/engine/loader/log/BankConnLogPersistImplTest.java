/**
 * BankConnLogPersistImplTest.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine.loader.log;

import java.util.Date;

import org.junit.Test;

/**
 * latest modified time : 2011-9-5 上午11:46:10
 * @author bigknife
 */
public class BankConnLogPersistImplTest {
	@Test
	public void testInsert(){
		IBankConnLogPersist persist = new BankConnLogPersistImpl();
		
		BankConnLog log = new BankConnLog();
		
		log.setCreateTime(new Date());
		log.setEndTime(new Date());
		log.setRequestBody("for test");
		log.setResponse("for test");
		log.setStatus("200");
		log.setStartTime(new Date());
		
		persist.insert(log);
	}
}
