//package com.pay.acc.service.identityverify;
//
//import java.util.Map;
//
//import javax.annotation.Resource;
//
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.Test;
//
//import com.pay.acc.identityverify.IdentityVerifyService;
//
//@ContextConfiguration(locations={"classpath:context/context-idcardverify.xml"})
//public class IdentityVerifyServiceTest extends AbstractTestNGSpringContextTests {
//
//	@Resource
//	private IdentityVerifyService identityVerifyService;
//	
//	private String name;
//	private String cardNo;
//	
//	@BeforeTest
//	public void before(){
//		name = "孙美洪";
//		cardNo = "330719196804253671";
//	}
//	
//	@Test
//	public void invoke() {
//		Map<String, String> params = identityVerifyService.generateReqParams(name, cardNo);
//		String response = identityVerifyService.invoke(params);
//		String result = identityVerifyService.handleResponse(response);
//		System.out.println(result);
//	}
//	
//	@Test
//	public void validate() {
//		boolean flag = identityVerifyService.validate(name, cardNo);
//		System.out.println(flag);
//	}
//}
