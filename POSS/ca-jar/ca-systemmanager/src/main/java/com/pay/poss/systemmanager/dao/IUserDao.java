package com.pay.poss.systemmanager.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.poss.systemmanager.formbean.UserFormBean;
import com.pay.poss.systemmanager.formbean.UserRelation;
import com.pay.poss.systemmanager.model.Duty;
import com.pay.poss.systemmanager.model.Org;
import com.pay.poss.systemmanager.model.Users;

/**
 * 用户管理Dao 接口类
 * 
 * @author wucan
 * @descript
 * @data 2010-7-22 下午06:00:24
 */
public interface IUserDao extends BaseDAO<Users> {
	
	List<UserFormBean> queryByMap(Map<String,String> map);
	
	List<Duty> queryAllDuty();
	
	List<Org> queryAllOrg();
	
	boolean deleteUser(Users user);
	
	UserFormBean queryUserByKy(Users user);
	
	public boolean updatePassword(Users user);
	
	public Page<UserFormBean> search(Page<UserFormBean> page,Map<String,String> map);

	public List<Users> queryUsersByRole(Long roleKey);
	/**
	 *  有权限处理指定流程和节点的用户列表
	 * @param wokeFlowName 工作流名称（必选）
	 * @param nodeName 节点名称（可选）
	 * @return List<Users> 用户列表信息
	 * @author 戴德荣
	 */
	//public List<Users> queryUsersByFlowName(String wokeFlowName,String nodeName);
	
	/**
	 * 通过登录号来查找用户
	 * @param loginId
	 * @return Users对象或是空
	 * @date 2010-11-30
	 * @author 戴德荣
	 */
	public Users findByLoginId(String loginId);
	
	/**
	 * 得到所有的用户
	 * @return List<Users>
	 * @date 2011-1-6
	 */
	public List<Users> findAllUsers();
	/***
	 * 获取BD名称
	 * @return
	 */
	 public List getBDName();
	 /***
	  * 添加BD权限
	  * @param userR
	  */
	void saveUserRelation(UserRelation userR);
	/***
	 * 修改BD权限
	 * @param userR
	 */
	void updateUserRelation(UserRelation userR);
	/**
	 * 获取Leader
	 * @return
	 */
	UserRelation getLeader();
	/**
	 * 通过loginId查询UserRelation
	 * @param getpLoginId
	 * @return
	 */
	UserRelation findUserRelation(String getpLoginId);

	List<UserRelation> findByLayer(Long id);

	UserRelation findUseRelationById(String layer);
	
	

 	
}
