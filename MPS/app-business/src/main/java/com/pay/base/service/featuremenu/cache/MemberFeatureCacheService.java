/**
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 版权所有
 */
package com.pay.base.service.featuremenu.cache;

import java.util.List;

import com.pay.base.dto.MenuDto;
import com.pay.base.model.PowersModel;

/**
 * 
 * @author fjl
 * @date 2011-7-14
 */
public interface MemberFeatureCacheService {

	/**
	 * 得到“某种会员类别“对应的菜单集合
	 * 
	 * @param securLevel
	 *            登录级别
	 * @param appScale
	 *            会员类别 2-企业3-个人
	 * @return
	 */
	List<MenuDto> queryMenuByFeature(int securLevel, int appScale);

	/**
	 * 得到“某个会员“对应的菜单集合
	 * @param securLevel
	 *            登录级别
	 * @param appScale
	 *            会员类别 2-企业3-个人
	 * @param memberCode
	 * @return
	 */
	List<PowersModel> getMemberMenu(int securLevel, int appScale, Long memberCode);
	
	/**
	 * 判断会员是否拥有某个菜单
	 * @param securLevel
	 * @param appScale
	 * @param memberCode
	 * @param url
	 * @return
	 */
	boolean isHaveMenu(int securLevel, int appScale, Long memberCode,String url);
	
	/**
	 * 判断url 是否为website 系统中的菜单
	 * @param url
	 * @return
	 */
	boolean isSysMenu(String url);
	
	public PowersModel isHaveCertMenu(int securLevel, int appScale, Long memberCode,
			String url);

}
