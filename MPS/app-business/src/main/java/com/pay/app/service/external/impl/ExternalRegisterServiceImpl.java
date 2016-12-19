package com.pay.app.service.external.impl;

import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.app.common.enums.BspCommEnum;
import com.pay.app.dto.ExternalRegisterDto;
import com.pay.app.dto.ExternalRegisterResultDto;
import com.pay.app.service.external.ExternalRegisterService;
import com.pay.app.service.external.MessageSignatureVerify;
import com.pay.base.dto.EnterpriseRegisterInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.LiquidateInfo;
import com.pay.base.service.enterprise.EnterpriseRegisterService;
import com.pay.base.service.enterprise.LiquidateInfoService;
import com.pay.base.service.member.MemberRelationService;
import com.pay.base.service.member.MemberService;
import com.pay.inf.service.BankService;
import com.pay.util.BeanUtil;
import com.pay.util.DESUtil;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

public class ExternalRegisterServiceImpl implements ExternalRegisterService {
	
	private final Log log = LogFactory.getLog(this.getClass().getName());
	private EnterpriseRegisterService enterpriseRegisterService;
	private MemberService memberService;
	private MemberRelationService	memberRelationService;
	private LiquidateInfoService liquidateInfoService;
	private MessageSignatureVerify messageSignatureVerify;
	private BankService bankService;
	private Pattern NUM_OR_ABC = Pattern.compile("^[0-9a-zA-Z]*$");
	private Pattern NUMBER = Pattern.compile("^[0-9]*$");
	
	/**
	 * @param bankService the bankService to set
	 */
	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	/**
	 * @param messageSignatureVerify the messageSignatureVerify to set
	 */
	public void setMessageSignatureVerify(
			MessageSignatureVerify messageSignatureVerify) {
		this.messageSignatureVerify = messageSignatureVerify;
	}

	/**
	 * @param liquidateInfoService the liquidateInfoService to set
	 */
	public void setLiquidateInfoService(LiquidateInfoService liquidateInfoService) {
		this.liquidateInfoService = liquidateInfoService;
	}

	public void setEnterpriseRegisterService(
			EnterpriseRegisterService enterpriseRegisterService) {
		this.enterpriseRegisterService = enterpriseRegisterService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * @param memberRelationService the memberRelationService to set
	 */
	public void setMemberRelationService(MemberRelationService memberRelationService) {
		this.memberRelationService = memberRelationService;
	}

	@Override
	public ExternalRegisterResultDto processRegisterRdTx(
			ExternalRegisterDto inputDto) {
		BspCommEnum error = validateExternalRegisterDto(inputDto);
		ExternalRegisterResultDto resultDto = new ExternalRegisterResultDto();
		if (error != null) {
			resultDto.setErrorCode(error.getCode()+"");
			resultDto.setErrorMsg(error.getMessage());
			resultDto.setResultFlag(false);
			log.info("验证数据有异常，提交不通过");
			try {
				String sign  = signMsgResult(resultDto, inputDto.getOriginCode());
				resultDto.setSignMsg(sign);
			} catch (Exception e) {
				e.printStackTrace();
				log.error("数据签名时异常");
			}
			return resultDto;
		}
		String bankCode =   bankService.getWithdrawOrgCodeByName(inputDto.getBankName().trim());
		//处理注册
		LiquidateInfo li = new LiquidateInfo();
		li.setBankAcct(DESUtil.encrypt(inputDto.getBankAcct()));
		li.setBankName(inputDto.getOaBankName());
		li.setCity(6410); //海口代码
		li.setProvince(6400);//海南
		li.setAcctName(inputDto.getCorpName());
		li.setAccountMode(2);
		li.setBankId(bankCode);
		li.setBankAddress("海口");
		li.setBigBankName(inputDto.getBankName());
		
		EnterpriseRegisterInfo info = new EnterpriseRegisterInfo();
		info.setBizLicenceCode(inputDto.getBizLicenceCode());
		info.setCompayLinkerName(inputDto.getCorpLinkerName());
		info.setCompayLinkerTel(inputDto.getCorpLinkerTel());
		info.setEmail(inputDto.getCorpEmail());
		info.setGovCode(inputDto.getGovCode());
		info.setTaxCode(inputDto.getTaxCode());
		info.setZhName(inputDto.getCorpName());
		info.setLegalName(inputDto.getLegalName());
		info.setTel(inputDto.getCorpLinkerTel());//是否可以
		info.setSsoUserId(inputDto.getBspId());
		info.setContractStartDate(inputDto.getOpenAccountDate());
		String[] dateStartEnd =  inputDto.getContractValidity().split("-");
		info.setContractEndDate(dateStartEnd[1]);
		info.setContractFactStartDate(dateStartEnd[0]);
		info.setContractFactEndDate(dateStartEnd[1]);
		info.setCorpAddress(inputDto.getCorpAddr());
		try {
			ResultDto rd = enterpriseRegisterService.doRegisterBaseInfo(info);
			Long currMemberCode = Long.valueOf(rd.getMemberCode());
			li.setMemberCode(currMemberCode);
			liquidateInfoService.saveOrUpdate(li);
			Long fatherMemberCode = Long.valueOf(inputDto.getOriginCode());
			Long usteelId = Long.valueOf(inputDto.getBspId());
			String usteelName = inputDto.getUsteelName();
			memberRelationService.createMemberRelation(currMemberCode, usteelId, usteelName, fatherMemberCode);
			resultDto.setMemberCode(currMemberCode+"");
			resultDto.setResultFlag(true);
			log.info("数据创建成功，memberCode:"+currMemberCode);
		} catch (Exception e) {
			e.printStackTrace();
			resultDto.setResultFlag(false);
		}
		String sign = null;
		try {
			sign = signMsgResult(resultDto, inputDto.getOriginCode());
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("数据签名时异常");
		}
		resultDto.setSignMsg(sign);
		return resultDto;
	}

	private BspCommEnum validateExternalRegisterDto(
			ExternalRegisterDto inputDto) {
		//验签
		try {
			//要的加密串
			String propertiesUrl = BeanUtil.beanValue2string(inputDto, 
					new String[]{
					"originCode",
					 "corpName", 
					 "corpAddr", 
					 "bizLicenceCode", 
					 "taxCode",
					 "govCode",
					 "corpEmail",
					 "bspId", 
					 "usteelName",
					 "legalName",
					 "corpLinkerName",
					 "corpLinkerTel",
					 "bankName",
					 "oaBankName",
					 "bankAcct",
					"openAccountDate",
					"contractValidity"});
			log.info("加密串是："+propertiesUrl);
			log.info("加密值："+messageSignatureVerify.SignatureByMmerchant(propertiesUrl,inputDto.getOriginCode()));
			//验证签名
			if(! messageSignatureVerify.verifyByMerchant(propertiesUrl, inputDto.getOriginCode(), inputDto.getSignMsg())){
				return BspCommEnum.SignatureVerifyFailure;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("验证签名是有异常",e);
		}
		
		if (StringUtil.isEmpty(inputDto.getBspId())) {
			return BspCommEnum.BSP_ID_ERROR;
		}
		if (!checkStringLength(inputDto.getCorpName(), 4, 32)) {
			return BspCommEnum.CORP_NAME_LENGTH_ERROR;
		}
		if (!checkStringLength(inputDto.getLegalName(), 2, 16)) {
			return BspCommEnum.LEGAL_NAME_LENGTH_ERROR;
		}
		if (!checkStringLength(inputDto.getCorpLinkerName(), 2, 32)) {
			return BspCommEnum.CORP_LINKER_NAME_ERROR;
		}
		if (StringUtil.isEmpty(inputDto.getBankName())) {
			return BspCommEnum.BANK_NAME_ERROR;
		}
		inputDto.setBankName(inputDto.getBankName().trim());
		if (StringUtil.isEmpty(inputDto.getOaBankName())) {
			return BspCommEnum.OA_BANK_NAME_ERROR;
		}
		if (!checkStringLength(inputDto.getBankAcct(), 8, 32)) {
			return BspCommEnum.BANK_ACCT_ERROR;
		}
		String bankCode = bankService.getWithdrawOrgCodeByName(inputDto.getBankName().trim());
		if(bankCode == null){
			return BspCommEnum.BANK_NAME_NOT_SUPPORT;
		}

		// 验证企业营业执照号码
		if (!checkStringLength(inputDto.getBizLicenceCode(), 13, 15)
				|| !isNumber(inputDto.getBizLicenceCode())) {
			return BspCommEnum.BIZ_LICENCE_CODE_ERROR;
		}

		// 验证企业营业执照号码
		if ((!StringUtil.isEmpty(inputDto.getTaxCode())) && !checkStringLength(inputDto.getTaxCode(), 13, 18)
				|| !isNumOrABC(inputDto.getTaxCode())) {
			return BspCommEnum.TAX_CODE_ERROR;
		}
		
		if(   inputDto.getOpenAccountDate()==null ||  (DateUtil.parse("yyyyMMdd", inputDto.getOpenAccountDate())==null)){
			
				return BspCommEnum.OPEN_ACCOUNT_DATE_ERROR;
		}
		
		if(   inputDto.getContractValidity()==null ||  ! inputDto.getContractValidity().matches("^\\d{8}-\\d{8}$") ){
			return BspCommEnum.CONTRACT_VALIDITY_ERROR;
		}else {
			String[] vaiditys =  inputDto.getContractValidity().split("-");
			if((DateUtil.parse("yyyyMMdd", vaiditys[0])==null) || (DateUtil.parse("yyyyMMdd", vaiditys[1])==null)){
				return BspCommEnum.CONTRACT_VALIDITY_ERROR;
			}
		}
		EnterpriseRegisterInfo infoValid = new EnterpriseRegisterInfo();
		infoValid.setBizLicenceCode(inputDto.getBizLicenceCode());
		infoValid.setTaxCode(inputDto.getTaxCode());
		infoValid.setGovCode(inputDto.getGovCode());
		int result = enterpriseRegisterService.checkUnique(infoValid);
		if (result > 0) {
			if (result == 1) {
				return BspCommEnum.BIZ_LICENCE_CODE_EXISTED;
			} else if (result == 2) {
				return BspCommEnum.GOV_CODE_EXISTED;
			} else {
				return BspCommEnum.TAX_CODE_ERROR;
			}
		}
		if (! memberService.checkLoginNameByRegister(inputDto.getCorpEmail(),
				Integer.valueOf(2))) {// 校验用户名是否唯一
			return BspCommEnum.EMAIL_EXISTED;
		}
		//判断公司名称是否唯一
		if(memberRelationService.isExistsSonZhName(inputDto.getCorpName())){
			return BspCommEnum.CORP_NAME_EXISTED;
		}
		//判断商户中心商户号是否存在
		if(! memberRelationService.isExistsOriginCode(Long.valueOf(inputDto.getOriginCode()))){
			return BspCommEnum.ORIGIN_CODE_NOT_EXISTS;
		}
		
		

		return null;
	}

	private boolean checkStringLength(String str, int min, int max) {
		if (StringUtils.isBlank(str)) {
			return false;
		}
		return (str.length() <= max && str.length() >= min);
	}

	private boolean isNumber(String str) {
		return NUMBER.matcher(str).matches();
	}

	private boolean isNumOrABC(String str) {
		return NUM_OR_ABC.matcher(str).matches();
	}
	private  String signMsgResult(ExternalRegisterResultDto resultDto,String merchantCode) throws Exception{
//		private String errorCode; 	//		注册失败错误代码
//		private String errorMsg	;	//注册失败错误信息
//		private Boolean resultFlag;	//	是否注册成功
//		private String memberCode;
		String url 
			= BeanUtil.beanValue2string(resultDto, new String[]{"errorCode","errorMsg","resultFlag","memberCode"});
		return messageSignatureVerify.SignatureByMmerchant(url, merchantCode);
	}
}
