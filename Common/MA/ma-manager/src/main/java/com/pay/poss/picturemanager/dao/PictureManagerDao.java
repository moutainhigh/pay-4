package com.pay.poss.picturemanager.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.picturemanager.dto.PayChainPictureDto;

public interface PictureManagerDao  extends  BaseDAO<PayChainPictureDto>{

	/**
	 * 根据条件查询支付链图片信息
	 * @param pictureDto
	 * @return
	 */
	public List<PayChainPictureDto>  queryPictureManagerByCondition(PayChainPictureDto pictureDto);
	
	/**根据支付链号查询图片信息
	 * @param pictureOwnerId
	 * @return
	 */
	public List<PayChainPictureDto> queryPictureManagerByPayChainNumber(Integer pictureOwnerId);
	
	public  Integer countPictureManagerByCondition(PayChainPictureDto pictureDto);
	
	public boolean updatePictureStatus(PayChainPictureDto pictureDto);
		
}
