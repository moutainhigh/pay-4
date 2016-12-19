/**
 * 
 */
package com.pay.app.controller.bsp;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.app.dto.UserPhoneDto;
import com.pay.app.service.bsp.UpdateUserPhoneService;

/**
 * @author DDR
 *
 */
public class UpdateUserPhoneController extends MultiActionController {

	private UpdateUserPhoneService updateUserPhoneService;
	private String formView;
	private String successView;
 	
/**
	 * @return the formView
	 */
	public String getFormView() {
		return formView;
	}


	/**
	 * @param formView the formView to set
	 */
	public void setFormView(String formView) {
		this.formView = formView;
	}


	/**
	 * @return the successView
	 */
	public String getSuccessView() {
		return successView;
	}


	/**
	 * @param successView the successView to set
	 */
	public void setSuccessView(String successView) {
		this.successView = successView;
	}


	//	
//	public UpdateUserPhoneController() {
//		setCommandClass(UserPhoneDto.class);
//		setCommandName("userPhoneDto");
//	}
	/**
	 * @param updateUserPhoneService the updateUserPhoneService to set
	 */
	public void setUpdateUserPhoneService(
			UpdateUserPhoneService updateUserPhoneService) {
		this.updateUserPhoneService = updateUserPhoneService;
	}


	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#referenceData(javax.servlet.http.HttpServletRequest)
	 */
//	@Override
//	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
//		
//		Map<String,Object> map = new HashMap<String, Object>();
//		Long memberCode = ServletRequestUtils.getLongParameter(request, "memberCode", -1L);
//		UserPhoneDto userPhoneDto = new UserPhoneDto();
//		if(memberCode > 0){
//			userPhoneDto = updateUserPhoneService.getUserPhoneByMemberCode(memberCode);
//		}
//		map.put("userPhoneDto", userPhoneDto);
//		return map;
//	}
	 @OperatorPermission(operatPermission = "BSP_CHANGE_MOBILE")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response,UserPhoneDto command)
			throws Exception {
		 
		 Long memberCode = ServletRequestUtils.getLongParameter(request, "memberCode", -1L);
		 LoginSession loginSession = SessionHelper.getLoginSession(request);
		 Long sessibonMbCode =  Long.valueOf(loginSession.getMemberCode());
		 if(memberCode == -1 || ! updateUserPhoneService.hasPromisson(sessibonMbCode, memberCode)){
			return new ModelAndView("redirect:/error.htm?method=noFeature");
		}
		 
		if(request.getMethod().equalsIgnoreCase("post")){
			return changeMobile(request, response, command);
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		UserPhoneDto userPhoneDto = new UserPhoneDto();
		if(memberCode > 0){
			userPhoneDto = updateUserPhoneService.getUserPhoneByMemberCode(memberCode);
		}
		map.put("userPhoneDto", userPhoneDto);
		return new ModelAndView(getFormView(),map);
	}
	
	

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.mvc.SimpleFormController#onSubmit(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.validation.BindException)
	 */
	public ModelAndView changeMobile(HttpServletRequest request,
			HttpServletResponse response, UserPhoneDto command)
			throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession();
		UserPhoneDto userPhoneDto = (UserPhoneDto) command;
		Boolean result  =  updateUserPhoneService.updateUserPhone(userPhoneDto);
		Map<String,Object> map = new HashMap<String, Object>();
		String msg = "";
		if(result){
			msg = "修改手机号成功";
			logger.info("用户"+loginSession.getLoginName()+"修改了"+userPhoneDto.getMemberCode()+"手机号为"+userPhoneDto.getMobile()+"，更新成功。");
		}
		else{
			msg = "修改手机号失败";
			logger.info("用户"+loginSession.getLoginName()+"试图修改"+userPhoneDto.getMemberCode()+"手机号为 "+userPhoneDto.getMobile()+"，更新失败");
		}
		
		map.put("memberCode", userPhoneDto.getMemberCode());
		map.put("result", result);
		map.put("msg", msg);
		return new ModelAndView(getSuccessView(),map);
	}
	
	
	
	
}
