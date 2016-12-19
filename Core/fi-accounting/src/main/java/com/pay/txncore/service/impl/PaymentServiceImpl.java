/**
 * Modify history:
 * 2016-05-09  增加了 退款手续费相关的业务代码
 * 2016-06-06  重构了 清算部分的代码，
 *             (1)把一个长的函数 ==> 短的函数调用
 *             (2)保证金  根据清算金额 进行了重新计算   
 *             (3)增加了 一种情况的处理：就是 清算金额 < 保证金清算金额 + 手续费的 的情况  、
 *             这是doAccount_Version1 的情况 
 * 2016-06-15  清算金额为0 的情况，我们进行了一些修改 , 判断是否要 扣除固定手续费  
 * 修改记账函数  doAccounting_liquidation_version2 , 增加了 分录 206
 * 问题： 余额不够，如果抛出异常，则该条记录 会被修改为  记账失败
 *             如果不抛出异常，则会报警 该清算未清算  
 *             
 * 2016-06-16  本地化支付，是没有卡号的,修正  getCardType()
 * 2016-06-27  delin  区分本地化，获取卡类型等
 * 2016-07-11  (1)nico.shao: 修改了 fixed_fee 字段 为*1000 之后的值，该字段数据库中字段为 number(20,0)，原来单位为元，
 *             比如设置的数据为   0.15 ，存储的数据，就是0 了。 
 *             (2)增加了SmallOrderFixedFee 字段 
 *
 */
package com.pay.txncore.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.model.RefundFeeConf;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acc.rate.dto.MerchantRateDto;
import com.pay.acc.rate.service.MerchantRateService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.cardbin.model.CardBinInfo;
import com.pay.cardbin.service.CardBinInfoService;
import com.pay.fi.commons.TradeTypeEnum;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.dao.BaseDAO;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.notification.request.WeiXinNotifyRequest;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.rm.service.model.RiskOrder;
import com.pay.txncore.commons.OrderCreditEnum;
import com.pay.txncore.commons.SettlementFlagEnum;
import com.pay.txncore.dao.PaymentOrderExpandDAO;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.OrderCreditDetailDTO;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.model.RefundFeeOrder;
import com.pay.txncore.model.SettlementRate;
import com.pay.txncore.model.TradeAmountCount;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.txncore.service.ChannelService;
import com.pay.txncore.service.OrderCreditService;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.txncore.service.PaymentOrderService;
import com.pay.txncore.service.PaymentService;
import com.pay.txncore.service.TradeAmountCountService;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class PaymentServiceImpl implements PaymentService {

	private final Log logger = LogFactory.getLog(getClass());
	private PaymentOrderService paymentOrderService;
	private AccountingService accounting_200_200;
	private AccountingService accounting_200_201;
	private AccountingService accounting_200_202;
	private AccountingService accounting_200_203;
	private AccountingService accounting_700_700;
	private AccountQueryService accountQueryService;
	private PartnerSettlementOrderService partnerSettlementOrderService;
	private ChannelService channelService;
	private ChannelOrderService channelOrderService;
	private PaymentOrderExpandDAO paymentOrderExpandDAO;
	final MathContext mc = new MathContext(4, RoundingMode.HALF_DOWN);
	private MerchantRateService merchantRateService;
	private AccountingService accounting_200_204;
	private AccountingService accounting_200_205;
	private AccountingService accounting_200_207;
	private AccountingService accounting_500_517;		//2016-05-09
	private AccountingService accounting_200_206;		//清算时固定手续费  2016-06-15
	private BaseDAO riskOrderDAO;
	private BaseDAO refundFeeOrderDAO;		//2016-05-09
	private TradeAmountCountService tradeAmountCountService;
	private CardBinInfoService cardBinInfoService;
	private OrderCreditService orderCreditService;
	private EnterpriseBaseService enterpriseBaseService;	//2016-06-15
	private AcctService acctService;			//2016-06-15
	
	private boolean resetMonthChargeRate(Date date){
		Calendar c = Calendar.getInstance();    
        c.set(Calendar.DAY_OF_MONTH,1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE,0);
        c.set(Calendar.SECOND,0);
        Date start = c.getTime();
        c.set(Calendar.MINUTE,15);
        Date end = c.getTime();
        return date.after(start) && date.before(end);
	}
	
	/**
	 * 获取当月使用费率
	 */
	public String[] getCurrenctMonthChargeRate(MerchantRateDto merchantRate,boolean needReset){
		logger.info("getCurrenctMonthChargeRate merchantRate: "+merchantRate);
		logger.info("getCurrenctMonthChargeRate isNextMonth: "+needReset);
		if(StringUtil.isEmpty(merchantRate.getMonthChargeRate())||needReset){
			Calendar calendar = Calendar.getInstance();
			String yearAndMonth = String.valueOf(calendar.get(Calendar.YEAR))+calendar.get(Calendar.MONTH);
			TradeAmountCount tradeAmountCount2 = new TradeAmountCount();
			tradeAmountCount2.setPartnerId(merchantRate.getMemberCode()+"");
			tradeAmountCount2.setCountMonth(yearAndMonth);
			TradeAmountCount tac2 = tradeAmountCountService.query(tradeAmountCount2);
			if(tac2==null){
				Collection<String[]> values = getFourLevelData(merchantRate).values();
				String[] data = (String[])values.toArray()[0];
				merchantRate.setMonthChargeRate(data[0]);
				logger.info("更新当月费率到清算表："+data[0]);
				merchantRateService.updateMerchantRate(merchantRate);
				return data;
			}
			//得到上个月的交易数据
			Long totalAmount = tac2.getTotalAmount();
			//如果本月的统计币种和上月的不一致，要转汇
			if(!tac2.getTotalCurrencyCode().equalsIgnoreCase(merchantRate.getLevelCurrencyCode())){
				SettlementRate settlementRate = channelService.getCurrencyRateService()
						.getSettlementRate(tac2.getTotalCurrencyCode(), 
						merchantRate.getLevelCurrencyCode(),"1", merchantRate.getMemberCode()+"", null);
				totalAmount = new BigDecimal(settlementRate.getExchangeRate()).multiply(new BigDecimal(totalAmount)).
						setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			}
			//根据上个月的交易统计决定当月的费率
			String[] chargeRate = geMathedLevelChargeRate(totalAmount, merchantRate);
			//把当月费率更新到清算费率表中
			merchantRate.setMonthChargeRate(chargeRate[0]);
			logger.info("更新当月费率到清算表："+chargeRate[0]);
			merchantRateService.updateMerchantRate(merchantRate);
			return chargeRate;
		}else{
			Collection<String[]> values = getFourLevelData(merchantRate).values();
			for(String[] level : values){
				if(level[0].equals(merchantRate.getMonthChargeRate())){
					return level;
				}
			}
			return null;
		}
	}

	@Override
	public void liquidationRnTx(PartnerSettlementOrder partnerSettlementOrder) {
	
		if(true){
			 liquidationRnTx2(partnerSettlementOrder);
			 return ;
		}
		
		/*
		String orderId = partnerSettlementOrder.getId().toString();
		Long orderAmount = partnerSettlementOrder.getOrderAmount();
		Long amount = partnerSettlementOrder.getAmount();
		Long assureAmount = partnerSettlementOrder.getAssureAmount();
		Long partnerId = partnerSettlementOrder.getPartnerId();
		String merchantOrderId = partnerSettlementOrder.getOrderId();
		String settlementCurrencyCode = partnerSettlementOrder
				.getSettlementCurrencyCode();
		logger.info("清算金额："+amount+"，订单金额"+orderAmount+"，保证金金额"+assureAmount);
		if(partnerSettlementOrder.getSettlementFlg()==SettlementFlagEnum.REFUND.getCode()){
			
			logger.info("退款中不清算.." + partnerSettlementOrder.getId());
			return;
		}
		
		ChannelOrderDTO channelOrderDTO = channelOrderService
				.queryByTradeOrderNo(partnerSettlementOrder.getPaymentOrderNo());
		
		String rate = "1";

		//如果清算订单中没有清算汇率就去查询当日的清算汇率,否则清算使用清算订单中的清算汇率
		if(StringUtil.isEmpty(partnerSettlementOrder.getSettlementRate())){
			SettlementRate settlementRate = channelService.getCurrencyRateService()
			          .getSettlementRate(partnerSettlementOrder.getCurrencyCode(), 
			settlementCurrencyCode,null,String.valueOf(partnerId),partnerSettlementOrder.getCreateDate());
			
			if (null == settlementRate) {
				logger.info("未找到对应结算汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: "+
			    partnerSettlementOrder.getCurrencyCode()
						+" ,targetCurrencyCode: "+settlementCurrencyCode);
				throw new BusinessException("settlementRate not exists",
						ExceptionCodeEnum.NO_SETTLEMENT_RATE);
			} else {
				rate = settlementRate.getExchangeRate();
			}
			
		}else{
			rate  =  partnerSettlementOrder.getSettlementRate();
		}
		
		
		//UPDATE BY CHMA,GET CONFIG FEE 
		//PaymentOrderExpand paymentOrderExpand = paymentOrderExpandDAO.queryPayOrderExpandByPayNO(partnerSettlementOrder.getPaymentOrderNo());
		// 一次性查询 PaymentOrderExpand 表的卡号 和创建时间 减少与数据库的交互次数 delin.dong 2016年5月19日 15:27:27
		PaymentOrderDTO paymentOrderDTO = paymentOrderService.queryByPaymentOrderNo(partnerSettlementOrder.getPaymentOrderNo());
		
		MerchantRateDto merchantRate = new MerchantRateDto();
		if(paymentOrderDTO.getTradeType().startsWith("40")){//40交易类型40开头的为 本地化交易
			merchantRate.setOrganisation("LOCAL"); //add by delin 设置默认 local 原因：没有卡号
			merchantRate.setCountryCode("LOCAL");
		}else{
			String cardNumber = paymentOrderDTO.getCardNo();
		
			if(!StringUtil.isEmpty(cardNumber)){
				CardBinInfo cardBinInfo = cardBinInfoService.getCardBinInfo(cardNumber.substring(0, 6));
				logger.info("商户结算查询到的cardbin信息是："+cardBinInfo);
				if(cardBinInfo!=null)
					merchantRate.setCountryCode(cardBinInfo.getCountryCode2());
			}
			String cardType = getCardType(cardNumber);
			merchantRate.setOrganisation(cardType);
		}
		
		merchantRate.setMemberCode(partnerId);
		merchantRate.setDealCode(202);

		
		String payType="EDC";
        if(paymentOrderDTO!=null){
        	payType=paymentOrderDTO.getPayType();
        }		
		
		int transType = 0;
		if(TransTypeEnum.EDC.getCode().equals(payType)){
			transType = 1;
		}else if(TransTypeEnum.DCC.getCode().equals(payType)){
			transType = 2;
		}
		//Add by Mack 2016年5月19日15:52:14 跟业务确认交易模型只区分3D非3D
		String modeType = "2"; // 默认值非3D
        if ( paymentOrderDTO.getTradeType().equals(TradeTypeEnum.THREEPAY.getCode())){
        	modeType = "1"; 
		 }else if(paymentOrderDTO.getTradeType().startsWith("40")){ //add by delin 2016年6月27日 11:25:16 区分本地化
        	modeType = "3"; 
        }
        		
		
		//Add End
		
		logger.info("商户交易方式transType=["+transType+"]");
		String feeAmount = "0";
		List<MerchantRateDto> merchantRates = merchantRateService.queryMerchantRate(merchantRate);
		if (null == merchantRates || merchantRates.isEmpty()) {
			throw new BusinessException("未找到商户手续费",
					ExceptionCodeEnum.NO_FEE_RATE);
		}else{ 
			for(MerchantRateDto merchantRateDto : merchantRates){
				//用交易类型和交易模型取做全匹配，找不到取全部和全部的匹配，再找不到取交易类型的					
				if ((transType == merchantRateDto.getTransType())&&( modeType.equals(merchantRateDto.getTransMode()))) {
					merchantRate = merchantRateDto;
					break;
				}
				
				if(merchantRateDto.getTransType() == 0 && "0".equals(merchantRateDto.getTransMode())){
					merchantRate = merchantRateDto;
					break;
				}
				if(transType ==merchantRateDto.getTransType()){
					merchantRate = merchantRateDto;
					break;
				}
			}
			if(null == merchantRate.getFeeType()){

				logger.info("未找到商户手续费配置");
				throw new BusinessException("未找到商户手续费配置",
						ExceptionCodeEnum.NO_FEE_RATE);
			}
		}
		logger.info("merchantRate：merchantRate.getMonthChargeRate()="+merchantRate.getMonthChargeRate()+"id="+merchantRate.getId()+"memberCode="+merchantRate.getMemberCode());
		//	
		String[] chargeRate = getCurrenctMonthChargeRate(merchantRate,resetMonthChargeRate(paymentOrderDTO.getpOExpandCreateDate()));
		logger.info("getCurrenctMonthChargeRate : "+Arrays.toString(chargeRate));
		if(chargeRate!=null){
			merchantRate.setChargeRate(chargeRate[0]);
			merchantRate.setFixedCharge(chargeRate[1]);
			merchantRate.setFixedCurrencyCode(chargeRate[2]);
		}
		
		String transExchangeRate = "1";
		
		if(StringUtil.isEmpty(partnerSettlementOrder.getFeeRate())){
			SettlementRate transRate = channelService.getCurrencyRateService()
			          .getSettlementRate(StringUtil.isEmpty(merchantRate.getFixedCurrencyCode())
			        		  ?CurrencyCodeEnum.USD.getCode():merchantRate.getFixedCurrencyCode(), 
			        		  partnerSettlementOrder.getSettlementCurrencyCode(),null,
			        		  String.valueOf(partnerId), partnerSettlementOrder.getCreateDate());
			if(null == transRate){
				throw new BusinessException("settlementRate not exists",
						ExceptionCodeEnum.NO_SETTLEMENT_RATE);
			}
			transExchangeRate = transRate.getExchangeRate();
		}else{
			transExchangeRate = partnerSettlementOrder.getFeeRate();
		}
        
		BigDecimal fixedFeeS= new BigDecimal("0");
		BigDecimal preFeeS= new BigDecimal("0");
		//1-固定费用，2-费率,6-固定+费率
		if(merchantRate.getFeeType() == 1){
			feeAmount = merchantRate.getFixedCharge();
			if(!CurrencyCodeEnum.USD.getCode().equals(settlementCurrencyCode)){
				feeAmount = new BigDecimal(feeAmount).multiply(new BigDecimal(transExchangeRate)).toString();
			}
			
			//feeAmount = new BigDecimal(feeAmount).multiply(new BigDecimal("1000")).toString(); 
			// change for 0.001 issue
			feeAmount = String.valueOf(new BigDecimal(feeAmount).multiply(new BigDecimal("1000")).longValue());
			partnerSettlementOrder.setFixedFee(merchantRate.getFixedCharge());
			partnerSettlementOrder.setFixedFeeSettlementAmount(feeAmount);
			fixedFeeS = new BigDecimal(feeAmount);
			
		}else if(merchantRate.getFeeType() == 2){
			feeAmount = new BigDecimal(amount).multiply(new BigDecimal(rate))
					.multiply(new BigDecimal(merchantRate.getChargeRate())).divide(new BigDecimal("100")).toString();
		    partnerSettlementOrder.setPerFee((new BigDecimal(feeAmount).multiply(new BigDecimal("10"))).longValue());
		    preFeeS = new BigDecimal(feeAmount);
		    
		}else if(merchantRate.getFeeType() == 6){
			BigDecimal chargeFee = new BigDecimal(amount).multiply(new BigDecimal(rate))
					.multiply(new BigDecimal(merchantRate.getChargeRate())).divide(new BigDecimal("100"));			
			partnerSettlementOrder.setPerFee(chargeFee.multiply(new BigDecimal("10")).longValue());
			preFeeS = chargeFee;

			feeAmount = merchantRate.getFixedCharge();
			if(!CurrencyCodeEnum.USD.getCode().equals(settlementCurrencyCode)){
				feeAmount = new BigDecimal(feeAmount).multiply(new BigDecimal(transExchangeRate)).toString();
			}
			
			//feeAmount = new BigDecimal(feeAmount).multiply(new BigDecimal("1000")).toString(); 
			// change for 0.001 issue
			feeAmount = String.valueOf(new BigDecimal(feeAmount).multiply(new BigDecimal("1000")).longValue());
			partnerSettlementOrder.setFixedFee(merchantRate.getFixedCharge());
			partnerSettlementOrder.setFixedFeeSettlementAmount(feeAmount);
			fixedFeeS = new BigDecimal(feeAmount);
			
			feeAmount = chargeFee.add(new BigDecimal(feeAmount)).toString();
		}
		
		//----------------------------------
		
		String transferRate = channelOrderDTO.getTransferRate();
		Long amount200 = amount;

		long fee = new BigDecimal(feeAmount).longValue();
		if (logger.isInfoEnabled()) {
			logger.info("channelRate:" + transferRate);
			logger.info("amount200:" + amount200);
			logger.info("settlement id:" + orderId + ",fee:" + fee);
		}
		
		//不够结算手续费，不清算
		long dAmount = new BigDecimal(amount).multiply(
				new BigDecimal(rate)).longValue();
		long dAssureAmount = new BigDecimal(assureAmount).multiply(
				new BigDecimal(rate)).longValue();
		
		//2015年11月7日13:53:00 peiyu.yang 现修改为如果订单金额不够扣手续费，就全部当成手续费扣除
		long accountingAmount = dAmount - new BigDecimal(feeAmount).longValue()-dAssureAmount;
		
		logger.info("orderAmount:"+orderAmount+" ,rate:"+rate+" ,dAmount: "+dAmount+" ,feeAmount:"+feeAmount
				+" ,accountingAmount: "+accountingAmount+" ,dAssureAmount: "+dAssureAmount);
		//----------------------------------
		
		
		logger.info("partner settlement order id:" + partnerSettlementOrder.getId() + 
				",get USD to " + settlementCurrencyCode + ",rate:" + transExchangeRate 
				+ ",partner settlement rata:" + rate);
		
		if(!StringUtil.isEmpty(rate)){
			BigDecimal settlementAmount = new BigDecimal(amount)
			.multiply(new BigDecimal(rate));
			partnerSettlementOrder.setSettlementAmount(settlementAmount.longValue());
		}
		
		DecimalFormat df = new DecimalFormat("0.000");
		df.setRoundingMode(RoundingMode.DOWN);
		
		String assureAmountStr = df.format((new BigDecimal(partnerSettlementOrder.getAssureAmount()))
				                      .multiply(new BigDecimal(rate)).divide(new BigDecimal("1000")));
		logger.info("保证金金额："+assureAmountStr);
		String perFeeAmount = df.format(new BigDecimal(partnerSettlementOrder.getPerFee())
		                                                 .divide(new BigDecimal("10000")));
		logger.info("perFeeAmount："+perFeeAmount);
		String settleAmountStr = new BigDecimal(partnerSettlementOrder.getSettlementAmount())
		.divide(new BigDecimal("1000")).toString();
		logger.info("settleAmountStr : "+settleAmountStr);
		String fixedFeeAmount = df.format(new BigDecimal(partnerSettlementOrder.getFixedFeeSettlementAmount())
		.divide(new BigDecimal("1000")));
		logger.info("fixedFeeAmount : "+fixedFeeAmount);
		BigDecimal recordAmount = new BigDecimal(settleAmountStr).subtract(new BigDecimal(fixedFeeAmount))
        .subtract(new BigDecimal(perFeeAmount)).subtract(new BigDecimal(assureAmountStr));
		logger.info("入账金额："+recordAmount);
		recordAmount = recordAmount.multiply(new BigDecimal("1000"));
		logger.info("recordAmount : "+recordAmount.longValue());
		logger.info("recordAmount:"+recordAmount+" ,accountingAmount:"+accountingAmount); 
		try {

			partnerSettlementOrder.setSettlementRate(rate);
			
			if (accountingAmount <= 0) {
                long perFee = preFeeS.longValue();
                
                if(perFee>=dAmount){
                	partnerSettlementOrder.setPerFee(dAmount);
                	partnerSettlementOrder.setFixedFeeSettlementAmount("0");
                }else{
                	long tmp = dAmount-perFee;
                	partnerSettlementOrder.setFixedFeeSettlementAmount(String.valueOf(tmp));
                }
                
				partnerSettlementOrder.setRecordedAmount(0L);
				partnerSettlementOrder.setAssureAmount(0L);
				//清算金额小于手续费时，全部清算为手续费
				logger.error("清算金额小于手续费时，全部清算为手续费, settlement id:" + orderId 
						+ ",settlement amount:" + dAmount);
				doAccounting_200_205(orderId,dAmount,settlementCurrencyCode,merchantOrderId);
			}else{

				// 货币兑换
				doAccounting_200_200(orderId, amount200,
						channelOrderDTO.getCurrencyCode(),merchantOrderId);
				doAccounting_200_201(orderId, amount, settlementCurrencyCode, rate,merchantOrderId);
				doAccounting_200_202(partnerId, orderId,recordAmount.longValue(),
						assureAmount, settlementCurrencyCode,
						partnerSettlementOrder,new BigDecimal(perFeeAmount).add(new BigDecimal(fixedFeeAmount))
						.multiply(new BigDecimal("1000")),merchantOrderId);
				// 先扣除保证金
				doAccounting_200_203(partnerId, orderId, assureAmount,
						settlementCurrencyCode, rate,merchantOrderId);
				partnerSettlementOrder.setRecordedAmount(recordAmount.longValue());
				
			}

			partnerSettlementOrder.setSettlementFlg(1);
			partnerSettlementOrder.setFee(fee);
			
			partnerSettlementOrderService
					.updatePartnerSettlementOrder(partnerSettlementOrder);

			// 最后一笔未结算订单, update peiyu.yang  这段是没有意义的代码,现在清算订单不是分两笔进行清算了。
			//List<PartnerSettlementOrder> settlementOrders = partnerSettlementOrderService
				//	.queryUnSettlementPartnerSettlementOrder(partnerId, 0);
			//if (null == settlementOrders || settlementOrders.isEmpty()) {
				paymentOrderService.updateSettlementFlg(
						partnerSettlementOrder.getPaymentOrderNo(), 1);
			//}
		} catch (Exception e) {
			logger.error("do accounting error,settement orderId:" 
		                                         + partnerSettlementOrder.getId(), e);
			Map<String,String> data = new HashMap<String, String>();
			data.put("first","系统异常:清算记账失败,txncore:PaymentServiceImpl-settlementOrderId:"
			                    +partnerSettlementOrder.getId());
			data.put("keyword1","清算记账失败");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
	        data.put("keyword2",formatter.format(new Date()));
	        data.put("remark","请尽快检查！");
			this.sendAlertMsg(data);
			partnerSettlementOrder.setSettlementFlg(2);
			partnerSettlementOrderService
					.updatePartnerSettlementOrder(partnerSettlementOrder);
		}
		*/
	}
	@Override
	public void BuildOrderCredit(PartnerSettlementOrder partnerSettlementOrder,String settlementCurrencyCode,String dayRate,Boolean flag) 
	throws BusinessException {
		
		String orderId = partnerSettlementOrder.getId().toString();
		Long orderAmount = partnerSettlementOrder.getOrderAmount();
		Long amount = partnerSettlementOrder.getAmount();
		Long assureAmount = partnerSettlementOrder.getAssureAmount();
		Long partnerId = partnerSettlementOrder.getPartnerId();
		String merchantOrderId = partnerSettlementOrder.getOrderId();
		logger.info("清算金额："+amount+"，订单金额"+orderAmount+"，保证金金额"+assureAmount);
		if(partnerSettlementOrder.getSettlementFlg()==SettlementFlagEnum.REFUND.getCode()){
			
			logger.info("退款中不清算.." + partnerSettlementOrder.getId());
			return;
		}
		
		
		String rate;
		if(null==settlementCurrencyCode || "".equals(settlementCurrencyCode))
		{
			settlementCurrencyCode="CNY";
		}
		if("CNY".equals(settlementCurrencyCode) && "CNY".equals(partnerSettlementOrder
				.getCurrencyCode()))
		{
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sourceCurrency", partnerSettlementOrder.getCurrencyCode());
			param.put("targetCurrency", "CNY");
			param.put("status", "1");
			param.put("memberCode", String.valueOf(partnerSettlementOrder.getPartnerId()));
			if (!StringUtils.isEmpty(partnerSettlementOrder.getCardOrg())) {
				param.put("cardOrg", partnerSettlementOrder.getCardOrg());
			}
			param.put("orderAmount", partnerSettlementOrder.getOrderAmount());
			param.put("ltaCurrencyCode", "USD");
			param.put("point", getTime());
			
			SettlementRate settlementRate = channelService
					.getCurrencyRateService().getNewSettlementRate(param);
			if (null == settlementRate) {
				rate="1";
			} else {
				rate = settlementRate.getExchangeRate();
			}
		}
		else if (!settlementCurrencyCode.equals(partnerSettlementOrder
				.getCurrencyCode())) {
			// 订单授信目前是全都清算成人民币
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sourceCurrency", partnerSettlementOrder.getCurrencyCode());
			param.put("targetCurrency", "CNY");
			param.put("status", "1");
			param.put("memberCode", String.valueOf(partnerSettlementOrder.getPartnerId()));
			if (!StringUtils.isEmpty(partnerSettlementOrder.getCardOrg())) {
				param.put("cardOrg", partnerSettlementOrder.getCardOrg());
			}
			param.put("orderAmount", partnerSettlementOrder.getOrderAmount());
			param.put("ltaCurrencyCode", "USD");
			param.put("point", getTime());
			
			SettlementRate settlementRate = channelService
					.getCurrencyRateService().getNewSettlementRate(param);
			if (null == settlementRate) {
				logger.info("未找到对应结算汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: "
						+ partnerSettlementOrder.getCurrencyCode()
						+ " ,targetCurrencyCode: " + settlementCurrencyCode+"orderId:"+orderId);
				throw new BusinessException("settlementRate not exists",
						ExceptionCodeEnum.NO_SETTLEMENT_RATE);
			} else {
				rate = settlementRate.getExchangeRate();
			}
		}else{
			rate = "1";
		}
		
		
		//UPDATE BY CHMA,GET CONFIG FEE 
		//PaymentOrderExpand paymentOrderExpand = paymentOrderExpandDAO.queryPayOrderExpandByPayNO(partnerSettlementOrder.getPaymentOrderNo());
		// 一次性查询 PaymentOrderExpand 表的卡号 和创建时间 减少与数据库的交互次数 delin.dong 2016年5月19日 15:27:27
		PaymentOrderDTO paymentOrderDTO = paymentOrderService.queryByPaymentOrderNo(partnerSettlementOrder.getPaymentOrderNo());
		
		MerchantRateDto merchantRate = new MerchantRateDto();
		if(paymentOrderDTO.getTradeType().startsWith("40")){//40交易类型40开头的为 本地化交易
			merchantRate.setOrganisation("LOCAL"); //add by delin 设置默认 local 原因：没有卡号
			merchantRate.setCountryCode("LOCAL");
		}else{
			String cardNumber = paymentOrderDTO.getCardNo();
			if(!StringUtil.isEmpty(cardNumber)){
				CardBinInfo cardBinInfo = cardBinInfoService.getCardBinInfo(cardNumber.substring(0, 6));
				logger.info("商户结算查询到的cardbin信息是："+cardBinInfo);
				if(cardBinInfo!=null)
					merchantRate.setCountryCode(cardBinInfo.getCountryCode2());
			}
			String cardType = getCardType(cardNumber);
			merchantRate.setOrganisation(cardType);
		}
		merchantRate.setMemberCode(partnerId);
		merchantRate.setDealCode(202);
		
		
		String payType="EDC";
		if(paymentOrderDTO!=null){
			payType=paymentOrderDTO.getPayType();
		}		
		
		int transType = 0;
		if(TransTypeEnum.EDC.getCode().equals(payType)){
			transType = 1;
		}else if(TransTypeEnum.DCC.getCode().equals(payType)){
			transType = 2;
		}
		//Add by Mack 2016年5月19日15:52:14 跟业务确认交易模型只区分3D非3D
		String modeType = "2"; // 默认值非3D
		if ( paymentOrderDTO.getTradeType().equals(TradeTypeEnum.THREEPAY.getCode())){
			modeType = "1"; 
		}else if(paymentOrderDTO.getTradeType().startsWith("40")){ //add by delin 2016年6月27日 11:25:16 区分本地化
			modeType = "3"; 
		}
		//Add End

		//Add by davis.guo 2016-06-28 添加Computop本地化支付方式判断----------------------
		String localPayCode = "0"; // 默认值非Computop本地化支付
		if("3".equals(modeType)){ //区分本地化
			localPayCode = paymentOrderDTO.getOrgCode();// 
		}
		logger.info("###localPayCode="+localPayCode);
		//Add End-------------------------------------------------------------------
		
		logger.info("商户交易方式transType=["+transType+"]");
		String feeAmount = "0";
		List<MerchantRateDto> merchantRates = merchantRateService.queryMerchantRate(merchantRate);
		if (null == merchantRates || merchantRates.isEmpty()) {
			throw new BusinessException("未找到商户手续费",
					ExceptionCodeEnum.NO_FEE_RATE);
		}else{
			for(MerchantRateDto merchantRateDto : merchantRates){
				if(StringUtil.isEmpty(merchantRateDto.getLocalPayCode()))
					merchantRateDto.setLocalPayCode("0");//为了支持旧数据
				logger.info("###localPayCode="+localPayCode+",getLocalPayCode()="+merchantRateDto.getLocalPayCode());
				//用交易类型和交易模型取做全匹配，找不到取全部和全部的匹配，再找不到取交易类型的					
				if ((transType == merchantRateDto.getTransType())
						&&( modeType.equals(merchantRateDto.getTransMode()))
						&& localPayCode.equals(merchantRateDto.getLocalPayCode())) {
					merchantRate = merchantRateDto;
					break;
				}
				
				if(merchantRateDto.getTransType() == 0 
						&& "0".equals(merchantRateDto.getTransMode())
						&& localPayCode.equals(merchantRateDto.getLocalPayCode())){
					merchantRate = merchantRateDto;
					break;
				}
				if(transType ==merchantRateDto.getTransType()
						&& localPayCode.equals(merchantRateDto.getLocalPayCode())){
					merchantRate = merchantRateDto;
					break;
				}
			}
			if(null == merchantRate.getFeeType()){
				
				logger.info("未找到商户手续费配置");
				throw new BusinessException("未找到商户手续费配置",
						ExceptionCodeEnum.NO_FEE_RATE);
			}
		}
		logger.info("merchantRate：merchantRate.getMonthChargeRate()="+merchantRate.getMonthChargeRate()+"id="+merchantRate.getId()+"memberCode="+merchantRate.getMemberCode());
		//	
		String[] chargeRate = getCurrenctMonthChargeRate(merchantRate,resetMonthChargeRate(paymentOrderDTO.getpOExpandCreateDate()));
		logger.info("getCurrenctMonthChargeRate : "+Arrays.toString(chargeRate));
		if(chargeRate!=null){
			merchantRate.setChargeRate(chargeRate[0]);
			merchantRate.setFixedCharge(chargeRate[1]);
			merchantRate.setFixedCurrencyCode(chargeRate[2]);
		}
		
		String transExchangeRate = "1";
		
		String fixedCurrencyCode=StringUtil.isEmpty(merchantRate.getFixedCurrencyCode())
				?CurrencyCodeEnum.USD.getCode():merchantRate.getFixedCurrencyCode();
		if(!"CNY".equals(fixedCurrencyCode)){
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sourceCurrency", fixedCurrencyCode);
			param.put("targetCurrency", "CNY");
			param.put("status", "1");
			param.put("memberCode", String.valueOf(partnerSettlementOrder.getPartnerId()));
			if (!StringUtils.isEmpty(partnerSettlementOrder.getCardOrg())) {
				param.put("cardOrg", partnerSettlementOrder.getCardOrg());
			}
			param.put("orderAmount", partnerSettlementOrder.getOrderAmount());
			param.put("ltaCurrencyCode", "USD");
			param.put("point", getTime());
			
			SettlementRate transRate = channelService
					.getCurrencyRateService().getNewSettlementRate(param);
			if(null == transRate){
				throw new BusinessException("settlementRate not exists",
						ExceptionCodeEnum.NO_SETTLEMENT_RATE);
			}
			transExchangeRate = transRate.getExchangeRate();
		}
		String tranRateCny = "1";
		
				if(!"CNY".equals(partnerSettlementOrder.getCurrencyCode())){
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("sourceCurrency", partnerSettlementOrder.getCurrencyCode());
					param.put("targetCurrency", "CNY");
					param.put("status", "1");
					param.put("memberCode", String.valueOf(partnerSettlementOrder.getPartnerId()));
					if (!StringUtils.isEmpty(partnerSettlementOrder.getCardOrg())) {
						param.put("cardOrg", partnerSettlementOrder.getCardOrg());
					}
					param.put("orderAmount", partnerSettlementOrder.getOrderAmount());
					param.put("ltaCurrencyCode", "USD");
					param.put("point", getTime());
					
					SettlementRate transRate2 = channelService
							.getCurrencyRateService().getNewSettlementRate(param);
					if(null == transRate2){
						throw new BusinessException("settlementRate not exists",
								ExceptionCodeEnum.NO_SETTLEMENT_RATE);
					}
					tranRateCny = transRate2.getExchangeRate();
				}
		
		partnerSettlementOrder.setOrderAmountCny(new BigDecimal(orderAmount).multiply(new BigDecimal(tranRateCny)).longValue());
		
		BigDecimal fixedFeeS= new BigDecimal("0");
		BigDecimal preFeeS= new BigDecimal("0");
		//1-固定费用，2-费率,6-固定+费率
		if(merchantRate.getFeeType() == 1){
			feeAmount = merchantRate.getFixedCharge();
			if(!CurrencyCodeEnum.USD.getCode().equals(settlementCurrencyCode)){
				feeAmount = new BigDecimal(feeAmount).multiply(new BigDecimal(transExchangeRate)).toString();
			}
			
			//feeAmount = new BigDecimal(feeAmount).multiply(new BigDecimal("1000")).toString(); 
			// change for 0.001 issue
			feeAmount = String.valueOf(new BigDecimal(feeAmount).multiply(new BigDecimal("1000")).longValue());
			//partnerSettlementOrder.setFixedFee(merchantRate.getFixedCharge());
			//fixed by nico.shao 2016-07-11 fixed_fee 从元转换为厘 
			{
				String fixedFee_yuan= merchantRate.getFixedCharge();		//单位为元
				String fixedFee_li = new BigDecimal(fixedFee_yuan).multiply(new BigDecimal("1000")).toString(); 
				partnerSettlementOrder.setFixedFee(fixedFee_li);
			}
			partnerSettlementOrder.setFixedFeeSettlementAmount(feeAmount);
			fixedFeeS = new BigDecimal(feeAmount);
			
		}else if(merchantRate.getFeeType() == 2){
			feeAmount = new BigDecimal(amount).multiply(new BigDecimal(rate))
					.multiply(new BigDecimal(merchantRate.getChargeRate())).divide(new BigDecimal("100")).toString();
			partnerSettlementOrder.setPerFee((new BigDecimal(feeAmount).multiply(new BigDecimal("10"))).longValue());
			preFeeS = new BigDecimal(feeAmount);
			
		}else if(merchantRate.getFeeType() == 6){
			BigDecimal chargeFee = new BigDecimal(amount).multiply(new BigDecimal(rate))
					.multiply(new BigDecimal(merchantRate.getChargeRate())).divide(new BigDecimal("100"));			
			partnerSettlementOrder.setPerFee(chargeFee.multiply(new BigDecimal("10")).longValue());
			preFeeS = chargeFee;
			
			feeAmount = merchantRate.getFixedCharge();
			if(!CurrencyCodeEnum.USD.getCode().equals(settlementCurrencyCode)){
				feeAmount = new BigDecimal(feeAmount).multiply(new BigDecimal(transExchangeRate)).toString();
			}
			
			//feeAmount = new BigDecimal(feeAmount).multiply(new BigDecimal("1000")).toString(); 
			// change for 0.001 issue
			feeAmount = String.valueOf(new BigDecimal(feeAmount).multiply(new BigDecimal("1000")).longValue());
			//partnerSettlementOrder.setFixedFee(merchantRate.getFixedCharge());	//fixed by nico.shao 2016-07-11 fixed_fee 从元转换为厘 
			{
				String fixedFee_yuan= merchantRate.getFixedCharge();		//单位为元
				String fixedFee_li = new BigDecimal(fixedFee_yuan).multiply(new BigDecimal("1000")).toString(); 
				partnerSettlementOrder.setFixedFee(fixedFee_li);
			}
			partnerSettlementOrder.setFixedFeeSettlementAmount(feeAmount);
			fixedFeeS = new BigDecimal(feeAmount);
			
			feeAmount = chargeFee.add(new BigDecimal(feeAmount)).toString();
		}
		
		//----------------------------------
		
		Long amount200 = amount;
		
		long fee = new BigDecimal(feeAmount).longValue();
		if (logger.isInfoEnabled()) {
			logger.info("amount200:" + amount200);
			logger.info("settlement id:" + orderId + ",fee:" + fee);
		}
		
		//不够结算手续费，不清算
		long dAmount = new BigDecimal(amount).multiply(
				new BigDecimal(rate)).longValue();
		long dAssureAmount = new BigDecimal(assureAmount).multiply(
				new BigDecimal(rate)).longValue();
		
		//2015年11月7日13:53:00 peiyu.yang 现修改为如果订单金额不够扣手续费，就全部当成手续费扣除
		long accountingAmount = dAmount - new BigDecimal(feeAmount).longValue()-dAssureAmount;
		
		logger.info("orderAmount:"+orderAmount+" ,rate:"+rate+" ,dAmount: "+dAmount+" ,feeAmount:"+feeAmount
				+" ,accountingAmount: "+accountingAmount+" ,dAssureAmount: "+dAssureAmount);
		//----------------------------------
		
		
		logger.info("partner settlement order id:" + partnerSettlementOrder.getId() + 
				",get USD to " + settlementCurrencyCode + ",rate:" + transExchangeRate 
				+ ",partner settlement rata:" + rate);
		
		if(!StringUtil.isEmpty(rate)){
			BigDecimal settlementAmount = new BigDecimal(amount)
			.multiply(new BigDecimal(rate));
			partnerSettlementOrder.setSettlementAmount(settlementAmount.longValue());
		}
		
		DecimalFormat df = new DecimalFormat("0.000");
		df.setRoundingMode(RoundingMode.DOWN);
		
		String assureAmountStr = df.format((new BigDecimal(partnerSettlementOrder.getAssureAmount()))
				.multiply(new BigDecimal(rate)).divide(new BigDecimal("1000")));
		logger.info("保证金金额："+assureAmountStr);
		String perFeeAmount = df.format(new BigDecimal(partnerSettlementOrder.getPerFee())
		.divide(new BigDecimal("10000")));
		logger.info("perFeeAmount："+perFeeAmount);
		String settleAmountStr = new BigDecimal(partnerSettlementOrder.getSettlementAmount())
		.divide(new BigDecimal("1000")).toString();
		logger.info("settleAmountStr : "+settleAmountStr);
		String fixedFeeAmount = df.format(new BigDecimal(partnerSettlementOrder.getFixedFeeSettlementAmount())
		.divide(new BigDecimal("1000")));
		logger.info("fixedFeeAmount : "+fixedFeeAmount);
		BigDecimal recordAmount = new BigDecimal(settleAmountStr).subtract(new BigDecimal(fixedFeeAmount))
				.subtract(new BigDecimal(perFeeAmount)).subtract(new BigDecimal(assureAmountStr));
		logger.info("入账金额："+recordAmount);
		recordAmount = recordAmount.multiply(new BigDecimal("1000"));
		logger.info("recordAmount : "+recordAmount.longValue());
		logger.info("recordAmount:"+recordAmount+" ,accountingAmount:"+accountingAmount);
		if (accountingAmount <= 0) {
			partnerSettlementOrder.setCreditFlag(OrderCreditEnum.creditFlag2.getType());
			logger.info("入账金额不够扣手续费，风控拒绝！清算订单号="+partnerSettlementOrder.getId());
		}
		partnerSettlementOrder.setSettlementRate(rate);
		partnerSettlementOrder.setFee(fee);
		partnerSettlementOrder.setRecordedAmount(recordAmount.longValue());
		partnerSettlementOrder.setApplyDate(new Date());
		partnerSettlementOrder.setSettlementCurrencyCode(settlementCurrencyCode);
		
		if(null!=dayRate && !"".equals(dayRate) && null !=partnerSettlementOrder.getApplyDate())
		{
			// 计息天数
			Long days = DateUtil.subtraction(
					partnerSettlementOrder.getSettlementDate(), new Date());
			Calendar cl = Calendar.getInstance();
			cl.setTime(partnerSettlementOrder.getApplyDate());
			int hour = cl.get(cl.HOUR_OF_DAY);
			if (hour > 14) {
				days = days - 1;
			}
		if(days<0)
		{
			days=0L;
		}
			BigDecimal rate2 = new BigDecimal(dayRate).divide(new BigDecimal("100"),7,BigDecimal.ROUND_HALF_UP).add(BigDecimal.ONE);
			MathContext mc = new MathContext(8);
			BigDecimal rateDays = rate2.pow(days.intValue(), mc);
			BigDecimal subRate=rateDays.subtract(BigDecimal.ONE);
			if(subRate.longValue()>1L)
			{
				subRate=BigDecimal.ONE;
			}
			BigDecimal charge = subRate
					.multiply(recordAmount)
					.setScale(3, BigDecimal.ROUND_HALF_UP);
			
			partnerSettlementOrder.setCharge(charge.longValue());
			if (flag) {
			// 授信提前清算记账
			settlementRnTx(partnerSettlementOrder, settlementCurrencyCode);
			}
		}
		
	}
	@Override
	public void BuildOrderCredit2(PartnerSettlementOrder partnerSettlementOrder,String settlementCurrencyCode,String dayRate,Boolean flag) 
			throws BusinessException {
		
		String orderId = partnerSettlementOrder.getId().toString();
		Long orderAmount = partnerSettlementOrder.getOrderAmount();
		Long amount = partnerSettlementOrder.getAmount();
		Long assureAmount = partnerSettlementOrder.getAssureAmount();
		Long partnerId = partnerSettlementOrder.getPartnerId();
		String merchantOrderId = partnerSettlementOrder.getOrderId();
		logger.info("清算金额：" + amount + "，订单金额" + orderAmount + "，保证金金额" + assureAmount);
		if (partnerSettlementOrder.getSettlementFlg() == SettlementFlagEnum.REFUND.getCode()) {

			logger.info("退款中不清算.." + partnerSettlementOrder.getId());
			return;
		}

		String tranRateCny = "1";

		if (!"CNY".equals(partnerSettlementOrder.getCurrencyCode())) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sourceCurrency", partnerSettlementOrder.getCurrencyCode());
			param.put("targetCurrency", "CNY");
			param.put("status", "1");
			param.put("memberCode", String.valueOf(partnerSettlementOrder.getPartnerId()));
			if (!StringUtils.isEmpty(partnerSettlementOrder.getCardOrg())) {
				param.put("cardOrg", partnerSettlementOrder.getCardOrg());
			}
			param.put("orderAmount", partnerSettlementOrder.getOrderAmount());
			param.put("ltaCurrencyCode", "USD");
			param.put("point", getTime());

			SettlementRate transRate2 = channelService.getCurrencyRateService()
					.getNewSettlementRate(param);
			if (null == transRate2) {
				throw new BusinessException("settlementRate not exists",
						ExceptionCodeEnum.NO_SETTLEMENT_RATE);
			}
			tranRateCny = transRate2.getExchangeRate();
		}

		partnerSettlementOrder.setOrderAmountCny(new BigDecimal(orderAmount).multiply(
				new BigDecimal(tranRateCny)).longValue());
	}
	public static double getTime() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		double s = min / 100.0;
		double rst = hour + s;

		return rst;
	}
	// 如果清算订单中没有清算汇率就去查询当日的清算汇率,否则清算使用清算订单中的清算汇率
	private String getSettlementRate_liquidation(
			final PartnerSettlementOrder partnerSettlementOrder) {

		String rate = "1";
		Long partnerId = partnerSettlementOrder.getPartnerId();

		String settlementCurrencyCode = partnerSettlementOrder
				.getSettlementCurrencyCode();

		// 如果清算订单中没有清算汇率就去查询当日的清算汇率,否则清算使用清算订单中的清算汇率
		if (StringUtil.isEmpty(partnerSettlementOrder.getSettlementRate())) {
			SettlementRate settlementRate = channelService
					.getCurrencyRateService().getSettlementRate(
							partnerSettlementOrder.getCurrencyCode(),
							settlementCurrencyCode, null,
							String.valueOf(partnerId),
							partnerSettlementOrder.getCreateDate());

			if (null == settlementRate) {
				logger.info("未找到对应结算汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: "
						+ partnerSettlementOrder.getCurrencyCode()
						+ " ,targetCurrencyCode: " + settlementCurrencyCode);
				throw new BusinessException("settlementRate not exists",
						ExceptionCodeEnum.NO_SETTLEMENT_RATE);
			} else {
				rate = settlementRate.getExchangeRate();
				
				//partnerSettlementOrder.setSettlementRate(rate);   //这句话在最后会补上 .  为什么放在最后，不放在这里？ 
			}

		} else {
			rate = partnerSettlementOrder.getSettlementRate();
		}
		return rate;
	}
	
	/*
	 * 获取固定手续费的 汇率 .  如果 partnerSettlementOrder.getFeeRate()==null 会被设置
	 */
	private String getExchangeRate_liquidation(PartnerSettlementOrder partnerSettlementOrder,
			final MerchantRateDto merchantRate){
		
		Long partnerId = partnerSettlementOrder.getPartnerId();
		String transExchangeRate = "1";
		
		if(StringUtil.isEmpty(partnerSettlementOrder.getFeeRate())){
			SettlementRate transRate = channelService.getCurrencyRateService()
			          .getSettlementRate(StringUtil.isEmpty(merchantRate.getFixedCurrencyCode())
			        		  ?CurrencyCodeEnum.USD.getCode():merchantRate.getFixedCurrencyCode(), 
			        		  partnerSettlementOrder.getSettlementCurrencyCode(),null,
			        		  String.valueOf(partnerId), partnerSettlementOrder.getCreateDate());
			
			if(null == transRate){
				String logCode = StringUtil.isEmpty(merchantRate.getFixedCurrencyCode())? CurrencyCodeEnum.USD.getCode():merchantRate.getFixedCurrencyCode();
				logger.info("未找到对应手续费的交易汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: "
						+ logCode 
						+ " ,targetCurrencyCode: " + partnerSettlementOrder.getSettlementCurrencyCode());
				throw new BusinessException("settlementRate not exists",
						ExceptionCodeEnum.NO_SETTLEMENT_RATE);
			}
			transExchangeRate = transRate.getExchangeRate();
			partnerSettlementOrder.setFeeRate(transExchangeRate);	//add by sch 2016-06-06
			
		}else{
			transExchangeRate = partnerSettlementOrder.getFeeRate();
		}
		return transExchangeRate;
	}
	
	//计算 手续费的费率
	private MerchantRateDto getMerchantRate_liquidation(final PartnerSettlementOrder partnerSettlementOrder,
			final PaymentOrderDTO paymentOrderDTO){

		
		MerchantRateDto merchantRate = new MerchantRateDto();
		if(paymentOrderDTO.getTradeType().startsWith("40")){//40交易类型40开头的为 本地化交易
			merchantRate.setOrganisation("LOCAL"); //add by delin 设置默认 local 原因：没有卡号
			merchantRate.setCountryCode("LOCAL");
		}else{
			String cardNumber = paymentOrderDTO.getCardNo();
			if(!StringUtil.isEmpty(cardNumber)){
				CardBinInfo cardBinInfo = cardBinInfoService.getCardBinInfo(cardNumber.substring(0, 6));
				logger.info("商户结算查询到的cardbin信息是："+cardBinInfo);
				if(cardBinInfo!=null)
					merchantRate.setCountryCode(cardBinInfo.getCountryCode2());
			}
			String cardType = getCardType(cardNumber);
			merchantRate.setOrganisation(cardType);
		}
		
		Long partnerId = partnerSettlementOrder.getPartnerId();	//原来没有这句
		merchantRate.setMemberCode(partnerId);
		merchantRate.setDealCode(202);

		String payType="EDC";
        if(paymentOrderDTO!=null){
        	payType=paymentOrderDTO.getPayType();
        }		
		
		int transType = 0;
		if(TransTypeEnum.EDC.getCode().equals(payType)){
			transType = 1;
		}else if(TransTypeEnum.DCC.getCode().equals(payType)){
			transType = 2;
		}

		//Add by Mack 2016年5月19日15:52:14 跟业务确认交易模型只区分3D非3D
		String modeType = "2"; // 默认值非3D		
        if ( paymentOrderDTO.getTradeType().equals(TradeTypeEnum.THREEPAY.getCode())){
        	modeType = "1"; 
        }
        else if(paymentOrderDTO.getTradeType().startsWith("40")){ //add by delin 2016年6月27日 11:25:16 区分本地化
        	modeType = "3"; 
        }
        		        
		//Add End
        //Add by davis.guo 2016-06-28 添加Computop本地化支付方式判断----------------------
  		String localPayCode = "0"; // 默认值非Computop本地化支付
  		if("3".equals(modeType)){ //区分本地化
  			localPayCode = paymentOrderDTO.getOrgCode();// 
  		}
  		logger.info("######localPayCode="+localPayCode);
  		//Add End-------------------------------------------------------------------
      		
		//String feeAmount = "0";
		List<MerchantRateDto> merchantRates = merchantRateService.queryMerchantRate(merchantRate);
		if (null == merchantRates || merchantRates.isEmpty()) {
			throw new BusinessException("未找到商户手续费",
					ExceptionCodeEnum.NO_FEE_RATE);
		}else{ 
			for(MerchantRateDto merchantRateDto : merchantRates){
				if(StringUtil.isEmpty(merchantRateDto.getLocalPayCode()))
					merchantRateDto.setLocalPayCode("0");//为了支持旧数据
				logger.info("######localPayCode="+localPayCode+",getLocalPayCode()="+merchantRateDto.getLocalPayCode());
				//用交易类型和交易模型取做全匹配，找不到取全部和全部的匹配，再找不到取交易类型的					
				if ((transType == merchantRateDto.getTransType())
						&&( modeType.equals(merchantRateDto.getTransMode()))
						&& localPayCode.equals(merchantRateDto.getLocalPayCode())) {
					merchantRate = merchantRateDto;
					break;
				}
				
				if(merchantRateDto.getTransType() == 0 
						&& "0".equals(merchantRateDto.getTransMode())
						&& localPayCode.equals(merchantRateDto.getLocalPayCode())){
					merchantRate = merchantRateDto;
					break;
				}
				if(transType ==merchantRateDto.getTransType()
						&& localPayCode.equals(merchantRateDto.getLocalPayCode())){
					merchantRate = merchantRateDto;
					break;
				}
			}
			if(null == merchantRate.getFeeType()){

				logger.info("未找到商户手续费配置");
				throw new BusinessException("未找到商户手续费配置",
						ExceptionCodeEnum.NO_FEE_RATE);
			}
		}
		logger.info("merchantRate：merchantRate.getMonthChargeRate()="+merchantRate.getMonthChargeRate()+"id="+merchantRate.getId()+"memberCode="+merchantRate.getMemberCode());
		//	
		String[] chargeRate = getCurrenctMonthChargeRate(merchantRate,resetMonthChargeRate(paymentOrderDTO.getpOExpandCreateDate()));
		logger.info("getCurrenctMonthChargeRate : "+Arrays.toString(chargeRate));
		if(chargeRate!=null){
			merchantRate.setChargeRate(chargeRate[0]);
			merchantRate.setFixedCharge(chargeRate[1]);
			merchantRate.setFixedCurrencyCode(chargeRate[2]);
		}	
		return merchantRate;
	}
	
	
	/*
	 * 计算 固定手续费、百分比手续费 
	 * rate: 清算汇率
	 * transExchangeRate: 固定手续费交易汇率 
	 * 输出参数：
	 *   partnerSettlementOrder: 这里会改变 固定手续费、百分比手续费的比例
	 * 返回值： 手续费的总值   清算币种 
	 * 业务逻辑： 如果是清算金额为0 （清算前全额退款,并且该商户配置为不退手续费）  会做一个修正。 
	 *         在这种情况下 ，返回值 ！= Fixed_FeeSettlment(0) + Per_Fee(0)
	 */
	private String calcFixedFee_PerFee_liquidation(PartnerSettlementOrder partnerSettlementOrder,
			MerchantRateDto merchantRate,
			String rate,String transExchangeRate){
		// 数据库中 Fixed_Fee,Per_Fee,Fixed_FeeSettlment 这几个字段，缺省值都是0，所以，可以不用特地去设置为0 
		
		Long amount = partnerSettlementOrder.getAmount();	//清算金额
		
		String settlementCurrencyCode = partnerSettlementOrder
				.getSettlementCurrencyCode();
		
		String feeAmount = "0";  
		BigDecimal fixedFeeS= new BigDecimal("0");
		BigDecimal preFeeS= new BigDecimal("0");
		
		//1-固定费用，2-费率,6-固定+费率
		if(merchantRate.getFeeType() == 1){
			feeAmount = merchantRate.getFixedCharge();
			if(!CurrencyCodeEnum.USD.getCode().equals(settlementCurrencyCode)){
				feeAmount = new BigDecimal(feeAmount).multiply(new BigDecimal(transExchangeRate)).toString();
			}
			
			//feeAmount = new BigDecimal(feeAmount).multiply(new BigDecimal("1000")).toString(); 
			// change for 0.001 issue
			feeAmount = String.valueOf(new BigDecimal(feeAmount).multiply(new BigDecimal("1000")).longValue());
			//partnerSettlementOrder.setFixedFee(merchantRate.getFixedCharge());
			//fixed by nico.shao 2016-07-11 fixed_fee 从元转换为厘 
			{
				String fixedFee_yuan= merchantRate.getFixedCharge();		//单位为元
				String fixedFee_li = new BigDecimal(fixedFee_yuan).multiply(new BigDecimal("1000")).toString(); 
				partnerSettlementOrder.setFixedFee(fixedFee_li);
			}
			partnerSettlementOrder.setFixedFeeSettlementAmount(feeAmount);
			fixedFeeS = new BigDecimal(feeAmount);
			
		}else if(merchantRate.getFeeType() == 2){
			feeAmount = new BigDecimal(amount).multiply(new BigDecimal(rate))
					.multiply(new BigDecimal(merchantRate.getChargeRate())).divide(new BigDecimal("100")).toString();
		    partnerSettlementOrder.setPerFee((new BigDecimal(feeAmount).multiply(new BigDecimal("10"))).longValue());		    															
		    preFeeS = new BigDecimal(feeAmount);
		    
		}else if(merchantRate.getFeeType() == 6){
			BigDecimal chargeFee = new BigDecimal(amount).multiply(new BigDecimal(rate))
					.multiply(new BigDecimal(merchantRate.getChargeRate())).divide(new BigDecimal("100"));			
			partnerSettlementOrder.setPerFee(chargeFee.multiply(new BigDecimal("10")).longValue());
			preFeeS = chargeFee;

			feeAmount = merchantRate.getFixedCharge();
			if(!CurrencyCodeEnum.USD.getCode().equals(settlementCurrencyCode)){
				feeAmount = new BigDecimal(feeAmount).multiply(new BigDecimal(transExchangeRate)).toString();
			}
			
			//feeAmount = new BigDecimal(feeAmount).multiply(new BigDecimal("1000")).toString(); 
			// change for 0.001 issue
			feeAmount = String.valueOf(new BigDecimal(feeAmount).multiply(new BigDecimal("1000")).longValue());
			//partnerSettlementOrder.setFixedFee(merchantRate.getFixedCharge());
			//fixed by nico.shao 2016-07-11 fixed_fee 从元转换为厘 
			{
				String fixedFee_yuan= merchantRate.getFixedCharge();		//单位为元
				String fixedFee_li = new BigDecimal(fixedFee_yuan).multiply(new BigDecimal("1000")).toString(); 
				partnerSettlementOrder.setFixedFee(fixedFee_li);
			}
			partnerSettlementOrder.setFixedFeeSettlementAmount(feeAmount);
			fixedFeeS = new BigDecimal(feeAmount);
			
			feeAmount = chargeFee.add(new BigDecimal(feeAmount)).toString();
		}
					
		//如果清算金额为0，我们重新修复一下 手续费 2016-06-15
		//逻辑如下： 如果手续费是要还的，则所有手续费的值为0 
		if(amount.longValue() == 0){
			RefundFeeConf refundFeeConf = enterpriseBaseService.queryRefundFeeConfByCode(partnerSettlementOrder.getPartnerId());
			if((refundFeeConf==null) || (refundFeeConf.getRefundFixedFeeConf()==0)){
				logger.info("refundFeeConf is null or RefundFixedFeeConf=0    feeAmount=" + feeAmount);
				return feeAmount;
			}
			
			logger.info("清算金额为0，退款时固定手续费要还，fee set to zero");
			//如果固定手续field是要还的，则把手续费设置为0 
			feeAmount = "0";
			//partnerSettlementOrder.setFixedFee(....);  //这个值不参与计算
			partnerSettlementOrder.setFixedFeeSettlementAmount(feeAmount);
			partnerSettlementOrder.setPerFee(Long.valueOf(0));				
		}
				
		return feeAmount;
	}
	
	/*
	 * 重新计算 保证金, 输出是标价币种 
	 * 
	 */
	public Long calcAssureAmount_liquidation(final PartnerSettlementOrder partnerSettlementOrder){
	
		EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService
					.queryEnterpriseBaseByMemberCode(partnerSettlementOrder.getPartnerId());

		Integer assurePercentage = enterpriseBaseDto.getAssurePercentage();
		if(assurePercentage > 100){
			return Long.valueOf(0);
		}
		
		//Long orderAmount = partnerSettlementOrder.getOrderAmount();
		Long amount = partnerSettlementOrder.getAmount();
		
		Long assureAmount = new BigDecimal(amount)
						.multiply(new BigDecimal(assurePercentage))
						.divide(new BigDecimal("100")).longValue();
		
		logger.info("CalcAmount assurePercentage=[" + assurePercentage + "]" + "amount=[" + amount
				+ "] assureAmount = [" + assureAmount + "]");
		
		return assureAmount;
	}
	
	
	/*
	 * 记账 版本1： 只使用 202,203,205 
	 * 输出参数： PartnerSettlementOrder,assureAmount
	 * 		   输出结果，没有意义
	 * 其他参数都是不变的 
	 * 返回值： true:  记账成功
	 *       false: 记账失败 
	 */
	public boolean doAccounting_liquidation_version1(
			PartnerSettlementOrder partnerSettlementOrder, Long assureAmount,
			final ChannelOrderDTO channelOrderDTO, final Long amount,
			final long dAmount, final long dAssureAmount, final long dfee,
			final Long partnerId, final String orderId,
			final String merchantOrderId, final String settlementCurrencyCode,
			final String rate) {

		try {
			partnerSettlementOrder.setSettlementRate(rate);
			
			// 清算订单上最好有一个标志位，用来记录发生了如下三种情况的哪一种
			if (dAmount <= dfee) { // 清算金额 < 手续费 ，所有清算金额 ==> 手续费 . 需要重新计算手续费

				// bug fixed 2016-06-06 PerFee 这个字段实际上，是 *10,所以在这个地方，先除以10，再乘以10来计算
				// 这种情况下， fixed_fee * settlementRate > setFixedFeeSettlementAmount, 是不匹配的。

				// long perFee = partnerSettlementOrder.getPerFee().longValue();
				long perFee = partnerSettlementOrder.getPerFee().longValue() / 10;

				// 重新计算百分比手续费和固定手续费, 因为有可能小于 百分比手续费 或者固定手续费
				if (perFee >= dAmount) {
					// partnerSettlementOrder.setPerFee(dAmount);
					partnerSettlementOrder.setPerFee(dAmount * 10);
					partnerSettlementOrder.setFixedFeeSettlementAmount("0");

				} else {
					long tmp = dAmount - perFee;
					partnerSettlementOrder.setFixedFeeSettlementAmount(String
							.valueOf(tmp));
				}

				partnerSettlementOrder.setRecordedAmount(0L);
				partnerSettlementOrder.setAssureAmount(0L);
				partnerSettlementOrder.setFee(dAmount);

				// 清算金额小于手续费时，全部清算为手续费
				logger.info("清算金额小于手续费时，全部清算为手续费, settlement id:" + orderId
						+ ",settlement damount:" + dAmount + ",Fee:"
						+ partnerSettlementOrder.getFee() + ",fixedFee="
						+ partnerSettlementOrder.getFixedFeeSettlementAmount()
						+ ",perFee=" + partnerSettlementOrder.getPerFee());

				doAccounting_200_205(orderId, dAmount, settlementCurrencyCode,
						merchantOrderId);

			} else if (dAmount <= dfee + dAssureAmount) {
				// 需要重新计算 保证金的金额
				// 这个情况其实 很少发生，因为 assureAmount 已经根据 清算金额重新做了处理

				doAccounting_200_205(orderId, dfee, settlementCurrencyCode,
						merchantOrderId);

				// 重新计算assureAmount,asureAmount 是交易币种，所以需要 / 清算汇率
				assureAmount = new BigDecimal(dAmount - dfee).divide(
						new BigDecimal(rate)).longValue();

				doAccounting_200_203(partnerId, orderId, assureAmount,
						settlementCurrencyCode, rate, merchantOrderId);

				partnerSettlementOrder.setAssureAmount(assureAmount); // 重新把assureAmount
																		// 设置一下
				partnerSettlementOrder.setRecordedAmount(0L);
				partnerSettlementOrder.setFee(Long.valueOf(dfee));

				// 清算金额小于手续费时，全部清算为手续费
				logger.info("清算金额小于手续费时，全部清算为手续费, settlement id:" + orderId
						+ ",settlement damount:" + dAmount + ",Fee:"
						+ partnerSettlementOrder.getFee() + ",fixedFee="
						+ partnerSettlementOrder.getFixedFeeSettlementAmount()
						+ ",perFee=" + partnerSettlementOrder.getPerFee()
						+ ",dAassureAmount=" + dAssureAmount
						+ ",calcAssureAmount=" + (dAmount - dfee)
						+ ",assureAmount=" + assureAmount);
			} else {

				Long recordAmount = Long
						.valueOf(dAmount - dfee - dAssureAmount);

				// 这个可能会产生过大的异常 ,一笔单子大于 100万，我们认为是有问题的
				if (recordAmount < 0 || recordAmount > 1000000000) {
					// 报警，并且爆出异常
					logger.info("recordAmount =" + recordAmount + ",is error");
					throw new BusinessException("recordAmount error",
							ExceptionCodeEnum.ORDERAMOUNT_ERROR);

				}

				// 货币兑换
				Long amount200 = amount;

				doAccounting_200_200(orderId, amount200,
						channelOrderDTO.getCurrencyCode(), merchantOrderId);
				doAccounting_200_201(orderId, amount, settlementCurrencyCode,
						rate, merchantOrderId);
				doAccounting_200_202_new(partnerId, orderId, recordAmount,
						assureAmount, settlementCurrencyCode,
						partnerSettlementOrder, Long.valueOf(dfee),
						merchantOrderId);

				// 先扣除保证金
				doAccounting_200_203(partnerId, orderId, assureAmount,
						settlementCurrencyCode, rate, merchantOrderId);

				partnerSettlementOrder.setFee(Long.valueOf(dfee));
				partnerSettlementOrder.setRecordedAmount(recordAmount);
				partnerSettlementOrder.setAssureAmount(assureAmount); // 重新把assureAmount
																		// 设置一下
			}

			partnerSettlementOrder.setSettlementFlg(1);

			partnerSettlementOrderService
					.updatePartnerSettlementOrder(partnerSettlementOrder);

			paymentOrderService.updateSettlementFlg(
					partnerSettlementOrder.getPaymentOrderNo(), 1);
		
		} catch (Exception e) {
			logger.error("do accounting error,settement orderId:"
					+ partnerSettlementOrder.getId(), e);
			Map<String, String> data = new HashMap<String, String>();
			data.put("first",
					"系统异常:清算记账失败,txncore:PaymentServiceImpl-settlementOrderId:"
							+ partnerSettlementOrder.getId());
			data.put("keyword1", "清算记账失败");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			data.put("keyword2", formatter.format(new Date()));
			data.put("remark", "请尽快检查！");
			this.sendAlertMsg(data);
			partnerSettlementOrder.setSettlementFlg(2);
			partnerSettlementOrderService
					.updatePartnerSettlementOrder(partnerSettlementOrder);
			return false;
		}
		return true;
	}
	


	/*检查帐户余额是否足够扣款
	 *返回值：true  表示余额是够的
	 *      false 表示余额不够
	 */	
	private boolean checkAcctBalanceEnough(String settlementCurrencyCode,
			Long partnerId,long dfeeAmount) {
		
		try {
			Integer acctType = AcctTypeEnum
					.getBasicAcctTypeByCurrency(settlementCurrencyCode);
			AcctAttribDto acctAttribDto = accountQueryService
					.doQueryAcctAttribNsTx(partnerId, acctType);
			if (acctAttribDto != null && acctAttribDto.getAcctCode() != null) {
				AcctDto acctDto = acctService.queryAcctByAcctCode(acctAttribDto
						.getAcctCode());

				if (dfeeAmount > acctDto.getBalance()) {
					logger.info("余额不够  acctCode="+ acctAttribDto.getAcctCode() + ",dfeeAmount=" + dfeeAmount + ",acctBalance=" + acctDto.getBalance());
					return false;
				}
				return true;
			}
		} catch (Exception e) {
			logger.error("check Acct failed");
			return false;
		}
		
		return false;		
	}
	
	/*
	 * 使用了 202,203,205,206 等，业务逻辑参见 
	 * 如果里面的记账函数有问题， 会抛出异常
	 * 余额不够，也会抛出异常  
	 */
	public boolean doAccounting_liquidation_version2(
			PartnerSettlementOrder partnerSettlementOrder, Long assureAmount,
			final ChannelOrderDTO channelOrderDTO, final Long amount,
			final long dAmount, final long dAssureAmount, final long dfee,
			final Long partnerId, final String orderId,
			final String merchantOrderId, final String settlementCurrencyCode,
			final String rate) {

		try {
			partnerSettlementOrder.setSettlementRate(rate);
			
			logger.info("清算-记账  orderId=" + orderId 
						+ ",settlement dAmount=" + dAmount
						+ ",dAssureAmount= " + dAssureAmount 
						+ ",dFee=" + dfee 
						+ ",fee=" + partnerSettlementOrder.getFee() 
						+ ",fixed_fee=" + partnerSettlementOrder.getFixedFeeSettlementAmount() 
						+ ",per_fee(need / 10)=" + partnerSettlementOrder.getPerFee());
			
			if (dAmount == 0)  {
				//全额退款的情况 				
				if (dfee == 0) {
					//全额退款，要退固定手续费的情况 , 不用做任何记账
					partnerSettlementOrder.setRecordedAmount(0L);
					partnerSettlementOrder.setAssureAmount(0L);
					partnerSettlementOrder.setFee(0L);
					partnerSettlementOrder.setAssureSettlementFlg(1);		//直接把保证金标志改为1
					//partnerSettlementOrder.setFixedFeeSettlementAmount("0");	//这两个字段无需设置，肯定都是0
					//partnerSettlementOrder.setPerFee(Long.valueOf(0));							
					
					logger.info("清算金额=手续费=0, 无需做任何事情");
							
				} else {
					//全额退款，不退固定手续费，所以要做一笔扣款 
					logger.info("清算金额=0,手续费>0 做206记账 ");
								
					if(checkAcctBalanceEnough(settlementCurrencyCode, partnerId, dfee)){
														 
						doAccounting_200_206(partnerId,orderId,dfee,settlementCurrencyCode,merchantOrderId);
						
						partnerSettlementOrder.setRecordedAmount(0L);
						partnerSettlementOrder.setAssureAmount(0L);
						partnerSettlementOrder.setFee(0L);
						
						partnerSettlementOrder.setAssureSettlementFlg(1); //直接把 保证金清算标志改为1						
						partnerSettlementOrder.setFixedFeeSettlementAmount("0"); //标志为0 	
						partnerSettlementOrder.setPerFee(Long.valueOf(0));
						partnerSettlementOrder.setSmallOrderFixedFeeAmount(Long.valueOf(dfee)); //2016-07-11,最好是用fixed_fee
						
						logger.info("清算金额=0  手续费 >0  206=[" + dfee +"]");
					}
					else {
						logger.error ("清算，做206 记账，余额不够"); 
						
						throw new BusinessException("balance not enough",
								ExceptionCodeEnum.ACCT_NO_SAVE_ACCOUNT);

					}
				}

			} else	if (dAmount <= dfee + dAssureAmount) { 	// 清算金额 < 手续费 +保证金数字  
				// (1)  从基本户进行扣款 206 . 扣款因为存在意外，所以先进行扣款，然后再进行 清算充值   
				// (2)  清算保证金， 保证金 金额不再重新计算
				// (3)  清算 基本户（dAmount-dfee - 百分比手续费)  
				
				//  在同一个事务中，可以做206分录  减少余额 ，然后 202分录 增加余额么？  可以的 
				
				//首先，我们要确保 dAmount >= dAssureAmount + 百分比手续费 
				long perFee = partnerSettlementOrder.getPerFee().longValue() / 10;
				
				if(dAmount < dAssureAmount + perFee ){ 
					//
					logger.error("dAmount < dAssureAmount + perFee" );
					throw new BusinessException("recordAmount error",
							ExceptionCodeEnum.ORDERAMOUNT_ERROR);
				}
			
				//需要确认　perFee + fixedFee = dFee ,否则的话，逻辑也是错误的　
				
				//先检查 基本户，够不够手续费
				long fixed_fee = Long.valueOf(partnerSettlementOrder.getFixedFeeSettlementAmount()).longValue();
				if(!checkAcctBalanceEnough(settlementCurrencyCode, partnerId, dfee)){
					throw new BusinessException("balance not enough",
							ExceptionCodeEnum.ACCT_NO_SAVE_ACCOUNT);
				}
								
				//先扣基本户 
				doAccounting_200_206(partnerId,orderId,fixed_fee,settlementCurrencyCode,merchantOrderId);
					
				Long recordAmount = Long
						.valueOf(dAmount - perFee - dAssureAmount);  //这里的计算公式和下面的不一样

				// 这个可能会产生过大的异常 ,一笔单子大于 100万，我们认为是有问题的 mack comment below
				/* if (recordAmount < 0 || recordAmount > 1000000000) {
					// 报警，并且爆出异常
					logger.error("recordAmount =" + recordAmount + ",is error");
					throw new BusinessException("recordAmount error",
							ExceptionCodeEnum.ORDERAMOUNT_ERROR);

				} */
				
				// 货币兑换
				Long amount200 = amount;

				doAccounting_200_200(orderId, amount200,
						channelOrderDTO.getCurrencyCode(), merchantOrderId);
				doAccounting_200_201(orderId, amount, settlementCurrencyCode,
						rate, merchantOrderId);
				
				doAccounting_200_202_new(partnerId, orderId, recordAmount,
						assureAmount, settlementCurrencyCode,
						partnerSettlementOrder, Long.valueOf(perFee),
						merchantOrderId);
				
				//结算保证金户
				doAccounting_200_203(partnerId, orderId, assureAmount,
						settlementCurrencyCode, rate, merchantOrderId);
				
				partnerSettlementOrder.setFee(Long.valueOf(perFee));
				partnerSettlementOrder.setFixedFeeSettlementAmount("0");								
				partnerSettlementOrder.setRecordedAmount(recordAmount);
				partnerSettlementOrder.setAssureAmount(assureAmount); 	
				partnerSettlementOrder.setSmallOrderFixedFeeAmount(Long.valueOf(fixed_fee)); //2016-07-13，这里不能用dfee,
				
				// 清算金额小于手续费时，全部清算为手续费
				logger.info("清算金额小于 手续费+保证金   206=[" + fixed_fee
						+"],203=[" + assureAmount 
						+"],202=[" + recordAmount + ","  + perFee + "]");

			} else {

				Long recordAmount = Long
						.valueOf(dAmount - dfee - dAssureAmount);

				// 这个可能会产生过大的异常 ,一笔单子大于 100万，我们认为是有问题的 mack comment blow request by ke.huang
              /*  if (recordAmount < 0 || recordAmount > 1000000000) {
					// 报警，并且爆出异常
					logger.error("recordAmount =" + recordAmount + ",is error");
					throw new BusinessException("recordAmount error",
							ExceptionCodeEnum.ORDERAMOUNT_ERROR);

				} */

				// 货币兑换
				Long amount200 = amount;

				doAccounting_200_200(orderId, amount200,
						channelOrderDTO.getCurrencyCode(), merchantOrderId);
				doAccounting_200_201(orderId, amount, settlementCurrencyCode,
						rate, merchantOrderId);
				doAccounting_200_202_new(partnerId, orderId, recordAmount,
						assureAmount, settlementCurrencyCode,
						partnerSettlementOrder, Long.valueOf(dfee),
						merchantOrderId);

				// 先扣除保证金
				doAccounting_200_203(partnerId, orderId, assureAmount,
						settlementCurrencyCode, rate, merchantOrderId);

				partnerSettlementOrder.setFee(Long.valueOf(dfee));
				partnerSettlementOrder.setRecordedAmount(recordAmount);
				partnerSettlementOrder.setAssureAmount(assureAmount); // 重新把assureAmount
																		// 设置一下
				
				logger.info("清算　正常流程 203=[" + assureAmount 
						+"],202=[" + recordAmount + ","  + dfee + "]");				
			}

			partnerSettlementOrder.setSettlementFlg(1);

			partnerSettlementOrderService
					.updatePartnerSettlementOrder(partnerSettlementOrder);


			paymentOrderService.updateSettlementFlg(
					partnerSettlementOrder.getPaymentOrderNo(), 1);
			
			logger.info("记账  结束");
		
		} catch (Exception e) {
			logger.error("do accounting error,settement orderId:"
					+ partnerSettlementOrder.getId(), e);
			Map<String, String> data = new HashMap<String, String>();
			data.put("first",
					"系统异常:清算记账失败,txncore:PaymentServiceImpl-settlementOrderId:"
							+ partnerSettlementOrder.getId());
			data.put("keyword1", "清算记账失败");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			data.put("keyword2", formatter.format(new Date()));
			data.put("remark", "请尽快检查！");
			this.sendAlertMsg(data);
			partnerSettlementOrder.setSettlementFlg(2);
			partnerSettlementOrderService
					.updatePartnerSettlementOrder(partnerSettlementOrder);
			return false;
		}
		return true;
	}
	
	/*
	 * 新的代码 
	 */
	public void liquidationRnTx2(PartnerSettlementOrder partnerSettlementOrder) {
		
		//(0) 如果有多个程序在执行的话，应该重新读取 partnerSettlementOrder,否则容易出现同一个订单多次清算的情况
		
		String orderId = partnerSettlementOrder.getId().toString();
		Long orderAmount = partnerSettlementOrder.getOrderAmount();
		Long amount = partnerSettlementOrder.getAmount();
		Long assureAmount = partnerSettlementOrder.getAssureAmount();
		Long partnerId = partnerSettlementOrder.getPartnerId();
		String merchantOrderId = partnerSettlementOrder.getOrderId();
		String settlementCurrencyCode = partnerSettlementOrder
				.getSettlementCurrencyCode();
		
		//(1)判断是否退款中 
		logger.info("清算过程开始：OrderId="+ orderId + ",清算金额="+amount+"，订单金额="+orderAmount+"，保证金金额="+assureAmount);
		if(partnerSettlementOrder.getSettlementFlg()==SettlementFlagEnum.REFUND.getCode()){			
			logger.info("退款中不清算.." + orderId);
			return;
		}
		
		//(2)获取清算汇率，如果在构造清算订单时，没有获取到，会降低要求，重新获取一次
		String rate = getSettlementRate_liquidation(partnerSettlementOrder);

		//(3)获取渠道订单、支付订单的信息  ： 渠道订单的作用是获取渠道支付币种：这个币种可以进一步存在清算订单中去么？ 可以减少一次查询  
		//							支付订单的作用是计算费率的时候用到		
		ChannelOrderDTO channelOrderDTO = channelOrderService
				.queryByTradeOrderNo(partnerSettlementOrder.getPaymentOrderNo());
		
		//UPDATE BY CHMA,GET CONFIG FEE 
		//PaymentOrderExpand paymentOrderExpand = paymentOrderExpandDAO.queryPayOrderExpandByPayNO(partnerSettlementOrder.getPaymentOrderNo());
		// 一次性查询 PaymentOrderExpand 表的卡号 和创建时间 减少与数据库的交互次数 delin.dong 2016年5月19日 15:27:27	
		PaymentOrderDTO paymentOrderDTO = paymentOrderService.queryByPaymentOrderNo(partnerSettlementOrder.getPaymentOrderNo());
		
		//(4)获取 手续费费率 
		MerchantRateDto  merchantRate = getMerchantRate_liquidation(partnerSettlementOrder,paymentOrderDTO);
		
		//(5)获取固定手续费的 交易汇率(手续费币种==>清算币种)
		String transExchangeRate = getExchangeRate_liquidation(partnerSettlementOrder,merchantRate);
		
	    //(6)获得 手续费的费用  ,  feeAmount 是手续费总和  （清算币种）  
		//fixedFee / PerFee/ fixed_fee_settlmentAomount  已经分别设置在  partnerSettlementOrder 相关字段中去了 
		//如果是全额退款（不退固定手续费）， perFee= 0 , fixed_fee_settlmentAomount 被调整为0， feeAmount = 原来的固定手续费 
		String feeAmount = calcFixedFee_PerFee_liquidation(partnerSettlementOrder, merchantRate, rate, transExchangeRate);
			
		//(7)  重新计算保证金 金额 ----------------------------------
				
		//所有d 开头的变量，全部是 清算币种

		long dfee = new BigDecimal(feeAmount).longValue();		//手续费金额 
			
		long dAmount = new BigDecimal(amount).multiply(
				new BigDecimal(rate)).longValue();		//清算金额_清算币种   总的
																																
		/* 重新计算保证金   只有订单金额和清算金额不匹配，才需要重新计算*/
		if( (orderAmount.longValue() > 0) && (orderAmount.longValue() != amount.longValue())) {				
			//重新读取一次手续费配置，再计算 
			assureAmount = calcAssureAmount_liquidation(partnerSettlementOrder);
		}
		
		//partnerSettlementOrder.setAssureAmount(assureAmount);  //重新把assureAmount 设置一下,记账函数中会调用的，这里无需调用
					
		long dAssureAmount = new BigDecimal(assureAmount).multiply(
				new BigDecimal(rate)).longValue();
		
		//2015年11月7日13:53:00 peiyu.yang 现修改为如果订单金额不够扣手续费，就全部当成手续费扣除
		//long accountingAmount = dAmount - dfee -dAssureAmount;	//这个值，其实已经没有用了 
		
		logger.info("orderAmount:"+orderAmount+" ,rate:"+rate +" ,dAmount(清算金额): "+dAmount+" ,feeAmount(手续费):"+ dfee
				+ ",dAssureAmount(保证金): "+dAssureAmount);
		//----------------------------------
				
		logger.info("partner settlement order id:" + partnerSettlementOrder.getId() + 
				",get USD to " + settlementCurrencyCode + ",rate:" + transExchangeRate 
				+ ",partner settlement rata:" + rate);
		
		//if(!StringUtil.isEmpty(rate)){   //这个代码是多余的 
		//	BigDecimal settlementAmount = new BigDecimal(amount)
		//	.multiply(new BigDecimal(rate));
		//	partnerSettlementOrder.setSettlementAmount(settlementAmount.longValue());
		//}
		
		partnerSettlementOrder.setSettlementAmount(dAmount);  //所有的清算金额
		
		/*  这里是计算 清算金额， 为什么又重新写一大堆代码，是因为 最后一位对不齐么  ?
		 * 这里主要是为了计算 recordAmount 
		DecimalFormat df = new DecimalFormat("0.000");
		df.setRoundingMode(RoundingMode.DOWN);
		
		String assureAmountStr = df.format((new BigDecimal(partnerSettlementOrder.getAssureAmount()))
				                      .multiply(new BigDecimal(rate)).divide(new BigDecimal("1000")));
		logger.info("保证金金额："+assureAmountStr);
		String perFeeAmount = df.format(new BigDecimal(partnerSettlementOrder.getPerFee())
		                                                 .divide(new BigDecimal("10000")));
		logger.info("perFeeAmount："+perFeeAmount);
		String settleAmountStr = new BigDecimal(partnerSettlementOrder.getSettlementAmount())
		.divide(new BigDecimal("1000")).toString();
		logger.info("settleAmountStr : "+settleAmountStr);
		String fixedFeeAmount = df.format(new BigDecimal(partnerSettlementOrder.getFixedFeeSettlementAmount())
		.divide(new BigDecimal("1000")));
		logger.info("fixedFeeAmount : "+fixedFeeAmount);
		BigDecimal recordAmount = new BigDecimal(settleAmountStr).subtract(new BigDecimal(fixedFeeAmount))
        .subtract(new BigDecimal(perFeeAmount)).subtract(new BigDecimal(assureAmountStr));
		logger.info("入账金额："+recordAmount);
		recordAmount = recordAmount.multiply(new BigDecimal("1000"));
		logger.info("recordAmount : "+recordAmount.longValue());
		logger.info("recordAmount:"+recordAmount+" ,accountingAmount:"+accountingAmount); 
		*/
		
		//(8) 记账 并更新清算订单
		
		boolean doAccountResult = doAccounting_liquidation_version2(partnerSettlementOrder,assureAmount,
				channelOrderDTO,amount,dAmount,dAssureAmount,dfee,
				partnerId,orderId,merchantOrderId,settlementCurrencyCode,rate);
		
		if(!doAccountResult){
			logger.error("记账失败");
		}
	}
	
	private void sendAlertMsg(Map<String,String> data){
		WeiXinNotifyRequest request = new WeiXinNotifyRequest();
        request.setBizCode("0015");
        request.setOpenId("0000");
        request.setType(RequestType.WEIXIN);
		
		request.setData(data);
		channelService.getJmsSender().send(request);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int countRiskRnTx(RiskOrder riskOrder) {
		//收取风控服务费
		try{
			if(riskOrder!=null){				
				//add by sch 2016-04-14  分控手续续费=0，则不收取，直接把状态改为2 
				if(riskOrder.getSettlementAmount()==0){
					String result = "风控手续费=0,不收取： tradeOrderNo: "+riskOrder.getTradeOrderNo()
							+",partnerId="+riskOrder.getPartnerId();
					logger.info("PaymentServiceImpl-method: countRiskRnTx ["+result+"]");					
					riskOrder.setFeeFlg(2);
					riskOrder.setUpdateDate(new Date());
					riskOrderDAO.update(riskOrder);				
					return 1;		//返回值为1，表示成功
				}
				//end by sch 2016-04-14
				int r = doAccounting_200_204(riskOrder.getPartnerId(), riskOrder.getTradeOrderNo().toString(),
						riskOrder.getSettlementAmount(), riskOrder.getSettlementCurrencyCode(),riskOrder.getMerchantOrderId());
				if(r == 1){
					riskOrder.setFeeFlg(1);
					riskOrder.setUpdateDate(new Date());
					riskOrderDAO.update(riskOrder);
				}else{
					String result = "风控手续费收取失败： tradeOrderNo: "+riskOrder.getTradeOrderNo()
							+",partnerId="+riskOrder.getPartnerId();
					logger.info("PaymentServiceImpl-method: countRiskRnTx ["+result+"]");
				}
				return r;
		    }
		}catch(Exception e){
			logger.error(",收取风控服务费异常",e);
			return 0;
		}
		return 0;
	}


	@Override
	public int countRefundFeeOrderRnTx(RefundFeeOrder refundFeeOrder) {
		//收取风控服务费
		try{
			if(refundFeeOrder!=null){				

				if(refundFeeOrder.getSettlementAmount()==0){
					String result = "退款手续费 =0,不收取： refundOrderNo: "+refundFeeOrder.getRefundOrderNo()
							+",partnerId="+refundFeeOrder.getPartnerId();
					logger.info("PaymentServiceImpl-method: countRefundFeeOrderRnTx ["+result+"]");					
					
					refundFeeOrder.setFeeFlg(2);
					refundFeeOrder.setUpdateDate(new Date());
					refundFeeOrderDAO.update(refundFeeOrder);				
					return 1;		//返回值为1，表示成功
				}
				//end by sch 2016-04-14
				
				int r = doAccounting_500_517(refundFeeOrder.getPartnerId(), refundFeeOrder.getRefundOrderNo().toString(),
						refundFeeOrder.getSettlementAmount(), refundFeeOrder.getSettlementCurrencyCode(),refundFeeOrder.getMerchantOrderId());
				
				if(r == 1){
					refundFeeOrder.setFeeFlg(1);
					refundFeeOrder.setUpdateDate(new Date());
					refundFeeOrderDAO.update(refundFeeOrder);
				}else{
					String result = "退款手续费收取失败： refundOrderNo: "+refundFeeOrder.getRefundOrderNo()
							+",partnerId="+ refundFeeOrder.getPartnerId();
					logger.info("PaymentServiceImpl-method: countRefundFeeOrderRnTx ["+result+"]");
				}
				return r;
		    }
		}catch(Exception e){
			logger.error(",收取退款手续费异常",e);
			return 0;
		}
		return 0;
	}


	public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
		this.paymentOrderService = paymentOrderService;
	}

	public void setAccounting_200_200(AccountingService accounting_200_200) {
		this.accounting_200_200 = accounting_200_200;
	}

	public void setAccounting_200_201(AccountingService accounting_200_201) {
		this.accounting_200_201 = accounting_200_201;
	}

	public void setAccounting_200_202(AccountingService accounting_200_202) {
		this.accounting_200_202 = accounting_200_202;
	}

	public void setAccounting_200_203(AccountingService accounting_200_203) {
		this.accounting_200_203 = accounting_200_203;
	}

	public void setAccounting_700_700(AccountingService accounting_700_700) {
		this.accounting_700_700 = accounting_700_700;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}



	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	public void setPaymentOrderExpandDAO(
			PaymentOrderExpandDAO paymentOrderExpandDAO) {
		this.paymentOrderExpandDAO = paymentOrderExpandDAO;
	}

	public void setAccounting_500_517(AccountingService accounting_500_517){
		this.accounting_500_517 = accounting_500_517;
	}

	public void setRefundFeeOrderDAO(BaseDAO refundFeeOrderDAO) {		
		this.refundFeeOrderDAO = refundFeeOrderDAO;
	}

	
	public int doAccounting_200_200(String orderId, Long amount,
			String currencyCode,String merchantOrderId) throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		// accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);

		return accounting_200_200.doAccountingReturn(accountingDto);
	}

	public int doAccounting_200_201(String orderId, Long amount,
			String currencyCode, String rate,String merchantOrderId) throws Exception {

		BigDecimal aAmount = new BigDecimal(amount).multiply(new BigDecimal(
				rate));
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(aAmount);
		accountingDto.setOrderAmount(aAmount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		return accounting_200_201.doAccountingReturn(accountingDto);
	}

	public int doAccounting_200_203(Long partnerId, String orderId,
			Long amount, String currencyCode, String rate,String merchantOrderId) throws Exception {

		BigDecimal aAmount = new BigDecimal(amount).multiply(new BigDecimal(
				rate));
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(aAmount);
		accountingDto.setOrderAmount(aAmount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setPayee(partnerId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		Integer acctType = AcctTypeEnum
				.getGuaranteeAcctTypeByCurrency(currencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayeeAcctType(acctType);
		accountingDto.setPayeeFullMemberAcctCode(acctAttribDto.getAcctCode());
		return accounting_200_203.doAccountingReturn(accountingDto);
	}
	
	public int doAccounting_200_204(Long partnerId, String orderId,
			Long amount, String currencyCode,String merchantOrderId) throws Exception {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setPayer(partnerId);
		Integer acctType = AcctTypeEnum
				.getBasicAcctTypeByCurrency(currencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayerAcctType(acctType);
		accountingDto.setPayerFullMemberAcctCode(acctAttribDto.getAcctCode());
		return accounting_200_204.doAccountingReturn(accountingDto);
	}
	
	public int doAccounting_500_517(Long partnerId, String orderId,
			Long amount, String currencyCode,String merchantOrderId) throws Exception {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setPayer(partnerId);
		Integer acctType = AcctTypeEnum
				.getBasicAcctTypeByCurrency(currencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayerAcctType(acctType);
		accountingDto.setPayerFullMemberAcctCode(acctAttribDto.getAcctCode());
		return accounting_500_517.doAccountingReturn(accountingDto);
	}
	public static int pow(int a,int n){
        if(n==0){
        return 1;
    }else{
        return a*pow(a,n-1);
    }
}
	/**
	 * 订单授信提前清算
	 * @param partnerSettlementOrder
	 * @param settlementCurrencyCode
	 */
	@Override
	public void settlementRnTx(PartnerSettlementOrder partnerSettlementOrder,
			String settlementCurrencyCode) {
		try {
			DecimalFormat df2 = new DecimalFormat("0.000");
			df2.setRoundingMode(RoundingMode.DOWN);
			String perFeeAmount2 = df2.format(new BigDecimal(partnerSettlementOrder.getPerFee())
			.divide(new BigDecimal("10000")));
			logger.info("perFeeAmount："+perFeeAmount2);
			String fixedFeeAmount2 = df2.format(new BigDecimal(partnerSettlementOrder.getFixedFeeSettlementAmount())
			.divide(new BigDecimal("1000")));
			logger.info("fixedFeeAmount : "+fixedFeeAmount2);
			String orderId2=partnerSettlementOrder.getId().toString();
			String merchantOrderId2 = partnerSettlementOrder.getOrderId();
			String rate2=partnerSettlementOrder.getSettlementRate();
			Long partnerId2 = partnerSettlementOrder.getPartnerId();
			
				
				// 货币兑换
				doAccounting_200_200(orderId2, partnerSettlementOrder.getAmount(),
						partnerSettlementOrder.getCurrencyCode(),merchantOrderId2);
				doAccounting_200_201(orderId2, partnerSettlementOrder.getAmount(), settlementCurrencyCode, rate2,merchantOrderId2);
				doAccounting_200_202(partnerId2, orderId2,partnerSettlementOrder.getRecordedAmount()-partnerSettlementOrder.getCharge(),
						partnerSettlementOrder.getAssureAmount(), settlementCurrencyCode,
						partnerSettlementOrder,new BigDecimal(perFeeAmount2).add(new BigDecimal(fixedFeeAmount2))
						.multiply(new BigDecimal("1000")),merchantOrderId2);
				// 先扣除保证金
				doAccounting_200_203(partnerId2, orderId2, partnerSettlementOrder.getAssureAmount(),
						settlementCurrencyCode, rate2,merchantOrderId2);
				doAccounting_200_207(orderId2,partnerSettlementOrder.getCharge(),settlementCurrencyCode,merchantOrderId2);
				
				Date oldDate=partnerSettlementOrder.getSettlementDate();
			partnerSettlementOrder.setSettlementFlg(1);	
			partnerSettlementOrder.setSettlementDate(new Date());
			partnerSettlementOrder.setCreditFlag(OrderCreditEnum.creditFlag4.getType());
			partnerSettlementOrderService
			.updatePartnerSettlementOrder(partnerSettlementOrder);
			
			paymentOrderService.updateSettlementFlg(
					partnerSettlementOrder.getPaymentOrderNo(), 1);
			logger.info("更新授信明细订单开始！");
			OrderCreditDetailDTO orderCreditDetailDTO = new OrderCreditDetailDTO();
			orderCreditDetailDTO.setPartnerSettlementOrderId(partnerSettlementOrder.getId());
			orderCreditDetailDTO.setAccountDate(new Date());
			orderCreditDetailDTO.setSettlementFlg(1);
			orderCreditService.updateDetail(orderCreditDetailDTO);
			logger.info("更新授信明细订单结束！");
		} catch (Exception e) {
			logger.error("do accounting error,settement orderId:" 
					+ partnerSettlementOrder.getId(), e);
			Map<String,String> data = new HashMap<String, String>();
			data.put("first","系统异常:清算记账失败,txncore:PaymentServiceImpl-settlementOrderId:"
					+partnerSettlementOrder.getId());
			data.put("keyword1","清算记账失败");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			data.put("keyword2",formatter.format(new Date()));
			data.put("remark","请尽快检查！");
			this.sendAlertMsg(data);
			partnerSettlementOrder.setSettlementFlg(2);
			partnerSettlementOrderService
			.updatePartnerSettlementOrder(partnerSettlementOrder);
		}
	}
	/*
	 * 不够收取手续费的时候，收取手续费
	 */
	public int doAccounting_200_206(Long partnerId, String orderId,
			Long amount, String currencyCode,String merchantOrderId) throws Exception {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setPayer(partnerId);
		Integer acctType = AcctTypeEnum
				.getBasicAcctTypeByCurrency(currencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayerAcctType(acctType);
		accountingDto.setPayerFullMemberAcctCode(acctAttribDto.getAcctCode());
		return accounting_200_206.doAccountingReturn(accountingDto);
	}
	
	public void doAccounting_200_205(String orderId,
			Long amount, String currencyCode,String merchantOrderId) throws Exception {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_200_205.doAccounting(accountingDto);
	}
	public void doAccounting_200_207(String orderId,
			Long amount, String currencyCode,String merchantOrderId) throws Exception {
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_200_207.doAccounting(accountingDto);
	}

	/*
	 * 新写的代码，doAccounting_202_202 
	 */
	public int doAccounting_200_202_new(Long partnerId, String orderId,
			Long recordAmount, Long assureAmount,
			String settlementCurrencyCode,
			PartnerSettlementOrder partnerSettlementOrder,Long fee,String merchantOrderId) throws Exception {
		
		logger.info("doAccounting_200_202_new recordAmount : "+recordAmount);
		logger.info("doAccounting_200_202_new fee : "+ fee);
		
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setOrderId(orderId);
		accountingDto.setPayee(partnerId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayerCurrencyCode(settlementCurrencyCode);
		accountingDto.setPayeeCurrencyCode(settlementCurrencyCode);
		Integer acctType = AcctTypeEnum
				.getBasicAcctTypeByCurrency(settlementCurrencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayeeAcctType(acctAttribDto.getAcctType());
		accountingDto.setPayeeFullMemberAcctCode(acctAttribDto.getAcctCode());

		// 为什么要更新 paymentOrderDTO 的  payeeFee ，这个值，有什么意义 
		if (assureAmount > 0) {
			if (fee > 0) {
				PaymentOrderDTO paymentOrderDTO = paymentOrderService
						.queryByPaymentOrderNo(partnerSettlementOrder
								.getPaymentOrderNo());
				paymentOrderDTO.setPayeeFee(fee);
				paymentOrderService.updatePaymentOrderRnTx(paymentOrderDTO);
			}
			//partnerSettlementOrder.setFee(fee);
		}
		
		if(recordAmount<=0){
			throw new BusinessException("订单金额不够清算手续费",
					ExceptionCodeEnum.NO_SETTLEMENT_RATE);
		}
		
		accountingDto.setHasCaculatedPrice(true);
		accountingDto.setPayeeFee(fee);
		accountingDto.setAmount(recordAmount);
		accountingDto.setOrderAmount(recordAmount);
		return accounting_200_202.doAccountingReturn(accountingDto);
	}
	
	
	public int doAccounting_200_202(Long partnerId, String orderId,
			Long recordAmount, Long assureAmount,
			String settlementCurrencyCode,
			PartnerSettlementOrder partnerSettlementOrder,BigDecimal feeStr,String merchantOrderId) throws Exception {
		logger.info("doAccounting_200_202 recordAmount : "+recordAmount);
		logger.info("doAccounting_200_202 feeStr : "+feeStr);
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setOrderId(orderId);
		accountingDto.setPayee(partnerId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayerCurrencyCode(settlementCurrencyCode);
		accountingDto.setPayeeCurrencyCode(settlementCurrencyCode);
		Integer acctType = AcctTypeEnum
				.getBasicAcctTypeByCurrency(settlementCurrencyCode);
		AcctAttribDto acctAttribDto = accountQueryService
				.doQueryAcctAttribNsTx(partnerId, acctType);
		accountingDto.setPayeeAcctType(acctAttribDto.getAcctType());
		accountingDto.setPayeeFullMemberAcctCode(acctAttribDto.getAcctCode());

		// 第一次结算全部收取手续费用
		if (assureAmount > 0) {
//			AccountingFeeRes accountingFeeRes = accounting_200_202
//					.caculateFee(accountingDto);
//			
//			if (null != accountingFeeRes) {
//				fee = accountingFeeRes.getPayeeFee();
//				logger.info("fee:" + fee);
//			}

			if (feeStr.longValue() > 0) {
				PaymentOrderDTO paymentOrderDTO = paymentOrderService
						.queryByPaymentOrderNo(partnerSettlementOrder
								.getPaymentOrderNo());
				paymentOrderDTO.setPayeeFee(feeStr.longValue());
				paymentOrderService.updatePaymentOrderRnTx(paymentOrderDTO);
			}
			partnerSettlementOrder.setFee(feeStr.longValue());
		}
		if(recordAmount<=0){
			throw new BusinessException("订单金额不够清算手续费",
					ExceptionCodeEnum.NO_SETTLEMENT_RATE);
		}
		
		accountingDto.setHasCaculatedPrice(true);
		accountingDto.setPayeeFee(feeStr.longValue());
		accountingDto.setAmount(recordAmount);
		accountingDto.setOrderAmount(recordAmount);
		return accounting_200_202.doAccountingReturn(accountingDto);
	}

	public void setChannelOrderService(ChannelOrderService channelOrderService) {
		this.channelOrderService = channelOrderService;
	}

	@Override
	public void liquidationAssureRnTx(
			PartnerSettlementOrder partnerSettlementOrder) {

		String orderId = partnerSettlementOrder.getId().toString();
		Long assureAmount = partnerSettlementOrder.getAssureAmount();
		Long partnerId = partnerSettlementOrder.getPartnerId();
		String settlementRate = partnerSettlementOrder.getSettlementRate();
		String settlementCurrencyCode = partnerSettlementOrder
				.getSettlementCurrencyCode();
		String merchantOrderId = partnerSettlementOrder.getOrderId();

		Long settlementAmount = new BigDecimal(assureAmount).multiply(
				new BigDecimal(settlementRate)).longValue();

		try {
			doAccounting_700_700(orderId, partnerId, settlementAmount,
					settlementCurrencyCode,merchantOrderId);
			partnerSettlementOrder.setAssureSettlementFlg(1);
			partnerSettlementOrderService
					.updatePartnerSettlementOrder(partnerSettlementOrder);
		} catch (Exception e) {
			//logger.error("do accounting error:", e);
			//partnerSettlementOrder.setSettlementFlg(2);
			logger.error("do accounting error: orderId= " + orderId, e);
			partnerSettlementOrder.setAssureSettlementFlg(2);  //bug fixed by sch 2016-07-05
			partnerSettlementOrderService
					.updatePartnerSettlementOrder(partnerSettlementOrder);
		}

	}

	public void doAccounting_700_700(String orderId, Long partnerId,
			Long amount, String currencyCode,String merchantOrderId) throws Exception {

		Integer payerAcctType = AcctTypeEnum
				.getGuaranteeAcctTypeByCurrency(currencyCode);
		Integer payeeAcctType = AcctTypeEnum
				.getBasicAcctTypeByCurrency(currencyCode);

		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);

		accountingDto.setPayerAcctType(payerAcctType);
		accountingDto.setPayeeAcctType(payeeAcctType);
		accountingDto.setPayer(partnerId);
		accountingDto.setPayee(partnerId);

		accounting_700_700.doAccounting(accountingDto);
	}

	public void setMerchantRateService(MerchantRateService merchantRateService) {
		this.merchantRateService = merchantRateService;
	}
	
	private String getCardType(String cardNo){
		//2016-06-16 本地化支付是没有卡号信息的 
		if(StringUtil.isEmpty(cardNo)){
			return null;
		}
		
		int cardLen = cardNo.length();
		
		if(cardLen == 16){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=400000 && subCard <=499999){
				return "VISA";
			}
			if((subCard>=510000 && subCard <=559999)||(subCard>=222100 && subCard <=272099)){
				return "MASTER";
			}
			if((subCard>=352800 && subCard <=358999)||(subCard>=213100 && subCard <=213199)||(subCard>=180000 && subCard <=180099)){
				return "JCB";
			}
		}
		if(cardLen == 14){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=300000 && subCard <=305999){
				return "DC";
			}
			if(subCard>=309500 && subCard <=309599){
				return "DC";
			}
			if(subCard>=360000 && subCard <=369999){
				return "DC";
			}
			if(subCard>=380000 && subCard <=399999){
				return "DC";
			}
		}
		if(cardLen == 15){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=340000 && subCard <=349999){
				return "AE";
			}
			if(subCard>=370000 && subCard <=379999){
				return "AE";
			}
		}
		return null;
	}

	public void setAccounting_200_204(AccountingService accounting_200_204) {
		this.accounting_200_204 = accounting_200_204;
	}

	public void setAccounting_200_205(AccountingService accounting_200_205) {
		this.accounting_200_205 = accounting_200_205;
	}

	public void setRiskOrderDAO(BaseDAO riskOrderDAO) {
		this.riskOrderDAO = riskOrderDAO;
	}

	public void setTradeAmountCountService(
			TradeAmountCountService tradeAmountCountService) {
		this.tradeAmountCountService = tradeAmountCountService;
	}
	
	public void setCardBinInfoService(CardBinInfoService cardBinInfoService) {
		this.cardBinInfoService = cardBinInfoService;
	}
	
	public void setAccounting_200_206(AccountingService accounting_200_206){
		this.accounting_200_206 = accounting_200_206;
	}

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService){
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setAcctService( AcctService acctService){
		this.acctService = acctService;
	}
	
	private Map<Long, String[]> getFourLevelData(MerchantRateDto merchantRate){
		Map<Long, String[]> tradeCountLevelMap=new LinkedHashMap<Long, String[]>();
		if(merchantRate.getMonthAmountLevel()!=null){
			tradeCountLevelMap.put(merchantRate.getMonthAmountLevel(), new String[]{merchantRate.getChargeRate(),merchantRate.getFixedCharge(),merchantRate.getFixedCurrencyCode()});
		}
		if(merchantRate.getMonthAmountLevel1()!=null){
			tradeCountLevelMap.put(merchantRate.getMonthAmountLevel1(), new String[]{merchantRate.getChargeRate1(),merchantRate.getFixedCharge1(),merchantRate.getFixed1CurrencyCode()});
		}
		if(merchantRate.getMonthAmountLevel2()!=null){
			tradeCountLevelMap.put(merchantRate.getMonthAmountLevel2(), new String[]{merchantRate.getChargeRate2(),merchantRate.getFixedCharge2(),merchantRate.getFixed2CurrencyCode()});
		}
		if(merchantRate.getMonthAmountLevel3()!=null){
			tradeCountLevelMap.put(merchantRate.getMonthAmountLevel3(), new String[]{merchantRate.getChargeRate3(),merchantRate.getFixedCharge3(),merchantRate.getFixed3CurrencyCode()});
		}
		return tradeCountLevelMap;
	}

	private String[] geMathedLevelChargeRate(Long totalAmount,MerchantRateDto merchantRate){
		logger.info("geMathedLevelChargeRate : totalAmount is "+totalAmount);
		Map<Long, String[]> tradeCountLevelMap = getFourLevelData(merchantRate);
		Long[] data = (Long[])tradeCountLevelMap.keySet().toArray(new Long[tradeCountLevelMap.keySet().size()]);
		Long targetLevel=null;
		if(totalAmount<=data[0]){
			targetLevel = data[0];
		}else if(totalAmount>=data[data.length-1]){
			targetLevel = data[data.length-1];
		}else{
			int index = -1;
			for(int i=0;i<data.length;i++){
				if(totalAmount>=data[i]){
					continue;
				}else{
					index = i;
					break;
				}
			}
			targetLevel = data[index-1];
		}
		return tradeCountLevelMap.get(targetLevel);
	}

	@Override
	public List<RiskOrder> findRiskOrderList(RiskOrder riskOrder) {		
		return riskOrderDAO.findByCriteria(riskOrder);
	}
	
	@Override 
	public List<RefundFeeOrder> findRefundFeeOrderList(RefundFeeOrder refundFeeOrder){
		return refundFeeOrderDAO.findByCriteria(refundFeeOrder);
	}


	public void setOrderCreditService(OrderCreditService orderCreditService) {
		this.orderCreditService = orderCreditService;
	}

	public void setAccounting_200_207(AccountingService accounting_200_207) {
		this.accounting_200_207 = accounting_200_207;
	}

	
		
}
