package com.pay.poss.systemmanager.dao.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.systemmanager.dao.IUserDao;
import com.pay.poss.systemmanager.formbean.UserFormBean;
import com.pay.poss.systemmanager.formbean.UserRelation;
import com.pay.poss.systemmanager.model.Duty;
import com.pay.poss.systemmanager.model.Org;
import com.pay.poss.systemmanager.model.Users;

public class UserDaoImpl extends BaseDAOImpl<Users> implements IUserDao {

	private BaseDAO daoService;

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

	public Page<UserFormBean> search(Page<UserFormBean> page,
			Map<String, String> map) {
		return daoService.findByQuery(namespace.concat("search"), page, map);
	}

	public List<UserFormBean> queryByMap(Map<String, String> map) {
		return (List<UserFormBean>) getSqlMapClientTemplate().queryForList(
				namespace.concat("search"), map);
	}

	@Override
	public List<Duty> queryAllDuty() {
		return (List<Duty>) getSqlMapClientTemplate().queryForList(
				namespace.concat("queryAllDuty"), null);
	}

	@Override
	public List<Org> queryAllOrg() {
		return (List<Org>) getSqlMapClientTemplate().queryForList(
				namespace.concat("queryAllOrg"), null);
	}

	@Override
	public boolean deleteUser(Users user) {
		return getSqlMapClientTemplate().update(namespace.concat("delete"),
				user) == 1;
	}

	@Override
	public UserFormBean queryUserByKy(Users user) {
		return (UserFormBean) getSqlMapClientTemplate().queryForObject(
				namespace.concat("queryUserByKy"), user);
	}

	public boolean updatePassword(Users user) {
		return getSqlMapClientTemplate().update(
				namespace.concat("updatePassword"), user) == 1;
	}

	@Override
	public List<Users> queryUsersByRole(Long roleKey) {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("queryUsersByRole"), roleKey);
	}

	/*
	 * @Override public List<Users> queryUsersByFlowName(String wokeflowName,
	 * String nodeName) { Map<String, String> map = new HashMap<String,
	 * String>(); map.put("wokeflowName", wokeflowName); map.put("nodeName",
	 * nodeName); return
	 * getSqlMapClientTemplate().queryForList("user.queryUsersByFlowName", map);
	 * }
	 */

	@Override
	public Users findByLoginId(String loginId) {
		return (Users) getSqlMapClientTemplate().queryForObject(
				namespace.concat("findByLoginId"), loginId);
	}

	@Override
	public List<Users> findAllUsers() {
		return getSqlMapClientTemplate().queryForList(
				namespace.concat("allUser"));
	}

	@Override
	public List getBDName() {
		List queryForList = getSqlMapClientTemplate().queryForList(
				namespace.concat("getBDName"));
		return queryForList;
	}

	@Override
	public void saveUserRelation(UserRelation userR) {
		getSqlMapClientTemplate().insert(namespace.concat("saveUserRelation"), userR);
	}

	@Override
	public void updateUserRelation(UserRelation userR) {
		getSqlMapClientTemplate().update(namespace.concat("updateUserRelation"),userR);
	}

	@Override
	public UserRelation getLeader() {
		List<UserRelation> queryForList = getSqlMapClientTemplate().queryForList(
				namespace.concat("getLeader"));
		return queryForList==null||queryForList.isEmpty()?null:queryForList.get(0);
	}

	@Override
	public UserRelation findUserRelation(String loginId) {
		List<UserRelation> queryForList = getSqlMapClientTemplate().queryForList(
				namespace.concat("findUserRelation"),loginId);
		return queryForList==null||queryForList.isEmpty()?null:queryForList.get(0);
	}

	@Override
	public List<UserRelation> findByLayer(Long id) {
		List<UserRelation> queryForList = getSqlMapClientTemplate().queryForList(
				namespace.concat("findLayerById"),id);
		return queryForList;
	}

	@Override
	public UserRelation findUseRelationById(String layer) {
		List<UserRelation> queryForList = getSqlMapClientTemplate().queryForList(
				namespace.concat("findUseRelationById"),layer);
		return  queryForList==null||queryForList.isEmpty()?null:queryForList.get(0);
	}
}
