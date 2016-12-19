/**
 *  File: LuceneServiceImplTest.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-21      Jason_wang      Changes
 *  
 *
 */
package com.pay.lucene.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.lucene.service.LuceneService;

@ContextConfiguration(locations = { "classpath*:context/**/*.xml" })
public class LuceneServiceImplTest extends AbstractTestNGSpringContextTests {

	@Resource(name = "fundout-lucene-service")
	private LuceneService luceneService;

	//@Test
	public void testBuildIndex() {
		try {
			luceneService.buildIndex("");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	 @Test
	public void testBuildSpecialIndex() {
		try {
			luceneService.buildIndex("中国银行");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testSearch() {
		try {
			SearchParamInfoDTO params = new SearchParamInfoDTO();
			params.setBankName("中国工商银行");
			params.setProvinceName("湖南省");
			params.setCityName("邵阳市");
			//params.setKeyWord("隆回");
			List<SearchResultInfoDTO> result = luceneService
					.searchBankUnionCodes(params);

			if (null != result) {
				System.out.println("总记录数：" + result.size());
				for (SearchResultInfoDTO bankInfo : result) {
					System.out.println(bankInfo.getBankName());
				}
			}
			System.out
					.println("################################################################################");

			/*
			 * List<SearchResultInfoDTO> result1 =
			 * luceneService.searchBankInfo(params); if (null != result1) {
			 * System.out.println("总记录数：" + result1.size()); for
			 * (SearchResultInfoDTO bankInfo : result1) {
			 * System.out.println(bankInfo.getBankName()); } }
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// @Test
	public void test1() throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new FileInputStream(new File(
						"G:\\opt\\pay\\fo\\lucene\\data\\bankfile.txt")),
				"UTF-8"));
		BufferedWriter write = new BufferedWriter(
				new OutputStreamWriter(
						new FileOutputStream(
								new File(
										"G:\\opt\\pay\\fo\\lucene\\data\\bankfile1111.txt")),
						"UTF-8"));
		String readLine;
		Map<String, String> map = new HashMap<String, String>();
		List<Integer> list = new ArrayList<Integer>();
		int count = 0;
		StringBuffer str = new StringBuffer();
		while ((readLine = reader.readLine()) != null) {

			String[] _line = readLine.split(","); // 分隔
			String line = _line[4];
			count++;
			if (map.containsKey(line)) {
				list.add(count);
			} else {
				map.put(line, line);
				str.append(_line[0]).append(",").append(_line[1]).append(",")
						.append(_line[2] + ",").append(
								_line[3] + "," + _line[4] + ",").append(
								_line[5] + "," + _line[6] + ",").append(
								_line[7] + "," + "\n");
			}

		}
		write.write(str.toString());
		write.flush();
		System.err.println(count);
	}

}
