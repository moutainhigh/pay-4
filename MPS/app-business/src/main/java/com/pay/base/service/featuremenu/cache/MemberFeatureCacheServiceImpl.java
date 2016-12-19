/**
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.base.service.featuremenu.cache;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.dto.MenuDto;
import com.pay.base.model.PowersModel;
import com.pay.base.service.featuremenu.FeatureMenuService;
import com.pay.base.service.featuremenu.FeatureService;
import com.pay.base.service.featuremenu.MemberProductService;
import com.pay.util.StringUtil;

/**
 * @author fjl
 * @date 2011-7-14
 */
public class MemberFeatureCacheServiceImpl implements MemberFeatureCacheService {

	private static final Log log = LogFactory.getLog(MemberFeatureCacheServiceImpl.class);

	//角色对应的菜单key 前缀
	private final static String CACHE_PRE = "menu";
	
	//角色对应的菜单key集合
	private final static String CACHE_KEY = "menu_key";
	
	//所有website 菜单key
	private final static String ALL_MENU = "all_website_menu";
	
	

	private FeatureService featureService;

	private FeatureMenuService featureMenuService;

	private MemberProductService memberProductService;
	

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuDto> queryMenuByFeature(int securLevel, int appScale) {

		String key = CACHE_PRE + "_" + securLevel + "_" + appScale;
		List<MenuDto> menuList = null;//(List<MenuDto>) CacheUtils.getMACache().get(key);
		if (menuList == null || menuList.isEmpty()) {
			// 查询数据库
			Long featureId = featureService.getFeatureIdByLvl(securLevel,appScale);
			if (featureId != null) {
				menuList = featureMenuService.queryMenuByFeature(featureId);
				if(menuList == null || menuList.isEmpty()){
					menuList = new ArrayList<MenuDto>();
				}
				//CacheUtils.getMACache().put(key,menuList);
				
				putKeyToCache(key);
			}
		}
		return menuList;
	}
	
	@SuppressWarnings("unchecked")
	private void putKeyToCache(String key){
//		List<String> keyList = (List<String>)CacheUtils.getMACache().get(CACHE_KEY);
//		if(keyList == null){
//			keyList = new ArrayList<String>();
//		}
//		boolean isexists = false;
//		for(String k : keyList){
//			if(key == k || key.equals(k)){
//				isexists = true;
//			}
//			break;
//		}
//		if(! isexists ){
//			keyList.add(key);
//			CacheUtils.getMACache().put(CACHE_KEY,keyList);
//		}
	}
	
	/*
	 * 得到某个会员拥有的菜单（菜单之间无上下级关系）
	 */
	private List<PowersModel> getMemberMenuNoRelation(int securLevel, int appScale,
			Long memberCode){
		List<PowersModel> pmAllList = null;
		
		try {
			List<MenuDto> listp = queryMenuByFeature(securLevel, appScale);
			if (listp != null) {
				pmAllList = this.getPowersList(listp);
			}
			// 查询会员产品类菜单
			List<MenuDto> proMenuList = memberProductService.findProductMenuByMemCode(memberCode);
			if (proMenuList != null) {
				List<PowersModel> proPowersList = this.getPowersList(proMenuList);
				if (pmAllList != null) {
					List<PowersModel> powersList = new ArrayList<PowersModel>();
					for (PowersModel powersModel : proPowersList) {
						// 过滤相同的菜单
						if (!pmAllList.contains(powersModel) && powersModel.getStatus() == 1) {
							powersList.add(powersModel);
						}
					}
					pmAllList.addAll(powersList);
				} else {
					pmAllList = proPowersList;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error("MemberFeatureCacheServiceImpl.getMemberMenuNoRelation throws error", e);
		}
		return pmAllList;
	}

	@Override
	public List<PowersModel> getMemberMenu(int securLevel, int appScale,
			Long memberCode) {
		List<PowersModel> pmSelfAllList = null;
		List<PowersModel> pmAllList = getMemberMenuNoRelation(securLevel, appScale, memberCode);
		
		if (pmAllList != null && pmAllList.size() > 0){
			pmSelfAllList = this.getSelfPmList(pmAllList);
		}
		return pmSelfAllList;
	}

	private List<PowersModel> getPowersList(List<MenuDto> menuList) {
		List<PowersModel> pmList = new ArrayList<PowersModel>();
		PowersModel pm = null;
		if (menuList != null && !menuList.isEmpty()) {
			for (MenuDto m : menuList) {
				pm = new PowersModel();
				pm.setId(m.getMenuId().toString());
				pm.setMenuName(m.getName());
				pm.setMenuUrl(m.getUrl());
				pm.setParentId(m.getParentId().toString());
				pm.setStatus(m.getStatus());
				pm.setMenuCode(m.getMenuCode());
				pm.setSecurityLvl(m.getSecurityLvl());
				pm.setDisplayFlag(m.getDisplayFlag());
				pm.setMenuType(m.getType());
				pmList.add(pm);
			}
		}

		return pmList;
	}

	private List<PowersModel> getSelfPmList(List<PowersModel> pmAllList) {
		List<PowersModel> selfList = new ArrayList<PowersModel>();
		List<PowersModel> selfRootList = this.getChildResouces(pmAllList, "0");
		List<PowersModel> selfChildList = null;
		for (PowersModel pm : selfRootList) {
			selfChildList = this.getChildResouces(pmAllList, pm.getId());
			if (selfChildList != null && selfChildList.size() > 0) {
				List<PowersModel> selfChildList1 = null;
				for (PowersModel pm1 : selfChildList) {
					selfChildList1 = this.getChildResouces(pmAllList,
							pm1.getId());
					if (selfChildList1 != null && selfChildList1.size() > 0) {
						pm1.setChildlist(selfChildList1);
					}
				}
				pm.setChildlist(selfChildList);
			}
			selfList.add(pm);
		}
		return selfList;
	}

	private List<PowersModel> getChildResouces(List<PowersModel> pmAllList,
			String parentResourceId) {
		List<PowersModel> childPmList = null;
		if (pmAllList != null && pmAllList.size() > 0) {
			childPmList = new ArrayList<PowersModel>();
			for (PowersModel pm : pmAllList) {
				if (pm.getParentId().equals(parentResourceId)) { // 找到子节点
					childPmList.add(pm);
				}
			}
		}
		return childPmList;
	}
	

	@Override
	public boolean isHaveMenu(int securLevel, int appScale, Long memberCode,
			String url) {
		if(StringUtil.isEmpty(url)){
			//?
			return false;
		}
		List<PowersModel> pmAllList = getMemberMenuNoRelation(securLevel, appScale, memberCode);
		if(pmAllList == null || pmAllList.isEmpty()){
			return false;
		}
		for(PowersModel item : pmAllList){
			if((url.equals(item.getMenuUrl()) || item.getMenuUrl().contains(url)) && StringUtils.isNotBlank(item.getMenuUrl())){
				return true;
			}
		}
		return false;
	}
	
	@Override
	public PowersModel isHaveCertMenu(int securLevel, int appScale, Long memberCode,
			String url) {
		if(StringUtil.isEmpty(url)){
			return null;
		}
		List<PowersModel> pmAllList = getMemberMenuNoRelation(securLevel, appScale, memberCode);
		if(pmAllList == null || pmAllList.isEmpty()){
			return null;
		}
		for(PowersModel item : pmAllList){
			if((url.equals(item.getMenuUrl()) || item.getMenuUrl().contains(url)) && StringUtils.isNotBlank(item.getMenuUrl())
					){
				return item;
			}
		}
		return null;
	}
	
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean isSysMenu(String url) {
		if(StringUtil.isEmpty(url)){
			return false;
		}
		
//		List<MenuDto> menuList = (List<MenuDto>) CacheUtils.getMACache().get( ALL_MENU );
//		if (menuList == null || menuList.isEmpty()) {
//			menuList = featureMenuService.getAllMenu();
//			if(menuList == null || menuList.isEmpty()){
//				menuList = new ArrayList<MenuDto>();
//			}
//			CacheUtils.getMACache().put(ALL_MENU ,menuList);
//		}
//		if(menuList != null && !menuList.isEmpty()){
//			for(MenuDto menu: menuList){
//				if(url.equals(menu.getUrl())){
//					return true;
//				}
//			}
//		}
		
		return false;
	}

	/**
	 * @param featureService
	 *            the featureService to set
	 */
	public void setFeatureService(FeatureService featureService) {
		this.featureService = featureService;
	}

	/**
	 * @param featureMenuService
	 *            the featureMenuService to set
	 */
	public void setFeatureMenuService(FeatureMenuService featureMenuService) {
		this.featureMenuService = featureMenuService;
	}

	public void setMemberProductService(MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}

}
