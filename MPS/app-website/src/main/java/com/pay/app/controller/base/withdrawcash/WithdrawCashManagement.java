package com.pay.app.controller.base.withdrawcash;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.SessionHelper;
import com.pay.app.service.bankacct.BankAcctService;
import com.pay.base.model.LiquidateInfo;
import com.pay.base.service.enterprise.LiquidateInfoService;
import com.pay.fundout.autofundout.service.AutoFundoutConfigService;
import com.pay.util.DESUtil;
import com.pay.util.StringUtil;



/**
 * @author xiaodai.Rainy
 *
 * @data 2015年12月22日下午3:48:34
 *  
 * @param
 */

public class WithdrawCashManagement extends MultiActionController{
	
	private String withdrawCashManagementView ;

	private  BankAcctService bankAcctService; 
	private LiquidateInfoService liquidateInfoService;
	private AutoFundoutConfigService autoFundoutConfigService;

	public void setAutoFundoutConfigService(
			AutoFundoutConfigService autoFundoutConfigService) {
		this.autoFundoutConfigService = autoFundoutConfigService;
	}


	public void setLiquidateInfoService(LiquidateInfoService liquidateInfoService) {
		this.liquidateInfoService = liquidateInfoService;
	}


	public void setBankAcctService(BankAcctService bankAcctService) {
		this.bankAcctService = bankAcctService;
	}

	

	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		//查询数据
		String memberCode = SessionHelper.getMemeberCodeBySession() ;
		List<LiquidateInfo> list= null ;
		if(null!= memberCode){
			//1.根据会员号查询银行账户信息
			list = bankAcctService.queryBankAcctByMemberCode(memberCode);
			// list.setBankAcct(DESUtil.decrypt(list.getBankAcct()));
			if(CollectionUtils.isNotEmpty(list)){
				for(LiquidateInfo liquidateInfo:list){
					//解密
					liquidateInfo.setBankAcct(DESUtil.decrypt(liquidateInfo.getBankAcct()));
				}
			}
		}
		return new ModelAndView(withdrawCashManagementView).addObject("list",list).addObject("bankCardNumber", list.size()) ;
	}

	public void setWithdrawCashManagementView(String withdrawCashManagementView) {
		this.withdrawCashManagementView = withdrawCashManagementView;
	}
	
	
	/**
	 * 删除提现银行卡
	 * @author xiaodai.Rainy
	 *
	 * @data 2015年12月25日下午3:13:19
	 * @param request
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView removeBankCard(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = SessionHelper.getMemeberCodeBySession() ;
		
			int cardCount = liquidateInfoService.getCountByMemberCode(Long.valueOf(memberCode));
			boolean isNotBindAutoFo = false;
			boolean resultBool = false;
			String errorMsg = "";
			if (cardCount != 1) { 
				Long id = Long.valueOf(StringUtil.null2String(request.getParameter("liquidateId"))) ;
				//先判断是否有绑定提现
				LiquidateInfo liquidateInfo =  liquidateInfoService.getById(id);
				if(liquidateInfo != null ){
					isNotBindAutoFo =   autoFundoutConfigService.findByMemberCodeAndBankCard(Long.valueOf(memberCode), liquidateInfo.getBankAcct(), String.valueOf(liquidateInfo.getBankId()));
					if(isNotBindAutoFo){
						resultBool =  liquidateInfoService.removeCorpAcctByMemberCodeAndId(memberCode, id);
					}else{
						errorMsg = "该卡已绑定,不能删除";
					}
				}else{
					errorMsg = "该卡不存在或已经被删除了";
				}
			}else{
				errorMsg = "删除失败，至少需要保留一张银行卡！";
			}
			return 	this.index(request, response).addObject("errorMsg", errorMsg);
	}
}
