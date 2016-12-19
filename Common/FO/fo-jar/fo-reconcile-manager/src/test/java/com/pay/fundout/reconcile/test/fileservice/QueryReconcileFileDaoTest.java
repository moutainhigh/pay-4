 /** @Description 
 * @project 	fo-reconcile-manager
 * @file 		QueryReconcileFileDao.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-2		Henry.Zeng			Create 
*/
package com.pay.fundout.reconcile.test.fileservice;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.fundout.reconcile.dao.fileservice.QueryReconcileFileDao;
import com.pay.fundout.reconcile.model.fileservice.ReconcileImportFile;
import com.pay.fundout.reconcile.model.fileservice.WebQueryFile;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PlatformDaoException;

/**
 * <p>查询文件上传信息</p>
 * @author Henry.Zeng
 * @since 2010-10-2
 * @see 
 */
@ContextConfiguration(locations={"classpath*:context/env/*.xml",
		"classpath*:context/spring/rcfileservice/fo-rc-fileservice-dao.xml"})
public class QueryReconcileFileDaoTest extends AbstractTestNGSpringContextTests{
	
	public void setQueryReconcileFileDao(QueryReconcileFileDao queryReconcileFileDao) {
		this.queryReconcileFileDao = queryReconcileFileDao;
	}

	@Resource(name="fo_rc_queryreconcilefiledao")
	private QueryReconcileFileDao queryReconcileFileDao;
	/**
	 * 查看上传银行对账文件 页面显示
	 * @param webQueryFile
	 * @return
	 * @throws PlatformDaoException
	 */
	@Test
	public void testQuery(){
		Page<ReconcileImportFile> page = new Page<ReconcileImportFile>();
		WebQueryFile webQueryFile = new WebQueryFile();
		queryReconcileFileDao.query(page, null);
	}
	
	/**
	 * 废除文件
	 * @param fileId
	 * @return
	 * @throws PlatformDaoException
	 */
	@Test
	public void testDeleteByFileId() throws PlatformDaoException{
		
	}
	
	
}
