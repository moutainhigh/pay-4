package com.pay.poss.userrelation.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.poss.userrelation.dto.NodesDto;
import com.pay.poss.userrelation.dto.UserRelationDto;

/**
 * 
 *  File: IUserRelationService.java
 *  Description:
 *  Copyright 2006-2012 pay Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2014-5-5   liuqinghe     Create
 *
 */
public interface IUserRelationService {

	/**
	 * 创建
	 * @param userRelationDto
	 */
	public abstract void createUserRelation(UserRelationDto userRelationDto);
	
	/**
	 * 删除
	 * @param id
	 */
	public abstract void deleteUserRelation(long id);
	
	/**
	 * 修改
	 * @param userRelationDto
	 */
	public abstract void updateUserRelation(UserRelationDto userRelationDto);
	
	/**
	 * 根据登录Id查询所有所属的子节点登录Id
	 * @param login_id
	 * @return
	 */
	public abstract List<NodesDto> findAllSubLoginId(String login_id);
	
	/**
	 * 根据登录loginId查询
	 * @param login_id
	 * @return
	 */
	public abstract  UserRelationDto findUserRelatoinByLoginId(String login_id);
	
	/**
	 * 根据主键ID得到对象
	 * @param id
	 * @return
	 */
	public abstract UserRelationDto findById(long id);
	
	/**
	 * 根据条件分页查询
	 * @param userRelationDto
	 * @param page
	 * @return
	 */
	public abstract List<UserRelationDto>  queryUserRelationByCondition(UserRelationDto userRelationDto,Page  page);
	
	/**
	 * 查找是否存在loginId用户名
	 * @param loginId
	 * @return
	 */
	public abstract boolean isExistUser(String loginId);
	/**
	 * 递归查找子节点
	 * @param id
	 * @return
	 */
	public abstract List<UserRelationDto> findByLayer(long id);

	public abstract List<NodesDto> findAll();

}
