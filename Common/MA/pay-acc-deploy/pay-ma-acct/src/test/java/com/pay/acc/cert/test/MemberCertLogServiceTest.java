//package com.pay.acc.cert.test;
//
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import com.pay.acc.cert.model.MemberCertLog;
//import com.pay.acc.cert.service.MemberCertLogService;
//
//public class MemberCertLogServiceTest {
//	
//	private MemberCertLogService memberCertLogService;
//
//	private static ApplicationContext ac = null;
//	
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//
//		ac = new ClassPathXmlApplicationContext(
//				new String[] { "classpath:context/context-cert-bean.xml","classpath:context/test-context-acct-datasource.xml" });
//		
//	}
//
//	@AfterClass
//	public static void tearDownAfterClass() throws Exception {
//
//		((ClassPathXmlApplicationContext) ac).close();
//	
//	}
//	
//
//	//@Before
//	public void setUp() throws Exception {
//		memberCertLogService = (MemberCertLogService) ac.getBean("acc-memberCertLogService");
//	}
//
//	//@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void testCreateMemberCertLogRnTx() {
//		MemberCertLog model = new MemberCertLog();
//		
//		model.setAuthCode("dsafdsf");
//		model.setMemberCode(10001L);
//		model.setOperatorId(100011L);
//		model.setRefNo("fadsfdsafdfsa");
//		model.setStatus(1);
//		model.setUserDn("dn=1,ou=1,cn=china");
//		
//		model.setStep(1);
//		model.setSerialNo(memberCertLogService.getSerialNo());
//		
//		Long id = memberCertLogService.createMemberCertLog(model);
//		System.out.println(id);
//		System.out.println(model);
//	}
//
//}
