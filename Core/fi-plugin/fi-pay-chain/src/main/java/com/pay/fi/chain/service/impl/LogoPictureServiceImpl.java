/**
 * 
 */
package com.pay.fi.chain.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.chain.dao.LogoPictureDao;
import com.pay.fi.chain.model.LogoPicture;
import com.pay.fi.chain.service.LogoPicService;

/**
 * @author PengJiangbo
 *
 */
public class LogoPictureServiceImpl implements LogoPicService {

	private Log logger = LogFactory.getLog(LogoPictureServiceImpl.class);
	 
	private LogoPictureDao logoPictureDao;
	
	@Override
	public Long createContextPic(LogoPicture logoPicture) {
		return  this.logoPictureDao.createlogoPic(logoPicture);
	}

	@Override
	public boolean deleteByPicId(Long pictureId) {
		 LogoPicture contextPic=new LogoPicture();
		 contextPic.setPictureId(pictureId);
		 return (logoPictureDao.deletePicByParam(contextPic)==1);
	}

	@Override
	public boolean batchUpdateOwner(LinkedList<Long> idsList, Long ownerId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LogoPicture queryPicListById(Long picId) {
		LogoPicture contextPic=new LogoPicture();
		 contextPic.setPictureId(picId);
		 List<LogoPicture> cpList=logoPictureDao.queryPicByParam(contextPic);
		 if(cpList!=null && cpList.size()>0){
			 contextPic=cpList.get(0);
		 }
		return contextPic;
	}

	@Override
	public List<LogoPicture> queryPicListByOwnerId(Long ownerId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLogoPictureDao(LogoPictureDao logoPictureDao) {
		this.logoPictureDao = logoPictureDao;
	}

	@Override
	public List<LogoPicture> findPicByMemberCode(Long memberCode) {
		return this.logoPictureDao.findPicByMemberCode(memberCode) ;
	}

	@Override
	public int updatePicByParam(LogoPicture logoPic) {
		return this.logoPictureDao.updatePicByParam(logoPic) ;
	}
	
}
