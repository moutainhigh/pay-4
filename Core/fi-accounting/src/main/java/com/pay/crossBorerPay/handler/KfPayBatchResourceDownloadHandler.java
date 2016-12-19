package com.pay.crossBorerPay.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.crossBorerPay.service.KfPayResourceService;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.model.KfPayResource;
import com.pay.util.JSonUtil;

public class KfPayBatchResourceDownloadHandler implements EventHandler{

	private KfPayResourceService kfPayResourceServiceImpl;
	
	Log logger = LogFactory.getLog(KfPayBatchResourceDownloadHandler.class);

	@SuppressWarnings("unchecked")
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap<String, Object>().getClass());
			List<KfPayResource> KfPayResourceList = kfPayResourceServiceImpl.findDownloadUrl(paraMap);
			resultMap.put("result", KfPayResourceList);
		} catch (Exception e) {
			logger.error("跨境付款下载文件 error:", e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

	public void setKfPayResourceServiceImpl(KfPayResourceService kfPayResourceServiceImpl) {
		this.kfPayResourceServiceImpl = kfPayResourceServiceImpl;
	}
}
