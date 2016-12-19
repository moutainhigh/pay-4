/**
 *  <p>File: FoRcLimitFacadeImpl.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: (c) 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author zengli
 *  @version 1.0  
 */
package com.pay.rm.impl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.MemberVerifyService;
import com.pay.acc.service.member.dto.MemberVerifyResult;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.base.exception.RMFacadeException;
import com.pay.rm.facade.RcLimitRuleFacade;
import com.pay.rm.facade.dto.RCLimitParamDTO;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCMEMBERTYPE;

/**
 * <p>风控规则提供封装类</p>
 * @author zengli
 * @since 2011-5-12
 * @see 
 */
public class FoRcLimitFacadeImpl implements FoRcLimitFacade {
	private Log logger = LogFactory.getLog(FoRcLimitFacadeImpl.class);
	/**
	 * @param rcLimitRuleFacade the rcLimitRuleFacade to set
	 */
	public void setRcLimitRuleFacade(RcLimitRuleFacade rcLimitRuleFacade) {
		this.rcLimitRuleFacade = rcLimitRuleFacade;
	}


	private RcLimitRuleFacade rcLimitRuleFacade;
	
	private MemberQueryService memberQueryService;
	
	private MemberVerifyService memberVerifyService;
	
	/**
	 * @param memberQueryService the memberQueryService to set
	 */
	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}


	public RCLimitResultDTO getRule(RCLimitParamDTO rcLimitParamDTO) throws RMFacadeException{
		if(logger.isInfoEnabled()){
			logger.info("调用风控规则入参:"+rcLimitParamDTO);
		}
		return rcLimitRuleFacade.getRule(rcLimitParamDTO);
	}
	
	/**
	 * 获取企业限额
	 * @param busiType
	 * @param mccCode
	 * @param memberCode
	 * @return
	 */
	public RCLimitResultDTO getBusinessRcLimit(int busiType,Long mccCode,long memberCode){
		Assert.notNull(busiType,"busiType is not be null");
		Assert.notNull(memberCode,"memberCode is not be null");
		RCLimitParamDTO rcLimitParamDTO = new RCLimitParamDTO();
		rcLimitParamDTO.setBusiType(busiType);
		if(mccCode == null){
			rcLimitParamDTO.setMccCode(0l);
		}else{
			rcLimitParamDTO.setMccCode(mccCode);
		}
		rcLimitParamDTO.setMemberType(RCMEMBERTYPE.ENTERPRISE.getValue());
		rcLimitParamDTO.setMemberCode(memberCode);
		try {
			Long levelCode = memberQueryService.queryEnterpriseRiskLeveCode(memberCode);
			if(null != levelCode){
				rcLimitParamDTO.setLevel(levelCode.intValue());
			}
			
			return getRule(rcLimitParamDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}
	/**
	 * 获取企业限额
	 * @param busiType
	 * @param mccCode
	 * @param memberCode
	 * @return
	 */
	public RCLimitResultDTO getUserRcLimit(int busiType,Long mccCode,long memberCode) {
		RCLimitParamDTO rcLimitParamDTO = new RCLimitParamDTO();
		rcLimitParamDTO.setBusiType(busiType);
		if(mccCode == null){
			rcLimitParamDTO.setMccCode(0l);
		}else{
			rcLimitParamDTO.setMccCode(mccCode);
		} 
		rcLimitParamDTO.setMemberCode(memberCode);
		rcLimitParamDTO.setMemberType(RCMEMBERTYPE.PERSONAL.getValue());
		try {
			MemberVerifyResult isVerify = memberVerifyService.QueryMemberVerifyByMemberCode(Long.valueOf(memberCode));
			rcLimitParamDTO.setLevel(isVerify.getMemberLevel());
			return getRule(rcLimitParamDTO);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}


	/**
	 * @param memberVerifyService the memberVerifyService to set
	 */
	public void setMemberVerifyService(MemberVerifyService memberVerifyService) {
		this.memberVerifyService = memberVerifyService;
	}
}
