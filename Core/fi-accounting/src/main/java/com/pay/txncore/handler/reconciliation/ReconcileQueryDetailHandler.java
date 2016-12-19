package com.pay.txncore.handler.reconciliation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.ReconcileImportRecordDetailDto;
import com.pay.txncore.service.ReconcileRecordService;
import com.pay.util.JSonUtil;

public class ReconcileQueryDetailHandler implements EventHandler{
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
			String batchNo = (String) paraMap.get("batchNo");
			String status = (String) paraMap.get("status");//add by davis.guo at 2016-08-11
			System.out.println("accounting============batchNo="+batchNo+",statusCode="+status);
			Map map=new HashMap();
			map.put("batchNo", batchNo);
			map.put("status", status);//add by davis.guo at 2016-08-11
			List<ReconcileImportRecordDetailDto> batchDetailDTOs=reconcileRecordService.queryReconcileDetailBatch(map);
			resultMap.put("batchDetailDTOs", batchDetailDTOs);
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
