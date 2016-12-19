/**
 * 
 */
package com.pay.txncore.handler;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.service.RefundExceptionBatchDetailService;
import com.pay.util.JSonUtil;

/**
 * 退款批量状态更新批次详情状态更新服务
 * @author PengJiangbo
 *
 */
public class RefundExceptionBatchDetailStatusHandler implements EventHandler {

	private static final Log logger = LogFactory.getLog(RefundExceptionBatchDetailStatusHandler.class) ;
	
	//注入
	private RefundExceptionBatchDetailService refundExceptionBatchDetailService ;
	public void setRefundExceptionBatchDetailService(
			RefundExceptionBatchDetailService refundExceptionBatchDetailService) {
		this.refundExceptionBatchDetailService = refundExceptionBatchDetailService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null ;
		Map resultMap = new HashMap() ;
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass()) ;
			Timestamp timestamp = new Timestamp(System.currentTimeMillis()) ;
			paraMap.put("updateTime", timestamp) ;
			boolean result = this.refundExceptionBatchDetailService.updateRefundExceptionBatchDetailStatus(paraMap) ;
			if(result){
				resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode()) ;
				resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc()) ;
			}else {
				resultMap.put("responseCode", ResponseCodeEnum.FAIL.getCode()) ;
				resultMap.put("responseDesc", ResponseCodeEnum.FAIL.getDesc()) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("更新表[refund_exception_batch_detail]失败：" + e);
			resultMap.put("responseCode", ResponseCodeEnum.UNDEFINED_ERROR.getCode()) ;
			resultMap.put("responseDesc", ResponseCodeEnum.UNDEFINED_ERROR.getDesc()) ;
		}
		return JSonUtil.toJSonString(resultMap);
	}

}
