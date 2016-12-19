/**
 *Copyright © 2004-2013 hnapay.com . All rights reserved. 海航新生版权所有
 */
package com.pay.fi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.SessionHelper;
import com.pay.fi.dto.RefuseSearchDTO;
import com.pay.fi.hession.RefusePaymentService;

/**
 * @author xfliang
 * @date 2014-8-26
 */
public class RefuseOrderController extends MultiActionController {

	private final Log logger = LogFactory.getLog(RefuseOrderController.class);
	/* 页面路径 */
	private String index;
	private String recordList;
	private RefusePaymentService refusePaymentService;

	/**
	 * 网站管理
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, RefuseSearchDTO refuseSearchDTO)
			throws Exception {

		// 获取当前登录的商户编号
		String memberCode = SessionHelper.getLoginSession(request)
				.getMemberCode();

		logger.info("RefuseOrder====================================memberCode="
				+ memberCode);

		//RefusePaymentOrderCriteria refusePaymentOrderCriteria = new RefusePaymentOrderCriteria();

		//Criteria criteria = refusePaymentOrderCriteria.createCriteria();

		//criteria.andPartnerIdEqualTo(memberCode);
		// 开始时间
		if (StringUtils.isNotBlank(refuseSearchDTO.getStartTime())) {
//			criteria.andCreateDateGreaterThanOrEqualTo(DateUtils.parseDate(
//					refuseSearchDTO.getStartTime(),
//					new String[] { "yyyy-MM-dd HH:mm:ss" }));
		}
		// 终止时间
		if (StringUtils.isNotBlank(refuseSearchDTO.getEndTime())) {
			//criteria.andCreateDateLessThanOrEqualTo(DateUtils.parseDate(refuseSearchDTO.getStartTime(),	new String[] { "yyyy-MM-dd HH:mm:ss" }));
		}
		// 订单号
		if (StringUtils.isNotBlank(refuseSearchDTO.getOrderId())) {
			//criteria.andOrderIdEqualTo(refuseSearchDTO.getOrderId());
		}
		// 交易号
		if (refuseSearchDTO.getTradeOrderNo() != null) {
			//criteria.andTradeOrderNoEqualTo(refuseSearchDTO.getTradeOrderNo());
		}

		// 拒付状态
		if (StringUtils.isNotBlank(refuseSearchDTO.getRefuseStatus())) {
			//criteria.andRefuseStatusEqualTo(refuseSearchDTO.getRefuseStatus());
		}

		// 设置排序
		//refusePaymentOrderCriteria.setOrderByClause("CREATE_DATE desc");

//		Page<RefusePaymentOrderDTO> page = PageUtils.getPage(request);
//		// page.setPageSize(1);
//		page = refusePaymentOrderService.selectRefusePamentOrderWebSite(
//				refusePaymentOrderCriteria, page);
//
//		Map<String, Object> model = new HashMap<String, Object>();
//
//		PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(),
//				page.getTotalRecord());// 分页处理
//		model.put("pu", pu);
//		model.put("page", page);
//		model.put("refuseSearchDTO", refuseSearchDTO);

		return null;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(String index) {
		this.index = index;
	}

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public void setRefusePaymentService(RefusePaymentService refusePaymentService) {
		this.refusePaymentService = refusePaymentService;
	}

}
