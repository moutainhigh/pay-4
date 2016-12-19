/**
 *  File: OrderMassPayToBankServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-28     darv      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.orderconsistency.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;
import com.pay.fo.order.service.batchpayment.BatchPaymentToBankReqDetailService;
import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportBaseDTO;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportDetailDTO;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankOrderDTO;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.dto.orderconsistency.masspaytobank.OrderByMasspayToBankDTO;
import com.pay.fundout.withdraw.service.masspaytobank.MassPaytobankImportDetailService;
import com.pay.fundout.withdraw.service.operatorlog.OperatorlogService;
import com.pay.fundout.withdraw.service.order.WithdrawOrderBusiType;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.fundout.withdraw.service.orderconsistency.OrderMassPayToBankService;
import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.common.order.WithdrawOrderStatus;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;
import com.pay.poss.dto.withdraw.notify.Notify2QueueRequest;
import com.pay.poss.service.ma.batchpaytoaccount.MassPayService;
import com.pay.poss.service.withdraw.inf.output.NotifyFacadeService;
import com.pay.util.JSonUtil;

/**
 * @author darv
 * 
 */
@SuppressWarnings("unchecked")
public class OrderMassPayToBankServiceImpl implements OrderMassPayToBankService {
	private BaseDAO daoService;

	private MassPayService massPayService;

	private OperatorlogService operatorlogService;

	private WithdrawOrderService withdrawOrderService;

	private MassPaytobankImportDetailService massPaytobankImportDetailService;

	private NotifyFacadeService notifyFacadeService;

	private String queueName;
	private BatchPaymentOrderService batchPaymentOrderService;
	/**
	 * 批付到银行请求明细服务类
	 */
	private BatchPaymentToBankReqDetailService batchPaymentToBankReqDetailService;

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public void setNotifyFacadeService(NotifyFacadeService notifyFacadeService) {
		this.notifyFacadeService = notifyFacadeService;
	}

	public void setMassPaytobankImportDetailService(
			MassPaytobankImportDetailService massPaytobankImportDetailService) {
		this.massPaytobankImportDetailService = massPaytobankImportDetailService;
	}

	public void setBatchPaymentOrderService(
			BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}

	public void setWithdrawOrderService(
			WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	public void setMassPayService(MassPayService massPayService) {
		this.massPayService = massPayService;
	}

	public void setOperatorlogService(OperatorlogService operatorlogService) {
		this.operatorlogService = operatorlogService;
	}

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

	@Override
	public List getMasspayToBankOrder(Map params) {
		return daoService.findByQuery(
				"orderconsistency_masspaytobank.getMasspayToBankOrder", params);
	}

	public void setBatchPaymentToBankReqDetailService(
			BatchPaymentToBankReqDetailService batchPaymentToBankReqDetailService) {
		this.batchPaymentToBankReqDetailService = batchPaymentToBankReqDetailService;
	}

	@Override
	public List getDetail(Map params) {
		return daoService.findByQuery(
				"orderconsistency_masspaytobank.getDetail", params);
	}

	@Override
	public void createSingleOrderRnTx(MassPaytobankImportDetailDTO detail,
			MassPaytobankImportBaseDTO base, MassPaytobankOrderDTO order,
			String operator) throws PossException {
		try {
			if (massPaytobankImportDetailService.updateOrderStatus(detail) == 1) {
				WithdrawOrderAppDTO appDTO = buildWithdrawOrderAppDTO(detail,
						base, order);
				Long id = withdrawOrderService.createWithdrawOrderRnTx(appDTO);
				if (id == null) {
					LogUtil.error(getClass(), "插入订单失败", OPSTATUS.EXCEPTION,
							"detailId:" + detail.getDetailSeq(), "", "", "", "");
					throw new PossException("创建子订单错误",
							ExceptionCodeEnum.UN_DEFINE_EXCEPTIONCODE);
				}
				appDTO.setSequenceId(id);
				String jsonStr = JSonUtil.toJSonString(appDTO.getSequenceId());
				// 通知后台处理
				notifyFacadeService
						.sendRequest(buildNotify2QueueRequest(jsonStr));

				OperatorlogDTO operDto = new OperatorlogDTO();
				operDto.setLogType(5);
				operDto.setLogTypeDesc("批量付款到银行总订单生成后未生成相关子订单");
				operDto.setBusiOrderId(String.valueOf(order.getMassOrderSeq()));
				operDto.setMark("重成子订单--批量总订单号：" + order.getMassOrderSeq()
						+ ";批次号：" + base.getBusinessNo() + ";明细记录号为："
						+ detail.getDetailSeq());
				operDto.setOperator(operator);
				operDto.setCreationDate(new Date());
				operatorlogService.saveOperatorLog(operDto);
			}
		} catch (Exception e) {
			LogUtil.error(getClass(), "创建子订单或记录日志有误", OPSTATUS.EXCEPTION,
					e.toString(), "", e.getMessage(), "", "");
			throw new PossException(e.toString(),
					ExceptionCodeEnum.UN_DEFINE_EXCEPTIONCODE, e);
		}
	}

	private WithdrawOrderAppDTO buildWithdrawOrderAppDTO(
			MassPaytobankImportDetailDTO detail,
			MassPaytobankImportBaseDTO base, MassPaytobankOrderDTO order) {
		WithdrawOrderAppDTO withdrawOrderAppDTO = new WithdrawOrderAppDTO();
		withdrawOrderAppDTO.setMemberCode(base.getPayerMomberCode());
		withdrawOrderAppDTO.setMemberType(new Long(MemberTypeEnum.MERCHANT
				.getCode()));
		withdrawOrderAppDTO.setMemberAccType(new Long(base.getPayerAcctType()));
		withdrawOrderAppDTO.setMemberAcc(this.getAcctCode(
				base.getPayerMomberCode(), base.getPayerAcctType()));
		withdrawOrderAppDTO.setOrderAmount(detail.getAmount()); // 设置订单金额
		// withdrawOrderAppDTO.setPrioritys(Short.valueOf("5"));
		withdrawOrderAppDTO.setAccountName(detail.getPayeeName());
		withdrawOrderAppDTO.setBankAcct(detail.getPayeeBankAcct());
		// withdrawOrderAppDTO.setBankAcctType(withdrawRequest.getBankAcctType());
		withdrawOrderAppDTO.setBankKy(detail.getBankCode().toString());
		withdrawOrderAppDTO.setOrderRemarks(detail.getRemark());
		withdrawOrderAppDTO.setBankBranch(detail.getOpeningBankName());
		withdrawOrderAppDTO.setBankProvince(Short.valueOf(detail
				.getProvinceCode().toString()));
		withdrawOrderAppDTO.setBankCity(Short.valueOf(String.valueOf(detail
				.getCityCode())));
		withdrawOrderAppDTO.setWithdrawBankCode(String.valueOf(detail
				.getBankCode()));// 出款银行
		withdrawOrderAppDTO.setOrderSeqId(detail.getBusinessOrderId());
		if (order.getIsPayerPayFee().intValue() == 0) {
			// 获取手续费
			withdrawOrderAppDTO.setAmount(detail.getAmount() + detail.getFee());
			withdrawOrderAppDTO.setPayerAmount(detail.getAmount());
		} else {
			withdrawOrderAppDTO.setAmount(detail.getAmount());
			withdrawOrderAppDTO.setPayerAmount(detail.getAmount());
		}
		withdrawOrderAppDTO.setFee(detail.getFee());
		// 交易类型
		withdrawOrderAppDTO.setType(1L);
		withdrawOrderAppDTO.setTradeType(1);
		withdrawOrderAppDTO.setBusiType(new Long(
				WithdrawOrderBusiType.MASSPAY2BANK.getCode()));
		// 订单状态
		withdrawOrderAppDTO.setStatus(Long.valueOf(WithdrawOrderStatus.INIT
				.getValue()));
		withdrawOrderAppDTO.setMassOrderSeq(order.getMassOrderSeq());
		return withdrawOrderAppDTO;
	}

	// 构建消息对象
	private Notify2QueueRequest buildNotify2QueueRequest(String jsonStr) {
		Notify2QueueRequest request = new Notify2QueueRequest();
		request.setQueueName(queueName);
		request.setTargetObject(jsonStr);
		return request;
	}

	/**
	 * 获得账户号
	 * 
	 * @param memberCode
	 * @param acctType
	 * @return
	 */
	private String getAcctCode(Long memberCode, Integer acctType) {
		AcctAttribDto acctAttribDto = null;
		try {
			acctAttribDto = this.massPayService.getAcctAttrInfo(memberCode,
					acctType);
		} catch (MaAccountQueryUntxException e) {
			e.printStackTrace();
		}
		if (acctAttribDto == null) {
			return null;
		}
		return acctAttribDto.getAcctCode();
	}

	@Override
	public void createSingleOrderRnTx(String uploadSeq,
			String batchPaymentOrderId, String operator) throws PossException {

		Map params = new HashMap();
		params.put("uploadSeq", uploadSeq);

		List<OrderByMasspayToBankDTO> list = getDetail(params);
		BatchPaymentOrderDTO batchPaymentOrderDTO = (BatchPaymentOrderDTO) batchPaymentOrderService
				.getOrder(Long.valueOf(batchPaymentOrderId));
		if (null != list && !list.isEmpty()) {
			for (OrderByMasspayToBankDTO order : list) {

				Long detailSeq = order.getDetailSeq();
				BatchPaymentToBankReqDetailDTO batchPaymentToBankReqDetailDTO = batchPaymentToBankReqDetailService
						.getDetail(detailSeq);
				batchPaymentOrderService.createDetailOrder(
						batchPaymentOrderDTO, batchPaymentToBankReqDetailDTO);
			}
		}
		// do log
	}
}
