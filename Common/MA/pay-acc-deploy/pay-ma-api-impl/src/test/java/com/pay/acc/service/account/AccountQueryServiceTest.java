//package com.pay.acc.service.account;
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
//import com.pay.acc.acctattrib.dto.AcctAttribDto;
//import com.pay.acc.exception.MaAccountQueryUntxException;
//import com.pay.acc.service.account.dto.BalancesDto;
//
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = true)
//@Transactional
//public class AccountQueryServiceTest extends AbstractTransactionalTestNGSpringContextTests{
//	
//	@Resource(name="acc-accountQueryService")
//	private AccountQueryService accountQueryService;
//	
//	@Resource(name="dataSourceAcc")
//	public void setDataSource(DataSource dataSource){
//		super.setDataSource(dataSource);
//	}
//	@Test
//	public void testAccountQueryService(){
//		Assert.assertNotNull(this.accountQueryService);
//	}
//	
//	@Test
//	public void testdoQueryBalancesNsTx(){
//		try{
//			BalancesDto dto=this.accountQueryService.doQueryBalancesNsTx(10000000606L, 10);
//			System.out.println("可提现金额：  "+dto.getWithdrawBalance()+ "  当前余额   : "+dto.getBalance());
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//	}
//	//@Test
//	public void testDoQueryAcctAttribNsTx(){
//		try {
//		 AcctAttribDto acctAttribDto=this.accountQueryService.doQueryAcctAttribNsTx(10000000606L, 21);
//		 Assert.assertNotNull(acctAttribDto);
////		 Assert.assertEquals(acctAttribDto.getAcctCode().longValue(), 1000000003310l);
//		} catch (MaAccountQueryUntxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	//@Test
//	public void testDoQueryBalanceEntryForTransAmountRntx(){
//		try {
//		   Long memberCode=10000005663L;
//		   Integer acctType=20;
//		   Long amount=20002L;
//		   Integer days=2;
//		   boolean flag=this.accountQueryService.doQueryBalanceEntryForTransAmountRntx(memberCode,acctType,amount,days);
//		 Assert.assertNotNull(flag);
//		 Assert.assertFalse(flag);
//		} catch (MaAccountQueryUntxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	//@Test
//	public void test2DoQueryBalanceEntryForTransAmountRntx(){
//		try {
//		   Long memberCode=10000000109L;
//		   Integer acctType=10;
//		   Long amount=2000002L;
//		   Integer days=2;
//		   boolean flag=this.accountQueryService.doQueryBalanceEntryForTransAmountRntx(memberCode,acctType,amount,days);
//		 Assert.assertNotNull(flag);
//		 Assert.assertTrue(flag);
//		} catch (MaAccountQueryUntxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//	//@Test
//	public void testDoQueryBalanceEntryNsTx(){
//		try {
//			Long acctAttrib=this.accountQueryService.doQueryBalanceByEntryRntx(10000000114L, 10);
//			System.out.println("========"+acctAttrib);
//		
////		 Assert.assertEquals(acctAttribDto.getAcctCode().longValue(), 1000000003310l);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//	}
//	
//
//}
