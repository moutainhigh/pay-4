package com.pay.poss.client;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.poss.dto.ReconcileBatchDTO;
import com.pay.util.JSonUtil;

//清结算系统
public class ReconcileService {
		
		private HessianInvokeService invokeService;
	
		public void setInvokeService(HessianInvokeService invokeService) {
			this.invokeService = invokeService;
		}

		public Map queryReconcile(ReconcileBatchDTO batchDTO,Page page) {
			Map paraMap = new HashMap();
			paraMap.put("reconcileBatchDTO", batchDTO);
			paraMap.put("page", page);
			String reqMsg = JSonUtil.toJSonString(paraMap);
			HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
			String sysTraceNo = SysTraceNoService
					.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
			String result = invokeService.invoke(
					SerCode.TXNCORE_RECONCILE_QUERY.getCode(), sysTraceNo,
					SystemCodeEnum.POSS.getCode(),
					SystemCodeEnum.TXNCORE.getCode(),
					SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());

			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();

			Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
			return resultMap;
		}

		public Map queryReconcileDetail(String batchNo, String status) {
			Map paraMap = new HashMap();
			paraMap.put("batchNo", batchNo); 
			paraMap.put("status", status); //添加状态查询条件 add by davis.guo at 2016-08-11
			String reqMsg = JSonUtil.toJSonString(paraMap);
			HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
			String sysTraceNo = SysTraceNoService
					.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
			String result = invokeService.invoke(
					SerCode.TXNCORE_RECONCILE_DETAIL_QUERY.getCode(), sysTraceNo,
					SystemCodeEnum.POSS.getCode(),
					SystemCodeEnum.TXNCORE.getCode(),
					SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
					param.getMsgCompress(), param.getDataMsg());

			param.parse(result);
			HessianInvokeHelper.processResponse(param);
			result = param.getDataMsg();

			Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());
			
			return resultMap;
		}
}
