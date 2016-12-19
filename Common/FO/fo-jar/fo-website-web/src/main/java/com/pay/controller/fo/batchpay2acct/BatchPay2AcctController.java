/**
 * 
 */
package com.pay.controller.fo.batchpay2acct;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.wrapper.SafeControllerWrapper;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;
import com.pay.fundout.service.ma.AccountInfo;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.util.AmountUtils;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class BatchPay2AcctController extends AbstractBatchPay2AcctController {

	private Integer maxSize;

	private String indexView;

	private String applyConfirmView;

	private String applySuccessView;

	private String errorView;
	private BatchPaymentOrderService batchPaymentOrderService;

	/**
	 * 请求批付到账户
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_MASSPAY2ACCOUNT_UPLOAD")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, BatchPay2AcctCommand command)
			throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		ModelAndView view = new ModelAndView(indexView);
		command.setPayerMemberCode(Long.parseLong(loginSession.getMemberCode()));
		try {
			// 初始化数据
			init(command);
			setBatchPay2AcctToken(request, command, "request");
			view.addObject("token", StringUtil.null2String(command.getToken()));
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			view.setViewName(errorView);
		}
		return view.addObject("command", command);
	}

	/**
	 * 预览上传信息
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_MASSPAY2ACCOUNT_UPLOAD")
	public ModelAndView confirm(HttpServletRequest request,
			HttpServletResponse response, BatchPay2AcctCommand command)
			throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		long memberCode = Long.valueOf(loginSession.getMemberCode());
		ModelAndView view = new ModelAndView(applyConfirmView);
		command.setPayerMemberCode(memberCode);
		command.setPayerLoginName(loginSession.getLoginName());
		synchronized (loginSession.getMemberCode()) {
			try {
				// 校验输入信息
				String rand = StringUtil.null2String(request.getSession()
						.getAttribute("rand"));
				request.getSession().removeAttribute("rand");
				if (StringUtil.isNull(command.getRandCode())
						|| StringUtil.isNull(rand)
						|| !command.getRandCode().equalsIgnoreCase(rand)) {
					init(command);
					view.setViewName(indexView);
					view.addObject("randError", "验证码错误,请重新输入");
					return view.addObject("command", command);
				}

				String message = BatchPay2AcctValidateUtils
						.validateInputInfo(command);

				// 校验付款方会员信息
				if (StringUtil.isNull(message)) {
					message = paymentValidateService
							.validatePayerMemberInfo(memberCode);
				}

				BatchPaymentReqBaseInfoDTO reqInfo = null;
				// 验证批次号是否重复
				if (StringUtil.isNull(message)) {
					reqInfo = batchPaymentReqBaseInfoService
							.getBatchPaymentReqBaseInfo(memberCode,
									OrderType.BATCHPAY2ACCT.getValue(),
									command.getBusinessBatchNo());
					if (null != reqInfo) {
						message = "您填写的业务批次号已存在，请输入新的业务批次号";
					}
				}

				// 验证批次号是否重复
				if (StringUtil.isNull(message)) {
					BatchPaymentOrderDTO batchPaymentOrderDTO = batchPaymentOrderService
							.getOrder(memberCode,
									OrderType.BATCHPAY2ACCT.getValue(),
									command.getBusinessBatchNo());
					if (null != batchPaymentOrderDTO) {
						message = "您填写的业务批次号已存在，请输入新的业务批次号";
					}
				}

				// 解析导入文件信息

				if (StringUtil.isNull(message)) {
					String fileType = request.getParameter("fileType");
					if ("xls".equals(fileType)) {
						reqInfo = batchPay2AcctRequestService.parseRequestInfo(
								command.getPaymentFile(), maxSize);
					} else if ("csv".equals(fileType)) {
						reqInfo = batchPay2AcctRequestService
								.parseRequestCsvInfo(command.getPaymentFile(),
										maxSize);
					}
					message = reqInfo.getErrorMsg();
					if (StringUtil.isNull(message)) {
						if (!command.getBusinessBatchNo().equalsIgnoreCase(
								reqInfo.getBusinessBatchNo())) {
							message = "您填写的业务批次号与上传文件中的业务批次号不一致";
						}
					}
				}

				// 校验导入基本及明细信息
				if (StringUtil.isNull(message)) {

					// 装配付款方基本信息
					MemberInfo payer = memberQueryFacadeService
							.getMemberInfo(memberCode);
					reqInfo.setPayerName(payer.getMemberName());
					reqInfo.setPayerMemberCode(memberCode);
					reqInfo.setPayerMemberType(payer.getMemberType());
					AccountInfo payerAcct = accountQueryFacadeService
							.getAccountInfo(memberCode,
									command.getPayerAcctType());
					reqInfo.setPayerAcctCode(payerAcct.getAcctCode());
					reqInfo.setPayerAcctType(command.getPayerAcctType());
					reqInfo.setPayerLoginName(loginSession.getLoginName());
					reqInfo.setCreator(loginSession.getOperatorIdentity());
					reqInfo.setCreateDate(new Date());
					reqInfo.setIsPayerPayFee(command.getIsPayerPayFee());

					reqInfo.setRequestType(OrderType.BATCHPAY2ACCT.getValue());
					reqInfo.setRequestSrc("内部批付到账户");
					batchPay2AcctRequestService.validateRequestInfo(reqInfo);

					// 设置初始状态
					reqInfo.setStatus(0);
					// 保存请求信息
					reqInfo.setRealpayAmount(reqInfo.getValidAmount()
							.longValue() + reqInfo.getFee().longValue());
					reqInfo.setRealoutAmount(reqInfo.getValidAmount());
					batchPay2AcctRequestService.saveRequestInfoRnTx(reqInfo);

					BeanUtils.copyProperties(reqInfo, command);

					command.setRealoutAmountStr(AmountUtils
							.numberFormat(command.getRealoutAmount()));
					command.setRealpayAmountStr(AmountUtils
							.numberFormat(command.getRealpayAmount()));
					command.setRequestAmountStr(AmountUtils
							.numberFormat(command.getRequestAmount()));
					command.setValidAmountStr(AmountUtils.numberFormat(command
							.getValidAmount()));
					command.setInvalidAmountStr(AmountUtils
							.numberFormat((command.getRequestAmount())
									- (command.getValidAmount())));
					command.setRequestFee(AmountUtils.numberFormat(reqInfo
							.getFee()));

				}

				if (!StringUtil.isNull(message)) {
					loadBalance(command);
					view.setViewName(indexView);
					view.addObject("message", message);
					setBatchPay2AcctToken(request, command, "request");
				} else {

					cachedCommand(request, command);
					setBatchPay2AcctToken(request, command, "confirm");
				}

				view.addObject("token",
						StringUtil.null2String(command.getToken()));
			} catch (Throwable e) {
				log.error(e.getMessage(), e);
				view.setViewName(errorView);
			}
			return view.addObject("command", command);

		}
	}

	/**
	 * 确认提交申请
	 * 
	 * @param request
	 * @param response
	 * @param command
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_MASSPAY2ACCOUNT_UPLOAD")
	public ModelAndView pay(HttpServletRequest request,
			HttpServletResponse response, BatchPay2AcctCommand command)
			throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		long memberCode = Long.valueOf(loginSession.getMemberCode());
		ModelAndView view = new ModelAndView(applySuccessView);
		command.setPayerMemberCode(memberCode);
		command.setPayerLoginName(loginSession.getLoginName());
		command.setPayerName(loginSession.getVerifyName());
		try {
			// 验证Token
			if (!BatchPay2AcctValidateUtils.validateToken(request)) {
				return index(request, response, command);
			}

			command = getCommand(request);

			// 验证支付密码
			SafeControllerWrapper safeControllerWrapper = new SafeControllerWrapper(
					request, new String[] { "passWord" });
			String paymentPwd = safeControllerWrapper.getParameter("passWord");
			String message = paymentValidateService.validatePaymentPassword(
					memberCode, command.getPayerAcctType(),
					loginSession.getOperatorId(), paymentPwd);

			if (!StringUtil.isNull(message)) {
				view.setViewName(applyConfirmView);
				view.addObject("passwordTip", StringUtil.null2String(message));
				setBatchPay2AcctToken(request, command, "confirm");
				view.addObject("token",
						StringUtil.null2String(command.getToken()));
				return view.addObject("command", command);
			}

			// 校验付款方会员信息
			if (StringUtil.isNull(message)) {
				message = paymentValidateService
						.validatePayerMemberInfo(memberCode);
			}

			// 验证付款方账户状态
			if (StringUtil.isNull(message)) {
				message = paymentValidateService.validatePayerAcctInfo(
						memberCode, command.getPayerAcctType());
			}

			// 更新请求基本信息状态为待审核状态
			if (StringUtil.isNull(message)) {
				boolean isHaveProduct = memberProductService.isHaveProduct(
						memberCode,
						com.pay.fo.order.common.FundoutProduct.AUDIT_BATCH2ACCT
								.getCode());
				// 添加是否开通复核功能判断，如果未开通复核功能，直接调用复核通过逻辑
				if (isHaveProduct) {
					BatchPaymentReqBaseInfoDTO reqInfo = batchPaymentReqBaseInfoService
							.getBatchPaymentReqBaseInfo(command.getRequestSeq());
					reqInfo.setStatus(1);
					reqInfo.setUpdateDate(new Date());
					reqInfo.setBusinessBatchNo(command.getBusinessBatchNo());
					batchPay2AcctRequestService.confirmRequestInfoRdTx(reqInfo);
				} else {
					batchPay2AcctRequestService.auditPassRequestRdTx(
							command.getRequestSeq(),
							loginSession.getOperatorIdentity(),
							command.getAuditRemark(), 0);
				}
			}

			if (!StringUtil.isNull(message)) {
				loadBalance(command);
				view.setViewName(indexView);
				view.addObject("message", message);
				setBatchPay2AcctToken(request, command, "request");
			} else {

				removeCommandCache(request);
				removeBatchPay2AcctToken(request);
			}

			view.addObject("token", StringUtil.null2String(command.getToken()));
		} catch (Throwable e) {
			log.error(e.getMessage(), e);
			view.setViewName(errorView);
		}
		return view.addObject("command", command);

	}

	private void init(BatchPay2AcctCommand command) throws Exception {
		// 默认选择人民币账户 获取余额
		loadBalance(command);
	}

	/**
	 * 加载余额
	 * 
	 * @param command
	 */
	private void loadBalance(BatchPay2AcctCommand command) {
		long balance = accountQueryFacadeService.getBalance(
				command.getPayerMemberCode(), command.getPayerAcctType());
		command.setCurrentBanlance(balance);
		command.setCurrentBanlanceStr(AmountUtils.numberFormat(balance));
		if (AmountUtils.checkAmount(command.getRequestAmountStr())) {
			command.setRequestAmount(AmountUtils.toLongAmount(command
					.getRequestAmountStr()));
		}
	}

	private void setBatchPay2AcctToken(HttpServletRequest request,
			BatchPay2AcctCommand command, String step) {
		String token = step + UUID.randomUUID().toString();
		command.setToken(token);
		request.getSession().setAttribute("token_batchpay2acct", token);
	}

	private void removeBatchPay2AcctToken(HttpServletRequest request) {
		request.getSession().removeAttribute("token_batchpay2acct");
	}

	/**
	 * 将请求表单信息存储到Session中
	 * 
	 * @param request
	 * @param command
	 */
	private void cachedCommand(HttpServletRequest request,
			BatchPay2AcctCommand command) {
		request.getSession().setAttribute("BatchPay2AcctCommand", command);
	}

	/**
	 * 删除Session中的表单信息
	 * 
	 * @param request
	 */
	private void removeCommandCache(HttpServletRequest request) {
		request.getSession().removeAttribute("BatchPay2AcctCommand");
	}

	/**
	 * 获取Session中的表单信息
	 * 
	 * @param request
	 * @return
	 */
	private BatchPay2AcctCommand getCommand(HttpServletRequest request) {
		Object obj = request.getSession().getAttribute("BatchPay2AcctCommand");
		if (obj != null) {
			return (BatchPay2AcctCommand) obj;
		}
		return new BatchPay2AcctCommand();
	}

	/**
	 * @param indexView
	 *            the indexView to set
	 */
	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	/**
	 * @param applyConfirmView
	 *            the applyConfirmView to set
	 */
	public void setApplyConfirmView(String applyConfirmView) {
		this.applyConfirmView = applyConfirmView;
	}

	/**
	 * @param successView
	 *            the successView to set
	 */
	public void setApplySuccessView(String applySuccessView) {
		this.applySuccessView = applySuccessView;
	}

	/**
	 * @param errorView
	 *            the errorView to set
	 */
	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}

	/**
	 * @param maxSize
	 *            the maxSize to set
	 */
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
	
	public void setBatchPaymentOrderService(
			BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}

	@Override
	protected void initBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) throws Exception {
		super.initBinder(request, binder);
		binder.registerCustomEditor(byte[].class,
				new ByteArrayMultipartFileEditor());
	}

}
