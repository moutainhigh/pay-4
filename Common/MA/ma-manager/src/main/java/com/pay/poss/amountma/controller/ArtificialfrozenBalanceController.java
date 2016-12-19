package com.pay.poss.amountma.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.acct.dto.MemberAcctDto;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.balancelog.dto.UnFrozenAmountDto;
import com.pay.util.RandomUtil;
import com.pay.util.StringUtil;

/**
 * 
 * @Description 人工资金冻结管理
 * @project ma-manager
 * @file ArtificialfrozenBalanceController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 woyo Corporation. All rights reserved. Date
 *          Author Changes 2012-6-11 gungun_zhang Create
 */
public class ArtificialfrozenBalanceController extends MultiActionController {

	private AcctService acctService;

	private String indexView;
	private String editView;
	private String listView;
	private String detailView;

	public AcctService getAcctService() {
		return acctService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public String getIndexView() {
		return indexView;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public String getEditView() {
		return editView;
	}

	public void setEditView(String editView) {
		this.editView = editView;
	}

	public String getListView() {
		return listView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public String getDetailView() {
		return detailView;
	}

	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(indexView);
	}

	public ModelAndView searchFrozenBalance(HttpServletRequest request,
			HttpServletResponse response) {

		String type = ServletRequestUtils.getStringParameter(request, "type",
				"1");
		String typeValue = ServletRequestUtils.getStringParameter(request,
				"typeValue", "");
		MemberAcctDto dto = null;
		if (type.equals("1")) {
			// 根据登陆名查询账户信息
			dto = acctService.queryMemberAcctByloginName(typeValue);
		} else if (type.equals("2")) {
			// 根据会员号查询账户信息
			dto = acctService.queryMemberAcctByMemberCode(Long
					.valueOf(typeValue));

		}
		BigDecimal frozenAmount = new BigDecimal(0);
		BigDecimal isforzenAmount = new BigDecimal(0);
		//查询出冻结金额
		
		if(!StringUtil.isNull(dto)){
		    frozenAmount = acctService.getPossFrozenAmount(dto.getMemberCode());
		    isforzenAmount = dto.getFrozenAmount().subtract(frozenAmount);
		}
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", dto);
		map.put("isfrozenAmount", isforzenAmount);
		return new ModelAndView(listView, map);
	}

	public ModelAndView freeFrozenBalance(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
				
		 Long memberCode = ServletRequestUtils.getLongParameter(request, "memberCode",0L);
		 String  acctCode = ServletRequestUtils.getStringParameter(request, "acctCode","");
		 String unFrozenAmount = ServletRequestUtils.getStringParameter(request, "unFrozenAmount","");
		 String unFrozenAmountDouble =String.valueOf(Double.valueOf(unFrozenAmount)* (1000));
		 BigDecimal freeFrozenAmount = new BigDecimal(unFrozenAmountDouble);
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		 Date date = new Date();
		 String orderSeqNo = "300" + sdf.format(date) + RandomUtil.randomDegital(2);
		 
		 UnFrozenAmountDto dto = new UnFrozenAmountDto();
		 dto.setMemberCode(memberCode);
		 dto.setAcctCode(acctCode);
		 dto.setUnFrozenAmount(freeFrozenAmount);
		 dto.setOrderSeqNo(orderSeqNo);
		 response.setContentType("text/plain;charset=UTF-8");
		 response.setHeader("Cache-Control", "no-cache");
		
		 boolean isok =false; 
		 String exceptionMsg = ""; 
		 try{ 
			 isok =acctService.unFrozenAmount(dto); 
		 if(! isok){
		     exceptionMsg = "确认是否已被解冻"; } 
		 }catch (Exception e) { 
			 isok = false;
		     exceptionMsg = "，后台操作异常"; 
		    }
		 response.getWriter().write(isok?"S":("解冻不成功"+exceptionMsg));
		 
		return null;
	}

	public ModelAndView getFrozenBalanceDetail(HttpServletRequest request,
			HttpServletResponse response) {

		Long id = ServletRequestUtils.getLongParameter(request, "id", 0L);
		String frozenAmount = ServletRequestUtils.getStringParameter(request, "isfrozenAmount","");
		BigDecimal isforzenAmount = new BigDecimal(frozenAmount);
		MemberAcctDto dto = acctService.queryMemberAcctByMemberCode(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", dto);
		map.put("isfrozenAmount",isforzenAmount);
		return new ModelAndView(detailView, map);
	}
}
