/**
 *  File: ProvinceCityService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-9-30      Jonathen Ni      Changes
 *  
 *
 */
package com.pay.poss.service.inf.input;

import java.util.List;

import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;

/**
 * <p>省份城市服务</p>
 * @author Jonathen Ni
 * @since 2010-9-30
 */
public interface ProvinceCityFacadeService {
	
	/**
	 * 获得城市对象
	 * @Auther Jonathen Ni
	 */
	public CityDTO getCity(Integer id);
	
	/**
	 * 获得城市对象
	 * @param cityName
	 * @return
	 */
	public CityDTO getCityByName(String cityName);
	
	/**
	 * 获得省份对象
	 * @Auther Jonathen Ni
	 */
	public ProvinceDTO getProvince(Integer id);
	/**
	 *  获得省份对象
	 * @param provinceName
	 * @return
	 */
	public ProvinceDTO getProvinceByName(String provinceName);
	
	/**
	 * 获得所有的省份
	 * @return
	 */
	public List<ProvinceDTO> getAllProvince();
	
	/**
	 * 获得省份的所有城市
	 * @param provinceId
	 * @return
	 */
	public List<CityDTO> getProvincesCity(Integer provinceId);
}
