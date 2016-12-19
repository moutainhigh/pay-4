/**
 *  File: GetSelectListInfoServiceMock.java
 *  Description:
 *  Copyright 2010-2010 pay Corporation. All rights reserved.
 *  Date        Author      Changes
 *  2010-10-2   Sandy_Yang  create
 *  
 *
 */
package com.pay.fundout.reconcile.service.common.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fundout.reconcile.service.common.GetSelectListInfoService;

/**
 * 
 * @author Sandy_Yang
 */
public class GetSelectListInfoServiceMock  implements GetSelectListInfoService{
	@Override
	public List<Map<String, String>> getBankOrgCodeList() {
		List<Map<String,String>> wdBankCodeList = new ArrayList<Map<String,String>>();
		Map<String,String> bankCodeMap = new HashMap<String, String>();
		bankCodeMap.put("text", "所有");
		bankCodeMap.put("value", "340");
		wdBankCodeList.add(bankCodeMap);
		bankCodeMap = new HashMap<String, String>();
		bankCodeMap.put("text", "招商银行");
		bankCodeMap.put("value", "001");
		wdBankCodeList.add(bankCodeMap);
		bankCodeMap = new HashMap<String, String>();
		bankCodeMap.put("text", "建设银行");
		bankCodeMap.put("value", "002");
		wdBankCodeList.add(bankCodeMap);
		bankCodeMap = new HashMap<String, String>();
		bankCodeMap.put("text", "工商银行");
		bankCodeMap.put("value", "003");
		wdBankCodeList.add(bankCodeMap);
		return wdBankCodeList;
	}
	/**
	 * 对账状态 业务标识 0 默认表示 100 平账 200银行多 210银行少 220错账
	 */
	@Override
	public List<Map<String, String>> getBusiFlag() {
		List<Map<String,String>> busiFlagList = new ArrayList<Map<String,String>>();
		Map<String,String> busiFlagMap = new HashMap<String, String>();
		busiFlagMap.put("text", "所有");
		busiFlagMap.put("value", "");
		busiFlagList.add(busiFlagMap);
//		busiFlagMap = new HashMap<String, String>();
//		busiFlagMap.put("text", "对账成功");
//		busiFlagMap.put("value", "100");
//		busiFlagList.add(busiFlagMap);
		busiFlagMap = new HashMap<String, String>();
		busiFlagMap.put("text", "银行多账");
		busiFlagMap.put("value", "200");
		busiFlagList.add(busiFlagMap);
		busiFlagMap = new HashMap<String, String>();
		busiFlagMap.put("text", "银行少账");
		busiFlagMap.put("value", "210");
		busiFlagList.add(busiFlagMap);
		busiFlagMap = new HashMap<String, String>();
		busiFlagMap.put("text", "差异");
		busiFlagMap.put("value", "220");
		busiFlagList.add(busiFlagMap);
		return busiFlagList;
	}
	/**
	 * 调账状态
	 */
	@Override
	public List<Map<String, String>> getReviseStatus() {
		List<Map<String,String>> reviseStatusList = new ArrayList<Map<String,String>>();
		Map<String,String> reviseStatusMap = new HashMap<String, String>();
		reviseStatusMap.put("text", "所有");
		reviseStatusMap.put("value", "");
		//调账状态 0：初始1：审核中2：调账审核通过3：调账审核驳回4调账中5调账成功 6调账失败
		reviseStatusList.add(reviseStatusMap);
		reviseStatusMap = new HashMap<String, String>();
		reviseStatusMap.put("text", "初始");
		reviseStatusMap.put("value", "0");
		reviseStatusList.add(reviseStatusMap);
		reviseStatusMap = new HashMap<String, String>();
		reviseStatusMap.put("text", "审核中");
		reviseStatusMap.put("value", "1");
		reviseStatusList.add(reviseStatusMap);
		reviseStatusMap = new HashMap<String, String>();
		reviseStatusMap.put("text", "调账审核通过");
		reviseStatusMap.put("value", "2");
		reviseStatusList.add(reviseStatusMap);
		reviseStatusMap = new HashMap<String, String>();
		reviseStatusMap.put("text", "调账审核驳回");
		reviseStatusMap.put("value", "3");
		reviseStatusList.add(reviseStatusMap);
		reviseStatusMap = new HashMap<String, String>();
		reviseStatusMap.put("text", "调账中");
		reviseStatusMap.put("value", "4");
		reviseStatusList.add(reviseStatusMap);
		reviseStatusMap = new HashMap<String, String>();
		reviseStatusMap.put("text", "调账成功");
		reviseStatusMap.put("value", "5");
		reviseStatusList.add(reviseStatusMap);
		reviseStatusMap = new HashMap<String, String>();
		reviseStatusMap.put("text", "调账失败");
		reviseStatusMap.put("value", "6");
		reviseStatusList.add(reviseStatusMap);
		return reviseStatusList;
	}
	
	/**
	 * 出款业务
	 */
	@Override
	public List<Map<String, String>> getWithdrawBusiType() {
		List<Map<String,String>> withdrawBusiTypeList = new ArrayList<Map<String,String>>();
		Map<String,String> withdrawBusiTypeMap = new HashMap<String, String>();
		withdrawBusiTypeMap.put("text", "普通提现");
		withdrawBusiTypeMap.put("value", "0");
		withdrawBusiTypeList.add(withdrawBusiTypeMap);
		withdrawBusiTypeMap = new HashMap<String, String>();
		withdrawBusiTypeMap.put("text", "信用卡还款");
		withdrawBusiTypeMap.put("value", "1");
		withdrawBusiTypeList.add(withdrawBusiTypeMap);
		withdrawBusiTypeMap = new HashMap<String, String>();
		withdrawBusiTypeMap.put("text", "公共事业缴费");
		withdrawBusiTypeMap.put("value", "2");
		withdrawBusiTypeList.add(withdrawBusiTypeMap);
		return withdrawBusiTypeList;
	}
	
	@Override
	public List<Map<String, String>> getUploadStatus() {
	    List<Map<String,String>> uploadStatusList = new ArrayList<Map<String,String>>();
		Map<String,String> uploadStatusMap = new HashMap<String, String>();
		uploadStatusMap.put("text", "所有");
		uploadStatusMap.put("value", "");
		uploadStatusList.add(uploadStatusMap);
		uploadStatusMap = new HashMap<String, String>();
		uploadStatusMap.put("text", "上传成功");
		uploadStatusMap.put("value", "1");
		uploadStatusList.add(uploadStatusMap);
		uploadStatusMap = new HashMap<String, String>();
		uploadStatusMap.put("text", "上传失败");
		uploadStatusMap.put("value", "2");
		uploadStatusList.add(uploadStatusMap);
		uploadStatusMap = new HashMap<String, String>();
		uploadStatusMap.put("text", "解析成功");
		uploadStatusMap.put("value", "3");
		uploadStatusList.add(uploadStatusMap);
		uploadStatusMap = new HashMap<String, String>();
		uploadStatusMap.put("text", "解析失败");
		uploadStatusMap.put("value", "4");
		uploadStatusList.add(uploadStatusMap);
		uploadStatusMap = new HashMap<String, String>();
		uploadStatusMap.put("text", "数据导入成功");
		uploadStatusMap.put("value", "5");
		uploadStatusList.add(uploadStatusMap);
		uploadStatusMap = new HashMap<String, String>();
		uploadStatusMap.put("text", "数据导入失败");
		uploadStatusMap.put("value", "6");
		uploadStatusList.add(uploadStatusMap);
		uploadStatusMap = new HashMap<String, String>();
		uploadStatusMap.put("text", "对账成功");
		uploadStatusMap.put("value", "7");
		uploadStatusList.add(uploadStatusMap);
		uploadStatusMap = new HashMap<String, String>();
		uploadStatusMap.put("text", "对账失败");
		uploadStatusMap.put("value", "8");
		uploadStatusList.add(uploadStatusMap);
		uploadStatusMap = new HashMap<String, String>();
		uploadStatusMap.put("text", "已废除");
		uploadStatusMap.put("value", "9");
		uploadStatusList.add(uploadStatusMap);		
		return uploadStatusList;
	}

}
