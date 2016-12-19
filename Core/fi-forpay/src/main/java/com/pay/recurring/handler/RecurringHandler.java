package com.pay.recurring.handler;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.recurring.dao.RecurringDAO;
import com.pay.recurring.service.RecurringService;
import com.pay.util.JSonUtil;

/**
 * 循环扣款取消
 * 2016年4月22日19:27:48
 * delin.dong
 */
public class RecurringHandler  implements EventHandler {
	
	private RecurringDAO recurringDao;
	
	public final Log logger = LogFactory
			.getLog(RecurringHandler.class);
	Map<String,String> paraMap = new HashMap<String,String>();
	Map resultMap = new HashMap();
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());	
			this.recurringDao.createRecurringCancel(paraMap);
			String cancelType=paraMap.get("cancelType");
			if(cancelType.equals("0")){ //即时取消
				this.recurringDao.cancelRecurring(paraMap);
			}else{	//延期取消
				this.recurringDao.cancelPostponeRecurring(paraMap);
			}
			resultMap.putAll(paraMap);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		}catch(Exception e){
			e.printStackTrace();
			resultMap.put("responseCode", ResponseCodeEnum.FAIL.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.FAIL.getDesc());
		}
		return JSonUtil.toJSonString(resultMap);
	}

	
	
	public void setRecurringDao(RecurringDAO recurringDao) {
		this.recurringDao = recurringDao;
	}
}
