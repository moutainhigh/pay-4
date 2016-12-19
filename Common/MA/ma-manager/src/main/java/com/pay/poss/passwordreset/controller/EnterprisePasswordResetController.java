/**
 * 
 */
package com.pay.poss.passwordreset.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.common.SessionUserUtils;
import com.pay.poss.base.common.constants.SendMailConstants;
import com.pay.poss.passwordreset.dto.PasswordResetDto;
import com.pay.poss.passwordreset.service.PasswordResetService;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.util.SpringControllerUtils;

/**
 * @Description 企业会员密码重置管理
 * @project 	ma-membermanager
 * @file 		EnterprisePasswordResetController.java 
 * @note		<br>
 * @develop		JDK1.6 + SpringSource 2.3.2
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-14		tianqing_wang			Create
 */
public class EnterprisePasswordResetController extends MultiActionController {
	private Log log = LogFactory.getLog(EnterprisePasswordResetController.class);
	
	private String enterpriseCreateView;
	private String enterpriseSearch;
	private String enterpriseSearchList;
	private String enterpriseSearchDetail;
	private String enterpriseHistorySearch;
	private String enterpriseHistorySearchList;
	private String enterprisePasswordResetDispose;
	
	
	private PasswordResetService passwordResetService;
	public ModelAndView index(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        return null;
    }
	
	//企业会员密码重置创建
	public ModelAndView enterpriseCreate(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		return new ModelAndView(enterpriseCreateView);
	}
	//企业会员密码重置创建保存
	public ModelAndView enterpriseCreateSave(HttpServletRequest request,
        HttpServletResponse response,PasswordResetDto dto)throws Exception{
		
		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
		MultipartFile imgFile1  =  multipartRequest.getFile("proposerObverseUrlP"); //经办人身份证正面
		MultipartFile imgFile2  =  multipartRequest.getFile("proposerReverseurlP"); //经办人身份证背面
		MultipartFile imgFile3  =  multipartRequest.getFile("legalObverseUrlP"); //法人身份证正面
		MultipartFile imgFile4  =  multipartRequest.getFile("legalReverseUrlP"); //法人身份证背面
		MultipartFile imgFile5  =  multipartRequest.getFile("licenceUrlP"); //公司营业执照正本复印件
		MultipartFile imgFile6  =  multipartRequest.getFile("formUrlP"); //申请表影像
		
		String loginName = dto.getLoginName();
		String oldPath = "/opt/upload/";
		String maPath = creatFolder(oldPath,"ma","passwordreset");
		String path = maPath+"/";
		Calendar c = Calendar.getInstance();
   	 	SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhh");
        String thisDateFile = f.format(c.getTime());		//每个loginName下的日期文件夹名称
        
		//判断文件夹是否存在(不存在创建并返回文件夹路径)
		String newPath = creatFolder(path,loginName,thisDateFile);
		 //组装写入数据库的值
        String setUrl = path+loginName+"/"+thisDateFile;
		//在新文件夹下创建图片文件
		
        try {   
        	if(imgFile1.getOriginalFilename()!=null && !"".equals(imgFile1.getOriginalFilename())){
        		String proposerObverseUrlP = trimType(imgFile1.getOriginalFilename(),"proposerObverseUrlP"); 
        		SaveFileFromInputStream(imgFile1.getInputStream(),newPath,proposerObverseUrlP);   
        		dto.setProposerObverseUrl(setUrl+"/"+proposerObverseUrlP);
        	}
        	if(imgFile2.getOriginalFilename()!=null && !"".equals(imgFile2.getOriginalFilename())){
        		String proposerReverseurlP = trimType(imgFile2.getOriginalFilename(),"proposerReverseurlP");
        		SaveFileFromInputStream(imgFile2.getInputStream(),newPath,proposerReverseurlP);
        		dto.setProposerReverseurl(setUrl+"/"+proposerReverseurlP);
        	}
        	if(imgFile3.getOriginalFilename()!=null && !"".equals(imgFile3.getOriginalFilename())){
        		String legalObverseUrlP = trimType(imgFile3.getOriginalFilename(),"legalObverseUrlP");
        		SaveFileFromInputStream(imgFile3.getInputStream(),newPath,legalObverseUrlP);
        		dto.setLegalObverseUrl(setUrl+"/"+legalObverseUrlP);
        	}
    		if(imgFile4.getOriginalFilename()!=null && !"".equals(imgFile4.getOriginalFilename())){
    			String legalReverseUrlP = trimType(imgFile4.getOriginalFilename(),"legalReverseUrlP");
    			SaveFileFromInputStream(imgFile4.getInputStream(),newPath,legalReverseUrlP);
    			 dto.setLegalReverseUrl(setUrl+"/"+legalReverseUrlP);
    		}  
    		if(imgFile5.getOriginalFilename()!=null && !"".equals(imgFile5.getOriginalFilename())){
    			String licenceUrlP = trimType(imgFile5.getOriginalFilename(),"licenceUrlP");
    			SaveFileFromInputStream(imgFile5.getInputStream(),newPath,licenceUrlP);
    			dto.setLicenceUrl(setUrl+"/"+licenceUrlP);
    		}  
    		String formUrlP = trimType(imgFile6.getOriginalFilename(),"formUrlP");  
            SaveFileFromInputStream(imgFile6.getInputStream(),newPath,formUrlP);
            dto.setFormUrl(setUrl+"/"+formUrlP);
            
        } catch (IOException e) {    
            System.out.println(e.getMessage());    
            log.error(e);
            return null;    
        }    
        dto.setStatus(1);
        SessionUserHolder user = SessionUserUtils.getUserInfo(request);
        dto.setCreator(user.getUsername());
        dto.setModifier(user.getUsername());
        String message = null;
        String sucess = null;
        PasswordResetDto queryDto = passwordResetService.queryMemberCodeByLoginName(dto.getLoginName());
        if(queryDto!=null){
        	passwordResetService.createEnterprisePasswordReset(dto);
        	sucess = "操作已成功";
        }else{
        	message = "你输入的会员登录号找不到对应的会员";
        }
        return new ModelAndView(enterpriseCreateView)
		        .addObject("message",message)
		        .addObject("sucess",sucess);
	}

	//企业会员密码重置查询页面
	public ModelAndView enterpriseSearch(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
        return new ModelAndView(enterpriseSearch);
    }
	//企业会员密码重置查询列表list显示
	@SuppressWarnings("unchecked")
	public ModelAndView enterpriseSearchList(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		Page  page = PageUtils.getPage(request);
		//dto.setStatus(1);
    	List<PasswordResetDto> info =  passwordResetService.queryEnterprisePasswordReset(dto,page);
    	page.setResult(info);
        return new ModelAndView(enterpriseSearchList).addObject("page", page);
    }
	//企业会员密码重置详情(初审)显示
	public ModelAndView enterpriseSearchDetail(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
	    //因为详情跟列表共用一个SQL 所以在此要设置分页的2个值
		dto.setPageStartRow(0);
		dto.setPageEndRow(1);
		PasswordResetDto resultDto =  passwordResetService.queryEnterprisePasswordResetById(dto);
		
        return new ModelAndView(enterpriseSearchDetail).addObject("resultDto", resultDto);
    }
	//企业会员密码重置详情(初审)确认(初审成功)
	public ModelAndView enterpriseSearchDetailSave(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		String message =null;
		SessionUserHolder user = SessionUserUtils.getUserInfo(request);
		dto.setStatus(2); //已审核
		dto.setModifier(user.getUsername());
		passwordResetService.updateEnterprisePasswordReset(dto);
		dto.setPageStartRow(0);
		dto.setPageEndRow(1);
		PasswordResetDto resultDto =  passwordResetService.queryEnterprisePasswordResetById(dto);
		message = "操作已成功";
        return new ModelAndView(enterpriseSearchDetail)
        			.addObject("resultDto", resultDto)
        			.addObject("message",message);
    }
	
	//密码重置处理显示页面(复审页面)审核确认,审核拒绝
	public ModelAndView disposeView(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		String typeName = dto.getTypeName();
		dto.setPageStartRow(0);
		dto.setPageEndRow(1);
		PasswordResetDto resultDto =  passwordResetService.queryEnterprisePasswordResetById(dto);
		return new ModelAndView(enterprisePasswordResetDispose)
				.addObject("resultDto", resultDto)
				.addObject("typeName",typeName);
	}
	//密码重置处理
	public ModelAndView dispose(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		SessionUserHolder user = SessionUserUtils.getUserInfo(request);
		dto.setModifier(user.getUsername());
		String typeName = dto.getTypeName();
		boolean isSuess = false;
		//判断是审核通过还是拒绝
		if(SendMailConstants.DISPOSE_CONFIRM.equals(dto.getDisposeFlag())){
			//审核通过
			isSuess = passwordResetService.passwordResetConfirmTrans(dto); 
		}else if (SendMailConstants.DISPOSE_REFUSE.equals(dto.getDisposeFlag())){  //审核拒绝
			if(SendMailConstants.TYPENAME_TYPELOGIN.equals(dto.getTypeName())){
				dto.setStatus(4);   //审核登录密码失败
				passwordResetService.updateEnterprisePasswordReset(dto);
			}else if(SendMailConstants.TYPENAME_TYPEPAY.equals(dto.getTypeName())){
				dto.setStatus(6);	 //审核支付密码失败
				passwordResetService.updateEnterprisePasswordReset(dto);
			}
		}
		//操作完成后再返回到本页面
		PasswordResetDto resultDto = new PasswordResetDto();
		resultDto.setId(dto.getId());
		resultDto.setPageStartRow(0);
		resultDto.setPageEndRow(1);
		resultDto =  passwordResetService.queryEnterprisePasswordResetById(resultDto);
		String show = "";
		if(isSuess==false) show = "NO";
		if(isSuess==true) show = "YES";
		return new ModelAndView(enterprisePasswordResetDispose)
			.addObject("resultDto", resultDto)
			.addObject("typeName",typeName)
			.addObject("show",show);
	}
	//密码重置处理(历史查询里边的重新发送)
	public ModelAndView disposeReset(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		dto.setPageStartRow(0);
		dto.setPageEndRow(1);
		PasswordResetDto resultDto =  passwordResetService.queryEnterprisePasswordResetById(dto);
		boolean isSuess = false;
		if(resultDto!=null){
			if(resultDto.getPasswordType()==1){
				resultDto.setTypeName(SendMailConstants.TYPENAME_TYPELOGIN);
			}else if(resultDto.getPasswordType()==2){
				resultDto.setTypeName(SendMailConstants.TYPENAME_TYPEPAY);
			}
		}
		isSuess = passwordResetService.passwordResetConfirmTrans(resultDto);
		return new ModelAndView(enterpriseHistorySearch)
				.addObject("dto", resultDto);
				//.addObject("show",show); //操作完后再回到历史页面 
	}
	//历史查询
	public ModelAndView enterpriseHistorySearch(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
        return new ModelAndView(enterpriseHistorySearch)
		        .addObject("dto", dto);
	}
	
	//历史查询列表
	@SuppressWarnings("unchecked")
	public ModelAndView enterpriseHistorySearchList(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		Page  page = PageUtils.getPage(request);
    	List<PasswordResetDto> info =  passwordResetService.queryEnterprisePasswordReset(dto,page);
    	if(info!=null){
    		Date thisDate = new Date();
    		for (PasswordResetDto newDto:info){
    			Date gmtModified = newDto.getGmtModified();
    			long diff = thisDate.getTime() - gmtModified.getTime();
    			long days = diff / (1000 * 60 * 60 );
    			if(days>24){
    				newDto.setHours24("YES");//大于24小时不显示重发按钮
    			}else{
    				newDto.setHours24("NO");
    			}
    		}
    	}
    	page.setResult(info);
		return new ModelAndView(enterpriseHistorySearchList)
				.addObject("page", page);
	}
	
	//异步检查校验(会员,手机)
	public ModelAndView checkLoginName(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		if(dto!=null){
			if(null!=dto.getLoginName() && !"".equals(dto.getLoginName())){
				PasswordResetDto queryDto = passwordResetService.queryMemberCodeByLoginName(dto.getLoginName());
				if(queryDto == null){
					SpringControllerUtils.renderText(response, "1");  //该会员不存在
				}else{
					//SpringControllerUtils.renderText(response, "2");
					if(queryDto.getStatus() == 3){
						SpringControllerUtils.renderText(response, "3");//该会员未激活
					}else{
						SpringControllerUtils.renderText(response, "2");//该会员存在状态正常
					}
				}
			}
			if(null!=dto.getMobile() && !"".equals(dto.getMobile())){
				Pattern p =Pattern.compile("^(13|14|15|18)\\d{9}$");
		        Matcher matcher = p.matcher(dto.getMobile());
		        if (matcher.matches()) {
		        	SpringControllerUtils.renderText(response, "true");
		        }
		        SpringControllerUtils.renderText(response, "false");
			}
		}
		
		return null;
	}
	
	
	
	
	
	//个人身份证正面
	public ModelAndView proposerObverseUrl(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		download(response,dto.getProposerObverseUrl(),"111");
		return null;
	}
	//个人身份证背面
	public ModelAndView proposerReverseurl(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		download(response,dto.getProposerReverseurl(),"222");
		return null;
	}
	//法人身份证正面
	public ModelAndView legalObverseUrl(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		download(response,dto.getLegalObverseUrl(),"333");
		return null;
	}
	//法人身份证背面
	public ModelAndView legalReverseUrl(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		download(response,dto.getLegalReverseUrl(),"444");
		return null;
	}
	//公司营业执照正本复印件
	public ModelAndView licenceUrl(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		download(response,dto.getLicenceUrl(),"555");
		return null;
	}
	//申请表影像
	public ModelAndView formUrl(HttpServletRequest request,
            HttpServletResponse response,PasswordResetDto dto) throws Exception {
		download(response,dto.getFormUrl(),"666");
		return null;
	}
	/**
	 * 下载文件专用类
	 * @param response　HttpServletResponse
	 * @param srcFile　文件在本地的绝对地址
	 * @param newFileName　下载时的客户端文件名
	 * @throws Exception 　异常，所有的异常将抛出
	 */
	public static void download(HttpServletResponse response,String srcFile,String newFileName ) throws Exception {
		if(null!=srcFile && !"".equals(srcFile)){
			response.setContentType("image/jpeg");    
			response.setCharacterEncoding("UTF-8");
			
			String fileName  =  FilenameUtils.getName(srcFile); 
			String suffex = fileName.substring(fileName.lastIndexOf("."));
			String filedisplay = URLEncoder.encode(newFileName+suffex,"UTF-8");
			response.addHeader("Content-Disposition","attachment;filename=" + filedisplay);    
			OutputStream outp = null;    
			FileInputStream in = null;    
			outp = response.getOutputStream();    
			in = new FileInputStream(srcFile);    
			byte[] b = new byte[2048];    
			int i = 0;    
			while((i = in.read(b)) > 0)    
			{    
			   outp.write(b, 0, i);    
			}    
			outp.flush();    
			outp.close();
		}
		/*Image image = null;
		try {
			File file = new File("image.gif");
	        image = ImageIO.read(file);
		} catch (IOException e) {
	    }*/

	}
	//创建文件夹
	public static String creatFolder(String srcPath,String newFile,String thisDateFile) {  
        File file = null;   
        File srcFolder = new File(srcPath);          		 //一级文件夹  
        if(srcFolder.exists()) {                            
            File secondFolder = new File(srcFolder,newFile); //创建loginName文件夹
            if(secondFolder.exists()) {                        
                file = new File(secondFolder,thisDateFile);  //创建loginName下的日期文件夹
                file.mkdir();
            }else {                                            
                secondFolder.mkdir();   
                file = new File(secondFolder,thisDateFile);        
                file.mkdir();
            }   
        }else {                                                
        	srcFolder.mkdir();   
            File secondFolder = new File(srcFolder,newFile);   
            if(secondFolder.exists()) {                        
                file = new File(secondFolder,thisDateFile);  
                file.mkdir();
            }else {                                            
                secondFolder.mkdir();   
                file = new File(secondFolder,thisDateFile);   
                file.mkdir();
            }   
        }   
        return file.getPath();   
   }
	
	//替换图片的前缀
	public static String trimType(String filename,String newFileName) {
	    int index = filename.lastIndexOf(".");
	    return filename.replace(filename.substring(0, index), newFileName);
	}

	//创建图片文件
	public void SaveFileFromInputStream(InputStream stream,String path,String filename) throws IOException    {          
        FileOutputStream fs=new FileOutputStream( path + "/"+ filename);    
        byte[] buffer =new byte[1024*1024*5];    
        int bytesum = 0;    
        int byteread = 0;     
        while ((byteread=stream.read(buffer))!=-1)    
        {    
           bytesum+=byteread;    
           fs.write(buffer,0,byteread);    
           fs.flush();    
        }     
        fs.close();    
        stream.close();          
    }   
	
	
	
	public String getEnterpriseCreateView() {
		return enterpriseCreateView;
	}
	public void setEnterpriseCreateView(String enterpriseCreateView) {
		this.enterpriseCreateView = enterpriseCreateView;
	}

	public PasswordResetService getPasswordResetService() {
		return passwordResetService;
	}
	public void setPasswordResetService(PasswordResetService passwordResetService) {
		this.passwordResetService = passwordResetService;
	}
	public String getEnterpriseSearch() {
		return enterpriseSearch;
	}
	public void setEnterpriseSearch(String enterpriseSearch) {
		this.enterpriseSearch = enterpriseSearch;
	}
	public String getEnterpriseSearchList() {
		return enterpriseSearchList;
	}
	public void setEnterpriseSearchList(String enterpriseSearchList) {
		this.enterpriseSearchList = enterpriseSearchList;
	}
	public String getEnterpriseSearchDetail() {
		return enterpriseSearchDetail;
	}
	public void setEnterpriseSearchDetail(String enterpriseSearchDetail) {
		this.enterpriseSearchDetail = enterpriseSearchDetail;
	}
	public String getEnterpriseHistorySearch() {
		return enterpriseHistorySearch;
	}
	public void setEnterpriseHistorySearch(String enterpriseHistorySearch) {
		this.enterpriseHistorySearch = enterpriseHistorySearch;
	}
	public String getEnterpriseHistorySearchList() {
		return enterpriseHistorySearchList;
	}
	public void setEnterpriseHistorySearchList(String enterpriseHistorySearchList) {
		this.enterpriseHistorySearchList = enterpriseHistorySearchList;
	}
	public String getEnterprisePasswordResetDispose() {
		return enterprisePasswordResetDispose;
	}
	public void setEnterprisePasswordResetDispose(
			String enterprisePasswordResetDispose) {
		this.enterprisePasswordResetDispose = enterprisePasswordResetDispose;
	}
}
