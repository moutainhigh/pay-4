/**
 * 
 */
package com.test;

import javax.annotation.Resource;

import com.pay.txncore.aop.TestBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author chaoyue
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:context/applicationContext-aop.xml" })
public class PaymentServiceTest {

	@Autowired
	private TestBean testBean;

	@Test
	public void testLiqu() {
		testBean.returnObj("asd121asds",123);
	}
}
