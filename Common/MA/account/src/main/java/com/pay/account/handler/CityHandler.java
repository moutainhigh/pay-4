/**
 * 
 */
package com.pay.account.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dto.CityDTO;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.inf.service.CityService;
import com.pay.util.JSonUtil;

/**
 * @author alex
 *
 */
public class CityHandler implements EventHandler {

	private CityService cityService;
	private final Log logger = LogFactory.getLog(getClass());

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		Map<String, Object> result = new HashMap<String, Object>();

		String province = paraMap.get("province");

		try {
			List<CityDTO> cityList = cityService.findByProvinceId(Integer
					.valueOf(province));

			result.put("cityList", cityList);
			result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
			result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		} catch (Exception e) {
			logger.error("error:", e);
			result.put("responseCode",
					ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			result.put("responseDesc",
					ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
		}

		return JSonUtil.toJSonString(result);
	}

}
