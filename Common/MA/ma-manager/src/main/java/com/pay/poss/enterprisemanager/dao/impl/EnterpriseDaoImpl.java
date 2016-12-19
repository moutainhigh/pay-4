package com.pay.poss.enterprisemanager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.enterprisemanager.dao.IEnterpriseDao;
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
 * @file MemberDaoImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-10-20 gungun_zhang Create
 */

public class EnterpriseDaoImpl extends BaseDAOImpl<Member> implements
		IEnterpriseDao {

	@Override
	public void accountTempJoinProductAdd(
			final ProductAcctTemplate productAcctTemplate) {
		this.getSqlMapClientTemplate().insert(
				"enterprise.accountTempJoinProductAdd", productAcctTemplate);

	}

	@Override
	public void accountTempJoinProductDelete(final String accountTempId) {
		this.getSqlMapClientTemplate().delete(
				"enterprise.accountTempJoinProductDelete", accountTempId);

	}

	@Override
	public AccountTempDto getAccountTempById(final String accountTempId) {

		return (AccountTempDto) this.getSqlMapClientTemplate().queryForObject(
				"enterprise.getAccountTempById", accountTempId);
	}

	@Override
	public List<BaseData> getAllProduct() {

		return this.getSqlMapClientTemplate().queryForList(
				"enterprise.getAllProduct");
	}

	@Override
	public List<BaseData> getProductOfAccountTemp(String accountTempId) {
		return this.getSqlMapClientTemplate().queryForList(
				"enterprise.getProductOfAccountTemp", accountTempId);
	}

	@Override
	public List<AccountTempDto> getAccountTempList(final AccountTempDto accountTempDto) {

		return this.getSqlMapClientTemplate().queryForList(
				"enterprise.getAccountTempList", accountTempDto);
	}

	@Override
	public Integer getAccountTempListCount(final AccountTempDto accountTempDto) {

		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"enterprise.getAccountTempListCount", accountTempDto);
	}

	@Override
	public void updateMemberProductStatus(final String productId) {
		this.getSqlMapClientTemplate().update(
				"enterprise.updateMemberProductStatus", productId);

	}

	@Override
	public Product queryProductById(final String productId) {
		return (Product) this.getSqlMapClientTemplate().queryForObject(
				"productconfig.getByProductId", new Long(productId));
	}

	@Override
	public Long insertProduct(final Product product) {
		Long id = (Long) getSqlMapClientTemplate().queryForObject(
				"productconfig.selectProductId");
		product.setId(id);
		this.getSqlMapClientTemplate().insert("productconfig.insertProduct",
				product);
		return id;

	}

	@Override
	public void updateProductStatus(final String productId) {
		this.getSqlMapClientTemplate().update("enterprise.updateProductStatus",
				productId);

	}

	@Override
	public List<EnterpriseSearchListDto> queryEnterprise(
			final EnterpriseSearchDto enterpriseSearchDto) {
		List<EnterpriseSearchListDto> enterpriseList = this
				.getSqlMapClientTemplate().queryForList(
						"enterprise.queryEnterprise", enterpriseSearchDto);
		return enterpriseList;
	}

	@Override
	public Integer queryEnterpriseCount(final EnterpriseSearchDto enterpriseSearchDto) {
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("enterprise.queryEnterpriseCount",
						enterpriseSearchDto);
		return count;
	}

	@Override
	public List<EnterpriseSearchListDto> queryAccountOfEnterprise(
			final EnterpriseSearchDto enterpriseSearchDto) {
		List<EnterpriseSearchListDto> enterpriseList = this
				.getSqlMapClientTemplate().queryForList(
						"enterprise.queryAccountOfEnterprise",
						enterpriseSearchDto);
		return enterpriseList;
	}

	@Override
	public Integer queryAccountOfEnterpriseCount(
			final EnterpriseSearchDto enterpriseSearchDto) {
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("enterprise.queryAccountOfEnterpriseCount",
						enterpriseSearchDto);
		return count;
	}

	@Override
	public List<MerchantDto> getMerchantByMemberCode(final Long memberCode) {

		return this.getSqlMapClientTemplate().queryForList(
				"enterprise.getMerchantByMemberCode", memberCode);
	}

	@Override
	public List<EnterpriseSearchListDto> queryDetailOfAccount(
			final EnterpriseSearchDto enterpriseSearchDto) {
		List<EnterpriseSearchListDto> enterpriseList = this
				.getSqlMapClientTemplate().queryForList(
						"enterprise.queryDetailOfAccount", enterpriseSearchDto);
		return enterpriseList;
	}

	@Override
	public Integer queryDetailOfAccountCount(
			final EnterpriseSearchDto enterpriseSearchDto) {
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("enterprise.queryDetailOfAccountCount",
						enterpriseSearchDto);
		return count;
	}

	@Override
	public List<BaseData> queryAllAccountType() {
		List<BaseData> baseDataList = this.getSqlMapClientTemplate()
				.queryForList("enterprise.queryAllAccountType");
		return baseDataList;
	}

	@Override
	public List<BaseData> queryAccountByMemberCode(final Long memberCode) {
		List<BaseData> baseDataList = this
				.getSqlMapClientTemplate()
				.queryForList("enterprise.queryAccountByMemberCode", memberCode);
		return baseDataList;
	}

	@Override
	public AccountAttribute queryAttributeByAccCode(final String accountCode) {
		AccountAttribute accountAttribute = (AccountAttribute) this
				.getSqlMapClientTemplate().queryForObject(
						"enterprise.queryAttributeByAccCode", accountCode);
		return accountAttribute;
	}

	@Override
	public List<AccountAttribute> queryAttributeByMemberCode(final String memberCode) {
		List<AccountAttribute> accountAttributes = this
				.getSqlMapClientTemplate().queryForList(
						"enterprise.queryAttributeByMemberCode", memberCode);
		return accountAttributes;
	}

	@Override
	public EnterpriseSearchListDto queryEnterpriseByMemberCode(final Long memberCode) {
		EnterpriseSearchListDto enterprise = (EnterpriseSearchListDto) this
				.getSqlMapClientTemplate().queryForObject(
						"enterprise.queryEnterpriseByMemberCode", memberCode);
		return enterprise;
	}

	@Override
	public void deleteAccountAttribuleByMemberCode(final Long memberCode) {
		this.getSqlMapClientTemplate().delete(
				"enterprise.deleteAcctAttriByMemberCode", memberCode);

	}

	@Override
	public void updateStatusOfEnterprise(final Map<String, String> paraMap) {
		this.getSqlMapClientTemplate().update(
				"enterprise.updateStatusOfEnterprise", paraMap);

	}

	@Override
	public void deleteAccountByMemberCode(final Long memberCode) {
		this.getSqlMapClientTemplate().delete(
				"enterprise.deleteAccountByMemberCode", memberCode);
	}

	@Override
	public void deleteProductByMemberCode(final Long memberCode) {
		this.getSqlMapClientTemplate().delete(
				"enterprise.deleteProductByMemberCode", memberCode);

	}

	@Override
	public void insertAccountOfEnterprise(final Account account) {
		this.getSqlMapClientTemplate().insert(
				"enterprise.insertAccountOfEnterprise", account);

	}

	@Override
	public void insertProductOfEnterprise(final MemberProduct product) {
		this.getSqlMapClientTemplate().insert(
				"enterprise.insertProductOfEnterprise", product);

	}

	@Override
	public void insertAccountAttribute(final AccountAttribute accountAttribute) {
		this.getSqlMapClientTemplate().insert(
				"enterprise.insertAccountAttribute", accountAttribute);

	}

	@Override
	public AccountAttributeTemplate queryAccAttriTemp(
			final Map<String, Object> paraMap) {
		AccountAttributeTemplate accountAttributeTemplate = (AccountAttributeTemplate) this
				.getSqlMapClientTemplate().queryForObject(
						"enterprise.queryAccAttriTemp", paraMap);
		return accountAttributeTemplate;
	}

	@Override
	public List<BaseData> queryAcctTempByMemberCode(final Long memberCode) {
		List<BaseData> baseDataList = this.getSqlMapClientTemplate()
				.queryForList("enterprise.queryAcctTempByMemberCode",
						memberCode);
		return baseDataList;
	}

	@Override
	public List<Relation> queryProductOfAcctTemp(final Integer acctTempId) {
		List<Relation> relationList = this.getSqlMapClientTemplate()
				.queryForList("enterprise.queryProductOfAcctTemp", acctTempId);
		return relationList;
	}

	@Override
	public List<BaseData> queryProductByMemberCode(final Long memberCode) {
		List<BaseData> baseDataList = this
				.getSqlMapClientTemplate()
				.queryForList("enterprise.queryProductByMemberCode", memberCode);
		return baseDataList;
	}

	@Override
	public String queryAcctTypeNameByCode(final Integer code) {
		String name = (String) this.getSqlMapClientTemplate().queryForObject(
				"enterprise.queryAcctTypeNameByCode", code);
		return name;
	}

	@Override
	public Boolean attributeOfAccountEdit(final AccountAttribute accountAttribute) {
		int updateRow = this.getSqlMapClientTemplate().update(
				"enterprise.attributeOfAccountEdit", accountAttribute);
		if (updateRow == 1) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public String queryBalance(final Map<String, String> paraMap) {
		String balance = (String) this.getSqlMapClientTemplate()
				.queryForObject("enterprise.queryBalance", paraMap);
		return balance;
	}

	@Override
	public void insertPossOperater(final PossOperate possOperate) {
		this.getSqlMapClientTemplate()
				.insert("possoperate.create", possOperate);
	}

	@Override
	public void updatePossOperater(final Map<String, Object> paramMap) {
		this.getSqlMapClientTemplate().update(
				"possoperate.updatePossOperateByCondition", paramMap);
	}

	@Override
	public void updateMemberOperateByCondition(final Map<String, Object> paramMap) {
		this.getSqlMapClientTemplate().update(
				"possoperate.updateMemberOperateByCondition", paramMap);
	}

	@Override
	public Account findMemberCodeByAcctCode(final String acctCode) {
		Account account = (Account) this
				.getSqlMapClientTemplate()
				.queryForObject("enterprise.findMemberCodeByAcctCode", acctCode);
		return account;
	}

	@Override
	public void updateLoginNameOfEnterpriseTrans(final Map<String, Object> paramMap)
			throws PossException {
		this.getSqlMapClientTemplate().update("enterprise.updateLoginOfMember",
				paramMap);
		this.getSqlMapClientTemplate().update(
				"enterprise.updateLoginOfEnterpriseContact", paramMap);
	}

	@Override
	public List<ProductSearchDto> queryProduct(final ProductSearchDto productSearchDto) {
		return this.getSqlMapClientTemplate().queryForList(
				"enterprise.queryProduct", productSearchDto);
	}

	@Override
	public Integer queryProductCount(final ProductSearchDto productSearchDto) {
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"enterprise.queryProductCount", productSearchDto);
	}

	@Override
	public List<Menu> getAllMenu(final String allowObject) {
		return this.getSqlMapClientTemplate().queryForList(
				"enterprise.getAllMenu", allowObject);
	}

	@Override
	public List<Menu> getMenuOfProduct(final String productId) {
		return this.getSqlMapClientTemplate().queryForList(
				"enterprise.getMenuOfProduct", productId);
	}

	@Override
	public void productJoinMenuAdd(final ProductMenu productMenu) {
		this.getSqlMapClientTemplate().insert("enterprise.productJoinMenuAdd",
				productMenu);

	}

	@Override
	public void productJoinMenuDelete(final String productId) {
		this.getSqlMapClientTemplate().delete(
				"enterprise.productJoinMenuDelete", productId);

	}

	@Override
	public int updateMemberRelationName(final Long memberCode, final String zhName,
			final String enName) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberCode", memberCode);
		map.put("zhName", zhName);
		map.put("enName", enName);
		return this.getSqlMapClientTemplate().update(
				"enterprise.updateMemberRelationName", map);
	}

	@Override
	public EnterpriseBase findByMemberCode(final Long memberCode) {
		return (EnterpriseBase) this.getSqlMapClientTemplate().queryForObject(
				"enterprise.findByMemberCode", memberCode);
	}

	@Override
	public boolean deleteProductFinal(final String productId) {
		return this.getSqlMapClientTemplate().delete(
				"enterprise.productDelete", productId) == 1;
	}

	@Override
	public void deleteMerberProductFinal(final String productId) {
		this.getSqlMapClientTemplate().delete("enterprise.MemberProductDelete",
				productId);
	}

	@Override
	public boolean existsProductCodeFilterId(final String productCode, final Long id) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("productCode", productCode);
		map.put("id", id);
		Object o = getSqlMapClientTemplate().queryForObject(
				"productconfig.existsProductCodeFilterId", map);
		return o != null;
	}

	@Override
	public Boolean updateProduct(final Product product) {
		return getSqlMapClientTemplate().update("productconfig.updateProduct",
				product) == 1;
	}

	@Override
	public UserRelation queryBD(final String loginId) {
		List<UserRelation> queryForList = getSqlMapClientTemplate()
				.queryForList("enterprise.queryBD", loginId);
		return queryForList.get(0);
	}

	@Override
	public List<EnterpriseSearchListDto> queryDetailOfAccountAll(
			EnterpriseSearchDto enterpriseSearchDto) {
		List<EnterpriseSearchListDto> enterpriseList = this
				.getSqlMapClientTemplate().queryForList(
						"enterprise.queryDetailOfAccountAll", enterpriseSearchDto);
		return enterpriseList;
	}

	@Override
	public List<EnterpriseSearchListDto> queryAccountOfEnterpriseAll(
			EnterpriseSearchDto enterpriseSearchDto) {
		List<EnterpriseSearchListDto> enterpriseList = this
				.getSqlMapClientTemplate().queryForList(
						"enterprise.queryAccountOfEnterpriseAll",
						enterpriseSearchDto);
		return enterpriseList;
	}




	@Override
	public AccountAttribute getAccountAttribute(Map<String, Object> params) {
		
		return (AccountAttribute) getSqlMapClientTemplate().queryForObject(
				"enterprise.getAccountAttribute",params);
	}

}
