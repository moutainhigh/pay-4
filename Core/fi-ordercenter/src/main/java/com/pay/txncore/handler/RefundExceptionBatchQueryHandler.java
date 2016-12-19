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
import com.pay.txncore.dto.RefundExceptionBatchDTO;
import com.pay.txncore.service.RefundExceptionBatchService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 退款状态批量处理查询服务
 * @author PengJiangbo
 */
public class RefundExceptionBatchQueryHandler implements EventHandler {

	private static final Log logger = LogFactory.getLog(RefundExceptionBatchQueryHandler.class) ;
	//注入
	private RefundExceptionBatchService refundExceptionBatchService ;
	public void setRefundExceptionBatchService(
			RefundExceptionBatchService refundExceptionBatchService) {
		this.refundExceptionBatchService = refundExceptionBatchService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass()) ;
			Map pageMap =(Map) paraMap.get("page") ;
			if(null == pageMap){
				//不分页
				List<RefundExceptionBatchDTO> refundExceptionBatchs = this.refundExceptionBatchService.queryRefundExceptionBatch(paraMap) ;
				resultMap.put("result", refundExceptionBatchs) ;
			}else{
				//分页处理
				Page page = MapUtil.map2Object(Page.class, pageMap) ;
				List<RefundExceptionBatchDTO> refundExceptionBatchs = this.refundExceptionBatchService.queryRefundExceptionBatch(paraMap, page) ;
				resultMap.put("result", refundExceptionBatchs) ;
				resultMap.put("page", page) ;
			}
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode()) ;
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc()) ;
		} catch (Exception e) {
			logger.error("query refundExceptionBatch error：" + e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

}
