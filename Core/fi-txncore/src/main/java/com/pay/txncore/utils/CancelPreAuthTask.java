/**
 * 
 */
package com.pay.txncore.utils;

import java.util.Map;

import org.apache.log4j.trace.LogTrace;
import org.apache.log4j.trace.ThreadLocalLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.service.preauth.PreAuth2Service;

/**
 * @author yanshichuan
 *
 */
public class CancelPreAuthTask extends Thread {
	private static Logger log = LoggerFactory.getLogger(CancelPreAuthTask.class);
	private PreAuth2Service preAuth2Service;
	private PaymentInfo paymentInfo;
	private LogTrace logTrace;
	/**
     * 撤销预授权失败重试次数
     */
	private int retryTimes;

	public CancelPreAuthTask(PreAuth2Service preAuth2Service, PaymentInfo paymentInfo, int retryTimes,
			LogTrace logTrace) {
		this.preAuth2Service = preAuth2Service;
		this.paymentInfo = paymentInfo;
		this.logTrace = logTrace;
		//传递traceId和seqNo，保持日志连续性
		if (logTrace != null) {
			ThreadLocalLog.setTrace(logTrace.getTraceId(), logTrace.getSeqNo());
		}
		this.retryTimes = retryTimes;
	}

	public void run() {
		PaymentResult result = null;
		int times = 0;
		while (times < retryTimes) {
			log.info("第" + (++times) + "次撤销预授权:orderId="+paymentInfo.getOrderId()+",memberCode="+paymentInfo.getPartnerId());
			result = preAuth2Service.preAuthVoid(paymentInfo);
			log.info("RegisterUserId="+paymentInfo.getRegisterUserId()+",OrigOrderId="+paymentInfo.getOrigOrderId()+",PartnerId="+paymentInfo.getPartnerId()+",撤销预授权返回代码="+result.getResponseCode());
			if (result!=null&&ExceptionCodeEnum.PRE_AUTH_VOID_SUCCESS.getCode().equals(result.getResponseCode())) {
				log.info("成功撤销预授权:orderId="+paymentInfo.getOrderId()+",memberCode="+paymentInfo.getPartnerId());
				break;
			}
			log.info("第" + (times) + "次撤销预授权失败:ResponseCode="+result.getResponseCode());
		}
	}

	public PreAuth2Service getPreAuth2Service() {
		return preAuth2Service;
	}

	public void setPreAuth2Service(PreAuth2Service preAuth2Service) {
		this.preAuth2Service = preAuth2Service;
	}

	public PaymentInfo getPaymentInfo() {
		return paymentInfo;
	}

	public void setPaymentInfo(PaymentInfo paymentInfo) {
		this.paymentInfo = paymentInfo;
	}

	public LogTrace getLogTrace() {
		return logTrace;
	}

	public void setLogTrace(LogTrace logTrace) {
		this.logTrace = logTrace;
	}

}
