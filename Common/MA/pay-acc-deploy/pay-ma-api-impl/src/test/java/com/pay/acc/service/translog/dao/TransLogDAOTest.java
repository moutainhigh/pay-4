///**
// * 
// */
//package com.pay.acc.service.translog.dao;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import com.pay.acc.translog.dao.TransLogDAO;
//
///**
// * @author Administrator
// *
// */
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = true)
//@Transactional
//public class TransLogDAOTest extends AbstractTransactionalTestNGSpringContextTests {
//	@Resource(name = "acc-transLogDAO")
//	private TransLogDAO transLogDAO;
//	
//	@Resource(name = "dataSourceAcc")
//	public void setDataSource(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}
//	@Test
//	public void testTransLogDao(){
//		Assert.assertNotNull(this.transLogDAO);
//	}
//	
//}
//
//
//
//
//
//
//
//
