
/**
 * 退款扩充订单的查询
 * 
 * @author sch
 */

package com.pay.txncore.handler.orderquery;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.refund.RefundOrderExtendDTO;
import com.pay.txncore.service.refund.RefundOrderExtendService;
import com.pay.util.JSonUtil;


public class RefundOrderExtendQueryHandler  implements EventHandler{


public final Log logger = LogFactory.getLog(RefundOrderQueryHandler.class);

private RefundOrderExtendService refundOrderExtendService;		

@Override
public String handle(String dataMsg) throws HessianInvokeException {

	Map paraMap = null;
	Map resultMap = new HashMap();

	try {		
		paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
					
		String refundOrderNo = (String) paraMap.get("refundOrderNo");		
		
		logger.info("refundOrderNo" + refundOrderNo);  //退款订单号 
		
		Long lRefundOrderNo = Long.valueOf(refundOrderNo);
		
		RefundOrderExtendDTO roeDTO = refundOrderExtendService.findByRefundOrderNo(lRefundOrderNo);
		
		List<RefundOrderExtendDTO>  roeDTOs = new ArrayList<RefundOrderExtendDTO>();
		
		if(roeDTO != null){
			roeDTOs.add(roeDTO);
		}
		else{
			logger.info("找不到对应的退款订单号扩充信息 ");
		}
			
		resultMap.put("result", roeDTOs);	
		
	} catch (Exception e) {
		logger.error("query error:", e);
	}

	return JSonUtil.toJSonString(resultMap);
}


public void setRefundOrderExtendService(RefundOrderExtendService refundOrderExtendService) {
	this.refundOrderExtendService = refundOrderExtendService;
}
}