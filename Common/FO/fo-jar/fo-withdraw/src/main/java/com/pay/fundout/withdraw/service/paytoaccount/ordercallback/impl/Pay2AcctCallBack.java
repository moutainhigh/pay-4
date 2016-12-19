package com.pay.fundout.withdraw.service.paytoaccount.ordercallback.impl;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.pay.fundout.service.OrderStatus;
import com.pay.fundout.withdraw.model.paytoaccount.Pay2AcctOrder;
import com.pay.fundout.withdraw.service.paytoaccount.Pay2AcctService;
import com.pay.jms.notification.request.EmailNotifyRequest;
import com.pay.jms.notification.request.SMSNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.withdraw.order.BackFundmentOrder;
import com.pay.poss.dto.withdraw.order.OrderFailProcAlertModel;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.external.service.ordercallback.AbstractOrderCallBackServiceImpl;

public class Pay2AcctCallBack extends AbstractOrderCallBackServiceImpl {
	private JmsSender jmsSender;
	private NumberFormat numberFormat;
	private Long emailTemplateId;
	private Long smsTemplateId;

	public Pay2AcctCallBack() {
		numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);// 最大2位小数
		numberFormat.setMinimumFractionDigits(2);// 最小2为小数
	}

	@Override
	protected boolean changeOrderStatus(HandlerParam param) {
		Pay2AcctOrder order = (Pay2AcctOrder) param.getBaseOrderDto();
		String requestFrom = order.getRequestFrom();
		// 批量的总订单状态由外系统处理
		if (Pay2AcctService.REQUEST_CODE_FOR_BATCH_FIRST.equals(requestFrom)) {
			order.setStatus(Constants.ORDER_STATUS_SUCC);
			return true;
		} else if  (WithdrawBusinessType.PAYTOACCT_BATCHORDER_FAIL_PERSON.getBusinessType().equals(requestFrom)) { 
			order.setStatus(order.getStatus());
		} else if  (WithdrawBusinessType.FUNDADJUSTMENT_ORDER_FAIL.getBusinessType().equals(requestFrom)) { 
			order.setStatus(OrderStatus.PROCESSED_FAILURE.getValue());
		} else if  (WithdrawBusinessType.FUNDADJUSTMENT_ORDER_SUCC.getBusinessType().equals(requestFrom)) { 
			order.setStatus(OrderStatus.PROCESSED_SUCCESS.getValue());
		}else if  (WithdrawBusinessType.FUNDADJUSTMENT_ORDER_REQ.getBusinessType().equals(requestFrom)) { 
			order.setStatus(OrderStatus.PROCESSING.getValue());
		}else {
			order.setStatus(Constants.ORDER_STATUS_SUCC);
		}
		// 更新订单状态
		try {
			order.setUpdateDate(new Date());
			daoService.update("pay2acct.updateOrderStatus", order);
		} catch (Exception e) {
			order.setStatus(Constants.ORDER_STATUS_FAIL);
			order.setErrorTips("更新订单状态失败 ");
			log.error("更新订单状态失败 [" + order + "]", e);
			return false;
		}
		return true;
	}

	@Override
	protected BackFundmentOrder buildBackOrder(HandlerParam param) {
		// 退款采用红冲方式
		Pay2AcctOrder order = (Pay2AcctOrder) param.getBaseOrderDto();
		String requestFrom = order.getRequestFrom();
		BackFundmentOrder backOrder = new BackFundmentOrder();

		backOrder.setUpdateTime(order.getUpdateDate());
		// 只对批量付款到第二步才进行退款
		if (WithdrawBusinessType.PAYTOACCT_BATCHORDER_FAIL_PERSON.getBusinessType().equals(requestFrom)) {
			backOrder.setSequenceSrc(order.getSequenceId());
			backOrder.setTimeSrc(order.getUpdateDate());
			backOrder.setAmountSrc(new BigDecimal(order.getAmount()));
			backOrder.setFeeSrc(new BigDecimal(0));
			backOrder.setFromCode(order.getRequestFrom());
			backOrder.setPayerMember(order.getPayeeMember());
			backOrder.setPayerAcctType(order.getPayeeAcctType());
			backOrder.setPayerAcctCode(order.getPayerCode());
			backOrder.setPayeeAcctCode(order.getPayerAcctCode());
			backOrder.setPayeeAcctType(order.getPayerAcctType());
			backOrder.setPayeeMember(order.getPayerMember());
			backOrder.setAppAmount(new BigDecimal(order.getAmount() * (-1)));
			backOrder.setAppType(order.getRequestFrom());
			backOrder.setAppFee(new BigDecimal(0));
		}
		return backOrder;
	}

	@Override
	protected OrderFailProcAlertModel buildAlertInfo(HandlerParam param) {
		Pay2AcctOrder order = (Pay2AcctOrder) param.getBaseOrderDto();
		OrderFailProcAlertModel result = new OrderFailProcAlertModel();
		result.setOrderSeq(order.getSequenceId());
		result.setOrderStatus(order.getStatus().intValue());
		result.setFailReason(order.getErrorTips());
		result.setAppFrom(order.getRequestFrom());
		result.setUpdateTime(new Date());
		return result;
	}

	@Override
	protected void doCancelAccounting(BackFundmentOrder backFundOrder) throws PossException {
		// 只对批量付款到第二步才进行退款
		backFundingInnerService.processCancelOrderRnTx(backFundOrder);
	}

	@Override
	public void notify(HandlerParam param) {
		Pay2AcctOrder order = (Pay2AcctOrder) param.getBaseOrderDto();
		String requestFrom = order.getRequestFrom();
		try {
			// 只为批量和FO的单笔发送用户通知
			if (Pay2AcctService.REQUEST_CODE_FOR_BATCH_SECOND.equals(requestFrom) || Pay2AcctService.REQUEST_CODE_FOR_FO.equals(requestFrom)) {

				Map<String, String> data = new HashMap<String, String>();
				data.put("payeeName", order.getPayeeName());
				data.put("payerName", order.getPayerName());
				data.put("payeeMember", order.getPayeeMember().toString());
				data.put("amount", numberFormat.format(order.getAmount() / 1000.00));
				data.put("fee", numberFormat.format(order.getPayeeFee() / 1000.00));
				data.put("remarks", order.getRemarks());
				data.put("sequenceId", order.getSequenceId().toString());
				data.put("appDate", getLocalDate(order.getUpdateDate()));
				/**
				 * 增加会员类型1个人2企业
				 */
				data.put("memberType", String.valueOf(order.getPayeeMemType()));
				

				if (1 == order.getPayeeMemType()) {
					data.put("url", "http://www.pay.com/website/app/queryHistoryTradeList.do?method=queryHistoryTrade");
				} else {
					data.put("url", "http://www.pay.com/website/app/queryHistoryTradeList.do?method=queryHistoryTrade");
				}

				if (StringUtils.isNotEmpty(order.getPayeeMail())) {
					String mail = order.getPayeeMail();
					StringBuffer sb = new StringBuffer();
					int pa = mail.indexOf("@");
					int pb = mail.indexOf(".", pa);
					sb.append(mail.substring(0, (pa < 3 ? pa : 3))).append("*").append(mail.substring(pa, pb));
					data.put("payeeLoginName", sb.toString());

					EmailNotifyRequest request = new EmailNotifyRequest();

					request.setFromAddress(Constants.SYSTEM_MAIL);

					List<String> mails = new ArrayList<String>(1);
					mails.add(mail);
					request.setRecAddress(mails);
					request.setTemplateId(emailTemplateId);// 模板ID
					request.setSubject("您的付款到账户已经处理成功！");
					request.setData(data);
					jmsSender.send(request);
				}
				if (StringUtils.isNotEmpty(order.getPayeePhone())) {
					String phone = order.getPayeePhone();
					StringBuffer sb = new StringBuffer();
					sb.append(phone.substring(0, 2)).append("*").append(phone.substring(phone.length() - 3, phone.length()));
					data.put("payeeLoginName", sb.toString());

					SMSNotifyRequest request = new SMSNotifyRequest();

					List<String> mobiles = new ArrayList<String>(1);
					mobiles.add(phone);
					request.setMobiles(mobiles);
					request.setTemplateId(smsTemplateId);
					request.setData(data);
					jmsSender.send(request);
				}
			}
		} catch (Exception e) {
			log.error("消息发送失败 [" + order + "]", e);
		}

	}

	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public void setEmailTemplateId(Long emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	public void setSmsTemplateId(Long smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}
	private static String getLocalDate(Date date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
		return dateFormat.format(date);
	}
}
