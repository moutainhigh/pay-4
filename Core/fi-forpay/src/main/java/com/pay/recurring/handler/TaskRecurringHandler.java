package com.pay.recurring.handler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.forpay.client.TxncoreClientService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.recurring.model.RecurringQue;
import com.pay.recurring.model.RecurringResponse;
import com.pay.recurring.service.TaskRecurringService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class TaskRecurringHandler implements EventHandler{

	public final Log logger = LogFactory
			.getLog(TaskRecurringHandler.class);
	
	private TaskRecurringService  taskRecurringService;
	
	private TxncoreClientService txncoreClientService;
	
	private JmsSender jmsSender;
	
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}
	public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
		this.txncoreClientService = txncoreClientService;
	}
	public void settaskRecurringService(
			TaskRecurringService taskRecurringService) {
		this.taskRecurringService = taskRecurringService;
	}


	public String handle(String dataMsg) throws HessianInvokeException {

		Map resultMap = new HashMap();
		
		try {
			  this.taskRecurringService.crateRecurringQue();//生成队列
			  List<RecurringQue> recurringQue=this.taskRecurringService.findRecurringQue();//查询队列
			  if(null == recurringQue || recurringQue.size() == 0 ){//判断队列中是否有数据。没有则没有需要循环扣款的数据
				  resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				  resultMap.put("responseDesc", "没有需要扣款的订单。");
				  return JSonUtil.toJSonString(resultMap);
			  }
			  for (RecurringQue recurringQue2 : recurringQue) {
				  	logger.info("recurring logs memberCode"+recurringQue2.getMemberCode()+"  issueno"+recurringQue2.getIssueno()+"  Amount"+recurringQue2.getAmount()+"  orderId"+recurringQue2.getOrderId());
			  		Map paraMap=new HashMap();			  
			  		String tradeRequest = recurringQue2.getStrContent(); //解析报文
					if(StringUtils.isNotEmpty(tradeRequest)){
						String[] data = tradeRequest.split("&");
						for(String parameter:data){
							String[] kv = StringUtils.split(parameter, "=", 2);
							if(kv.length > 1) {
								if(kv[0].equals("orderId")){
									if(recurringQue2.getIssueno()+"".length()==1){//判断商户订单号的位数 10002142 01的格式
										paraMap.put(kv[0], kv[1]+"0"+recurringQue2.getIssueno()+recurringQue2.getFailedTimes());
									}else{//10002142 12
										paraMap.put(kv[0], kv[1]+""+recurringQue2.getIssueno()+recurringQue2.getFailedTimes());
									}
								}else if(kv[0].equals("submitTime")){
									SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
									paraMap.put("submitTime", format.format(new Date()));
								}else if(kv[0].equals("tradeType")){
									paraMap.put("tradeType", "5000");
								}else{
									paraMap.put(kv[0], kv[1]);
								}
							}else{
								if(kv.length == 1 && StringUtils.isNotEmpty(kv[0]))
								paraMap.put(kv[0], null);
							}
						}
					}
					 resultMap=this.txncoreClientService.crossApiPay(paraMap);
					 String responseCode=resultMap.get("responseCode")+"";
					 String responseDesc=resultMap.get("responseDesc")+"";
					 if(!responseCode.equals("0000")){
						 this.taskRecurringService.updateRecurring(recurringQue2); //更新循环退款表
						 this.taskRecurringService.delRecurringQue(recurringQue2); //删除队列表信息
						 logger.info("responseCode = "+responseCode+"  responseDesc = "+responseDesc); 
						 RecurringResponse recurringResponse=new RecurringResponse();
						 this.packaging(recurringResponse, recurringQue2);
						 recurringResponse.setResultCode(responseCode);
						 recurringResponse.setResultDesc(responseDesc);
						 this.notifyMerchant(recurringResponse); 
						logger.info("recurring logs: responseCode = "+responseCode+"  responseDesc = "+resultMap.get("responseDesc"));//发消息通知商户
					 }else{
						 Boolean flag=this.taskRecurringService.compareFailedTimes(recurringQue2);//比较失败次数是否到了最大失败的次数
						 if(!flag){ //false 为失败次数 已到达最多可失败笔数
							 this.taskRecurringService.updateRecurringQue(recurringQue2); //失败 更新队列表信息
							 this.taskRecurringService.updateFailedRecurring(recurringQue2); 
						 }else{
							 this.taskRecurringService.updateSingleFailedRecurringQue(recurringQue2);
						 }
						 logger.info("recurring logs: responseCode = "+responseCode+"  responseDesc = "+resultMap.get("responseDesc"));
						 //发消息通知商户
						 RecurringResponse recurringResponse=new RecurringResponse();
						 this.packaging(recurringResponse, recurringQue2);
						 recurringResponse.setResultCode(responseCode);
						 recurringResponse.setResultDesc(responseDesc);
						 this.notifyMerchant(recurringResponse); 	
					 }
				}		
			  
		  resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		  resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("error:", e);
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}
	
	private void notifyMerchant(RecurringResponse recurringResponse) {
		try {
			HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
			Map<String,String> notifyMap = MapUtil
					.bean2map(recurringResponse);
			
			notifyRequest.setData(notifyMap);
			notifyRequest.setTemplateId(1001L);
			notifyRequest.setMerchantCode(recurringResponse.getMemberCode());
			notifyRequest.setUrl(recurringResponse.getCustInterface());
			jmsSender.send(notifyRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void  packaging(RecurringResponse recurringResponse,RecurringQue recurringQue){
		recurringResponse.setMemberCode(recurringQue.getMemberCode()+"");
		recurringResponse.setOrderId(recurringQue.getOrderId());
		recurringResponse.setPaymentDate(recurringQue.getPaymentDate().toString());
		recurringResponse.setCurrencyCode(recurringQue.getCurrencyCode());
		recurringResponse.setAmount(recurringQue.getAmount()+"");
		recurringResponse.setIssueno(recurringQue.getIssueno()+"");
		recurringResponse.setCustInterface(recurringQue.getCustInterface());
	}
	
	
}
