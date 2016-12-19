package com.pay.poss.enterprisemanager.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.enterprisemanager.dto.AccountTempDto;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchDto;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchListDto;
import com.pay.poss.enterprisemanager.dto.MerchantDto;
import com.pay.poss.enterprisemanager.dto.ProductSearchDto;
import com.pay.poss.enterprisemanager.model.Account;
import com.pay.poss.enterprisemanager.model.AccountAttribute;
import com.pay.poss.enterprisemanager.model.AccountAttributeTemplate;
import com.pay.poss.enterprisemanager.model.BaseData;
import com.pay.poss.enterprisemanager.model.ProductAcctTemplate;
import com.pay.poss.enterprisemanager.model.ProductMenu;
import com.pay.poss.enterprisemanager.model.Relation;
import com.pay.poss.featuremenu.model.Menu;
import com.pay.poss.membermanager.model.Member;
import com.pay.poss.membermanager.model.MemberProduct;
import com.pay.poss.membermanager.model.Product;
import com.pay.poss.merchantmanager.model.EnterpriseBase;
import com.pay.poss.operatelogmanager.model.PossOperate;
import com.pay.poss.systemmanager.formbean.UserRelation;


/**
 * 
 * @Description
 * @project poss-membermanager
 * @file IMemberDao.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-10-20 gungun_zhang Create
 */

public interface IEnterpriseDao extends BaseDAO<Member> {

	public List<MerchantDto> getMerchantByMemberCode(Long memberCode);

	public List<EnterpriseSearchListDto> queryEnterprise(
			EnterpriseSearchDto enterpriseSearchDto);

	public Integer queryEnterpriseCount(EnterpriseSearchDto enterpriseSearchDto);

	public List<EnterpriseSearchListDto> queryAccountOfEnterprise(
			EnterpriseSearchDto enterpriseSearchDto);

	public Integer queryAccountOfEnterpriseCount(
			EnterpriseSearchDto enterpriseSearchDto);

	public List<EnterpriseSearchListDto> queryDetailOfAccount(
			EnterpriseSearchDto enterpriseSearchDto);

	public Integer queryDetailOfAccountCount(
			EnterpriseSearchDto enterpriseSearchDto);

	public List<BaseData> queryAllAccountType();

	public List<BaseData> queryAccountByMemberCode(Long memberCode);

	public List<BaseData> queryAcctTempByMemberCode(Long memberCode);

	public List<Relation> queryProductOfAcctTemp(Integer acctTempId);

	public AccountAttribute queryAttributeByAccCode(String accountCode);

	public EnterpriseSearchListDto queryEnterpriseByMemberCode(Long memberCode);

	public void deleteAccountByMemberCode(Long memberCode);

	public void deleteProductByMemberCode(Long memberCode);

	public void deleteAccountAttribuleByMemberCode(Long memberCode);

	public void insertAccountOfEnterprise(Account account);

	public void insertAccountAttribute(AccountAttribute accountAttribute);

	public void insertProductOfEnterprise(MemberProduct product);

	public void updateStatusOfEnterprise(Map<String, String> paraMap);

	public AccountAttributeTemplate queryAccAttriTemp(
			Map<String, Object> paraMap);

	public List<BaseData> queryProductByMemberCode(Long memberCode);

	public String queryAcctTypeNameByCode(Integer code);

	public Boolean attributeOfAccountEdit(AccountAttribute accountAttribute);

	public void insertPossOperater(PossOperate possOperate);

	public void updatePossOperater(Map<String, Object> paraMap);

	public Account findMemberCodeByAcctCode(String acctCode);

	public String queryBalance(Map<String, String> paraMap);

	public void updateLoginNameOfEnterpriseTrans(Map<String, Object> paramMap)
			throws PossException;

	public void updateProductStatus(String productId);

	public List<ProductSearchDto> queryProduct(ProductSearchDto productSearchDto);

	public Integer queryProductCount(ProductSearchDto productSearchDto);

	public List<Menu> getMenuOfProduct(String productId);

	public List<Menu> getAllMenu(String allowObject);

	public void productJoinMenuDelete(String productId);

	public void updateMemberProductStatus(String productId);

	public void productJoinMenuAdd(ProductMenu productMenu);

	public Long insertProduct(Product product);

	public void updateMemberOperateByCondition(Map<String, Object> paramMap);

	public Product queryProductById(String productId);

	public List<AccountTempDto> getAccountTempList(AccountTempDto accountTempDto);

	public Integer getAccountTempListCount(AccountTempDto accountTempDto);

	public AccountTempDto getAccountTempById(String accountTempId);

	public List<BaseData> getProductOfAccountTemp(String accountTempId);

	public List<BaseData> getAllProduct();

	public void accountTempJoinProductDelete(String accountTempId);

	public void accountTempJoinProductAdd(
			ProductAcctTemplate productAcctTemplate);

	/**
	 * 根据子的membeCode修改简称和名称
	 * 
	 * @param memberCode
	 * @param zhName
	 * @param enName
	 * @return 更新的个数
	 */
	public int updateMemberRelationName(Long memberCode, String zhName,
			String enName);

	/**
	 * 通过用户号来查找企业的基本信息
	 * 
	 * @param memberCode
	 * @return EnterpriseBase
	 */
	public EnterpriseBase findByMemberCode(Long memberCode);

	/**
	 * 根据会员号查询账户属性信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public List<AccountAttribute> queryAttributeByMemberCode(String memberCode);

	/**
	 * 根据产品id删除对应产品表信息
	 * 
	 * @param productId
	 * @return
	 */
	public boolean deleteProductFinal(String productId);

	/**
	 * 根据产品号id，删除会员开通的该产品信息
	 * 
	 * @param productId
	 * @return
	 */
	public void deleteMerberProductFinal(String productId);

	/**
	 * 查询是否有重复的productCode存在
	 * 
	 * @param productCode
	 * @param id
	 * @return
	 */
	public boolean existsProductCodeFilterId(String productCode, Long id);

	/**
	 * 更新
	 * 
	 * @param product
	 * @return
	 */
	public Boolean updateProduct(Product product);
	/**
	 * 查询BD
	 * @param loginId
	 * @return
	 */
	public UserRelation queryBD(String loginId);

	public List<EnterpriseSearchListDto> queryDetailOfAccountAll(
			EnterpriseSearchDto enterpriseSearchDto);
	/***
	 * 查询全部的账户信息
	 * @param enterpriseSearchDto
	 * @return
	 */
	public List<EnterpriseSearchListDto> queryAccountOfEnterpriseAll(
			EnterpriseSearchDto enterpriseSearchDto);
	
	
	public AccountAttribute getAccountAttribute(Map<String,Object> params);


}