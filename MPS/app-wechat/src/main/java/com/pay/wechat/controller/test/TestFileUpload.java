/**
 * 
 */
package com.pay.wechat.controller.test;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;

/**
 * @author PengJiangbo
 *
 */
public class TestFileUpload extends MultiActionController {
	
	/**
	 * 到达上传页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toUploadPage(HttpServletRequest request, HttpServletResponse response){
		System.out.println("=============index===============");
		return new ModelAndView("/wechat/five_certificates") ;
	}
	
	
	public ModelAndView doFormFile(HttpServletRequest request, HttpServletResponse response) throws IllegalStateException, IOException{
		
		String test = request.getParameter("hexNotTest") ;
		System.out.println("hexNotTest====>>>" + test);
		String memberCode = "" ;
		LoginSession loginSession = SessionHelper.getLoginSession();
		if(null != loginSession){
			memberCode = loginSession.getMemberCode() ;
		}else{	//非法请求路径
			memberCode = "nosessionPath" ;
		}
		
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //记录上传过程起始时的时间，用来计算上传时间  
                int pre = (int) System.currentTimeMillis();  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){  
                    //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){  
                        System.out.println(myFileName);  
                        //重命名上传后的文件名  
                        //String fileName = "demoUpload" + file.getOriginalFilename();  
                        String fileName = UUID.randomUUID().toString().replace("-", "") + "_" + file.getOriginalFilename() ;
                        //定义上传路径  
                        //String path = "E:/" + fileName;
                        String realPath = request.getSession().getServletContext().getRealPath(File.separator + "upload") ;
                        String path = realPath + File.separator +  memberCode + File.separator + fileName ;
                        //父级目录不存在，创建父级目录
                        if(!new File(path).getParentFile().exists()){
                        	new File(path).mkdirs() ;
                        }
                        File localFile = new File(path);  
                        file.transferTo(localFile);  
                    }  
                }  
                //记录上传该文件后的时间  
                int finaltime = (int) System.currentTimeMillis();  
                System.out.println(finaltime - pre);  
            }  
              
        } 
        System.out.println("====success==========................................");
        //return "/success"; 
		
		return null ;
		//return new ModelAndView() ;
	}
}
