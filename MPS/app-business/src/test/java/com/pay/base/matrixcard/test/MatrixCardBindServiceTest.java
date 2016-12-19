package com.pay.base.matrixcard.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.service.matrixcard.bind.MatrixCardBindService;

/**
 * @author jim_chen
 * @version 2010-9-17
 */
@ContextConfiguration(locations = { "classpath*:context/*.xml" })
public class MatrixCardBindServiceTest extends AbstractTestNGSpringContextTests {

	@Autowired
	MatrixCardBindService matrixCardBindService;

	@Test
	public void testmatrixCardBindService() {
		Assert.assertNotNull(this.matrixCardBindService);
	}

	@Test
	public void testprocessRequest() {
		OperatorInfo operatorInfo = new OperatorInfo();
		operatorInfo.setIp("192.168.0.1");
		operatorInfo.setMemberCode(10000000082L);
		operatorInfo.setSessionId("10000000004342");
		operatorInfo.setOperator("10000000082");
		operatorInfo.setU_id(10000000082L);

		MatrixCardVfyRequest mcVfyRequest = new MatrixCardVfyRequest();

		this.matrixCardBindService.processRequest(mcVfyRequest, operatorInfo);
	}
	
	@Test
	public void testBind(){
		MatrixCardVfyDto matrixCardVfyDto =new MatrixCardVfyDto();
		matrixCardVfyDto.setRequestIp("192.168.0.1");
		matrixCardVfyDto.setSerialNo("676025486741931");
		matrixCardVfyDto.setStatus(0);
		matrixCardVfyDto.setRequestUid(10000000082L);
		matrixCardVfyDto.setToken("a9ec4d727bedecb332f5ba658fbca67b99e4455d0000000000007");
		matrixCardVfyDto.setVfyIp("192.168.0.1");
		matrixCardVfyDto.setXy1("B10");
		matrixCardVfyDto.setXy0("G2");
		matrixCardVfyDto.setXy2("E4");
		matrixCardVfyDto.setTransId(45L);
		matrixCardVfyDto.setRequestId(45L);
		
		this.matrixCardBindService.bind(matrixCardVfyDto);
	}

}
