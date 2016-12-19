/**
 *  File: ProvinceCityhunterServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-30      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.poss.service.inf.input.impl;

import java.util.List;

import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;
import com.pay.poss.service.inf.input.ProvinceCityFacadeService;

/**
 * @author jonathen Ni
 * @since 2010-9-30
 */
public class ProvinceCityFacadeServiceImpl implements ProvinceCityFacadeService {
	private ProvinceService provinceService;
	private CityService cityService;
	
	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}
	
	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

	@Override
	public CityDTO getCity(Integer id) {
		return cityService.findByCityCode(id);
	}

	@Override
	public ProvinceDTO getProvince(Integer id) {
		return provinceService.findById(id);
	}

	/**
	 * sean_yi
	 * 获得所有的省份
	 * @return
	 */
	@Override
	public List<ProvinceDTO> getAllProvince() {
		return provinceService.findAll();
	}

	/**
	 * sean_yi
	 * 获得省份的所有城市
	 * @param provinceId
	 * @return
	 */
	@Override
	public List<CityDTO> getProvincesCity(Integer provinceId) {
		return cityService.findByProvinceId(provinceId);
		
	}

	@Override
	public CityDTO getCityByName(String cityName) {
		return cityService.findByName(cityName);
	}

	@Override
	public ProvinceDTO getProvinceByName(String provinceName) {
		return provinceService.findByName(provinceName);
	}
	
	
	
	
}
