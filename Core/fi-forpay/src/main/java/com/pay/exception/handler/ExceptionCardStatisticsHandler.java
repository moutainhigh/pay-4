/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.exception.handler;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.notification.request.WeiXinNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.rm.exceptioncard.dto.ExceptionCardDTO;
import com.pay.rm.exceptioncard.service.ExceptionCardService;
import com.pay.util.JSonUtil;

/**
 * @author PengJiangbo
 */
public class ExceptionCardStatisticsHandler implements EventHandler {

	private static final Log logger = LogFactory.getLog(ExceptionCardStatisticsHandler.class) ;
	//注入
	private ExceptionCardService exceptionCardService ;
	private JmsSender jmsSender;
	
	/**
	 * @param exceptionCardService the exceptionCardService to set
	 */
	public void setExceptionCardService(ExceptionCardService exceptionCardService) {
		this.exceptionCardService = exceptionCardService;
	}

	/**
	 * @param jmsSender the jmsSender to set
	 */
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}



	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map resultMap = new HashMap();
		try {
			Map<String, Object> hMap = new HashMap<String, Object>() ;
			//当天当前时间段记录是否存在
			//构造时间参数
			hMap.put("timeZoneBetween", "true") ;
			hMap.put("timeBetween", "true") ;
			this.getDayStart2EndStr(hMap);
			List<ExceptionCardDTO> queryExceptionCardList = this.exceptionCardService.queryExceptionCardList(hMap) ;
			if(CollectionUtils.isNotEmpty(queryExceptionCardList)){
				for(ExceptionCardDTO exceptionCardDTO : queryExceptionCardList){
					//异常卡比例
					String exceptionCardPercent = exceptionCardDTO.getExceptionCardPercent() ;
					exceptionCardPercent = null == exceptionCardPercent ? "0.00" : exceptionCardPercent.substring(0, exceptionCardPercent.length() -1) ;
					//七天前分时比例
					String aweekAgoTimeZonePercent =  exceptionCardDTO.getAweekAgoTimeZonePercent() ;
					aweekAgoTimeZonePercent = null == aweekAgoTimeZonePercent ? "0.00" : aweekAgoTimeZonePercent.substring(0, aweekAgoTimeZonePercent.length()-1) ;
					//异常卡笔数
					Long exceptionCardCount = exceptionCardDTO.getExceptionCardCount() ;
					//
					double doubleValue = new BigDecimal(exceptionCardPercent).subtract(new BigDecimal(aweekAgoTimeZonePercent)).doubleValue() ;
					Map<String,String> data = new HashMap<String, String>();
					if(doubleValue > 10 || exceptionCardCount > 20){
						try {
							//E预警状态
							if(!"E".equals(exceptionCardDTO.getStatus())){
								ExceptionCardDTO eForUpdate = new ExceptionCardDTO() ;
								eForUpdate.setId(exceptionCardDTO.getId());
								eForUpdate.setStatus("E") ;
								this.exceptionCardService.updateExceptionCardTrans(eForUpdate) ;
							}
						} catch (Exception e) {
							logger.info("更新异常卡状态失败:" + e);
							e.printStackTrace();
						}
						data.put("first","商户异常卡订单比例超限，请确认是否有刷单情况，会员号:" + exceptionCardDTO.getMemberCode());
						data.put("keyword1","异常卡报警提醒");
						SimpleDateFormat formatter = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
				        data.put("keyword2",formatter.format(new Date()));
				        data.put("remark","请尽快检查！");
						this.sendAlertMsg(data);
					}else{
						//正常
						if(!"N".equals(exceptionCardDTO.getStatus())){
							ExceptionCardDTO eForUpdate = new ExceptionCardDTO() ;
							eForUpdate.setId(exceptionCardDTO.getId());
							eForUpdate.setStatus("N") ;
							this.exceptionCardService.updateExceptionCardTrans(eForUpdate) ;
						}
					}
				}
			}
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode()) ;
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc()) ;
		} catch (Exception e) {
			logger.error("query fiExceptionCardDetailMsg error：" + e);
			e.printStackTrace();
		}
		return JSonUtil.toJSonString(resultMap);
	}
	
	
	private void sendAlertMsg(Map<String,String> data){
		WeiXinNotifyRequest request = new WeiXinNotifyRequest();
        request.setBizCode("0015");
        request.setOpenId("0000");
        request.setType(RequestType.WEIXIN);
		request.setRole("R"); //风控
        
		request.setData(data);
		jmsSender.send(request);
	}
	/**
	 * 获取当前日的开始和结束时间
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private void getDayStart2EndStr(Map<String, Object> map){
		try {
			//Map<String, Object> dayStrMap = new HashMap<String, Object>() ;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") ;
			//获取当天开始时间
			Calendar c1 = Calendar.getInstance() ;
			c1.set(Calendar.HOUR_OF_DAY, 0);
			c1.set(Calendar.MINUTE, 0);
			c1.set(Calendar.SECOND, 0);
			String dayStart = df.format(c1.getTime()) ;
			//获取当天结束时间
			Calendar c2 = Calendar.getInstance() ;
			c2.set(Calendar.HOUR_OF_DAY, 23);
			c2.set(Calendar.MINUTE, 59);
			c2.set(Calendar.SECOND, 59);
			String dayEnd = df.format(c2.getTime()) ;
			//获取当前时间小时区间
			Date now = new Date() ;
			String hours = now.getHours()+"" ;
			String hourStart = hours + ":00" ;
			String hourEnd = hours + ":59" ;
			map.put("hours", hours) ;
			map.put("dayStart", dayStart) ;
			map.put("dayEnd", dayEnd) ;
			map.put("hourStart", hourStart) ;
			map.put("hourEnd", hourEnd) ;
		} catch (Exception e) {
			logger.info("构造查询t_exception_card所需要的时间参数出错了!");
			e.printStackTrace();
		}
	    //return map ;
	}
}
