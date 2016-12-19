//package com.pay.acc.service.translog.service;
//
//import java.util.Date;
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
//import com.pay.acc.translog.dto.TransLogDto;
//import com.pay.acc.translog.exception.TransLogException;
//import com.pay.acc.translog.exception.TransLogUnknowException;
//import com.pay.acc.translog.service.TransLogService;
//
//@ContextConfiguration(locations = { "classpath*:context/*.xml" })
//@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = true)
//@Transactional
//public class TransLogServiceTest extends AbstractTransactionalTestNGSpringContextTests{
//	
//	@Resource(name = "acc-transLogService")
//	private TransLogService transLogService;
//	@Resource(name = "dataSourceAcc")
//	public void setDataSource(DataSource dataSource) {
//		super.setDataSource(dataSource);
//	}
//	@Test
//	public void testTransLogService(){
//		Assert.assertNotNull(this.transLogService);
//	}
//	@Test
//	public void testCreateTransLog(){
//		TransLogDto transLogDto = new TransLogDto();
//		transLogDto.setAcctType(10);
//		transLogDto.setAmount(1000l);
//		transLogDto.setConfirmDate(new Date());
////		transLogDto.setPayAcct(2010092612001l);
////		transLogDto.setRecvAcct(2010092612002l);
//		transLogDto.setPayType(1);
//		transLogDto.setPayDate(new Date());
//		transLogDto.setSerialNo(2010092612001001111l);
//		transLogDto.setStatus(1);
//		transLogDto.setCreateDate(new Date());
//		transLogDto.setUpdateDate(new Date());
//		try {
//		Long id=this.transLogService.createTransLog(transLogDto);
//		Assert.assertNotNull(id);
//		} catch (TransLogException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (TransLogUnknowException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//
//}
