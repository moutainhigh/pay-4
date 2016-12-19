package com.pay.base.matrixcard.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.base.service.matrixcard.unbind.MatrixCardUnBindService;

@ContextConfiguration(locations = { "classpath*:context/*.xml" })
public class MatrixCardUnBindServiceTest extends AbstractTestNGSpringContextTests {
	@Autowired
	MatrixCardUnBindService matrixCardUnBindService;
	
	@Test
	public void testMatrixCardUnBindService(){
		Assert.assertNotNull(this.matrixCardUnBindService);
	}
}
