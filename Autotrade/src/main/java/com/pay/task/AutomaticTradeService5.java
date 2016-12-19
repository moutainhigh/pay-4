package com.pay.task;


import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pay.redis.RedisClientTemplate;
import com.pay.txncore.crosspay.handler.AutomaticTradeHandler;
import com.pay.util.JSonUtil;

public class AutomaticTradeService5  extends QuartzJobBean{
		
	private Log logger = LogFactory.getLog(getClass());
	 private AutomaticTradeHandler automaticTradeHandler;

	 private RedisClientTemplate redisClient; 
	 
	
	public RedisClientTemplate getRedisClient() {
		return redisClient;
	}

	public void setRedisClient(RedisClientTemplate redisClient) {
		this.redisClient = redisClient;
	}

	public void setAutomaticTradeHandler(
			AutomaticTradeHandler automaticTradeHandler2) {
		this.automaticTradeHandler = automaticTradeHandler2;
	}
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
	}

	
	public void automaticTrade() {
			
			
			
			String filename="properties/artificialTradeBase2Conf.properties";
			Map<String, String> readProperties = this.readProperties(filename);
			String startflag =readProperties.get("startflag3");
			String picitotal =readProperties.get("picitotal");
			Map paraMap = new HashMap();
			String memberCode=readProperties.get("memberCode3");
			paraMap.put("memberCode", memberCode);
			paraMap.put("startflag", startflag);
			String batchName=readProperties.get("batch3");
			paraMap.put("batch", batchName);
			paraMap.put("pflag", "P3");
			paraMap.put("picitotal", picitotal);
			paraMap.put("cardOrg", readProperties.get("cardOrg3"));
		
			//第一次启动，开启才调用核心进行人工交易
			SimpleDateFormat format1 = new SimpleDateFormat("yyyyMMddHHmmss");
			if(logger.isInfoEnabled()){
				logger.info("开始进行人工交易.....memberCode:"+memberCode+" batchName:"+batchName);
			}
			//第一次启动时
			 if(redisClient.get(startflag).equals("1")){
				 txcoresubmit(paraMap);	
				
			 }else{
				 if(redisClient.exists("P3")){
					 Map redisMap = redisClient.hgetAll("P3");
					String status =redisMap.get("status").toString();
					String subDate;
					if(status.equals("2")){
						subDate =redisMap.get("subDate").toString();}else{
							subDate=redisMap.get("startDate").toString();
						}
					Date date1;
					try {
						date1 = format1.parse(subDate);
						long date11=date1.getTime();
						Date nowdate =new Date();
						long nowdate11=nowdate.getTime();
						Double spand =(double) (nowdate11-date11);
							double jiange= spand/(1000 * 60 *60);
							if(jiange>25){
								logger.info("p3调用核心....."+batchName);
								 txcoresubmit(paraMap);
								logger.info("p3调用核心结束....."+batchName);
							}
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					}
			 }
			 logger.info("结束人工交易.....memberCode:"+memberCode+" batchName:"+batchName); 
	}

	/***
	 * 读取配置
	 * 
	 * @param filePath
	 * @return
	 */
	public Map<String, String> readProperties(String filePath) {
		if(PropertiesMap.maps==null||PropertiesMap.maps.size()==0){
		Properties props = new Properties();
		InputStream in = null;
		try {
			 in =  this.getClass().getClassLoader().getResourceAsStream(filePath);
			props.load(in);
			Set<Object> keySet = props.keySet();
			
			for (Object object : keySet) {
				PropertiesMap.maps.put(object + "", props.get(object) + "");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}finally{
			try {
				if(null!=in)
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
		return PropertiesMap.maps;
}
	
	public void txcoresubmit(Map<String, String> reqMsg) {
		String result=automaticTradeHandler.handle(reqMsg);
		
		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());  
		
		if(!resultMap.get("responseCode").equals("0000")){
			logger.info("人工交易失败！................"+"result: " + result);
			
		}else{
			logger.info("人工交易成功！................"+"result: " + result);
		}
		
	}
	


}
