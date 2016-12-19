package com.pay.ce.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.ce.model.CurrencyExchangeOrder;

/*import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SerCode;
import com.pay.inf.enums.SystemCodeEnum;
import com.pay.inf.params.HessianInvokeParam;
import com.pay.inf.service.HessianInvokeService;
import com.pay.inf.service.SysTraceNoService;
import com.pay.inf.utils.HessianInvokeHelper;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;*/


public interface CurrencyExchangeService {
	
	void insertCurrencyExchange(CurrencyExchangeOrder currencyExchangeOrder);

	
	public List<CurrencyExchangeOrder> findByCriteria(CurrencyExchangeOrder currencyExchangeOrder);
	
	
	/*public Map currencyExchQuery(String partnerId,
			String memberCode,Page page) {

		Map paraMap = new HashMap();

		if (!StringUtil.isEmpty(partnerId)) {
			paraMap.put("partnerId", partnerId);
		}
		if (!StringUtil.isEmpty(memberCode)) {
			paraMap.put("memberCode", memberCode);
		}

		paraMap.put("page", page);

		String reqMsg = JSonUtil.toJSonString(paraMap);
		HessianInvokeParam param = HessianInvokeHelper.processRequest(reqMsg);
		String sysTraceNo = SysTraceNoService
				.generateSysTraceNo(SystemCodeEnum.POSS.getCode());
		String result = invokeService.invoke(
				SerCode.TXNCORE_CROSSPAY_MERCHANT_WEBSITE_QUERY.getCode(),
				sysTraceNo, SystemCodeEnum.POSS.getCode(),
				SystemCodeEnum.TXNCORE.getCode(),
				SystemCodeEnum.TXNCORE.getVersion(), param.getDataLength(),
				param.getMsgCompress(), param.getDataMsg());

		param.parse(result);
		HessianInvokeHelper.processResponse(param);
		result = param.getDataMsg();

		Map resultMap = JSonUtil.toObject(result, new HashMap().getClass());


		return resultMap;
	}*/
	
	
}
