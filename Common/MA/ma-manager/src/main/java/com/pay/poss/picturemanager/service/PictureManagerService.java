package com.pay.poss.picturemanager.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.poss.picturemanager.dto.PayChainPictureDto;

/** 

* @Title: PictureManagerService.java
* @Package com.pay.poss.picturemanager.service
* @Description: 图片管理服务
* @author cf
* @date 2011-12-28 下午3:33:43
* @version V1.0 
*/ 
public interface PictureManagerService {

	public List<PayChainPictureDto> queryPictureManagerByCondition(
			PayChainPictureDto pictureDto,Page  page) ;
	
	/**根据支付链号查询图片信息
	 * @param pictureOwnerId
	 * @return
	 */
	public List<PayChainPictureDto> queryPictureManagerByPayChainNumber(Integer pictureOwnerId);
	
	/**更新图片状态
	 * @param pictureDto
	 * @return
	 */
	public boolean updatePictureStatus(PayChainPictureDto pictureDto);
}
