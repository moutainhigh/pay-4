package com.pay.poss.controller.fi.crosspay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.util.StringUtil;

public class TrackingMgrController extends MultiActionController {

	private String queryInit;

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}

	private String recordList;

	public void setRecordList(String recordList) {
		this.recordList = recordList;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(queryInit);
	}

	

	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		String orderId = StringUtil
				.null2String(request.getParameter("orderId"));// 订单号
		String expressCom = StringUtil.null2String(request
				.getParameter("expressCom"));// 快递公司
		String trackingStatus = StringUtil.null2String(request
				.getParameter("trackingStatus"));// 运单状态
		String trackingNo = StringUtil.null2String(request
				.getParameter("trackingId"));// 运单号
		String uploadStartTime = StringUtil.null2String(request
				.getParameter("uploadStartTime"));// 上传查询开始时间
		String uploadEndTime = StringUtil.null2String(request
				.getParameter("uploadStartTime"));// 上传查询结束时间
//		ExpressTrackingCriteria expressTrackingCriteria = new ExpressTrackingCriteria();
//		Criteria criteria = expressTrackingCriteria.createCriteria();
//		if (StringUtils.isNotBlank(orderId)) {
//			criteria.andOrderIdEqualTo(new BigDecimal(orderId));
//		}
//		if (StringUtils.isNotBlank(expressCom)) {
//			criteria.andExpressComEqualTo(expressCom);
//		}
//		if (StringUtils.isNotBlank(trackingStatus)) {
//			criteria.andStateEqualTo(trackingStatus);
//		}
//		if (StringUtils.isNotBlank(trackingNo)) {
//			criteria.andTrackingNoEqualTo(trackingNo);
//		}
//		if (StringUtils.isNotBlank(uploadStartTime)) {
//			criteria.andUpdateDateGreaterThanOrEqualTo(DateUtil.parse(
//					"yyyy-MM-dd", uploadStartTime));
//		}
//		if (StringUtils.isNotBlank(uploadEndTime)) {
//			criteria.andUpdateDateGreaterThanOrEqualTo(DateUtil.parse(
//					"yyyy-MM-dd", uploadStartTime));
//		}
//
//		Page<ExpressTracking> page = PageUtils.getPage(request);
//		page = expressTrackingService.queryExpressTrackingForPage(
//				expressTrackingCriteria, page);
		return new ModelAndView(recordList);
	}

	public ModelAndView checkExpress(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String tradeOrderNo = StringUtil.null2String(request
				.getParameter("chk_tradeOrderNo"));// 交易号
		String remark = StringUtil.null2String(request
				.getParameter("chk_remark"));// 审核原因
		String act = StringUtil.null2String(request.getParameter("act"));// 审查结果（0：通过，1：审查不通过）；

//		ExpressTracking expressTracking = new ExpressTracking();
//
//		if (StringUtils.isNotBlank(tradeOrderNo)) {
//			expressTracking.setTradeOrderNo(tradeOrderNo);
//		}
//		if (StringUtils.isNotBlank(remark)) {
//			expressTracking.setRemark(remark);
//		}
//		if (StringUtils.isNotBlank(act)) {
//			if (act == "0")// 审核通过
//			{
//				expressTracking.setTrackingStatus("2");
//			} else if (act == "1") {// 审核不通过
//				expressTracking.setTrackingStatus("3");
//			}
//		}
//		boolean isUpdate = expressTrackingService
//				.updateTrackingInfoByTradeOrderNo(expressTracking);
		return null;
	}
}
