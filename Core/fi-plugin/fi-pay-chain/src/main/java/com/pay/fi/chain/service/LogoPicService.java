/**
 * 
 */
package com.pay.fi.chain.service;

import java.util.LinkedList;
import java.util.List;

import com.pay.fi.chain.model.LogoPicture;


/**
 * @author PengJiangbo
 *
 */
public interface LogoPicService {

	public abstract Long createContextPic(LogoPicture logoPicture);

	public abstract boolean deleteByPicId(Long pictureId);

	public abstract boolean batchUpdateOwner(LinkedList<Long> idsList,Long ownerId);

	public abstract LogoPicture queryPicListById(Long picId);

	public abstract List<LogoPicture> queryPicListByOwnerId(Long ownerId);
	
	public List<LogoPicture> findPicByMemberCode(Long memberCode ) ;
	
	int updatePicByParam(LogoPicture logoPic) ;
	
}
