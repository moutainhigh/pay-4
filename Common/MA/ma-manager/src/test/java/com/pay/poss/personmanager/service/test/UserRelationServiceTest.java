package com.pay.poss.personmanager.service.test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pay.poss.userrelation.dto.NodesDto;
import com.pay.poss.userrelation.dto.UserRelationDto;
import com.pay.poss.userrelation.service.IUserRelationService;

@ContextConfiguration(locations = {"classpath*:context/test-*.xml","classpath*:context/ma/userrelation/*.xml", "classpath*:context/ma/base/*.xml","classpath*:config/spring/**/*.xml" })
public class UserRelationServiceTest extends AbstractTestNGSpringContextTests {
	
	@Autowired
	IUserRelationService relationService; 
	
	@Test
	public void testRelationService(){
		Assert.assertNotNull(this.relationService);		
	}
//	
	@Test
	public void testRelationAdd(){
		UserRelationDto userRelationDto = new UserRelationDto();
		userRelationDto.setLoginId("a8");
		userRelationDto.setName("a88");
		userRelationDto.setPloginId("aa");
		userRelationDto.setPname("root");
		this.relationService.createUserRelation(userRelationDto);
		List<NodesDto> list = this.relationService.findAllSubLoginId("aa");
		for (NodesDto string : list) {
			System.out.println("node:"+string);
			
		}
	}
//	
//	@Test
//	public void testRelationUpdate(){
//		List<String> list1 = this.relationService.findAllSubLoginId("a3");
//		for (String string : list1) {
//			System.out.println("before node:"+string);
//			
//		}
//		
//		UserRelationDto userRelationDto = new UserRelationDto();
//		userRelationDto.setId(26);
//		userRelationDto.setLoginId("a1");
//		userRelationDto.setLv(2);
//		userRelationDto.setRv(5);
//		userRelationDto.setName("a11");
//		userRelationDto.setPloginId("a3");
//		userRelationDto.setPname("a33");
//		
//		this.relationService.updateUserRelation(userRelationDto);
//		List<String> list = this.relationService.findAllSubLoginId("a3");
//		for (String string : list) {
//			System.out.println("node:"+string);
//			
//		}
//	}
//	
//	@Test
//	public void testRelationDelete(){
//		List<String> list1 = this.relationService.findAllSubLoginId("a3");
//		for (String string : list1) {
//			System.out.println("before node:"+string);
//			
//		}
//		
//	
//		
//		this.relationService.deleteUserRelation(31);
//		List<String> list = this.relationService.findAllSubLoginId("a3");
//		for (String string : list) {
//			System.out.println("node:"+string);
//			
//		}
//	}
	
}
