/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.user.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.common.MaConstant;
import com.pay.acc.member.memberenum.MemberStatusEnum;
import com.pay.acc.member.model.Member;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.account.dto.VerifyResultDto;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.NotifyFacadeService;
import com.pay.app.base.session.RequestLocal;
import com.pay.app.common.helper.AppConf;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.common.login.LoginUtil;
import com.pay.base.dto.IndividualInfoDto;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.MemberDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.Operator;
import com.pay.base.service.individual.IndividualInfoService;
import com.pay.base.service.member.MemberOperateService;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.base.service.operator.statusEnum.OperatorStatusEnum;
import com.pay.base.service.user.UserLogService;
import com.pay.base.service.user.UserLoginService;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.BeanConvertUtil;
import com.pay.util.WebUtil;

/**
 * 用户登录服务
 * 
 * @author zhi.wang
 * @version $Id: UserLoginServiceImpl.java, v 0.1 2010-11-10 下午04:32:35 zhi.wang
 *          Exp $
 */
public class UserLoginServiceImpl implements UserLoginService {
    private static final Log      logger            = LogFactory.getLog(UserLoginServiceImpl.class);
    /** 个人会员服务 */
    private MemberService         memberService;
    /** 操作员服务 */
    private OperatorServiceFacade operatorServiceFacade;
    /** 摘要 */
    private IMessageDigest        iMessageDigest;
    /** 个人会员信息 */
    private IndividualInfoService individualInfoService;
    /** 操作日志服务 */
    private UserLogService        userLogService;

    private MemberQueryService    memberQueryService;
    
    private MemberOperateService  memberOperateService;
    /** 密码输错次数替换字符 */
    private static final String   ERRORMSG_REPLACE  = "faildNum";
    /** sumNum 密码输错总次数 */
    private static final String   SUMNUM_REPLACE    = "sumNum";
    /** limitTime 限制时间 */
    private static final String   LIMITTIME_REPLACE = "limitTime";
    
    /** website登录密码输入失败前三次发异步消息接口 */
    private NotifyFacadeService notifyFacadeService;
    
    public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService){
    	this.notifyFacadeService = notifyFacadeService;
    }

    public ResultDto enterpriceMemberLogin(String loginName, String identity, String loginPwd) {
        ResultDto result = new ResultDto();
        if (StringUtils.isEmpty(loginName)) {
            result.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_EMPTY);
        } else if (StringUtils.isEmpty(identity)) {
            result.setErrorNum(ErrorCodeEnum.OPERATOR_LOGINNAME_EMPTY);
        } else if (StringUtils.isEmpty(loginPwd)) {
            result.setErrorNum(ErrorCodeEnum.OPERATOR_LOGINPWD_EMPTY);
        } else {
            try {
                // 转为小写
                loginName = StringUtils.lowerCase(loginName);
                //当member type 为2或者3的时候都可以登录入
                MemberDto memberget=memberService.findMemberByLoginName(loginName);
                System.out.println(memberget.getType());
                // 查询企业会员信息
                MemberDto member=null;
                if(memberget.getType()==2){
                	member = memberService.findMemberByLogin(loginName, new Integer(2));
                }else if(memberget.getType()==3){
                	member = memberService.findMemberByLogin(loginName, new Integer(3));
                }
                 
                if (member == null) {
                    // MEMBER_LOGINNAME_NULL_ERROR
                    result.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_PWD_ERROR);
                    // 设置登录失败次数
                    HttpServletRequest request = (HttpServletRequest) RequestLocal.getRequest();
                    LoginUtil.setLoginErrorNumLog(null, null, WebUtil.getIp(request));
                } else if (member.getStatus() == MemberStatusEnum.FROZEEN.getCode()) {
                    // 检查是否该会员被后台人员操作冻结
                    List<String> list = userLogService.findPossOperateObject(new Integer(1),
                        new Integer(1), String.valueOf(member.getMemberCode()));
                    if (list != null && list.size() > 0) {
                        result.setErrorNum(ErrorCodeEnum.MEMBER_LOGINPWD_FROZEEN);
                    } else {
                        // 开放会员登录行为所剩余的时间(小时)
                        long time = this.getLimitTime(member.getUpdateDate());
                        if (time < 0 || time == 0) {
                            return this.enLogin(result, identity, member, loginPwd);
                        }
                        result.setErrorNum(ErrorCodeEnum.MEMBER_STATUS_FROZEEN);
                        // 提示信息中加入开放会员登录行为所剩余的时间(小时)
                        result.setErrorMsg(StringUtils.replace(
                            ErrorCodeEnum.MEMBER_STATUS_FROZEEN.getMessage(),
                            LIMITTIME_REPLACE, String.valueOf(time)));
                    }
                } else if (member.getStatus() == MemberStatusEnum.CREATE.getCode()
                           || member.getStatus() == MemberStatusEnum.NO_ACTIVE.getCode()) {
                    result.setErrorNum(ErrorCodeEnum.MEMBER_INVALID);
                } else {
                    this.enLogin(result, identity, member, loginPwd);
                }
            } catch (Exception e) {
                logger.error("operatorDAO.memberLogin throws exception :", e);
                result.setErrorNum(ErrorCodeEnum.SYSTEM_BUSY);
            }
        }

        return result;
    }

    /**
     * 企业会员登录实现
     * 
     * @param result
     * @param identity
     * @param member
     * @param loginPwd
     * @return
     * @throws Exception
     */
    private ResultDto enLogin(ResultDto result, String identity, MemberDto member, String loginPwd)
                                                                                                   throws Exception {
        MemberCriteria memberInfo = null;
        Operator operator = operatorServiceFacade.getByIdentityMemCode(identity, member
            .getMemberCode());
        if (operator == null) {
            result.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_PWD_ERROR);
            // 设置登录失败次数
            HttpServletRequest request = (HttpServletRequest) RequestLocal.getRequest();
            LoginUtil.setLoginErrorNumLog(null, null, WebUtil.getIp(request));
        } else if (operator.getStatus() == OperatorStatusEnum.CLOSE.getValue()
                   || operator.getStatus() == OperatorStatusEnum.DELETE.getValue()
                   || operator.getStatus() == OperatorStatusEnum.CREATE.getValue()) {
            result.setErrorNum(ErrorCodeEnum.OPERATOR_IS_NOT_OPEN);

        } else if (!StringUtils.equals(operator.getPassword(), iMessageDigest//
                .genMessageDigest(loginPwd.getBytes()))) {

            // 设置登录密码失败次数
            LoginUtil.setLoginErrorNumLog(null, String.valueOf(operator.getOperatorId()), null);

            int failNum = LoginUtil.getLoginFailNum(null, String.valueOf(operator.getOperatorId()),
                null);
            // 获取密码输错次数最高限制
            String limitNum = AppConf.get(AppConf.limitNum);
            // 默认3次
            int limNum = 3;
            if (StringUtils.isNotBlank(limitNum)) {
                limNum = Integer.valueOf(limitNum);
            }
            
            // modified by meng.li，对于商户情况，密码输入1，2，3次情况都发消息记录登录失败信息，再多不计，防止恶意攻击
            if(failNum <= limNum){
            	HashMap<String, Object> request = new HashMap<String, Object>();
            	request.put(MaConstant.MEMBER_CODE, member.getMemberCode());
            	request.put(MaConstant.ERROR_TIME, new Date());
            	notifyFacadeService.sendRequest(MaConstant.FAIL_LOGIN_QUEUE_NAME, request);
            }
            // end of modify
            
            if (failNum >= limNum) {
                result.setErrorNum(ErrorCodeEnum.MEMBER_STATUS_FROZEEN);
                if (StringUtils.equals(operator.getName(), "admin")) {
                    // 管理员类型密码失败超过3次，将会员状态设置为冻结。
                    memberService.updateStatusByMemberCode(member.getMemberCode(),
                        MemberStatusEnum.FROZEEN.getCode());
                    HttpServletRequest request=(HttpServletRequest)RequestLocal.getRequest();
                    String clientIp = WebUtil.getIp(request);
            		if (StringUtils.isBlank(clientIp)) {
            			clientIp = "127.0.0.1";
            		} 
            		// 记录操作日志
                    userLogService.createPossOperateObject(String.valueOf(member.getMemberCode()), "website", clientIp, "企业会员冻结");
                    // 提示信息中加入开放会员登录行为所剩余的时间(小时)
                    
                    MemberDto memberDto = memberService.findByMemberCode(member.getMemberCode());
                    // 提示信息中加入开放会员登录行为所剩余的时间(小时)
                    result.setErrorMsg(StringUtils.replace(
                        ErrorCodeEnum.MEMBER_STATUS_FROZEEN.getMessage(),
                        LIMITTIME_REPLACE, String.valueOf(this.getLimitTime(memberDto.getUpdateDate()))));
                } else {
                    // 操作员类型密码失败超过3次，将操作员状态设置为关闭。
                    operatorServiceFacade.updateOperatorStatus(member.getMemberCode(), operator
                        .getOperatorId(), OperatorStatusEnum.CLOSE.getValue());
                    
                    // 记录操作日志
                    HttpServletRequest request=(HttpServletRequest)RequestLocal.getRequest();
                    String clientIp = WebUtil.getIp(request);
            		if (StringUtils.isBlank(clientIp)) {
            			clientIp = "127.0.0.1";
            		} 
                    userLogService.createPossOperateObject(String.valueOf(operator.getOperatorId()), "system", clientIp, "企业操作员冻结");
                    // 提示信息中加入开放会员登录行为所剩余的时间(小时)
                    Operator operatorInfo = operatorServiceFacade.getOperatorById(operator
                        .getOperatorId());
                    result.setErrorMsg(StringUtils.replace(
                        ErrorCodeEnum.MEMBER_STATUS_FROZEEN.getMessage(),
                        LIMITTIME_REPLACE, String.valueOf(this.getLimitTime(operatorInfo.getUpdateDate()))));
                }
                return result;
            } else {
                int num = limNum - failNum;
                result.setErrorNum(ErrorCodeEnum.MEMBER_LOGINPWD_FAILNUM);
                String errorMsg = ErrorCodeEnum.MEMBER_LOGINPWD_FAILNUM.getMessage();
                // 获取登录失败锁定会员时间限制
                String limitDate = AppConf.get(AppConf.limitDate);
                // 默认120分钟
                long limDate = 120;
                if (StringUtils.isNotBlank(limitDate)) {
                    limDate = Integer.valueOf(limitDate);
                }
                // 获取错误信息
                String msg = StringUtils.replace(errorMsg, ERRORMSG_REPLACE, String.valueOf(num))
                    .replace(SUMNUM_REPLACE, String.valueOf(limNum)).replace(LIMITTIME_REPLACE,
                        String.valueOf(limDate));
                result.setErrorMsg(msg);
            }
        } else {
            if (member.getStatus() == MemberStatusEnum.FROZEEN.getCode()) {
                if (StringUtils.equals(operator.getName(), "admin")) {
                    memberService.updateStatusByMemberCode(member.getMemberCode(),
                        MemberStatusEnum.NORMAL.getCode());
                    member = memberService.findMemberByLogin(member.getLoginName(), new Integer(2));
                } else {
                    operatorServiceFacade.updateOperatorStatus(member.getMemberCode(), operator
                        .getOperatorId(), OperatorStatusEnum.CLOSE.getValue());
                }
            }
            memberInfo = new MemberCriteria();
            memberInfo.setMemberCode(String.valueOf(member.getMemberCode()));
            memberInfo.setStatus(String.valueOf(member.getStatus()));
            memberInfo.setVerifyName(operator.getName());
            memberInfo.setSalutatory(member.getGreeting());
            memberInfo.setWelcomeMsg(member.getGreeting());
            memberInfo.setAnswer(member.getSecurityAnswer());
            memberInfo.setQuestionId(String.valueOf(member.getSecurityQuestion()));
            memberInfo.setUpdateDate(member.getUpdateDate());
            memberInfo.setRegType(String.valueOf(member.getLoginType()));
            memberInfo.setLoginName(member.getLoginName());
            memberInfo.setCreateDate(member.getCreateDate());
            memberInfo.setOperatorId(operator.getOperatorId());
            memberInfo.setMemberType(member.getType());
            // 服务级别
            memberInfo.setServiceLevel(member.getServiceLevelCode());
            // 获取上次登录时间
            String loginTime = memberOperateService.queryEpLastLoginTime(member.getMemberCode(), operator.getOperatorId());
            memberInfo.setLastLoginTime(loginTime);
            result.setObject(memberInfo);
            // 登录成功后，清除记录失败次数
            LoginUtil.clearLoginFailNum(null, String.valueOf(operator.getOperatorId()), null);
        }
        return result;

    }


    /** 开放会员登录行为所剩余的时间(分钟) */
    private long getLimitTime(Date updateDate) {
        Date nowdate = new Date();
        // 获取登录失败锁定会员时间限制
        String limitDate = AppConf.get(AppConf.limitDate);
        // 默认24小时
        int limDate = 120;
        if (StringUtils.isNotBlank(limitDate)) {
            limDate = Integer.valueOf(limitDate);
        }
        int minutes = limDate-((int)(System.currentTimeMillis()-updateDate.getTime())/60000);
        return minutes;
    }
    /**
     * 个人会员登录
     * 
     * @param loginName
     * @param loginPwd
     * @return
     * @see com.pay.base.service.user.UserLoginService#individualMemberLogin(java.lang.String,
     *      java.lang.String)
     */
    public ResultDto individualMemberLogin(String loginName, String loginPwd) {
        ResultDto result = new ResultDto();
        if (StringUtils.isEmpty(loginName)) {
            result.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_EMPTY);
        } else if (StringUtils.isEmpty(loginPwd)) {
            result.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_EMPTY);
        } else {
            try {
                // 转为小写
                loginName = StringUtils.lowerCase(loginName);
                // 检查是否为个人会员登录
                MemberDto memberDto = memberService.findMemberByLoginName(loginName);
                if(memberDto == null || memberDto.getType() == MemberTypeEnum.MERCHANT.getCode()){
                    result.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_PWD_ERROR);
                    return result;
                }
                // 校验个人会员登录
                MaResultDto maResultDto = memberQueryService.doLogin(loginName,loginPwd);
                Object obj = maResultDto.getObject();
                int resultStatus = maResultDto.getResultStatus();
                if (obj != null) {
                    /** 校验失败 **/
                    if (obj instanceof VerifyResultDto) {
                        VerifyResultDto verifyResult = (VerifyResultDto) obj;
                        // 会员已被锁定
                        if (resultStatus == MaConstant.ERROR_AND_LOCK) {
                            int limitTime = verifyResult.getLeavingMinute();
                            result.setErrorNum(ErrorCodeEnum.MEMBER_STATUS_FROZEEN);
                            // 提示信息中加入开放会员登录行为所剩余的时间(小时)
                            result.setErrorMsg(StringUtils.replace(
                                ErrorCodeEnum.MEMBER_STATUS_FROZEEN.getMessage(),
                                LIMITTIME_REPLACE, String.valueOf(limitTime)));
                        } else { // 密码输入错误次数
                            int limitTime = verifyResult.getLeavingMinute();
                            // 总次数
                            int totalNum = verifyResult.getTotalTime();
                            // 剩余输入次数
                            int faildNum = verifyResult.getLeavingTime();
                            result.setErrorNum(ErrorCodeEnum.MEMBER_LOGINPWD_FAILNUM);
                            String errorMsg = ErrorCodeEnum.MEMBER_LOGINPWD_FAILNUM.getMessage();
                            // 获取错误信息
                            String msg = StringUtils.replace(errorMsg, ERRORMSG_REPLACE,
                                String.valueOf(faildNum)).replace(SUMNUM_REPLACE,
                                String.valueOf(totalNum)).replace(LIMITTIME_REPLACE,
                                String.valueOf(limitTime));
                            result.setErrorMsg(msg);
                        }
                        /** 校验成功 **/
                    } else if (obj instanceof Member || obj instanceof com.pay.acc.member.dto.MemberDto) {
                         // 会员状态不正确.
                        if(resultStatus == MaConstant.FAILED_MEMBER_STATUS_EXCEPTION) {
                            result.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_PWD_ERROR);
                        } else {
                            Member member = BeanConvertUtil.convert(Member.class, obj);
                                IndividualInfoDto individualInfo = individualInfoService
                                    .queryIndividualInfoByMemberCode(member.getMemberCode());
                            if (individualInfo != null) {
                                MemberCriteria memberInfo = new MemberCriteria();
                                memberInfo.setMemberCode(String.valueOf(member.getMemberCode()));
                                memberInfo.setStatus(String.valueOf(member.getStatus()));
                                memberInfo.setVerifyName(individualInfo.getName());
                                memberInfo.setSalutatory(member.getGreeting());
                                memberInfo.setWelcomeMsg(member.getGreeting());
                                memberInfo.setAnswer(member.getSecurityAnswer());
                                memberInfo.setQuestionId(String.valueOf(member.getSecurityQuestion()));
                                memberInfo.setUpdateDate(member.getUpdateDate());
                                memberInfo.setRegType(String.valueOf(member.getLoginType()));
                                memberInfo.setLoginName(member.getLoginName());
                                memberInfo.setCreateDate(member.getCreateDate());
                                memberInfo.setMemberType(member.getType());
                                // 服务级别
                                memberInfo.setServiceLevel(member.getServiceLevelCode());
                                // 上次登录时间
                                memberInfo.setLastLoginTime(member.getLastLoginTime());
                                result.setObject(memberInfo);
                                result.setMemberCode(String.valueOf(member.getMemberCode()));
                            } else {
                                logger.warn("individualMemberLogin Error, individualInfo is null!LoginName=" + loginName);
                                result.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_PWD_ERROR);
                            }
                        }
                    } else {
                        logger
                            .warn("individualMemberLogin Error, maResultDto.getObject  Unreasonable!LoginName="
                                  + loginName + ";resultStatus=" + resultStatus);
                        result.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_NULL_ERROR);
                    }
                } else {
                    // 会员不存在
                    if (resultStatus == MaConstant.MEMBER_NOT_EXISTS) {
                        result.setErrorNum(ErrorCodeEnum.MEMBER_LOGINNAME_NULL_ERROR);
                    } else {
                        // 系统异常
                        logger.error("individualMemberLogin Error!LoginName="
                            + loginName + ";resultStatus=" + resultStatus+";ErrorMessage="+maResultDto.getErrorMsg());
                        result.setErrorNum(ErrorCodeEnum.SYSTEM_BUSY);
                    } 
                }
            } catch (Exception e) {
                logger.error("individualMemberLogin error!", e);
                result.setErrorNum(ErrorCodeEnum.SYSTEM_BUSY);
            }
        }

        return result;
    }


    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
        this.operatorServiceFacade = operatorServiceFacade;
    }

    public void setiMessageDigest(IMessageDigest iMessageDigest) {
        this.iMessageDigest = iMessageDigest;
    }

    public void setIndividualInfoService(IndividualInfoService individualInfoService) {
        this.individualInfoService = individualInfoService;
    }

    public void setUserLogService(UserLogService userLogService) {
        this.userLogService = userLogService;
    }

    public void setMemberQueryService(MemberQueryService memberQueryService) {
        this.memberQueryService = memberQueryService;
    }

    public void setMemberOperateService(MemberOperateService memberOperateService) {
        this.memberOperateService = memberOperateService;
    }

}
