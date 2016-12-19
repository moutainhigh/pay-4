package com.pay.app.controller.base.payedit;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.common.helper.MessageConvertFactory;



public class PayEditManageController extends MultiActionController {
	
	private static final String OS_WIN = "Windows";
	private static final String OS_MAC = "Macintosh";
	
	private static final String BROWSER_IE = "MSIE";
	private static final String BROWSER_FIREFOX = "Firefox";

	public ModelAndView downloadpayEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String agent = request.getHeader("User-Agent");
//		StringTokenizer st = new StringTokenizer(agent,";");
//		st.nextToken();
//		String userbrowser = st.nextToken();
//		String useros = st.nextToken().toLowerCase(); payedit.setup.MSIE.path
		String browser = BROWSER_IE;
		StringBuffer prefix = new StringBuffer("payedit.setup");
		
		
		try{
			if(agent.indexOf(OS_WIN)!=-1){
				//windows系统
				prefix.append(".").append("win");
			}else if(agent.indexOf(OS_MAC)!=-1){
				//mac系统
				prefix.append(".").append("win");
			}else{
				prefix.append(".").append("win");
			}
		}catch(Exception e){
			prefix.append(".").append("win");
		}
		
		try{
			if(agent.indexOf(BROWSER_IE)!=-1){
				//ie浏览器
				browser = BROWSER_IE;
			}
			else if(agent.indexOf(BROWSER_FIREFOX)!=-1){
				//firefox浏览器
				browser = BROWSER_FIREFOX;
			}
			else{
				browser = BROWSER_IE;
			}
		}catch(Exception e){
			browser = BROWSER_IE;
		}
		
		
		String path = MessageConvertFactory.getMessage(prefix.append(".").append(browser).append(".").append("path").toString());
		
		String filename = path.substring(path.lastIndexOf("/")+1);

		InputStream fis=this.getClass().getClassLoader().getResourceAsStream(path);
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		try{
			int fileLength = fis.available();
	        byte[] buffer = new byte[fileLength];
	        fis.read(buffer);
	        fis.close();
	        // 清空response
	        response.reset();
	        // 设置response的Header
	        response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
	        response.addHeader("Content-Length", String.valueOf(fileLength));
	        response.setContentType("application/octet-stream");
	        toClient.write(buffer);
	        toClient.flush();
		}finally{
			toClient.close();
		}
		return null;	
	}
	
	public ModelAndView downloadpayCertEdit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String agent = request.getHeader("User-Agent");
//		StringTokenizer st = new StringTokenizer(agent,";");
//		st.nextToken();
//		String userbrowser = st.nextToken();
//		String useros = st.nextToken().toLowerCase(); payedit.setup.MSIE.path
		String browser = BROWSER_IE;
		StringBuffer prefix = new StringBuffer("payedit.cert.setup");
		
		
		try{
			if(agent.indexOf(OS_WIN)!=-1){
				//windows系统
				prefix.append(".").append("win");
			}else if(agent.indexOf(OS_MAC)!=-1){
				//mac系统
				prefix.append(".").append("win");
			}else{
				prefix.append(".").append("win");
			}
		}catch(Exception e){
			prefix.append(".").append("win");
		}
		
		try{
			if(agent.indexOf(BROWSER_IE)!=-1){
				//ie浏览器
				browser = BROWSER_IE;
			}
			else if(agent.indexOf(BROWSER_FIREFOX)!=-1){
				//firefox浏览器
				browser = BROWSER_FIREFOX;
			}
			else{
				browser = BROWSER_IE;
			}
		}catch(Exception e){
			browser = BROWSER_IE;
		}
		
		
		String path = MessageConvertFactory.getMessage(prefix.append(".").append(browser).append(".").append("path").toString());
		
		String filename = path.substring(path.lastIndexOf("/")+1);

		InputStream fis=this.getClass().getClassLoader().getResourceAsStream(path);
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		try{
			int fileLength = fis.available();
	        byte[] buffer = new byte[fileLength];
	        fis.read(buffer);
	        fis.close();
	        // 清空response
	        response.reset();
	        // 设置response的Header
	        response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
	        response.addHeader("Content-Length", String.valueOf(fileLength));
	        response.setContentType("application/octet-stream");
	        toClient.write(buffer);
	        toClient.flush();
		}finally{
			toClient.close();
		}
		return null;	
	}
	
}