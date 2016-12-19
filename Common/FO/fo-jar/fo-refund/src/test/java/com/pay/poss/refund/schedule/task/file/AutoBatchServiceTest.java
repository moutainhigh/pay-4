package com.pay.poss.refund.schedule.task.file;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.pay.poss.refund.schedule.RefundAutoBatchFile;

@ContextConfiguration(locations = {
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/spring/base/*.xml",
		"classpath*:context/spring/refund/context-fundout-refund-file-service.xml",
		"classpath*:quartzcontext/context-fundout-batchfile-quartz.xml"})
public class AutoBatchServiceTest extends AbstractTestNGSpringContextTests{
	
	@Resource(name="fundout-refund-refundAutoBatchFile")
	private RefundAutoBatchFile refundAutoBatchFile;
	
	@BeforeMethod
	public void init(){
		System.out.println("============================");
	}
	
	
	@Test
	public void testQuartz(){
		refundAutoBatchFile.disposeBatchFile();
	}
}
