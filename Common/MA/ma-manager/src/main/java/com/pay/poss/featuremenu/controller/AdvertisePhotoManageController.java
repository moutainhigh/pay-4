package com.pay.poss.featuremenu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.base.common.file.ImgFileUtil;
import com.pay.poss.base.common.file.PictureLoadView;
import com.pay.poss.featuremenu.common.WebConstants;
import com.pay.poss.featuremenu.dto.AdvertisementDto;
import com.pay.poss.featuremenu.enums.TargetsEnums;
import com.pay.poss.featuremenu.model.Advertisement;
import com.pay.poss.featuremenu.service.AdvertiseService;
import com.pay.poss.featuremenu.validate.AdvertiseValidator;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

public class AdvertisePhotoManageController extends MultiActionController{
	
	
	//初始化 首页 合作商户 管理
	private String initAdvertisePhoto;
	//新增合作商户
	private String addAdvertisePhoto;
	//编辑合作商户
	private String editAdvertisePhoto;
	//获取合作商户
	private String getAdvertisePhoto;
	//首页合作商户类表
	private String advertisePhotoList;
	//更新首页合作商户类表
	private String updateAdvertisePhoto;
	
	
	private List<Advertisement> advertiseList = null;
	
	private AdvertiseService advertiseService;
	
	private AdvertisementDto advertisementDto;
	
	public ModelAndView initAdvertisePhoto(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ModelAndView(initAdvertisePhoto);
	}
	
	public ModelAndView addAdvertisePhoto(HttpServletRequest request,
			HttpServletResponse response,AdvertisementDto advertisementDto) throws Exception {
		Integer locationCode  = 0;
		locationCode = advertiseService.getCountByLocation(TargetsEnums.MERCHANT_ADV.getCode());
		advertisementDto.setSort(locationCode + 1);
		advertisementDto.setAdvtype(WebConstants.ADV_TYPE_INNER);
		
		//上传图片
		//String filePath = StringUtils.isEmpty(fileInfo.getFilePath())?CommonConfiguration("filePath"):fileInfo.getFilePath();
//		String filePath =CommonConfiguration.getStrProperties("photoPath");
//		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
//		final CommonsMultipartFile orginalFile = (CommonsMultipartFile) multipartRequest.getFile("imgfile"); 
//		String validateFileName = orginalFile.getOriginalFilename();
//		
//		String msg = AdvertiseValidator.validateAdvertise(advertisementDto,validateFileName );
//		ModelAndView mv = new ModelAndView(addAdvertisePhoto);
//		if(msg != null){
//			mv.addObject("advMsg", msg);
//			return mv;
//		}
//		
//		String uuid = UUIDUtil.uuid();
//		Calendar c = Calendar.getInstance();
//   	 	SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhh");
//        String thisDateFile = f.format(c.getTime());
//        
//		String filename = uuid + thisDateFile;
//		FileInfoDTO fileInfoDTO = new FileInfoDTO();
//		fileInfoDTO.setFileName(filename+validateFileName);
//		FileHandler.getFileHandler().uploadFile(orginalFile.getInputStream(), fileInfoDTO);
//		advertisementDto.setImgpath(filePath+File.separator +filename+validateFileName);
		
		advertisementDto.setStarttime(DateUtil.parse("yyyy-MM-dd HH:mm:ss", advertisementDto.getStarttimeStr()));
		advertisementDto.setEndtime(DateUtil.parse("yyyy-MM-dd HH:mm:ss", advertisementDto.getEndtimeStr()));
		
		String msg = AdvertiseValidator.validateAdvertise(advertisementDto,advertisementDto.getImgpath());
		ModelAndView mv = new ModelAndView(addAdvertisePhoto);
		if(msg != null){
			mv.addObject("advMsg", msg);
			return mv;
		}
		
		advertiseService.creatAdvertise(advertisementDto);
		mv.addObject("advMsg", "保存成功");
		return mv;

	}
	

	
	public ModelAndView advertisePhotoList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer locationCode  = 0;
		String targets = request.getParameter("targets");
		Integer targetsInt;
		if(!StringUtil.isEmpty(targets)){
			targetsInt = Integer.valueOf(targets);
		}else{
			targetsInt = TargetsEnums.MERCHANT_ADV.getCode();
		}
		locationCode = advertiseService.getCountByLocation(targetsInt);
		advertiseList = advertiseService.queryAdvertiseListByTargets(targetsInt);
		
		ModelAndView mv = new ModelAndView(advertisePhotoList);
		mv.addObject("advertiseList", advertiseList);
		mv.addObject("locationCode", locationCode);
		mv.addObject("targets", targetsInt);
		return mv;
	}
	
	
	public ModelAndView up(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String idString = request.getParameter("id");
		String targets = request.getParameter("targets");
		if(idString != null){
			Long id1 = Long.valueOf(idString);
			Integer targetsInt = 0;
			if(!StringUtil.isEmpty(targets)){
				targetsInt = Integer.valueOf(targets);
			}
			advertiseService.doUpdateSortRnTx(targetsInt,id1,"UP");
		}else{
			return new ModelAndView("redirect:" + WebConstants.ADVERTISE_PHOTO_LIST_URL+targets).addObject("errormsg", "数据错误");
		}
		return new ModelAndView("redirect:" + WebConstants.ADVERTISE_PHOTO_LIST_URL+targets);
	}
	
	public ModelAndView down(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String idString = request.getParameter("id");
		String targets = request.getParameter("targets");
		if(idString != null){
			Integer targetsInt = 0;
			if(!StringUtil.isEmpty(targets)){
				targetsInt = Integer.valueOf(targets);
			}
			Long id1 = Long.valueOf(idString);
			advertiseService.doUpdateSortRnTx(targetsInt,id1,"NEXT");
		}else{
			return new ModelAndView("redirect:" + WebConstants.ADVERTISE_PHOTO_LIST_URL+targets).addObject("errormsg", "数据错误");
		}
		return new ModelAndView("redirect:" + WebConstants.ADVERTISE_PHOTO_LIST_URL+targets);
	}
	
	
	public ModelAndView getAdvertisePhoto(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String idString = request.getParameter("id");
		if(idString != null){
			Long id = Long.valueOf(idString);
			advertisementDto = advertiseService.queryAdvertiseById(id);
			advertisementDto.setStarttimeStr(DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", advertisementDto.getStarttime()));
			advertisementDto.setEndtimeStr(DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", advertisementDto.getEndtime()));
		}
		
		
		return new ModelAndView(getAdvertisePhoto).addObject("advertisementDto", advertisementDto).addObject("statusList",getStatusList());
	}
	
	
	private List<Map<String,String>> getStatusList(){
		List<Map<String,String>> list =  new ArrayList<Map<String,String>>();
		Map<String,String> map1 = new HashMap<String, String>();
		map1.put("text", "有效");
		map1.put("value", "1");
		Map<String,String> map2 = new HashMap<String, String>();
		map2.put("text", "无效");
		map2.put("value", "0");
		list.add(map1);
		list.add(map2);
		return list;
	}
	
	
	public ModelAndView updateAdvertisePhoto(HttpServletRequest request,
			HttpServletResponse response,AdvertisementDto advertisementDto) throws Exception {

		
		//上传图片
		//String filePath = StringUtils.isEmpty(fileInfo.getFilePath())?CommonConfiguration("filePath"):fileInfo.getFilePath();
//		String filePath =CommonConfiguration.getStrProperties("photoPath");
//		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
//		final CommonsMultipartFile orginalFile = (CommonsMultipartFile) multipartRequest.getFile("imgfile"); 
//
//		if(orginalFile != null){
//			String validateFileName = orginalFile.getOriginalFilename();
//			
//			String msg = AdvertiseValidator.validateAdvertise(advertisementDto,validateFileName );
//			ModelAndView mv = new ModelAndView(addAdvertisePhoto);
//			if(msg != null){
//				mv.addObject("advMsg", msg);
//				return mv;
//			}
//			
//			String uuid = UUIDUtil.uuid();
//			Calendar c = Calendar.getInstance();
//	   	 	SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhh");
//	        String thisDateFile = f.format(c.getTime());
//	        
//			String filename = uuid + thisDateFile;
//			FileInfoDTO fileInfoDTO = new FileInfoDTO();
//			fileInfoDTO.setFileName(filename+validateFileName);
//			FileHandler.getFileHandler().uploadFile(orginalFile.getInputStream(), fileInfoDTO);
//			
//			AdvertisementDto adv = advertiseService.queryAdvertiseById(advertisementDto.getId());
//			if(!StringUtil.isEmpty(adv.getImgpath())){
//				ImgFileUtil.deleteFile(adv.getImgpath());
//			}
//			
//			advertisementDto.setImgpath(filePath+File.separator +filename+validateFileName);
//		}
		
		advertisementDto.setStarttime(DateUtil.parse("yyyy-MM-dd HH:mm:ss", advertisementDto.getStarttimeStr()));
		advertisementDto.setEndtime(DateUtil.parse("yyyy-MM-dd HH:mm:ss", advertisementDto.getEndtimeStr()));
		
		advertiseService.updateAdvertise(advertisementDto);
		return new ModelAndView("redirect:" + WebConstants.ADVERTISE_PHOTO_LIST_URL);
	}
	

	
	public ModelAndView delAdvertisePhoto(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String idString = request.getParameter("id");
		String targets = request.getParameter("targets");
		if(idString != null){
			Long id = Long.valueOf(idString);
			advertisementDto = advertiseService.queryAdvertiseById(id);
			advertiseService.delAdvertisetById(id);
		}
		if(!StringUtil.isEmpty(advertisementDto.getImgpath())){
			ImgFileUtil.deleteFile(advertisementDto.getImgpath());
		}
		Integer locationCode  = 0;
		if(!StringUtil.isEmpty(targets)){
			Integer targetsInt = Integer.valueOf(targets);
			locationCode = advertiseService.getCountByLocation(targetsInt);
			advertiseService.doUpdateSortNextRnTx(targetsInt, locationCode);
		}
		
		return new ModelAndView("redirect:" + WebConstants.ADVERTISE_PHOTO_LIST_URL+targets);
	}
	
	
	public ModelAndView pictureView(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String idString = request.getParameter("id");
		if(idString != null){
			Long id = Long.valueOf(idString);
			advertisementDto = advertiseService.queryAdvertiseById(id);
		}
		PictureLoadView.downloadView(response, advertisementDto.getImgpath());
	
		return null;
	}

	

	public String getInitAdvertisePhoto() {
		return initAdvertisePhoto;
	}

	public void setInitAdvertisePhoto(String initAdvertisePhoto) {
		this.initAdvertisePhoto = initAdvertisePhoto;
	}

	public String getAddAdvertisePhoto() {
		return addAdvertisePhoto;
	}

	public void setAddAdvertisePhoto(String addAdvertisePhoto) {
		this.addAdvertisePhoto = addAdvertisePhoto;
	}

	public String getEditAdvertisePhoto() {
		return editAdvertisePhoto;
	}

	public void setEditAdvertisePhoto(String editAdvertisePhoto) {
		this.editAdvertisePhoto = editAdvertisePhoto;
	}

	public String getGetAdvertisePhoto() {
		return getAdvertisePhoto;
	}

	public void setGetAdvertisePhoto(String getAdvertisePhoto) {
		this.getAdvertisePhoto = getAdvertisePhoto;
	}

	public String getAdvertisePhotoList() {
		return advertisePhotoList;
	}

	public void setAdvertisePhotoList(String advertisePhotoList) {
		this.advertisePhotoList = advertisePhotoList;
	}

	public String getUpdateAdvertisePhoto() {
		return updateAdvertisePhoto;
	}

	public void setUpdateAdvertisePhoto(String updateAdvertisePhoto) {
		this.updateAdvertisePhoto = updateAdvertisePhoto;
	}

	public List<Advertisement> getAdvertiseList() {
		return advertiseList;
	}

	public void setAdvertiseList(List<Advertisement> advertiseList) {
		this.advertiseList = advertiseList;
	}

	public AdvertiseService getAdvertiseService() {
		return advertiseService;
	}

	public void setAdvertiseService(AdvertiseService advertiseService) {
		this.advertiseService = advertiseService;
	}

	public AdvertisementDto getAdvertisementDto() {
		return advertisementDto;
	}

	public void setAdvertisementDto(AdvertisementDto advertisementDto) {
		this.advertisementDto = advertisementDto;
	}

	
}
