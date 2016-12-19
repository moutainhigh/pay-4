package com.pay.app.controller.common;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.app.base.api.common.enums.BspIdentityEnum;
import com.pay.app.base.api.common.enums.BspOrgEnum;
import com.pay.app.base.session.LoginSession;
import com.pay.base.common.enums.ScaleEnum;
import com.pay.base.common.enums.SecurityLvlEnum;
import com.pay.base.dto.MemberRelationDto;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.service.enterprise.EnterpriseBaseService;
import com.pay.base.service.member.MemberRelationService;


public class UserHelper {
	
	private static final Log  logger = LogFactory.getLog(UserHelper.class);
	
	private EnterpriseBaseService  enterpriseBaseService;
	private MemberRelationService  memberRelationService;
    
	public  LoginSession handlerBspSession(Long memberCode,LoginSession loginSession){
		EnterpriseBase eb=enterpriseBaseService.findByMemberCode(memberCode);
		if(eb.getEnterpriseType().equals(BspOrgEnum.CORP_GG.getOrg())){
			loginSession.setBspOrgCode(BspOrgEnum.CORP_GG.getOrg());
    	}
		loginSession.setScaleType(ScaleEnum.TRADING_CENTER.getValue());
		loginSession.setBspIdentity(BspIdentityEnum.getCompareValue(eb.getIdentity()));
		if(eb.getIdentity()==BspIdentityEnum.CORP_TRADING.getIdentity()){
			MemberRelationDto mr=memberRelationService.getMemberRelationBySonMemberCode(memberCode);
			if(mr!=null){
				loginSession.setBspMemberCode(mr.getFatherMemberCode());
				loginSession.setUsteelName(mr.getSonUsteelName());
				loginSession.setBspUid(mr.getSonUsteelId());
				loginSession.setSecurityLvl(SecurityLvlEnum.BSP_MERCHANT.getValue());
			}
		}else if(eb.getIdentity()==BspIdentityEnum.CORP_TRADINGMANAGER.getIdentity()){
			loginSession.setSecurityLvl(SecurityLvlEnum.BSP_ADMIN.getValue());
		}
		//SessionHelper.setLoginSession(loginSession);
		return loginSession;
	}

	/**
	 * 判断商户是否为交易中心的合法商户（交易中心管理或交易商）。
	 * 如果是合法商户注入交易属性到商户。
	 * @param memberCode 交易商会员编号
	 * @param fatherMemberCode 交易中心的会员编号
	 * @param loginSession
	 * @return
	 */
	public boolean handlerSingleBspSession(Long memberCode,Long fatherMemberCode,LoginSession loginSession){
		EnterpriseBase eb=enterpriseBaseService.findByMemberCode(memberCode);
		
		if(eb != null){
			//商户是否为“交易中心管理员”
			if(BspIdentityEnum.CORP_TRADINGMANAGER.getIdentity() == eb.getIdentity()){
				if(logger.isInfoEnabled()){
					logger.info("会员号为："+memberCode+",父会员号为："+fatherMemberCode+",的商户为交易中心管理员！");
				}
			//	handlerBspSession(memberCode,loginSession);
			//交易中心不能通过联合登录
				return false;
			}else if(BspIdentityEnum.CORP_TRADING.getIdentity() == eb.getIdentity()){
			//商户如果为“交易商“
				EnterpriseBase feb = enterpriseBaseService.findByMemberCode(fatherMemberCode);
				if(feb != null && BspIdentityEnum.CORP_TRADINGMANAGER.getIdentity() == feb.getIdentity()){
					if(memberRelationService.isFatherAndSon(memberCode, feb.getMemberCode())){
						if(logger.isInfoEnabled()){
							logger.info("会员号为："+memberCode+",父会员号为："+fatherMemberCode+",的商户为交易中心的合法交易商！");
						}
						handlerBspSession(memberCode,loginSession);
						return true;
					}
				}
			}
		}
		
		if(logger.isInfoEnabled()){
			logger.info("会员号为："+memberCode+",父商户号为："+fatherMemberCode+",的商户来源非法！");
		}
		return false;
	}
	
	
	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setMemberRelationService(MemberRelationService memberRelationService) {
		this.memberRelationService = memberRelationService;
	}
	
	
}
