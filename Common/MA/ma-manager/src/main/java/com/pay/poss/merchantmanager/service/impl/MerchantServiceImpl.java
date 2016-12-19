package com.pay.poss.merchantmanager.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.comm.Constants;
import com.pay.acc.comm.MerchantLevelEnum;
import com.pay.acc.comm.MerchantTypeEnum;
import com.pay.acc.comm.SearchKeyUtils;
import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acc.memberaccttemplate.dto.MemberAcctTemplateDto;
import com.pay.acc.memberaccttemplate.service.MemberAcctTemplateService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.inf.service.IMessageDigest;
import com.pay.jms.notification.request.EmailNotifyRequest;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.sender.JmsSender;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.merchantmanager.dao.IMemberDao;
import com.pay.poss.merchantmanager.dto.MerchantDto;
import com.pay.poss.merchantmanager.dto.MerchantSearchDto;
import com.pay.poss.merchantmanager.dto.MerchantSearchListDto;
import com.pay.poss.merchantmanager.model.Account;
import com.pay.poss.merchantmanager.model.AccountAttribute;
import com.pay.poss.merchantmanager.model.AccountAttributeTemplate;
import com.pay.poss.merchantmanager.model.CheckCode;
import com.pay.poss.merchantmanager.model.EnterpriseBase;
import com.pay.poss.merchantmanager.model.EnterpriseContact;
import com.pay.poss.merchantmanager.model.EnterpriseContract;
import com.pay.poss.merchantmanager.model.LiquidateInfo;
import com.pay.poss.merchantmanager.model.Member;
import com.pay.poss.merchantmanager.model.MemberProduct;
import com.pay.poss.merchantmanager.model.Operator;
import com.pay.poss.merchantmanager.service.IMerchantService;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;
import com.pay.rm.facade.RcLimitRuleFacade;
import com.pay.util.DESUtil;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;
import com.pay.util.UUIDUtil;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file ContractServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-8-16 gungun_zhang Create
 */
public class MerchantServiceImpl implements IMerchantService {

	private final Log log = LogFactory.getLog(MerchantServiceImpl.class);
	private MemberAcctTemplateService memberAcctTemplateService;
	private AcctAttribService acctAttribService;
	private AcctService acctService;
	private EnterpriseBaseService enterpriseBaseService;
	private RcLimitRuleFacade rcLimitRuleFacade;
	private IMemberDao memberDao;
	private IMessageDigest messageDigest;
	private JmsSender jmsSender;
	private String emailActionUri;

	/**
	 * 获取36位随机验证码
	 * 
	 * @return
	 */
	private String checkCode() {
		return UUIDUtil.uuid();
	}

	private void sendEmail(String merchantCode, String checkCode,
			String userName, List<String> recAddress, String subject,
			String url, long templateId) {
		Map<String, String> data = new HashMap<String, String>();
		EmailNotifyRequest request = new EmailNotifyRequest();
		// request.setFromAddress(Constants.EMAIL_SENDER);// 默认邮件发送者
		request.setRecAddress(recAddress);// 收件人列表
		request.setTemplateId(templateId);// 模板ID
		request.setSubject(subject);// 邮件主题
		request.setBizCode("activeUser4");
		request.setMerchantCode("0");
		request.setType(RequestType.EMAIL);
		data.put("userId", userName);// 商户名称
		data.put("url", url + checkCode);// 激活url
		request.setData(data);
		jmsSender.send(request);
	}

	private void sendEmailForMarket(Map<String, String> data,
			List<String> recAddress, String subject, long templateId) {
		EmailNotifyRequest request = new EmailNotifyRequest();
		request.setFromAddress(Constants.EMAIL_SENDER);// 默认邮件发送者
		request.setRecAddress(recAddress);// 收件人列表
		request.setBizCode("001");
		request.setMerchantCode("0");
		request.setTemplateId(templateId);// 模板ID
		request.setSubject(subject);// 邮件主题
		request.setData(data);
		jmsSender.send(request);

	}

	@Override
	public Boolean sendEmailForActivationTrans(String[] checkedArray)
			throws PossException {
		Boolean isSuccess = true;
		try {
			for (int i = 0; i < checkedArray.length; i++) {
				String checked = checkedArray[i];
				String[] codeAndEmail = checked.split("\\|");
				if (codeAndEmail.length == 3) {
					String memberCode = codeAndEmail[0];
					String memberName = codeAndEmail[1];
					String email = codeAndEmail[2];
					List<String> emailList = new ArrayList<String>();
					emailList.add(email);
					String checkCode = this.checkCode();
					// 以前的邮件激活码置为无效
					int updateCount = memberDao.updateCheckCodeStatus(
							Long.valueOf(memberCode), 2);// 2为校验过了，相当 于置为无效
					// 下发最新的激活邮件
					this.insertCheckCode(checkCode, Long.valueOf(memberCode),
							Constants.ORIGIN, emailList.get(0) + "", null);

					EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService
							.queryEnterpriseBaseByMemberCode(Long
									.valueOf(memberCode));
					this.sendEmail(enterpriseBaseDto.getMerchantCode()
							.toString(), checkCode, memberName, emailList,
							Constants.EMAIL_TITLE, emailActionUri,
							Constants.EMAIL_TEMPID);
				}
			}
		} catch (Exception e) {
			log.error("MerchantServiceImpl.sendEmailForActivationTrans is error...");
			e.printStackTrace();
			isSuccess = false;
			throw new PossException("批量发送激活邮件",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		return isSuccess;
	}

	@Override
	public void sendEmailTrans(MerchantDto merchantDto) throws PossException {

		try {
			List<String> emailList = new ArrayList<String>();
			emailList.add(merchantDto.getEmail());
			String checkCode = this.checkCode();
			// 以前的邮件激活码置为无效
			int updateCount = memberDao.updateCheckCodeStatus(
					Long.valueOf(merchantDto.getMemberCode()), 2);// 2为校验过了，相当
																	// 于置为无效

			this.insertCheckCode(checkCode,
					Long.valueOf(merchantDto.getMemberCode()),
					Constants.ORIGIN, emailList.get(0) + "", null);

			EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService
					.queryEnterpriseBaseByMemberCode(Long.valueOf(merchantDto
							.getMemberCode()));
			this.sendEmail(enterpriseBaseDto.getMerchantCode().toString(),
					checkCode, merchantDto.getZhName(), emailList,
					Constants.EMAIL_TITLE, emailActionUri,
					Constants.EMAIL_TEMPID);
			// //发email通知 市场部
			// String email = this.getMarketEmail(merchantDto.getSignDepart());
			// if(StringUtils.isNotEmpty(email)){
			// Map<String, String> data = new HashMap<String, String>();
			// List<String> emailAddress = new ArrayList<String>();
			// emailAddress.add(email);
			// data.put("memberCode",merchantDto.getMemberCode());
			// data.put("merchantCode",merchantDto.getMerchantCode());
			// data.put("merchantName",merchantDto.getZhName());
			// data.put("compayLinkerName",merchantDto.getCompayLinkerName());
			// data.put("compayLinkerTel",merchantDto.getCompayLinkerTel());
			// data.put("emailType",Constants.EMAILTYPE_2);
			//
			// this.sendEmailForMarket(data, emailAddress,
			// Constants.EMAIL_TITLEFORMARKET, Constants.EMAIL_TEMPIDOfMARKET);
			// }
		} catch (Exception e) {
			log.error("MerchantServiceImpl.sendEmail is error...", e);
			throw new PossException("补发激活邮件",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
	}

	@Override
	public List<MerchantSearchListDto> queryMerchant(
			MerchantSearchDto merchantSearchDto) {
		return this.memberDao.queryMerchant(merchantSearchDto);
	}

	@Override
	public List<MerchantSearchListDto> queryMerchantOfUnActive(
			MerchantSearchDto merchantSearchDto) {
		return this.memberDao.queryMerchantOfUnActive(merchantSearchDto);
	}

	@Override
	public Integer queryMerchantCount(MerchantSearchDto merchantSearchDto) {
		return this.memberDao.queryMerchantCount(merchantSearchDto);
	}

	@Override
	public Integer queryMerchantOfUnActiveCount(
			MerchantSearchDto merchantSearchDto) {
		return this.memberDao.queryMerchantOfUnActiveCount(merchantSearchDto);
	}

	@Override
	synchronized public String createMerchantTrans(MerchantDto merchantDto)
			throws PossException {
		Long memberCode = null;
		Long merchantCode = null;

		try {
			memberCode = insertMember(merchantDto);
			merchantCode = this.getNewMerchantCode(merchantDto
					.getMerchantCodePrefix());
			this.insertEnterpriseBase(
					this.getSearchKey(merchantDto.getZhName()), merchantCode,
					memberCode, merchantDto);
			this.insertEnterpriseContact(memberCode, merchantDto);
			this.insertEnterpriseContract(memberCode, merchantDto);
			this.insertLiquidateInfo(memberCode, merchantDto);
			this.insertOperator(memberCode, merchantDto);
			// 暂时不记日志
			// this.insertFlowLog(memberCode, memberDto);
			// 开通账户
			Integer enterpriseType = Integer.valueOf(merchantDto
					.getEnterpriseType());
			Integer acctType = AcctTypeEnum.BASIC_CNY.getCode();// 账户类型,默认开通人民币账户
			this.insertAccount(memberCode.toString(), merchantDto, acctType);
			if (enterpriseType == MerchantTypeEnum.CROSSPAY_ENTERPRISEMERCHANT
					.getCode()) {
				acctType = AcctTypeEnum.GUARANTEE_CNY.getCode();// 账户类型,默认开通人民币保证金账户
				this.insertAccount(memberCode.toString(), merchantDto, acctType);
			}

		} catch (Exception e) {
			log.error("MerchantServiceImpl.createMerchant is error...", e);
			throw new PossException("新增商户错误",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}

		return memberCode.toString();

	}

	// private Boolean insertRisk(Long memberCode,MerchantDto memberDto) throws
	// RMFacadeException, NumberFormatException{
	// return
	// riskService.setDefaultMccLimit(memberCode,Integer.valueOf(memberDto.getIndustry()),Integer.valueOf(memberDto.getRiskLeveCode()));
	// }
	private void insertMemberProduct(Long memberCode, MerchantDto memberDto) {
		List<AccountAttributeTemplate> acctTempList = this.memberDao
				.queryAllAcctTempOfBusiness();
		if (acctTempList != null && acctTempList.size() > 0) {
			for (int i = 0; i < acctTempList.size(); i++) {
				AccountAttributeTemplate acctTemp = acctTempList.get(i);
				String acctTemplateId = acctTemp.getMatId().toString();

				List<MemberProduct> productList = this.memberDao
						.queryProductAcctTempByAccTempId(acctTemplateId);
				if (productList != null && productList.size() > 0) {
					Date createDate = new Date();
					for (int j = 0; j < productList.size(); j++) {
						MemberProduct memberProduct = productList.get(j);

						memberProduct.setMemberCode(memberCode);
						memberProduct.setStatus(Constants.PRODUCTSTATUS_OPEN);
						memberProduct.setCreationDate(createDate);
						memberProduct.setUpdateDate(createDate);
						this.memberDao.insertMemberProduct(memberProduct);
					}
				}
			}
		}

	}

	@Override
	public Boolean updateMerchantTrans(MerchantDto memberDto)
			throws PossException {
		Boolean isSuccess = true;
		try {
			this.updateMember(memberDto);
			this.updateEnterpriseBase(memberDto);
			this.updateEnterpriseContact(memberDto);
			this.updateEnterpriseContract(memberDto);
			this.updateLiquidateInfo(memberDto);
			// this.updateRisk(memberDto);

		} catch (Exception e) {
			log.error("MerchantServiceImpl.updateMerchantTrans is error...");
			e.printStackTrace();
			isSuccess = false;
			throw new PossException("更新商户",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);

		}
		return isSuccess;
	}

	// private void updateRisk(MerchantDto memberDto) throws RMFacadeException{
	// riskService.updateBusinessLimitCustomTrans(Long.valueOf(memberDto.getMemberCode()),Integer.valueOf(memberDto.getIndustry()),Integer.valueOf(memberDto.getRiskLeveCode()));
	// }

	private void insertCheckCode(String checkCodeStr, Long memberCode,
			String origin, String email, String phone) {
		CheckCode checkCode = new CheckCode();
		checkCode.setCheckCode(checkCodeStr);
		checkCode.setMemberCode(memberCode);
		checkCode.setStatus(Integer.valueOf(Constants.URLUNACTIVATION));
		checkCode.setOrigin(origin);
		if (StringUtils.isNotEmpty(email)) {
			checkCode.setEmail(email);
		}
		if (StringUtils.isNotEmpty(phone)) {
			checkCode.setPhone(phone);
		}
		Date createDate = new Date();
		checkCode.setCreateDate(createDate);
		checkCode.setUpdateDate(createDate);
		this.memberDao.insertCheckCode(checkCode);

	}

	@Override
	public Boolean updateMerchantStatusTrans(Long memberCode, String email,
			String name, Integer status) throws PossException {
		Boolean isSuccess = true;
		EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService
				.queryEnterpriseBaseByMemberCode(Long.valueOf(memberCode));
		try {
			if (status == 1) {
				Member member = new Member();
				member.setMemberCode(memberCode);
				member.setStatus(Constants.STATUS_UNACTIVATION);// 未激活
				this.memberDao.updateMember(member);
				// 发email给商户
				List<String> recAddress = new ArrayList<String>();
				recAddress.add(email);
				String checkCode = this.checkCode();

				memberDao.updateCheckCodeStatus(memberCode, 2);// 置以前的为无效
				this.insertCheckCode(checkCode, memberCode, Constants.ORIGIN,
						email, null);

				this.sendEmail(enterpriseBaseDto.getMerchantCode().toString(),
						checkCode, name, recAddress, Constants.EMAIL_TITLE,
						emailActionUri, Constants.EMAIL_TEMPID);
				// 发email通知 市场部
				// String email =
				// this.getMarketEmail(merchantDto.getSignDepart());
				// if(StringUtils.isNotEmpty(email)){
				// Map<String, String> data = new HashMap<String, String>();
				// List<String> emailAddress = new ArrayList<String>();
				// emailAddress.add(email);
				// data.put("memberCode",merchantDto.getMemberCode());
				// data.put("merchantCode",merchantDto.getMerchantCode());
				// data.put("merchantName",merchantDto.getZhName());
				// data.put("compayLinkerName",merchantDto.getCompayLinkerName());
				// data.put("compayLinkerTel",merchantDto.getCompayLinkerTel());
				// data.put("emailType",Constants.EMAILTYPE_1);

				// this.sendEmailForMarket(data, emailAddress,
				// Constants.EMAIL_TITLEFORMARKET,
				// Constants.EMAIL_TEMPIDOfMARKET);
				// }
			} else if (status == 0) {
				Member member = new Member();
				member.setMemberCode(memberCode);
				member.setStatus(Constants.STATUS_CREATE);// 创建状态
				this.memberDao.updateMember(member);
			}
			EnterpriseBase enterpriseBase = new EnterpriseBase();
			enterpriseBase.setAudiStatus(status);
			enterpriseBase.setMemberCode(memberCode);
			this.memberDao.updateMerchantCheckStatus(enterpriseBase);

			// 如果境外商户，创建保证金账户
			if (enterpriseBaseDto.getEnterpriseType() == MerchantTypeEnum.CROSSPAY_ENTERPRISEMERCHANT
					.getCode()) {
				MemberAcctTemplateDto memberAcctTemplateDto = memberAcctTemplateService
						.queryByMemberTypeAndAcctType(
								MemberTypeEnum.MERCHANT.getCode(),
								AcctTypeEnum.GUARANTEE_CNY.getCode());
				if (null == memberAcctTemplateDto) {
					log.error("保证金账户开通失败！");
					return false;
				}

				StringBuilder memberAcctCode = new StringBuilder();
				memberAcctCode.append(memberCode);
				memberAcctCode.append(memberAcctTemplateDto.getAcctType());

				AcctAttribDto acctAttrib = new AcctAttribDto();
				BeanUtils.copyProperties(memberAcctTemplateDto, acctAttrib);
				String acctCode = memberAcctTemplateDto.getSubjectCode()
						+ memberCode + memberAcctTemplateDto.getAcctType();
				acctAttrib.setAcctCode(acctCode);
				acctAttrib.setMemberCode(memberCode);
				acctAttrib.setMemberAcctCode(Long.valueOf(memberAcctCode
						.toString()));
				acctAttrib.setCreateDate(new Date());
				acctAttrib.setUpdateDate(new Date());
				acctAttrib.setCurCode(memberAcctTemplateDto.getCurCode());

				AcctAttribDto acctAttribDto = acctAttribService
						.queryAcctAttribByAcctCode(acctCode);
				if(null == acctAttribDto){
					acctAttribService.createAcctAttrib(acctAttrib);

					// 创建余额对象
					AcctDto acct = new AcctDto();
					acct.setAcctCode(acctCode);
					acct.setMemberCode(memberCode);
					acct.setCreateDate(new Date());
					acct.setUpdateDate(new Date());
					acctService.createAcct(acct);
				}

			}

			// FlowLog flowLog = new FlowLog();
			// flowLog.setMemberCode(Long.valueOf(merchantDto.getMemberCode()));
			// if(StringUtils.isEmpty(merchantDto.getUserDept())){
			// flowLog.setDepart(Constants.DEPT_RISK);//风控部
			// }else{
			// flowLog.setDepart(merchantDto.getUserDept());//
			// }
			// flowLog.setOperatorId(Long.valueOf(merchantDto.getUserId()));//操作员ID
			// flowLog.setName(merchantDto.getUserName());
			// flowLog.setStatus(Long.valueOf(status));//当前状态 1审核通过
			// flowLog.setNote(merchantDto.getGoBackRemark());
			// Date date = new Date();
			// flowLog.setAudiDate(date) ;
			// flowLog.setCreateDate(date);
			// flowLog.setUpdateDate(date);
			// this.memberDao.insertFlowLog(flowLog);
		} catch (Exception e) {
			log.error(
					"MerchantServiceImpl.updateMerchantStatusTrans is error...",
					e);
			isSuccess = false;
			throw new PossException("更新商户状态",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);

		}
		return isSuccess;
	}

	private String getMarketEmail(String signDept) {
		return this.memberDao.getMarketEmail(signDept);

	}

	private void updateMember(MerchantDto merchantDto) {
		Member member = new Member();

		member.setMemberCode(Long.valueOf(merchantDto.getMemberCode()));
		member.setServiceLevelCode(Integer.valueOf(merchantDto
				.getServiceLevelCode()));
		member.setStatus(Constants.STATUS_CREATE);// 会员状态为创建状态
		member.setUpdateDate(new Date());

		this.memberDao.updateMember(member);
	}

	private void updateEnterpriseBase(MerchantDto merchantDto) {
		EnterpriseBase enterpriseBase = new EnterpriseBase();
		enterpriseBase.setMemberCode(Long.valueOf(merchantDto.getMemberCode()));
		enterpriseBase.setEnterpriseType(Integer.valueOf(merchantDto
				.getEnterpriseType()));
		enterpriseBase.setAudiStatus(Constants.CHECK_READYCHECK);// 未审核
		enterpriseBase.setZhName(merchantDto.getZhName());
		enterpriseBase.setSearchKey(this.getSearchKey(merchantDto.getZhName()));// 中文名拼音首字母（查询用）
		enterpriseBase.setEnName(merchantDto.getEnName());
		enterpriseBase.setWebsite(merchantDto.getWebsite());
		enterpriseBase.setNation(merchantDto.getNation());
		enterpriseBase.setRegion(merchantDto.getRegion());
		enterpriseBase.setCity(Long.valueOf(merchantDto.getCity()));
		enterpriseBase.setIndustry(merchantDto.getIndustry());
		enterpriseBase.setBizLicenceCode(merchantDto.getBizLicenceCode());
		enterpriseBase.setExpire(DateUtil.parse("yyyy-MM-dd",
				merchantDto.getExpire()));
		enterpriseBase.setGovCode(merchantDto.getGovCode());
		enterpriseBase.setTaxCode(merchantDto.getTaxCode());
		enterpriseBase.setRiskLeveCode(merchantDto.getRiskLeveCode());
		enterpriseBase.setUpdateDate(new Date());
		enterpriseBase.setSettlementCycle(merchantDto.getSettlementCycle());
		// merchantCode随mcc,风险等级,城市的改变而改变.
		Long merchantCode = this.getNewMerchantCode(merchantDto.getCity(),
				merchantDto.getIndustry(), merchantDto.getRiskLeveCode());
		enterpriseBase.setMerchantCode(merchantCode);
		this.memberDao.updateEnterpriseBase(enterpriseBase);

	}

	private void updateEnterpriseContact(MerchantDto merchantDto) {
		EnterpriseContact enterpriseContact = new EnterpriseContact();
		enterpriseContact.setMemberCode(Long.valueOf(merchantDto
				.getMemberCode()));
		enterpriseContact.setAddress(merchantDto.getAddress());
		enterpriseContact.setFax(merchantDto.getFax());
		enterpriseContact.setTel(merchantDto.getTel());
		enterpriseContact.setZip(merchantDto.getZip());
		enterpriseContact.setLegalName(merchantDto.getLegalName());
		enterpriseContact.setLegalLink(merchantDto.getLegalLink());
		// enterpriseContact.setEmail(merchantDto.getEmail());
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
		this.memberDao.updateEnterpriseContact(enterpriseContact);
	}

	private void updateEnterpriseContract(MerchantDto merchantDto) {
		EnterpriseContract enterpriseContract = new EnterpriseContract();
		enterpriseContract.setMemberCode(Long.valueOf(merchantDto
				.getMemberCode()));
		enterpriseContract.setSignName(merchantDto.getSignName());
		// 2014/5/12
		enterpriseContract.setSignLoginId(merchantDto.getSignLoginId());

		enterpriseContract.setSignDepart(merchantDto.getSignDepart());
		enterpriseContract.setContinueSign(Integer.valueOf(merchantDto
				.getContinueSign()));
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
		this.memberDao.updateEnterpriseContract(enterpriseContract);
	}

	private void updateLiquidateInfo(MerchantDto merchantDto) {
		LiquidateInfo liquidateInfo = new LiquidateInfo();

		liquidateInfo.setMemberCode(Long.valueOf(merchantDto.getMemberCode()));
		liquidateInfo.setBankName(merchantDto.getBankName());
		liquidateInfo.setBankAcct(DESUtil.encrypt(merchantDto.getBankAcct()));// 商户结算银行账户
		liquidateInfo.setAcctName(merchantDto.getAcctName());// 商户结算账户名称
		// liquidateInfo.setAccountMode(Integer.valueOf(merchantDto.getAccountMode()));
		liquidateInfo.setProvince(Long.valueOf(merchantDto.getRegionBank()));
		liquidateInfo.setCity(Long.valueOf(merchantDto.getCityBank()));
		liquidateInfo.setBankId(merchantDto.getBankId());
		liquidateInfo.setBankAddress(merchantDto.getBankAddress());
		liquidateInfo.setUpdateDate(new Date());
		this.memberDao.updateLiquidateInfo(liquidateInfo);
	}

	@Override
	public List<MerchantDto> getMerchantListByMemberCode(String memberCode) {
		List<MerchantDto> list = this.memberDao
				.getMerchantListByMemberCode(Long.valueOf(memberCode));
		for (MerchantDto mDto : list) {
			mDto.setBankAcct(DESUtil.decrypt(mDto.getBankAcct()));
		}
		return list;
	}

	private void insertAccount(String memberCode, MerchantDto merchantDto,
			Integer acctType) {

		// 商户类型
		Integer memberType = MemberTypeEnum.MERCHANT.getCode();

		MemberAcctTemplateDto memberAcctTemplateDto = memberAcctTemplateService
				.queryByMemberTypeAndAcctType(memberType, acctType);
		if (null == memberAcctTemplateDto) {
			throw new PossException("新增商户错误",
					ExceptionCodeEnum.MEMBER_ACCT_TEMPLATE_NOT_EXISTS);
		}
		String subjectCode = memberAcctTemplateDto.getSubjectCode();
		String accountCode = subjectCode + memberCode + acctType;
		this.insertAccountAttribute(accountCode, acctType,
				Long.valueOf(memberCode), memberAcctTemplateDto, merchantDto);

		Account account = new Account();
		account.setMemberCode(Long.valueOf(memberCode));
		account.setBalance(Constants.BALANCE);
		account.setFrozenAmount(Constants.FROZEN);
		account.setStatus(Constants.ACCOUNTSTATUS);
		Date createDate = new Date();
		account.setCreateDate(createDate);
		account.setUpdateDate(createDate);
		account.setCreditBalance(Constants.CREDITBALANCE);// 借方发生额
		account.setDebitBalance(Constants.DEBITBALANCE);// 贷方发生额
		account.setAcctCode(accountCode);
		account.setTotalBalance(Constants.TOTALBALANCE);
		this.memberDao.insertAccount(account);
	}

	@Override
	public String isAllowCreateOrUpdateMerchant(Map<String, String> paraMap) {
		String result = "";
		// result = this.isBizLicenceCodeExist(paraMap.get("bizLicenceCode"));
		// result = result+this.isGovCodeExist(paraMap.get("govCode"));
		// result = result+this.isTaxCodeExist(paraMap.get("taxCode"));
		result = result + this.isEmailExist(paraMap.get("email"));
		return result;
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

	private String isEmailExist(String email) {
		Boolean isExist = this.memberDao.isEmailExist(email);
		if (isExist) {
			return "Email已被占用!";
		} else {
			return "";
		}
	}

	private void insertAccountAttribute(String accountCode, Integer acctType,
			Long memberCode, MemberAcctTemplateDto memberAcctTemplateDto,
			MerchantDto merchantDto) {

		AccountAttribute accountAttribute = new AccountAttribute();
		BeanUtils.copyProperties(memberAcctTemplateDto, accountAttribute);
		if (!StringUtil.isEmpty(merchantDto.getAllowAdvanceMoney())) {
			accountAttribute.setAllowAdvanceMoney(Integer.parseInt(merchantDto
					.getAllowAdvanceMoney()));
		}
		Date date = new Date();
		accountAttribute.setCreateDate(date);
		accountAttribute.setUpdateDate(date);
		accountAttribute.setCurCode(memberAcctTemplateDto.getCurCode());
		accountAttribute.setMemberCode(memberCode);
		accountAttribute.setAcctCode(accountCode);
		accountAttribute.setMemberAcctCode(Long.valueOf(memberCode + ""
				+ acctType));
		if(acctType == AcctTypeEnum.BASIC_CNY.getCode())
		{
			accountAttribute.setDefRecAcct(1);//注册时默认生成的人民币基本账户，设为默认收款账户  2016年4月28日11:56:45 delin.dong
		}
		// 表结构调整,支付密码可以为空,取消默认值.
		/*
		 * try {
		 * accountAttribute.setPayPwd(messageDigest.genMessageDigest(Constants
		 * .PW.getBytes()));//这个地方有待考虑,有安全漏洞 } catch (Exception e) { log.error(
		 * "createMerchant.insertAccountAttribute.messageDigest.genMessageDigest() is error !"
		 * ); e.printStackTrace(); }
		 */
		Date createDate = new Date();
		accountAttribute.setCreateDate(createDate);
		accountAttribute.setUpdateDate(createDate);
		this.memberDao.insertAccountAttribute(accountAttribute);
	}

	private List<AccountAttributeTemplate> queryAccAttriTempByAccType(
			Map<String, Object> paraMap) {
		return this.memberDao.queryAccAttriTempByAccType(paraMap);
	}

	private void insertOperator(Long memberCode, MerchantDto memberDto) {
		Operator op = new Operator();

		op.setIdentity(Constants.OPLOGINNAME);// 操作员登录标识
		op.setMemberCode(memberCode);// 会员号
		op.setName(Constants.OPNAME);// 操作员姓名
		op.setStatus(Constants.OPSTATUS);// 操作员状态0创建，1正常，2冻结，3删除
		op.setEmail(Constants.UNKNOW); // email
		try {
			op.setLoginPwd(messageDigest.genMessageDigest(memberDto.getEmail()
					.getBytes()));// 登录密码
		} catch (Exception e) {
			log.error("createMerchant.insertOperator.messageDigest.genMessageDigest() is error !");
			e.printStackTrace();
		}
		Date date = new Date();
		op.setCreateDate(date);// 数据创建时间
		op.setUpdateDate(date);// 数据更新时间
		op.setDepart(Constants.UNKNOW);// 操作员部门
		op.setCertType(Constants.OPCERTTYPE);// 身份证
		this.memberDao.insertOperator(op);
	}

	// private void insertFlowLog(Long memberCode,MerchantDto memberDto){
	// FlowLog flowLog = new FlowLog();
	//
	// flowLog.setMemberCode(memberCode);
	// if(StringUtils.isEmpty(memberDto.getUserDept())){
	// flowLog.setDepart(Constants.DEPT_MARKET);//市场部
	// }else{
	// flowLog.setDepart(memberDto.getUserDept());//
	// }
	//
	// flowLog.setOperatorId(Long.valueOf(memberDto.getUserId()));//操作员ID
	// flowLog.setName(memberDto.getUserName());
	// flowLog.setStatus(Long.valueOf(Constants.CHECK_READYCHECK));//当前状态 2未审核
	// Date date = new Date();
	// flowLog.setAudiDate(date) ;
	// flowLog.setCreateDate(date);
	// flowLog.setUpdateDate(date);
	// this.memberDao.insertFlowLog(flowLog);
	// }

	private void insertLiquidateInfo(Long memberCode, MerchantDto memberDto) {
		LiquidateInfo liquidateInfo = new LiquidateInfo();
		liquidateInfo.setMemberCode(memberCode);
		liquidateInfo.setBankName(memberDto.getBankName());
		String bankAcct = memberDto.getBankAcct();
		if(!StringUtil.isEmpty(bankAcct)){
			liquidateInfo.setBankAcct(DESUtil.encrypt(bankAcct));// 商户结算银行账户
		}
		
		liquidateInfo.setAcctName(memberDto.getAcctName());// 商户结算账户名称
		String regionBank = memberDto.getRegionBank();
		if(!StringUtil.isEmpty(regionBank)){
			liquidateInfo.setProvince(Long.valueOf(regionBank));
		}
		
		String cityBank = memberDto.getCityBank();
		if(!StringUtil.isEmpty(cityBank)){
			liquidateInfo.setCity(Long.valueOf(cityBank));
		}
		liquidateInfo.setBankId(memberDto.getBankId());
		liquidateInfo.setBankAddress(memberDto.getBankAddress());
		Date createDate = new Date();
		liquidateInfo.setCreateDate(createDate);
		liquidateInfo.setUpdateDate(createDate);
		String branchBankId = memberDto.getBranchBankId();
		if(!StringUtil.isEmpty(branchBankId)){
			liquidateInfo.setBranchBankId(Long.valueOf(branchBankId));
		}
		
		this.memberDao.insertLiquidateInfo(liquidateInfo);
	}

	private void insertEnterpriseContract(Long memberCode, MerchantDto memberDto) {
		EnterpriseContract enterpriseContract = new EnterpriseContract();

		enterpriseContract.setMemberCode(memberCode);
		enterpriseContract.setContractCode(Constants.UNKNOW);
		enterpriseContract.setSignName(memberDto.getSignName());
		// 2014/5/12
		enterpriseContract.setSignLoginId(memberDto.getSignLoginId());

		enterpriseContract.setSignDepart(memberDto.getSignDepart());
		enterpriseContract.setContractStatus(Constants.CONTRACTSTATUS);
		enterpriseContract.setMarketName(Constants.UNKNOW);
		enterpriseContract.setMarketLink(StringUtil.isEmpty(memberDto
				.getMarketLink()) ? "" : memberDto.getMarketLink());
		if (StringUtils.isNotEmpty(memberDto.getStartDate())) {
			enterpriseContract.setStartDate(DateUtil.parse("yyyy-MM-dd",
					memberDto.getStartDate()));
		}
		if (StringUtils.isNotEmpty(memberDto.getEndDate())) {
			enterpriseContract.setEndDate(DateUtil.parse("yyyy-MM-dd",
					memberDto.getEndDate()));
		}
		if (StringUtils.isNotEmpty(memberDto.getOpenFee())) {
			enterpriseContract.setOpenFee(Long.valueOf(memberDto.getOpenFee()));
		}
		if (StringUtils.isNotEmpty(memberDto.getYearFee())) {
			enterpriseContract.setYearFee(Long.valueOf(memberDto.getYearFee()));
		}
		enterpriseContract.setFactOpenFee(Long.valueOf(memberDto
				.getFactOpenFee()));
		enterpriseContract.setFactYearFee(Long.valueOf(memberDto
				.getFactYearFee()));
		enterpriseContract.setFactStartDate(DateUtil.parse("yyyy-MM-dd",
				memberDto.getFactStartDate()));
		enterpriseContract.setFactEndDate(DateUtil.parse("yyyy-MM-dd",
				memberDto.getFactEndDate()));
		enterpriseContract.setAssureFee(Long.valueOf(memberDto.getAssureFee()));
		enterpriseContract.setFactAssureFee(Constants.UNKNOW_LONG);
		enterpriseContract.setAssureDesc(memberDto.getAssureDesc());
		Date createDate = new Date();
		enterpriseContract.setCreateDate(createDate);
		enterpriseContract.setUpdateDate(createDate);
		this.memberDao.insertEnterpriseContract(enterpriseContract);
	}

	private void insertEnterpriseContact(Long memberCode, MerchantDto memberDto) {
		EnterpriseContact enterpriseContact = new EnterpriseContact();

		enterpriseContact.setMemberCode(memberCode);
		enterpriseContact.setAddress(memberDto.getAddress());
		enterpriseContact.setFax(memberDto.getFax());
		enterpriseContact.setTel(memberDto.getTel());
		enterpriseContact.setZip(memberDto.getZip());
		enterpriseContact.setLegalName(memberDto.getLegalName());
		enterpriseContact.setLegalLink(memberDto.getLegalLink());
		enterpriseContact.setEmail(memberDto.getEmail().toLowerCase());// 一律改成小写的email
		enterpriseContact.setFinanceName(memberDto.getFinanceName());
		enterpriseContact.setFinanceLink(memberDto.getFinanceLink());
		enterpriseContact.setCompayLinkerName(memberDto.getCompayLinkerName());
		enterpriseContact.setCompayLinkerTel(memberDto.getCompayLinkerTel());
		enterpriseContact.setTechName(memberDto.getTechName());
		enterpriseContact.setTechLink(memberDto.getTechLink());
		enterpriseContact.setWebName1(memberDto.getWebName1());
		enterpriseContact.setWebAddr1(memberDto.getWebAddr1());
		enterpriseContact.setWebName2(memberDto.getWebName2());
		enterpriseContact.setWebAddr2(memberDto.getWebAddr2());
		enterpriseContact.setWebName3(memberDto.getWebName3());
		enterpriseContact.setWebAddr3(memberDto.getWebAddr3());
		Date createDate = new Date();
		enterpriseContact.setCreateDate(createDate);
		enterpriseContact.setUpdateDate(createDate);
		this.memberDao.insertEnterpriseContact(enterpriseContact);
	}

	private void insertEnterpriseBase(String searchKey, Long merchantCode,
			Long memberCode, MerchantDto memberDto) {
		EnterpriseBase enterpriseBase = new EnterpriseBase();

		enterpriseBase.setMemberCode(memberCode);
		enterpriseBase.setMerchantCode(merchantCode);// 根据规则生成
		enterpriseBase.setEnterpriseType(Integer.valueOf(memberDto
				.getEnterpriseType()));
		enterpriseBase.setAudiStatus(Constants.CHECK_READYCHECK);
		enterpriseBase.setZhName(memberDto.getZhName());
		enterpriseBase.setSearchKey(searchKey);// 中文名拼音首字母（查询用）
		enterpriseBase.setEnName(memberDto.getEnName());
		enterpriseBase.setWebsite(memberDto.getWebsite());
		enterpriseBase.setNation(memberDto.getNation());
		enterpriseBase.setRegion(memberDto.getRegion());
		enterpriseBase.setCity(Long.valueOf(memberDto.getCity()));
		enterpriseBase.setIndustryKind(Constants.UNKNOW);// 行业门类
		enterpriseBase.setIndustryType(Constants.UNKNOW);// 行业大类
		enterpriseBase.setIndustry(memberDto.getIndustry());
		enterpriseBase.setBizLicenceCode(memberDto.getBizLicenceCode());
		enterpriseBase.setExpire(DateUtil.parse("yyyy-MM-dd",
				memberDto.getExpire()));
		enterpriseBase.setGovCode(memberDto.getGovCode());
		enterpriseBase.setTaxCode(memberDto.getTaxCode());
		enterpriseBase.setRiskLeveCode(memberDto.getRiskLeveCode());
		Date createDate = new Date();
		enterpriseBase.setCreateDate(createDate);
		enterpriseBase.setUpdateDate(createDate);
		enterpriseBase.setSettlementCycle(memberDto.getSettlementCycle());
		String serviceLevelCode = memberDto.getServiceLevelCode();
		if (serviceLevelCode != null
				&& Integer.parseInt(serviceLevelCode) == MerchantLevelEnum.TRADING_CENTER
						.getCode()) {
			enterpriseBase.setIdentity(1);// 商户身份标识 0-普通商户 1-交易中心管理员 2-交易商';
		} else {
			enterpriseBase.setIdentity(0);// 默认
		}

		this.memberDao.insertEnterpriseBase(enterpriseBase);
	}

	private Long insertMember(MerchantDto memberDto) {
		Member member = new Member();

//		member.setMerchantType(Constants.MEMBERTYPE);
		member.setMerchantType(Integer.valueOf(memberDto.getMerchantType()));
		member.setServiceLevelCode(Integer.valueOf(memberDto
				.getServiceLevelCode()));
		member.setGreeting(Constants.GREETING);
		member.setStatus(Constants.STATUS_CREATE);
		member.setSecurityQuestion(Constants.SECUTITYQUESTION);
		// member.setSsoUserId(Constants.SSOID);
		member.setLoginType(Constants.LOGINTYPE);
		member.setLoginName(memberDto.getEmail());
		try {
			member.setSecurityAnswer(this.messageDigest
					.genMessageDigest(Constants.SECURITYANSWER.getBytes()));
			member.setLoginPwd(this.messageDigest.genMessageDigest(Constants.PW
					.getBytes()));
		} catch (Exception e) {
			log.error("createMerchant.insertMember.messageDigest.genMessageDigest() is error !");
			e.printStackTrace();
		}
		Date createDate = new Date();
		member.setCreateDate(createDate);
		member.setUpdateDate(createDate);
		return this.memberDao.insertMember(member);
	}

	private Long getNewMerchantCode(String merchantCodePrefix) {

		String acqId = merchantCodePrefix;// ACQ ID

		String newMerchantCodeTemp = acqId + System.currentTimeMillis();
		String nextValue = this.getNextValue(newMerchantCodeTemp);
		String newMerchantCode = newMerchantCodeTemp + nextValue;
		return Long.valueOf(newMerchantCode);
	}

	private String getSearchKey(String zhName) {
		SearchKeyUtils skUtils = new SearchKeyUtils();
		String searchKey = skUtils.getAllFirstLetter(zhName);
		return searchKey;
	}

	private Long getNewMerchantCode(String cityCode, String mcc,
			String riskLeveCode) {
		String acqId = Constants.NEWMERCHANT_ACQ;// ACQ ID
		// 港澳台前面补0
		String cityCodeTemp = cityCode;
		if (cityCodeTemp.length() == 3) {
			cityCodeTemp = "0" + cityCodeTemp;
		}
		// 3位的mcc前面补0
		String mccTemp = mcc;
		if (mccTemp.length() == 3) {
			mccTemp = "0" + mccTemp;
		}
		switch (Integer.parseInt(riskLeveCode)) {
		case 200:
			riskLeveCode = "5";
			break;
		case 201:
			riskLeveCode = "4";
			break;
		case 202:
			riskLeveCode = "3";
			break;
		case 203:
			riskLeveCode = "2";
			break;
		case 204:
			riskLeveCode = "1";
			break;
		}

		String newMerchantCodeTemp = acqId + cityCodeTemp + mccTemp
				+ riskLeveCode;
		String nextValue = this.getNextValue(newMerchantCodeTemp);
		String newMerchantCode = newMerchantCodeTemp + nextValue;
		return Long.valueOf(newMerchantCode);
	}

	private String getNextValue(String merchantCodeTemp) {
		List<EnterpriseBase> enterpriseBaseList = this.memberDao
				.getCurrMaxMerchantCode(merchantCodeTemp);
		String currMaxCode = "001";

		if (enterpriseBaseList.size() > 0) {
			for (int i = 0; i < enterpriseBaseList.size(); i++) {
				EnterpriseBase enterpriseBase = (EnterpriseBase) enterpriseBaseList
						.get(i);
				String currMerchantCode = enterpriseBase.getMerchantCode()
						.toString();
				String currCode = currMerchantCode.substring(
						currMerchantCode.length() - 3,
						currMerchantCode.length());
				if (Long.valueOf(currCode) > Long.valueOf(currMaxCode)) {
					currMaxCode = currCode;
				}
			}
			String nextValueString = "";
			Integer nextValueInt = Integer.valueOf(currMaxCode) + 1;
			if (nextValueInt.toString().length() == 1) {
				nextValueString = "00" + nextValueInt.toString();
			} else if (nextValueInt.toString().length() == 2) {
				nextValueString = "0" + nextValueInt.toString();
			} else {
				nextValueString = nextValueInt.toString();
			}
			return nextValueString;
		} else {
			return currMaxCode;
		}

	}

	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public void setRcLimitRuleFacade(RcLimitRuleFacade rcLimitRuleFacade) {
		this.rcLimitRuleFacade = rcLimitRuleFacade;
	}

	public void setEmailActionUri(String emailActionUri) {
		this.emailActionUri = emailActionUri;
	}

	public void setMemberDao(IMemberDao memberDao) {
		this.memberDao = memberDao;
	}

	public void setMessageDigest(IMessageDigest messageDigest) {
		this.messageDigest = messageDigest;
	}

	public void setMemberAcctTemplateService(
			MemberAcctTemplateService memberAcctTemplateService) {
		this.memberAcctTemplateService = memberAcctTemplateService;
	}

	public void setEnterpriseBaseService(
			EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}
}
