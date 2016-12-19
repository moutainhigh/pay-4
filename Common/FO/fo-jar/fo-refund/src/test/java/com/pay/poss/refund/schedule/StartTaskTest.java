/**
 * 
 */
package com.pay.poss.refund.schedule;

import java.util.ArrayList;
import java.util.List;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.pay.poss.base.env.context.ContextService;
import com.pay.poss.refund.schedule.StartTask;

/**
 * @Description
 * @project poss-refund
 * @file StartTaskTest.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-9-8 Rick.Lv Create
 */
@ContextConfiguration(locations = {
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/spring/base/*.xml",
		"classpath*:context/spring/refund/context-fundout-refund-file-service.xml"})
public class StartTaskTest extends AbstractTestNGSpringContextTests {

	@BeforeClass
	public void init() {
	}

//	@Test
	public void testReBuildFile() {
		String oldBatchNum = "";
		StartTask.getInstance().reBuildFile(oldBatchNum);
	}

//	@Test
	public void testReBuildBatch() {
		String oldBatchNum = "SYS_0";
		StartTask.getInstance().reBuildBatch(oldBatchNum);
	}

	@Test
	public void testScheduleBuildBatch() {
		StartTask.getInstance().scheduleBuildBatch("1:200001:11:11:11:20100910151001555");
	}

//	@Test
	public void testManualBuildBatch() {
		List<String> seqs = new ArrayList<String>();
		seqs.add("1");
		seqs.add("2");
		StartTask.getInstance().manualBuildBatch(seqs);
		try {
			Thread.sleep(50000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
