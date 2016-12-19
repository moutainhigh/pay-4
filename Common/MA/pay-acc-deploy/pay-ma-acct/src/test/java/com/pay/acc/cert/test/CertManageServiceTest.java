//package com.pay.acc.cert.test;
//
//import java.util.List;
//
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Before;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//import org.testng.annotations.AfterClass;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
//
//import com.pay.acc.cert.model.CertManage;
//import com.pay.acc.cert.service.CertManageService;
//
//public class CertManageServiceTest {
//	
//	private CertManageService certManageService;
//	
//	private static ApplicationContext ac = null;
//	
//
//	//@BeforeClass
//	public static void setUpBeforeClass() throws Exception {
//
//		ac = new ClassPathXmlApplicationContext(
//				new String[] { "classpath:context/context-acct-bean.xml","classpath:context/test-context-acct-datasource.xml" });
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
//		certManageService =(CertManageService) ac.getBean("acc-certManageService");
//	}
//
//	//@After
//	public void tearDown() throws Exception {
//		
//	}
//
//	//@Test
//	public void testCreateCerManage() {
//		CertManage model = new CertManage();
//		model.setMachineId("jl-fang");
//		model.setMemberCode(10001L);
//		model.setOperatorId(100011L);
//		model.setStatus(1);
//		model.setUsePlace("笔记本");
//		model.setUserDn("dn=1erwrewqre,ou=1e13,cn=dfdsf");
//		
//		certManageService.createCerManage(model);
//	}
//
//	//@Test
//	public void testModifyCerManage() {
//		
//		CertManage model = new CertManage();
//		model.setId(1L);
//		model.setMachineId("acdfsafds_modify");
//		model.setMemberCode(10001L);
//		model.setOperatorId(100011L);
//		model.setStatus(0);
//		model.setUsePlace("办公室修改");
//		model.setUserDn("dn=1erwrewqre,ou=1e13,cn=dfdsf,modify");
//		
//		certManageService.modifyCerManage(model);
//	}
//
//	//@Test
//	public void testQueryCount() {
//		int count = certManageService.queryCount(10001L, 100011L);
//		System.out.println("count="+count);
//	}
//
//	@Test
//	public void testQueryCertManage() {
//		List<CertManage> list = certManageService.queryCertManage(10001L, 100011L);
//
//		for(CertManage bean : list){
//			System.out.println(bean.toString());
//		}
//	}
//
//	//@Test
//	public void testDeleteById() {
//		boolean b = certManageService.logicDeleteById(2L);
//		System.out.println("b= " + b);
//	}
//
//}
