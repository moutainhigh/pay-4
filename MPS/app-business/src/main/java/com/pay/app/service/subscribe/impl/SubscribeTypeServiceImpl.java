package com.pay.app.service.subscribe.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.util.BeanConvertUtil;
import com.pay.app.dao.subscribe.SubscribeTypeDAO;
import com.pay.app.dto.SubscribeTypeDto;
import com.pay.app.model.SubscribeNotify;
import com.pay.app.model.SubscribeType;
import com.pay.app.service.subscribe.SubscribeNotifyService;
import com.pay.app.service.subscribe.SubscribeTypeService;

public class SubscribeTypeServiceImpl implements SubscribeTypeService {

	private SubscribeTypeDAO subscribeTypeDao;
	private SubscribeNotifyService subscribeNotifyService;

	public void setSubscribeNotifyService(SubscribeNotifyService subscribeNotifyService) {
    	this.subscribeNotifyService = subscribeNotifyService;
    }

	public void setSubscribeTypeDao(SubscribeTypeDAO subscribeTypeDao) {
		this.subscribeTypeDao = subscribeTypeDao;
	}

	@Override
    public int countSebscribeType(SubscribeTypeDto dto) {
		return this.subscribeTypeDao.countSubscribeType(dto);	   
    }
	
	
	@Override
	public List<SubscribeTypeDto> querySubscribeTypeDtoList(SubscribeTypeDto dto,Long memberCode) {
		List<SubscribeType> subscribeTypeList = this.subscribeTypeDao.querySubscribeTypeList(dto);
		List<SubscribeTypeDto> subscribeTypeDtoList = new ArrayList<SubscribeTypeDto>();
		Map<String,Object> paramMap=new HashMap<String,Object>();
		paramMap.put("memberCode", memberCode);
		if (null != subscribeTypeList && subscribeTypeList.size() > 0) {
			for (SubscribeType subcribeType : subscribeTypeList) {
				paramMap.put("type", subcribeType.getNotifyId());
				SubscribeNotify tmp=subscribeNotifyService.querySubscribeNotify(paramMap);
				SubscribeTypeDto tmpDto = BeanConvertUtil.convert(SubscribeTypeDto.class, subcribeType);
				if(null!=tmp){
					tmpDto.setNoticeMode(tmp.getNoticeMode());
				}else{
					tmpDto.setNoticeMode(0L);
				}
				subscribeTypeDtoList.add(tmpDto);
			}
		}
		return subscribeTypeDtoList;
	}



}
