package com.pay.txncore.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.pay.acc.rate.dto.MerchantRateDto;
import com.pay.acc.rate.service.MerchantRateService;
import com.pay.cardbin.model.CardBinInfo;
import com.pay.cardbin.service.CardBinInfoService;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.fi.helper.ChannelItemPriceStrategyEnum;
import com.pay.fi.helper.CredoraxCurrencyCodeEnum;
import com.pay.pricingstrategy.model.PricingStrategyDetailReport;
import com.pay.pricingstrategy.service.PricingStrategyService;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.dao.PaymentOrderExpandDAO;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.dto.ReconcileImportRecordDetailDto;
import com.pay.txncore.model.ChannelOrderReport;
import com.pay.txncore.model.PaymentOrderExpand;
import com.pay.txncore.model.SettlementBaseRate;
import com.pay.txncore.model.SettlementRate;
import com.pay.txncore.model.TradeAmountCount;
import com.pay.txncore.model.TranDailyReportVo;
import com.pay.txncore.service.PaymentOrderService;
import com.pay.txncore.service.ReconcileRecordService;
import com.pay.txncore.service.ReportService;
import com.pay.txncore.service.TradeAmountCountService;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.StringUtil;

public class ReportServiceImpl implements ReportService {
	private PricingStrategyService pricingStrategyService;
	private CurrencyRateService currencyRateService;
	private PaymentOrderExpandDAO paymentOrderExpandDAO;
	private CardBinInfoService cardBinInfoService;
	private PaymentOrderService paymentOrderService;
	private MerchantRateService merchantRateService;
	private TradeAmountCountService tradeAmountCountService;
	private ReconcileRecordService reconcileRecordService;

	
	private Log logger = LogFactory.getLog(getClass());

	@Override
	public List<TranDailyReportVo> tranDailyReport(List<ChannelOrderReport> channelOrders) {
		
		//不符合生成日报表数据条件的记录的条数
		Integer unReportCount = 0 ;
		logger.info("开始构造交易日报表数据集合.............................");
		List<TranDailyReportVo> list = new ArrayList<TranDailyReportVo>();
		for (ChannelOrderReport channelOrder : channelOrders) {
			//交易日报表记录备注信息
			StringBuilder sb = new StringBuilder() ;
			//渠道订单号
			Long channelOrderNo = channelOrder.getChannelOrderNo() ;
			logger.info("渠道订单号：" + channelOrderNo + " 开始");
			TranDailyReportVo reportvo = new TranDailyReportVo();
			//交易时间， 渠道订单的交易时间
			reportvo.setCreateDate(channelOrder.getCreateDate());
			//商户订单号
			reportvo.setOrderId(channelOrder.getOrderId());
			//网关订单号
			reportvo.setTradeOrderNo(channelOrder.getTradeOrderNo());
			//支付订单号
			reportvo.setPaymentOrderNo(channelOrder.getPaymentOrderNo());
			//渠道订单号
			reportvo.setChannelOrderNo(channelOrderNo);
			//渠道编号
			String orgCode = channelOrder.getOrgCode() ;
			reportvo.setOrgCode(orgCode);
			//渠道名称
			String channelname = ChannelItemOrgCodeEnum.getChannelItemByCode(orgCode).getDesc();
			reportvo.setOrgName(channelname);
			//会员号
			reportvo.setPartnerId(channelOrder.getPartnerId());
			//交易金额，网关订单中的交易金额
			reportvo.setTranAmount(channelOrder.getTradeOrderAmount());
			//交易币种， 网关订单中的交易币种
			reportvo.setTranCurrencyCode(channelOrder.getTradeCurrencyCode());
			reportvo.setCurrencyCode(channelOrder.getTradeCurrencyCode());
			//支付汇率，渠道订单中的支付汇率
			reportvo.setPayRate(channelOrder.getChannelTransferRate());
			//支付金额，渠道订单中的支付金额
			reportvo.setPayAmount(channelOrder.getChannelPayAmount());
			//支付币种， 渠道订单里的支付币种
			reportvo.setPayCurrencyCode(channelOrder.getTransferCurrencyCode());
			//清算币种， 清算订单中的清算币种
			reportvo.setSettlementCurrencyCode(channelOrder.getSettlementCurrencyCode());
			//清算汇率
			if(org.apache.commons.lang.StringUtils.isNotEmpty(channelOrder.getSettlementRate())){
				reportvo.setSettlementRate(new BigDecimal(channelOrder.getSettlementRate()));
			}else{
				sb.append("清算汇率不存在！") ;
				//清算汇率不存在，不生成交易日报表
				logger.info("渠道订单【"+ channelOrderNo +"的清算汇率不存在 ，不生成交易日报表数据！");
				unReportCount ++ ;
				continue ;
			}
			//手续费费率， 取交易币种兑人民币的清算汇率
			reportvo.setFeeRate(new BigDecimal(channelOrder.getFeeRate()));
			//二级渠道号	
			reportvo.setMerchantNo(channelOrder.getMerchantNo());
			//授权码
			reportvo.setAuthorisation(channelOrder.getAuthorisation());
			//渠道结果
			reportvo.setStatus(1);
			//渠道支付金额
			BigDecimal channelPayAmount = channelOrder.getChannelPayAmount() ;
			reportvo.setBankPayAmount(channelPayAmount);
			//渠道支付币种
			reportvo.setChannelPayCurrencyCode(channelOrder.getTransferCurrencyCode());
			//清算订单号， 不要求在报表中显示
			reportvo.setPartnerSettlementOrderNo(channelOrder.getPartnerSettlementOrderNo());
			//是否对账标志
			Integer reconciliationFlg = channelOrder.getReconciliationFlg() ;
			//===================此处开始计算各类费用Sta================================================
			ReconcileImportRecordDetailDto reconciliationFlag1Dto = null ;
			if(1 == reconciliationFlg){
				logger.info("该笔订单已对账，渠道订单号：【" + channelOrderNo + "】已对账，开始取对账单记录信息..");
				reconciliationFlag1Dto = this.getReconciliationFlag1Dto(channelOrder) ;
				if(null == reconciliationFlag1Dto){
					logger.info("渠道订单【"+channelOrderNo+"】已对账，但是未取到对账明细记录信息.");
				}
			}
			//计算渠道手续费率
			//银行比例手续费 | 银行比例手续费币种 | 银行固定手续费 | 银行固定手续费币种
			//渠道为credorax时
			if (ChannelItemOrgCodeEnum.CREDORAX.getCode().equals(orgCode)) {
				logger.info("CREDORAX的渠道手续费系统上是从对账单上取,未对账时，默认为0值");
				//若已对账，则取RECONCILE_IMPORT_RECORD_DETAIL中的固定费，
				if(1 == reconciliationFlg){
					//对账单数据
					if(null != reconciliationFlag1Dto){
						//credorax的银行比例手续费
						reportvo.setBankPerFee(null == reconciliationFlag1Dto.getFeeErcentage() ? BigDecimal.ZERO : new BigDecimal(reconciliationFlag1Dto.getFeeErcentage()).multiply(new BigDecimal(1000)));
						reportvo.setBankFixedFee(null == reconciliationFlag1Dto.getFpi() ? BigDecimal.ZERO : new BigDecimal(reconciliationFlag1Dto.getFpi()).multiply(new BigDecimal(1000)));
					}
				}else{
					//未对账时，则走对账后异步更新逻辑
					reportvo.setBankFixedFee(BigDecimal.ZERO);
					reportvo.setBankPerFee(BigDecimal.ZERO);
				}
				reportvo.setBankPerCurrencyCode(channelOrder.getSettlementCurrencyCode());
				reportvo.setBankFixedCurrencyCode(channelOrder.getSettlementCurrencyCode());
				
			}else {
				//非credorax的其他渠道时
				ChannelItemPriceStrategyEnum penum = ChannelItemPriceStrategyEnum.getChannelItemByCode(orgCode);
				String paymentSeriviceCode = "";
				if (null == penum || "".equals(penum)) {
					//未配置渠道计费策略时， 不生成交易日报表数据
					logger.info("请配置渠道【" + channelname + "】的计费策略==【" + channelOrderNo +"】不生成交易日报表数据");
					unReportCount ++ ;
					continue;
				} else {
					paymentSeriviceCode = penum.getDesc();
				}
				if (null == paymentSeriviceCode || "".equals(paymentSeriviceCode)) {
					//未配置渠道计费策略时， 不生成交易日报表数据
					logger.info("请配置渠道" + channelname + "的计费策略==【" + channelOrderNo +"】不生成交易日报表数据");
					unReportCount ++ ;
					continue;
				}
				//获取渠道计费策略配置
				List<PricingStrategyDetailReport> detailList = pricingStrategyService.getAllPricingStrategyDetailByPSC(Integer.valueOf(paymentSeriviceCode));
				if (null != detailList && detailList.size() > 1) {
					//当为指定渠道配置了多种渠道计费策略时，不生成交易日报表数据
					logger.info("渠道【" + channelname + "】计费策略配置查询到多个==【" + channelOrderNo +"】不生成交易日报表数据");
					unReportCount ++ ;
					continue;
				}
				//取到符合条件的渠道的产品计费策略配置的配置信息
				PricingStrategyDetailReport detail = detailList.get(0);
				// 银行比例手续费=》取渠道订单支付金额*渠道手续费费率
				BigDecimal bankPerFee = channelOrder.getChannelPayAmount().multiply(detail.getChargeRate().divide(new BigDecimal(10000)));
				reportvo.setBankPerFee(bankPerFee);
				reportvo.setBankPerCurrencyCode(channelOrder.getTransferCurrencyCode());
				//==========此处处理银行固定手续费、银行固定手续费币种Sta====================
				//银行固定手续费
				BigDecimal bankFixedFee = new BigDecimal(0) ;
				String bankFixedCurrencyCode = "" ;
				//卡司固定手续费直接取0.3 CNY
				if(ChannelItemOrgCodeEnum.BOCS.getCode().equals(orgCode)){
					bankFixedFee = new BigDecimal("300") ;
					bankFixedCurrencyCode = "CNY" ;
				}else{
					bankFixedFee = new BigDecimal(0) ;
					bankFixedCurrencyCode = "CNY" ;
				}
				reportvo.setBankFixedFee(bankFixedFee);
				reportvo.setBankFixedCurrencyCode(bankFixedCurrencyCode);
				//==========此处处理银行固定手续费、银行固定手续费币种End====================
				//计算手续费支出
				BigDecimal tranFee = bankFixedFee.add(bankPerFee) ;
				reportvo.setTranFee(tranFee);
			}
			// 农行
//			if (!ChannelItemOrgCodeEnum.ABC.getCode().equals(orgCode) && "DCC".equals(channelOrder.getPayType())) {
//				logger.info("农行dcc的渠道结算汇率在对账单上取！");
//				reportvo.setPayAmount(BigDecimal.ZERO);
//				reportvo.setTranFee(BigDecimal.ZERO);
//				reportvo.setBankAmount(BigDecimal.ZERO);
//				sb.append("农行dcc的渠道结算汇率在对账单上取！") ;
//			} else {
//				Map<String, String> currencyMap = new HashMap<String, String>();
//				currencyMap.put("CAD", "加元");
//				currencyMap.put("BRL", "巴西里亚尔");
//				currencyMap.put("INR", "印尼卢比");
//				currencyMap.put("KRW", "韩元");
//				currencyMap.put("MOP", "澳门元");
//				currencyMap.put("MYR", "林吉特");
//				currencyMap.put("PHP", "菲律宾比索");
//				currencyMap.put("RUB", "卢布");
//				currencyMap.put("THB", "泰国铢");
//				currencyMap.put("TWD", "新台币");
//				if (ChannelItemOrgCodeEnum.CREDORAX.getCode().equals(orgCode) && currencyMap.containsKey(channelOrder.getCurrencyCode())) {
//					logger.info("CREDORAX小币种，渠道用美元结算，和渠道支付币种不一致，计算有误差");
//					SettlementRate credoraxRate = currencyRateService.getSettlementRate(
//							channelOrder.getCurrencyCode(), CurrencyCodeEnum.USD.getCode(), null,
//							String.valueOf(channelOrder.getPartnerId()),
//							channelOrder.getCreateDate());
//					if (null == credoraxRate) {
//						logger.info("未找到对应CREDORAX小币种渠道汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: "
//								+ channelOrder.getCurrencyCode() + " ,targetCurrencyCode: "
//								+ CurrencyCodeEnum.USD.getCode());
//						sb.append("未找到对应CREDORAX小币种渠道汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode:"+ channelOrder.getCurrencyCode() + " ,targetCurrencyCode: "
//								+ CurrencyCodeEnum.USD.getCode()) ;
//						reportvo.setCurrencyCode(channelOrder.getCurrencyCode());
//						reportvo.setPayAmount(channelOrder.getChannelPayAmount());
//					} else {
//						sb.append("CREDORAX小币种，渠道用美元结算，和渠道支付币种不一致，要查询该币种转换为美元的汇率！") ;
//						reportvo.setCurrencyCode(CurrencyCodeEnum.USD.getCode());
//						reportvo.setPayAmount(channelOrder.getChannelPayAmount().multiply(
//								new BigDecimal(credoraxRate.getExchangeRate())));
//					}
//				} else {
//					reportvo.setCurrencyCode(channelOrder.getCurrencyCode());
//					reportvo.setPayAmount(channelOrder.getChannelPayAmount());
//				}
//			}
			
			//设置支付币种兑CNY汇率，取交易当日清算基本汇率
			SettlementBaseRate settlementBaseRate = currencyRateService.findSettlementBaseRate(channelOrder.getTransferCurrencyCode(), "CNY", null, channelOrder.getCreateDate());
			
			BigDecimal payCnyRate = BigDecimal.ZERO ;
			if(null == settlementBaseRate){
				sb.append("未找到支付币种兑CNY汇率") ;
				logger.info("未找到支付币种兑CNY汇率,渠道订单号为：" + channelOrderNo);
			}else{
				payCnyRate = new BigDecimal(settlementBaseRate.getExchangeRate()) ;
			}
			reportvo.setPayCnyRate(payCnyRate);
			//渠道支付金额CNY
			BigDecimal bankPayAmountCny = BigDecimal.ZERO ; 
			if(ChannelItemOrgCodeEnum.ABC.getCode().equals(orgCode)){
				if(1 == reconciliationFlg){
					//农行，取对账单上的交易金额（人民币）那一列其
					if(null != reconciliationFlag1Dto){
						bankPayAmountCny = null == reconciliationFlag1Dto.getInterChangeAmount() ? BigDecimal.ZERO : new BigDecimal(reconciliationFlag1Dto.getInterChangeAmount()).multiply(new BigDecimal(1000)) ;
					}
				}else{
					//未对账时，默认取0
					//bankPayAmountCny = BigDecimal.ZERO ;
					//此处做一悠，未对账时，不取0，同其他渠道=》取支付金额*支付币种兑CNY汇率 - >此时未对账的数据还是无效数据，需要对账后异步更新数据后取到最终的结果数据
					bankPayAmountCny = channelPayAmount.multiply(payCnyRate) ;
				}
				
			}else{
				//他情况渠道取支付金额*支付币种兑CNY汇率
				bankPayAmountCny = channelPayAmount.multiply(payCnyRate) ;
			}
			reportvo.setBankPayAmountCny(bankPayAmountCny);
			//银行固定手续费CNY=>银行固定手续费金额*支付币种兑CNY汇率
			
			BigDecimal bankFixedFeeCny = null == reportvo.getBankFixedFee() ? BigDecimal.ZERO : reportvo.getBankFixedFee().multiply(payCnyRate) ;
			reportvo.setBankFixedFeeCny(bankFixedFeeCny);
			
			// 获取商户清算汇率
			String rate = getSettlementRate(channelOrder);
			if(rate==null)
			{
				logger.info("该记录，渠道订单号【"+channelOrderNo+"】没有取到清算汇率，不生成交错日报表数据数据");
				unReportCount ++ ;
				continue;
			}
			// 取订单金额结算
			BigDecimal amount = channelOrder.getChannelAmount();
			logger.info("清算金额：" + amount + "，订单金额" + amount + "，保证金金额"+ channelOrder.getAssureAmount());
			//清算金额
			BigDecimal settleAmount = amount.multiply(new BigDecimal(rate));
			reportvo.setSettlementAmount(settleAmount);
			//银行比例手续费（CNY）=>若走农行，取对账单上的交易金额（人民币）-清算金额的值,其他情况：渠道支付金额*支付币种兑CNY汇率
			BigDecimal bankPerFeeCny = BigDecimal.ZERO ;
			if(ChannelItemOrgCodeEnum.ABC.getCode().equals(orgCode) || ChannelItemOrgCodeEnum.CYBSCTV.getCode().equals(orgCode)){
				logger.info("计算");
				//农行，取对账单上的交易金额（人民币）-清算金额的值
				if(1 == reconciliationFlg){
					//对账单数据
					if(null != reconciliationFlag1Dto){
						bankPerFeeCny = new BigDecimal(
								reconciliationFlag1Dto.getInterChangeAmount())
								.multiply(new BigDecimal(1000))
								.subtract(
										new BigDecimal(reconciliationFlag1Dto
												.getAcctAmountGross())
												.multiply(new BigDecimal(1000))); 
					}
				}
				
			}else{
				//其他情况：渠道支付金额*支付币种兑CNY汇率  - 》前面的算法错了， 正确的为银行比例手续费  * 支付币种兑CNY汇率 
				bankPerFeeCny = reportvo.getBankPerFee().multiply(payCnyRate) ;
			}
			reportvo.setBankPerFeeCny(bankPerFeeCny);
			//=======此处计算比例费、固定费、基本户、保证金Sta==============================
			// 获取商户手续费配置
			MerchantRateDto merchantRate = setMerchantRate(channelOrder);
			if(merchantRate==null)
			{
				logger.info("该记录，渠道订单号【"+channelOrderNo+"】没有取到商户手续费配置，不生成交易日报表数据");
				unReportCount ++ ;
				continue;
			}
			
			String transExchangeRate = "1";
			if (StringUtil.isEmpty(channelOrder.getFeeRate())) {
				//使用新的商户清算汇率查询方法
				Map<String, Object> param = new HashMap<String, Object>() ;
				param.put("sourceCurrency", StringUtil.isEmpty(merchantRate.getFixedCurrencyCode()) ? CurrencyCodeEnum.USD
						.getCode() : merchantRate.getFixedCurrencyCode());
				param.put("targetCurrency", channelOrder.getSettlementCurrencyCode());
				param.put("status", null);
				param.put("memberCode", channelOrder.getPartnerId());
				if (!StringUtils.isEmpty(channelOrder.getCardOrg())) {
					param.put("cardOrg", channelOrder.getCardOrg());
				}
				param.put("orderAmount", channelOrder.getChannelAmount());
				param.put("ltaCurrencyCode", "USD");
				param.put("point", getTime());
				SettlementRate transRate = currencyRateService.getNewSettlementRate(param) ;
				
				if (null == transRate) {
					sb.append("settlementRate not exists！") ;
				}
				transExchangeRate = transRate.getExchangeRate();
			} else {
				transExchangeRate = channelOrder.getFeeRate();
			}
			
			String feeAmount = "0";
			BigDecimal fixedFeeS;
			BigDecimal preFeeS;
			// 1-固定费用，2-费率,6-固定+费率
			if (merchantRate.getFeeType() == 1) {
				feeAmount = merchantRate.getFixedCharge();
				if (!CurrencyCodeEnum.USD.getCode().equals(channelOrder.getSettlementCurrencyCode())) {
					feeAmount = new BigDecimal(feeAmount).multiply(new BigDecimal(transExchangeRate)).toString();
				}
				channelOrder.setFixedFee(merchantRate.getFixedCharge());
				channelOrder.setFixedFeeSettlementAmount(new BigDecimal(feeAmount)
						.multiply(new BigDecimal("1000")));
				fixedFeeS = new BigDecimal(feeAmount).multiply(new BigDecimal("1000"));
			} else if (merchantRate.getFeeType() == 2) {
				feeAmount = amount.multiply(new BigDecimal(rate))
						.multiply(new BigDecimal(merchantRate.getChargeRate()))
						.divide(new BigDecimal("100")).toString();
				channelOrder.setPerFee((new BigDecimal(feeAmount).multiply(new BigDecimal("10"))));
				preFeeS = new BigDecimal(feeAmount);
			} else if (merchantRate.getFeeType() == 6) {
				BigDecimal chargeFee = amount.multiply(new BigDecimal(rate))
						.multiply(new BigDecimal(merchantRate.getChargeRate()))
						.divide(new BigDecimal("100"));
				channelOrder.setPerFee(chargeFee.multiply(new BigDecimal("10")));
				preFeeS = chargeFee;
				feeAmount = merchantRate.getFixedCharge();
				if (!CurrencyCodeEnum.USD.getCode()
						.equals(channelOrder.getSettlementCurrencyCode())) {
					feeAmount = new BigDecimal(feeAmount).multiply(
							new BigDecimal(transExchangeRate)).toString();
				}
				channelOrder.setFixedFee(merchantRate.getFixedCharge());
				channelOrder.setFixedFeeSettlementAmount(new BigDecimal(feeAmount)
						.multiply(new BigDecimal("1000")));
				fixedFeeS = new BigDecimal(feeAmount).multiply(new BigDecimal("1000"));
				feeAmount = chargeFee.add(new BigDecimal(feeAmount)).toString();
			}
			//保证金
			BigDecimal assureAmount = channelOrder.getAssureAmount().multiply(new BigDecimal(rate));
			reportvo.setAssureAmount(assureAmount);
			//比例费
			BigDecimal perFeeAmount = channelOrder.getPerFee().divide(new BigDecimal("10"));
			reportvo.setPerFee(perFeeAmount);
			//固定费=》（商户费率配置中有，也分渠道）固定费*手续费汇率
			BigDecimal fixedFeeAmount = channelOrder.getFixedFeeSettlementAmount() == null ? new BigDecimal(0) : channelOrder.getFixedFeeSettlementAmount();
			reportvo.setFixedFee(fixedFeeAmount);
			
			//银行存款入账
			//银行存款入账 | 银行存款入账币种
			reportvo.setTranType("交易入款");
			//银行存在入账币种 =>与渠道结算金额币种一致,中行、农行、卡司都是CNY, 这个算法已经不用了， 使用下面一行的算法
			//银行存款入账：对账过的数据，取渠道结算币种，未对账的，同支付币种，  中行、农行、卡司都是CNY
			String bankCurrencyCode = "" ;
			if(1 == reconciliationFlg ){
				//对过账，先取对账单结算币种
				ReconcileImportRecordDetailDto reconcileImportRecordDetailDto = getReconciliationFlag1Dto(channelOrder);
				if(null != reconcileImportRecordDetailDto){
					//取对账单结算金额(单位元)
					bankCurrencyCode = reconcileImportRecordDetailDto.getInterChangeCurrency() ;
				}else{
					logger.info("渠道订单【"+channelOrderNo+"】已对账，但是未取到对账明细记录信息.");
					sb.append("已对账，未取到渠道结算币种！") ;
				}
			}else{
				if (ChannelItemOrgCodeEnum.BOCS.getCode().equals(orgCode)
						|| ChannelItemOrgCodeEnum.BOCM.getCode().equals(orgCode)
						|| ChannelItemOrgCodeEnum.BOCI.getCode().equals(orgCode)
						|| ChannelItemOrgCodeEnum.CYBSCTV.getCode().equals(orgCode)
						|| ChannelItemOrgCodeEnum.BOC.getCode().equals(orgCode)
						|| ChannelItemOrgCodeEnum.ABC.getCode().equals(orgCode)){
					bankCurrencyCode = "CNY" ;
				}else if(ChannelItemOrgCodeEnum.CREDORAX.getCode().equals(orgCode)){
					if(CredoraxCurrencyCodeEnum.isNeedChange(channelOrder.getTransferCurrencyCode())){
						bankCurrencyCode = "USD" ;
					}else{
						bankCurrencyCode = channelOrder.getTransferCurrencyCode() ;
					}
				}else{
					bankCurrencyCode = channelOrder.getTransferCurrencyCode() ;
				}
			}
			reportvo.setBankCurrencyCode(bankCurrencyCode);
			
			//已对账
			BigDecimal bankAmount = BigDecimal.ZERO ;
			if(1 == reconciliationFlg){
				//对过账，先取对账单结算金额
				ReconcileImportRecordDetailDto reconcileImportRecordDetailDto = getReconciliationFlag1Dto(channelOrder);
				if(null != reconcileImportRecordDetailDto){
					//取对账单结算金额(单位元)
					bankAmount = new BigDecimal(reconcileImportRecordDetailDto.getAcctAmountGross()).multiply(new BigDecimal(1000)) ;
				}else{
					logger.info("渠道订单【"+channelOrderNo+"】已对账，但是未取到对账明细记录信息.");
				}
			}else{
				//未对账时
				//.未对账时，取值= （渠道支付金额-银行比例手续费-固定手续费) （取当日交易基本汇率）（若走卡司，卡司固定手续费这边取0）
				if(ChannelItemOrgCodeEnum.BOCS.getCode().equals(orgCode)){
					bankAmount = (channelPayAmount.subtract(reportvo.getBankPerFee()).subtract(BigDecimal.ZERO)) ;
				}else{
					bankAmount = (channelPayAmount.subtract(reportvo.getBankPerFee()).subtract(reportvo.getBankFixedFee())) ;
				}
			}
			reportvo.setBankAmount(bankAmount);
			
			//基本户
			BigDecimal baseAmount = settleAmount.subtract(assureAmount).subtract(perFeeAmount).subtract(fixedFeeAmount);
			reportvo.setBaseAmount(baseAmount);
			//若基本户结果为负数，将基本户和保证金置为0，固定费=清算金额-比例费
			if(baseAmount.compareTo(BigDecimal.ZERO) == -1){
				reportvo.setBaseAmount(BigDecimal.ZERO);
				reportvo.setAssureAmount(BigDecimal.ZERO);
				reportvo.setFixedFee(settleAmount.subtract(perFeeAmount));
			}
			//=======此处计算比例费、固定费、基本户、保证金End==============================
			//清算币种兑CNY汇率=》取交易当日清算基本汇率
			SettlementBaseRate settlementCurrencyCode2CnyBaseRate = currencyRateService.findSettlementBaseRate(channelOrder.getSettlementCurrencyCode(), "CNY", null, channelOrder.getCreateDate());
			BigDecimal settlementCnyRate = new BigDecimal(settlementCurrencyCode2CnyBaseRate.getExchangeRate()) ;
			reportvo.setSettlementCnyRate(settlementCnyRate);
			//清算金额CNY=>清算金额*清算币种兑CNY汇率
			BigDecimal settlementAmountCny = BigDecimal.ZERO ;
			settlementAmountCny = settleAmount.multiply(settlementCnyRate) ;
			reportvo.setSettlementAmountCny(settlementAmountCny);
			//基本户（CNY）	数字（取两位小数）=》 清算金额*清算币种兑CNY汇率  ==> 公式修改（ 之前的基本户*清算币种兑CNY汇率）
			BigDecimal baseAmountCny = BigDecimal.ZERO ;
			baseAmountCny = reportvo.getBaseAmount().multiply(settlementCnyRate) ;
			reportvo.setBaseAmountCny(baseAmountCny);
			//保证金（CNY）	数字（取两位小数）=>保证金*清算币种兑CNY汇率
			BigDecimal assureAmountCny = assureAmount.multiply(settlementCnyRate) ;
			reportvo.setAssureAmountCny(assureAmountCny);
			//比例费（CNY）	数字（取两位小数）=>比例费*清算币种兑CNY汇率
			BigDecimal perFeeCny = null == reportvo.getPerFee() ? new BigDecimal(0) : reportvo.getPerFee().multiply(settlementCnyRate) ;
			reportvo.setPerFeeCny(perFeeCny);
			
			//固定费（CNY）	数字（取两位小数=》固定费*清算币种兑CNY汇率
			BigDecimal fixedFeeCny = null == reportvo.getFixedFee() ? new BigDecimal(0) : reportvo.getFixedFee().multiply(settlementCnyRate) ;
			reportvo.setFixedFeeCny(fixedFeeCny);
			//汇差=》渠道支付金额CNY-清算金额CNY（银行还没收手续费的值-我们还没收商户手续费的值）
			BigDecimal rateIncome = bankPayAmountCny.subtract(settlementAmountCny) ;
			reportvo.setRateIncome(rateIncome);
			//汇差收益率=》汇差/渠道支付金额CNY
			logger.info("bankPayAmountCny:" + bankPayAmountCny);
			logger.info("汇差收入：" + rateIncome);
		    BigDecimal rateRate = rateIncome.divide(BigDecimal.ZERO.compareTo(bankPayAmountCny) == 0  ? new BigDecimal(1) : bankPayAmountCny, 10, BigDecimal.ROUND_HALF_UP) ;
		    reportvo.setRateRate(rateRate);
		    //总收入=》比例费CNY+固定费CNY+汇差
		    BigDecimal totalIncome = BigDecimal.ZERO;
			totalIncome = perFeeCny.add(fixedFeeCny).add(rateIncome) ;
			reportvo.setTotalIncome(totalIncome);
			//毛率=》总收入-银行固定手续费CNY-银行比例手续费CNY
			BigDecimal profit = BigDecimal.ZERO;
		    //毛利=>总收入-银行固定手续费CNY-银行比例手续费CNY
			BigDecimal tranFee = null == reportvo.getTranFee() ? new BigDecimal(0) : reportvo.getTranFee() ;
			profit = totalIncome.subtract(bankFixedFeeCny).subtract(bankPerFeeCny);
			reportvo.setProfit(profit);
			//毛利率=》毛利/渠道支付金额CNY
			BigDecimal profitRate = profit.divide(BigDecimal.ZERO.compareTo(bankPayAmountCny) == 0  ? new BigDecimal(1) : bankPayAmountCny, 10, BigDecimal.ROUND_HALF_UP) ;
			reportvo.setProfitRate(profitRate);
			//===================此处开始计算各类费用End================================================
			//清算日期
			reportvo.setSettlementDate(channelOrder.getSettlementDate());
			//数据有效性
			reportvo.setFlag( null == channelOrder.getReconciliationFlg() ? "0" : (1 == channelOrder.getReconciliationFlg() ? "1" : "0") );
			//是否DCC=》是/否用于 查询条件DCC/EDC分类
			reportvo.setPayType(channelOrder.getPayType());
			reportvo.setSettlementFlg(channelOrder.getSettlementFlg());
			reportvo.setReconciliationFlg(channelOrder.getReconciliationFlg());
			reportvo.setAssuresettlementFlg(channelOrder.getAssuresettlementFlg());
			//设置备注
			reportvo.setRemark(sb.toString());
			logger.info("渠道订单号：" + channelOrderNo + " 结束");
			list.add(reportvo);
			
		}
		Integer size = null == list ? 0 : list.size() ;
		logger.info("构造交易日报表数据集合结束，数据集合大小为：" + size);
		logger.info("不符合生成交易日报表数据条件的记录为【"+ unReportCount +"】条！");
		return list;
	}


	/**
	 * 获取已对账对象
	 * @param channelOrder
	 * @return
	 */
	private ReconcileImportRecordDetailDto getReconciliationFlag1Dto(
			ChannelOrderReport channelOrder) {
		Map<String, Object> paraMap = new HashMap<String, Object>() ;
		paraMap.put("channelOrderNo", channelOrder.getChannelOrderNo()) ;
		paraMap.put("reconciliationFlg", 1) ;
		List<ReconcileImportRecordDetailDto> reconcileDetailList = this.reconcileRecordService.queryReconcileDetailSingle(paraMap) ;
		if(CollectionUtils.isNotEmpty(reconcileDetailList)){
			ReconcileImportRecordDetailDto reconcileImportRecordDetailDto = reconcileDetailList.get(0) ;
			return reconcileImportRecordDetailDto;
		}
		return null ;
	}

	
	/**
	 * 10002001 农行EDC DCC 10078001 农行CTV 10076001 卡司 10080001 中银Migs
	 * 10079001 中银Moto 10075001 Credorax
	 */
	private void getOrgName(ChannelOrderReport channelOrder,
			TranDailyReportVo reportvo) {
		if ("10002001".equals(channelOrder.getOrgCode())) {
			reportvo.setOrgName("农行");
		} else if ("10078001".equals(channelOrder.getOrgCode())) {
			reportvo.setOrgName("农行CTV");
		} else if ("10076001".equals(channelOrder.getOrgCode())) {
			reportvo.setOrgName("卡司");
		} else if ("10080001".equals(channelOrder.getOrgCode())) {
			reportvo.setOrgName("中银Migs");
		} else if ("10079001".equals(channelOrder.getOrgCode())) {
			reportvo.setOrgName("中银Moto");
		} else if ("10075001".equals(channelOrder.getOrgCode())) {
			reportvo.setOrgName("Credorax");
		}else{
			reportvo.setOrgName("");
		}
	}

	/**
	 * 获取商户手续费配置
	 * 
	 * @param channelOrder
	 * @param merchantRate
	 */
	private MerchantRateDto setMerchantRate(ChannelOrderReport channelOrder) {
		MerchantRateDto merchantRate = new MerchantRateDto();
		PaymentOrderExpand paymentOrderExpand = paymentOrderExpandDAO
				.queryPayOrderExpandByPayNO(channelOrder.getPaymentOrderNo());
		String cardNumber = paymentOrderExpand.getCardNo();

		if (!StringUtil.isEmpty(cardNumber)) {
			CardBinInfo cardBinInfo = cardBinInfoService.getCardBinInfo(cardNumber.substring(0, 6));
			logger.info("商户结算查询到的cardbin信息是：" + cardBinInfo);
			if (cardBinInfo != null)
				merchantRate.setCountryCode(cardBinInfo.getCountryCode2());
		}
		String cardType = getCardType(cardNumber);
		merchantRate.setOrganisation(cardType);
		merchantRate.setMemberCode(channelOrder.getPartnerId());
		merchantRate.setDealCode(202);

		PaymentOrderDTO paymentOrderDTO = paymentOrderService.queryByPaymentOrderNo(channelOrder
				.getPaymentOrderNo());

		String payType = "EDC";
		if (paymentOrderDTO != null) {
			payType = paymentOrderDTO.getPayType();
		}

		int transType = 0;
		if (TransTypeEnum.EDC.getCode().equals(payType)) {
			transType = 1;
		} else if (TransTypeEnum.DCC.getCode().equals(payType)) {
			transType = 2;
		}

		List<MerchantRateDto> merchantRates = merchantRateService.queryMerchantRate(merchantRate);
		if (null == merchantRates || merchantRates.isEmpty()) {
			//throw new BusinessException("未找到商户手续费", ExceptionCodeEnum.NO_FEE_RATE);
			logger.info("未找到商户手续费,会员号："+channelOrder.getPartnerId());
			return null;
			
		} else {
			for (MerchantRateDto merchantRateDto : merchantRates) {
				if (merchantRateDto.getTransType() == 0
						&& "0".equals(merchantRateDto.getTransMode())) {
					merchantRate = merchantRateDto;
					break;
				}
				if (transType == merchantRateDto.getTransType()) {
					merchantRate = merchantRateDto;
					break;
				}
			}
			if (null == merchantRate.getFeeType()) {
				//throw new BusinessException("未找到商户手续费配置", ExceptionCodeEnum.NO_FEE_RATE);
				logger.info("未找到商户手续费,会员号："+channelOrder.getPartnerId());
				return null;
			}
		}
		String[] chargeRate = getCurrenctMonthChargeRate(merchantRate,
				resetMonthChargeRate(paymentOrderExpand.getCreateDate()));
		logger.info("getCurrenctMonthChargeRate : " + Arrays.toString(chargeRate));
		if (chargeRate != null) {
			merchantRate.setChargeRate(chargeRate[0]);
			merchantRate.setFixedCharge(chargeRate[1]);
			merchantRate.setFixedCurrencyCode(chargeRate[2]);
		}
		return merchantRate;
	}

	/**
	 * 获取当月使用费率
	 */
	public String[] getCurrenctMonthChargeRate(MerchantRateDto merchantRate, boolean needReset) {
		logger.info("getCurrenctMonthChargeRate merchantRate: " + merchantRate);
		logger.info("getCurrenctMonthChargeRate isNextMonth: " + needReset);
		if (StringUtil.isEmpty(merchantRate.getMonthChargeRate()) || needReset) {
			Calendar calendar = Calendar.getInstance();
			String yearAndMonth = String.valueOf(calendar.get(Calendar.YEAR))
					+ calendar.get(Calendar.MONTH);
			TradeAmountCount tradeAmountCount2 = new TradeAmountCount();
			tradeAmountCount2.setPartnerId(merchantRate.getMemberCode() + "");
			tradeAmountCount2.setCountMonth(yearAndMonth);
			TradeAmountCount tac2 = tradeAmountCountService.query(tradeAmountCount2);
			if (tac2 == null) {
				Collection<String[]> values = getFourLevelData(merchantRate).values();
				String[] data = (String[]) values.toArray()[0];
				merchantRate.setMonthChargeRate(data[0]);
				logger.info("更新当月费率到清算表：" + data[0]);
				merchantRateService.updateMerchantRate(merchantRate);
				return data;
			}
			// 得到上个月的交易数据
			Long totalAmount = tac2.getTotalAmount();
			// 如果本月的统计币种和上月的不一致，要转汇
			if (!tac2.getTotalCurrencyCode().equalsIgnoreCase(merchantRate.getLevelCurrencyCode())) {
				SettlementRate settlementRate = currencyRateService.getSettlementRate(
						tac2.getTotalCurrencyCode(), merchantRate.getLevelCurrencyCode(), "1",
						merchantRate.getMemberCode() + "", null);
				totalAmount = new BigDecimal(settlementRate.getExchangeRate())
						.multiply(new BigDecimal(totalAmount))
						.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			}
			// 根据上个月的交易统计决定当月的费率
			String[] chargeRate = geMathedLevelChargeRate(totalAmount, merchantRate);
			// 把当月费率更新到清算费率表中
			merchantRate.setMonthChargeRate(chargeRate[0]);
			logger.info("更新当月费率到清算表：" + chargeRate[0]);
			merchantRateService.updateMerchantRate(merchantRate);
			return chargeRate;
		} else {
			Collection<String[]> values = getFourLevelData(merchantRate).values();
			for (String[] level : values) {
				if (level[0].equals(merchantRate.getMonthChargeRate())) {
					return level;
				}
			}
			return null;
		}
	}

	private boolean resetMonthChargeRate(Date date) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date start = c.getTime();
		c.set(Calendar.MINUTE, 15);
		Date end = c.getTime();
		return date.after(start) && date.before(end);
	}

	/**
	 * 如果清算订单中没有清算汇率就去查询当日的清算汇率,否则清算使用清算订单中的清算汇率
	 * 
	 * @param channelOrder
	 * @return
	 */
	private String getSettlementRate(ChannelOrderReport channelOrder) {
		String rate = "1";
		if (StringUtil.isEmpty(channelOrder.getSettlementRate())) {
			SettlementRate settlementRate = currencyRateService.getSettlementRate(
					
					channelOrder.getCurrencyCode(), channelOrder.getSettlementCurrencyCode(), null,
					String.valueOf(channelOrder.getPartnerId()), channelOrder.getCreateDate());

			if (null == settlementRate) {
				logger.info("未找到对应结算汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: "
						+ channelOrder.getCurrencyCode() + " ,targetCurrencyCode: "
						+ channelOrder.getSettlementCurrencyCode());
				return null;
			} else {
				rate = settlementRate.getExchangeRate();
			}

		} else {
			rate = channelOrder.getSettlementRate();
		}
		return rate;
	}

	private String getCardType(String cardNo) {
		int cardLen = cardNo.length();

		if (cardLen == 16) {
			long subCard = Long.valueOf(cardNo.substring(0, 6));
			if (subCard >= 400000 && subCard <= 499999) {
				return "VISA";
			}
			if ((subCard>=510000 && subCard <=559999)||(subCard>=222100 && subCard <=272099)) {
				return "MASTER";
			}
			if ((subCard >= 352800 && subCard <= 358999)
					|| (subCard >= 213100 && subCard <= 213199)
					|| (subCard >= 180000 && subCard <= 180099)) {
				return "JCB";
			}
		}
		if (cardLen == 14) {
			long subCard = Long.valueOf(cardNo.substring(0, 6));
			if (subCard >= 300000 && subCard <= 305999) {
				return "DC";
			}
			if (subCard >= 309500 && subCard <= 309599) {
				return "DC";
			}
			if (subCard >= 360000 && subCard <= 369999) {
				return "DC";
			}
			if (subCard >= 380000 && subCard <= 399999) {
				return "DC";
			}
		}
		if (cardLen == 15) {
			long subCard = Long.valueOf(cardNo.substring(0, 6));
			if (subCard >= 340000 && subCard <= 349999) {
				return "AE";
			}
			if (subCard >= 370000 && subCard <= 379999) {
				return "AE";
			}
		}
		return null;
	}

	private Map<Long, String[]> getFourLevelData(MerchantRateDto merchantRate) {
		Map<Long, String[]> tradeCountLevelMap = new LinkedHashMap<Long, String[]>();
		if (merchantRate.getMonthAmountLevel() != null) {
			tradeCountLevelMap.put(merchantRate.getMonthAmountLevel(),
					new String[] { merchantRate.getChargeRate(), merchantRate.getFixedCharge(),
							merchantRate.getFixedCurrencyCode() });
		}
		if (merchantRate.getMonthAmountLevel1() != null) {
			tradeCountLevelMap.put(merchantRate.getMonthAmountLevel1(),
					new String[] { merchantRate.getChargeRate1(), merchantRate.getFixedCharge1(),
							merchantRate.getFixed1CurrencyCode() });
		}
		if (merchantRate.getMonthAmountLevel2() != null) {
			tradeCountLevelMap.put(merchantRate.getMonthAmountLevel2(),
					new String[] { merchantRate.getChargeRate2(), merchantRate.getFixedCharge2(),
							merchantRate.getFixed2CurrencyCode() });
		}
		if (merchantRate.getMonthAmountLevel3() != null) {
			tradeCountLevelMap.put(merchantRate.getMonthAmountLevel3(),
					new String[] { merchantRate.getChargeRate3(), merchantRate.getFixedCharge3(),
							merchantRate.getFixed3CurrencyCode() });
		}
		return tradeCountLevelMap;
	}

	private String[] geMathedLevelChargeRate(Long totalAmount, MerchantRateDto merchantRate) {
		logger.info("geMathedLevelChargeRate : totalAmount is " + totalAmount);
		Map<Long, String[]> tradeCountLevelMap = getFourLevelData(merchantRate);
		Long[] data = (Long[]) tradeCountLevelMap.keySet().toArray(
				new Long[tradeCountLevelMap.keySet().size()]);
		Long targetLevel = null;
		if (totalAmount <= data[0]) {
			targetLevel = data[0];
		} else if (totalAmount >= data[data.length - 1]) {
			targetLevel = data[data.length - 1];
		} else {
			int index = -1;
			for (int i = 0; i < data.length; i++) {
				if (totalAmount >= data[i]) {
					continue;
				} else {
					index = i;
					break;
				}
			}
			targetLevel = data[index - 1];
		}
		return tradeCountLevelMap.get(targetLevel);
	}
	public static double getTime() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		double s = min / 100.0;
		double rst = hour + s;

		return rst;
	}
	public void setPricingStrategyService(PricingStrategyService pricingStrategyService) {
		this.pricingStrategyService = pricingStrategyService;
	}

	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	public void setPaymentOrderExpandDAO(PaymentOrderExpandDAO paymentOrderExpandDAO) {
		this.paymentOrderExpandDAO = paymentOrderExpandDAO;
	}

	public void setCardBinInfoService(CardBinInfoService cardBinInfoService) {
		this.cardBinInfoService = cardBinInfoService;
	}

	public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
		this.paymentOrderService = paymentOrderService;
	}

	public void setMerchantRateService(MerchantRateService merchantRateService) {
		this.merchantRateService = merchantRateService;
	}

	public void setTradeAmountCountService(TradeAmountCountService tradeAmountCountService) {
		this.tradeAmountCountService = tradeAmountCountService;
	}

	public void setLogger(Log logger) {
		this.logger = logger;
	}


	/**
	 * @param reconcileRecordService the reconcileRecordService to set
	 */
	public void setReconcileRecordService(
			ReconcileRecordService reconcileRecordService) {
		this.reconcileRecordService = reconcileRecordService;
	}
	
}
