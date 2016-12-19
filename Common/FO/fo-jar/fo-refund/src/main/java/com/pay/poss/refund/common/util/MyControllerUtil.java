 /** @Description 
 * @project 	poss-refund
 * @file 		MyControllerUtil.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-30		sunsea.li		Create 
*/
package com.pay.poss.refund.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import com.pay.poss.refund.model.RefundOrderD;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.model.WebQueryRefundDTO;
import com.pay.util.DateUtil;

public class MyControllerUtil {
	/**
	 * 通过request组装RefundOrderM
	 * 批量申请的时候用到
	 */
	public static RefundOrderM wrapModel(HttpServletRequest request) {
		RefundOrderM mDto = new RefundOrderM();
		mDto.setBalance(new BigDecimal(request.getParameter("balance")));
	  	mDto.setMemberCode(request.getParameter("account"));
	  	mDto.setMemberName(request.getParameter("userName"));
	  	mDto.setMemberAcc(request.getParameter("acctCode"));
	  	mDto.setMemberType(request.getParameter("memberType"));
	  	mDto.setMemberAccType(new Integer(request.getParameter("accountType")));
	  	mDto.setMemberLevel(new Integer(request.getParameter("levelCode")));
	  	mDto.setOperatorIp(request.getRemoteAddr());
	  	mDto.setApplyFrom("POSS");
	  	//mDto.setApplyReason("没钱用了");
	  	//mDto.setStatus(new Integer(0));
	  	
	  	BigDecimal applyTotalAmount = new BigDecimal(0);
	  	//下面来组装明细信息
	  	String[] selected=request.getParameterValues("ifRefund");
	  	String[] rechargeOrderSeq=request.getParameterValues("rechargeOrderSeq");
	  	String[] rechargeBankSeq=request.getParameterValues("rechargeBankSeq");
	  	String[] rechargeAmount=request.getParameterValues("rechargeAmount");
	  	String[] rechargeTime=request.getParameterValues("rechargeTime");
	  	String[] rechargeBank=request.getParameterValues("rechargeBank");
	  	String[] applyAmount=request.getParameterValues("applyAmount");
	  	String[] applyReason=request.getParameterValues("applyReason");
	  	String[] applyMax=request.getParameterValues("applyMax");
	  	String[] rechargeBankOrder=request.getParameterValues("rechargeBankOrder");
	  	String[] rechargeChannel=request.getParameterValues("rechargeChannel");
	  	String[] depositTypeName=request.getParameterValues("depositTypeName");
	  	
	  	if(null != selected){
	  		int n = selected.length;//已经选择的列表长度
	  		//int m = rechargeOrderSeq.length;//总列表的长度
	  		List<RefundOrderD> listDetails = new ArrayList<RefundOrderD>();
	  		for (int i = 0; i < n; i++) {
	  			RefundOrderD dDto = new RefundOrderD();
	  			if(!StringUtils.isEmpty(rechargeOrderSeq[i])){
	  				dDto.setRechargeOrderSeq(new Long(rechargeOrderSeq[i]));
	  			}
	  			if(!StringUtils.isEmpty(rechargeBankSeq[i])){
	  				dDto.setRechargeBankSeq(rechargeBankSeq[i]);
	  			}
	  			if(!StringUtils.isEmpty(rechargeAmount[i])){
	  				dDto.setRechargeAmount(new BigDecimal(rechargeAmount[i]));
	  			}
	  			if(!StringUtils.isEmpty(applyAmount[i])){
	  				dDto.setApplyAmount(new BigDecimal(applyAmount[i].replaceAll(",", "")).multiply(new BigDecimal(1000)));
	  			}
	  			if(!StringUtils.isEmpty(applyAmount[i])){
	  				applyTotalAmount = applyTotalAmount.add(new BigDecimal(applyAmount[i].replaceAll(",", "")).multiply(new BigDecimal(1000)));
	  			}
	  			if(!StringUtils.isEmpty(rechargeTime[i])){
	  				dDto.setRechargeTime(DateUtil.parse("yyyy-MM-dd HH:mm:ss", rechargeTime[i]));
	  			}
	  			if(!StringUtils.isEmpty(rechargeBank[i])){
	  				dDto.setRechargeBank(rechargeBank[i]);
	  			}
	  			if(!StringUtils.isEmpty(applyReason[i])){
	  				dDto.setApplyRemark(applyReason[i]);
	  			}
	  			if(!StringUtils.isEmpty(applyMax[i])){
	  				dDto.setApplyMax(new BigDecimal(applyMax[i]));
	  			}
	  			if(!StringUtils.isEmpty(rechargeBankOrder[i])){
	  				dDto.setRechargeBankOrder(rechargeBankOrder[i]);
	  			}
	  			if(!StringUtils.isEmpty(rechargeChannel[i])){
	  				dDto.setRechargeChannel(rechargeChannel[i]);
	  			}
	  			if(!StringUtils.isEmpty(depositTypeName[i])){
	  				dDto.setDepositTypeName(depositTypeName[i]);
	  			}
	  			dDto.setShowPosition(new Integer(1));
	  			dDto.setErrorTip("初始");
	  			listDetails.add(dDto);
	  		}
	  		mDto.setApplyAmount(applyTotalAmount);//申请总金额
	  		mDto.setListDetails(listDetails);
	  	}
	  	return mDto;
	}
	
	/**
	 * 通过request组装List<String>
	 * 批量审核的时候用到
	 */
	public static void wrapFileKyList(HttpServletRequest request,WebQueryRefundDTO webQueryRefundDTO) {
		List<String> fileKys = new ArrayList<String>();
	  	String[] selected=request.getParameterValues("ifChecked");
	  	String[] fileKy=request.getParameterValues("fileKy");
	  	
	  	if(null != selected){
	  		int n = selected.length;//已经选择的列表长度
	  		//int m = acceptKy.length;//总列表的长度
	  		for (int i = 0; i < n; i++) {
	  			if(!StringUtils.isEmpty(fileKy[new Integer(selected[i]).intValue()])){
	  				fileKys.add(fileKy[new Integer(selected[i]).intValue()]);
	  			}
	  		}
	  	}
	  	webQueryRefundDTO.setFileKys(fileKys);
	  	return;
	}
	
	/**
	 * 通过request组装List<RefundOrderM>
	 * 批量审核的时候用到
	 */
	public static List<RefundOrderM> wrapList(HttpServletRequest request) {
		List<RefundOrderM> requestList = new ArrayList<RefundOrderM>();
		
	  	String[] selected=request.getParameterValues("ifRefund");
	  	String[] acceptKy=request.getParameterValues("acceptKy");
	  	
	  	if(null != selected){
	  		int n = selected.length;//已经选择的列表长度
	  		//int m = acceptKy.length;//总列表的长度
	  		for (int i = 0; i < n; i++) {
	  			RefundOrderM mDto = new RefundOrderM();
	  			if(!StringUtils.isEmpty(acceptKy[new Integer(selected[i]).intValue()])){
	  				mDto.setOrderKy(new Long(acceptKy[new Integer(selected[i]).intValue()]));
	  			}
	  			requestList.add(mDto);
	  		}
	  	}
	  	return requestList;
	}
}
