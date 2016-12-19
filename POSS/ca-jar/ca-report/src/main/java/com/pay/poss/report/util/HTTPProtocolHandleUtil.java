package com.pay.poss.report.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description Http协议预处理工具类
 * @project gateway-pay
 * @file RefundService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2011 HNA Corporation. All rights reserved. Date
 *          Author Changes 2010-10-13 Sean.Chen  Update
 */

public class HTTPProtocolHandleUtil {
	
	public static void setAll(HttpServletRequest request, HttpServletResponse response){
		HTTPProtocolHandleUtil.setNoCache(request, response);
		HTTPProtocolHandleUtil.setUTF8(request, response);
	}
	
	/**
	 * 设置字符集为UTF-8
	 * @param request
	 * @param response
	 */
	public static void setUTF8(HttpServletRequest request, HttpServletResponse response){
		try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("UTF-8");
	}
	
	/**
	 * 设置字符集为GBK
	 * @param request
	 * @param response
	 */
	public static void setGBK(HttpServletRequest request, HttpServletResponse response){
		try {
			request.setCharacterEncoding("GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("GBK");
	}
	
	/**
	 * 设置字符集为GB2312
	 * @param request
	 * @param response
	 */
	public static void setGB2312(HttpServletRequest request, HttpServletResponse response){
		try {
			request.setCharacterEncoding("GB2312");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		response.setCharacterEncoding("GB2312");
	}
	
	/**
	 * 设置不缓存
	 * @param request
	 * @param response
	 */
	public static void setNoCache(HttpServletRequest request, HttpServletResponse response){
		if (request.getProtocol().compareTo("HTTP/1.0") == 0){

			response.setHeader("Pragma","no-cache");

			}else if (request.getProtocol().compareTo("HTTP/1.1") == 0){

			response.setHeader("Cache-Control","no-cache");

			}
			response.setDateHeader("Expires",0);
	}
	
	public static String encodeDownFilename(String fileName,HttpServletRequest request) throws UnsupportedEncodingException  {
		   String agent = request.getHeader("USER-AGENT");
		  
		   if (null != agent && -1 != agent.indexOf("MSIE")) {// IE
		       fileName = URLEncoder.encode(fileName, "UTF-8");     
		   } else if (null != agent && -1 != agent.indexOf("Mozilla")) {// Firefox
		       fileName = new String(fileName.getBytes("UTF-8"),"iso-8859-1");
		   } else{
		       fileName = URLEncoder.encode(fileName, "UTF-8");      
		   }	
		   return fileName;
	    }
	
	
}
