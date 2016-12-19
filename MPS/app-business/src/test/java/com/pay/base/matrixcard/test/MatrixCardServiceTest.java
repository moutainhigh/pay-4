package com.pay.base.matrixcard.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.base.common.helper.matrixcard.MatrixCardStatus;
import com.pay.base.dto.matrixcard.MatrixCardDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.service.matrixcard.IMatrixCardService;

@ContextConfiguration(locations = { "classpath*:context/*.xml" })
public class MatrixCardServiceTest extends AbstractTestNG{
	@Autowired	
	IMatrixCardService matrixCardService;
	
	public void testMatrixCardService(){
		Assert.assertNotNull(matrixCardService);
	}
	
//	@Test
	public void testSaveMatrixCard(){
		MatrixCardDto matrixCardDto=new MatrixCardDto();
		matrixCardDto.setSerialNo("11000123215445656");
		matrixCardDto.setBindMemberCode(10000000004L);
		matrixCardDto.setStatus(MatrixCardStatus.CREATE.getValue());
		matrixCardService.save(matrixCardDto);
	}
	
	@Test
	public void testCreate(){
		try {
	        Assert.assertNotNull(this.matrixCardService.create());
        }
        catch (MatrixCardException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
	
	@Test
	public void testCountMatrixCard(){
		this.matrixCardService.countMatrixCard();
	}
	
//	@Test
	public void testUpdateMatrixCard(){
		MatrixCardDto matrixCardDto=new MatrixCardDto();
		matrixCardDto.setSerialNo("11000123215445656");
		matrixCardDto.setBindMemberCode(10000000004L);
		matrixCardDto.setStatus(MatrixCardStatus.CREATE.getValue());
		matrixCardDto.setId(2L);
		matrixCardDto.setBindDate(new Date());
		matrixCardService.updateMatrixCard(matrixCardDto);
	}
	
	@Test
	public void testSelectMatrixCard(){
		this.matrixCardService.selectMatrixCardById(2L);
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
