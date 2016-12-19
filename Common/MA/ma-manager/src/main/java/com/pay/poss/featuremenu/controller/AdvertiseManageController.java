package com.pay.poss.featuremenu.controller;

import java.util.List;

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

public class AdvertiseManageController extends MultiActionController{
	//初始化广告管理
	private String initAdvertise;
	//新增广告
	private String addAdvertise;
	//编辑广告位
	private String editAdvertise;
	//获取广告位
	private String getAdvertise;
	//首页广告位类表
	private String advertiseList;
	//更新首页广告位类表
	private String updateAdvertise;
	
	
	private List<Advertisement> advList = null;
	
	private AdvertiseService advertiseService;
	
	private AdvertisementDto advertisementDto;
	
	public ModelAndView initAdvertise(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String targets = request.getParameter("targets");
		return new ModelAndView(initAdvertise).addObject("targets", targets);
	}
	
	public ModelAndView addAdvertise(HttpServletRequest request,
			HttpServletResponse response,AdvertisementDto advertisementDto) throws Exception {
		Integer locationCode  = 0;
		
		locationCode = advertiseService.getCountByLocation(advertisementDto.getTargets());
		advertisementDto.setSort(locationCode + 1);
		advertisementDto.setAdvtype(WebConstants.ADV_TYPE_INNER);
		
//		//上传图片
//		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
//		MultipartFile advIndex  =  multipartRequest.getFile("imgfile"); 
		
//		String validateFileName = advIndex.getOriginalFilename();
		
		advertisementDto.setStarttime(DateUtil.parse("yyyy-MM-dd HH:mm:ss", advertisementDto.getStarttimeStr()));
		advertisementDto.setEndtime(DateUtil.parse("yyyy-MM-dd HH:mm:ss", advertisementDto.getEndtimeStr()));
		
		String validateFileName = null;
		String msg = AdvertiseValidator.validateAdvertise(advertisementDto, validateFileName);
		ModelAndView mv = new ModelAndView(addAdvertise);
		mv.addObject("targets", advertisementDto.getTargets());
		if(msg != null){
			mv.addObject("advMsg", msg);
			return mv;
		}
		
//		String appealCode = WebConstants.ADVERTISE_UPLOAD_PATH;
//		String uuid = UUIDUtil.uuid();
//		Calendar c = Calendar.getInstance();
//   	 	SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhh");
//        String thisDateFile = f.format(c.getTime());
//        
//		String filename = uuid + thisDateFile;
//		String filePath = ImgFileUtil.upLoadImg(advIndex, filename, appealCode);
//		
//		advertisementDto.setImgpath(filePath);
		advertiseService.creatAdvertise(advertisementDto);
		mv.addObject("advMsg", "保存成功");
		return mv;
	}
	
	public ModelAndView advertiseList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Integer locationCode  = 0;
		String targets = request.getParameter("targets");
		Integer targetsInt;
		if(!StringUtil.isEmpty(targets)){
			targetsInt = Integer.valueOf(targets);
		}else{
			targetsInt = TargetsEnums.INDEX_ADV.getCode();
		}
		locationCode = advertiseService.getCountByLocation(targetsInt);
		advList = advertiseService.queryAdvertiseListByTargets(targetsInt);
		
		ModelAndView mv = new ModelAndView(advertiseList);
		mv.addObject("advList", advList);
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
			return new ModelAndView("redirect:" + WebConstants.ADVERTISE_LIST_URL+targets).addObject("errormsg", "数据错误");
		}
		return new ModelAndView("redirect:" + WebConstants.ADVERTISE_LIST_URL+targets);
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
			return new ModelAndView("redirect:" + WebConstants.ADVERTISE_LIST_URL+targets).addObject("errormsg", "数据错误");
		}
		return new ModelAndView("redirect:" + WebConstants.ADVERTISE_LIST_URL+targets);
	}
	
	
	public ModelAndView getAdvertise(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String idString = request.getParameter("id");
		if(idString != null){
			Long id = Long.valueOf(idString);
			advertisementDto = advertiseService.queryAdvertiseById(id);
			advertisementDto.setStarttimeStr(DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", advertisementDto.getStarttime()));
			advertisementDto.setEndtimeStr(DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss", advertisementDto.getEndtime()));
		}
		return new ModelAndView(getAdvertise).addObject("advertisementDto", advertisementDto);
	}
	
	public ModelAndView updateAdvertise(HttpServletRequest request,
			HttpServletResponse response,AdvertisementDto advertisementDto) throws Exception {
		//上传图片
//		MultipartHttpServletRequest multipartRequest  =  (MultipartHttpServletRequest) request;
//		MultipartFile advIndex  =  multipartRequest.getFile("imgfile"); 
		
//		String validateFileName = advIndex.getOriginalFilename();
		advertisementDto.setStarttime(DateUtil.parse("yyyy-MM-dd HH:mm:ss", advertisementDto.getStarttimeStr()));
		advertisementDto.setEndtime(DateUtil.parse("yyyy-MM-dd HH:mm:ss", advertisementDto.getEndtimeStr()));
		String validateFileName = null;
		String msg = AdvertiseValidator.validateAdvertise(advertisementDto, validateFileName);
		ModelAndView mv = new ModelAndView(updateAdvertise);
		if(msg != null){
			mv.addObject("advMsg", msg);
			mv.addObject("advertisementDto", advertisementDto);
			return mv;
		}
		
//		String appealCode = WebConstants.ADVERTISE_UPLOAD_PATH;
//		String uuid = UUIDUtil.uuid();
//		Calendar c = Calendar.getInstance();
//   	 	SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhh");
//        String thisDateFile = f.format(c.getTime());
//        
//		String filename = uuid + thisDateFile;
//		String filePath = ImgFileUtil.upLoadImg(advIndex, filename, appealCode);
//		
//		if(filePath != null){
//			AdvertisementDto adv = advertiseService.queryAdvertiseById(advertisementDto.getId());
//			if(!StringUtil.isEmpty(adv.getImgpath())){
//				ImgFileUtil.deleteFile(adv.getImgpath());
//			}
//		}
//		advertisementDto.setImgpath(filePath);
		advertiseService.updateAdvertise(advertisementDto);
		return new ModelAndView("redirect:" + WebConstants.ADVERTISE_LIST_URL+advertisementDto.getTargets());
	}
	
	public ModelAndView delAdvertise(HttpServletRequest request,
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
		
		return new ModelAndView("redirect:" + WebConstants.ADVERTISE_LIST_URL+targets);
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

	public String getInitAdvertise() {
		return initAdvertise;
	}

	public void setInitAdvertise(String initAdvertise) {
		this.initAdvertise = initAdvertise;
	}

	public String getAddAdvertise() {
		return addAdvertise;
	}

	public void setAddAdvertise(String addAdvertise) {
		this.addAdvertise = addAdvertise;
	}

	public String getEditAdvertise() {
		return editAdvertise;
	}

	public void setEditAdvertise(String editAdvertise) {
		this.editAdvertise = editAdvertise;
	}

	public String getGetAdvertise() {
		return getAdvertise;
	}

	public void setGetAdvertise(String getAdvertise) {
		this.getAdvertise = getAdvertise;
	}

	public AdvertiseService getAdvertiseService() {
		return advertiseService;
	}

	public void setAdvertiseService(AdvertiseService advertiseService) {
		this.advertiseService = advertiseService;
	}

	public String getAdvertiseList() {
		return advertiseList;
	}

	public void setAdvertiseList(String advertiseList) {
		this.advertiseList = advertiseList;
	}

	public List<Advertisement> getAdvList() {
		return advList;
	}

	public void setAdvList(List<Advertisement> advList) {
		this.advList = advList;
	}

	public AdvertisementDto getAdvertisementDto() {
		return advertisementDto;
	}

	public void setAdvertisementDto(AdvertisementDto advertisementDto) {
		this.advertisementDto = advertisementDto;
	}

	public String getUpdateAdvertise() {
		return updateAdvertise;
	}

	public void setUpdateAdvertise(String updateAdvertise) {
		this.updateAdvertise = updateAdvertise;
	}
	
}
