package com.pay.base.matrixcard.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.base.common.helper.matrixcard.MatrixCardTransType;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.transmgr.IMatrixCardTransInfoService;

/**
 * @author cf
 * 
 */
@ContextConfiguration(locations = { "classpath*:context/*.xml" })
public class MatrixCardTransInfoServiceTest extends
		AbstractTestNGSpringContextTests {
	@Autowired
	IMatrixCardTransInfoService matrixCardTransInfoService;
	
	@Test
	public void testMatrixCardTransInfoService(){
		Assert.assertNotNull(matrixCardTransInfoService);
	}
	
//	@Test
	public void testMatrixCardTransInfosave(){
		MatrixCardTransInfoDto info=new MatrixCardTransInfoDto();
		info.setIp("192.168.0.1");
		info.setTransType(MatrixCardTransType.REQUEST.getValue());
		info.setMemberCode(10000000004L);
		info.setSessionId("257899999");
		info.setU_id(10000000004L);
		try {
	        this.matrixCardTransInfoService.save(info);
        }
        catch (MatrixCardException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
	
	@Test
	public void testcountMatrixCardTransInfo(){
		this.matrixCardTransInfoService.countMatrixCardTransInfo();
	}

	
	@Test
	public void testupdateMatrixCardTransInfo(){
		MatrixCardTransInfoDto info=new MatrixCardTransInfoDto();
		info.setIp("192.168.0.1");
		info.setTransType(MatrixCardTransType.REQUEST.getValue());
		info.setMemberCode(10000000004L);
		info.setSessionId("257899999");
		info.setU_id(10000000004L);
		info.setId(2L);
		info.setSerialNo("11000123215445656");
//		info.setReqSendDate(new Date());
		try {
	        this.matrixCardTransInfoService.updateMatrixCardTransInfo(info);
        }
        catch (MatrixCardException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
	
	
	
	@Test
	public void testselectMatrixCardTransInfoById(){
		this.matrixCardTransInfoService.selectMatrixCardTransInfoById(2L);
	}

}
