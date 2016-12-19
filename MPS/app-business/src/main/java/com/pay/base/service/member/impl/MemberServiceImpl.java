/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.base.service.member.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.app.common.helper.AppConf;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.base.common.enums.AcctStatusEnum;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.common.enums.MemberStatusEnum;
import com.pay.base.common.enums.ServiceLevelEnum;
import com.pay.base.dao.acct.AcctDAO;
import com.pay.base.dao.member.MemberDAO;
import com.pay.base.dao.operator.OperatorDAO;
import com.pay.base.dao.operator.OperatorMenuDAO;
import com.pay.base.dto.IndividualInfoDto;
import com.pay.base.dto.MemberBalancesDto;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.MemberDto;
import com.pay.base.dto.MemberInfoDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.Acct;
import com.pay.base.model.Member;
import com.pay.base.model.Operator;
import com.pay.base.model.OperatorMenu;
import com.pay.base.model.PowersModel;
import com.pay.base.service.acctatrrib.AcctAttribService;
import com.pay.base.service.featuremenu.MemberFeatureService;
import com.pay.base.service.individual.IndividualInfoService;
import com.pay.base.service.member.MemberService;
import com.pay.inf.exception.AppException;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.BeanConvertUtil;
import com.pay.util.CheckUtil;
import com.pay.util.DESUtil;
import com.pay.util.FormatDate;
import com.pay.util.StringUtil;
import com.pay.util.ValidateUtils;

/**
 * 会员基本信息服务
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-19 上午12:18:23
 * 
 */
public class MemberServiceImpl implements MemberService {

    private static final Log      logger = LogFactory.getLog(MemberServiceImpl.class);

    /** 会员基本信息DAO */
    private MemberDAO             memberDAO;

	private CheckCodeService checkCodeService;

    private OperatorDAO           operatorDAO;

    private IMessageDigest        iMessageDigest;
    /** 授权操作员菜单 */
    private MemberFeatureService  memberFeatureService;
    /** 操作员菜单权限 */
    private OperatorMenuDAO       operatorMenuDAO;

    private AcctDAO               acctDAO;
    
    private AcctAttribService 	  acctAttribService;
    
    private IndividualInfoService individualInfoService;
    

    /*

     * (non-Javadoc)
     * @see com.pay.app.service.member.MemberService#findByMemberCode(long)
     */
    @Override
    public MemberDto findByMemberCode(long memberCode) {
        MemberDto memberDto = null;
        Member member = null;
        if (memberCode > 0) {
            try {
                member = memberDAO.findMemberByMemCode(memberCode);
                if (member != null) {
                    memberDto = new MemberDto();
                    //BeanUtils.copyProperties(member, memberDTO);
                    memberDto = BeanConvertUtil.convert(MemberDto.class, member);
                    if(StringUtils.isNotBlank(memberDto.getGreeting())){
                        memberDto.setGreeting(replaceGreeting(memberDto.getGreeting()).replaceAll("&apos;", "'"));//HTML编码转码
                    }
                    
                }
            } catch (Exception ex) {
                logger.error("根据会员号查询会员基本信息异常！memberCode=" + memberCode, ex);
            }
        } else {
            logger.error("根据会员号查询会员基本信息时，会员号非法！memberCode=" + memberCode);
        }
        return memberDto;
    }

    /*
     * (non-Javadoc)
     * @see com.pay.app.service.member.MemberService#updateMember(com.pay.app.dto.MemberDTO)
     */
    @Override
    public int updateMember(MemberDto memberDto) {
        if (memberDto != null && memberDto.getMemberCode() > 0) {
            Member member = new Member();
            try {
                BeanUtils.copyProperties(memberDto, member);
                return memberDAO.updateMember(member);
            } catch (Exception ex) {
                logger.error("根据会员号更新会员基本信息异常！memberCode=" + memberDto.getMemberCode(), ex);
                return 0;
            }
        }
        logger.error("根据会员号更新会员基本信息时，会员基本信息为空或会员号非法！");
        return 0;
    }

    /**
     * 
     * @param memberDto
     * @param checkCode
     * @param operatorIdentity
     * @param oldIdentity
     * @return
     * @throws AppException
     * @see com.pay.base.service.member.MemberService#activeEnterpriseRnTx(com.pay.base.dto.MemberDto, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
	public ResultDto activeEnterpriseRnTx(MemberDto memberDto,
			String checkCode, String operatorIdentity, String oldIdentity,
			String payPassword, int securityLvl, int scale) throws AppException {
        ResultDto result = new ResultDto();
        if (memberDto != null && memberDto.getMemberCode() > 0 && StringUtils.isNotBlank(checkCode) 
                && StringUtils.isNotBlank(operatorIdentity) && StringUtils.isNotBlank(oldIdentity)
                && StringUtils.isNotBlank(payPassword)) {
            try {
                Member member = memberDAO.findMemberByMemCode(memberDto.getMemberCode());
                if (member != null) {
                    // 设置会员状态为正常.
                    member.setStatus(1);
                    String loginPwd = iMessageDigest.genMessageDigest(memberDto.getLoginPwd()
                        .getBytes());
                    member.setLoginPwd(loginPwd);
                    member.setSecurityQuestion(memberDto.getSecurityQuestion());
                    if (StringUtils.isNotBlank(memberDto.getSecurityAnswer())) {
                        String securityAnswer = iMessageDigest.genMessageDigest(memberDto
                            .getSecurityAnswer().getBytes());
                        member.setSecurityAnswer(securityAnswer);
                    }

                    member.setGreeting(memberDto.getGreeting());
                    // 获取企业会员操作员,并修改其登录密码
                    Operator operator = operatorDAO.getByIdentityMemCode(oldIdentity, memberDto
                        .getMemberCode());
                    // 激活操作员
                    // 设置操作员登录密码、设置操作员状态为正常、设置登录名
                    operatorDAO.activeOperator(loginPwd, operatorIdentity, operator.getOperatorId());

                    // 更新会员基本信息
                    memberDAO.updateMember(member);
                    
                    List<Acct> accts = acctDAO.getByMemberCode(member.getMemberCode());
                    if(null != accts && !accts.isEmpty()){
                    	for(Acct acct : accts){
                    		//if(acct.getStatus() != AcctStatusEnum.VALID.getCode()){
                            	// 更新所有账户类型的状态为有效
                            	acctDAO.updateAcctStatus(acct.getAcctCode(), AcctStatusEnum.VALID.getCode());
                            	
                            	String newPayPwd = iMessageDigest.genMessageDigest(payPassword.getBytes());
                                boolean acctResult =  acctAttribService.resetAcctAttribPwd(acct.getAcctCode(), newPayPwd);
                                if(!acctResult){ // 设置支付密码失败
                                   logger.error("企业用户激活时，设置支付密码失败！memberCode="+memberDto.getMemberCode());
                                   // 抛出异常使其执行事务回滚.
                                   throw new AppException();
                                }
                            //}
                    	}
                    }
                    
                    
                    // 设置checkCode状态为已激活
                    checkCodeService.updateCheckCodeStates(checkCode);

                    // 设置管理员操作员的菜单权限
                    if (operator != null) {
                        OperatorMenu operatorMenu = new OperatorMenu();
                        operatorMenu.setMenuArray(this.getAllMenu(memberDto.getMemberCode(),securityLvl,scale));
                        operatorMenu.setOperateId(operator.getOperatorId());
                        operatorMenuDAO.createOperatorMenu(operatorMenu);
                    } else {
                        logger.warn(oldIdentity+"操作员不存在！memberCode=" + memberDto.getMemberCode());
                    }

                    result.setMemberCode(String.valueOf(memberDto.getMemberCode()));

                } else {
                    logger.error("会员信息不存在，会员号=" + memberDto.getMemberCode());
                    result.setErrorCode("0002");
                    result.setErrorMsg("会员信息不存在");
                }
            } catch (Exception ex) {
                logger.error("根据会员号更新会员基本信息异常！memberCode=" + memberDto.getMemberCode()
                             + ",checkCode=" + checkCode, ex);
                result.setErrorCode("0001");
                result.setErrorMsg("更新会员基本信息异常");
                // 抛出异常使其执行事务回滚.
                throw new AppException();
            }
        } else {
            logger.error("根据会员号更新会员基本信息时，会员基本信息为空或激活码为空！checkCode=" + checkCode);
            result.setErrorCode("0002");
            result.setErrorMsg("会员基本信息为空");
        }
        return result;
    }

    /**
     * 获取企业会员所有授权操作员菜单
     *
     * @param memberCode
     * @return
     */
    private String getAllMenu(long memberCode,int securityLvl,int scale) {
        List<String> mList = new ArrayList<String>();
//        List<PowersModel> list = memberFeatureService.getEnterpriseMenu(SecurityLvlEnum.NORMAL
//            .getValue(), memberCode);
        List<PowersModel> list = memberFeatureService.getEnterpriseMenu(securityLvl, memberCode,scale);
        if (list != null && list.size() > 0) {
            // 目前只考虑两级菜单
            for (PowersModel powersModel : list) {
                mList.add(powersModel.getId());
                if (powersModel.getChildlist() != null && powersModel.getChildlist().size() > 0) {
                    // 有子菜单的情况
                    for (PowersModel powersModel2 : powersModel.getChildlist()) {
                        mList.add(powersModel2.getId());
                    }
                }
            }
            StringBuffer menuArray = new StringBuffer();
            for (String str : mList) {
                if (StringUtils.isNotBlank(menuArray.toString())) {
                    menuArray.append(",").append(str);
                } else {
                    menuArray.append(str);
                }
            }
            return menuArray.toString();
        }
        logger.warn("授权操作员菜单为空！memberCode=" + memberCode);
        return "";
    }

    public MemberDto findMemberByLoginName(String loginName) {
        MemberDto memberDto = null;
        Member member = memberDAO.findMemberByLoginName(loginName);
        if (member != null) {
            memberDto = new MemberDto();
            memberDto = BeanConvertUtil.convert(MemberDto.class, member);
            if(StringUtils.isNotBlank(memberDto.getGreeting())){
                memberDto.setGreeting(replaceGreeting(memberDto.getGreeting()).replaceAll("&apos;", "'"));//HTML编码转码
            }
            
        }
        return memberDto;
    }
    
    public Long findMemberCodeByLoginName(String loginName) {
        return memberDAO.findMemberCodeByLoginName(loginName);
    }

    public MemberDto findMemberByLogin(String loginName,int type){
    	MemberDto memberDto = null;
    	Member member = memberDAO.findMemberByLogin(loginName, type);
    	if (member != null) {
            memberDto = new MemberDto();
            memberDto = BeanConvertUtil.convert(MemberDto.class, member);
            if(StringUtils.isNotBlank(memberDto.getGreeting())){
                memberDto.setGreeting(replaceGreeting(memberDto.getGreeting()).replaceAll("&apos;", "'"));//HTML编码转码
            }
            
        }
    	return memberDto;
    }
    

    public MemberInfoDto queryMemberInfoByMemberCodeNsTx(Long memberCode) {
        MemberDto memberDto = null;
        memberDto = this.findByMemberCode(memberCode);

        IndividualInfoDto individualInfo = null;
        individualInfo = individualInfoService.queryIndividualInfoByMemberCode(memberCode);

        MemberInfoDto memberInfoDto = new MemberInfoDto();
        if (memberDto != null) {
            memberInfoDto.setGreeting(memberDto.getGreeting());
            memberInfoDto.setLoginName(memberDto.getLoginName());
            memberInfoDto.setRegType(memberDto.getLoginType());
            memberInfoDto.setSecurityAnswer(memberDto.getSecurityAnswer());
            memberInfoDto.setSecurityQuestion(memberDto.getSecurityQuestion());
            memberInfoDto.setMemberCode(memberDto.getMemberCode());
            memberInfoDto.setSsoUserId(memberDto.getSsoUserId());
            memberInfoDto.setCreateDate(memberDto.getCreateDate());
            memberInfoDto.setStatus(memberDto.getStatus());
        }
        if (individualInfo != null) {
            memberInfoDto.setAddr(individualInfo.getAddr());
            memberInfoDto.setCertificateType(individualInfo.getCerType());
            memberInfoDto.setCertificateNo(individualInfo.getCerCode());
            memberInfoDto.setCity(individualInfo.getCity());
            memberInfoDto.setEmail(individualInfo.getEmail());
            memberInfoDto.setFax(individualInfo.getFax());
            memberInfoDto.setMobile(individualInfo.getMobile());
            memberInfoDto.setMsn(individualInfo.getMsn());
            memberInfoDto.setRealName(individualInfo.getName());
            memberInfoDto.setProvince(individualInfo.getProvince());
            memberInfoDto.setQq(individualInfo.getQq());
            memberInfoDto.setTel(individualInfo.getTel());
            memberInfoDto.setZip(individualInfo.getZip());
        }

        //		ResultDto result = new ResultDto();
        //		
        //		if(memberDto == null){
        //			result.setErrorNum(ErrorCodeEnum.MEMBER_NULL_ERROR);
        //		}else{
        //			result.setMemberCode(memberInfoDto.getMemberCode().toString());
        //			result.setObject(memberInfoDto);
        //		}

        return memberInfoDto;
    }

    public MemberCriteria queryMemberCriteriaByMemberCodeNsTx(Long memberCode) {

        MemberInfoDto memberInfoDto = this.queryMemberInfoByMemberCodeNsTx(memberCode);

        MemberCriteria memberCriteria = new MemberCriteria();
        memberCriteria.setAddress(memberInfoDto.getAddr());
        memberCriteria.setAnswer(memberInfoDto.getSecurityAnswer());
        memberCriteria.setAptoticPhone(memberInfoDto.getTel());
        if(StringUtils.isNotEmpty(memberInfoDto.getCertificateNo())){
            memberCriteria.setCardNo(DESUtil.decrypt(memberInfoDto.getCertificateNo()));
        }
       
        memberCriteria.setCardType(memberInfoDto.getCertificateType());
        if (memberInfoDto.getRegType() == 1) {
            memberCriteria.setContact(memberInfoDto.getEmail());
        } else {
            memberCriteria.setContact(memberInfoDto.getMobile());
        }
        memberCriteria.setCreateDate(memberInfoDto.getCreateDate());
        memberCriteria.setCity(memberInfoDto.getCity());
        memberCriteria.setEmail(memberInfoDto.getEmail());
        memberCriteria.setFaxes(memberInfoDto.getFax());
        memberCriteria.setLoginName(memberInfoDto.getLoginName());
        memberCriteria.setMemberCode(memberInfoDto.getMemberCode().toString());
        memberCriteria.setMobileNo(memberInfoDto.getMobile());
        memberCriteria.setMsn(memberInfoDto.getMsn());
        memberCriteria.setPayPwd(memberInfoDto.getPayPassword());
        memberCriteria.setPostCode(memberInfoDto.getZip());
        memberCriteria.setProvince(memberInfoDto.getProvince());
        memberCriteria.setQq(memberInfoDto.getQq());
        memberCriteria.setQuestionId(memberInfoDto.getSecurityQuestion().toString());
        memberCriteria.setRegType(memberInfoDto.getRegType().toString());
        memberCriteria.setSalutatory(transferWelcomeMsg(memberInfoDto.getGreeting()));
        memberCriteria.setUpdateDate(memberInfoDto.getUpdateDate());
        //		memberCriteria.setUserId(memberInfoDto.getMemberCode().toString());
        //		memberCriteria.setUserName(memberInfoDto.getRealName());
        memberCriteria.setVerifyName(memberInfoDto.getRealName());
        memberCriteria.setWelcomeMsg(replaceGreeting(memberInfoDto.getGreeting()));
        memberCriteria.setUserId(memberInfoDto.getSsoUserId());
        if(memberInfoDto.getStatus() != null){
        	memberCriteria.setStatus(memberInfoDto.getStatus().toString());
         }
       
        return memberCriteria;
    }

    private String replaceGreeting(String greeting){
    	 String welcomeMsg = greeting;
    	 if(null != welcomeMsg && welcomeMsg.contains("&quot;")){
    		 welcomeMsg=welcomeMsg.replace("&quot;","\"");
    	 }
    	 if(null != welcomeMsg && welcomeMsg.contains("&#39;")){
    		 welcomeMsg=welcomeMsg.replace("&#39;","\'");
    	 }
    	 return welcomeMsg;
    }
    private String transferWelcomeMsg(String greeting) {
        String welcomeMsg = greeting;
        welcomeMsg=replaceGreeting(welcomeMsg);
        if (!StringUtil.isEmpty(welcomeMsg) && welcomeMsg.length() > 25) {
            welcomeMsg = welcomeMsg.substring(0, 25) + " ...";
        }
        return welcomeMsg;
    }

    public ResultDto doUpdateMemberInfoRnTx(MemberInfoDto memberInfoDto) {
        ResultDto result = new ResultDto();

        if (memberInfoDto != null) {
            MemberDto memberDto = null;
            memberDto = this.findByMemberCode(memberInfoDto.getMemberCode());
            if (memberDto != null) {
                String securityAnswer = null;
                try {
                    if (memberInfoDto.getSecurityAnswer() != null) {
                        securityAnswer = iMessageDigest.genMessageDigest(memberInfoDto
                            .getSecurityAnswer().getBytes());
                        memberDto.setSecurityAnswer(securityAnswer);
                    }
                } catch (Exception e) {
                    logger.error("SHAMessageDigest is error");
                    result.setErrorNum(ErrorCodeEnum.SHA_MESSAGE_DIGEST_ERROR);
                }
                if (memberInfoDto.getSecurityQuestion() != null) {
                    memberDto.setSecurityQuestion(memberInfoDto.getSecurityQuestion());
                }
                if (!StringUtil.isEmpty(memberInfoDto.getGreeting())) {
                    memberDto.setGreeting(memberInfoDto.getGreeting());
                }
                memberDto.setMemberCode(memberInfoDto.getMemberCode());

                //更新用户
                memberDAO.updateMember(BeanConvertUtil.convert(Member.class, memberDto));
            } else {
                logger.error("Member is not exist");
                result.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
                return result;
            }

            IndividualInfoDto person = new IndividualInfoDto();
            person = individualInfoService.queryIndividualInfoByMemberCode(memberInfoDto
                .getMemberCode());
            if (person != null) {
                person.setAddr(memberInfoDto.getAddr());
                person.setCerType(memberInfoDto.getCertificateType());
                person.setCity(memberInfoDto.getCity());
                person.setEmail(memberInfoDto.getEmail());
                person.setFax(memberInfoDto.getFax());
                person.setMobile(memberInfoDto.getMobile());
                person.setMsn(memberInfoDto.getMsn());
                person.setProvince(memberInfoDto.getProvince());
                person.setQq(memberInfoDto.getQq());
                person.setTel(memberInfoDto.getTel());
                person.setZip(memberInfoDto.getZip());
                person.setMemberCode(memberInfoDto.getMemberCode());
                person.setCerCode(memberInfoDto.getCertificateNo());
                person.setCerType(memberInfoDto.getCertificateType());
                //更新会员详细信息
                individualInfoService.updateIndividualInfo(person);
            } else {
                logger.error("Member Info is not exist");
                result.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
                return result;
            }
            result.setObject(memberInfoDto);
            return result;
        } else {
            result.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
            return result;
        }
    }
    
    public ResultDto doUpdateCorpMemberInfoRnTx(MemberInfoDto memberInfoDto) {
        ResultDto result = new ResultDto();

        if (memberInfoDto != null) {
            MemberDto memberDto = null;
            memberDto = this.findByMemberCode(memberInfoDto.getMemberCode());
            if (memberDto != null) {
                memberDto.setGreeting(memberInfoDto.getGreeting());
                String securityAnswer = null;
                try {
                    if (memberInfoDto.getSecurityAnswer() != null) {
                        securityAnswer = iMessageDigest.genMessageDigest(memberInfoDto
                            .getSecurityAnswer().getBytes());
                        memberDto.setSecurityAnswer(securityAnswer);
                    }
                } catch (Exception e) {
                    logger.error("SHAMessageDigest is error");
                    result.setErrorNum(ErrorCodeEnum.SHA_MESSAGE_DIGEST_ERROR);
                }
                if (memberInfoDto.getSecurityQuestion() != null) {
                    memberDto.setSecurityQuestion(memberInfoDto.getSecurityQuestion());
                }
                if (!StringUtil.isEmpty(memberInfoDto.getGreeting())) {
                    memberDto.setGreeting(memberInfoDto.getGreeting());
                }
                memberDto.setMemberCode(memberInfoDto.getMemberCode());

                //更新用户
                memberDAO.updateMember(BeanConvertUtil.convert(Member.class, memberDto));
            } else {
                logger.error("Member is not exist");
                result.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
                return result;
            }

           
            result.setObject(memberInfoDto);
            return result;
        } else {
            result.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
            return result;
        }
    }

    public boolean checkLoginNameByRegister(String loginName, int loginType) {
        if (StringUtil.isEmpty(loginName)) {
            return false;
        }

        if (memberDAO.checkIndividualLoginName(loginName)) {
//            if (LoginTypeEnum.email.getValue() == loginType) {
//                return individualInfoService.checkEmail(loginName);
//            } else if (LoginTypeEnum.mobile.getValue() == loginType) {
//                return individualInfoService.checkMobile(loginName);
//            }
            return true;
        }

        return false;
    }
    
    public Integer checkLoginNameByRegister(String loginName){
        try {
            return memberDAO.checkIndividualLoginNameAndStatus(loginName);
        } catch (Exception e) {
            logger.error("memberDAO.checkIndividualLoginNameAndStatus throws errors ",e);
            return MemberStatusEnum.UNKNOW.getCode();
        }
    }

    @Override
    public ResultDto doIndividualMemberRegisterRdTx(MemberInfoDto mDto)throws AppException {
		String emailName = "";
	    String phoneName = "";
    	MemberInfoDto memberInfoDto=BeanConvertUtil.convert(MemberInfoDto.class, mDto);
        ResultDto resultDto = new ResultDto();
        if(memberInfoDto.getLoginName().contains("@")){
        	emailName=memberInfoDto.getLoginName();
		}else{			
			phoneName=memberInfoDto.getLoginName();
		}
        String cardNo=memberInfoDto.getCertificateNo();
        /*
         * 校验登录名与登录密码
         */
        if (StringUtils.isEmpty(memberInfoDto.getLoginName())) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_EMPTY);
        } else if (StringUtils.isEmpty(memberInfoDto.getPassword())) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_LOGINPWD_EMPTY);
        }else if (memberInfoDto.getLoginName().length()>60) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_INDEXBUNDOF);
        } else if (!CheckUtil.checkLoginPwd(memberInfoDto.getPassword())) {
           resultDto.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_PAYPASSWORD);
        } else if (!CheckUtil.checkEmail(emailName) && !"".equals(emailName)) {
           resultDto.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_EMAILNAME);
        }else if (!CheckUtil.checkPhone(phoneName) && !"".equals(phoneName)) {
           resultDto.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_PHONENAME);
        }else if (memberInfoDto.getPassword().length()<8) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_LOGINPWD_INDEXLEAST);
        } else if (memberInfoDto.getPassword().length()>20) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_LOGINPWD_INDEXBUNDOF);
        }else if (StringUtils.isNotEmpty(cardNo) && DESUtil.encrypt(cardNo).length()>100) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_CER_INDEXBUNDOF);
        } 
        else {
            try {
                String passWord = iMessageDigest.genMessageDigest(memberInfoDto.getPassword()
                    .getBytes());
                memberInfoDto.setPassword(passWord);
                String answer = iMessageDigest.genMessageDigest(memberInfoDto.getSecurityAnswer()
                    .getBytes());
              
                if(StringUtils.isNotEmpty(cardNo)){
                    memberInfoDto.setCertificateNo(DESUtil.encrypt(cardNo));
                }
                memberInfoDto.setSecurityAnswer(answer);
                Member member = memberDAO.convertIndividualMember(memberInfoDto);
                Long memberCode = memberDAO.createIndividualMember(member);
                if (memberCode != null) {
                    IndividualInfoDto individualInfo = individualInfoService.convertIndividualInfo(
                        memberInfoDto, memberCode);
                    Long individualId = individualInfoService.createIndividualInfo(individualInfo);
                    Long acctAttribId = acctDAO.createAcctAttrib(memberCode);
                    String acctCode = acctDAO.getAccCodeById(acctAttribId);
                    Acct acct = new Acct();
                    acct.setAcctCode(acctCode);
                    acct.setMemberCode(memberCode);
                    acctDAO.createAcct(acct);
                    resultDto.setMemberCode(memberCode.toString());
                }

            } catch (Exception e) {
                resultDto.setErrorNum(ErrorCodeEnum.UNKNOW_ERROR);
                logger.error("MemberServiceImpl doIndividualMemberRegisterRdTx throws exception :",
                    e);
                throw new AppException();
            }
        }

        return resultDto;

    }
    
    /* (non-Javadoc)
     * @see com.pay.base.service.member.MemberService#resetSecurityQuestionRnTx(java.lang.Long, java.lang.Integer, java.lang.String)
     */
    public ResultDto resetSecurityQuestionRnTx(Long memberCode, Integer securQuestionId,
                                               String answer) {
        ResultDto resultDto = new ResultDto();
        //参数验证
        if (null == memberCode || memberCode.longValue() <= 0) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBERCODE_ERROR);
            return resultDto;
        } else if (null == securQuestionId || securQuestionId.intValue() <= 0) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_SECURITYANSWER_ERROR);
            return resultDto;
        } else if (null == answer || StringUtils.isEmpty(answer)) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_SECURITYQUESTION_ERROR);
            return resultDto;
        }

        MemberDto memberDto = this.findByMemberCode(memberCode);
        //判断是否存在会员
        if (null != memberDto) {
            try {
                // 安全问题加密
                answer = iMessageDigest.genMessageDigest(answer.getBytes());
                //重置
                memberDto.setSecurityQuestion(securQuestionId);
                memberDto.setSecurityAnswer(answer);
                if (this.memberDAO.updateMember(BeanConvertUtil.convert(Member.class, memberDto)) != 1) {
                    resultDto.setErrorNum(ErrorCodeEnum.MEMBER_UPDATE_SECURITYANSWER_ERROR);
                    return resultDto;
                }
                resultDto.setObject(String.valueOf(memberDto.getMemberCode()));
            } catch (Exception e) {
                logger.error("会员设置安全问题 更新失败！memberCode=" + memberDto.getMemberCode()
                             + " ,securQuestionId=" + securQuestionId + " ,answer=" + answer, e);
                resultDto.setErrorNum(ErrorCodeEnum.MEMBER_UPDATE_SECURITYANSWER_ERROR);
            }
        } else {
            //会员不存在
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
        }
        return resultDto;
    }

    /* (non-Javadoc)
     * @see com.pay.base.service.member.MemberService#resetLoginPwdTx(java.lang.Long, java.lang.String)
     */
    public ResultDto resetLoginPwdRnTx(Long memberCode, String loginPwd,Long operatorId) {
        ResultDto resultDto = new ResultDto();
        
        //如果登录密码校验失败
        if(!CheckUtil.checkLoginPwd(loginPwd)){
    		resultDto.setErrorNum(ErrorCodeEnum.MEMBER_VERIFY_PASSWORD_ERROR);
    		return resultDto;
    	}
        //参数验证
        if (null == memberCode || memberCode.longValue() <= 0) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBERCODE_ERROR);
            return resultDto;
        } else if (null == loginPwd || StringUtils.isEmpty(loginPwd)) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_PASSWORD_ERROR);
            return resultDto;
        }

        MemberDto memberDto = this.findByMemberCode(memberCode);
        //判断是否存在会员
        if (null != memberDto) {
            try {
                // 密码加密
                loginPwd = iMessageDigest.genMessageDigest(loginPwd.getBytes());
                if(memberDto.getType() == MemberTypeEnum.MERCHANT.getCode() && operatorId != null && operatorId > 0){
                    operatorDAO.updateOperatorPWD(loginPwd, operatorId,memberCode);
                }
                //重置
                memberDto.setLoginPwd(loginPwd);
                if (this.memberDAO.updateMember(BeanConvertUtil.convert(Member.class, memberDto)) != 1) {
                    resultDto.setErrorNum(ErrorCodeEnum.MEMBER_UPDATE_PASSWORD);
                    return resultDto;
                }
                resultDto.setObject(String.valueOf(memberDto.getMemberCode()));
            } catch (Exception e) {
                logger.error("会员设置登录密码 失败！memberCode=" + memberDto.getMemberCode() + " ,loginPwd="
                             + loginPwd, e);
                resultDto.setErrorNum(ErrorCodeEnum.MEMBER_UPDATE_PASSWORD);
            }
        } else {
            //会员不存在
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
        }
        return resultDto;
    }

    /* (non-Javadoc)
     * @see com.pay.base.service.member.MemberService#verifyLoginPwdTx(java.lang.Long, java.lang.String)
     */
    public ResultDto verifyLoginPwdNsTx(Long memberCode, String loginPwd) {
        ResultDto resultDto = new ResultDto();
        //参数验证
        if (null == memberCode || memberCode.longValue() <= 0) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBERCODE_ERROR);
            return resultDto;
        } else if (null == loginPwd || StringUtils.isEmpty(loginPwd)) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_PASSWORD_ERROR);
            return resultDto;
        }

        MemberDto memberDto = this.findByMemberCode(memberCode);
        //判断是否存在会员
        if (null != memberDto) {
            try {
                // 密码加密
                loginPwd = iMessageDigest.genMessageDigest(loginPwd.getBytes());
                if (!memberDto.getLoginPwd().equals(loginPwd)) {
                    resultDto.setErrorNum(ErrorCodeEnum.MEMBER_VERIFY_PASSWORD_ERROR);
                    return resultDto;
                }
                resultDto.setObject(String.valueOf(memberDto.getMemberCode()));
            } catch (Exception e) {
                logger.error("会员验证登陆密码失败！memberCode=" + memberDto.getMemberCode() + " ,loginPwd="
                             + loginPwd, e);
                resultDto.setErrorNum(ErrorCodeEnum.MEMBER_VERIFY_PASSWORD_ERROR);
            }
        } else {
            //会员不存在
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
        }

        return resultDto;
    }
    
    public ResultDto verifyLoginPwd(Long memberCode, String loginPwd,
			Long operatorId) {
    	 ResultDto resultDto = new ResultDto();
         //参数验证
         if (null == memberCode || memberCode.longValue() <= 0) {
             resultDto.setErrorNum(ErrorCodeEnum.MEMBERCODE_ERROR);
             return resultDto;
         } else if (null == loginPwd || StringUtils.isEmpty(loginPwd)) {
             resultDto.setErrorNum(ErrorCodeEnum.MEMBER_PASSWORD_ERROR);
             return resultDto;
         }

         MemberDto memberDto = this.findByMemberCode(memberCode);
         //判断是否存在会员
         if (null != memberDto) {
             try {
                 // 密码加密
                 loginPwd = iMessageDigest.genMessageDigest(loginPwd.getBytes());
                 String oldLoginPwd = "";
                 if(memberDto.getType() == MemberTypeEnum.MERCHANT.getCode() && operatorId != null && operatorId > 0){
                 	oldLoginPwd = operatorDAO.getOperatorById(operatorId).getPassword();
                 }else{
                	oldLoginPwd = memberDto.getLoginPwd(); 
                 }
                 if (!oldLoginPwd.equals(loginPwd)) {
                     resultDto.setErrorNum(ErrorCodeEnum.MEMBER_VERIFY_PASSWORD_ERROR);
                     return resultDto;
                 }
                 resultDto.setObject(String.valueOf(memberDto.getMemberCode()));
             } catch (Exception e) {
                 logger.error("会员验证登陆密码失败！memberCode=" + memberDto.getMemberCode() + " ,loginPwd="
                              + loginPwd, e);
                 resultDto.setErrorNum(ErrorCodeEnum.MEMBER_VERIFY_PASSWORD_ERROR);
             }
         } else {
             //会员不存在
             resultDto.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
         }

         return resultDto;
	}

    /* (non-Javadoc)
     * @see com.pay.base.service.member.MemberService#updateEmail(java.lang.Long, java.lang.String)
     */
    public ResultDto updateEmailRnTx(Long memberCode, String email, String oldEmail) {
        ResultDto resultDto = new ResultDto();
        //参数验证
        if (null == memberCode || memberCode.longValue() <= 0) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBERCODE_ERROR);
            return resultDto;
        } else if (null == email || StringUtils.isEmpty(email)
                   || !ValidateUtils.isValidEmail(email)) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_EMAIL_ERROR);
            return resultDto;
        } else if (null == oldEmail || StringUtils.isEmpty(oldEmail)
                   || !ValidateUtils.isValidEmail(oldEmail)) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_EMAIL_ERROR);
            return resultDto;
        }

        //邮箱一致直接返回
        else if (email.equals(oldEmail)) {
            resultDto.setMemberCode(memberCode.toString());
            return resultDto;
        }
        //校验个人会员email是否唯一
        else if (!individualInfoService.checkEmail(email)) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_EMAIL_IS_EXIST);
            return resultDto;
        }

        MemberDto memberDto = this.findByMemberCode(memberCode);
        if (null == memberDto) {
            //会员不存在
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
            return resultDto;
        }

        IndividualInfoDto individualInfoDto = individualInfoService
            .queryIndividualInfoByMemberCode(memberCode);
        if (null == individualInfoDto) {
            //个人会员信息不存在
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_INDIVIDUAL_INFO_ERROR);
            return resultDto;
        }

        try {
            individualInfoDto.setEmail(email);
            individualInfoService.updateIndividualInfo(individualInfoDto);
            resultDto.setObject(String.valueOf(memberDto.getMemberCode()));
        } catch (Exception e) {
            logger
                .error("会员更新邮箱失败！memberCode=" + memberDto.getMemberCode() + " ,email=" + email, e);
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_UPDATE_EMAIL_ERROR);
        }

        return resultDto;
    }

    public ResultDto doActiveMemberRnTx(Long memberCode, final String activeType, Date cdate) {
        ResultDto resultDto = new ResultDto();
        int activeStatus = 0;

        if (checkActiveInterval(cdate, activeType)) {
            try {
                activeStatus = memberDAO.updateMemberStatus(memberCode);
                resultDto.setObject(String.valueOf(activeStatus));
            } catch (Exception e) {
                logger.error("MemberServiceImpl.doActiveMemberRnTx throws error", e);
                resultDto.setErrorNum(ErrorCodeEnum.MEMBER_ACTIVE_ERROR);
            }
            if (activeStatus != AppConf.ACTIVE_SUCCESS) {
                resultDto.setErrorCode(String.valueOf(activeStatus));

            }

        } else {
            resultDto.setErrorCode(String.valueOf(activeStatus));
            if (AppConf.activeMobile.equals(activeType)) {
                resultDto.setErrorMsg(MessageConvertFactory.getMessage("mobileValid"));
            } else {
                resultDto.setErrorMsg(MessageConvertFactory.getMessage("emailValid"));
            }
        }

        return resultDto;
    }
    /*
     * (non-Javadoc)
     * @see com.pay.base.service.member.MemberService#updateServiceLevelByMemCode(com.pay.base.common.enums.ServiceLevelEnum, long)
     */
    public int updateServiceLevelByMemCode(ServiceLevelEnum serviceLevel,long memberCode){
        if(serviceLevel != null){
            return memberDAO.updateServiceLevelByMemCode(serviceLevel.getValue(), memberCode);
        }
        if(logger.isInfoEnabled()){
            logger.info("updateServiceLevelByMemCode fail!Because serviceLevel is null.memberCode="+memberCode);
        }
        return 0;
    }
	
	public int editMemberTypeByMemberCode(Map map) {
		return this.memberDAO.editMemberTypeByMemberCode(map);
	}
   

    /* (non-Javadoc)
     * @see com.pay.base.service.member.MemberService#updateMobile(java.lang.Long, java.lang.String)
     */
    public ResultDto updateMobileRnTx(Long memberCode, String mobile, String oldMobile) {
        ResultDto resultDto = new ResultDto();
        //参数验证
        if (null == memberCode || memberCode.longValue() <= 0) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBERCODE_ERROR);
            return resultDto;
        } else if (null == mobile || StringUtils.isEmpty(mobile)
                   || !ValidateUtils.isValidMobile(mobile)) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_MOBILE_ERROR);
            return resultDto;
        } else if (null == oldMobile || StringUtils.isEmpty(oldMobile)
                   || !ValidateUtils.isValidMobile(oldMobile)) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_MOBILE_ERROR);
            return resultDto;
        }

        //手机一致直接返回
        else if (mobile.equals(oldMobile)) {
            resultDto.setObject(memberCode.toString());
            return resultDto;
        }

        //校验个人会员mobile是否唯一
        else if (!individualInfoService.checkMobile(mobile)) {
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_MOBILE_IS_EXIST);
            return resultDto;
        }

        MemberDto memberDto = this.findByMemberCode(memberCode);
        if (null == memberDto) {
            //会员不存在
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
            return resultDto;
        }

        IndividualInfoDto individualInfoDto = individualInfoService
            .queryIndividualInfoByMemberCode(memberCode);
        if (null == individualInfoDto) {
            //个人会员信息不存在
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_INDIVIDUAL_INFO_ERROR);
            return resultDto;
        }

        try {
            individualInfoDto.setMobile(mobile);
            individualInfoService.updateIndividualInfo(individualInfoDto);
            resultDto.setObject(String.valueOf(memberDto.getMemberCode()));
        } catch (Exception e) {
            logger.error("会员更新手机号码失败！memberCode=" + memberDto.getMemberCode() + " ,mobile="
                         + mobile, e);
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_UPDATE_MOBILE_ERROR);
        }

        return resultDto;
    }

    private boolean checkActiveInterval(Date cdate, final String activeType) {
        Calendar calendar = Calendar.getInstance();
        Date currentDate = calendar.getTime();
        if (AppConf.activeMobile.equals(activeType)) {
            if (FormatDate.getIntervalMinutes(cdate, currentDate) <= Integer.parseInt(AppConf
                .get(AppConf.sms_interval))) {
                return true;
            }
        } else {
            if (FormatDate.getIntervalDays(cdate, currentDate) <= Integer.parseInt(AppConf
                .get(AppConf.mail_interval))) {
                return true;
            }
        }
        return false;
    }
    
    public ResultDto doUpdateTempMemberInfoRnTx(MemberInfoDto memberInfoDto,Long memberCode,String payPassword) throws AppException {
    	ResultDto result = new ResultDto();
    	List<Acct> accts = acctDAO.getByMemberCode(memberCode);
		//验证帐号
		//需要判断用户是否有效，如果该用户无效了，就不能进行密码的设置
		if(null==accts){
			logger.error("acct is not exist ");
			result.setErrorNum(ErrorCodeEnum.ACCT_NON_EXIST_ERROR);
			return result;
		}
		
		if(null != accts && !accts.isEmpty()){
			for(Acct acct :accts){
				//需要判断用户是否有效，如果该用户无效了，就不能进行密码的设置
				if(acct.getStatus()==AcctStatusEnum.INVALID.getCode()){
					logger.error("acct invalid error ");
					result.setErrorNum(ErrorCodeEnum.ACCT_INVALID_ERROR);
					return result;
				}
				//需要判断用户是否冻结，如果该用户冻结了，就不能进行密码的设置
				if(acct.getStatus()==AcctStatusEnum.FROZEN.getCode()){
					logger.error("acct frozen error ");
					result.setErrorNum(ErrorCodeEnum.ACCT_FROZEN_ERROR);
					return result;
				}
				
				try {
					result = this.doUpdateTempMemberInfoRnTx(memberInfoDto);
					payPassword = iMessageDigest.genMessageDigest(payPassword.getBytes());
					acctAttribService.queryAcctAttribByAcctCode(acct.getAcctCode());
					acctAttribService.resetAcctAttribPwd(acct.getAcctCode(), payPassword);
				} catch (Exception e) {
					logger.error("unknow error", e);
					result.setErrorNum(ErrorCodeEnum.UNKNOW_ERROR);
					throw new AppException();
				}
			}
		}
    	
		return result;
    }

    @Override
    public ResultDto validateSecurMemberQuestionWidthMemberInfo(Long memberCode,
                                                                Integer questionId, String securInfo) {
        ResultDto resultDto = new ResultDto();
        logger.info("验证会员:" + memberCode + " 的安全问题信息");
        if (memberCode == null || memberCode.longValue() <= 0 || questionId == null
            || questionId.intValue() <= 0) {
            logger.error("输入的会员号有误，请核对");
            resultDto.setErrorNum(ErrorCodeEnum.MEMBERCODE_ERROR);
            resultDto.setResultStatus(false);
            return resultDto;
        }
        if (StringUtil.isNull(securInfo) || StringUtil.isEmpty(securInfo)) {
            logger.error("输入的安全问题有误，请核对");
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_SECURINFO_ERROR);
            resultDto.setResultStatus(false);
            return resultDto;
        }
        logger.info("查询会员号为：" + memberCode);
        Integer result = new Integer(0);
        try {
            //				result = this.memberDAO.validateSecurMemberQuestion(memberCode, questionId, securInfo
            //						);
            result = this.memberDAO.validateSecurMemberQuestion(memberCode, questionId,
                iMessageDigest.genMessageDigest(securInfo.getBytes()));
        } catch (Exception e) {
            logger.error("发生未知异常", e);
            resultDto.setErrorNum(ErrorCodeEnum.UNKNOW_ERROR);
            resultDto.setResultStatus(false);
            return resultDto;
        }
        if (result == null || result.intValue() != 1) {
            logger.info("对于会员号为：" + memberCode + "安全问题有误");
            resultDto.setErrorNum(ErrorCodeEnum.MEMBER_NULL_ERROR);
            resultDto.setResultStatus(false);
            return resultDto;
        }
        resultDto.setResultStatus(true);
        return resultDto;

    }
    
    public ResultDto doUpdateTempMemberInfoRnTx(MemberInfoDto memberInfoDto) {
        ResultDto result = new ResultDto();

        if (memberInfoDto != null) {
            MemberDto memberDto = null;
            memberDto = this.findByMemberCode(memberInfoDto.getMemberCode());
            if (memberDto != null) {
                String securityAnswer = null;
                try {
                    if (memberInfoDto.getSecurityAnswer() != null) {
                        securityAnswer = iMessageDigest.genMessageDigest(memberInfoDto
                            .getSecurityAnswer().getBytes());
                        memberDto.setSecurityAnswer(securityAnswer);
                    }
                } catch (Exception e) {
                    logger.error("SHAMessageDigest is error");
                    result.setErrorNum(ErrorCodeEnum.SHA_MESSAGE_DIGEST_ERROR);
                }
                if (memberInfoDto.getSecurityQuestion() != null) {
                    memberDto.setSecurityQuestion(memberInfoDto.getSecurityQuestion());
                }
                if (!StringUtil.isEmpty(memberInfoDto.getGreeting())) {
                    memberDto.setGreeting(memberInfoDto.getGreeting());
                }
                memberDto.setMemberCode(memberInfoDto.getMemberCode());
                memberDto.setStatus(memberInfoDto.getStatus());
                if(StringUtils.isNotBlank(memberDto.getLoginPwd())){
                	memberDto.setLoginPwd(memberInfoDto.getPassword());
                }
                //更新用户
                memberDAO.updateMember(BeanConvertUtil.convert(Member.class, memberDto));
            } else {
                logger.error("Member is not exist");
                result.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
                return result;
            }

            IndividualInfoDto person = new IndividualInfoDto();
            person = individualInfoService.queryIndividualInfoByMemberCode(memberInfoDto
                .getMemberCode());
            if (person != null) {
                person.setAddr(memberInfoDto.getAddr());
                person.setCerType(memberInfoDto.getCertificateType());
                person.setCity(memberInfoDto.getCity());
                person.setEmail(memberInfoDto.getEmail());
                person.setFax(memberInfoDto.getFax());
                person.setMobile(memberInfoDto.getMobile());
                person.setMsn(memberInfoDto.getMsn());
                person.setProvince(memberInfoDto.getProvince());
                person.setQq(memberInfoDto.getQq());
                person.setTel(memberInfoDto.getTel());
                person.setZip(memberInfoDto.getZip());
                person.setMemberCode(memberInfoDto.getMemberCode());
                person.setCerCode(memberInfoDto.getCertificateNo());
                person.setCerType(memberInfoDto.getCertificateType());
                person.setName(memberInfoDto.getRealName());
                //更新会员详细信息
                individualInfoService.updateIndividualInfo(person);
            } else {
                logger.error("Member Info is not exist");
                result.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
                return result;
            }
            result.setObject(memberInfoDto);
            return result;
        } else {
            result.setErrorNum(ErrorCodeEnum.MEMBER_NON_EXIST_ERROR);
            return result;
        }
    }

    public MemberDto queryMemberBySsoUserid(String ssoUserid) {
        Member member = memberDAO.queryMemberBySsoUserid(ssoUserid);
        return BeanConvertUtil.convert(MemberDto.class, member);
    }
    
    public MemberDto queryPersonMemberBySsoUserid(String ssoUserid) {
        Member member = memberDAO.queryPersonMemberBySsoUserid(ssoUserid);//TODO
        return BeanConvertUtil.convert(MemberDto.class, member);
    }

    public boolean updateMemberSsoUserid(Long memberCode, String ssoUserid) {
        Map<String, Object> ssoMap = new HashMap<String, Object>();
        ssoMap.put("memberCode", memberCode);
        ssoMap.put("ssoUserid", ssoUserid);
        return this.memberDAO.updateMemberSsoUserid(ssoMap);
    }
    
	@Override
	public List<MemberBalancesDto> queryMemberByParent(Map<String, Object> param) {
		if(param == null || param.get("fatherMemberCode") == null){
			return null;
		}
		return memberDAO.querySonMemberByParent(param);
	}

	@Override
	public int queryMemberCountByParent(Map<String, Object> param) {
		if(param == null || param.get("fatherMemberCode") == null){
			return 0;
		}
		return memberDAO.querySonMemberCountByParent(param);
	}

	public int updateStatusByMemberCode(long memberCode, int status){
        return memberDAO.updateStatusByMemberCode(memberCode, status);
    }
    
    public void setMemberDAO(MemberDAO memberDAO) {
        this.memberDAO = memberDAO;
    }

    public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
        this.iMessageDigest = iMessageDigest;
    }

    public void setOperatorDAO(OperatorDAO operatorDAO) {
        this.operatorDAO = operatorDAO;
    }

    public void setMemberFeatureService(MemberFeatureService memberFeatureService) {
        this.memberFeatureService = memberFeatureService;
    }

    public void setOperatorMenuDAO(OperatorMenuDAO operatorMenuDAO) {
        this.operatorMenuDAO = operatorMenuDAO;
    }

    public void setIndividualInfoService(IndividualInfoService individualInfoService) {
        this.individualInfoService = individualInfoService;
    }

    public void setAcctDAO(AcctDAO acctDAO) {
        this.acctDAO = acctDAO;
    }

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

}
