/**
 * 
 */
package com.pay.fo.order.service.batchpay2bank.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.pay.api.model.MerchantConfigure;
import com.pay.api.model.MerchantConfigureCriteria;
import com.pay.fo.order.common.CSVAnalysis;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.common.UploadFileType;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fo.order.dto.batchpayment.RequestDetail;
import com.pay.fo.order.dto.task.PaymentTaskInfoDTO;
import com.pay.fo.order.model.base.FundoutOrder;
import com.pay.fo.order.model.batchpayment.BatchPaymentOrder;
import com.pay.fo.order.model.batchpayment.BatchPaymentTotalInfo;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankOrderService;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankRequestService;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankTemplateUtils;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;
import com.pay.fo.order.service.batchpayment.BatchPaymentToBankReqDetailService;
import com.pay.fo.order.service.task.PaymentTaskInfoService;
import com.pay.fo.order.validate.BatchPaymentRequest;
import com.pay.fo.order.validate.BatchPaymentResponse;
import com.pay.fo.order.validate.ValidateService;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.fundout.util.AmountUtils;
import com.pay.fundout.util.FreeMarkParseUtil;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.exception.AppException;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.sender.JmsSender;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.service.inf.input.BankInfoFacadeService;
import com.pay.poss.service.inf.input.ProvinceCityFacadeService;
import com.pay.poss.service.ma.pay2bank.Pay2BankValidateService;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.rm.FoRcLimitFacade;
import com.pay.rm.facade.dto.RCLimitResultDTO;
import com.pay.rm.facade.util.RCLIMITCODE;
import com.pay.util.Base64Util;
import com.pay.util.MD5Util;
import com.pay.util.StringUtil;

/**
 * @author NEW
 * 
 */
public class BatchPay2BankRequestServiceImpl implements
		BatchPay2BankRequestService {

	/**
	 * 批量付款请求基本信息服务类
	 */
	private BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService;
	/**
	 * 批付到银行请求明细服务类
	 */
	private BatchPaymentToBankReqDetailService batchPaymentToBankReqDetailService;
	private Log logger = LogFactory
			.getLog(BatchPay2BankRequestServiceImpl.class);

	/**
	 * 银行信息服务类
	 */
	private BankInfoFacadeService bankInfoFacadeService;
	/**
	 * 省份城市信息服务类
	 */
	private ProvinceCityFacadeService provinceCityFacadeService;
	/**
	 * 付款到银行验证服务类
	 */
	private Pay2BankValidateService pay2BankValidateService;
	/**
	 * 配置银行服务类
	 */
	private ConfigBankService configBankService;

	private Map<String, Integer> provinceCode;

	private Map<String, Integer> cityCode;

	private Map<String, String> bankCode;

	/**
	 * 风控限额限次查询服务类
	 */
	private FoRcLimitFacade foRcLimitFacade;

	// 算费服务
	private AccountingService reqAccountingService;
	/**
	 * 批量付款到银行订单服务类
	 */
	private BatchPay2BankOrderService batchPay2BankOrderService;
	/**
	 * 消息通知服务类
	 */
	private NotifyFacadeService notifyFacadeService;
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

	private BatchPaymentOrderService batchPaymentOrderService;

	private ValidateService batch2bankValidateService;
	
	private PaymentTaskInfoService paymentTaskInfoService;
	
	/**
	 * 商户通知队列名
	 */
	private String queueName;
	
	private String apiNotifyTemplate;
	private BaseDAO merchantConfigureDao;
	private Long apiNotifyTemplateId;
	// 消息发送服务
	private JmsSender jmsSender;
	
	/**
	 * 解析批次文件
	 * 
	 * @param file
	 * @param maxSize
	 * @param businessBatchNo
	 * @return
	 */
	public void parseRequestInfo(byte[] file ,BatchPaymentReqBaseInfoDTO reqInfo) {

		HSSFSheet sheet = getSheet(file);
		BatchPaymentRequest request = new BatchPaymentRequest();
		request.setSheet(sheet);
		request.setBusinessNo(reqInfo.getBusinessBatchNo());
		request.setMemberCode(reqInfo.getPayerMemberCode());
		request.setIsPayerPayFee(reqInfo.getIsPayerPayFee());
		
		String errorMsg = null;
		List<RequestDetail> details = null;
		try {
			// 验证批文件信息
			batch2bankValidateService.validate(request);
			
			BatchPaymentResponse response = request.getBatchPaymentResponse();
			details = response.getDetails();
			errorMsg = response.getErrorMsg();
			reqInfo.setErrorMsg(errorMsg);
			reqInfo.setRequestDetails(details);
			reqInfo.setFee(response.getTotalFee());
			
			reqInfo.setRequestCount(response.getTotalNum());
			reqInfo.setRequestAmount(response.getTotalAmount());
			reqInfo.setValidCount(response.getValidNum());
			reqInfo.setValidAmount(response.getValidAmount());
			reqInfo.setRealpayAmount(response.getRealpayAmount());
			reqInfo.setRealoutAmount(response.getRealoutAmount());
			
		} catch (Exception e) {
			logger.error("validate batch2bank excel error:", e);
			errorMsg = "请上传正确的批次文件！";
		}
	}
	public void parseRequestInfo(InputStream file ,BatchPaymentReqBaseInfoDTO reqInfo,int fileType) {
		BatchPaymentRequest request = new BatchPaymentRequest();
		request.setFileType(fileType);
		if(UploadFileType.xls.getValue() == fileType){
			Sheet sheet = getSheet(file);
			request.setJxlSheet(sheet);
		}else if(UploadFileType.csv.getValue() == fileType){
			try {
				List<String[]> csvList = CSVAnalysis.readCSVFile(file);
				request.setCsvList(csvList);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		request.setBusinessNo(reqInfo.getBusinessBatchNo());
		request.setMemberCode(reqInfo.getPayerMemberCode());
		request.setIsPayerPayFee(reqInfo.getIsPayerPayFee());
		
		//----------added  PengJiangbo
		request.setPayerAcctType(reqInfo.getPayerAcctType());
		request.setPayeeAcctType(reqInfo.getPayeeAcctType());
		request.setPayerCurrencyCode(reqInfo.getPayerCurrencyCode());
		request.setPayeeCurrencyCode(reqInfo.getPayeeCurrencyCode());
		//----------added  PengJiangbo end
		
		
		String errorMsg = null;
		List<RequestDetail> details = null;
		try {
			// 验证批文件信息
			batch2bankValidateService.validate(request);
			BatchPaymentResponse response = request.getBatchPaymentResponse();
			details = response.getDetails();
			errorMsg = response.getErrorMsg();
			reqInfo.setErrorMsg(errorMsg);
			reqInfo.setRequestDetails(details);
			reqInfo.setFee(response.getTotalFee());
			
			reqInfo.setRequestCount(response.getTotalNum());
			reqInfo.setRequestAmount(response.getTotalAmount());
			reqInfo.setValidCount(response.getValidNum());
			reqInfo.setValidAmount(response.getValidAmount());
			reqInfo.setRealpayAmount(response.getRealpayAmount());
			reqInfo.setRealoutAmount(response.getRealoutAmount());
			
		} catch (Exception e) {
			logger.error("validate batch2bank excel error:", e);
			errorMsg = "请上传正确的批次文件！";
		}
	}

	private HSSFSheet getSheet(byte[] file) {
		HSSFWorkbook book = null;
		HSSFSheet sheet = null;
		try {
			book = new HSSFWorkbook(new ByteArrayInputStream(file));
			sheet = book.getSheetAt(0);
		} catch (IOException e) {
			logger.error("paser file error:", e);
		}
		return sheet;
	}
	private Sheet getSheet(InputStream file) {
		Workbook book = null;
		Sheet sheet = null;
		try {
			book = Workbook.getWorkbook(file);
			sheet = book.getSheet(0);
		} catch (Exception e) {
			logger.error("paser file error:", e);
		}
		return sheet;
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
		long fee = 0L;
		long totalFee = 0L;

		// 加载银行
		List<Bank> banks = bankInfoFacadeService.getWithdrawBanks();
		this.setBankCode(banks);
		// 加载所有省份
		List<ProvinceDTO> proList = provinceCityFacadeService.getAllProvince();
		this.setProvinceCode(proList);
		// 加载城市
		Map<String, Integer> cityMap = new HashMap<String, Integer>();
		for (ProvinceDTO pro : proList) {
			List<CityDTO> cityList = provinceCityFacadeService
					.getProvincesCity(pro.getProvincecode());
			cityMap = proCityMap(pro.getProvincecode(), cityMap, cityList);
		}
		this.setCityCode(cityMap);

		RCLimitResultDTO rule = foRcLimitFacade.getBusinessRcLimit(
				RCLIMITCODE.FO_PAY_ENTERPRISE_BANK2P.getKey(), null, reqInfo
						.getPayerMemberCode());
		;
		if (rule == null) {
			reqInfo.setErrorMsg("暂不支持该请求");
			return;
		}
		for (RequestDetail requestDetail : details) {
			if (requestDetail instanceof BatchPaymentToBankReqDetailDTO) {
				BatchPaymentToBankReqDetailDTO detail = (BatchPaymentToBankReqDetailDTO) requestDetail;
				// 验证请求明细基本信息
				BatchPay2BankTemplateUtils.validateRequestDetail(detail, rule,
						provinceCode, cityCode, bankCode);

				// 出款行验证
				if (!StringUtil.isNull(detail.getPayeeBankAcctCode())
						&& detail.getValidStatus() == 1) {
					if (StringUtil.isNull(getOutBankCode(String.valueOf(detail
							.getPayeeBankCode())))) {
						detail.setErrorMsg("暂不支持付款到"
								+ detail.getPayeeBankName());
						detail.setValidStatus(0);
					}
				}

				// 验证是否支持出款
				if ("C".equalsIgnoreCase(detail.getTradeType())
						&& detail.getValidStatus() == 1) {
					if (!StringUtil.isNull(pay2BankValidateService
							.validateCardBin(detail.getPayeeBankAcctCode(),
									String.valueOf(detail.getPayeeBankCode())))) {
						detail.setErrorMsg("银行账号不正确");
						detail.setValidStatus(0);
					}
				}
				detail.setIsPayerPayFee(reqInfo.getIsPayerPayFee());
				// 统计有效记录和有效金额
				if (detail.getValidStatus().intValue() == 1) {
					caculateFee(reqInfo, detail);
					fee = detail.getFee();
					validNum = validNum + 1;
					validAmount = validAmount
							+ detail.getPaymentAmount().longValue();
					totalFee = totalFee + Math.abs(fee);
				} else {
					detail.setFee(0L);
					detail.setRealpayAmount(0L);
					detail.setRealoutAmount(0L);
				}
				// 统计总记录数 和金额
				totalNum = totalNum + 1;
				totalAmount = totalAmount
						+ detail.getPaymentAmount().longValue();
			}

		}
		reqInfo.setFee(totalFee);
		reqInfo.setRequestCount(totalNum);
		reqInfo.setRequestAmount(totalAmount);
		reqInfo.setValidCount(validNum);
		reqInfo.setValidAmount(validAmount);
		if (reqInfo.getIsPayerPayFee() == 1) {
			reqInfo.setRealpayAmount(fee + validAmount);
			reqInfo.setRealoutAmount(validAmount);
		} else {
			reqInfo.setRealpayAmount(validAmount);
			reqInfo.setRealoutAmount(validAmount - fee);
		}
	}

	private String getOutBankCode(String bankCode) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 目的银行编号
		map.put("targetBankId", bankCode);
		// 出款方式
		map.put("foMode", "1");
		// 出款业务
		map.put("fobusiness", OrderType.BATCHPAY2BANK.getValue());// 3 付款到银行

		return StringUtil.null2String(configBankService
				.queryFundOutBank2Withdraw(map));
	}

	/**
	 * 获取费用
	 * 
	 * @param mass
	 * @param detail
	 */
	private void caculateFee(BatchPaymentReqBaseInfoDTO reqInfo,
			BatchPaymentToBankReqDetailDTO detail) {

		detail.setIsPayerPayFee(reqInfo.getIsPayerPayFee());
		AccountingFeeRe accountingFeeRe = new AccountingFeeRe();
		accountingFeeRe.setAmount(detail.getPaymentAmount());
		accountingFeeRe.setPayer(reqInfo.getPayerMemberCode());
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
		detail.setFee(fee);

		if (reqInfo.getIsPayerPayFee() == 1) {
			detail.setRealpayAmount(fee + detail.getPaymentAmount());
			detail.setRealoutAmount(detail.getPaymentAmount());
		} else {
			detail.setRealpayAmount(detail.getPaymentAmount());
			detail.setRealoutAmount(detail.getPaymentAmount() - fee);
		}
	}

	@Override
	public void saveRequestInfoRnTx(BatchPaymentReqBaseInfoDTO reqInfo)
			throws AppException {
		try {
			// 付款方付费
			//reqInfo.setRealpayAmount(reqInfo.getValidAmount().longValue() + reqInfo.getFee().longValue());
			//reqInfo.setRealoutAmount(reqInfo.getValidAmount());
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
			batchPaymentToBankReqDetailService.create(details, reqInfo);
		} catch (Exception ex) {
			LogUtil.error(this.getClass(), "批量付款到银行", OPSTATUS.EXCEPTION, "",
					"", ex.getMessage(), "", "存储请求信息发生异常");
			throw new AppException(ex);
		}

	}

	@Override
	public void confirmRequestInfoRdTx(BatchPaymentReqBaseInfoDTO reqInfo)
			throws AppException {
		try {
			if (!batchPaymentReqBaseInfoService.updateStatus(reqInfo, 0)) {
				throw new RuntimeException("确认提交请求发生异常");
			}
			
			//如果是定期处理，则存储任务信息
			if(reqInfo.getProcessType()!=null&&reqInfo.getProcessType()==1){
				Date current = new Date();
				PaymentTaskInfoDTO task = new PaymentTaskInfoDTO();
				task.setCreateDate(current);
				task.setExcuteDate(reqInfo.getExcuteDate());
				task.setMemberCode(reqInfo.getPayerMemberCode());
				task.setStatus(0);
				task.setTaskBatchNo(reqInfo.getBusinessBatchNo());
				task.setTaskType(reqInfo.getRequestType());
				task.setUpdateDate(current);
				paymentTaskInfoService.create(task);
			}
			
			sendEmail(reqInfo, reqInfo.getCreator(), "上传批量付款到银行文件成功",
					reqEmailTemplateId);
		} catch (Throwable e) {
			LogUtil.error(this.getClass(), "批量付款到银行", OPSTATUS.EXCEPTION, "",
					"", e.getMessage(), "", "确认提交请求发生异常");
			throw new AppException(e);
		}
	}
	
	public void executeTask(BatchPaymentReqBaseInfoDTO reqInfo ){
		//如果是定期处理
		if(reqInfo.getProcessType()!=null&&reqInfo.getProcessType()==1){
		
			//更新任务状态
			PaymentTaskInfoDTO upTask = new PaymentTaskInfoDTO();
			upTask.setUpdateDate(new Date());
			upTask.setStatus(2);
			upTask.setMemberCode(reqInfo.getPayerMemberCode());
			upTask.setTaskType(reqInfo.getRequestType());
			upTask.setTaskBatchNo(reqInfo.getBusinessBatchNo());
			
			if(paymentTaskInfoService.update(upTask, 1)){
				BatchPaymentOrderDTO order = buildBatchPaymentOrder(reqInfo);
				batchPay2BankOrderService.createOrder(order);
			}
		}
		
		
	}
	
	public void closeTask(BatchPaymentReqBaseInfoDTO reqInfo ){
		//如果是定期处理
		if(reqInfo.getProcessType()!=null&&reqInfo.getProcessType()==1){
		
			//更新任务状态
			PaymentTaskInfoDTO upTask = new PaymentTaskInfoDTO();
			upTask.setUpdateDate(new Date());
			upTask.setStatus(3);
			upTask.setMemberCode(reqInfo.getPayerMemberCode());
			upTask.setTaskType(reqInfo.getRequestType());
			upTask.setTaskBatchNo(reqInfo.getBusinessBatchNo());
			
			if(!paymentTaskInfoService.update(upTask, 1)){
				throw new RuntimeException("更新任务状态发生异常");
			}
		}
		
		
	}
	
	@Override
	public void auditPassRequestRdTx(BatchPaymentReqBaseInfoDTO reqInfo,
			int oldStatus) throws AppException {
		try {
			Date current = new Date();
			reqInfo.setUpdateDate(current);
			reqInfo.setStatus(2);
			//如果是定期处理，则更新任务状态
			if(reqInfo.getProcessType()!=null&&reqInfo.getProcessType()==1){
				if(oldStatus==0){
					PaymentTaskInfoDTO task = new PaymentTaskInfoDTO();
					task.setCreateDate(current);
					task.setExcuteDate(reqInfo.getExcuteDate());
					task.setMemberCode(reqInfo.getPayerMemberCode());
					task.setStatus(1);
					task.setTaskBatchNo(reqInfo.getBusinessBatchNo());
					task.setTaskType(reqInfo.getRequestType());
					task.setUpdateDate(current);
					paymentTaskInfoService.create(task);
				}else if(oldStatus==1){
					PaymentTaskInfoDTO task = new PaymentTaskInfoDTO();
					task.setMemberCode(reqInfo.getPayerMemberCode());
					task.setStatus(1);
					task.setTaskBatchNo(reqInfo.getBusinessBatchNo());
					task.setTaskType(reqInfo.getRequestType());
					task.setUpdateDate(current);
					if(!paymentTaskInfoService.update(task, 0)){
						throw new RuntimeException("更新任务状态发生异常");
					}
				}
				
			}

			// 更新批量付款请求审批状态
			if (batchPaymentReqBaseInfoService.updateStatus(reqInfo, oldStatus)) {
				
				if(reqInfo.getProcessType()==null || reqInfo.getProcessType()==0){//如果不是是定期处理，则创建并支付订单

					BatchPaymentOrderDTO order = buildBatchPaymentOrder(reqInfo);
					batchPay2BankOrderService.createOrder(order);
					// 发送邮件通知
					sendEmail(reqInfo, reqInfo.getAuditor(), "复核批量付款到银行文件通过",
							passEmailTempalteId);
				}

			} else {
				LogUtil
						.error(getClass(), "批量付款到银行", OPSTATUS.FAIL,
								"auditPassRequestRdTx", "", "更新审核状态失败", "",
								"批量付款到银行异常");
				throw new RuntimeException("更新审核状态失败");
			}
		} catch (Throwable e) {
			LogUtil.error(getClass(), "批量付款到银行", OPSTATUS.EXCEPTION,
					"auditPassRequestRdTx", "", "审核通过处理或发生异常", "", "批量付款到银行异常");
			throw new AppException(e);
		}
		
	}

	@Override
	public void auditPassRequestRdTx(Long requestSeq, String auditor,
			String auditRemark) throws AppException {
		try {
			BatchPaymentReqBaseInfoDTO reqInfo = batchPaymentReqBaseInfoService
					.getBatchPaymentReqBaseInfo(requestSeq);
			Date current = new Date();
			reqInfo.setAuditor(auditor);
			reqInfo.setUpdateDate(current);
			reqInfo.setAuditRemark(auditRemark);
			reqInfo.setStatus(2);
			//如果是定期处理，则更新任务状态
			if(reqInfo.getProcessType()!=null&&reqInfo.getProcessType()==1){
				PaymentTaskInfoDTO task = new PaymentTaskInfoDTO();
				task.setMemberCode(reqInfo.getPayerMemberCode());
				task.setStatus(1);
				task.setTaskBatchNo(reqInfo.getBusinessBatchNo());
				task.setTaskType(reqInfo.getRequestType());
				task.setUpdateDate(current);
				if(!paymentTaskInfoService.update(task, 0)){
					throw new RuntimeException("更新任务状态发生异常");
				}
			}

			// 更新批量付款请求审批状态
			if (batchPaymentReqBaseInfoService.updateStatus(reqInfo, 1)) {
				
				if(reqInfo.getProcessType()==null || reqInfo.getProcessType()==0){//如果不是是定期处理，则创建并支付订单

					BatchPaymentOrderDTO order = buildBatchPaymentOrder(reqInfo);
					batchPay2BankOrderService.createOrder(order);
					// 发送邮件通知
					sendEmail(reqInfo, reqInfo.getAuditor(), "复核批量付款到银行文件通过",
							passEmailTempalteId);
				}

			} else {
				LogUtil
						.error(getClass(), "批量付款到银行", OPSTATUS.FAIL,
								"auditPassRequestRdTx", "", "更新审核状态失败", "",
								"批量付款到银行异常");
				throw new RuntimeException("更新审核状态失败");
			}
		} catch (Throwable e) {
			LogUtil.error(getClass(), "批量付款到银行", OPSTATUS.EXCEPTION,
					"auditPassRequestRdTx", "", "审核通过处理或发生异常", "", "批量付款到银行异常");
			throw new AppException(e);
		}

	}

	private BatchPaymentOrderDTO buildBatchPaymentOrder(
			BatchPaymentReqBaseInfoDTO reqInfo) {
		BatchPaymentOrderDTO order = new BatchPaymentOrderDTO();
		//---------------added  PengJiangbo 
		order.setPayerAcctType(reqInfo.getPayerAcctType());
		order.setPayeeAcctType(reqInfo.getPayeeAcctType());
		order.setPayerCurrencyCode(reqInfo.getPayerCurrencyCode());
		order.setPayeeCurrencyCode(reqInfo.getPayeeCurrencyCode());
		//---------------added  PengJiangbo end 
		
		order.setBusinessBatchNo(reqInfo.getBusinessBatchNo());
		order.setCreateDate(reqInfo.getUpdateDate());
		order.setCreator(reqInfo.getAuditor());
		order.setOrderType(OrderType.BATCHPAY2BANK.getValue());
		order.setOrderStatus(OrderStatus.INIT.getValue());
		if(reqInfo.getProcessType()!=null&&reqInfo.getProcessType()==1){
			order.setOrderSmallType(OrderSmallType.AUTO_BATCHPAY2BANK.getValue());
		}else{
			order.setOrderSmallType(OrderSmallType.COMMON_BATCHPAY2BANK.getValue());
		}
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
	public void auditRejectRequestRdTx(Long requestSeq, String auditor,
			String auditRemark) {
		BatchPaymentReqBaseInfoDTO reqInfo = batchPaymentReqBaseInfoService
				.getBatchPaymentReqBaseInfo(requestSeq);
		Date current = new Date();
		reqInfo.setAuditor(auditor);
		reqInfo.setAuditRemark(auditRemark);
		reqInfo.setUpdateDate(current);
		reqInfo.setStatus(3);
		//如果是定期处理，则更新任务状态
		if(reqInfo.getProcessType()!=null&&reqInfo.getProcessType()==1){
			PaymentTaskInfoDTO task = new PaymentTaskInfoDTO();
			task.setMemberCode(reqInfo.getPayerMemberCode());
			task.setStatus(3);
			task.setTaskBatchNo(reqInfo.getBusinessBatchNo());
			task.setTaskType(reqInfo.getRequestType());
			task.setUpdateDate(current);
			if(!paymentTaskInfoService.update(task, 0)){
				throw new RuntimeException("更新任务状态发生异常");
			}
		}
		
		// 更新批量付款请求审批状态为审核拒绝
		batchPaymentReqBaseInfoService.updateStatus(reqInfo, 1);

		sendEmail(reqInfo, auditor, "复核批量付款到银行文件拒绝", rejectEmailTempalteId);

	}

	/**
	 * 供请求成功、审批通过、审批拒绝时发送邮件
	 * 
	 * @param reqInfo
	 * @param operator
	 * @param subject
	 * @param templateId
	 */
	private void sendEmail(BatchPaymentReqBaseInfoDTO reqInfo, String operator,
			String subject, long templateId) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("payerName", reqInfo.getPayerName());
		data.put("operator", operator);
		data.put("businessNo", reqInfo.getBusinessBatchNo());
		data.put("totalNum", String.valueOf(reqInfo.getRequestCount()));
		data.put("totalAmount", AmountUtils.numberFormat(reqInfo
				.getRequestAmount()));
		data.put("validNum", String.valueOf(reqInfo.getValidCount()));
		data.put("validAmount", AmountUtils.numberFormat(reqInfo
				.getValidAmount()));
		data.put("errorNum", String.valueOf(reqInfo.getRequestCount()
				- reqInfo.getValidCount()));
		data.put("errorAmount", AmountUtils.numberFormat(reqInfo
				.getRequestAmount()
				- reqInfo.getValidAmount()));
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
	private void sendEmail(Map<String, String> data, String subject,
			long templateId) {
		
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
	public void setBatchPaymentReqBaseInfoService(
			BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService) {
		this.batchPaymentReqBaseInfoService = batchPaymentReqBaseInfoService;
	}

	/**
	 * @param batchPaymentToBankReqDetailService
	 *            the batchPaymentToBankReqDetailService to set
	 */
	public void setBatchPaymentToBankReqDetailService(
			BatchPaymentToBankReqDetailService batchPaymentToBankReqDetailService) {
		this.batchPaymentToBankReqDetailService = batchPaymentToBankReqDetailService;
	}

	public void setProvinceCityFacadeService(
			ProvinceCityFacadeService provinceCityFacadeService) {
		this.provinceCityFacadeService = provinceCityFacadeService;
	}

	public void setBankInfoFacadeService(
			BankInfoFacadeService bankInfoFacadeService) {
		this.bankInfoFacadeService = bankInfoFacadeService;
	}

	/**
	 * @param pay2BankValidateService
	 *            the pay2BankValidateService to set
	 */
	public void setPay2BankValidateService(
			Pay2BankValidateService pay2BankValidateService) {
		this.pay2BankValidateService = pay2BankValidateService;
	}

	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}

	public Map<String, Integer> getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(List<ProvinceDTO> provinecList) {
		Map<String, Integer> code = new HashMap<String, Integer>();
		if (provinecList == null || provinecList.size() == 0) {
			return;
		}
		for (ProvinceDTO pro : provinecList) {
			code.put(pro.getProvincename(), pro.getProvincecode());
		}
		this.provinceCode = code;
	}

	public Map<String, Integer> getCityCode() {
		return cityCode;
	}

	public void setCityCode(Map<String, Integer> cityCode) {
		this.cityCode = cityCode;
	}

	public Map<String, String> getBankCode() {
		return bankCode;
	}

	/**
	 * 通过银行名称初始化银行
	 * 
	 * @param list
	 */
	public void setBankCode(List<Bank> list) {
		Map<String, String> code = new HashMap<String, String>();
		if (list == null || list.size() == 0) {
			return;
		}
		for (Bank bank : list) {
			code.put(bank.getBankName(), bank.getBankId() + "");
		}
		this.bankCode = code;
	}

	private Map<String, Integer> proCityMap(Integer proCode,
			Map<String, Integer> map, List<CityDTO> cityList) {
		if (cityList == null || cityList.size() == 0) {
			return map;
		}
		for (CityDTO city : cityList) {
			map.put(proCode + city.getCityname(), city.getCitycode());
		}
		return map;
	}

	/**
	 * @param foRcLimitFacade
	 *            the foRcLimitFacade to set
	 */
	public void setFoRcLimitFacade(FoRcLimitFacade foRcLimitFacade) {
		this.foRcLimitFacade = foRcLimitFacade;
	}

	/**
	 * @param reqAccountingService
	 *            the reqAccountingService to set
	 */
	public void setReqAccountingService(AccountingService reqAccountingService) {
		this.reqAccountingService = reqAccountingService;
	}

	/**
	 * @param batchPay2BankOrderService
	 *            the batchPay2BankOrderService to set
	 */
	public void setBatchPay2BankOrderService(
			BatchPay2BankOrderService batchPay2BankOrderService) {
		this.batchPay2BankOrderService = batchPay2BankOrderService;
	}

	/**
	 * @param provinceCode
	 *            the provinceCode to set
	 */
	public void setProvinceCode(Map<String, Integer> provinceCode) {
		this.provinceCode = provinceCode;
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

	@Override
	public void processCompleteBatchPay2BankOrder() {
		List batchPaymentOrders = batchPaymentOrderService
				.getCompleteBatchPay2BankOrders();
		for (Iterator iterator = batchPaymentOrders.iterator(); iterator
				.hasNext();) {
			try {
				BatchPaymentOrder order = (BatchPaymentOrder) iterator.next();
				BatchPaymentTotalInfo info = batchPaymentOrderService
						.getTotalComplateBatchPay2BankOrder(order.getOrderId());
				BatchPaymentOrderDTO dto = new BatchPaymentOrderDTO();
				dto.setOrderId(order.getOrderId());
				dto.setUpdateDate(new Date());
				dto.setOrderStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
				dto.setSuccessAmount(info.getSuccessAmount());
				dto.setSuccessCount(info.getSuccessCount());
				dto.setSuccessFee(info.getSuccessFee());
				batchPaymentOrderService.updateOrder(dto);
				
				//通知API商户
				try {
					if (OrderSmallType.API_BATCHPAY2BANK.getValue().equals(
							order.getOrderSmallType())) {
						buildRequestForMerchant(info,order);
					}
				} catch (Throwable e) {
					logger.error("BatchPay2AcctOrderServiceImpl批量付款到账户通知商户出错!", e);
				}
				
			} catch (Exception e) {
				LogUtil.error(getClass(), e.toString(), OPSTATUS.EXCEPTION, "",
						e.getMessage(), "", "", "更新批量付款到银行完成状态发生异常");
			}

		}

	}
	
	/**
	 * 构建消息
	 * @param serialNo
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private void buildRequestForMerchant(BatchPaymentTotalInfo info,BatchPaymentOrder order)
			throws UnsupportedEncodingException {
		
		String merchantCode = String.valueOf(order.getPayerMemberCode());
		MerchantConfigureCriteria criteria = new MerchantConfigureCriteria();
		criteria.createCriteria().andMerchantCodeEqualTo(merchantCode);
		MerchantConfigure configure = (MerchantConfigure) merchantConfigureDao
				.findObjectByCriteria(criteria);

		Map paraMap = new HashMap();
		paraMap.put("MERCHANT_CODE", merchantCode);
		paraMap.put("BIZ_NO", order.getBusinessBatchNo());
		paraMap.put("TOTAL_AMOUNT", new BigDecimal(order.getOrderAmount())
				.divide(new BigDecimal("10")).toString());
		paraMap.put("TOTAL_COUNT", order.getPaymentCount());
		paraMap.put("SUCCESS_AMOUNT", new BigDecimal(info.getSuccessAmount())
				.divide(new BigDecimal("10")).toString());
		paraMap.put("SUCCESS_COUNT", info.getSuccessCount());
		paraMap.put("TOTAL_FEE", new BigDecimal(info.getSuccessFee()).divide(
				new BigDecimal("10")).toString());
		StringBuilder signValue = new StringBuilder();
		signValue.append(merchantCode);
		signValue.append(paraMap.get("BIZ_NO"));
		signValue.append(paraMap.get("TOTAL_AMOUNT"));
		signValue.append(paraMap.get("TOTAL_COUNT"));
		signValue.append(paraMap.get("SUCCESS_AMOUNT"));
		signValue.append(paraMap.get("SUCCESS_COUNT"));
		signValue.append(paraMap.get("TOTAL_FEE"));
		signValue.append(configure.getPublicKey());
		paraMap.put("SIGNVALUE", MD5Util.md5Hex(signValue.toString()));

		List<Map> itemList = new ArrayList<Map>();

		List<FundoutOrder> list = batchPaymentOrderService
				.getBankDetailOrderByParentId(order.getOrderId());
		for (FundoutOrder item : list) {
			Map itemMap = new HashMap();
			itemMap.put("pay_SEQ_NO", String.valueOf(item.getOrderId()));
			itemMap.put("STATUS", item.getOrderStatus() == 111 ? 1 : 0);
			itemMap.put("ERROR_CODE", item.getOrderStatus() == 111 ? "0000"
					: "0001");
			itemMap
					.put("ERROR_MSG", item.getOrderStatus() == 111 ? "成功"
							: "失败");
			itemMap.put("ORDER_ID", item.getForeignOrderId());
			itemMap.put("SUCCESS_TIME", new SimpleDateFormat("yyyyMMddHHmmss")
					.format(item.getUpdateDate()));
			itemList.add(itemMap);
		}

		paraMap.put("itemList", itemList);

		String data = FreeMarkParseUtil.parseTemplate(apiNotifyTemplate.trim(),
				paraMap);

		logger.info("notify merchant report:" + data);

		Map resultMap = new HashMap();
		String responseParater = new String(Base64Util.encode(data.getBytes()));
		resultMap.put("responseParameters", URLEncoder.encode(responseParater,
				"UTF-8"));

		HttpNotifyRequest httpNotifyRequest = new HttpNotifyRequest();
		httpNotifyRequest.setMerchantCode(String.valueOf(order.getPayerMemberCode()));
		httpNotifyRequest.setData(resultMap);
		httpNotifyRequest.setUrl(configure.getNotifyUrl());
		httpNotifyRequest.setUrlMethod("POST");
		httpNotifyRequest.setTemplateId(apiNotifyTemplateId);

		if (configure.getNotifyFlag() != null && configure.getNotifyFlag() == 1) {
			try {
				jmsSender.send(httpNotifyRequest);
			} catch (Exception e) {
				logger.error("notify merchant error:", e);
			}
		}
	}
	
	/**
	 * @param batchPaymentOrderService
	 *            the batchPaymentOrderService to set
	 */
	public void setBatchPaymentOrderService(
			BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}

	public void setBatch2bankValidateService(
			ValidateService batch2bankValidateService) {
		this.batch2bankValidateService = batch2bankValidateService;
	}

	/**
	 * @param paymentTaskInfoService the paymentTaskInfoService to set
	 */
	public void setPaymentTaskInfoService(
			PaymentTaskInfoService paymentTaskInfoService) {
		this.paymentTaskInfoService = paymentTaskInfoService;
	}
	
	/**
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	public void setApiNotifyTemplate(String apiNotifyTemplate) {
		this.apiNotifyTemplate = apiNotifyTemplate;
	}
	public void setMerchantConfigureDao(BaseDAO merchantConfigureDao) {
		this.merchantConfigureDao = merchantConfigureDao;
	}
	public void setApiNotifyTemplateId(Long apiNotifyTemplateId) {
		this.apiNotifyTemplateId = apiNotifyTemplateId;
	}
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

}
