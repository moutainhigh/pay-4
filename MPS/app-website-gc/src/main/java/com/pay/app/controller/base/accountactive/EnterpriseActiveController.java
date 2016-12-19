/*
 * Copyright © 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.controller.base.accountactive;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.checkcode.dto.CheckCodeDto;
import com.pay.acc.comm.CheckCodeOriginEnum;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.base.common.enums.MemberStatusEnum;
import com.pay.base.dto.EnterpriseInfo;
import com.pay.base.dto.MemberDto;
import com.pay.base.model.Operator;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.util.DateUtil;

/**
 * 企业会员激活
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-18 下午02:47:25
 * 
 */
public class EnterpriseActiveController extends MultiActionController {

	/** 限制时间(天) */
	private static final int LIMITED_TIME = 5;
	
	/** 激活状态码 2-已激活 */
	private static final int SUCCESSS_TATUS = 2;
	
	/** 会员激活信息服务 */
	private CheckCodeService checkCodeService;
	/** 操作员 */
    private OperatorServiceFacade  operatorServiceFacade;
    /** 会员基本信息服务 */
    private MemberService memberService;
    
	/** 已经激活过跳转页面 */
	private String activeReady;
	/** 失败页面 */
	private String activeFaile;
	/** 补全信息页面 */
	private String completionInfo;
	/** 链接失效信息页面 */
	private String activeInvalidEmail;
	
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String returnView=activeFaile;
		// Step[1] 根据code获取memberCode和loginName
		String checkCode = request.getParameter("code");
		if(StringUtils.isBlank(checkCode)){
			return new ModelAndView("redirect:/outapp.htm");
		}
		
		// 校验会员状态是否为创建状态或未激活状态
		int memberType = 1;//默认是1 个人，2为企业 
		Map<String,Object> map = new HashMap<String,Object>();
		CheckCodeDto ck = checkCodeService.getByCheckCodeAndOrigin(checkCode,CheckCodeOriginEnum.POSS_CORP_ACTIVE_REGISTER);
		if(ck == null){
			map.put("errMsg", MessageConvertFactory.getMessage("activeError"));
			ModelAndView mv = new ModelAndView(returnView, map);
			return mv;
		}
		
		MemberDto memberDto =   memberService.findByMemberCode(ck.getMemberCode());
		if(memberDto.getStatus() != MemberStatusEnum.CREATE.getCode() 
				&& memberDto.getStatus() != MemberStatusEnum.NO_ACTIVE.getCode()){
		    return new ModelAndView(activeReady);
		}
		else{
			CheckCodeDto lastCheckCode = checkCodeService.getByLastCheckCode(ck.getMemberCode(),CheckCodeOriginEnum.POSS_CORP_ACTIVE_REGISTER,1);
			if(lastCheckCode==null || lastCheckCode.getId() != ck.getId() ){ //不是最后一条
				map.put("errMsg", MessageConvertFactory.getMessage("activeError"));
				ModelAndView mv = new ModelAndView(returnView, map);
				return mv;
				
			}
			else{
				Operator operator = operatorServiceFacade.getByIdentityMemCode("admin", ck.getMemberCode());
				String verifyName = "";
				String operatorIdentity = "";
				EnterpriseInfo enterpriseInfo = new EnterpriseInfo();
				if(operator != null){
					// 获取用户真实姓名
					verifyName = operator.getName();
					// 操作员登录名
					operatorIdentity = operator.getIdentity();
					map.put("verifyName", verifyName);
					enterpriseInfo.setOldIdentity(operatorIdentity);
					map.put("operatorIdentity", operatorIdentity);
				}
				// 激活链接是否有效
				if(this.checkByActiveTime(ck.getCreateTime())){
					// 有效返回补全企业用户信息页面
					returnView = completionInfo;
				} else {
					// 失效
					map.put("isInvalid", "yes");
					map.put("limitedDay", LIMITED_TIME);
					map.put("email", memberDto.getLoginName());
					map.put("memberCode", memberDto.getMemberCode());
					map.put("checkCode", checkCode);
					map.put("loginName", memberDto.getLoginName());
					returnView = activeInvalidEmail;
				}
				enterpriseInfo.setLoginName(memberDto.getLoginName());
				enterpriseInfo.setMemberCode(ck.getMemberCode());
				enterpriseInfo.setCheckCode(ck.getCheckCode());
				memberType = 2;//企业 
				map.put("enterpriseInfo", enterpriseInfo);
				map.put("memberType", memberType);
				
			}
			
			ModelAndView mv = new ModelAndView(returnView, map);
			return mv;
		}
	}

	/**
	 * 检查激活链接是否有效<br>
	 * 有效则返回true,否则返回false;
	 * @return
	 */
	private boolean checkByActiveTime(Date createtime) {
		Date date = DateUtil.skipDateTime(createtime, LIMITED_TIME);
		if(date != null){
			// 当前时间早于成功发出激活信时间加有效天数(5天)的时间
			Date sysDate = new Date();
			if (sysDate.after(date)) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setActiveReady(String activeReady) {
		this.activeReady = activeReady;
	}

	public void setActiveFaile(String activeFaile) {
		this.activeFaile = activeFaile;
	}

	public void setCompletionInfo(String completionInfo) {
		this.completionInfo = completionInfo;
	}

	public void setActiveInvalidEmail(String activeInvalidEmail) {
		this.activeInvalidEmail = activeInvalidEmail;
	}

    public void setOperatorServiceFacade(OperatorServiceFacade operatorServiceFacade) {
        this.operatorServiceFacade = operatorServiceFacade;
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }
	
}
