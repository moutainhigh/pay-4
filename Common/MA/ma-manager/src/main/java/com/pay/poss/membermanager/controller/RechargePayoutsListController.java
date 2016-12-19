package com.pay.poss.membermanager.controller;
/*package com.pay.poss.membermanager.controller;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.gateway.common.enums.FIFOEnum;
import com.pay.gateway.dto.transaction.QueryFIFOCountDTO;
import com.pay.gateway.dto.transaction.QueryFIFODetailDTO;
import com.pay.gateway.dto.transaction.QueryFIFOParamDTO;
import com.pay.poss.base.dao.model.Page;
import com.pay.poss.base.util.PageUtils;
import com.pay.poss.membermanager.common.Constants;
import com.pay.poss.membermanager.formbean.MemberTradeSearchFormBean;
import com.pay.poss.service.gateway.MB4GateWayServiceApi;
import com.pay.util.DateUtil;

*//**
 * 
 * @Description
 * @project poss-membermanager
 * @file MemberAccountListController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-8-3 gungun_zhang Create
 *//*

public class RechargePayoutsListController extends SimpleFormController {

	private Log log = LogFactory.getLog(RechargePayoutsListController.class);
	private MB4GateWayServiceApi memberService;

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("RechargePayoutsListController.referenceData() is running...");

		String memberCode = (String) request.getParameter("memberCode");
		String userName = (String) request.getParameter("userName");
		userName = new String(userName.getBytes(Constants.ENCODING_ISO), Constants.ENCODING_UTF);
		FIFOEnum[] fifoEnumArray = FIFOEnum.values();

		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("userName", userName);
		dataMap.put("memberCode", memberCode);
		dataMap.put("fifoEnumArray", fifoEnumArray);

		return dataMap;
	}

	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		MemberTradeSearchFormBean memberTradeSearchFormBean = (MemberTradeSearchFormBean) command;
		QueryFIFOParamDTO queryFIFOParamDTO = new QueryFIFOParamDTO();

		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getMemberCode())) {
			queryFIFOParamDTO.setMembercode(Long.valueOf(memberTradeSearchFormBean.getMemberCode()));

		}
		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getFifoType())) {
			queryFIFOParamDTO.setfIFOType(Integer.valueOf(memberTradeSearchFormBean.getFifoType()));
		}

		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getStartTime())) {
			queryFIFOParamDTO.setStartTime(DateUtil.parse("yyyy-MM-dd", memberTradeSearchFormBean.getStartTime()));
		}
		if (StringUtils.isNotEmpty(memberTradeSearchFormBean.getEndTime())) {
			queryFIFOParamDTO.setEndTime(DateUtil.parse("yyyy-MM-dd", memberTradeSearchFormBean.getEndTime()));
		}

		Page<QueryFIFODetailDTO> page = PageUtils.getPage(request);

		List<QueryFIFODetailDTO> tradeInfoList = memberService.queryRechargePayouts(queryFIFOParamDTO, page.getPageSize(), page
				.getPageNo());
		QueryFIFOCountDTO queryFIFOCountDTO = memberService.queryRechargePayoutsCount(queryFIFOParamDTO);
		page.setResult(tradeInfoList);
		page.setTotalCount(queryFIFOCountDTO.getCountTotal());

		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("queryFIFOCountDTO", queryFIFOCountDTO);
		dataMap.put("page", page);

		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
	}

	public void setMemberService(MB4GateWayServiceApi memberService) {
		this.memberService = memberService;
	}

	

}
*/