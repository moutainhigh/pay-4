package com.pay.base.service.advertise.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.dao.advertise.AdvertiseDAO;
import com.pay.base.dto.AdvertisementDTO;
import com.pay.base.model.Advertisement;
import com.pay.base.service.advertise.AdvertiseService;
import com.pay.util.BeanConvertUtil;




/**
 * @Description 
 * @project 	ma-manager
 * @file 		AdvertiseServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 * Date				Author			Changes
 * 2010-12-31		geng			Create
 */
public class AdvertiseServiceImpl implements AdvertiseService {
	
	private static final Log logger = LogFactory.getLog(AdvertiseServiceImpl.class);
	
	private AdvertiseDAO advertiseDAO;

	
	public Integer getCountByLocation(Integer targets){
		int countAdv;
		countAdv = (Integer) advertiseDAO.findObjectByTemplate("getCountByLocation", targets);
		return countAdv;
	}
	
	
	public AdvertisementDTO queryAdvertiseById(Long advId){
		Advertisement advertisement = (Advertisement) advertiseDAO.findById(advId);
		if(advertisement != null){
			return BeanConvertUtil.convert(AdvertisementDTO.class, advertisement);
		}
		return null;
	}
	
	public long creatAdvertise(AdvertisementDTO advertiseDto){
		Advertisement advertise = null;
		if(advertiseDto != null){
			advertise = BeanConvertUtil.convert(Advertisement.class, advertiseDto);
		}
		return (Long) advertiseDAO.create(advertise);
	}
	
	public boolean updateAdvertise(AdvertisementDTO advertiseDto){
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
	
	public List<Advertisement> queryAdvertiseListByTargetsAvail(Integer targets,Integer avail){
		Map<String, Integer> paraMap = new HashMap<String, Integer>();
		paraMap.put("targets", targets);
		paraMap.put("available", avail);
		List<Advertisement> advertiseList = new ArrayList<Advertisement>();
		advertiseList = (List<Advertisement>) advertiseDAO.findByTemplate("queryAdvertiseListByTargetsAvail", paraMap);
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
	
	public boolean delAdvertisetById(Long id){
		return advertiseDAO.delete(id);
	}
	
	public boolean doUpdateSortRnTx(Integer location,Long id1,Integer sort1,Integer sort2){
		Advertisement advertisement = this.queryAdvertisetByLocSort(location,sort2);
		Long id2 = null;
		if(advertisement != null){
			id2 = advertisement.getId();
		}else{
			logger.error("data has question!");
			return false;
		}
		
		boolean sortb1 = this.updateSortById(id1, sort2);
		boolean sortb2 = this.updateSortById(id2, sort1);
		return sortb1 && sortb2;
	}
	
	public boolean doUpdateSortNextRnTx(Integer location,Integer sort,Integer count){
		for(int i=sort;i<=count;i++){
			Advertisement advertisement = this.queryAdvertisetByLocSort(location,i+1);
			if(advertisement != null){
				this.updateSortById(advertisement.getId(), advertisement.getSort()-1);
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