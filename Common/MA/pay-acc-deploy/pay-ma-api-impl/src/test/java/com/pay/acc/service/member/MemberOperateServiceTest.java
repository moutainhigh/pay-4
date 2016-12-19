//package com.pay.acc.service.member;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
//import org.springframework.test.context.transaction.TransactionConfiguration;
//import org.springframework.transaction.annotation.Transactional;
//import org.testng.Assert;
//import org.testng.annotations.Test;
//
//import com.pay.acc.member.service.MemberOperateService;
//import com.pay.acc.service.member.MemberProductService;
//import com.pay.acc.service.member.dto.MemberProductResult;
//
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class MemberOperateServiceTest extends AbstractTransactionalTestNGSpringContextTests{
//
//	@Resource(name="acc-memberOperateService")
//	private MemberOperateService memberOperateService;
//	@Resource(name="acc-memberProductService")
//	private MemberProductService memberProductService;
//	
//	@Resource(name="dataSourceAcc")
//	public void setDataSource(DataSource dataSource){
//		super.setDataSource(dataSource);
//	}
//	
//	//@Test
//	public void testMemberOperate(){
//		Assert.assertNotNull(this.memberOperateService);
//		Assert.assertNotNull(this.memberProductService);
//	}
//	
//	//@Test
//	public void testMemberProduct(){
//		boolean result=memberProductService.isHaveProduct(20000000044L, "testtest");
//		System.out.println("=========="+result);
//	}
//	
//	
//	@Test
//	public void testMemberOperatorProduct(){
//		//200028
//		MemberProductResult result=memberProductService.isHaveProduct(20000000011L, "ACCOUNT_PAY", 200028L);
//		if(!result.isReturnBool()){
//			System.out.println(result.getErrMsg());
//		}else{
//			System.out.println("==========yibangding");
//		}
//		
//	}
//}
