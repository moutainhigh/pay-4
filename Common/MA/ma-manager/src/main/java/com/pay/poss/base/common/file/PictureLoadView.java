package com.pay.poss.base.common.file;



import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		IsMemberAndAccountActivController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-5		gungun_zhang			Create
 */


public class PictureLoadView {
	
	private static Log log = LogFactory.getLog(PictureLoadView.class);
	
	

	 /**
	 * 下载文件专用类
	 * @param response　HttpServletResponse
	 * @param srcFile　文件在本地的绝对地址
	 * @param newFileName　下载时的客户端文件名
	 * @throws Exception 　异常，所有的异常将抛出
	 */
	public static void downloadView(HttpServletResponse response,String srcFile)  {

			FileInputStream fileIn = null;
			OutputStream fileOut = null;
			try {
				fileIn = new FileInputStream(srcFile);	
				fileOut = response.getOutputStream();
						
				byte[] b = new byte[2048];    
				int i = 0;    
				while((i = fileIn.read(b)) > 0)    
				{    
					fileOut.write(b, 0, i);    
				}    
				fileOut.flush();    
				fileOut.close();
			} catch (IOException e) {
				log.error("PictureLoadView.download is error...");
				fileOut = null;
				fileIn = null;
				e.printStackTrace();
			}    
		}		
}
