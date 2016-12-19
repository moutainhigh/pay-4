package com;



import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

@ContextConfiguration(locations = { "classpath*:/context/*.xml" })
@TransactionConfiguration(transactionManager = "peTransactionManager", defaultRollback = true)
@Transactional
public class Test22 extends
		AbstractTransactionalTestNGSpringContextTests {

	@Resource
	private DataSource infDataSource;

	// 会员 固定费用	
	@Test
	public void testPaymentServiceService() {
		String aa = null ;
		String bb  = String.valueOf(aa);
	}

}