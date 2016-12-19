/**
 *Copyright (c) 2006-2010 pay.com,Inc. All Rights Reserved.
 *@Project_Name app-website 
 *@CreateDate 下午08:43:41 2010-11-9
 */
package com.pay.app.controller.base.individualseller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.common.enums.ScaleEnum;
import com.pay.base.dto.MemberDto;
import com.pay.base.service.individualseller.IndividualSellerService;
import com.pay.base.service.member.MemberService;
import com.pay.app.common.individualseller.IndividualSellerEnum;


/**
 * @author Sunny Ying
 * @description TODO
 * @version 下午08:43:41 & 2010-11-9
 */

public class IndividualSellerController extends MultiActionController{

	private String unCidVerify;
	private String individualSellerIndex;
	private String success;
	private String exitsSeller;
	private String fail;
	private String justSeller;
	private String openAccount;
	private String openCardAccount;
	private String addCardAccountSuccess;
	private String addCardAccountFail;
	
	private IndividualSellerService individualSellerService;
	private MemberService memberService;
	
	
	/** 只开通了个人卖家身份 **/
	private static final int justSellerPurview = 1;

	/** 开通了个人卖家身份并且开通了 点账户 **/
	private static final int sellerAccountPurview = 2;
	
	/** 开通了个人卖家身份并且开通了 点账户 **/
	private static final int sellerAccount = 3;

	
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		LoginSession loginSession = SessionHelper.getLoginSession();	
		String memberCode = loginSession.getMemberCode().toString();
		boolean result_bool = true;
		result_bool = individualSellerService.checkUserCidVerify(memberCode);
		if(!result_bool){
			return new ModelAndView(unCidVerify);
		}
		MemberDto memberDto = memberService.findByMemberCode(Long.parseLong(memberCode));
		if(memberDto.getServiceLevelCode() == IndividualSellerEnum.sellerCode || memberDto.getServiceLevelCode() == IndividualSellerEnum.paySellerCode){
			return new ModelAndView(exitsSeller);//个人卖家已经开通
			}
//		if(memberDto.getType() == sellerAccount){
//			loginSession.setScaleType(ScaleEnum.INDIVIDUAL_SELLER.getValue());
//			return new ModelAndView(openAccount);
//		}
		return new ModelAndView(individualSellerIndex);
	}
	
	public ModelAndView applySeller(HttpServletRequest request,HttpServletResponse response){

		LoginSession loginSession = SessionHelper.getLoginSession();	
		String memberCode = loginSession.getMemberCode().toString();
		boolean result_bool = true;
		result_bool = individualSellerService.checkUserCidVerify(memberCode);
		if(!result_bool){
			return new ModelAndView(unCidVerify);
		}
		MemberDto memberDto = memberService.findByMemberCode(Long.parseLong(memberCode));
//		if(memberDto.getType() == sellerAccount){
//			return new ModelAndView(openAccount);//个人卖家已经开通，去开通 点卡账户的页面
//		}
		if(memberDto.getServiceLevelCode() == IndividualSellerEnum.sellerCode || memberDto.getServiceLevelCode() == IndividualSellerEnum.paySellerCode){
		return new ModelAndView(exitsSeller);//个人卖家已经开通
		}
//		if(loginSession.getServiceLevel() == IndividualSellerEnum.sellerCode){
//			return new ModelAndView(exitsSeller);
//		}
		String szxAccount = request.getParameter("szx") == null ?"":request.getParameter("szx");
		String dxAccount = request.getParameter("dx") == null ?"":request.getParameter("dx");
		String ltAccount = request.getParameter("lt") == null ?"":request.getParameter("lt");
		String jwAccount = request.getParameter("jw") == null ?"":request.getParameter("jw");
		Map<String,String> map = new HashMap<String,String>();
		map.put("memberCode", memberCode);
		map.put("szxAccount", szxAccount);
		map.put("dxAccount", dxAccount);
		map.put("ltAccount", ltAccount);
		map.put("jwAccount", jwAccount);
		int serviceCode = IndividualSellerEnum.sellerCode;
		if(memberDto.getServiceLevelCode() == IndividualSellerEnum.payCode){
			serviceCode = IndividualSellerEnum.paySellerCode;
		}
		int result =individualSellerService.editMemberTypeByMemberCode(serviceCode,memberCode);
//		if(!"".equals(szxAccount) || !"".equals(dxAccount) || !"".equals(ltAccount) || !"".equals(jwAccount)){
//			result =individualSellerService.openUserAccountRdTx(map);
//		}
		if(result == justSellerPurview){
	//		loginSession.setScaleType(ScaleEnum.INDIVIDUAL_SELLER.getValue());
			return new ModelAndView(justSeller);//只开通了个人卖家页面
		}
		if(result == sellerAccountPurview){
	//		loginSession.setScaleType(ScaleEnum.INDIVIDUAL_SELLER.getValue());
			return new ModelAndView(success);//开通了个人卖家并且开通了 点账户
		}	
		return new ModelAndView(fail);
	}
	
	public ModelAndView openCardAccount(HttpServletRequest request,HttpServletResponse response){
		LoginSession loginSession = SessionHelper.getLoginSession();	
		String memberCode = loginSession.getMemberCode().toString();
		boolean result_bool = true;
		result_bool = individualSellerService.checkUserCidVerify(memberCode);
		if(!result_bool){
			return new ModelAndView(unCidVerify);
		}
		MemberDto memberDto = memberService.findByMemberCode(Long.parseLong(memberCode));
		if(memberDto.getType() == sellerAccount){
			loginSession.setScaleType(ScaleEnum.INDIVIDUAL_SELLER.getValue());
			Map<String,String> map = new HashMap<String,String>();
			map.put("memberCode", memberCode);
			map.put("szxAccount", "szxAccount");
			map.put("dxAccount", "dxAccount");
			map.put("ltAccount", "ltAccount");
			map.put("jwAccount", "jwAccount");
			map = individualSellerService.isExitsCardAccount(map);		
			return new ModelAndView(openCardAccount).addAllObjects(map);
		}
		return new ModelAndView(individualSellerIndex);
	}
	
	public ModelAndView setCardAccount(HttpServletRequest request,HttpServletResponse response){
		LoginSession loginSession = SessionHelper.getLoginSession();	
		String memberCode = loginSession.getMemberCode().toString();
		boolean result_bool = true;
		result_bool = individualSellerService.checkUserCidVerify(memberCode);
		if(!result_bool){
			return new ModelAndView(unCidVerify);
		}
		MemberDto memberDto = memberService.findByMemberCode(Long.parseLong(memberCode));
		if(memberDto.getServiceLevelCode() == IndividualSellerEnum.sellerCode || memberDto.getServiceLevelCode() == IndividualSellerEnum.paySellerCode){
			return new ModelAndView(exitsSeller);
		}
//		if(loginSession.getServiceLevel() == IndividualSellerEnum.sellerCode || loginSession.getServiceLevel() == IndividualSellerEnum.paySellerCode){
//			return new ModelAndView(exitsSeller);
//		}
		String szxAccount = request.getParameter("szx") == null ?"":request.getParameter("szx");
		String dxAccount = request.getParameter("dx") == null ?"":request.getParameter("dx");
		String ltAccount = request.getParameter("lt") == null ?"":request.getParameter("lt");
		String jwAccount = request.getParameter("jw") == null ?"":request.getParameter("jw");
		Map<String,String> map = new HashMap<String,String>();
		map.put("memberCode", memberCode);
		map.put("szxAccount", szxAccount);
		map.put("dxAccount", dxAccount);
		map.put("ltAccount", ltAccount);
		map.put("jwAccount", jwAccount);
//		int result = individualSellerService.openUserAccountRdTx(map);
//		if(result == sellerAccountPurview){
//			loginSession.setScaleType(ScaleEnum.INDIVIDUAL_SELLER.getValue());
//			return new ModelAndView(addCardAccountSuccess);//开通了个人卖家并且开通了 点账户
//		}
		return new ModelAndView(addCardAccountFail);
	}

	public void setUnCidVerify(String unCidVerify) {
		this.unCidVerify = unCidVerify;
	}

	public void setIndividualSellerIndex(String individualSellerIndex) {
		this.individualSellerIndex = individualSellerIndex;
	}

	public void setIndividualSellerService(
			IndividualSellerService individualSellerService) {
		this.individualSellerService = individualSellerService;
	}



	public void setSuccess(String success) {
		this.success = success;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public void setJustSeller(String justSeller) {
		this.justSeller = justSeller;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setOpenAccount(String openAccount) {
		this.openAccount = openAccount;
	}

	public void setOpenCardAccount(String openCardAccount) {
		this.openCardAccount = openCardAccount;
	}

	public void setAddCardAccountSuccess(String addCardAccountSuccess) {
		this.addCardAccountSuccess = addCardAccountSuccess;
	}

	public void setAddCardAccountFail(String addCardAccountFail) {
		this.addCardAccountFail = addCardAccountFail;
	}

	public void setExitsSeller(String exitsSeller) {
		this.exitsSeller = exitsSeller;
	}
	
}
