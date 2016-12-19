package com.pay.poss.featuremenu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.featuremenu.dao.AdvertiseDAO;
import com.pay.poss.featuremenu.dto.AdvertisementDto;
import com.pay.poss.featuremenu.model.Advertisement;
import com.pay.poss.featuremenu.service.AdvertiseService;
import com.pay.util.BeanConvertUtil;




/**
 * @Description 
 * @project 	ma-manager
 * @file 		AdvertiseServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-31		geng			Create
 */
public class AdvertiseServiceImpl implements AdvertiseService {
	
	private static final Log logger = LogFactory.getLog(AdvertiseServiceImpl.class);
	
	private AdvertiseDAO advertiseDAO;

	
	public Integer getCountByLocation(Integer targets){
		int countAdv;
		countAdv = (Integer) advertiseDAO.countByCriteria("getCountByLocation", targets);
		return countAdv;
	}
	
	
	public AdvertisementDto queryAdvertiseById(Long advId){
		Advertisement advertisement = advertiseDAO.findById(advId);
		if(advertisement != null){
			return BeanConvertUtil.convert(AdvertisementDto.class, advertisement);
		}
		return null;
	}
	
	public long creatAdvertise(AdvertisementDto advertiseDto){
		Advertisement advertise = null;
		if(advertiseDto != null){
			advertise = BeanConvertUtil.convert(Advertisement.class, advertiseDto);
		}
		return (Long) advertiseDAO.create(advertise);
	}
	
	public boolean updateAdvertise(AdvertisementDto advertiseDto){
		Advertisement advertise = null;
		if(advertiseDto != null){
			advertise = BeanConvertUtil.convert(Advertisement.class, advertiseDto);
		}
		return advertiseDAO.update(advertise);
	}
	
	
	public List<Advertisement> queryAdvertiseListByTargets(Integer targets){
		List<Advertisement> advertiseList = new ArrayList<Advertisement>();
		advertiseList = (List<Advertisement>) advertiseDAO.findByTemplate("queryAdvertiseListByTargets", targets);
		return advertiseList;
	}
	
	
	public boolean updateSortById(Long id,Integer sort){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("sort", sort);
		return advertiseDAO.updateSortById(paramMap);
	}
	
	public Advertisement queryAdvertisetByLocSort(Integer location,Integer sort){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("location", location);
		paramMap.put("sort", sort);
		return (Advertisement) advertiseDAO.findObjectByTemplate("queryAdvertisetByLocSort", paramMap);
	}
	
	public Advertisement queryAdvertisetNextByLocSort(Integer location,Integer sort){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("location", location);
		paramMap.put("sort", sort);
		return (Advertisement) advertiseDAO.findObjectByTemplate("queryAdvertisetNextByLocSort", paramMap);
	}
	
	public Advertisement queryAdvertisetUpByLocSort(Integer location,Integer sort){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("location", location);
		paramMap.put("sort", sort);
		return (Advertisement) advertiseDAO.findObjectByTemplate("queryAdvertisetUpByLocSort", paramMap);
	}
	
	public boolean delAdvertisetById(Long id){
		return advertiseDAO.delete(id);
	}
	
	public boolean doUpdateSortRnTx(Integer location,Long id1,String upOrDown){
		Integer sort1;
		Integer sort2;
		AdvertisementDto advert1 = this.queryAdvertiseById(id1);
		sort1 = advert1.getSort();
		Advertisement  advert2 = null;
		if(upOrDown.equals("NEXT")){
			advert2 = this.queryAdvertisetNextByLocSort(location,sort1);
		}else if(upOrDown.equals("UP")){
			advert2 = this.queryAdvertisetUpByLocSort(location,sort1);
		}
		
		Long id2 = null;
		if(advert2 != null){
			sort2 = advert2.getSort();
			id2 = advert2.getId();
		}else{
			logger.error("data has question!");
			return false;
		}
		boolean sortb1 = this.updateSortById(id1, sort2);
		boolean sortb2 = this.updateSortById(id2, sort1);
		return sortb1 && sortb2;
	}
	
	public boolean doUpdateSortNextRnTx(Integer location,Integer count){
		List<Advertisement> advList = new ArrayList<Advertisement>();
		advList = this.queryAdvertiseListByTargets(location);
		for(int i=0;i<count;i++){
			Advertisement adv = advList.get(i);
			if(adv != null){
				this.updateSortById(adv.getId(), i+1);
			}
		}
		return true;
	}
	
	
	public AdvertiseDAO getAdvertiseDAO() {
		return advertiseDAO;
	}

	public void setAdvertiseDAO(AdvertiseDAO advertiseDAO) {
		this.advertiseDAO = advertiseDAO;
	}

}
