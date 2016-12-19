package com.pay.poss.merchantmanager.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.poss.merchantmanager.dto.SignMessageDto;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		ISignMessageService.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-29		tianqing_wang			Create
 */
public interface ISignMessageService {
	
	/**
     * 频道新增
     * @param SignMessageDto
     * @return Long
     */
	public Long createSignMessage(SignMessageDto dto);
	
	/**
     * 频道列表查询list
     * @param SignMessageDto
     * @param Page
     * @return List<SignMessageDto>
     */
	@SuppressWarnings("unchecked")
	public List<SignMessageDto> querySignMessageList(SignMessageDto paramDto,Page  page);
	
	
	/**
     * 修改频道信息
     * @param SignMessageDto
     * @return Integer
     */
	public Integer updateSignMessage(SignMessageDto dto);
	
	/**
     * 删除频道信息
     * @param SignMessageDto
     * @return Integer
     */
	public Integer deleteSignMessage(SignMessageDto dto);
	
	/**
     * 由id查询频道信息
     * @param SignMessageDto
     * @return SignMessageDto
     */
	public SignMessageDto querySignMessageById(SignMessageDto dto);
	
	/**
     * 由部门编号或searchKey查询部门(频道)信息
     * @param SignMessageDto
     * @return List<SignMessageDto>
     */
	public List<SignMessageDto> querySignMessageByCondition(SignMessageDto dto);
	
	/**
     * 由部门编号或searchKey查询部门(频道)信息 转化为String
     * @param SignMessageDto
     * @return String
     */
	public String querySignMessageByConditionToStr(SignMessageDto dto);
	
	
	/**
     * 由频道名称查询是否有该信息
     * @param SignMessageDto
     * @return Integer
     */
	public Integer countSignMessageByCondition(SignMessageDto dto);
	public List<SignMessageDto> validateDepName(SignMessageDto dto);
}
