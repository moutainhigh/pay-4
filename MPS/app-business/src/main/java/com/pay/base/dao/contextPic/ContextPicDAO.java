package com.pay.base.dao.contextPic;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pay.base.model.ContextPicture;

public interface ContextPicDAO {

	public abstract Long createContextPic(ContextPicture contextPic);

	public abstract void batchUpdateOwner(final List<ContextPicture> cpList)
			throws DataAccessException;

	@SuppressWarnings("unchecked")
	public abstract List<ContextPicture> queryPicByParam(
			ContextPicture contextPic);

	public abstract int deletePicByParam(ContextPicture contextPic);



}