package com.pay.poss.merchantmanager.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.poss.merchantmanager.dao.ISignMessageDao;
import com.pay.poss.merchantmanager.dto.SignMessageDto;
import com.pay.poss.merchantmanager.service.ISignMessageService;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		SignMessageServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-29		tianqing_wang			Create
 */
public class SignMessageServiceImpl implements ISignMessageService{
	private Log log = LogFactory.getLog(SignMessageServiceImpl.class);
	private ISignMessageDao iSignMessageDao;
	
	public Long createSignMessage(SignMessageDto dto){
		return iSignMessageDao.createSignMessage(dto);
	}
	
	@SuppressWarnings("unchecked")
	public List<SignMessageDto> querySignMessageList(SignMessageDto paramDto,Page  page){
		Integer totalCount = iSignMessageDao.countSignMessage(paramDto);
		SignMessageDto dto = setPage(paramDto,page,totalCount);
		if(null == dto) return null;
		return iSignMessageDao.querySignMessageList(dto);	
	}
	
	public Integer updateSignMessage(SignMessageDto dto){
		return iSignMessageDao.updateSignMessage(dto);
	}
	public Integer deleteSignMessage(SignMessageDto dto){
		return iSignMessageDao.deleteSignMessage(dto);
	}
	public SignMessageDto querySignMessageById(SignMessageDto dto){
		return iSignMessageDao.querySignMessageById(dto);
	}
	
	public List<SignMessageDto> querySignMessageByCondition(SignMessageDto dto){
		return iSignMessageDao.querySignMessageByCondition(dto);
	}
	public List<SignMessageDto> validateDepName(SignMessageDto dto){
		return iSignMessageDao.validateDepName(dto);
	}
	
	public  String querySignMessageByConditionToStr(SignMessageDto dto){
		List<SignMessageDto>  listDto =  iSignMessageDao.querySignMessageByCondition(dto);
		String str = this.getString(listDto);
		return str;
	}

	public Integer countSignMessageByCondition(SignMessageDto dto){
		return iSignMessageDao.countSignMessageByCondition(dto);
	}
	private String getString(List<SignMessageDto>  list){
		String str ="";
		if(list!=null && list.size()>0){
			for(SignMessageDto dto : list){
				str += "|"+dto.getDepartmentName();
			}
		}
		return str;
	}
	@SuppressWarnings("unchecked")
	private SignMessageDto  setPage(SignMessageDto dto,Page  page,Integer totalCount){
		Integer pageStartRow;// 起始行
		Integer pageEndRow;// 结束行
        if (null == page || totalCount == 0) {
			  return null;
        }
        page.setTotalCount(totalCount);
        pageEndRow = page.getPageNo() * page.getPageSize();
        if ((page.getPageNo() - 1) == 0) {
        	pageStartRow = 0;
        }else {
        	pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
        }
        dto.setPageStartRow(pageStartRow);
        dto.setPageEndRow(pageEndRow);
		return dto;
	}
	
	public ISignMessageDao getiSignMessageDao() {
		return iSignMessageDao;
	}
	public void setiSignMessageDao(ISignMessageDao iSignMessageDao) {
		this.iSignMessageDao = iSignMessageDao;
	}
}
