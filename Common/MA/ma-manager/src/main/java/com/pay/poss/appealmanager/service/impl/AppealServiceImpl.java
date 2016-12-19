package com.pay.poss.appealmanager.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

import com.pay.poss.appealmanager.dao.IAppealDao;
import com.pay.poss.appealmanager.dto.AppealDto;
import com.pay.poss.appealmanager.dto.AppealTaskListDto;
import com.pay.poss.appealmanager.dto.AppealTaskSearchDto;
import com.pay.poss.appealmanager.model.Appeal;
import com.pay.poss.appealmanager.model.AppealHistory;
import com.pay.poss.appealmanager.service.IAppealService;
import com.pay.poss.base.exception.PossException;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;
import com.pay.util.DateUtil;
/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		AppealServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-12-19		gungun_zhang			Create
 */
public class AppealServiceImpl implements IAppealService{
	private Log log = LogFactory.getLog(AppealServiceImpl.class);
	private IAppealDao appealDao;
	
	
	@Override
	public String insertAppealPictureValidate(Map<String, MultipartFile> multipartFileMap) {
		String validateMessage = "";
		MultipartFile imgFile1  =  multipartFileMap.get("picture1"); 
		MultipartFile imgFile2  =  multipartFileMap.get("picture2"); 
		MultipartFile imgFile3  =  multipartFileMap.get("picture3"); 
		MultipartFile imgFile4  =  multipartFileMap.get("picture4"); 
		MultipartFile imgFile5  =  multipartFileMap.get("picture5"); 
		MultipartFile imgFile6  =  multipartFileMap.get("picture6"); 
		
		String[] allowedContentTypes = new String[] {"image/pjpeg", "image/jpeg","image/jpg","image/gif","image/x-png","image/bmp" }; 
		
		if(imgFile1!=null&&imgFile1.getSize()>0){
			if (imgFile1.getSize() > 1024*1024*2)  {
				validateMessage = "图片资料1不能大于2M &nbsp;&nbsp;";
			}
			if (!ArrayUtils.contains(allowedContentTypes, imgFile1.getContentType())){
				validateMessage = validateMessage + "图片资料1格式只能为.jpg.gif.png.bmp <br>";        
			} 
		}
		if(imgFile2!=null&&imgFile2.getSize()>0){
			if (imgFile2.getSize() > 1024*1024*2)  {
				validateMessage = validateMessage + "&nbsp;图片资料2不能大于2M&nbsp;&nbsp;";
			} 
			if (!ArrayUtils.contains(allowedContentTypes, imgFile2.getContentType())){
				validateMessage = validateMessage + "图片资料2格式只能为.jpg.gif.png.bmp <br>";        
			} 
		}
		if(imgFile3!=null&&imgFile3.getSize()>0){
			if (imgFile3.getSize() > 1024*1024*2)  {
				validateMessage = validateMessage + "&nbsp;图片资料3不能大于2M&nbsp;&nbsp;";
			} 
			if (!ArrayUtils.contains(allowedContentTypes, imgFile3.getContentType())){
				validateMessage = validateMessage + "图片资料3格式只能为.jpg.gif.png.bmp <br>";        
			} 
		}
		if(imgFile4!=null&&imgFile4.getSize()>0){
			if (imgFile4.getSize() > 1024*1024*2)  {
				validateMessage = validateMessage + "&nbsp;图片资料4不能大于2M&nbsp;&nbsp;";
			} 
			if (!ArrayUtils.contains(allowedContentTypes, imgFile4.getContentType())){
				validateMessage = validateMessage + "图片资料4格式只能为.jpg.gif.png.bmp <br>";        
			} 
		}
		if(imgFile5!=null&&imgFile5.getSize()>0){
			if (imgFile5.getSize() > 1024*1024*2)  {
				validateMessage = validateMessage + "&nbsp;图片资料5不能大于2M&nbsp;&nbsp;";
			} 
			if (!ArrayUtils.contains(allowedContentTypes, imgFile5.getContentType())){
				validateMessage = validateMessage + "图片资料5格式只能为.jpg.gif.png.bmp <br>";        
			} 
		}
		if(imgFile6!=null&&imgFile6.getSize()>0){
			if (imgFile6.getSize() > 1024*1024*2)  {
				validateMessage = validateMessage + "&nbsp;图片资料6不能大于2M&nbsp;&nbsp;";
			} 
			if (!ArrayUtils.contains(allowedContentTypes, imgFile6.getContentType())){
				validateMessage = validateMessage + "图片资料6格式只能为.jpg.gif.png.bmp<br>";        
			} 
		}
	
		return validateMessage;
	}
	@Override
	public String insertAppealTrans(AppealDto appealDto ,Map<String,MultipartFile> multipartFileMap) throws PossException {
		log.debug("AppealServiceImpl.insertAppealTrans is running...");
		String appealCode = null ;
		try{
			appealCode = this.getAppealCode();	
			appealDto.setAppealCode(appealCode);
			Long appealId = this.insertAppeal(appealDto);	
			appealDto.setAppealId(appealId.toString());
			this.insertAppealHistory(appealDto);
			this.insertAppealPicture(appealCode, multipartFileMap);
			
		}catch(Exception e){
			log.error("AppealServiceImpl.insertAppealTrans");
			e.printStackTrace();
		}    
		            
		
		return appealCode ;
	}
	private void insertAppealPicture(String appealCode ,Map<String,MultipartFile> multipartFileMap) throws Exception  {
		//上传图片

		
		MultipartFile imgFile1  =  multipartFileMap.get("picture1"); 
		MultipartFile imgFile2  =  multipartFileMap.get("picture2"); 
		MultipartFile imgFile3  =  multipartFileMap.get("picture3"); 
		MultipartFile imgFile4  =  multipartFileMap.get("picture4"); 
		MultipartFile imgFile5  =  multipartFileMap.get("picture5"); 
		MultipartFile imgFile6  =  multipartFileMap.get("picture6"); 
		
		String appealPath = "/opt/upload/ma/appeal/"+appealCode;
		String appealPathNew = null;
		if((imgFile1!=null&&imgFile1.getSize()>0)||(imgFile2!=null&&imgFile2.getSize()>0)
				||(imgFile3!=null&&imgFile3.getSize()>0)||(imgFile4!=null&&imgFile4.getSize()>0)
				||(imgFile6!=null&&imgFile6.getSize()>0)||(imgFile5!=null&&imgFile5.getSize()>0)
			){
			appealPathNew = this.creatFolder(appealPath);
		}else{
			return ;
		}
		
		if(imgFile1!=null&&imgFile1.getSize()>0){
			this.uploadPicture(imgFile1.getInputStream(),appealPathNew,"picture1.jpg");
		}
		if(imgFile2!=null&&imgFile2.getSize()>0){
			this.uploadPicture(imgFile2.getInputStream(),appealPathNew,"picture2.jpg");
		}
		if(imgFile3!=null&&imgFile3.getSize()>0){
			this.uploadPicture(imgFile3.getInputStream(),appealPathNew,"picture3.jpg");
		}
		if(imgFile4!=null&&imgFile4.getSize()>0){
			this.uploadPicture(imgFile4.getInputStream(),appealPathNew,"picture4.jpg");
		}
		if(imgFile5!=null&&imgFile5.getSize()>0){
			this.uploadPicture(imgFile5.getInputStream(),appealPathNew,"picture5.jpg");
		}
		if(imgFile6!=null&&imgFile6.getSize()>0){
			this.uploadPicture(imgFile6.getInputStream(),appealPathNew,"picture6.jpg");
		}	
		
	}
	//创建文件夹
	private String creatFolder(String srcPath) { 
		File srcFolderMa = new File("/opt/upload/ma");          	
        if(!srcFolderMa.exists()) {                                                                                           
        	srcFolderMa.mkdir();           	
        } 
		File srcFolderAppeal = new File("/opt/upload/ma/appeal");          	
        if(!srcFolderAppeal.exists()) {                                                                                           
        	srcFolderAppeal.mkdir();           	
        } 
        File srcFolder = new File(srcPath);          	
        if(!srcFolder.exists()) {                                                                                           
        	srcFolder.mkdir();           	
        }   
        return srcPath+"/";    
   }
		

	//上传图片文件
	private void uploadPicture(InputStream stream,String path,String filename) throws IOException    {          
        FileOutputStream fs=new FileOutputStream( path + filename);    
        byte[] buffer =new byte[1024*1024*2];    
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
	
		
	private Long insertAppeal(AppealDto appealDto){
		Appeal appeal = new Appeal();
		appeal.setAppealCode(appealDto.getAppealCode());	
		appeal.setIsNeedReply(Integer.valueOf(appealDto.getIsNeedReply()));
		appeal.setIsUrgency(Integer.valueOf(appealDto.getIsUrgency()));
		appeal.setCallPhone(appealDto.getCallPhone());
		appeal.setLinkPhone(appealDto.getLinkPhone());
		appeal.setLinkEmail(appealDto.getLinkEmail());
		appeal.setCustomerName(appealDto.getCustomerName());
		if(StringUtils.isNotEmpty(appealDto.getOccurTime())){
			appeal.setOccurTime(DateUtil.strToDate(appealDto.getOccurTime(), "yyyy-MM-dd"));
		}
		appeal.setAppealSourceCode(appealDto.getAppealSourceCode());
		appeal.setAppealBody(appealDto.getAppealBody());
		appeal.setAppealStatusCode(appealDto.getAppealStatusCode());
		Date date = new Date();
		appeal.setCreateTime(date);
		appeal.setUpdateTime(date);	
		return this.appealDao.insertAppeal(appeal);
		
	}
	private void insertAppealHistory(AppealDto appealDto){
		AppealHistory appealHistory = new AppealHistory();
		
		appealHistory.setAppealId(Long.valueOf(appealDto.getAppealId()));
		appealHistory.setAppealStatusCode(appealDto.getAppealStatusCode());
		appealHistory.setOperatorId(Long.valueOf(appealDto.getOperatorId()));
		if(StringUtils.isNotEmpty(appealDto.getOperatorDeptCode())){
			appealHistory.setOperatorDeptCode(Long.valueOf(appealDto.getOperatorDeptCode()));
		}
		appealHistory.setRemark(appealDto.getRemark());
		appealHistory.setCreateTime(new Date());
		
		this.appealDao.insertAppealHistory(appealHistory);
	}
	

	@Override
	public void updateAppealTrans(AppealDto appealDto) throws PossException {
		log.debug("AppealServiceImpl.updateAppealTrans is running...");
		try{			
			this.updateAppeal(appealDto);
			this.insertAppealHistory(appealDto);
		}catch(Exception e){
			log.error("AppealServiceImpl.updateAppealTrans is error...");
			e.printStackTrace();
			throw new PossException("申诉状态更新失败!", ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
			
		}
		
		
	}
	

	@Override
	public String isAppealStateValidate(String appealId, String appealState) {
		if("dispense".equals(appealState)){
			return this.appealDao.vaildateDispense(appealId);
		}else if("dept".equals(appealState)){
			return this.appealDao.vaildateDept(appealId);
		}else if("callBack".equals(appealState)){
			return this.appealDao.vaildateCallBack(appealId);
		}else if("finish".equals(appealState)){
			return this.appealDao.vaildateFinish(appealId);
		}else{
			return "false";
		}
		
	}
	@Override
	public List<AppealHistory> getAppealHistoryByAppealId(String appealId) {
		
		return this.appealDao.getAppealHistoryByAppealId(Long.valueOf(appealId));
	}
	private void updateAppeal(AppealDto appealDto){
		this.appealDao.updateAppeal(appealDto);
	}
	@Override
	public AppealDto getAppealById(String appealId) {
		return this.appealDao.getAppealById(Long.valueOf(appealId));	
	}
	@Override
	public List<AppealTaskListDto> getAppealListForDispense(AppealTaskSearchDto appealTaskSearchDto) {
		return this.appealDao.getAppealListForDispense(appealTaskSearchDto);
	}
	@Override
	public List<AppealTaskListDto> getAppealListForCallBack(
			AppealTaskSearchDto appealTaskSearchDto) {

		return this.appealDao.getAppealListForCallBack(appealTaskSearchDto);
	}
	@Override
	public List<AppealTaskListDto> getAppealListForDept(
			AppealTaskSearchDto appealTaskSearchDto) {
		return this.appealDao.getAppealListForDept(appealTaskSearchDto);
	}
	@Override
	public List<AppealTaskListDto> getAppealListForFinish(
			AppealTaskSearchDto appealTaskSearchDto) {
		return this.appealDao.getAppealListForFinish(appealTaskSearchDto);
	}
	
	@Override
	public List<AppealTaskListDto> getAppealListForSearch(
			AppealTaskSearchDto appealTaskSearchDto) {
		return this.appealDao.getAppealListForSearch(appealTaskSearchDto);
	}
	@Override
	public Integer getAppealListForSearchCount(
			AppealTaskSearchDto appealTaskSearchDto) {
		return this.appealDao.getAppealListForSearchCount(appealTaskSearchDto);
	}
	@Override
	public Integer getAppealListForDispenseCount() {		
		return this.appealDao.getAppealListForDispenseCount();
	}
	
	@Override
	public Integer getAppealListForCallBackCount(
			AppealTaskSearchDto appealTaskSearchDto) {
		return this.appealDao.getAppealListForCallBackCount(appealTaskSearchDto);
	}
	@Override
	public Integer getAppealListForDeptCount(
			AppealTaskSearchDto appealTaskSearchDto) {
		return this.appealDao.getAppealListForDeptCount(appealTaskSearchDto);
	}
	@Override
	public Integer getAppealListForFinishCount(
			AppealTaskSearchDto appealTaskSearchDto) {
		return this.appealDao.getAppealListForFinishCount(appealTaskSearchDto);
	}
	@Override
	public String getAppealCode() {
		
		return this.appealDao.getAppealCode();
	}
	public void setAppealDao(IAppealDao appealDao) {
		this.appealDao = appealDao;
	}
	
}
