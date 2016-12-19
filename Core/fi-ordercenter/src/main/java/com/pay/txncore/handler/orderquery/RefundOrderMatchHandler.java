package com.pay.txncore.handler.orderquery;

/*
 * author: sch
 * create date: 2016-05-26
 * 根据 条件 找到 匹配的退款 订单   
 * migs: 根据 返回码,授权码 ==>渠道订单,  再根据 金额\日期 找到 退款订单 
 * ctv : 根据渠道订单   金额 找到退款订单. 将来可能会根据 渠道订单和requestId 直接找到 退款订单  
*/

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.commons.RefundStatusEnum;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.refund.RefundOrderDTO;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.txncore.service.refund.RefundOrderService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.NumberUtil;
import com.pay.util.StringUtil;
import com.pay.txncore.model.MIGSRefundOrderQueryModel;
import com.pay.txncore.model.CTVRefundOrderQueryModel;
import com.pay.txncore.service.RefundOrderMatchService;

public class RefundOrderMatchHandler implements EventHandler {

	public final Log logger = LogFactory.getLog(RefundOrderMatchHandler.class);

	private RefundOrderMatchService refundOrderMatchService;
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
						
			logger.info("paraMap " + paraMap);
						
			//获得参数对列			
			String source = (String) paraMap.get("source");
		
			if(source !=null){
				source = source.trim();
			}
			
			if(source.equals("ctv")){
				List<Map> listMap = (List<Map>) paraMap.get("ctvrefundorderlist");
				List<CTVRefundOrderQueryModel> ctvROQMList = MapUtil.map2List(CTVRefundOrderQueryModel.class, listMap);
				
				String method = (String) paraMap.get("method");
				
				// 方法 1
				if (method.trim().equals("1")) {
					refundOrderMatchService.call_CTV_Method_1(ctvROQMList);
				} else if (method.trim().equals("2")) {
				// 使用 requestId 和 CTV 
					refundOrderMatchService.call_CTV_Method_2(ctvROQMList);
				}

				resultMap.put("result", ctvROQMList);		
				resultMap.put("method", method);
			}		
			else if(source.equals("migs")){
				
				String method = (String) paraMap.get("method");

				List<Map> listMap = (List<Map>) paraMap
						.get("migsrefundorderlist");
				List<MIGSRefundOrderQueryModel> migsROQMList = MapUtil
						.map2List(MIGSRefundOrderQueryModel.class, listMap);

				// 方法1
				if (method.trim().equals("1")) {
					refundOrderMatchService.call_MIGS_Method_1(migsROQMList);
				} else if (method.trim().equals("2")) {
					// 参考号 + 金额 ：作为 Key ，看一下map 表里有没有重复的 ,如果有的话，就一起做掉
					refundOrderMatchService.call_MIGS_Method_2(migsROQMList);
				}else if(method.trim().equals("3")){
					// 参考号 + 金额 ：作为 Key ，看一下map 表里有没有重复的 ,如果有的话，就一起做掉
					refundOrderMatchService.call_MIGS_Method_3(migsROQMList);
				}

				resultMap.put("result", migsROQMList);
				resultMap.put("method", method);
			}
			
		} catch (Exception e) {
			logger.error("query error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}
	
	public void setRefundOrderMatchService(RefundOrderMatchService refundOrderMatchService){
		this.refundOrderMatchService = refundOrderMatchService;
	}
		
}

