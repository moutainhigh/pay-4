package com.pay.crossBorerPay.handler;

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

public class KfPayResourceDownloadHandler implements EventHandler {

	private KfPayResourceService kfPayResourceServiceImpl;
	
	Log logger = LogFactory.getLog(KfPayResourceDownloadHandler.class);

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap<String, Object>().getClass());
			KfPayResource KfPayResource = MapUtil.map2Object(KfPayResource.class, paraMap);
			KfPayResource KfPayResourceList = kfPayResourceServiceImpl.findDownloadUrl(KfPayResource);
			resultMap.put("result", KfPayResourceList);
		} catch (Exception e) {
			logger.error("跨境付款下载文件 error:", e);
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
