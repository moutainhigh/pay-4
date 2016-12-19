package com.pay.poss.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.dto.CardFilterDTO;
import com.pay.inf.service.CardFilterService;
import com.pay.util.StringUtil;

/**
 * 网站管理poss 后台
 * 
 * @Description
 * @file SitesetController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 hnapayay Corporation. All rights reserved.
 *          Date
 */
public class CarbinRateController extends MultiActionController {

	private final Log logger = LogFactory.getLog(getClass());
	private String queryInit;
	private String addView;
	private String recordList;
	private CardFilterService cardFilterService;
	private EnterpriseBaseService enterpriseBaseService;

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public void setCardFilterService(CardFilterService cardFilterService) {
		this.cardFilterService = cardFilterService;
	}

	/**
	 * 默认查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return new ModelAndView(queryInit);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView toAdd(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return new ModelAndView(addView);
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String partnerIds = request.getParameter("partnerId");
		String cardFilterTypes = request.getParameter("cardFilterType");
		String endCardNos = request.getParameter("endCardNo");
		String startCardnos = request.getParameter("startCardno");
		String message = "";
		if (!StringUtil.isEmpty(partnerIds)) {
		EnterpriseBaseDto code = enterpriseBaseService.queryEnterpriseBaseByMemberCode(Long
				.valueOf(partnerIds));
		if (code == null) {
			message = "商户会员号不存在！";
			return new ModelAndView(addView).addObject("message", message);
		}
		}
		CardFilterDTO cardFilterDTO = new CardFilterDTO();
		if (!StringUtil.isEmpty(partnerIds)) {
			cardFilterDTO.setPartnerId(Long.valueOf(partnerIds));
		}
		if (!StringUtil.isEmpty(cardFilterTypes)) {
			cardFilterDTO.setCardFilterType(Long.valueOf(cardFilterTypes));
		}
		if (!StringUtil.isEmpty(startCardnos)) {
			cardFilterDTO.setStartCardno(Long.valueOf(startCardnos));
		}
		if (!StringUtil.isEmpty(endCardNos)) {
			cardFilterDTO.setEndCardNo(Long.valueOf(endCardNos));
		}
		cardFilterDTO.setCreateDate(new Date());

		Long l=cardFilterService.create(cardFilterDTO);

		if(l>0)
		{
			message = "添加已成功！";
		} 
		
		return new ModelAndView(addView).addObject("message", message);

	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");

		String message = "";
		int i=cardFilterService.delete(id);
		if(i>0)
		{
			message="删除已成功！";
		} 
		return new ModelAndView(queryInit).addObject("message", message);
	}

	/**
	 * 查询网站信息列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String partnerIds = StringUtil.null2String(request.getParameter("partnerId"));
		String cardNos = StringUtil.null2String(request.getParameter("cardNo"));
		String beginTime = StringUtil.null2String(request.getParameter("effectDate"));
		String endTime = StringUtil.null2String(request.getParameter("expireDate"));
		Long partnerId = null;
		Long cardNo = null;
		if (!StringUtil.isEmpty(beginTime)) {
			beginTime = beginTime.trim();
		}
		if (!StringUtil.isEmpty(endTime)) {
			endTime = endTime.trim();
		}
		if (!StringUtil.isEmpty(partnerIds)) {
			partnerId = Long.valueOf(partnerIds);
		}
		if (!StringUtil.isEmpty(cardNos)) {
			cardNo = Long.valueOf(cardNos.trim());
		}

		Page<CardFilterDTO> page = PageUtils.getPage(request);
		Page<CardFilterDTO> rateList = cardFilterService.queryCardFilter(page, beginTime, endTime,
				cardNo, partnerId);
		return new ModelAndView(recordList).addObject("rateList", rateList);

	}

}
