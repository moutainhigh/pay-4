package com;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.pe.account.dao.EntryQueryDao;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.service.AccService;
import com.pay.pe.manualbooking.service.VouchService;

@ContextConfiguration(locations = { "classpath*:/context/*.xml" ,"classpath*:/context/spring/**/*.xml"})


public class PePossTest extends
	AbstractTestNGSpringContextTests{


//	@Resource
//	private PEService peService;
	
	@Resource
	private  EntryQueryDao entryQueryDao  ;
	
	@Resource
	private  AccService accService  ;
	
	@Resource
	private  VouchService vouchService  ;
	
	
	private  String getOrderCode(int pkgCode){
//		int a = Integer.parseInt(pkgCode);
		int a = pkgCode ;
		int b = a/10 ;
		int c = b*10 ;
		return c+"" ;
	}
	
	
//	@Test
//	public void testCalculateFeeDetai181() throws Exception {
//		
//		MfDate beginDate = new MfDate();
//		MfDate endDate = new MfDate();
//		
//		QueryBalanceDTO queryBalance = entryQueryDao.getBalanceInfo("2181010010001", beginDate, endDate);
//		System.out.println(queryBalance)		;
//	}
	
	
	@Test
	public void testAccService() throws Exception {
//		VouchDataDto vouchDataDto = new VouchDataDto();
//		vouchDataDto.setCreator("adminA");
//		vouchDataDto.setCreateDate(new java.util.Date());
//		vouchDataDto.setVouchDataId(8001000000160L);
//		vouchDataDto.setApplicationCode("2010111601188");
		System.out.println(accService)		;
		System.out.println(vouchService)		;
		
		VouchDataDto vouchDataDto = vouchService.getVouchDataById(8001000000021L);
		System.out.println(vouchDataDto)		;
		
		accService.posting(vouchDataDto);
	
		
	}
	
	
	
	
	
	
	
	
	
	
}