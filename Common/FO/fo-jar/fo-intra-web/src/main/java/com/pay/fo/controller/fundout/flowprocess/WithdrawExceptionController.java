/**
 *  File: WithdrawExceptionController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-6     darv      Changes
 *  
 *
 */
package com.pay.fo.controller.fundout.flowprocess;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pay.fundout.withdraw.model.flowprocess.WithdrawExceptionInfo;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawExceptionService;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.dto.Bank;
import com.pay.inf.service.BankService;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.util.UUIDUtil;

/**
 * @author darv
 * 
 */
public class WithdrawExceptionController extends AbstractBaseController {
	private Log log = LogFactory.getLog(getClass());

	private WithdrawExceptionService withdrawExceptionService;

	private WithdrawOrderAuditService withdrawOrderAuditService;

	private BankService bankService;

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void setWithdrawOrderAuditService(WithdrawOrderAuditService withdrawOrderAuditService) {
		this.withdrawOrderAuditService = withdrawOrderAuditService;
	}

	public void setWithdrawExceptionService(WithdrawExceptionService withdrawExceptionService) {
		this.withdrawExceptionService = withdrawExceptionService;
	}

	public ModelAndView init(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("uuid", UUIDUtil.uuid());
		return new ModelAndView(URL("init"));
	}

	/**
	 * 查询出款异常数据列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("businessSeq", StringUtils.trimToNull(request.getParameter("businessSeq")));
		params.put("memberNo", StringUtils.trimToNull(request.getParameter("memberNo")));
		params.put("businessType", StringUtils.trimToNull(request.getParameter("businessType")));
		params.put("bankAcct", StringUtils.trimToNull(request.getParameter("bankAcct")));
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if (startTime != null && startTime.trim().length() > 0) {
			params.put("startTime", dateFormat.parse(startTime));
		}
		if (endTime != null && endTime.trim().length() > 0) {
			params.put("endTime", dateFormat.parse(endTime));
		}
		Page<WithdrawExceptionInfo> page = PageUtils.getPage(request);
		page = withdrawExceptionService.getWithDrawExceptionInfoList(page, params);
		List<Bank> bankList = this.bankService.getAllBankList();
		Map<String, String> banks = new HashMap<String, String>();
		for (Bank bank : bankList) {
			banks.put(String.valueOf(bank.getBankId()), bank.getBankName());
		}
		for (int i = 0; i < page.getResult().size(); i++) {
			page.getResult().get(i).setBankName(banks.get(page.getResult().get(i).getBankKy()));
		}
		return new ModelAndView(URL("list")).addObject("page", page);
	}

	/**
	 * 对没有生成工单的生成工单，没有关联工作流的进行关联
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView relation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String uuid = request.getParameter("uuid");
			if (uuid != null && request.getSession().getAttribute("uuid") != null
					&& uuid.equalsIgnoreCase(request.getSession().getAttribute("uuid").toString())) {
				request.getSession().removeAttribute("uuid");
				String[] ids = request.getParameter("ids").split(",");
				for (int i = 0; i < ids.length; i++) {
					String[] str = ids[i].split("_");
					if ("0".equals(str[1])) {  //未生成工单的情况
						withdrawOrderAuditService.startWfAndCreateOrderRdTx(str[0]);
					} else {  //生成工单但没有关联工作流的情况
						withdrawExceptionService.updateWorkOrderOfExceptionOrderRdTx(str[0]);
					}
				}
			}
		} catch (Exception e) {
			log.error("重成工单或关联流水出错：" + e);
			e.printStackTrace();
		}
		return new ModelAndView(new RedirectView(request.getContextPath() + URL("toInit")));
	}
}
