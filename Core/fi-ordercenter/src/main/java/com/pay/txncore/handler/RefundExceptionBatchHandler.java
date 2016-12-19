/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.RefundExceptionBatchDetailDTO;
import com.pay.txncore.service.RefundExceptionBatchService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

/**
 * 退款批量状态更新批次及批次详情保存服务
 * @author PengJiangbo
 */
public class RefundExceptionBatchHandler implements EventHandler {

	private static final Log logger = LogFactory.getLog(RefundExceptionBatchHandler.class) ;
	//注入
	private RefundExceptionBatchService refundExceptionBatchService ;
	public void setRefundExceptionBatchService(
			RefundExceptionBatchService refundExceptionBatchService) {
		this.refundExceptionBatchService = refundExceptionBatchService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map paraMap = null;
		Map resultMap = new HashMap();
		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass()) ;
			Timestamp timestamp = new Timestamp(System.currentTimeMillis()) ;
			paraMap.put("createTime", timestamp) ;
			paraMap.put("updateTime", timestamp) ;
			List<Map> lists = (List<Map>) paraMap.get("lists") ;
			List<RefundExceptionBatchDetailDTO> rs = new ArrayList<RefundExceptionBatchDetailDTO>() ;
			for(Map m : lists){
				RefundExceptionBatchDetailDTO r = MapUtil.map2Object(RefundExceptionBatchDetailDTO.class, m) ;
				r.setBatchNo((String)paraMap.get("batchNo"));
				r.setCreateTime(timestamp);
				r.setUpdateTime(timestamp);
				String refundResult = r.getRefundResult() ;
				if(StringUtils.isNotEmpty(refundResult)){
					if(refundResult.equals("成功"))
						r.setRefundResult("S");
					else if(refundResult.equals("失败"))
						r.setRefundResult("F");
				}else{
					r.setRefundResult("B"); //空或退款结果未按要求填写
				}
				rs.add(r) ;
			}
			List<RefundExceptionBatchDetailDTO> refundExceptionBatchDetailDTOs = this.refundExceptionBatchService.saveRefundExceptionBatchRnTx(paraMap, rs, null);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode()) ;
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc()) ;
			resultMap.put("result", refundExceptionBatchDetailDTOs) ;
		} catch (Exception e) {
			logger.error("调用创建退款批量状态更新接口服务失败：" + e);
		}
		return JSonUtil.toJSonString(resultMap);
	}

}
