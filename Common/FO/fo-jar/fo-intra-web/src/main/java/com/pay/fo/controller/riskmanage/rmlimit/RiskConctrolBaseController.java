package com.pay.fo.controller.riskmanage.rmlimit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.rm.service.dto.rmlimit.business.RcBusinessDTO;
import com.pay.rm.service.dto.rmlimit.mcccategory.RcMccCategoryDTO;
import com.pay.rm.service.model.rmlimit.risklevel.RcRiskLevel;
import com.pay.rm.service.model.rmlimit.userlevel.RcUserLevel;
import com.pay.rm.service.service.rmlimit.business.RmBusinessService;
import com.pay.rm.service.service.rmlimit.mcccategory.RmMccCategoryService;
import com.pay.rm.service.service.rmlimit.risklevel.RmRiskLevelService;
import com.pay.rm.service.service.rmlimit.userlevel.RmUserLevelService;

/**
 * 风控 Controller基类
 * 
 * @Description
 * @project rm-web
 * @file RiskConctrolBaseController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved. Date
 *          Author Changes 2010-10-21 Volcano.Wu Create
 */
public abstract class RiskConctrolBaseController extends AbstractBaseController {

	private RmUserLevelService rmUserLevelService; // 用户等级
	private RmRiskLevelService rmRiskLevelService; // 风险等级
	private RmBusinessService rmBusinessService;// 业务类型
	private RmMccCategoryService rmMccCategoryService; //行业类别

	public void setRmUserLevelService(RmUserLevelService rmUserLevelService) {
		this.rmUserLevelService = rmUserLevelService;
	}

	public void setRmRiskLevelService(RmRiskLevelService rmRiskLevelService) {
		this.rmRiskLevelService = rmRiskLevelService;
	}

	public void setRmBusinessService(RmBusinessService rmBusinessService) {
		this.rmBusinessService = rmBusinessService;
	}
	
	public void setRmMccCategoryService(RmMccCategoryService rmMccCategoryService) {
		this.rmMccCategoryService = rmMccCategoryService;
	}

	protected Map<String, Object> loadDropDownList(String liststr) {

		Map<String, Object> model = new HashMap<String, Object>();
		//用户等级
		if (liststr.toUpperCase().indexOf("U") > -1) {
			List<RcUserLevel> userLevelList = rmUserLevelService.getUserLevelList();
			model.put("userlevellist", userLevelList);
		}
		//用户等级code2name
		if (liststr.toUpperCase().indexOf("A") > -1) {
			List<Map<String,String>> useLevelMapList = new ArrayList<Map<String,String>>();
			List<RcUserLevel> userLevelList = rmUserLevelService.getUserLevelList();
			Map<String,String> map;
			if(userLevelList != null){
				for (RcUserLevel useLevel : userLevelList) {
					 map = new HashMap<String,String>();
					 String userLevel = String.valueOf(useLevel.getUserLevel());
					 String levelName = useLevel.getLevelName();
					 map.put("value", userLevel);
					 map.put("text", levelName);
					 useLevelMapList.add(map);
				}
			}
			model.put("useLevelMapList", useLevelMapList);
		}
		//风险等级
		if (liststr.toUpperCase().indexOf("R") > -1) {
			List<RcRiskLevel> riskLevelList = rmRiskLevelService.getRiskLevelList();
			model.put("risklevellist", riskLevelList);
		}
		//风险等级code2name
		if (liststr.toUpperCase().indexOf("L") > -1) {
			List<Map<String,String>> riskLevelMapList = new ArrayList<Map<String,String>>();
			List<RcRiskLevel> riskLevelList = rmRiskLevelService.getRiskLevelList();
			Map<String,String> map;
			if(riskLevelList != null){
				for (RcRiskLevel rcRiskLevel : riskLevelList) {
					 map = new HashMap<String,String>();
					 String riskLevel = String.valueOf(rcRiskLevel.getRiskLevel());
					 String levelName = rcRiskLevel.getLevelName();
					 map.put("value", riskLevel);
					 map.put("text", levelName);
					 riskLevelMapList.add(map);
				}
			}
			model.put("riskLevelMapList", riskLevelMapList);
		}
		//业务类型
		if (liststr.indexOf("B") > -1) {
			List<RcBusinessDTO> businessList = rmBusinessService.getBusinessList();
			model.put("businesslist", businessList);
		}
		//业务类型code2name
		if (liststr.indexOf("C") > -1) {
			List<Map<String, String>> rcBusiness = rmBusinessService.queryRcBusiness();
			model.put("rcBusiness", rcBusiness);
		}
		//行业类别
		if (liststr.indexOf("M") > -1) {
			List<RcMccCategoryDTO>  rcMccCategoryList = rmMccCategoryService.queryAllRcMccCategory();
			model.put("rcMccCategoryList", rcMccCategoryList);
		}
		//状态
		if (liststr.indexOf("S") > -1) {
			List<Map<String, String>> statusList = new ArrayList<Map<String, String>>();
			Map<String, String> statusMap = new HashMap<String, String>();
			statusMap.put("value", "0");
			statusMap.put("text", "无效");
			statusList.add(statusMap);
			statusMap = new HashMap<String, String>();
			statusMap.put("value", "1");
			statusMap.put("text", "有效");
			statusList.add(statusMap);

			model.put("statusList", statusList);
		}

		return model;
	}

}
