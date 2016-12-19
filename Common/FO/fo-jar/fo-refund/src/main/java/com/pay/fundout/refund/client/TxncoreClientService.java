/**
 * 
 */
package com.pay.fundout.refund.client;

import java.util.HashMap;
import java.util.Map;

import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;

/**
 * 渠道服务接口
 * 
 * @author chaoyue
 *
 */
public class TxncoreClientService {

	private HessianInvokeService invokeService;

	public void setInvokeService(HessianInvokeService invokeService) {
		this.invokeService = invokeService;
	}

	/**
	 * 退款复核通知
	 * 
	 * @param refundOrderNo
	 * @param processFlg
	 *            :1-审核通过，0-审核不通过
	 * @return
	 */
	public String doRefund(String refundOrderNo, String processFlg) {

		Map paraMap = new HashMap();
		paraMap.put("refundOrderNo", refundOrderNo);
		paraMap.put("processFlg", processFlg);
		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_REFUND_CONFORM.getCode(), sysTraceNo,
				SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map<String, String> resultMap = JSonUtil.toObject(result,
				new HashMap<String, String>().getClass());

		String responseCode = resultMap.get("responseCode");
		return responseCode;
	}

}
