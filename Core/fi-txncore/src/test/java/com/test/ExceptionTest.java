package com.test;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.pay.txncore.aop.Book;
import com.pay.txncore.aop.TestBean;
import com.pay.txncore.aop.TestBean2;

public class ExceptionTest {

	private static ApplicationContext context;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new  FileSystemXmlApplicationContext("C:/tokenversion/iPay/Core/fi-txncore/src/main/resources/context/applicationContext-aop.xml");
//		context = new  FileSystemXmlApplicationContext(new String[]{"C:/tokenversion/iPay/Core/fi-txncore/src/main/resources/context/*.xml",
//				"C:/zy-project/Common/common-inf/if-notification-client/src/main/resources/context/notification-client-jms.xml"});// ClassPathXmlApplicationContext("classPath:context/applicationContext-test-aop.xml");
//		context = new  ClassPathXmlApplicationContext("classPath:context/*.xml");
	}

	@Test
	public void test() {
//		TestBean testBean = (TestBean) context.getBean("testBean");
//		testBean.returnObj("dog", 112);
		
		Book book = new Book("忧伤","商务出版社");
		TestBean2 testBean2 = (TestBean2) context.getBean("testBean2");
		testBean2.buyBook(book);
	}

}
