package com.pay.fundout.withdraw.service.fileservice.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.fundout.withdraw.dao.fileservice.QueryBatchFileDao;
import com.pay.fundout.withdraw.schedule.StartTask;
import com.pay.fundout.withdraw.service.fileservice.SpecialBusiFundoutService;
import com.pay.poss.base.model.BatchInfo;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

/**		
 *  @author lIWEI
 *  @Date 2011-5-10
 *  @Description
 *  @Copyright Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
public class SpecialBusiFundoutServiceImpl implements SpecialBusiFundoutService {

	private QueryBatchFileDao queryBatchFileDao;
	
	public void setQueryBatchFileDao(final QueryBatchFileDao queryBatchFileDao) {
		this.queryBatchFileDao = queryBatchFileDao;
	}
	
	/* (non-Javadoc)
	 * @see com.pay.fundout.withdraw.service.fileservice.SpecialBusiFundout#generateSpecialBusiBatchInfo()
	 */
	@Override
	public void generateSpecialBusiBatchInfo() {
		// 1.求特殊商户待出批次工单列表
		List<String> workorderList = new ArrayList<String>();
		workorderList = queryBatchFileDao.findByQuery("fundoutBatch.fundout-special-batch-data", new Object());
		if(null==workorderList || workorderList.size()==0){
			LogUtil.info(getClass(), "特殊商户自动批次-没有待出批次数据", OPSTATUS.FAIL, "", "");
			return;
		}
		// 2.求ruleKy
		String ruleKy = null;
		List<String> ruleKys = queryBatchFileDao.findByQuery("fundoutBatch.fundout-ruleky-query", new Object());
		if(null==ruleKys || ruleKys.size()==0){
			LogUtil.info(getClass(), "特殊商户自动批次-请先设置批次规则", OPSTATUS.FAIL, "", "");
			return;
		}
		ruleKy = ruleKys.get(0);
		
		// 3.生成批次文件
		String batchNum = StartTask.getInstance().manualBuildBatch(workorderList,ruleKy);
		
		if("ignor".equals(batchNum)) 
			return;
		
		// 4把批次名称更新批次表
		BatchInfo batchInfo = new BatchInfo();
		batchInfo.setBatchNum(batchNum);
		batchInfo.setBatchName("特殊商户自动批次");
		queryBatchFileDao.updateWdBatchInfo(batchInfo);

	}

}
