/** @Description 
 * @project 	poss-withdraw
 * @file 		WithDrawRcServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-6		Henry.Zeng			Create 
 */
package com.pay.fundout.withdraw.service.reconcile.impl;

import com.pay.fundout.withdraw.service.reconcile.WithDrawRcService;
import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

/**
 * <p>
 * 提现对账Service实现类
 * </p>
 * 
 * @author Henry.Zeng
 * @param <E>
 * @since 2010-9-6
 * @see
 */
public class WithDrawRcServiceImpl implements WithDrawRcService {

	private BaseDAO<Object> iBaseDao;

	public void setiBaseDao(BaseDAO<Object> iBaseDao) {
		this.iBaseDao = iBaseDao;
	}

	@Override
	public Boolean invalidBatchFileRdTx(String batchNum) throws PossException {

		// 更新批次订单表的状态为已删除
		try {
			iBaseDao.update("WD.updateWithdrawWorkorder", batchNum);

			iBaseDao.update("WD.updateWithdrawBatchInfoDTO", batchNum);

			// 工作流退回审核节点

		} catch (Exception exception) {
			throw new PossException("invalidBatchFileRdTx is error...",
					ExceptionCodeEnum.TASK_EXCEPTION);
		}
		return null;
	}

}
