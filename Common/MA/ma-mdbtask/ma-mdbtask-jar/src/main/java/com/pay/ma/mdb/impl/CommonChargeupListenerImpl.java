/**
 * 
 */
package com.pay.ma.mdb.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.ma.chargeup.dto.BalanceDealDto;
import com.pay.ma.chargeup.service.BalanceDealService;
import com.pay.ma.commmon.ChargeUpStatusEnum;
import com.pay.ma.facade.PEServiceFacade;
import com.pay.pe.service.PaymentReqDto;
import com.pay.pe.service.PaymentResponseDto;

/**
 * @author Administrator
 * 
 */
public class CommonChargeupListenerImpl extends AbstractChargeupListener {

	private Log log = LogFactory.getLog(CommonChargeupListenerImpl.class);

	private BalanceDealService balanceDealService;

	private PEServiceFacade peServiceFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.ma.mdb.impl.AbstractChargeupListener#doChargeup(com.woyo
	 * .pay.pe.service.PaymentResponseDto)
	 */
	@Override
	protected void doChargeup(PaymentResponseDto paymentResponseDto) {
		// 验证是否已经成功记账,未记账调用PE进行记账
		boolean result = false;
		if (!this.handlerCheckChargeup(paymentResponseDto)) {
			// 流水号
			String serialNo = paymentResponseDto.getPaymentReq().getOrderId();
			// 交易号
			Integer dealCode = paymentResponseDto.getPaymentReq().getDealCode();
			if (log.isInfoEnabled()) {
				log.info("订单号 orderId 为【" + serialNo + "】，交易号deal_code为【" + dealCode + "】开始记账");
			}
			try {
				result = this.peServiceFacade.accountCallFeeReponse(paymentResponseDto);
			} catch (Exception e) {
				if (log.isErrorEnabled()) {
					log.error("订单号 orderId 为【" + serialNo + "】，交易号deal_code为【" + dealCode + "】记账发生异常", e);
				}
				// 处理记账失败
				this.balanceDealService.callUpdateBalanceDealStatus(serialNo, dealCode, ChargeUpStatusEnum.FAIL.getCode());
			}
			// 记账成功，更新记账状态
			if (result) {
				try {

					boolean res = this.balanceDealService.callUpdateBalanceDealStatus(serialNo, dealCode, ChargeUpStatusEnum.SUCCESS
							.getCode());
					if (res) {

						if (log.isInfoEnabled()) {
							log.info("订单号 orderId 为【" + serialNo + "】，交易号deal_code为【" + dealCode + "】更新记账状态成功");
						}
					}else{
						if (log.isInfoEnabled()) {
							log.info("订单号 orderId 为【" + serialNo + "】，交易号deal_code为【" + dealCode + "】更新记账状态失败");
						}
					}
					if (log.isInfoEnabled()) {
						log.info("订单号 orderId 为【" + serialNo + "】，交易号deal_code为【" + dealCode + "】记账成功");
					}
				} catch (Exception e) {
					
					if (log.isErrorEnabled()) {
						log.error("订单号 orderId 为【" + serialNo + "】，交易号deal_code为【" + dealCode + "】更新记账状态发生异常", e);
					}
					//内部异常，可能会导致记账的状态为3
//					this.balanceDealService.callUpdateBalanceDealStatus(serialNo, dealCode, ChargeUpStatusEnum.FAIL.getCode());
				}
			} else {
				if (log.isInfoEnabled()) {
					log.info("订单号 orderId 为【" + serialNo + "】，交易号deal_code为【" + dealCode + "】记账失败");
				}
				this.balanceDealService.callUpdateBalanceDealStatus(serialNo, dealCode, ChargeUpStatusEnum.FAIL.getCode());
			}
		}

	}

	/**
	 * 验证是否已经记过帐
	 * 
	 * @return
	 */
	private boolean handlerCheckChargeup(PaymentResponseDto paymentResponseDto) {
		PaymentReqDto PaymentReqDto = paymentResponseDto.getPaymentReq();
//		BalanceDealDto balanceDealDto = this.balanceDealService.callQueryBalanceDealInfo(PaymentReqDto.getOrderId(), PaymentReqDto
//				.getDealCode(), PaymentReqDto.getAmount());
		List<BalanceDealDto> balanceDealDtos = this.balanceDealService.callQueryBalanceDealInfoByVo(paymentResponseDto.getVoucherCode());
		if (balanceDealDtos != null) {
			// 验证是否已经记账
			if (balanceDealDtos.get(0).getChargeUpStatus().intValue() == ChargeUpStatusEnum.SUCCESS.getCode()) {
				// 说明此交易信息已经记账
				return true;
			}
		}
		return false;

	}

	/**
	 * @param balanceDealService
	 *            the balanceDealService to set
	 */
	public void setBalanceDealService(BalanceDealService balanceDealService) {
		this.balanceDealService = balanceDealService;
	}

	/**
	 * @param peServiceFacade
	 *            the peServiceFacade to set
	 */
	public void setPeServiceFacade(PEServiceFacade peServiceFacade) {
		this.peServiceFacade = peServiceFacade;
	}

}
