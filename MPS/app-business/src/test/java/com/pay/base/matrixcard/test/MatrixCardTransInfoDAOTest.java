package com.pay.base.matrixcard.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.base.dao.matrixcard.transmgr.IMatrixCardTransInfoDao;

@ContextConfiguration(locations = { "classpath*:context/*.xml" })
public class MatrixCardTransInfoDAOTest extends AbstractTestNG{
	
	@Autowired
	IMatrixCardTransInfoDao baseMatrixCardTransInfoDAO;
	
	@Test
	public void testMatrixCardTransInfoDAO(){
		Assert.assertNotNull(baseMatrixCardTransInfoDAO);
	}
	
	@Test
	public void testcountMatrixCardTransInfo(){
		this.baseMatrixCardTransInfoDAO.countMatrixCardTransInfo();
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
