/*
 * Copyright © 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.controller.base.operator;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.common.MaConstant;
import com.pay.acc.service.account.SafeQuestionVerifyService;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.common.helper.MessageConvertFactory;
import com.pay.app.common.safequestion.Safequestion;
import com.pay.base.dto.MemberCriteria;
import com.pay.base.dto.MenuDto;
import com.pay.base.model.Operator;
import com.pay.base.model.OperatorExtLoginInfo;
import com.pay.base.model.OperatorMenu;
import com.pay.base.model.PowersModel;
import com.pay.base.service.featuremenu.FeatureMenuService;
import com.pay.base.service.featuremenu.MemberFeatureService;
import com.pay.base.service.member.MemberService;
import com.pay.base.service.operator.OperatorMenuService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.DESUtil;

/**
 * 操作员管理
 * 
 * @author 
 * @version
 * @data 2010-9-21 下午04:32:24
 * 
 */
public class OperatorManageController extends MultiActionController {
	/** 操作员服务 */
	private OperatorServiceFacade operatorServiceFacade;

	private FeatureMenuService featureMenuService;
	/** 操作员管理页面 */
	private String operatorManage;
	/** 操作员登录信息页面 */
	private String operatLoginInfo;

	private String login;
	/** 添加操作员的页面 */
	private String addOperator;
	/** 修改操作员的页面 */
	private String editOperator;
	/** 修改操作员登录密码页面 */
	private String updateOperatorLoginPwd;
	private String  updateOperatorPayPwd;
	/** 选择操作员权限 */
	private String selectPermission;
	/** 查看操作员权限 */
	private String selectPermissionView;
	
	
	/** 授权操作员菜单 */
	private MemberFeatureService memberFeatureService;
	/** 操作员菜单权限 */
	private OperatorMenuService operatorMenuService;
	/** 默认当前页码 1 */
	private static final int PAGE_CURRENT = 1;
	/** 默认每页条数 7 */
	private static final int PAGE_SIZE = 20;
	
	//重构的service
	private MemberService memberService;
	/**  重置操作员登录密码安全问题页面 */
	private String resetOperatorLoginPwdQuestion;
	/**  重置操作员支付密码安全问题页面 */
	private String resetOperatorPayPwdQuestion;
	
	private SafeQuestionVerifyService safeQuestionVerifyService;

	/**
	 * 展示操作员管理页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//20160423 Mack MPS3期 去除注解权限控制   @OperatorPermission(operatPermission = "OPERATOR_MANAGE")
	public ModelAndView showOperatorView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = this.getMemberCode(request);
		if (StringUtils.isBlank(memberCode)) {
			return new ModelAndView(login);
		}

		String pageCurrent = request.getParameter("pager_offset");
		int pageSizeNum = PAGE_SIZE;
		int pageCurrentNum = PAGE_CURRENT;
		if (StringUtils.isNotBlank(pageCurrent)) {
			pageCurrentNum = Integer.valueOf(pageCurrent);
		}
		// 获取总记录条数
		int count = operatorServiceFacade.getOperatorCountForEndLog(Long
				.valueOf(memberCode));

		PageUtil pu = new PageUtil(pageCurrentNum, pageSizeNum, count);// 分页处理
		List<OperatorExtLoginInfo> infoList = operatorServiceFacade
				.getOperatorInfoForEndLog(Long.valueOf(memberCode),
						pageCurrentNum, pageSizeNum);
		Map<String, Object> map = new HashMap<String, Object>();
		// 是否为普通操作员
		LoginSession loginSession = SessionHelper.getLoginSession();
		if (loginSession.getOperatorId() > 0) {
			map.put("isOperator", "Y");
			map.put("operatorName", loginSession.getOperatorIdentity());
		}
		
		for(OperatorExtLoginInfo info:infoList){
			info.setEncodeId(DESUtil.encrypt(info.getOperatorId()+""));
			//重新给menuNameList赋值
			List<String> menuNameList = new ArrayList<String>();
			Map menu = getRightMenu(this.getMemberCode(request),info.getMenuArray());
			List<PowersModel> menuList = (List<PowersModel>)menu.get("menuList");
			if(menuList!=null&&!menuList.isEmpty()){
				for(PowersModel pm:menuList){
					List<PowersModel> childlist = pm.getChildlist();
					if(childlist==null)continue;
					for(PowersModel pmm:childlist){
						if(pmm.isChecked()){
							menuNameList.add(pmm.getMenuName());
						}
						info.setMenuNameList(menuNameList);
					}
				}
			}
		}
		
		map.put("infoList", infoList);
		map.put("pu", pu);
		map.put("memberCode", memberCode);
		return new ModelAndView(operatorManage, map);
	}
	
	private Map getRightMenu(String memberCode,
			String selectPerm){
		//String memberCode = this.getMemberCode(request);
		
		//String selectPerm = request.getParameter("selectVal");
		Map<String, Object> map = new HashMap<String, Object>();
		String[] mStr = null;
		if (StringUtils.isNotBlank(selectPerm)) {
			// 获取功能权限ID列表
			mStr = StringUtils.split(selectPerm, ",");
			// 去除重复
			List<Integer> list = new ArrayList<Integer>();
			for (String mId : mStr) {
				if (!list.contains(Integer.valueOf(mId))) {
					list.add(Integer.valueOf(mId));
				}
			}
			StringBuffer menuArray = new StringBuffer();
			for (Integer integer : list) {
				if (StringUtils.isNotBlank(menuArray.toString())) {
					menuArray.append(",").append(integer);
				} else {
					menuArray.append(integer);
				}
			}
			map.put("menuIdList", menuArray.toString());
		}
		map.put("menuList", this.getAllMenuSelected(Long.valueOf(memberCode),mStr));
		
		return map;	
	}

	/** 获取当前登录者的MemberCode */
	private String getMemberCode(HttpServletRequest request) {
		// return request.getParameter("memberCode");
		if (request.getSession().getAttribute("memberCode") == null) {
			return "";
		}
		LoginSession loginSession = SessionHelper.getLoginSession();
		return loginSession.getMemberCode();

	}

	/**
	 * 展示操作员登录信息页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//20160423 Mack MPS3期 去除注解权限控制   @OperatorPermission(operatPermission = "OPERATOR_LOGIN_QUERY")
	public ModelAndView showOperatorLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = this.getMemberCode(request);
		if (StringUtils.isBlank(memberCode)) {
			return new ModelAndView(login);
		}

		String pageCurrent = request.getParameter("pager_offset");
		int pageSizeNum = PAGE_SIZE;
		int pageCurrentNum = PAGE_CURRENT;
		if (StringUtils.isNotBlank(pageCurrent)) {
			try {
				pageCurrentNum = Integer.valueOf(pageCurrent);
			} catch (NumberFormatException e) {
				pageCurrentNum = PAGE_CURRENT;
			}
		}
		// 获取查询条件
		// 操作员姓名
		String operatorName = request.getParameter("sel_Name");
		// 操作员状态
		String operatorStatus = request.getParameter("sel_status");
		// 时间
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		int count = operatorServiceFacade.getOperatortLoginCount(Long
				.valueOf(memberCode), operatorName, startTime, endTime,
				operatorStatus);
		PageUtil pu = new PageUtil(pageCurrentNum, pageSizeNum, count);// 分页处理
		List<OperatorExtLoginInfo> infoList = operatorServiceFacade
				.getOperatorLoginInfo(Long.valueOf(memberCode), pageCurrentNum,
						pageSizeNum, operatorName, startTime, endTime,
						operatorStatus);

		List<String> nameList = operatorServiceFacade
				.getOperatorNameByMemCode(Long.valueOf(memberCode));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("sel_Name", operatorName);
		map.put("sel_status", operatorStatus);
		map.put("startTime", startTime);
		map.put("endTime", endTime);

		map.put("infoList", infoList);
		map.put("pu", pu);
		map.put("nameList", nameList);
		map.put("memberCode", memberCode);
		return new ModelAndView(operatLoginInfo, map);
	}

	/**
	 * 展示操作员添加页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_MANAGE_ADD")
	public ModelAndView showOperatorAdd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = this.getMemberCode(request);
		if (StringUtils.isBlank(memberCode)) {
			return new ModelAndView(login);
		}

		Map<String, Object> map = new HashMap<String, Object>();
		Operator operator = new Operator();
		operator.setMemberCode(Long.valueOf(memberCode));
		map.put("operator", operator);
		return new ModelAndView(addOperator, map);
	}

	/**
	 * 展示操作员修改页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "OPERATOR_MANAGE_EDIT")
	public ModelAndView showOperatorEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = this.getMemberCode(request);
		if (StringUtils.isBlank(memberCode)) {
			return new ModelAndView(login);
		}

		String operatorName = request.getParameter("operatorName");

		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(operatorName)
				&& StringUtils.isNotBlank(memberCode)) {
			Operator operator = operatorServiceFacade.getByIdentityMemCode(
					operatorName, Long.valueOf(memberCode));

			if (operator != null) {
				// 操作员姓名不能为admin
				if (StringUtils.equals(operator.getName(), "admin")) {
					map.put("errorMsg", "不能修改管理员信息");
				}
				LoginSession loginSession = SessionHelper.getLoginSession();
				if (loginSession.getOperatorId() > 0) {
					if (!StringUtils.equals(operatorName, loginSession
							.getOperatorIdentity())) {
						map.put("errorMsg", "不能修改其他操作员信息");
					}
				}
				OperatorMenu operMenu = operatorMenuService
						.getByOperateId(operator.getOperatorId());
				if (operMenu != null) {
					String menu = operMenu.getMenuArray();
					map.put("menuIds", menu);
					map.put("menuNames", getSelectedMenuName(Long.valueOf(memberCode), menu));
				}
			}
			map.put("operator", operator);
		}
		return new ModelAndView(editOperator, map);
	}

	/** 展示修改操作员登录密码页面 */
	//20160423 Mack Mps3期去除注解权限 @OperatorPermission(operatPermission = "OPERATOR_MANAGE_UPDATE_PWD")
	public ModelAndView showUpdateLoginPwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = this.getMemberCode(request);
		if (StringUtils.isBlank(memberCode)) {
			return new ModelAndView(login);
		}
		String operatorName = request.getParameter("operatorName");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(operatorName)
				&& StringUtils.isNotBlank(memberCode)) {
			Operator operator = operatorServiceFacade.getByIdentityMemCode(
					operatorName, Long.valueOf(memberCode));
			// 初始操作员密码，页面展示为空。
			if (operator != null) {
				// 操作员姓名不能为admin
				if (StringUtils.equals(operator.getName(), "admin")) {
					map.put("errorMsg", "不能修改管理员密码");
				}
				LoginSession loginSession = SessionHelper.getLoginSession();
				if (loginSession.getOperatorId() > 0) {
					if (!StringUtils.equals(operatorName, loginSession
							.getOperatorIdentity())) {
						map.put("errorMsg", "不能修改其他操作员密码");
					}
				}
				operator.setPassword("");
			}
			map.put("operator", operator);
		}
		return new ModelAndView(updateOperatorLoginPwd, map);
	}
	
	/** 展示重置操作员登录密码的安全问题页面 1.1 */
//20160423 Mack Mps3期去除注解权限 @OperatorPermission(operatPermission = "OPERATOR_MANAGE_UPDATE_PWD")
	public ModelAndView showLoginPwdSafeQuestion(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		return gotoGetSafeQuestion(request,response,resetOperatorLoginPwdQuestion);
	}
	
	/** 展示修改操作员支付密码安全问题页面 2.1 */
//20160423 Mack Mps3期去除注解权限 	@OperatorPermission(operatPermission = "OPERATOR_MANAGE_UPDATE_PAY_PWD")
	public ModelAndView showPayPwdSafeQuestion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return gotoGetSafeQuestion(request,response,resetOperatorPayPwdQuestion);
	}
	
	/** 跳转到重置操作员登录密码或支付密码的安全问题页面	 */
	private ModelAndView gotoGetSafeQuestion(HttpServletRequest request,
			HttpServletResponse response,String viewName) throws Exception {

		Map<String, Object> resMsg = new HashMap<String, Object>();

		String memberCode = this.getMemberCode(request);
		if (StringUtils.isBlank(memberCode)) {
			return new ModelAndView(login);
		}
		
		String operatorName = request.getParameter("operatorName");
		resMsg.put("operatorName", operatorName);
		// 根据用户名查询会员信息
		//切换到acc库
		MemberCriteria memberCriteria = null;
		memberCriteria = getMemberService().queryMemberCriteriaByMemberCodeNsTx(Long.valueOf(memberCode));
		if (null != memberCriteria) {
			
			resMsg.put("safequestion",
						Safequestion.getsafequestion(memberCriteria
								.getQuestionId()));
			resMsg.put("questionId", memberCriteria
								.getQuestionId());
		}
		return new ModelAndView(viewName,resMsg);
	}
	
	
	
	/** 安全问题回答正确后跳转到修改操作员登录密码重置页面 1.2 */
//20160423 Mack Mps3期去除注解权限 	@OperatorPermission(operatPermission = "OPERATOR_MANAGE_UPDATE_PWD")
	public ModelAndView showUpdateLoginPwdByQuestion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return gotoGetNewPwd(request,response,resetOperatorLoginPwdQuestion);
	}
	
	/** 安全问题回答正确后跳转到修改操作员支付密码重置页面 2.2 */
	//20160423 Mack Mps3期去除注解权限  @OperatorPermission(operatPermission = "OPERATOR_MANAGE_UPDATE_PAY_PWD")
	public ModelAndView showUpdatePayPwdByQuestion(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return gotoGetNewPwd(request,response,resetOperatorPayPwdQuestion);
	}
	
	/**
	 * 验证安全问题,跳转到重置密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private ModelAndView gotoGetNewPwd(HttpServletRequest request,
			HttpServletResponse response,String currViewName) throws Exception {

		/*
		 * 1.根据memberCode查询会员信息 2.将会员信息里的安全问题和答案与用户输入 的比对 3.步骤2验证正确则重置用户支付密码
		 */

		LoginSession loginSession = SessionHelper.getLoginSession(request);
		String memberCode = "";
		String safequestion = null == request.getParameter("questionId")
				? ""
				: request.getParameter("questionId");
		String answer = null == request.getParameter("safeanswer")
				? ""
				: request.getParameter("safeanswer");
		String safequestioninfo = null == request.getParameter("safequestion")
				? ""
				: request.getParameter("safequestion");
		if (null != loginSession) {
			memberCode = null == loginSession.getMemberCode()
					? ""
					: loginSession.getMemberCode();
		}
		Map<String, Object> resMsg = new HashMap<String, Object>();
		String operatorName = request.getParameter("operatorName");
		resMsg.put("operatorName", operatorName);
		
		if (null != memberCode && !"".equals(memberCode)) {
			MaResultDto maResultDto = safeQuestionVerifyService.doVerify(Long.valueOf(memberCode),Integer.parseInt(safequestion), answer);
			//验证成功
			if(maResultDto.getResultStatus()==MaConstant.SECCESS){
				if(currViewName != null && currViewName.equals(resetOperatorLoginPwdQuestion)){
					return showUpdateLoginPwd(request,response);
				}else if(currViewName != null && currViewName.equals(resetOperatorPayPwdQuestion)){
					return showUpdatePayPwd(request,response);
				}
				
			}
		}
		resMsg.put("returninfo", MessageConvertFactory.getMessage("answerfailure"));
		resMsg.put("safequestion", safequestioninfo);
		resMsg.put("questionId", safequestion);
		
		return new ModelAndView(currViewName, resMsg);
	}
	
	/** 展示修改操作员支付密码页面 */
	//20160423 Mack Mps3期去除注解权限  @OperatorPermission(operatPermission = "OPERATOR_MANAGE_UPDATE_PAY_PWD")
	public ModelAndView showUpdatePayPwd(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = this.getMemberCode(request);
		if (StringUtils.isBlank(memberCode)) {
			return new ModelAndView(login);
		}
		String operatorName = request.getParameter("operatorName");
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(operatorName)
				&& StringUtils.isNotBlank(memberCode)) {
			Operator operator = operatorServiceFacade.getByIdentityMemCode(
					operatorName, Long.valueOf(memberCode));
			// 初始操作员密码，页面展示为空。
			if (operator != null) {
				// 操作员姓名不能为admin
				if (StringUtils.equals(operator.getName(), "admin")) {
					map.put("errorMsg", "不能修改管理员支付密码");
				}
				LoginSession loginSession = SessionHelper.getLoginSession();
				if (loginSession.getOperatorId() > 0) {
					if (!StringUtils.equals(operatorName, loginSession
							.getOperatorIdentity())) {
						map.put("errorMsg", "不能修改其他操作员支付密码");
					}
				}
				operator.setPassword("");
			}
			map.put("operator", operator);
		}
		return new ModelAndView(updateOperatorPayPwd, map);
	}
	/** 选择操作员权限 */
	public ModelAndView selectPermission(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = this.getMemberCode(request);
		if (StringUtils.isBlank(memberCode)) {
			return new ModelAndView(login);
		}
		Map map = getRightMenu(request,response);
		return new ModelAndView(selectPermission, map);
	}
	
	/** 选择操作员权限 */
	public ModelAndView selectPermissionView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String memberCode = this.getMemberCode(request);
		if (StringUtils.isBlank(memberCode)) {
			return new ModelAndView(login);
		}
		Map map = getRightMenu(request,response);
		return new ModelAndView(selectPermissionView, map);
	}
	
	private Map getRightMenu(HttpServletRequest request,
			HttpServletResponse response){

		String memberCode = this.getMemberCode(request);
		
		String selectPerm = request.getParameter("selectVal");
		Map<String, Object> map = new HashMap<String, Object>();
		String[] mStr = null;
		if (StringUtils.isNotBlank(selectPerm)) {
			// 获取功能权限ID列表
			mStr = StringUtils.split(selectPerm, ",");
			// 去除重复
			List<Integer> list = new ArrayList<Integer>();
			for (String mId : mStr) {
				if (!list.contains(Integer.valueOf(mId))) {
					list.add(Integer.valueOf(mId));
				}
			}
			StringBuffer menuArray = new StringBuffer();
			for (Integer integer : list) {
				if (StringUtils.isNotBlank(menuArray.toString())) {
					menuArray.append(",").append(integer);
				} else {
					menuArray.append(integer);
				}
			}
			map.put("menuIdList", menuArray.toString());
		}
		map.put("menuList", this.getAllMenuSelected(Long.valueOf(memberCode),mStr));
		
		return map;	
	}

	/** 校验操作员登录名,给企业会员激活时所用 **/
	public void checkNameForActive(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String loginName = request.getParameter("identity");
		String memberCode = request.getParameter("memberCode");
		boolean flag = false;
		if (StringUtils.isNotBlank(memberCode)) {
			if (StringUtils.equals(loginName, "admin")) {
				flag = false;
			} else {
				try {
					Operator operator = operatorServiceFacade
							.getByIdentityMemCode(loginName, Long
									.valueOf(memberCode));
					if (operator == null) {
						flag = true;
					}
				} catch (Exception ex) {
					logger.warn(
							"checkNameForActive getByIdentityMemCode error!",
							ex);
				}
			}
		}
		PrintWriter out = null;
		out = response.getWriter();

		out.print(flag);
		out.flush();
		out.close();
	}

	/** 校验操作员登录名 **/
	public void checkLoginName(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String loginName = request.getParameter("identity");
		String memberCode = this.getMemberCode(request);
		boolean flag = false;
		if (StringUtils.isNotBlank(memberCode)) {
			//登录名不区分大小写
			if (StringUtils.equalsIgnoreCase(loginName, "admin")) {
				flag = false;
			} else {
				Operator operator = operatorServiceFacade.getByIdentityMemCode(
						loginName, Long.valueOf(memberCode));
				if (operator == null) {
					flag = true;
				}
			}
		}

		PrintWriter out = null;
		out = response.getWriter();

		out.print(flag);
		out.flush();
		out.close();
	}

	private String getSelectedMenuName(long memberCode, String menuArray) {
		if (StringUtils.isBlank(menuArray)) {
			return "";
		}
		List<MenuDto> mList = featureMenuService.queryMenuByIdsArray(menuArray);
		StringBuffer strbuf = new StringBuffer();
		if (mList != null && mList.size() > 0) {
			// 获取功能级信息
			int j =0;
			for (MenuDto menuDto : mList) {
				if (menuDto.getType() == 6 || menuDto.getType() == 9){
					if (StringUtils.isNotBlank(strbuf.toString())) {
						j += 1;
						if (j % 5 == 0) {
							strbuf.append("<br/>");
						} else {
							strbuf.append("&nbsp;&nbsp;&nbsp;");
						}
					}
					strbuf.append(menuDto.getName());
				}
			}
		}
		return strbuf.toString();
	}

	private List<PowersModel> getAllMenuSelected(long memberCode,
			String[] menuArray) {
		List<PowersModel> list = getAllMenu(memberCode);
		if (menuArray == null || menuArray.length < 1) {
			return list;
		}
		for (int i = 0; i < menuArray.length; i++) {
			String mId = menuArray[i];
			top: for (PowersModel powersModel : list) {
				if (StringUtils.equals(mId, powersModel.getId())) {
					powersModel.setChecked(true);
					break;
				}
				List<PowersModel> list1 = powersModel.getChildlist();
				if (list1 != null && list1.size() > 0) {
					// 获取功能级信息
					for (PowersModel powersModel2 : list1) {
						if (StringUtils.equals(mId, powersModel2.getId())) {
							powersModel2.setChecked(true);
							break top;
						}
						if (powersModel2.getChildlist() != null
								&& powersModel2.getChildlist().size() > 0) {
							for (PowersModel powersModel3 : powersModel2
									.getChildlist()) {
								if (StringUtils.equals(mId, powersModel3
										.getId())) {
									powersModel3.setChecked(true);
									break top;
								}
							}
						}
					}
				}
			}
		}
		return list;
	}

	/**
	 * 获取操作员授权菜单
	 * 
	 * @param memberCode
	 * @return
	 */
	private List<PowersModel> getAllMenu(long memberCode) {
		
//		List<PowersModel> list = memberFeatureService.getMemberMenu(
//				SecurityLvlEnum.NORMAL.getValue(), ScaleEnum.ENTERPRICE
//						.getValue(), memberCode);
		LoginSession loginSession=SessionHelper.getLoginSession();
		List<PowersModel> list = memberFeatureService.getMemberMenu(
				loginSession.getSecurityLvl(), loginSession.getScaleType(), memberCode);
		if (list != null && list.size() > 0) {
			// 目前只考虑两级菜单
			for (PowersModel powersModel : list) {
				if (powersModel.getChildlist() != null
						&& powersModel.getChildlist().size() > 0) {
					// 获取功能级信息
					getChildFeatures(powersModel.getChildlist());
					for(PowersModel pm4:powersModel.getChildlist()){
						if(pm4.getChildlist()!=null && pm4.getChildlist().size()>0){
							getChildFeatures(pm4.getChildlist());
						}
					}
				} else {
					// 无子菜单的情况
				
					List<MenuDto> menuList = featureMenuService
							.queryMenuByParentId(Long.valueOf(powersModel
									.getId()), new Integer(6));
					if (menuList != null && menuList.size() > 0) {
						List<PowersModel> childlist = new ArrayList<PowersModel>();
						for (MenuDto menuDto : menuList) {
							if (menuDto.getDisplayFlag() == 1) {
								PowersModel pmd = new PowersModel();
								pmd.setId(menuDto.getMenuId().toString());
								pmd.setMenuName(menuDto.getName());
								pmd.setMenuUrl(menuDto.getUrl());
								pmd.setParentId(menuDto.getParentId()
										.toString());
								pmd.setStatus(menuDto.getStatus());
								pmd.setDesc(menuDto.getType().toString());
								childlist.add(pmd);
							}
						}
						powersModel.setChildlist(childlist);
					}

				}
			}
		}
		return list;
	}

	private void getChildFeatures(List<PowersModel> pmAllList) {
		if (pmAllList != null && pmAllList.size() > 0) {
			for (PowersModel pm : pmAllList) {
				List<PowersModel> childlist = new ArrayList<PowersModel>();
				if(pm.getChildlist()!=null && pm.getChildlist().size()>0){
					for(PowersModel pmc:pm.getChildlist()){
						if(pmc.getMenuType()!=null && pmc.getMenuType().equals(9L)){
							childlist.add(pmc);
						}
					}
				}
				List<MenuDto> menuList = featureMenuService
						.queryMenuByParentId(Long.valueOf(pm.getId()),
								new Integer(6));
				if (menuList != null && menuList.size() > 0) {
					String defalutIds = "";
					for (MenuDto menuDto : menuList) {
						if (menuDto.getDisplayFlag() == 1) {
							PowersModel pmd = new PowersModel();
							pmd.setId(menuDto.getMenuId().toString());
							pmd.setMenuName(menuDto.getName());
							pmd.setMenuUrl(menuDto.getUrl());
							pmd.setParentId(menuDto.getParentId().toString());
							pmd.setStatus(menuDto.getStatus());
							pmd.setDesc(menuDto.getType().toString());
							// 设置该菜单的默认权限
							if (menuDto.getHierarchy() != null
									&& menuDto.getHierarchy() == 10
									) {
								defalutIds += menuDto.getMenuId()+",";
							}
							childlist.add(pmd);
						}
					}
					if(defalutIds.length()>1){
						defalutIds = defalutIds.substring(0, defalutIds.length()-1);
						pm.setDefaultPermId(defalutIds);
					}
					
					pm.setChildlist(childlist);
				}
			}
		}
	}

	public void setOperatorServiceFacade(
			OperatorServiceFacade operatorServiceFacade) {
		this.operatorServiceFacade = operatorServiceFacade;
	}

	public void setOperatorManage(String operatorManage) {
		this.operatorManage = operatorManage;
	}

	public void setAddOperator(String addOperator) {
		this.addOperator = addOperator;
	}

	public void setEditOperator(String editOperator) {
		this.editOperator = editOperator;
	}

	public void setMemberFeatureService(
			MemberFeatureService memberFeatureService) {
		this.memberFeatureService = memberFeatureService;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setOperatLoginInfo(String operatLoginInfo) {
		this.operatLoginInfo = operatLoginInfo;
	}

	public void setOperatorMenuService(OperatorMenuService operatorMenuService) {
		this.operatorMenuService = operatorMenuService;
	}

	public void setUpdateOperatorLoginPwd(String updateOperatorLoginPwd) {
		this.updateOperatorLoginPwd = updateOperatorLoginPwd;
	}

	public void setFeatureMenuService(FeatureMenuService featureMenuService) {
		this.featureMenuService = featureMenuService;
	}

	public void setSelectPermission(String selectPermission) {
		this.selectPermission = selectPermission;
	}

	public void setUpdateOperatorPayPwd(String updateOperatorPayPwd) {
		this.updateOperatorPayPwd = updateOperatorPayPwd;
	}

	/**
	 * @return the memberService
	 */
	public MemberService getMemberService() {
		return memberService;
	}

	/**
	 * @param memberService the memberService to set
	 */
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	/**
	 * @param resetOperatorLoginPwdQuestion the resetOperatorLoginPwdQuestion to set
	 */
	public void setResetOperatorLoginPwdQuestion(
			String resetOperatorLoginPwdQuestion) {
		this.resetOperatorLoginPwdQuestion = resetOperatorLoginPwdQuestion;
	}

	/**
	 * @param resetOperatorPayPwdQuestion the resetOperatorPayPwdQuestion to set
	 */
	public void setResetOperatorPayPwdQuestion(String resetOperatorPayPwdQuestion) {
		this.resetOperatorPayPwdQuestion = resetOperatorPayPwdQuestion;
	}

	/**
	 * @param safeQuestionVerifyService the safeQuestionVerifyService to set
	 */
	public void setSafeQuestionVerifyService(
			SafeQuestionVerifyService safeQuestionVerifyService) {
		this.safeQuestionVerifyService = safeQuestionVerifyService;
	}

	/**
	 * @return the selectPermissionView
	 */
	public String getSelectPermissionView() {
		return selectPermissionView;
	}

	/**
	 * @param selectPermissionView the selectPermissionView to set
	 */
	public void setSelectPermissionView(String selectPermissionView) {
		this.selectPermissionView = selectPermissionView;
	}

	
}
