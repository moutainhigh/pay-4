package com.pay.app.controller.common;

import java.util.LinkedList;

import com.pay.base.service.common.ImgResonse;

public class UploadStatistics {
	private int uploadNum;
	private LinkedList<ImgResonse> imgList;
	
	public UploadStatistics(int uploadNum,LinkedList<ImgResonse> imgList){
		this.uploadNum=uploadNum;
		this.imgList=imgList;
	}
	
	public int getUploadNum() {
		return uploadNum;
	}
	public void setUploadNum(int uploadNum) {
		this.uploadNum = uploadNum;
	}

	public LinkedList<ImgResonse> getImgList() {
		return imgList;
	}

	public void setImgList(LinkedList<ImgResonse> imgList) {
		this.imgList = imgList;
	}

	public LinkedList<Long> getIdList(){
		LinkedList<Long> idList=null;
		LinkedList<ImgResonse> imgList=this.getImgList();
		if(imgList!=null){
			idList=new LinkedList<Long>();
			for(ImgResonse resp:imgList){
				idList.add(resp.getImgId());
			}
		}
		return idList;
	}
	
	
}
