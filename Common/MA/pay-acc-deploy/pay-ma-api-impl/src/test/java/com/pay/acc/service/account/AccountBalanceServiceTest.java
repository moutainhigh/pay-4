///**
// * 
// */
//package com.pay.acc.service.account;
//
//import java.util.ArrayList;
//import java.util.Date;
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
//import com.pay.acc.service.account.AccountBalanceService;
//import com.pay.acc.service.account.dto.AccountBalanceChangeDto;
//import com.pay.acc.service.account.dto.AcctDetailBalanceDto;
//import com.pay.acc.service.account.dto.ChargeUpPEDto;
//import com.pay.acc.service.account.exception.MaAcctBalanceException;
//
///**
// * @author Administrator
// * 
// */
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
//@Transactional
//public class AccountBalanceServiceTest extends AbstractTransactionalTestNGSpringContextTests {
//	@Resource(name = "acc-accountBalanceService")
//	private AccountBalanceService accountBalanceService;
//
//	@Resource(name = "dataSourceAcc")
//	public void setDataSource(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}
//
//	@Test
//	public void testAccountBalanceService() {
//		Assert.assertNotNull(this.accountBalanceService);
//	}
//
//	@Test
//	public void testDoUpdateAcctBalanceRntx() {
//		try {
//			int result = this.accountBalanceService.doUpdateAcctBalanceRntx(initAccountBalanceChangeDtoData());
//			Assert.assertEquals(result, 1);
//		} catch (MaAcctBalanceException e) {
//			e.printStackTrace();
//		}
//	}
//
//	@Test(expectedExceptions = MaAcctBalanceException.class)
//	public void testDoUpdateAcctBalanceRntxWithException() throws MaAcctBalanceException {
//		int result = this.accountBalanceService.doUpdateAcctBalanceRntx(initAccountBalanceChangeDtoData());
//		Assert.assertNotSame(result, 1);
//	}
//
//	private AccountBalanceChangeDto initAccountBalanceChangeDtoData() {
//		AccountBalanceChangeDto accountBalanceChangeDto = new AccountBalanceChangeDto();
//		List<AcctDetailBalanceDto> lists = new ArrayList<AcctDetailBalanceDto>();
//		AcctDetailBalanceDto acctDetailBalanceDto = null;
//		// 借
//		acctDetailBalanceDto = new AcctDetailBalanceDto();
//
//		acctDetailBalanceDto.setAmount(1000l);
//		acctDetailBalanceDto.setSerialNo(2001010080944000531l);
//		acctDetailBalanceDto.setAcctCode("20010200100011000000011410");
//		acctDetailBalanceDto.setPayType(2);
//		acctDetailBalanceDto.setAmountBy(2);
//		acctDetailBalanceDto.setDebitBy(1);
//		acctDetailBalanceDto.setPayDate(new Date());
//		lists.add(acctDetailBalanceDto);
//		// 贷
//		acctDetailBalanceDto = new AcctDetailBalanceDto();
//
//		acctDetailBalanceDto.setAmount(1000l);
//		acctDetailBalanceDto.setSerialNo(2001010080944000531l);
//		acctDetailBalanceDto.setAcctCode("2181010010002");
//		acctDetailBalanceDto.setPayType(2);
//		acctDetailBalanceDto.setAmountBy(1);
//		acctDetailBalanceDto.setDebitBy(2);
//		acctDetailBalanceDto.setPayDate(new Date());
//
//		lists.add(acctDetailBalanceDto);
//
//		ChargeUpPEDto chargeUpPEDto = new ChargeUpPEDto();
//		chargeUpPEDto.setOrderAmount(1000l);
//		chargeUpPEDto.setPayAcctCode("20010200100011000000011410");
//		chargeUpPEDto.setRevAcctCode("2181012010002");
//		chargeUpPEDto.setPayAcctType(10);
//		accountBalanceChangeDto.setChargeUpPEDto(chargeUpPEDto);
//		accountBalanceChangeDto.setAcctDetailBalanceDtos(lists);
//		return accountBalanceChangeDto;
//
//	}
//
//	@Test
//	public void testInteger() {
//		Integer a = null;
//		int b = 128;
//		boolean result = (b == a);
////		System.out.println("============================" + (a == b) + "=========================================");
//
//	}
//
//}
