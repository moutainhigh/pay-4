/**
 *  File: BankBrancheInfoServiceTest.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-2   terry     Create
 *
 */
package com.pay.lucene.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.inf.dao.Page;
import com.pay.lucene.dto.BankBrancheInfoDto;
import com.pay.lucene.service.BankBrancheInfoService;

/**
 * 
 */
public class BankBrancheInfoServiceTest extends AbstractTestNGSpringContextTests {

	@Resource(name="fundout-bankBrancheInfoService")
	private BankBrancheInfoService bankBrancheInfoService;
	
	//@Test
	public void testAdd(){
		BankBrancheInfoDto bankInfo = new BankBrancheInfoDto();
		bankInfo.setAddress("中国银行西安太白小区支行");
		bankInfo.setBankKaihu("中国银行西安太白小区支行");
		bankInfo.setBankName("中国银行");
		bankInfo.setBankNumber("104791005929");
		bankInfo.setCity("西安市");
		bankInfo.setProvince("陕西省");
		bankInfo.setFlag(1);
		bankBrancheInfoService.addBankBrancheRnTx(bankInfo);
	}
	
	@Test
	public void testQuery(){
		BankBrancheInfoDto bankInfo = new BankBrancheInfoDto();
		Page page = new Page();
		bankInfo.setProvince("上海市");
		List<BankBrancheInfoDto> list =bankBrancheInfoService.findByCondition(page, bankInfo);
		if(null != list){
			System.out.println(list.size());
		}
	}
}
