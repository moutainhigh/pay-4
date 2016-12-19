package com.pay.base.service.contextPic.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.dao.contextPic.ContextPicDAO;
import com.pay.base.model.ContextPicture;
import com.pay.base.service.contextPic.ContextPicService;



public class ContextPicServiceImpl implements ContextPicService {
	 private Log logger = LogFactory.getLog(ContextPicServiceImpl.class);
	 
	 private ContextPicDAO contextPicDao;

	 
	 
	 /* (non-Javadoc)
	 * @see com.pay.base.service.contextPic.impl.ContextPicService#createContextPic(com.pay.base.model.ContextPicture)
	 */
	public Long createContextPic(ContextPicture contextPic){
		return  contextPicDao.createContextPic(contextPic);
	 }
	 
	 
	 
	 /* (non-Javadoc)
	 * @see com.pay.base.service.contextPic.impl.ContextPicService#deleteByPicId(java.lang.Long)
	 */
	public boolean deleteByPicId(Long pictureId){
		 ContextPicture contextPic=new ContextPicture();
		 contextPic.setPictureId(pictureId);
		 return (contextPicDao.deletePicByParam(contextPic)==1);
	 }
	 
	 
	 /* (non-Javadoc)
	 * @see com.pay.base.service.contextPic.impl.ContextPicService#batchUpdateOwner(java.lang.String[], java.lang.Long)
	 */
	public boolean batchUpdateOwner(LinkedList<Long> idsList,Long ownerId){
		 ContextPicture contextPic=null;
		 List<ContextPicture> cpList=null;
		 if(idsList!=null && idsList.size()>0){
			 cpList=new ArrayList<ContextPicture>(idsList.size());
			 for(Long id:idsList){
				 contextPic=new ContextPicture();
				 contextPic.setPictureId(Long.valueOf(id));
				 contextPic.setPictureOwnerId(ownerId);
				 cpList.add(contextPic);
			 }
		 }
		 if(cpList!=null && cpList.size()>0){
			 contextPicDao.batchUpdateOwner(cpList);
			 return true;
		 }
		 return false;
	 } 
	 
	 
	 /* (non-Javadoc)
	 * @see com.pay.base.service.contextPic.impl.ContextPicService#queryPicListById(java.lang.Long)
	 */
	public ContextPicture queryPicListById(Long picId){
		 ContextPicture contextPic=new ContextPicture();
		 contextPic.setPictureId(picId);
		 List<ContextPicture> cpList=contextPicDao.queryPicByParam(contextPic);
		 if(cpList!=null && cpList.size()>0){
			 contextPic=cpList.get(0);
		 }
		return contextPic;
	 }
	 
	 
	 /* (non-Javadoc)
	 * @see com.pay.base.service.contextPic.impl.ContextPicService#queryPicListByOwnerId(java.lang.Long)
	 */
	public List<ContextPicture> queryPicListByOwnerId(Long ownerId){
		 ContextPicture contextPic=new ContextPicture();
		 contextPic.setPictureOwnerId(ownerId);
		return contextPicDao.queryPicByParam(contextPic);
	 }
	 
	public void setContextPicDao(ContextPicDAO contextPicDao) {
		this.contextPicDao = contextPicDao;
	}
	 
	 
	 
}
