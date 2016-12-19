///**
// * 
// */
//package com.pay.acc.service.member;
//
//import java.util.List;
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
//import com.pay.acc.exception.MaMemberBankException;
//import com.pay.acc.service.member.MemberBankService;
//import com.pay.acc.service.member.dto.MemberBankDto;
//
///**
// * 
// * @date 2010-7-16
// */
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class MemberBankServiceTest extends AbstractTransactionalTestNGSpringContextTests {
//	
//	@Resource(name="acc-memberBankService")
//	private MemberBankService memberBankService;
//	
//	@Resource(name="dataSourceAcc")
//	public void setDataSource(DataSource dataSource){
//		super.setDataSource(dataSource);
//	}
//	
//	
//	@Test
//	public void testDoQueryMemberBankNsTx()throws MaMemberBankException{
//		try {
//			List<MemberBankDto> list = memberBankService.doQueryMemberBankNsTx(1000000090L);
//			Assert.assertNotNull(list);
//			//Assert.assertFalse(flag);
//		} catch (MaMemberBankException e) {
//			// TODO Auto-generated catch block
////			System.out.println("******************"+e.getErrorEnum().getMessage()+"******************");
//		}
//	}
//}
