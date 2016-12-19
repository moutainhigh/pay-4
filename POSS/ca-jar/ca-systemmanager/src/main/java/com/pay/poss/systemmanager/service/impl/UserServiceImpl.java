package com.pay.poss.systemmanager.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.encoding.Md5PasswordEncoder;

import com.pay.inf.dao.Page;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.poss.systemmanager.dao.IUserDao;
import com.pay.poss.systemmanager.formbean.UserFormBean;
import com.pay.poss.systemmanager.formbean.UserRelation;
import com.pay.poss.systemmanager.model.Duty;
import com.pay.poss.systemmanager.model.Org;
import com.pay.poss.systemmanager.model.Users;
import com.pay.poss.systemmanager.service.IUserService;

/**
 * 用户管理Service
 * 
 * @author wucan
 * @descript
 * @data 2010-7-22 下午05:58:47
 */
public class UserServiceImpl implements IUserService {

	private Map DutyAndOrgmap = new HashMap();

	private Log logger = LogFactory.getLog(getClass());

	private IUserDao userDAO;

	// 注入DAO
	public void setUserDAO(IUserDao userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public boolean deleteUser(String id) {
		Users user = new Users();
		user.setStatus(0);
		user.setUserKy(new Long(id));
		return userDAO.deleteUser(user);
	}

	@Override
	public Users findUserById(String id) {
		return userDAO.findById(new Long(id));
	}

	@Override
	public List<Users> findAllUsers() {
		return userDAO.findAllUsers();
	}

	@Override
	public void saveUser(UserFormBean userFormBean) throws Exception {

		try {
			// // 自然人
			// Person person = new Person();
			// person.setName(userFormBean.getUserName());
			//
			// // 新增自然人
			// personDAO.create(person);
			//
			// // 员工
			// Staff staff = new Staff();
			// staff.setEmail(userFormBean.getUserEmail());
			// staff.setRtxCode(userFormBean.getUserRTX());
			// staff.setPhone(userFormBean.getUserPhone());
			// staff.setMobile(userFormBean.getUserMobile());
			// staff.setPersonKy(person.getPersonKy());
			// staff.setOrgKy(new Long(userFormBean.getUserOrgKy()));
			// staff.setDutyKy(new Long(userFormBean.getUserDutyKy()));
			//
			// // 新增员工
			// staffDAO.create(staff);

			/*
			 * // 岗位 Position position = new Position(); position.setDutyKy(new
			 * Long(userFormBean.getUserDuty())); position.setOrgKy(new
			 * Long(userFormBean.getUserOrg()));
			 */

			// 新增用户
			Users user = new Users();
			setFormBeanToUser(userFormBean, user);
			userDAO.create(user);
			logger.info("新增用户" + user.getLoginId() + "--" + user.getUserName());
		} catch (Exception ex) {
			logger.error("新增用户关联自然人、员工、岗位");
			ex.printStackTrace();
			throw new Exception("新增用户关联自然人、员工、岗位");
		}
	}

	@Override
	public boolean updateUser(UserFormBean userFormBean) throws Exception {

		try {
			// 自然人
			// Person person = personDAO.findById(new
			// Long(userFormBean.getPersonKy()));
			// person.setName(userFormBean.getUserName());
			//
			// // 新增自然人
			// personDAO.update(person);
			//
			// // 员工
			// Staff staff = staffDAO.findById(new
			// Long(userFormBean.getStaffKy()));
			// staff.setEmail(userFormBean.getUserEmail());
			// staff.setRtxCode(userFormBean.getUserRTX());
			// staff.setPhone(userFormBean.getUserPhone());
			// staff.setMobile(userFormBean.getUserMobile());
			// staff.setPersonKy(person.getPersonKy());
			// staff.setOrgKy(new Long(userFormBean.getUserOrgKy()));
			// staff.setDutyKy(new Long(userFormBean.getUserDutyKy()));
			//
			// // 新增员工
			// staffDAO.update(staff);
			//			
			// 新增用户
			Users user = userDAO.findById(new Long(userFormBean.getUserKy()));
			setFormBeanToUser(userFormBean, user);
			userDAO.update(user);
		} catch (Exception ex) {
			logger.error(ex);
			logger.error("修改用户关联自然人、员工、岗位", ex);
			throw new Exception("修改用户关联自然人、员工、岗位");
		}

		return true;
	}

	@Override
	public List<UserFormBean> queryAll(Map<String, String> map) {
		return userDAO.queryByMap(map);
	}

	// 条件查询分页
	public Page<UserFormBean> search(Page<UserFormBean> page,
			Map<String, String> map) {
		return userDAO.search(page, map);
	}

	@Override
	public Map queryAllDutyAndOrg() {

		if (!DutyAndOrgmap.containsKey("dutys")) {
			List<Duty> dutyList = userDAO.queryAllDuty();
			DutyAndOrgmap.put("dutys", dutyList);
		}
		if (!DutyAndOrgmap.containsKey("org")) {
			List<Org> orgList = userDAO.queryAllOrg();
			DutyAndOrgmap.put("orgs", orgList);
		}
		return DutyAndOrgmap;
	}

	@Override
	public UserFormBean queryUserByKy(String id) {
		Users user = new Users();
		user.setUserKy(new Long(id));
		return userDAO.queryUserByKy(user);
	}

	public boolean changePassword(String currentPassword, String newPassword) {
		Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();
		SessionUserHolder sessionUserHolder = (SessionUserHolder) authentication
				.getPrincipal();
		Md5PasswordEncoder md5 = new Md5PasswordEncoder();
		String currentPwd = sessionUserHolder.getPassword();
		currentPassword = md5.encodePassword(currentPassword, null);
		if (currentPassword.equals(currentPwd)) {
			Users user = new Users();
			user.setUserKy(new Long(sessionUserHolder.getUserKy()));
			newPassword = md5.encodePassword(newPassword, null);
			user.setPassword(newPassword);
			return userDAO.updatePassword(user);
		} else {
			return false;
		}
	}

	/*
	 * @Override public List<String> findLoginIdByFlowName(String wokeFlowName,
	 * String nodeName) { if(StringUtils.isBlank(wokeFlowName)){ return null; }
	 * List<Users> users= userDAO.queryUsersByFlowName(wokeFlowName, nodeName);
	 * List<String> loginIds = new ArrayList<String>(); for(Users us : users){
	 * loginIds.add(us.getLoginId()); } return loginIds; }
	 * 
	 * @Override public List<String> findLoginIdByFlowName(String wokeFlowName)
	 * { return findLoginIdByFlowName(wokeFlowName,null); }
	 */

	public Users findUserByLoginId(String loginId) {
		return userDAO.findByLoginId(loginId);
	}

	/**
	 *将userFormBean的值设置到user里
	 * 
	 * @param userFormBean
	 * @param user
	 * @date 2011-1-5
	 */
	private void setFormBeanToUser(UserFormBean userFormBean, Users user) {
		user.setLoginId(userFormBean.getUserCode());
		if (StringUtils.isNotEmpty(userFormBean.getUserPassword())) {
			Md5PasswordEncoder md5 = new Md5PasswordEncoder();
			String password = md5.encodePassword(
					userFormBean.getUserPassword(), null);
			user.setPassword(password);
		}
		user.setStatus(userFormBean.getUserStatus());
		user.setUserEmail(userFormBean.getUserEmail());
		user.setUserName(userFormBean.getUserName());
		user.setUserMobile(userFormBean.getUserMobile());
		user.setUserPhone(userFormBean.getUserPhone());
		user.setUserRTX(userFormBean.getUserRTX());
		user.setDutyCode(userFormBean.getUserDutyCode());
		user.setOrgCode(userFormBean.getUserOrgCode());
	}
	
	/***
	 * 获取BD名称
	 */
	@Override
	public List getBDName() {
		return userDAO.getBDName();
	}

	@Override
	public void saveUserRelation(UserRelation userR) {
		userDAO.saveUserRelation(userR);
	}

	@Override
	public void updateUserRelation(UserRelation userR) {
		userDAO.updateUserRelation(userR);
	}

	@Override
	public UserRelation getLeader() {
		return userDAO.getLeader();
	}

	@Override
	public UserRelation findUserRelation(String loginId) {
		return userDAO.findUserRelation(loginId);
	}

	@Override
	public List<UserRelation> findByLayer(Long id) {
		return userDAO.findByLayer(id);
	}

	@Override
	public UserRelation findUseRelationById(String layer) {
		return userDAO.findUseRelationById(layer);
	}

}
