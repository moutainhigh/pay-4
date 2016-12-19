package com.pay.base.service.contextPic;

import java.util.LinkedList;
import java.util.List;

import com.pay.base.model.ContextPicture;

public interface ContextPicService {

	public abstract Long createContextPic(ContextPicture contextPic);

	public abstract boolean deleteByPicId(Long pictureId);

	public abstract boolean batchUpdateOwner(LinkedList<Long> idsList,Long ownerId);

	public abstract ContextPicture queryPicListById(Long picId);

	public abstract List<ContextPicture> queryPicListByOwnerId(Long ownerId);

}