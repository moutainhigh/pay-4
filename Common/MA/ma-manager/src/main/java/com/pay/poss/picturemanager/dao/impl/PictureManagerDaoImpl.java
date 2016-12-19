package com.pay.poss.picturemanager.dao.impl;

import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.picturemanager.dao.PictureManagerDao;
import com.pay.poss.picturemanager.dto.PayChainPictureDto;

@SuppressWarnings("unchecked")
public class PictureManagerDaoImpl  extends BaseDAOImpl<PayChainPictureDto> implements PictureManagerDao  {

	@Override
	public List<PayChainPictureDto>  queryPictureManagerByCondition(
			PayChainPictureDto pictureDto) {
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("queryPictureManagerByCondition"),pictureDto);
	}

	@Override
	public Integer countPictureManagerByCondition(PayChainPictureDto pictureDto) {
		Integer obj=(Integer) getSqlMapClientTemplate().queryForObject(namespace.concat("countPictureManagerByCondition"),pictureDto);
		if(null==obj){
			return 0;
		}
		return obj;
	}

	@Override
	public List<PayChainPictureDto> queryPictureManagerByPayChainNumber(
			Integer pictureOwnerId) {
		return this.getSqlMapClientTemplate().queryForList(namespace.concat("queryPictureManagerByPayChainNumber"),pictureOwnerId);
	}

	@Override
	public boolean updatePictureStatus(PayChainPictureDto pictureDto) {
		return getSqlMapClientTemplate().update(namespace.concat("update"), pictureDto)>= 1;		
	}

}
