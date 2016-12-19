package com.pay.crossBorerPay.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.crossBorerPay.service.KfCrossBorerPayService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.service.RemitFailTypingService;
import com.pay.util.JSonUtil;
/**
 * 出款失败录入 审核
 * @Date 2016年8月26日20:28:10
 * @author delin
 *
 */
public class RemitFailTypingReviewedHandler implements EventHandler {
	private static Logger logger = LoggerFactory.getLogger(RemitFailTypingReviewedHandler.class);

	private RemitFailTypingService remitFailTypingService;
	
	private KfCrossBorerPayService kfCrossBorerPayService;
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			String status=String.valueOf(paraMap.get("status"));
			String detailNos=String.valueOf(paraMap.get("detailNos"));
			
			StringBuffer deatilNo=new StringBuffer();
		    String[] detailNoArr = detailNos.split(",");
		    for (String item : detailNoArr) {
		    	deatilNo.append("'"+item+"'").append(",");
			}
		    //记账
		    kfCrossBorerPayService.RemitFailTypingReviewed(status,deatilNo.toString().substring(0,deatilNo.length()-1));
		    //批量更新
			remitFailTypingService.batchReviewed(paraMap);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			resultMap.put("responseCode", ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

	public void setRemitFailTypingService(
			RemitFailTypingService remitFailTypingService) {
		this.remitFailTypingService = remitFailTypingService;
	}

	public void setKfCrossBorerPayService(KfCrossBorerPayService kfCrossBorerPayService) {
		this.kfCrossBorerPayService = kfCrossBorerPayService;
	}

}
