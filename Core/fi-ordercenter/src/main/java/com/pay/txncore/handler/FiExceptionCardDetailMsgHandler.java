/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.ExceptionCardDetailMsgDTO;
import com.pay.txncore.service.FiExceptionCardService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * @author PengJiangbo
 */
public class FiExceptionCardDetailMsgHandler implements EventHandler {

	private static final Log logger = LogFactory.getLog(FiExceptionCardDetailMsgHandler.class) ;
	//注入
	private FiExceptionCardService fiExceptionCardService ;

	/**
	 * @param fiExceptionCardService the fiExceptionCardService to set
	 */
	public void setFiExceptionCardService(
			FiExceptionCardService fiExceptionCardService) {
		this.fiExceptionCardService = fiExceptionCardService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass()) ;
			Map pageMap = (Map) paraMap.get("page") ;
			if(null == pageMap){
				List<ExceptionCardDetailMsgDTO> exceptionCardDetailMsgs = this.fiExceptionCardService.queryExceptionCardDetailMsg(paraMap) ;
				resultMap.put("result", exceptionCardDetailMsgs) ;
			}else{
				Page page = MapUtil.map2Object(Page.class, pageMap) ;
				List<ExceptionCardDetailMsgDTO> exceptionCardDetailMsgs = this.fiExceptionCardService.queryExceptionCardDetailMsg(paraMap, page) ;
				resultMap.put("result", exceptionCardDetailMsgs) ;
				resultMap.put("page", page) ;
			}
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode()) ;
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc()) ;
		} catch (Exception e) {
			logger.error("query fiExceptionCardDetailMsg error：" + e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

}
