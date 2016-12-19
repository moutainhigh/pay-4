package com.pay.poss.systemmanager.controller;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.poss.systemmanager.formbean.UserFormBean;
import com.pay.poss.systemmanager.formbean.UserRelation;
import com.pay.poss.systemmanager.model.Users;
import com.pay.poss.systemmanager.service.IUserService;

/**
 * 用户新增
 * 
 * @author wucan
 * @descript
 * @data 2010-7-22 下午12:08:12
 * @author 戴德荣
 * @date 2010-11-30
 */

public class UserAddController extends SimpleFormController {

	protected final Log log = LogFactory.getLog(getClass());

	private IUserService userService;

	
	public ModelAndView ajaxOnSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		UserFormBean userFormBean = (UserFormBean) command;
		PrintWriter out = response.getWriter();
		try {
			userService.saveUser(userFormBean);
			out.print("success");
		} catch (Exception e) {
			log.error("UserAddController.ajaxOnSubmit保存用户出现异常", e);
			out.print("error");
			
		}
		return null;
	}
	
	@Override
	public ModelAndView onSubmit(HttpServletRequest request,
			HttpServletResponse response, Object command, BindException errors)
			throws Exception {
		UserFormBean userFormBean = (UserFormBean) command;
		UserRelation userR=null;
		String ifBD=request.getParameter("ifBD"); //是否是市场部  1 否 2是
		String ifLeader=request.getParameter("ifLeader"); //是否是 leader  1 否 2是
		String superior=request.getParameter("superior");//上级领导id
		String userCode = request.getParameter("userCode");
		String userName = request.getParameter("userName");
		if(ifBD.equals("2")){//市场部
			userR=new UserRelation();
			userR.setLoginId(userCode);
			userR.setName(userName);
			userR.setCreateDate(new Timestamp(new Date().getTime()));
			if(ifLeader.equals("1")){//不是leader
				if(StringUtils.isEmpty(superior)){//没有输入上级领导
					return showForm(request, errors,"请输入上级领导");	
				}else{//输入了上级领导
					userR.setLayer(superior);
				}
			}else{//是上级领导
				userR.setLayer(null);   ///上级为空则为leader
			}
		}
		//判断是否被注册过
		Users exitsUser =  userService.findUserByLoginId(userFormBean.getUserCode());
		if(exitsUser !=null && exitsUser instanceof Users ){
			return showForm(request, errors, getFormView()).addObject("command",userFormBean).addObject("error",userFormBean.getUserCode()+"已经存在，请更名！");
		}
		if(userR!=null){
			userService.saveUserRelation(userR);
		}
		try {
			userService.saveUser(userFormBean);
			
		} catch (Exception e) {
//			System.out.println(code.getCode());
//			System.out.println(code.getDescription());
			log.error("UserAddController.onSubmit保存用户出现异常", e);
		}
		return new ModelAndView("redirect:user.do");
	}
	
	
	
	
	@Override
	protected Map referenceData(HttpServletRequest request) throws Exception {
		return userService.queryAllDutyAndOrg();
	}
	
	@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws Exception {
		
		return new UserFormBean ();
	}
	

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
}
