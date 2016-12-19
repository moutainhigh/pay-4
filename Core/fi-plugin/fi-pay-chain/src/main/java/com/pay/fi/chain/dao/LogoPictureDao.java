/**
 * 
 */
package com.pay.fi.chain.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pay.fi.chain.model.LogoPicture;

/**
 * @author PengJiangbo
 *
 */
public interface LogoPictureDao {

	public abstract Long createlogoPic(LogoPicture logoPic);

	public abstract void batchUpdateOwner(final List<LogoPicture> cpList)
			throws DataAccessException;

	public abstract List<LogoPicture> queryPicByParam(
			LogoPicture logoPic);

	public abstract int deletePicByParam(LogoPicture logoPic);
	
	public List<LogoPicture> findPicByMemberCode(Long memberCode ) ;
	
	int updatePicByParam(LogoPicture logoPic) ;
	
}
