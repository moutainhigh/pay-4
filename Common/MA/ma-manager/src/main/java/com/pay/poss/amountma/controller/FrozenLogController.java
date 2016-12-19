package com.pay.poss.amountma.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.model.Acct;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.amountma.dto.FrozenLogDto;
import com.pay.poss.amountma.service.IFrozenLogService;
import com.pay.util.StringUtil;

/**
 * 
 * @Description
 * @project ma-manager
 * @file FrozenLogController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *          Date Author Changes 2010-12-19 DDR Create
 */

public class FrozenLogController extends MultiActionController {

	private String indexView;
	private String editView;
	private String listView;
	private String detailView;

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

	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}

	private IFrozenLogService frozenLogService;

	public void setFrozenLogService(IFrozenLogService frozenLogService) {
		this.frozenLogService = frozenLogService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, FrozenLogDto frozenLogDto) {
		AcctTypeEnum []  basicAccts =  AcctTypeEnum.getBasicAcctTypes();
		AcctTypeEnum []  guaranteeAccts =  AcctTypeEnum.getGuaranteeAcctTypes();
		//基本户和保证金户合并 add by tom.wang
		AcctTypeEnum[] accts = new AcctTypeEnum[basicAccts.length+ guaranteeAccts.length];
		System.arraycopy(basicAccts, 0, accts, 0, basicAccts.length);
		System.arraycopy(guaranteeAccts, 0, accts, basicAccts.length,guaranteeAccts.length);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("acctType",accts);
		
		return new ModelAndView(indexView,map);
	}

	public ModelAndView searchFrozenLog(HttpServletRequest request,
			HttpServletResponse response, FrozenLogDto frozenLogDto) {
		Page<FrozenLogDto> paramPage = PageUtils.getPage(request);
		Page<FrozenLogDto> page = frozenLogService.search(paramPage,
				frozenLogDto);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		
		return new ModelAndView(listView, map);
	}

	public ModelAndView searchBlance(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String nameOrCode = ServletRequestUtils.getStringParameter(request,
				"nameOrCode", "");
		Integer fmt = ServletRequestUtils.getIntParameter(request, "format", 0);
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		if (StringUtil.isEmpty(nameOrCode)) {
			response.getWriter().write("账户或登录名必须输入");
			return null;
		}
		
		String acctType = request.getParameter("acctType");

		BigDecimal bic = frozenLogService.getBlanceByLongNameOrCode(nameOrCode,Integer.valueOf(acctType));
		
		if (bic == null) {
			response.getWriter().write("未得到对应的用户的可用金额，请确认输入是否正确");
			return null;
		}
		String fmtBlance = bic.toString();
		if (fmt == 1) {
			fmtBlance = DecimalFormat.getInstance().format(bic);
		}
		response.getWriter().write(fmtBlance);

		return null;
	}

	public ModelAndView addFrozenLog(HttpServletRequest request,
			HttpServletResponse response)
			throws ServletRequestBindingException, IOException {
		String amount = ServletRequestUtils.getStringParameter(request,
				"amount");
		String nameOrCode = ServletRequestUtils.getStringParameter(request,
				"nameOrCode"); //获取字段名有误 ，已修改 2016/01/20 ddl
		String acctType = request.getParameter("acctType");
		
		
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		try {

			if (!amount.matches("^\\d+(.\\d{1,2})?$")) {
				response.getWriter().write("输入金额必须是数字,并且最多只能保留两位小数");
				return null;
			}
			if (Double.valueOf(amount) <= 0) {
				response.getWriter().write("冻结金额必须大于0");
				return null;
			}
		} catch (NumberFormatException e) {
			response.getWriter().write("冻结金额输入非法");
			return null;
		}
		Long memberCode = frozenLogService
				.getMemberCodeByLongNameOrCode(nameOrCode);
		if (memberCode == null) {
			response.getWriter().write("未得到对应的用户的信息");
			return null;
		}
		String desc = ServletRequestUtils.getStringParameter(request, "desc",
				"");
		boolean isok = false;
		String exceptionMsg = "";
		try {
			isok = frozenLogService.addFrozenLogRnTx(nameOrCode,
					new BigDecimal(amount), desc,Integer.valueOf(acctType));
		} catch (Exception e) {
			isok = false;
			exceptionMsg = "，后台操作异常";
			logger.error("", e);
			e.printStackTrace();
		}

		response.getWriter().write(isok ? "S" : "冻结不成功" + exceptionMsg);
		return null;

	}

	public ModelAndView freeFrozenLog(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Long id = ServletRequestUtils.getLongParameter(request, "id", 0L);
		String memberCode = ServletRequestUtils.getStringParameter(request,
				"memberCode", "");

		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String acctType = request.getParameter("acctType");
		boolean isok = false;
		String exceptionMsg = "";
		try {
			isok = frozenLogService.freeFrozenLogRnTx(memberCode, id);
			if (!isok) {
				exceptionMsg = "确认是否已被解冻";
			}
		} catch (Exception e) {
			isok = false;
			exceptionMsg = "，后台操作异常";
		}
		response.getWriter().write(isok ? "S" : ("解冻不成功，" + exceptionMsg));
		return null;
	}

	public ModelAndView getFrozenDetial(HttpServletRequest request,
			HttpServletResponse response) {

		Long id = ServletRequestUtils.getLongParameter(request, "id", 0L);
		FrozenLogDto dto = frozenLogService.getById(id);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dto", dto);
		return new ModelAndView(detailView, map);
	}
}
