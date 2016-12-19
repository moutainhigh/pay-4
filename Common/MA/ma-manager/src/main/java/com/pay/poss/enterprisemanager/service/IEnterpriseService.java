package com.pay.poss.enterprisemanager.service;

import java.util.List;
import java.util.Map;

import com.pay.poss.base.exception.PossException;
import com.pay.poss.enterprisemanager.dto.AccountTempDto;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchDto;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchListDto;
import com.pay.poss.enterprisemanager.dto.MerchantDto;
import com.pay.poss.enterprisemanager.dto.ProductSearchDto;
import com.pay.poss.enterprisemanager.model.AccountAttribute;
import com.pay.poss.enterprisemanager.model.BaseData;
import com.pay.poss.enterprisemanager.model.Relation;
import com.pay.poss.featuremenu.model.Menu;
import com.pay.poss.membermanager.model.Product;
import com.pay.poss.systemmanager.formbean.UserRelation;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		IMerchantService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-20		gungun_zhang			Create
 */

/**
 * @Description
 * @project ma-manager
 * @file IEnterpriseService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 woyo Corporation. All rights reserved. Date
 *          Author Changes 2012-6-19 gungun_zhang Create
 */
public interface IEnterpriseService {

	public List<MerchantDto> getMerchantListByMemberCode(String memberCode);

	public List<EnterpriseSearchListDto> queryEnterprise(
			EnterpriseSearchDto enterpriseSearchDto);

	public List<EnterpriseSearchListDto> queryAccountOfEnterprise(
			EnterpriseSearchDto enterpriseSearchDto);

	public List<EnterpriseSearchListDto> queryDetailOfAccount(
			EnterpriseSearchDto enterpriseSearchDto);

	public Integer queryEnterpriseCount(EnterpriseSearchDto enterpriseSearchDto);

	public Integer queryAccountOfEnterpriseCount(
			EnterpriseSearchDto enterpriseSearchDto);

	public Integer queryDetailOfAccountCount(
			EnterpriseSearchDto enterpriseSearchDto);

	public List<BaseData> queryAllAccountType();

	public EnterpriseSearchListDto queryEnterpriseByMemberCode(String memberCode);

	public List<BaseData> queryAccountByMemberCode(String memberCode);

	public Boolean accountOfEnterpriseEditTrans(String memberCode,
			String accountOfEnterprise[]) throws PossException;

	public List<BaseData> queryAcctTempByMemberCode(String memberCode);

	public List<Relation> queryProductOfAcctTemp(Integer acctTempId);

	public List<BaseData> queryProductByMemberCode(String memberCode);

	public String queryAcctTypeNameByCode(String code);

	public AccountAttribute queryAttributeByAccCode(String accountCode);

	public Boolean attributeOfAccountEdit(AccountAttribute accountAttribute);

	public void updateStatusOfEnterprise(String memberCode, String memberStatus);

	public void updateMemberStatusTrans(Map<String, Object> paramMap)
			throws PossException;

	public Boolean updateAcctAttributeTrans(Map<String, Object> paramMap)
			throws PossException;

	public String queryBalance(Map<String, String> paraMap);

	public String isAllowCreateOrUpdateMerchant(Map<String, String> paraMap);

	public String isMccExist(String mcc);

	/**
	 * 
	 * @param memberDto
	 * @return
	 * @throws PossException
	 */
	public Boolean updateMerchantTrans(MerchantDto memberDto)
			throws PossException;

	/**
	 * 
	 * @param memberDto
	 * @return
	 * @throws PossException
	 */
	public Boolean updateMerchant(MerchantDto memberDto);

	public Boolean updateLoginNameOfEnterpriseTrans(Map<String, Object> paramMap)
			throws PossException;

	public Product queryProductById(String productId);

	public List<ProductSearchDto> queryProduct(ProductSearchDto productSearchDto);

	public Integer queryProductCount(ProductSearchDto productSearchDto);

	public List<Menu> getMenuOfProduct(String productId);

	public List<Menu> getAllMenu(String allowObject);

	public void productJoinMenuEditTrans(String[] productMenu, String productId)
			throws PossException;

	public void productDeleteTrans(String productId) throws PossException;

	/**
	 * 修改或更新产品
	 * 
	 * @param product
	 * @return
	 */
	public String saveOrUpdateProduct(Product product);

	public List<AccountTempDto> getAccountTempList(AccountTempDto accountTempDto);

	public Integer getAccountTempListCount(AccountTempDto accountTempDto);

	public void productOfAccountTempEditTrans(String[] productOfAccountTemp,
			String accountTempId) throws PossException;

	public AccountTempDto getAccountTempById(String accountTempId);

	public List<BaseData> getProductOfAccountTemp(String accountTempId);

	public List<BaseData> getAllProduct();

	/**
	 * 根据产品id删除产品
	 * 
	 * @param productId
	 * @return
	 * @throws PossException
	 */
	public boolean productDeleteFinal(String productId) throws PossException;
	/***
	 * 通过登入名称查询BD
	 * @param loginId
	 * @return
	 */
	public UserRelation queryBD(String loginId);

	public List<EnterpriseSearchListDto> queryDetailOfAccountAll(
			EnterpriseSearchDto enterpriseSearchDto);
		/**
		 * 查询全部账 户 信 息 不分页
		 * @param enterpriseSearchDto
		 * @return
		 */
	public List<EnterpriseSearchListDto> queryAccountOfEnterpriseAll(
			EnterpriseSearchDto enterpriseSearchDto);
	
	/**根据会员号查询其下所有的账户信息集合
	 * @param memberCode 会员哈
	 * @return 账户信息集合
	 */
	public List<AccountAttribute> queryAttributeByMemberCode(String memberCode);
}
