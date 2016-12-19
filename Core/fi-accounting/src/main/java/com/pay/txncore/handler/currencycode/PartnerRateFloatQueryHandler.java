/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pay.txncore.handler.currencycode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.crosspay.service.PartnerRateFloatService;
import com.pay.txncore.model.PartnerRateFloat;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * 商户汇率浮动查询
 * @author peiyu.yang
 */
public class PartnerRateFloatQueryHandler implements EventHandler {

	public final static String DEFAULT_DATE_FROMAT = "yyyy-MM-dd HH:mm:ss";
	public final Log logger = LogFactory
			.getLog(PartnerRateFloatQueryHandler.class);
	private PartnerRateFloatService partnerRateFloatService;

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String,Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());

			String partnerId = (String)paraMap.get("partnerId");
			
			Map pageMap = (Map) paraMap.get("page");
			Page page = MapUtil.map2Object(Page.class, pageMap);

			PartnerRateFloat rateFloat = new PartnerRateFloat();

			if (!StringUtil.isEmpty(partnerId)) {
				rateFloat.setPartnerId(partnerId);
			}

			List<PartnerRateFloat> list = null;
			
			if(page!=null){
				 list = partnerRateFloatService.findByCriteria(rateFloat);
			}else{
				list = partnerRateFloatService.findByCriteria(rateFloat, page);
			}
			
			resultMap.put("list", list);
			resultMap.put("page", page);
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			resultMap.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	public void setPartnerRateFloatService(
			PartnerRateFloatService partnerRateFloatService) {
		this.partnerRateFloatService = partnerRateFloatService;
	}
}
