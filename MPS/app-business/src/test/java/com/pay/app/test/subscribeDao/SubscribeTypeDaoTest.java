package com.pay.app.test.subscribeDao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.app.dao.subscribe.SubscribeTypeDAO;
import com.pay.app.dto.SubscribeTypeDto;
import com.pay.app.model.SubscribeType;
@ContextConfiguration(locations = { "classpath*:context/*.xml" })

public class SubscribeTypeDaoTest extends AbstractTestNG{
	
	@Resource(name="base-subscribeTypeDAO")
	SubscribeTypeDAO subscribeType;
	
	@Test
	public void testSubScribeType(){
		Assert.assertNotNull(this.subscribeType);
	}
	
	@Test
	public void testSubScribeTypeList(){
		SubscribeTypeDto subscribeTypeDto=new SubscribeTypeDto();
		subscribeTypeDto.setBeginNum(0);
		subscribeTypeDto.setEndNum(100);
		List<SubscribeType> list=this.subscribeType.querySubscribeTypeList(subscribeTypeDto);
		for(SubscribeType sub:list){
			System.out.println(sub.getName());
		}
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
