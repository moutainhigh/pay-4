///**
// *  File: HotelManageServiceTest.java
// *  Description:
// *  Copyright 2006-2011 pay Corporation. All rights reserved.
// *  Date      Author      Changes
// *  2011-12-1   liwei     Create
// *
// */
//package com.pay.acc.cert.test;
//
//import java.math.BigDecimal;
//
//import javax.annotation.Resource;
//
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.testng.annotations.Test;
//
//import com.pay.acc.acct.dto.MemberAcctDto;
//import com.pay.acc.acct.service.AcctService;
//import com.pay.acc.balancelog.dto.FrozenAmountDto;
//import com.pay.acc.balancelog.dto.UnFrozenAmountDto;
//import com.pay.acc.balancelog.service.FrozenOperatorLogService;
//
///**		
// *  @author ddr
// *  @Date 2012-6-6
// *  @Description
// */
//@ContextConfiguration(locations = { 
//		"classpath*:context/context-acct-bean.xml",
//		"classpath*:context/test-context-acct-datasource.xml"
//})
//
//public class FrozenOperatorLogServiceTest extends AbstractTestNGSpringContextTests {
//	@Resource(name = "acc-acctService")
//	private AcctService acctService;
//	
//	@Resource(name = "acc-frozenOperatorLogService")
//	private FrozenOperatorLogService fzLogService;
//	
//	
//	//@Test
//	public void testCreateLog(){
//		FrozenAmountDto fdt = new FrozenAmountDto();
//		fdt.setAcctCode("20010200100012000000004710");
//		fdt.setMemberCode(20000000047L);
//		fdt.setFrozenAmount(BigDecimal.valueOf(1000));
//		acctService.addFrozenAmount(fdt);
//		
//	}
//	
//	
//	//@Test
////	public void testCreateListLog(){
////		List<FrozenAmountDto> fdts = new ArrayList<FrozenAmountDto>();
////		
////		for(int i  = 0;i<10;i++){
////			FrozenAmountDto fdt = new FrozenAmountDto();
////			fdt.setAcctCode("20010200100012000000004710");
////			fdt.setMemberCode(20000000047L);
////			fdt.setFrozenAmount(BigDecimal.valueOf(20));
////			fdts.add(fdt);
////		}
////		
////		Boolean is =  acctService.batchAddFrozenAmount(fdts);
////		List list =  fzLogService.batchAddFrozenLog(fdts);
////		System.out.println("成功吗？-->"+is);
////		System.out.println("日志记录？-->"+list.size());
////		
////	}
//	
//	
//	//@Test
////	public void testCreateUnFrozenListLog(){
////		List<UnFrozenAmountDto> fdts = new ArrayList<UnFrozenAmountDto>();
////		
////		for(int i  = 0;i<1;i++){
////			UnFrozenAmountDto fdt = new UnFrozenAmountDto();
////			fdt.setAcctCode("20010200100011000000038410");
////			fdt.setMemberCode(10000000384L);
////			fdt.setUnFrozenAmount(BigDecimal.valueOf(10));
////			fdts.add(fdt);
////		}
////		
////		Boolean is =  acctService.batchUnFrozenAmount(fdts);
////		List list =  fzLogService.batchUnFrozenLog(fdts);
////		System.out.println("成功吗？-->"+is);
////		 
////	}
//	
////	@Test
//	public void testCreateUnFrozentLog(){
//			UnFrozenAmountDto fdt = new UnFrozenAmountDto();
//			fdt.setAcctCode("20010200100012000000004710");
//			fdt.setMemberCode(20000000047L);
//			fdt.setUnFrozenAmount(BigDecimal.valueOf(10));
//			fdt.setOrderSeqNo(System.currentTimeMillis()+"");
//		Boolean is =  acctService.unFrozenAmount(fdt);
//		System.out.println("成功？-->"+is);
//		
//	}
//	@Test
//	public void testQueryMemberAcct(){
//		
//		MemberAcctDto dto = acctService.queryMemberAcctByloginName("13761123436");
//	System.out.println("成功？-->"+dto.getMemberCode());
//	MemberAcctDto dto2 = acctService.queryMemberAcctByMemberCode(20000000047L);
//	System.out.println("成功？-->"+dto2.getLoginName());
//	
//}
//	
//}
