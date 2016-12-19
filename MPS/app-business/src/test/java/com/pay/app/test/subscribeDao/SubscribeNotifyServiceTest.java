package com.pay.app.test.subscribeDao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.app.common.enums.NoticeModeEnum;
import com.pay.app.model.SubscribeNotify;
import com.pay.app.service.subscribe.SubscribeNotifyService;
import com.pay.base.exception.matrixcard.MatrixCardException;
@ContextConfiguration(locations = { "classpath*:context/*.xml" })
@TransactionConfiguration(transactionManager = "accTxManager", defaultRollback = false)
@Transactional
public class SubscribeNotifyServiceTest extends AbstractTransactionalTestNGSpringContextTests{
	
	@Resource(name="base-subscribeNotifyService")
	SubscribeNotifyService subscribeNotifyService;
	
	@Test
	public void testSubscribeNotiryService(){
		Assert.assertNotNull(this.subscribeNotifyService);
	}
	
	@Resource(name="dataSourceAcc")
	public void setDataSource(DataSource dataSource){
		super.setDataSource(dataSource);
	}
	
	@Test
	public void testBatchInsert(){
		SubscribeNotify sub=new SubscribeNotify();
		sub.setNoticeMode(new Long(NoticeModeEnum.EMAIL.getCode()));
		sub.setMemberCode(10000000117L);
		sub.setStatus(1L);
		sub.setType(2L);
//		订阅类型 1余额增加 2余额减少 3付款到支付 4登录
		sub.setContext("余额减少");
		List<SubscribeNotify> list=new ArrayList<SubscribeNotify>();
		list.add(sub);
		SubscribeNotify sub1=new SubscribeNotify();
		sub.setNoticeMode(new Long(NoticeModeEnum.NOTE.getCode()));
		sub.setMemberCode(10000000117L);
		sub1.setStatus(1L);
		sub1.setType(3L);
		sub1.setContext("付款到支付");
		list.add(sub1);
		try {
	        this.subscribeNotifyService.batchInsertRdTx(list);
        }
        catch (MatrixCardException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
        }
	}
	
}
