package com.pay.base.service.queryhistory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import com.pay.app.facade.dto.MaSumDto;
import com.pay.base.model.Acct;
import com.pay.base.service.queryhistory.QueryBalanceService;
@ContextConfiguration(locations = { "classpath*:context/*.xml" })
@TransactionConfiguration(transactionManager = "app-transactionManager", defaultRollback = true)
@Transactional
public class QueryCorpBalanceServiceTest extends AbstractTransactionalTestNGSpringContextTests {

	@Resource(name="base-queryBalanceService")
	private QueryBalanceService queryCorpBalanceService;

	@Resource(name="dataSourceAcc")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	//@Test
	public void testQueryBalanceList() {
		//fail("Not yet implemented");
	}

//	@Test
//	public void testQueryHistoryBusinessSum() throws ParseException {
//		Acct acct = new Acct();
//		acct.setAcctCode("1000000005610");
//		acct.setBalance(1234567.12);
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//		MaSumDto maSumDto = queryCorpBalanceService.queryHistoryBusinessSum(sdf.parse("2010-9-1"), sdf.parse("2010-10-31"), acct,null,"20");
//	}

}
