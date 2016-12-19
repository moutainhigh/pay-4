package com.pay.poss.membermanager.service;

import java.util.List;

import com.pay.poss.featuremenu.model.Menu;
import com.pay.poss.membermanager.model.Product;

/**
 * 产品配置相关
 * @Description 
 * @project 	poss-membermanager
 * @file 		ProductConfigService.java 
 * @note		<br>
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2012-5-29		DDR				Create
 */
 
public interface ProductConfigService {
	/**
	 * 取得基础服务
	 * 1.个人 2企业
	 * @param type
	 * @return
	 */
	List<List<Menu>>  getBaseProductTrees(int type);
	
	/**
	 * 1.个人 2企业
	 * @param type
	 * @return
	 */
	List<List<Menu>> getExtraProductTrees(int type);
	
	/**
	 * 取得默认产品 
	 * 1.个人 2企业
	 * @param type
	 * @return
	 */
	List<Product>	getDefaultProducts(int type);
	/**
	 * 取得增值产品 
	 * 1.个人 2企业
	 * @param type
	 * @return
	 */
	List<Product>	getExtraProducts(int type);
	
	/**
	 * 查询用户当前所有有效的产品 
	 * @param memberCode
	 * @return List<Product>
	 */
	List<Product> getMemberProducts(long memberCode);
	
	/**
	 * 插入用户产品信息
	 * @param memberCode
	 * @param productIds
	 * @return
	 */
	boolean createMemberProductRdTx(long memberCode, String [] productIds);
	
	/**
	 * 通过产品id取得产品
	 * @param id
	 * @return
	 */
	Product getByProductId(Long id);
	
	
	
	
}	
