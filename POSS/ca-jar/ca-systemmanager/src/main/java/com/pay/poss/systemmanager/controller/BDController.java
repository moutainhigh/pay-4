package com.pay.poss.systemmanager.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.systemmanager.formbean.UserRelation;
import com.pay.poss.systemmanager.service.IUserService;
import com.pay.util.JSonUtil;

public class BDController extends MultiActionController{

	private IUserService userService;
	
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}
	
	//获取登入名称
	public void getBDName(HttpServletRequest request,HttpServletResponse response){
		List<UserRelation> userRelations=userService.getBDName();
		String bean2json = JSonUtil.toJSonString(userRelations);
		 response.setHeader("Content-type", "text/html;charset=UTF-8");  
		 response.setCharacterEncoding("UTF-8"); 
		try {
			response.getWriter().print(bean2json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private  List<UserRelation> allUserRelationDto = new ArrayList<UserRelation>();
	
	public void getrelationDto(List<UserRelation> data){
		for(UserRelation item : data){  
			List<UserRelation> subData = userService.findByLayer(item.getId());
			if(subData!=null && !subData.isEmpty()){
				allUserRelationDto.addAll(subData);
				getrelationDto(subData);
			}else{
				allUserRelationDto.add(item);
			}
		}
	}
	
	public void getUserRelation(HttpServletRequest request,HttpServletResponse response){
		String loginId = SessionUserHolderUtil.getLoginId();
		UserRelation relationDto = userService.findUserRelation(loginId);
		allUserRelationDto.clear();
		if(relationDto !=null){
			allUserRelationDto.add(relationDto); //
			long id =relationDto.getId();
			List<UserRelation> findById = userService.findByLayer(id);
			if(findById!=null || !findById.isEmpty()){
				allUserRelationDto.addAll(findById);
				getrelationDto(findById);
			}
		}
		List<UserRelation>	tempList =null;
		if(allUserRelationDto!=null && allUserRelationDto.size()>0){
			tempList= new ArrayList<UserRelation>();  
			for(UserRelation userRelationDto:allUserRelationDto){  
		    	if(!tempList.contains(userRelationDto)){  
		            tempList.add(userRelationDto);  
		        } 
		    } 
		  }
		String bean2json="";
		if(tempList==null||tempList.isEmpty()){
			tempList=userService.getBDName();
		}
			
		 bean2json= JSonUtil.toJSonString(tempList);
		 response.setHeader("Content-type", "text/html;charset=UTF-8");  
		 response.setCharacterEncoding("UTF-8"); 
		 try {
				response.getWriter().print(bean2json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	//获取leader
	public void getLeader(HttpServletRequest request,HttpServletResponse response){
		UserRelation userRelation=userService.getLeader();
		String bean2json = JSonUtil.toJSonString(userRelation);
		 response.setHeader("Content-type", "text/html;charset=UTF-8");  
		 response.setCharacterEncoding("UTF-8"); 
		 try {
				response.getWriter().print(bean2json);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
