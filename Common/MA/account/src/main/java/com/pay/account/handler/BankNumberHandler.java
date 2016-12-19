/**
 *  File: BankNumberHandler.java
 *  Description:
 *  Copyright 2014-2014 Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2014年11月19日   alex     Create
 *
 */
package com.pay.account.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.inf.service.BankService;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.lucene.dto.BankBrancheInfoDto;
import com.pay.lucene.service.BankBrancheInfoService;
import com.pay.util.JSonUtil;
import com.pay.util.StringUtil;

/**
 * 
 */
public class BankNumberHandler implements EventHandler {

	private BankService bankService;
	private ProvinceService provinceService;
	private CityService cityService;
	private BankBrancheInfoService bankBrancheInfoService;

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public void setBankBrancheInfoService(
			BankBrancheInfoService bankBrancheInfoService) {
		this.bankBrancheInfoService = bankBrancheInfoService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	@Override
	public String handle(String dataMsg) throws HessianInvokeException {

		@SuppressWarnings("unchecked")
		Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
				new HashMap<String, String>().getClass());

		String bankId = paraMap.get("bankId");
		String province = paraMap.get("province");
		String city = paraMap.get("city");
		String keyWord = paraMap.get("keyWord");

		String bankName = bankService.getBankById(bankId);
		ProvinceDTO provinceDTO = provinceService.findById(Integer
				.valueOf(province));

		CityDTO cityDTO = cityService.findByCityCode(Integer.valueOf(city));

		BankBrancheInfoDto brancheInfo = new BankBrancheInfoDto();
		brancheInfo.setBankName(bankName);
		if (null != cityDTO) {
			brancheInfo.setCity(cityDTO.getCityname());
		}

		if (null != provinceDTO) {
			brancheInfo.setProvince(provinceDTO.getProvincename());
		}

		if(!StringUtil.isEmpty(keyWord)){
			brancheInfo.setBankKaihu(keyWord);
		}
		

		List<BankBrancheInfoDto> bankBrancheInfos = bankBrancheInfoService
				.findByCondition(brancheInfo);

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
		result.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
		result.put("bankNumberList", bankBrancheInfos);
		return JSonUtil.toJSonString(result);

	}

}
