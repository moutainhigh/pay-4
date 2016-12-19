package com.pay.base.matrixcard.test;

import java.sql.SQLException;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.base.dao.matrixcard.transmgr.MatrixCardTransMgrDAO;

@ContextConfiguration(locations = { "classpath*:context/*.xml" })
public class MatrixCardTransMgrDAOTest extends AbstractTestNG{
	
	//@Autowired
	@Resource(name = "baseMatrixCardTransMgrDAO")
	MatrixCardTransMgrDAO baseMatrixCardTransMgrDAO;
	
	@Test
	public void testMatrixCardTransMgrDAO(){
		Assert.assertNotNull(baseMatrixCardTransMgrDAO);
	}
	
	@Test
	public void testCountMatrixCardTransMgr(){
		this.baseMatrixCardTransMgrDAO.countMatrixCardTransMgr(null);
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
