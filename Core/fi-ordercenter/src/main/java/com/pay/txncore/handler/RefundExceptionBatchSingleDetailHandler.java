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
import com.pay.txncore.dto.RefundExceptionBatchDetailDTO;
import com.pay.txncore.service.RefundExceptionBatchDetailService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 退款批量状态更新批次单条记录详情
 * @author PengJiangbo
 */
public class RefundExceptionBatchSingleDetailHandler implements EventHandler {

	private static final Log logger = LogFactory.getLog(RefundExceptionBatchSingleDetailHandler.class) ;
	//注入
	private RefundExceptionBatchDetailService refundExceptionBatchDetailService ;
	public void setRefundExceptionBatchDetailService(
			RefundExceptionBatchDetailService refundExceptionBatchDetailService) {
		this.refundExceptionBatchDetailService = refundExceptionBatchDetailService;
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
				List<RefundExceptionBatchDetailDTO> refundExceptionBatchDetails = this.refundExceptionBatchDetailService.queryRefundExceptionBatchDetail(paraMap) ;
				resultMap.put("result", refundExceptionBatchDetails) ;
			}else{
				Page page = MapUtil.map2Object(Page.class, pageMap) ;
				List<RefundExceptionBatchDetailDTO> refundExceptionBatchDetails = this.refundExceptionBatchDetailService.queryRefundExceptionBatchDetail(paraMap, page) ;
				resultMap.put("result", refundExceptionBatchDetails) ;
				resultMap.put("page", page) ;
			}
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode()) ;
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc()) ;
		} catch (Exception e) {
			logger.error("query single RefundExceptionBatch detail error：" + e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

}
