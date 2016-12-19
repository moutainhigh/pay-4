package com.pay.txncore.handler.ordercredit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
 * 日利率删除
 * @author Jiangbo.Peng
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class DayRateDeleteHandler implements EventHandler{
	
	private static Logger logger = LoggerFactory.getLogger(DayRateDeleteHandler.class);
	//注入
	private DayRateService dayRateService ;
	
	@Override
	public String handle(String dataMsg) throws HessianInvokeException {
		Map<String, Object> paraMap = null;
		Map resultMap = new HashMap();

		try {
			paraMap = JSonUtil.toObject(dataMsg, new HashMap().getClass());
			//String isBatch = (String) paraMap.get("isBatch") ;
			Map dayRateMap = (Map) paraMap.get("dayRate") ;
			List<Object> listMap = (List<Object>) paraMap.get("list") ;
			DayRateDTO dayRateDTO = MapUtil.map2Object(DayRateDTO.class, dayRateMap) ;
			//List<Object> list = MapUtil.map2List(Object.class, listMap) ;
			boolean boo = false ;
			if(CollectionUtils.isEmpty(listMap)){
				boo = this.dayRateService.delete(dayRateDTO) ;
			}else{
				Integer r = this.dayRateService.deleteBatch("dayRate_deleteBatch", listMap) ;
				if(null != r ){
					boo = true ;
				}
			}
			if(boo){
				resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
				resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
			}else{
				resultMap.put("responseCode", ResponseCodeEnum.FAIL.getCode()) ;
				resultMap.put("responseDesc", ResponseCodeEnum.FAIL.getDesc()) ;
			}
			
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
