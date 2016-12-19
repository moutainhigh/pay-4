package com.pay.app;


import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pay.app.service.external.SignatureVerify;

public class SignatureVerifyTest extends TestCase {

	/*static {
		String log4j = SignatureVerifyTest.class.getResource(
				"/properties/log4j.properties").getFile();
		// 配置log4j的配置文件路径
		PropertyConfigurator.configure(log4j);
	}
	*/
	private ApplicationContext ac = null;
	
	private SignatureVerify signatureVerify = null;
	
	private static String sign = null; 
	
	protected void setUp() throws Exception {
		super.setUp();
		
		ac = new ClassPathXmlApplicationContext(new String[] { 
				"classpath*:context/**/*.xml"
		});
		signatureVerify = (SignatureVerify) ac.getBean("bsp-signatureVerify");
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		((ClassPathXmlApplicationContext)ac).close();
	}

	public void testSignatureString() {
		String data = "pay";
		try {
			sign = signatureVerify.Signature(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(sign);
		
	}

	public void testVerifyStringString() {
		String data = "pay";
		boolean b = signatureVerify.verify(data, sign);
		System.out.println(b);
	}

}
