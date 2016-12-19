/**
 *  File: AccountInfoController.java
 *  Description:
 *  Copyright (c) 2004-2013 pay.com . All rights reserved. 
 *  Date      Author      Changes
 *  2011-7-1   ddr     Create
 *
 */
package com.pay.app.controller.bsp;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.controller.base.accountactive.AccountInfoController;
import com.pay.app.model.BankAcct;
import com.pay.base.common.enums.ScaleEnum;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.MemberDto;
import com.pay.base.dto.MemberRelationDto;
import com.pay.base.model.Acct;
import com.pay.base.model.EnterpriseBase;
import com.pay.base.model.EnterpriseContact;
import com.pay.base.model.EnterpriseContract;
import com.pay.base.model.LiquidateInfo;
import com.pay.base.model.Operator;
import com.pay.base.service.enterprise.EnterpriseRegisterService;
import com.pay.base.service.member.MemberRelationService;
import com.pay.util.FormatNumber;

/**
 * 
 */
//@HasFeature({CutsConstants.FEATURE_MAXTRIX,CutsConstants.FEATURE_NORMAL})
public class BspAccountInfoController extends AccountInfoController {

	//交易中心查看交易商的页面
	private String bspInfoView;
	//交易中心或交易商查看自己的页面
	private String myBspfoView;
	
    
     
    //父子关系 查询
    private MemberRelationService memberRelationService;
    
    

	public void setEnterpriseRegisterService(
			EnterpriseRegisterService enterpriseRegisterService) {
		this.enterpriseRegisterService = enterpriseRegisterService;
	}

	/**
	 * @return the memberRelationService
	 */
	public MemberRelationService getMemberRelationService() {
		return memberRelationService;
	}

	/**
	 * @param memberRelationService the memberRelationService to set
	 */
	public void setMemberRelationService(MemberRelationService memberRelationService) {
		this.memberRelationService = memberRelationService;
	}


	/**
	 * @return the bspInfoView
	 */
	public String getBspInfoView() {
		return bspInfoView;
	}

	/**
	 * @param bspInfoView the bspInfoView to set
	 */
	public void setBspInfoView(String bspInfoView) {
		this.bspInfoView = bspInfoView;
	}

	/**
	 * @return the myBspfoView
	 */
	public String getMyBspfoView() {
		return myBspfoView;
	}

	/**
	 * @param myBspfoView the myBspfoView to set
	 */
	public void setMyBspfoView(String myBspfoView) {
		this.myBspfoView = myBspfoView;
	}

	/**
	 * 查看我的桌面
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//登录的身份验证
		LoginSession loginSession = SessionHelper.getLoginSession();
		Long memberCode = Long.valueOf((loginSession.getMemberCode()));
		long requestMemberCode = ServletRequestUtils.getLongParameter(request, "mbCode", -1L);
		String view = getBspInfoView();
		if(requestMemberCode>0 ){
			//判断是否有父子关系,如果有才执行
			if(memberCode != requestMemberCode ){
				boolean isFs =  memberRelationService.isFatherAndSon(requestMemberCode, memberCode);
				if(isFs){
					memberCode = requestMemberCode;
				}else{
					return new ModelAndView("redirect:/error.htm?method=noFeature");
				}
			}else{
				view = getMyBspfoView(); 
			}
			
		}		
		MemberCriteria memberCriteria = baseMemberService.queryMemberCriteriaByMemberCodeNsTx(memberCode);
		
		Map<String,Object> paraMap = new HashMap<String,Object>();
		paraMap.put("memberCriteria", memberCriteria);
		 // 会员是否通过实名认证
        boolean boolvfy = verify2GovService.checkQueryCidVerify(memberCode+"");
        if(boolvfy){//已实名认证
            paraMap.put("qdbool","true");
        }else{
            paraMap.put("qdbool","false");
        }
		paraMap.put("type",ScaleEnum.TRADING_CENTER.getValue());
		Acct acct = acctService.getByMemberCode(memberCode,  AcctTypeEnum.BASIC_CNY.getCode());
        paraMap.put("acct", acct);
        
        /* balance  frozenAmount 显示为实现值 */
    	BigDecimal balance=(acct == null ? new BigDecimal(0)  : acct.getBalance());
		BigDecimal frozenAmount=(acct == null ? new BigDecimal(0) : acct.getFrozenAmount());
		paraMap.put("acct_balance", FormatNumber.decimalsRound(balance.divide(new BigDecimal(1000)), 2));
		paraMap.put("acct_frozenAmount", FormatNumber.decimalsRound(frozenAmount.divide(new BigDecimal(1000)), 2));
		
        AcctAttribDto attribDto =  verify2GovService.getAccountAcctAttrib(Long.valueOf(memberCode),  AcctTypeEnum.BASIC_CNY.getCode());
        paraMap.put("attribDto", attribDto);
//		if(SessionHelper.isCorpLogin()){
		    long mCode = Long.valueOf(memberCode);
            // 获取操作员总数
            int operatorNum = operatorServiceFacade.queryCountByMemberCode(memberCode);
            paraMap.put("operatorNum", operatorNum);
            Operator operator = operatorServiceFacade.getAdminOperator(memberCode);
            paraMap.put("operatorIdentity", operator.getIdentity());
            MemberDto memberDto = this.baseMemberService.findByMemberCode(memberCode);
            paraMap.put("loginName", memberDto.getLoginName());
            // 获取企业会员基本信息
            EnterpriseBase enterpriseBase = enterpriseBaseService.findByMemberCode(mCode);
            if(enterpriseBase != null){
                paraMap.put("enterpriseBase", enterpriseBase);
            }
            // 获取企业会员联系信息
            EnterpriseContact enterpriseContact = enterpriseContactService.findByMemberCode(mCode);
            if(enterpriseContact != null){
                paraMap.put("enterpriseContact", enterpriseContact);
            }
            StringBuffer address = new StringBuffer();
            String region = enterpriseBase.getRegion();
            String city = enterpriseBase.getCity();
            if(StringUtils.isNotBlank(region)){
                region = provinceServiceFacade.getProvinceById(Integer.valueOf(region));
                address.append(region).append("&nbsp;&nbsp;");
            }
            if(StringUtils.isNotBlank(city)){
                city = provinceServiceFacade.getCityById(Integer.valueOf(city));
                address.append(city).append("&nbsp;&nbsp;");
            }
            if(StringUtils.isNotEmpty(enterpriseContact.getAddress())){
                address.append(enterpriseContact.getAddress());
            }
            if(StringUtils.isNotEmpty(enterpriseContact.getZip())){
            	address.append("&nbsp;("+enterpriseContact.getZip()+")");
            }
            paraMap.put("address", address.toString());
            // 获取企业会员结算银行信息
            List<LiquidateInfo> liquidateInfoList = liquidateInfoService.getByMemberCode(mCode);
            paraMap.put("liquidateList", liquidateInfoList);

          //获取账户信息
            
			EnterpriseContract econtract = enterpriseRegisterService
					.getContractByMemberCode(mCode);
            paraMap.put("econtract", econtract);
            
            MemberRelationDto memberRelationDto =  memberRelationService.getMemberRelationBySonMemberCode(memberCode);
            paraMap.put("memberRelation", memberRelationDto);
        List<BankAcct> bankList = bankAcctService.getBankAcctByCode(memberCode+"");
        int bankNum=bankAcctService.getUserBankAcctNum(memberCode+"");
        paraMap.put("bankList", bankList);
        paraMap.put("bankNum", bankNum);
		return new ModelAndView(view,paraMap);
	}

    public ModelAndView myBspInfo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	
    	Map<String,Object> map = index(request, response).getModelMap(); 
    	return new ModelAndView(myBspfoView,map);
    	
    	
    }
    
    
	
	
}

