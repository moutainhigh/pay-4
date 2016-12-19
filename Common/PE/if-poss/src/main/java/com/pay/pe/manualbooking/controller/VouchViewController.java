package com.pay.pe.manualbooking.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.service.VouchService;
import com.pay.pe.manualbooking.service.VouchUserService;
import com.pay.pe.manualbooking.util.VouchDataStatus;
import com.pay.pe.manualbooking.util.VouchSearchCriteria;

/**
 * 
 * 手工记账申请查看控制器
 */
public class VouchViewController extends BaseVouchSearchController {
	private static final Log LOG = LogFactory.getLog(VouchViewController.class);
	private String auditVouchPage;

	private String cancelVouchPage;

	private String viewVouchPage;

	private String batchAuditVouchPage;// 批量审核

	private VouchService vouchService;

	private VouchUserService vouchUserService;

	public String getAuditVouchPage() {
		return auditVouchPage;
	}

	public void setAuditVouchPage(String auditVouchPage) {
		this.auditVouchPage = auditVouchPage;
	}

	public String getCancelVouchPage() {
		return cancelVouchPage;
	}

	public void setCancelVouchPage(String cancelVouchPage) {
		this.cancelVouchPage = cancelVouchPage;
	}

	public String getViewVouchPage() {
		return viewVouchPage;
	}

	public void setViewVouchPage(String viewVouchPage) {
		this.viewVouchPage = viewVouchPage;
	}

	public VouchService getVouchService() {
		return vouchService;
	}

	public void setVouchService(VouchService vouchService) {
		this.vouchService = vouchService;
	}

	public VouchUserService getVouchUserService() {
		return vouchUserService;
	}

	public void setVouchUserService(VouchUserService vouchUserService) {
		this.vouchUserService = vouchUserService;
	}

	/**
	 * @Title: verifyVouch
	 * @Description: TODO批量审核页面
	 * @param @param request
	 * @param @param response
	 * @param @return 设定文件
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	public ModelAndView batchVerifyVouch(final HttpServletRequest request,
			final HttpServletResponse response) {
		LOG.info("Start");
		// VouchSearchCriteria vouchSearchCriteria =
		// getVouchSearchCriteria(request);

		String username = vouchUserService.getCurrentUser();
		String strVouchDataId = request.getParameter(BATCH_VOUCH_DATA_ID);
		if (strVouchDataId.endsWith(",")) {
			strVouchDataId = strVouchDataId.substring(0,
					strVouchDataId.length() - 1);
		}
		String[] vouchDatas = strVouchDataId.split(",");
		List<VouchDataDto> dtoList = new ArrayList<VouchDataDto>();
		for (String data : vouchDatas) {
			if (null == data || data.length() == 0) {
				continue;
			}
			Long vouchDataId = Long.parseLong(data);
			VouchDataDto vouchDataDto = vouchService
					.getVouchDataById(vouchDataId);
			LOG.info("vouchDataDto==" + vouchDataDto);
			Integer vouchStatus = vouchDataDto.getStatus();
			String vouchCreator = vouchDataDto.getCreator();
			// 未审核 and current user is not the creator of this vouch, go
			// auditVouchPage
			if (VouchDataStatus.UNAUDITED.getValue().equals(vouchStatus)) {
				if ("adminA".equals(username)
						|| !vouchCreator.equalsIgnoreCase(username)) {
					dtoList.add(vouchDataDto);
					LOG.debug("vouchDataDto  :" + vouchDataDto.toString());
				}

				// (未审核 or 审核不通过) and current user is the creator of this vouch,
				// go cancelVouchPage
			}
		}
		LOG.debug("dtoList.size()  :" + dtoList.size());
		LOG.info("End");
		if (dtoList.size() == 0) {
			return new ModelAndView("redirect:/manualbooking/misController.do");
		}
		return new ModelAndView(batchAuditVouchPage).addObject("appContext",
				request.getContextPath())
		// .addObject(VOUCH_SEARCH_CRITERIA_NAME, vouchSearchCriteria)
				.addObject(BATCH_VOUCH_DATA, dtoList);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return 查看申请
	 */
	public ModelAndView viewVouch(final HttpServletRequest request,
			final HttpServletResponse response) {
		LOG.info("Start");
		VouchSearchCriteria vouchSearchCriteria = getVouchSearchCriteria(request);

		String username = vouchUserService.getCurrentUser();

		String strVouchDataId = request.getParameter(VOUCH_DATA_ID);
		Long vouchDataId = Long.parseLong(strVouchDataId);
		VouchDataDto vouchDataDto = vouchService.getVouchDataById(vouchDataId);

		LOG.info("vouchDataDto==" + vouchDataDto);
		Integer vouchStatus = vouchDataDto.getStatus();
		String vouchCreator = vouchDataDto.getCreator();

		String goPageName = null;
		// 未审核 and current user is not the creator of this vouch, go
		// auditVouchPage
		if (VouchDataStatus.UNAUDITED.getValue().equals(vouchStatus)
				&& !vouchCreator.equalsIgnoreCase(username)) {
			goPageName = auditVouchPage;
			LOG.debug("Go to audit page");
			// (未审核 or 审核不通过) and current user is the creator of this vouch, go
			// cancelVouchPage
		} else if ((VouchDataStatus.UNAUDITED.getValue().equals(vouchStatus) || VouchDataStatus.REJECTED
				.getValue().equals(vouchStatus))
				&& vouchCreator.equalsIgnoreCase(username)) {
			goPageName = cancelVouchPage;
			LOG.debug("Go to cancel page");
			// 审核通过 or 作废, go viewVouchPage
		} else if (VouchDataStatus.APPROVED.getValue().equals(vouchStatus)
				|| VouchDataStatus.CANCELED.getValue().equals(vouchStatus)) {
			goPageName = viewVouchPage;
			LOG.debug("Go to view page");
			// 其他情况， 审核不通过 and current user is not the creator of this vouch, go
			// viewVouchPage
		} else {
			goPageName = viewVouchPage;
			LOG.debug("Go to view page");
		}
		System.out.println("goPageName==" + goPageName + "    " + username
				+ "   " + vouchCreator);
		LOG.info("End");
		return new ModelAndView(goPageName)
				.addObject("appContext", request.getContextPath())
				.addObject(VOUCH_SEARCH_CRITERIA_NAME, vouchSearchCriteria)
				.addObject(VOUCH_DATA, vouchDataDto);
	}

	public String getBatchAuditVouchPage() {
		return batchAuditVouchPage;
	}

	public void setBatchAuditVouchPage(String batchAuditVouchPage) {
		this.batchAuditVouchPage = batchAuditVouchPage;
	}

}
