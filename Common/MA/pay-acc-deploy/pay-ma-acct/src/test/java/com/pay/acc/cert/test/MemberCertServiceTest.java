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
//import com.pay.acc.cert.model.MemberCert;
//import com.pay.acc.cert.service.MemberCertService;
//
//public class MemberCertServiceTest {
//
//	private MemberCertService memberCertService;
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
//	//@Before
//	public void setUp() throws Exception {
//		memberCertService = (MemberCertService) ac.getBean("acc-memberCertService");
//	}
//
//	//@After
//	public void tearDown() throws Exception {
//		
//	}
//
//	//@Test
//	public void testCreateMemberCert() {
//		MemberCert model = new MemberCert();
//		model.setAuthCode("dsafdsf");
//		model.setMemberCode(10001L);
//		model.setOperatorId(100011L);
//		model.setRefNo("fadsfdsafdfsa");
//		model.setStatus(1);
//		model.setUserDn("dn=1,ou=1,cn=china");
//		model.setCertCode("dsfafdsaf");
//		String ss = "MIID0DCCAzmgAwIBAgIQV6Gk4HhhcDz3rlhXzM8WVjANBgkqhkiG9w0BAQUFADAk"
//		+"MQswCQYDVQQGEwJDTjEVMBMGA1UEChMMQ0ZDQSBURVNUIENBMB4XDTExMTExODEw"
//		+"NTkxNloXDTEyMTExODEwNTkxNloweDELMAkGA1UEBhMCQ04xFTATBgNVBAoTDENG"
//		+"Q0EgVEVTVCBDQTEPMA0GA1UECxMGSG5hcGF5MRIwEAYDVQQLEwlDdXN0b21lcnMx"
//		+"LTArBgNVBAMUJDA0MUAwNDMwMzAyMTk4MTEwMjIwMDc4QGZqbEAwMDAwMDAxMjCB"
//		+"nzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAs6nz4MeJrRtVgjk5ggL6JMQGFEWA"
//		+"diLU78LL6xrEG007BNleKViF3CUTjZLjm7JIstAYPZCwUhSS86oYROhmcUIUd+A6"
//		+"PAL0VzVSptm/tKwidZjJwZtlxXcamLlzBmxvy3BjERtkRFZlhVM+0uADgATCvWmz"
//		+"iJ3zu0YFeooqnaUCAwEAAaOCAa0wggGpMB8GA1UdIwQYMBaAFEZy3CVynwJOVYO1"
//		+"gPkL2+mTs/RFMB0GA1UdDgQWBBR0/lumih8CkIJDMqk6IWd1pp7LzTALBgNVHQ8E"
//		+"BAMCBaAwHAYDVR0RBBUwE4ERamwtZmFuZ0BobmFpci5jb20wDAYDVR0TBAUwAwEB"
//		+"ADA7BgNVHSUENDAyBggrBgEFBQcDAQYIKwYBBQUHAwIGCCsGAQUFBwMDBggrBgEF"
//		+"BQcDBAYIKwYBBQUHAwgwgfAGA1UdHwSB6DCB5TBPoE2gS6RJMEcxCzAJBgNVBAYT"
//		+"AkNOMRUwEwYDVQQKEwxDRkNBIFRFU1QgQ0ExDDAKBgNVBAsTA0NSTDETMBEGA1UE"
//		+"AxMKY3JsMTI2XzE5MzCBkaCBjqCBi4aBiGxkYXA6Ly90ZXN0bGRhcC5jZmNhLmNv"
//		+"bS5jbjozODkvQ049Y3JsMTI2XzE5MyxPVT1DUkwsTz1DRkNBIFRFU1QgQ0EsQz1D"
//		+"Tj9jZXJ0aWZpY2F0ZVJldm9jYXRpb25MaXN0P2Jhc2U/b2JqZWN0Y2xhc3M9Y1JM"
//		+"RGlzdHJpYnV0aW9uUG9pbnQwDQYJKoZIhvcNAQEFBQADgYEAHzhHzdAOQHzCCnrm"
//		+"X9I6YYB7qXb4ySZPoruMz8lM3L4qbPDpfxaWlDu/wR6SVsRQzqCoHxyxBlvQFhOd"
//		+"OIhokS41TLHrPtegfxNvzaqjumFsMk619IIYmbUUoEZCk5KhZ6EAm5rQPztbBJpN"
//		+"eXPJaslADRfziEmeydF4pJ29ENY=";
//		
//		model.setCertCode(ss);
//		Long id = memberCertService.createMemberCert(model);
//		System.out.println(id);
//	}
//
//	//@Test
//	public void testModifyMemberCert() {
//		MemberCert model = new MemberCert();
//		model.setId(1L);
//		model.setAuthCode("dsafdsf1");
//		model.setMemberCode(10001L);
//		model.setOperatorId(100011L);
//		model.setRefNo("fadsfdsafdfsa1");
//		model.setStatus(0);
//		model.setUserDn("dn=1erwrewqre,ou=1e13,cn=dfdsf11");
//		
//		boolean bol =memberCertService.modifyMemberCert(model);
//		System.out.println(bol);
//	}
//
//	//@Test
//	public void testDisableMemberCert() {
//		boolean bol = memberCertService.disableMemberCertByDn("dn=1,ou=1,cn=china");
//		System.out.println(bol);
//	}
//
//	//@Test
//	public void testQueryMemberCertString() {
//		MemberCert bean =memberCertService.queryMemberCertByDn("dn=1erwrewqre,ou=1e13,cn=dfdsf11");
//		System.out.println(bean);
//	}
//
//	//@Test
//	public void testQueryMemberCertLongLong() {
//		MemberCert bean =memberCertService.queryMemberCert(10001L,100011L);
//		System.out.println(bean);
//	}
//	
//	@Test
//	public void testByid(){
//		MemberCert bean =memberCertService.queryById(9L);
//		System.out.println(bean);
//	}
//
//}
