package com.pay.poss.featuremenu.service.impl;

import java.util.List;

import com.pay.inf.service.impl.BaseServiceImpl;
import com.pay.poss.featuremenu.dao.FeatureDAO;
import com.pay.poss.featuremenu.dto.FeatureDto;
import com.pay.poss.featuremenu.model.Feature;
import com.pay.poss.featuremenu.service.FeatureService;

public class FeatureServiceImpl extends BaseServiceImpl implements
		FeatureService {

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
		return ((FeatureDAO) getMainDao()).queryAllFeature();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureService#getFeature(java.
	 * lang.Long)
	 */
	public FeatureDto getFeature(Long featureId) {
		return (FeatureDto) ((FeatureDAO) getMainDao())
				.getFeatureById(featureId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureService#createFeature(com
	 * .pay.base.dto.FeatureDto)
	 */
	public Long createFeature(FeatureDto fDto) {
		return ((FeatureDAO) getMainDao()).creatFeature(fDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureService#updateFeature(com
	 * .pay.base.dto.FeatureDto)
	 */
	public boolean updateFeature(FeatureDto fDto) {
		return ((FeatureDAO) getMainDao()).updateFeature(fDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureService#getFeatureIdByLvl
	 * (int, int)
	 */
	public Long getFeatureIdByLvl(int securLevel, int appScale) {
		return ((FeatureDAO) getMainDao()).getFeatureIdByLvl(securLevel,
				appScale);
	}

	@Override
	protected Class getModelClass() {
		return Feature.class;
	}
}
