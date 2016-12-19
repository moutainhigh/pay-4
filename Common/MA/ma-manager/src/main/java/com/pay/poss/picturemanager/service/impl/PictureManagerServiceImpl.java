package com.pay.poss.picturemanager.service.impl;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.poss.picturemanager.dao.PictureManagerDao;
import com.pay.poss.picturemanager.dto.PayChainPictureDto;

public class PictureManagerServiceImpl implements
		com.pay.poss.picturemanager.service.PictureManagerService {

	private PictureManagerDao pictureManagerDao;	


	@Override
	public List<PayChainPictureDto>  queryPictureManagerByCondition(
			PayChainPictureDto pictureDto,Page  page) {
		Integer totalCount=pictureManagerDao.countPictureManagerByCondition(pictureDto);
		pictureDto=setPage(pictureDto, page, totalCount);
		if(null==pictureDto){
			return null ;
		}
		return pictureManagerDao.queryPictureManagerByCondition(pictureDto);
	}

	@Override
	public boolean updatePictureStatus(PayChainPictureDto pictureDto) {		
		return pictureManagerDao.updatePictureStatus(pictureDto);
	}

	
	@Override
	public List<PayChainPictureDto> queryPictureManagerByPayChainNumber(
			Integer pictureOwnerId) {		
		return pictureManagerDao.queryPictureManagerByPayChainNumber(pictureOwnerId);
	}
	
	private PayChainPictureDto  setPage(PayChainPictureDto dto,Page  page,Integer totalCount){
		Integer pageStartRow;// 起始行
		Integer pageEndRow;// 结束行
        if (null == page || totalCount == 0) {
			  return null;
        }
        page.setTotalCount(totalCount);
        pageEndRow = page.getPageNo() * page.getPageSize();
        if ((page.getPageNo() - 1) == 0) {
        	pageStartRow = 0;
        }else {
        	pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
        }
        dto.setPageStartRow(pageStartRow);
        dto.setPageEndRow(pageEndRow);
        
    	
		return dto;
	}
	
	public void setPictureManagerDao(PictureManagerDao pictureManagerDao) {
		this.pictureManagerDao = pictureManagerDao;
	}


}
