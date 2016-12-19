//package com.pay.acc.service.cert;
//
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import com.pay.acc.service.cert.dto.CertManageDto;
//
//public class CertQueryServiceTest {
//
//	private CertQueryService certQueryService = null;
//	
//	private static ApplicationContext ac = null;
//
//	@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//
//		ac = new ClassPathXmlApplicationContext(new String[] { "classpath*:context/*.xml" });
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
//	//@Before
//	public void setUp() throws Exception {
//		certQueryService = (CertQueryService) ac.getBean("pki-certQueryService");
//	}
//
//	//@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void testIsCertUser() {
//		Long memberCode= 20001L;
//		Long operatorId = 200011L;
//		boolean bol = certQueryService.isCertUser(memberCode, operatorId);
//		System.out.println(bol);
//		
//	}
//
//	@Test
//	public void testQueryUserCertStatus() {
//		Long memberCode= 20001L;
//		Long operatorId = 200011L;
//		//CerStatusEnum status = certQueryService.queryUserCertStatus(memberCode, operatorId);
////		if(status == null){
////			System.out.println("证书不存在");
////		}else{
////			System.out.println(status.getDes());
////		}
//	}
//
//	@Test
//	public void testIsValidCertUser() {
//		Long memberCode= 20001L;
//		Long operatorId = 200011L;
//		boolean bol = certQueryService.isValidCertUser(memberCode, operatorId);
//		System.out.println(bol);
//	}
//	
//	@Test
//	public void testQueryUsePalce() {
//		Long memberCode= 20001L;
//		Long operatorId = 200011L;
//		String machineId = "1234567890";
//		CertManageDto dto =  certQueryService.queryUsePalce(memberCode, operatorId,machineId);
//		if(dto != null){
//			System.out.println(dto.getUsePlace());
//		}
//	}
//
//}
