package com.pay.poss.systemmanager.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.systemmanager.dao.IRoleDao;
import com.pay.poss.systemmanager.model.Role;
import com.pay.poss.systemmanager.model.RoleRes;
import com.pay.poss.systemmanager.model.UserRole;

public class RoleDaoImpl extends BaseDAOImpl<Role> implements IRoleDao {

	private BaseDAO daoService;

	private Log log = LogFactory.getLog(RoleDaoImpl.class);

	public Long insertRole(Role role) {
		log.debug("RoleDaoImpl.insertRole is running...");
		Long roleId = (Long) this.getSqlMapClientTemplate().insert(
				namespace.concat("insertRole"), role);
		return roleId;
	}

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

	// 条件分页查询
	public Page<Role> search(Page<Role> page, Map<String, String> map) {
		return daoService.findByQuery(namespace.concat("queryRole"), page, map);
	}

	public List findAllRole() {
		log.debug("RoleDaoImpl.findAllRole is running...");
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("findAllRole"));
	}

	public List allRole() {
		log.debug("RoleDaoImpl.findAllRole is running...");
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("allRole"));
	}

	public List queryRole(Map map) {
		log.debug("RoleDaoImpl.queryRole is running...");
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("queryRole"), map);
	}

	public boolean dropRoleById(String id) {
		log.debug("RoleDaoImpl.dropRoleById is running...");
		return this.delete(Long.parseLong(id));

	}

	public List queryMenuOfRole(String roleId) {
		log.debug("RoleDaoImpl.queryMenuOfRole is running...");
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("queryMenuOfRole"), roleId);
	}

	public List queryRoleOfUser(String userId) {
		log.debug("RoleDaoImpl.queryRoleOfUser is running...");
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("queryRoleOfUser"), userId);
	}

	public List findAllMenu() {
		log.debug("RoleDaoImpl.findAllMenu is running...");
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("findAllMenu"));

	}

	public void dropMenuOfRole(Map map) {
		log.debug("RoleDaoImpl.dropMenuOfRole is running...");
		this.getSqlMapClientTemplate().delete(
				namespace.concat("dropMenuOfRole"), map);

	}

	public void dropRoleOfUser(Map<String, String> map) {
		log.debug("RoleDaoImpl.dropRoleOfUser is running...");
		this.getSqlMapClientTemplate().delete(
				namespace.concat("dropRoleOfUser"), map);

	}

	public void insertMenuOfRole(RoleRes roleRes) {
		log.debug("RoleDaoImpl.insertMenuOfRole is running...");
		this.getSqlMapClientTemplate().insert(
				namespace.concat("insertMenuOfRole"), roleRes);

	}

	public void insertRoleOfUser(UserRole userRole) {
		log.debug("RoleDaoImpl.insertRoleOfUser is running...");
		this.getSqlMapClientTemplate().insert(
				namespace.concat("insertRoleOfUser"), userRole);

	}

	public List<RoleRes> queryRoleResByRoleKy(Long roleKy) {
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("queryRoleRes"), roleKy);
	}

	public int updateRole(Role role) {
		return this.getSqlMapClientTemplate().update(
				namespace.concat("updateRole"), role);
	}

	public int updateRoleRes(RoleRes roleRes) {
		return this.getSqlMapClientTemplate().update(
				namespace.concat("updateRoleRes"), roleRes);
	}

	public int deleteRole(String roleKy) {
		return this.getSqlMapClientTemplate().delete(
				namespace.concat("deleteRole"), roleKy);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.systemmanager.dao.IRoleDao#queryUsersRoleByRoleKy(java.lang
	 * .Long)
	 */
	@Override
	public List<Map<String, Object>> queryUsersRoleByRoleCode(String roleCode) {
		log.debug("RoleDaoImpl.queryUsersRoleByRoleKy is running...");
		return this.getSqlMapClientTemplate().queryForList(
				namespace.concat("queryUsersRoleByRoleKy"), roleCode);
	}
}
