package com.pay.app.test.subscribeDao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.app.AbstractTestNG;
import com.pay.app.dto.SubscribeTypeDto;
import com.pay.app.service.subscribe.SubscribeTypeService;

@ContextConfiguration(locations = { "classpath*:context/*.xml" })
public class SubscribeTypeServiceTest extends AbstractTestNG {

	@Resource(name = "base-subscribeTypeService")
	SubscribeTypeService subscribeTypeService;

	@Test
	public void testSubscribeTypeService() {
		Assert.assertNotNull(this.subscribeTypeService);
	}

	@Test
	public void testSubscribeTypeList(){
		SubscribeTypeDto subscribeTypeDto=new SubscribeTypeDto();
		subscribeTypeDto.setBeginNum(0);
		subscribeTypeDto.setEndNum(100);
		List<SubscribeTypeDto> list=subscribeTypeService.querySubscribeTypeDtoList(subscribeTypeDto,10000000117L);
		for(SubscribeTypeDto dto:list){
			System.out.println(dto.getNoticeMode()+ "   "+ dto.getDescription());
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
