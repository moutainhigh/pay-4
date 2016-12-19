package com.pay.fo.quartz.autorisk;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditQueryDTO;
import com.pay.fundout.withdraw.service.autocheck.AutoCheckService;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawOrderAuditService;
import com.pay.jms.notification.request.RequestType;
import com.pay.poss.base.common.Constants;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.DateUtil;

/**		
 *  @author meng.li
 *  @Date 2013-12-2
 *  @Description 风控自动审核任务执行器
 */
public class AutoRiskTaskExecutor extends QuartzJobBean {

	protected transient Log log = LogFactory.getLog(getClass());
	
	private WithdrawOrderAuditService withdrawOrderAuditService;
	
	private AutoCheckService autoCheckService;
	
	private NotifyFacadeService notifyFacadeService;
	
	private List<String> recAddressList;
	
	private List<String> ccAddressList;
	
	private String tempId;
	
	public void setWithdrawOrderAuditService(WithdrawOrderAuditService withdrawOrderAuditService) {
		this.withdrawOrderAuditService = withdrawOrderAuditService;
	}
	
	public void setAutoCheckService(AutoCheckService autoCheckService) {
		this.autoCheckService = autoCheckService;
	}
	
	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}
	
	public void setRecAddressList(List<String> recAddressList) {
		this.recAddressList = recAddressList;
	}
	
	public void setCcAddressList(List<String> ccAddressList) {
		this.ccAddressList = ccAddressList;
	}
	
	public void setTempId(String tempId) {
		this.tempId = tempId;
	}
	
	/**
	 * 自动审核
	 */
	public void autorisk() {
		if (log.isInfoEnabled()) {
			log.info("-----风控自动审核开始-----");
		}
		WithdrawAuditQueryDTO auditQueryDTO = new WithdrawAuditQueryDTO();
		auditQueryDTO.setMemberAccType("10"); // 人民币账户
		auditQueryDTO.setPrioritys("5"); // 默认为5
		auditQueryDTO.setStartDate(DateUtil.skipDateTime(new Date(), -30));
		List<WithdrawAuditDTO> orders = withdrawOrderAuditService.search(auditQueryDTO);
		int totalNums = 0;
		if (orders != null && orders.size() > 0) {
			if (log.isInfoEnabled()) {
				log.info("-----需要自动审核的风控列表长度为：" + orders.size() + "-----");
			}
			for (WithdrawAuditDTO dto : orders) {
				try {
					int result = this.autoCheckService.autocheckRnTx(dto);
					if (1 == result) {
						totalNums += 1;
					}
				} catch (Exception e) {
					log.error("-----工单号为：" + dto.getWorkOrderky() + "的订单自动审核出错", e);
				}
			}
		} else {
			if (log.isInfoEnabled()) {
				log.info("-----没有需要自动审核的订单-----");
			}
		}
		// 发邮件通知自动风控审核同意笔数
		if (totalNums > 0) {
			this.notifyPayerEmail(totalNums, Long.valueOf(tempId), "风控自动审核成功");
		}
		if (log.isInfoEnabled()) {
			log.info("-----风控自动审核结束-----");
		}
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {

	}
	
	/**
	 * 给风控发邮件通知
	 * @param totalNums
	 * @param tempId
	 * @param subject
	 */
	private void notifyPayerEmail(int totalNums, long tempId, String subject){
		Map<String, String> data = new HashMap<String, String>();
		// 自动风控审核同意情况笔数
		data.put("totalNums", String.valueOf(totalNums));
		
		NotifyTargetRequest request = new NotifyTargetRequest();
		request.setData(data);
		request.setNotifyType(RequestType.EMAIL.value());
		request.setFromAddress(Constants.SYSTEM_MAIL);
		request.getRecAddress().addAll(recAddressList);
//		List<String> recList = new ArrayList<String>();
//		recList.add("287455404@qq.com");
//		recList.add("358786509@qq.com");
//		recList.add("175230663@qq.com");
//		recList.add("501374997@qq.com");
//		request.getRecAddress().addAll(recList);
		request.getRecAddress().addAll(ccAddressList);
//		request.getCcAddress().add("501374997@qq.com");
		request.setTemplateId(tempId);
		request.setRequestTime(new Date());
		request.setSubject(subject);
		notifyFacadeService.notifyRequest(request);
	}
	
}
