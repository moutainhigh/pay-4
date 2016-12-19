package com.pay.jms.listener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.jms.notification.request.NotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.monitor.service.OrderMonitorService;
import com.pay.redis.RedisClientTemplate;
import com.pay.rm.blacklistcheck.dto.BlackChecklistDTO;
import com.pay.rm.blacklistcheck.service.BlacklistCheckService;
import com.pay.rm.orderthrehold.dto.OrderThreholdRuleDTO;
import com.pay.rm.orderthrehold.service.OrderThreholdService;

/**
 * 监听高频风险订单消息
 * @author dehong.liu
 *
 */
public class BlacklistMessageListener implements MessageListener {

	private final Log log = LogFactory.getLog(BlacklistMessageListener.class);
	
	 private RedisClientTemplate redisClient;
	 private BlacklistCheckService blacklistCheckService;
	 private OrderThreholdService orderThreholdService;
	 private JmsSender jmsSender;
	 private OrderMonitorService  orderMonitorService; 
	 
	 
	public JmsSender getJmsSender() {
		return jmsSender;
	}
	public OrderMonitorService getOrderMonitorService() {
		return orderMonitorService;
	}
	public void setOrderMonitorService(OrderMonitorService orderMonitorService) {
		this.orderMonitorService = orderMonitorService;
	}
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
	public OrderThreholdService getOrderThreholdService() {
		return orderThreholdService;
	}
	public void setOrderThreholdService(OrderThreholdService orderThreholdService) {
		this.orderThreholdService = orderThreholdService;
	}
	public BlacklistCheckService getBlacklistCheckService() {
		return blacklistCheckService;
	}
	public void setBlacklistCheckService(BlacklistCheckService blacklistCheckService) {
		this.blacklistCheckService = blacklistCheckService;
	}
	public RedisClientTemplate getRedisClient() {
		return redisClient;
	}
	public void setRedisClient(RedisClientTemplate redisClient) {
		this.redisClient = redisClient;
	}
	


	public void onMessage(Message message) {
		if (message instanceof ObjectMessage) {
			ObjectMessage om = (ObjectMessage) message;
			
			NotifyRequest request;
			try {
				request = (NotifyRequest) om.getObject();
				log.info("开始插入风险缓存  request ="+ request);
				Map<String, String> bnotifyMap=request.getData();
				String cardNo =bnotifyMap.get("cardHolderNumber");
				String IP =bnotifyMap.get("customerIP");
				String email =bnotifyMap.get("billEmail");
				String billAddress =bnotifyMap.get("billAddress");
				String shippingAddress =bnotifyMap.get("shippingAddress");
				String tradeOrderNo =String.valueOf(bnotifyMap.get("tradeOrderNo"));
				String result =bnotifyMap.get("result");
				String responseDesc =bnotifyMap.get("responseDesc");
				String memberCode=request.getMerchantCode();
				String cardCountry =bnotifyMap.get("cardCountry");
				String billCountry =bnotifyMap.get("billCountryCode");
				String shippingCountry =bnotifyMap.get("shippingCountryCode");
				SimpleDateFormat format1 = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
				//单位时间内
				long orderThrehold_duration = 60*3;
				//单位时间内次数
				long orderThrehold_threhold=3;
				if(redisClient.exists("orderThrehold_duration")){
					orderThrehold_duration=Long.valueOf(redisClient.get("orderThrehold_duration"));
					orderThrehold_threhold=Long.valueOf(redisClient.get("orderThrehold_threhold"));
				}else{
					OrderThreholdRuleDTO orderThreholdRuleDTO	=orderThreholdService.findOrderThreholdRule("orderThrehold");
					if(orderThreholdRuleDTO!=null){
						orderThrehold_duration=orderThreholdRuleDTO.getDuration();
						orderThrehold_threhold=orderThreholdRuleDTO.getThrehold();
						redisClient.set("orderThrehold_duration", String.valueOf(orderThrehold_duration));
						redisClient.set("orderThrehold_threhold", String.valueOf(orderThrehold_threhold));
					}
				
				}
				
				long memberduration =60;
				 long menbertimes =7;
				 long emailduration =12*60;
				 long emailtimes =5; 
				if(redisClient.exists("orderriskmoniter.rule")){
					Map<String, String> ordermonitorMap=redisClient.hgetAll("orderriskmoniter.rule");
					memberduration=Long.valueOf(ordermonitorMap.get("memberduration"));
					menbertimes=Long.valueOf(ordermonitorMap.get("menbertimes"));
					emailduration=Long.valueOf(ordermonitorMap.get("emailduration"))*60;
					emailtimes=Long.valueOf(ordermonitorMap.get("emailtimes"));
					}
				
				
				//缓存中如果不存卡号则添加，存在则判断是否超过阀值，超过阀值极为风险订单，插入风险订单表				
				if(redisClient.exists(cardNo)){
					redisClient.persist(cardNo);
					Map<String, String> redisMap=redisClient.hgetAll(cardNo);
					long counts =Long.valueOf(redisMap.get("counts"))+1;
					StringBuffer tradeOrderNo1=new StringBuffer(redisMap.get("tradeOrderNo"));
					tradeOrderNo1.append(",").append(tradeOrderNo);
					redisMap.put("counts", String.valueOf(counts));
					redisMap.put("tradeOrderNo", tradeOrderNo1.toString());
					redisClient.hmset(cardNo, redisMap);
					redisClient.expire(cardNo, (int)orderThrehold_duration);
					if(counts>orderThrehold_threhold){
						//redisClient.del(cardNo);
						BlackChecklistDTO dto= new BlackChecklistDTO();
						dto.setMemberCode(memberCode);						
						dto.setTradeOrderNos(tradeOrderNo1.toString());
						dto.setStatus(0);
						dto.setBusinessTypeId(1);
						dto.setContent(cardNo);
						blacklistCheckService.addBlackchecklist(dto);
						
					}					
						
				}else{
					Map<String, String> redisMap =new HashMap<String, String>();
					redisMap.put("counts", "1");
					redisMap.put("tradeOrderNo", tradeOrderNo);
					redisClient.hmset(cardNo, redisMap);
					redisClient.expire(cardNo, (int)orderThrehold_duration);
				}
				
				
				
				//缓存中如果不存在IP则添加，存在则判断是否超过阀值，超过阀值极为风险订单，插入风险订单表				
				if(redisClient.exists(IP)){
					redisClient.persist(IP);
					Map<String, String> redisMap=redisClient.hgetAll(IP);
					long counts =Long.valueOf(redisMap.get("counts"))+1;
					StringBuffer tradeOrderNo1=new StringBuffer(redisMap.get("tradeOrderNo"));
					tradeOrderNo1.append(",").append(tradeOrderNo);
					redisMap.put("counts", String.valueOf(counts));
					redisMap.put("tradeOrderNo", tradeOrderNo1.toString());
					redisClient.hmset(IP, redisMap);
					redisClient.expire(IP, (int)orderThrehold_duration);
					if(counts>orderThrehold_threhold){
						
						BlackChecklistDTO dto= new BlackChecklistDTO();
						dto.setMemberCode(memberCode);						
						dto.setTradeOrderNos(tradeOrderNo1.toString());
						dto.setStatus(0);
						dto.setBusinessTypeId(3);
						dto.setContent(IP);
						blacklistCheckService.addBlackchecklist(dto);
					}
					
				}else{
					Map<String, String> redisMap =new HashMap<String, String>();
					redisMap.put("counts", "1");
					redisMap.put("tradeOrderNo", tradeOrderNo);
					redisClient.hmset(IP, redisMap);
					redisClient.expire(IP, (int)orderThrehold_duration);
				}
				
				
				//缓存中如果不存在email则添加，存在则判断是否超过阀值，超过阀值极为风险订单，插入风险订单表				
				if(redisClient.exists(email)){
					redisClient.persist(email);
					Map<String, String> redisMap=redisClient.hgetAll(email);
					long counts =Long.valueOf(redisMap.get("counts"))+1;
					StringBuffer tradeOrderNo1=new StringBuffer(redisMap.get("tradeOrderNo"));
					tradeOrderNo1.append(",").append(tradeOrderNo);
					redisMap.put("counts", String.valueOf(counts));
					redisMap.put("tradeOrderNo", tradeOrderNo1.toString());
					redisClient.hmset(email, redisMap);
					redisClient.expire(email, (int)orderThrehold_duration);
					if(counts>orderThrehold_threhold){
						
						BlackChecklistDTO dto= new BlackChecklistDTO();
						dto.setMemberCode(memberCode);						
						dto.setTradeOrderNos(tradeOrderNo1.toString());
						dto.setStatus(0);
						dto.setBusinessTypeId(2);
						dto.setContent(email);
						blacklistCheckService.addBlackchecklist(dto);
					}
					
				}else{
					Map<String, String> redisMap =new HashMap<String, String>();
					redisMap.put("counts", "1");
					redisMap.put("tradeOrderNo", tradeOrderNo);
					redisClient.hmset(email, redisMap);
					redisClient.expire(email, (int)orderThrehold_duration);
				}
				
			//如果存在账单地址，则做处理	
			if(null!=billAddress){
				//缓存中如果不存在IP则添加，存在则判断是否超过阀值，超过阀值极为风险订单，插入风险订单表
				if(redisClient.exists(billAddress)){
					redisClient.persist(billAddress);
					Map<String, String> redisMap=redisClient.hgetAll(billAddress);
					long counts =Long.valueOf(redisMap.get("counts"))+1;
					StringBuffer tradeOrderNo1=new StringBuffer(redisMap.get("tradeOrderNo"));
					tradeOrderNo1.append(",").append(tradeOrderNo);
					redisMap.put("counts", String.valueOf(counts));
					redisMap.put("tradeOrderNo", tradeOrderNo1.toString());
					redisClient.hmset(billAddress, redisMap);
					redisClient.expire(billAddress, (int)orderThrehold_duration);
					if(counts>orderThrehold_threhold){
						
						BlackChecklistDTO dto= new BlackChecklistDTO();
						dto.setMemberCode(memberCode);					
						dto.setTradeOrderNos(tradeOrderNo1.toString());
						dto.setStatus(0);
						dto.setBusinessTypeId(9);
						dto.setContent(billAddress);
						blacklistCheckService.addBlackchecklist(dto);
					}
					
				}else{
					Map<String, String> redisMap =new HashMap<String, String>();
					redisMap.put("counts", "1");
					redisMap.put("tradeOrderNo", tradeOrderNo);
					redisClient.hmset(billAddress, redisMap);
					redisClient.expire(billAddress, (int)orderThrehold_duration);
				}
			}
			
			//如果存在收货地址，则做处理	
			if(null!=shippingAddress){
				//缓存中如果不存在IP则添加，存在则判断是否超过阀值，超过阀值极为风险订单，插入风险订单表
				if(redisClient.exists(shippingAddress)){
					redisClient.persist(shippingAddress);
					Map<String, String> redisMap=redisClient.hgetAll(shippingAddress);
					long counts =Long.valueOf(redisMap.get("counts"))+1;
					StringBuffer tradeOrderNo1=new StringBuffer(redisMap.get("tradeOrderNo"));
					tradeOrderNo1.append(",").append(tradeOrderNo);
					redisMap.put("counts", String.valueOf(counts));
					redisMap.put("tradeOrderNo", tradeOrderNo1.toString());
					redisClient.hmset(shippingAddress, redisMap);
					redisClient.expire(shippingAddress, (int)orderThrehold_duration);
					if(counts>orderThrehold_threhold){
					
						BlackChecklistDTO dto= new BlackChecklistDTO();
						dto.setMemberCode(memberCode);						
						dto.setTradeOrderNos(tradeOrderNo1.toString());
						dto.setStatus(0);
						dto.setBusinessTypeId(8);
						dto.setContent(shippingAddress);
						blacklistCheckService.addBlackchecklist(dto);
					
					}
					
				}else{
					Map<String, String> redisMap =new HashMap<String, String>();
					redisMap.put("counts", "1");
					redisMap.put("tradeOrderNo", tradeOrderNo);
					redisClient.hmset(shippingAddress, redisMap);
					redisClient.expire(shippingAddress, (int)orderThrehold_duration);
				}
			}
				
			
			//缓存中如果不存会员号则添加，存在则判断是否超过阀值，超过阀值极为风险订单，插入风控报表				
			if(redisClient.exists(memberCode)){
				redisClient.persist(memberCode);
				Map<String, String> redisMap=redisClient.hgetAll(memberCode);
				long counts =Long.valueOf(redisMap.get("counts"))+1;
				StringBuffer tradeOrderNo1=new StringBuffer(redisMap.get("tradeOrderNo"));
				tradeOrderNo1.append(",").append(tradeOrderNo);
				redisMap.put("counts", String.valueOf(counts));
				redisMap.put("tradeOrderNo", tradeOrderNo1.toString());
				redisClient.hmset(memberCode, redisMap);
				redisClient.expire(memberCode, (int)orderThrehold_duration);
				if(counts>orderThrehold_threhold){
					//redisClient.del(cardNo);
					BlackChecklistDTO dto= new BlackChecklistDTO();
					dto.setMemberCode(memberCode);						
					dto.setTradeOrderNos(tradeOrderNo1.toString());
					dto.setStatus(0);
					dto.setBusinessTypeId(1);
					dto.setContent(cardNo);
					blacklistCheckService.addBlackchecklist(dto);
					
				}					
					
			}else{
				Map<String, String> redisMap =new HashMap<String, String>();
				redisMap.put("counts", "1");
				redisMap.put("tradeOrderNo", tradeOrderNo);
				redisClient.hmset(cardNo, redisMap);
				redisClient.expire(cardNo, (int)orderThrehold_duration);
			}
			
			//=======================交易监控预警规则报表======================================//
			
			
			
//			//缓存中如果不存会员号则添加，存在则判断失败订单是否超过阀值，超过阀值极为风险订单，插入风控报表		
//			String M_memberCode=memberCode+"_M";
//			if(!"0000".equals(result)){
//			if(redisClient.exists(M_memberCode)){
//				redisClient.persist(M_memberCode);
//				Map<String, String> redisMap=redisClient.hgetAll(M_memberCode);
//				long counts =Long.valueOf(redisMap.get("counts"))+1;
//				
//				StringBuffer tradeOrderNo1=new StringBuffer(redisMap.get("tradeOrderNo"));
//				if(redisMap.get("tradeOrderNo").equals("")){
//					tradeOrderNo1.append(tradeOrderNo);
//				}else{
//					tradeOrderNo1.append(",").append(tradeOrderNo);
//				}
//				
//				redisMap.put("counts", String.valueOf(counts));
//				redisMap.put("tradeOrderNo", tradeOrderNo1.toString());
//				redisClient.hmset(M_memberCode, redisMap);
//				redisClient.expire(M_memberCode, (int)memberduration);
//				if(counts>menbertimes){
//					
//					//超过阈值，将缓存中数据，抛向消息队列，以便处理
//					BlacklistCheckNotifyRequest monitorRequest = new BlacklistCheckNotifyRequest() {
//					};
//					
////					//利用该字段传递监控类型
////					monitorRequest.setBizCode("1");
////					//利用该字段传递监控值
////					monitorRequest.setMerchantCode(memberCode);
////					//利用该字段抓取到的订单号
////					monitorRequest.setBizId(tradeOrderNo1.toString());
////					monitorRequest.setData(new HashMap<String, String>());
////					
////					jmsSender.send("notify.forpay.moitorlist", monitorRequest);		
//					
//					//完成监控信息表的插入
//					String[] monitorlist =tradeOrderNo1.toString().split(",");
//					if( monitorlist.length>0){
//						for (int i = 0; i < monitorlist.length; i++) {
//						
//							orderMonitorService.addMonitorOrder(monitorlist[i],1,bnotifyMap);
//						}
//					}
//										
//					redisMap.put("tradeOrderNo", "");
//					redisClient.hmset(M_memberCode, redisMap);
//					
//					
//				}					
//					
//			}else{
//				Map<String, String> redisMap =new HashMap<String, String>();
//				redisMap.put("counts", "1");
//				redisMap.put("tradeOrderNo", tradeOrderNo);
//				redisClient.hmset(M_memberCode, redisMap);
//				redisClient.expire(M_memberCode, (int)memberduration);
//			}
//		}	
			
			
			
//			String M_memberCode=memberCode+"_M";
//			if(!"0000".equals(result)){
//			if(redisClient.exists(M_memberCode)){
//				redisClient.persist(M_memberCode);
//				Map<String, String> redisMap=redisClient.hgetAll(M_memberCode);
//				long counts =Long.valueOf(redisMap.get("counts"))+1;
//				
//				StringBuffer tradeOrderNo1=new StringBuffer(redisMap.get("tradeOrderNo"));
//				
//				tradeOrderNo1.append(",").append(tradeOrderNo+"_"+bnotifyMap.get("completeDate"));
//				
//				
//				redisMap.put("counts", String.valueOf(counts));
//				redisMap.put("tradeOrderNo", tradeOrderNo1.toString());
//				redisClient.hmset(M_memberCode, redisMap);
//								
//					
//			}else{
//				Map<String, String> redisMap =new HashMap<String, String>();
//				redisMap.put("counts", "1");
//				redisMap.put("tradeOrderNo", tradeOrderNo+"_"+bnotifyMap.get("completeDate"));
//				redisClient.hmset(M_memberCode, redisMap);
//				redisClient.expire(M_memberCode, (int)memberduration);
//				String trademonitoremail =redisClient.get("forpay.trademonitoremail");
//				if(null==trademonitoremail){
//					redisClient.set("forpay.trademonitor", M_memberCode);
//				}else{
//					redisClient.set("forpay.trademonitor", trademonitoremail+","+M_memberCode);
//				}
//				
//			}
//		}	
			
			
			String M_memberCode=memberCode+"_M";
			if(!"0000".equals(result)){
			if(redisClient.exists(M_memberCode)){
				
				redisClient.rpush(M_memberCode, tradeOrderNo+"_"+bnotifyMap.get("completeDate")+"_"+bnotifyMap.get("cardHolderNumber")+"_"+bnotifyMap.get("cardCountry")+"_"+bnotifyMap.get("result")+"_"+bnotifyMap.get("responseDesc"));
				long len =redisClient.llen(M_memberCode);
				if(len>menbertimes){
					try {
					String[] trademonitortemp1 = redisClient.lindex(M_memberCode, len-menbertimes-1).split("_");
					Date date1;
					
						date1 = format1.parse(trademonitortemp1[1]);
					
					long date11=date1.getTime();
					
					Date date2 = format1.parse(bnotifyMap.get("completeDate"));
					long date21=date2.getTime();					
					
					Double spand =(double) (date21-date11);
						double jiange= spand/(1000 * 60 );
						if(jiange<memberduration){
							for (int i = (int) (len-menbertimes)-1; i < len; i++) {
								
								String trademonitortemp2 = redisClient.lindex(M_memberCode, i);
								orderMonitorService.addMonitorOrder(trademonitortemp2,1);
								
							}
						}} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}					
								
					
			}else{
				redisClient.rpush(M_memberCode, tradeOrderNo+"_"+bnotifyMap.get("completeDate")+"_"+bnotifyMap.get("cardHolderNumber")+"_"+bnotifyMap.get("cardCountry")+"_"+bnotifyMap.get("result")+"_"+bnotifyMap.get("responseDesc"));
				int exprireTime =getTimesnight()-timeStamp();
				redisClient.expire(M_memberCode, exprireTime);
				
				
			}
		}	
			
			String M_email=email+"_M";
			
			if(redisClient.exists(M_email)){
				redisClient.rpush(M_email, tradeOrderNo+"_"+bnotifyMap.get("completeDate")+"_"+bnotifyMap.get("cardHolderNumber")+"_"+bnotifyMap.get("cardCountry")+"_"+bnotifyMap.get("result")+"_"+bnotifyMap.get("responseDesc"));
				long len =redisClient.llen(M_email);
				if(len>emailtimes){
					try {
					String[] trademonitortemp1 = redisClient.lindex(M_email, len-emailtimes-1).split("_");
					Date date1;
					
						date1 = format1.parse(trademonitortemp1[1]);
					
					long date11=date1.getTime();
					
					Date date2 = format1.parse(bnotifyMap.get("completeDate"));
					long date21=date2.getTime();					
					
					Double spand =(double) (date21-date11);
						double jiange= spand/(1000 * 60 );
						if(jiange<emailduration){
							for (int i = (int) (len-emailtimes)-1; i < len; i++) {
								
								String trademonitortemp2 = redisClient.lindex(M_email, i);
								orderMonitorService.addMonitorOrder(trademonitortemp2,2);
								
							}
						}} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}				
					
			}else{
				redisClient.rpush(M_email, tradeOrderNo+"_"+bnotifyMap.get("completeDate")+"_"+bnotifyMap.get("cardHolderNumber")+"_"+bnotifyMap.get("cardCountry")+"_"+bnotifyMap.get("result")+"_"+bnotifyMap.get("responseDesc"));
				int exprireTime =getTimesnight()-timeStamp();
				redisClient.expire(M_email, exprireTime);
				
			
		}	
			
			
			
//			String M_email=email+"_M";
//			
//			if(redisClient.exists(M_email)){
//				redisClient.persist(M_email);
//				Map<String, String> redisMap=redisClient.hgetAll(M_email);
//				long counts =Long.valueOf(redisMap.get("counts"))+1;
//				
//				StringBuffer tradeOrderNo1=new StringBuffer(redisMap.get("tradeOrderNo"));
//				if(redisMap.get("tradeOrderNo").equals("")){
//					tradeOrderNo1.append(tradeOrderNo);
//				}else{
//					tradeOrderNo1.append(",").append(tradeOrderNo);
//				}
//				
//				redisMap.put("counts", String.valueOf(counts));
//				redisMap.put("tradeOrderNo", tradeOrderNo1.toString());
//				redisClient.hmset(M_email, redisMap);
//				redisClient.expire(M_email, (int)emailduration);
//				if(counts>menbertimes){
//					
//					//完成监控信息表的插入
//					String[] monitorlist =tradeOrderNo1.toString().split(",");
//					if( monitorlist.length>0){
//						for (int i = 0; i < monitorlist.length; i++) {
//						
//							orderMonitorService.addMonitorOrder(monitorlist[i],2,bnotifyMap);
//						}
//					}
//										
//					redisMap.put("tradeOrderNo", "");
//					redisClient.hmset(M_email, redisMap);
//					
//					
//				}					
//					
//			}else{
//				Map<String, String> redisMap =new HashMap<String, String>();
//				redisMap.put("counts", "1");
//				redisMap.put("tradeOrderNo", tradeOrderNo);
//				redisClient.hmset(M_email, redisMap);
//				redisClient.expire(M_email, (int)emailduration);
//			
//		}	
			
			
			if(billCountry!=null&&shippingCountry!=null){
				if(!billCountry.equals(shippingCountry)){
					orderMonitorService.addMonitorOrder(tradeOrderNo,3,bnotifyMap);
				}
			}
			
			
							
				log.info("完成插入风险缓存  request ="+ request);
			} catch (JMSException e) {
				log.info("插入风险缓存失败 ");
				log.error(e);
			}
			
			
			
			
			
			
			
		}
	}
	
	 public  int getTimesnight(){
		  Calendar cal = Calendar.getInstance();
		  cal.set(Calendar.HOUR_OF_DAY, 24);
		  cal.set(Calendar.SECOND, 0);
		  cal.set(Calendar.MINUTE, 0);
		  cal.set(Calendar.MILLISECOND, 0);
		  return (int) (cal.getTimeInMillis()/1000);
		 } 
	 
	 public  int timeStamp(){ 
	     long time = System.currentTimeMillis(); 
	     return   (int) (time/1000);
	 }  
}