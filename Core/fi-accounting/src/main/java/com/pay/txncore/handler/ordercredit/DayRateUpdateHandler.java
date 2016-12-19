package com.pay.txncore.handler.ordercredit;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.DayRateDTO;
import com.pay.txncore.service.DayRateService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;


/**
 * 日利率修改
 * @author Jiangbo.Peng
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class DayRateUpdateHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(DayRateUpdateHandler.class);
	//注入
	private DayRateService dayRateService ;
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			Map dayRateMap = (Map) paraMap.get("dayRateDTO") ;
			DayRateDTO dayRateDTO = MapUtil.map2Object(DayRateDTO.class, dayRateMap) ;
			//此处调用的是单个的修改接口， 不能通boolean返回值判断是否成功，通过异常捕获
			this.dayRateService.update(dayRateDTO) ;
//			if(boo){
				resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
//			}else{
//				resultMap.put("responseCode", ResponseCodeEnum.FAIL.getCode()) ;
//				resultMap.put("responseDesc", ResponseCodeEnum.FAIL.getDesc()) ;
//			}
			
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
