/**
 *  File: ReconcileDao.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-10-2   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.reconcile.test.rcresult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.reconcile.dao.rcresult.QueryReconcileResultDao;
import com.pay.fundout.reconcile.model.rcresult.ReconcileResult;
import com.pay.fundout.reconcile.model.rcresult.ReconcileResultSummary;
import com.pay.inf.dao.Page;

/**
 * 对账DAO
 * @author Sandy_Yang
 */
@ContextConfiguration(locations={"classpath*:context/env/*.xml",
"classpath*:context/spring/**/fo-rc-queryresult-dao.xml"})
public class QueryReconcileResultDaoTest extends AbstractTestNGSpringContextTests{
	@Autowired
	private QueryReconcileResultDao queryReconcileResultDao;
	/**
	 * 查询对账列表
	 * @return
	 */
	@Test
	public void testQueryReconcileList(){
		Page<ReconcileResultSummary> page = new Page<ReconcileResultSummary>();
		page = queryReconcileResultDao.queryReconcileList(null, page);
		
	}
	
	/**
	 * 查询对账详情银行比数列表
	 * @param map
	 * @return
	 */
	@Test
	public void testQueryReconcileDetailByBank(){
		Page<ReconcileResult> page = new Page<ReconcileResult>();
		page = queryReconcileResultDao.queryReconcileDetailByBank(null, page);
		
	}
	/**
	 * 查询对账详情 比数列表
	 * @param map
	 * @param page
	 * @return
	 */
	@Test
	public void testQueryReconcileDetailBySys(){
		Page<ReconcileResult> page = new Page<ReconcileResult>();
		page = queryReconcileResultDao.queryReconcileDetailBySys(null, page);
	}
}
