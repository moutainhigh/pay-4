/*
 * pay.com Inc.
 * Copyright (c) 2006-2011 All Rights Reserved.
 */
package com.pay.base.service.enterprise.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.app.base.api.common.enums.BspIdentityEnum;
import com.pay.app.base.api.common.enums.BspOrgEnum;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.common.enums.LoginTypeEnum;
import com.pay.base.common.enums.MemberStatusEnum;
import com.pay.base.common.enums.ServiceLevelEnum;
import com.pay.base.dao.acct.AcctDAO;
import com.pay.base.dao.enterprise.EnterpriseBaseDAO;
import com.pay.base.dao.enterprise.EnterpriseContactDAO;
import com.pay.base.dao.enterprise.EnterpriseContractDAO;
import com.pay.base.dao.member.MemberDAO;
import com.pay.base.dao.operator.OperatorDAO;
import com.pay.base.dto.EnterpriseRegisterInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.Acct;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.model.EnterpriseContact;
import com.pay.base.model.EnterpriseContract;
import com.pay.base.model.LiquidateInfo;
import com.pay.base.model.Member;
import com.pay.base.model.Operator;
import com.pay.base.service.enterprise.EnterpriseRegisterService;
import com.pay.base.service.enterprise.LiquidateInfoService;
import com.pay.base.service.operator.statusEnum.OperatorStatusEnum;
import com.pay.inf.exception.AppException;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.DateUtil;

/**
 *
 * @author zhi.wang
 * @version $Id: EnterpriseRegisterServiceImpl.java, v 0.1 2011-2-21 上午11:14:21 zhi.wang Exp $
 */
public class EnterpriseRegisterServiceImpl implements EnterpriseRegisterService {
	
	private static final Log     logger = LogFactory.getLog(EnterpriseRegisterServiceImpl.class);
	//private RiskControlService    riskService;
	private LiquidateInfoService liquidateInfoService;
	private EnterpriseContactDAO  enterpriseContactDAO;
	private EnterpriseBaseDAO     enterpriseBaseDAO;
	private EnterpriseContractDAO enterpriseContractDAO;
	/** 会员基本信息DAO */
    private MemberDAO             memberDAO;
    private OperatorDAO           operatorDAO;
    private IMessageDigest        iMessageDigest;
    private AcctDAO               acctDAO;
    
    /**
     *  初始会员信息
     *
     * @param email
     * @return
     * @throws Exception
     */
    private Member initMember(String email,String ssoId) throws Exception{
    	Member member = new Member();
    	member.setSsoUserId(ssoId);
    	// 初始默认密码
    	String paassword ="aa12345678";
        String passWord = iMessageDigest.genMessageDigest(paassword.getBytes());
        member.setLoginName(email);
        member.setLoginPwd(passWord);
        member.setSecurityQuestion(Integer.valueOf(1));
        // 初始默认答案
        String securityAnswer = "abcdef";
        String answer = iMessageDigest.genMessageDigest(securityAnswer.getBytes());
        member.setSecurityAnswer(answer);
        member.setGreeting("hello");
        // 企业会员
        member.setType(MemberTypeEnum.MERCHANT.getCode());
        member.setLoginType(LoginTypeEnum.email.getValue());
        member.setServiceLevelCode(ServiceLevelEnum.ENTERPRICE_SMALL.getValue());
        member.setType(MemberTypeEnum.MERCHANT.getCode());
        member.setStatus(MemberStatusEnum.CREATE.getCode());
        member.setServiceLevelCode(300);
        return member;
    }
    /**
     * 
     * 初始操作员信息
     * @param memberCode
     * @param email
     * @return
     * @throws Exception
     */
    private Operator initOperator(Long memberCode,String email) throws Exception{
    	Operator operator = new Operator();
    	// 初始默认密码
    	String paassword ="aa12345678";
        String passWord = iMessageDigest.genMessageDigest(paassword.getBytes());
        operator.setPassword(passWord);
    	operator.setMemberCode(memberCode);
    	operator.setIdentity("admin");
    	operator.setName("admin");
    	operator.setEmail(email);
    	operator.setDepart("1");
    	operator.setStatus(OperatorStatusEnum.NORMAL.getValue());
    	return operator;
    }
    private EnterpriseBase initEnterpriseBase(Long memberCode,EnterpriseRegisterInfo info){
    	EnterpriseBase base = new EnterpriseBase();
    	base.setMemberCode(memberCode);
    	
    	base.setMerchantCode(getNewMerchantCode("6410", "1234", "3"));
    	
    	base.setZhName(info.getZhName());
    	base.setEnterpriseType(Integer.valueOf(1));
    	base.setAudiStatus(Integer.valueOf(2));//初始化为未审核状态
    	base.setWebsite("http://www.test.com");
    	base.setNation("1");
    	base.setRegion("6400");
    	base.setCity("6410");//
    	base.setInIndustry("1234");
        base.setExpire(this.getDate());
    	base.setBizLicenceCode(info.getBizLicenceCode());
    	base.setGovCode(info.getGovCode());
    	base.setRiskLeveCode("202");//默认是3级风险，是202
    	base.setTaxCode(info.getTaxCode());
    	base.setSearchKey("searchT");
    	base.setIdentity(BspIdentityEnum.CORP_TRADING.getIdentity());
    	base.setEnterpriseType(BspOrgEnum.CORP_GG.getOrg());
    	base.setEnName(info.getZhName());//默认是用zhname
    	return base;
    }
    private Date getDate(){
    	String strDateTime = "2099/12/31";
    	String pattern = "yyyy/MM/dd";
    	Date date = new Date();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            formatter.setLenient(false);
            date = formatter.parse(strDateTime);
        }
        catch (ParseException e) {
        	
        }
        return date;
    }
    private EnterpriseContact initEnterpriseContact(Long memberCode,EnterpriseRegisterInfo info){
    	EnterpriseContact contact = new EnterpriseContact();
    	contact.setMemberCode(memberCode);
    	contact.setAddress(info.getCorpAddress());
    	contact.setFax("00000000");
    	contact.setTel("00000000");
    	contact.setZip("000000");
    	contact.setLegalName(info.getLegalName());
    	contact.setLegalLink("00000000");
    	String email = info.getEmail();
    	// 数据库长度32位
    	contact.setEmail(email);
    	contact.setFinanceName("000");
    	contact.setFinanceLink("00000000");
    	contact.setTechName("000");
    	contact.setTechLink("00000000");
    	contact.setCompayLinkerTel(info.getCompayLinkerTel());
    	contact.setCompayLinkerName(info.getCompayLinkerName());
    	return contact;
    }
    
    private EnterpriseContract initEnterpriseContract(Long memberCode,EnterpriseRegisterInfo info){
    	EnterpriseContract contract = new EnterpriseContract();
    	contract.setMemberCode(memberCode);
    	contract.setContractCode("未知");
    	contract.setSignName("无");
    	contract.setSignDepart("1");
    	contract.setContinueSign(Integer.valueOf(1));
    	contract.setContractStatus(Integer.valueOf(1));
    	contract.setMarketName("未知");
    	contract.setMarketLink("未知");
    	String parse = "yyyyMMdd";
    	// 合同开始日期
    	contract.setStartDate(DateUtil.parse(parse, info.getContractStartDate()));
    	// 合同结束日期
    	contract.setEndDate(DateUtil.parse(parse, info.getContractStartDate()));
    	contract.setFactStartDate(DateUtil.parse(parse, info.getContractFactStartDate()));
    	contract.setFactEndDate(DateUtil.parse(parse, info.getContractFactEndDate()));
    	
    	contract.setFactOpenFee(0l);
    	contract.setFactYearFee(0l);
    	contract.setAssureFee(0l);
    	contract.setFactAssureFee(0l);
    	contract.setAssureDesc("无");
    	return contract;
    }
    
    /**
     *  获取MerchantCode
     * acqId=515
     *
     * @param cityCode 城市code
     * @param mcc 行业代码
     * @param riskLeveCode
     * @return
     */
    private Long getNewMerchantCode(String cityCode ,String mcc,String riskLeveCode){
		String acqId = "515";//ACQ ID
		//港澳台前面补0
		String cityCodeTemp = cityCode;
		if(cityCodeTemp.length()==3){
			cityCodeTemp = "0"+cityCodeTemp;
		}
		//3位的mcc前面补0
		String mccTemp = mcc;
		if(mccTemp.length()==3){
			mccTemp = "0"+mccTemp;
		}
		String newMerchantCodeTemp = acqId + cityCodeTemp + mccTemp + riskLeveCode;
		String nextValue = this.getNextValue(newMerchantCodeTemp);
		String newMerchantCode =newMerchantCodeTemp + nextValue;
	
		return Long.valueOf(newMerchantCode);
	}
    
    private String getNextValue(String merchantCodeTemp){
		List<EnterpriseBase> enterpriseBaseList =enterpriseBaseDAO.queryCurrMaxMerchantCode(merchantCodeTemp);
		String currMaxCode ="001";
		
		if(enterpriseBaseList.size()>0){
			for(int i = 0;i<enterpriseBaseList.size();i++){
				EnterpriseBase enterpriseBase =(EnterpriseBase)enterpriseBaseList.get(i);
				String currMerchantCode = String.valueOf(enterpriseBase.getMerchantCode());
				String currCode = currMerchantCode.substring(currMerchantCode.length()-3,currMerchantCode.length());
				if(Long.valueOf(currCode)>Long.valueOf(currMaxCode) ){
					currMaxCode = currCode;
				}
			}
			String nextValueString = "";
			Integer nextValueInt = Integer.valueOf(currMaxCode)+1;
			if(nextValueInt.toString().length()==1){
				nextValueString = "00" + nextValueInt.toString();
			}else if(nextValueInt.toString().length()==2){
				nextValueString = "0" + nextValueInt.toString();
			}else{
				nextValueString = nextValueInt.toString();
			}
			return nextValueString;
		}else{
			return currMaxCode;
		}
								
						
	}

	/** 
	 * @param info
	 * @return
	 * @throws AppException
	 * @see com.pay.base.service.enterprise.EnterpriseRegisterService#doRegisterBaseInfoRdTx(com.pay.base.dto.EnterpriseRegisterInfo)
	 */
	@Override
	public ResultDto doRegisterBaseInfo(EnterpriseRegisterInfo info )
			throws AppException {
		ResultDto resultDto = new ResultDto();
		
		Member m = memberDAO.findMemberByLoginName(info.getEmail());
		
		if(m!=null){
			resultDto.setResultStatus(false);
			resultDto.setErrorMsg(ErrorCodeEnum.MEMBER_EXIST_ERROR.getMessage());
			resultDto.setErrorCode(ErrorCodeEnum.MEMBER_EXIST_ERROR.getErrorCode());
			resultDto.setErrorNum(ErrorCodeEnum.MEMBER_EXIST_ERROR);
			resultDto.setMemberCode(String.valueOf(m.getMemberCode()));
			return resultDto;
		}
		
		// step5、设置风控系数
		try {
			//step1、 生成t_member 表数据
			Member member = this.initMember(info.getEmail(),info.getSsoUserId());
			// 创建企业会员
            Long memberCode = memberDAO.createCorpMember(member);
            // step2、生成t_acct 和 t_acct_attrib表数据
            if (memberCode != null) {
                Long acctAttribId = acctDAO.createAcctAttrib(memberCode);
                String acctCode = acctDAO.getAccCodeById(acctAttribId);
                Acct acct = new Acct();
                acct.setAcctCode(acctCode);
                acct.setMemberCode(memberCode);
                acctDAO.createAcct(acct);
                // step3、 生成t_operator表数据
                Operator operator = this.initOperator(memberCode,info.getEmail());
                operatorDAO.createOperator(operator);
                // step4、 生成t_enterprise_contact 和 t_enterprise_base,t_enterprise_contract表数据
                EnterpriseBase enterpriseBase = this.initEnterpriseBase(memberCode, info);
                enterpriseBaseDAO.createBaseInfo(enterpriseBase);
                
                EnterpriseContact enterpriseContact = this.initEnterpriseContact(memberCode, info);
                enterpriseContactDAO.createContact(enterpriseContact);
                
                //
                EnterpriseContract contract = this.initEnterpriseContract(memberCode,info);
                enterpriseContractDAO.createContract(contract);
                
               //riskService.setDefaultMccLimit(memberCode,Integer.valueOf(enterpriseBase.getInIndustry()),Integer.valueOf(enterpriseBase.getRiskLeveCode()));

                resultDto.setMemberCode(memberCode.toString());
            }

        } catch (Exception e) {
            resultDto.setErrorNum(ErrorCodeEnum.UNKNOW_ERROR);
            logger.error("EnterpriseRegisterServiceImpl doRegisterBaseInfo is Error!Register email="+info.getEmail(),
                e);
            throw new AppException(e);
        }
		return resultDto;
	}
	
	@Override
	public ResultDto doRegisterRdTx(EnterpriseRegisterInfo info,
			LiquidateInfo liquidateInfo) throws AppException {
		
		ResultDto rd = this.doRegisterBaseInfo(info);
		
		if(rd.getErrorCode()!=null){
			return rd;
		}
		try{
			liquidateInfo.setMemberCode(Long.parseLong(rd.getMemberCode()));
			rd = new ResultDto();
			rd.setResultStatus(liquidateInfoService.saveOrUpdate(liquidateInfo));
			rd.setMemberCode(liquidateInfo.getMemberCode()+"");
		}catch(Exception e){
			logger.error("EnterpriseRegisterServiceImpl doRegisterRdTx is Error!Register email="+info.getEmail(),
	                e);
			throw new AppException(e);
		}
		
		if(!rd.isResultStatus()){
			throw new AppException();
		}
		
		return rd;
	}
	
	public int checkUnique(EnterpriseRegisterInfo info){
		// 0 : 检查通过
		if(enterpriseBaseDAO.checkByBizLicenceCode(info.getBizLicenceCode())){
			return  1;
		}
		if(enterpriseBaseDAO.checkByGovCode(info.getGovCode())){
			return  2;
		}
		if(enterpriseBaseDAO.checkByTaxCode(info.getTaxCode())){
			return 3;
		}
		return 0;
	}
	
	@Override
	public EnterpriseContract getContractByMemberCode(Long memberCode){
		EnterpriseContract ec=null;
		List<EnterpriseContract> contractEnterpriseList=enterpriseContractDAO.getContractByMemberCode(memberCode);
		if(contractEnterpriseList!=null && contractEnterpriseList.size()>0){
			ec=(EnterpriseContract)contractEnterpriseList.get(0);
		}
		return ec;
	} 
	

	public void setEnterpriseContactDAO(EnterpriseContactDAO enterpriseContactDAO) {
		this.enterpriseContactDAO = enterpriseContactDAO;
	}
	public void setEnterpriseBaseDAO(EnterpriseBaseDAO enterpriseBaseDAO) {
		this.enterpriseBaseDAO = enterpriseBaseDAO;
	}
	public void setEnterpriseContractDAO(EnterpriseContractDAO enterpriseContractDAO) {
		this.enterpriseContractDAO = enterpriseContractDAO;
	}
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	public void setOperatorDAO(OperatorDAO operatorDAO) {
		this.operatorDAO = operatorDAO;
	}
	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}
	public void setAcctDAO(AcctDAO acctDAO) {
		this.acctDAO = acctDAO;
	}
	public void setLiquidateInfoService(
			LiquidateInfoService liquidateInfoService) {
		this.liquidateInfoService = liquidateInfoService;
	}

}
