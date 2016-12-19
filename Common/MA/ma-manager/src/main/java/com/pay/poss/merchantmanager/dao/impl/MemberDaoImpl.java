package com.pay.poss.merchantmanager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.merchantmanager.dao.IMemberDao;
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

public class MemberDaoImpl extends BaseDAOImpl<Member> implements IMemberDao {

	@Override
	public Long insertMember(Member member) {
		Long memberCode = (Long) this.getSqlMapClientTemplate().insert(
				"merchant.insertMember", member);
		return memberCode;
	}

	@Override
	public void insertEnterpriseBase(EnterpriseBase enterpriseBase) {
		this.getSqlMapClientTemplate().insert(
				this.namespace.concat("insertEnterpriseBase"), enterpriseBase);
	}

	@Override
	public void insertEnterpriseContact(EnterpriseContact enterpriseContact) {
		this.getSqlMapClientTemplate().insert("merchant.insertContact",
				enterpriseContact);
	}

	@Override
	public void insertEnterpriseContract(EnterpriseContract enterpriseContract) {
		this.getSqlMapClientTemplate().insert("merchant.insertContract",
				enterpriseContract);
	}

	@Override
	public void insertFlowLog(FlowLog flowLog) {
		this.getSqlMapClientTemplate()
				.insert("merchant.insertFlowLog", flowLog);

	}

	@Override
	public void insertLiquidateInfo(LiquidateInfo liquidateInfo) {
		this.getSqlMapClientTemplate().insert("merchant.insertLiquidateInfo",
				liquidateInfo);
	}

	@Override
	public void insertOperator(Operator operator) {
		this.getSqlMapClientTemplate().insert("merchant.insertOperator",
				operator);
	}

	@Override
	public void insertAccount(Account account) {
		this.getSqlMapClientTemplate()
				.insert("merchant.insertAccount", account);
	}

	@Override
	public void insertMemberProduct(MemberProduct memberProduct) {
		this.getSqlMapClientTemplate().insert("merchant.insertMemberProduct",
				memberProduct);

	}

	@Override
	public void insertAccountAttribute(AccountAttribute accountAttribute) {
		this.getSqlMapClientTemplate().insert(
				"merchant.insertAccountAttribute", accountAttribute);
	}

	@Override
	public void insertCheckCode(CheckCode checkCode) {
		this.getSqlMapClientTemplate().insert("merchant.insertCheckCode",
				checkCode);

	}

	@Override
	public int updateMember(Member member) {
		return this.getSqlMapClientTemplate().update("merchant.updateMember",
				member);

	}

	@Override
	public Boolean updateMerchantCheckStatus(EnterpriseBase enterpriseBase) {

		this.getSqlMapClientTemplate().update("merchant.updateEnterpriseBase",
				enterpriseBase);

		return true;
	}

	@Override
	public int updateEnterpriseBase(EnterpriseBase enterpriseBase) {
		try {
			return this.getSqlMapClientTemplate().update(
					"merchant.updateEnterpriseBase", enterpriseBase);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			logger.error(enterpriseBase.getZhName() + ": --修改基本信息出错：" + e);
		}
		return 0;

	}

	@Override
	public int updateEnterpriseContact(EnterpriseContact enterpriseContact) {
		return this.getSqlMapClientTemplate().update("merchant.updateContact",
				enterpriseContact);
	}
	

	@Override
	public int updateEnterpriseContract(EnterpriseContract enterpriseContract) {
		return this.getSqlMapClientTemplate().update("merchant.updateContract",
				enterpriseContract);

	}

	@Override
	public int updateLiquidateInfo(LiquidateInfo liquidateInfo) {
		return this.getSqlMapClientTemplate().update(
				"merchant.updateLiquidateInfo", liquidateInfo);
	}

	@Override
	public List<AccountAttributeTemplate> queryAccAttriTempByAccType(
			Map<String, Object> paraMap) {
		List<AccountAttributeTemplate> accountAttributeTemplateList = this
				.getSqlMapClientTemplate().queryForList(
						"merchant.queryAccAttriTempByAccType", paraMap);
		return accountAttributeTemplateList;
	}

	@Override
	public List<EnterpriseBase> getCurrMaxMerchantCode(String merchantCodeTemp) {
		List<EnterpriseBase> enterpriseBaseList = this
				.getSqlMapClientTemplate().queryForList(
						"merchant.queryCurrMaxMerchantCode", merchantCodeTemp);
		return enterpriseBaseList;
	}

	@Override
	public List<MerchantSearchListDto> queryMerchant(
			MerchantSearchDto merchantSearchDto) {
		List<MerchantSearchListDto> merchantList = this
				.getSqlMapClientTemplate().queryForList(
						"merchant.queryMerchant", merchantSearchDto);
		return merchantList;
	}

	@Override
	public List<MerchantSearchListDto> queryMerchantOfUnActive(
			MerchantSearchDto merchantSearchDto) {
		List<MerchantSearchListDto> merchantList = this
				.getSqlMapClientTemplate().queryForList(
						"merchant.queryMerchantOfUnActive", merchantSearchDto);
		return merchantList;
	}

	@Override
	public Integer queryMerchantCount(MerchantSearchDto merchantSearchDto) {
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("merchant.queryMerchantCount",
						merchantSearchDto);
		return count;
	}

	@Override
	public Integer queryMerchantOfUnActiveCount(
			MerchantSearchDto merchantSearchDto) {
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("merchant.queryMerchantOfUnActiveCount",
						merchantSearchDto);
		return count;
	}

	@Override
	public List<MemberProduct> queryProductAcctTempByAccTempId(
			String acctTemplateId) {
		List<MemberProduct> productList = this.getSqlMapClientTemplate()
				.queryForList("merchant.queryProductAcctTempByAccTempId",
						acctTemplateId);
		return productList;
	}

	@Override
	public List<AccountAttributeTemplate> queryAllAcctTempOfBusiness() {
		List<AccountAttributeTemplate> accountAttributeTemplateList = this
				.getSqlMapClientTemplate().queryForList(
						"merchant.queryAllAcctTempOfBusiness");
		return accountAttributeTemplateList;
	}

	@Override
	public List<MerchantDto> getMerchantListByMemberCode(Long memberCode) {
		return this.getSqlMapClientTemplate().queryForList(
				"merchant.getMerchantByMemberCode", Long.valueOf(memberCode));
	}

	@Override
	public Boolean isBizLicenceCodeExist(String bizLicenceCode) {
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("merchant.isBizLicenceCodeExist",
						bizLicenceCode);
		if (count > 0) {
			return true;
		} else {
			return false;
		}

	}

	@Override
	public Boolean isGovCodeExist(String govCode) {
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("merchant.isGovCodeExist", govCode);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean isTaxCodeExist(String taxCode) {
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("merchant.isTaxCodeExist", taxCode);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Boolean isEmailExist(String email) {
		Integer count = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("merchant.isEmailExist", email);
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<BaseData> queryAllAccountType() {

		return this.getSqlMapClientTemplate().queryForList(
				"merchant.queryAllAccountType");
	}

	@Override
	public String getMarketEmail(String signDept) {
		String email = (String) this.getSqlMapClientTemplate().queryForObject(
				"merchant.getMarketEmail", signDept);
		return email;
	}

	@Override
	public int updateCheckCodeStatus(Long memberCode, Integer status) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("status", status);
		map.put("memberCode", memberCode);
		return getSqlMapClientTemplate().update(
				"merchant.updateCheckCodeStatus", map);
	}

	@Override
	public int updateAccountMode(Integer acctMode, Long memberCode) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("settlementCycle", acctMode);
		map.put("memberCode", memberCode);
		return getSqlMapClientTemplate().update("merchant.updateAcctMode",
				map);
	}

}
