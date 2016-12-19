package com.pay.poss.systemmanager.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.poss.systemmanager.dao.IRoleDao;
import com.pay.poss.systemmanager.formbean.RoleFormBean;
import com.pay.poss.systemmanager.model.Role;
import com.pay.poss.systemmanager.model.UserInfo;
import com.pay.poss.systemmanager.model.UserRole;

public interface IRoleService {

	public boolean insertRole(Role role);

	public boolean insertBatch(String roleKy, RoleFormBean roleFormBean,
			long[] ids) throws Exception;

	public Role queryRoleByRoleKy(String roleKy);

	public List<Role> findAllRole();

	public boolean deleteRole(String roleKy) throws Exception;

	public List<Role> queryRole(Map<String, String> map);

	public List<RoleFormBean> allRole();

	public List<Role> queryRoleOfUser(String userId);

	public Page<Role> search(Page<Role> page, Map<String, String> map);

	public List<UserInfo> findAllUser();

	public boolean insertRoleOfUser(UserRole userRole);

	public boolean dropRoleOfUser(Map<String, String> map);

	/**
	 * 得到对应角色所有userKy以及角色姓名
	 * 
	 * @param roleKy
	 *            角色id
	 * @see IRoleDao#queryUsersRoleByRoleCode(java.lang.Strings)
	 * @return <Map<String,Object>> key value USER_KEY 用户id Long ROLE_NAME 角色名称
	 *         String
	 * @author stoor_dai
	 */
	public List<Map<String, Object>> queryUsersRoleByRoleCode(String roleCode);
}
