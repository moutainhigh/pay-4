/**
 *  modifyHistory:
 *  2016-08-09 nico.shao 增加了voucherCode 
 */
package com.pay.ma.task.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.jms.sender.JmsSender;
import com.pay.ma.chargeup.dto.BalanceDealDto;
import com.pay.ma.chargeup.dto.BalanceEntryDto;
import com.pay.ma.commmon.ChargeUpStatusEnum;
import com.pay.pe.service.PaymentResponseDto;

/**
 * 补发记账信息task
 * @author Administrator
 *
 */
public class ChargeupTaskSendMsgImpl extends AbstractChargeupTask {
	
	private Log log = LogFactory.getLog(ChargeupTaskSendMsgImpl.class);
	
	private JmsSender jmsSender;

	/* (non-Javadoc)
	 * @see com.pay.ma.task.impl.AbstractTask#doTask()
	 */
	@Override
	protected void doTask() {
		//查询没有发mq交易deal信息
		if(log.isInfoEnabled()){
			log.info("#############查询发mq失败的deal信息###########");
		}
		//查询没有发mq交易信息
		List<BalanceDealDto> balanceDealDtos = this.queryBalanceChargeupWithEnum(ChargeUpStatusEnum.CHARGEUP_NO_SEND_MQ);
		//查询3个小时没有记账交易信息进行记账
		List<BalanceDealDto> lists =this.queryBalanceChargeupWithEnum(ChargeUpStatusEnum.CHARGEUP_SEND_MQ);
		balanceDealDtos.addAll(lists);
		List<BalanceEntryDto> balanceEntryDtos = null;
		for (BalanceDealDto balanceDealDto : balanceDealDtos) {
			// 查询对应的分录信息
			String serialNo = balanceDealDto.getOrderId();
			Integer dealCode = balanceDealDto.getDealCode();
			Long voucherCode = balanceDealDto.getVoucherCode();   //2016-08-09
			if (log.isInfoEnabled()) {
				this.log.info("订单号 order_id [" + serialNo + "] 交易号 deal_code [" + dealCode + "] voucherCode ["+ voucherCode +"]记账发mq");
				this.log.info("=================================================");
				this.log.info("获取单个的交易信息[" + balanceDealDto.toString() + "]");
				this.log.info("=====================================================");
				
			}
			balanceEntryDtos = this.queryBalanceEntryInfo(serialNo, dealCode,voucherCode);
			// 组装记账对象
			PaymentResponseDto calFeeReponse = this.makeUpCalFeeReponse(balanceDealDto, balanceEntryDtos);
			this.log.info("voucherCode =========["+calFeeReponse.getVoucherCode()+"]");
			this.jmsSender.send("acc.chargeUpMessage", calFeeReponse);
		}

	}
	
	

	/**
	 * @param jmsSender the jmsSender to set
	 */
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}



	/* (non-Javadoc)
	 * @see com.pay.ma.task.impl.AbstractTask#getTaskName()
	 */
	@Override
	protected String getTaskName() {
		// TODO Auto-generated method stub
		return "补mq记账信息";
	}

}
