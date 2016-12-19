/**
 *  modifyHistory:
 *  2016-08-09 nico.shao 增加了voucherCode 
 */
package com.pay.ma.task.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.ma.chargeup.dto.BalanceDealDto;
import com.pay.ma.chargeup.dto.BalanceEntryDto;
import com.pay.ma.commmon.ChargeUpStatusEnum;
import com.pay.ma.facade.PEServiceFacade;
import com.pay.pe.service.PaymentResponseDto;

/**
 * 补账task
 * @author Administrator
 * 
 */
public class RepairChargeupTaskImpl extends AbstractChargeupTask {

	private Log log = LogFactory.getLog(RepairChargeupTaskImpl.class);

	private PEServiceFacade peServiceFacade;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ma.task.impl.AbstractTask#doTask()
	 */
	@Override
	protected void doTask() {
		if (log.isInfoEnabled()) {
			log.info("#############查询记账失败的deal信息###########");
		}
		List<BalanceDealDto> balanceDealDtos = this.queryBalanceChargeupWithEnum(ChargeUpStatusEnum.FAIL);
		List<BalanceEntryDto> balanceEntryDtos = null;
		for (BalanceDealDto balanceDealDto : balanceDealDtos) {
			// 查询对应的分录信息
			String serialNo = balanceDealDto.getOrderId();
			Integer dealCode = balanceDealDto.getDealCode();
			Long voucherCode = balanceDealDto.getVoucherCode();
			if (log.isInfoEnabled()) {
				this.log.info("订单号 order_id [" + serialNo + "] 交易号 deal_code [" + dealCode + "] voucherCode ["+ voucherCode +"] 记账开始补账");
				this.log.info("=================================================");
				this.log.info("获取单个的交易信息[" + balanceDealDto.toString() + "]");
				this.log.info("=====================================================");

			}
			balanceEntryDtos = this.queryBalanceEntryInfo(serialNo, dealCode,voucherCode);
			// 组装记账对象
			PaymentResponseDto calFeeReponse = this.makeUpCalFeeReponse(balanceDealDto, balanceEntryDtos);
			this.callPEChargeup(calFeeReponse, serialNo, dealCode);
			

		}

	}

	private void callPEChargeup(PaymentResponseDto calFeeReponse, String serialNo, Integer dealCode) {
		boolean result = false;
		try {
			result = this.peServiceFacade.accountCallFeeReponse(calFeeReponse);
		} catch (Exception e) {
			if (log.isErrorEnabled()) {
				log.error("订单号 order_id [" + serialNo + "] 交易号 deal_code [" + dealCode + "] 记账失败", e);
			}
		}
		if (result) {
			try {
				this.handlerUpdateDealChargeupStatus(serialNo, dealCode, ChargeUpStatusEnum.SUCCESS);
			} catch (Exception e) {
				if (log.isErrorEnabled()) {
					log.error("订单号 order_id [" + serialNo + "] 交易号 deal_code [" + dealCode + "] 记账状态更新失败", e);
				}
			}
		}else{
			if(log.isInfoEnabled()){
				log.info("订单号 orderId 为【" + serialNo + "】，交易号deal_code为【" + dealCode + "】记账失败");
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.ma.task.impl.AbstractTask#getTaskName()
	 */
	@Override
	protected String getTaskName() {
		return "补账";
	}

	/**
	 * @param peServiceFacade
	 *            the peServiceFacade to set
	 */
	public void setPeServiceFacade(PEServiceFacade peServiceFacade) {
		this.peServiceFacade = peServiceFacade;
	}

}
