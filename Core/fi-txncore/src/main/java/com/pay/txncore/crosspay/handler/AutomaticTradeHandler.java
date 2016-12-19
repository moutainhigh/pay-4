package com.pay.txncore.crosspay.handler;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.artificial.trade.model.AutoTranRela;
import com.pay.artificial.trade.model.AutomaticTrade;
import com.pay.artificial.trade.model.CardInfo;
import com.pay.artificial.trade.service.ManualTranSubService;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.service.ApiPayService;
import com.pay.txncore.service.PaymentService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class AutomaticTradeHandler implements EventHandler {
	public final Log logger = LogFactory.getLog(getClass());

	private PaymentService paymentService;
	private ApiPayService apiPayService;
	
	public void setApiPayService(ApiPayService apiPayService) {
		this.apiPayService = apiPayService;
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	private ManualTranSubService manualTranSubService;

	public void setManualTranSubService(
			ManualTranSubService manualTranSubService) {
		this.manualTranSubService = manualTranSubService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, String> paraMap = null;
		Map resultMap = new HashMap();
		logger.info("dataMsg: "+dataMsg);
		
		try {
			paraMap = JSonUtil.toObject(dataMsg,
						new HashMap<String, String>().getClass());
			String filename="/opt/pay/config/inf/artificialTrade/artificialTradeBaseConf.properties";
			Map<String, String> readProperties = this.readProperties(filename);
			String currencyCode = readProperties.get("currencyCode");	//币种
			String tradingTimes = "1";//readProperties.get("tradingTimes"); //每张卡的需要刷交易的次数
			String amountMax = readProperties.get("amountMax");  //范围金额 取随机数 最大的金额
			String amountMin = readProperties.get("amountMin");  //最小的金额
			String sleepEndTime = readProperties.get("sleepEndTime");
			String  sleepStartTime=readProperties.get("sleepStartTime");
			String memberCode=paraMap.get("memberCode")+"";
			AutomaticTrade automaticTrade=manualTranSubService.findAutomaticTrade(memberCode);
			if(automaticTrade == null){
				resultMap.put("responseCode",
						ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
				resultMap.put("responseDesc","未找到人工交易！！");
				return JSonUtil.toJSonString(resultMap);
			}
		/*	CardInfo card=new CardInfo();	// 新批次之前开启 所有被禁用的卡片
			card.setStatus("1");
			card.setUpdateDate(new Date());
			manualTranSubService.updateCardInfo(card);*/

			String cardCount = automaticTrade.getCardCount(); // 每张卡交易的次数
			int tradingTime = Integer.parseInt(tradingTimes);
			int cardcount=Integer.parseInt(cardCount);
			int allCardTradeCount=cardcount*tradingTime; //总交易笔数
			logger.info("交易总笔数: "+allCardTradeCount);
			int i=1;
			Random random = new Random();
			float amountmax = Float.parseFloat(amountMax);
			float amountmin = Float.parseFloat(amountMin);
		   
			int sum = 0;  //add by Mack  2016年6月1日18
			
			while (i<=allCardTradeCount) {
				List<AutomaticTrade> queryManualTran = manualTranSubService.queryManualTran(automaticTrade,MapUtil.map2Object(Page.class, null));
				if(queryManualTran == null || queryManualTran.size()<=0){
					resultMap.put("responseCode",
							ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
					resultMap.put("responseDesc","交易已被停止！！！");
					return JSonUtil.toJSonString(resultMap);
				}
				List<CardInfo>	cardInfos=manualTranSubService.findCardInfoAll(); //查询卡信息
				if(cardInfos!=null && cardInfos.size()>0  ){
			      sum = cardInfos.size();			
				}
				Iterator<CardInfo> iterator = cardInfos.iterator();
				//下面循环初始化本批次卡号状态
				while (iterator.hasNext()) {
					CardInfo cardInfo = (CardInfo) iterator.next();
					SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
					Date date = new Date();
					String format = dateFormat1.format(date);
					Date updateDate = cardInfo.getUpdateDate();
					
					int month = updateDate.getMonth(); //最后更新月份 
					int month2 = date.getMonth();   //当前月分
					if(month<month2){
						CardInfo cardInfo2=new CardInfo();
						cardInfo2.setUpdateDate(new Date());
						cardInfo2.setMonthsSuccessCount("0");
						cardInfo2.setTradingTimes("0");
						cardInfo2.setDaySuccessCount("0");
						cardInfo2.setDayFailCount("0");
						cardInfo2.setId(cardInfo.getId());
						manualTranSubService.updateCardInfo(cardInfo2);	
					}else{
						if (!updateDate.after(dateFormat1.parse(format))) {
							CardInfo cardInfo3=new CardInfo();
							cardInfo3.setUpdateDate(new Date());
							cardInfo3.setTradingTimes("0");
							cardInfo3.setDaySuccessCount("0");
							cardInfo3.setDayFailCount("0");
							cardInfo3.setId(cardInfo.getId());
							manualTranSubService.updateCardInfo(cardInfo3);	
						}
					}
				}
				if(cardInfos==null || cardInfos.size()<=0){
					AutomaticTrade automatic=new AutomaticTrade();
					//automatic.setSuccesCount(i+"");
					automatic.setEndDate(new Date());
					automatic.setBatchNumber(automaticTrade.getBatchNumber());
					automatic.setStatus("2");
					manualTranSubService.updateAutomaticTrade(automatic);
					resultMap.put("responseCode",
							ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
					resultMap.put("responseDesc","请配置卡信息！！");
					return JSonUtil.toJSonString(resultMap);
				}
				if(i==1){
					AutomaticTrade autoTrade=new AutomaticTrade();  //设置开始时间
					autoTrade.setBatchNumber(automaticTrade.getBatchNumber());
					autoTrade.setStartDate(new Date());
					manualTranSubService.updateAutomaticTrade(autoTrade);
				}
				//Modified below by Mack  2016年6月1日18
				//int s = random.nextInt(cardInfos.size())%(cardInfos.size()-0+1) + 0;  //随机取卡
				int s = (i%sum);
				BigDecimal db = new BigDecimal(Math.random() * (amountmax - amountmin) + amountmin).multiply(new BigDecimal(100));   //随机取金额
				BigDecimal setScale = db.setScale(0, BigDecimal.ROUND_HALF_UP);  //保留两位小数
				Date currentTime = new Date();    //生成  orderId
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
				Map paramap=new HashMap();
				paramap.put("version", "1.0");
				paramap.put("orderId", formatter.format(currentTime));
				paramap.put("goodsName", "myGoodsName");
				paramap.put("goodsDesc", "myGoods");
				paramap.put("submitTime", format.format(currentTime));
				paramap.put("customerIP", "180.168.145.74");
				paramap.put("siteId", "www.pay.com");
				paramap.put("orderAmount", setScale.toString());
				paramap.put("tradeType", "1000");
				paramap.put("payType", "EDC");
				paramap.put("currencyCode", currencyCode);
				paramap.put("borrowingMarked", "0");
				paramap.put("noticeUrl", "http://localhost/gatewayTest/notify");
				paramap.put("partnerId", automaticTrade.getPartnerId().trim());
				paramap.put("billName", "chaoyue");
				paramap.put("billAddress", "shanghai,china");
				paramap.put("billEmail", "501374997@qq.com");
				paramap.put("billPhoneNumber", "18621758498");
				paramap.put("billPostalCode", "200120");
				paramap.put("billStreet", "laoshan");
				paramap.put("billCity", "shanghai");
				paramap.put("billCountryCode", "86");
				paramap.put("shippingName", "chaoyue");
				paramap.put("shippingAddress", "laoshan,shanghai,china");
				paramap.put("shippingMail", "peiyu.yang@live.com");
				paramap.put("shippingPhoneNumber", "02160625509");
				paramap.put("shippingPostalCode", "888888");
				paramap.put("shippingStreet", "shangchengroad");
				paramap.put("shippingCity", "shanghai");
				paramap.put("shippingState", "shanghai");
				paramap.put("shippingCountryCode", "86");
				paramap.put("payMode", "10");
				paramap.put("cardHolderNumber", cardInfos.get(s).getCardCode());
				paramap.put("cardHolderFirstName", "yang");
				paramap.put("cardHolderLast", "chao");
				paramap.put("billState", "shanghai");
				paramap.put("cardExpirationMonth",cardInfos.get(s).getEffectiveMonth());
				paramap.put("cardExpirationYear",cardInfos.get(s).getEffectiveYear());
				paramap.put("securityCode",cardInfos.get(s).getCvv());
				paramap.put("cardHolderEmail","y493571ds1@126.com");
				paramap.put("deviceFingerprintId","fdsfds");
				paramap.put("orderTerminal","00");
				paramap.put("prdtCode","EDC");
				paramap.put("signMsg", "2");
				paramap.put("signType", "2");
				paramap.put("charset", "1");
				PaymentInfo paymentInfo = MapUtil.map2Object(PaymentInfo.class,
						paramap);
				PaymentResult paymentResult = apiPayService   //下单
						.crossApiPay(paymentInfo);

				if(paymentResult.getResponseCode().equals("0000")){
					AutomaticTrade automatic=new AutomaticTrade();  //更新成功状态
					automatic.setSuccesCount(i+"");
					automatic.setBatchNumber(automaticTrade.getBatchNumber());
					manualTranSubService.updateAutomaticTrade(automatic);
					CardInfo cardInfo=new CardInfo();   //更新那张卡的 成功笔数 状态
					cardInfo.setId(cardInfos.get(s).getId());
					cardInfo.setSuccessCount(i+"");	
					cardInfo.setUpdateDate(new Date());
					manualTranSubService.updateCardInfo(cardInfo);	
				}else{
					int y=1; //失败笔数
					CardInfo cardInfo=new CardInfo();  //更新 失败的状态 并且禁用那张卡
					cardInfo.setId(cardInfos.get(s).getId());
					cardInfo.setFailCount(y+"");
					cardInfo.setStatus("2");
					cardInfo.setUpdateDate(new Date());
					manualTranSubService.updateCardInfo(cardInfo);
					y++;
				}
				Long tradeOrderNo = paymentResult.getTradeOrderNo();  //更新网关订单号 和 自动刷单的 关系表
				String batchNumber = automaticTrade.getBatchNumber();
				AutoTranRela autoTranRela=new AutoTranRela();
				autoTranRela.setBatchNumber(batchNumber);
				autoTranRela.setTradeOrderNo(tradeOrderNo+"");
				paymentService.getTradeOrderService().createAutoTranRela(autoTranRela);
				
				if(i==allCardTradeCount){
						AutomaticTrade automatic=new AutomaticTrade();   // 交易数量 已完成
					//	automatic.setSuccesCount(i+"");
						automatic.setBatchNumber(automaticTrade.getBatchNumber());
						automatic.setStatus("2");
						automatic.setEndDate(new Date()); //结束时间
						manualTranSubService.updateAutomaticTrade(automatic);
				}
				resultMap.putAll(MapUtil.bean2map(paymentResult));
				resultMap.put("responseCode", paymentResult.getResponseCode());
				resultMap.put("responseDesc", paymentResult.getResponseDesc());
				int sleepStart=Integer.parseInt(sleepStartTime);
				int sleepEnd=Integer.parseInt(sleepEndTime); 
				int a = random.nextInt(sleepEnd)%(sleepEnd-sleepStart+sleepStart) + sleepStart;  //随机暂停秒数
				Thread.sleep(a*1000);  
				i++;
			}
		} catch (Exception e) {
			logger.error("api pay error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.MSG_PARSING_FAILURE.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}

	/***
	 * 读取配置
	 * 
	 * @param filePath
	 * @return
	 */
	public Map<String, String> readProperties(String filePath) {
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					filePath));
			props.load(in);
			Set<Object> keySet = props.keySet();
			Map<String, String> map = new HashMap<String, String>();
			for (Object object : keySet) {
				map.put(object + "", props.get(object) + "");
			}
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
