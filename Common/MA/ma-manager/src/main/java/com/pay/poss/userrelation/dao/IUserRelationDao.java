package com.pay.poss.userrelation.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.userrelation.dto.NodesDto;
import com.pay.poss.userrelation.dto.UserRelationDto;

/**
 * 
 * File: IUserRelationDao.java Description: Copyright 2006-2012 pay
 * Corporation. All rights reserved. Date Author Changes 2014-5-5 liuqinghe
 * Create
 * 
 */
public interface IUserRelationDao extends BaseDAO {

	/**
	 * 插入更新右值
	 * 
	 * @param rv
	 */
	public abstract void insertUpdateRigthValue(int rv);

	/**
	 * 插入更新左值
	 * 
	 * @param rv
	 */
	public abstract void insertUpdateLeftValue(int rv);

	/**
	 * 得到当前节点的层级
	 * 
	 * @param lv
	 * @param rv
	 * @return
	 */
	public abstract int countLayer(int lv, int rv);

	/**
	 * 根据登录Id 查找
	 * 
	 * @param login_id
	 * @return
	 */
	public abstract UserRelationDto findUserRDtoByLoginId(String login_id);

	/**
	 * 根据登录Id查询所有所属的子节点登录Id
	 * 
	 * @param login_id
	 * @return
	 */
	public abstract List<NodesDto> findAllSubLoginId(int lv, int rv);

	/**
	 * 得到所有节点最大右值
	 * 
	 * @return
	 */
	public abstract int getMaxRv();

	/**
	 * 节点上级发生改变,以当前节点左右值更新
	 * 
	 * @param lv
	 * @param rv
	 */
	public abstract void changeNodeUpdate(int lv, int rv);

	/**
	 * 节点上级发生改变,更新右值
	 * 
	 * @param lv
	 * @param rv
	 */
	public abstract void changeNodeUpdateRV(int lv, int rv);

	/**
	 * 节点上级发生改变,更新左值
	 * 
	 * @param lv
	 * @param rv
	 */
	public abstract void changeNodeUpdateLV(int lv, int rv);

	/**
	 * 节点上级发生改变,上级节点不为空,更新右值
	 * 
	 * @param lv
	 * @param rv
	 */
	public abstract void changeNodeUpdateRVForNP(int offset, int newLv);

	/**
	 * 节点上级发生改变,上级节点不为空,更新左值
	 * 
	 * @param lv
	 * @param rv
	 */
	public abstract void changeNodeUpdateLVForNP(int offset, int newLv);

	/**
	 * 更新右值小于等于0的所有节点
	 * 
	 * @param offset
	 */
	public abstract void changeNodeUpdateRV(int offset);

	/**
	 * 更新左值小于等于0的所有节点
	 * 
	 * @param offset
	 */
	public abstract void changeNodeUpdateLV(int offset);

	/**
	 * 删除节点和所有子节点
	 * 
	 * @param lv
	 * @param rv
	 */
	public abstract void deleteNode(int lv, int rv);

	/**
	 * 删除后更新节点左值
	 * 
	 * @param lv
	 * @param rv
	 */
	public abstract void deleteNodeUpdateLV(int lv, int rv);

	/**
	 * 删除节点更新节点右值
	 * 
	 * @param lv
	 * @param rv
	 */
	public abstract void deleteNodeUpdateRv(int lv, int rv);

	public abstract int countMemberRelationByCondition(
			UserRelationDto userRelationDto);

	public abstract List<UserRelationDto> queryMemberRelationByCondition(
			UserRelationDto dto);

	/**
	 * 查找是否存在loginId用户名
	 * 
	 * @param loginId
	 * @return
	 */
	public abstract int isExistUser(String loginId);
	
	
	/***
	 * 利用id 查询层级 查询所有的子节点
	 * @param id
	 * @return
	 */
	public abstract List<UserRelationDto> findByLayer(long id);

	
	public abstract List<NodesDto> findAll();


}
