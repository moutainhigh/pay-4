/**
 * 
 */
package com.pay.notification.impl;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ExceptionCardCodeEnum;
import com.pay.jms.notification.request.ExceptionCardNotifyRequest;
import com.pay.jms.notification.request.NotifyRequest;
import com.pay.notification.ExceptionCardSender;
import com.pay.rm.exceptioncard.dto.ExceptionCardDTO;
import com.pay.rm.exceptioncard.service.ExceptionCardService;

/**
 * @author Jiangbo.peng
 *
 */
public class ExceptionCardSenderImpl implements ExceptionCardSender {

	private static final Log logger = LogFactory.getLog(ExceptionCardSenderImpl.class) ;
	//注入
	private ExceptionCardService exceptionCardService ;
	
	@Override
	public boolean process(NotifyRequest notifyRequest) {
		try {
			logger.info("异常卡监听开始...........................");
//			ExceptionCardNotifyRequest exceptionCardNotifyRequest = (ExceptionCardNotifyRequest) notifyRequest ;
//			//获取网关返回原因码
//			String responseCode = exceptionCardNotifyRequest.getSystemRespCode() ;
//			//根据网关原因码获取所有对应的渠道原因码
//			SystemRespCodeEnum[] codeEnums = null ;
//			boolean flag = false ;
//			String systemRespCode = "" ;
//			String systemRespDesc = "" ;
//			if(!"3099".equals(responseCode)){
//				codeEnums = SystemRespCodeEnum.getResponseCodeEnumArrByRespCode(responseCode) ;
//				//if(null != codeEnums.)
//				for(SystemRespCodeEnum responseCodeEnum : codeEnums){
//					//获取渠道原因码
//					if(null != responseCodeEnum){
//						systemRespCode = responseCodeEnum.getSystemCode() ;
//						if(ExceptionCardCodeEnum.isExists(systemRespCode)){
//							systemRespDesc = responseCodeEnum.getSystemDesc() ;
//							flag = true ;
//							//命中了异常卡范围则跳出
//							break ;
//						}
//					}
//				}
//			}
			
			boolean flag = false ;
			ExceptionCardNotifyRequest exceptionCardNotifyRequest = (ExceptionCardNotifyRequest) notifyRequest ;
			Map<String, String> data = exceptionCardNotifyRequest.getData() ;
			//渠道编码
			String orgCode = data.get("orgCode") ;
			//获取渠道返回原因码
			String channelRespCode = exceptionCardNotifyRequest.getSystemRespCode() ;
			//根据渠道返回码和渠道编码判断渠道返回原因是否在异常卡范围内
			boolean exists = ExceptionCardCodeEnum.isExists(channelRespCode, orgCode) ;
			if(exists){
				//命中异常卡
				flag = true ;
			}
			ExceptionCardDTO exceptionCardDTO = new ExceptionCardDTO() ;
			exceptionCardDTO.setMemberCode(Long.valueOf(exceptionCardNotifyRequest.getMerchantCode()));
			//异常卡导致交易失败
			if(flag){
				logger.info("异常卡导致交易失败，渠道返回原因码为[systemRespCode]＝>>>" + channelRespCode + "渠道编码：" + orgCode); //"-----渠道返回原因描述[systemRespDesc]=>>" + systemRespDesc
				exceptionCardDTO.setExceptionCardFlag(true);
				this.exceptionCardService.saveOrUpdate(exceptionCardDTO);
			}else{
				//非异常卡导致交易失败
				logger.info("非异常卡导致交易失败，渠道返回原因码为[systemRespCode]＝>>>" + channelRespCode + "渠道编码：" + orgCode);
				exceptionCardDTO.setExceptionCardFlag(false);
				this.exceptionCardService.saveOrUpdate(exceptionCardDTO);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false ;
		}
	}
		
	/**
	 * @param exceptionCardService the exceptionCardService to set
	 */
	public void setExceptionCardService(ExceptionCardService exceptionCardService) {
		this.exceptionCardService = exceptionCardService;
	}

	
}
