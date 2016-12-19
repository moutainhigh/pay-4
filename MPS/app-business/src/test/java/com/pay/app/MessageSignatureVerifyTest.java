package com.pay.app;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.pay.app.service.external.MessageSignatureVerify;

public class MessageSignatureVerifyTest extends TestCase {

private ApplicationContext ac = null;
	
	private MessageSignatureVerify messageSignatureVerify = null;
	
	private static String sign = null; 
	
	private static String merchantCode = "515641010005001";
	
	protected void setUp() throws Exception {
		super.setUp();
		
		ac = new ClassPathXmlApplicationContext(new String[] { 
				"classpath*:context/**/*.xml"
		});
		messageSignatureVerify = (MessageSignatureVerify) ac.getBean("bsp-messageSignatureVerify");
		
	}

	protected void tearDown() throws Exception {
		super.tearDown();
		((ClassPathXmlApplicationContext)ac).close();
	}


	public void testSignatureByMmerchant() {
		//32022b3a751f7f53bdb05b00c89094fc
		String data = "2&test&1309832794927";
		try {
			sign = messageSignatureVerify.SignatureByMmerchant(data, merchantCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(sign);
	}

	public void testVerifyByMerchant() {
		String data = "2&test&1309832794927";
		boolean b = messageSignatureVerify.verifyByMerchant(data, merchantCode,sign);
		System.out.println(b);
	}

}
