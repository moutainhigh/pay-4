package com.pay.base.matrixcard.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.request.IMatrixCardReqService;

@ContextConfiguration(locations = { "classpath*:context/*.xml" })
public class MatrixCardReqServiceTest  extends AbstractTestNG{
	
	@Autowired	
	IMatrixCardReqService matrixCardReqService;
	
	@Test
	public void testmatrixCardReqService(){
		Assert.assertNotNull(this.matrixCardReqService);
	}
	
	@Test
	public void testprocessRequest(){
		MatrixCardTransInfoDto transInfoDto=new MatrixCardTransInfoDto();
		transInfoDto.setCreationDate(new Date(System.currentTimeMillis()));
		transInfoDto.setIp("192.168.0.1");
		transInfoDto.setSessionId("98745612");
		transInfoDto.setMemberCode(10000000082L);
		transInfoDto.setU_id(10000000082L);
//		transInfoDto.setReqSendAddr("chenfeiwoow@163.com");
//		transInfoDto.setReqSendDate(new Date());	
	    this.matrixCardReqService.processRequest(transInfoDto);
       
	}
	
//	@Test
	public void testDown(){
		try {
	        this.matrixCardReqService.download("4", "12354621");
        }
        catch (MatrixCardException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}

	@Override
    public void testDeleteById() {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void testFindById() {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void testLoadAll() {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void testSave() {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void testUpdate() {
	    // TODO Auto-generated method stub
	    
    }

}
