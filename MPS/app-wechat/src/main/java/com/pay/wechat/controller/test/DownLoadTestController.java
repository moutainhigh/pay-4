/**
 * 
 */
package com.pay.wechat.controller.test;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * @author PengJiangbo
 *
 */
public class DownLoadTestController extends MultiActionController {

	private static final String DOWN_LOAD_EXCEL = "EXCEL下载测试" ;
	
	
	public ModelAndView toDownPage(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView("/wechat/z_downpage") ;
	}
	
	public ModelAndView down(HttpServletRequest request, HttpServletResponse response){
		
		try {
			String fileName = this.DOWN_LOAD_EXCEL ;
			// 设置下载格式
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Cache-Control",
					"must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			String agent = request.getHeader("User-Agent");
			if (agent.contains("MSIE")) {
				response.setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
			} else {
				response.setHeader(
						"Content-Disposition",
						"attachment;filename="
								+ new String((fileName + ".xls")
										.getBytes("UTF-8"), "ISO8859_1"));
			}
			
			return new ModelAndView("/wechat/z_download_test") ;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
}
