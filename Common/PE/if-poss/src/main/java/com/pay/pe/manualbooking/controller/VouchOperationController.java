package com.pay.pe.manualbooking.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.pay.pe.manualbooking.dao.VouchValidator;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchDataPostingException;
import com.pay.pe.manualbooking.exception.VouchValidateMessage;
import com.pay.pe.manualbooking.service.VouchLogService;
import com.pay.pe.manualbooking.service.VouchService;
import com.pay.pe.manualbooking.service.VouchUserService;
import com.pay.pe.manualbooking.util.VouchDataStatus;
import com.pay.pe.manualbooking.util.VouchSearchCriteria;

/**
 * 
 * 手工记账申请操作控制器
 */
public class VouchOperationController extends BaseVouchSearchController {

	private static final Log LOG = LogFactory
			.getLog(VouchOperationController.class);
	private String cancelResultPage;

	private String auditResultPage;

	private VouchService vouchService;

	private VouchUserService vouchUserService;

	private VouchLogService vouchLogService;

	private VouchValidator vouchValidator;

	private String batchAuditVouchPage;// 批量审核

	public String getCancelResultPage() {
		return cancelResultPage;
	}

	public void setCancelResultPage(String cancelResultPage) {
		this.cancelResultPage = cancelResultPage;
	}

	public String getAuditResultPage() {
		return auditResultPage;
	}

	public void setAuditResultPage(String auditResultPage) {
		this.auditResultPage = auditResultPage;
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

	public VouchLogService getVouchLogService() {
		return vouchLogService;
	}

	public void setVouchLogService(VouchLogService vouchLogService) {
		this.vouchLogService = vouchLogService;
	}

	// 未复核 或被拒绝 相同人
	private boolean isCancelable(VouchDataDto vouchDataDto) {
		String username = vouchUserService.getCurrentUser();

		Integer vouchStatus = vouchDataDto.getStatus();
		String vouchCreator = vouchDataDto.getCreator();

		if ((VouchDataStatus.UNAUDITED.getValue().equals(vouchStatus) || VouchDataStatus.REJECTED
				.getValue().equals(vouchStatus))
				&& vouchCreator.equalsIgnoreCase(username)) {
			return true;
		}
		return false;
	}

	// 未复核 不同人
	private boolean isRejectable(VouchDataDto vouchDataDto) {
		String username = vouchUserService.getCurrentUser();

		Integer vouchStatus = vouchDataDto.getStatus();
		String vouchCreator = vouchDataDto.getCreator();

		if (VouchDataStatus.UNAUDITED.getValue().equals(vouchStatus)) {

			if ("adminA".equalsIgnoreCase(username)
					|| !vouchCreator.equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}

	// 未复核 不同人
	private boolean isApprovable(VouchDataDto vouchDataDto) {
		String username = vouchUserService.getCurrentUser();

		Integer vouchStatus = vouchDataDto.getStatus();
		String vouchCreator = vouchDataDto.getCreator();

		if (VouchDataStatus.UNAUDITED.getValue().equals(vouchStatus)) {
			if ("adminA".equalsIgnoreCase(username)
					|| !vouchCreator.equalsIgnoreCase(username)) {
				return true;
			}

		}
		return false;
	}

	private String getRemoteIP(HttpServletRequest request) {
		if (request.getHeader("x-forwarded-for") == null) {
			return request.getRemoteAddr();
		}
		return request.getHeader("x-forwarded-for");
	}

	/**
	 * @Title: batchApproveVouch
	 * @Description: TODO批量审核通过　
	 * @param @param request
	 * @param @param response
	 * @param @return 设定文件
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	public ModelAndView batchApproveVouch(final HttpServletRequest request,
			final HttpServletResponse response) {
		LOG.info("Start");
		// VouchSearchCriteria vouchSearchCriteria =
		// getVouchSearchCriteria(request);

		String[] strVouchDataId = request
				.getParameterValues(BATCH_VOUCH_DATA_ID);
		String[] vouchRemark = request.getParameterValues("vouchRemark");

		int i = 0;
		List<String> messages = new ArrayList<String>();
		List<VouchDataDto> dtoList = new ArrayList<VouchDataDto>();

		for (String dataId : strVouchDataId) {
			Long vouchDataId = Long.parseLong(dataId);
			LOG.debug("Vouch data id : " + dataId);
			VouchDataDto vouchDataDto = vouchService
					.getVouchDataById(vouchDataId);
			String remark = vouchRemark[i];
			i++;
			if (StringUtils.isNotEmpty(remark)) {
				remark = remark.trim();
			}
			dtoList.add(vouchDataDto);
			vouchDataDto.setRemark(remark);
			LOG.debug("Remark : " + vouchRemark);
			String operator = vouchUserService.getCurrentUser();
			String ip = getRemoteIP(request);

			if (!isApprovable(vouchDataDto)) {
				LOG.debug("The current user can not approve the vouch!");
				vouchLogService.logApproveFailure(vouchDataDto, operator, ip,
						"数据并发失败");
				messages.add(VOUCH_DATA_MSG + vouchDataDto.getVouchDataId()
						+ APPROVE_FAIL_MESSAGE);
				continue;
				// modelAndView.addObject(VOUCH_DATA, vouchDataDto)
				// .addObject(VOUCH_MESSAGE, APPROVE_FAIL_MESSAGE);
			}
			// 验证数据
			List<VouchValidateMessage> msg = vouchValidator
					.validate(vouchDataDto);
			if (msg != null && msg.size() > 0) {
				LOG.debug("The vouch data is invalid! for example: account not exist, etc!");
				vouchLogService.logApproveFailure(vouchDataDto, operator, ip,
						"数据验证失败");
				messages.add(VOUCH_DATA_MSG + vouchDataDto.getVouchDataId()
						+ msg.toString());
				continue;
				// modelAndView.addObject(VOUCH_DATA, vouchDataDto)
				// .addObject(VOUCH_MESSAGE, APPROVE_FAIL_MESSAGE)
				// .addObject("messages", messages);
				// return modelAndView;
			}
			VouchDataDto newVouchDataDto = vouchDataDto;
			try {
				newVouchDataDto = vouchService.approveVouchData(vouchDataDto);
			} catch (VouchDataPostingException e) {
				LOG.debug("Fails to approve the vouch", e);
				msg.add(VouchValidateMessage.createValidateMessage(e
						.getMessage()));
				if (e.getCause() != null) {
					msg.add(VouchValidateMessage.createValidateMessage(e
							.getCause().getMessage()));
				}
				vouchLogService.logApproveFailure(vouchDataDto, operator, ip,
						"记账失败");
				messages.add(VOUCH_DATA_MSG + vouchDataDto.getVouchDataId()
						+ msg.toString());
				continue;

				// modelAndView.addObject(VOUCH_DATA, newVouchDataDto)
				// .addObject(VOUCH_MESSAGE, APPROVE_FAIL_MESSAGE)
				// .addObject("messages", messages);
				// return modelAndView;
			} catch (Throwable e) {
				LOG.debug("Fails to approve the vouch", e);
				vouchLogService.logApproveFailure(vouchDataDto, operator, ip,
						"数据并发失败");
				messages.add(VOUCH_DATA_MSG + vouchDataDto.getVouchDataId()
						+ APPROVE_FAIL_MESSAGE);
				// modelAndView.addObject(VOUCH_DATA, newVouchDataDto)
				// .addObject(VOUCH_MESSAGE, APPROVE_FAIL_MESSAGE);
				// return modelAndView;
				continue;

			}
			vouchLogService.logApproveSuccess(vouchDataDto, newVouchDataDto,
					operator, ip, "审核通过成功");

			// modelAndView.addObject(VOUCH_DATA, newVouchDataDto)
			// .addObject(VOUCH_MESSAGE, APPROVE_SUCC_MESSAGE);

		}

		LOG.info("End");
		if (messages.size() == 0) {
			messages.add(APPROVE_SUCC_MESSAGE);
		}
		ModelAndView modelAndView = new ModelAndView(batchAuditVouchPage)
				.addObject("appContext", request.getContextPath())
				.addObject(VOUCH_MESSAGE, messages)
				.addObject(BATCH_VOUCH_DATA, dtoList);

		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return 审核通过手工记账申请
	 */

	public ModelAndView approveVouch(final HttpServletRequest request,
			final HttpServletResponse response) {
		LOG.info("Start");
		VouchSearchCriteria vouchSearchCriteria = getVouchSearchCriteria(request);

		String strVouchDataId = request.getParameter(VOUCH_DATA_ID);
		Long vouchDataId = Long.parseLong(strVouchDataId);
		LOG.debug("Vouch data id : " + vouchDataId);

		VouchDataDto vouchDataDto = vouchService.getVouchDataById(vouchDataId);

		String vouchRemark = request.getParameter("vouchRemark");
		if (StringUtils.isNotEmpty(vouchRemark)) {
			vouchRemark = vouchRemark.trim();
		}
		vouchDataDto.setRemark(vouchRemark);
		LOG.debug("Remark : " + vouchRemark);

		ModelAndView modelAndView = new ModelAndView(auditResultPage)
				.addObject("appContext", request.getContextPath()).addObject(
						VOUCH_SEARCH_CRITERIA_NAME, vouchSearchCriteria);

		String operator = vouchUserService.getCurrentUser();
		String ip = getRemoteIP(request);

		if (!isApprovable(vouchDataDto)) {
			LOG.debug("The current user can not approve the vouch!");

			vouchLogService.logApproveFailure(vouchDataDto, operator, ip,
					"数据并发失败");
			modelAndView.addObject(VOUCH_DATA, vouchDataDto).addObject(
					VOUCH_MESSAGE, APPROVE_FAIL_MESSAGE);
			return modelAndView;
		}

		// 验证数据
		List<VouchValidateMessage> messages = vouchValidator
				.validate(vouchDataDto);
		if (messages != null && messages.size() > 0) {
			LOG.debug("The vouch data is invalid! for example: account not exist, etc!");

			vouchLogService.logApproveFailure(vouchDataDto, operator, ip,
					"数据验证失败");
			modelAndView.addObject(VOUCH_DATA, vouchDataDto)
					.addObject(VOUCH_MESSAGE, APPROVE_FAIL_MESSAGE)
					.addObject("messages", messages);
			return modelAndView;
		}

		VouchDataDto newVouchDataDto = vouchDataDto;
		try {
			newVouchDataDto = vouchService.approveVouchData(vouchDataDto);
		} catch (VouchDataPostingException e) {
			LOG.debug("Fails to approve the vouch", e);

			messages = new ArrayList<VouchValidateMessage>();
			messages.add(VouchValidateMessage.createValidateMessage(e
					.getMessage()));
			if (e.getCause() != null) {
				messages.add(VouchValidateMessage.createValidateMessage(e
						.getCause().getMessage()));
			}

			vouchLogService.logApproveFailure(vouchDataDto, operator, ip,
					"记账失败");
			modelAndView.addObject(VOUCH_DATA, newVouchDataDto)
					.addObject(VOUCH_MESSAGE, APPROVE_FAIL_MESSAGE)
					.addObject("messages", messages);
			return modelAndView;
		} catch (Throwable e) {
			LOG.debug("Fails to approve the vouch", e);

			vouchLogService.logApproveFailure(vouchDataDto, operator, ip,
					"数据并发失败");
			modelAndView.addObject(VOUCH_DATA, newVouchDataDto).addObject(
					VOUCH_MESSAGE, APPROVE_FAIL_MESSAGE);
			return modelAndView;
		}

		vouchLogService.logApproveSuccess(vouchDataDto, newVouchDataDto,
				operator, ip, "审核通过成功");

		LOG.info("End");
		modelAndView.addObject(VOUCH_DATA, newVouchDataDto).addObject(
				VOUCH_MESSAGE, APPROVE_SUCC_MESSAGE);
		return modelAndView;
	}

	public ModelAndView batchRejectVouch(final HttpServletRequest request,
			final HttpServletResponse response) {
		LOG.info("Start");

		String[] strVouchDataId = request
				.getParameterValues(BATCH_VOUCH_DATA_ID);
		String[] vouchRemark = request.getParameterValues("vouchRemark");
		List<String> messages = new ArrayList<String>();
		List<VouchDataDto> dtoList = new ArrayList<VouchDataDto>();

		int i = 0;
		for (String dataId : strVouchDataId) {
			Long vouchDataId = Long.parseLong(dataId);
			LOG.debug("Vouch data id : " + dataId);
			VouchDataDto vouchDataDto = vouchService
					.getVouchDataById(vouchDataId);
			String remark = vouchRemark[i];
			i++;
			if (StringUtils.isNotEmpty(remark)) {
				remark = remark.trim();
			}
			vouchDataDto.setRemark(remark);
			dtoList.add(vouchDataDto);

			String operator = vouchUserService.getCurrentUser();
			String ip = getRemoteIP(request);

			if (!isRejectable(vouchDataDto)) {
				LOG.debug("The current user can not reject the vouch!");
				vouchLogService.logRejectFailure(vouchDataDto, operator, ip,
						"数据并发失败");
				messages.add(VOUCH_DATA_MSG + vouchDataDto.getVouchDataId()
						+ REJECT_FAIL_MESSAGE);
				continue;
			}

			VouchDataDto newVouchDataDto = vouchDataDto;
			try {
				newVouchDataDto = vouchService.rejectVouchData(vouchDataDto);
			} catch (Throwable e) {
				LOG.debug("Fails to reject the vouch", e);

				vouchLogService.logRejectFailure(vouchDataDto, operator, ip,
						"数据并发失败");
				messages.add(VOUCH_DATA_MSG + vouchDataDto.getVouchDataId()
						+ REJECT_FAIL_MESSAGE);
				continue;
			}
			vouchLogService.logRejectSuccess(vouchDataDto, newVouchDataDto,
					operator, ip, "审核不通过成功");
		}
		LOG.info("End");
		if (messages.size() == 0) {
			messages.add(REJECT_SUCC_MESSAGE);
		}
		ModelAndView modelAndView = new ModelAndView(batchAuditVouchPage)
				.addObject("appContext", request.getContextPath())
				.addObject(VOUCH_MESSAGE, messages)
				.addObject(BATCH_VOUCH_DATA, dtoList);

		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return 审核不通过记账申请
	 */
	public ModelAndView rejectVouch(final HttpServletRequest request,
			final HttpServletResponse response) {
		LOG.info("Start");
		VouchSearchCriteria vouchSearchCriteria = getVouchSearchCriteria(request);

		String strVouchDataId = request.getParameter(VOUCH_DATA_ID);
		Long vouchDataId = Long.parseLong(strVouchDataId);
		LOG.debug("Vouch data id : " + vouchDataId);

		VouchDataDto vouchDataDto = vouchService.getVouchDataById(vouchDataId);

		String vouchRemark = request.getParameter("vouchRemark");
		if (StringUtils.isNotEmpty(vouchRemark)) {
			vouchRemark = vouchRemark.trim();
		}
		vouchDataDto.setRemark(vouchRemark);
		LOG.debug("Remark : " + vouchRemark);

		ModelAndView modelAndView = new ModelAndView(auditResultPage)
				.addObject("appContext", request.getContextPath()).addObject(
						VOUCH_SEARCH_CRITERIA_NAME, vouchSearchCriteria);

		String operator = vouchUserService.getCurrentUser();
		String ip = getRemoteIP(request);

		if (!isRejectable(vouchDataDto)) {
			LOG.debug("The current user can not reject the vouch!");

			vouchLogService.logRejectFailure(vouchDataDto, operator, ip,
					"数据并发失败");
			modelAndView.addObject(VOUCH_DATA, vouchDataDto).addObject(
					VOUCH_MESSAGE, REJECT_FAIL_MESSAGE);
			return modelAndView;
		}

		VouchDataDto newVouchDataDto = vouchDataDto;
		try {
			newVouchDataDto = vouchService.rejectVouchData(vouchDataDto);
		} catch (Throwable e) {
			LOG.debug("Fails to reject the vouch", e);

			vouchLogService.logRejectFailure(vouchDataDto, operator, ip,
					"数据并发失败");
			modelAndView.addObject(VOUCH_DATA, newVouchDataDto).addObject(
					VOUCH_MESSAGE, REJECT_FAIL_MESSAGE);
			return modelAndView;
		}

		vouchLogService.logRejectSuccess(vouchDataDto, newVouchDataDto,
				operator, ip, "审核不通过成功");

		LOG.info("End");
		modelAndView.addObject(VOUCH_DATA, newVouchDataDto).addObject(
				VOUCH_MESSAGE, REJECT_SUCC_MESSAGE);
		return modelAndView;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return 取消记账申请
	 */
	public ModelAndView cancelVouch(final HttpServletRequest request,
			final HttpServletResponse response) {
		LOG.info("Start");
		VouchSearchCriteria vouchSearchCriteria = getVouchSearchCriteria(request);

		String strVouchDataId = request.getParameter(VOUCH_DATA_ID);
		Long vouchDataId = Long.parseLong(strVouchDataId);
		LOG.debug("Vouch data id : " + vouchDataId);

		VouchDataDto vouchDataDto = vouchService.getVouchDataById(vouchDataId);

		ModelAndView modelAndView = new ModelAndView(cancelResultPage)
				.addObject("appContext", request.getContextPath()).addObject(
						VOUCH_SEARCH_CRITERIA_NAME, vouchSearchCriteria);

		String operator = vouchUserService.getCurrentUser();
		String ip = getRemoteIP(request);

		if (!isCancelable(vouchDataDto)) {
			LOG.debug("The current user can not cancel the vouch!");

			vouchLogService.logCancelFailure(vouchDataDto, operator, ip,
					"数据并发失败");
			modelAndView.addObject(VOUCH_DATA, vouchDataDto).addObject(
					VOUCH_MESSAGE, CANCEL_FAIL_MESSAGE);
			return modelAndView;
		}

		VouchDataDto newVouchDataDto = vouchDataDto;
		try {
			newVouchDataDto = vouchService.cancelVouchData(vouchDataDto);
		} catch (Throwable e) {
			LOG.debug("Fails to cancel the vouch", e);

			vouchLogService.logCancelFailure(vouchDataDto, operator, ip,
					"数据并发失败");
			modelAndView.addObject(VOUCH_DATA, newVouchDataDto).addObject(
					VOUCH_MESSAGE, CANCEL_FAIL_MESSAGE);
			return modelAndView;
		}

		vouchLogService.logCancelSuccess(vouchDataDto, newVouchDataDto,
				operator, ip, "取消成功");

		LOG.info("End");
		modelAndView.addObject(VOUCH_DATA, newVouchDataDto).addObject(
				VOUCH_MESSAGE, CANCEL_SUCC_MESSAGE);
		return modelAndView;

	}

	public VouchValidator getVouchValidator() {
		return vouchValidator;
	}

	public void setVouchValidator(VouchValidator vouchValidator) {
		this.vouchValidator = vouchValidator;
	}

	public String getBatchAuditVouchPage() {
		return batchAuditVouchPage;
	}

	public void setBatchAuditVouchPage(String batchAuditVouchPage) {
		this.batchAuditVouchPage = batchAuditVouchPage;
	}
}
