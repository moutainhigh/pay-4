package com.pay.poss.merchantmanager.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.merchantmanager.dto.MerchantDto;
import com.pay.poss.merchantmanager.dto.MerchantSearchDto;
import com.pay.poss.merchantmanager.dto.MerchantSearchListDto;
import com.pay.poss.merchantmanager.model.Account;
import com.pay.poss.merchantmanager.model.AccountAttribute;
import com.pay.poss.merchantmanager.model.AccountAttributeTemplate;
import com.pay.poss.merchantmanager.model.BaseData;
import com.pay.poss.merchantmanager.model.CheckCode;
import com.pay.poss.merchantmanager.model.EnterpriseBase;
import com.pay.poss.merchantmanager.model.EnterpriseContact;
import com.pay.poss.merchantmanager.model.EnterpriseContract;
import com.pay.poss.merchantmanager.model.FlowLog;
import com.pay.poss.merchantmanager.model.LiquidateInfo;
import com.pay.poss.merchantmanager.model.Member;
import com.pay.poss.merchantmanager.model.MemberProduct;
import com.pay.poss.merchantmanager.model.Operator;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file IMerchantDao.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-9-19 gungun_zhang Create
 */

public interface IMemberDao extends BaseDAO<Member> {

	public Long insertMember(Member mmber);

	public void insertEnterpriseBase(EnterpriseBase enterpriseBase);

	public void insertEnterpriseContact(EnterpriseContact enterpriseContact);

	public void insertEnterpriseContract(EnterpriseContract enterpriseContract);

	public void insertLiquidateInfo(LiquidateInfo liquidateInfo);

	public void insertOperator(Operator operator);

	public void insertFlowLog(FlowLog flowLog);

	public void insertMemberProduct(MemberProduct memberProduct);

	public void insertAccount(Account account);

	public void insertAccountAttribute(AccountAttribute accountAttribute);

	public void insertCheckCode(CheckCode checkCode);

	/**
	 * 更新用户member信息
	 * 
	 * @param member
	 * @return 更新个数
	 */
	public int updateMember(Member member);

	/**
	 * 更新Base信息
	 * 
	 * @param enterpriseBase
	 * @return
	 */
	public int updateEnterpriseBase(EnterpriseBase enterpriseBase);

	/**
	 * 修改联系信息
	 * 
	 * @param enterpriseContact
	 */
	public int updateEnterpriseContact(EnterpriseContact enterpriseContact);

	/**
	 * 修改合同信息
	 * 
	 * @param enterpriseContract
	 */

	public int updateEnterpriseContract(EnterpriseContract enterpriseContract);

	/**
	 * 修改银行卡信息（有多张）
	 * 
	 * @param liquidateInfo
	 * @return
	 */
	public int updateLiquidateInfo(LiquidateInfo liquidateInfo);

	public List<AccountAttributeTemplate> queryAccAttriTempByAccType(
			Map<String, Object> paraMap);

	public List<EnterpriseBase> getCurrMaxMerchantCode(String merchantTemp);

	public List<MerchantSearchListDto> queryMerchant(
			MerchantSearchDto merchantSearchDto);

	public List<MerchantSearchListDto> queryMerchantOfUnActive(
			MerchantSearchDto merchantSearchDto);

	public List<MemberProduct> queryProductAcctTempByAccTempId(String accTempId);

	public List<BaseData> queryAllAccountType();

	public List<AccountAttributeTemplate> queryAllAcctTempOfBusiness();

	public Integer queryMerchantCount(MerchantSearchDto merchantSearchDto);

	public Integer queryMerchantOfUnActiveCount(
			MerchantSearchDto merchantSearchDto);

	public List<MerchantDto> getMerchantListByMemberCode(Long memberCode);

	public Boolean updateMerchantCheckStatus(EnterpriseBase enterpriseBase);

	public Boolean isBizLicenceCodeExist(String bizLicenceCode);

	public Boolean isGovCodeExist(String govCode);

	public Boolean isTaxCodeExist(String taxCode);

	public Boolean isEmailExist(String emailCode);

	public String getMarketEmail(String signDept);

	/**
	 * 更新checkCode的状态
	 * 
	 * @param memberCode
	 * @param status
	 * @return 更新的条数
	 * @author 戴德荣
	 */
	public int updateCheckCodeStatus(Long memberCode, Integer status);

	/**
	 * 
	 * @param acctMode
	 * @param memberCode
	 */
	public int updateAccountMode(Integer acctMode, Long memberCode);

}