package com.pay.base.matrixcard.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.service.matrixcard.change.MatrixCardChangeService;
/**
 * @author jim_chen
 * @version 
 * 2010-9-19 
 */
@ContextConfiguration(locations = { "classpath*:context/*.xml" })
public class MatrixCardChangeServiceTest extends AbstractTestNGSpringContextTests{

	@Autowired
	MatrixCardChangeService matrixCardChangeService;
	
	@Test
	public void testMatrixCardChangeService(){
		Assert.assertNotNull(matrixCardChangeService);
	}
	
	@Test
	public void teststartChangeTrans(){
		OperatorInfo operatorInfo = new OperatorInfo();
		operatorInfo.setIp("192.168.0.1");
		operatorInfo.setMemberCode(10000000082L);
		operatorInfo.setSessionId("10000000004342");
		operatorInfo.setOperator("10000000082");
		operatorInfo.setU_id(10000000082L);
		this.matrixCardChangeService.startChangeTrans(operatorInfo);		
	}
	
}
