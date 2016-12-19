/** @Description 
 * @project 	poss-withdraw
 * @file 		QueryBatchInfoServiceTest.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-14		Henry.Zeng			Create 
 */
package com.pay.fundout.withdraw.service.fileservice;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

import com.pay.fundout.withdraw.dao.fileservice.QueryBatchFileDao;
import com.pay.fundout.withdraw.model.fileservice.QueryBatchWithDraw;
import com.pay.fundout.withdraw.model.fileservice.WithdrawBatchInfo;
import com.pay.fundout.withdraw.model.ruleconfig.BatchRuleConfig;
import com.pay.fundout.withdraw.model.work.WithdrawWorkorder;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.base.model.BatchFileInfo;
import com.pay.poss.base.model.BatchInfo;

/**
 * <p>
 * test Fileservice
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-9-14
 * @see
 */
@ContextConfiguration(locations = {
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:config/spring/platform/*.xml",
		"classpath*:context/**/context-fundout-withdraw-fileservice-*.xml",
		"classpath*:context/**/context-fundout-withdraw-fileservice-*.xml",
		"classpath*:context/**/context-inf-pagemsg-*.xml" })
public class QueryBatchInfoDaoTest extends AbstractTestNGSpringContextTests {

	@Resource(name = "fundout-withdraw-querybatchfiledao")
	private QueryBatchFileDao queryBatchFileDao;

	public void setQueryBatchFileDao(QueryBatchFileDao queryBatchFileDao) {
		this.queryBatchFileDao = queryBatchFileDao;
	}

	@Autowired
	@Qualifier("PLATFORM.DEFAULT.DAOSERVICE")
	private BaseDAO daoService;

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

	@Test
	public void testQueryBatchInfo() {
		Page<WithdrawBatchInfo> queryPage = new Page<WithdrawBatchInfo>();

		queryPage.setPageSize(10);

		QueryBatchWithDraw queryBatchWithDraw = new QueryBatchWithDraw();

		queryBatchWithDraw.setStartTime(new Date());

		queryBatchWithDraw.setEndTime(new Date());

		queryBatchWithDraw.setBatchStatus(1);

		queryBatchWithDraw.setBusiType("1");

		AssertJUnit.assertNotNull(queryBatchFileDao.findWdBatchInfo4Page(
				queryPage, queryBatchWithDraw));
		AssertJUnit.assertNotNull(queryBatchFileDao.findWdSubmitBank4Page(
				queryPage, queryBatchWithDraw));

	}

	@Test
	public void testQueryBatchRuleConfig() {
		for (BatchRuleConfig batchRuleConfig : queryBatchFileDao
				.queryBatchRuleConfig()) {
			System.out.println(batchRuleConfig);
		}
	}

	@Test
	public void testUpdateBatchInfo() {
		BatchInfo batchInfo = new BatchInfo();
		batchInfo.setBatchNum("1");
		batchInfo.setBatchName("hello");
		queryBatchFileDao.updateWdBatchInfo(batchInfo);
	}

	@Test
	public void testUpdateBatchFileInfo() {
		BatchFileInfo batchFileInfo = new BatchFileInfo();
		batchFileInfo.setBatchNum("1");
		queryBatchFileDao.updateBatchFileInfo(batchFileInfo);
	}

	@Test
	public void testUpdateWithdrawWorkorder() {
		WithdrawWorkorder withdrawWorkorder = new WithdrawWorkorder();
		withdrawWorkorder.setWorkOrderky(1L);
		queryBatchFileDao.updateWdWorkorder(withdrawWorkorder);
	}

	@Test
	public void testQueryBatchInfoById() {
		BatchInfo batchInfo = new BatchInfo();
		batchInfo.setBatchNum("1");
		queryBatchFileDao.queryBatchInfo(batchInfo);
	}

	@Test
	public void testQueryBatchFileInfo() {
		BatchFileInfo batchFileInfo = new BatchFileInfo();
		batchFileInfo.setBatchNum("M_189");
		batchFileInfo.setFileType(12L);
		queryBatchFileDao.queryBatchFileInfo(batchFileInfo);
	}

	public void testUpdate() {

		Map<String, String> params = new HashMap<String, String>();
		params.put("batchNum", "M_108");
		params.put("seqSql", "(14,15)");
		daoService.update("wdfileservice.fundout-withdraw-update-generate",
				params);
		Map<String, String> params1 = new HashMap<String, String>();
		params1.put("batchNum", "M_108");
		daoService.update("wdfileservice.fundout-withdraw-update-batch-auto",
				params1);
		params1.put("newBatchNum", "M_208");
		daoService.update("wdfileservice.fundout-withdraw-update-regenerate",
				params1);

	}

}
