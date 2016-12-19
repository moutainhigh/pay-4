/**
 * 
 */
package com.pay.acc.member.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.comm.Constants;
import com.pay.acc.comm.SearchKeyUtils;
import com.pay.acc.exception.MaMemberException;
import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.dto.EnterpriseContactDto;
import com.pay.acc.member.dto.EnterpriseContractDto;
import com.pay.acc.member.dto.IndividualInfoDto;
import com.pay.acc.member.dto.LiquidateInfoDto;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.dto.MemberIdentityDto;
import com.pay.acc.member.dto.MemberInfoDto;
import com.pay.acc.member.dto.OperatorDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acc.member.service.IndividualInfoService;
import com.pay.acc.member.service.LiquidateInfoService;
import com.pay.acc.member.service.MemberIdentityService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.member.service.RegisterService;
import com.pay.acc.memberaccttemplate.dto.MemberAcctTemplateDto;
import com.pay.acc.memberaccttemplate.service.MemberAcctTemplateService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.DESUtil;
import com.pay.util.IdentityUtil;

/**
 * @author chaoyue
 *
 */
public class RegisterServiceImpl implements RegisterService {

	private final Log logger = LogFactory.getLog(getClass());

	private MemberService memberService;
	private IndividualInfoService individualInfoService;
	private MemberAcctTemplateService memberAcctTemplateService;
	private AcctAttribService acctAttribService;
	private AcctService acctService;
	private IMessageDigest iMessageDigest;
	private MemberIdentityService memberIdentityService;
	private EnterpriseBaseService enterpriseBaseService;
	private LiquidateInfoService liquidateInfoService;

	@Override
	public Long registerRdTx(MemberInfoDto memberInfoDto) throws Exception {

		// 判断会员是否存在
		String loginName = memberInfoDto.getLoginName();

		MemberDto memberDto = memberService.queryMemberByLoginName(loginName);
		if (null != memberDto) {
			logger.error("会员已存在!");
			throw new MaMemberException(ErrorExceptionEnum.MEMBER_EXIST_ERROR,
					"会员已存在！");
		}

		// 创建会员
		Long memberCode = constructMember(memberInfoDto);
		logger.info("create member:" + memberCode);

		// 保存登录标识
		//constructMemberIdentity(loginName, memberCode);

		// 获取创建会员类型
		Integer memberType = memberInfoDto.getType();
		// 创建基本信息
		if (memberType == MemberTypeEnum.INDIVIDUL.getCode()) {
			IndividualInfoDto individualInfo = new IndividualInfoDto();
			BeanUtils.copyProperties(memberInfoDto, individualInfo);
			individualInfo.setMemberCode(memberCode);
			individualInfoService.createIndividualInfo(individualInfo);
		} else {
			// 企业信息
			String merchantCodePrefix = memberInfoDto.getMerchantCodePrefix();
			Long merchantCode = this.getNewMerchantCode(merchantCodePrefix);
			insertEnterpriseBase(this.getSearchKey(memberInfoDto.getZhName()),
					merchantCode, memberCode, memberInfoDto);
			// 企业联系方式
			this.insertEnterpriseContact(memberCode, memberInfoDto);
			// 企业合同信息
			this.insertEnterpriseContract(memberCode, memberInfoDto);
			this.insertLiquidateInfo(memberCode, memberInfoDto);
			this.insertOperator(memberCode, memberInfoDto);
		}

		// 创建账户对象
		MemberAcctTemplateDto memberAcctTemplateDto = memberAcctTemplateService
				.queryByMemberTypeAndAcctType(memberType,
						AcctTypeEnum.BASIC_CNY.getCode());

		Assert.notNull(memberAcctTemplateDto,
				"memberAcctTemplateDto must be not null");

		StringBuilder memberAcctCode = new StringBuilder();
		memberAcctCode.append(memberCode);
		memberAcctCode.append(memberAcctTemplateDto.getAcctType());

		AcctAttribDto acctAttrib = new AcctAttribDto();
		BeanUtils.copyProperties(memberAcctTemplateDto, acctAttrib);
		String acctCode = memberAcctTemplateDto.getSubjectCode() + memberCode
				+ memberAcctTemplateDto.getAcctType();
		acctAttrib.setAcctCode(acctCode);
		acctAttrib.setMemberCode(memberCode);
		acctAttrib.setCurCode(memberAcctTemplateDto.getCurCode());
		acctAttrib.setMemberAcctCode(Long.valueOf(memberAcctCode.toString()));
		acctAttrib.setCreateDate(new Date());
		acctAttrib.setUpdateDate(new Date());
		acctAttrib.setDefRecAcct(1);//注册时默认生成的人民币基本账户，设为默认收款账户  2016年4月28日11:56:45 delin.dong
		acctAttribService.createAcctAttrib(acctAttrib);

		// 创建余额对象
		AcctDto acct = new AcctDto();
		acct.setAcctCode(acctCode);
		acct.setMemberCode(memberCode);
		acct.setCreateDate(new Date());
		acct.setUpdateDate(new Date());
		acctService.createAcct(acct);
		return memberCode;
	}

	private void constructMemberIdentity(String loginName, Long memberCode) {
		MemberIdentityDto memberIdentityDto = new MemberIdentityDto();
		memberIdentityDto.setCreateDate(new Date());
		memberIdentityDto.setIdContent(loginName);
		int idType = IdentityUtil.getIDType(loginName);
		memberIdentityDto.setIdType(idType);
		memberIdentityDto.setIsPrimaryId(1);
		memberIdentityDto.setMemberCode(memberCode);
		memberIdentityDto.setStatus(1);
		memberIdentityService.createMemberIdentity(memberIdentityDto);
	}

	private Long constructMember(MemberInfoDto memberInfoDto) throws Exception {
		MemberDto member = new MemberDto();
		BeanUtils.copyProperties(memberInfoDto, member);
		member.setCreateDate(new Date());
		member.setStatus(0);
		member.setUpdateDate(new Date());
		member.setServiceLevelCode(100);
		member.setLoginPwd(iMessageDigest.genMessageDigest(memberInfoDto
				.getLoginPwd().getBytes()));
		Long memberCode = memberService.createMember(member);
		return memberCode;
	}

	private Long getNewMerchantCode(String merchantCodePrefix) {

		String acqId = merchantCodePrefix;// ACQ ID

		String newMerchantCodeTemp = acqId + System.currentTimeMillis();
		String nextValue = this.getNextValue(newMerchantCodeTemp);
		String newMerchantCode = newMerchantCodeTemp + nextValue;
		return Long.valueOf(newMerchantCode);
	}

	private String getNextValue(String merchantCodeTemp) {

		List<EnterpriseBaseDto> enterpriseBaseList = enterpriseBaseService
				.getCurrMaxMerchantCode(merchantCodeTemp);
		String currMaxCode = "001";

		if (null != enterpriseBaseList && enterpriseBaseList.size() > 0) {
			for (int i = 0; i < enterpriseBaseList.size(); i++) {
				EnterpriseBaseDto enterpriseBase = (EnterpriseBaseDto) enterpriseBaseList
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

	private void insertEnterpriseBase(String searchKey, Long merchantCode,
			Long memberCode, MemberInfoDto memberInfoDto) {
		EnterpriseBaseDto enterpriseBase = new EnterpriseBaseDto();

		enterpriseBase.setMemberCode(memberCode);
		enterpriseBase.setMerchantCode(merchantCode);// 根据规则生成
		enterpriseBase.setEnterpriseType(Integer.valueOf(memberInfoDto
				.getEnterpriseType()));
		enterpriseBase.setAudiStatus(Constants.CHECK_READYCHECK);
		enterpriseBase.setZhName(memberInfoDto.getZhName());
		enterpriseBase.setSearchKey(searchKey);// 中文名拼音首字母（查询用）
		enterpriseBase.setEnName(memberInfoDto.getEnName());
		enterpriseBase.setWebsite(memberInfoDto.getWebsite());
		enterpriseBase.setNation(memberInfoDto.getNation());
		enterpriseBase.setRegion(memberInfoDto.getRegion());
		enterpriseBase.setCity(memberInfoDto.getCity());
		enterpriseBase.setIndustryKind(Constants.UNKNOW);// 行业门类
		enterpriseBase.setIndustryType(Constants.UNKNOW);// 行业大类
		enterpriseBase.setIndustry(memberInfoDto.getIndustry());

		Date createDate = new Date();
		enterpriseBase.setCreateDate(createDate);
		enterpriseBase.setUpdateDate(createDate);

		this.enterpriseBaseService.insertEnterpriseBase(enterpriseBase);
	}

	private void insertOperator(Long memberCode, MemberInfoDto memberInfoDto) {
		OperatorDto op = new OperatorDto();

		op.setIdentity(Constants.OPLOGINNAME);// 操作员登录标识
		op.setMemberCode(memberCode);// 会员号
		op.setName(Constants.OPNAME);// 操作员姓名
		op.setStatus(Constants.OPSTATUS);// 操作员状态0创建，1正常，2冻结，3删除
		op.setEmail(Constants.UNKNOW); // email
		try {
			op.setLoginPwd(iMessageDigest.genMessageDigest(memberInfoDto
					.getLoginPwd().getBytes()));// 登录密码
		} catch (Exception e) {
			logger.error("createMerchant.insertOperator.messageDigest.genMessageDigest() is error !");
			e.printStackTrace();
		}
		Date date = new Date();
		op.setCreateDate(date);// 数据创建时间
		op.setUpdateDate(date);// 数据更新时间
		op.setDepart(Constants.UNKNOW);// 操作员部门
		// op.setCertType(Constants.OPCERTTYPE);// 身份证
		enterpriseBaseService.insertOperator(op);
	}

	private void insertLiquidateInfo(Long memberCode,
			MemberInfoDto memberInfoDto) {
		LiquidateInfoDto liquidateInfo = new LiquidateInfoDto();
		liquidateInfo.setMemberCode(memberCode);
		liquidateInfo.setBankName(memberInfoDto.getBankName());
		liquidateInfo.setBankAcct(DESUtil.encrypt(memberInfoDto.getBankAcct()));// 商户结算银行账户
		liquidateInfo.setAcctName(memberInfoDto.getAcctName());// 商户结算账户名称
		liquidateInfo.setProvince(Long.valueOf(memberInfoDto.getRegionBank()));
		liquidateInfo.setCity(Long.valueOf(memberInfoDto.getCityBank()));
		liquidateInfo.setBankId(memberInfoDto.getBankId());
		// liquidateInfo.setBankAddress(memberInfoDto.getBankAddress());
		Date createDate = new Date();
		liquidateInfo.setCreateDate(createDate);
		liquidateInfo.setUpdateDate(createDate);
		liquidateInfo.setBigBankName(memberInfoDto.getBigBankName());
		liquidateInfo.setBranchBankId(Long.valueOf(memberInfoDto
				.getBranchBankId()));
		liquidateInfoService.insertLiquidateInfo(liquidateInfo);
	}

	private void insertEnterpriseContract(Long memberCode,
			MemberInfoDto memberInfoDto) {
		EnterpriseContractDto enterpriseContract = new EnterpriseContractDto();

		enterpriseContract.setMemberCode(memberCode);
		enterpriseContract.setContractCode(Constants.UNKNOW);
		// enterpriseContract.setSignName(memberInfoDto.getSignName());
		// 2014/5/12
		// enterpriseContract.setSignLoginId(memberDto.getSignLoginId());

		// enterpriseContract.setSignDepart(memberDto.getSignDepart());
		// enterpriseContract.setContinueSign(Integer.valueOf(memberInfoDto.getContinueSign()));
		enterpriseContract.setContractStatus(Constants.CONTRACTSTATUS);
		enterpriseContract.setMarketName(Constants.UNKNOW);
		// enterpriseContract.setMarketLink(StringUtil.isEmpty(memberInfoDto.getMarketLink())
		// ? "" : memberInfoDto.getMarketLink());

		Date createDate = new Date();
		enterpriseContract.setCreateDate(createDate);
		enterpriseContract.setUpdateDate(createDate);
		enterpriseBaseService.insertEnterpriseContract(enterpriseContract);
	}

	private void insertEnterpriseContact(Long memberCode,
			MemberInfoDto memberInfoDto) {
		EnterpriseContactDto enterpriseContact = new EnterpriseContactDto();

		enterpriseContact.setMemberCode(memberCode);
		enterpriseContact.setAddress(memberInfoDto.getAddress());
		enterpriseContact.setFax(memberInfoDto.getFax());
		enterpriseContact.setTel(memberInfoDto.getTel());
		enterpriseContact.setZip(memberInfoDto.getZip());
		enterpriseContact.setLegalName(memberInfoDto.getLegalName());
		enterpriseContact.setLegalLink(memberInfoDto.getLegalLink());
		enterpriseContact.setEmail(memberInfoDto.getEmail().toLowerCase());// 一律改成小写的email
		Date createDate = new Date();
		enterpriseContact.setCreateDate(createDate);
		enterpriseContact.setUpdateDate(createDate);
		enterpriseBaseService.insertEnterpriseContact(enterpriseContact);
	}

	private String getSearchKey(String zhName) {
		SearchKeyUtils skUtils = new SearchKeyUtils();
		String searchKey = skUtils.getAllFirstLetter(zhName);
		return searchKey;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setIndividualInfoService(
			IndividualInfoService individualInfoService) {
		this.individualInfoService = individualInfoService;
	}

	public void setMemberAcctTemplateService(
			MemberAcctTemplateService memberAcctTemplateService) {
		this.memberAcctTemplateService = memberAcctTemplateService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setMemberIdentityService(
			MemberIdentityService memberIdentityService) {
		this.memberIdentityService = memberIdentityService;
	}

	public void setEnterpriseBaseService(
			EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setLiquidateInfoService(
			LiquidateInfoService liquidateInfoService) {
		this.liquidateInfoService = liquidateInfoService;
	}
}
