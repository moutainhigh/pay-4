/**
 *  File: RefundExceptionController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-7     darv      Changes
 *  
 *
 */
package com.pay.fo.controller.refund;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.refund.model.RefundExceptionInfo;
import com.pay.poss.refund.service.RefundExceptionService;
import com.pay.util.UUIDUtil;

/**
 * @author darv
 * 
 */
public class RefundExceptionController extends AbstractBaseController {
	private Log log = LogFactory.getLog(getClass());

	private RefundExceptionService refundExceptionService;

	public void setRefundExceptionService(RefundExceptionService refundExceptionService) {
		this.refundExceptionService = refundExceptionService;
	}

	public ModelAndView init(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("uuid", UUIDUtil.uuid());
		return new ModelAndView(URL("init"));
	}

	/**
	 * 查询充退异常数据列表
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
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		if (startTime != null && startTime.trim().length() > 0) {
			params.put("startTime", dateFormat.parse(startTime));
		}
		if (endTime != null && endTime.trim().length() > 0) {
			params.put("endTime", dateFormat.parse(endTime));
		}
		Page<RefundExceptionInfo> page = PageUtils.getPage(request);
		page = refundExceptionService.getRefundExceptionInfoList(page, params);
		return new ModelAndView(URL("list")).addObject("page", page);
	}

	/**
	 * 对没有关联工作流的进行关联以及没有分配的进行分配
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView relation(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			//TODO 不需要判断工作流关
			String uuid = request.getParameter("uuid");
			if (uuid != null && request.getSession().getAttribute("uuid") != null
					&& uuid.equalsIgnoreCase(request.getSession().getAttribute("uuid").toString())) {
				request.getSession().removeAttribute("uuid");
				String[] ids = request.getParameter("ids").split(",");
				for (int i = 0; i < ids.length; i++) {
					String[] str = ids[i].split("_");
					if ("0".equals(str[1])) {	//未关联工作流
						refundExceptionService.updateWorkOrderRdTx(str[0]);
					} else {  //未分配人
						//refundExceptionService.assignOwnerRdTx(str[0]);
					}
				}
			}
		} catch (Exception e) {
			log.error("工作流关联或分配到操作员时出错：" + e);
			e.printStackTrace();
		}
		return new ModelAndView(new RedirectView(request.getContextPath() + URL("toInit")));
	}
}
