package com.pay.app.test.subscribeDao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.app.common.enums.NoticeModeEnum;
import com.pay.app.dao.subscribe.SubscribeNotifyDAO;
import com.pay.app.model.SubscribeNotify;

@ContextConfiguration(locations = { "classpath*:context/*.xml" })
public class SubscribeNotifyDAOTest extends AbstractTestNG{
	
	@Resource(name="base-subscribeNotifyDAO")
	SubscribeNotifyDAO subscribeNotifyDao;
	
	@Test
	public void testSubscribeNotifyDao(){
		Assert.assertNotNull(this.subscribeNotifyDao);
	}
	
	@Test
	public void testSelectSubScribeNotify(){		
		this.subscribeNotifyDao.querySubscribeNotifyList(null);
	}
	
	@Test
	public void testCreateSubscribeNotify(){
		SubscribeNotify sub=new SubscribeNotify();
		sub.setNoticeMode(new Long(NoticeModeEnum.EMAIL.getCode()));
		sub.setMemberCode(10000000020L);
		sub.setStatus(1L);
		sub.setType(1L);
//		订阅类型 1余额增加 2余额减少 3付款到支付 4登录
		sub.setContext("个人帐单处理通知");
		subscribeNotifyDao.create(sub);
	}

	
//	@Test
	public void testCount(){
		this.subscribeNotifyDao.countSubscribeNotifyByMap(null);
	}

//	@Test
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
		sub1.setNoticeMode(new Long(NoticeModeEnum.NOTE.getCode()));
		sub1.setMemberCode(10000000020L);
		sub1.setStatus(1L);
		sub1.setType(3L);
		sub1.setContext("付款到支付");
		list.add(sub1);
		this.subscribeNotifyDao.batchInsert(list);
	}
	
//	@Test
	public void testSelectById(){
		SubscribeNotify sub=(SubscribeNotify) subscribeNotifyDao.findById(322L);
		sub.setNoticeMode(new Long(NoticeModeEnum.WEBSITE_MSG.getCode()));
		sub.setContext("余额减少");
		sub.setStatus(2L);
		subscribeNotifyDao.update(sub);
	}
	
	
	@Override
    public void testDeleteById() {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void testFindById() {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void testLoadAll() {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void testSave() {
	    // TODO Auto-generated method stub
	    
    }

	@Override
    public void testUpdate() {
	    // TODO Auto-generated method stub
	    
    }

}
