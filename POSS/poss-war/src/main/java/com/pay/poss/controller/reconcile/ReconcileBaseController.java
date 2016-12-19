/** @Description 
 * @project 	poss-withdraw
 * @file 		WithdrawBaseController.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-6		Henry.Zeng			Create 
 */
package com.pay.poss.controller.reconcile;

import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.service.common.GetSelectListInfoService;
import com.pay.poss.base.controller.AbstractBaseController;

/**
 * <p>
 * 提现基类Controller 提现Controller公共的方法在此添加
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-9-6
 * @see
 */
public abstract class ReconcileBaseController extends AbstractBaseController {

	protected List<Map<String, String>> bankList = null;
	protected List<Map<String, String>> busiFlagList = null;
	protected List<Map<String, String>> reviseStatusList = null;
	protected List<Map<String, String>> withdrawBusiTypeList = null;
	protected transient GetSelectListInfoService getSelectListInfoService;

	public void setGetSelectListInfoService(
			GetSelectListInfoService getSelectListInfoService) {
		this.getSelectListInfoService = getSelectListInfoService;
		bankList = getSelectListInfoService.getBankOrgCodeList();// 出款银行
		busiFlagList = getSelectListInfoService.getBusiFlag();// 对账状态
		reviseStatusList = getSelectListInfoService.getReviseStatus();// 调账状态
		withdrawBusiTypeList = getSelectListInfoService.getWithdrawBusiType();// 出款业务
	}

	protected void loadInfo(Map<String, Object> model) {
		model.put("busiFlagList", busiFlagList);
		model.put("reviseStatusList", reviseStatusList);
		model.put("withdrawBusiTypeList", withdrawBusiTypeList);
		model.put("bankList", bankList);
	}

}
