package com.pay.poss.appealmanager.controller;


import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealInfoForCallBackController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-28		gungun_zhang			Create
 */

public class AppealPictureInfoController extends AbstractController{
	
	private Log log = LogFactory.getLog(AppealPictureInfoController.class);

	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
    	log.debug("AppealInfoForCallBackController.handleRequestInternal() is running...");
    	String appealCode = request.getParameter("appealCode");
    	String picturePath = "/opt/upload/ma/appeal/"+appealCode;
    	String isExist1 = null;
    	String isExist2 = null;
    	String isExist3 = null;
    	String isExist4 = null;
    	String isExist5 = null;
    	String isExist6 = null;
    
    	try{FileInputStream in1 = new FileInputStream(picturePath+"/picture1.jpg"); }catch(Exception e){isExist1 = new String();}
    	try{FileInputStream in2 = new FileInputStream(picturePath+"/picture2.jpg"); }catch(Exception e){isExist2 = new String();}
    	try{FileInputStream in3 = new FileInputStream(picturePath+"/picture3.jpg"); }catch(Exception e){isExist3 = new String();}
    	try{FileInputStream in4 = new FileInputStream(picturePath+"/picture4.jpg"); }catch(Exception e){isExist4 = new String();}
    	try{FileInputStream in5 = new FileInputStream(picturePath+"/picture5.jpg"); }catch(Exception e){isExist5 = new String();}
    	try{FileInputStream in6 = new FileInputStream(picturePath+"/picture6.jpg"); }catch(Exception e){isExist6 = new String();}
    	
    	Map<String,Object> dataMap = new HashMap<String,Object>();
    	dataMap.put("appealCode", appealCode);
    	dataMap.put("isExist1", isExist1);
    	dataMap.put("isExist2", isExist2);
    	dataMap.put("isExist3", isExist3);
    	dataMap.put("isExist4", isExist4);
    	dataMap.put("isExist5", isExist5);
    	dataMap.put("isExist6", isExist6);
    	
        return new ModelAndView("/appealmanager/appealPictureInfo").addAllObjects(dataMap);
	}
   
  
		          
  
   
   
	
	
}
