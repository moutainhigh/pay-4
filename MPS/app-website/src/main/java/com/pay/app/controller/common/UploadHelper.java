package com.pay.app.controller.common;


import java.util.LinkedList;

import javax.servlet.http.HttpServletRequest;

import com.pay.app.base.session.RequestLocal;
import com.pay.base.service.common.ImgResonse;

public class UploadHelper {
	private static final String uploadKey="payChainUpload";
	private static final int maxNum=4;
	
	/**
	 * 验证上传的图片的次数
	 * @return
	 */
	public static boolean validateUploadNum(){
		UploadStatistics us=getUploadStatistics();
		if(us!=null){
			return (us.getUploadNum()<maxNum);
		}
		return true;
	}
	
	public static void removeUploadNum(Long picId){
		HttpServletRequest request=RequestLocal.getRequest();
		UploadStatistics us=getUploadStatistics();
		if(us!=null){
			LinkedList<ImgResonse> picIdList=removeImgList(picId,us.getImgList());
			us=new UploadStatistics(us.getUploadNum()-1, picIdList);
			request.getSession().setAttribute(uploadKey, us);
		}
		
	}
	
	public static void clearUploadNum(){
		HttpServletRequest request=RequestLocal.getRequest();
		request.getSession().removeAttribute(uploadKey);   
	}
	
	public static boolean checkPicId(Long picId){
		UploadStatistics us=getUploadStatistics();
		if(us!=null){
			LinkedList<ImgResonse> picIdList=us.getImgList();
			if(picIdList!=null && validateImgList(picId,picIdList)){
				return true;
			}
		}
		
		return false;
	}
	
	private static LinkedList<ImgResonse> removeImgList(Long picId,LinkedList<ImgResonse> picIdList){
		for(ImgResonse resp:picIdList){
			if(resp.getImgId().equals(picId)){
				picIdList.remove(resp);
				return picIdList;
			}
				
		}
		return picIdList;
		
	}
	
	private static boolean validateImgList(Long picId,LinkedList<ImgResonse> picIdList){
		for(ImgResonse resp:picIdList){
			if(resp.getImgId().equals(picId))
				return true;
		}
		return false;
	}
	
	public static void generateUploadNum(Long picId,String picPath){
		HttpServletRequest request=RequestLocal.getRequest();
		UploadStatistics us=getUploadStatistics();
		LinkedList<ImgResonse> imgList=null;
		int num=1;
		if(us!=null){
			imgList=us.getImgList();
			num=us.getUploadNum()+1;
		}else{
			imgList=new LinkedList<ImgResonse>();
		}
		ImgResonse img=new ImgResonse();
		img.setImgId(picId);
		img.setImgUrl(picPath);
		imgList.add(img);
		us=new UploadStatistics(num, imgList);
		request.getSession().setAttribute(uploadKey, us);
	}
	
	public static UploadStatistics getUploadStatistics(){
		HttpServletRequest request=RequestLocal.getRequest();
		UploadStatistics us=null;
		if(request.getSession().getAttribute(uploadKey)!=null)
			us=(UploadStatistics)request.getSession().getAttribute(uploadKey);
		return us;
		
	}
	
	
	public static void main(String[] args) {
		LinkedList<ImgResonse> picIdList=new LinkedList<ImgResonse>();
		ImgResonse rp1=new ImgResonse();
		rp1.setImgId(2L);
		rp1.setImgUrl("gdfgfdgdf");
		
		ImgResonse rp2=new ImgResonse();
		rp2.setImgId(4L);
		rp2.setImgUrl("gfhfghgfhf");
		
		ImgResonse rp3=new ImgResonse();
		rp3.setImgId(5L);
		rp3.setImgUrl("5676uyiyuiyui");
		
		ImgResonse rp4=new ImgResonse();
		rp4.setImgId(9L);
		rp4.setImgUrl("ouiouppiopoip");
		
		picIdList.add(rp1);
		picIdList.add(rp2);
		picIdList.add(rp3);
		picIdList.add(rp4);
		
		picIdList=UploadHelper.removeImgList(9L, picIdList);
		
		for(ImgResonse resp:picIdList){
			System.out.println("id=["+resp.getImgId()+"]path=["+resp.getImgUrl()+"]");
		}
	}
	
}
