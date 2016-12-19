/**
 * 
 */
package com.pay.fo.quarze.batchpay2bank;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pay.acc.service.member.MemberProductService;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.dto.task.PaymentTaskInfoDTO;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankOrderValidateService;
import com.pay.fo.order.service.batchpay2bank.BatchPay2BankRequestService;
import com.pay.fo.order.service.batchpayment.BatchPaymentReqBaseInfoService;
import com.pay.fo.order.service.task.PaymentTaskInfoService;
import com.pay.fo.order.service.validate.PaymentValidateService;
import com.pay.jms.notification.request.RequestType;
import com.pay.poss.base.common.Constants;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class BatchPay2BankTaskExecutor extends QuartzJobBean{
	
	/**
	 * 批量付款请求基本信息服务类
	 */
	private BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService;
	
	/**
	 * 支付任务服务类
	 */
	private PaymentTaskInfoService paymentTaskInfoService;
	
	/**
	 * 批复到银行请求服务类
	 */
	private BatchPay2BankRequestService batchPay2BankRequestService; 
	
	/**
	 * 支付验证服务类
	 */
	private PaymentValidateService paymentValidateService;
	
	/**
	 * 批复到银行订单验证服务类
	 */
	private BatchPay2BankOrderValidateService batchPay2BankOrderValidateService;
	
	/**
	 * 会员产品服务
	 */
	private MemberProductService memberProductService;
	
	/**
	 * 通知服务
	 */
	private NotifyFacadeService notifyFacadeService;
	
	/**
	 * 出错邮件通知模板
	 */
	private Long errorEmailTemplateId;
	
	/**
	 * 关闭任务邮件通知模板
	 */
	private Long closeEmailTemplateId;
	
	private static final String DATE_FMT="yyyy年MM月dd日HH点";
	
	private static final String closeEmailTemplateTital = "自动批量付款到银行处理失败";
	
	private static final String errorEmailTemplateTital = "自动批量付款到银行余额不足";
	
	
	private Log log = LogFactory.getLog(getClass());

	public void execute() {
		//获取需要处理的任务列表
		log.info("START TO CALL Process BatchPay2BankTask");
		
		Date current = new Date();
		
		List<PaymentTaskInfoDTO> taskList = paymentTaskInfoService.getList(1,new Date());
		for (PaymentTaskInfoDTO paymentTaskInfoDTO : taskList) {
			boolean isClose = false;
			try{
				
				BatchPaymentReqBaseInfoDTO reqInfo = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(paymentTaskInfoDTO.getMemberCode(),paymentTaskInfoDTO.getTaskType(),paymentTaskInfoDTO.getTaskBatchNo());
				if(reqInfo == null){
					log.error("未获取到批量付款到银行信息[memberCode|batchTaskNo:"+paymentTaskInfoDTO.getMemberCode()+"|"+paymentTaskInfoDTO.getTaskBatchNo()+"]");
					continue;
				}
				
				//验证批复申请信息
				 String message = batchPay2BankOrderValidateService.validateRCLimitInfo(paymentTaskInfoDTO.getMemberCode(), reqInfo.getValidAmount(), reqInfo.getValidCount());
				 
				  
				  if(StringUtil.isNull(message)){
				    	message = paymentValidateService.validatePayerBanlance(reqInfo.getValidAmount(),reqInfo.getIsPayerPayFee(), reqInfo.getFee(), reqInfo.getPayerMemberCode(), reqInfo.getPayerAcctType());
				    	if(!StringUtil.isNull(message)){
				    		message = "余额不足";
				    	}
				  }else{
					  
					  reqInfo.setErrorMsg(message);
					  //风控规则验证失败时，关闭任务
					  batchPay2BankRequestService.closeTask(reqInfo);
					  isClose = true;
				  }
				  
				//生成订单并扣款
				  if(StringUtil.isNull(message)){
					  batchPay2BankRequestService.executeTask(reqInfo);
				  }else{
					  reqInfo.setErrorMsg(message);
					  log.error("Process BatchPay2BankTask Error["+message+"]");
					  if(DateUtil.getHour(current)==0){
						  batchPay2BankRequestService.closeTask(reqInfo);
						  isClose = true;
					  }
					  //发邮件
					  if(isClose){
						  notifyPayerEmail(reqInfo.getPayerLoginName(), reqInfo.getPayerName(), message, paymentTaskInfoDTO.getExcuteDate(), closeEmailTemplateId, closeEmailTemplateTital);
					  }else{
						  notifyPayerEmail(reqInfo.getPayerLoginName(), reqInfo.getPayerName(), message, paymentTaskInfoDTO.getExcuteDate(), errorEmailTemplateId, errorEmailTemplateTital);
					  }
				  }
				
			}catch(Throwable e){
				log.error("处理自动批复到银行任务失败的任务[sequenceId："+paymentTaskInfoDTO.getSequenceId()+"]", e);
			}
		}
		log.info("END TO CALL Process BatchPay2BankTask");
		
		
	}
	
	
	/**
	 * 给付款方发送邮件
	 * @param dto
	 */
	private void notifyPayerEmail(String payerLoginName,String payerName,String message,Date date,long tempId,String subject){
		Map<String, String> data = new HashMap<String, String>();
		
		//付款方名称
		data.put("payerName", payerName);
		//日期
		data.put("date", DateUtil.dateToStr(date, DATE_FMT));
		
		//异常信息
		data.put("message", message);
		
		NotifyTargetRequest request = new NotifyTargetRequest();
		request.setData(data);
		request.setNotifyType(RequestType.EMAIL.value());
		request.setFromAddress(Constants.SYSTEM_MAIL);
		request.getRecAddress().add(payerLoginName);
		request.setTemplateId(tempId);
		request.setRequestTime(new Date());
		request.setSubject(subject);
		notifyFacadeService.notifyRequest(request);
		
	}
	

	public void setPaymentTaskInfoService(
			PaymentTaskInfoService paymentTaskInfoService) {
		this.paymentTaskInfoService = paymentTaskInfoService;
	}

	
	

	public void setMemberProductService(MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}

	@Override
	protected void executeInternal(JobExecutionContext context)
			throws JobExecutionException {
	}

	public void setBatchPaymentReqBaseInfoService(
			BatchPaymentReqBaseInfoService batchPaymentReqBaseInfoService) {
		this.batchPaymentReqBaseInfoService = batchPaymentReqBaseInfoService;
	}

	public void setBatchPay2BankRequestService(
			BatchPay2BankRequestService batchPay2BankRequestService) {
		this.batchPay2BankRequestService = batchPay2BankRequestService;
	}

	public void setPaymentValidateService(
			PaymentValidateService paymentValidateService) {
		this.paymentValidateService = paymentValidateService;
	}

	public void setBatchPay2BankOrderValidateService(
			BatchPay2BankOrderValidateService batchPay2BankOrderValidateService) {
		this.batchPay2BankOrderValidateService = batchPay2BankOrderValidateService;
	}

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	public void setErrorEmailTemplateId(Long errorEmailTemplateId) {
		this.errorEmailTemplateId = errorEmailTemplateId;
	}

	public void setCloseEmailTemplateId(Long closeEmailTemplateId) {
		this.closeEmailTemplateId = closeEmailTemplateId;
	}
	

}
