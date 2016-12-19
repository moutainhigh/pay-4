 /** @Description 
 * @project 	poss-refund
 * @file 		RefundManageServiceImplTest.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-31		sunsea.li		Create 
*/
package com.pay.poss.refund.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.refund.common.constant.RefundConstants;
import com.pay.poss.refund.dao.RefundDao;
import com.pay.poss.refund.model.RefundOrderD;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.model.RefundWorkOrderAndM;
import com.pay.poss.refund.model.WebQueryRefundDTO;
import com.pay.poss.refund.service.RefundManageService;

/**
 * <p>充退管理服务具体实现Test类</p>
 * @author sunsea.li
 * @since 2010-8-25
 * @see 
 */
@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml",
					"classpath*:config/ibatis/mappings/**/*.xml","classpath*:config/spring/**/*.xml","classpath*:context/*.xml" })
public class RefundManageServiceImplTest  extends AbstractTestNGSpringContextTests {
	private transient Log log = LogFactory.getLog(getClass());
	
	/**
	 * 处理数据库操作的基础DAO
	 */
	@Autowired
	@Qualifier("PLATFORM.DEFAULT.DAOSERVICE")
	private BaseDAO<Object> baseDao;

	@Autowired
	@Qualifier("refundDao")
	private RefundDao refundDao;//本地数据库访问对象
	
	@Autowired
	private RefundManageService refundManageService;	//充退服务


	@Test
	public void testHanderRefundApply() {
		//组织主表信息，插入本地数据库
		RefundOrderM mDto = new RefundOrderM();
		long acceptKy = (Long) baseDao.create(RefundConstants.CREATE_REFUNDORDERM ,mDto);
		
		//组织从表信息，批量插入数据库
		for(RefundOrderD dDto:mDto.getListDetails()){
			dDto.setMasterKy(new Long(acceptKy));
			//long detailKy = baseDao.create(RefundConstants.CREATE_RefundOrderD, mDto.getListDetails());
			//dDto.setDetailKy(detailKy);
		}
		List<Long> detailKeys = refundDao.insertBatch(RefundConstants.CREATE_REFUNDORDERD, mDto.getListDetails());
		System.out.println(detailKeys);
	}
	
	//add jason_wang
	@Test
	public void testHanderRefundAuditSingle(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("refundStatus","1");
		params.put("workOrderKy","1");
		params.put("userId","Deki_liu@staff.hnacom");
		params.put("processInstanceId","refund.530015");
		params.put("refundRemark","审核通过");
		
		Map <String,Object> result = refundManageService.handerRefundAuditSingle(params);
		log.debug("处理结果:" + result.toString());
	}
	
	@Test
	public void testQueryRefundReAuditInfo(){
		Page<RefundWorkOrderAndM> page = new Page<RefundWorkOrderAndM>();
		WebQueryRefundDTO query = new WebQueryRefundDTO();
		query.setUserId("wolf_huang@staff.hnacom");
		
		Map<String,Object> params = refundManageService.queryRefundReAuditInfo(page, query);
		log.debug("查询结果:" + params.toString());
	}
	
	@Test
	public void testHanderRefundReAuditSingle(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("refundStatus","4");
		params.put("workOrderKy","1");
		params.put("userId","wolf_huang@staff.hnacom");
		params.put("processInstanceId","refund.530015");
		params.put("refundRemark","复核通过");
		params.put("handleType","agree");
		
		Map <String,Object> result = refundManageService.handerRefundReAuditSingle(params);
		log.debug("处理结果:" + result.toString());
	}
	
	@Test
	public void testQueryRefundLiquidateInfo(){
		Page<RefundWorkOrderAndM> page = new Page<RefundWorkOrderAndM>();
		WebQueryRefundDTO query = new WebQueryRefundDTO();
		query.setUserId("gunther_zhen@staff.hnacom");
		
		Map<String,Object> params = refundManageService.queryRefundLiquidateInfo(page, query);
		log.debug("查询结果:" + params.toString());
	}
	
	@Test
	public void testHanderRefundLiquidateSingle(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("refundStatus","6");
		params.put("workOrderKy","1");
		params.put("userId","gunther_zhen@staff.hnacom");
		params.put("processInstanceId","refund.530015");
		params.put("refundRemark","清算拒绝");
		params.put("handleType","flowEnd");
		
		Map <String,Object> result = refundManageService.handerRefundLiquidateSingle(params);
		log.debug("处理结果:" + result.toString());
	}
	//end jason_wang
}
