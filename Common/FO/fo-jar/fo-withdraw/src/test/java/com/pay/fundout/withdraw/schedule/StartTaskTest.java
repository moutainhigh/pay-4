 /** @Description 
 * @project 	poss-withdraw
 * @file 		StartTaskTest.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-11		Rick_lv			Create 
*/
package com.pay.fundout.withdraw.schedule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.schedule.StartTask;


@ContextConfiguration(locations = {
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/spring/base/*.xml",
		"classpath*:context/spring/bankfile/*.xml"})
public class StartTaskTest extends AbstractTestNGSpringContextTests {
	@Test
	public void testManualBuildBatch() {
		List<String> seqs = new ArrayList<String>();
		seqs.add("2001011061440004661");
		seqs.add("2001011091623004862");
		seqs.add("2001011091822004885");
		seqs.add("2001011052006004589");
		seqs.add("2001011051014003126");
		seqs.add("2001011051014003128");
		seqs.add("2001011051014003130");
		seqs.add("2001011051151003152");
		StartTask.getInstance().manualBuildBatch(seqs,"1");

	}
}