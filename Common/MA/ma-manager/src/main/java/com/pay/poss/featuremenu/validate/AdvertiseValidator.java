package com.pay.poss.featuremenu.validate;

import com.pay.poss.featuremenu.dto.AdvertisementDto;
import com.pay.util.StringUtil;





public class AdvertiseValidator {
	  
	  public static String validateAdvertise(AdvertisementDto advertisementDto,String fileName){
//		  String[] picTypes = {"gif","jpeg","png","bmp","jpg"};
		  String validateString = null;
		  if(StringUtil.isEmpty(advertisementDto.getTitle())){
			  return validateString = "广告标题不能为空";
		  }
//		  if(!StringUtil.isEmpty(fileName)){
//			  int indexTail = fileName.lastIndexOf(".");
//			  if(indexTail == -1){
//				  return validateString = "图片格式错误";
//			  }
//			  String tailName = fileName.substring(indexTail + 1);
//			  tailName = tailName.toLowerCase();
//			  for (String picType : picTypes) {
//				if(picType.equals(tailName)){
//					validateString = null;
//					return  null;
//				}
//			  }
//			  return validateString = "图片格式错误";
//		  }
		  if(advertisementDto.getStarttime() == null){
			  return validateString = "开始时间不能为空！";
		  }
		  if(advertisementDto.getEndtime() == null){
			  return validateString = "结束时间不能为空！";
		  }
		  if(advertisementDto.getStarttime().after(advertisementDto.getEndtime())){
			  return validateString = "开始时间必须在结束时间之前！";
		  }
		  if(advertisementDto.getImgpath() == null){
			  return validateString = "图片路劲不能为空！";
		  }
		  return validateString;
	  }
	  
}
