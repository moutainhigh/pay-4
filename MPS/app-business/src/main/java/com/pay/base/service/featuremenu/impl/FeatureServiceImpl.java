package com.pay.base.service.featuremenu.impl;

import java.util.List;

import com.pay.base.dao.featuremenu.FeatureDAO;
import com.pay.base.dto.FeatureDto;
import com.pay.base.service.featuremenu.FeatureService;
import com.pay.inf.dao.BaseDAO;

public class FeatureServiceImpl implements FeatureService {

	private BaseDAO mainDao;

	public void setMainDao(BaseDAO mainDao) {
		this.mainDao = mainDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.base.service.featuremenu.impl.FeatureService#getDtoClass()
	 */
	@Override
	public Class getDtoClass() {
		return FeatureDto.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureService#queryAllFeature()
	 */
	public List<FeatureDto> queryAllFeature() {
		return ((FeatureDAO) mainDao).queryAllFeature();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureService#getFeature(java.
	 * lang.Long)
	 */
	public FeatureDto getFeature(Long featureId) {
		return (FeatureDto) ((FeatureDAO) mainDao).getFeatureById(featureId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureService#createFeature(com
	 * .pay.base.dto.FeatureDto)
	 */
	public Long createFeature(FeatureDto fDto) {
		return ((FeatureDAO) mainDao).creatFeature(fDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureService#updateFeature(com
	 * .pay.base.dto.FeatureDto)
	 */
	public boolean updateFeature(FeatureDto fDto) {
		return ((FeatureDAO) mainDao).updateFeature(fDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureService#getFeatureIdByLvl
	 * (int, int)
	 */
	public Long getFeatureIdByLvl(int securLevel, int appScale) {
		return ((FeatureDAO) mainDao).getFeatureIdByLvl(securLevel, appScale);
	}
}
