/**
 *  File: ProvinceLuceneServiceTest.java
 *  Description:
 *  Copyright 2006-2011 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2011-9-14   terry     Create
 *
 */
package com.pay.lucene.service.impl;

import javax.annotation.Resource;

import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.lucene.service.ProvinceLuceneService;

/**
 * 
 */
public class ProvinceLuceneServiceTest extends AbstractTestNGSpringContextTests {

	@Resource(name = "fundout-provinceLuceneService")
	private ProvinceLuceneService provinceLuceneService;

	@Test
	public void testBuildProvinceIndex() {
		provinceLuceneService.buildProvinceIndex();
	}

	@Test
	public void testSearchProvince() {
		SearchResultInfoDTO result = provinceLuceneService.searchProvince("上海市");
		System.out.println(result.getProvinceCode() + "$$$$$");
	}
}
