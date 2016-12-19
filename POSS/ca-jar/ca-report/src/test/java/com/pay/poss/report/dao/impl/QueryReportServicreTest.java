//package com.pay.poss.report.dao.impl;
///**  
// * @author      : sean  
// * @create_date : 2011-12-27 下午03:44:02  
// * @Name 	   : Test.java
// */
//
//import java.util.List;
//
//import javax.annotation.Resource;
//
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
//import org.testng.annotations.Test;
//
//import com.pay.poss.report.dto.QueryRequstParameter;
//import com.pay.poss.report.dto.QueryResponseDTO;
//import com.pay.poss.report.service.QueryCostServicre;
//
//
//@ContextConfiguration(locations = {"classpath*:/context/*.xml","classpath*:/context/poss/*.xml","classpath*:/context/intra/*.xml"})
//public class QueryReportServicreTest extends AbstractTestNGSpringContextTests {
//
//	@Resource(name="poss_report_queryCostService")
//	QueryCostServicre queryReportServicre;
//	
//	@Test
//	public void testService(){
//		QueryRequstParameter parameter = new QueryRequstParameter();
//		parameter.setBusinessType("5");
//		parameter.setMemberType(2);
////		parameter.setInnerMember("y");
//		parameter.setStartDate("2011-09-30");
//		parameter.setEndDate("2011-11-30");
//		//parameter.setMemberCode("20000000159");
//		List<QueryResponseDTO> list = queryReportServicre.getAllQueryReport(parameter);
//		System.out.println(list.size());
//		
//		System.out.println("test");
//	}
//}
