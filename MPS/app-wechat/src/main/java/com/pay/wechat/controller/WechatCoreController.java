package com.pay.wechat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;


import com.pay.wechat.service.CoreService;
import com.pay.wechat.util.MessageUtil;
import com.pay.wechat.util.SignUtil;


/**
 * 核心请求处理类
 * 
 */
public class WechatCoreController extends MultiActionController {
	
	private static Log log = LogFactory.getLog(WechatCoreController.class) ;
	
	//private String toEmployeeBind ;
	
	private CoreService coreService ;
	
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	public void doServerResp(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		/**
		 * 确认请求来自微信服务器
		 */
		
		if(null != request.getParameter("signature")){
			
			if("GET".equals(request.getMethod())){
				log.info("进入coreController get...");
				// 微信加密签名
				String signature = request.getParameter("signature");
				log.info("微信加密签名＝＝＝>>>>" + signature) ;
				// 时间戳
				String timestamp = request.getParameter("timestamp");
				// 随机数
				String nonce = request.getParameter("nonce");
				// 随机字符串
				String echostr = request.getParameter("echostr");
				response.setContentType("text/html;charset=utf-8");
				PrintWriter out = response.getWriter();
				// 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
				if (SignUtil.checkSignature(signature, timestamp, nonce)) {
					out.print(echostr);
				}
				out.close();
				out = null;
			}
			
			/*
			 * 处理微信服务器发来的消息
			 */
			else if("POST".equals(request.getMethod())){
				//将请求、响应的编码均设置为UTF-8(防止中文乱码)
				request.setCharacterEncoding("UTF-8");
				response.setCharacterEncoding("UTF-8");
				
				//调用核心业务类接收消息、处理消息
				String respMessage = coreService.processRequest(request) ;
				
				//响应消息
				PrintWriter out = response.getWriter() ;
				out.print(respMessage);
				out.close(); 
			}
			
		}
		
		
	}

//	//到达员工绑定页面
//	public ModelAndView toEmployeeBind(HttpServletRequest request, HttpServletResponse response){
//		
//		try {
//			Map<String, String> requestMap = MessageUtil.parseXml(null) ;
//			
//			//发送方账号（一个openID）
//			String fromUserName = requestMap.get("FromUserName") ;
//			System.out.println("fromUserName:" + fromUserName);
//			//是否已绑定检查
//			
//			//
//			
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return new ModelAndView(toEmployeeBind) ;
//	}
	
	//=====================================================
//	public void setToEmployeeBind(String toEmployeeBind) {
//		this.toEmployeeBind = toEmployeeBind;
//	}
	
	public void setCoreService(CoreService coreService) {
		this.coreService = coreService;
	}
	
}
