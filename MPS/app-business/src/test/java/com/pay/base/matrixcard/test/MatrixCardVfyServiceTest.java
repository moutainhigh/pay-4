package com.pay.base.matrixcard.test;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.model.matrixcard.MatrixCardVfy;
import com.pay.base.service.matrixcard.vfy.IMatrixCardVfyService;

/**
 * @author cf
 *
 */
@ContextConfiguration(locations = { "classpath*:context/*.xml" })
public class MatrixCardVfyServiceTest extends
AbstractTestNGSpringContextTests {
	
	@Autowired
	IMatrixCardVfyService matrixCardVfyService;
	
	@Test
	public void testMatrixCardVfyService(){
		Assert.assertNotNull(matrixCardVfyService);
	}
	
//	@Test
	public void testmatrixCardVfySave(){
		MatrixCardVfy dto=new MatrixCardVfy();
		dto.setSerialNo("11000123215445656");
		dto.setRequestUid(10000000004L);
		dto.setStatus(1);
		dto.setRequestType(0);
		this.matrixCardVfyService.save(dto);
	}
	@Test
	public void testUpdate(){
		MatrixCardVfyDto dto=new MatrixCardVfyDto();
		dto.setSerialNo("11000123215445656");
		dto.setRequestUid(10000000004L);
		dto.setStatus(1);
		dto.setRequestType(0);
		dto.setRequestDate(new Date());
		dto.setId(2L);
		this.matrixCardVfyService.updateMatrixCardVfy(dto);
	}
	
	@Test
	public void testSelectMatrixCard(){
		this.matrixCardVfyService.countMatrixCardVfy();
	}
	
}
