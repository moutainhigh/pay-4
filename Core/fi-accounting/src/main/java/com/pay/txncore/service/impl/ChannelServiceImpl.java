/**
 * modify history
 * 2016-06-25 nico.shao 增加了交易币种的检查
 * 2016-07-26 nico.shao 修改了退款金额的检查，原来的范围是一元，改为1厘 
 * 2016-08-05 nico.shao 修改了渠道订单的记账标志
 * 		 '对账标志 0:未对账 1:对账成功 2:对账失败 4:对账成功(记账失败) 8:补单';
 */
package com.pay.txncore.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.jms.sender.JmsSender;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.txncore.commons.ChannelOrderStatusEnum;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.crosspay.service.OrgExchangeRateService;
import com.pay.txncore.dao.RefundOrderDAO;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ReconcileImportRecordBatchDto;
import com.pay.txncore.dto.ReconcileImportRecordDetailDto;
import com.pay.txncore.dto.ReconciliationDto;
import com.pay.txncore.model.OrgCurrencyExchangeRate;
import com.pay.txncore.model.RefundOrder;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.txncore.service.ChannelService;
import com.pay.txncore.service.ReconcileRecordService;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class ChannelServiceImpl implements ChannelService {

	private final Log logger = LogFactory.getLog(getClass());
	private ChannelOrderService channelOrderService;
	private OrgExchangeRateService orgExchangeRateService;
	MathContext mc = new MathContext(4, RoundingMode.HALF_DOWN);
	// 对账成功记账
	private AccountingService accounting_100_101;
	private AccountingService accounting_100_102;
	private Map<String, AccountingService> reconciliationAccountingServiceMap;
	private CurrencyRateService currencyRateService;
	private JmsSender jmsSender;
	private ReconcileRecordService reconcileRecordService;
	private RefundOrderDAO refundOrderDAO;
	
	@Override
	public ChannelOrderDTO reconciliationRnTx(String startDate, String endDate,
			ReconciliationDto reconciliationDto,String[] settlementCurrencyCodes) throws BusinessException {
		
		ChannelOrderDTO channelOrderDTO = new ChannelOrderDTO();
		
		if (null == reconciliationDto) {
			channelOrderDTO.setReconciliationFlg(2);
			channelOrderDTO.setErrorMsg("对账对象不对为空");
			return channelOrderDTO;
		}

		String channelOrderNo = reconciliationDto.getChannelOrderNo();

		channelOrderDTO.setTransFee(reconciliationDto.getTransFee());
		channelOrderDTO.setSettAmount(reconciliationDto.getSettAmount());
		channelOrderDTO.setSettleDate(reconciliationDto.getSettleDate());
		channelOrderDTO.setChannelOrderNo(Long.valueOf(reconciliationDto.getChannelOrderNo()));
		
		// 判断状态
		String resultCode = reconciliationDto.getResultCode();
		if (!ResponseCodeEnum.SUCCESS.getCode().equals(resultCode)) {
			if (logger.isInfoEnabled()) {
				logger.info("reconciliationDto not success,so continue ..."
						+ reconciliationDto.getChannelOrderNo());
			}
//			throw new BusinessException("对账记录状态不为成功",
//					ExceptionCodeEnum.DEPOSIT_BATCH_REPAIR_ERROR002);
			channelOrderDTO.setReconciliationFlg(2);
			channelOrderDTO.setErrorMsg("对账记录状态不为成功");
			return channelOrderDTO;
		}

		if (StringUtil.isEmpty(channelOrderNo)) {
			if (logger.isInfoEnabled()) {
				logger.info("channelOrderNo is null , so continue ..."
						+ reconciliationDto.getChannelOrderNo());
			}
			channelOrderDTO.setReconciliationFlg(2);
			channelOrderDTO.setErrorMsg("对账记录渠道订单号为空");
			return channelOrderDTO;
//			throw new BusinessException("对账记录渠道订单号为空",
//					ExceptionCodeEnum.DEPOSIT_BATCH_REPAIR_ERROR002);
		}

		/*
		String[] settlementCurrencyCodes = reconciliationDto
				.getSettlementCurrencyCodes();
		*/
		
		if (null == settlementCurrencyCodes) {
			if (logger.isInfoEnabled()) {
				logger.info("CurrencyCodeEnum is null , so continue ..."
						+ reconciliationDto.getChannelOrderNo());
			}
			channelOrderDTO.setReconciliationFlg(2);
			channelOrderDTO.setErrorMsg("未选择结算币种");
			return channelOrderDTO;
//			throw new BusinessException("未选择结算币种",
//					ExceptionCodeEnum.DEPOSIT_BATCH_REPAIR_ERROR002);
		}

//		String channelCode = reconciliationDto.getChannelCode();
//		if(ChannelItemOrgCodeEnum.ABC.getCode().equals(channelCode)){
//			channelOrderDTO = channelOrderService
//					.queryByOrgCodeAndSerialNo(channelCode,channelOrderNo);
//		}else{
//			channelOrderDTO = channelOrderService
//					.queryByChannelOrderNo(Long.valueOf(channelOrderNo));
//		}

//		String dealAmount = reconciliationDto.getDealAmount();
//		String merchantOrderId = channelOrderDTO.getOrderId();
//		String channelFee = reconciliationDto.getTransFee();
		
		channelOrderDTO = channelOrderService
				.queryByChannelOrderNo(Long.valueOf(channelOrderNo));
		
		logger.info("渠道订单号："+channelOrderNo);
		if (null == channelOrderDTO) {
				channelOrderDTO = new ChannelOrderDTO();
				channelOrderDTO.setReconciliationFlg(2);
				channelOrderDTO.setChannelOrderNo(Long.valueOf(reconciliationDto.getChannelOrderNo()));
				channelOrderDTO.setErrorMsg("系统渠道订单或退款订单不存在");
				return channelOrderDTO;
		}
		
		String dealAmount = reconciliationDto.getDealAmount();
		String merchantOrderId = channelOrderDTO.getOrderId();
		String channelFee = reconciliationDto.getTransFee();
		
		channelOrderDTO.setTransFee(reconciliationDto.getTransFee());
		channelOrderDTO.setSettAmount(reconciliationDto.getSettAmount());
		channelOrderDTO.setSettleDate(reconciliationDto.getSettleDate());
		
		//BeanUtils.copyProperties(channelOrderDTO1, channelOrderDTO);
		if (channelOrderDTO.getReconciliationFlg() == 1) {
			channelOrderDTO.setErrorMsg("该笔交易已对账");
			channelOrderDTO.setReconciliationFlg(2);
			return channelOrderDTO;
//			throw new BusinessException("该笔交易已对账",
//					ExceptionCodeEnum.DEPOSIT_BATCH_REPAIR_ERROR002);
		}
		
		//modify by nico.shao 2016-07-27 这里包括一些预授权状态的订单，也全部都不能对账了
		//if (channelOrderDTO.getStatus() == 0) { 
		if (channelOrderDTO.getStatus() != ChannelOrderStatusEnum.SUCCESS.getCode()) {
			if(channelOrderDTO.getStatus()==ChannelOrderStatusEnum.FAIL.getCode()){
				channelOrderDTO.setErrorMsg("渠道订单是失败的，需要补单");
			}
			else {
				channelOrderDTO.setErrorMsg("渠道订单未成功，需要补单");
			}
			channelOrderDTO.setReconciliationFlg(2);
			return channelOrderDTO;
//			throw new BusinessException("渠道订单未成功",
//					ExceptionCodeEnum.DEPOSIT_BATCH_REPAIR_ERROR002);
		}
		
		// 判断金额
		Long payAmount = channelOrderDTO.getPayAmount();
		
		BigDecimal dAmt = new BigDecimal(dealAmount).multiply(new BigDecimal("100"));
		if (Math.abs(payAmount/10 - dAmt.longValue()) > 1) {
			
			logger.error(channelOrderNo + ",payAmount:"+payAmount/10 +",dAmt:" + dAmt);
			channelOrderDTO.setErrorMsg("金额不匹配");
			channelOrderDTO.setReconciliationFlg(2);
			return channelOrderDTO;
//			throw new BusinessException("金额不匹配",
//					ExceptionCodeEnum.DEPOSIT_BATCH_REPAIR_ERROR002);
		}
		
		//2016-06-25 交易币种检查
		{
			String tranCurrencyCode = reconciliationDto.getTransCurrency();
			if(!StringUtil.isEmpty(tranCurrencyCode)){
				if(!tranCurrencyCode.equals(channelOrderDTO.getTransferCurrencyCode() )){
					logger.error(channelOrderNo + ", 对账单交易币种"+tranCurrencyCode +", 渠道订单交易币种" 
								+ channelOrderDTO.getTransferCurrencyCode());
					channelOrderDTO.setErrorMsg("交易币种不匹配");
					channelOrderDTO.setReconciliationFlg(2);
					return channelOrderDTO;
				}
			}
		}
		//end 2016-06-25
		
		String settlementCurrencyCode = getSettlementCurrencyCode(
				channelOrderDTO, settlementCurrencyCodes);

		String orgCode = channelOrderDTO.getOrgCode();
		String orderId = channelOrderDTO.getChannelOrderNo() + "";
		Long amount = channelOrderDTO.getPayAmount();

		String rate = "1";
		logger.info("###orgCode="+orgCode+",orderId="+orderId+",amount="+amount);
		// Credorax
		/**
		 * 1 交易币种是X，结算币种是EUR, 那么credorax给我们的汇率就是：（X兑欧元的标志汇率）/(1+1%) ， 2
		 * 交易币种X,结算币种是Y, 那么credorax给我们的汇率就是：（Y兑欧元的标准汇率/X兑欧元的标准汇率）/(1+1%)
		 */
		if (ChannelItemOrgCodeEnum.CREDORAX.getCode().equals(orgCode)) {
			// 结算成欧元
			if (CurrencyCodeEnum.EUR.getCode().equals(settlementCurrencyCode)) {
				OrgCurrencyExchangeRate currencyExchangeRate = orgExchangeRateService
						.findCurrentCurrencyRate(orgCode,
								channelOrderDTO.getCurrencyCode(),
								settlementCurrencyCode, new Date());

				if (logger.isInfoEnabled()) {
					logger.info("orgCode-10075001,get rate:"
							+ currencyExchangeRate.getExchangeRate());
				}
				rate = currencyExchangeRate.getExchangeRate();
				// 只有交易币种与结算币种不一致才收1%的 mark up
				if (!channelOrderDTO.getTransferCurrencyCode().equals(
						settlementCurrencyCode)) {
					BigDecimal tempRate = new BigDecimal(
							currencyExchangeRate.getExchangeRate()).divide(
							new BigDecimal("1.01"), mc);

					if (logger.isInfoEnabled()) {
						logger.info("calar rate:" + tempRate);
					}
					rate = tempRate.toString();
				}

			} else {

				// 只有交易币种与结算币种不一致才收1%的 mark up
				if (!channelOrderDTO.getTransferCurrencyCode().equals(
						settlementCurrencyCode)) {
					// 结算非欧元
					OrgCurrencyExchangeRate yExchangeRate = orgExchangeRateService
							.findCurrentCurrencyRate(orgCode,
									settlementCurrencyCode,
									CurrencyCodeEnum.EUR.getCode(), new Date());
					
					if (null == yExchangeRate) {
						channelOrderDTO.setErrorMsg("未找到结算币种对欧元汇率,结算币种-" + settlementCurrencyCode + "," + channelOrderNo);
						channelOrderDTO.setReconciliationFlg(2);
						return channelOrderDTO;
					}
					
					OrgCurrencyExchangeRate xExchangeRate = orgExchangeRateService
							.findCurrentCurrencyRate(orgCode,
									channelOrderDTO.getTransferCurrencyCode(),
									CurrencyCodeEnum.EUR.getCode(), new Date());
					
					if (null == xExchangeRate) {
						channelOrderDTO.setErrorMsg("未找到结算币种对欧元汇率,交易币种-" + channelOrderDTO.getTransferCurrencyCode() + "," + channelOrderNo);
						channelOrderDTO.setReconciliationFlg(2);
						return channelOrderDTO;
					}
					
					BigDecimal tempRate = new BigDecimal(
							yExchangeRate.getExchangeRate()).divide(
							new BigDecimal(xExchangeRate.getExchangeRate()))
							.divide(new BigDecimal("1.01"), mc);
					rate = tempRate.toString();
				}

			}

		} else if (ChannelItemOrgCodeEnum.BOCS.getCode().equals(orgCode)||ChannelItemOrgCodeEnum.BOCM.getCode().equals(orgCode)|| ChannelItemOrgCodeEnum.BOCI.getCode().equals(orgCode) ) {// 中银卡司,中银MOTO，中银MIGS

			String settlementRate = reconciliationDto.getSettlementRate();
			if (!StringUtil.isEmpty(settlementRate)) {
				logger.error("未找到汇率.....");
				rate = settlementRate;
			} else if (channelOrderDTO.getCurrencyCode().equalsIgnoreCase(
					settlementCurrencyCode)) {
				rate = "1";
			}

		} else if (ChannelItemOrgCodeEnum.ABC.getCode().equals(orgCode)||ChannelItemOrgCodeEnum.CYBSCTV.getCode().equals(orgCode)) {//农行CTV获取汇率 by tom.wang 2016年5月24日10:18:54
			String settlementRate = reconciliationDto.getSettlementRate();
			rate = settlementRate;
			
		} else if(orgCode.equals("10077004")){	//新生支付
			//logger.info("新生支付渠道 对账");
			//rate = "1";
		}else if (ChannelItemOrgCodeEnum.BELTO.getCode().equals(orgCode)) {//boleto获取汇率 by tom.wang
			String settlementRate = reconciliationDto.getSettlementRate();
			if (!StringUtil.isEmpty(settlementRate)) {
				logger.error("boleto未找到汇率.....");
				rate = settlementRate;
			} else if (channelOrderDTO.getCurrencyCode().equalsIgnoreCase(
					settlementCurrencyCode)) {
				rate = "1";
			}
		}
		//设置Computop结算汇率 by davis.guo at 2016-08-02
		else if (ChannelItemOrgCodeEnum.CT_BOLETO.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_SAFETYPAY.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_DirectDebitsSSL.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_SofortBanking.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_EPS.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_Giropay.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_PagBrasilDebitCard.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_PagBrasilOTF.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_Poli.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_Przelewy24.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_Qiwi.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_SEPA.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_Teleingreso.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_TrustPay.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.CT_iDeal.getCode().equals(orgCode)) {
			String settlementRate = reconciliationDto.getSettlementRate();
			//设置结算汇率
			if (!StringUtil.isEmpty(settlementRate)) {
				rate = settlementRate;
			} else if (channelOrderDTO.getCurrencyCode().equalsIgnoreCase(
					settlementCurrencyCode)) {
				rate = "1";
			}
		}
		//设置前海成融结算汇率 by davis.guo at 2016-08-14
		else if (ChannelItemOrgCodeEnum.QHWR.getCode().equals(orgCode)) {
			String settlementRate = reconciliationDto.getSettlementRate();
			if (!StringUtil.isEmpty(settlementRate)) {
				logger.error("前海成融未找到汇率.....");
				rate = settlementRate;
			} else if (channelOrderDTO.getCurrencyCode().equalsIgnoreCase(
					settlementCurrencyCode)) {
				rate = "1";
			}
		}
		else{
			if (!channelOrderDTO.getCurrencyCode().equals(
					settlementCurrencyCode)) {
				OrgCurrencyExchangeRate currencyExchangeRate = orgExchangeRateService
						.findCurrentCurrencyRate(orgCode,
								channelOrderDTO.getCurrencyCode(),
								settlementCurrencyCode, new Date());
				if (null == currencyExchangeRate) {

					if (logger.isInfoEnabled()) {
						logger.info("CurrencyCodeEnum is null , so continue ..."
								+ reconciliationDto.getChannelOrderNo());
					}

					logger.info("未找到对应结算汇率，以人民币为基础，转换汇率.........");
					currencyExchangeRate = orgExchangeRateService
							.findCurrentCurrencyRate(orgCode,
									channelOrderDTO.getCurrencyCode(),
									CurrencyCodeEnum.CNY.getCode(), new Date());
					OrgCurrencyExchangeRate settlementCurrencyExchangeRate = orgExchangeRateService
							.findCurrentCurrencyRate(orgCode,
									settlementCurrencyCode,
									CurrencyCodeEnum.CNY.getCode(), new Date());

					if (null == currencyExchangeRate
							|| null == settlementCurrencyExchangeRate) {
						logger.info("未找到汇率.....");
						channelOrderDTO.setErrorMsg("没有找到汇率");
						channelOrderDTO.setReconciliationFlg(2);
						return channelOrderDTO;
//						throw new BusinessException("没有找到汇率",
//								ExceptionCodeEnum.DEPOSIT_BATCH_REPAIR_ERROR002);
					}

					if (logger.isInfoEnabled()) {
						logger.info("currency ExchangeRate rate:"
								+ currencyExchangeRate.getExchangeRate()
								+ ",settlement Currency ExchangeRate:"
								+ settlementCurrencyExchangeRate
										.getExchangeRate());
					}

					rate = new BigDecimal(
							settlementCurrencyExchangeRate.getExchangeRate())
							.divide(new BigDecimal(
									currencyExchangeRate.getExchangeRate()), mc)
							.toString();

				} else {
					rate = currencyExchangeRate.getExchangeRate();
				}
			}
		}

		channelOrderDTO.setReconciliationFlg(1);
		channelOrderDTO.setSettlementCurrencyCode(settlementCurrencyCode);
		channelOrderDTO.setSettlementRate(rate);
		//只有成功状态的订单，才会更新这个状态
		boolean updateFlg = channelOrderService.updateChannelProtocolRnTx(
				channelOrderDTO, ChannelOrderStatusEnum.SUCCESS.getCode());

		logger.info("channel order update result:" + updateFlg
				+ ",channelOrderNo:" + channelOrderNo + ",settlementCurrentyCode=" + settlementCurrencyCode);

		// 记账处理
		if (updateFlg) {
			try {

//				if (ChannelItemOrgCodeEnum.BOC.getCode().equals(channelOrderDTO.getOrgCode()) 
//						|| ChannelItemOrgCodeEnum.CREDORAX.getCode().equals(channelOrderDTO.getOrgCode())) {
//
//					logger.info("开始登记 101 102  记账 --" + channelOrderNo);
//					doAccounting_100_101(orgCode, orderId,
//							channelOrderDTO.getAmount(),
//							channelOrderDTO.getCurrencyCode());
//
//					doAccounting_100_102(orgCode, orderId,
//							channelOrderDTO.getPayAmount(),
//							settlementCurrencyCode, rate);
//				}

				AccountingService rAccountingService = reconciliationAccountingServiceMap
						.get(orgCode);
				if(rAccountingService == null)
				{
					//add by davis.guo at 2016-08-03
					logger.error("do channel accounting error, rAccountingService is null " );
					//channelOrderDTO.setReconciliationFlg(2);
					channelOrderDTO.setReconciliationFlg(4);		//2016-08-04 
					channelOrderService.updateChannelProtocolRnTx(channelOrderDTO);
					channelOrderDTO.setErrorCode(ExceptionCodeEnum.UN_KNOWN_EXCEPTION.getCode());
					channelOrderDTO.setErrorMsg("记账失败:未配置"+orgCode+"渠道对应记账规则！");
					return channelOrderDTO;
				}
				doReconciliationAccounting(rAccountingService, orgCode,
						orderId, amount, settlementCurrencyCode, rate, dealAmount,channelFee,merchantOrderId);
				
				//如果是农行对账，且该笔发生过退款，去做退款509&510销账
//				try {
//					
//					if(ChannelItemOrgCodeEnum.ABC.getCode().equals(orgCode)){
//						RefundOrderDTO refundOrderDTO = refundOrderService
//								.queryRefundOrderByPaymentOrder(
//										channelOrderDTO.getPaymentOrderNo(),
//										RefundStatusEnum.SUCCESS.getCode());
//						if (null != refundOrderDTO) {
//							refundService.doAbcAccounting(orgCode,
//									refundOrderDTO.getRefundOrderNo(),
//									refundOrderDTO.getRefundAmount(),
//									channelOrderDTO.getCurrencyCode(),
//									settlementCurrencyCode,
//									channelOrderDTO.getSettlementRate());
//						}
//					}
//					
//				} catch (Exception e) {
//					logger.error("做农行退款销账失败，channelOrderNo:" + channelOrderNo, e);
//				}

			} catch (Exception e) {
				logger.error("do channel accounting error:" + channelOrderNo, e);
				//channelOrderDTO.setReconciliationFlg(2);
				channelOrderDTO.setReconciliationFlg(4);		//2016-08-04 
				channelOrderService.updateChannelProtocolRnTx(channelOrderDTO);
				channelOrderDTO.setErrorCode(ExceptionCodeEnum.UN_KNOWN_EXCEPTION.getCode());
				channelOrderDTO.setErrorMsg(ExceptionCodeEnum.UN_KNOWN_EXCEPTION.getDescription());
				channelOrderDTO.setErrorMsg("记账失败:"+e.getMessage());//"记账失败" modify by davis.guo at 2016-08-02
				return channelOrderDTO;
			}
		}
		
		return channelOrderDTO;
	}

	@Override
	public RefundOrder reconciliationRefundRnTx(String startDate, String endDate,
			ReconciliationDto reconciliationDto) throws BusinessException {

		RefundOrder refundOrder = new RefundOrder();

		String refundOrderNo = reconciliationDto.getChannelOrderNo();


		// 判断状态
		String resultCode = reconciliationDto.getResultCode();
		if (!ResponseCodeEnum.SUCCESS.getCode().equals(resultCode)) {
			if (logger.isInfoEnabled()) {
				logger.info("reconciliationDto not success,so continue ..."
						+ reconciliationDto.getChannelOrderNo());
			}
			refundOrder.setReconciliationFlg(2);
			refundOrder.setErrorMsg("对账记录状态不为成功");
			return refundOrder;
		}

		/*
		String[] settlementCurrencyCodes = reconciliationDto.getSettlementCurrencyCodes();
		if (null == settlementCurrencyCodes) {
			if (logger.isInfoEnabled()) {
				logger.info("CurrencyCodeEnum is null , so continue ..."
						+ reconciliationDto.getChannelOrderNo());
			}
			refundOrder.setReconciliationFlg(2);
			refundOrder.setErrorMsg("未选择结算币种");
			return refundOrder;
		}
		*/

		String dealAmount = reconciliationDto.getDealAmount();
		refundOrder = (RefundOrder) refundOrderDAO
				.findById(Long.valueOf(refundOrderNo));

		// 查询退款订单对应的渠道订单
		if (null == refundOrder) {
			refundOrder = new RefundOrder();
			refundOrder.setReconciliationFlg(2);
			refundOrder.setErrorMsg("未找到对应的退款订单refundOrderNo=" + refundOrderNo);
			refundOrder.setRefundOrderNo(Long.valueOf(refundOrderNo));
			return refundOrder;

		}
		if (null == refundOrder.getPaymentOrderNo()) {
			refundOrder.setReconciliationFlg(2);
			refundOrder.setErrorMsg("未找到该退款订单对应的支付订单号，refundOrderNo=" + refundOrderNo);
			return refundOrder;

		}
		Long paymentOrderNo = refundOrder.getPaymentOrderNo();
		ChannelOrderDTO channelOrderDTO = channelOrderService.queryByTradeOrderNo(paymentOrderNo);

		if (null == channelOrderDTO) {
			refundOrder.setReconciliationFlg(2);
			refundOrder.setErrorMsg("未找到该退款订单对应的渠道订单，refundOrderNo=" + refundOrderNo);
			refundOrder.setRefundOrderNo(Long.valueOf(refundOrderNo));
			return refundOrder;

		}

		channelOrderDTO.setTransFee(reconciliationDto.getTransFee());
		channelOrderDTO.setSettAmount(reconciliationDto.getSettAmount());
		channelOrderDTO.setSettleDate(reconciliationDto.getSettleDate());

		if (refundOrder.getReconciliationFlg() == 1) {
			refundOrder.setErrorMsg("该笔交易已对账");
			refundOrder.setReconciliationFlg(2);
			return refundOrder;
		}
		// 2为退款成功，只有退款成功才能对账
		if (!refundOrder.getStatus().equals("2")) {
			refundOrder.setErrorMsg("退款订单未成功，可能风控审核未通过！");//渠道订单未成功 modified by davis.guo at 2016-08-26
			refundOrder.setReconciliationFlg(2);
			return refundOrder;
		}

		// 支付汇率  这段代码的范围是1元，我们还留着，以防止更大的意外，比如退款时算错了。 
		String transferRate = channelOrderDTO.getTransferRate();
		Long refundAmount = refundOrder.getRefundAmount();
		BigDecimal reconAmt = new BigDecimal(refundAmount).divide(new BigDecimal("1000")).multiply(new BigDecimal(transferRate));
		BigDecimal dAmt = new BigDecimal(dealAmount).abs();
		// add by mmzhang 农行对账单退款金额为正其他渠道都为负数，这里要进行处理
		if (Math.abs(reconAmt.longValue() - dAmt.longValue()) > 1) {

			logger.error(refundOrderNo + ",reconAmt:" + reconAmt.longValue() + ",dAmt:"
 + dAmt
					+ ",transferRate:" + transferRate);
			refundOrder.setErrorMsg("金额不匹配");
			refundOrder.setReconciliationFlg(2);
			return refundOrder;
		}
		
		//add by nico.shao 2016-07-26	
		Long transferAmount = refundOrder.getTransferAmount();	
		BigDecimal tAmt = dAmt.multiply(new BigDecimal("1000"));//dAmt的单位是元，所以要进行转换modified by davis.guo at 2016-08-04
		if (Math.abs(transferAmount - tAmt.longValue()) > 10) {//银行是以分为单位的，所以要用10来判断，不能用1作判断。
			logger.error(refundOrderNo + ",transAmount:" + transferAmount  + ",dAmt:" + dAmt);
			refundOrder.setErrorMsg("金额不匹配");
			refundOrder.setReconciliationFlg(2);
			return refundOrder;
		}
		//end nico.shao 2016-07-26
		

		refundOrder.setReconciliationFlg(1);
		boolean updateFlg = refundOrderDAO.updateRefundReconciliationFlg(refundOrderNo,
				ChannelOrderStatusEnum.SUCCESS.getCode());

		logger.info("channel order update result:" + updateFlg + ",refundOrderNo:" + refundOrderNo);

		return refundOrder;
	}

	public void setChannelOrderService(ChannelOrderService channelOrderService) {
		this.channelOrderService = channelOrderService;
	}

	public void setOrgExchangeRateService(
			OrgExchangeRateService orgExchangeRateService) {
		this.orgExchangeRateService = orgExchangeRateService;
	}

	public void setAccounting_100_101(AccountingService accounting_100_101) {
		this.accounting_100_101 = accounting_100_101;
	}

	public void setAccounting_100_102(AccountingService accounting_100_102) {
		this.accounting_100_102 = accounting_100_102;
	}

	public void setRefundOrderDAO(RefundOrderDAO refundOrderDAO) {
		this.refundOrderDAO = refundOrderDAO;
	}

	public void setReconciliationAccountingServiceMap(
			Map<String, AccountingService> reconciliationAccountingServiceMap) {
		this.reconciliationAccountingServiceMap = reconciliationAccountingServiceMap;
	}
	
	public void doAccounting_100_101(String orgCode, String orderId,
			Long amount, String currencyCode,String merchantOrderId) throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);

		accounting_100_101.doAccounting(accountingDto);
	}

	public void doAccounting_100_102(String orgCode, String orderId,
			Long amount, String currencyCode, String rate,String merchantOrderId) throws Exception {

		BigDecimal aAmount = new BigDecimal(amount).multiply(new BigDecimal(
				rate));
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(aAmount);
		accountingDto.setOrderAmount(aAmount);
		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_100_102.doAccounting(accountingDto);
	}

	public void doReconciliationAccounting(AccountingService accountingService,
			String orgCode, String orderId, Long amount, String currencyCode,
			String rate,String dealAmount,String channelFee,String merchantOrderId) throws Exception {

		Long fee = 0L;
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		
		//农行订制处理
		
		
		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setAmount(amount);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setOrderAmount(amount);
		
		if(ChannelItemOrgCodeEnum.ABC.getCode().equals(orgCode)){
			amount = new BigDecimal(dealAmount).multiply(new BigDecimal("1000")).longValue();
			fee = new BigDecimal(channelFee).multiply(new BigDecimal("1000")).longValue();
			accountingDto.setAmount(amount);
			accountingDto.setOrderAmount(amount);
		}else{
			AccountingFeeRes accountingFeeRes = accountingService
					.caculateFee(accountingDto);
			
			if (null != accountingFeeRes) {
				fee = accountingFeeRes.getPayeeFee();
				logger.info("fee:" + fee);
			}
		}
		//add by mack 
	   logger.info("fee:" + fee+",amount="+amount);
		BigDecimal dAmount = new BigDecimal(amount - fee)
				.multiply(new BigDecimal(rate));
		BigDecimal fAmount = new BigDecimal(fee).multiply(new BigDecimal(rate));

		accountingDto.setHasCaculatedPrice(true);
		accountingDto.setPayeeFee(fAmount.longValue());
		accountingDto.setAmount(dAmount);
		accountingDto.setOrderAmount(dAmount);

		logger.info("begin do accounting :" + orderId);
		accountingService.doAccounting(accountingDto);
	}

	private String getSettlementCurrencyCode(ChannelOrderDTO channelOrder,
			String[] settlementCurrencys) {

		if (null == settlementCurrencys || settlementCurrencys.length == 0) {
			return CurrencyCodeEnum.USD.getCode();
		}

		for (String settlementCurrency : settlementCurrencys) {
			if (channelOrder.getTransferCurrencyCode().equals(settlementCurrency)) {
				return settlementCurrency;
			}
		}

		if (settlementCurrencys.length == 1) {
			return settlementCurrencys[0];
		} else {
			return CurrencyCodeEnum.USD.getCode();
		}
	}

	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	@Override
	public CurrencyRateService getCurrencyRateService() {
		return currencyRateService;
	}
	
	@Override
	public JmsSender getJmsSender() {
		return jmsSender;
	}
	
	
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
	
	@Override
	public String insertReconcileImportRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch) {
		return reconcileRecordService.insertReconcileImportRecordBatch(importRecordBatch);
	}
	@Override
	public  void insertReconcileImportRecordDetailDto(
			List<ReconcileImportRecordDetailDto> detailDtos) {
		 reconcileRecordService.insertReconcileImportRecordDetailDto(detailDtos);
	}
	@Override
	public void updateReconcileRecordBatch(
			ReconcileImportRecordBatchDto importRecordBatch) {
		reconcileRecordService.updateReconcileRecordBatch(importRecordBatch);
	}
	@Override
	public void updateReconcileRecordDetail(
			List<ReconcileImportRecordDetailDto> detailDtos) {
		reconcileRecordService.updateReconcileRecordDetail(detailDtos);
	}
	
	public void setReconcileRecordService(
			ReconcileRecordService reconcileRecordService) {
		this.reconcileRecordService = reconcileRecordService;
	}

	@Override
	public List<ChannelOrderDTO> queryChannelOrder(
			ChannelOrderDTO channelOrderDTO) {
		
		return  channelOrderService.queryChannelOrder(channelOrderDTO);
	}
}
