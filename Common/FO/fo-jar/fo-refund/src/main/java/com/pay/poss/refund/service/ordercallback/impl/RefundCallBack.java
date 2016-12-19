/** @Description 
 * @project 	poss-refund
 * @file 		RefundCallBack.java 
 * Copyright © 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-26		Rick_lv			Create 
 */
package com.pay.poss.refund.service.ordercallback.impl;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.jms.notification.request.RequestType;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.common.order.WithdrawOrderStatus;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.service.RefundManageService;
import com.pay.poss.service.ma.RD4MaServiceApi;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.DateUtil;
import com.pay.util.NumberUtil;

/**
 * <p>
 * 充退回调服务
 * </p>
 * 
 * @author Rick_lv
 * @since 2010-10-26
 * @see
 */
public class RefundCallBack extends AbstractOrderCallBackServiceImpl {
	private NumberFormat numberFormat;
	// 充退管理服务
	private RefundManageService refundManageService;
	private Map<Integer, String> appTypes = new HashMap<Integer, String>();
	private NotifyFacadeService notifyFacadeService;// 消息发送服务
	private transient RD4MaServiceApi foRD4MaServiceApi;// ma接口对象
	//科目映射
	private Map<String,String> subjectMap;
	private Integer successEmailTemplateId;
	private Integer failEmailTemplateId;
	private Integer successSmsTemplateId;
	private Integer failSmsTemplateId;
	
	//set注入
	public void setSubjectMap(Map<String, String> subjectMap) {
		this.subjectMap = subjectMap;
	}
	
	public void setFoRD4MaServiceApi(RD4MaServiceApi foRD4MaServiceApi) {
		this.foRD4MaServiceApi = foRD4MaServiceApi;
	}

	public void setSuccessEmailTemplateId(Integer successEmailTemplateId) {
		this.successEmailTemplateId = successEmailTemplateId;
	}

	public void setFailEmailTemplateId(Integer failEmailTemplateId) {
		this.failEmailTemplateId = failEmailTemplateId;
	}

	public void setSuccessSmsTemplateId(Integer successSmsTemplateId) {
		this.successSmsTemplateId = successSmsTemplateId;
	}

	public void setFailSmsTemplateId(Integer failSmsTemplateId) {
		this.failSmsTemplateId = failSmsTemplateId;
	}

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	public RefundCallBack() {
		numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);// 最大2位小数
		numberFormat.setMinimumFractionDigits(2);// 最小2为小数
		appTypes.put(101, WithdrawBusinessType.ACCTCHARGE_REFUND_REQ_PERSON.getBusinessType());
		appTypes.put(111, WithdrawBusinessType.ACCTCHARGE_REFUND_ORDER_SUCC_PERSON.getBusinessType());
		appTypes.put(112, WithdrawBusinessType.ACCTCHARGE_REFUND_ORDER_FAIL_PERSON.getBusinessType());
	}

	@Override
	protected OrderFailProcAlertModel buildAlertInfo(HandlerParam param) {
		RefundOrderM order = (RefundOrderM) param.getBaseOrderDto();
		OrderFailProcAlertModel result = new OrderFailProcAlertModel();
		result.setOrderSeq(order.getDetailKy());
		result.setOrderStatus(order.getStatus().intValue());
		result.setAppFrom("refund");
		result.setUpdateTime(new Date());
		return result;
	}

	@Override
	protected BackFundmentOrder buildBackOrder(HandlerParam param) {
		RefundOrderM order = (RefundOrderM) param.getBaseOrderDto();
		BackFundmentOrder backOrder = new BackFundmentOrder();

		// backOrder.setSequenceSrc(order.getDetailKy());
		// backOrder.setTimeSrc(order.getApplyTime());
		// backOrder.setAmountSrc(order.getApplyAmount());
		// backOrder.setFeeSrc(new BigDecimal(0));
		// backOrder.setFromCode(order.getApplyFrom());
		//
		// backOrder.setPayerMember(new Long(order.getMemberCode()));
		// backOrder.setPayerAcctType(order.getMemberAccType());
		// backOrder.setPayerAcctCode(order.getMemberAcc());
		//
		// backOrder.setPayeeAcctCode("2182010310001");
		// backOrder.setUpdateTime(order.getApplyTime());
		// backOrder.setAppAmount(order.getApplyAmount().multiply(new
		// BigDecimal(-1)));
		//		
		// backOrder.setAppType(appTypes.get(order.getStatus()));

		backOrder.setSequenceSrc(order.getDetailKy());
		backOrder.setTimeSrc(new Date());
		backOrder.setAmountSrc(order.getApplyAmount());
		backOrder.setFeeSrc(new BigDecimal(0));
		backOrder.setFromCode("refund");

		backOrder.setPayerMember(new Long(order.getMemberCode()));
		backOrder.setPayerAcctType(order.getMemberAccType());
		backOrder.setPayerAcctCode(order.getMemberAcc());

		backOrder.setPayeeAcctCode(new StringBuffer("2182").append(subjectMap.get(order.getBankCode())).toString());
		backOrder.setUpdateTime(new Date());
		backOrder.setAppAmount(order.getApplyAmount().multiply(new BigDecimal(-1)));

		backOrder.setAppType(appTypes.get(order.getStatus()));
		backOrder.setAppFee(new BigDecimal(0));
		return backOrder;
	}

	@Override
	protected boolean changeOrderStatus(HandlerParam param) {
		RefundOrderM order = (RefundOrderM) param.getBaseOrderDto();
		return refundManageService.updateRefundOrder(order);
	}

	@Override
	protected void doCancelAccounting(BackFundmentOrder backFundOrder) throws PossException {
		backFundingInnerService.processCancelOrderRnTx(backFundOrder);
	}

	@Override
	public void notify(HandlerParam param) {
		RefundOrderM order = (RefundOrderM) param.getBaseOrderDto();
		// 调用ma接口拿app会员信息
		MemberInfoDto maResultDto = new MemberInfoDto();
		String loginName;
		try {
			maResultDto = foRD4MaServiceApi.doQueryMemberInfoNsTx(null, new Long(order.getMemberCode()), null, null);
			loginName = maResultDto.getLoginName();
		} catch (Exception e) {
			log.error("调用查询会员信息接口异常:" + e.getMessage());
			loginName = "sunsea_li@staff.hnacom";
		}
		if (WithdrawOrderStatus.SUCCESS.getValue() == order.getStatus().intValue()) {
			if (loginName.indexOf('@') != -1) {// 充退处理成功发送邮件
				this.sendEmail(order, successEmailTemplateId, loginName, "您的申请充退已经处理完毕！");
			} else {// 充退处理成功发送短信
				this.sendMobileMessage(order, successSmsTemplateId, loginName);
			}
		} else if (WithdrawOrderStatus.FAIL.getValue() == order.getStatus().intValue()) {
			if (loginName.indexOf('@') != -1) {// 充退处理失败发送邮件
				this.sendEmail(order, failEmailTemplateId, loginName, "您的申请充退处理失败！");
			} else {// 充退处理失败发送短信
				this.sendMobileMessage(order, failSmsTemplateId, loginName);
			}
		}
	}

	/**
	 * @param order
	 * @param templateId
	 * @param emailAdress
	 * @param title
	 */
	private void sendEmail(RefundOrderM order, int templateId, String emailAdress, String title) {
		NotifyTargetRequest request = new NotifyTargetRequest();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("payerName", order.getMemberName());
		map.put("appDate", DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", order.getApplyTime()));
		map.put("amount", NumberUtil.formatDoubleToString(order.getApplyAmount().divide(new BigDecimal(1000)).doubleValue(), "##0.00"));
		/**
		 * 增加会员类型1个人2企业
		 */
		map.put("memberType", order.getMemberType());
		
		request.setData(map);// 请求的数据
		request.setMerchantCode(String.valueOf(order.getPayee()));// 商户号
		request.setRequestTime(new Date());// 请求创建时间
		request.setRetryTimes(0);// 重试次数

		request.setNotifyType(RequestType.EMAIL.value());// 发送类型
		request.setTemplateId(templateId);// 模板id,在inf.notify_template表中配置
		List<String> recAddress = new ArrayList<String>();
		recAddress.add(emailAdress);
		// recAddress.add("sunsea_li@staff.hnacom");
		request.setRecAddress(recAddress);// 收件人列表
		request.setSubject(title);// 邮件标题
		request.setFromAddress(Constants.SYSTEM_MAIL);// 发件人
		notifyFacadeService.notifyRequest(request);
	}

	/**
	 * @param order
	 * @param templateId
	 * @param mobileNo
	 */
	private void sendMobileMessage(RefundOrderM order, int templateId, String mobileNo) {
		NotifyTargetRequest request = new NotifyTargetRequest();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("payerName", order.getMemberName());
		map.put("appDate", DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", order.getApplyTime()));
		map.put("amount", NumberUtil.formatDoubleToString(order.getApplyAmount().divide(new BigDecimal(1000)).doubleValue(), "##0.00"));
		request.setData(map);// 请求的数据
		request.setMerchantCode(String.valueOf(order.getPayee()));// 商户号
		request.setRequestTime(new Date());// 请求创建时间
		request.setRetryTimes(0);// 重试次数

		request.setNotifyType(RequestType.SMS.value());
		request.setTemplateId(templateId);// 模板id,在inf.notify_template表中配置
		List<String> mobileList = new ArrayList<String>();
		mobileList.add(mobileNo);
		// mobileList.add("13761164419");
		request.setMobiles(mobileList);
		notifyFacadeService.notifyRequest(request);
	}

	// set注入
	public void setRefundManageService(final RefundManageService param) {
		this.refundManageService = param;
	}
}
