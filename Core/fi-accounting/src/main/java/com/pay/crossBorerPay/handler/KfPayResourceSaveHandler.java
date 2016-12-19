package com.pay.crossBorerPay.handler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.crossBorerPay.service.KfPayResourceService;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.KfPayResource;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class KfPayResourceSaveHandler implements EventHandler {
	
	private KfPayResourceService kfPayResourceServiceImpl;
	Log logger = LogFactory.getLog(KfPayResourceSaveHandler.class);
	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("repCode", "0");
		try {
			Map<String, Object> paraMap =JSonUtil.toObject(dataMsg, Map.class);
			KfPayResource kfPayResource=MapUtil.map2Object(KfPayResource.class, (Map<String, Object>)paraMap.get("entity"));
			kfPayResource.setCreateData(new Date());
			kfPayResourceServiceImpl.save(kfPayResource);
			resultMap.put("repCode",0);
		} catch (Exception e) {
			logger.error("保存跨境付款资源文件报错",e);
			resultMap.put("repCode","1");
		}
		
		return JSonUtil.toJSonString(resultMap);
	}
	public KfPayResourceService getKfPayResourceServiceImpl() {
		return kfPayResourceServiceImpl;
	}
	public void setKfPayResourceServiceImpl(KfPayResourceService kfPayResourceServiceImpl) {
		this.kfPayResourceServiceImpl = kfPayResourceServiceImpl;
	}
	
	

}
