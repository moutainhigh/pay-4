package com.pay.app.service.subscribe.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.app.dao.subscribe.SubscribeNotifyDAO;
import com.pay.app.dto.SubscribeNotifyDto;
import com.pay.app.model.SubscribeNotify;
import com.pay.app.service.subscribe.SubscribeNotifyService;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.util.BeanConvertUtil;

public class SubscribeNotifyServiceImpl implements SubscribeNotifyService {

	private SubscribeNotifyDAO subscribeNotifyDao;

	public void setSubscribeNotifyDao(SubscribeNotifyDAO subscribeNotifyDao) {
		this.subscribeNotifyDao = subscribeNotifyDao;
	}

	@Override
	public SubscribeNotifyDto saveSubscribeNotify(SubscribeNotifyDto dto) {
		SubscribeNotify subscribeNotify = BeanConvertUtil.convert(SubscribeNotify.class, dto);
		subscribeNotify = (SubscribeNotify) subscribeNotifyDao.create(subscribeNotify);
		return BeanConvertUtil.convert(SubscribeNotifyDto.class, subscribeNotify);
	}

	@Override
	public boolean saveSubscribeNotifyRdTx(List<SubscribeNotifyDto> dtoList) throws MatrixCardException {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<SubscribeNotify> insertList = new ArrayList<SubscribeNotify>();
		List<SubscribeNotify> updateList = new ArrayList<SubscribeNotify>();
		for (SubscribeNotifyDto dto : dtoList) {
			paramMap.put("memberCode", dto.getMemberCode());
			paramMap.put("type", dto.getType());
			SubscribeNotify tmp = this.querySubscribeNotify(paramMap);
			// int count = this.subscribeNotifyDao.countSubscribeNotifyByMap(paramMap);
			// 如果存在会员号+订阅类型的记录进行更新
			if (null == tmp) {
				if(dto.getNoticeMode()!=0){					
					insertList.add(BeanConvertUtil.convert(SubscribeNotify.class, dto));
				}
			}
			else {// 新增
				dto.setId(tmp.getId());
				if(dto.getNoticeMode()==0){
					dto.setStatus(0L);
				}
				updateList.add(BeanConvertUtil.convert(SubscribeNotify.class, dto));
			}
		}
		try {
			if(insertList.size()>0){
				this.batchInsertRdTx(insertList);
			}
			if(updateList.size()>0){
				this.batchUpdateRdTx(updateList);
			}
		}
		catch (MatrixCardException e) {
			throw e;
		}
		return true;
	}
	
	@Override
	public SubscribeNotify querySubscribeNotify(Map<String, Object> paramMap) {
		List<SubscribeNotify> list = this.subscribeNotifyDao.querySubscribeNotifyList(paramMap);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public SubscribeNotify querySubscribeNotifyByType(Long memberCode, Long type) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberCode", memberCode);
		paramMap.put("type", type);
		List<SubscribeNotify> list = this.subscribeNotifyDao.querySubscribeNotifyList(paramMap);
		if (null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public boolean batchInsertRdTx(List<SubscribeNotify> subscribeNotifyList) throws MatrixCardException {
		boolean insertFlag = this.subscribeNotifyDao.batchInsert(subscribeNotifyList);
		if (!insertFlag) {
			throw new MatrixCardException();
		}
		return true;
	}

	@Override
	public boolean batchUpdateRdTx(List<SubscribeNotify> subscribeNotifyList) throws MatrixCardException {
		boolean insertFlag = this.subscribeNotifyDao.batchUpdateRdTx(subscribeNotifyList);
		if (!insertFlag) {
			throw new MatrixCardException();
		}
		return true;
	}
}
