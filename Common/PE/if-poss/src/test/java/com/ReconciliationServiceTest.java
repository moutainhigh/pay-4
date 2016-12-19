package com;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.pe.reconciliation.service.ReconciliationService;
import com.pay.util.MfDate;

@ContextConfiguration(locations={"classpath*:context/**/**/*.xml"})
public class ReconciliationServiceTest extends AbstractTestNGSpringContextTests{
	
	@Resource(name="reconciliationService")
	private ReconciliationService reconciliationService;
	
	
//	@Test
	public void testChargeBackService(){
		Assert.assertNotNull(reconciliationService);
	}
	
//	@Test
	public void testdoUpdateBalance(){
		java.util.Calendar cal=Calendar.getInstance();
		cal.add(Calendar.MONTH, -2);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
        SimpleDateFormat formatter = new SimpleDateFormat(MfDate.strPattern);
        String beginDate=formatter.format(cal.getTime());
        System.out.println("beginDate  :"+beginDate);
		cal.add(Calendar.MONTH, 2);
		cal.add(Calendar.DAY_OF_MONTH, 1);

        String endDate=formatter.format(cal.getTime());
        System.out.println("endDate  :"+endDate);

		//System.out.println(reconciliationService.getFileNameByDate(beginDate, endDate));	
	}
	
	@Test
	public void testgenerateLocalFilePath(){
		System.out.println(reconciliationService.generateLocalFilePath("dz_mobile_20120331140117.zip"));	

	}

}
