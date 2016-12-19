package com.pay.txncore.handler.reconciliation;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.ReconcileImportRecordBatchDto;
import com.pay.txncore.service.ReconcileRecordService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

public class ReconcileQueryHandler implements EventHandler {
	
	public final Log logger = LogFactory.getLog(getClass());
	
	private ReconcileRecordService reconcileRecordService;
	public void setReconcileRecordService(
			ReconcileRecordService reconcileRecordService) {
		this.reconcileRecordService = reconcileRecordService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map resultMap = new HashMap();
		try{
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map listMap = (Map) paraMap.get("reconcileBatchDTO");
			Long endTime=(Long)listMap.get("endTime");
			Long startTime=(Long)listMap.get("startTime");
			Map pageMap = (Map) paraMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);
			if(startTime!=null){
				listMap.put("startTime", new Date(startTime));
			}
			if(endTime!=null){
				listMap.put("endTime", new Date(endTime));
			}
			List<ReconcileImportRecordBatchDto> batchDTOs=reconcileRecordService.queryReconcileBatch(listMap,page);
			resultMap.put("batchDTOs", batchDTOs);
			resultMap.put("page", page);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			return JSonUtil.toJSonString(resultMap);
		}catch(Exception e){
			logger.error("查询银行对账 出错！", e);
			e.printStackTrace();
		}
		return null;
	}

	
	
	
	
}
