package com.pay.txncore.handler.ordercredit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.DayRateDTO;
import com.pay.txncore.service.DayRateService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;


/**
 * 日利率查询
 * @author Jiangbo.Peng
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class DayRateQueryHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(DayRateQueryHandler.class);
	//注入
	private DayRateService dayRateService ;
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map pageMap = (Map) paraMap.get("page") ;
			Page page = MapUtil.map2Object(Page.class, pageMap) ;
			if(null != page){
				List<DayRateDTO> list = this.dayRateService.queryDayRateList(paraMap) ;
				resultMap.put("result", list) ;
			}else{
				List<DayRateDTO> list = this.dayRateService.queryDayRateList(paraMap, page) ;
				resultMap.put("result", list) ;
				resultMap.put("page", page) ;
			}
			resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			
		} catch (Exception e) {
			resultMap.put("responseCode", ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			resultMap.put("responseDesc", ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			logger.error("query partner error:", e);
		}

		return JSonUtil.toJSonString(resultMap);
	}

	/**
	 * @param dayRateService the dayRateService to set
	 */
	public void setDayRateService(DayRateService dayRateService) {
		this.dayRateService = dayRateService;
	}

}
