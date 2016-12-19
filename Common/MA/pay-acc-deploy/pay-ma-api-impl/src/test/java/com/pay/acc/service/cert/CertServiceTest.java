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
//import com.pay.acc.cert.enums.StepEnum;
//import com.pay.acc.cert.model.MemberCertLog;
//import com.pay.acc.constant.CertIdCardEnum;
//import com.pay.acc.service.cert.dto.MemberCertLogDto;
//import com.pay.acc.service.member.dto.ApplyCertRequest;
//import com.pay.acc.service.member.dto.ApplyCertResponse;
//import com.pay.acc.service.member.dto.CertResultDto;
//
//public class CertServiceTest {
//
//	private CertService certService = null;
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
//		certService =(CertService) ac.getBean("pki-certService");
//	}
//
//	//@After
//	public void tearDown() throws Exception {
//		
//	}
//
//	//@Test
//	public void testApplyCert() {
//		ApplyCertRequest req = new ApplyCertRequest();
//		req.setIdCardNo("430302198110220078");
//		req.setIdCardTypeNum(CertIdCardEnum.MAINLAND_ID.getCode());
//		req.setMachineIdentifier("1234567890");
//		req.setMobile("13651832600");
//		req.setRealName("张三");
//		req.setUsePlace("白宫");
//		String email = "zhangshan@gmail.com", engName = "shanzhang";
//		req.setMachineIdentifier("987654321");
//		
//		CertResultDto dto = certService.applyCert(req, email, engName,30001L, 300011L);
//		
//		System.out.println(dto.isResultBool());
//		System.out.println(dto.getResultObj().toString());
//		//ApplyCertResponse [dn=cn=041@0430302198110220078@shanzhang@00000010,ou=Customers,ou=pay,o=CFCA TEST CA,c=cn, 
//		//authCode=c99d5cf8229a3ca1125504296078877c, 
//		//refNo=6bc7dbeeaf5a05ddc8ed7ca5900e99b3, encCertPem=null, encPriKeyPem=null, signCertPem=null]
//
//	}
//
//	//@Test
//	public void testCertSign() {
//		String dn="041@0430302198110220078@shanzhang@00000013,ou=Customers,ou=pay,o=CFCA TEST CA,c=cn" ,
//		authCode = "12b01a0d98ef6007d4e45453c392eea3" ,
//		refNo ="12e3fe5e5dfccd60a449b5fcef9ce8c2";
//		
//		ApplyCertResponse applyCertResponse = new ApplyCertResponse(dn,authCode,refNo);
//		
//		String pkcs10 = "MIICzjCCAjcCAQAwfjELMAkGA1UEBhMCY24xFTATBgNVBAoMDENGQ0EgVEVTVCBD"
//		+"QTEPMA0GA1UECwwGSG5hcGF5MRIwEAYDVQQLDAlDdXN0b21lcnMxMzAxBgNVBAMM"
//		+"KjA0MUAwNDMwMzAyMTk4MTEwMjIwMDc4QHNoYW56aGFuZ0AwMDAwMDAxMzCBnzAN"
//		+"BgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEA0THt2MDtFcY9QGMG4CPu1862TuuB8dIB"
//		+"sQD7JxO8gbPbXeykvNoIJ8ObNmdI27jZ2AuzFuzvCZf0Y9Aziub7J4Wj3ONWzmt3"
//		+"8YqjHekkfGIbRrj7S3KNQEC1QnB3SyGrEqbmOWlPtooIYEGrnxRfZAOErRnx1o1x"
//		+"NxX49GV5BTECAwEAAaCCAQ4wGgYKKwYBBAGCNw0CAzEMFgo2LjEuNzYwMS4yMDoG"
//		+"CSsGAQQBgjcVFDEtMCsCAQUMCWZqbC1USElOSwwNZmpsLVRISU5LXGZqbAwMaWV4"
//		+"cGxvcmUuZXhlMD4GCSqGSIb3DQEJDjExMC8wHQYDVR0OBBYEFLVH4FwXb2MX3FSh"
//		+"rjqrsASf80XoMA4GA1UdDwEB/wQEAwIHgDB0BgorBgEEAYI3DQICMWYwZAIBAh5c"
//		+"AE0AaQBjAHIAbwBzAG8AZgB0ACAARQBuAGgAYQBuAGMAZQBkACAAQwByAHkAcAB0"
//		+"AG8AZwByAGEAcABoAGkAYwAgAFAAcgBvAHYAaQBkAGUAcgAgAHYAMQAuADADAQAw"
//		+"DQYJKoZIhvcNAQEEBQADgYEAe/nmpofma0mtmziKrlfjcPc0nAkor/DeEz6cxZXW"
//		+"zzcB5PDq2+pgLN5BuD7LvoI7v4siRtU5IxgV4i2jKCcmZyH7crTSF6+txo8vbFpT"
//		+"BpPULqbEnRBYW9/4JYArsw+XJ/ezjqX3Nwlad1hFCs8zOkXxNde5AOenfhCldBik"
//		+"1m8=";
//
//		
//		CertResultDto dto = certService.certSign( pkcs10,30001L, 300011L);
//		System.out.println(dto.isResultBool());
//		
//		System.out.println(dto.getResultObj().toString());
//		/*
//		 * 
//		 * ApplyCertResponse [dn=cn=041@0430302198110220078@shanzhang@00000010,ou=Customers,ou=pay,o=CFCA TEST CA,c=cn, 
//		 * authCode=c99d5cf8229a3ca1125504296078877c, 
//		 * refNo=6bc7dbeeaf5a05ddc8ed7ca5900e99b3, 
//		 * encCertPem=null, encPriKeyPem=null, 
//		 * signCertPem=
//		 */
//	}
//
//	@Test
//	public void testDoMemberCertRntx() {
//		ApplyCertRequest req = new ApplyCertRequest();
//		req.setMachineIdentifier("987654321");
//		//req.setUsePlace("白宫");
//		
//		String dn="041@0430302198110220078@shanzhang@00000013,ou=Customers,ou=pay,o=CFCA TEST CA,c=cn" ,
//		authCode = "12b01a0d98ef6007d4e45453c392eea3" ,
//		refNo ="12e3fe5e5dfccd60a449b5fcef9ce8c2";
//		
//		ApplyCertResponse applyCertResponse = new ApplyCertResponse(dn,authCode,refNo);
//		
//		Long memberCode= 30001L;
//		Long operatorId = 300011L;
//		
//		certService.doMemberCertRntx("987654321", memberCode, operatorId);
//	}
//
//	//@Test
//	public void testRemoveCertPlaceRntx() {
//		boolean bol = certService.removeCertPlaceRntx(4L);
//		System.out.println(bol);
//	}
//
//	//@Test
//	public void testDisableCertOfUserRntx() {
//		Long memberCode= 20001L;
//		Long operatorId = 200011L;
//		boolean bol =  certService.disableCertOfUserRntx(memberCode, operatorId);
//		System.out.println(bol);
//	}
//	
//	//@Test
//	public void testLog(){
//		MemberCertLogDto dto = new MemberCertLogDto();
//		
//		dto.setAuthCode("dsafdsf");
//		dto.setMemberCode(10001L);
//		dto.setOperatorId(100011L);
//		dto.setRefNo("fadsfdsafdfsa");
//		dto.setStatus(MemberCertLog.StatusEnum.FAILURE.getValue());
//		dto.setUserDn("dn=1,ou=1,cn=china");
//		
//		dto.setStep(StepEnum.BACKUP.getValue());
//		dto.setSerialNo(certService.getLogSerialNo());
//		
//		Long id = certService.createMemberCertLogRntx(dto);
//		System.out.println(id);
//
//	}
//
//}
