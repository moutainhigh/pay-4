package com.pay.txncore.crosspay.handler;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.util.Random;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.artificial.trade.model.AutoTranRela;
import com.pay.artificial.trade.model.AutomaticTrade;
import com.pay.artificial.trade.model.CardInfo;
import com.pay.artificial.trade.service.ManualTranSubService;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;

import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.redis.RedisClientTemplate;
import com.pay.task.PropertiesMap;
import com.pay.txncore.crosspay.dto.acquire.CrosspayRequest;
import com.pay.txncore.dao.TradeOrderAutoDAO;

import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class AutomaticTradeHandler  {

	public final Log logger = LogFactory.getLog(getClass());

	
	
	private TradeOrderAutoDAO tradeOrderautoService;
	
	
	public TradeOrderAutoDAO getTradeOrderautoService() {
		return tradeOrderautoService;
	}


	public void setTradeOrderautoService(TradeOrderAutoDAO tradeOrderautoService) {
		this.tradeOrderautoService = tradeOrderautoService;
	}


	


	private RedisClientTemplate redisClient;
	
	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}
	

	public RedisClientTemplate getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(RedisClientTemplate redisClient) {
		this.redisClient = redisClient;
	}

	

	public HessianInvokeService getInvokeService() {
		return invokeService;
	}



	private ManualTranSubService manualTranSubService;

	public void setManualTranSubService(
			ManualTranSubService manualTranSubService) {
		this.manualTranSubService = manualTranSubService;
	}

	
	public String handle(Map<String, String> paraMap) {

		
		Map resultMap = new HashMap();
		
		logger.info("dataMsg: "+paraMap);
		
		try {
			
			
			Map<String, String> readProperties = PropertiesMap.maps;
			String currencyCode = readProperties.get("currencyCode");	//币种
			String tradingTimes = "1";//readProperties.get("tradingTimes"); //每张卡的需要刷交易的次数
			String amountMax = readProperties.get("amountMax");  //范围金额 取随机数 最大的金额
			String amountMin = readProperties.get("amountMin");  //最小的金额
			String sleepEndTime = readProperties.get("sleepEndTime");
			String  sleepStartTime=readProperties.get("sleepStartTime");
			String memberCode=paraMap.get("memberCode")+"";
			String batch=paraMap.get("batch");
			String pflag=paraMap.get("pflag");
			String cardOrg=paraMap.get("cardOrg");// 卡组织
			String estimatedTime=paraMap.get("estimatedTime");// 交易时长
			String startflag=paraMap.get("startflag");
			int picitotal=100;
			if(paraMap.get("picitotal")!=null){picitotal=Integer.valueOf(paraMap.get("picitotal"));};
			float amountmax = Float.parseFloat(amountMax);
			float amountmin = Float.parseFloat(amountMin);
			Random random = new Random();
			
			//没有禁用卡 add byliu 2016-08-12
			/*
			CardInfo card=new CardInfo();	// 新批次之前开启 所有被禁用的卡片
			card.setStatus("1");
			card.setUpdateDate(new Date());
			manualTranSubService.updateCardInfo(card);	*/
							
		
			int i=1;			  
			int sum = 0; 
			int nexestartime=0;
			List<CardInfo>	cardInfos=manualTranSubService.findCardInfoAllBybatch(batch); //查询卡信息
			if(cardInfos!=null && cardInfos.size()>0  ){
		      sum = cardInfos.size();			
			}
			
			String partnerId = memberCode;// 会员号
			String cardNum = String.valueOf(sum);// 卡数量
			
						
			//生成人工交易批次
			Date currentTime = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String BatchNumber ="M_" + pflag+"_"+format.format(currentTime);
			AutomaticTrade automaticTradepc = new AutomaticTrade();
			automaticTradepc.setCardCount(cardNum);
			automaticTradepc.setCardOrg(cardOrg);
			automaticTradepc.setPartnerId(partnerId);
			automaticTradepc.setSubDate(currentTime);
			automaticTradepc.setOperator("administrator");
			automaticTradepc.setEstimatedTime(estimatedTime);
			automaticTradepc.setStatus("1");
			automaticTradepc.setBatchNumber(BatchNumber);
			manualTranSubService.createAutomaticTradeAuto(automaticTradepc);
			Map redisMap = new HashMap();
			if(cardInfos==null || cardInfos.size()<=0){
				AutomaticTrade automatic=new AutomaticTrade();
				//automatic.setSuccesCount(i+"");
				automatic.setEndDate(new Date());
				automatic.setBatchNumber(BatchNumber);
				automatic.setStatus("2");
				manualTranSubService.updateAutomaticTrade(automatic);
				resultMap.put("responseCode",
						ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
				resultMap.put("responseDesc","请配置卡信息！！");
				return JSonUtil.toJSonString(resultMap);
			}
			
			//下面循环初始化本批次卡号状态
			Iterator<CardInfo> iterator = cardInfos.iterator();
			while (iterator.hasNext()) {
				CardInfo cardInfo = (CardInfo) iterator.next();
				SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				String format1 = dateFormat1.format(date);
				Date updateDate = cardInfo.getUpdateDate();
				
				int month = updateDate.getMonth(); //最后更新月份 
				int month2 = date.getMonth();   //当前月分
				if(month<month2){
					CardInfo cardInfo2=new CardInfo();
					cardInfo2.setUpdateDate(new Date());
					cardInfo2.setMonthsSuccessCount("0");
					cardInfo2.setTradingTimes("0");
					cardInfo2.setDaySuccessCount("0");
					cardInfo.setDaySuccessCount("0");
					cardInfo.setDayFailCount("0");
					cardInfo2.setDayFailCount("0");
					cardInfo2.setId(cardInfo.getId());
					manualTranSubService.updateCardInfo(cardInfo2);	
				}else{
					if (!updateDate.after(dateFormat1.parse(format1))) {
						CardInfo cardInfo3=new CardInfo();
						cardInfo3.setUpdateDate(new Date());
						cardInfo3.setTradingTimes("0");
						cardInfo3.setDaySuccessCount("0");
						cardInfo.setDaySuccessCount("0");
						cardInfo.setDayFailCount("0");
						cardInfo3.setDayFailCount("0");
						cardInfo3.setId(cardInfo.getId());
						manualTranSubService.updateCardInfo(cardInfo3);	
					}
				}
			}
		
			
			int allCardTradeCount=0;
			int pici=(sum/picitotal-(sum%picitotal)/picitotal)+1;
			//以100张卡为一批，循环跑
			for(int j=0;j<pici;j++){
				if(j==0){
					AutomaticTrade autoTrade=new AutomaticTrade();  //设置开始时间
					autoTrade.setBatchNumber(automaticTradepc.getBatchNumber());
					autoTrade.setStartDate(new Date());
					autoTrade.setBatchNumber(BatchNumber);
					manualTranSubService.updateAutomaticTrade(autoTrade);
					redisMap.put("startDate", format.format(new Date()));
					redisMap.put("subDate", format.format(new Date()));
					redisMap.put("status", "1");
					redisMap.put("batchName", BatchNumber);
					redisClient.hmset(pflag, redisMap);					
					redisClient.set(startflag, "0");
				}if(j==1){
					//第一批第一张卡跑完时设置时间
//					AutomaticTradeResult automaticTradeResult =new AutomaticTradeResult();
//					automaticTradeResult.setBatchName(batch);
//					automaticTradeResult.setSubDate(new Date());
//					automaticTradeResult.setStatus("1");
//					manualTranSubService.updateAutomaticTradeResult(automaticTradeResult);
					
					redisMap.put("subDate", format.format(new Date()));
					redisMap.put("status", "1");
					redisMap.put("batchName", BatchNumber);
					redisClient.hmset(pflag, redisMap);
				}
				int sunindex =(j+1)*picitotal;
				if(j==(pici-1)){
					sunindex=sum;
				}
				List<CardInfo> dotraceList =new ArrayList<CardInfo>();
				dotraceList.addAll(cardInfos.subList(j*picitotal, sunindex));
				//每张卡跑15次，循环跑
				for(int k=0;k<15;k++){
					//100张卡轮流跑
					
					for(int l=0;l<dotraceList.size();l++){
						
						List<AutomaticTrade> queryManualTran = manualTranSubService.queryManualTran(automaticTradepc,MapUtil.map2Object(Page.class, null));
						if(queryManualTran == null || queryManualTran.size()<=0){
							resultMap.put("responseCode",
									ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
							resultMap.put("responseDesc","交易已被停止！！！");
							return JSonUtil.toJSonString(resultMap);
						}
						Calendar c = Calendar.getInstance();//判断是否农行日切，日切则休眠16分钟						 
						
						int hour = c.get(Calendar.HOUR_OF_DAY); 
						int minute = c.get(Calendar.MINUTE); 						
					
						if((hour==4&&(0<=minute&&minute<22))||(hour==3&&(58<=minute))){
							Thread.sleep(1000*60*22);
						}
							allCardTradeCount++;	
							BigDecimal db = new BigDecimal(Math.random() * (amountmax - amountmin) + amountmin).multiply(new BigDecimal(100));   //随机取金额
							BigDecimal setScale = db.setScale(0, BigDecimal.ROUND_HALF_UP);  //保留两位小数
							Date tempcurrentTime = new Date();    //生成  orderId
							SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
							SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
							CrosspayRequest request = new CrosspayRequest();
							Map paramap=new HashMap();
							paramap.put("version", "1.0");
							request.setVersion("1.0");
							paramap.put("orderId", formatter.format(tempcurrentTime));
							request.setOrderId(formatter.format(tempcurrentTime));
							paramap.put("goodsName", "myGoodsName");
							request.setGoodsName("myGoodsName");
							paramap.put("goodsDesc", "myGoods");
							request.setGoodsDesc("myGoods");
							paramap.put("submitTime", format1.format(currentTime));
							request.setSubmitTime(format1.format(currentTime));
							paramap.put("customerIP", "180.168.145.74");
							request.setCustomerIP("180.168.145.74");
							paramap.put("siteId", "www.pay.com");
							request.setSiteId("www.pay.com");
							paramap.put("orderAmount", setScale.toString());
							request.setOrderAmount(setScale.toString());
							paramap.put("tradeType", "1000");
							request.setTradeType("1000");
							paramap.put("payType", "EDC");
							request.setPayType("EDC");
							paramap.put("currencyCode", currencyCode);
							request.setCurrencyCode(currencyCode);
							paramap.put("borrowingMarked", "0");
							request.setBorrowingMarked("0");
							paramap.put("noticeUrl", "http://localhost/gatewayTest/notify");
							request.setNoticeUrl("http://localhost/gatewayTest/notify");
							paramap.put("partnerId", automaticTradepc.getPartnerId().trim());
							request.setPartnerId(automaticTradepc.getPartnerId().trim());
							paramap.put("billName", "chaoyue");
							request.setBillName("chaoyue");
							paramap.put("billAddress", "shanghai,china");
							request.setBillAddress("shanghai,china");
							paramap.put("billEmail", "501374997@qq.com");
							request.setBillEmail("501374997@qq.com");
							paramap.put("billPhoneNumber", "18621758498");
							request.setBillPhoneNumber("18621758498");
							paramap.put("billPostalCode", "200120");
							request.setBillPostalCode("200120");
							paramap.put("billStreet", "laoshan");
							request.setBillAddress("laoshan");
							paramap.put("billCity", "shanghai");
							request.setBillCity("shanghai");
							paramap.put("billCountryCode", "86");
							request.setBillCountryCode("86");
							paramap.put("shippingName", "chaoyue");
							request.setShippingName("chaoyue");
							paramap.put("shippingAddress", "laoshan,shanghai,china");
							request.setShippingAddress("laoshan,shanghai,china");
							paramap.put("shippingMail", "peiyu.yang@live.com");
							request.setShippingMail("peiyu.yang@live.com");
							paramap.put("shippingPhoneNumber", "02160625509");
							request.setShippingPhoneNumber("02160625509");
							paramap.put("shippingPostalCode", "888888");
							request.setShippingPostalCode("888888");
							paramap.put("shippingStreet", "shangchengroad");
							request.setShippingStreet("shangchengroad");
							paramap.put("shippingCity", "shanghai");
							request.setShippingCity("shanghai");
							paramap.put("shippingState", "shanghai");
							request.setShippingState("shanghai");
							paramap.put("shippingCountryCode", "86");
							request.setShippingCountryCode("86");
							paramap.put("payMode", "10");
							request.setPayMode("10");
							paramap.put("cardHolderNumber", dotraceList.get(l).getCardCode());
							request.setCardHolderNumber(dotraceList.get(l).getCardCode());
							paramap.put("cardHolderFirstName", "yang");
							request.setCardHolderFirstName("yang");
							paramap.put("cardHolderLast", "chao");
							request.setCardHolderLastName("chao");
							paramap.put("billState", "shanghai");
							request.setBillState("shanghai");
							paramap.put("cardExpirationMonth",dotraceList.get(l).getEffectiveMonth());
							request.setCardExpirationMonth(dotraceList.get(l).getEffectiveMonth());
							paramap.put("cardExpirationYear",dotraceList.get(l).getEffectiveYear());
							request.setCardExpirationYear(dotraceList.get(l).getEffectiveYear());
							paramap.put("securityCode",dotraceList.get(l).getCvv());
							request.setSecurityCode(dotraceList.get(l).getCvv());
							paramap.put("cardHolderEmail","y493571ds1@126.com");
							request.setCardHolderEmail("y493571ds1@126.com");
							paramap.put("deviceFingerprintId","fdsfds");
							request.setDeviceFingerprintId("fdsfds");
							paramap.put("orderTerminal","00");
							request.setOrderTerminal("00");
							paramap.put("prdtCode","EDC");
							
							paramap.put("signMsg", "2");
							request.setSignMsg("2");
							paramap.put("signType", "2");
							request.setSignType("2");
							paramap.put("charset", "1");
							request.setCharset("1");
//							PaymentInfo paymentInfo = MapUtil.map2Object(PaymentInfo.class,
//									paramap);
//							crosspayApiAcquire(request);
//							PaymentResult paymentResult = apiPayService   //下单
//							
//									.crossApiPay(paymentInfo);
							Map paymentResult = crosspayApiAcquire(request);

							if(paymentResult.get("responseCode").equals("0000")){
								AutomaticTrade automatic=new AutomaticTrade();  //更新成功状态
								automatic.setSuccesCount(i+"");
								automatic.setBatchNumber(automaticTradepc.getBatchNumber());
								manualTranSubService.updateAutomaticTrade(automatic);
								CardInfo cardInfo=new CardInfo();   //更新那张卡的 成功笔数 状态
								cardInfo.setId(dotraceList.get(l).getId());
							
								cardInfo.setSuccessCount(i+"");	
								cardInfo.setUpdateDate(new Date());
								manualTranSubService.updateCardInfo(cardInfo);	
								i++;
							}else{
								//add by liu  不禁用卡 2016-08-12
								/**
								int y=1; //失败笔数
								CardInfo cardInfo=new CardInfo();  //更新 失败的状态 并且禁用那张卡
								cardInfo.setId(dotraceList.get(l).getId());
								cardInfo.setFailCount(y+"");
								cardInfo.setStatus("2");
								cardInfo.setUpdateDate(new Date());
								manualTranSubService.updateCardInfo(cardInfo);
								
								dotraceList.remove(l);
								//y++;
								 **/
							
								
							}
							Long tradeOrderNo = Long.valueOf(paymentResult.get("tradeOrderNo").toString());  //更新网关订单号 和 自动刷单的 关系表
							String batchNumber = automaticTradepc.getBatchNumber();
							AutoTranRela autoTranRela=new AutoTranRela();
							autoTranRela.setBatchNumber(batchNumber);
							autoTranRela.setTradeOrderNo(tradeOrderNo+"");
							
							//paymentService.getTradeOrderService().createAutoTranRela(autoTranRela);
							tradeOrderautoService.createAutoTranRela(autoTranRela);
						resultMap.putAll(MapUtil.bean2map(paymentResult));
						resultMap.put("responseCode", resultMap.get("responseCode"));
						resultMap.put("responseDesc", resultMap.get("responseDesc"));
						int sleepStart=Integer.parseInt(sleepStartTime);
						int sleepEnd=Integer.parseInt(sleepEndTime); 
						//int a = random.nextInt(sleepEnd)%(sleepEnd-sleepStart+sleepStart) + sleepStart;  //随机暂停秒数
						//两笔之间间隔
						
						int a = random.nextInt(sleepEnd)%(sleepEnd-sleepStart+sleepStart) + sleepStart;  //随机暂停秒数
						Thread.sleep(a*1000);   
						
						}
						
					}
				}
				
			
			
		
				AutomaticTrade automatic=new AutomaticTrade();   // 交易数量 已完成
			//	automatic.setSuccesCount(i+"");
				automatic.setBatchNumber(automaticTradepc.getBatchNumber());
				automatic.setStatus("2");
				automatic.setEndDate(new Date()); //结束时间
				manualTranSubService.updateAutomaticTrade(automatic);
				
				//交易完成设置结果
//				AutomaticTradeResult automaticTradeResult =new AutomaticTradeResult();
//				automaticTradeResult.setStartDate(new Date() );
//				automaticTradeResult.setEndDate(new Date());
//				automaticTradeResult.setStatus("2");
//				manualTranSubService.updateAutomaticTradeResult(automaticTradeResult);
				
				redisMap.put("endDate", format.format(new Date()));
				redisMap.put("status","2");
				redisClient.hmset(pflag, redisMap);
				
				
				
				logger.info("交易总笔数: "+allCardTradeCount);
	
		} catch (Exception e) {
			logger.error("api pay error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}

		
	
	/**
	 * 跨境API收单
	 * 
	 * @return
	 */
	public Map crosspayApiAcquire(CrosspayRequest crosspayApiRequest) {

		Map<String, String> paraMap = MapUtil.bean2map(crosspayApiRequest);
		
		paraMap.put("prdtCode","EDC");
	
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.WEBGATE.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_API_ACQUIRE.getCode(), sysTraceNo,
				SystemCodeEnum.WEBGATE.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());
		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
		return resultMap;
	}
	

	
}
