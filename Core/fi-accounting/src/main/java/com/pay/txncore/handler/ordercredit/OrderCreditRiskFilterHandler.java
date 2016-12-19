/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.ordercredit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.inf.model.IpCountryInfo;
import com.pay.inf.service.IpCountryInfoService;
import com.pay.txncore.commons.OrderCreditEnum;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.dao.CreditFilterDAO;
import com.pay.txncore.model.CreditFilter;
import com.pay.txncore.model.CreditFilterOrder;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.model.SettlementRate;
import com.pay.txncore.service.PartnerSettlementOrderService;
import com.pay.util.IPv4Util;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 订单授信风控过滤
 * @author mmzhang
 *
 */
public class OrderCreditRiskFilterHandler implements EventHandler {

	public final Log logger = LogFactory
			.getLog(OrderCreditRiskFilterHandler.class);
	private int maxSize = 100;
	private PartnerSettlementOrderService partnerSettlementOrderService;
	private CreditFilterDAO creditFilterDAO;
	private CurrencyRateService currencyRateService;
	private IpCountryInfoService ipCountryInfoService;
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, Object> resultMap = new HashMap<String, Object>();

		Map<String, String> paraMap = null;
		int status = 1;
		paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		Date createEndTime = new Date();
		// 实例化日历类
		Calendar cal = Calendar.getInstance();
		// 设置日期
		cal.setTime(new Date());
		// 取3个月前的 日期
		cal.add(Calendar.MONTH, -3);
		Date threeMonthAgoDate = cal.getTime();
		String threeMonthAgoDates = df.format(threeMonthAgoDate);

		String beginDate = creditFilterDAO.queryCreditFilterMaxDate();

		
		String endDate = df.format(new Date());
		/*if (StringUtil.isEmpty(beginDate)) {
			Calendar c = Calendar.getInstance();
			c.setTime(new Date());
			c.set(Calendar.DATE, c.get(Calendar.DATE) - 1);
			beginDate = df.format(c.getTime());
		}*/
		logger.info("存入发生拒付和调单的ip和卡号和邮箱信息开始！");
		// 查询日期内清算订单
		List<CreditFilterOrder> channelOrders = partnerSettlementOrderService
				.queryChargeBackForCredit(beginDate, endDate, null);
		logger.info("查询到交易在" + beginDate + "和" + endDate + "的拒付订单数据为"
				+ channelOrders.size() + "条！");

		if (null != channelOrders && !channelOrders.isEmpty()
				&& channelOrders.size() > 0) {
			for (CreditFilterOrder creditFilterOrder : channelOrders) {
				CreditFilter creditFilterCard = new CreditFilter();
				if (null != creditFilterOrder.getCardholderCardno()
						|| "".equals(creditFilterOrder.getCardholderCardno())) {
					creditFilterCard.setContent(creditFilterOrder
							.getCardholderCardno());
					creditFilterCard.setType(OrderCreditEnum.type0.getType());
					creditFilterCard.setOrderId(creditFilterOrder.getOrderId());
					creditFilterCard.setCreateDate(new Date());
					creditFilterCard.setCpType(creditFilterOrder.getCpType());
					creditFilterDAO.create(creditFilterCard);
				}
				if (null != creditFilterOrder.getIp()
						|| "".equals(creditFilterOrder.getIp())) {
					CreditFilter creditFilterIP = new CreditFilter();
					creditFilterIP.setContent(creditFilterOrder.getIp());
					creditFilterIP.setType(OrderCreditEnum.type2.getType());
					creditFilterIP.setOrderId(creditFilterOrder.getOrderId());
					creditFilterIP.setCreateDate(new Date());
					creditFilterIP.setCpType(creditFilterOrder.getCpType());
					creditFilterDAO.create(creditFilterIP);
				}
				if (null != creditFilterOrder.getEmail()
						|| "".equals(creditFilterOrder.getEmail())) {
					CreditFilter creditFilterEmail = new CreditFilter();
					creditFilterEmail.setContent(creditFilterOrder.getEmail());
					creditFilterEmail.setType(OrderCreditEnum.type2.getType());
					creditFilterEmail
							.setOrderId(creditFilterOrder.getOrderId());
					creditFilterEmail.setCpType(creditFilterOrder.getCpType());
					creditFilterEmail.setCreateDate(new Date());
					creditFilterDAO.create(creditFilterEmail);
				}
			}
		}
		logger.info("存入发生拒付和调单的ip和卡号和邮箱信息结束！");

		logger.info("订单授信风控过滤开始！");
		// 统计当前时间商户保证金总金额，不是人民币转换为人民币，汇总
		List<CreditFilter> filterBalance = creditFilterDAO
				.queryCreditBalanceList(null);
		Map<String, BigDecimal> balancemap = new HashMap<String, BigDecimal>();
		getBalanceMap(filterBalance, balancemap);

		// 三个月的拒付清算金额
		Map<String, String> paraMap2 = new HashMap<String, String>();
		paraMap2.put("beginDate", threeMonthAgoDates);
		paraMap2.put("endDate", endDate);
		List<PartnerSettlementOrder> CreditFilterOrder2 = partnerSettlementOrderService
				.querySettlementOrderForTotal(paraMap2);
		Map<String, BigDecimal> settlementBouncedmap = new HashMap<String, BigDecimal>();
		getBalanceMap2(CreditFilterOrder2, settlementBouncedmap);

		/*// 三个月拒付率统计
		Map<String, String> paraMap3 = new HashMap<String, String>();
		paraMap2.put("beginDate", threeMonthAgoDates);
		paraMap2.put("endDate", endDate);
		List<CreditFilter> filterBalance2 = creditFilterDAO
				.queryChageBackOrderList(paraMap3);*/
		/*Map<String, BigDecimal> bouncedmap = new HashMap<String, BigDecimal>();
		if (null != filterBalance2 && filterBalance2.size() > 0) {
			for (CreditFilter creditFilter : filterBalance2) {
				bouncedmap.put(creditFilter.getMemberCode(),
						creditFilter.getBouncedRate());
			}
		}*/
		String[] creditFlags = new String[]{
				OrderCreditEnum.creditFlag1.getType(),
				OrderCreditEnum.creditFlag3.getType()
		};
		Map<String, Object> paraMap1 = new HashMap<String, Object>();
		paraMap1.put("creditFlags", creditFlags);
		paraMap1.put("settlementFlg", "0");
		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date());
		c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 2);
		Date settleStart = c1.getTime();
		//原清算时间距task执行时间小于2天的订单、发生过拒付、退款订单不予授信
		paraMap1.put("settleStart", settleStart);
		List<PartnerSettlementOrder> settleList = partnerSettlementOrderService
				.querySettlementOrderForCredit(paraMap1);

		CreditFilter queryfilter = new CreditFilter();
		List<CreditFilter> creditlist = creditFilterDAO
				.queryCreditFilterList(queryfilter);
		Map<String, CreditFilter> creditmap = new HashMap<String, CreditFilter>();
		if (null != creditlist && !creditlist.isEmpty()
				&& creditlist.size() > 0) {
			for (CreditFilter filter : creditlist) {
				creditmap.put(filter.getContent().concat(filter.getType()),
						filter);
			}
		}
			logger.info("要处理的清算订单为" + settleList.size() + "条");
			List<PartnerSettlementOrder> updateList= new ArrayList<PartnerSettlementOrder>();
			if (null != settleList && !settleList.isEmpty()
					&& settleList.size() > 0) {
				for (PartnerSettlementOrder order : settleList) {
					PartnerSettlementOrder orderNew = new PartnerSettlementOrder();
					String creditFlag=OrderCreditEnum.creditFlag3.getType();
					orderNew.setCreditFlag(creditFlag);
					orderNew.setId(order.getId());
					updateList.add(orderNew);
					// 商户循环保证金余额-（商户最近3个月的平均拒付比例*商户最近3个月的平均月拒付订单清算订单金额*6
					String key = "" + order.getPartnerId();
					BigDecimal balanceamt = BigDecimal.ZERO;
					if (balancemap != null && null != balancemap.get(key)) {
						balanceamt = balancemap.get(key);

					} else {
						balanceamt = BigDecimal.ZERO;
					}
					BigDecimal settlementBouncedamt = BigDecimal.ZERO;
					if (settlementBouncedmap != null && null != settlementBouncedmap.get(key)) {
						settlementBouncedamt = settlementBouncedmap.get(key);

					} else {
						settlementBouncedamt = BigDecimal.ZERO;
					}
					BigDecimal totalamt = settlementBouncedamt.multiply(new BigDecimal("2"));
					//保证金余额不够扣6个月拒付金额
					if (balanceamt.compareTo(totalamt) == -1) {
						creditFlag=OrderCreditEnum.creditFlag2.getType();
						orderNew.setCreditFlag(creditFlag);
						orderNew.setCreditRemark("商户保证金额不够扣半年拒付！balanceamt="+balanceamt+",totalamt="+totalamt);
						logger.info("商户保证金额不够扣半年拒付！balanceamt="+balanceamt+",totalamt="+totalamt);
						continue;
					}
					// ip,卡号，email未发生拒付
					if(order.getIp()==null)
					{
						creditFlag=OrderCreditEnum.creditFlag2.getType();
						orderNew.setCreditRemark("IP为空");
						orderNew.setCreditFlag(creditFlag);
						continue;
					}
					if (creditmap.containsKey(order.getIp().concat("2"))) {
						creditFlag=OrderCreditEnum.creditFlag2.getType();
						order.setCreditFlag(creditFlag);
						orderNew.setCreditRemark("该ip地址发生拒付，ip="+order.getIp());
						orderNew.setCreditFlag(creditFlag);
						logger.info("该ip地址发生拒付，ip="+order.getIp());
						continue;
					}
					if(order.getCardholderCardno()==null)
					{
						creditFlag=OrderCreditEnum.creditFlag2.getType();
						orderNew.setCreditRemark("卡号为空");
						orderNew.setCreditFlag(creditFlag);
						continue;
					}
					if (creditmap.containsKey(order.getCardholderCardno()
							.concat("0"))) {
						creditFlag=OrderCreditEnum.creditFlag2.getType();
						orderNew.setCreditFlag(creditFlag);
						orderNew.setCreditRemark("该卡发生拒付，Cardno="+order.getCardholderCardno());
						logger.info("该卡发生拒付，Cardno="+order.getCardholderCardno());
						continue;
					}
					if(order.getEmail()==null)
					{
						creditFlag=OrderCreditEnum.creditFlag2.getType();
						orderNew.setCreditRemark("email为空");
						orderNew.setCreditFlag(creditFlag);
						continue;
					}
					if (creditmap.containsKey(order.getEmail().concat("1"))) {
						creditFlag=OrderCreditEnum.creditFlag2.getType();
						orderNew.setCreditFlag(creditFlag);
						orderNew.setCreditRemark("该邮箱地址发生拒付，Email="+order.getEmail());
						logger.info("该邮箱地址发生拒付，Email="+order.getEmail());
						continue;
					}
					
				if (!"CNY".equals(order.getCurrencyCode())) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("sourceCurrency", order.getCurrencyCode());
					param.put("targetCurrency", "CNY");
					param.put("status", "1");
					param.put("memberCode", String.valueOf(order.getPartnerId()));
					if (!StringUtils.isEmpty(order.getCardOrg())) {
						param.put("cardOrg", order.getCardOrg());
					}
					param.put("orderAmount", order.getOrderAmount());
					param.put("ltaCurrencyCode", "USD");
					param.put("point", getTime());
System.out.print("@@@@@@@@order.getOrderAmount()="+order.getOrderAmount());
					SettlementRate settlementRate = currencyRateService
							.getNewSettlementRate(param);

					if (null == settlementRate) {
						logger.info("未找到对应结算汇率，请确认是否配置了对应的汇率sourceCurrencyCode: "
								+ order.getCurrencyCode()
								+ " ,targetCurrencyCode: " + "CNY");
						creditFlag = OrderCreditEnum.creditFlag2.getType();
						orderNew.setCreditRemark("未找到对应结算汇率，请确认是否配置了对应的汇率sourceCurrencyCode: "
								+ order.getCurrencyCode()+ " ,targetCurrencyCode: " + "CNY");
						orderNew.setCreditFlag(creditFlag);
						continue;
					}
				}
				
				String[] ipAddressInArray = order.getIp().split("\\."); 
				if(ipAddressInArray.length<4)
				{
					creditFlag=OrderCreditEnum.creditFlag2.getType();
					orderNew.setCreditRemark("该ip地址格式不正确，ip="+order.getIp());
					orderNew.setCreditFlag(creditFlag);
					logger.info("该ip地址格式不正确，ip="+order.getIp());
					continue;
				}
					Long ipint = IPv4Util.ipToLong(order.getIp());
					Map<String,Object> params_ = new HashMap<String, Object>();
					params_.put("ip",ipint);
					IpCountryInfo info = ipCountryInfoService.getCountryInfo(params_);
					if(info!=null && null!=info.getCountryCode3() && null!=order.getCardCountryCode() && null!=order.getShippingCountryCode()
							&& info.getCountryCode3().equals(order.getCardCountryCode()) 
							&& order.getCardCountryCode().equals(order.getShippingCountryCode()))
					{
						logger.info("持卡人IP地址所属国=持卡人信用卡所属国=收货地址所属国,CardCountryCode="+order.getCardCountryCode());
						creditFlag=OrderCreditEnum.creditFlag3.getType();
						orderNew.setCreditFlag(creditFlag);
					}else{
					String countryCode3="";
					if (info!=null && null != info.getCountryCode3()) {
						countryCode3=info.getCountryCode3();
					}
					String cardCountryCode="";
					if (null != order.getCardCountryCode()) {
						cardCountryCode=order.getCardCountryCode();
					}
					String shippingCountryCode="";
					if (null != order.getShippingCountryCode()) {
						shippingCountryCode=order.getShippingCountryCode();
					}
						creditFlag=OrderCreditEnum.creditFlag2.getType();
						orderNew.setCreditRemark("持卡人IP地址所属国="+countryCode3+"，持卡人信用卡所属国="+cardCountryCode+"，收货地址所属国="+shippingCountryCode+"不一致");
						orderNew.setCreditFlag(creditFlag);
						continue;
					}	
					logger.info("ipCountryInfo: "+info);
				}
				if (null != updateList && !updateList.isEmpty()
						&& updateList.size() > 0) {
					for (PartnerSettlementOrder partnerSettlementOrder : updateList) {
						partnerSettlementOrderService.updatePartnerSettlementOrder(partnerSettlementOrder);
					}
					
				}
				
			}
		
		resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		return JSonUtil.toJSonString(resultMap);
	}

	
	public static double getTime() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		double s = min / 100.0;
		double rst = hour + s;

		return rst;
	}
	
	/**
	 * 商户最近3个月的平均拒付比例*商户最近3个月的平均月清算订单金额*6 总
	 * 
	 * @param filterBalance
	 * @param balancemap
	 */
	private void getBalanceMap2(List<PartnerSettlementOrder> filterBalance,
			Map<String, BigDecimal> balancemap) {
		if (null != filterBalance && !filterBalance.isEmpty()
				&& filterBalance.size() > 0) {
			for (PartnerSettlementOrder balance : filterBalance) {

				BigDecimal balanceTemp = BigDecimal.ZERO;
				if("CNY".equals(balance.getCurrencyCode()))
				{
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("sourceCurrency", balance.getCurrencyCode());
					param.put("targetCurrency", "CNY");
					param.put("status", "1");
					param.put("memberCode", String.valueOf(balance.getPartnerId()));
					if (!StringUtils.isEmpty(balance.getCardOrg())) {
						param.put("cardOrg", balance.getCardOrg());
					}
					param.put("orderAmount", balance.getOrderAmount());
					param.put("ltaCurrencyCode", "USD");
					param.put("point", getTime());
					// 如果清算订单中没有清算汇率就去查询当日的清算汇率,否则清算使用清算订单中的清算汇率
					SettlementRate settlementRate = currencyRateService
							.getNewSettlementRate(param);
					
					if (null != settlementRate) {
						
						balanceTemp = new BigDecimal(
								balance.getChargeBackAmount())
								.multiply(
										new BigDecimal(settlementRate
												.getExchangeRate()))
								.setScale(4,BigDecimal.ROUND_HALF_UP);
					} else {
						balanceTemp = new BigDecimal(balance.getChargeBackAmount())
						.setScale(4,BigDecimal.ROUND_HALF_UP);
					}
				}
				else if (!balance.getCurrencyCode().equals("CNY")) {
					
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("sourceCurrency", balance.getCurrencyCode());
					param.put("targetCurrency", "CNY");
					param.put("status", "1");
					param.put("memberCode", String.valueOf(balance.getPartnerId()));
					if (!StringUtils.isEmpty(balance.getCardOrg())) {
						param.put("cardOrg", balance.getCardOrg());
					}
					param.put("orderAmount", balance.getOrderAmount());
					param.put("ltaCurrencyCode", "USD");
					param.put("point", getTime());
					// 如果清算订单中没有清算汇率就去查询当日的清算汇率,否则清算使用清算订单中的清算汇率
					SettlementRate settlementRate = currencyRateService
							.getNewSettlementRate(param);
					
					if (null != settlementRate) {
						
						balanceTemp = new BigDecimal(
								balance.getChargeBackAmount())
								.multiply(
										new BigDecimal(settlementRate
												.getExchangeRate()))
								.setScale(4,BigDecimal.ROUND_HALF_UP);
					} else {
						logger.info("未找到对应结算汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: "
								+ balance.getSettlementCurrencyCode()
								+ " ,targetCurrencyCode: CNY");
					}

				} else {
					balanceTemp = new BigDecimal(balance.getChargeBackAmount())
							.setScale(4,BigDecimal.ROUND_HALF_UP);
				}

				if (balancemap.containsKey("" +balance.getPartnerId())) {
					balancemap.put("" + balance.getPartnerId(), balancemap.get(""+balance.getPartnerId()).add(balanceTemp));
				} else {
					balancemap.put("" + balance.getPartnerId(), balanceTemp);
				}
			}
		}
	}

	/**
	 * 计算商户的币种汇总保证金余额
	 * 
	 * @param filterBalance
	 * @param balancemap
	 */
	private void getBalanceMap(List<CreditFilter> filterBalance,
			Map<String, BigDecimal> balancemap) {
		if (null != filterBalance && !filterBalance.isEmpty()
				&& filterBalance.size() > 0) {
			for (CreditFilter balance : filterBalance) {

				BigDecimal balanceTemp = BigDecimal.ZERO;
				if (!balance.getCurCode().equals("CNY")) {

					// 如果清算订单中没有清算汇率就去查询当日的清算汇率,否则清算使用清算订单中的清算汇率
					SettlementRate settlementRate = currencyRateService
							.getSettlementRate(balance.getCurCode(), "CNY",
									"1",
									String.valueOf(balance.getMemberCode()),
									null);

					if (null != settlementRate) {
						balanceTemp = balance
								.getBalance()
								.multiply(
										new BigDecimal(settlementRate
												.getExchangeRate()))
								.setScale(4,BigDecimal.ROUND_HALF_UP);
					} else {
						logger.info("未找到对应结算汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: "
								+ balance.getCurCode()
								+ " ,targetCurrencyCode: CNY");
					}

				} else {
					balanceTemp = balance.getBalance();
				}

				if (balancemap.containsKey(balance.getMemberCode())) {
					balancemap.put(balance.getMemberCode(), balancemap.get(balance.getMemberCode()).add(balanceTemp));
				} else {
					balancemap.put(balance.getMemberCode(), balanceTemp);
				}
			}
		}
	}

	public void setPartnerSettlementOrderService(
			PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}

	public void setCreditFilterDAO(CreditFilterDAO creditFilterDAO) {
		this.creditFilterDAO = creditFilterDAO;
	}

	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	public void setIpCountryInfoService(IpCountryInfoService ipCountryInfoService) {
		this.ipCountryInfoService = ipCountryInfoService;
	}


}
