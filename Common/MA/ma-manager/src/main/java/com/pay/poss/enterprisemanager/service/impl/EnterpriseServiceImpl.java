package com.pay.poss.enterprisemanager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.comm.SearchKeyUtils;
import com.pay.acc.memberaccttemplate.dto.MemberAcctTemplateDto;
import com.pay.acc.memberaccttemplate.service.MemberAcctTemplateService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.pe.helper.AcctType;
import com.pay.poss.base.common.constants.OperateStatusConstants;
import com.pay.poss.base.enums.CommonEnum;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.enterprisemanager.common.Constants;
import com.pay.poss.enterprisemanager.dao.IEnterpriseDao;
import com.pay.poss.enterprisemanager.dto.AccountTempDto;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchDto;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchListDto;
import com.pay.poss.enterprisemanager.dto.MerchantDto;
import com.pay.poss.enterprisemanager.dto.ProductSearchDto;
import com.pay.poss.enterprisemanager.model.Account;
import com.pay.poss.enterprisemanager.model.AccountAttribute;
import com.pay.poss.enterprisemanager.model.BaseData;
import com.pay.poss.enterprisemanager.model.ProductAcctTemplate;
import com.pay.poss.enterprisemanager.model.ProductMenu;
import com.pay.poss.enterprisemanager.model.Relation;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.poss.featuremenu.model.Menu;
import com.pay.poss.membermanager.model.Product;
import com.pay.poss.merchantmanager.dao.IMemberDao;
import com.pay.poss.merchantmanager.model.EnterpriseBase;
import com.pay.poss.merchantmanager.model.EnterpriseContact;
import com.pay.poss.merchantmanager.model.EnterpriseContract;
import com.pay.poss.merchantmanager.model.LiquidateInfo;
import com.pay.poss.merchantmanager.model.Member;
import com.pay.poss.operatelogmanager.model.PossOperate;
import com.pay.poss.systemmanager.formbean.UserRelation;
import com.pay.rm.base.exception.RMFacadeException;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;
import com.pay.rm.facade.RcLimitRuleFacade;
import com.pay.util.DESUtil;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

/**
 * 
 * @Description
 * @project ma-manager
 * @file EnterpriseServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2011-1-20 gungun_zhang Create
 */

public class EnterpriseServiceImpl implements IEnterpriseService {

	private final Log log = LogFactory.getLog(getClass());
	private IEnterpriseDao enterpriseDao;
	private IMemberDao memberDao;
	private RcLimitRuleFacade rcLimitRuleFacade;
	public static final String memberFrozen = "2"; // 会员表中的冻结状态
	public static final String memberUfrozen = "1"; // 会员表中的解冻状态状态(即为正常状态)

	public static final String operateFrozen = "1"; // t_poss_operate中的冻结状态
	public static final String operateUfrozen = "2"; // t_poss_operate中的解冻状态状态
	public static final String actionUrlFrozen = "会员冻结"; //

	private MemberAcctTemplateService memberAcctTemplateService;
	private AcctAttribService acctAttribService;

	@Override
	public AccountTempDto getAccountTempById(String accountTempId) {

		return this.enterpriseDao.getAccountTempById(accountTempId);
	}

	@Override
	public List<BaseData> getAllProduct() {

		return this.enterpriseDao.getAllProduct();
	}

	@Override
	public List<BaseData> getProductOfAccountTemp(String accountTempId) {

		return this.enterpriseDao.getProductOfAccountTemp(accountTempId);
	}

	@Override
	public List<AccountTempDto> getAccountTempList(AccountTempDto accountTempDto) {
		return this.enterpriseDao.getAccountTempList(accountTempDto);
	}

	@Override
	public Integer getAccountTempListCount(AccountTempDto accountTempDto) {
		return this.enterpriseDao.getAccountTempListCount(accountTempDto);
	}

	@Override
	public Product queryProductById(String productId) {

		return this.enterpriseDao.queryProductById(productId);
	}

	@Override
	public void productDeleteTrans(String productId) throws PossException {
		try {
			this.enterpriseDao.updateProductStatus(productId);
			this.enterpriseDao.updateMemberProductStatus(productId);
		} catch (Exception e) {
			log.error("EnterpriseServiceImpl.productDeleteTrans is error... ");
			e.printStackTrace();
			throw new PossException("下线产品失败!",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
	}

	@Override
	public String saveOrUpdateProduct(Product product) {
		String sign = "产品新增成功!";
		boolean exists = enterpriseDao.existsProductCodeFilterId(
				product.getProductCode(), product.getId());
		if (exists) {
			sign = "产品编号已被占用，请换一个！";
			return sign;
		}
		// 新增加
		try {
			Integer type = product.getType();
			if (product.getId() == null) {
				Long id = this.enterpriseDao.insertProduct(product);
				sign = "产品新增成功!";
			} else {// 修改
				boolean updateStatus = this.enterpriseDao
						.updateProduct(product);
				if (updateStatus) {
					sign = "更新信息产品成功";
				} else {
					sign = "更新信息产品失败";
				}
			}

		} catch (Exception e) {
			log.error("EnterpriseServiceImpl.insertProduct is error... ");
			e.printStackTrace();
			sign = "产品新增失败,数据库操作异常";
		}

		return sign;
	}

	@Override
	public List<EnterpriseSearchListDto> queryEnterprise(
			EnterpriseSearchDto enterpriseSearchDto) {
		log.debug("EnterpriseServiceImpl.queryEnterprise is running...");
		List<EnterpriseSearchListDto> enterpriseSearchListDto = enterpriseDao
				.queryEnterprise(enterpriseSearchDto);
		return enterpriseSearchListDto;
	}

	@Override
	public Integer queryEnterpriseCount(EnterpriseSearchDto enterpriseSearchDto) {
		log.debug("EnterpriseServiceImpl.queryEnterpriseCount is running...");
		Integer count = enterpriseDao.queryEnterpriseCount(enterpriseSearchDto);
		return count;
	}

	@Override
	public List<EnterpriseSearchListDto> queryAccountOfEnterprise(
			EnterpriseSearchDto enterpriseSearchDto) {
		log.debug("EnterpriseServiceImpl.queryAccountOfEnterprise is running...");
		List<EnterpriseSearchListDto> enterpriseSearchListDto = enterpriseDao
				.queryAccountOfEnterprise(enterpriseSearchDto);
		return enterpriseSearchListDto;
	}

	@Override
	public Integer queryAccountOfEnterpriseCount(
			EnterpriseSearchDto enterpriseSearchDto) {
		log.debug("EnterpriseServiceImpl.queryAccountOfEnterpriseCount is running...");
		Integer count = enterpriseDao
				.queryAccountOfEnterpriseCount(enterpriseSearchDto);
		return count;
	}

	@Override
	public List<EnterpriseSearchListDto> queryDetailOfAccount(
			EnterpriseSearchDto enterpriseSearchDto) {
		log.debug("EnterpriseServiceImpl.queryDetailOfAccount is running...");
		List<EnterpriseSearchListDto> enterpriseSearchListDto = enterpriseDao
				.queryDetailOfAccount(enterpriseSearchDto);
		return enterpriseSearchListDto;
	}

	@Override
	public Integer queryDetailOfAccountCount(
			EnterpriseSearchDto enterpriseSearchDto) {
		log.debug("EnterpriseServiceImpl.queryDetailOfAccountCount is running...");
		Integer count = enterpriseDao
				.queryDetailOfAccountCount(enterpriseSearchDto);
		return count;
	}

	@Override
	public AccountAttribute queryAttributeByAccCode(String accountCode) {
		log.debug("EnterpriseServiceImpl.queryAttributeByAccCode is running...");
		AccountAttribute AccountAttribute = enterpriseDao
				.queryAttributeByAccCode(accountCode);
		return AccountAttribute;
	}

	@Override
	public List<MerchantDto> getMerchantListByMemberCode(String memberCode) {
		log.debug("EnterpriseServiceImpl.getMerchantByMemberCode is running...");
		List<MerchantDto> list = enterpriseDao.getMerchantByMemberCode(Long
				.valueOf(memberCode));
		if (list.size() > 0) {
			for (MerchantDto merchantDto : list) {
				merchantDto.setBankAcct(DESUtil.decrypt(merchantDto
						.getBankAcct()));

			}

		}
		return list;
	}

	@Override
	public EnterpriseSearchListDto queryEnterpriseByMemberCode(String memberCode) {
		log.debug("EnterpriseServiceImpl.queryEnterpriseByMemberCode is running...");
		EnterpriseSearchListDto enterprise = enterpriseDao
				.queryEnterpriseByMemberCode(Long.valueOf(memberCode));
		return enterprise;

	}

	@Override
	public void updateStatusOfEnterprise(String memberCode, String memberStatus) {
		Map<String, String> dataMap = new Hashtable<String, String>();
		dataMap.put("memberCode", memberCode);
		dataMap.put("memberStatus", memberStatus);
		enterpriseDao.updateStatusOfEnterprise(dataMap);
	}

	public void updateMemberStatusTrans(Map<String, Object> paramMap)
			throws PossException {
		String memberCode = (String) paramMap.get("objectCode");
		String memberStatus = (String) paramMap.get("memberStatus");
		try {
			// 1:更新企业会员t_member表中的STATUS
			this.updateStatusOfEnterprise(memberCode, memberStatus);
			// 2:如果页面传值为冻结(2)
			if (CommonEnum.STATUS_TWO.getCode().equals(memberStatus)) {
				// 2-1在日志表中插入冻结状态的记录
				PossOperate operate = new PossOperate();
				operate.setObjectCode(memberCode);
				operate.setLoginName((String) paramMap.get("loginName"));
				operate.setLoginIp((String) paramMap.get("loginIp"));
				operate.setActionUrl(CommonEnum.MEEMBER_FROZEN.getDescription());
				operate.setUpdateOperator("loginName");
				operate.setType(1);
				operate.setStatus(1);
				enterpriseDao.insertPossOperater(operate);
			}
			// 3:如果页面传值为解冻(1)
			if (CommonEnum.STATUS_ONE.getCode().equals(memberStatus)) {
				// 3-1 在日志表中插入解冻的记录
				PossOperate operate = new PossOperate();
				operate.setObjectCode(memberCode);
				operate.setLoginName((String) paramMap.get("loginName"));
				operate.setLoginIp((String) paramMap.get("loginIp"));
				operate.setActionUrl(CommonEnum.MEEMBER_UNFROZEN
						.getDescription());
				operate.setType(2);
				operate.setStatus(2);
				enterpriseDao.insertPossOperater(operate);
				// 3-1更新该会员冻结状态记录为解冻
				paramMap.put("newStatus", 2);
				paramMap.put("oldStatus", 1);
				enterpriseDao.updatePossOperater(paramMap);
				// added by wangtq 2011-1-12 修改t_member_operate中的fail_time为0
				paramMap.put("memberCode", memberCode);
				paramMap.put("type", 0);
				enterpriseDao.updateMemberOperateByCondition(paramMap);
			}
		} catch (Exception e) {
			log.error("EnterpriseServiceImpl.updateMemberStatusTrans is error...");
			e.printStackTrace();
			throw new PossException("操作会员状态失败(冻结,解冻)!",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}

	}

	@Override
	public List<BaseData> queryAllAccountType() {
		List<BaseData> baseDataList = this.enterpriseDao.queryAllAccountType();
		return baseDataList;
	}

	@Override
	public List<BaseData> queryAccountByMemberCode(String memberCode) {
		List<BaseData> baseDataList = this.enterpriseDao
				.queryAccountByMemberCode(Long.valueOf(memberCode));
		return baseDataList;
	}

	// 暂时只有开通账户功能,没有关闭账户的功能
	public Boolean accountOfEnterpriseEditTrans(String memberCode,
			String accountOfEnterprise[]) throws PossException {
		Boolean isSuccess = true;
		try {
			// this.deleteAccountByMemberCode(memberCode);
			// this.deleteAccountAttribuleByMemberCode(memberCode);
			if (accountOfEnterprise != null && accountOfEnterprise.length > 0) {
				// 开通对应影子账户
				List<String> accountList = new ArrayList<String>();
				for (int i = 0; i < accountOfEnterprise.length; i++) {
					String accTypeId = accountOfEnterprise[i];
					accountList.add(accTypeId);
				}
				this.insertAccount(memberCode, accountList);
			}

		} catch (Exception e) {
			log.error(
					"EnterpriseServiceImpl.accountOfEnterpriseEditTrans is error...",
					e);
			isSuccess = false;
			throw new PossException("开通企业账户失败!",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return isSuccess;
	}

	private void insertAccount(String memberCode,
			List<String> accountOfEnterprise) {
		
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("memberCode",memberCode);
		params.put("acctType",AcctTypeEnum.BASIC_CNY.getCode());
		
		AccountAttribute attribute = enterpriseDao.getAccountAttribute(params);
		
		if (accountOfEnterprise != null && accountOfEnterprise.size() > 0) {
			for (int i = 0; i < accountOfEnterprise.size(); i++) {
				String accTypeId = accountOfEnterprise.get(i);

				AcctAttribDto acctAttribDto = acctAttribService
						.queryAcctAttribByMemberCodeAndAcctType(
								Long.valueOf(memberCode),
								Integer.valueOf(accTypeId));

				if (acctAttribDto != null) {
					log.error("member this acct is exists ,memberCode:"
							+ memberCode + ",acctType:" + accTypeId);
					continue;
				}

				Map<String, Object> paraMap = new Hashtable<String, Object>();
				paraMap.put("acctTypeId", accTypeId);// 账户类型
				paraMap.put("type", Constants.MEMBERTYPE);// 会员类型1.个人2.企业

				MemberAcctTemplateDto memberAcctTemplateDto = memberAcctTemplateService
						.queryByMemberTypeAndAcctType(
								MemberTypeEnum.MERCHANT.getCode(),
								Integer.valueOf(accTypeId));

				if (null != memberAcctTemplateDto) {

					String subjectCode = memberAcctTemplateDto.getSubjectCode();
					String accType = memberAcctTemplateDto.getAcctType()
							.toString();
					String accountCode = subjectCode + memberCode + accType;

					Account account = new Account();
					account.setMemberCode(Long.valueOf(memberCode));
					account.setAcctTypeId(Integer.valueOf(accTypeId));// 账户类型
					account.setBalance(Constants.BALANCE);
					account.setFrozenAmount(Constants.FROZEN);
					account.setStatus(Constants.ACCOUNTSTATUS);
					Date createDate = new Date();
					account.setCreateDate(createDate);
					account.setUpdateDate(createDate);
					account.setCreditBalance(Constants.CREDITBALANCE);// 借方发生额
					account.setDebitBalance(Constants.DEBITBALANCE);// 贷方发生额
					account.setAcctCode(accountCode);
					this.enterpriseDao.insertAccountOfEnterprise(account);

					AccountAttribute accountAttribute = new AccountAttribute();
					BeanUtils.copyProperties(memberAcctTemplateDto,
							accountAttribute);
					accountAttribute.setCreateDate(createDate);
					accountAttribute.setUpdateDate(createDate);
					accountAttribute.setMemberCode(Long.valueOf(memberCode));
					accountAttribute.setAcctCode(accountCode);
					accountAttribute.setCurCode(memberAcctTemplateDto
							.getCurCode());
					if(attribute!=null){
						accountAttribute.setPayPwd(attribute.getPayPwd());
					}
					accountAttribute.setMemberAcctCode(Long.valueOf(memberCode
							+ accType));
					this.enterpriseDao.insertAccountAttribute(accountAttribute);

				}

			}

		}
	}

	@Override
	public List<BaseData> queryAcctTempByMemberCode(String memberCode) {
		List<BaseData> baseDataList = this.enterpriseDao
				.queryAcctTempByMemberCode(Long.valueOf(memberCode));
		return baseDataList;
	}

	@Override
	public List<Relation> queryProductOfAcctTemp(Integer acctTempId) {
		List<Relation> relationList = this.enterpriseDao
				.queryProductOfAcctTemp(acctTempId);
		return relationList;
	}

	@Override
	public String queryAcctTypeNameByCode(String code) {
		String name = this.enterpriseDao.queryAcctTypeNameByCode(Integer
				.valueOf(code));
		return name;
	}

	@Override
	public List<BaseData> queryProductByMemberCode(String memberCode) {
		List<BaseData> baseDataList = this.enterpriseDao
				.queryProductByMemberCode(Long.valueOf(memberCode));
		return baseDataList;
	}

	@Override
	public Boolean attributeOfAccountEdit(AccountAttribute accountAttribute) {
		Boolean isSuess = this.enterpriseDao
				.attributeOfAccountEdit(accountAttribute);
		return isSuess;
	}

	@Override
	public Boolean updateAcctAttributeTrans(Map paramMap) throws PossException {
		Boolean isSuess = false;
		AccountAttribute accAttribute = (AccountAttribute) paramMap
				.get("accAttribute");
		try {
			// 1:更新该账户的状态(如果前端没做操作则传入值为nul不进行数据库操作)
			if (null != accAttribute.getFrozen()
					|| null != accAttribute.getAllowIn()
					|| null != accAttribute.getAllowOut()) {
				this.attributeOfAccountEdit(accAttribute);
			}
			// 2:如果controller传值为冻结(0)止入, 止出
			PossOperate operate = new PossOperate();
			operate.setObjectCode(accAttribute.getAcctCode());
			operate.setLoginName((String) paramMap.get("loginName"));
			operate.setLoginIp((String) paramMap.get("loginIp"));
			if (new Integer(0).equals(accAttribute.getFrozen())
					|| new Integer(0).equals(accAttribute.getAllowIn())
					|| new Integer(0).equals(accAttribute.getAllowOut())) {
				// 2-1 判断是冻结,止入,还是止出 、
				if (new Integer(0).equals(accAttribute.getFrozen())) {
					operate.setActionUrl(OperateStatusConstants.ACCT_FROZEN);
					operate.setType(1);
					operate.setStatus(1);
					// 2-2在日志表中插入状态改变的记录
					enterpriseDao.insertPossOperater(operate);
				}
				if (new Integer(0).equals(accAttribute.getAllowIn())) {
					operate.setActionUrl(OperateStatusConstants.ACCT_ALLOWIN);
					operate.setType(3);
					operate.setStatus(3);
					// 2-2在日志表中插入状态改变的记录
					enterpriseDao.insertPossOperater(operate);
				}
				if (new Integer(0).equals(accAttribute.getAllowOut())) {
					operate.setActionUrl(OperateStatusConstants.ACCT_ALLOWOUT);
					operate.setType(5);
					operate.setStatus(5);
					// 2-2在日志表中插入状态改变的记录
					enterpriseDao.insertPossOperater(operate);
				}

			}

			// 3:如果controller传值为解冻(1),解除止入, 解除止出
			if (new Integer(1).equals(accAttribute.getFrozen())
					|| new Integer(1).equals(accAttribute.getAllowIn())
					|| new Integer(1).equals(accAttribute.getAllowOut())) {
				Account account = enterpriseDao
						.findMemberCodeByAcctCode(accAttribute.getAcctCode());
				if (new Integer(1).equals(accAttribute.getFrozen())) {
					operate.setActionUrl(OperateStatusConstants.ACCT_UNFROZEN);
					operate.setType(2);
					operate.setStatus(2);
					paramMap.put("newStatus", 2);
					paramMap.put("oldStatus", 1);
					// 3-1 在日志表中插入状态改变的记录
					enterpriseDao.insertPossOperater(operate);
					// 3-2更新该会员账户状态记录为解冻,解除止入,或者解除止出
					paramMap.put("objectCode", accAttribute.getAcctCode());
					enterpriseDao.updatePossOperater(paramMap);

					// added by wangtq 2011-1-12 修改t_member_operate中的fail_time为0
					paramMap.put("memberCode", account.getMemberCode());
					paramMap.put("type", 1);
					paramMap.put("acctType", 10);
					enterpriseDao.updateMemberOperateByCondition(paramMap);
				}
				if (new Integer(1).equals(accAttribute.getAllowIn())) {
					operate.setActionUrl(OperateStatusConstants.ACCT_UNALLOWIN);
					operate.setType(4);
					operate.setStatus(4);
					paramMap.put("newStatus", 4);
					paramMap.put("oldStatus", 3);
					// 3-1 在日志表中插入状态改变的记录
					enterpriseDao.insertPossOperater(operate);
					// 3-2更新该会员账户状态记录为解冻,解除止入,或者解除止出
					paramMap.put("objectCode", accAttribute.getAcctCode());
					enterpriseDao.updatePossOperater(paramMap);
				}
				if (new Integer(1).equals(accAttribute.getAllowOut())) {
					operate.setActionUrl(OperateStatusConstants.ACCT_UNALLOWOUT);
					operate.setType(6);
					operate.setStatus(6);
					paramMap.put("newStatus", 6);
					paramMap.put("oldStatus", 5);
					// 3-1 在日志表中插入状态改变的记录
					enterpriseDao.insertPossOperater(operate);
					// 3-2更新该会员账户状态记录为解冻,解除止入,或者解除止出
					paramMap.put("objectCode", accAttribute.getAcctCode());
					enterpriseDao.updatePossOperater(paramMap);

					// added by wangtq 2011-1-12 修改t_member_operate中的fail_time为0
					paramMap.put("memberCode", account.getMemberCode());
					paramMap.put("type", 1);
					paramMap.put("acctType", 10);
					enterpriseDao.updateMemberOperateByCondition(paramMap);
				}

			}
			isSuess = true;
		} catch (Exception e) {
			log.error("EnterpriseServiceImpl.updateStatusOfEnterpriseTrans is error...");
			e.printStackTrace();
			throw new PossException("操作会员账户状态失败(冻结,解冻)!",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return isSuess;
	}

	@Override
	public String isMccExist(String mcc) {
		Boolean isExist = false;
		try {
			isExist = rcLimitRuleFacade.isExistMcc(Long.valueOf(mcc));
		} catch (Exception e) {
			log.error("MerchantServiceImp.isExistMcc is error...");
			e.printStackTrace();
		}
		if (isExist) {
			return "";
		} else {
			return "您输入的mcc代码不存在,请于管理员联系...";
		}

	}

	@Override
	public String isAllowCreateOrUpdateMerchant(Map<String, String> paraMap) {
		String result = "";
		result = this.isBizLicenceCodeExist(paraMap.get("bizLicenceCode"));
		result = result + this.isGovCodeExist(paraMap.get("govCode"));
		result = result + this.isTaxCodeExist(paraMap.get("taxCode"));
		return result;
	}

	@Override
	public Boolean updateMerchantTrans(MerchantDto memberDto)
			throws PossException {
		Boolean isSuccess = true;
		try {

			log.info("--更新会员号为： " + memberDto.getMemberCode() + "用户名为："
					+ memberDto.getZhName() + "信息开始.......");
			boolean updateMemberFlag = this.updateMember(memberDto);
			log.info("--更新会员基本信息----->" + (updateMemberFlag ? "成功" : "失败"));
			boolean updateBaseFlag = this.updateEnterpriseBase(memberDto);
			log.info("--更新企业基本信息----->" + (updateBaseFlag ? "成功" : "失败"));
			boolean updateContactFlag = this.updateEnterpriseContact(memberDto);
			log.info("--更新用户联系信息----->" + (updateContactFlag ? "成功" : "失败"));
			boolean updatePactFlag = this.updateEnterpriseContract(memberDto);
			log.info("--更新企业合同信息----->" + (updatePactFlag ? "成功" : "失败"));

			this.updateRisk(memberDto);

		} catch (Exception e) {
			log.error("MerchantServiceImpl.updateMerchantTrans is error...", e);
			isSuccess = false;
			throw new PossException("更新商户",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return isSuccess;
	}

	@Override
	public Boolean updateMerchant(MerchantDto memberDto) {
		Boolean isSuccess = true;
		try {

			boolean updateBaseFlag = this.updateEnterpriseBase(memberDto);
			log.info("--更新企业基本信息----->" + (updateBaseFlag ? "成功" : "失败"));
			
			if(null != memberDto.getLiquidates()){
				for (LiquidateInfo liquidateInfo : memberDto.getLiquidates()) {
					
					
					boolean updateLiqFlag = this.updateLiquidateInfo(liquidateInfo);
					log.info("--更新银行卡信息" + liquidateInfo.getBankAcct() + "----->"
							+ (updateLiqFlag ? "成功" : "失败"));
				}
			}

		} catch (Exception e) {
			log.error("MerchantServiceImpl.updateMerchantTrans is error...", e);
			isSuccess = false;
			throw new PossException("更新商户",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return isSuccess;
	}

	private String isBizLicenceCodeExist(String bizLicenceCode) {
		Boolean isExist = this.memberDao.isBizLicenceCodeExist(bizLicenceCode);
		if (isExist) {
			return "企业营业执照号码已被占用!";
		} else {
			return "";
		}

	}

	private String isGovCodeExist(String govCode) {
		Boolean isExist = this.memberDao.isGovCodeExist(govCode);
		if (isExist) {
			return "企业机构证件号码已被占用!";
		} else {
			return "";
		}

	}

	private String isTaxCodeExist(String taxCode) {
		Boolean isExist = this.memberDao.isTaxCodeExist(taxCode);
		if (isExist) {
			return "企业税务等级证件号码已被占用!";
		} else {
			return "";
		}
	}

	private boolean updateMember(MerchantDto merchantDto) {
		Member member = new Member();

		member.setMemberCode(Long.valueOf(merchantDto.getMemberCode()));
		member.setServiceLevelCode(Integer.valueOf(merchantDto
				.getServiceLevelCode()));
		member.setUpdateDate(new Date());

		return this.memberDao.updateMember(member) == 1;
	};

	private boolean updateEnterpriseBase(MerchantDto merchantDto) {

		EnterpriseBase enterpriseBase = new EnterpriseBase();
		BeanUtils.copyProperties(merchantDto, enterpriseBase,
				new String[] { "expire" });
		enterpriseBase.setSearchKey(this.getSearchKey(merchantDto.getZhName()));// 中文名拼音首字母（查询用）
		if (!StringUtil.isEmpty(merchantDto.getExpire())) {
			enterpriseBase.setExpire(DateUtil.parse("yyyy-MM-dd",
					merchantDto.getExpire()));
		}

		enterpriseBase.setUpdateDate(new Date());

		return this.memberDao.updateEnterpriseBase(enterpriseBase) == 1;

	};

	private boolean updateEnterpriseContact(MerchantDto merchantDto) {
		EnterpriseContact enterpriseContact = new EnterpriseContact();
		enterpriseContact.setMemberCode(Long.valueOf(merchantDto
				.getMemberCode()));
		enterpriseContact.setAddress(merchantDto.getAddress());
		enterpriseContact.setFax(merchantDto.getFax());
		enterpriseContact.setTel(merchantDto.getTel());
		enterpriseContact.setZip(merchantDto.getZip());
		enterpriseContact.setLegalName(merchantDto.getLegalName());
		enterpriseContact.setLegalLink(merchantDto.getLegalLink());
		enterpriseContact.setFinanceName(merchantDto.getFinanceName());
		enterpriseContact.setFinanceLink(merchantDto.getFinanceLink());
		enterpriseContact
				.setCompayLinkerName(merchantDto.getCompayLinkerName());
		enterpriseContact.setCompayLinkerTel(merchantDto.getCompayLinkerTel());
		enterpriseContact.setTechName(merchantDto.getTechName());
		enterpriseContact.setTechLink(merchantDto.getTechLink());
		enterpriseContact.setWebName1(merchantDto.getWebName1());
		enterpriseContact.setWebAddr1(merchantDto.getWebAddr1());
		enterpriseContact.setWebName2(merchantDto.getWebName2());
		enterpriseContact.setWebAddr2(merchantDto.getWebAddr2());
		enterpriseContact.setWebName3(merchantDto.getWebName3());
		enterpriseContact.setWebAddr3(merchantDto.getWebAddr3());
		enterpriseContact.setUpdateDate(new Date());
		return this.memberDao.updateEnterpriseContact(enterpriseContact) == 1;
	};

	private boolean updateEnterpriseContract(MerchantDto merchantDto) {
		EnterpriseContract enterpriseContract = new EnterpriseContract();
		enterpriseContract.setMemberCode(Long.valueOf(merchantDto
				.getMemberCode()));
		enterpriseContract.setSignName(merchantDto.getSignName());
		// 2015/5/12
		enterpriseContract.setSignLoginId(merchantDto.getSignLoginId());

		enterpriseContract.setSignDepart(merchantDto.getSignDepart());
	
		if (StringUtils.isNotEmpty(merchantDto.getStartDate())) {
			enterpriseContract.setStartDate(DateUtil.parse("yyyy-MM-dd",
					merchantDto.getStartDate()));
		}
		if (StringUtils.isNotEmpty(merchantDto.getEndDate())) {
			enterpriseContract.setEndDate(DateUtil.parse("yyyy-MM-dd",
					merchantDto.getEndDate()));
		}
		if (StringUtils.isNotEmpty(merchantDto.getOpenFee())) {
			enterpriseContract
					.setOpenFee(Long.valueOf(merchantDto.getOpenFee()));
		}
		if (StringUtils.isNotEmpty(merchantDto.getYearFee())) {
			enterpriseContract
					.setYearFee(Long.valueOf(merchantDto.getYearFee()));
		}
		enterpriseContract.setFactOpenFee(Long.valueOf(merchantDto
				.getFactOpenFee()));
		enterpriseContract.setFactYearFee(Long.valueOf(merchantDto
				.getFactYearFee()));
		enterpriseContract.setFactStartDate(DateUtil.parse("yyyy-MM-dd",
				merchantDto.getFactStartDate()));
		enterpriseContract.setFactEndDate(DateUtil.parse("yyyy-MM-dd",
				merchantDto.getFactEndDate()));
		enterpriseContract
				.setAssureFee(Long.valueOf(merchantDto.getAssureFee()));
		enterpriseContract.setAssureDesc(merchantDto.getAssureDesc());
		enterpriseContract.setUpdateDate(new Date());
		enterpriseContract.setMarketLink(StringUtil.isEmpty(merchantDto
				.getMarketLink()) ? "" : merchantDto.getMarketLink());
		return this.memberDao.updateEnterpriseContract(enterpriseContract) == 1;
	};

	private boolean updateLiquidateInfo(LiquidateInfo liquidateInfo) {
		liquidateInfo.setUpdateDate(new Date());
		String encodeAcct = DESUtil.encrypt(liquidateInfo.getBankAcct());
		liquidateInfo.setBankAcct(encodeAcct);
		return this.memberDao.updateLiquidateInfo(liquidateInfo) == 1;
	}

	private void updateRisk(MerchantDto memberDto) throws RMFacadeException {
		// riskService.updateBusinessLimitCustomTrans(Long.valueOf(memberDto.getMemberCode()),Integer.valueOf(memberDto.getIndustry()),Integer.valueOf(memberDto.getRiskLeveCode()));
	}

	private String getSearchKey(String zhName) {
		SearchKeyUtils skUtils = new SearchKeyUtils();
		String searchKey = skUtils.getAllFirstLetter(zhName);
		return searchKey;
	}

	@Override
	public String queryBalance(Map<String, String> paraMap) {

		return this.enterpriseDao.queryBalance(paraMap);
	}

	public Boolean updateLoginNameOfEnterpriseTrans(Map<String, Object> paramMap)
			throws PossException {
		Boolean success = false;
		try {
			enterpriseDao.updateLoginNameOfEnterpriseTrans(paramMap);
			success = true;
		} catch (PossException p) {
			throw new PossException("修改会员登录名错误",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return success;
	}

	@Override
	public void productJoinMenuEditTrans(String[] productMenu, String productId)
			throws PossException {
		try {
			this.productJoinMenuDelete(productId);
			this.productJoinMenuAdd(productMenu, productId);
		} catch (Exception e) {
			log.error("EnterpriseServiceImpl.productJoinMenuEditTrans is error ...");
			e.printStackTrace();
			throw new PossException("产品配置菜单失败!",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}

	}

	private void productJoinMenuDelete(String productId) {
		this.enterpriseDao.productJoinMenuDelete(productId);
	}

	private void productJoinMenuAdd(String[] productMenuArray, String productId) {
		if (productMenuArray != null && productMenuArray.length > 0
				&& productId != null) {
			for (int i = 0; i < productMenuArray.length; i++) {
				String menuId = productMenuArray[i];
				ProductMenu productMenu = new ProductMenu();
				Date date = new Date();
				productMenu.setMenuId(Integer.valueOf(menuId));
				productMenu.setProductId(Integer.valueOf(productId));
				productMenu.setCreateDate(date);
				productMenu.setUpdateDate(date);
				this.enterpriseDao.productJoinMenuAdd(productMenu);
			}
		}
	}

	@Override
	public void productOfAccountTempEditTrans(String[] productOfAccountTemp,
			String accountTempId) throws PossException {
		try {
			this.accountTempJoinProductDelete(accountTempId);
			this.accountTempJoinProductAdd(productOfAccountTemp, accountTempId);
		} catch (Exception e) {
			log.error("EnterpriseServiceImpl.productOfAccountTempEditTrans is error ...");
			e.printStackTrace();
			throw new PossException("账户模板配置产品失败!",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}

	}

	private void accountTempJoinProductDelete(String accountTempId) {
		this.enterpriseDao.accountTempJoinProductDelete(accountTempId);
	}

	private void accountTempJoinProductAdd(String[] productOfAccountTemp,
			String accountTempId) {
		if (productOfAccountTemp != null && productOfAccountTemp.length > 0
				&& accountTempId != null) {
			for (int i = 0; i < productOfAccountTemp.length; i++) {
				String productId = productOfAccountTemp[i];
				ProductAcctTemplate productAcctTemplate = new ProductAcctTemplate();
				Date date = new Date();
				productAcctTemplate.setProductId(Long.valueOf(productId));
				productAcctTemplate.setMemberAcctTemplateId(Long
						.valueOf(accountTempId));
				productAcctTemplate.setCreationDate(date);
				productAcctTemplate.setScene(Integer.valueOf(2));
				productAcctTemplate.setProductKey(Integer.valueOf(0));
				this.enterpriseDao
						.accountTempJoinProductAdd(productAcctTemplate);
			}
		}
	}

	@Override
	public List<Menu> getAllMenu(String allowObject) {
		if (allowObject.equals("1")) {
			allowObject = "8";
		} else if (allowObject.equals("2")) {
			allowObject = "9";
		}
		return this.enterpriseDao.getAllMenu(allowObject);

	}

	@Override
	public List<Menu> getMenuOfProduct(String productId) {
		return this.enterpriseDao.getMenuOfProduct(productId);
	}

	@Override
	public List<ProductSearchDto> queryProduct(ProductSearchDto productSearchDto) {
		return this.enterpriseDao.queryProduct(productSearchDto);
	}

	@Override
	public Integer queryProductCount(ProductSearchDto productSearchDto) {
		return this.enterpriseDao.queryProductCount(productSearchDto);
	}

	// private void deleteAccountByMemberCode(String memberCode){
	// this.enterpriseDao.deleteAccountByMemberCode(Long.valueOf(memberCode));
	// }
	// private void deleteAccountAttribuleByMemberCode(String memberCode){
	// this.enterpriseDao.deleteAccountAttribuleByMemberCode(Long.valueOf(memberCode));
	// }
	private void deleteProductByMemberCode(String memberCode) {
		this.enterpriseDao.deleteProductByMemberCode(Long.valueOf(memberCode));
	}

	public void setEnterpriseDao(IEnterpriseDao enterpriseDao) {
		this.enterpriseDao = enterpriseDao;
	}

	public void setMemberDao(IMemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void setRcLimitRuleFacade(RcLimitRuleFacade rcLimitRuleFacade) {
		this.rcLimitRuleFacade = rcLimitRuleFacade;
	}

	public void setMemberAcctTemplateService(
			MemberAcctTemplateService memberAcctTemplateService) {
		this.memberAcctTemplateService = memberAcctTemplateService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	@Override
	public boolean productDeleteFinal(String productId) throws PossException {
		try {
			this.enterpriseDao.deleteProductFinal(productId);
			this.enterpriseDao.productJoinMenuDelete(productId);
			return true;
			// this.enterpriseDao.deleteMerberProductFinal(productId);
		} catch (Exception e) {
			log.error("EnterpriseServiceImpl.productDeleteTrans is error... ");
			e.printStackTrace();
			throw new PossException("删除产品失败!",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
	}

	@Override
	public UserRelation queryBD(String loginId) {
		return 	this.enterpriseDao.queryBD(loginId);
	}

	@Override
	public List<EnterpriseSearchListDto> queryDetailOfAccountAll(
			EnterpriseSearchDto enterpriseSearchDto) {
		log.debug("EnterpriseServiceImpl.queryDetailOfAccount is running...");
		List<EnterpriseSearchListDto> enterpriseSearchListDto = enterpriseDao
				.queryDetailOfAccountAll(enterpriseSearchDto);
		return enterpriseSearchListDto;
	}

	@Override
	public List<EnterpriseSearchListDto> queryAccountOfEnterpriseAll(
			EnterpriseSearchDto enterpriseSearchDto) {
		log.debug("EnterpriseServiceImpl.queryAccountOfEnterprise is running...");
		List<EnterpriseSearchListDto> enterpriseSearchListDto = enterpriseDao
				.queryAccountOfEnterpriseAll(enterpriseSearchDto);
		return enterpriseSearchListDto;
	}

	@Override
	public List<AccountAttribute> queryAttributeByMemberCode(String memberCode) {
		List<AccountAttribute> aa = enterpriseDao.queryAttributeByMemberCode(memberCode);
		return aa;
	}

}
