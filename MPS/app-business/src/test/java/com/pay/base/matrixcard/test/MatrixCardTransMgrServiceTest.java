package com.pay.base.matrixcard.test;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransMgrDto;
import com.pay.base.service.matrixcard.transmgr.MatrixCardTransMgrService;

@ContextConfiguration(locations = { "classpath*:context/*.xml" })
public class MatrixCardTransMgrServiceTest extends
		AbstractTestNGSpringContextTests {

	@Autowired
	MatrixCardTransMgrService matrixCardTransMgrService;

	@Test
	public void testMatrixCardTransMgrService() {
		Assert.assertNotNull(matrixCardTransMgrService);
	}

	@Test
	public void testcountMartrixCardTransMgrByParamMap() {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		Calendar from = Calendar.getInstance();
		from.add(Calendar.DATE, -1);
		Date dateFm = from.getTime();
		Date dateTo = Calendar.getInstance().getTime();
		paramMap.put("ip", "192.168.0.1");
		paramMap.put("creationDateBegin", dateFm);
		paramMap.put("creationDateEnd", dateTo);
		this.matrixCardTransMgrService
				.countMartrixCardTransMgrByParamMap(paramMap);
	}

	// @Test
	public void testSave() {
		MatrixCardTransMgrDto matrixdto = new MatrixCardTransMgrDto();
		matrixdto.setIp("192.168.0.1");
		matrixdto.setMemberCode(000123L);
		matrixdto.setSessionId("257899999");
		matrixdto.setStatus(0);
		matrixdto.setTransType(1);
		matrixdto.setU_id(5457L);
		matrixdto.setCreationDate(new Date());
		this.matrixCardTransMgrService.save(matrixdto);
	}

	// @Test
	public void testcountMartrixCardTransMgr() {
		this.matrixCardTransMgrService.countMartrixCardTransMgr();
	}

}
