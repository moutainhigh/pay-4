package com.pay.poss.merchantmanager.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.merchantmanager.dto.SignMessageDto;
import com.pay.poss.merchantmanager.model.SignMessage;


/**
 * 
 * @Description 
 * @project 	ma_manager
 * @file 		ISignMessageDao.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-29		tianqing_wang			Create
 */

public interface ISignMessageDao extends BaseDAO<SignMessage> {
	
	/**
     * 频道新增
     * @param SignMessageDto
     * @return Long
     */
	
	public Long createSignMessage(SignMessageDto dto);
	
	/**
     * 频道查询list
     * @param SignMessageDto
     * @return List<SignMessageDto>
     */
	public List<SignMessageDto> querySignMessageList(SignMessageDto dto);
	
	/**
     * 频道查询count
     * @param SignMessageDto
     * @return Integer
     */
	public Integer countSignMessage(SignMessageDto dto);
	
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
     * 由id查询频道信息
     * @param SignMessageDto
     * @return List<SignMessageDto>
     */
	public List<SignMessageDto> querySignMessageByCondition(SignMessageDto dto);
	
	/**
     * 由频道名称查询是否有该信息
     * @param SignMessageDto
     * @return Integer
     */
	public Integer countSignMessageByCondition(SignMessageDto dto);
	public List<SignMessageDto> validateDepName(SignMessageDto dto);

}