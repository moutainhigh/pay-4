package com.pay.txncore.handler.bounced;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;






import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.crosspay.service.TransactionBaseRateService;
import com.pay.txncore.dao.BouncedFraudQueryDAO;
import com.pay.txncore.dto.BouncedFraudResultDTO;
import com.pay.txncore.model.BouncedOrderVO;
import com.pay.txncore.model.BouncedRateListVO;
import com.pay.txncore.model.SettlementRate;
import com.pay.txncore.model.TransactionBaseRate;
import com.pay.txncore.model.TransactionRate;
import com.pay.txncore.service.chargeback.BouncedFineTaskService;
import com.pay.txncore.service.chargeback.ChargeBackOrderService;
import com.pay.util.DateUtil;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/***
 * 拒付余额不足商户查询
 * @author delin
 * @date 2016年7月21日15:14:18
 */
public class BouncedBalanceNotEnoughHandler implements EventHandler {
	
	public final Log logger = LogFactory.getLog(BouncedBalanceNotEnoughHandler.class);

	private BouncedFineTaskService balanceNotEnoughService;
	
	private ChargeBackOrderService  chargeBackOrderService;
	
	private BaseDAO chargeBackOrderDAO;
	
	private CurrencyRateService   currencyRateService;
	
	private BouncedFraudQueryDAO   bouncedFraudQueryDAO;
	
	private TransactionBaseRateService  transactionBaseRateService;
	public static String getLastMonthFirstDay(Date date) {

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 不加下面2行，就是取当前时间前一个月的第一天及最后一天
		String year = new SimpleDateFormat("yyyy").format(date);
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		cal.add(Calendar.MONTH, -1);
		cal.set(Calendar.DAY_OF_MONTH, 1);

		Date firstDate = cal.getTime();

		return new SimpleDateFormat("yyyy-MM-dd").format(firstDate);
	}
	public static String getLastMonthFirstDay2(Date date) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 不加下面2行，就是取当前时间前一个月的第一天及最后一天
		String year = new SimpleDateFormat("yyyy").format(date);
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		cal.add(Calendar.MONTH, -2);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		
		Date firstDate = cal.getTime();
		
		return new SimpleDateFormat("yyyy-MM-dd").format(firstDate);
	}
	public static String getLastMonthEndDay(Date date) {
		
		Calendar calendar = Calendar.getInstance();  
		int month = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
		Date strDateTo = calendar.getTime();  
		return new SimpleDateFormat("yyyy-MM-dd").format(strDateTo);
	}
	public static String getLastMonthEndDay2(Date date) {
		
		Calendar calendar = Calendar.getInstance();  
		int month = calendar.get(Calendar.MONTH);
		calendar.add(Calendar.MONTH, -2);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
		Date strDateTo = calendar.getTime();  
		return new SimpleDateFormat("yyyy-MM-dd").format(strDateTo);
	}

	/**
	 * 简单取对应日期交易汇率
	 * 
	 * @param sourceCurrency
	 * @param targetCurrency
	 * @param screateDate
	 * @param isIgnoreMarkup
	 * @return 2016年6月13日 mmzhang add
	 */
	private BigDecimal getTrantionRate(String sourceCurrency, String targetCurrency,
			Date screateDate, String memberCode,String cardOrg) {
		BigDecimal rate = BigDecimal.ONE;

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("sourceCurrency", sourceCurrency);
			param.put("targetCurrency", targetCurrency);
			//param.put("status", "1");
			param.put("currentDate", screateDate);
			param.put("memberCode", memberCode);
			param.put("cardOrg",cardOrg);
			param.put("point", getTime());
			param.put("ltaCurrencyCode", "USD");

			TransactionRate transactionRate = currencyRateService.getNewTransactionRate(param);
			if (null == transactionRate) {
				logger.info("未找到对应交易汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: " + sourceCurrency
						+ " ,targetCurrencyCode: " + targetCurrency);
				rate = null;

			} else {
				rate = new BigDecimal(transactionRate.getExchangeRate());
				logger.info("找到对应交易汇率为rate=" + rate + ".....sourceCurrencyCode: "
						+ sourceCurrency + " ,targetCurrencyCode: " + targetCurrency);
			}
		return rate;
	}
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		try {
			String beginCreateDate = getLastMonthFirstDay(new Date());
			String endCreateDate = getLastMonthEndDay(new Date());
			
			String beginCreateDate2 = getLastMonthFirstDay2(new Date());
			String endCreateDate2 = getLastMonthEndDay2(new Date());
			
			Map paraMap = new HashMap();
			paraMap.put("status", "1");
			paraMap.put("beginCreateDate", beginCreateDate);
			paraMap.put("endCreateDate", endCreateDate);
			//Visa交易数据
			List<BouncedFraudResultDTO> BouncedFrauds = bouncedFraudQueryDAO.bouncedFraudQuery(paraMap);
			
			Map paraMap2 = new HashMap();
			paraMap2.put("status", "1");
			paraMap2.put("beginCreateDate", beginCreateDate2);
			paraMap2.put("endCreateDate", endCreateDate2);
			//master交易数据
			List<BouncedFraudResultDTO> BouncedFrauds2 = bouncedFraudQueryDAO.bouncedFraudQuery(paraMap2);
			
			//生成拒付欺诈数据
			BouncedOrderVO bouncedOrderVO = new BouncedOrderVO();
			bouncedOrderVO.setCpdBeginTime(beginCreateDate);
			bouncedOrderVO.setCpdEndTime(endCreateDate);
			//该逻辑包括gc银行调单文件
			bouncedOrderVO.setBouncedFlag("0");
			bouncedOrderVO.setChargeBackAmount("0");
			List<BouncedOrderVO> bouncedOrderVOs = chargeBackOrderService
					.queryBouncedOrders(bouncedOrderVO);
			
			
			
			TransactionBaseRate exchangeRate = new TransactionBaseRate();
			exchangeRate.setStatus("1");
			List<TransactionBaseRate> list = transactionBaseRateService
					.findByCriteria(exchangeRate);
			Map<String,BigDecimal> ratemap=new HashMap<String,BigDecimal>();
			for (TransactionBaseRate ratevo : list) {
				 //币种代码
				String currency=ratevo.getCurrency();
				 //* 兑换的币种
				String targetCurrency=ratevo.getTargetCurrency();
				 //交换汇率
				String rate=ratevo.getExchangeRate();
				String key=currency.concat(targetCurrency);
				ratemap.put(key,new BigDecimal(rate));
			}
			//插入visa的拒付数据
			insertBouncedRateListForCardOrg(BouncedFrauds, bouncedOrderVOs,"VISA",ratemap);
			//插入MASTER的拒付数据
			insertBouncedRateListForCardOrg(BouncedFrauds2, bouncedOrderVOs,"MASTER",ratemap);
			
			//查询拒付欺诈报表
			List<Map<String, Object>> fineRuleAndRate = balanceNotEnoughService
					.queryFineRuleAndRate();
			for (Map<String, Object> map : fineRuleAndRate) {
				String ruleNo = (String) map.get("ruleNo"); // 拒付罚款规则
				Long partnerId=Long.valueOf(map.get("partnerId").toString());
				String bouncedCurrencyCode=(String)map.get("bouncedCurrencyCode");
				Integer bouncedCount = Integer.valueOf(map.get("bouncedCount").toString()) ; // 拒付笔数
				String  bouncedRate = (String) map.get("bouncedRate"); // 拒付率
				String  countRate1 = (String) map.get("countRate1"); // 笔数 or 比例  规则一为 金额
				Long fineAmount1 = Long.valueOf(getMapValueByAmount(map,"fineAmount1"));
				String  countRate2 = (String) map.get("countRate2");
				Long fineAmount2 =Long.valueOf(getMapValueByAmount(map,"fineAmount2")); 
				String  countRate3 = (String) map.get("countRate3");
				Long fineAmount3 = Long.valueOf(getMapValueByAmount(map,"fineAmount3"));
				String cardOrg = (String) map.get("cardOrg");
				Long totalCount = Long.valueOf(getMapValueByAmount(map,"totalCount"));
				Long lastTotalCount = Long.valueOf(getMapValueByAmount(map,"lastTotalCount"));
				Long bouncedFineNo=balanceNotEnoughService.queryBouncedFineOrderNo();
				BigDecimal fineAmount = null;// 罚款金额
				if(StringUtil.isEmpty(ruleNo)){
					logger.error("请配置拒付罚款规则 会员号 :"+partnerId);
					resultMap.put("responseCode",
							ResponseCodeEnum.FAIL.getCode());
					resultMap.put("responseDesc", "请配置拒付罚款规则 会员号 :"+partnerId);
					logger.info("请配置拒付罚款规则 会员号 :"+partnerId);
					continue;
				}else if (ruleNo.equals("1")) {// 拒付罚款规则1的计算
					fineAmount = new BigDecimal(bouncedCount)
							.multiply(new BigDecimal(countRate1));// 规则一的罚款金额
				} else if (ruleNo.equals("2")) { //规则二 
					Integer countRate1Int = Integer.valueOf(countRate1);
					Integer countRate2Int = Integer.valueOf(countRate2);
					Integer countRate3Int = Integer.valueOf(countRate3);
					if(bouncedCount<countRate1Int){ //小于 A笔 则  笔数 * A笔配置的罚款金额
						fineAmount=new BigDecimal(bouncedCount).multiply(new BigDecimal(fineAmount1));
					}else if(bouncedCount>=countRate1Int && bouncedCount<=countRate2Int){
						fineAmount=new BigDecimal(bouncedCount).multiply(new BigDecimal(fineAmount2));
					}else if(bouncedCount>countRate3Int){
						fineAmount=new BigDecimal(bouncedCount).multiply(new BigDecimal(fineAmount3));
					}
				} else if(ruleNo.equals("3")){//规则3
						 BigDecimal countRate1Big = new BigDecimal(countRate1);  //拒付率1  --规则
						 BigDecimal countRate2Big = new BigDecimal(countRate2);  //拒付率 2  --规则
						 BigDecimal countRate3Big = new BigDecimal(countRate3);  //拒付率 3  --规则
						 BigDecimal bouncedRateBig = new BigDecimal(bouncedRate); // 拒付率
						 int compareTo = bouncedRateBig.compareTo(countRate1Big);//返回的结果是int类型,-1表示小于,0是等于,1是大于.
						 int compareTo2 = bouncedRateBig.compareTo(countRate2Big);
						 int compareTo3 = bouncedRateBig.compareTo(countRate3Big);
						 if(compareTo == -1){
							 fineAmount=new BigDecimal(bouncedCount).multiply(new BigDecimal(fineAmount1));
						 }else if(compareTo > -1 && compareTo2 < 1){
 							 fineAmount=new BigDecimal(bouncedCount).multiply(new BigDecimal(fineAmount2)); 
						 }else if(compareTo3 > -1){
							 fineAmount=new BigDecimal(bouncedCount).multiply(new BigDecimal(fineAmount3));
						 }
				} else if(ruleNo.equals("4")){//规则4
					 BigDecimal countRate1Big = new BigDecimal(countRate1);  //拒付率1  --规则
					 BigDecimal countRate2Big = new BigDecimal(countRate2);  //拒付率 2  --规则
					 BigDecimal countRate3Big = new BigDecimal(countRate3);  //拒付率 3  --规则
					 BigDecimal bouncedRateBig = new BigDecimal(bouncedRate); // 拒付率
					 int compareTo = bouncedRateBig.compareTo(countRate1Big);//返回的结果是int类型,-1表示小于,0是等于,1是大于.
					 int compareTo2 = bouncedRateBig.compareTo(countRate2Big);
					 int compareTo3 = bouncedRateBig.compareTo(countRate3Big);
					 if(compareTo == -1){
						 fineAmount=new BigDecimal(bouncedCount).multiply(new BigDecimal(fineAmount1));
					 }else if(compareTo > -1 && compareTo2 < 1){
						 if(cardOrg.equals("VISA")){
							 fineAmount=new BigDecimal(fineAmount2).multiply(bouncedRateBig.subtract(countRate1Big)).multiply(new BigDecimal(totalCount));//收取b*（e%-拒付率)*总笔数的拒付罚款。 
						 }else{
							 fineAmount=new BigDecimal(fineAmount2).multiply(bouncedRateBig.subtract(countRate1Big)).multiply(new BigDecimal(lastTotalCount));//收取b*（e%-拒付率)*总笔数的拒付罚款。 
						 }
					 }else if(compareTo3 > -1){
						 fineAmount=new BigDecimal(bouncedCount).multiply(new BigDecimal(fineAmount3));
					 }
				}
				map.put("fineAmount", fineAmount.longValue());
				map.put("currencyCode", "USD");
				map.put("bouncedFineNo", bouncedFineNo);
				String rate = "1";
				SettlementRate settlementRate = currencyRateService.getSettlementRate(
						"USD",
						bouncedCurrencyCode, "1",
						partnerId+"",
								null);
				if (null == settlementRate) {
					logger.info("未找到对应结算汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: "
							+ "USD"
							+ " ,targetCurrencyCode: " + bouncedCurrencyCode);
					throw new BusinessException("settlementRate not exists",
							ExceptionCodeEnum.NO_SETTLEMENT_RATE);
				} else {
					rate = settlementRate.getExchangeRate();
					
					//partnerSettlementOrder.setSettlementRate(rate);   //这句话在最后会补上 .  为什么放在最后，不放在这里？ 
				}
				BigDecimal multiply = fineAmount.multiply(new BigDecimal(rate));//转换后要扣的拒付罚款金额
				map.put("settlementAmount", multiply.longValue());
				map.put("partnerId", partnerId);
				Boolean baseCheckBalance = chargeBackOrderService.baseCheckBalance(multiply, partnerId, bouncedCurrencyCode, true);	// 比较商户的此币种的 基本户 是否够扣
				if(!baseCheckBalance){//不够扣则 拒付罚款表状态改为失败
					map.put("status", "2");
					logger.info("余额不足，" + "fineAmount:" + fineAmount.longValue() + ",partnerId:" + partnerId+",bouncedCurrencyCode"+bouncedCurrencyCode+",settlementAmount="+multiply.longValue());
				}else{
					//chargeBackOrderService.doFineAccounting(map);//够扣则 调用记账。
					map.put("status", "1");
				}
				balanceNotEnoughService.insertBouncedFine(map); // 创建 拒付罚款表
				balanceNotEnoughService.updateFineRuleAndRate(map);
			}

			List<Map<String,Object>> resultList=chargeBackOrderDAO.findAll("queryBouncedBalanceNotEnoughMemberHandler");
			resultMap.put("result", resultList);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			resultMap.put("responseCode",
					ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("bounced fine error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());

		}
		return JSonUtil.toJSonString(resultMap);
	}
	private String getMapValueByAmount(Map vo, String name) {
		String doingRefundAmount = "0";
		if (vo.get(name) != null && !"".equals(vo.get(name))) {
			doingRefundAmount = vo.get(name).toString();
		}
		return doingRefundAmount;
	}
	/**
	 *计算拒付率并保存入库
	 * @param BouncedFrauds
	 * @param bouncedOrderVOs
	 * 2016年7月29日   mmzhang     add
	 * @throws ParseException 
	 */
	private void insertBouncedRateListForCardOrg(List<BouncedFraudResultDTO> BouncedFrauds,
			List<BouncedOrderVO> bouncedOrderVOs,String cardOrg,Map<String,BigDecimal> ratemap) throws ParseException {
		//计算visa的商户拒付率数据
		Map<String, BouncedRateListVO> bouncedTradeOrders=new HashMap<String, BouncedRateListVO>();
		if(null!=BouncedFrauds && BouncedFrauds.size()>0)
		{	
		for (BouncedFraudResultDTO bouncedFraudResultDTO : BouncedFrauds) {
			if (null == bouncedFraudResultDTO.getCreateDate()) {
				continue;
			}
			if (null == bouncedFraudResultDTO.getPartnerId()) {
				continue;
			}
			if (null == bouncedFraudResultDTO.getCardOrg()) {
				continue;
			} else if (!cardOrg.equals(bouncedFraudResultDTO.getCardOrg())) 
			{
				continue;
			}
			String createDate = bouncedFraudResultDTO.getCreateDate();
			String yyyy1 = createDate.substring(0, 4);
			String mm1 = createDate.substring(4, 6);
			String dd1 = createDate.substring(6, 8);
			String createDateRate = yyyy1.concat("-").concat(mm1).concat("-").concat(dd1);

			String amount0 = bouncedFraudResultDTO.getAmount().toString();
			String currencyCode = bouncedFraudResultDTO.getCurrencyCode();

			String spartnerId = ""+bouncedFraudResultDTO.getPartnerId();
			String partnerName = bouncedFraudResultDTO.getPartnerName();
			// String merchantNos1 = getMapValueByString(map, "merchantNo");
			String cardOrg1 = bouncedFraudResultDTO.getCardOrg();
			String createDateSub = createDate.substring(0, 6);
			Date currentDate= new SimpleDateFormat("yyyy-MM-dd").parse(createDateRate);
			BigDecimal rate = BigDecimal.ONE;
			String rateKey = currencyCode.concat("CNY");
			if (!"CNY".equals(currencyCode) && ratemap.containsKey(rateKey)) {
				rate = ratemap.get(rateKey);
			} else if(!"CNY".equals(currencyCode)) {
				logger.info("未找到对应交易汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: " + currencyCode
						+ " ,targetCurrencyCode: CNY");
				throw new BusinessException("settlementRate not exists",
						ExceptionCodeEnum.NO_SETTLEMENT_RATE);
			}

			BigDecimal amount = BigDecimal.ZERO;
			if (null != rate) {
				amount = new BigDecimal(amount0).multiply(rate).setScale(0,
						BigDecimal.ROUND_HALF_UP);
			}

			BouncedRateListVO bouncedRateListVO = new BouncedRateListVO();
			bouncedRateListVO.setPartnerid(spartnerId);
			bouncedRateListVO.setPartnername(partnerName);
			bouncedRateListVO.setCardorg(cardOrg1);
			String key = "";
			key = createDateSub.concat("|").concat(cardOrg1).concat("|").concat(spartnerId);

			bouncedRateListVO.setCreatedate(currentDate);

			if (bouncedTradeOrders.containsKey(key)) {
				bouncedTradeOrders.get(key).setTotalcount(
						bouncedTradeOrders.get(key).getTotalcount().add(BigDecimal.ONE));

				BigDecimal totalamt = bouncedTradeOrders.get(key).getTotalamount();
				if (null == totalamt) {
					totalamt = BigDecimal.ZERO;
				}
				bouncedTradeOrders.get(key).setTotalamount(totalamt.add(amount));
			} else {
				bouncedRateListVO.setTotalcount(BigDecimal.ONE);
				bouncedRateListVO.setTotalamount(amount);
				bouncedTradeOrders.put(key, bouncedRateListVO);
			}
		}
		}
		Map<String, BouncedRateListVO> bouncedDataOrders=new HashMap<String, BouncedRateListVO>();
		if(null!=bouncedOrderVOs && bouncedOrderVOs.size()>0)
		{	
			for (BouncedOrderVO vo : bouncedOrderVOs) {
				BouncedRateListVO bouncedOrderVO1 = new BouncedRateListVO();
				if(null==vo.getTradeDate())
				{
					continue;
				}
				if(null==vo.getMemberCode())
				{
					continue;
				}
				if(null==vo.getCpdDate())
				{
					continue;
				}
				if (null == vo.getCardOrg()) {
					continue;
				} else if (!cardOrg.equals(vo.getCardOrg())) 
				{
					continue;
				}
				String tradeDateRate = DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, vo.getTradeDate());
				
				String cpdDate=vo.getCpdDate();
				String cpdDates = cpdDate.substring(0,4).concat(cpdDate.substring(5,7)).concat(cpdDate.substring(8,10));
				String memberCode = vo.getMemberCode();
				String merchantName = vo.getMerchantName();
				String channelOrderId = ""+vo.getChannelOrderId();
				String tranCurrencyCode = vo.getTranCurrencyCode();
				String chargeBackAmount = vo.getChargeBackAmount();
				String visableCode = vo.getVisableCode();
				// 使用拒付日期
				String cpdDateSub = cpdDates.substring(0, 6);
				
				BigDecimal rate=BigDecimal.ONE;
				String rateKey=tranCurrencyCode.concat("CNY");
				Date currentDate= new SimpleDateFormat("yyyy-MM-dd").parse(tradeDateRate);
				if(!"CNY".equals(tranCurrencyCode) && ratemap.containsKey(rateKey))
				{
					rate=ratemap.get(rateKey);
				}else if(!"CNY".equals(tranCurrencyCode)){
					logger.info("未找到对应交易汇率，请确认是否配置了对应的汇率.....sourceCurrencyCode: " + tranCurrencyCode
							+ " ,targetCurrencyCode: CNY");
					throw new BusinessException("settlementRate not exists",
							ExceptionCodeEnum.NO_SETTLEMENT_RATE);
				}
				BigDecimal amount=BigDecimal.ZERO;
				if(null != rate)
				{
					amount = new BigDecimal(chargeBackAmount).multiply(rate).setScale(0,
							BigDecimal.ROUND_HALF_UP);
				}
				
				bouncedOrderVO1.setPartnerid(memberCode);
				bouncedOrderVO1.setPartnername(merchantName);
				bouncedOrderVO1.setCardorg(cardOrg);
				bouncedOrderVO1.setBounceddate(cpdDateSub);
				String key="";
				
				key = cpdDateSub.concat("|").concat(cardOrg).concat("|").concat(memberCode);
				
				if (bouncedDataOrders.containsKey(key)) {
					bouncedDataOrders.get(key).setBouncedcount(
							bouncedDataOrders.get(key).getBouncedcount().add(BigDecimal.ONE));
					
					// 欺诈金额计算
					if ("3".equals(visableCode) || "5".equals(visableCode)) {
						BigDecimal fraudamt=bouncedDataOrders.get(key).getFraudamount();
						if(null==fraudamt)
						{
							fraudamt=BigDecimal.ZERO;
						}
						bouncedDataOrders.get(key).setFraudamount(
								fraudamt.add(amount));
					}
				} else {
					bouncedOrderVO1.setBouncedcount(BigDecimal.ONE);
					// 欺诈金额计算
					if ("3".equals(visableCode) || "5".equals(visableCode)) {
						bouncedOrderVO1.setFraudamount(amount);
					}
					bouncedDataOrders.put(key, bouncedOrderVO1);
				}
				
			}
		}
		List<BouncedRateListVO> insertBouncedRateList= new ArrayList<BouncedRateListVO>();
		for (Entry<String, BouncedRateListVO> entry : bouncedDataOrders.entrySet()) {
			String key=entry.getKey();
			if("MASTER".equals(cardOrg))
			{
				String[] keys=key.split("\\|");
				String date=keys[0];
				String masterDate=gegMasterDate(date);
				key=masterDate.concat("|").concat(keys[1]).concat("|").concat(keys[2]);
			}
			
			BouncedRateListVO bouncedvo=entry.getValue();
			BouncedRateListVO tradevo=bouncedTradeOrders.get(key);
			
			if(null!=tradevo)
			{
			//拒付率计算	
			BigDecimal bouncedRate=BigDecimal.ZERO;
			if(null!=tradevo.getTotalcount() && tradevo.getTotalcount().longValue()>0)
			{
				BigDecimal bouncedcount=bouncedvo.getBouncedcount();
				if(null==bouncedcount)
				{
					bouncedcount=BigDecimal.ZERO;
				}
				bouncedRate = bouncedcount.divide(tradevo.getTotalcount(), 4,
						BigDecimal.ROUND_HALF_UP);
			}
			BigDecimal fraudRate=BigDecimal.ZERO;
			if(null!=tradevo.getTotalcount() && tradevo.getTotalcount().longValue()>0)
			{
				BigDecimal fraudAmount=bouncedvo.getFraudamount();
				if(null==fraudAmount)
				{
					fraudAmount=BigDecimal.ZERO;
				}
				fraudRate = fraudAmount.divide(tradevo.getTotalamount(), 4,
						BigDecimal.ROUND_HALF_UP);
			}
			bouncedvo.setBouncedrate(bouncedRate);
			bouncedvo.setBouncedcount(bouncedvo.getBouncedcount());
			if("MASTER".equals(cardOrg))
			{
				bouncedvo.setLasttotalcount(tradevo.getTotalcount());
				bouncedvo.setLasttotalamount(tradevo.getTotalamount());
			}else{
				bouncedvo.setTotalcount(tradevo.getTotalcount());
				bouncedvo.setTotalamount(tradevo.getTotalamount());
			}
			bouncedvo.setFraudrate(fraudRate);
			
			bouncedvo.setPartnername(tradevo.getPartnername());
			if(bouncedRate.compareTo(BigDecimal.ZERO)==1)
			{
				insertBouncedRateList.add(bouncedvo);
			}
			}
		}
		if(null!=insertBouncedRateList && insertBouncedRateList.size()>0)
		{
			balanceNotEnoughService.batchCreate(insertBouncedRateList);
		}
	}
	/**
	 * 计算上月日期
	 * @param createDateSub
	 * @return
	 * 2016年8月1日   mmzhang     add
	 */
	private String gegMasterDate(String createDateSub) {
		String MASTERDate = "";
		String yyyy = createDateSub.substring(0, 4);
		String mm = createDateSub.substring(4, 6);
		if (mm.equals("01")) {
			MASTERDate = (Integer.valueOf(yyyy) - 1) + "12";
		} else {
			if (Integer.valueOf(mm) < 10) {
				MASTERDate = yyyy + "0" + (Integer.valueOf(mm) -1);
			} else {
				MASTERDate = yyyy + (Integer.valueOf(mm) - 1);
			}
		}
		return MASTERDate;
	}

	public void setBalanceNotEnoughService(
			BouncedFineTaskService balanceNotEnoughService) {
		this.balanceNotEnoughService = balanceNotEnoughService;
	}
	public void setChargeBackOrderService(
			ChargeBackOrderService chargeBackOrderService) {
		this.chargeBackOrderService = chargeBackOrderService;
	}

	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}
	
	public static double getTime() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		double s = min / 100.0;
		double rst = hour + s;

		return rst;
	}
	public void setBouncedFraudQueryDAO(BouncedFraudQueryDAO bouncedFraudQueryDAO) {
		this.bouncedFraudQueryDAO = bouncedFraudQueryDAO;
	}
	public void setTransactionBaseRateService(TransactionBaseRateService transactionBaseRateService) {
		this.transactionBaseRateService = transactionBaseRateService;
	}
	public void setChargeBackOrderDAO(BaseDAO chargeBackOrderDAO) {
		this.chargeBackOrderDAO = chargeBackOrderDAO;
	}
	
}
