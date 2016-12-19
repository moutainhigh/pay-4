///**
// * 
// */
//package com.pay.ma.facade;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.test.AbstractSpringContextTests;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//
//
///**
// * @author Administrator
// *
// */
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "mdbTaskTxManager", defaultRollback = true)
//@Transactional
//public class PeServiceFacadeTest extends  AbstractTransactionalTestNGSpringContextTests {
//	@Resource(name = "peServiceFacade")
//	private PEServiceFacade peServiceFacade;
//
//	@Resource(name = "dataSourceMdbTask")
//	public void setDataSource(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}
//	@Test
//	public void testPeSerivceFacade(){
//		Assert.assertNotNull(this.peServiceFacade);
//	}
//	@Test
//	public void testAccountCallFeeReponse(){
////		this.peServiceFacade.accountCallFeeReponse();
//		
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
