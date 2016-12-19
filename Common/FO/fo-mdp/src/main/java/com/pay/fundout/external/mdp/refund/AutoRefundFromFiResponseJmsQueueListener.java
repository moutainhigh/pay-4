package com.pay.fundout.external.mdp.refund;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQObjectMessage;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.mdp.refund.RefundAccountingResponseJmsQueueListener;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.refund.model.RefundOrderD;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.service.RefundManageService;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * @author LIBO
 * @Date 2016-10-11
 * @Description 此类用来处理来自fi的Task退款请求
 */
public class AutoRefundFromFiResponseJmsQueueListener implements
		MessageListener {

	private final Log logger = LogFactory.getLog(getClass());
	// 充退管理服务
	private RefundManageService refundManageService;
	
	// set注入
	public void setRefundManageService(final RefundManageService param) {
		this.refundManageService = param;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		try {
			if (message instanceof ActiveMQObjectMessage) {
				ActiveMQObjectMessage msg = (ActiveMQObjectMessage) message;
				String jsonString = (String) msg.getObject();
				// 此处添加业务处理方法-------------
				@SuppressWarnings("unchecked")
				Map<String, String> requestMap = JSonUtil.toObject(jsonString,
						new HashMap<String, String>().getClass());
				LogUtil.info(RefundAccountingResponseJmsQueueListener.class,
						"自动退款Task消息处理", OPSTATUS.START,
						"onMessage(Message message)",
						"充值订单号:" + requestMap.get("depositProtocolId") + "");
				this.processRequest(requestMap);
				LogUtil.info(RefundAccountingResponseJmsQueueListener.class,
						"自动退款Task消息处理", OPSTATUS.SUCCESS,
						"onMessage(Message message)",
						"充值订单号:" + requestMap.get("depositProtocolId") + "");
			}
		} catch (Exception e) {
			logger.error("自动退款Task消息处理!:", e);
			throw new RuntimeException(e);// 此处抛出异常是为了补单用
		}
	}

	/**
	 * @param mDto
	 */
	private void processRequest(Map<String, String> requestMap)
			throws PossException {
		RefundOrderM mDto = new RefundOrderM();
		RefundOrderD dDto = new RefundOrderD();
		//parseRequestMap(requestMap, mDto, dDto);
		//refundManageService.handerApplyResponseFromGW(mDto);
		//refundManageService.handerApplyResponseFromFiRdTx(mDto, dDto);
		refundManageService.handeAutoRefundTaskBatch(null);
	}

	/**
	 * 通过request组装RefundOrderM 批量申请的时候用到
	 */
	private boolean parseRequestMap(Map<String, String> request,
			RefundOrderM mDto, RefundOrderD dDto) {

		Integer memberAccType = Integer.valueOf(String.valueOf(request
				.get("memberAccType")));
		mDto.setMemberCode(String.valueOf(request.get("memberCode")));
		mDto.setMemberName(String.valueOf(request.get("userName")));//
		mDto.setMemberAcc(String.valueOf(request.get("acctCode")));//
		mDto.setMemberType(String.valueOf(request.get("memberType")));//
		mDto.setMemberAccType(memberAccType);
		String payee = request.get("payee");
		if(!StringUtil.isEmpty(payee)){
			mDto.setPayee(payee); // 退款商户CODE
		}
		
		mDto.setPayeeName(StringUtils.trimToEmpty(request.get("payeeName")));// 退款商户NAME

		String levelCode = String.valueOf(request.get("levelCode"));
		if (!StringUtil.isEmpty(levelCode)) {
			mDto.setMemberLevel(new Integer(String.valueOf(request
					.get("levelCode"))));
		}
		mDto.setOperatorIp("1");
		mDto.setApplyFrom("FI");

		BigDecimal applyTotalAmount = new BigDecimal(0);
		// 下面来组装明细信息
		String rechargeOrderSeq = String.valueOf(request.get("depositOrderNo"));
		String rechargeBankSeq = String.valueOf(request.get("bankSerialNo"));
		String rechargeAmount = String.valueOf(request.get("depositAmount"));
		String rechargeTime = String.valueOf(request.get("depositDate"));
		String rechargeBank = String.valueOf(request.get("bankChannel"));
		String applyAmount = String.valueOf(request.get("applyAmount"));//
		String applyReason = String.valueOf(request.get("applyReason"));//
		String applyMax = String.valueOf(request.get("applyMax"));//
		String rechargeBankOrder = String.valueOf(request.get("bankOrderId"));
		String depositBackNo = String.valueOf(request.get("depositBackNo"));
		String depositTypeName = String.valueOf(request.get("depositTypeName"));
		String refundOrderNo = request.get("refundOrderNo");
		
		if(!StringUtil.isEmpty(refundOrderNo)){
			dDto.setRefundOrderNo(refundOrderNo);
		}

		List<RefundOrderD> listDetails = new ArrayList<RefundOrderD>();
		if (!StringUtil.isEmpty(rechargeOrderSeq)) {
			dDto.setRechargeOrderSeq(new Long(rechargeOrderSeq));
		}
		if (!StringUtil.isEmpty(rechargeBankSeq)) {
			dDto.setRechargeBankSeq(rechargeBankSeq);
		}
		if (!StringUtil.isEmpty(rechargeAmount)) {
			dDto.setRechargeAmount(new BigDecimal(rechargeAmount));
		}
		if (!StringUtil.isEmpty(applyAmount)) {
			dDto.setApplyAmount(new BigDecimal(applyAmount));
		}
		if (!StringUtil.isEmpty(applyAmount)) {
			// applyTotalAmount+=(new
			// BigDecimal(applyAmount)).doubleValue()*1000;
			applyTotalAmount = applyTotalAmount
					.add(new BigDecimal(applyAmount));
		}
		if (!StringUtil.isEmpty(rechargeTime)) {
			// dDto.setRechargeTime(DateUtil.parse("yyyy-MM-dd HH:mm:ss",
			// rechargeTime));
			dDto.setRechargeTime(DateUtil.parse("yyyyMMddHHmmss", rechargeTime));
		}
		if (!StringUtil.isEmpty(rechargeBank)) {
			dDto.setRechargeBank(rechargeBank);
		}
		if (!StringUtil.isEmpty(applyReason)) {
			dDto.setApplyRemark(applyReason);
		}
		if (!StringUtil.isEmpty(applyMax)) {
			dDto.setApplyMax(new BigDecimal(applyMax));
		}
		if (!StringUtil.isEmpty(rechargeBankOrder)) {
			dDto.setRechargeBankOrder(rechargeBankOrder);
		}
		if (!StringUtil.isEmpty(depositBackNo)) {
			dDto.setDepositBackNo(depositBackNo);
		}
		if (!StringUtil.isEmpty(depositTypeName)) {
			dDto.setDepositTypeName(depositTypeName);
		}
		dDto.setRechargeChannel("fi");
		dDto.setShowPosition(new Integer(1));
		dDto.setErrorTip("初始");

		mDto.setApplyAmount(applyTotalAmount);// 申请总金额
		mDto.setListDetails(listDetails);

		return true;
	}

}
