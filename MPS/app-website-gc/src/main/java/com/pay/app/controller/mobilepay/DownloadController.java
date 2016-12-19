package com.pay.app.controller.mobilepay;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.common.helper.MessageConvertFactory;

public class DownloadController extends MultiActionController{
	private String downloadView;
	
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(downloadView);
	}
	
	
	public ModelAndView download(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String path = MessageConvertFactory.getMessage("mobilepay.setup.win.path");
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
	

	public void setDownloadView(String downloadView) {
		this.downloadView = downloadView;
	}

	
	
	
}
