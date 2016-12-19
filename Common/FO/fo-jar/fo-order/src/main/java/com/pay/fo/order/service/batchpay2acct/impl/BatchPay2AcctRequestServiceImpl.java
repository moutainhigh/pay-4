/**
 * 
 */
package com.pay.fo.order.service.batchpay2acct.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.fo.order.common.CSVAnalysis;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToAcctReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.RequestDetail;
import com.pay.fo.order.service.batchpay2acct.BatchPay2AcctOrderService;
import com.pay.fo.order.service.batchpay2acct.BatchPay2AcctRequestService;
import com.pay.fo.order.service.batchpay2acct.ParseBatchPay2AcctTemplateUtils;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;
import com.pay.fo.order.service.batchpayment.BatchPaymentToAcctReqDetailService;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.fundout.util.AmountUtils;
import com.pay.inf.exception.AppException;
import com.pay.jms.notification.request.RequestType;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;
import com.pay.util.StringUtil;

/**
 * @author NEW
 * 
 */
public class BatchPay2AcctRequestServiceImpl implements BatchPay2AcctRequestService {

	/**
	 * 风控限额限次查询服务类
	 */
	protected FoRcLimitFacade foRcLimitFacade;
	/**
	 * 批量付款请求基本信息服务类
	 */
	private BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService;
	/**
	 * 批付到账户请求明细服务类
	 */
	private BatchPaymentToAcctReqDetailService batchPaymentToAcctReqDetailService;

	/**
	 * 付款验证服务类
	 */
	private PaymentValidateService paymentValidateService;

	/**
	 * 批量付款到账户订单服务类
	 */
	private BatchPay2AcctOrderService batchPay2AcctOrderService;
	/**
	 * 消息通知服务类
	 */
	private NotifyFacadeService notifyFacadeService;
	/**
	 * 算费服务
	 */
	private AccountingService reqAccountingService;
	/**
	 * 请求成功Email模板ID
	 */
	private Long reqEmailTemplateId;
	/**
	 * 审批通过Email模板ID
	 */
	private Long passEmailTempalteId;
	/**
	 * 审批通拒绝Email模板ID
	 */
	private Long rejectEmailTempalteId;

	@Override
	public BatchPaymentReqBaseInfoDTO parseRequestInfo(byte[] file, int maxSize) throws IOException {
		BatchPaymentReqBaseInfoDTO baseInfo = new BatchPaymentReqBaseInfoDTO();
		List<RequestDetail> details = new ArrayList<RequestDetail>();

		HSSFWorkbook book = new HSSFWorkbook(new ByteArrayInputStream(file));
		HSSFSheet sheet = book.getSheetAt(0);

		if (null == sheet) {
			return baseInfo;
		}

		baseInfo.setBusinessBatchNo(ParseBatchPay2AcctTemplateUtils.getBusinessBatchNo(sheet));
		// sheet.getLastRowNum() 取索引值
		int totalRows = sheet.getLastRowNum() + 1;
		if (totalRows > maxSize + 2) {
			baseInfo.setErrorMsg("您请求处理的记录数不能大于" + maxSize + "条");
			return baseInfo;
		}

		BatchPaymentToAcctReqDetailDTO detail = null;
		for (int i = 0; i < totalRows; i++) {
			HSSFRow row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			// 校验模板
			if (i == 0) {
				String tmpId = row.getCell(0).getStringCellValue();
				if (!ParseBatchPay2AcctTemplateUtils.TEMPLATEID.equalsIgnoreCase(tmpId)) {
					baseInfo.setErrorMsg("请求处理的模板不正确");
					return baseInfo;
				}
				continue;

			}
			if (i == 1) {
				if (!ParseBatchPay2AcctTemplateUtils.validateHeadRow(row)) {
					baseInfo.setErrorMsg("请求处理的模板不正确");
					return baseInfo;
				}
				continue;
			}

			detail = ParseBatchPay2AcctTemplateUtils.getgetBatchPaymentToAcctReqDetail(row);
			// 验证是否是有效的明细对象
			if (ParseBatchPay2AcctTemplateUtils.isValidDetail(detail)) {
				details.add(detail);
			}
		}
		baseInfo.setRequestDetails(details);
		return baseInfo;
	}

	public BatchPaymentReqBaseInfoDTO parseRequestCsvInfo(byte[] file, int maxSize) throws IOException {
		BatchPaymentReqBaseInfoDTO baseInfo = new BatchPaymentReqBaseInfoDTO();
		List<RequestDetail> details = new ArrayList<RequestDetail>();
		BatchPaymentToAcctReqDetailDTO detail = null;
		List<String[]> rowList = CSVAnalysis.readCSVFile(file);
		int totalRows = rowList.size() + 1;
		if (totalRows > maxSize + 2) {
			baseInfo.setErrorMsg("您请求处理的记录数不能大于" + maxSize + "条");
			return baseInfo;
		}
		for (int i = 0; i < rowList.size(); i++) {
			String[] row = rowList.get(i);
			if (i == 0) {
				String tmpId = String.valueOf(row[0]);
				if (!ParseBatchPay2AcctTemplateUtils.TEMPLATEID.equalsIgnoreCase(tmpId)) {
					baseInfo.setErrorMsg("请求处理的模板不正确");
					return baseInfo;
				}
				baseInfo.setBusinessBatchNo(row[2].toString());
				continue;
			}
			if (i == 1)
				continue;

			detail = new BatchPaymentToAcctReqDetailDTO();
			detail.setForeignOrderId(row[0]);
			detail.setPayeeName(row[1]);
			detail.setPayeeLoginName(row[2]);
			detail.setRequestAmount(row[3]);
			detail.setRemark(row[4]);
			
			// 验证是否是有效的明细对象
			if (ParseBatchPay2AcctTemplateUtils.isValidDetail(detail)) {
				details.add(detail);
			}
		}
		baseInfo.setRequestDetails(details);
		return baseInfo;
	}

	@Override
	public void validateRequestInfo(BatchPaymentReqBaseInfoDTO reqInfo) {
		List<RequestDetail> details = reqInfo.getRequestDetails();
		if (details == null || details.size() < 1) {
			return;
		}
		int totalNum = 0;
		int validNum = 0;
		long totalAmount = 0L;
		long validAmount = 0L;
		long totalFee = 0L;
		long fee = 0L;

		List<RequestDetail> processDetails = new ArrayList<RequestDetail>();
		
		RCLimitResultDTO rule = foRcLimitFacade.getBusinessRcLimit(RCLIMITCODE.FO_PAY_ENTERPRISE_ACC2P.getKey(), null, reqInfo.getPayerMemberCode());
		if (rule == null) {
			reqInfo.setErrorMsg("暂不支持该业务");
			LogUtil.error(this.getClass(), "批量付款到账户", OPSTATUS.EXCEPTION, "", "", "未获取到风控限额限次配置信息", "", "验证请求信息发生异常");
			return;
		}

		for (RequestDetail requestDetail : details) {
			if (requestDetail instanceof BatchPaymentToAcctReqDetailDTO) {
				BatchPaymentToAcctReqDetailDTO detail = (BatchPaymentToAcctReqDetailDTO) requestDetail;
				// 验证请求明细基本信息
				ParseBatchPay2AcctTemplateUtils.validateRequestDetail(detail, rule);
				if (detail.getValidateStatus().intValue() == 1) {
					// 验证收款方信息（是否是有效的会员、会员状态、账户状态）
					String message = paymentValidateService
							.validatePayeeMemberInfo(detail.getPayeeLoginName(), detail.getPayeeName(), String.valueOf(reqInfo.getPayerMemberCode()));
					if (StringUtil.isNull(message)) {
						message = paymentValidateService.validatePayeeAcctInfo(detail.getPayeeLoginName(), reqInfo.getPayerAcctType());
					}
					if (!StringUtil.isNull(message)) {
						detail.setErrorMsg(message);
						detail.setValidateStatus(0);
					}
				}
				
				
				if (detail.getValidateStatus().intValue() == 1) {
					// 统计有效记录和有效金额
					caculateFee(reqInfo.getPayerMemberCode(), reqInfo.getIsPayerPayFee(), detail);
					fee = detail.getPayerFee();
					validNum = validNum + 1;
					validAmount = validAmount + detail.getPaymentAmount().longValue();
					totalFee = totalFee + Math.abs(fee);
					
					detail.setPayerFee(fee);
					detail.setRealpayAmount(detail.getPaymentAmount().longValue()+fee);
					detail.setRealoutAmount(detail.getPaymentAmount().longValue());
				} else {
					detail.setPayerFee(0L);
					detail.setRealpayAmount(0L);
					detail.setRealoutAmount(0L);
				}
				processDetails.add(detail);
				// 统计总记录数 和金额
				totalNum = totalNum + 1;
				totalAmount = totalAmount + detail.getPaymentAmount().longValue();
			}

		}
		reqInfo.setRequestDetails(processDetails);
		reqInfo.setFee(totalFee);
		reqInfo.setTotalFee(totalFee);
		reqInfo.setRequestCount(totalNum);
		reqInfo.setRequestAmount(totalAmount);
		reqInfo.setValidCount(validNum);
		reqInfo.setValidAmount(validAmount);
		
		if (reqInfo.getIsPayerPayFee() == 1) {
			reqInfo.setRealpayAmount(totalFee + validAmount);
			reqInfo.setRealoutAmount(validAmount);
		} else {
			reqInfo.setRealpayAmount(validAmount);
			reqInfo.setRealoutAmount(validAmount - totalFee);
		}
	}
	
	
	/**
	 * 获取费用
	 * 
	 * @param mass
	 * @param detail
	 */
	private void caculateFee(Long memberCode, Integer isPayerPayFee, BatchPaymentToAcctReqDetailDTO detail) {

		AccountingFeeRe accountingFeeRe = new AccountingFeeRe();
		accountingFeeRe.setAmount(detail.getPaymentAmount());
		accountingFeeRe.setPayer(memberCode);
		AccountingFeeRes dealResponse = null;
		try {
			dealResponse = reqAccountingService.caculateFee(accountingFeeRe);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Long fee = null;
		// 收款方付费与付款方付费都从payerFee中取值
		fee = dealResponse.getPayerFee();
		if (fee == null) {
			fee = 0L;
		}
		detail.setPayerFee(fee);

		if (isPayerPayFee == 1) {
			detail.setRealpayAmount(fee + detail.getPaymentAmount());
			detail.setRealoutAmount(detail.getPaymentAmount());
		} else {
			detail.setRealpayAmount(detail.getPaymentAmount());
			detail.setRealoutAmount(detail.getPaymentAmount() - fee);
		}
	}

	@Override
	public void saveRequestInfoRnTx(BatchPaymentReqBaseInfoDTO reqInfo) throws AppException {
		try {
			// 付款方付费

			// reqInfo.setRealpayAmount(reqInfo.getValidAmount().longValue() +
			// reqInfo.getFee().longValue());
			// reqInfo.setRealoutAmount(reqInfo.getValidAmount());
			// 存储请求基本信息
			Long requestSeq = batchPaymentReqBaseInfoService.create(reqInfo);
			reqInfo.setRequestSeq(requestSeq);
			if (requestSeq == null) {
				throw new RuntimeException("保存请求基本信息发生异常");
			}
			List<RequestDetail> details = reqInfo.getRequestDetails();
			if (details == null || details.size() == 0) {
				throw new RuntimeException("至少需要请求一条明细记录");
			}
			// 存储请求明细
			batchPaymentToAcctReqDetailService.create(details, reqInfo);
		} catch (Exception ex) {
			LogUtil.error(this.getClass(), "批量付款到账户", OPSTATUS.EXCEPTION, "", "", ex.getMessage(), "", "存储请求信息发生异常");
			throw new AppException(ex);
		}
	}

	@Override
	public void confirmRequestInfoRdTx(BatchPaymentReqBaseInfoDTO reqInfo) throws AppException {
		try {
			if (!batchPaymentReqBaseInfoService.updateStatus(reqInfo, 0)) {
				throw new RuntimeException("确认提交请求发生异常");
			}
			sendEmail(reqInfo, reqInfo.getCreator(), "上传批量付款到账户文件成功", reqEmailTemplateId);
		} catch (Throwable e) {
			LogUtil.error(this.getClass(), "批量付款到账户", OPSTATUS.EXCEPTION, "", "", e.getMessage(), "", "确认提交请求发生异常");
			throw new AppException(e);
		}
	}

	@Override
	public void auditPassRequestRdTx(Long requestSeq, String auditor, String auditRemark) throws AppException {
		auditPassRequestRdTx(requestSeq, auditor, auditRemark, 1);
	}
	
	@Override
	public void auditPassRequestRdTx(Long requestSeq, String auditor, String auditRemark, int oldStaus) throws AppException {
		try {
			BatchPaymentReqBaseInfoDTO reqInfo = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(requestSeq);
			reqInfo.setAuditor(auditor);
			reqInfo.setUpdateDate(new Date());
			reqInfo.setAuditRemark(auditRemark);
			reqInfo.setStatus(2);

			// 更新批量付款请求审批状态
			if (batchPaymentReqBaseInfoService.updateStatus(reqInfo, oldStaus)) {

				BatchPaymentOrderDTO order = buildBatchPaymentOrder(reqInfo);
				batchPay2AcctOrderService.createOrder(order);
				// 发送邮件通知
				sendEmail(reqInfo, reqInfo.getAuditor(), "复核批量付款到账户文件通过", passEmailTempalteId);

			} else {
				LogUtil.error(getClass(), "批量付款到账户", OPSTATUS.FAIL, "auditPassRequestRdTx", "", "更新审核状态失败", "", "批量付款到账户异常");
				throw new RuntimeException("更新审核状态失败");
			}
		} catch (Throwable e) {
			LogUtil.error(getClass(), "批量付款到账户", OPSTATUS.EXCEPTION, "auditPassRequestRdTx", "", "审核通过处理或发生异常", "", "批量付款到账户异常");
			throw new AppException(e);
		}
	}

	private BatchPaymentOrderDTO buildBatchPaymentOrder(BatchPaymentReqBaseInfoDTO reqInfo) {
		BatchPaymentOrderDTO order = new BatchPaymentOrderDTO();
		order.setBusinessBatchNo(reqInfo.getBusinessBatchNo());
		order.setCreateDate(reqInfo.getUpdateDate());
		order.setCreator(reqInfo.getAuditor());
		order.setOrderType(OrderType.BATCHPAY2ACCT.getValue());
		order.setOrderStatus(OrderStatus.INIT.getValue());
		order.setOrderSmallType(OrderSmallType.COMMON_BATCHPAY2ACCT.getValue());
		order.setPayerAcctCode(reqInfo.getPayerAcctCode());
		order.setPayerAcctType(reqInfo.getPayerAcctType());
		order.setPayerLoginName(reqInfo.getPayerLoginName());
		order.setPayerMemberCode(reqInfo.getPayerMemberCode());
		order.setPayerMemberType(reqInfo.getPayerMemberType());
		order.setPayerName(reqInfo.getPayerName());
		order.setOrderAmount(reqInfo.getValidAmount());
		order.setIsPayerPayFee(reqInfo.getIsPayerPayFee());
		order.setFee(reqInfo.getFee());
		order.setRealoutAmount(reqInfo.getRealoutAmount());
		order.setRealpayAmount(reqInfo.getRealpayAmount());
		order.setPaymentCount(reqInfo.getValidCount());

		return order;
	}

	@Override
	public void auditRejectRequestRdTx(Long requestSeq, String auditor, String auditRemark) {
		BatchPaymentReqBaseInfoDTO reqInfo = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(requestSeq);
		reqInfo.setAuditor(auditor);
		reqInfo.setAuditRemark(auditRemark);
		reqInfo.setUpdateDate(new Date());
		reqInfo.setStatus(3);
		// 更新批量付款请求审批状态为审核拒绝
		batchPaymentReqBaseInfoService.updateStatus(reqInfo, 1);

		sendEmail(reqInfo, auditor, "复核批量付款到账户文件拒绝", rejectEmailTempalteId);

	}

	/**
	 * 供请求成功、审批通过、审批拒绝时发送邮件
	 * 
	 * @param reqInfo
	 * @param operator
	 * @param subject
	 * @param templateId
	 */
	private void sendEmail(BatchPaymentReqBaseInfoDTO reqInfo, String operator, String subject, long templateId) {
		Map<String, String> data = new HashMap<String, String>();

		data.put("memberName", reqInfo.getPayerName());
		data.put("operator", operator);
		data.put("batchNum", reqInfo.getBusinessBatchNo());
		data.put("totalCount", String.valueOf(reqInfo.getRequestCount()));
		data.put("totalAmount", AmountUtils.numberFormat(reqInfo.getRequestAmount()));
		data.put("payTotalCount", String.valueOf(reqInfo.getValidCount()));
		data.put("payTotalAmount", AmountUtils.numberFormat(reqInfo.getValidAmount()));
		data.put("errorCount", String.valueOf(reqInfo.getRequestCount() - reqInfo.getValidCount()));
		data.put("errorAmount", AmountUtils.numberFormat(reqInfo.getRequestAmount() - reqInfo.getValidAmount()));
		data.put("targetAddress", reqInfo.getPayerLoginName());

		sendEmail(data, subject, templateId);
	}

	/**
	 * 发送邮件
	 * 
	 * @param data
	 * @param subject
	 * @param templateId
	 */
	private void sendEmail(Map<String, String> data, String subject, long templateId) {
		NotifyTargetRequest request = new NotifyTargetRequest();
		request.setData(data);
		request.setNotifyType(RequestType.EMAIL.value());
		request.setFromAddress(Constants.SYSTEM_MAIL);
		request.setSubject(subject);
		request.getRecAddress().add(data.get("targetAddress"));
		request.setTemplateId(Long.valueOf(templateId));
		request.setRequestTime(new Date());
		notifyFacadeService.notifyRequest(request);
	}

	/**
	 * @param batchPaymentReqBaseInfoService
	 *            the batchPaymentReqBaseInfoService to set
	 */
	public void setBatchPaymentReqBaseInfoService(BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService) {
		this.batchPaymentReqBaseInfoService = batchPaymentReqBaseInfoService;
	}

	/**
	 * @param batchPaymentToAcctReqDetailService
	 *            the batchPaymentToAcctReqDetailService to set
	 */
	public void setBatchPaymentToAcctReqDetailService(BatchPaymentToAcctReqDetailService batchPaymentToAcctReqDetailService) {
		this.batchPaymentToAcctReqDetailService = batchPaymentToAcctReqDetailService;
	}

	/**
	 * @param foRcLimitFacade
	 *            the foRcLimitFacade to set
	 */
	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}

	/**
	 * @param paymentValidateService
	 *            the paymentValidateService to set
	 */
	public void setPaymentValidateService(PaymentValidateService paymentValidateService) {
		this.paymentValidateService = paymentValidateService;
	}

	/**
	 * @param batchPay2AcctOrderService
	 *            the batchPay2AcctOrderService to set
	 */
	public void setBatchPay2AcctOrderService(BatchPay2AcctOrderService batchPay2AcctOrderService) {
		this.batchPay2AcctOrderService = batchPay2AcctOrderService;
	}

	/**
	 * @param notifyFacadeService
	 *            the notifyFacadeService to set
	 */
	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	/**
	 * @param reqEmailTemplateId
	 *            the reqEmailTemplateId to set
	 */
	public void setReqEmailTemplateId(Long reqEmailTemplateId) {
		this.reqEmailTemplateId = reqEmailTemplateId;
	}

	/**
	 * @param passEmailTempalteId
	 *            the passEmailTempalteId to set
	 */
	public void setPassEmailTempalteId(Long passEmailTempalteId) {
		this.passEmailTempalteId = passEmailTempalteId;
	}

	/**
	 * @param rejectEmailTempalteId
	 *            the rejectEmailTempalteId to set
	 */
	public void setRejectEmailTempalteId(Long rejectEmailTempalteId) {
		this.rejectEmailTempalteId = rejectEmailTempalteId;
	}

	public void setReqAccountingService(AccountingService reqAccountingService) {
		this.reqAccountingService = reqAccountingService;
	}
	
}
