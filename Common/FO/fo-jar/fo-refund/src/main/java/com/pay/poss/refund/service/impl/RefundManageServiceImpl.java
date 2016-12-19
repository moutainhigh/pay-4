/** @Description 
 * @project 	poss-refund
 * @file 		RefundManageServiceImpl.java 
 * Copyright © 2006-2020 IpayLinks.com Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-8-25		sunsea.li		Create 
 * 2016-05-04		sch				modify  handerRefundReAuditBatch(....)
 */
package com.pay.poss.refund.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.dto.MemberInfoDto;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.fundout.refund.client.RefundProcessService;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.sender.JmsSender;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.poss.base.common.Constants;
import com.pay.poss.base.common.order.WithdrawOrderStatus;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.common.accounting.WithdrawBusinessType;
import com.pay.poss.dto.fi.DepositQueryParamDTO;
import com.pay.poss.dto.withdraw.notify.NotifyTargetRequest;
import com.pay.poss.dto.withdraw.orderhandlermanage.HandlerParam;
import com.pay.poss.refund.common.constant.RefundConstants;
import com.pay.poss.refund.common.util.MyServiceUtil;
import com.pay.poss.refund.model.MemberInfoDTO;
import com.pay.poss.refund.model.RechargeRecordDto;
import com.pay.poss.refund.model.RefundBanlanceCheck;
import com.pay.poss.refund.model.RefundDetailInfoDTO;
import com.pay.poss.refund.model.RefundOrderD;
import com.pay.poss.refund.model.RefundOrderM;
import com.pay.poss.refund.model.RefundWorkOrderAndM;
import com.pay.poss.refund.model.RefundWorkorder;
import com.pay.poss.refund.model.WebQueryRefundDTO;
import com.pay.poss.refund.model.WordorderOperationLogDto;
import com.pay.poss.refund.service.RefundManageService;
import com.pay.poss.refund.service.RefundOrderService;
import com.pay.poss.refund.service.RefundValidateService;
import com.pay.poss.refund.service.WorkorderOperationService;
import com.pay.poss.service.gateway.RD4GateWayServiceApi;
import com.pay.poss.service.ma.RD4MaServiceApi;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;

/**
 * <p>
 * 充退管理服务具体实现类
 * </p>
 * 
 * @author sunsea.li
 * @since 2010-8-25
 * @see
 */
public class RefundManageServiceImpl extends BaseServiceImpl implements
		RefundManageService {

	private final transient Log log = LogFactory.getLog(getClass());
	private transient BaseDAO baseDao; // 处理数据库操作的基础DAO

	@Override
	protected Notify2QueueRequest buildNotify2QueueRequest(String jsonStr, String queueName) {
		return super.buildNotify2QueueRequest(jsonStr, queueName);
	}

	private transient RD4GateWayServiceApi foRD4GatewayService; // 网关接口对象
	private transient RD4MaServiceApi foRD4MaServiceApi; // ma接口对象
	private AccountingService accountingServiceApply; // 充退请求提交成功记账服务
	private String queueName;
	private NotifyFacadeService notifyFacadeService; // 消息发送服务
	private WorkorderOperationService workorderOperationService;
	private RefundValidateService refundValidateService;
	private Integer emailTemplateId;
	private Integer smsTemplateId;
	private RefundOrderService refundOrderService;
	// 消息发送服务
	private JmsSender jmsSender;
	private RefundProcessService refundProcessService;
	// set注入
	public void setJmsSender(final JmsSender param) {
		this.jmsSender = param;
	}

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	public void setChannelClientService(
			RefundProcessService refundProcessService) {
		this.refundProcessService = refundProcessService;
	}

	public void setWorkorderOperationService(
			WorkorderOperationService workorderOperationService) {
		this.workorderOperationService = workorderOperationService;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public void setRefundOrderService(RefundOrderService refundOrderService) {
		this.refundOrderService = refundOrderService;
	}

	public void setAccountingServiceApply(
			AccountingService accountingServiceApply) {
		this.accountingServiceApply = accountingServiceApply;
	}

	public void setBaseDao(BaseDAO baseDao) {
		this.baseDao = baseDao;
	}

	public void setFoRD4GatewayService(RD4GateWayServiceApi foRD4GatewayService) {
		this.foRD4GatewayService = foRD4GatewayService;
	}

	public void setFoRD4MaServiceApi(RD4MaServiceApi foRD4MaServiceApi) {
		this.foRD4MaServiceApi = foRD4MaServiceApi;
	}

	public void setRefundValidateService(
			RefundValidateService refundValidateService) {
		this.refundValidateService = refundValidateService;
	}

	public void setEmailTemplateId(Integer emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}

	public void setSmsTemplateId(Integer smsTemplateId) {
		this.smsTemplateId = smsTemplateId;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hnapay.poss.refund.service.RefundManageService#queryRechargeInfo
	 * (com.hnapay.poss.base.dao.model.Page,
	 * com.hnapay.poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public Map<String, Object> queryRechargeInfo(Page<RechargeRecordDto> page,
			WebQueryRefundDTO dto) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("webQueryRefundDTO", dto);// 原查询参数返回
		String errCode = null;
		// verify data
		errCode = refundValidateService.validateRefundData(dto);
		if (null != errCode) {
			model.put("err1", errCode);
			return model;
		}

		// setting dada
		errCode = refundValidateService.validateMember(dto, page, model);
		if (null != errCode) {
			model.put("err1", errCode);
			return model;
		}

		// query data
		// 4.调用网关接口查询充值信息
		// 4a.封装查询条件
		MemberInfoDTO memberInfo = (MemberInfoDTO) model.get("memberInfo");
		DepositQueryParamDTO gwQueryDto = new DepositQueryParamDTO();
		MyServiceUtil.wrapQueryDto(page, dto, gwQueryDto, memberInfo);

		// 4b.调充值明细查询接口
		List resultList = new ArrayList();
		try {
			// resultList = foRD4GatewayService.queryDeposits(gwQueryDto);
		} catch (PossUntxException e) {
			log.error("充值明细查询错误 [" + dto + "]", e);
			model.put("err2", "充值明细查询错误:" + e.getMessage());
			return model;
		}
		// 4c.调充值记录总数查询接口
		Integer totalCount = 0;
		try {
			// totalCount = foRD4GatewayService.queryDepositsCount(gwQueryDto);
		} catch (Exception e) {
			log.error("充值记录总数查询错误 [" + dto + "]", e);
			model.put("err2", "充值记录总数查询错误:" + e.getMessage());
			return model;
		}
		page.setTotalCount(totalCount);
		// 4d. 解析网关返回的结果信息,计算可充退金额
		Long balance = (Long) model.get("balance");
		// this.parseGwData(page, resultList, balance);

		// 5.将结果返回
		model.put("page", page);
		return model;
	}

	private void parseGwData(Page<RechargeRecordDto> page, List resultList,
			Long balance) {
		List<RechargeRecordDto> pageList = new ArrayList<RechargeRecordDto>();
		// for (DepositOrderQueryDetailDTO resultDetailDto : resultList) {
		// RechargeRecordDto dto = new RechargeRecordDto();
		// dto.setRechargeAmount(new BigDecimal(resultDetailDto
		// .getDepositAmount()));// 充值金额
		// dto.setRechargeBank(resultDetailDto.getBankChannel());// 银行渠道
		// dto.setRechargeBankSeq(StringUtil.null2String(resultDetailDto
		// .getBankSerialNo()));// 充值银行流水
		// dto.setRechargeOrderSeq(resultDetailDto.getDepositOrderNo());// 充值流水号
		// dto.setRechargeTime(resultDetailDto.getDepositDate());// 充值时间
		// dto.setRechargeStatus(resultDetailDto.getDepositStatus());// 充值状态
		// dto.setRechargeBankOrder(resultDetailDto.getBankOrderId());//
		// dto.setRechargeBankName(resultDetailDto.getBankChannelName());
		// dto.setRechargeChannel(resultDetailDto.getBankChannelName());
		// dto.setDepositTypeName(resultDetailDto.getDepositTypeName());
		//
		// if (null != resultDetailDto.getTradeOrderNo()) {
		// dto.setTradeOrderNo(resultDetailDto.getTradeOrderNo());
		// }
		// if (null != resultDetailDto.getPaymentDate()) {
		// dto.setPaymentDate(resultDetailDto.getPaymentDate());
		// }
		// if (null != resultDetailDto.getPaymentStatus()) {
		// dto.setPaymentStatus(resultDetailDto.getPaymentStatus());
		// }
		// if (null != resultDetailDto.getPaymentAmount()) {
		// dto.setPaymentAmount(resultDetailDto.getPaymentAmount());
		// }
		// if (null != resultDetailDto.getMerchantOrderId()) {
		// dto.setMerchantOrderId(resultDetailDto.getMerchantOrderId());
		// }
		//
		// // 下面来计算可充退金额
		// BigDecimal accountBalance = new BigDecimal(balance);// 账户余额
		// BigDecimal rechargeAmount = new BigDecimal(
		// resultDetailDto.getDepositAmount());// 充值金额
		// BigDecimal allowAmount = new BigDecimal(0);// 可充退金额
		// // 1.根据充值流水号查refund_banlance_check表，查已充退金额
		// RefundBanlanceCheck queryDto = new RefundBanlanceCheck();
		// queryDto.setRechargeSeq(new Long(resultDetailDto
		// .getDepositOrderNo()));
		// RefundBanlanceCheck balanceDto = (RefundBanlanceCheck) baseDao
		// .findObjectByCriteria(
		// RefundConstants.QUERY_REFUNDBANLANCECHECK, queryDto);
		// if (null != balanceDto && null != balanceDto.getRefundAmount()) {
		// allowAmount = (rechargeAmount.longValue()
		// - balanceDto.getRefundAmount().longValue() < accountBalance
		// .longValue()) ? new BigDecimal(
		// rechargeAmount.longValue()
		// - balanceDto.getRefundAmount().longValue())
		// : accountBalance;
		// } else {
		// allowAmount = (rechargeAmount.longValue() < accountBalance
		// .longValue()) ? rechargeAmount : accountBalance;
		// }
		// if (allowAmount.longValue() < rechargeAmount.longValue()) {
		// dto.setFlag(false);
		// } else {
		// dto.setFlag(true);
		// }
		// dto.setApplyMax(allowAmount);
		// dto.setRefundType(resultDetailDto.getRefundType());
		// pageList.add(dto);
		// }
		page.setResult(pageList);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.poss.refund.service.RefundManageService#handerRefundApply(
	 * com.hnapay.poss.refund.model.RefundOrderM)
	 */
	@Override
	public Map<String, Object> handerRefundApplyRdTx(RefundOrderM mDto,
			WebQueryRefundDTO dto) throws PossException {

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("webQueryRefundDTO", dto);// 原查询参数返回
		String validateBankCodeResult = refundValidateService
				.validateBankCode(mDto);
		if (validateBankCodeResult != null) {
			result.put("err", validateBankCodeResult);
			return result;
		}
		try {// 此处捕捉异常用来做事务控制,数据库回滚动作
				// 1.组织主表信息，插入本地数据库
				// mDto.setMemberLevel(10);
			mDto.setApplyTime(new Date());
			mDto.setStatus(new Integer(WithdrawOrderStatus.APPLY.getValue()));// 订单申请状态
			long acceptKy = (Long) baseDao.create(
					RefundConstants.CREATE_REFUNDORDERM, mDto);

			mDto.setOrderKy(new Long(acceptKy));
			// 2.组织从表信息，批量插入数据库
			for (RefundOrderD dDto : mDto.getListDetails()) {
				dDto.setMasterKy(new Long(acceptKy));
				dDto.setStatus(new Integer(WithdrawOrderStatus.APPLY.getValue()));// 订单初始化状态
				// 2.a保存子订单
				long detailKy = (Long) baseDao.create(
						RefundConstants.CREATE_REFUNDORDERD, dDto);
				dDto.setDetailKy(detailKy);

			}

		} catch (Exception e) {// 事务处理结束
			result.put("err", "系统异常：" + e.getMessage());
			throw new PossException(e.getMessage(), null);
		}

		// 总余额判断
		if (!refundValidateService.validateAmount(mDto)) {
			result.put("err", "余额不足，充退失败");
			return result;
		}

		boolean flag = false;

		// 扣款
		for (RefundOrderD dDto : mDto.getListDetails()) {
			// 校验可充退金额
			if (!refundValidateService.validateApplyMax(mDto, dDto)) {
				log.error("可充退金额不足，请检查:" + dDto.getDetailKy());
				continue;
			}
			// 调用inf记账，调用acc更新账户余额
			RefundOrderM order = this.wrapAccountingDto(mDto, dDto);
			// update by terry_ma
			AccountingDto accountingDto = new AccountingDto();
			accountingDto.setOrderId(dDto.getDetailKy());
			accountingDto.setOrderAmount(order.getApplyAmount());
			accountingDto.setAmount(order.getApplyAmount());
			accountingDto.setOrderAmount(order.getApplyAmount());
			// accountingDto.setBusinessType(PayForEnum.INCOME_BACK.getCode());
			accountingDto.setBankCode(dDto.getRechargeBank());
			accountingDto.setPayer(order.getMemberCode());
			int accountingResult = 0;
			try {
				accountingResult = this.accountingServiceApply
						.doAccountingReturn(accountingDto);
			} catch (Exception e) {
				log.error("记账失败:" + order.getDetailKy() + ":" + e.getMessage());
				continue;
			}

			if (accountingResult == 1) {
				// 2.c更新可充退金额
				RefundBanlanceCheck balanceDto = new RefundBanlanceCheck();
				balanceDto.setRechargeSeq(dDto.getRechargeOrderSeq());
				balanceDto.setRechargeAmount(dDto.getRechargeAmount());
				balanceDto.setRefundAmount(dDto.getApplyAmount());
				baseDao.update(
						RefundConstants.UPDATE_OR_CEATE_REFUNDBANLANCECHECK,
						balanceDto);
				// 修改子订单状态为101
				order.setStatus(WithdrawOrderStatus.INIT.getValue());
				baseDao.update(RefundConstants.UPDATE_DSTATUSAFTERACCOUNTING,
						order);
				flag = true;
			} else {
				log.error("记账失败:" + order.getDetailKy());
			}
		}

		if (flag) {
			// 更新总订单状态为101
			RefundOrderM m = new RefundOrderM();
			m.setOrderKy(mDto.getOrderKy());
			m.setStatus(WithdrawOrderStatus.INIT.getValue());
			baseDao.update(RefundConstants.UPDATE_REFUND_ORDER_M_STATUS, m);

			RefundWorkorder workOrder = new RefundWorkorder();
			workOrder.setRefundMKy(mDto.getOrderKy());
			workOrder.setStatus(new Integer(RefundConstants.REFUND_STATUS_0));// 初始状态
			Long workorderKy = (Long) baseDao.create(
					RefundConstants.CREATE_REFUNDWORKORDER, workOrder);
			saveOperationLog(dto.getOperator(), new Integer(
					RefundConstants.REFUND_STATUS_0), workorderKy,
					dto.getApplyReason());

			// 处理成功，将相关数据返回
			result.put("err", "充退申请成功，对应充退流水号为：" + mDto.getOrderKy());

			// 7.发送通知
			// 调用ma接口拿app会员信息
			MemberInfoDto maResultDto = new MemberInfoDto();
			String loginName;
			try {
				maResultDto = foRD4MaServiceApi.doQueryMemberInfoNsTx(null,
						new Long(mDto.getMemberCode()), null, null);
				loginName = maResultDto.getLoginName();
			} catch (Exception e) {
				log.error("调用查询会员信息接口异常:" + e.getMessage());
				loginName = "liwei_851227@163.com";
			}
			if (loginName.indexOf('@') != -1) {// 充退申请成功发送邮件
				this.sendEmail(mDto, emailTemplateId, loginName,
						"您已经申请充退，等待向银行账户打款！");
			} else {// 充退申请成功发送短信
				this.sendMobileMessage(mDto, smsTemplateId, loginName);
			}
		} else {
			result.put("err", "充退申请失败，未完成记账，对应充退流水号为：" + mDto.getOrderKy());
		}

		return result;
	}

	/**
	 * 封装记账Dto
	 * 
	 * @param m
	 * @param d
	 * @return
	 */
	private RefundOrderM wrapAccountingDto(RefundOrderM m, RefundOrderD d) {
		RefundOrderM refundOrderM = new RefundOrderM();
		refundOrderM.setMemberAcc(m.getMemberAcc());
		refundOrderM.setMemberAccType(m.getMemberAccType());
		refundOrderM.setMemberCode(m.getMemberCode());
		refundOrderM.setStatus(m.getStatus());
		refundOrderM.setDetailKy(d.getDetailKy());
		refundOrderM.setBankCode(d.getRechargeBank());
		refundOrderM.setApplyAmount(d.getApplyAmount());
		return refundOrderM;
	}

	/**
	 * @param order
	 * @param templateId
	 * @param emailAdress
	 * @param title
	 */
	private void sendEmail(RefundOrderM order, int templateId,
			String emailAdress, String title) {
		NotifyTargetRequest request = new NotifyTargetRequest();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("payerName", order.getMemberName());
		map.put("appDate",
				DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss",
						order.getApplyTime()));
		map.put("amount",
				NumberUtil.formatDoubleToString(
						order.getApplyAmount().divide(new BigDecimal(1000))
								.doubleValue(), "##0.00"));
		map.put("memberType", order.getMemberType());
		request.setData(map);// 请求的数据
		request.setMerchantCode(Long.valueOf("123456"));// 商户号
		request.setRequestTime(new Date());// 请求创建时间
		request.setRetryTimes(0);// 重试次数

		request.setNotifyType(RequestType.EMAIL.value());// 发送类型
		request.setTemplateId(templateId);// 模板id,在inf.notify_template表中配置
		List<String> recAddress = new ArrayList<String>();
		recAddress.add(emailAdress);
		// recAddress.add("liwei_851227@163.com");
		request.setRecAddress(recAddress);// 收件人列表
		request.setSubject(title);// 邮件标题
		request.setFromAddress(Constants.SYSTEM_MAIL);// 发件人
		request.setType(RequestType.EMAIL);
		notifyFacadeService.notifyRequest(request);
	}

	/**
	 * @param order
	 * @param templateId
	 * @param mobileNo
	 */
	private void sendMobileMessage(RefundOrderM order, int templateId,
			String mobileNo) {
		NotifyTargetRequest request = new NotifyTargetRequest();
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("payerName", order.getMemberName());
		map.put("appDate",
				DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss",
						order.getApplyTime()));
		map.put("amount",
				NumberUtil.formatDoubleToString(
						order.getApplyAmount().divide(new BigDecimal(1000))
								.doubleValue(), "##0.00"));
		request.setData(map);// 请求的数据
		request.setMerchantCode(Long.valueOf("123456"));// 商户号
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

	@Override
	public void handerApplyResponseFromFiRdTx(RefundOrderM mDto,
			RefundOrderD dDto) throws PossException {
		String errorMsg = "";
		String result = "1";// 1为成功，0为失败
		String type = "1";// 1为商户退款，2为后台充退
		Map<String, String> toFi = new HashMap<String, String>();
		toFi.put("depositOrderNo", String.valueOf(dDto.getRechargeOrderSeq()));
		toFi.put("depositBackNo", String.valueOf(dDto.getDepositBackNo()));
		toFi.put("amount", String.valueOf(dDto.getApplyAmount()));
		toFi.put(
				"dateTime",
				DateUtil.formatDateTime("yyyyMMddHHmmss",
						dDto.getRechargeTime()));
		toFi.put("type", type);

		String validateBankCodeResult = refundValidateService
				.validateBankCode(mDto);
		if (validateBankCodeResult != null) {
			errorMsg = "1001";
			result = "0";
			sendMsgToFi(toFi, result, errorMsg);
			return;
		}
		String validateAndSetMemberInfo = refundValidateService
				.setMemberInfo(mDto);
		if (validateAndSetMemberInfo != null) {
			errorMsg = "1002";
			result = "0";
			sendMsgToFi(toFi, result, errorMsg);
			return;
		}
		try {// 此处捕捉异常用来做事务控制,数据库回滚动作
				// 1.组织主表信息，插入本地数据库
				// mDto.setMemberLevel(10);
			mDto.setApplyTime(new Date());
			mDto.setStatus(new Integer(WithdrawOrderStatus.INIT.getValue()));// 订单初始化状态
			// long acceptKy = (Long) baseDao.create(
			// RefundConstants.CREATE_REFUNDORDERM, mDto);
			long acceptKy = refundOrderService.saveRefundMRnTx(mDto);

			mDto.setOrderKy(new Long(acceptKy));
			// 2.组织从表信息，批量插入数据库
			dDto.setMasterKy(new Long(acceptKy));
			dDto.setStatus(new Integer(WithdrawOrderStatus.INIT.getValue()));// 订单初始化状态
			// 2.a保存子订单
			// long detailKy = (Long) baseDao.create(
			// RefundConstants.CREATE_REFUNDORDERD, dDto);
			long detailKy = refundOrderService.saveRefundDRnTx(dDto);
			dDto.setDetailKy(detailKy);
			// 2.b调用inf记账，调用acc更新账户余额
			RefundOrderM order = this.wrapAccountingDto(mDto, dDto);
			HandlerParam param = new HandlerParam();
			param.setBaseOrderDto(order);
			param.setOrderStatus(new Integer(WithdrawOrderStatus.INIT
					.getValue()));// 订单初始化状态
			param.setWithdrawBusinessType(WithdrawBusinessType.ACCTCHARGE_REFUND_REQ_PERSON
					.getBusinessType());//
			// update by terry_ma
			AccountingDto accountingDto = new AccountingDto();
			accountingDto.setOrderId(detailKy);
			accountingDto.setOrderAmount(order.getApplyAmount());
			accountingDto.setAmount(order.getApplyAmount());
			accountingDto.setOrderAmount(order.getApplyAmount());
			// accountingDto.setBusinessType(PayForEnum.INCOME_BACK.getCode());
			accountingDto.setBankCode(dDto.getRechargeBank());
			accountingDto.setPayer(order.getMemberCode());
			// int accountingResult =
			// this.accountingServiceApply.doAccountingReturn(accountingDto);

			// if (accountingResult == 1) {
			// 2.c更新可充退金额
			RefundBanlanceCheck balanceDto = new RefundBanlanceCheck();
			balanceDto.setRechargeSeq(dDto.getRechargeOrderSeq());
			balanceDto.setRechargeAmount(dDto.getRechargeAmount());
			balanceDto.setRefundAmount(dDto.getApplyAmount());
			baseDao.update(RefundConstants.UPDATE_OR_CEATE_REFUNDBANLANCECHECK,
					balanceDto);
			// } else {
			// throw new PossException("记账失败", null);
			// }

			RefundWorkorder workOrder = new RefundWorkorder();
			workOrder.setRefundMKy(new Long(acceptKy));
			workOrder.setStatus(new Integer(RefundConstants.REFUND_STATUS_0));// 初始状态
			// Long workorderKy = (Long) baseDao.create(
			// RefundConstants.CREATE_REFUNDWORKORDER, workOrder);
			Long workorderKy = refundOrderService
					.saveRefundWorkorderRnTx(workOrder);
			saveOperationLog("fi",
					new Integer(RefundConstants.REFUND_STATUS_0), workorderKy,
					"商户退款");
			// 处理成功，将相关数据返回
			errorMsg = "充退申请成功，对应充退流水号为：" + acceptKy;

		} catch (Exception e) {// 事务处理结束
			log.error("save error:", e);
			errorMsg = "1003";
			result = "0";
			sendMsgToFi(toFi, result, errorMsg);
			throw new PossException(e.getMessage(), null);
		}
		// 7.发送通知
		// 调用ma接口拿app会员信息

		MemberInfoDto maResultDto = new MemberInfoDto();
		String loginName;
		try {
			maResultDto = foRD4MaServiceApi.doQueryMemberInfoNsTx(null,
					new Long(mDto.getMemberCode()), null, null);
			loginName = maResultDto.getLoginName();
		} catch (Exception e) {
			log.error("调用查询会员信息接口异常:", e);
			loginName = "liwei_851227@163.com";
		}
		if (loginName.indexOf('@') != -1) {// 充退申请成功发送邮件
			this.sendEmail(mDto, emailTemplateId, loginName,
					"您已经申请充退，等待向银行账户打款！");
		} else {// 充退申请成功发送短信
			this.sendMobileMessage(mDto, smsTemplateId, loginName);
		}

		return;
	}

	private void sendMsgToFi(Map<String, String> info, String result,
			String errorMsg) {
		info.put("result", result);
		info.put("errorCode", errorMsg);
		String jsonStr = JSonUtil.toJSonString(info);
		log.info("【向FI发送结果消息：】" + "消息名：fi.depositback.notice，消息内容：" + jsonStr);
		jmsSender.send("fi.depositback.notice", jsonStr);
	}

	@Override
	public Map<String, Object> queryRefundInfo(
			Page<RefundWorkOrderAndM> resultPage,
			WebQueryRefundDTO webQueryRefundDTO) {
		// 查询充退统计信息
		Map<String, Object> model = (Map<String, Object>) baseDao
				.findObjectByCriteria(RefundConstants.STAT_REFUND_INFO,
						webQueryRefundDTO);

		// 查询充退信息
		resultPage = baseDao.findByQuery(
				RefundConstants.QUERY_ASSING_TASK_INFO_1, resultPage,
				webQueryRefundDTO);
		if (null == model) {
			model = new HashMap<String, Object>();
		}
		model.put("page", resultPage);
		model.put("webQueryRefundDTO", webQueryRefundDTO);
		return model;
	}

	@Override
	public Map<String, Object> queryRefundInfoDetail(
			WebQueryRefundDTO webQueryRefundDTO) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 查询会员信息

		// 查询充值详细信息
		List<RefundOrderM> resultList = baseDao.findByQuery(
				RefundConstants.QUERY_REFUNDORDERDETAIL_BY_ACCEPTKY,
				webQueryRefundDTO);
		// 查询审核记录信息

		RefundOrderM mDto = new RefundOrderM();
		if (null != resultList && resultList.size() > 0) {
			mDto = resultList.get(0);
		}

		// 充值历史操作日志记录，操作人员查询
		List<WordorderOperationLogDto> operattionLogs = workorderOperationService
				.queryByWorkorderKy(webQueryRefundDTO.getWorkorderKy());
		model.put("mDto", mDto);
		model.put("webQueryRefundDTO", webQueryRefundDTO);
		model.put("operattionLogs", operattionLogs);
		return model;
	}

	@Override
	public Map<String, Object> handerRefundAuditSingle(
			Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		String refundStatus = (String) params.get("refundStatus");
		String refundRemark = StringUtil
				.null2String(params.get("refundRemark"));
		// String tempStatus = (String)params.get("tempStatus");
		// String handleType = (String)params.get("handleType");
		result.put("requestUrl", params.get("requestUrl"));

		Map<String, Object> workOrder = new HashMap<String, Object>();
		workOrder.put("workOrderKy",
				Long.valueOf((String) params.get("workOrderKy")));

		if (RefundConstants.REFUND_STATUS_2.equals(refundStatus)) {
			refundRemark = StringUtils.isNotEmpty(refundRemark) ? refundRemark
					: "风控审核拒绝";
		} else if (RefundConstants.REFUND_STATUS_1.equals(refundStatus)) {
			refundRemark = StringUtils.isNotEmpty(refundRemark) ? refundRemark
					: "风控审核同意";
		} else if (RefundConstants.REFUND_STATUS_3.equals(refundStatus)) {
			refundRemark = StringUtils.isNotEmpty(refundRemark) ? refundRemark
					: "风控审核滞留";
		}

		workOrder.put("status", Integer.valueOf(refundStatus));
		boolean upFlag = baseDao.update(
				RefundConstants.UPDATE_REFUND_STATUS_INFO, workOrder);
		if (!upFlag) {
			result.put("resultMsg", "未更新到数据，审批操作失败！");
			return result;
		}
		saveOperationLog(String.valueOf(params.get("userId")),
				Integer.valueOf(refundStatus),
				Long.valueOf((String) params.get("workOrderKy")), refundRemark);
		result.put("resultMsg", "审批操作成功！");
		return result;
	}

	@Override
	public Map<String, Object> handerRefundAuditBatch(
			Page<RefundWorkOrderAndM> resultPage,
			WebQueryRefundDTO webQueryRefundDTO,List<Map> channelOrders) {
		// 组装工作流所需参数
		String[] refundInfos = webQueryRefundDTO.getHandleDatas().split(
				RefundConstants.STR_SPLIT_SEPARATOR_W);
		String handleStatus = webQueryRefundDTO.getHandleStatus();
		Map<String,String> updateMap = new HashMap<String,String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userId = webQueryRefundDTO.getUserId();
		int len = refundInfos.length;
		String[] refundDetailInfo = null;
		List<WordorderOperationLogDto> workorderOperationLogs = new ArrayList<WordorderOperationLogDto>();

		String refundRemark = webQueryRefundDTO.getAuditRefundRemark();
		if (RefundConstants.REFUND_STATUS_2.equals(handleStatus)) {
			refundRemark = StringUtils.isNotEmpty(refundRemark) ? refundRemark
					: "风控审核拒绝";
		} else if (RefundConstants.REFUND_STATUS_1.equals(handleStatus)) {
			refundRemark = StringUtils.isNotEmpty(refundRemark) ? refundRemark
					: "风控审核同意";
		} else if (RefundConstants.REFUND_STATUS_3.equals(handleStatus)) {
			refundRemark = StringUtils.isNotEmpty(refundRemark) ? refundRemark
					: "风控审核滞留";
		}
		
		
		for (int i = 0; i < len; i++) {
			WordorderOperationLogDto workorderOperationLog = new WordorderOperationLogDto();
			refundDetailInfo = refundInfos[i]
					.split(RefundConstants.STR_SPLIT_SEPARATOR);
			updateMap.put(refundDetailInfo[0],handleStatus);

			workorderOperationLog.setOperationTime(new Date());
			workorderOperationLog.setOperator(userId);
			workorderOperationLog.setState(Integer.parseInt(handleStatus));
			workorderOperationLog.setWorkorderKy(Long
					.valueOf(refundDetailInfo[0]));
			workorderOperationLog.setRemark(refundRemark);
			 String[] refundInfo=refundInfos[i].split(",");
			 String  tradeOrderNo=refundInfo[refundInfo.length-1];
			 for (Map map : channelOrders) {
					if(tradeOrderNo.equals(map.get("tradeOrderNo")+"")){
						String cpType = (Integer)map.get("cpType")+"";
						if(cpType.equals("3")){
							workorderOperationLog.setRemark("订单已拒付");
							workorderOperationLog.setState(2);
							updateMap.put(refundDetailInfo[0],"2");
						}
					}
				}
			workorderOperationLogs.add(workorderOperationLog);
		}
		
		if(updateMap!=null){
			Set<Entry<String,String>> entrySet = updateMap.entrySet();
			for (Entry<String, String> entry : entrySet) {
				Map<String, Object>  params=new HashMap<String, Object>();
				  params.put("workOrderKy", entry.getKey());
				  params.put("status", entry.getValue());
				  params.put("oldStatus", webQueryRefundDTO.getOldStatus());
				  baseDao.update(RefundConstants.UPDATE_REFUND_STATUS_INFO, params);
			}
			resultMap = this.queryRefundInfo(resultPage, webQueryRefundDTO);
			batchInsertOperationLog(workorderOperationLogs);
		}
			return resultMap;
	}

	/*
	 * 批量处理  复核结果 
	 * 
	 */
	@Override
	public Map<String, Object> handeRefundReAuditBatch(
			Page<RefundWorkOrderAndM> resultPage,
			WebQueryRefundDTO webQueryRefundDTO,List<Map> channelOrders) {
		// 组装工作流所需参数
		String[] refundInfos = webQueryRefundDTO.getHandleDatas().split(
				RefundConstants.STR_SPLIT_SEPARATOR_W);
		String handleStatus = webQueryRefundDTO.getHandleStatus();
		// String handleType = webQueryRefundDTO.getHandleType();
		Map<String,String> updateMap = new HashMap<String, String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userId = webQueryRefundDTO.getUserId();

		int len = refundInfos.length;
		String[] refundDetailInfo = null;

		String refundRemark = webQueryRefundDTO.getAuditRefundRemark();
		if (RefundConstants.REFUND_STATUS_2.equals(handleStatus)) {
			refundRemark = StringUtils.isNotEmpty(refundRemark) ? refundRemark
					: "风控复核拒绝";
		} else if (RefundConstants.REFUND_STATUS_1.equals(handleStatus)) {
			refundRemark = StringUtils.isNotEmpty(refundRemark) ? refundRemark
					: "风控复核同意";
		} else if (RefundConstants.REFUND_STATUS_3.equals(handleStatus)) {
			refundRemark = StringUtils.isNotEmpty(refundRemark) ? refundRemark
					: "风控复核滞留";
		} 

		List<WordorderOperationLogDto> workorderOperationLogs = new ArrayList<WordorderOperationLogDto>();
		for (int i = 0; i < len; i++) {
			refundDetailInfo = refundInfos[i]
					.split(RefundConstants.STR_SPLIT_SEPARATOR);
			//updateMap.put(refundDetailInfo[0],handleStatus);
			
			if(Integer.valueOf(refundDetailInfo[refundDetailInfo.length-2])==1){
				updateMap.put(refundDetailInfo[0],handleStatus);
			}else{
				updateMap.put(refundDetailInfo[0],refundDetailInfo[refundDetailInfo.length-2]);
			}
			
			Integer reStatus = Integer.parseInt(handleStatus);
			if (RefundConstants.REFUND_STATUS_5.equals(handleStatus)) {
				reStatus = Integer.parseInt(RefundConstants.REFUND_STATUS_0);
			} else {
				if ("1".equals(refundDetailInfo[2])) {
					reStatus = Integer
							.parseInt(RefundConstants.REFUND_STATUS_4);
				} else {
					reStatus = Integer
							.parseInt(RefundConstants.REFUND_STATUS_5);
				}
			}

			WordorderOperationLogDto workorderOperationLog = new WordorderOperationLogDto();
			workorderOperationLog.setOperationTime(new Date());
			workorderOperationLog.setOperator(userId);
			workorderOperationLog.setState(reStatus);
			workorderOperationLog.setWorkorderKy(Long
					.valueOf(refundDetailInfo[0]));
			workorderOperationLog.setRemark(refundRemark);
			 String[] refundInfo=refundInfos[i].split(",");
			 String  tradeOrderNo=refundInfo[refundInfo.length-1];
			 for (Map map : channelOrders) {
					if(tradeOrderNo.equals(map.get("tradeOrderNo")+"")){
						String cpType = (Integer)map.get("cpType")+"";
						if(cpType.equals("3")){
							workorderOperationLog.setRemark("订单已拒付");
							workorderOperationLog.setState(5);
							updateMap.put(refundDetailInfo[0],"5");
						}
					}
				}
			workorderOperationLogs.add(workorderOperationLog);
		}

		//2016-05-04 这个批处理语句可以放在下面的代码段之前执行，也可以放在后面。  这里考虑先写日志，万一 下面的代码段有异常，可以先记录日志 
		batchInsertOperationLog(workorderOperationLogs);	
		
		boolean updateFlg = false;
		if (!updateMap.isEmpty()) {
			Set<Entry<String, String>> entrySet = updateMap.entrySet();
			for (Entry<String, String> entry : entrySet) {
				Map<String, Object> params = new HashMap<String, Object>();
				 params.put("workOrderKy", entry.getKey());
				if (RefundConstants.REFUND_STATUS_4.equals(entry.getValue())) {
					params.put("status", entry.getValue());
					updateFlg = baseDao
							.update(RefundConstants.UPDATE_REFUND_WORK_ORDER_STATUS,
									params);
					params.put("workOrderKy", entry.getKey());
				} else if (RefundConstants.REFUND_STATUS_5.equals(entry.getValue())) {
					params.put("status", RefundConstants.REFUND_STATUS_0);
					updateFlg = baseDao.update(
							RefundConstants.UPDATE_REFUND_STATUS_INFO, params);
				}
				
				//batchInsertOperationLog(workorderOperationLogs);  //delete by sch 2016-05-04 在循环里头，做批处理 ,会导致插入多条操作记录 
				
				List<RefundOrderM> orderList = baseDao.findByQuery(
						RefundConstants.REFUND_KEY + "queryRefundDetailInfo",
						params);
				for (RefundOrderM refundOrderM : orderList) {
					String responseCode = refundProcessService.doRefund(
							refundOrderM.getRefundOrderNo(),
							entry.getValue());
					
					
					if(ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
						refundOrderService.updateRefundDStatusRnTx(refundOrderM.getDetailKy(), 111);
						refundOrderService.updateRefundMStatusRnTx(refundOrderM.getOrderKy(), 111);
						refundOrderService.updateWorkorderStatusRnTx(refundOrderM.getWorkOrderKy(), 7);
					}else if(ResponseCodeEnum.FAIL.getCode().equals(responseCode)){
						refundOrderService.updateRefundDStatusRnTx(refundOrderM.getDetailKy(), 112);
						refundOrderService.updateRefundMStatusRnTx(refundOrderM.getOrderKy(), 112);
						//refundOrderService.updateWorkorderStatusRnTx(refundOrderM.getWorkOrderKy(), 0);
						refundOrderService.updateWorkorderStatusRnTx(refundOrderM.getWorkOrderKy(), 7);	
						//modify by sch,否则的话，失败的订单还会再次出现,这个逻辑和单条订单的处理保持一致 
					}
					
					//对于失败的退款订单，其实最好是再次去查询一下 退款订单的情况，如果是退款定单状态不是进行中，则可以将状态改为7，如果依然是 0，则可以考虑将工作流转为 0.
				}
			}
			resultMap = this.queryRefundReAuditInfo(resultPage, webQueryRefundDTO);
			resultMap.put("resultFlg", updateFlg);
				
			}
		return resultMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.poss.refund.service.RefundManageService#queryRefundReAuditInfo
	 * (com.hnapay.poss.base.dao.model.Page,
	 * com.hnapay.poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public Map<String, Object> queryRefundReAuditInfo(
			Page<RefundWorkOrderAndM> resultPage,
			WebQueryRefundDTO webQueryRefundDTO) {
		webQueryRefundDTO
				.setQueryType(RefundConstants.REFUND_WORKFLOW_NODE_REAUDIT);
		// 查询充退统计信息
		Map<String, Object> model = (Map<String, Object>) baseDao
				.findObjectByCriteria(RefundConstants.STAT_REFUND_INFO,
						webQueryRefundDTO);

		// 查询充退信息
		resultPage = baseDao.findByQuery(
				RefundConstants.QUERY_ASSING_TASK_INFO, resultPage,
				webQueryRefundDTO);
		if (null == model) {
			model = new HashMap<String, Object>();
		}
		model.put("page", resultPage);
		model.put("webQueryRefundDTO", webQueryRefundDTO);
		return model;
	}

	private List<String> getRefundParamDetailInfo(String[] temp, int index) {
		if (null == temp)
			return new ArrayList<String>();
		List<String> result = new ArrayList<String>();
		String[] tempStrs = null;
		for (String str : temp) {
			tempStrs = str.split(RefundConstants.STR_SPLIT_SEPARATOR);
			result.add(tempStrs[index]);
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hnapay.poss.refund.service.RefundManageService#
	 * queryRefundReAuditInfoDetail
	 * (com.hnapay.poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public Map<String, Object> queryRefundReAuditInfoDetail(
			WebQueryRefundDTO webQueryRefundDTO) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 查询会员信息

		// 查询充值详细信息
		List<RefundOrderM> resultList = baseDao.findByQuery(
				RefundConstants.QUERY_REFUNDORDERDETAIL_BY_ACCEPTKY,
				webQueryRefundDTO);
		// 查询审核记录信息

		RefundOrderM mDto = new RefundOrderM();
		if (null != resultList && resultList.size() > 0) {
			mDto = resultList.get(0);
		}

		List<WordorderOperationLogDto> operattionLogs = workorderOperationService
				.queryByWorkorderKy(webQueryRefundDTO.getWorkorderKy());

		model.put("mDto", mDto);
		model.put("webQueryRefundDTO", webQueryRefundDTO);
		model.put("operattionLogs", operattionLogs);

		return model;
	}

	/*
		 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.poss.refund.service.RefundManageService#handeRefundLiquidateBatch
	 * (java.util.Map)
	 */
	@Override
	public Map<String, Object> handeRefundLiquidateBatch(
			Page<RefundWorkOrderAndM> resultPage,
			WebQueryRefundDTO webQueryRefundDTO) {
		// 组装工作流所需参数
		String[] refundInfos = webQueryRefundDTO.getHandleDatas().split(
				RefundConstants.STR_SPLIT_SEPARATOR_W);
		String handleStatus = webQueryRefundDTO.getHandleStatus();
		// String handleType = webQueryRefundDTO.getHandleType();
		List<String> updateList = new ArrayList<String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userId = webQueryRefundDTO.getUserId();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("status", handleStatus);

		List<WordorderOperationLogDto> workorderOperationLogs = new ArrayList<WordorderOperationLogDto>();
		String[] tempStrs = null;
		for (String str : refundInfos) {
			tempStrs = str.split(RefundConstants.STR_SPLIT_SEPARATOR);
			WordorderOperationLogDto workorderOperationLog = new WordorderOperationLogDto();
			workorderOperationLog.setOperationTime(new Date());
			workorderOperationLog.setOperator(userId);
			workorderOperationLog.setState(Integer.parseInt(handleStatus));
			workorderOperationLog.setWorkorderKy(Long.valueOf(tempStrs[0]));
			workorderOperationLog.setRemark(webQueryRefundDTO.getApplyReason());
			workorderOperationLogs.add(workorderOperationLog);
		}

		if (RefundConstants.REFUND_STATUS_6.equals(handleStatus)) {// 清算拒绝，则需记账
			updateList = getRefundParamDetailInfo(refundInfos, 0);
			params.put("acceptKyList", updateList);
			List<RefundOrderM> orderList = baseDao.findByQuery(
					"refund.batch.queryRefundDetailInfo", params);
			// 记账
			for (RefundOrderM mDto : orderList) {
				mDto.setStatus(RefundConstants.REFUND_HANDLE_STATUS_112);
				mDto.setOverStatus("1");
				mDto.setHandler(webQueryRefundDTO.getUserId());
				String jsonStr = JSonUtil.toJSonString(mDto);
				notifyFacadeService.sendRequest(buildNotify2QueueRequest(
						jsonStr, queueName));
			}
			resultMap.put("webQueryRefundDTO", webQueryRefundDTO);
			resultMap.put("err1", "清算拒绝操作完成！");
		} else {// 退回
			updateList = getRefundParamDetailInfo(refundInfos, 0);
			if (!updateList.isEmpty()) {
				params.put("acceptKyList", updateList);
				baseDao.update(RefundConstants.UPDATE_REFUND_STATUS_INFO,
						params);

				batchInsertOperationLog(workorderOperationLogs);
			}
			resultMap = this.queryRefundLiquidateInfo(resultPage,
					webQueryRefundDTO);
		}

		return resultMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hnapay.poss.refund.service.RefundManageService#
	 * queryRefundLiquidateInfoDetail
	 * (com.hnapay.poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public Map<String, Object> queryRefundLiquidateInfoDetail(
			WebQueryRefundDTO webQueryRefundDTO) {
		Map<String, Object> model = new HashMap<String, Object>();
		// 查询会员信息

		// 查询充值详细信息
		List<RefundOrderM> resultList = baseDao.findByQuery(
				RefundConstants.QUERY_REFUNDORDERDETAIL_BY_ACCEPTKY,
				webQueryRefundDTO);
		RefundOrderM mDto = new RefundOrderM();
		if (null != resultList && resultList.size() > 0) {
			mDto = resultList.get(0);
		}

		List<WordorderOperationLogDto> operattionLogs = workorderOperationService
				.queryByWorkorderKy(webQueryRefundDTO.getWorkorderKy());
		model.put("mDto", mDto);
		model.put("webQueryRefundDTO", webQueryRefundDTO);
		model.put("operattionLogs", operattionLogs);
		return model;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hnapay.poss.refund.service.RefundManageService#
	 * handerRefundLiquidateSingle(java.util.Map)
	 */
	@Override
	public Map<String, Object> handerRefundLiquidateSingle(
			Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		String refundStatus = (String) params.get("refundStatus");
		String refundRemark = StringUtil
				.null2String(params.get("refundRemark"));
		String userId = StringUtil.null2String(params.get("userId"));
		result.put("requestUrl", params.get("requestUrl"));

		Map<String, Object> workOrder = new HashMap<String, Object>();
		workOrder.put("workOrderKy",
				Long.valueOf((String) params.get("workOrderKy")));
		workOrder.put("status", Integer.valueOf(refundStatus));

		if (RefundConstants.REFUND_STATUS_6.equals(refundStatus)) {// 清算拒绝，则需记账
			List<RefundOrderM> orderList = baseDao.findByQuery(
					"refund.batch.queryRefundDetailInfo", workOrder);
			// 记账
			for (RefundOrderM mDto : orderList) {
				mDto.setStatus(RefundConstants.REFUND_HANDLE_STATUS_112);
				mDto.setHandler(userId);
				mDto.setOverStatus("1");
				String jsonStr = JSonUtil.toJSonString(mDto);
				notifyFacadeService.sendRequest(buildNotify2QueueRequest(
						jsonStr, queueName));
			}
			result.put("resultMsg", "清算拒绝操作完成！");
		} else {// 退回
			refundRemark = StringUtils.isNotEmpty(refundRemark) ? refundRemark
					: "结算退回";
			boolean upFlag = baseDao.update(
					RefundConstants.UPDATE_REFUND_STATUS_INFO, workOrder);
			if (!upFlag) {
				result.put("resultMsg", "未更新到数据，清算退回操作失败！");
				return result;
			}

			saveOperationLog(userId, Integer.valueOf(refundStatus),
					Long.valueOf((String) params.get("workOrderKy")),
					refundRemark);
			result.put("resultMsg", "清算退回操作成功！");
		}

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.poss.refund.service.RefundManageService#handerRefundReAuditSingle
	 * (java.util.Map)
	 */
	@Override
	public Map<String, Object> handerRefundReAuditSingle(
			Map<String, Object> params) {
		Map<String, Object> result = new HashMap<String, Object>();
		String refundStatus = (String) params.get("refundStatus");
		String refundRemark = StringUtil
				.null2String(params.get("refundRemark"));
		String workOrderStatus = StringUtil.null2String(params
				.get("workOrderStatus"));
		String handleType = (String) params.get("handleType");
		result.put("requestUrl", params.get("requestUrl"));
		String userId = String.valueOf(params.get("userId"));

		Map<String, Object> workOrder = new HashMap<String, Object>();
		workOrder.put("workOrderKy",
				Long.valueOf((String) params.get("workOrderKy")));

		if ("agree".equals(handleType)) {// 同意
			if (RefundConstants.REFUND_STATUS_5.equals(refundStatus)) {
				refundRemark = StringUtils.isNotEmpty(refundRemark) ? refundRemark
						: "复核拒绝";

			} else if (RefundConstants.REFUND_STATUS_4.equals(refundStatus)) {
				refundRemark = StringUtils.isNotEmpty(refundRemark) ? refundRemark
						: "复核同意";
			}
			if (RefundConstants.REFUND_STATUS_2.equals(workOrderStatus)) {// 审核拒绝
				workOrder.put("status",
						Integer.valueOf(RefundConstants.REFUND_STATUS_5));
			} else {// RefundConstants.REFUND_STATUS_1 审核同意
				workOrder.put("status",
						Integer.valueOf(RefundConstants.REFUND_STATUS_4));
			}

		} else {// "rollback" 退回
			refundRemark = StringUtils.isNotEmpty(refundRemark) ? refundRemark
					: "复核退回";
			workOrder.put("status",
					Integer.valueOf(RefundConstants.REFUND_STATUS_0));
		}

		boolean upFlag = baseDao.update(
				RefundConstants.UPDATE_REFUND_STATUS_INFO, workOrder);
		if (!upFlag) {
			result.put("resultMsg", "复核操作失败,未跟新到数据!");
			result.put("resultFlg", false);
			return result;
		} else {
			if ("agree".equals(handleType)) {
				List<RefundOrderM> orderList = baseDao.findByQuery(
						RefundConstants.REFUND_KEY + "queryRefundDetailInfo",
						workOrder);
				for (RefundOrderM refundOrderM : orderList) {
					String responseCode = refundProcessService.doRefund(
							refundOrderM.getRefundOrderNo(),
							refundOrderM.getWorkOrderStatus());
					
					if(ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
						refundOrderService.updateRefundDStatusRnTx(refundOrderM.getDetailKy(), 111);
						refundOrderService.updateRefundMStatusRnTx(refundOrderM.getOrderKy(), 111);
						refundOrderService.updateWorkorderStatusRnTx(refundOrderM.getWorkOrderKy(), 7);
					}else if(ResponseCodeEnum.FAIL.getCode().equals(responseCode)){
						refundOrderService.updateRefundDStatusRnTx(refundOrderM.getDetailKy(), 112);
						refundOrderService.updateRefundMStatusRnTx(refundOrderM.getOrderKy(), 112);
						refundOrderService.updateWorkorderStatusRnTx(refundOrderM.getWorkOrderKy(), 7);
					}
				}
			}

		}

		saveOperationLog(userId,
				Integer.parseInt(String.valueOf(workOrder.get("status"))),
				Long.valueOf((String) params.get("workOrderKy")), refundRemark);
		result.put("resultMsg", "复核操作成功！");

		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.poss.refund.service.RefundManageService#queryAuditInfo(com
	 * .hnapay.poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public List<RefundDetailInfoDTO> queryAuditInfo(
			WebQueryRefundDTO webQueryRefundDTO) {
		// 查询充退信息
		List<RefundDetailInfoDTO> result = baseDao.findByQuery(
				RefundConstants.QUERY_REFUND_DOWNLOAD_INFO, webQueryRefundDTO);
		return (null != result ? result : new ArrayList<RefundDetailInfoDTO>());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.poss.refund.service.RefundManageService#queryReAuditInfo(com
	 * .hnapay.poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public List<RefundDetailInfoDTO> queryReAuditInfo(
			WebQueryRefundDTO webQueryRefundDTO) {
		// 查询充退信息
		List<RefundDetailInfoDTO> result = baseDao.findByQuery(
				RefundConstants.QUERY_REFUND_DOWNLOAD_INFO, webQueryRefundDTO);
		return (null != result ? result : new ArrayList<RefundDetailInfoDTO>());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.poss.refund.service.RefundManageService#queryRefundLiquidateInfo
	 * (com.hnapay.poss.base.dao.model.Page,
	 * com.hnapay.poss.refund.model.WebQueryRefundDTO)
	 */
	@Override
	public Map<String, Object> queryRefundLiquidateInfo(
			Page<RefundWorkOrderAndM> resultPage,
			WebQueryRefundDTO webQueryRefundDTO) {
		webQueryRefundDTO
				.setQueryType(RefundConstants.REFUND_WORKFLOW_NODE_LIQUIDATE);
		// 查询充退统计信息
		Map<String, Object> model = (Map<String, Object>) baseDao
				.findObjectByCriteria(RefundConstants.STAT_REFUND_INFO,
						webQueryRefundDTO);

		// 查询充退信息
		resultPage = baseDao.findByQuery(
				RefundConstants.QUERY_ASSING_TASK_INFO, resultPage,
				webQueryRefundDTO);
		if (null == model) {
			model = new HashMap<String, Object>();
		}
		model.put("page", resultPage);
		model.put("webQueryRefundDTO", webQueryRefundDTO);
		return model;
	}

	/**
	 * 订单回调订单状态更新接口
	 */
	@Override
	public boolean updateRefundOrder(RefundOrderM order) {

		boolean result = false;
		// 1.更新订单状态
		result = baseDao.update(RefundConstants.UPDATE_DSTATUSAFTERACCOUNTING,
				order);
		if (!result) {
			return result;
		}

		// 2.更新可充退金额（当处理失败，款已经退回，可充退金额需要增加）
		if (WithdrawOrderStatus.FAIL.getValue() == order.getStatus().intValue()) {
			RefundBanlanceCheck queryDto = new RefundBanlanceCheck();
			queryDto.setRechargeSeq(order.getRechargeOrderSeq());
			RefundBanlanceCheck resultDto = (RefundBanlanceCheck) baseDao
					.findObjectByCriteria(
							RefundConstants.QUERY_REFUNDBANLANCECHECK, queryDto);
			if (null != resultDto && null != resultDto.getRechargeSeq()
					&& 0 != resultDto.getRechargeSeq().longValue()) {
				resultDto.setRefundAmount(resultDto.getRefundAmount().add(
						order.getApplyAmount().negate()));
				baseDao.update(RefundConstants.UPDATE_REFUNDBANLANCECHECK,
						resultDto);
			}
		}

		String status = "0";
		String refundRemark;
		String errorCode = "";
		if ("1".equalsIgnoreCase(order.getOverStatus())) {// 1:清算拒绝 2：完成
			status = RefundConstants.REFUND_STATUS_6;
			refundRemark = "清算拒绝";
			errorCode = "1011";
		} else {
			status = RefundConstants.REFUND_STATUS_7;
			refundRemark = "已完成";
		}
		if (!StringUtil.isNull(order.getWorkOrderStatus())
				&& !RefundConstants.REFUND_STATUS_6.equals(order
						.getWorkOrderStatus())
				&& !RefundConstants.REFUND_STATUS_7.equals(order
						.getWorkOrderStatus())) {
			// 更新工单状态
			Map<String, Object> updateMap = new HashMap<String, Object>();
			updateMap.put("workOrderKy", order.getWorkOrderKy());
			updateMap.put("status", status);
			if (status == RefundConstants.REFUND_STATUS_6) {
				baseDao.update(RefundConstants.UPDATE_REFUND_STATUS_INFO,
						updateMap);
			}
			// 记录历史
			saveOperationLog("admin", Integer.valueOf(status),
					order.getWorkOrderKy(), refundRemark);
		}
		String resultStatus = "1";// 1：成功 0：失败
		if (WithdrawOrderStatus.SUCCESS.getValue() == order.getStatus()
				.intValue()) {
			resultStatus = "1";
		} else if (WithdrawOrderStatus.FAIL.getValue() == order.getStatus()
				.intValue()) {
			resultStatus = "0";
			errorCode = "1010";
		}
		String type = "2";
		if ("FI".equals(order.getApplyFrom())) {
			type = "1";
		}
		Map<String, String> toFi = new HashMap<String, String>();
		toFi.put("depositOrderNo", String.valueOf(order.getRechargeOrderSeq()));
		toFi.put(
				"depositBackNo",
				order.getDepositBackNo() == null ? "" : order
						.getDepositBackNo());
		toFi.put("amount", String.valueOf(order.getApplyAmount()));
		toFi.put("dateTime",
				DateUtil.formatDateTime("yyyyMMddHHmmss", order.getApplyTime()));
		toFi.put("type", type);
		sendMsgToFi(toFi, resultStatus, errorCode);
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.hnapay.poss.refund.service.RefundManageService#updateOrderMStatusTask
	 * ()
	 */
	@Override
	public void updateOrderMStatusTask() {
		baseDao.update(RefundConstants.UPDATE_MSTATUSWHENTASK, new Integer(0));
		baseDao.update(RefundConstants.UPDATE_WORKORDERSTATUSWHENTASK,
				new Integer(0));
	}

	private void saveOperationLog(String userId, Integer refundWorkorderStatus,
			Long workorderKy, String refundRemark) {
		try {
			WordorderOperationLogDto workorderOperationLog = new WordorderOperationLogDto();
			workorderOperationLog.setOperationTime(new Date());
			workorderOperationLog.setOperator(userId);
			workorderOperationLog.setState(refundWorkorderStatus);
			workorderOperationLog.setWorkorderKy(workorderKy);
			workorderOperationLog.setRemark(refundRemark);
			workorderOperationService.create(workorderOperationLog);
		} catch (Exception e) {
		}
	}

	private void batchInsertOperationLog(
			List<WordorderOperationLogDto> workorderOperationLogs) {

		try {
			for (WordorderOperationLogDto pojo : workorderOperationLogs) {
				workorderOperationService.create(pojo);
			}

		} catch (Exception e) {

		}
	}
	@Override
	public RefundOrderM queryTradeOrderNo(String refundOrderNo) {
		RefundOrderM  refundOrderM=(RefundOrderM) baseDao.findById(RefundConstants.QUERY_TRADEORDERNO,refundOrderNo);
		return refundOrderM;
	}
	
	/*
	 * 批量处理  自动退款任务 
	 * 
	 */
	@Override
	public Map<String, Object> handeAutoRefundTaskBatch(WebQueryRefundDTO webQueryRefundDTO) {
		// 组装工作流所需参数
		Map<String, Object> reqMap = new HashMap<String, Object>();
		List<RefundWorkOrderAndM> refundWorkOrderList = baseDao.findByQuery(
				RefundConstants.REFUND_KEY + "queryAutoRefundTaskInfo",
				reqMap);
		
		String handleStatus = RefundConstants.REFUND_STATUS_4; //系统自动复核同意
		// String handleType = webQueryRefundDTO.getHandleType();
		Map<String,String> updateMap = new HashMap<String, String>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userId = "fo";//webQueryRefundDTO.getUserId();

		List<WordorderOperationLogDto> workorderOperationLogs = new ArrayList<WordorderOperationLogDto>();
		for(RefundWorkOrderAndM refundOrdm: refundWorkOrderList){
			RefundWorkorder rwo = refundOrdm.getRefundWorkorder(); //工作流对象
			String tradeOrderNo = refundOrdm.getRefundOrderM().getTradeOrderNo();  //网关订单号
			if(StringUtils.isEmpty(tradeOrderNo)){
				System.out.println("退款工单号：" + rwo.getWorkorderKy() + " 的网关订单号为空！！") ;
				continue;
			}
			System.out.println("handeAutoRefundTaskBatch-" + refundOrdm.getRefundOrderM().getOrgCode());
			if(ChannelItemOrgCodeEnum.ABC.getCode().equals(
					refundOrdm.getRefundOrderM().getOrgCode())){  //农行线下退款
				WordorderOperationLogDto abcWolDto = new WordorderOperationLogDto();
				abcWolDto.setOperationTime(new Date());
				abcWolDto.setOperator(userId);
				abcWolDto.setState(Integer.parseInt(RefundConstants.REFUND_STATUS_3)); //自动审核滞留
				abcWolDto.setWorkorderKy(rwo.getWorkorderKy());
				abcWolDto.setRemark("自动审核滞留");
				workorderOperationService.create(abcWolDto);
				continue;
			}
			
			updateMap.put(rwo.getWorkorderKy().toString(), handleStatus);
			
			WordorderOperationLogDto workorderOperationLog = new WordorderOperationLogDto();
			workorderOperationLog.setOperationTime(new Date());
			workorderOperationLog.setOperator(userId);
			workorderOperationLog.setState(Integer.parseInt(RefundConstants.REFUND_STATUS_1)); //审核通过
			workorderOperationLog.setWorkorderKy(rwo.getWorkorderKy());
			workorderOperationLog.setRemark("自动审核通过");
			
			WordorderOperationLogDto workorderOperationLog2 = new WordorderOperationLogDto();
			workorderOperationLog2.setOperationTime(new Date());
			workorderOperationLog2.setOperator(userId);
			workorderOperationLog2.setState(Integer.parseInt(RefundConstants.REFUND_STATUS_4)); //复核通过
			workorderOperationLog2.setWorkorderKy(rwo.getWorkorderKy());
			workorderOperationLog2.setRemark("自动复核通过");
			
			
			Map<String, Object> reqChargeMap = new HashMap<String, Object>();
			reqChargeMap.put("tradeOrderNo", tradeOrderNo);
			Map chargeMap = refundProcessService.queryChargeBackOrder(reqChargeMap);
			List<Map> channelOrders = (List<Map>) chargeMap.get("result");
			
			for (Map map : channelOrders) {
				if(tradeOrderNo.equals(map.get("tradeOrderNo")+"")){
					String cpType = (Integer)map.get("cpType")+"";
					if(cpType.equals("3")){
						workorderOperationLog.setRemark("订单已拒付");
						workorderOperationLog.setState(
								Integer.parseInt(RefundConstants.REFUND_STATUS_2));
						
						workorderOperationLog2.setRemark("订单已拒付");
						workorderOperationLog2.setState(
								Integer.parseInt(RefundConstants.REFUND_STATUS_5));
						
						updateMap.put(rwo.getWorkorderKy().toString(), RefundConstants.REFUND_STATUS_5);
					}
				}
			}
			workorderOperationLogs.add(workorderOperationLog);
			workorderOperationLogs.add(workorderOperationLog2);
		}

		//2016-05-04 这个批处理语句可以放在下面的代码段之前执行，也可以放在后面。  这里考虑先写日志，万一 下面的代码段有异常，可以先记录日志 
		batchInsertOperationLog(workorderOperationLogs);	
		System.out.println("RefundManageServiceImpl=====" + updateMap);
		boolean updateFlg = false;
		if (!updateMap.isEmpty()) {
			Set<Entry<String, String>> entrySet = updateMap.entrySet();
			for (Entry<String, String> entry : entrySet) {
				Map<String, Object> params = new HashMap<String, Object>();
				 params.put("workOrderKy", entry.getKey());
				 //params.put("oldStatus", webQueryRefundDTO.getOldStatus());
				 //baseDao.update(RefundConstants.UPDATE_REFUND_STATUS_INFO, params);
				if (RefundConstants.REFUND_STATUS_4.equals(entry.getValue())) { //复核通过
					params.put("status", entry.getValue());
					updateFlg = baseDao
							.update(RefundConstants.UPDATE_REFUND_WORK_ORDER_STATUS,
									params);
					params.put("workOrderKy", entry.getKey());
				} else if (RefundConstants.REFUND_STATUS_5.equals(entry.getValue())) { //复核拒绝
					params.put("status", RefundConstants.REFUND_STATUS_0);
					updateFlg = baseDao.update(
							RefundConstants.UPDATE_REFUND_STATUS_INFO, params);
				}
				
				//batchInsertOperationLog(workorderOperationLogs);  //delete by sch 2016-05-04 在循环里头，做批处理 ,会导致插入多条操作记录 
				
				List<RefundOrderM> orderList = baseDao.findByQuery(
						RefundConstants.REFUND_KEY + "queryRefundDetailInfo",
						params);
				System.out.println("RefundManageServiceImpl=====orderList " + orderList.size());
				for (RefundOrderM refundOrderM : orderList) {
					String responseCode = refundProcessService.doRefund(
							refundOrderM.getRefundOrderNo(),
							entry.getValue());
					
					if(ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
						refundOrderService.updateRefundDStatusRnTx(refundOrderM.getDetailKy(), 111);
						refundOrderService.updateRefundMStatusRnTx(refundOrderM.getOrderKy(), 111);
						refundOrderService.updateWorkorderStatusRnTx(refundOrderM.getWorkOrderKy(), 7);
					}else if(ResponseCodeEnum.FAIL.getCode().equals(responseCode)){
						refundOrderService.updateRefundDStatusRnTx(refundOrderM.getDetailKy(), 112);
						refundOrderService.updateRefundMStatusRnTx(refundOrderM.getOrderKy(), 112);
						//refundOrderService.updateWorkorderStatusRnTx(refundOrderM.getWorkOrderKy(), 0);
						refundOrderService.updateWorkorderStatusRnTx(refundOrderM.getWorkOrderKy(), 7);	
						//modify by sch,否则的话，失败的订单还会再次出现,这个逻辑和单条订单的处理保持一致 
					}
					
					//对于失败的退款订单，其实最好是再次去查询一下 退款订单的情况，如果是退款定单状态不是进行中，则可以将状态改为7，如果依然是 0，则可以考虑将工作流转为 0.
				}
			}
			//resultMap = this.queryRefundReAuditInfo(resultPage, webQueryRefundDTO);
			resultMap.put("resultFlg", updateFlg);
				
			}
		return resultMap;
	}

}
