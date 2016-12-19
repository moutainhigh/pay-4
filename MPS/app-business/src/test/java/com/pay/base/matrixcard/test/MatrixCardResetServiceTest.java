package com.pay.base.matrixcard.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.base.service.matrixcard.reset.MatrixCardResetService;

@ContextConfiguration(locations = { "classpath*:context/*.xml" })
public class MatrixCardResetServiceTest extends AbstractTestNG{
	
	@Autowired
	MatrixCardResetService matrixCardResetService;
	
	@Test
	public void testMatrixCardResetService(){
		Assert.assertNotNull(matrixCardResetService);
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
